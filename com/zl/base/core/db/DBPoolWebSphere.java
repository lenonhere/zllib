package com.zl.base.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBPoolWebSphere extends DBPool {
	private static DBPoolWebSphere instance;
	private static Logger log = Logger.getLogger(DBPoolWebSphere.class
			.getName());

	public static synchronized DBPool getInstance(DBPoolInfo dbpoolinfo) {
		log.info("初始化WebSphere连接池:" + dbpoolinfo.getURL());
		if (instance == null)
			instance = new DBPoolWebSphere();
		try {
			instance.addDBPool(dbpoolinfo.getName(), dbpoolinfo);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			return null;
		}
		return instance;
	}

	public Connection getConnection(String s) {
		Connection connection = null;
		try {
			DBPoolInfo dbpoolinfo = super.getDBPool(s);
			InitialContext initialcontext = new InitialContext();
			DataSource datasource = (DataSource) initialcontext
					.lookup(dbpoolinfo.getURL());
			connection = datasource.getConnection();
			freeContext(initialcontext);
		} catch (Exception exception) {
			log.error("不能获得WebSphere连接池中的数据库连接:" + exception.toString());
		}
		return connection;
	}

	private static void freeContext(Context context) {
		try {
			context.close();
		} catch (NamingException namingexception) {
			log.error("WebSphere上下文关闭出错:" + namingexception.toString());
		}
		context = null;
	}

	public void freeConnection(String s, Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException sqlexception) {
			log.error("数据库连接关闭:" + sqlexception.toString());
		}
	}

	public void release() {
	}

	static Class class$(String s) throws Exception {
		return Class.forName(s);
	}
}