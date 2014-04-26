package com.zl.exception;

public class AppRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 8874394731775392401L;
	static final String RESOURCE = "com.zl.exception.ErrorMessages";

	public AppRuntimeException(String paramString) {
		super(paramString);
	}

	public AppRuntimeException(String paramString, Object[] paramArrayOfObject) {
		super(MessageFormatter.getMessage("com.zl.exception.ErrorMessages",
				paramString, paramArrayOfObject));
	}
}
