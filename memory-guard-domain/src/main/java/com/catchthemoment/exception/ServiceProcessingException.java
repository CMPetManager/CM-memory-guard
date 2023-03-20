package com.catchthemoment.exception;

public class ServiceProcessingException extends RuntimeException {

    public ServiceProcessingException() {
        super();
    }

    public ServiceProcessingException(String message) {
        super(message);
    }
}
