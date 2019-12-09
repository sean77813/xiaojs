package com.sean.dao;

import com.sean.bean.User;

import java.util.List;

public interface UserMapper{
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    String selectUserNameByUid(String uid);

    User selectByPrimaryKey(String uid);

    User selectByUsername(String username);

    String selectUidByUsername(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getList();
}