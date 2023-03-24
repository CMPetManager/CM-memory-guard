package com.catchthemoment.controller;

import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ServiceProcessingException exception) {
        log.error("ServiceProcessingException: {}", exception.toString());
        ErrorResponse errorResponse = new ErrorResponse().code(exception.getCode()).message(exception.getMessage());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAll(Exception exception) {
        log.error("Exception: {}", exception.toString());
            ErrorResponse errorResponse = new ErrorResponse()
                    .code(ApplicationErrorEnum.DEFAULT_EXCEPTION.getCode())
                    .message(ApplicationErrorEnum.DEFAULT_EXCEPTION.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}
