package com.web.form;

import org.apache.struts.action.ActionForm;

public class SystemSelectCompanyForm extends ActionForm {

	private static final long serialVersionUID = -1118767120133977603L;
	String systemId;
	String companyIdSet;

	public String getCompanyIdSet() {
		return companyIdSet;
	}

	public void setCompanyIdSet(String companyIdSet) {
		this.companyIdSet = companyIdSet;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
