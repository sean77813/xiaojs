package com.sean.dao;

import com.sean.bean.PublicPic;

import java.util.List;

public interface PublicPicMapper {
    int deleteByPrimaryKey(String id);

    int insert(PublicPic record);

    int insertSelective(PublicPic record);

    PublicPic selectByPrimaryKey(String id);

    String selectIdByFid(String fid);

    int updateByPrimaryKeySelective(PublicPic record);

    int updateByPrimaryKey(PublicPic record);

    int isExistPicByFid(String fid);

    List<String> selectFidAll();

    PublicPic selectPpicByFid(String fid);
}