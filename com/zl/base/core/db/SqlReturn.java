package com.zl.base.core.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

public class SqlReturn {
	static Logger logger = Logger.getLogger(SqlReturn.class);
	private ArrayList<BasicDynaBean> result = null;
	private ArrayList<String> OutputParamlist = null;
	private int ResultCount = 0;
	private int AffectRowCount = 0;
	private int OutputParamCount = 0;

	public int getResultCount() {
		return this.ResultCount;
	}

	public int getAffectRowCount() {
		return this.AffectRowCount;
	}

	public void setAffectRowCount(int i) {
		this.AffectRowCount = i;
	}

	public int getOutputParamCount() {
		return this.OutputParamCount;
	}

	public ArrayList<BasicDynaBean> getResultSet() {
		return this.result;
	}

	public ArrayList<BasicDynaBean> getResultSet(int i) {
		BasicDynaBean dynaBean = (BasicDynaBean) this.result.get(i);
		ArrayList<BasicDynaBean> list = new ArrayList<BasicDynaBean>();
		list.add(dynaBean);
		return list;
	}

	@SuppressWarnings("unchecked")
	public void addResultSet(ResultSet rs) {
		if (this.result == null)
			this.result = new ArrayList<BasicDynaBean>();
		try {
			RowSetDynaClass rowSetDynaClass = new RowSetDynaClass(rs);
			this.result = (ArrayList<BasicDynaBean>) rowSetDynaClass.getRows();
			this.ResultCount += 1;
		} catch (Exception localException) {
			localException.getMessage();
		}
	}

	public String getOutputParam(int i) {
		return this.OutputParamlist.get(i);
	}

	public void addOutputParam(String obj) {
		if (this.OutputParamlist == null)
			this.OutputParamlist = new ArrayList<String>();
		this.OutputParamlist.add(obj);
		this.OutputParamCount += 1;
	}

	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\tResultCount:" + this.ResultCount + "\n");
		strBuffer.append("\tAffectRowCount:" + this.AffectRowCount + "\n");
		strBuffer.append("\tOutputParamCount:" + this.OutputParamCount + "\n");
		return strBuffer.toString();
	}
}
