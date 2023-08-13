package com.catchthemoment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorEnum {
	DEFAULT_EXCEPTION(1001, "Problems on the server.Try it another time"),

	USER_NOT_FOUND(2001,"User not found"),
	VERIFICATION_FAIL(3002,"User's verification failed"),
	PASSWORD_INPUT_FAILS(3003,"Password inputs incorrect or empty"),
	IMAGE_NOT_FOUND(2002,"Image not found"),
	ACCESS_DENIED(2003,"Access denied"),
	ILLEGAL_STATE(2004,"User's already exists"),
	MAIL_INCORRECT(4007,"User's  email invalid"),
	TOKEN_TIME_EXPIRED(4008,"Access token expired "),
	INCORRECT_INPUT(4001,"The inputs data did not pass validation"),
	USER_PARAMS_INCORRECT(4002,"Request body are invalid or empty"),
	INVALID_ACCOUNT(4006,"Account not verified "),
	EMPTY_REQUEST(4004,"The request is empty"),
	ALBUM_ERROR_INPUT(4005,"Invalid processing of creation of the album");

	private final int code;
	private final String message;
}
