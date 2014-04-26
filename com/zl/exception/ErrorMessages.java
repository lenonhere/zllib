package com.zl.exception;

import java.util.Vector;

public class ErrorMessages {
	private Vector<String> errors;

	public ErrorMessages() {
		errors = new Vector<String>();
	}

	public void addError(String key) {
		errors.addElement(key);
	}

	public String getError(int index) {
		return errors.elementAt(index);
	}

	public String[] getErrors() {
		if (errors.size() > 0) {
			String array[] = new String[errors.size()];
			errors.copyInto(array);
			return array;
		} else {
			return null;
		}
	}

	public int getSize() {
		return errors.size();
	}

}
