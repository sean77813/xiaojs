package com.sean.app;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sean.bean.Permission;
import com.sean.bean.Role;
import com.sean.bean.User;
import com.sean.dao.UserMapper;
import com.sean.fastdfs.FastDFSClientUtil;
import com.sean.spring.prop.SeanJson;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Set;

@SpringBootTest
class XiaojsApplicationTests {

    @Autowired
    private FastDFSClientUtil dfsClient;

    @Test
    void contextLoads() {
        String path = "group1/M00/00/00/rBCgn13SSwGAVeL2AARwl-zydAg114.jpg";
        int ts = (int) (System.currentTimeMillis() / 1000);
        String secret_key = "FastDFS1234567890";
//        InputStream input = dfsClient.download("group1",path);
//        System.out.println(input==null);
        try {
           String url =  dfsClient.autoDownloadWithToken("group1",path);
//           String url2 = dfsClient.getToken(path,secret_key,"101.37.77.138:8888/");
            System.out.println(url);
//            System.out.println(url2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private SeanJson sjson;

    @Test
    void test1(){
        String filePath = "http://xiaojs.vip:8888/group1/M00/00/00/rBCgn13p21mAWt0NAAIZ1B1_xTI112_337x190.jpg";

        String regular = "[a-zA-z]+://[^\\s]*8888/";
        filePath = filePath.replaceAll(regular,"");
        String[] paths = filePath.split("/");
        String groupName = null ;
        for (String item : paths) {
            if (item.indexOf("group") != -1) {
                groupName = item;
                break ;
            }
        }
        String path = filePath.substring(filePath.indexOf(groupName) + groupName.length() + 1, filePath.length());
        System.out.println("path:"+path);
        System.out.println("groupName:"+groupName);




        InputStream is = dfsClient.download(groupName,path);
        if(is!=null){
            System.out.println("下载成功！");
        }else{
            System.out.println("下载失败！");
        }
    }

    @Value("classpath:json/property.json")
    private  Resource fileprop;

    @Test
    public void jsonRead() {
        String json = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(fileprop.getInputStream()));
            StringBuffer message=new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                message.append(line);
            }
            String defaultString=message.toString();
            json = defaultString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }

}
