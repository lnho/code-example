package com.lnho.example.bigdata.snappy;

import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class SnappyJavaTest {
    public static void main(String[] args) throws IOException {
        basicExample();
        streamBasedExample();
    }

    private static void basicExample() throws IOException {
        String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
                + "Snappy, a fast compresser/decompresser.";
        byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
        byte[] uncompressed = Snappy.uncompress(compressed);

        String result = new String(uncompressed, "UTF-8");
        PrintStream ps=new PrintStream(new File("d:/1.txt"));
        ps.println(result);
        ps.close();
    }
    private static void streamBasedExample() throws IOException {

    }
}
