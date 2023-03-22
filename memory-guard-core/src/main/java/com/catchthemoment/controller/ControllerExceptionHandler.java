package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

//    fixme make return ResponseEntity<Error>
//    fixme put particular exception as args and process it in proper - no hardcode
    @ExceptionHandler
    public Error handleAccessDenied(ServiceProcessingException spe) {
        Error error = new Error();
        error.setMessage("Access denied");
        return error;
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleAll(Exception ex) {
        if(ex.getClass().isAssignableFrom(ServiceProcessingException.class)) {
//            make it nice
        }
        return ResponseEntity.badRequest().build();
    }
}
