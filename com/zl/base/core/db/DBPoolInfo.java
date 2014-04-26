package com.zl.base.core.db;

public class DBPoolInfo {
	private String name;
	private String URL;
	private String user;
	private String password;
	private String maxConn;
	private String type;
	private String driver;

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String s) {
		this.type = s;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String s) {
		this.name = s;
	}

	public String getURL() {
		return this.URL;
	}

	public void setURL(String s) {
		this.URL = s;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String s) {
		this.user = s;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String s) {
		this.password = s;
	}

	public String getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("[ ");
		str.append("Driver:" + this.driver);
		str.append("\nURL:" + this.URL);
		str.append("\nUser:" + this.user);
		str.append("\nPassWord:" + this.password);
		str.append("\nMaxConn:" + this.maxConn);
		str.append("\nServerType:" + this.type);
		str.append(" ]\n");
		return str.toString();
	}
}