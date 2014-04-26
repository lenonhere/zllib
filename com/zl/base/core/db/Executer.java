package com.zl.base.core.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.zl.common.Performance;

public class Executer {
	static Logger logger = Logger.getLogger(Executer.class);
	private static DBConnectionManager connMgr = null;
	private static String DataSouce = null;
	private static Executer db = null;

	/**
	 * 设置DataSource数据源,默认为idb
	 */
	private static void setDataSouce() {
		try {
			// 读取数据库配制文件: db.properties
			ResourceBundle resource = ResourceBundle.getBundle("db");
			String ds = resource.getString("DataSource");
			if (ds != null && ds != "") {
				setDataSouce(ds);
			}
		} catch (Exception e) {
			logger.error("没有找到配置文件:" + db + ".properties", e);
		}
	}

	/**
	 * 设置DataSource数据源
	 *
	 * @param ds
	 */
	public static void setDataSouce(String ds) {
		DataSouce = ds.toLowerCase().trim();
	}

	/**
	 * 使用默认的DataSource数据源
	 *
	 * @return
	 */
	public static Executer getInstance() {
		if (db == null) {
			db = new Executer();
		}
		setDataSouce();
		connMgr = DBConnectionManager.getInstance(DataSouce);
		return db;
	}

	/**
	 * 使用指定的数据源
	 *
	 * @param dataSource
	 * @return
	 */
	public static Executer getInstance(String dataSource) {
		if (db == null) {
			db = new Executer();
		}
		setDataSouce(dataSource);
		connMgr = DBConnectionManager.getInstance(DataSouce);
		return db;
	}

	/**
	 * @return Connection
	 * @throws Exception
	 */
	private Connection getConn() throws Exception {
		Connection conn = null;
		try {
			if (conn == null) {
				if (DataSouce == null) {
					setDataSouce();
				}
				connMgr = DBConnectionManager.getInstance();
				conn = connMgr.getConnection(DataSouce);
			}
		} catch (Exception e) {
			throw new Exception("getConn Error :" + e.getMessage());
		}
		return conn;
	}

