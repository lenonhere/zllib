package com.zl.manage;

import org.apache.struts.action.ActionForm;

public class TemplateForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private int systemid;
	private String templatename;
	private String templatepath;
	private String templatetype;

	public int getSystemid() {
		return systemid;
	}

	public void setSystemid(int systemid) {
		this.systemid = systemid;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public String getTemplatepath() {
		return templatepath;
	}

	public void setTemplatepath(String templatepath) {
		this.templatepath = templatepath;
	}

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}
}
