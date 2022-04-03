package net;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;

public final class Client {
    private DatagramChannel client;
    private InetSocketAddress address;
    ByteBuffer buffer;

    public Client(String host, int port) {
        try {
            client = DatagramChannel.open().bind(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        address = new InetSocketAddress(host, port);
        buffer = ByteBuffer.allocate(1024);
    }

    public void sendMsg(String msg) throws IOException {
        client.send(ByteBuffer.wrap(msg.getBytes()), address);
    }

    public String receiveMsg() {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes);
    }

    public static void main(String[] args) {

    }
}
