package com.lnho.example.java.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Subject {
    void request();
}

class RealSubject implements Subject {
    public RealSubject() {
    }

    @Override
    public void request() {
        System.out.println("real subject");
    }
}

class ProxySubject implements InvocationHandler {
    private Subject obj;

    public ProxySubject() {
    }

    public ProxySubject(Subject obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("预处理工作");
        method.invoke(obj, args);
        System.out.println("后续工作");
        return null;
    }
}

public class JDKProxy {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Class<?> clazz = realSubject.getClass();
        InvocationHandler handler = new ProxySubject(realSubject);
        Subject subject = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), clazz.getInterfaces(), handler);
        subject.request();
    }
}