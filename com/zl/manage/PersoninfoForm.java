package com.zl.manage;

import org.apache.struts.action.ActionForm;

public class PersoninfoForm extends ActionForm {
	private static final long serialVersionUID = -7892416871986267663L;
	private int personid; // 系统唯一编码
	private String logincode; // 用户登陆编码
	private String password; // 登陆密码
	private String personname; // 用户名称
	private int persontype; // 用户类型1：一般用户0：超级用户
	private String phonecode; // 电话号码
	private int isactive;
	private String repassword;

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public String getLogincode() {
		return logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String _personname) {
		this.personname = _personname;
	}

	public int getPersontype() {
		return persontype;
	}

	public void setPersontype(int persontype) {
		this.persontype = persontype;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
}