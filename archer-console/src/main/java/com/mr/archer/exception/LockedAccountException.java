package com.mr.archer.exception;

/**
 * Created by feng on 2019/5/21
 */
public class LockedAccountException extends RuntimeException{
	public LockedAccountException() {
	}

	public LockedAccountException(String message) {
		super(message);
	}

	public LockedAccountException(Throwable cause) {
		super(cause);
	}

	public LockedAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
