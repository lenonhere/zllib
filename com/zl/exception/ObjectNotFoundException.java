package com.zl.exception;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5436871968119620079L;

	public ObjectNotFoundException() {

	}

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
