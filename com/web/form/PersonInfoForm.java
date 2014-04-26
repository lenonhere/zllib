package com.web.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class PersonInfoForm extends ActionForm {
	private static final long serialVersionUID = -5848317267405081777L;
	private String receivepersonid = "";
	private String receivername = "";

	private String personid = "";

	private String personcode = "";

	private String personname = "";

	private String personusername = "";

	private String password = "";

	private String oldpassword = "";

	private String passwordcheck = "";

	private String departid = "";

	private String isactive = "";

	private String isworker = "";

	private String workarea = "";

	private String workno = "";

	private String mobile = "";

	private String mobile2 = "";

	private String birthday;

	private String officephone;

	private String idcode;

	private String email;

	private String address;

	private String detailXML;

	private String officenumber;

	protected ArrayList details = null;

	private String deleteIDs = "";

	private String stoString = "";

	private String rang = "1";

	private String persontype = "";

	private String persontypename = "";

	private String isduty = "";

	private String pinyin = "";

	private String belong = "";

	public String getIsduty() {
		return isduty;
	}

	public void setIsduty(String isduty) {
		this.isduty = isduty;
	}

	public String getPersontype() {
		return persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public String getPersontypename() {
		return persontypename;
	}

	public void setPersontypename(String persontypename) {
		this.persontypename = persontypename;
	}

	public String getrang() {
		return rang;
	}

	public void setrang(String rangTemp) {
		rang = rangTemp;
	}

	private String companyinfoid = null;

	public String getcompanyinfoid() {
		return companyinfoid;
	}

	public void setcompanyinfoid(String companyinfoTemp) {
		companyinfoid = companyinfoTemp;
	}

	public PersonInfoForm() {
	}

	// personid
	public String getpersonid() {
		return personid;
	}

	public void setpersonid(String value) {
		this.personid = value;
	}

	// personcode
	public String getpersoncode() {
		return personcode;
	}

	public void setpersoncode(String value) {
		this.personcode = value;
	}

	// personname
	public String getpersonname() {
		return personname;
	}

	public void setpersonname(String value) {
		this.personname = value;
	}

	// personusername
	public String getpersonusername() {
		return personusername;
	}

	public void setpersonusername(String value) {
		this.personusername = value;
	}

	// password
	public String getpassword() {
		return password;
	}

	public void setpassword(String value) {
		this.password = value;
	}

	// departmentid
	public String getdepartid() {
		return departid;
	}

	public void setdepartid(String value) {
		this.departid = value;
	}

	// isactive
	public String getisactive() {
		return isactive;
	}

	public void setisactive(String value) {
		this.isactive = value;
	}

	// iswork
	public String getisworker() {
		return isworker;
	}

	public void setisworker(String value) {
		this.isworker = value;
	}

	// workarea
	public String getworkarea() {
		return workarea;
	}

	public void setworkarea(String value) {
		this.workarea = value;
	}

	// workno
	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getCompanyinfoid() {
		return companyinfoid;
	}

	public void setCompanyinfoid(String companyinfoid) {
		this.companyinfoid = companyinfoid;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getIsworker() {
		return isworker;
	}

	public void setIsworker(String isworker) {
		this.isworker = isworker;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersoncode() {
		return personcode;
	}

	public void setPersoncode(String personcode) {
		this.personcode = personcode;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getPersonusername() {
		return personusername;
	}

	public void setPersonusername(String personusername) {
		this.personusername = personusername;
	}

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public String getStoString() {
		return stoString;
	}

	public void setStoString(String stoString) {
		this.stoString = stoString;
	}

	public String getWorkarea() {
		return workarea;
	}

	public void setWorkarea(String workarea) {
		this.workarea = workarea;
	}

	public ArrayList getDetails() {
		return details;
	}

	public void setDetails(ArrayList details) {
		this.details = details;
	}

	public String getDetailXML() {
		return detailXML;
	}

	public void setDetailXML(String detailXML) {
		this.detailXML = detailXML;
	}

	public void addDetail(Object detail) {
		if (details == null) {
			details = new ArrayList();
		}
		details.add(detail);
	}

	public String getDeleteIDs() {
		return deleteIDs;
	}

	public void setDeleteIDs(String deleteIDs) {
		this.deleteIDs = deleteIDs;
	}

	public String toString() {
		stoString = stoString + this.getClass().getName() + "  property value ";
		stoString = stoString + "\n detailXML=" + detailXML;
		stoString = stoString + "\n personid=" + personid;
		stoString = stoString + "\n personcode=" + personcode;
		stoString = stoString + "\n personname=" + personname;
		stoString = stoString + "\n personusername=" + personusername;
		stoString = stoString + "\n password=" + password;
		stoString = stoString + "\n departid=" + departid;
		stoString = stoString + "\n isactive=" + isactive;
		stoString = stoString + "\n isworker=" + isworker;
		stoString = stoString + "\n workarea=" + workarea;
		stoString = stoString + "\n workno=" + workno;
		return stoString;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getPasswordcheck() {
		return passwordcheck;
	}

	public void setPasswordcheck(String passwordcheck) {
		this.passwordcheck = passwordcheck;
	}

	public String getOfficenumber() {
		return officenumber;
	}

	public void setOfficenumber(String officenumber) {
		this.officenumber = officenumber;
	}

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param pinyin
	 *            the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return the belong
	 */
	public String getBelong() {
		return belong;
	}

	/**
	 * @param belong
	 *            the belong to set
	 */
	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getReceivepersonid() {
		return receivepersonid;
	}

	public void setReceivepersonid(String receivepersonid) {
		this.receivepersonid = receivepersonid;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

}