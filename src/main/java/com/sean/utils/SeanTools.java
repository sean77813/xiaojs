package com.sean.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.EnumUtils;

import java.util.UUID;

public class SeanTools {

    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /**
     * 暴力解析:Alibaba fastjson
     * @param test
     * @return
     */
    public final static boolean isJSONValid(String test) {
        try {
            JSONObject.parseObject(test);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
//        System.out.println("filetype:"+Constants.imgFile.JPG.getMessage());
//        boolean b = EnumUtils.isValidEnum(Constants.imgFile.class, "jpg");
//        System.out.println("b:"+b);
//        System.out.println("b:"+Constants.imgFile.valueOf("JPG").getCode());
//        Constants.docFile[] s =  Constants.docFile.values();
//        for(Constants.docFile e:s){
//            System.out.println(e.getCode());
//        }
//        String url1 = "http://101.37.77.138:8888/group1/M00/00/00/rBCgn13TgxWAJ8HWAAwSJo9DHRY860.jpg";
//        String url2 = "www.sina.com.cn/video/12dsasd12eq.mp4";
//        String re = "[a-zA-z]+://[^\\s]*8888/";
//        System.out.println(url1.replaceAll(re,""));
//       System.out.println(url1.substring(url1.indexOf("/"),url1.length()));
    }
}
