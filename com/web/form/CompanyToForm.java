package com.web.form;

import org.apache.struts.action.ActionForm;

public class CompanyToForm extends ActionForm {
	private static final long serialVersionUID = 2319778568292815342L;
	private String compsystid;
	private String destid;
	private String whouseid;
	private String infomation;

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getCompsystid() {
		return compsystid;
	}

	public void setCompsystid(String compsystid) {
		this.compsystid = compsystid;
	}

	public String getDestid() {
		return destid;
	}

	public void setDestid(String destid) {
		this.destid = destid;
	}

	public String getWhouseid() {
		return whouseid;
	}

	public void setWhouseid(String whouseid) {
		this.whouseid = whouseid;
	}
}
