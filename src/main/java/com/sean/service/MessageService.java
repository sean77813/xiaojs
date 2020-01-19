package com.sean.service;

import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MessageList;

import java.util.Date;

/**
 * 消息服务
 */
public interface MessageService {

    /**
     * 发送消息
     * @param fromUser
     * @param toUser
     * @param time
     * @param type
     * @param title
     * @param content
     * @param parameters
     */
    void sendMessage(String fromUser, String toUser, Date time, String type, String title, String content, String parameters);

    String getEmailList();

    boolean addStar(String mId, String uId, int star);

    MessageList getEmileContent(String pkId);
}
