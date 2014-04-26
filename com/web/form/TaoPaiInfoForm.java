package com.web.form;

import org.apache.struts.action.ActionForm;

public class TaoPaiInfoForm extends ActionForm {
	private static final long serialVersionUID = 7213127184214393668L;
	String tobaIdSet;
	String tobaccoId;
	String tobaCode;
	String isActive;
	String tobaName;
	String bandName;
	String priceLevelId;
	String includePrice;
	String tradePrice;
	String retailPrice;
	String tobaccoAlias;
	String contractPrice;// 含税价;

	public String getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(String contractPrice) {
		this.contractPrice = contractPrice;
	}

	public String getTobaccoAlias() {
		return tobaccoAlias;
	}

	public void setTobaccoAlias(String tobaccoAlias) {
		this.tobaccoAlias = tobaccoAlias;
	}

	public String getTobaIdSet() {
		return tobaIdSet;
	}

	public void setTobaIdSet(String tobaIdSet) {
		this.tobaIdSet = tobaIdSet;
	}

	public String getTobaccoId() {
		return tobaccoId;
	}

	public void setTobaccoId(String tobaccoId) {
		this.tobaccoId = tobaccoId;
	}

	public String getTobaCode() {
		return tobaCode;
	}

	public void setTobaCode(String tobaCode) {
		this.tobaCode = tobaCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public String getTobaName() {
		return tobaName;
	}

	public void setTobaName(String tobaName) {
		this.tobaName = tobaName;
	}

	public String getPriceLevelId() {
		return priceLevelId;
	}

	public void setPriceLevelId(String priceLevelId) {
		this.priceLevelId = priceLevelId;
	}

	public String getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getIncludePrice() {
		return includePrice;
	}

	public void setIncludePrice(String includePrice) {
		this.includePrice = includePrice;
	}

}
