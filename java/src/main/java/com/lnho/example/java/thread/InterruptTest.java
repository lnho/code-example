package com.lnho.example.java.thread;

/**
 * Created by Administrator on 2016-05-27.
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread("MyThread");
        t.start();
        Thread.sleep(100);// 睡眠100毫秒
        t.interrupt();// 中断t线程
    }
}

class MyThread extends Thread {
    int i = 0;

    public MyThread(String name) {
        super(name);
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!isInterrupted()) {// 当前线程没有被中断，则执行

            System.out.println(getName() + getId() + "执行了" + ++i + "次");
        }
    }
}