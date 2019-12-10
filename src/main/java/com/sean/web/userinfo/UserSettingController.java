package com.sean.web.userinfo;

import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MyFile;
import com.sean.bean.User;
import com.sean.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户设置
 */
@RestController
@RequestMapping("/usetting")
public class UserSettingController {

    @Autowired
    private UserService userService;

    /**
     * 更新头像
     * @param avatar
     * @return
     */
    @PostMapping("/setAvatar")
    public Map<String,Object> setAvatar(@RequestParam("avatar_file") MultipartFile avatar){
        System.out.println("=======>setAvatar");
        System.out.println("avatar:"+avatar.getOriginalFilename());
        String avatar_img = userService.updateAvatar(avatar);
        Map<String,Object> map = new HashMap<>();
        System.out.println("avatar_img:"+avatar_img);
        map.put("result",avatar_img);
        return map;
    }

    @PostMapping("/getUserAvatar")
    public Map<String,Object> getUserAvatar(@RequestParam(value = "uid",required = false) String uid){
        System.out.println("=======>getUserAvatar");
        System.out.println("uid:"+uid);
        String avatar_img = userService.getUserAvatar(uid);
        Map<String,Object> map = new HashMap<>();
        System.out.println("avatar_img:"+avatar_img);
        map.put("avatar_img",avatar_img);
        return map;
    }



    /**
     *  获取用户基础信息
     * @param uid
     * @return
     */
    @PostMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestParam(value = "uid",required = false)String uid){
        System.out.println("=======>getUserInfo");
        System.out.println("uid："+uid);
       User user = userService.getUserById(uid);
        Map<String,Object> map = new HashMap<>();
        System.out.println("user:"+user.toString());
        map.put("user",user);
        return map;
    }

    /**
     * 修改用户基础信息
     * @param user
     * @return
     */
    @PostMapping("/edituserinfo")
    public Map<String,Object> editUserInfo(User user){
        System.out.println("=======>edituserinfo");
        System.out.println(user.toString());
        boolean isUpdate = userService.editUserInfo(user);
        Map<String,Object> map = new HashMap<>();
        System.out.println("isUpdate:"+isUpdate);
        map.put("result",isUpdate);
        return map;
    }

}
