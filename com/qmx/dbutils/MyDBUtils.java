package com.qmx.dbutils;

import static com.zl.util.MethodFactory.print;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.SystemConfig;
import com.qmx.ioutils.FileUtils;
import com.qmx.sysutils.MyMD5Util;
import com.zl.base.core.db.DBPool;
import com.zl.base.core.db.DBPoolInfo;
import com.zl.base.core.db.DBPoolQmx;

public class MyDBUtils {
	private static Log log = LogFactory.getLog(MyDBUtils.class);
	// 数据库链接信息配置文件
	private static final ResourceBundle bundle = ResourceBundle.getBundle("db");

	// 数据库链接信息
	private static String dataSource = null;
	// private static String SCHEMA;
	private static String DRIVER;
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;
	private static String MAXCONN;
	private static String SERVERTYPE;
	private static MyDBUtils mydb = null;
	private static int clients = 0;
	private static Vector<Driver> drivers = null;
	private static Hashtable<String, DBPool> pools = null;
	// 数据库对象
	private static Connection conn = null;
	private static PreparedStatement psmt = null;
	private static ResultSet rs = null;
	// 临时变量
	private static List<BasicDynaBean> list = null;
	// 数据库导出 DBExportGUI
	private static final String dbInfoFilePath = SystemConfig
			.getDBInfoFilePath();
	private static List<DBInformation> dbInfoList = new ArrayList<DBInformation>();
	private static Map<String, DBInformation> dbInfoMap = new HashMap<String, DBInformation>();

	/**
	 * 构造函数
	 */
	public MyDBUtils() {
		dataSource = getDataSource();
		drivers = new Vector<Driver>();
		pools = new Hashtable<String, DBPool>();

		init();
	}

	/**
	 * 构造函数
	 *
	 * @param dataSource
	 */
	public MyDBUtils(String dataSource) {
		if ((dataSource == null) || ("".equals(dataSource))) {
			MyDBUtils.dataSource = getDataSource();
		} else {
			MyDBUtils.dataSource = dataSource.toLowerCase();
		}
		drivers = new Vector<Driver>();
		pools = new Hashtable<String, DBPool>();

		init();
	}

	/**
	 * 构造函数
	 *
	 * @param dbInfo
	 */
	public MyDBUtils(DBInformation dbInfo) {
		if (dbInfo != null) {
			dataSource = dbInfo.getDatasource();
		} else {
			dataSource = getDataSource();
		}

		drivers = new Vector<Driver>();
		pools = new Hashtable<String, DBPool>();

		init(dbInfo);
	}

	/**
	 * @return
	 */
	public static MyDBUtils getMyDB() {
		clients += 1;
		if (mydb != null)
			log.info("MyDBUtil.getMyDB().clients: " + clients);
		else {
			mydb = new MyDBUtils();
		}

		return mydb;
	}

	/**
	 * @param dataSource
	 * @return
	 */
	public static MyDBUtils getMyDB(String dataSource) {
		if (mydb == null) {
			mydb = new MyDBUtils(dataSource);
		}
		clients += 1;
		if (mydb != null) {
			if (MyDBUtils.dataSource.equalsIgnoreCase(dataSource)) {
				log.info("MyDBUtil.getMyDB(" + dataSource + ")clients: "
						+ clients);
			} else {
				mydb = new MyDBUtils(dataSource);
			}
		}

		return mydb;
	}

	/**
	 * @param dbInfo
	 * @return
	 */
	public static MyDBUtils getMyDB(DBInformation dbInfo) {
		if (mydb == null) {
			mydb = new MyDBUtils(dbInfo);
		}
		clients += 1;
		if (mydb != null) {
			if (MyDBUtils.dataSource.equalsIgnoreCase(dbInfo.getDatasource())) {
				log.info("MyDBUtil.getMyDB(" + dataSource + ")clients: "
						+ clients);
			} else {
				mydb = new MyDBUtils(dbInfo);
			}
		}

		return mydb;
	}

	/**
	 * 从db.properties文件中读取DataSource的值
	 *
	 * @return
	 */
	public static String getDataSource() {
		String ds = bundle.getString("DataSource");
		log.info("====//" + ds + "//====");
		return ds;
	}

	/**
	 *
	 * @return 数据库相关信息
	 */
	public static DBInformation getDataInfo() {
		DBInformation dbInfo = new DBInformation();
		dbInfo.setDatasource(dataSource);
		dbInfo.setDriver(DRIVER);
		dbInfo.setUrl(URL);
		dbInfo.setUsername(USERNAME);
		dbInfo.setPassword(PASSWORD);
		dbInfo.setMaxconn(MAXCONN);
		dbInfo.setServertype(SERVERTYPE);
		return dbInfo;
	}

