package com.lnho.example.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatteringReadsEx {
    public static void main(String[] args) {
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel channel = aFile.getChannel();
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        channel.read(bufferArray);
    }
}
