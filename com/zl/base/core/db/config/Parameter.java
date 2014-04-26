package com.zl.base.core.db.config;

public class Parameter {
	private String name;
	private String type;
	private String value;
	private String source;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getSource() {
		return source;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[Parameter >>> name=");
		buffer.append(name);
		buffer.append(", type=");
		buffer.append(type);
		buffer.append(", value=");
		buffer.append(value);
		buffer.append(", source=");
		buffer.append(source);
		buffer.append("]");
		return buffer.toString();
	}
}
