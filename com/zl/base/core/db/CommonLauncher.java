package com.zl.base.core.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.base.core.db.SqlParam;
import com.zl.base.core.db.SqlReturn;
import com.zl.exception.DbException;

public class CommonLauncher extends CommonExecuter {
	private static Log log = LogFactory.getLog(CommonLauncher.class);

	protected static Map clazzesMap = null;

	protected static Map stringsMap = null;

	protected static final List EMPTY_LIST = new ArrayList();

	protected static CommonLauncher launcher = new CommonLauncher();

	static {
		clazzesMap = new HashMap();
		clazzesMap.put(String.class, new Integer(1));
		clazzesMap.put(Integer.class, new Integer(4));
		clazzesMap.put(BigDecimal.class, new Integer(3));
		clazzesMap.put(Double.class, new Integer(8));
		clazzesMap.put(Float.class, new Integer(6));

		stringsMap = new HashMap();
		stringsMap.put("java.lang.String", new Integer(1));
		stringsMap.put("java.lang.Integer", new Integer(4));
		stringsMap.put("java.math.BigDecimal", new Integer(3));
		stringsMap.put("java.lang.Double", new Integer(8));
		stringsMap.put("java.lang.Float", new Integer(6));
	}

	public static Connection getPooledConnection() {
		return launcher.getConnection();
	}

	public static Connection getPooledConnection(String dynaDataSource) {
		return launcher.getConnection(dynaDataSource);
	}

	public static void closePooledConnection(Connection conn) {
		launcher.closeConnection(conn);
	}

	public static void closePooledConnection(Connection conn,
			String dynaDataSource) {
		launcher.closeConnection(conn, dynaDataSource);
	}

	public static SqlReturn execute(String procName, SqlParam[] params)
			throws DbException {
		String literalStmt = makeLiteralCallableStatement(procName, params);
		String literalParameters = makeLiteralParameters(params);

		Connection conn = null;
		SqlReturn result = null;
		try {
			conn = launcher.getConnection();
			result = executeProcedure(procName, literalStmt, params, conn);
		} catch (DbException ex) {
			log.error("Execute error: callable statment <" + literalStmt
					+ "> with <" + literalParameters + ">");
			throw ex;
		} finally {
			launcher.closeConnection(conn);
		}
		return result;
	}

	public static SqlReturn execute(String procName, SqlParam[] params,
			Connection conn) throws DbException {
		String literalStmt = makeLiteralCallableStatement(procName, params);
		String literalParameters = makeLiteralParameters(params);

		SqlReturn result = null;
		try {
			result = executeProcedure(procName, literalStmt, params, conn);
		} catch (DbException ex) {
			log.error("Execute error: callable statment <" + literalStmt
					+ "> with <" + literalParameters + ">");
			throw ex;
		}
		return result;
	}

	protected static String makeLiteralParameters(SqlParam[] params) {
		StringBuffer paramValues = new StringBuffer();
		int length = params.length;
		if (length > 0) {
			paramValues.append(wrapParameter(params[0]));
			for (int i = 1; i < length; i++) {
				paramValues.append(", " + wrapParameter(params[i]));
			}
		}
		return paramValues.toString();
	}

	protected static String wrapParameter(SqlParam param) {
		int sqlType = param.getType();
		if ((sqlType == 12) || (sqlType == 1)) {
			return "'" + param.getValue() + "'";
		}
		return param.getValue();
	}

	protected static String makeLiteralCallableStatement(String procName,
			SqlParam[] params) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("{ ? = call ");
		buffer.append(procName);
		buffer.append(" ( ");

		int length = params.length;
		if (length > 0) {
			buffer.append(" ? ");
			for (int i = 1; i < length; i++) {
				buffer.append(", ? ");
			}
		}

