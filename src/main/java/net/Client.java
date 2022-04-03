package net;
import commands.Command;
import commands.CommandCreator;
import commands.ExecuteScript;
import commands.dependencies.Instances;
import io.ConsoleOutput;
import io.ConsoleReader;
import io.FileReader;
import io.request.ConsoleRequester;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class Client {
    private DatagramChannel channel;
    private SocketAddress address;
    private Selector selector;
    private ByteBuffer buffer;
    private Instances instances;

    public Client(String host, int port) throws IOException {
        address = new InetSocketAddress(host, port);
        buffer = ByteBuffer.allocate(1024);
        selector = Selector.open();
        channel = DatagramChannel.open();
        channel.configureBlocking(false);

        registerWrite();

        instances = new Instances();
        instances.outPutter = new ConsoleOutput();
        instances.fileReader = new FileReader();
        instances.consoleReader = new ConsoleReader();
        instances.consoleRequester = new ConsoleRequester();
    }

    private void registerRead() throws ClosedChannelException {
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void registerWrite() throws ClosedChannelException {
        channel.register(selector, SelectionKey.OP_WRITE);
    }


    private List<Command> getUserInput() {
        List<Command> list = CommandCreator.getCommands(instances.consoleReader);
        Command command = list.get(0);
        return command instanceof ExecuteScript ?
                ((ExecuteScript) command).getNestedCommands(instances) : list;
    }

    private void loopBody() throws IOException {
        selector.select();

        while(Time);
    }


    public void sendMsg(String msg) throws IOException {
        channel.send(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_16)), address);
    }

    public String receiveMsg() throws IOException {
        buffer.clear();
        address = channel.receive(buffer);
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_16);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4444);

        client.sendMsg("Hello");
        //client.buffer.flip();
        System.out.println("Ответ: " + client.receiveMsg());
    }
}
