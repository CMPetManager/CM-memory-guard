package com.catchthemoment.controller.advice;

import com.catchthemoment.exception.*;
import com.catchthemoment.model.ApplicationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApplicationError> handle(ServiceProcessingException exception) {
        log.error("*** ServiceProcessingException: {} ***", exception.toString());
        ApplicationError applicationError = new ApplicationError()
                .code(exception.getCode())
                .message(exception.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(applicationError);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> handleAll(Exception exception) {
        log.error("*** Exception: {} ***", exception.toString());
        ApplicationError errorResponse = new ApplicationError()
                .code(ApplicationErrorEnum.DEFAULT_EXCEPTION.getCode())
                .message(ApplicationErrorEnum.DEFAULT_EXCEPTION.getMessage());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

}
