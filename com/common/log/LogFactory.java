package com.common.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;

public class LogFactory {

	public static final String LOGNAME = "qmxLogger";

	public static Log getLog() {
		return org.apache.commons.logging.LogFactory.getLog(LOGNAME);
	}

	public static void main(String[] args) {
		Log logger = LogFactory.getLog();
		logger.debug("start for "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
						System.currentTimeMillis())));
		for (int i = 0; i < 10; i++) {
			logger.debug("myLog hello logger!");
		}
		logger.debug("end for "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
						System.currentTimeMillis())));
	}
}
