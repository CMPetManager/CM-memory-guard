package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

import static com.catchthemoment.exception.ApplicationErrorEnum.USER_PARAMS_INCORRECT;

/**
 * Filter incoming request from ui , check it on
 * valid params. The main purpose of it to filter
 * requests before they go to service layer
 */
@Component
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    private static final String URI_CONFIRM = "/users/confirm-account";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String currentMethod = request.getMethod();
        final Enumeration<String> params = request.getAttributeNames();
        if (request.getRequestURI().equalsIgnoreCase(URI_CONFIRM)
                && HttpMethod.POST.matches(currentMethod) &&
                params.hasMoreElements()) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        log.error("*** error occurred with incoming request params..");
        throw new ServiceProcessingException(USER_PARAMS_INCORRECT);
    }

}

