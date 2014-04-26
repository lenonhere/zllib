package com.zl.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageFormatter {
	public static String getMessage(String paramString1, String paramString2,
			Object[] paramArrayOfObject) {
		ResourceBundle bundle = ResourceBundle.getBundle(paramString1);
		String str = bundle.getString(paramString2);
		return MessageFormat.format(str, paramArrayOfObject);
	}

	public static String getMessage(String paramString,
			Object[] paramArrayOfObject) {
		String str = "errormessage.properties";
		return getMessage(str, paramString, paramArrayOfObject);
	}
}
