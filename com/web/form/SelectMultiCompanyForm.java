package com.web.form;

import org.apache.struts.action.ActionForm;

public class SelectMultiCompanyForm extends ActionForm {

	private static final long serialVersionUID = -2755132592438121817L;
	private String userId;
	private String areaCode;
	private String type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
