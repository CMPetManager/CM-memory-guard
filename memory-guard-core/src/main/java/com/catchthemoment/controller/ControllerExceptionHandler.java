package com.catchthemoment.controller;

import com.catchthemoment.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponce> handle(ServiceProcessingException exception) {
        log.error("ServiceProcessingException: {}", exception.toString());
        ErrorResponce errorResponse = new ErrorResponce(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponce> handleAll(Exception exception) {
        log.error("Exception: {}", exception.toString());
        ErrorResponce errorResponse = new ErrorResponce(
                ApplicationErrorEnum.DEFAULT_EXCEPTION.getCode(),
                ApplicationErrorEnum.DEFAULT_EXCEPTION.getMessage());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<VerifyAccountError> handle(VerifyAccountException ex) {
        log.error("ConfirmationMailService failed:{}", ex.toString());
        VerifyAccountError accountError = new VerifyAccountError(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(accountError, UNPROCESSABLE_ENTITY);
    }
}
