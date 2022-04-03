package net;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;

public final class Client {
    private DatagramChannel client;
    private SocketAddress address;
    ByteBuffer buffer;

    public Client(String host, int port) {
        address = new InetSocketAddress(host, port);
        buffer = ByteBuffer.allocate(1024);
        try {
            client = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) throws IOException {
        client.send(ByteBuffer.wrap(msg.getBytes()), address);
    }

    public String receiveMsg() throws IOException {
        buffer.clear();
        address = client.receive(buffer);
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4444);

        client.sendMsg("Hello");
        client.buffer.flip();
        System.out.println("Ответ: " + client.receiveMsg());
    }
}
