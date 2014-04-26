package com.web.form.business.daily;

import org.apache.struts.action.ActionForm;

public class PhoneAttendanceForm extends ActionForm {
	private static final long serialVersionUID = -7541790945203076132L;
	private String departName;
	private String beginDate;
	private String departId;
	private String personName;
	private String companyId;
	private String startMonth;
	private String startYear;
	private String id;
	private String companyIdSet;
	private String companyNameSet;
	private String companyKind;

	public String getCompanyIdSet() {
		return companyIdSet;
	}

	public void setCompanyIdSet(String companyIdSet) {
		this.companyIdSet = companyIdSet;
	}

	public String getCompanyNameSet() {
		return companyNameSet;
	}

	public void setCompanyNameSet(String companyNameSet) {
		this.companyNameSet = companyNameSet;
	}

	public String getCompanyKind() {
		return companyKind;
	}

	public void setCompanyKind(String companyKind) {
		this.companyKind = companyKind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
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

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

}
