package com.web.form;

import org.apache.struts.action.ActionForm;

public class AreaInfoForm extends ActionForm {

	private static final long serialVersionUID = 4205593648490428440L;
	private String userId;
	private String areaCode;
	private String parentCode;
	private String areaName;
	private String orderTag;
	private String ifProvince;
	private String ifArea;
	private String ifLeaf;
	private String isActive;

	public AreaInfoForm() {
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getIfArea() {
		return ifArea;
	}

	public void setIfArea(String ifArea) {
		this.ifArea = ifArea;
	}

	public String getIfLeaf() {
		return ifLeaf;
	}

	public void setIfLeaf(String ifLeaf) {
		this.ifLeaf = ifLeaf;
	}

	public String getIfProvince() {
		return ifProvince;
	}

	public void setIfProvince(String ifProvince) {
		this.ifProvince = ifProvince;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(String orderTag) {
		this.orderTag = orderTag;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
