package com.common.tackle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.ProcedureManager;

public class SheetSubmission {

	private static Log log = LogFactory.getLog(SheetSubmission.class);

	/**
	 * 需要调用的用于记录日志的存储过程访问名
	 */
	String logName = null;

	/**
	 * 需要提交的数据构成的字符串
	 */
	String infomation = null;

	/**
	 * 数据的列名（对应于存储过程的参数名）
	 */
	String columnNames = null;

	/**
	 * 需要提交的数据构成的字符串的列分割符
	 */
	String columnDelim = ",";

	/**
	 * 需要提交的数据构成的字符串的行分割符
	 */
	String rowDelim = ";";

	/**
	 * 是否将空串（包括只含空字符的串）替换成null
	 */
	boolean trimToNull = true;

	/**
	 * 行标志列的名（对应于存储过程的参数名）
	 */
	String identityNames = null;

	/**
	 * 列标志列名（对应于存储过程的参数名）
	 */
	String fieldNames = null;

	/**
	 * 列标志列的值
	 */
	String fieldInfomation = null;

	public SheetSubmission() {
	}

	public String getColumnDelim() {
		return columnDelim;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public String getIdentityNames() {
		return identityNames;
	}

	public String getInfomation() {
		return infomation;
	}

	public String getLogName() {
		return logName;
	}

	public String getRowDelim() {
		return rowDelim;
	}

	public boolean isTrimToNull() {
		return trimToNull;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public String getFieldInfomation() {
		return fieldInfomation;
	}

	public void setColumnDelim(String columnDelim) {
		this.columnDelim = columnDelim;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public void setIdentityNames(String identityNames) {
		this.identityNames = identityNames;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public void setRowDelim(String rowDelim) {
		this.rowDelim = rowDelim;
	}

	public void setTrimToNull(boolean trimToNull) {
		this.trimToNull = trimToNull;
	}

	public void setFieldInfomation(String fieldInfomation) {
		this.fieldInfomation = fieldInfomation;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	List identityNameList = null;

	List columnNameList = null;

	List fieldNameList = null;

	List infomationValueList = null;
	List fieldValueList = null;

	int rowCount = 0;
	int columnCount = 0;

	boolean autoCommit = true;
	boolean resetAutoCommit = false;

	Connection conn = null;

	/**
	 * 原子上报数据
	 *
	 * @param helper
	 *            CallHelper
	 * @return CallHelper
	 */
	public CallHelper atomicReport(CallHelper helper) {

		preprocess();

		try {
			conn = ProcedureManager.getConnection();
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			resetAutoCommit = true;

			try {
				helper = process(helper, conn);

				if (helper.getState() == 0) {
					processLog(helper, conn);
				}
			} catch (Exception e) {
				helper.setState(1);
				log.error("processing failed: " + e);
			}

			if (helper.getState() == 0) {
				conn.commit();
			} else {
				conn.rollback();
			}

			if (resetAutoCommit) {
				conn.setAutoCommit(autoCommit);
			}

		} catch (Exception e) {
			helper.setState(1);
			log.error("database connection problems: " + e);
		} finally {
			ProcedureManager.closeConnection(conn);
		}

		return helper;
	}

	public CallHelper report(CallHelper helper) throws Exception {
		preprocess();
		helper = process(helper, null);
		processLog(helper, conn);
		return helper;
	}

	public CallHelper process(CallHelper helper, Connection conn)
			throws Exception {

		if (identityNameList.size() == 0 && columnNameList.size() == 0
				&& fieldNameList.size() == 0) {
			helper = call(helper, conn);
			return helper;
		}

		int success = 0;

		int allProcess = 0;

		CallHelper newHelper = new CallHelper(helper.getName());
		newHelper.autoCopy(helper);

		columnCount = columnCount == 0 ? 1 : columnCount;

		int fieldPos = 0;
		int infoPos = 0;

		for (int i = 0; i < rowCount; i++) {
			for (int k = 0; k < identityNameList.size(); k++) {
				newHelper.setParam(identityNameList.get(k),
						infomationValueList.get(infoPos++));
			}

			fieldPos = 0;
			for (int j = 0; j < columnCount; j++) {
				fieldPos = j;
				for (int n = 0; n < fieldNameList.size(); n++) {
					newHelper.setParam(fieldNameList.get(n),
							fieldValueList.get(fieldPos++));
				}

				for (int m = 0; m < columnNameList.size(); m++) {
					newHelper.setParam(columnNameList.get(m),
							infomationValueList.get(infoPos++));
				}

				helper = call(newHelper, conn);

				allProcess++;
				if (helper.getState() == 0) {
					success++;
				}
			}
		}

		helper.setState(success == allProcess ? 0 : 1);

		return helper;
	}

	public CallHelper processLog(CallHelper helper, Connection conn)
			throws Exception {
		if (logName != null && !"".equals(logName.trim())) {
			CallHelper newHelper = new CallHelper(logName);
			newHelper.autoCopy(helper);
			newHelper = call(newHelper, conn);
			helper.setState(newHelper.getState());
		}
		return helper;
	}

	private void preprocess() {

		identityNameList = split(identityNames, columnDelim, true);
		columnNameList = split(columnNames, columnDelim, true);
		fieldNameList = split(fieldNames, columnDelim, true);

		List rows = split(infomation, rowDelim, true);
		rowCount = rows.size();
		infomationValueList = split(rows, columnDelim, false);

		if (trimToNull) {
			for (int i = 0; i < infomationValueList.size(); i++) {
				String value = infomationValueList.get(i).toString();
				if ("".equals(value.trim())) {
					infomationValueList.set(i, null);
				}
			}
		}

		List cols = split(fieldInfomation, rowDelim, true);
		columnCount = cols.size();
		fieldValueList = split(cols, columnDelim, false);
	}

	private List split(List values, String delim, boolean skip) {
		List valueList = new ArrayList();

		if (values == null || values.size() == 0 || delim == null) {
			return valueList;
		}

		for (int i = 0; i < values.size(); i++) {
			List columns = split(values.get(i).toString(), delim, skip);
			valueList.addAll(columns);
		}

		return valueList;
	}

	private List split(String values, String delim, boolean skip) {

		List valueList = new ArrayList();

		if (values == null || delim == null) {
			return valueList;
		}

		// 防止末尾只有连续的分隔符而没有其他字符而遭舍弃
		values += delim + "MANUAL_ADDED";
		String[] valueArray = values.split(delim);

		if (!skip) {
			for (int i = 0; i < valueArray.length - 1; i++) {
				valueList.add(valueArray[i].trim());
			}
			return valueList;
		}

		for (int i = 0; i < valueArray.length - 1; i++) {
			String value = valueArray[i].trim();
			if (value != null && !"".equals(value)) {
				valueList.add(value);
			}
		}

		return valueList;
	}

	private CallHelper call(CallHelper helper, Connection conn)
			throws Exception {

		if (conn == null) {
			helper = ProcedureManager.call(helper);
		} else {
			helper = ProcedureManager.call(helper, conn);
		}
		return helper;
	}
}
