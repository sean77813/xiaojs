package com.sean.service;

import com.sean.bean.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User getUserById(String userid);

    int doLogin(String username,String password);

    List<User> getList();

    //注册账号
    boolean register(User user);
}
