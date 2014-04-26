package com.web.form;

import org.apache.struts.action.ActionForm;

/**
 * 驾驶员信息 FORM luogj 2006-06-06
 *
 */
public class DriverInfoForm extends ActionForm {

	private static final long serialVersionUID = 2488647389962023616L;
	private String personId; // 系统编号
	private String passId; // 驾驶证
	private String idNo; // 身份证
	private String driverName; // 姓名
	private String driverAge; // 驾龄
	private String whName; // 所属部门--仓库
	private String whId; // 所属部门--仓库
	private String telephone; // 联系电话
	private String homeAddress; // 家庭地址
	private String otherInfo; // 其他情况备注

	// ///////////
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	// //////////////
	public String getPassId() {
		return passId;
	}

	public void setPassId(String passId) {
		this.passId = passId;
	}

	// ////////////
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	// //////
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverAge() {
		return driverAge;
	}

	public void setDriverAge(String driverAge) {
		this.driverAge = driverAge;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
}