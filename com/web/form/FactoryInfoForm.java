package com.web.form;

import org.apache.struts.action.ActionForm;

public class FactoryInfoForm extends ActionForm {

	private static final long serialVersionUID = -9125508199004847595L;
	private String factSystId;
	private String factoryCode;
	private String factoryName;
	private String factoryAlias;
	private String isActive;
	private String factoryOldCode;
	private String provSystId;

	public String getFactoryAlias() {
		return factoryAlias;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public String getFactoryOldCode() {
		return factoryOldCode;
	}

	public String getFactSystId() {
		return factSystId;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getProvSystId() {
		return provSystId;
	}

	public void setFactoryAlias(String factoryAlias) {
		this.factoryAlias = factoryAlias;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public void setFactoryOldCode(String factoryOldCode) {
		this.factoryOldCode = factoryOldCode;
	}

	public void setFactSystId(String factSystId) {
		this.factSystId = factSystId;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setProvSystId(String provSystId) {
		this.provSystId = provSystId;
	}

}
