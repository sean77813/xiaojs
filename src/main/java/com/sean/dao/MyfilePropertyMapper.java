package com.sean.dao;

import com.sean.bean.MyfileProperty;

public interface MyfilePropertyMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyfileProperty record);

    int insertSelective(MyfileProperty record);

    String selectIdByFid(String fid);

    MyfileProperty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MyfileProperty record);

    int updateByPrimaryKey(MyfileProperty record);

    String selectLabelsByFid(String fid);

    MyfileProperty selectByFid(String fid);
}