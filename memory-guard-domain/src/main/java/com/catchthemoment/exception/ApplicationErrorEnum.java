package com.catchthemoment.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1001, "server fault"),
	USER_NOT_FOUND(2001,"Not found"),
	ACCESS_DENIED(2002,"Access denied"),
	ILLEGAL_STATE(2003,"Already exists"),
	INCORRECT_INPUT(4001,"The entered data did not pass validation"),

	MAIL_INCORRECT(4002,"The mail incorrect"),
	VALID_ACCOUNT_ERROR(4003, "Verify account went wrong..");




	private final int code;
	private final String message;

	ApplicationErrorEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
