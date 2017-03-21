package com.lnho.example.java.thread;

/**
 * Created by Administrator on 2016-05-26.
 */
public class Thread04 {
    private Thread04 instance;
    private int a;

    public void inc() {
//        for (int i = 0; i < 100; i++) {
        a++;
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        }
    }

    public void get() {
//        for (int i = 0; i < 100; i++) {
            System.out.println(a);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void main(String[] args) {
        Thread04 thread04=new Thread04();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                thread04.inc();
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread04.get();
    }
}