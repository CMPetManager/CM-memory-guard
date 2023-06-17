package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceProcessingException extends Exception {

    private int code;
    private String message;

    public ServiceProcessingException(ApplicationErrorEnum applicationErrorEnum) {
        this.message = getMessage();
        this.code= getCode();
    }

}
