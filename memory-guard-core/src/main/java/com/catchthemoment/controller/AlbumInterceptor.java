package com.catchthemoment.controller;

import com.catchthemoment.auth.JwtUtils;
import com.catchthemoment.exception.ServiceProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

import static com.catchthemoment.exception.ApplicationErrorEnum.ALBUM_ERROR_INPUT;

@Component
@Slf4j
public class AlbumInterceptor implements HandlerInterceptor {
    private static final String ALBUM_URI = "/albums";
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestMethod = request.getMethod();
        final Enumeration<String> attList = request.getAttributeNames();
        final String authorizationHeaderValue = request.getHeader("Authorization");
        final String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        if (request.getRequestURI().equalsIgnoreCase(ALBUM_URI) && HttpMethod.POST.matches(requestMethod) &&
                attList.hasMoreElements()&& jwtUtils.isJwtExpiredTimeAt(token)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        log.error("params should not be empty or invalid");
        throw new ServiceProcessingException(ALBUM_ERROR_INPUT);
    }
}
