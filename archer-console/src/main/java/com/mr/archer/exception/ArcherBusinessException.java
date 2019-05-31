package com.mr.archer.exception;

public class ArcherBusinessException extends RuntimeException {

	public ArcherBusinessException() {
	}

	public ArcherBusinessException(String message) {
		super(message);
	}

	public ArcherBusinessException(Throwable cause) {
		super(cause);
	}

	public ArcherBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
