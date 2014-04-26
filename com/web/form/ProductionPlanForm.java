package com.web.form;

import org.apache.struts.action.ActionForm;

public class ProductionPlanForm extends ActionForm {

	private static final long serialVersionUID = -2095243868677752667L;
	private String planDate;
	private String unitId;
	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
