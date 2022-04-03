package net;

import commands.dependencies.Instances;
import io.ConsoleOutput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    InetSocketAddress address;
    DatagramChannel server;
    Instances instances;
    ByteBuffer buffer;
    public Server(Instances instances) {
        this.address = new InetSocketAddress("localhost",4444);
        try {
            this.server = DatagramChannel.open().bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.instances = instances;
        this.buffer = ByteBuffer.allocate(1024);

        instances.outPutter.output("server started at " + address);
    }
    public SocketAddress receive(){
        SocketAddress clientAddress = null;
        try {
            clientAddress = server.receive(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientAddress;
    }

    public String readMessage(){
        buffer.flip();
        String message;
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        message = new String(bytes);
        return message;
    }

    public void send(String message, SocketAddress address) throws IOException {
        buffer.clear();
        server.send(ByteBuffer.wrap(message.getBytes()), address);
    }


    public static void main(String[] args) {
        Instances instances = new Instances();
        instances.outPutter = new ConsoleOutput();
        Server server = new Server(instances);
        SocketAddress clientAddress = server.receive();
        String message = server.readMessage();
        instances.outPutter.output("Client at " + clientAddress + " sent: " + message);
    }

}

