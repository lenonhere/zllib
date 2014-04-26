package com.zl.base.core.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.SystemConfig;

public class DBConnectionManager {
	private static Log logger = LogFactory.getLog(DBConnectionManager.class);
	private static DBConnectionManager instance;
	private static int clients;
	private Vector<Driver> drivers = new Vector<Driver>();
	// private static PrintWriter log;
	private static Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();
	private static String dataSource;

	/**
	 * 实例化
	 *
	 * @return
	 */
	public static synchronized DBConnectionManager getInstance() {
		// dataSource=null;
		if (instance == null) {// ||dataSource==null||pools.get(dataSource)==null
			instance = new DBConnectionManager();
		}
		clients += 1;
		return instance;
	}

	/**
	 * 实例化
	 *
	 * @return
	 */
	public static synchronized DBConnectionManager getInstance(String dataSource) {
		DBConnectionManager.dataSource = dataSource;// .toLowerCase().trim();
		// print("getInstance::" + instance);
		// if (instance == null) {
		// instance = new DBConnectionManager();
		// }
		DBConnectionPool pool = pools.get(dataSource);

		if (pool == null) {
			instance = new DBConnectionManager();
		}
		clients += 1;
		return instance;
	}

	private DBConnectionManager() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		InputStream is = getClass().getResourceAsStream("/db.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (Exception localException) {
			System.err.println("不能读取属性文件. 请确保db.properties在CLASSPATH指定的路径中");
			return;
		}
		// String str = properties.getProperty("logfile",
		// "DBConnectionManager.log");
		// try {
		// PrintWriter log = new PrintWriter(new FileWriter(str, true), true);
		// } catch (IOException e) {
		// logger.error("无法打开日志文件: " + str + "\n" + e);
		// PrintWriter log = new PrintWriter(System.err);
		// }
		createPools(properties);
	}

	/**
	 * 创建连接池
	 *
	 * @param paramProperties
	 */
	private void createPools(Properties paramProperties) {

		Enumeration<?> localEnumeration = paramProperties.propertyNames();
		if (dataSource == null) {
			// 获取默认的DataSource
			dataSource = SystemConfig.getResource("db").getString("DataSource")
					.toLowerCase().trim();
		}
		boolean stopFlag = true;
		while (stopFlag && localEnumeration.hasMoreElements()) {

			String str = (String) localEnumeration.nextElement();
			if (str.toLowerCase().trim().startsWith(dataSource)) {
				String url = paramProperties.getProperty(dataSource + ".url");
				if (url == null || "".equals(url.trim())) {
					logger.debug("没有为连接池" + dataSource + "指定URL");
				} else {
					String username = paramProperties.getProperty(dataSource
							+ ".username");
					String password = paramProperties.getProperty(dataSource
							+ ".password");
					String maxconn = paramProperties.getProperty(dataSource
							+ ".maxconn", "");
					String driver = paramProperties.getProperty(dataSource
							+ ".driver", "");
					// 加载注册数据库驱动类
					loadDrivers(driver);
					int i;
					try {
						i = Integer.valueOf(maxconn).intValue();
					} catch (NumberFormatException nfe) {
						logger.error("错误的最大连接数限制: " + maxconn + " .连接池: "
								+ dataSource);
						i = 0;
					}
					DBConnectionPool localDBConnectionPool = new DBConnectionPool(
							dataSource, url, username, password, i);
					// 将连接信息存放在Hashtable中
					pools.put(dataSource, localDBConnectionPool);
					stopFlag = false;
				}
			}
		}

	}

	/**
	 * 加载注册数据库驱动
	 *
	 * @param paramString
	 */
	private void loadDrivers(String driverStr) {
		try {
			Driver localDriver = (Driver) Class.forName(driverStr)
					.newInstance();
			DriverManager.registerDriver(localDriver);
			this.drivers.addElement(localDriver);
		} catch (Exception e) {
			logger.error("无法注册JDBC驱动程序: " + driverStr + ", 错误: " + e);
		}
	}

	/**
	 * @param paramString
	 * @param paramConnection
	 */
	public void freeConnection(String paramString, Connection paramConnection) {
		DBConnectionPool localDBConnectionPool = (DBConnectionPool) pools
				.get(paramString);
		if (localDBConnectionPool != null)
			localDBConnectionPool.freeConnection(paramConnection);
		else
			try {
				logger.error("连接池没有查到：" + paramString);
				paramConnection.close();
			} catch (Exception localException) {
			}
	}

	public Connection getnewConnection(String paramString) {
		DBConnectionPool localDBConnectionPool = (DBConnectionPool) pools
				.get(paramString);
		if (localDBConnectionPool != null)
			return localDBConnectionPool.newConnection();
		return null;
	}

	/**
	 * 从连接池中获取连接
	 *
	 * @param paramString
	 * @return
	 */
	public Connection getConnection(String dataSource) {
		DBConnectionPool localDBConnectionPool = (DBConnectionPool) pools
				.get(dataSource);
		if (localDBConnectionPool != null) {
			return localDBConnectionPool.getConnection();
		}
		return null;
	}

	public Connection getConnection(String paramString, long paramLong) {
		DBConnectionPool localDBConnectionPool = (DBConnectionPool) pools
				.get(paramString);
		if (localDBConnectionPool != null)
			return localDBConnectionPool.getConnection(paramLong);
		return null;
	}

	public synchronized void release() {
		if (--clients != 0)
			return;
		DBConnectionPool dbconnectionpool;
		for (Enumeration<?> enumeration = pools.elements(); enumeration
				.hasMoreElements(); dbconnectionpool.release())
			dbconnectionpool = (DBConnectionPool) enumeration.nextElement();

		for (Enumeration<?> enumeration1 = drivers.elements(); enumeration1
				.hasMoreElements();) {
			Driver driver = (Driver) enumeration1.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug("撤销JDBC驱动程序 " + driver.getClass().getName()
						+ "的注册");
			} catch (SQLException e) {
				logger.error("无法撤销下列JDBC驱动程序的注册: "
						+ driver.getClass().getName() + "\n" + e);
			}
		}

	}

	/**
	 * DBConnectionPool连接池内部类
	 *
	 * @author NTSD
	 *
	 */
	class DBConnectionPool {
		private int checkedOut;
		private Vector<Connection> freeConnections = new Vector<Connection>();
		private int maxConn;
		private String name;
		private String password;
		private String URL;
		private String user;

		/**
		 * 构造函数
		 *
		 * @param paramString1
		 * @param paramString2
		 * @param paramString3
		 * @param paramInt
		 * @param i
		 */
		public DBConnectionPool(String paramString1, String paramString2,
				String paramString3, String paramInt, int i) {
			this.name = paramString1;
			this.URL = paramString2;
			this.user = paramString3;
			this.password = paramInt;
			this.maxConn = i;
		}

		public synchronized void freeConnection(Connection paramConnection) {
			if (this.maxConn == 0) {
				try {
					paramConnection.close();
				} catch (Exception localException) {
				}
			} else {
				this.freeConnections.addElement(paramConnection);
				notifyAll();
			}
		}

		public boolean checkconnect(Connection paramConnection) {
			boolean bool = true;
			try {
				Statement localStatement = paramConnection.createStatement();
				localStatement.executeBatch();
				localStatement.close();
				localStatement = null;
				bool = true;
			} catch (Exception localException1) {
				bool = false;
				try {
					paramConnection.close();
				} catch (Exception localException2) {
				}
				paramConnection = null;
			}
			return bool;
		}

		public synchronized Connection getConnection() {
			int i = 0;
			Connection localConnection = null;
			if ((this.maxConn == 0) || (this.checkedOut < this.maxConn)) {
				localConnection = newConnection();
				i = 1;
			} else if (this.freeConnections.size() > 0) {
				localConnection = (Connection) this.freeConnections
						.firstElement();
				this.freeConnections.removeElementAt(0);
				if (!checkconnect(localConnection)) {
					this.checkedOut -= 1;
					// logger.debug("从连接池" + this.name + "删除一个无效连接");
					localConnection = getConnection();
					i = 1;
				}
			} else {
				// logger.debug("从连接池" + this.name + "删除一个无效连接");
				i = 1;
				localConnection = newConnection();
			}
			if ((localConnection != null) && (this.maxConn != 0) && (i == 1))
				this.checkedOut += 1;
			return localConnection;
		}

		public synchronized Connection getConnection(long paramLong) {
			long l = new Date().getTime();
			Connection localConnection;
			while ((localConnection = getConnection()) == null) {
				try {
					wait(paramLong);
				} catch (InterruptedException localInterruptedException) {
				}
				if (new Date().getTime() - l >= paramLong)
					return null;
			}
			return localConnection;
		}

		public synchronized void release() {
			Enumeration<Connection> localEnumeration = this.freeConnections
					.elements();
			while (localEnumeration.hasMoreElements()) {
				Connection localConnection = localEnumeration.nextElement();
				try {
					localConnection.close();
					logger.debug("关闭连接池" + this.name + "中的一个连接");
				} catch (SQLException e) {
					logger.error("无法关闭连接池" + this.name + "中的连接" + e);
				}
			}
			this.freeConnections.removeAllElements();
		}

		/**
		 * 创建新的连接
		 *
		 * @return
		 */
		public Connection newConnection() {
			Connection conn = null;
			try {
				if (this.user == null) {
					conn = DriverManager.getConnection(this.URL);
				} else {
					conn = DriverManager.getConnection(this.URL, this.user,
							this.password);
				}
			} catch (SQLException e) {
				logger.error("无法创建下列URL的连接: " + this.URL + "\n" + e);
				return null;
			}
			return conn;
		}
	}
}
