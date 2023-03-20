package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ServiceProcessingException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error handleAccessDenied() {
        Error error = new Error();
        error.setMessage("Access denied");
        return error;
    }
}
