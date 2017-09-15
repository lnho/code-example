package com.lnho.example.java.jvm;

/**
 * VM Args:-Xss2M
 */
public class JavaVMStackOOM {
    private int n = 0;

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(this::dontStop);
            thread.start();
            System.out.println(++n);
        }
    }
}