		buffer.append(" ) } ");
		return buffer.toString();
	}

	protected static SqlReturn executeProcedure(String procName,
			String statement, SqlParam[] params, Connection conn)
			throws DbException {
		SqlReturn result = new SqlReturn();
		CallableStatement cStmt = null;
		try {
			cStmt = conn.prepareCall(statement);
			launcher.setParameters(cStmt, params);
			launcher.getRs(cStmt, result);
			launcher.getOutParameters(cStmt, params, result);
		} catch (SQLException ex) {
			throw new DbException("Can't prepare statemnet=" + statement);
		} finally {
			launcher.closeStatement(cStmt);
		}

		return result;
	}

	protected static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	protected static SqlParam[] createParams(int length) {
		SqlParam[] params = new SqlParam[length];
		for (int i = 0; i < length; i++) {
			params[i] = new SqlParam();
		}
		return params;
	}

	public static SqlReturn callProcedure(String procName) throws DbException {
		return callProcedure(procName, EMPTY_LIST, EMPTY_LIST);
	}

	public static SqlReturn callProcedure(String procName, List inParams)
			throws DbException {
		return callProcedure(procName, inParams, EMPTY_LIST);
	}

	public static SqlReturn callProcedure(String procName, List inParams,
			List outParams) throws DbException {
		SqlParam[] params = makeParams(procName, inParams, outParams);
		return execute(procName, params);
	}

	public static SqlReturn callProcedure(String procName, Connection conn)
			throws DbException {
		return callProcedure(procName, EMPTY_LIST, EMPTY_LIST, conn);
	}

	public static SqlReturn callProcedure(String procName, List inParams,
			Connection conn) throws DbException {
		return callProcedure(procName, inParams, EMPTY_LIST, conn);
	}

	public static SqlReturn callProcedure(String procName, List inParams,
			List outParams, Connection conn) throws DbException {
		SqlParam[] params = makeParams(procName, inParams, outParams);
		return execute(procName, params, conn);
	}

	public static SqlReturn callProcedure(String procName, List inParams,
			List inParamTypes, List outParams) throws DbException {
		SqlParam[] params = makeParams(procName, inParams, inParamTypes,
				outParams);
		return execute(procName, params);
	}

	public static SqlReturn callProcedure(String procName, List inParams,
			List inParamTypes, List outParams, Connection conn)
			throws DbException {
		SqlParam[] params = makeParams(procName, inParams, inParamTypes,
				outParams);
		return execute(procName, params, conn);
	}

	protected static SqlParam[] makeParams(String procName, List inParams,
			List outParams) {
		List inParamTypes = new ArrayList();
		List outParamTypes = new ArrayList();

		for (int i = 0; i < inParams.size(); i++) {
			inParamTypes.add(inParams.get(i).getClass());
		}

		for (int i = 0; i < outParams.size(); i++) {
			outParamTypes.add(outParams.get(i).getClass());
		}

		return makeParams(procName, inParams, inParamTypes, clazzesMap,
				outParams, outParamTypes, clazzesMap);
	}

	protected static SqlParam[] makeParams(String procName, List inParams,
			List inParamTypes, List outParams) {
		List outParamTypes = new ArrayList();

		for (int i = 0; i < outParams.size(); i++) {
			outParamTypes.add(outParams.get(i).getClass());
		}

		return makeParams(procName, inParams, inParamTypes, stringsMap,
				outParams, outParamTypes, clazzesMap);
	}

	protected static SqlParam[] makeParams(String procName, List inParams,
			List inParamTypes, Map inTypesMap, List outParams,
			List outParamTypes, Map outTypesMap) {
		int inSize = inParams == null ? 0 : inParams.size();
		int outSize = outParams == null ? 0 : outParams.size();

		if ((procName == null) || (procName.equals(""))) {
			throw new NullPointerException("procedure name is null or empty");
		}

		log.debug("[" + procName + "] in = " + inSize + " out = " + outSize);

		SqlParam[] params = createParams(inSize + outSize);

		StringBuffer buffer = new StringBuffer();
		buffer.append("[SQL:" + launcher.getDataSouce() + "] CALL " + procName
				+ "(");

		for (int i = 0; i < inSize; i++) {
			Object obj = inParams.get(i);
			Object type = inTypesMap.get(inParamTypes.get(i));

			if (type == null) {
				params[i].setAll(0, 1, toString(obj));
			} else {
				params[i].setAll(0, ((Integer) type).intValue(), toString(obj));
			}

			logParameter(buffer, type, obj, i);
		}

		for (int i = 0; i < outSize; i++) {
			Object obj = outParams.get(i);
			Object type = outTypesMap.get(outParamTypes.get(i));

			if (type == null) {
				params[(i + inSize)].setAll(1, 1, toString(obj));
			} else {
				params[(i + inSize)].setAll(1, ((Integer) type).intValue(),
						toString(obj));
			}

			logParameter(buffer, new Integer(4), "?", i + inSize);
		}

		buffer.append(") ");

		log.debug(buffer);

		return params;
	}

	protected static void logParameter(StringBuffer buffer, Object type,
			Object param, int index) {
		if (index != 0) {
			buffer.append(", ");
		}

		int kind = type == null ? 1 : ((Integer) type).intValue();

		if ((kind == 1) || (kind == 12))
			buffer.append("'" + toString(param) + "'");
		else {
			buffer.append(toString(param));
		}

		log.debug("parameter " + index + " : " + toString(param));
	}
}