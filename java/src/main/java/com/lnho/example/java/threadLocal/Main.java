package com.lnho.example.java.threadLocal;

/**
 * Created by Administrator on 2016-05-31.
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            RequestThread requestThread = new RequestThread();
            requestThread.start();
        }
    }

    private static class RequestThread extends Thread {
        @Override
        public void run() {
            UserController.getInstance().add();
        }
    }
}
