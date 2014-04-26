package com.zl.base.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DBPoolQmx extends DBPool {
	private static Logger log = Logger.getLogger(DBPoolQmx.class.getName());

	private Hashtable pools = new Hashtable();
	Connection conn = null;

	public static synchronized DBPool getInstance(DBPoolInfo dbPoolInfo) {
		if (instance == null)
			instance = new DBPoolQmx();
		try {
			instance.addDBPool(dbPoolInfo.getName(), dbPoolInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
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
				connection.close();
			} else {
				vector.addElement(connection);
				notifyAll();
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
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
			} catch (Exception e) {
			}
			connection = null;
		}
		return flag;
	}

	public synchronized Connection getConnection(String dbType) {
		try {
			Vector vector = (Vector) this.pools.get(dbType);
			DBPoolInfo dbpoolinfo = super.getDBPool(dbType);
			if ((vector == null) || (dbpoolinfo.getMaxConn() == "0")
					|| (vector.size() == 0)) {
				conn = newConnection(dbType);
			} else if (vector.size() > 0) {
				conn = (Connection) vector.firstElement();
				vector.removeElementAt(0);
				if (!checkconnect(conn)) {
					log.debug("从连接池" + dbpoolinfo.getName() + "删除一个无效连接");
					conn = getConnection(dbType);
				}
			} else {
				log.debug("从连接池" + dbpoolinfo.getName() + "删除一个无效连接");
				conn = newConnection(dbType);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return conn;
	}

	public synchronized Connection getConnection(String s, long l) {
		long l1 = (new Date()).getTime();

		try {
			conn = getConnection(s);
			while (conn == null) {
				wait(l);
				if ((new Date()).getTime() - l1 >= l) {
					return null;
				}
			}
		} catch (InterruptedException e) {
			log.debug(e.getMessage());
		}
		return conn;
	}

	public synchronized void release() {
	}

	public Connection newConnection(String dbType) {
		try {
			Vector vector = (Vector) this.pools.get(dbType);
			DBPoolInfo dbPoolInfo = super.getDBPool(dbType);
			if (dbPoolInfo.getUser() == null)
				conn = DriverManager.getConnection(dbPoolInfo.getURL());
			else
				conn = DriverManager.getConnection(dbPoolInfo.getURL(),
						dbPoolInfo.getUser(), dbPoolInfo.getPassword());
			if (vector == null) {
				this.pools.put(dbType, new Vector());
				// this.pools.put(dbType, dbPoolInfo);
			}
			log.debug("连接池" + dbPoolInfo.getName() + "创建一个新的连接");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.debug("无法创建下列URL的连接: " + dbType + ":" + e.getMessage());
			return null;
		}
		return conn;
	}

}