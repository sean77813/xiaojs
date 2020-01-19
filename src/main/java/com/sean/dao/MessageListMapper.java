package com.sean.dao;

import com.sean.bean.MessageList;

import java.util.List;

public interface MessageListMapper {
    int deleteByPrimaryKey(String pkId);

    int insert(MessageList record);

    int insertSelective(MessageList record);

    MessageList selectByPrimaryKey(String pkId);

    int updateByPrimaryKeySelective(MessageList record);

    int updateByPrimaryKey(MessageList record);

    List<MessageList> selectList();
}