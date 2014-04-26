package com.zl.base.core.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.exception.DbException;

public class ProcedureLauncher extends Executer {
	private static final Log log = LogFactory.getLog(ProcedureLauncher.class);

	/**
	 * map class to sql types
	 */
	protected static Map<Object,Object> clazzesMap = null;

	/**
	 * map class name to sql types
	 */
	protected static Map<String,Object> stringsMap = null;

	/**
	 * empty list, i don't know how to make it immutable
	 */
	protected static final List EMPTY_LIST = new ArrayList();

	static {
		clazzesMap = new HashMap<Object,Object>();
		clazzesMap.put(java.lang.String.class, new Integer(Types.CHAR));
		clazzesMap.put(java.lang.Integer.class, new Integer(Types.INTEGER));
		clazzesMap.put(java.math.BigDecimal.class, new Integer(Types.DECIMAL));
		clazzesMap.put(java.lang.Double.class, new Integer(Types.DOUBLE));
		clazzesMap.put(java.lang.Float.class, new Integer(Types.FLOAT));
	}

	static {
		stringsMap = new HashMap<String,Object>();
		stringsMap.put("java.lang.String", new Integer(Types.CHAR));
		stringsMap.put("java.lang.Integer", new Integer(Types.INTEGER));
		stringsMap.put("java.math.BigDecimal", new Integer(Types.DECIMAL));
		stringsMap.put("java.lang.Double", new Integer(Types.DOUBLE));
		stringsMap.put("java.lang.Float", new Integer(Types.FLOAT));
	}

	protected static ProcedureLauncher launcher = new ProcedureLauncher();

	/**
	 * get a pooled connection
	 *
	 * @return
	 */
	public static Connection getPooledConnection() {
		return launcher.getConnection();
	}

	/**
	 * close a pooled connection (return it to the connecion pool)
	 *
	 * @param conn
	 */
	public static void closePooledConnection(Connection conn) {
		launcher.closeConnection(conn);
	}

	/**
	 * execute a procedure with no connection supplied
	 *
	 * @param procName
	 *            String
	 * @param params
	 *            SqlParam[]
	 * @param conn
	 *            Connection
	 * @return SqlReturn
	 */
	public static SqlReturn execute(String procName, SqlParam[] params)
			throws Exception {
		// { ? = call G_PersonLoginMultiMethod ( ? , ? , ? , ? , ? , ? ) }
		String literalStmt = makeLiteralCallableStatement(procName, params);
		// DEV, 123, 1, '?', '?', '?'
		String literalParameters = makeLiteralParameters(params);

		Connection conn = null;
		SqlReturn result = null;
		try {
			conn = launcher.getConnection();
			result = executeProcedure(procName, literalStmt, params, conn);
		} catch (Exception e) {
			log.error("Execute error: callable statment <" + literalStmt
					+ "> with <" + literalParameters + ">");
		} finally {
			launcher.closeConnection(conn);
		}
		return result;
	}

	/**
	 * execute a procedure through a given connection
	 *
	 * @param procName
	 *            String
	 * @param params
	 *            SqlParam[]
	 * @param conn
	 *            Connection
	 * @return SqlReturn
	 */
	public static SqlReturn execute(String procName, SqlParam[] params,
			Connection conn) throws Exception {
		String literalStmt = makeLiteralCallableStatement(procName, params);
		String literalParameters = makeLiteralParameters(params);

		SqlReturn result = null;
		try {
			result = executeProcedure(procName, literalStmt, params, conn);
		} catch (Exception ex) {
			log.error("Execute error: callable statment <" + literalStmt
					+ "> with <" + literalParameters + ">");
			throw ex;
		}
		return result;
	}

	/**
	 * make a string of parameter values
	 *
	 * @param params
	 * @return
	 */
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

	/**
	 * Warp parameter with "'" if its type is CHAR OR VARCHAR.
	 *
	 * @return
	 */
	protected static String wrapParameter(SqlParam param) {
		int sqlType = param.getType();
		if (sqlType == Types.VARCHAR || sqlType == Types.CHAR) {
			return "'" + param.getValue() + "'";
		} else {
			return param.getValue();
		}
	}

	/**
	 * make a callable statement
	 *
	 * @param procName
	 * @param params
	 * @return
	 */
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

	/**
	 * execute a procedure (really done)
	 *
	 * @param procName
	 * @param statement
	 * @param params
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected static SqlReturn executeProcedure(String procName,
			String statement, SqlParam[] params, Connection conn)
			throws Exception {
		SqlReturn result = new SqlReturn();
		CallableStatement cStmt = null;
		try {
			cStmt = conn.prepareCall(statement);
			launcher.setParameters(cStmt, params);
			launcher.getRs(cStmt, result);
			launcher.getOutParameters(cStmt, params, result);
		} catch (SQLException e) {
			throw new Exception("Can't prepare statemnet=" + statement);
		} finally {
			launcher.closeStatement(cStmt);
		}

		return result;
	}

	/**
	 * null safe toString wrapper
	 *
	 * @param obj
	 *            Object
	 * @return String
	 */
	protected static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
	 * allocate *length* parameter holders
	 *
	 * @param length
	 *            int
	 * @return SqlParam[]
	 */
	protected static SqlParam[] createParams(final int length) {
		SqlParam[] params = new SqlParam[length];
		for (int i = 0; i < length; i++) {
			params[i] = new SqlParam();
		}
		return params;
	}

