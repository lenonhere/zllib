package com.qmx.dbutils;

public class DBInformation {
	private String driver;
	private String url;
	private String username;
	private String password;
	private String maxconn;
	private String datasource;
	private String servertype;

	public DBInformation() {

	}

	/**
	 * @param datasource
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 */
	public DBInformation(String datasource, String driver, String url,
			String username, String password) {
		this.datasource = datasource;
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * @param datasource
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @param maxconn
	 * @param servertype
	 */
	public DBInformation(String datasource, String driver, String url,
			String username, String password, String maxconn, String servertype) {
		this.datasource = datasource;
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxconn = maxconn;
		this.servertype = servertype;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("[datasource=");
		buf.append(this.datasource);
		buf.append(",servertype=");
		buf.append(this.servertype);
		buf.append(",driver=");
		buf.append(this.driver);
		buf.append(",url=");
		buf.append(this.url);
		buf.append(",username=");
		buf.append(this.username);
		buf.append(",password=");
		buf.append(this.password);
		buf.append(",maxconn=");
		buf.append(this.maxconn);
		buf.append("]");
		return buf.toString();
	}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxconn() {
		return maxconn;
	}

	public void setMaxconn(String maxconn) {
		this.maxconn = maxconn;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getServertype() {
		return servertype;
	}

	public void setServertype(String servertype) {
		this.servertype = servertype;
	}

}
