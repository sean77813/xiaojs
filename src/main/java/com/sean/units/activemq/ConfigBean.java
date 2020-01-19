package com.sean.units.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@EnableJms
@EnableScheduling
public class ConfigBean {

    @Value("${mytopic}")
    private String mytopic;

    @Bean
    public Topic topic() {
    return new ActiveMQTopic(mytopic);
    }

}
