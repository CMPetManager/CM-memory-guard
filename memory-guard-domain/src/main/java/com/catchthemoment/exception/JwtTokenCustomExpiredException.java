package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenCustomExpiredException extends RuntimeException{

    private final int code;
    private String message;

    public JwtTokenCustomExpiredException(ApplicationErrorEnum applicationErrorEnum){
        this.code = applicationErrorEnum.getCode();
        this.message = applicationErrorEnum.getMessage();
    }

}
