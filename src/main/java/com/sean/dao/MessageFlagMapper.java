package com.sean.dao;

import com.sean.bean.MessageFlag;

public interface MessageFlagMapper {
    int deleteByPrimaryKey(String pkId);

    int insert(MessageFlag record);

    int insertSelective(MessageFlag record);

    MessageFlag selectByPrimaryKey(String pkId);

    int updateByPrimaryKeySelective(MessageFlag record);

    int updateByPrimaryKey(MessageFlag record);

    MessageFlag selectFlag(String mId,String uId);
}