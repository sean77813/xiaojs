package com.sean.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.sean.bean.MessageFlag;
import com.sean.bean.MessageList;
import com.sean.dao.MessageFlagMapper;
import com.sean.dao.MessageListMapper;
import com.sean.service.MessageService;
import com.sean.units.activemq.TopicProduce;
import com.sean.utils.SeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    private MessageListMapper messageMapper;
    @Autowired
    private MessageFlagMapper messageFlagMapper;
    @Autowired
    private TopicProduce topicProduce;


    @Transactional
    @Override
    public void sendMessage(String fromUser, String toUser, Date time, String type, String title, String content, String parameters) {
        try {
            MessageList message = new MessageList();
            message.setPkId(SeanTools.getUUID32());
            message.setFromUser(fromUser);
            message.setToUser(toUser);
            message.setTime(time);
            message.setType(type);
            message.setTitle(title);
            message.setContent(content);
            message.setParameters(parameters);
            messageMapper.insertSelective(message);
            topicProduce.topicProduce(content);
            logger.info("[发布公告]-[fromUser:"+fromUser+"]-[toUser:"+toUser+"]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEmailList() {
        List<MessageList> list = messageMapper.selectList();
        JSONArray array = new JSONArray();
        array.addAll(list);
        return array.toJSONString();
    }

    @Override
    public boolean addStar(String mId, String uId, int star) {
        MessageFlag flag = messageFlagMapper.selectFlag(mId, uId);
        int result = 0;
        if( null == flag ){
            result = messageFlagMapper.insertSelective(new MessageFlag(SeanTools.getUUID32(), mId, uId, null, star));
        }else{
            flag.setStar(star);
            result = messageFlagMapper.updateByPrimaryKeySelective(flag);
        }
        return (result>0);
    }

    @Override
    public MessageList getEmileContent(String pkId) {
        MessageList message = messageMapper.selectByPrimaryKey(pkId);
        return message;
    }
}
