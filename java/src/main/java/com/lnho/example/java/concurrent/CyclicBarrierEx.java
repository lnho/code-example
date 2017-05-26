package com.lnho.example.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierEx {

    public static void main(String[] args) {
        System.out.println("main"+Thread.currentThread().getName());
        final int count = 5;
        final CyclicBarrier barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                System.out.println("drink beer!"+Thread.currentThread().getName());
            }
        });

        // they do not have to start at the same time...
        for (int i = 0; i < count; i++) {
            new Thread(new Worker(i, barrier)).start();
        }
        System.out.println("main end");
    }

}

class Worker implements Runnable {
    final int id;
    final CyclicBarrier barrier;

    public Worker(final int id, final CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.id + "starts to run !"+Thread.currentThread().getName());
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println(this.id + "arrived !");
            this.barrier.await();
            System.out.println("haha");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}