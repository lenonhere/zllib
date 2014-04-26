package com.qmx.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @Title: BackupMsSQL.java
 * @Package com.qmx.dbutils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author qmx
 * @date 2013/04/11 15:42:04
 * @version V1.0
 */
public class BackupMsSQL {
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static String sql = "";

	public static void test() {

	}

	/**
	 * @param dbName
	 * @param bakDirPath
	 *            E:/logs/
	 */
	public void backup(String dbName, String bakDirPath) {
		try {
			// 盘名是否正确
			if (bakDirPath.lastIndexOf("\\") == -1) {
				bakDirPath += "\\";
			}
			// 与数据库进行操作
			String file = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new java.util.Date()) + ".bak";
			sql = "backup database " + dbName + " to disk='" + bakDirPath
					+ file + "' with format,name='full backup of " + dbName
					+ "'";

			int i = MyDBUtils.getMyDB("mssql").ExecUpdateSql(sql);
			System.out.println("备份数据成功！" + i);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("备份数据失败！");
		}

	}

	/**
	 * @param dbName
	 * @param bakFilePath
	 *            E:/logs/mssql-20130405.bak
	 */
	public void restore(String dbName, String bakFilePath) {
		try {
			sql = "alter database " + dbName
					+ " set offline with rollback immediate;";
			sql += "restore database  " + dbName + "  from disk='"
					+ bakFilePath + "'";
			sql += "with replace "; // 解决备尚未备份数据库 数据库 的日志尾部
			sql += "alter database  " + dbName
					+ "  set onLine with rollback immediate;";

			conn = MyDBUtils.getMyDB("mssql").getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate("use master");
			pstmt.executeUpdate();
			System.out.println("还原数据成功！");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("还原数据失败！");
		} finally {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
