package com.zl.base.core.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBPoolWeblogic extends DBPool {
	private static DBPoolWeblogic instance;
	private static Logger log = Logger
			.getLogger(DBPoolWeblogic.class.getName());

	public static synchronized DBPool getInstance(DBPoolInfo dbpoolinfo) {
		if (instance == null)
			instance = new DBPoolWeblogic();
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
			Hashtable hashtable = new Hashtable();
			hashtable.put("java.naming.factory.initial",
					"weblogic.jndi.WLInitialContextFactory");
			hashtable.put("java.naming.provider.url", dbpoolinfo.getDriver());
			InitialContext initialcontext = new InitialContext(hashtable);
			DataSource datasource = (DataSource) initialcontext
					.lookup(dbpoolinfo.getURL());
			connection = datasource.getConnection();
			freeContext(initialcontext);
		} catch (Exception exception) {
			log.error("不能获得Weblogic连接池中的数据库连接:" + exception.toString());
		}
		return connection;
	}

	private static void freeContext(InitialContext initialcontext) {
		try {
			initialcontext.close();
		} catch (NamingException namingexception) {
			log.error("Weblogic上下文关闭出错:" + namingexception.toString());
		}
		initialcontext = null;
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