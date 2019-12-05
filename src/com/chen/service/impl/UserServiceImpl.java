package com.chen.service.impl;

import com.chen.dao.UserDao;
import com.chen.dao.impl.UserDaoImpl;
import com.chen.service.UserService;

/**
 * @program: Online
 * @description:
 * @author: chenliang
 * @date: 2019-12-01 21:30
 **/

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean login(String username, String password) {
        return userDao.login(username, password);
    }
}
