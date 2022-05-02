package net;

import exceptions.ResponseTimeoutException;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public final class Client {
    private final Selector selector;

    private static final int MAX_PACKET_LENGTH = 10_000;

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
        // never returned, read throws Exception
        return null;
    }

    private void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.write(StandardCharsets.UTF_16.encode(msg));
    }

    private String read(SelectionKey key, long timeout) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_LENGTH * 2); // UTF 16 encoding
        buffer.clear();
        long startTime = System.currentTimeMillis();
        List<String> received = new ArrayList<>(10);

        while (System.currentTimeMillis() - startTime < timeout * 1000) {

            if (channel.read(buffer) <= 0) continue;

            buffer.flip();
            String msg = StandardCharsets.UTF_16.decode(buffer).toString();

            if (msg.equals("END"))
                return attachInput(received);

            received.add(msg);
            buffer.clear();
        }

        throw new ResponseTimeoutException();
    }

    private String attachInput(List<String> input) {
        StringBuilder b = new StringBuilder();
        input.forEach(b::append);
        return b.toString();
    }

    public static String toMD5(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}