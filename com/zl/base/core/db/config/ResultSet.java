package com.zl.base.core.db.config;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {
	private String name;
	private List<Object> ignores = new ArrayList<Object>();

	// renames holds old columns names, and replaces holds new names
	// for these columns names
	private List<Object> renames = new ArrayList<Object>();
	private List<Object> replaces = new ArrayList<Object>();
	private List<Object> horizons = new ArrayList<Object>();
	private List<Object> verticals = new ArrayList<Object>();
	private List<Object> contents = new ArrayList<Object>();
	private String separator = ""; // use empty string as default separator

	public void addIgnore(Object ignore) {
		this.ignores.add(ignore);
	}

	public void addRename(Object rename, Object replace) {
		this.renames.add(rename);
		this.replaces.add(replace);
	}

	public void addHorizon(Object horizon) {
		this.horizons.add(horizon);
	}

	public void addVertical(Object vertical) {
		this.verticals.add(vertical);
	}

	public void addContent(Object content) {
		this.contents.add(content);
	}

	public String getName() {
		return name;
	}

	public String getSeparator() {
		return separator;
	}

	public List<Object> getContents() {
		return contents;
	}

	public List<Object> getHorizons() {
		return horizons;
	}

	public List<Object> getIgnores() {
		return ignores;
	}

	public List<Object> getRenames() {
		return renames;
	}

	public List<Object> getReplaces() {
		return replaces;
	}

	public List<Object> getVerticals() {
		return verticals;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSeparator(String separator) {
		if (separator != null) {
			this.separator = separator;
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ResultSet >>> name=");
		buffer.append(name);
		buffer.append(", separator=");
		buffer.append(separator);
		buffer.append(", ignores=");
		buffer.append(ignores);
		buffer.append(", renames=");
		buffer.append(renames);
		buffer.append(", replaces=");
		buffer.append(replaces);
		buffer.append(", horizons=");
		buffer.append(horizons);
		buffer.append(", verticals=");
		buffer.append(verticals);
		buffer.append(", contents=");
		buffer.append(contents);
		buffer.append("]");
		return buffer.toString();
	}
}
