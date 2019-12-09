package com.sean.spring.prop;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Component
public class SeanJson {
    @Value("classpath:json/property.json")
    public Resource fileprop;

    public String jsonRead() {
        String json = "";
        try {
            String areaData =  IOUtils.toString(fileprop.getInputStream(), Charset.forName("UTF-8"));
            json = areaData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
