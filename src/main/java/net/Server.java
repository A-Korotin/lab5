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

    public Server(String host, int port) throws IOException {
        selector = Selector.open();
        address = new InetSocketAddress(host, port);
        channel = DatagramChannel.open().bind(address);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, new ClientClass());
        instances = new Instances();
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


    private void commandExecution(String request, SelectionKey k) throws InterruptedException {
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
        handlingThread.join();

        List<String> list = instances.outPutter.compound();
        Server.writeLayer(k, list, instances);

        list.clear();
    }



    private synchronized static String read(SelectionKey key) throws IOException {

        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

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
        task1.join();

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
        String request = forkJoinPool.invoke(task);
        forkJoinPool.shutdown();
        return request;
    }

    private static void writeLayer(SelectionKey k, List<String> list, Instances instances){
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

    private synchronized static void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();
        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            try {
                channel.send(StandardCharsets.UTF_16.encode(msg), client.clientAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        service.execute(task);
        service.shutdown();
    }

    static class ClientClass {
        public SocketAddress clientAddress;
        public ByteBuffer buffer = ByteBuffer.allocate(1024);
    }
}