	/**
	 * 根据DataSource获取当前数据库的Schema
	 *
	 * @param dataSource
	 * @return
	 */
	public static String getSchema(String dataSource) {
		String schema = "";
		if ("db2".equals(dataSource) || "oracle".equals(dataSource)) {
			// 取用户名
			schema = bundle.getString(dataSource + ".username");
		} else if ("mssql".equals(dataSource)) {
			String connUrl = bundle.getString(dataSource + ".url");
			int index = connUrl.lastIndexOf("=");
			// 服数据库名
			schema = connUrl.substring(index + 1);
		} else if ("mysql".equals(dataSource)) {
			String connUrl = bundle.getString(dataSource + ".url");
			int index = connUrl.lastIndexOf("/");
			// 服数据库名
			schema = connUrl.substring(index + 1);
		}

		return schema;
	}

	/**
	 * 初始化数据库链接信息
	 */
	private void init() {
		if ((dataSource == null) || ("".equals(dataSource))) {
			DRIVER = bundle.getString("driver");
			URL = bundle.getString("url");
			USERNAME = bundle.getString("username");
			PASSWORD = bundle.getString("password");
			MAXCONN = bundle.getString("maxconn");
		} else {
			DRIVER = bundle.getString(dataSource + ".driver");
			URL = bundle.getString(dataSource + ".url");
			USERNAME = bundle.getString(dataSource + ".username");
			PASSWORD = bundle.getString(dataSource + ".password");
			MAXCONN = bundle.getString(dataSource + ".maxconn");
		}
		if ((MAXCONN == null) || ("".equals(MAXCONN))) {
			MAXCONN = "1";
		}
		SERVERTYPE = bundle.getString("ServerType");

		//
		createPools();
	}

	/**
	 * 初始化数据库链接信息
	 */
	private void init(DBInformation dbInfo) {
		if ((dataSource == null) || ("".equals(dataSource))) {
			DRIVER = bundle.getString("driver");
			URL = bundle.getString("url");
			USERNAME = bundle.getString("username");
			PASSWORD = bundle.getString("password");
			MAXCONN = bundle.getString("maxconn");
		} else {
			DRIVER = dbInfo.getDriver();
			URL = dbInfo.getUrl();
			USERNAME = dbInfo.getUsername();
			PASSWORD = dbInfo.getPassword();
		}
		if ((MAXCONN == null) || ("".equals(MAXCONN))) {
			MAXCONN = "1";
		}
		SERVERTYPE = bundle.getString("ServerType");

		//
		createPools();
	}

	private void createPools() {
		if (URL == null) {
			log.error("没有为连接池" + dataSource + "指定URL");
		} else {

			loadDrivers(DRIVER);

			DBPoolInfo dbPoolInfo = new DBPoolInfo();
			dbPoolInfo.setName(dataSource);
			dbPoolInfo.setDriver(DRIVER);
			dbPoolInfo.setURL(URL);
			dbPoolInfo.setUser(USERNAME);
			dbPoolInfo.setPassword(PASSWORD);
			dbPoolInfo.setMaxConn(MAXCONN);
			dbPoolInfo.setType(SERVERTYPE);
			DBPool dbpool = DBPoolQmx.getInstance(dbPoolInfo);

			pools.put(dataSource, dbpool);
			// log.info("createPools().成功创建连接池" + dataSource);
		}
	}

	private void loadDrivers(String s) {
		try {
			Driver driver = (Driver) Class.forName(s).newInstance();
			DriverManager.registerDriver(driver);
			drivers.addElement(driver);

			// log.info("成功注册JDBC驱动程序" + s);
		} catch (Exception e) {
			log.error("无法注册JDBC驱动程序: " + s + ", 错误: " + e);
		}
	}

	public Connection getConn(String dataSource) {
		DBPool pool = (DBPool) pools.get(dataSource);

		if (pool != null)
			conn = pool.getConnection(dataSource);
		else {
			return null;
		}
		return conn;
	}

	public Connection getConn() {
		DBPool pool = (DBPool) pools.get(dataSource);

		if (pool != null)
			conn = pool.getConnection(dataSource);
		else {
			return null;
		}
		return conn;
	}

