package com.sean.dao;

import com.sean.bean.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(String rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}