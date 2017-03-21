package com.lnho.example.java.threadLocal;

/**
 * Created by Administrator on 2016-05-31.
 */
public class UserService {
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            return new UserService();
        }
        return instance;
    }

    public void add() {
        UserDao.getInstance().add();
    }

    private UserDao userDao;
}
