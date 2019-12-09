package com.sean.service.impl;

import com.sean.bean.User;
import com.sean.bean.UserAndRole;
import com.sean.dao.UserAndRoleMapper;
import com.sean.dao.UserMapper;
import com.sean.service.UserService;
import com.sean.utils.SeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAndRoleMapper urMapper;

    @Override
    public User findByUsername(String username) {
        if(username==null || "".equals(username))
            return null;
        User user = userMapper.selectByUsername(username);
        return user;
    }

    @Override
    public User getUserById(String userid) {
        return null;
    }

    @Override
    public int doLogin(String username, String password) {
        int flag = 0;
//        User user = userMapper.getUser(username);
//        if(user == null)
//            return flag;
//        String pwdmd5 = user.getPassword();
//        String pagemd5 = MD5.MD5(password);
//        if(pwdmd5.equals(pagemd5)){
//            flag = 1;
//        }else{
//            flag = -1;
//        }
        return flag;
    }

    @Override
    public List<User> getList() {
        List<User> list = new ArrayList<>();
        list = userMapper.getList();
        return list;
    }

    @Override
    public boolean register(User user) {
        System.out.println("姓名:"+user.getActualname());
        int flag = 0;
        try {
            user.setUid(SeanTools.getUUID32());
            flag = userMapper.insertSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(flag>0){
            urMapper.insert(new UserAndRole(user.getUid(),"2"));
            return true;
        }
        return false;
    }
}
