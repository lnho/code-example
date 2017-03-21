package com.lnho.example.java.threadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016-05-31.
 */
public class UserController {
    private static UserController instance;
    private static ThreadLocal<Map> mapThreadLocal = new ThreadLocal<>();

    public static UserController getInstance() {
        if (instance == null) {
            return new UserController();
        }
        return instance;
    }

    public void add() {
        Map map = new HashMap<>();
        map.put("requestId", String.valueOf(UUID.randomUUID()));
        map.put("rpcId", 0);
        mapThreadLocal.set(map);
        UserService.getInstance().add();
    }
}
