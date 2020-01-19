package com.sean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.sean.dao")
@SpringBootApplication
public class XiaojsApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaojsApplication.class, args);
    }

    @Value("${spring.server.MaxFileSize}")
    private String MaxFileSize;
    @Value("${spring.server.MaxRequestSize}")
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.parse(MaxFileSize));
        // 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse(MaxRequestSize));
        return factory.createMultipartConfig();
    }

}
