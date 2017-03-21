package com.lnho.example.java.thread;

/**
 * Created by Administrator on 2016-05-26.
 */
public class Thread02 {
    public static volatile int count = 0;
    public static final Object lock = new Object();

    public static void inc() {

        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
//        synchronized (lock) {
        count++;
//            System.out.println(count);
//        }
    }

    public static void main(String[] args) {

        //同时启动1000个线程，去进行i++计算，看看实际结果

        for (int i = 0; i < 1000; i++) {
            new Thread(Thread02::inc).start();
        }
//        try {
//            Thread.sleep(5000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Thread02.count);
    }
}
