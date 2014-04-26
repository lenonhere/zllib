package com.zl.base.core.db.config;

import java.util.ArrayList;
import java.util.List;

public class Accessor {

	private String name;
	private List<Object> consts = new ArrayList<Object>();// Parameter
	private List<Object> sources = new ArrayList<Object>();// Parameter
	private List<Object> resultSets = new ArrayList<Object>();// ResultSet

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getConsts() {
		return consts;
	}

	public void addConst(Object constant) {
		this.consts.add(constant);
	}

	public List<Object> getSources() {
		return sources;
	}

	public void addSource(Object source) {
		this.sources.add(source);
	}

	public List<Object> getResultSets() {
		return resultSets;
	}

	public void addResultSet(Object resultSet) {
		this.resultSets.add(resultSet);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[Accessor >>> name=");
		buffer.append(name);
		buffer.append(", consts=");
		buffer.append(consts);
		buffer.append(", sources=");
		buffer.append(sources);
		buffer.append(", resultSets=");
		buffer.append(resultSets);
		buffer.append("]");
		return buffer.toString();
	}
}
