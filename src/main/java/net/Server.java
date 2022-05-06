package net;

import collection.DragonDAO;
import collection.SQLDragonDAO;
import com.fasterxml.jackson.core.JsonParseException;
import commands.Command;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import io.Autosaver;
import io.FileManipulator;
import jdbc.UserManager;
import json.Json;
import net.auth.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;


public class Server {
    Selector selector;
    InetSocketAddress address;
    DatagramChannel channel;
    Instances instances;
    ExecutorService service;
    ForkJoinPool forkJoinPool;


    public Server(String host, int port) throws IOException {
        selector = Selector.open();
        address = new InetSocketAddress(host, port);
        channel = DatagramChannel.open().bind(address);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, new ClientClass());
        instances = new Instances();
        service = Executors.newFixedThreadPool(2);
        forkJoinPool = ForkJoinPool.commonPool();

    }

    public void run() throws IOException, NullPointerException, InterruptedException {

        try {
            instances.dao = new SQLDragonDAO();
        } catch (RuntimeException e) {
            instances.outPutter.output(e.getMessage());
        }

        while (true) {
            selector.select();
            for (SelectionKey k : selector.selectedKeys()) {
                if (k.isReadable()) {
                    String request = read(k);
                    commandExecution(request, k);
                }
            }
        }
    }

    private boolean checkPassword(List<String> loginAndPassword, SelectionKey k){
        boolean rightPassword = false;
        try {
            if (UserManager.getPassword(loginAndPassword.get(1)).equals(loginAndPassword.get(2))) {
                rightPassword = true;
                instances.outPutter.output("YES");
                List<String> list = instances.outPutter.compound();
                Server.writeLayer(k, list, instances);
                list.clear();
            } else {
                instances.outPutter.output("NO");
                List<String> list = instances.outPutter.compound();
                Server.writeLayer(k, list, instances);
                list.clear();
            }
        } catch (SQLException e) {
            return false;
        }
        return rightPassword;
    }

//    private void newAccount(List<String> loginAndPassword, SelectionKey k){
//        try {
//            UserManager.registerUser(loginAndPassword.get(1), loginAndPassword.get(2));
//        } catch (SQLException ignored) {return;}
//        instances.outPutter.output("Новый пользователь создан" + System.lineSeparator());
//        List<String> list = instances.outPutter.compound();
//        Server.writeLayer(k, list, instances);
//        list.clear();
//    }


    private synchronized void commandExecution(String request, SelectionKey k) {
        Runnable requestHandling = () -> {
            try {
                Request req = Json.fromJson(Json.parse(request), Request.class);
                Command command = Command.restoreFromProperties(req.properties, req.user);
                command.execute(instances);

            } catch (JsonParseException e) {
                instances.outPutter.output("Ben запретил такое отправлять" + System.lineSeparator() + e.getMessage());

            } catch (IOException e) {
                instances.outPutter.output("Ben запретил такое отправлять" + System.lineSeparator() + e.getMessage());
            }

        };

        Thread handlingThread = new Thread(requestHandling);
        handlingThread.start();

        List<String> list = instances.outPutter.compound();
        writeLayer(k, list, instances);

        list.clear();
    }



    private synchronized String read(SelectionKey key) throws IOException {

        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();

        RecursiveAction task1 = new RecursiveAction() {
            @Override
            protected void compute() {
                client.buffer.clear();
                try {
                    client.clientAddress = channel.receive(client.buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        forkJoinPool.invoke(task1);

        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                if(client.clientAddress != null){
                    client.buffer.flip();
                    return StandardCharsets.UTF_16.decode(client.buffer).toString();
                }
                return null;
            }
        };

        return forkJoinPool.invoke(task);
    }

    private synchronized void writeLayer(SelectionKey k, List<String> list, Instances instances){
            try{
                for (String msg : list) {
                    write(k, msg);

                    TimeUnit.MILLISECONDS.sleep(10);
                }
                write(k, "END");
            }
            catch(NullPointerException | IOException | InterruptedException e){
                instances.outPutter.output(e.getMessage());
            }
    }

    private synchronized void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();
        Runnable task = () -> {
            try {
                channel.send(StandardCharsets.UTF_16.encode(msg), client.clientAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        service.execute(task);
    }

    static class ClientClass {
        public SocketAddress clientAddress;
        public ByteBuffer buffer = ByteBuffer.allocate(1024);
    }
}

