package com.sean.units.activemq;

import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class QueueConsumer {

//    @JmsListener(destination="${myqueue}")
    public void recive(TextMessage textMessage) throws Exception {
        System.out.println("消费者接到的消息"+textMessage.getText());
    }
}
