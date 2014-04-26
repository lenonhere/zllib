package com.web.pojos;

import java.io.Serializable;

public class ChannelColumn implements Serializable{
	
	private String columnId;
	
	private String columnName;
	
	private String columnType;
	
	private String sortOrder;
	
	private String columnContent;
	
	private String columncontentId;
	
	
	public String getColumncontentId() {
		return columncontentId;
	}

	public void setColumncontentId(String columncontentId) {
		this.columncontentId = columncontentId;
	}

	public String getColumnContent() {
		return columnContent;
	}

	public void setColumnContent(String columnContent) {
		this.columnContent = columnContent;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	

}
