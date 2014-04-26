package com.zl.base.core.db;

import org.apache.log4j.Logger;

public class SqlParam {
	static Logger logger = Logger.getLogger(SqlParam.class);
	public static final int TYPE_IN = 0;
	public static final int TYPE_OUT = 1;
	private String name = "";
	private int sqlType = 12;
	private String value = "";
	private int type = 0;

	public String toString() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("\tname:" + this.name + "\n");
		localStringBuffer.append("\tsqlType:" + this.sqlType + "\n");
		localStringBuffer.append("\tvalue:" + this.value + "\n");
		localStringBuffer.append("\ttype:" + this.type + "\n");
		return localStringBuffer.toString();
	}

	public String getName() {
		return this.name;
	}

	public int getSqlType() {
		return this.sqlType;
	}

	public int getType() {
		return this.type;
	}

	public String getValue() {
		return this.value;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setSqlType(int paramInt) {
		this.sqlType = paramInt;
	}

	public void setType(int paramInt) {
		this.type = paramInt;
	}

	public void setValue(String paramString) {
		if (paramString == null)
			this.value = "^^^###";
		else
			this.value = paramString;
	}

	public void setAll(int paramInt1, int paramInt2, String paramString) {
		this.type = paramInt1;
		this.sqlType = paramInt2;
		this.value = paramString;
		if (paramString == null)
			this.value = "^^^###";
		else
			this.value = paramString;
	}

	public void setAll(int paramInt, String paramString1, String paramString2) {
		this.type = paramInt;
		this.sqlType = Integer.parseInt(paramString1);
		this.value = paramString2;
		if (paramString2 == null)
			this.value = "^^^###";
		else
			this.value = paramString2;
	}
}
