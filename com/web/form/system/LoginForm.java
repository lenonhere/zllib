package com.web.form.system;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	public LoginForm() {
	}

	private String personCode = null;

	private String password = null;

	private String loginType;

	private String newpassword = null;

	private String newpasswordcheck = null;

	private String oldpassword = null;

	private String checkCode;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getNewpasswordcheck() {
		return newpasswordcheck;
	}

	public void setNewpasswordcheck(String newpasswordcheck) {
		this.newpasswordcheck = newpasswordcheck;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getPassword() {
		return password;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	/**
	 * @return Returns the loginType.
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType
	 *            The loginType to set.
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

}
