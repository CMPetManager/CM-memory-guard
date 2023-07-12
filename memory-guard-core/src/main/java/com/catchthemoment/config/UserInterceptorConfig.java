package com.catchthemoment.config;

import com.catchthemoment.controller.AlbumInterceptor;
import com.catchthemoment.controller.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UserInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;
    @Autowired
    private AlbumInterceptor albumInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String pathVariable = "/users/confirm-account";
        String urlVariable = "/albums";
        registry.addInterceptor(userLoginInterceptor).addPathPatterns(pathVariable);
        registry.addInterceptor(albumInterceptor).addPathPatterns(urlVariable);
   
}
  
}
