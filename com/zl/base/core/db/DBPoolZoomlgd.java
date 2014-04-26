package com.zl.base.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DBPoolZoomlgd extends DBPool {
	private static Logger logger = Logger.getLogger(DBPoolZoomlgd.class
			.getName());
	private Hashtable pools;

	public DBPoolZoomlgd() {
		this.pools = new Hashtable();
	}

	public static synchronized DBPool getInstance(DBPoolInfo dbpoolinfo) {
		if (instance == null)
			instance = new DBPoolZoomlgd();
		try {
			instance.addDBPool(dbpoolinfo.getName(), dbpoolinfo);
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			return null;
		}
		return instance;
	}

	public synchronized void freeConnection(String s, Connection connection) {
		try {
			Vector vector = (Vector) this.pools.get(s);
			DBPoolInfo dbpoolinfo = super.getDBPool(s);
			int maxconn = Integer.valueOf(dbpoolinfo.getMaxConn());
			if (maxconn == 0 || (vector.size() >= maxconn)) {
				try {
					connection.close();
				} catch (Exception localException1) {
				}
			} else {
				vector.addElement(connection);
				notifyAll();
			}
		} catch (Exception exception) {
			logger.debug(exception.getMessage());
		}
	}

	public boolean checkconnect(Connection connection) {
		boolean flag = true;
		try {
			Statement statement = connection.createStatement();
			statement.executeBatch();
			statement.close();
			statement = null;
			flag = true;
		} catch (Exception exception) {
			flag = false;
			try {
				connection.close();
			} catch (Exception localException1) {
			}
			connection = null;
		}
		return flag;
	}

	public synchronized Connection getConnection(String s) {
		Connection connection = null;
		try {
			Vector vector = (Vector) this.pools.get(s);
			DBPoolInfo dbpoolinfo = super.getDBPool(s);
			if ((vector == null) || (dbpoolinfo.getMaxConn() == "0")
					|| (vector.size() == 0)) {
				connection = newConnection(s);
			} else if (vector.size() > 0) {
				connection = (Connection) vector.firstElement();
				vector.removeElementAt(0);
				if (!checkconnect(connection)) {
					logger.debug("从连接池" + dbpoolinfo.getName() + "删除一个无效连接");
					connection = getConnection(s);
				}
			} else {
				logger.debug("从连接池" + dbpoolinfo.getName() + "删除一个无效连接");
				connection = newConnection(s);
			}
		} catch (Exception exception) {
			logger.debug(exception.getMessage());
		}
		return connection;
	}

	public synchronized Connection getConnection(String s, long l) {
		long l1 = (new Date()).getTime();
		Connection connection;
		while ((connection = getConnection(s)) == null) {
			try {
				wait(l);
			} catch (InterruptedException interruptedexception) {
			}
			if ((new Date()).getTime() - l1 >= l)
				return null;
		}
		return connection;
	}

	public synchronized void release() {
	}

	public Connection newConnection(String s) {
		Connection connection = null;
		try {
			Vector vector = (Vector) this.pools.get(s);
			DBPoolInfo dbpoolinfo = super.getDBPool(s);
			if (dbpoolinfo.getUser() == null)
				connection = DriverManager.getConnection(dbpoolinfo.getURL());
			else
				connection = DriverManager.getConnection(dbpoolinfo.getURL(),
						dbpoolinfo.getUser(), dbpoolinfo.getPassword());
			if (vector == null) {
				this.pools.put(s, new Vector());
			}
			logger.debug("连接池" + dbpoolinfo.getName() + "创建一个新的连接");
		} catch (Exception exception) {
			logger.debug("无法创建下列URL的连接: " + s + ":" + exception.getMessage());
			return null;
		}
		return connection;
	}

	static Class class$(String s) throws Exception {
		return Class.forName(s);
	}
}