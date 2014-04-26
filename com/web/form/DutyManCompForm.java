package com.web.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class DutyManCompForm extends ActionForm {
	private static final long serialVersionUID = 3020394126536600560L;

	private String dutyid = "";

	private String dutytype = "";

	private String personidSet = "";

	private String dutyidSet = "";

	private String personid = "";

	private String userId;

	private String compcode = "";

	private String compsystid = "";

	private String compsystidSet = "";

	private String areaCode;

	private String message = "";

	private String personName;

	private String detailXML;

	protected ArrayList details = null;

	private String deleteIDs = "";

	private String stoString = "";
	private String departId;

	private String budgetid = "";

	public String getBudgetid() {
		return budgetid;
	}

	public void setBudgetid(String budgetid) {
		this.budgetid = budgetid;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDutyid() {
		return dutyid;
	}

	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}

	public String getDutyidSet() {
		return dutyidSet;
	}

	public void setDutyidSet(String dutyidSet) {
		this.dutyidSet = dutyidSet;
	}

	public String getDutytype() {
		return dutytype;
	}

	public void setDutytype(String dutytype) {
		this.dutytype = dutytype;
	}

	public String getPersonidSet() {
		return personidSet;
	}

	public void setPersonidSet(String personidSet) {
		this.personidSet = personidSet;
	}

	public String getCompcode() {
		return compcode;
	}

	public void setCompcode(String compcode) {
		this.compcode = compcode;
	}

	public String getCompsystid() {
		return compsystid;
	}

	public void setCompsystid(String compsystid) {
		this.compsystid = compsystid;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getStoString() {
		return stoString;
	}

	public void setStoString(String stoString) {
		this.stoString = stoString;
	}

	public DutyManCompForm() {
	}

	public String getpersonid() {
		return personid;
	}

	public void setpersonid(String value) {
		this.personid = value;
	}

	public String getmessage() {
		return message;
	}

	public String getUserId() {
		return userId;
	}

	public void setmessage(String value) {
		this.message = value;
	}

	public String getcompcode() {
		return compcode;
	}

	public void setcompcode(String value) {
		this.compcode = value;
	}

	public String getcompsystid() {
		return compsystid;
	}

	public void setcompsystid(String value) {
		this.compsystid = value;
	}

	public String getCompsystidSet() {
		return compsystidSet;
	}

	public void setCompsystidSet(String value) {
		this.compsystidSet = value;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public ArrayList getDetails() {
		return details;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

		return stoString;
	}
}