	/**
	 * call procedure with procedure name
	 *
	 * @param procName
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName) throws Exception {

		return callProcedure(procName, EMPTY_LIST, EMPTY_LIST);
	}

	/**
	 * call procedure with procedure name and input parameters
	 *
	 * @param procName
	 *            String
	 * @param inParams
	 *            List
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName, List inParams)
			throws Exception {

		return callProcedure(procName, inParams, EMPTY_LIST);
	}

	/**
	 * call procedure with procedure name, input parameters and output
	 * parameters
	 *
	 * @param procName
	 *            String
	 * @param inParams
	 *            List
	 * @param outParams
	 *            List
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			List outParams) throws Exception {

		SqlParam[] params = makeParams(procName, inParams, outParams);
		return execute(procName, params);
	}

	/**
	 * call procedure with procedure name and connection
	 *
	 * @param procName
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static SqlReturn callProcedure(String procName, Connection conn)
			throws Exception {
		return callProcedure(procName, EMPTY_LIST, EMPTY_LIST, conn);
	}

	/**
	 * call procedure with procedure name, input parameters and connection
	 *
	 * @param procName
	 * @param inParams
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			Connection conn) throws Exception {
		return callProcedure(procName, inParams, EMPTY_LIST, conn);
	}

	/**
	 * call procedure with procedure name, input parameters, output parameters
	 * and connection
	 *
	 * @param procName
	 * @param inParams
	 * @param outParams
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			List outParams, Connection conn) throws Exception {
		SqlParam[] params = makeParams(procName, inParams, outParams);
		return execute(procName, params, conn);
	}

	/**
	 * call procedure with procedure name, input paramters (and types) and
	 * output parameters
	 *
	 * @param procName
	 * @param inParams
	 * @param inParamTypes
	 * @param outParams
	 * @return
	 * @throws Exception
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			List inParamTypes, List outParams) throws Exception {
		SqlParam[] params = makeParams(procName, inParams, inParamTypes,
				outParams);
		return execute(procName, params);
	}

	/**
	 * call procedure with procedure name, input paramters (and types), output
	 * parameters, connection
	 *
	 * @param procName
	 * @param inParams
	 * @param inParamTypes
	 * @param outParams
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			List inParamTypes, List outParams, Connection conn)
			throws Exception {
		SqlParam[] params = makeParams(procName, inParams, inParamTypes,
				outParams);
		return execute(procName, params, conn);
	}

	/**
	 * make parameters without input parameter type name(using their class
	 * instead)
	 *
	 * @param procName
	 * @param inParams
	 * @param outParams
	 * @return
	 */
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

	/**
	 * make parameters with input parameter type name(class name)
	 *
	 * @param procName
	 * @param inParams
	 * @param inParamTypes
	 * @param outParams
	 * @return
	 */
	protected static SqlParam[] makeParams(String procName, List inParams,
			List inParamTypes, List outParams) {
		List outParamTypes = new ArrayList();
		for (int i = 0; i < outParams.size(); i++) {
			outParamTypes.add(outParams.get(i).getClass());
		}
		// return makeParams(procName, inParams, inParamTypes, stringsMap,
		// outParams, outParams, clazzesMap);
		return makeParams(procName, inParams, inParamTypes, stringsMap,
				outParams, outParamTypes, clazzesMap);
	}

	/**
	 * make parameters
	 *
	 * @param procName
	 * @param inParams
	 * @param inParamTypes
	 * @param inTypesMap
	 * @param outParams
	 * @param outParamTypes
	 * @param outTypesMap
	 * @return
	 */
	protected static SqlParam[] makeParams(String procName, List inParams,
			List inParamTypes, Map inTypesMap, List outParams,
			List outParamTypes, Map outTypesMap) {
		int inSize = (inParams == null ? 0 : inParams.size());
		int outSize = (outParams == null ? 0 : outParams.size());
		if (procName == null || procName.equals("")) {
			throw new NullPointerException("procedure name is null or empty");
		}
		log.debug("[" + procName + "] in = " + inSize + " out = " + outSize);

		SqlParam[] params = createParams(inSize + outSize);
		StringBuffer buffer = new StringBuffer();
		buffer.append("[SQL] CALL " + procName + "(");
		// collect input parameters
		for (int i = 0; i < inSize; i++) {
			Object obj = inParams.get(i);
			Object type = inTypesMap.get(inParamTypes.get(i));
			if (type == null) {
				// use Types.CHAR as default sql type for unspecified parameter
				params[i].setAll(0, Types.CHAR, toString(obj));
			} else {
				params[i].setAll(0, ((Integer) type).intValue(), toString(obj));
			}
			logParameter(buffer, type, obj, i);
		}
		// collect output parameters
		for (int i = 0; i < outSize; i++) {
			Object obj = outParams.get(i);
			Object type = outTypesMap.get(outParamTypes.get(i));
			if (type == null) {
				// use Types.CHAR as default sql type for unspecified parameter
				params[i + inSize].setAll(1, Types.CHAR, toString(obj));
			} else {
				params[i + inSize].setAll(1, ((Integer) type).intValue(),
						toString(obj));
			}
			// Log output parameter as a single ? (mark)
			logParameter(buffer, new Integer(Types.INTEGER), "?", i + inSize);
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

		int kind = (type == null) ? Types.CHAR : ((Integer) type).intValue();

		if (kind == Types.CHAR || kind == Types.VARCHAR) {
			buffer.append("'" + toString(param) + "'");
		} else {
			buffer.append(toString(param));
		}

		log.debug("parameter " + index + " : " + toString(param));
	}
}
