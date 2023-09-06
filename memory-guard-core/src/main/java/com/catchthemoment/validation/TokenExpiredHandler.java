package com.catchthemoment.validation;

import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TokenExpiredHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceProcessingException> handle(ServiceProcessingException ex) {
        return ResponseEntity.internalServerError().body(ex(ApplicationErrorEnum.TOKEN_TIME_EXPIRED));
    }

    private ServiceProcessingException ex(ApplicationErrorEnum applicationErrorEnum) {
        return new ServiceProcessingException(ApplicationErrorEnum.TOKEN_TIME_EXPIRED);
    }
}
