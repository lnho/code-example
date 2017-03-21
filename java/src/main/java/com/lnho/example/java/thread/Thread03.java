package com.lnho.example.java.thread;

/**
 * Created by Administrator on 2016-05-26.
 */
public class Thread03 {
    private static volatile boolean ready;
    private static volatile int number;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            ready = false;
            number = 0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new ReaderThread().start();
            ready = true;
            number = 42;

        }
    }

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
