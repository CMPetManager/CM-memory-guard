package com.catchthemoment.exception;

public class ServiceProcessingException extends RuntimeException {

    private int code;
    private String message;

    public ServiceProcessingException() {
        super();
    }

    public ServiceProcessingException(String message) {
        super(message);
    }

    // todo create constructor
}
