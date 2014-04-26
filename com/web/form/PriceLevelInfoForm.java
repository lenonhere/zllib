package com.web.form;

import org.apache.struts.action.ActionForm;

public class PriceLevelInfoForm extends ActionForm {
	private static final long serialVersionUID = -6319530262355739945L;
	private String priceLevelId;
	private String priceLevelName;
	private String priceLevelCode;
	private String lowLimitPrice;
	private String topLimitPrice;
	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public String getLowLimitPrice() {
		return lowLimitPrice;
	}

	public String getPriceLevelCode() {
		return priceLevelCode;
	}

	public String getPriceLevelId() {
		return priceLevelId;
	}

	public String getPriceLevelName() {
		return priceLevelName;
	}

	public String getTopLimitPrice() {
		return topLimitPrice;
	}

	public void setPriceLevelName(String priceLevelName) {
		this.priceLevelName = priceLevelName;
	}

	public void setTopLimitPrice(String topLimitPrice) {
		this.topLimitPrice = topLimitPrice;
	}

	public void setPriceLevelId(String priceLevelId) {
		this.priceLevelId = priceLevelId;
	}

	public void setPriceLevelCode(String priceLevelCode) {
		this.priceLevelCode = priceLevelCode;
	}

	public void setLowLimitPrice(String lowLimitPrice) {
		this.lowLimitPrice = lowLimitPrice;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
