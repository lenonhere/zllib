package com.qmx.dbutils;

import static com.qmx.dbutils.MyDBUtils.getResultList;
import static com.zl.util.MethodFactory.isEmpty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.transaction.SystemException;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.qmx.dateutils.DateUtils;
import com.qmx.sysutils.SysConstants;
import com.zl.base.core.db.Executer;
import com.zl.util.OptionHold;

public class MetaDataUtils {
	private static final Logger log = Logger.getLogger(MetaDataUtils.class);
	// 数据库对象
	private static ResultSet rs = null;
	// 临时变量
	private static String sql = "";
	private static StringBuffer strBuffer = null;
	private static List<BasicDynaBean> list = null;
	private static ArrayList<OptionHold> optionList = null;

	/**
	 * 返回数据库类型列表
	 * 
	 * @return [0.mysql, 1.mssql, 2.oracle, 3.db2, -1.other]
	 */
	public static ArrayList<OptionHold> getDBNamesList() {
		optionList = new ArrayList<OptionHold>();
		optionList.add(new OptionHold("mysql", "MySql", "0", ""));
		optionList.add(new OptionHold("mssql", "SQLServer", "1", ""));
		optionList.add(new OptionHold("oracle", "Oracle", "2", ""));
		optionList.add(new OptionHold("db2", "DB2", "3", ""));
		// optionList.add(new OptionHold("gsql", "GSQL", "4", ""));

		return optionList;
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @return [0.mysql, 1.mssql, 2.oracle, 3.db2, -1.other]
	 */
	public static Integer getDBTypeInt(String dbType) {
		Integer dbFlag = -1;
		try {

			if ("mysql".equalsIgnoreCase(dbType)) {
				dbFlag = 0;
			} else if ("mssql".equalsIgnoreCase(dbType)) {
				dbFlag = 1;
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				dbFlag = 2;
			} else if ("db2".equalsIgnoreCase(dbType)) {
				dbFlag = 3;
			} else {
				log.debug("暂不支持" + dbType + "数据库,请检查!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

		return dbFlag;
	}

	/**
	 * 获取列名和列值
	 * 
	 * @param rs
	 *            查询结果集ResultSet
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer[] getColumnNameAndValue(ResultSet rs)
			throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		StringBuffer ColumnName = null;
		StringBuffer ColumnValue = null;
		StringBuffer ColumnValueAll = null;
		boolean isFirst = true;
		while (rs.next()) {
			if (isFirst) {
				ColumnName = new StringBuffer();
				ColumnValueAll = new StringBuffer();
			}
			ColumnValue = new StringBuffer();
			for (int i = 1; i <= columnCount; i++) {
				String value = rs.getString(i).trim();
				if ("".equals(value)) {
					value = " ";
				}
				if (i == 1 || i == columnCount) {
					if (isFirst) {
						if (i == columnCount) {
							ColumnName.append(",")
									.append(rsmd.getColumnName(i));
						} else {
							ColumnName.append(rsmd.getColumnName(i));
						}
					}
					if (Types.CHAR == rsmd.getColumnType(i)
							|| Types.VARCHAR == rsmd.getColumnType(i)
							|| Types.LONGVARCHAR == rsmd.getColumnType(i)) {
						ColumnValue.append("'").append(value).append("'");
					} else if (Types.SMALLINT == rsmd.getColumnType(i)
							|| Types.INTEGER == rsmd.getColumnType(i)
							|| Types.BIGINT == rsmd.getColumnType(i)
							|| Types.FLOAT == rsmd.getColumnType(i)
							|| Types.DOUBLE == rsmd.getColumnType(i)
							|| Types.NUMERIC == rsmd.getColumnType(i)
							|| Types.DECIMAL == rsmd.getColumnType(i)
							//
							|| Types.TINYINT == rsmd.getColumnType(i)
							|| Types.REAL == rsmd.getColumnType(i)
					//
					) {
						ColumnValue.append(value);// .append(",");
					} else if (Types.DATE == rsmd.getColumnType(i)
							|| Types.TIME == rsmd.getColumnType(i)
							|| Types.TIMESTAMP == rsmd.getColumnType(i)) {
						ColumnValue.append("timestamp '").append(value)
								.append("'");
					} else {
						ColumnValue.append(value);
					}
					if (i != columnCount) {
						ColumnValue.append(",");
					}
				} else {
					if (isFirst) {
						ColumnName.append("," + rsmd.getColumnName(i));
					}
					if (Types.CHAR == rsmd.getColumnType(i)
							|| Types.VARCHAR == rsmd.getColumnType(i)
							|| Types.LONGVARCHAR == rsmd.getColumnType(i)) {
						ColumnValue.append("'").append(value).append("'")
								.append(",");
					} else if (Types.SMALLINT == rsmd.getColumnType(i)
							|| Types.INTEGER == rsmd.getColumnType(i)
							|| Types.BIGINT == rsmd.getColumnType(i)
							|| Types.FLOAT == rsmd.getColumnType(i)
							|| Types.DOUBLE == rsmd.getColumnType(i)
							|| Types.NUMERIC == rsmd.getColumnType(i)
							|| Types.DECIMAL == rsmd.getColumnType(i)
							//
							|| Types.TINYINT == rsmd.getColumnType(i)
							|| Types.REAL == rsmd.getColumnType(i)
					//
					) {
						ColumnValue.append(value).append(",");
					} else if (Types.DATE == rsmd.getColumnType(i)
							|| Types.TIME == rsmd.getColumnType(i)
							|| Types.TIMESTAMP == rsmd.getColumnType(i)) {
						ColumnValue.append("timestamp '").append(value)
								.append("',");
					} else {
						ColumnValue.append(value).append(",");
					}
				}
			}
			isFirst = false;
			ColumnValueAll.append(ColumnValue).append(";");
		}
		// print(ColumnName, "ColumnName");
		// print(ColumnValueAll, "ColumnValueAll");
		StringBuffer cols[] = new StringBuffer[2];
		cols[0] = ColumnName;
		cols[1] = ColumnValueAll;
		return cols;
	}

	/**
	 * 获取列名和列值
	 * 
	 * @param fromDBType
	 *            源数据库类型
	 * @param querySql
	 *            查询SQL语句
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer[] getColumnNameAndValue(String fromDBType,
			String querySql) throws Exception {

		if (!isEmpty(querySql)) {
			rs = Executer.getInstance(fromDBType).ExecSeletSQL2(querySql);
		}
		return getColumnNameAndValue(rs);
	}

	/**
	 * 拼装Insert..To..Sql语句
	 * 
	 * @param toTabSchema
	 *            目标数据库tabSchema
	 * @param toTabName
	 *            目标数据库tabName
	 * @param ColumnNameAndValue
	 *            <p>
	 *            [0]列名 col0,col1...coln
	 *            <p>
	 *            <p>
	 *            [2]列值 val0,val1...valn;val0,val1...valn;
	 *            <p>
	 * @return
	 */
	private static String generateInsertSqlStr(String toTabSchema,
			String toTabName, StringBuffer[] ColumnNameAndValue) {
		// TODO InsertSql
		strBuffer = new StringBuffer();
		strBuffer.append("\n/********** ");
		strBuffer.append(DateUtils.getCurrentDateTime());
		strBuffer.append(" **********/\n");
		//
		StringBuffer ColumnName = ColumnNameAndValue[0];
		StringBuffer ColumnValueAll = ColumnNameAndValue[1];
		if (ColumnValueAll != null) {
			//
			StringBuffer title = new StringBuffer();
			title.append("INSERT INTO ");
			// 添加tabSchema.
			if (null != toTabSchema && !"".equals(toTabSchema)) {
				title.append(toTabSchema).append(".");
			}
			title.append(toTabName);
			title.append("(").append(ColumnName).append(")\n");
			//
			StringBuffer content = new StringBuffer();
			String[] values = ColumnValueAll.toString().split(";");
			for (int i = 0; i < values.length; i++) {
				content.append(title);
				content.append("VALUES (");
				content.append(values[i]);
				content.append(");\n");
			}
			strBuffer.append(content);
		}
		return strBuffer.toString();
	}

	/**
	 * @param fromDBType
	 *            源数据库类型
	 * @param toTabSchema
	 *            目标数据库tabSchema
	 * @param toTabName
	 *            目标数据库tabName
	 * @param querySql
	 *            查询SQL语句
	 * @return
	 * @throws Exception
	 */
	public static String generateInsertSqlStr(String fromDBType,
			String toTabSchema, String toTabName, String querySql)
			throws Exception {
		StringBuffer cols[] = new StringBuffer[2];
		cols = getColumnNameAndValue(fromDBType, querySql);
		return generateInsertSqlStr(toTabSchema, toTabName, cols);
	}

	/**
	 * @param schema
	 * @return
	 */
	private static boolean schemaIsEmpty(String schema) {
		boolean flag = false;
		if (schema == null || "".equals(schema)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @return
	 */
	public static List<BasicDynaBean> getTables(String dbType) {
		return getTables(dbType, null);
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @return
	 */
	public static List<BasicDynaBean> getTables(String dbType, String schema) {
		try {

			switch (getDBTypeInt(dbType)) {
			case 0:
				list = getMYSqlTables(schema);
				break;
			case 1:
				list = getMSSqlTables(schema);
				break;
			case 2:
				list = getOracleTables(schema);
				break;
			case 3:
				list = getDB2Tables(schema);
				break;
			default:
				log.debug("暂不支持" + dbType + "数据库,请检查!!");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

	/**
	 * 查询MySql数据库中的表信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getMYSqlTables(String schema)// int
																	// dbTypeInt,
			throws Exception {
		// "select id,name from sysobjects where xtype='U' order by name";
		strBuffer = new StringBuffer();
		strBuffer
				.append("select CONCAT(TRIM(t.TABLE_SCHEMA),'.',t.TABLE_NAME) as tabname");
		// strBuffer
		// .append("select "+dbTypeInt+" as dbtype,t.TABLE_SCHEMA as tabschema,t.TABLE_NAME as tabname");
		strBuffer.append("  from information_schema.TABLES t");
		if (!schemaIsEmpty(schema)) {
			strBuffer.append(" where t.TABLE_SCHEMA='" + schema + "'");
		}
		strBuffer.append(" order by t.TABLE_SCHEMA,t.TABLE_NAME");

		sql = strBuffer.toString();
		return getResultList("mysql", sql);
	}

	/**
	 * 查询MsSql数据库中的表信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getMSSqlTables(String schema)
			throws Exception {
		// "select id,name,info as colcount from sysobjects where xtype='U' order by name";
		strBuffer = new StringBuffer();
		strBuffer.append("select t.NAME,t.OBJECT_ID");
		strBuffer.append("  from sys.objects t");
		strBuffer.append(" where t.type='U'");
		strBuffer.append(" order by t.NAME");

		sql = strBuffer.toString();
		return getResultList("mssql", sql);
	}

	/**
	 * 查询Oracle数据库中的表信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(OWNER)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getOracleTables(String schema)
			throws Exception {
		// "select rownum as id,table_name as name from user_tables t order by table_name";
		strBuffer = new StringBuffer();
		/* 方法一 */
		// strBuffer.append("select t.OWNER,t.TABLE_NAME");
		// strBuffer.append("  from ALL_TABLES t");
		// strBuffer.append(" where t.OWNER=UPPER('" + schema + "')");
		// strBuffer.append(" order by t.TABLE_NAME");
		/* 方法二 */
		strBuffer.append("select " + schema
				+ " as tabschema,t.TABLE_NAME as tabname");
		strBuffer.append("  from USER_TABLES t");
		strBuffer.append(" order by t.TABLE_NAME");

		sql = strBuffer.toString();
		return getResultList("oracle", sql);
	}

	/**
	 * 查询DB2数据库中的表信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABSCHEMA)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getDB2Tables(String schema)
			throws Exception {
		// "select tableid as id,tabname as name,colcount from syscat.tables where DEFINER=USER order by tabname";
		strBuffer = new StringBuffer();
		strBuffer.append("select TRIM(t.TABSCHEMA)||'.'||t.TABNAME as tabname");
		strBuffer.append("  from SYSCAT.TABLES t where 1=1");
		if (!schemaIsEmpty(schema)) {
			strBuffer.append(" and t.TABSCHEMA=UPPER('" + schema + "')");
		}
		strBuffer.append("   and t.OWNERTYPE='U'");
		strBuffer.append(" order by t.TABSCHEMA,t.TABNAME");

		sql = strBuffer.toString();
		return getResultList("db2", sql);
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @param tableName
	 *            表名称(TABLE_NAME)
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<BasicDynaBean> getColumns(String dbType, String schema,
			String tabName) {
		try {
			if (isEmpty(schema) || isEmpty(tabName)) {
				log.debug("Schema或TableName为空,请检查!!");
			} else {
				switch (getDBTypeInt(dbType)) {
				case 0:
					list = getMYSqlColumns(schema, tabName);
					break;
				case 1:
					list = getMSSqlColumns(schema, tabName);
					break;
				case 2:
					list = getOracleColumns(schema, tabName);
					break;
				case 3:
					list = getDB2Columns(schema, tabName);
					break;
				default:
					log.debug("暂不支持" + dbType + "数据库,请检查!!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

	/**
	 * 查询MySql数据库表中的列信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @param tabName
	 *            表名称(TABLE_NAME)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getMYSqlColumns(String schema,
			String tabName) throws Exception {
		// "select a.id,a.name,b.name as typename from information_schema.syscolumns a left join information_schema.systypes b on a.xusertype=b.xusertype where a.id=object_id('"
		// + tabName.toUpperCase() + "') order by colorder";
		strBuffer = new StringBuffer();
		strBuffer
				.append("select t.TABLE_SCHEMA,t.TABLE_NAME,t.COLUMN_NAME,t.DATA_TYPE,t.COLUMN_TYPE,t.IS_NULLABLE,t.COLUMN_DEFAULT");
		strBuffer.append("  from information_schema.COLUMNS t where 1=1");
		strBuffer.append("   and t.TABLE_SCHEMA='" + schema + "'");
		strBuffer.append("   and t.TABLE_NAME='" + tabName + "'");
		strBuffer.append(" order by t.ORDINAL_POSITION");

		sql = strBuffer.toString();
		return getResultList("mysql", sql);
	}

	/**
	 * @param tabName
	 *            表名称(TABLE_NAME)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getMYSqlColumnsIsReturn(String tabName)
			throws Exception {
		// String sqlStr =
		// "select name as caption,name as property,'true' as isreturn,100 as width from information_schema.syscolumns a where a.id=object_id('"
		// + tabName.toUpperCase() + "') order by colorder";
		strBuffer = new StringBuffer();
		strBuffer
				.append("select UPPER(t.COLUMN_NAME) as caption,t.COLUMN_NAME as property,'true' as isreturn,85 as width");
		strBuffer.append("  from information_schema.COLUMNS t where 1=1");
		strBuffer.append("   and t.TABLE_NAME='" + tabName + "'");
		strBuffer.append(" order by t.ORDINAL_POSITION");
		sql = strBuffer.toString();
		return getResultList("mysql", sql);
	}

	/**
	 * 查询MsSql数据库表中的列信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABLE_SCHEMA)
	 * @param tabName
	 *            表名称(TABLE_NAME)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getMSSqlColumns(String schema,
			String tabName) throws Exception {
		strBuffer = new StringBuffer();
		strBuffer.append("select t.TABLENAME,t.Name");
		strBuffer.append("  from SYS.COLUMNS c, join SYS.TYPES t");
		strBuffer.append("    on c.system_type_id = t.system_type_id");
		strBuffer.append("   and c.object_id=object_id( N'" + tabName + "')");
		// strBuffer.append(" order by ");

		sql = strBuffer.toString();
		return getResultList("mssql", sql);
	}

	@SuppressWarnings("unused")
	private static List<BasicDynaBean> getColumnsNameListByGSql(String tabName)
			throws Exception {
		String sqlStr = "select a.id,a.name,b.name as typename from syscolumns a left join systypes b on a.xusertype=b.xusertype where a.id=object_id('"
				+ tabName.toUpperCase() + "') order by colorder";
		return getResultList("gsql", sqlStr);
	}

	private static List<BasicDynaBean> getColumnsNameListByGSqlIsReturn(
			String tabName) throws Exception {
		String sqlStr = "select UPPER(name) as caption,name as property,'true' as isreturn,85 as width from syscolumns a where a.id=object_id('"
				+ tabName.toUpperCase() + "') order by colorder";
		return getResultList("gsql", sqlStr);
	}

	/**
	 * 查询Oracle数据库表中的列信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(OWNER)
	 * @param tableName
	 *            表名称(TABLE_NAME)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getOracleColumns(String schema,
			String tableName) throws Exception {
		strBuffer = new StringBuffer();
		/* 方法一 */
		// strBuffer.append("select t.OWNER,t.TABLE_NAME,t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH,t.NULLABLE,t.DATA_DEFAULT");
		// strBuffer.append("  from ALL_TAB_COLUMNS t");
		// strBuffer.append(" where t.OWNER=UPPER('" + schema + "')");
		// strBuffer.append("   and t.TABLE_NAME=UPPER('" + tableName + "')");
		// strBuffer.append(" order by t.COLUMN_ID");
		/* 方法二 */
		strBuffer
				.append("select "
						+ schema
						+ " as OWNER,t.TABLE_NAME,t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH,t.NULLABLE,t.DATA_DEFAULT");
		strBuffer.append("  from USER_TAB_COLUMNS t");
		strBuffer.append(" where t.TABLE_NAME=UPPER('" + tableName + "')");
		strBuffer.append(" order by t.COLUMN_ID");

		sql = strBuffer.toString();
		return getResultList("oracle", sql);
	}

	@SuppressWarnings("unused")
	private static List<BasicDynaBean> getColumnsNameListByOracle10g(
			String table_name) throws Exception {
		String sqlStr = "select column_name as name,data_type as typename from user_tab_columns where table_name='"
				+ table_name.toUpperCase() + "'";
		return getResultList("oracle", sqlStr);
	}

	private static List<BasicDynaBean> getColumnsNameListByOracle10gIsReturn(
			String table_name) throws Exception {
		String sqlStr = "select UPPER(column_name) as caption,column_name as property,'true' as isreturn,85 as width from user_tab_columns where table_name='"
				+ table_name.toUpperCase() + "'";
		return getResultList("oracle", sqlStr);
	}

	/**
	 * 查询DB2数据库表中的列信息
	 * 
	 * @param schema
	 *            表所属的SCHEMA名称(TABSCHEMA)
	 * @param tableName
	 *            表名称(TABNAME)
	 * @return
	 * @throws Exception
	 */
	private static List<BasicDynaBean> getDB2Columns(String schema,
			String tableName) throws Exception {
		// "select colno as id,colname as name,typename from syscat.columns where tabname='"
		// + tableName.toUpperCase() + "'";
		strBuffer = new StringBuffer();
		strBuffer
				.append("select t.TABSCHEMA,t.TABNAME,t.COLNAME,t.TYPENAME,t.LENGTH,t.NULLS,t.DEFAULT");
		strBuffer.append("  from SYSCAT.COLUMNS t");
		strBuffer.append(" where t.TABSCHEMA=UPPER('" + schema + "')");
		strBuffer.append("   and t.TABNAME=UPPER('" + tableName + "')");
		strBuffer.append(" order by t.COLNO");

		sql = strBuffer.toString();
		return getResultList("db2", sql);
	}

	private static List<BasicDynaBean> getDB2ColumnsIsReturn(String tabname)
			throws Exception {

		strBuffer = new StringBuffer();
		strBuffer
				.append("select UPPER(colname) as caption,colname as property,'true' as isreturn,85 as width");
		strBuffer.append("  from syscat.columns where 1=1");
		strBuffer.append("   and tabname='" + tabname.toUpperCase() + "'");

		sql = strBuffer.toString();
		return getResultList("db2", sql);
	}

	public static List<BasicDynaBean> getColnumsNameListByIsReturn(
			String dbType, String tabName) throws Exception {
		if ("db2".equals(dbType))
			list = getDB2ColumnsIsReturn(tabName);
		else if ("oracle".equals(dbType))
			list = getColumnsNameListByOracle10gIsReturn(tabName);
		else if ("gsql".equals(dbType))
			list = getColumnsNameListByGSqlIsReturn(tabName);
		else if ("mysql".equals(dbType)) {
			list = getMYSqlColumnsIsReturn(tabName);
		}
		return list;
	}

	/**
	 * @param path
	 * @param sbInsert
	 */
	public static void saveFile(String path, String sbInsert) {
		// TODO SaveFile
		File file = null;
		if (path.length() > 0) {
			file = new File(path);
			if (file.isFile()) {
				log.info("文件" + file.getName() + "已经存在，确定要覆盖它吗");
				int option = JOptionPane.showConfirmDialog(null,
						"文件" + file.getName() + "已经存在，确定要覆盖它吗", "警告", 1, 3);
				if (option == 2) {
					log.info("创建insert语句被取消");
					return;
				}
				if (option == 0) {
					log.info("是");
				}
			}
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(path);
			fileWriter.write(sbInsert);
		} catch (IOException ex2) {
			log.error("保存insert语句失败");
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex3) {
				log.error("文件关闭失败", ex3);
			}
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex3) {
				log.error("文件关闭失败", ex3);
			}
		}
	}

	// ----------------------------------BEGIN1
	/**
	 * @param dbType
	 * @param filePath
	 * @param tables
	 * @param encodeType
	 * @param schema
	 * @param fileType
	 * @return
	 */
	public static boolean createFiles(String dbType, String filePath,
			String[] tables, List<String> encodeType, String schema,
			List<String> fileType) {
		try {
			createSQLFile(dbType, filePath, tables, encodeType, schema,
					fileType);
			// createImportScript(tables, filePath, encodeType);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param dbType
	 *            数据库类型dataSource
	 * @param filePath
	 *            E:/logs/
	 * @param tables
	 *            { "T_USER", "T_ROLE" }
	 * @param encodeTypes
	 *            [UTF-8, GBK]
	 * @param schema
	 *            mydb
	 * @param fileTypes
	 *            [SQL, CSV, EXCEL]
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void createSQLFile(String dbType, String filePath,
			String[] tables, List<String> encodeTypes, String schema,
			List<String> fileTypes) throws IOException, SQLException {

		for (int i = 0; i < encodeTypes.size(); i++) {
			String code = encodeTypes.get(i);
			for (int j = 0; j < tables.length; j++) {
				String table = tables[j];
				makeDataStr(dbType, table, schema, fileTypes, filePath, code);
			}
		}
	}

	/**
	 * @param dbType
	 * @param tableName
	 * @param schema
	 * @param fileTypes
	 * @param filePath
	 * @param encodeType
	 * @return
	 * @throws SQLException
	 */
	public static boolean makeDataStr(String dbType, String tableName,
			String schema, List<String> fileTypes, String filePath,
			String encodeType) throws SQLException {
		boolean flag = false;
		String sql = "select * from " + schema + "." + tableName;

		ResultSet rs = MyDBUtils.getMyDB(dbType).ExecSelectSql2(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columenCount = rsmd.getColumnCount();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 1; i <= columenCount; i++) {
			map = new HashMap<String, String>();
			map.put("name", rsmd.getColumnName(i));
			map.put("type", rsmd.getColumnTypeName(i));
			columns.add(map);
		}

		StringBuilder sqls = new StringBuilder();
		StringBuilder csvs = new StringBuilder();

		sqls.append("DELETE FROM " + tableName + " WHERE 1=1;\n");
		String csvHeader = "";
		String tableColumns;
		String splitFlag = "^$";
		while (rs.next()) {

			tableColumns = "";
			String columnKey = "";
			String columnValue = "";
			String columnValues = "";
			for (int i = 0; i < columns.size(); i++) {
				map = columns.get(i);
				columnKey = map.get("name");
				tableColumns += columnKey;
				columnValue = rs.getString(columnKey);
				String columType = map.get("type");
				// System.out.println(i + "=" + columnKey + "=" + columnValue
				// + "=" + columType);
				if ((columType.equals("VARCHAR"))
						|| (columType.equals("VARCHAR2"))
						|| (columType.equals("LONGVARCHAR"))
						|| (columType.equals("CHAR"))
						|| (columType.equals("CHARACTER"))
						|| (columType.equals("NCHAR"))
						|| (columType.equals("NVARCHAR"))
						|| (columType.equals("TEXT"))
						|| (columType.equals("NTEXT"))
						|| (columType.equals("XML"))
						|| (columType.equals("CLOB"))
						|| (columType.equals("BLOB"))
						|| (columType.equals("DATE"))
						|| (columType.equals("TIME"))
						|| (columType.equals("DATETIME"))
						|| (columType.equals("TIMESTAMP"))) {
					// if (columnValue == null
					// || "null".equalsIgnoreCase(columnValue)) {
					// columnValues += columnValue;
					// } else
					if ("'".equals(columnValue) && fileTypes.size() == 1
							&& fileTypes.contains("SQL")) {
						columnValues += columnValue.replaceAll("'", "''");
					} else {
						columnValues += splitFlag + columnValue + splitFlag;
					}
				} else if ((columType.equals("NULL"))
						|| (columType.equals("BOOLEAN"))
						|| (columType.equals("BIT"))
						|| (columType.equals("INT"))
						|| (columType.equals("INTEGER"))
						|| (columType.equals("BIGINT"))
						|| (columType.equals("SMALLINT"))
						|| (columType.equals("TINYINT"))
						|| (columType.equals("INT UNSIGNED"))
						|| (columType.equals("SMALLINT UNSIGNED"))
						|| (columType.equals("NUMBER"))
						|| (columType.equals("NUMERIC"))
						|| (columType.equals("DECIMAL"))
						|| (columType.equals("DOUBLE"))
						|| (columType.equals("FLOAT"))
						|| (columType.equals("REAL"))
						|| (columType.equals("MONEY"))
						|| (columType.equals("SMALLMONEY"))) {
					columnValues += columnValue;
				} else {
					columnValues += splitFlag + columnValue + splitFlag;
					System.out.println("未处理类型：  " + tableName + " : "
							+ map.toString());
				}
				if (i != columns.size() - 1) {
					tableColumns += ", ";
					columnValues = columnValues + ", ";
				}
			}
			// System.out.println(":::" + columnValues);
			if (fileTypes.contains("SQL")) {
				String vs = columnValues;
				vs = vs.replaceAll("\\^\\$null\\^\\$", "null");
				vs = vs.replaceAll("\\^\\$", "'");
				strBuffer = new StringBuffer();
				strBuffer.append("INSERT INTO " + tableName).append("(");
				strBuffer.append(tableColumns).append(") VALUES (");
				strBuffer.append(vs).append(");");
				strBuffer.append(SysConstants.UNIX_LINE_END);

				sqls.append(strBuffer);
			}
			if (fileTypes.contains("CSV")) {
				csvHeader = tableColumns;
				String vs = columnValues;
				vs = vs.replaceAll("\\^\\$null\\^\\$", "\"\"");
				vs = vs.replaceAll("\\^\\$", "\"");
				csvs.append(vs).append(SysConstants.UNIX_LINE_END);
			}
		}

		for (String fileType : fileTypes) {
			StringBuilder content = new StringBuilder();
			String detailFilePath = "";
			String dt = DateUtils.getToday("yyyyMMddHHmmss");
			if (fileType.equals("SQL")) {
				content = sqls;
				detailFilePath = filePath + "/" + dbType + "/" + schema + "/"
						+ encodeType + "/SQL/" + tableName + "_" + dt + ".SQL";
			} else if (fileType.equals("CSV")) {
				content.append(csvHeader + SysConstants.UNIX_LINE_END);
				content.append(csvs);
				detailFilePath = filePath + "/" + dbType + "/" + schema + "/"
						+ encodeType + "/CSV/" + tableName + "_" + dt + ".CSV";
			}
			try {
				writeFile(detailFilePath, content.toString(), encodeType);
				flag = true;
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
			}
		}

		return flag;
	}

	// /**
	// * 导入脚本
	// *
	// * @param tables
	// * @param filePath
	// * @param encodeTypes
	// * @throws IOException
	// */
	// private static void createImportScript(String[] tables, String filePath,
	// List<String> encodeTypes) throws IOException {
	// StringBuilder scripts = new StringBuilder();
	// for (String code : encodeTypes) {
	// String[] arrayOfString = tables;
	// int j = tables.length;
	// for (int i = 0; i < j; i++) {
	// String table = arrayOfString[i];
	// scripts.append("db2 -tvf " + table + ".SQL;\n");
	// }
	// writeFile(filePath + "/" + code + "/importAll.sh",
	// scripts.toString(), code);
	// }
	// }

	/**
	 * @param filePath
	 * @param content
	 * @param encode
	 * @throws IOException
	 */
	private static void writeFile(String filePath, String content, String encode)
			throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream outer = new FileOutputStream(file);
		OutputStreamWriter outWriter = new OutputStreamWriter(outer, encode);
		outWriter.write(content);
		outWriter.flush();
		outWriter.close();
		outer.close();
	}

	/**
	 * @param schema
	 * @param dbType
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getTableList(String schema, String dbType)
			throws SQLException {
		String sql = "";
		if (dbType.equals(SysConstants.DB_MYSQL)) {
			sql = SysConstants.DB_MYSQL_TABLE_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_MSSQL_2000)
				|| dbType.equals(SysConstants.DB_MSSQL_2005)) {
			sql = SysConstants.DB_MSSQL_TABLE_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_ORACLE_10G)
				|| dbType.equals(SysConstants.DB_ORACLE_11G)) {
			sql = SysConstants.DB_ORACLE_TABLE_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_DB2)) {
			sql = SysConstants.DB_DB2_TABLE_LIST_SQL;
		}

		List<String> tables = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			tables = new ArrayList<String>();
			DBInformation dbInfo = MyDBUtils.readDBInfoBy(dbType);
			stmt = MyDBUtils.getMyDB(dbInfo).getConn().prepareStatement(sql);
			stmt.setString(1, schema);
			rs = stmt.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		return tables;
	}

	/**
	 * @param dbType
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getSchemaList(String dbType) throws SQLException {
		String sql = "";
		if (dbType.equals(SysConstants.DB_MYSQL)) {
			sql = SysConstants.DB_MYSQL_SCHEMA_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_MSSQL_2000)
				|| dbType.equals(SysConstants.DB_MSSQL_2005)) {
			sql = SysConstants.DB_MSSQL_SCHEMA_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_ORACLE_10G)
				|| dbType.equals(SysConstants.DB_ORACLE_11G)) {
			sql = SysConstants.DB_ORACLE_SCHEMA_LIST_SQL;
		} else if (dbType.equals(SysConstants.DB_DB2)) {
			sql = SysConstants.DB_DB2_SCHEMA_LIST_SQL;
		}

		List<String> schemas = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			schemas = new ArrayList<String>();
			DBInformation dbInfo = MyDBUtils.readDBInfoBy(dbType);
			stmt = MyDBUtils.getMyDB(dbInfo).getConn().prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				schemas.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		return schemas;
	}

	// ----------------------------------END1

	// ----------------------------------Begig2
	/**
	 * 
	 * 获得数据 二维数组
	 * 
	 * @param jdbcDataSource
	 *            数据源
	 * @param tableName
	 *            表
	 * @param columnName
	 *            导出字段列名
	 * @return data 数据库中对应的数据
	 * 
	 * @throws SQLException
	 * @throws SystemException
	 * 
	 */
	private static String[][] getData(MyDBUtils db, String tableName,
			String[] columnName) throws SystemException, SQLException {
		String[][] data = new String[0][0];
		try {
			StringBuffer buffercount = new StringBuffer();
			buffercount.append("select count(1) from ");
			buffercount.append(tableName);
			buffercount.append(" where 1=1 ");
			ResultSet rs = db.ExecSelectSql2(buffercount.toString());
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt(1);
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			if (null == columnName || columnName.length == 0) {
				buffer.append("*");
			} else {
				for (String str : columnName) {
					buffer.append(str);
					buffer.append(",");
				}
				buffer.deleteCharAt(buffer.toString().length() - 1);
			}
			buffer.append(" from ");
			buffer.append(tableName);
			buffer.append(" where 1=1");

			rs = db.ExecSelectSql2(buffer.toString());
			// 列对象
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			// 列数目
			int colCount = resultSetMetaData.getColumnCount();
			// 行与列建数组
			data = new String[rowCount][colCount];
			// 填充数组
			int row = 0;
			while (rs.next()) {
				for (int col = 0; col < colCount; col++) {
					// if (row < 1) {
					// String cn = resultSetMetaData.getColumnLabel(col + 1);
					// System.out.println(cn);
					// }
					data[row][col] = rs.getString(col + 1);
				}
				row++;
			}
		} catch (Exception ex) {
			throw new SystemException();
		}
		return data;
	}

	/**
	 * 备份数据
	 * 
	 * @param jdbcDataSource
	 *            数据源
	 * @param tableName
	 *            表
	 * @param columnName
	 *            导出字段列名
	 * @param dirString
	 *            目录
	 * @param fileString
	 *            文件
	 * @return result 操作结果信息
	 * @throws IOException
	 * @throws SystemException
	 * @throws Exception
	 * 
	 */
	public static int backupTable(MyDBUtils db, String tableName,
			String[] columnName, String dirString, String fileString)
			throws IOException, SystemException {
		int rs = 0;
		String[][] data = null;
		File catRoot = new File(dirString);
		if (!catRoot.exists()) {
			catRoot.mkdirs();
		}
		File file = new File(catRoot, fileString);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			// 数据
			data = getData(db, tableName, columnName);
			for (int row = 0; row < data.length; row++) {
				for (int col = 0; col < data[0].length; col++) {
					// 如果数据为空则加空格
					if (data[row][col] == null) {
						data[row][col] = "null";
					} else if (data[row][col].length() == 0) {
						data[row][col] = " ";
					}
					if (col == data[0].length - 1) {
						// 一条记录表示一行所以要换行
						writer.write(data[row][col] + "\n");
					} else {
						// 水平制表符
						writer.write(data[row][col] + ",");
					}
				}
			}
			rs = 1;

		} catch (Exception e) {
			throw new SystemException();
		} finally {
			writer.close();
		}
		return rs;

	}

	/**
	 * 数据填充表
	 * 
	 * @param jdbcDataSource
	 *            数据源
	 * @param tableName
	 *            表名
	 * @param columnName
	 *            字段名
	 * @param data
	 *            二维数组数据
	 * @param oracleString
	 *            是否为oracle
	 * 
	 * @return 操作结果信息
	 * @throws SQLException
	 * 
	 */
	private static int setData(MyDBUtils db, String tableName,
			String[] columnName, String[][] data) throws SystemException,
			SQLException {
		int result = 0;
		String selectString = getSelectString(tableName, columnName);
		String insertString = getInsertString(tableName, columnName);
		Connection con = null;
		try {
			con = db.getConn();

			con.setAutoCommit(false);

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(selectString);

			ResultSetMetaData resultSetMetaData = rs.getMetaData();

			int colCount = resultSetMetaData.getColumnCount();

			PreparedStatement pstmt = con.prepareStatement(insertString);
			// 日期转换类
			java.text.DateFormat dateLongFormat = java.text.DateFormat
					.getDateTimeInstance();
			java.text.DateFormat dateShortFormat = java.text.DateFormat
					.getDateInstance();
			Timestamp timeStamp = null;
			for (int row = 0; row < data.length; row++) {
				int index = 0;
				for (int col = 0; col < colCount; col++) {
					String columnTypeName = resultSetMetaData
							.getColumnTypeName(col + 1).toLowerCase();
					// 如果要更多可以添加
					if (columnTypeName.equals("varchar")
							| columnTypeName.equals("char")
							| columnTypeName.equals("varchar2")
							| columnTypeName.equals

							("longvarchar")) {
						pstmt.setString(index + 1, data[row][col]);
					}
					// int
					else if (columnTypeName.equals("int")
							| columnTypeName.equals("long")
							| columnTypeName.equals("number")) {
						if (data[row][col].equals("null")) {
							pstmt.setString(index + 1, null);
						} else {
							pstmt.setInt(index + 1,
									Integer.parseInt(data[row][col]));
						}
					}
					// float
					else if (columnTypeName.equals("float")
							| columnTypeName.equals("decimal")) {
						pstmt.setDouble(index + 1,
								Double.parseDouble(data[row][col]));
					}
					// timestamp
					else if (columnTypeName.equals("timestamp")
							| columnTypeName.equals("datetime")
							| columnTypeName.equals("date")) {

						if (data[row][col].equals("null")) {
							timeStamp = null;
						} else if (data[row][col].length() > 10) {
							timeStamp = new Timestamp(dateLongFormat.parse(
									data[row][col]).getTime());

						} else {
							timeStamp = new Timestamp(dateShortFormat.parse(
									data[row][col]).getTime());
						}
						pstmt.setTimestamp(index + 1, timeStamp);

					}
					// image
					else if (columnTypeName.equals("image")) {
						if (data[row][col] == null
								|| data[row][col].length() <= 4) {
							pstmt.setBytes(index + 1, null);
						} else {
							pstmt.setBytes(index + 1, data[row][col].getBytes());
						}

					} else {
						log.error("**************other sqltype:+"
								+ columnTypeName + "***********");
					}
					index++;
				}
				pstmt.execute();
			}
			con.commit();
			result = 1;
		} catch (Exception ex) {
			con.rollback();
			data = new String[0][0];
			throw new SystemException();
		} finally {
			con.close();
		}
		return result;
	}

	/**
	 * 
	 * 恢复数据
	 * 
	 * @param jdbcDataSource
	 *            数据源
	 * @param tableName
	 *            表
	 * @param columnName
	 *            导出字段列名
	 * @param backPath
	 *            导入路径
	 * @param oracleString
	 *            数据库是否是oracle
	 * @return result 操作结果信息
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 * 
	 */
	public static int restoreFiles(MyDBUtils db, String tableName,
			String[] columnName, String backPath) throws SystemException,
			SQLException, IOException {
		int result = 0;
		// 数组
		String[][] data = null;
		File inFile = new File(backPath);
		List<String> list = new ArrayList<String>();
		BufferedReader bufferedReader = null;
		bufferedReader = new BufferedReader(new FileReader(inFile));
		while (bufferedReader.ready()) {
			// 一条记录
			list.add(bufferedReader.readLine());
		}
		bufferedReader.close();
		if (list.size() > 0) {
			// 行数目
			int rowLength = list.size();
			String tempStr = (String) list.get(0);
			StringTokenizer stringToken = new StringTokenizer(tempStr, ",");
			// 列数目
			int colLength = stringToken.countTokens();
			// 行和列创建数组
			data = new String[rowLength][colLength];
			for (int row = 0; row < rowLength; row++) {
				stringToken = new StringTokenizer((String) list.get(row), ",");
				for (int col = 0; col < colLength; col++) {
					tempStr = stringToken.nextToken();
					tempStr.replace('\n', ' ');
					tempStr = tempStr.trim();
					data[row][col] = tempStr;
				}

			}
			// 填充数据
			result = setData(db, tableName, columnName, data);
		}

		return result;

	}

	/**
	 * 构建insert语句
	 * 
	 * @param columnName
	 *            字段名 tableName 表名oracleString是否为oralce
	 * @return
	 */
	public static String getInsertString(String tableName, String[] columnName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into ");
		buffer.append(tableName);
		if (null != columnName && columnName.length > 0) {
			buffer.append("(");
			for (String str : columnName) {
				buffer.append(str);
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.toString().length() - 1);
			buffer.append(")");
		}
		buffer.append(" values(");
		for (int i = 0; i < columnName.length; i++) {
			buffer.append("?");
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.toString().length() - 1);
		buffer.append(")");
		return buffer.toString();
	}

	/**
	 * 构建select语句
	 * 
	 * @param columnName
	 *            字段名 tableName 表名
	 * @return
	 */
	public static String getSelectString(String tableName, String[] columnName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		if (null == columnName || columnName.length == 0) {
			buffer.append("*");
		} else {
			for (String parmName : columnName) {
				buffer.append(parmName);
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.toString().length() - 1);
		}
		buffer.append(" from");
		buffer.append(" " + tableName);
		return buffer.toString();
	}

	// ----------------------------------End2
	public String FieldToStr(String paramString) {
		String str = "NULL";
		if (paramString != null)
			paramString = paramString.trim();
		if (paramString != null)
			str = "'" + paramString + "'";
		return str;
	}

	public String FieldToStr(Double paramDouble) {
		String str = "NULL";
		if (paramDouble != null)
			str = paramDouble.toString();
		if (str.charAt(0) == '-')
			str = '(' + str + ')';
		return str;
	}

	public String FieldToStr(Integer paramInteger) {
		String str = "NULL";
		if (paramInteger != null)
			str = paramInteger.toString();
		if (str.charAt(0) == '-')
			str = '(' + str + ')';
		return str;
	}

	public String FieldToStr(Object paramObject, boolean paramBoolean) {
		String str1 = "NULL";
		if (paramObject != null) {
			str1 = paramObject.toString();
			if ((paramObject instanceof String)) {
				str1 = str1.trim();
				if (str1 != null)
					str1 = "'" + str1 + "'";
			} else if ((paramObject instanceof Timestamp)) {
				String str2 = "yyyy-MM-dd HH:mm:ss";
				String str3 = "yyyy-mm-dd HH24:MI:SS";
				if (!paramBoolean) {
					str2 = "yyyy-MM-dd";
					str3 = "yyyy-mm-dd";
				}
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
						str2);
				str1 = localSimpleDateFormat.format((Timestamp) paramObject);
				str1 = "to_Date('" + str1 + "', '" + str3 + "')";
			} else if ((((paramObject instanceof Integer)) || ((paramObject instanceof Double)))
					&& (str1.charAt(0) == '-')) {
				str1 = '(' + str1 + ')';
			}
		}
		return str1;
	}

	public String FieldToStr2(String paramString) throws Exception {
		Object localObject = null;
		try {
			paramString = paramString.trim();
			paramString = "get" + paramString.substring(0, 1).toUpperCase()
					+ paramString.substring(1).toLowerCase();
			Method localMethod = getClass().getMethod(paramString, null);
			localObject = localMethod.invoke(this, null);
		} catch (Exception localException) {
			throw new Exception("不能获取方法“" + paramString + "”的值，因为："
					+ localException.toString());
		}
		return FieldToStr(localObject, true);
	}
}
