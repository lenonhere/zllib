package com.zl.base.core.db;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.zl.exception.DbException;

public class CommonExecuter {
	public static final String NULL = "^^^###";
	private static CommonExecuter db = null;

	private String DataSouce = "idb";

	private DBConnectionManager connMgr = DBConnectionManager.getInstance();

	static Logger logger = Logger.getLogger(CommonExecuter.class.getName());

	public Connection getConnection() {
		try {
			return getConn();
		} catch (DbException ex) {
		}
		return null;
	}

	public Connection getConnection(String dynaDataSource) {
		try {
			return getConn(dynaDataSource);
		} catch (DbException ex) {
		}
		return null;
	}

	protected void closeConnection(Connection conn) {
		closeConn(conn);
	}

	protected void closeConnection(Connection conn, String dynaDataSource) {
		closeConn(conn, dynaDataSource);
	}

	protected void setParameters(CallableStatement cstmt, SqlParam[] params)
			throws DbException {
		try {
			cstmt.registerOutParameter(1, 4);
			if (params == null) {
				return;
			}

			int OFFSET = 2;

			for (int i = 0; i < params.length; i++)
				if (params[i].getType() == 1) {
					cstmt.registerOutParameter(i + 2, params[i].getSqlType());
				} else {
					String currParam = params[i].getValue();

					if ("^^^###".equals(currParam)) {
						cstmt.setNull(i + 2, params[i].getSqlType());
					} else {
						switch (params[i].getSqlType()) {
						case 1:
						case 12:
							cstmt.setString(i + 2, currParam);
							break;
						case 4:
							if ("".equals(currParam))
								cstmt.setInt(i + 2, 0);
							else {
								cstmt.setInt(i + 2, Integer.parseInt(currParam));
							}
							break;
						case 6:
							if ("".equals(currParam))
								cstmt.setFloat(i + 2, 0.0F);
							else {
								cstmt.setFloat(i + 2,
										Float.parseFloat(currParam));
							}
							break;
						case 8:
							if ("".equals(currParam))
								cstmt.setDouble(i + 2, 0.0D);
							else {
								cstmt.setDouble(i + 2,
										Double.parseDouble(currParam));
							}
							break;
						case 3:
							if ("".equals(currParam))
								cstmt.setBigDecimal(i + 2, new BigDecimal("0"));
							else {
								cstmt.setBigDecimal(i + 2, new BigDecimal(
										currParam));
							}
							break;
						case 2:
						case 5:
						case 7:
						case 9:
						case 10:
						case 11:
						default:
							cstmt.setString(i + 2, currParam);
						}
					}
				}
		} catch (SQLException e) {
			throw new DbException(e.toString());
		}

	}

	public static CommonExecuter getInstance() {
		if (db == null) {
			db = new CommonExecuter();
		}
		return db;
	}

	public void setDataSouce(String value) {
		this.DataSouce = value.toLowerCase().trim();
	}

	public String getDataSouce() {
		return this.DataSouce;
	}

