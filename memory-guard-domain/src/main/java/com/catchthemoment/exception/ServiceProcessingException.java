package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceProcessingException extends Exception {

    private final int code;
    private final String message;

    public ServiceProcessingException(ApplicationErrorEnum applicationErrorEnum) {
        this.code= applicationErrorEnum.getCode();
        this.message= applicationErrorEnum.getMessage();
    }
}
