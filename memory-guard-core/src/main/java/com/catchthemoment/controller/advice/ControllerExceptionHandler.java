package com.catchthemoment.controller.advice;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ApplicationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.catchthemoment.exception.ApplicationErrorEnum.DEFAULT_EXCEPTION;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApplicationError> handle(ServiceProcessingException exception) {
        log.error("*** ServiceProcessingException: {} ***", exception.toString());
        ApplicationError applicationError = new ApplicationError()
                .code(exception.getCode())
                .message(exception.getMessage());
        return ResponseEntity.internalServerError().body(applicationError);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> handleAll(Exception exception) {
        log.error("*** Exception: {} ***", exception.toString());
        ApplicationError applicationError = new ApplicationError()
                .code(DEFAULT_EXCEPTION.getCode())
                .message(DEFAULT_EXCEPTION.getMessage() + " : " + exception.getMessage());
        return ResponseEntity.internalServerError().body(applicationError);
    }

    //todo handle required properties abscense exception
}
