package com.web.form;

import org.apache.struts.action.ActionForm;

public class PermissionAssignForm extends ActionForm {
	private static final long serialVersionUID = 3203409878612329676L;

	String userId;
	String menuIdSet;
	String personName;

	public String getMenuIdSet() {
		return menuIdSet;
	}

	public String getUserId() {
		return userId;
	}

	public void setMenuIdSet(String menuIdSet) {
		this.menuIdSet = menuIdSet;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @param personName
	 *            the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
