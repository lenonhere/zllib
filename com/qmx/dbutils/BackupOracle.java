package com.qmx.dbutils;

public class BackupOracle {

	// 临时变量
	// private static String sql = "";
	private static StringBuffer strBuffer = null;

	/**
	 * 备份Oracle数据库
	 *
	 * @param username
	 * @param password
	 * @param hostaddress
	 * @param port
	 * @param bakpath
	 * @param logpath
	 * @param schema
	 * @return
	 */
	private static String expOracleDB(String username, String password,
			String hostaddress, String port, String bakpath, String logpath,
			String schema) {
		strBuffer = new StringBuffer();
		strBuffer.append("exp " + username + "/" + password);
		strBuffer.append("@" + hostaddress);
		strBuffer.append(" full=y commit=y ignore=y");
		strBuffer.append(" owner=" + schema);
		strBuffer.append(" file=" + bakpath);
		strBuffer.append(" log=" + logpath);
		return strBuffer.toString();
	}

	/**
	 * 还原Oracle数据库
	 *
	 * @param username
	 * @param password
	 * @param hostaddress
	 * @param port
	 * @param bakpath
	 * @param logpath
	 * @param fromuser
	 * @param touser
	 * @return
	 */
	private static String impOracleDB(String username, String password,
			String hostaddress, String port, String bakpath, String logpath,
			String fromuser, String touser) {
		strBuffer = new StringBuffer();
		strBuffer.append("imp " + username + "/" + password);
		strBuffer.append("@" + hostaddress);
		strBuffer.append(" full=y commit=y ignore=y");
		strBuffer.append(" file=" + bakpath);
		strBuffer.append(" log=" + logpath);
		strBuffer.append(" fromuser=" + fromuser);
		strBuffer.append(" touser=" + touser);
		return strBuffer.toString();
	}
}
