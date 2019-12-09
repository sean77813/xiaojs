package com.sean.service;

import com.sean.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User getUserById(String userid);

    int doLogin(String username,String password);

    List<User> getList();

    /**
     *  检查用户名
     * @param username
     * @return
     */
    String isExistsUserName(String username);

    //注册账号
    boolean register(User user);

    /**
     * 更新头像
     * @param avatarfile
     * @return
     */
    String updateAvatar(MultipartFile avatarfile);

    /**
     *  获取用户头像
     * @param uid //参数为空时取本人头像
     * @return
     */
    String getUserAvatar(String uid);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    boolean editUserInfo(User user);
}
