package com.lnho.example.java.thread;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocal {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 1; i < 20; i++) {
            threadPool.execute(new MyThread());
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }

    private static class MyThread extends Thread {
        private String requestId;
        private int rpcId;

        public MyThread() {
            rpcId = 0;
            requestId = String.valueOf(UUID.randomUUID());
        }

        @Override
        public void run() {
            System.out.println("requestId="+requestId+",rpcId="+rpcId);
        }
    }
}