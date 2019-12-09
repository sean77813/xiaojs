package com.sean.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sean.auth.sms.SendSms;
import com.sean.bean.User;
import com.sean.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println(">>>/");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("username", user.getUsername());
        return "view/index";
    }


    @GetMapping("/list")
    public List<User> getUserList(){
        return userService.getList();
    }

    //跳转到登录页面
    @GetMapping("/login")
    public String login() {
        System.out.println(">>loginpage");
        return "login";
    }

    /**
     * 没有权限的回调接口
     * @return
     */
    @RequestMapping("unauthorized")
    public String unauthorized() {
        System.out.println(">>>unauthorized");
        return "unauthorized";
    }

    /**
     * 需要admin角色才能访问
     * @return
     */
    @RequestMapping("/admin")
    @RequiresRoles("/admin")
    public String admin() {
        System.out.println(">>>admin");
        return "admin success";
    }

    /**
     * 需要修改权限才能访问
     * @return
     */
    @RequestMapping("/edit")
    @RequiresPermissions("edit")
    public String edit() {
        System.out.println(">>>edit");
        return "edit success";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        System.out.println(">>>logout");
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject);
        if (subject != null) {
            System.out.println("注销用户..");
            subject.logout();
        }
        return "login";
    }

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletRequest request) {
//        System.out.println(">>>loginUser");
//        System.out.println("username:"+username);
//        System.out.println("password:"+password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        System.out.println(token);
        Subject subject = SecurityUtils.getSubject();
//        System.out.println(subject);
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            System.out.println(user.getUsername()+"登录成功...");
            return "redirect:/kwin";
        } catch (Exception e) {
            System.out.println("登录错误...");
            return "login";
        }
    }

    @PostMapping("/isExistsUserName")
    public Map<String,Object> isExistsUserName(@RequestParam("username")String username){
        System.out.println("=======>isExistsUserName");
        String  uid = userService.isExistsUserName(username);
        System.out.println("uid:"+uid);
        boolean isExists = false;
        Map<String,Object> map = new HashMap<>();
        if ("".equals(uid)){
            isExists = true;
        }
        map.put("result",isExists);
        return map;
    }


    /**
     * 短信验证..
     * @return
     */
    @GetMapping("/verification")
    public String verification(
           // @RequestParam(value = "phonenamber",required = false)String phonenamber,
    //                           HttpServletRequest request
    ){
        System.out.println("->verification");
//        boolean result = SendSms.VerificationCode(phonenamber);
        if(true){
//            request.setAttribute("phonenamber", phonenamber);
            return "page_register";
        }else{
            return "login";
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public Map<String,Object> register(User user){
        System.out.println("getUsername:"+user.getUsername());
        System.out.println("getPassword:"+user.getPassword());
        System.out.println("getActualname:"+user.getActualname());
        boolean result = userService.register(user);
        Map<String,Object> json = new HashMap<>();
        if(result){
            json.put("resultCode",200);
            json.put("msg","注册成功！");
        }else{
            json.put("resultCode",501);
            json.put("msg","注册失败！请联系管理员。");
        }
        return json;
    }

}
