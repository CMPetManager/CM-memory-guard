package com.catchthemoment.config;

import com.catchthemoment.controller.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UserInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String pathVariable = "/users/confirm-account";
        String pathPattern = "/Users/login";
        registry.addInterceptor(userLoginInterceptor).addPathPatterns(pathVariable,pathPattern);
    }
}
