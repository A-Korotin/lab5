package net;

import collection.Describable;
import collection.DragonDAO;
import commands.Command;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import exceptions.SavedToTmpFileException;
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

    public void run() throws IOException {
        int loopCount = 0;
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
                    write(k, instances.outPutter.compound());
                    loopCount ++;
                    if (loopCount % 2 == 0){
                        try {
                            FileManipulator.save(((Describable) instances.dao));
                            instances.outPutter.output("Коллекция была автоматически сохранена");
                        } catch (SavedToTmpFileException e) {
                            instances.outPutter.output(e.getMessage());
                        }
                        catch (RuntimeException e) {
                            instances.outPutter.output("Автоматическое сохранение коллекции завершилось ошибкой (" + e.getMessage() + ")");
                        }

                    }
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

