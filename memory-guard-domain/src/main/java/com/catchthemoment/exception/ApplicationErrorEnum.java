package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1001, "Problems on the server.Try it another time"),

	USER_NOT_FOUND(2001,"User not found"),
	VERIFICATION_FAIL(3002,"User's verification has been failed"),
	IMAGE_NOT_FOUND(2002,"Image not found"),
	ACCESS_DENIED(2003,"Access denied"),
	ILLEGAL_STATE(2004,"Already exists"),

	INCORRECT_INPUT(4001,"The entered data did not pass validation"),
	MAIL_INCORRECT(4002,"The mail incorrect"),
	VALID_ACCOUNT_ERROR(4003, "Verify account went wrong incorrect input"),
	EMPTY_REQUEST(4004,"The request is empty"),
	ALBUM_ERROR_INPUT(4005,"Invalid processing of creation album");

	private final int code;
	private final String message;
}
