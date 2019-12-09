package com.sean.auth.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;

public class SendSms {

   // public final static Logger logger = LoggerFactory.getLogger(SendSms.class);


    private static final String REGIONID = "华东1（杭州）";
    private static final String ACCESSKEYID = "LTAI4Ft15QeC3dcHcYdSQmWG";
    private static final String SECRET = "RsDSTg511rDkTztCGB4n6XlsaKXndp";
    private static final String SIGNNAME_ZC = "Sean文件系统";
    private static final String  TEMPLATECODE = "SMS_179280132";

    public static void main(String[] args) {
//        boolean flag = VerificationCode("17521668894");
//        System.out.println(flag);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "17521668894");
        request.putQueryParameter("SignName", SIGNNAME_ZC);
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
//        request.putQueryParameter("");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static boolean VerificationCode(String PhoneNumbers){
        boolean flag = false;
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("SignName", SIGNNAME_ZC);
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        try {
            CommonResponse response = client.getCommonResponse(request);
            String msg = response.getData();
            Map map = (Map) JSON.parse(msg);
            flag = "OK".equalsIgnoreCase((String) map.get("Code"));
            System.out.println(msg);
//            logger.info(msg);
        } catch (ServerException e) {
            e.printStackTrace();
            flag = false;
        } catch (ClientException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
