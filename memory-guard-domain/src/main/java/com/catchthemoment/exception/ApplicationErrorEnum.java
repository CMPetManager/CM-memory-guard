package com.catchthemoment.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1000, "server fault");

	private final int code;
	private final String message;

	ApplicationErrorEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
