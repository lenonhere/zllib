package com.zl.exception;

public class AppException extends Exception {
	private static final long serialVersionUID = 1002456429153320856L;
	static final String RESOURCE = "com.zl.exception.ErrorMessages";
	protected Exception org = null;

	public AppException(String paramString) {
		super(paramString);
	}

	public AppException(String paramString, Object[] paramArrayOfObject) {
		super(MessageFormatter.getMessage("com.zl.exception.ErrorMessages",
				paramString, paramArrayOfObject));
	}

	public void setOriginalException(Exception paramException) {
		this.org = paramException;
	}

	public Exception getOriginalException() {
		return this.org;
	}
}