	private void closeConn(String dataSource, Connection conn) {
		DBPool dbpool = (DBPool) pools.get(dataSource);
		if (dbpool != null) {
			dbpool.freeConnection(dataSource, conn);
		} else {
			try {
				log.debug("连接池没有查到：" + dataSource);
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 执行查询语句
	 *
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BasicDynaBean> ExecSelectSql(String sql) {
		try {
			print(sql, dataSource);
			conn = getConn(dataSource);
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			log.info("MyDBUtil.ExecSelectSql()--" + sql);
			RowSetDynaClass rsdc = new RowSetDynaClass(rs);
			list = rsdc.getRows();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			closeConn(dataSource, conn);
		}

		return list;
	}

	/**
	 * 执行查询语句
	 *
	 * <pre>
	 * 注意关闭 rs.ResultSet, psmt.PrepareStatement,conn.Connection
	 * </pre>
	 *
	 * @param sql
	 * @return
	 */
	public ResultSet ExecSelectSql2(String sql) {
		try {
			print(sql, dataSource);
			conn = getConn(dataSource);
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

		} catch (SQLException e) {
			log.error(e);
		} finally {
			// closeConn(dataSource, conn);
		}

		return rs;
	}

	/**
	 * 执行增加.修改.删除语句
	 *
	 * @param sql
	 * @return
	 */
	public int ExecUpdateSql(String sql) {
		int affectRowCount = -1;
		try {
			print(sql, dataSource);
			conn = getConn(dataSource);
			psmt = conn.prepareStatement(sql);
			affectRowCount = psmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			closeConn(dataSource, conn);
		}

		return affectRowCount;
	}

	/**
	 * 执行增加.修改.删除语句
	 *
	 * @param sql
	 * @return
	 */
	public boolean ExecureSql(String sql) {
		boolean flag = false;
		try {
			print(sql, dataSource);
			conn = getConn(dataSource);
			psmt = conn.prepareStatement(sql);
			flag = psmt.execute();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			closeConn(dataSource, conn);
		}

		return flag;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("[ ");
		str.append("Driver:" + DRIVER);
		str.append(",URL:" + URL);
		str.append(",UserName:" + USERNAME);
		str.append(",PassWord:" + PASSWORD);
		str.append(",MaxConn:" + MAXCONN);
		str.append(",ServerType:" + SERVERTYPE);
		str.append(" ]\n");

		return str.toString();
	}

	/**
	 * 使用默认数据源dataSource执行查询操作
	 *
	 * @param sqlStr
	 * @return
	 * @throws Exception
	 */
	public static List<BasicDynaBean> getResultList(String sqlStr)
			throws Exception {
		list = MyDBUtils.getMyDB().ExecSelectSql(sqlStr);
		return list;
	}

	/**
	 * 使用指定的数据源dbType执行查询操作
	 *
	 * @param dbType
	 *            数据库类型dataSource
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<BasicDynaBean> getResultList(String dataSource,
			String sqlStr) throws Exception {
		list = MyDBUtils.getMyDB(dataSource).ExecSelectSql(sqlStr);
		return list;
	}

	/**
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<BasicDynaBean> getResultList(ResultSet rs)
			throws Exception {
		if (rs.next()) {
			RowSetDynaClass rowSetDynaClass = new RowSetDynaClass(rs);
			list = rowSetDynaClass.getRows();
		}
		return list;
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @return
	 */
	public static DBInformation readDBInfoBy(String dbType) {
		DBInformation db = null;
		if (dbInfoMap.isEmpty()) {
			try {
				String[] lines = FileUtils.getLineArrayFromFile(dbInfoFilePath);
				for (String line : lines) {
					String[] dbinfo = line.split("##");
					if (dbinfo.length < 5) {
						System.out.println("配置文件中对数据库信息定义错误！");
					} else {

						if (dbType.equals(dbinfo[0])) {
							db = new DBInformation(dbinfo[0], dbinfo[1],
									dbinfo[2], dbinfo[3], dbinfo[4]);
							break;
						}
					}
				}
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			for (DBInformation dbinfo : dbInfoList) {
				if (dbinfo.getDatasource().equals(dbType)) {
					db = dbinfo;
					break;
				}
			}
		}
		return db;
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @return
	 */
	public static List<String> readDBInfo(String dbType) {
		List<String> dbUrlList = new ArrayList<String>();
		if (dbInfoMap.isEmpty()) {
			try {
				String[] lines = FileUtils.getLineArrayFromFile(dbInfoFilePath);
				for (String line : lines) {
					String[] dbinfo = line.split("##");
					if (dbinfo.length < 4) {
						System.out.println("配置文件中对数据库信息定义错误！");
					} else {
						String pwd = MyMD5Util.decrypt(dbinfo[4]);
						DBInformation db = new DBInformation(dbinfo[0],
								dbinfo[1], dbinfo[2], dbinfo[3], pwd);
						dbInfoMap.put(db.getDatasource() + db.getUrl(), db);
						dbInfoList.add(db);
						if (db.getDatasource().equals(dbType)) {
							dbUrlList.add(db.getUrl());
						}
					}
				}
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			for (DBInformation db : dbInfoList) {
				if (db.getDatasource().equals(dbType)) {
					dbUrlList.add(db.getUrl());
				}
			}
		}
		return dbUrlList;
	}

	/**
	 * @param dbinfo
	 * @return
	 */
	public static boolean storeDBInfo(DBInformation dbInfo) {
		if (!dbInfoMap.containsKey(dbInfo.getDatasource() + dbInfo.getUrl())) {
			dbInfoList.add(dbInfo);
			dbInfoMap.put(dbInfo.getDatasource() + dbInfo.getUrl(), dbInfo);
		}

		StringBuilder sb = new StringBuilder();
		for (DBInformation db : dbInfoList) {
			sb.append(db.getDatasource()).append("##");
			sb.append(db.getDriver()).append("##");
			sb.append(db.getUrl()).append("##");
			sb.append(db.getUsername()).append("##");
			String pwd = MyMD5Util.encrypt(db.getPassword());
			sb.append(pwd).append("\n");
		}
		try {
			FileUtils.writeFile(dbInfoFilePath, sb.toString(), "UTF-8");
		} catch (IOException e) {
			log.error(e);
			return false;
		}
		return true;
	}

	/**
	 * @param key
	 * @return
	 */
	public static DBInformation getDBInfoByKey(String key) {
		return (DBInformation) dbInfoMap.get(key);
	}

}
