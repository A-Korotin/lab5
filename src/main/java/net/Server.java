package net;

import collection.SQLDragonDAO;
import com.fasterxml.jackson.core.JsonParseException;
import commands.Command;
import commands.dependencies.Instances;
import exceptions.DatabaseException;
import io.OutPutter;
import io.ServerOutput;
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
import java.util.zip.InflaterOutputStream;


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
        channel.register(selector, SelectionKey.OP_READ);
        instances = new Instances();
        service = Executors.newFixedThreadPool(3);
        forkJoinPool = ForkJoinPool.commonPool();

    }

    public void run() throws IOException, NullPointerException {

        try {
            instances.dao = new SQLDragonDAO();
        } catch (RuntimeException e) {
            System.out.printf("Не удалось инициализировать коллекцию. (%s)%n", e.getMessage());
            return;
        }

        while (true) {
            selector.select();
            for (SelectionKey k : selector.selectedKeys()) {
                if (k.isReadable()) {
                    OutPutter outPutter = new ServerOutput();
                    read(k, outPutter);
                }
            }
        }
    }


    private synchronized void commandExecution(String request, SelectionKey k, SocketAddress address, OutPutter outPutter) {
        Runnable requestHandling = () -> {
            try {
                Request req = Json.fromJson(Json.parse(request), Request.class);
                Command command = Command.restoreFromProperties(req.properties, req.user);
                command.execute(instances, outPutter);

            } catch (DatabaseException | IOException ignored) {
                //instances.outPutter.output(e.getMessage() + ". ");
            }

            writeLayer(k, outPutter, instances, address);

        };

        Thread handlingThread = new Thread(requestHandling);
        handlingThread.start();
    }



    private void read(SelectionKey key, OutPutter outPutter) throws IOException {

        RecursiveAction task = new RecursiveAction() {
            @Override
            protected void compute() {
                String request;
                DatagramChannel channel = (DatagramChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(10000);
                SocketAddress address;
                try {
                    address = channel.receive(buffer);
                    if(address != null){
                        buffer.flip();
                        request = StandardCharsets.UTF_16.decode(buffer).toString();
                        commandExecution(request, key, address, outPutter);
                    }
                } catch (IOException ignored) {}
            }
        };

        forkJoinPool.execute(task);
    }

    private void writeLayer(SelectionKey k, OutPutter outPutter, Instances instances, SocketAddress address){

        List<String> list = outPutter.compound();

        try{
                for (String msg : list) {
                    write(k, msg, address);

                    TimeUnit.MILLISECONDS.sleep(10);
                }
                write(k, "END", address);
            }
            catch(NullPointerException | IOException | InterruptedException ignored) {}
    }

    private void write(SelectionKey key, String msg, SocketAddress address) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Runnable task = () -> {
            try {
                channel.send(StandardCharsets.UTF_16.encode(msg), address);
            } catch (IOException ignored) {}
        };
        service.execute(task);
    }

}

