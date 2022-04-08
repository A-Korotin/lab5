package net;

import collection.Describable;
import collection.DragonDAO;
import commands.Command;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import exceptions.SavedToTmpFileException;
import io.Autosaver;
import io.FileManipulator;
import io.OutPutter;
import json.Json;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    public void run() throws IOException, NullPointerException{

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
                    String input = read(k);
                    Command command = Command.restoreFromProperties(Json.fromJson(Json.parse(input),CommandProperties.class));
                    command.execute(instances);

                    List<String> list = instances.outPutter.compound();

                    Server.writeLayer(k, list, instances);

                    Autosaver.autosave(instances);

                    list.clear();

                }
            }
        }
    }

    private static String read(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();
        client.buffer.clear();
        client.clientAddress = channel.receive(client.buffer);
        if(client.clientAddress != null) {
            client.buffer.flip();
            return StandardCharsets.UTF_16.decode(client.buffer).toString();
        }
        return null;
    }

    private static void writeLayer(SelectionKey k, List<String> list, Instances instances){
        for (String msg : list){
            try{
                write(k, msg);
            }
            catch(NullPointerException | IOException e){
                instances.outPutter.output(e.getMessage());
            }
        }
    }

    private static void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientClass client = (ClientClass) key.attachment();
        int bytesSent = channel.send(StandardCharsets.UTF_16.encode(msg), client.clientAddress);

    }

    static class ClientClass {
        public SocketAddress clientAddress;
        public ByteBuffer buffer = ByteBuffer.allocate(1024);
    }
}

