package com.qmx.sysutils;

public class SysConstants {

	// public static final String DB_INFO_FILE_PATH =
	// "E:/logs/dbinfo.properties";
	public static final String UNIX_LINE_END = "\n";
	public static final String WINDOWS_LINE_END = "/r\n";
	//
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String ENCODE_GBK = "GBK";
	public static final String ENCODE_GB2312 = "GB2312";
	//
	public static final String[] SYSTEMCATEGORY = { "省内系统", "省外系统" };

	/** 角色：系统管理员 */
	public static final String ROLE_ADMIN = "01010001";
	/** 角色：决策层 */
	public static final String ROLE_DECISION_MAKING = "02010001";
	/** 角色：厂长经理层 */
	public static final String ROLE_MANAGER = "03010001";
	/** 角色：一般管理层 */
	public static final String ROLE_SUPERVISOR = "04010001";

	/** 省内用户角色：省内计划员 */
	public static final String ROLE_I_PLANNER = "05010001";
	/** 省内用户角色：省内业务员 */
	public static final String ROLE_I_SALES = "05030001";
	/** 省内用户角色：省内信息员 */
	public static final String ROLE_I_INFO = "05020001";
	/** 省内用户角色：省内协消员 */
	public static final String ROLE_I_ASSIST = "05040001";
	/** 省内用户角色：省内烟草公司 */
	public static final String ROLE_I_CORP = "05050001";

	/** 省外用户角色：省外计划员 */
	public static final String ROLE_O_PLANNER = "06010001";
	/** 省外用户角色：省外业务员 */
	public static final String ROLE_O_SALES = "06030001";
	/** 省外用户角色：省外信息员 */
	public static final String ROLE_O_INFO = "06020001";
	/** 省外用户角色：省外协消员 */
	public static final String ROLE_O_ASSIST = "06040001";
	/** 省外用户角色：省外烟草公司 */
	public static final String ROLE_O_CORP = "06050001";
	//
	public static final String DB_TYPES = "MYSQL,MSSQL_2000,MSSQL_2005,ORACLE_10G,ORACLE_11G,DB2";
	public static final String EXPORT_FILE_TYPE_EXCEL = "EXCEL";
	public static final String EXPORT_FILE_TYPE_SQL = "SQL";
	public static final String EXPORT_FILE_TYPE_CSV = "CSV";
	// MYSQL
	public static final String DB_MYSQL = "MYSQL";
	public static final String DB_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_MYSQL_URL = "jdbc:mysql://localhost:3306/dbName";
	public static final String DB_MYSQL_SCHEMA_LIST_SQL = "select SCHEMA_NAME from information_schema.SCHEMATA";
	public static final String DB_MYSQL_TABLE_LIST_SQL = "select TABLE_NAME from information_schema.tables where table_schema=?";
	// MSSQL
	public static final String DB_MSSQL_2000 = "MSSQL_2000";
	public static final String DB_MSSQL_2005 = "MSSQL_2005";
	public static final String DB_MSSQL_2000_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public static final String DB_MSSQL_2005_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String DB_MSSQL_2000_URL = "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=dbName";
	public static final String DB_MSSQL_2005_URL = "jdbc:sqlserver://localhost:1433;databaseName=dbName";
	public static final String DB_MSSQL_SCHEMA_LIST_SQL = "select name from sys.schemas";
	public static final String DB_MSSQL_TABLE_LIST_SQL = "select name from sys.tables where schema_id=(select schema_id from sys.schemas where name=?)";
	// ORACLE
	public static final String DB_ORACLE_10G = "ORACLE_10G";
	public static final String DB_ORACLE_11G = "ORACLE_11G";
	public static final String DB_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DB_ORACLE_10G_URL = "jdbc:oracle:thin:@localhost:1521:dbName";
	public static final String DB_ORACLE_11G_URL = "jdbc:oracle:thin:@//localhost:1521/dbName";
	public static final String DB_ORACLE_SCHEMA_LIST_SQL = "select DISTINCT OWNER from ALL_TABLES order by 1";
	public static final String DB_ORACLE_TABLE_LIST_SQL = "select TABLE_NAME from ALL_TABLES where OWNER=UPPER(?) order by 1";
	// DB2
	public static final String DB_DB2 = "DB2";
	public static final String DB_DB2_TYPE2_DRIVER = "COM.ibm.db2.jdbc.app.DB2Driver";
	public static final String DB_DB2_TYPE4_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String DB_DB2_TYPE2_URL = "jdbc:db2:dbName";
	public static final String DB_DB2_TYPE4_URL = "jdbc:db2://localhost:50000/dbName";
	public static final String DB_DB2_SCHEMA_LIST_SQL = "select RTRIM(SCHEMANAME) from syscat.SCHEMATA";
	public static final String DB_DB2_TABLE_LIST_SQL = "select TABNAME from syscat.tables where tabschema=?";
	//
}
