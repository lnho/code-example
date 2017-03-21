package com.lnho.example.java.thread;

import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {
    public static void main(String[] args) {
        Callable<Integer> callable = () -> new Random().nextInt(100);
        FutureTask<Integer> future = new FutureTask<>(callable);
        new Thread(future).start();
        try {
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Integer> future2 = threadPool.submit(() -> new Random().nextInt(100));
        try {
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}