	/**
	 * @return
	 */
	protected Connection getConnection() {
		try {
			return getConn();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @param conn
	 */
	private void closeConn(Connection conn) {
		connMgr.freeConnection(DataSouce, conn);
	}

	/**
	 * @param conn
	 */
	protected void closeConnection(Connection conn) {
		closeConn(conn);
	}

	/**
	 * @param paramStatement
	 */
	protected void closeStatement(Statement paramStatement) {
		if (paramStatement != null)
			try {
				paramStatement.close();
			} catch (SQLException e) {
			}
	}

	/**
	 * @param paramCallableStatement
	 * @param paramArrayOfSqlParam
	 * @throws Exception
	 */
	protected void setParameters(CallableStatement paramCallableStatement,
			SqlParam[] paramArrayOfSqlParam) throws Exception {
		try {
			paramCallableStatement.registerOutParameter(1, 4);
			if (paramArrayOfSqlParam == null)
				return;
			for (int i = 0; i < paramArrayOfSqlParam.length; i++)
				if (paramArrayOfSqlParam[i].getType() == 1) {
					paramCallableStatement.registerOutParameter(i + 2,
							paramArrayOfSqlParam[i].getSqlType());
				} else {
					String str = paramArrayOfSqlParam[i].getValue();
					if ("^^^###".equals(str))
						paramCallableStatement.setNull(i + 2,
								paramArrayOfSqlParam[i].getSqlType());
					else
						switch (paramArrayOfSqlParam[i].getSqlType()) {
						case 1:
						case 12:
							paramCallableStatement.setString(i + 2, str);
							break;
						case 4:
							if ("".equals(str))
								paramCallableStatement.setInt(i + 2, 0);
							else
								paramCallableStatement.setInt(i + 2,
										Integer.parseInt(str));
							break;
						case 6:
							if ("".equals(str))
								paramCallableStatement.setFloat(i + 2, 0.0F);
							else
								paramCallableStatement.setFloat(i + 2,
										Float.parseFloat(str));
							break;
						case 8:
							if ("".equals(str))
								paramCallableStatement.setDouble(i + 2, 0.0D);
							else
								paramCallableStatement.setDouble(i + 2,
										Double.parseDouble(str));
							break;
						case 3:
							if ("".equals(str))
								paramCallableStatement.setBigDecimal(i + 2,
										new BigDecimal("0"));
							else
								paramCallableStatement.setBigDecimal(i + 2,
										new BigDecimal(str));
							break;
						case 2:
						case 5:
						case 7:
						case 9:
						case 10:
						case 11:
						default:
							paramCallableStatement.setString(i + 2, str);
						}
				}
		} catch (SQLException localSQLException) {
			throw new Exception(localSQLException.toString());
		}
	}

	/**
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public SqlReturn ExecSeletSQL(String sql) throws Exception {
		SqlReturn localSqlReturn = new SqlReturn();
		Connection localConnection = null;
		try {
			localConnection = getConn();
			Statement localStatement = localConnection.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(sql);
			localSqlReturn.addResultSet(localResultSet);
		} catch (Exception e) {
			throw new Exception("ExecSQL Error :" + e.getMessage());
		} finally {
			closeConn(localConnection);
		}
		return localSqlReturn;
	}

	/**
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet ExecSeletSQL2(String sql) throws Exception {
		Connection localConnection = null;
		ResultSet localResultSet = null;
		try {
			localConnection = getConn();
			Statement localStatement = localConnection.createStatement();
			localResultSet = localStatement.executeQuery(sql);
		} catch (Exception e) {
			throw new Exception("ExecSQL Error :" + e.getMessage());
		} finally {
			closeConn(localConnection);
		}
		return localResultSet;
	}

	/**
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public SqlReturn ExecUpdateSQL(String sql) throws Exception {
		SqlReturn localSqlReturn = new SqlReturn();
		Connection localConnection = null;
		try {
			localConnection = getConn();
			Statement localStatement = localConnection.createStatement();
			localSqlReturn.setAffectRowCount(localStatement.executeUpdate(sql));
		} catch (Exception localException) {
			throw new Exception("ExecSQL Error :" + localException.getMessage());
		} finally {
			closeConn(localConnection);
		}
		return localSqlReturn;
	}

	/**
	 * @param sql
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public SqlReturn ExecSeletSQL(String sql, Connection conn) throws Exception {
		SqlReturn localSqlReturn = new SqlReturn();
		try {
			Statement localStatement = conn.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(sql);
			localSqlReturn.addResultSet(localResultSet);
		} catch (Exception localException) {
			throw new Exception("ExecSQL Error :" + localException.getMessage());
		}
		return localSqlReturn;
	}

	/**
	 * @param paramString
	 * @param paramConnection
	 * @return
	 * @throws Exception
	 */
	public SqlReturn ExecUpdateSQL(String sql, Connection conn)
			throws Exception {
		SqlReturn localSqlReturn = new SqlReturn();
		try {
			Statement localStatement = conn.createStatement();
			localSqlReturn.setAffectRowCount(localStatement.executeUpdate(sql));
		} catch (Exception localException) {
			throw new Exception("ExecSQL Error :" + localException.getMessage());
		}
		return localSqlReturn;
	}

	/**
	 * @param paramString
	 * @param paramArrayOfSqlParam
	 * @return
	 * @throws Exception
	 */
	public SqlReturn ExecStoreProcedure(String paramString,
			SqlParam[] paramArrayOfSqlParam) throws Exception {
		String str1 = "";
		try {
			String str2 = "";
			for (int i = 0; i < paramArrayOfSqlParam.length; i++) {
				if (str2 == "") {
					str2 = "?";
					str1 = str1 + "'" + paramArrayOfSqlParam[i].getValue() + "'";
				} else {
					str2 = str2 + ",?";
					str1 = str1 + ",'" + paramArrayOfSqlParam[i].getValue()
							+ "'";
				}
			}
			// 执行存储过程性能测试Begin
			Performance.getInstance("Exec " + paramString + " " + str1);
			Performance.Start();
			str2 = "(" + str2 + ")";
			str2 = "{?=call " + paramString + " " + str2 + "}";
			System.out.println("Executer.ExecStoreProcedure()::" + str2);
			Executer localExecuter = getInstance();
			SqlReturn localSqlReturn = localExecuter.StoreProcedure(str2,
					paramArrayOfSqlParam);
			localSqlReturn.toString();
			Performance.End();
			// 执行存储过程性能测试End
			return localSqlReturn;
		} catch (Exception e) {
			throw new Exception("ExecStoreProcedure Error :<Exec "
					+ paramString + " " + str1 + ">" + e.getMessage());
		}
	}

	/**
	 * @param paramString
	 * @param paramArrayOfSqlParam
	 * @return
	 * @throws Exception
	 */
	private SqlReturn StoreProcedure(String paramString,
			SqlParam[] paramArrayOfSqlParam) throws Exception {
		SqlReturn localSqlReturn = new SqlReturn();
		Connection localConnection = null;
		try {
			localConnection = getConn();
			CallableStatement localCallableStatement = localConnection
					.prepareCall(paramString);
			SetParam(localCallableStatement, paramArrayOfSqlParam);
			getRs(localCallableStatement, localSqlReturn);
			getOutParameters(localCallableStatement, paramArrayOfSqlParam,
					localSqlReturn);
		} catch (Exception localException) {
			throw new Exception("StoreProcedure Error :"
					+ localException.getMessage());
		} finally {
			closeConn(localConnection);
		}
		return localSqlReturn;
	}

	/**
	 * @param paramCallableStatement
	 * @param paramArrayOfSqlParam
	 * @throws Exception
	 */
	private void SetParam(CallableStatement paramCallableStatement,
			SqlParam[] paramArrayOfSqlParam) throws Exception {
		try {
			paramCallableStatement.registerOutParameter(1, 4);
			if (paramArrayOfSqlParam != null)
				for (int i = 0; i < paramArrayOfSqlParam.length; i++)
					if (paramArrayOfSqlParam[i].getType() == 0) {
						if (paramArrayOfSqlParam[i].getValue().equals("^^^###"))
							paramCallableStatement.setNull(i + 2,
									paramArrayOfSqlParam[i].getSqlType());
						else
							switch (paramArrayOfSqlParam[i].getSqlType()) {
							case 1:
							case 12:
								paramCallableStatement.setString(i + 2,
										paramArrayOfSqlParam[i].getValue());
								break;
							case -7:
							case -6:
							case -5:
							case 4:
								if (paramArrayOfSqlParam[i].getValue()
										.equals(""))
									paramCallableStatement.setInt(i + 2, 0);
								else
									paramCallableStatement
											.setInt(i + 2,
													Integer.parseInt(paramArrayOfSqlParam[i]
															.getValue()));
								break;
							case 3:
							case 6:
								if (paramArrayOfSqlParam[i].getValue()
										.equals(""))
									paramCallableStatement
											.setFloat(i + 2, 0.0F);
								else
									paramCallableStatement
											.setFloat(
													i + 2,
													Float.parseFloat(paramArrayOfSqlParam[i]
															.getValue()));
								break;
							case 91:
								paramCallableStatement.setString(i + 2,
										paramArrayOfSqlParam[i].getValue());
								break;
							default:
								paramCallableStatement.setString(i + 2,
										paramArrayOfSqlParam[i].getValue());
								break;
							}
					} else
						paramCallableStatement.registerOutParameter(i + 2,
								paramArrayOfSqlParam[i].getSqlType());
		} catch (Exception localException) {
			throw new Exception("SetParam Error :"
					+ localException.getMessage());
		}
	}

	/**
	 * @param paramCallableStatement
	 * @param paramArrayOfSqlParam
	 * @param paramSqlReturn
	 * @throws Exception
	 */
	protected void getOutParameters(CallableStatement paramCallableStatement,
			SqlParam[] paramArrayOfSqlParam, SqlReturn paramSqlReturn)
			throws Exception {
		try {
			paramSqlReturn.addOutputParam(Integer
					.toString(paramCallableStatement.getInt(1)));
			SqlParam localSqlParam = null;
			for (int i = 0; i < paramArrayOfSqlParam.length; i++) {
				localSqlParam = paramArrayOfSqlParam[i];
				if (localSqlParam.getType() != 0)
					switch (localSqlParam.getSqlType()) {
					case 1:
					case 12:
						paramSqlReturn.addOutputParam(paramCallableStatement
								.getString(i + 2));
						break;
					case -7:
					case -6:
					case -5:
					case 4:
						paramSqlReturn
								.addOutputParam(Integer
										.toString(paramCallableStatement
												.getInt(i + 2)));
						break;
					case 3:
					case 6:
						paramSqlReturn
								.addOutputParam(Integer
										.toString(paramCallableStatement
												.getInt(i + 2)));
						break;
					case 91:
						paramSqlReturn.addOutputParam(paramCallableStatement
								.getString(i + 2));
						break;
					default:
						paramSqlReturn.addOutputParam(paramCallableStatement
								.getString(i + 2));
					}
			}
		} catch (Exception localException) {
			throw new Exception("getOutParameters Error:"
					+ localException.getMessage());
		}
	}

	/**
	 * @param paramCallableStatement
	 * @param paramSqlReturn
	 * @throws Exception
	 */
	protected void getRs(CallableStatement paramCallableStatement,
			SqlReturn paramSqlReturn) throws Exception {
		boolean bool = false;
		try {
			for (bool = paramCallableStatement.execute(); bool; bool = paramCallableStatement
					.getMoreResults()) {
				ResultSet localResultSet = paramCallableStatement
						.getResultSet();
				paramSqlReturn.addResultSet(localResultSet);
			}
		} catch (Exception localException) {
			throw new Exception("getRs Error :" + localException.getMessage());
		}
	}
}
