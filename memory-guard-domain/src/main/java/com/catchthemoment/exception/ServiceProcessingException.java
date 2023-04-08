package com.catchthemoment.exception;

import lombok.Getter;

@Getter
public class ServiceProcessingException extends Exception {

    private int code;
    private String message;


    public ServiceProcessingException(int code, String  message) {
        this.code = code;
        this.message = message;
    }


}
