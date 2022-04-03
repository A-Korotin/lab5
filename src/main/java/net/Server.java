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
            //server.configureBlocking(false);
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
        return StandardCharsets.UTF_16.decode(buffer).toString();
    }

    public void send(String message, SocketAddress address) throws IOException {
        buffer.clear();
        buffer = StandardCharsets.UTF_16.encode(message);
        server.send(buffer, address);
    }


    public static void main(String[] args) throws IOException {
        Instances instances = new Instances();
        instances.outPutter = new ConsoleOutput();
        Server server = new Server(instances);
        SocketAddress clientAddress = server.receive();
        server.buffer.flip();
        String message = server.readMessage();
        server.buffer.flip();
        instances.outPutter.output("Client at " + clientAddress + " sent: " + message);

        server.send("хуй))", clientAddress);
    }

}

