package com.lnho.example.java.reflect.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {
    public static void main(String[] args) {
        CGLibProxy cgLibProxy = new CGLibProxy();
        Human humanProxy = cgLibProxy.getProxy(Human.class);
        humanProxy.eat("rice");
    }

    public  <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
        System.out.println("before");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("after");
        return result;
    }
}