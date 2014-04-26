package com.web.form;

import org.apache.struts.action.ActionForm;

public class DepartInfoForm extends ActionForm {

	private static final long serialVersionUID = -2045881618317917711L;

	public String getDepartCode() {
		return departCode;
	}

	public String getDepartId() {
		return departId;
	}

	public String getDepartName() {
		return departName;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getParentId() {
		return parentId;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	public String getIsduty() {
		return isduty;
	}

	public void setIsduty(String isduty) {
		this.isduty = isduty;
	}

	private String departId;
	private String departCode;
	private String departName;
	private String parentId;
	private String isActive;
	private String isduty;
	private String pinyin;

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param pinyin
	 *            the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}
