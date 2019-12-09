package com.sean.dao;

import com.sean.bean.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(String pid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String pid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}