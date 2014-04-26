package com.zl.base.core.db;

import java.sql.Connection;
import java.util.Hashtable;

public abstract class DBPool {
	protected static DBPool instance;
	protected Hashtable dbPoolInfo = new Hashtable();

	public abstract Connection getConnection(String dbType);

	public abstract void freeConnection(String dbType, Connection conn);

	public abstract void release();

	protected boolean checkPool(String dbType) throws Exception {
		boolean flag = false;
		if ((dbType == null) || ("".equals(dbType.trim())))
			throw new Exception("连接池不能为空名称！");
		if (this.dbPoolInfo.get(dbType.trim()) != null)
			flag = true;
		return flag;
	}

	protected void addDBPool(String dbType, DBPoolInfo dbPoolInfo)
			throws Exception {
		if (!checkPool(dbType))
			this.dbPoolInfo.put(dbType, dbPoolInfo);
	}

	protected DBPoolInfo getDBPool(String dbType) throws Exception {
		if (this.dbPoolInfo.get(dbType.trim()) == null) {
			throw new Exception("连接池名称为：" + dbType + "不存在！");
		}
		return (DBPoolInfo) this.dbPoolInfo.get(dbType.trim());
	}
}