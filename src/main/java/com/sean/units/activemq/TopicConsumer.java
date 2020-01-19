package com.sean.units.activemq;

import com.sean.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class TopicConsumer {

    @Autowired
    private MessageService messageService;

//    @JmsListener(destination="${mytopic}")
    public void receive(TextMessage textMessage) {
        try {
            System.out.println("收到订阅者的消息："+textMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
