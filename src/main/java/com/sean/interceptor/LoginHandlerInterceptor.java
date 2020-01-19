package com.sean.interceptor;

import com.sean.auth.CodeMsg;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //拦截逻辑
        Object user = request.getSession().getAttribute(CodeMsg.SESSION_USER_NAME);
        if (user == null) {
            System.out.println("没有权限请先登陆");
            //未登陆，返回登陆界面
            request.setAttribute("msg", "没有权限请先登陆");
//            request.getRequestDispatcher("/html/login.html").forward(request, response);
            response.sendRedirect("/html/login.html");
            return false;
        } else {
            //已登陆，放行请求
            return true;
        }
    }

}
