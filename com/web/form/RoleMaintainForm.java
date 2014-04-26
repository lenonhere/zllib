package com.web.form;

import org.apache.struts.action.ActionForm;

public class RoleMaintainForm extends ActionForm {

	private static final long serialVersionUID = 2577724851497802012L;
	String roleId;
	String roleName;
	String roleCode;
	String menuIdSet;
	String personIdSet;
	String comment;
	String personInfo;
	String menuInfo;

	public String getPersonIdSet() {
		return personIdSet;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public String getComment() {
		return comment;
	}

	public String getMenuIdSet() {
		return menuIdSet;
	}

	public void setPersonIdSet(String personIdSet) {
		this.personIdSet = personIdSet;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setMenuIdSet(String menuIdSet) {
		this.menuIdSet = menuIdSet;
	}
}
