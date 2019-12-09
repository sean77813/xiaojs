package com.sean.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    LoginHandlerInterceptor loginHandlerInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/html/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的请求
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/users/login","/html/login.html","/jsp/error.jsp").excludePathPatterns("/static/**","/js/**");
    }
}
