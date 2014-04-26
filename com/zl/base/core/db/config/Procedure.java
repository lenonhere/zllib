package com.zl.base.core.db.config;

import java.util.ArrayList;
import java.util.List;

public class Procedure {
	private String name;
	private String target;
	private List<Object> inParams = new ArrayList<Object>();// Parameter
	private List<Object> outParams = new ArrayList<Object>();// Parameter
	private List<Object> resultSets = new ArrayList<Object>();// ResultSet
	private List<Object> accessors = new ArrayList<Object>();// Accessor

	public String getName() {
		return this.name;
	}

	public void setName(String s) {
		this.name = s;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String s) {
		this.target = s;
	}

	public List<Object> getInParams() {
		return this.inParams;
	}

	public void addInParam(Object obj) {
		this.inParams.add(obj);
	}

	public List<Object> getOutParams() {
		return this.outParams;
	}

	public void addOutParam(Object obj) {
		this.outParams.add(obj);
	}

	public List<Object> getResultSets() {
		return this.resultSets;
	}

	public void addResultSet(Object obj) {
		this.resultSets.add(obj);
	}

	public List<Object> getAccessors() {
		return this.accessors;
	}

	public void addAccessor(Object obj) {
		this.accessors.add(obj);
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("[Procedure >>> name=");
		stringbuffer.append(this.name);
		stringbuffer.append(", target=");
		stringbuffer.append(this.target);
		stringbuffer.append(", inParams=");
		stringbuffer.append(this.inParams);
		stringbuffer.append(", outParams=");
		stringbuffer.append(this.outParams);
		stringbuffer.append(", resultSets=");
		stringbuffer.append(this.resultSets);
		stringbuffer.append(", accessors=");
		stringbuffer.append(this.accessors);
		stringbuffer.append("]");
		return stringbuffer.toString();
	}
}