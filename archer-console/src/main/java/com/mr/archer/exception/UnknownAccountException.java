package com.mr.archer.exception;

/**
 * Created by feng on 2019/5/21
 */
public class UnknownAccountException extends RuntimeException{
	public UnknownAccountException() {
	}

	public UnknownAccountException(String message) {
		super(message);
	}

	public UnknownAccountException(Throwable cause) {
		super(cause);
	}

	public UnknownAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
