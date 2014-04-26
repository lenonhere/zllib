package com.zl.base.core.db;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CallHelper {

	private static final Log log = LogFactory.getLog(CallHelper.class);
	private static final List EMPTY_LIST = new ArrayList();
	private static final String EMPTY_STRING = "";
	public static final int EXE_OK = 0;
	public static final int EXE_WITH_WARNING = 1;
	public static final int EXE_WITH_ERROR = 2;
	protected static List<String> ignoreProperties = new ArrayList<String>();

	static {
		ignoreProperties.add("servlet");
		ignoreProperties.add("class");
		ignoreProperties.add("remoteAddr");
		ignoreProperties.add("sessionLocale");
		ignoreProperties.add("localeDisplay");
		ignoreProperties.add("servletWrapper");
		ignoreProperties.add("dispatch");
		ignoreProperties.add("mutable");
		ignoreProperties.add("multipartRequestHandler");
	}

	/**
	 * the called procedure mapping name, not must be acutal procedure name
	 */
	private String name;

	/**
	 * passed-in parameters
	 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	/**
	 * result sets after calling procedure
	 */
	private List results;

	/**
	 * names for result sets
	 */
	private List resultNames;

	/**
	 * output after calling procedure
	 */
	private List outputs;

	/**
	 * name for outputs
	 */
	private List outputNames;

	/**
	 * state of executed procedure
	 */
	private int state;

	/**
	 * 增加SQLCODE
	 */
	private int sqlCode = 0;

	/**
	 * 通过此标记获取存储过程执行状态
	 *
	 * EXE_OK:执行通过
	 *
	 * EXE_WITH_WARNING:执行有警告
	 *
	 * EXE_WITH_ERROR:执行有错误
	 */
	private int errorLevel = CallHelper.EXE_OK;

	/**
	 * 增加错误信息
	 */
	private String errorMessage = "";

	/**
	 * constructor
	 *
	 * @param name
	 *            String
	 */
	public CallHelper(String name) {
		this.name = name;
	}

	/**
	 * get state
	 *
	 * @return int
	 */
	public int getState() {
		return state;
	}

	/**
	 * set state
	 *
	 * @param state
	 *            int
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * get name
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * set results
	 *
	 * @param results
	 *            List
	 */
	public void setResults(List results) {
		this.results = results;
	}

	/**
	 * set result names
	 *
	 * @param results
	 *            List
	 */
	public void setResultNames(List resultNames) {
		this.resultNames = resultNames;
	}

	/**
	 * set outputs
	 *
	 * @param outputs
	 *            List
	 */
	public void setOutputs(List outputs) {
		this.outputs = outputs;
	}

	/**
	 * set output names
	 *
	 * @param outputs
	 *            List
	 */
	public void setOutputNames(List outputNames) {
		this.outputNames = outputNames;
	}

	/**
	 * get result with index
	 *
	 * @param i
	 *            int
	 * @return Object
	 */
	public List getResult(int i) {
		return (List) results.get(i);
	}

	/**
	 * get result with name. if not found, return empty list
	 *
	 * @param name
	 *            String
	 * @return Object
	 */
	public List getResult(String name) {
		if (name == null || "".equals(name)) {
			return EMPTY_LIST;
		}
		for (int i = 0, n = resultNames.size(); i < n; i++) {
			if (name.equals(resultNames.get(i))) {
				return (List) results.get(i);
			}
			System.out.println();
		}
		return EMPTY_LIST;
	}

	/**
	 * get output with index
	 *
	 * @param i
	 *            int
	 * @return Object
	 */
	public Object getOutput(int i) {
		return outputs.get(i);
	}

	/**
	 * get output with name. if not found, return empty list
	 *
	 * @param name
	 *            String
	 * @return Object
	 */
	public Object getOutput(String name) {
		if (name == null || "".equals(name)) {
			return EMPTY_STRING;
		}
		for (int i = 0, n = outputNames.size(); i < n; i++) {
			if (name.equals(outputNames.get(i))) {
				return outputs.get(i);
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * get the number of outputs
	 *
	 * @return int
	 */
	public int getOutputCount() {
		return outputs.size();
	}

	/**
	 * get the number of results
	 *
	 * @return int
	 */
	public int getResultCount() {
		return results.size();
	}

	/**
	 * get parameter
	 *
	 * @param name
	 *            String
	 * @return String
	 */
	public String getParam(String name) {
		return (String) paramMap.get(name);
	}

	/**
	 * set parameter
	 *
	 * @param name
	 *            String
	 * @param value
	 *            String
	 */
	public void setParam(String name, String value) {
		paramMap.put(name, value);
	}

	/**
	 * set parameter, adopted to reduce manual type cast
	 *
	 * @param name
	 *            Object
	 * @param value
	 *            Object
	 */
	public void setParam(Object name, Object value) {
		paramMap.put(name.toString(), value == null ? null : value.toString());
	}

	public void autoCopy(Object src) {

		if (src == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		if (src instanceof DynaBean) {
			DynaProperty origDescriptors[] = ((DynaBean) src).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (filter(name)) {
					continue;
				}
				Object value = ((DynaBean) src).get(name);
				paramMap.put(name, value);
			}
		} else if (src instanceof Map) {
			Iterator names = ((Map) src).keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				if (filter(name)) {
					continue;
				}
				Object value = ((Map) src).get(name);
				paramMap.put(name, value);
			}
		} else if (src instanceof CallHelper) {
			Map srcMap = ((CallHelper) src).paramMap;
			Iterator names = srcMap.keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				if (filter(name)) {
					continue;
				}
				Object value = srcMap.get(name);
				paramMap.put(name, value);
			}
		} else {
			PropertyDescriptor[] origDescriptors = PropertyUtils
					.getPropertyDescriptors(src);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (filter(name)) {
					continue;
				}
				Object value = null;
				try {
					value = PropertyUtils.getSimpleProperty(src, name);
				} catch (Exception ex) {
					log.error("can't auto copy property: " + name
							+ ", call helper name=" + this.name);
				}
				paramMap.put(name, value);
			}
		}
	}

	protected boolean filter(String name) {
		return ignoreProperties.contains(name);
	}

	public void execute() {
		try {
			ProcedureManager.call(this);
		} catch (Exception e) {
			log.error(e);
		}
	}

	// TODO Execute
	public void execute(String dynaDataSource) {
		try {
			Connection conn = CommonLauncher
					.getPooledConnection(dynaDataSource);
			ProcedureManager.call(this, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[Callhelper >>> ");
		buffer.append("name=");
		buffer.append(name);
		buffer.append(", paramMap=");
		buffer.append(paramMap);
		buffer.append(", results=");
		buffer.append(results);
		buffer.append(", resultNames=");
		buffer.append(resultNames);
		buffer.append(", outputs=");
		buffer.append(outputs);
		buffer.append(", outputNames=");
		buffer.append(outputNames);
		buffer.append(", state=");
		buffer.append(state);
		buffer.append("]");

		return buffer.toString();
	}

	/**
	 * @return the sqlCode
	 */
	public int getSqlCode() {
		return sqlCode;
	}

	/**
	 * @param sqlCode
	 *            the sqlCode to set
	 */
	public void setSqlCode(int sqlCode) {
		this.sqlCode = sqlCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorLevel
	 */
	public int getErrorLevel() {
		return errorLevel;
	}

	/**
	 * @param errorLevel
	 *            the errorLevel to set
	 */
	public void setErrorLevel(int errorLevel) {
		this.errorLevel = errorLevel;
	}
}
