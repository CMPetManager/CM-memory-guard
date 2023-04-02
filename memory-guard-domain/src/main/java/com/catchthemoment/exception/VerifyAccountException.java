package com.catchthemoment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyAccountException extends Exception{

    private int code;
    private String message;
}
