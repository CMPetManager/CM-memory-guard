package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.catchthemoment.exception.ApplicationErrorEnum.MAIL_INCORRECT;

@Component
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    private static final String URI = "/users/confirm-account";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String currentMethod = request.getMethod();
        if (request.getRequestURI().equalsIgnoreCase(URI)&& HttpMethod.POST.matches(currentMethod)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;

        }
        log.error("*** error occurred with incoming request params..");
        throw new ServiceProcessingException(MAIL_INCORRECT);

    }

}