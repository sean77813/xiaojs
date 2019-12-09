package com.sean.dao;

import com.sean.bean.UserAndRole;

public interface UserAndRoleMapper {
    int insert(UserAndRole record);

    int insertSelective(UserAndRole record);
}