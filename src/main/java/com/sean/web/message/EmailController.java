package com.sean.web.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MessageList;
import com.sean.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mail")
public class EmailController {

    private static final Logger logger = LogManager.getLogger(EmailController.class);

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @PostMapping("/list")
    public String getEmailList(){
        System.out.println("-----------getEmailList------------>");
        String json = messageService.getEmailList();
        System.out.println("json=>"+json);
        return json;
    }

    @GetMapping("/getContent")
    public String getEmailContent(@RequestParam("pkId")String pkId, HttpServletRequest request){
        System.out.println("-----------getEmailContent------------>");
        MessageList message = messageService.getEmileContent(pkId);
        request.setAttribute("data",message);
        System.out.println(" message=>"+message.toString());
        return "mail/message_content";
    }

    @ResponseBody
    @PostMapping("/addStar")
    public String getEmailContent(@RequestParam("mId")String mId, @RequestParam("uId")String uId, @RequestParam("star")int star){
        System.out.println("-----------addStar------------>");
        JSONObject result = new JSONObject();
        boolean flag = messageService.addStar(mId,uId,star);
        if(flag){
            logger.info("[标星邮件]-[用户:'"+uId+"']-[邮件:'"+mId+"']-[状态:'"+star+"']");
        }else{
            logger.error("[标星邮件]-[用户:'"+uId+"']-[邮件:'"+mId+"']-[状态:'"+star+"']");
        }
        result.put("result",flag);
        return result.toJSONString();
    }
}
