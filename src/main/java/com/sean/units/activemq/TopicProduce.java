package com.sean.units.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.Topic;

@Service
@Component
public class TopicProduce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;


//    @Scheduled(fixedDelay=10000)
//    public void topicProduce() {
//        jmsMessagingTemplate.convertAndSend(topic,"主题消息："+ SeanTools.getUUID32().toString());
//    }

    public void topicProduce(String message) {
        jmsMessagingTemplate.convertAndSend(topic,message);
    }
}
