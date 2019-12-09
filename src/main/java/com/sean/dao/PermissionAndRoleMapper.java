package com.sean.dao;

import com.sean.bean.PermissionAndRole;

public interface PermissionAndRoleMapper {
    int insert(PermissionAndRole record);

    int insertSelective(PermissionAndRole record);
}