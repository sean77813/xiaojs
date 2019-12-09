package com.sean.web;

import com.sean.bean.User;
import com.sean.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统中转页面
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println(">>>/");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("username", user.getUsername());
        return "view/index";
    }

    @GetMapping("/kwin")
    public String testIndex(HttpServletRequest request){
        System.out.println(">>>kwin");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("username", user.getUsername());
        return "view/index";
    }
}
