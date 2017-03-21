package com.lnho.example.java.threadLocal;

import java.util.Map;

/**
 * Created by Administrator on 2016-05-31.
 */
public class UserDao {
    private static UserDao instance;
    ThreadLocal<Map> threadLocal = new ThreadLocal();

    public static UserDao getInstance() {
        if (instance == null) {
            return new UserDao();
        }
        return instance;
    }

    public void add() {
        //System.out.println("dao-rpcId" +threadLocal.get().get("requestId"));
    }
}
