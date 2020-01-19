package com.sean.app;

import com.sean.fastdfs.FastDFSClientUtil;
import com.sean.spring.prop.SeanJson;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import javax.jms.Destination;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//@SpringBootTest
class XiaojsApplicationTests {

    @Autowired
    private FastDFSClientUtil dfsClient;

    @Test
    void contextLoads() {

    }


}
