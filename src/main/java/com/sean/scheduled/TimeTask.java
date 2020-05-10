package com.sean.scheduled;

import com.sean.service.RestFulService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@EnableScheduling
public class TimeTask {
    private static Logger logger = LogManager.getLogger(TimeTask.class);

    @Autowired
    public SimpMessagingTemplate template;
    @Autowired
    RestFulService restFulService;
    @Scheduled(cron = "0/20 * * * * ?")
    public void test(){
        logger.info("*********   定时任务执行   **************");
        String message=restFulService.getMessage();
        template.convertAndSend("/topic/getResponse", "我是服务器主动推送的定时消息"+message+"|||"+new Date().toLocaleString());
        logger.info("/n 定时任务完成.......");
    }
}
