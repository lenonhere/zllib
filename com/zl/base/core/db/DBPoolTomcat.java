package com.zl.base.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBPoolTomcat extends DBPool {
	private static Logger log = Logger.getLogger(DBPoolTomcat.class.getName());

	public static synchronized DBPool getInstance(DBPoolInfo dbpoolinfo) {
		if (instance == null)
			instance = new DBPoolTomcat();
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

			System.out.println("==============" + dbpoolinfo.getURL());

			InitialContext initialcontext = new InitialContext();
			DataSource datasource = (DataSource) initialcontext
					.lookup(dbpoolinfo.getURL());

			connection = datasource.getConnection();
		} catch (Exception exception) {
			log.error("不能获得Tomcat连接池中的数据库连接:" + exception.toString());
		}
		return connection;
	}

	public void freeConnection(String s, Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
	}

	public void release() {
	}

}