	public SqlReturn ExecSeletSQL(String sSQL) throws DbException {
		SqlReturn sq = new SqlReturn();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);
			sq.addResultSet(rs);
		} catch (Exception e) {
			throw new DbException("ExecSQL Error :" + e.getMessage());
		} finally {
			closeStatement(stmt);
			closeConn(conn);
		}
		return sq;
	}

	public SqlReturn ExecUpdateSQL(String sSQL) throws DbException {
		SqlReturn sq = new SqlReturn();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			sq.setAffectRowCount(stmt.executeUpdate(sSQL));
		} catch (Exception e) {
			throw new DbException("ExecSQL Error :" + e.getMessage());
		} finally {
			closeStatement(stmt);
			closeConn(conn);
		}
		return sq;
	}

	public SqlReturn ExecSeletSQL(String sSQL, Connection conn)
			throws DbException {
		SqlReturn sq = new SqlReturn();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);
			sq.addResultSet(rs);
		} catch (Exception e) {
			throw new DbException("ExecSQL Error :" + e.getMessage());
		}
		return sq;
	}

	public SqlReturn ExecUpdateSQL(String sSQL, Connection conn)
			throws DbException {
		SqlReturn sq = new SqlReturn();
		try {
			Statement stmt = conn.createStatement();
			sq.setAffectRowCount(stmt.executeUpdate(sSQL));
		} catch (Exception e) {
			throw new DbException("ExecSQL Error :" + e.getMessage());
		}
		return sq;
	}

	public SqlReturn ExecStoreProcedure(String spName, SqlParam[] params)
			throws DbException {
		String sparams = "";
		try {
			String statement = "";
			for (int i = 0; i < params.length; i++) {
				if (statement == "") {
					statement = "?";
					sparams = sparams + "'" + params[i].getValue() + "'";
				} else {
					statement = statement + ",?";
					sparams = sparams + ",'" + params[i].getValue() + "'";
				}

			}

			statement = "(" + statement + ")";
			statement = "{?=call " + spName + " " + statement + "}";
			CommonExecuter db = getInstance();
			return db.StoreProcedure(statement, params);
		} catch (Exception e) {
			throw new DbException("ExecStoreProcedure Error :<Exec " + spName
					+ " " + sparams + ">" + e.getMessage());
		}
	}

	private SqlReturn StoreProcedure(String statement, SqlParam[] params)
			throws DbException {
		SqlReturn sr = new SqlReturn();
		Connection conn = null;
		CallableStatement sp = null;
		try {
			conn = getConn();
			sp = conn.prepareCall(statement);
			SetParam(sp, params);
			getRs(sp, sr);
			getOutParameters(sp, params, sr);
		} catch (Exception e) {
			throw new DbException("StoreProcedure Error :" + e.getMessage());
		} finally {
			closeStatement(sp);
			closeConn(conn);
		}

		return sr;
	}

	protected void SetParam(CallableStatement sp, SqlParam[] params)
			throws DbException {
		try {
			sp.registerOutParameter(1, 4);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i].getType() == 0) {
						if (params[i].getValue().equals("^^^###")) {
							sp.setNull(i + 2, params[i].getSqlType());
						} else {
							switch (params[i].getSqlType()) {
							case 1:
							case 12:
								sp.setString(i + 2, params[i].getValue());
								break;
							case -7:
							case -6:
							case -5:
							case 4:
								if (params[i].getValue().equals(""))
									sp.setInt(i + 2, 0);
								else {
									sp.setInt(i + 2, Integer.parseInt(params[i]
											.getValue()));
								}

								break;
							case 3:
							case 6:
								if (params[i].getValue().equals(""))
									sp.setFloat(i + 2, 0.0F);
								else {
									sp.setFloat(i + 2, Float
											.parseFloat(params[i].getValue()));
								}
								break;
							case 91:
								sp.setString(i + 2, params[i].getValue());
								break;
							default:
								sp.setString(i + 2, params[i].getValue());
								break;
							}
						}
					} else {
						sp.registerOutParameter(i + 2, params[i].getSqlType());
					}
				}
			}
		} catch (Exception e) {
			throw new DbException("SetParam Error :" + e.getMessage());
		}
	}

	protected void getOutParameters(CallableStatement sp, SqlParam[] params,
			SqlReturn sr) throws DbException {
		try {
			sr.addOutputParam(Integer.toString(sp.getInt(1)));
			SqlParam param = null;
			for (int i = 0; i < params.length; i++) {
				param = params[i];
				if (param.getType() != 0)
					switch (param.getSqlType()) {
					case 1:
					case 12:
						sr.addOutputParam(sp.getString(i + 2));
						break;
					case -7:
					case -6:
					case -5:
					case 4:
						sr.addOutputParam(Integer.toString(sp.getInt(i + 2)));
						break;
					case 3:
					case 6:
						sr.addOutputParam(Integer.toString(sp.getInt(i + 2)));
						break;
					case 91:
						sr.addOutputParam(sp.getString(i + 2));
						break;
					default:
						sr.addOutputParam(sp.getString(i + 2));
					}
			}
		} catch (Exception e) {
			throw new DbException("getOutParameters Error:" + e.getMessage());
		}
	}

	protected void getRs(CallableStatement sp, SqlReturn sr) throws DbException {
		boolean hasResultSet = false;
		try {
			hasResultSet = sp.execute();

			while (hasResultSet) {
				ResultSet rst = sp.getResultSet();
				sr.addResultSet(rst);
				hasResultSet = sp.getMoreResults();
			}
		} catch (Exception e) {
			throw new DbException("getRs Error :" + e.getMessage());
		}
	}

	private Connection getConn() throws DbException {
		Connection conn = null;
		try {
			if (conn == null)
				conn = this.connMgr.getConnection(this.DataSouce);
		} catch (Exception e) {
			throw new DbException("getConn Error :" + e.getMessage());
		}
		return conn;
	}

	private Connection getConn(String dynaDataSource) throws DbException {
		Connection conn = null;
		try {
			if (conn == null)
				conn = this.connMgr.getConnection(dynaDataSource);
		} catch (Exception e) {
			throw new DbException("getConn Error :" + e.getMessage());
		}
		return conn;
	}

	private void closeConn(Connection conn) {
		this.connMgr.freeConnection(this.DataSouce, conn);
	}

	private void closeConn(Connection conn, String dynaDataSource) {
		this.connMgr.freeConnection(dynaDataSource, conn);
	}

	protected void closeStatement(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException localSQLException) {
			}
	}

	private Method findMethod(Object obj, String name) {
		Method ret = null;
		Method[] methods = obj.getClass().getMethods();

		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().trim().equals(name.trim())) {
					ret = methods[i];
					break;
				}
			}
		}
		return ret;
	}

	private static String sqlTypeToJava(String sqlType) {
		return sqlTypeToJava(Integer.parseInt(sqlType));
	}

	private static String sqlTypeToJava(int sqlType) {
		String javaType = "";
		switch (sqlType) {
		case 1:
		case 12:
			javaType = "String";
			break;
		case -7:
		case -6:
		case 4:
			javaType = "Int";
			break;
		case -5:
			javaType = "Long";
			break;
		case 3:
		case 6:
			javaType = "Float";
			break;
		case 91:
			javaType = "Date";
			break;
		default:
			javaType = "String";
		}
		return javaType;
	}
}