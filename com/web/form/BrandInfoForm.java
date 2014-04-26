package com.web.form;

import org.apache.struts.action.ActionForm;

public class BrandInfoForm extends ActionForm {

	private static final long serialVersionUID = -8096224368230627708L;
	private String brandCode;
	private String brandName;
	private String isActive;

	public String getBrandCode() {
		return brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
