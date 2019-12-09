package com.sean.dao;

import com.sean.bean.OperationLog;

import java.util.List;

public interface OperationLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKey(OperationLog record);

    List<OperationLog> selectLogByLike(String fid,int type);

    List<OperationLog> selectLog(String fid,int type);
}