package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceProcessingException extends Exception {

    private final int code;
    private final String message;
}
