package com.catchthemoment.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1000, "server fault"),
	USER_NOT_FOUND(2000,"User not found"),
	ACCESS_DENIED(2001,"Access denied");


	private final int code;
	private final String message;

	ApplicationErrorEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
