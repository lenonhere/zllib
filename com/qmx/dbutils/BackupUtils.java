package com.qmx.dbutils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 *备份
 *
 */
public class BackupUtils {

	private static Log log = LogFactory.getLog(BackupUtils.class);

	public BackupUtils() {
	}

	/**
	 * @param dbInfo
	 *            数据库信息
	 * @param bakFilePath
	 *            备份路径
	 * @return
	 */
	public static boolean backupDB(DBInformation dbInfo, String bakFilePath) {
		// TODO Backup Database
		boolean returnValue = false;
		String username = dbInfo.getUsername();
		String password = dbInfo.getPassword();
		String hostaddress = dbInfo.getUrl();
		String port = "";
		String schema = dbInfo.getDatasource();
		String bakpath;
		String logpath;
		try {
			if (bakFilePath.indexOf(".") == -1) {
				Long systime = System.currentTimeMillis();
				bakpath = bakFilePath + systime + ".dmp";
				logpath = bakFilePath + systime + ".log";
			} else {
				bakpath = bakFilePath;
				logpath = bakpath.substring(0, bakpath.indexOf(".")) + ".log";
			}

			String command = "";

			int dbType = 0;
			switch (dbType) {
			case 0:
				// command = expOracleDB(username, password, hostaddress, port,
				// bakpath, logpath,
				// schema);
				break;
			default:
				break;
			}

			if (command != null && !"".equals(command)) {
				Runtime.getRuntime().exec(command);
			}
			returnValue = true;
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return returnValue;
	}

	public static void main(String[] args) {
		try {
			MyDBUtils db = MyDBUtils.getMyDB("mysql");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}