package net;

import collection.DragonDAO;
import com.fasterxml.jackson.core.JsonParseException;
import commands.Command;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import io.Autosaver;
import io.FileManipulator;
import json.Json;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;


public class Server {
    Selector selector;
    InetSocketAddress address;
    DatagramChannel channel;
    Instances instances;
    Map<String, String> accounts = new HashMap<String, String>();
    ArrayList<String> logins = new ArrayList<>();
    boolean rightPassword;
    boolean loginsSended;

    public Server(String host, int port) throws IOException {
        selector = Selector.open();
        address = new InetSocketAddress(host, port);
        channel = DatagramChannel.open().bind(address);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, new ClientClass());
        instances = new Instances();
        accounts.put("11111", "b0baee9d279d34fa1dfd71aadb908c3f");
        accounts.put("22222", "b0baee9d279d34fa1dfd71aadb908c3f");
        logins.add("11111");
        logins.add("22222");
        rightPassword = false;
        loginsSended = false;
    }

    public void run() throws IOException, NullPointerException, InterruptedException {

        try {
            instances.dao = FileManipulator.get();
        } catch (RuntimeException e) {
            instances.outPutter.output(e.getMessage());
            instances.dao = new DragonDAO();
        }


        while (true) {
            selector.select();
            for (SelectionKey k: selector.selectedKeys()) {
                if(k.isReadable()) {
                    String request = read(k);
                    if (Objects.equals(request, "send me logins")){
                        instances.outPutter.output(loginsToString(logins));
                        List<String> list = instances.outPutter.compound();
                        Server.writeLayer(k, list, instances);
                        list.clear();
                        loginsSended = true;
                    }

                    else{
                        ArrayList<String> loginAndPassword = takeLoginAndPassword(request);

                        if(loginAndPassword.get(0).equals("N")){
                            newAccount(loginAndPassword, k);
                        }
                        else{
                            if (!rightPassword){
                                rightPassword = checkPassword(loginAndPassword, k);
                            }
                            else{
                                commandExecution(request, k);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkPassword(ArrayList<String> loginAndPassword, SelectionKey k){
        boolean rightPassword = false;
        if (accounts.get(loginAndPassword.get(1)).equals(loginAndPassword.get(2))){
            rightPassword = true;
            instances.outPutter.output("YES");
            List<String> list = instances.outPutter.compound();
            Server.writeLayer(k, list, instances);
            list.clear();
        }
        else{
            instances.outPutter.output("NO");
            List<String> list = instances.outPutter.compound();
            Server.writeLayer(k, list, instances);
            list.clear();
        }
        return rightPassword;
    }

    private void newAccount(ArrayList<String> loginAndPassword, SelectionKey k){
        accounts.put(loginAndPassword.get(1), loginAndPassword.get(2));
        instances.outPutter.output("Новый пользователь создан");
        List<String> list = instances.outPutter.compound();
        Server.writeLayer(k, list, instances);
        list.clear();
    }


    private void commandExecution(String request, SelectionKey k) throws InterruptedException {
        Runnable requestHandling = () -> {
            try{
                Command command = Command.restoreFromProperties(Json.fromJson(Json.parse(request), CommandProperties.class));
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
        Autosaver.autosave(instances);
        list.clear();
    }

    private String loginsToString(ArrayList<String> logins){
        StringBuilder result = new StringBuilder();
        for(String login : logins){
            result.append(login).append('\t');
        }
        return result.toString();
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
    private ArrayList<String> takeLoginAndPassword(String passwordAndLogin){
        ArrayList<String> passwordAndLoginList = new ArrayList<>();
        char sign = '\t';
        String login = "";
        String password = "";
        int flag = 0;
        String info = String.valueOf(passwordAndLogin.charAt(0));

        for(int i = 1; i < passwordAndLogin.length(); i++){
            if (flag == 1){
                login = login + passwordAndLogin.charAt(i);
            }

            if (passwordAndLogin.charAt(i) == sign){
                flag = 1;
            }

            if (flag == 0){
                password = password + passwordAndLogin.charAt(i);
            }
        }
        passwordAndLoginList.add(info);
        passwordAndLoginList.add(login);
        passwordAndLoginList.add(password);
        System.out.println(info);
        System.out.println(login);
        System.out.println(password);
        return passwordAndLoginList;
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





//        DatagramChannel channel = (DatagramChannel) key.channel();
//        ClientClass client = (ClientClass) key.attachment();
//        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
//        client.buffer.clear();
//        client.clientAddress = channel.receive(client.buffer);
//        if(client.clientAddress != null) {
//            client.buffer.flip();
//            return StandardCharsets.UTF_16.decode(client.buffer).toString();
//        }
//        return null;

