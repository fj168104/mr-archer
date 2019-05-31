package com.mr.archer.exception;

/**
 * Created by feng on 2019/5/21
 */
public class IncorrectCredentialsException extends RuntimeException{
	public IncorrectCredentialsException() {
	}

	public IncorrectCredentialsException(String message) {
		super(message);
	}

	public IncorrectCredentialsException(Throwable cause) {
		super(cause);
	}

	public IncorrectCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}
}
