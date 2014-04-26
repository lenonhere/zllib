package com.common;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class WebMessages {

	private static final String BUNDLE_NAME = "com.common.LocalizedWebMessages";

	private static final ResourceBundle RESOURCE_BUNDLE;

	private static final Locale DEFAULT_LOCALE = Locale.getDefault();

	static {
		ResourceBundle temp = null;

		try {
			temp = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);
		} catch (Throwable t) {
			throw new RuntimeException(
					"Can't load resource bundle due to underlying exception "
							+ t.toString());
		} finally {
			RESOURCE_BUNDLE = temp;
		}
	}

	/**
	 * Dis-allow construction ...
	 */
	private WebMessages() {
	}

	/**
	 * Returns the localized message for the given message key
	 *
	 * @param key
	 *            the message key
	 * @return The localized message for the key
	 */
	public static String getString(String key) {
		if (RESOURCE_BUNDLE == null) {
			throw new RuntimeException(
					"Localized messages from resource bundle '" + BUNDLE_NAME
							+ "' not loaded");
		}
		try {
			if (key == null) {
				throw new IllegalArgumentException(
						"Message key can not be null");
			}

			String message = RESOURCE_BUNDLE.getString(key);

			if (message == null) {
				message = "Missing error message for key '" + key + "'";
			}

			if (DEFAULT_LOCALE.equals(Locale.SIMPLIFIED_CHINESE)) {
				message = new String(message.getBytes("8859_1"), "GB2312");
			}
			return message;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		} catch (UnsupportedEncodingException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object[] args) {
		return MessageFormat.format(getString(key), args);
	}

}
