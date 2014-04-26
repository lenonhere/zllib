package com.web.form;

import org.apache.struts.action.ActionForm;

public class NoteSendForm extends ActionForm {

	private static final long serialVersionUID = 1044478823536783754L;
	private String phonecode = "";
	private String content = "";
	private String beginDate = "";
	private String endDate = "";
	private String unitId = "";
	private String contenttype = "";
	private String days = "7";
	private String tobaccoIdSet = "-1";
	private String companyIdSet = "-1";
	private String userId = "";

	public NoteSendForm() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getCompanyIdSet() {
		return companyIdSet;
	}

	public void setCompanyIdSet(String companyIdSet) {
		this.companyIdSet = companyIdSet;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTobaccoIdSet() {
		return tobaccoIdSet;
	}

	public void setTobaccoIdSet(String tobaccoIdSet) {
		this.tobaccoIdSet = tobaccoIdSet;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
