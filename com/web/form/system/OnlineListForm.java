package com.web.form.system;

import org.apache.struts.action.ActionForm;

public class OnlineListForm extends ActionForm {
	private static final long serialVersionUID = 2559845905646980572L;
	String beginDate;
	String endDate;
	String searchName;
	String personCode;
	String personName;
	String departId;
	String departName;
	String loggin_times;
	String loggin_days;
	String logintime;
	String ondate;
	String personType;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getLoggin_days() {
		return loggin_days;
	}

	public void setLoggin_days(String loggin_days) {
		this.loggin_days = loggin_days;
	}

	public String getLoggin_times() {
		return loggin_times;
	}

	public void setLoggin_times(String loggin_times) {
		this.loggin_times = loggin_times;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getOndate() {
		return ondate;
	}

	public void setOndate(String ondate) {
		this.ondate = ondate;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

}
