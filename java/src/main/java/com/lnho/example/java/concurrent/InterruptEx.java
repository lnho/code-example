package com.lnho.example.java.concurrent;

public class InterruptEx {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for(;;){
                System.out.println("1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2");
        thread.interrupt();
    }
}
