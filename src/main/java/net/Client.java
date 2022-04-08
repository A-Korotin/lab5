package net;

import exceptions.ResponseTimeoutException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;

public final class Client {
    private final Selector selector;

    public Client(String host, int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(host, port);
        DatagramChannel channel = DatagramChannel.open().connect(address);
        selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_WRITE, address);
    }

    public String sendAndReceiveResponse(String message, long timeout) throws IOException {
        selector.select();
        for (SelectionKey key: selector.selectedKeys()) {
            if(key.isWritable()) {
                write(key, message);
                return read(key, timeout);
            }
        }
        return null;
    }

    private void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.write(StandardCharsets.UTF_16.encode(msg));
    }

    private String read(SelectionKey key, long timeout) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.clear();
        long startTime = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();

        while (System.currentTimeMillis() - startTime < timeout * 1000) {

            if (channel.read(buffer) <= 0) continue;

            buffer.flip();
            String received = StandardCharsets.UTF_16.decode(buffer).toString();
            if (received.equals("END"))
                return builder.toString();
            builder.append(received).append(System.lineSeparator());
        }

        throw new ResponseTimeoutException();
    }
}