package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1001, "Problems on the server.Try it another time"),

	USER_NOT_FOUND(2001,"Not found"),
	ACCESS_DENIED(2002,"Access denied"),
	ILLEGAL_STATE(2003,"Already exists"),

	INCORRECT_INPUT(4001,"The entered data did not pass validation"),
	MAIL_INCORRECT(4002,"The mail incorrect"),
	VALID_ACCOUNT_ERROR(4003, "Verify account went wrong..");

	private final int code;
	private final String message;
}
