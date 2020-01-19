package com.sean.units.activemq;

import com.sean.utils.SeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import javax.jms.Queue;

@Component
public class QueueProduce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue,"***"+ SeanTools.getUUID32());
    }

//     @Scheduled(fixedDelay=2000)
     public void produceMsgScheduled() {
        jmsMessagingTemplate.convertAndSend(queue,"***"+SeanTools.getUUID32());
        System.out.println("间隔发送");
    }
}
