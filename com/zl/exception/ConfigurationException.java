package com.zl.exception;

public class ConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 4129007439357701078L;

	public ConfigurationException() {

	}

	public ConfigurationException(String message) {
		super(message);
	}
}
