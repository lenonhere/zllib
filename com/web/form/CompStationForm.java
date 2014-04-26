package com.web.form;

import org.apache.struts.action.ActionForm;

public class CompStationForm extends ActionForm {
	private static final long serialVersionUID = 3545935787730605644L;
	private String personid;
	private String sycompsystid;
	private String sycompname;
	private String stationname;
	private String areaName;
	private String linkname;
	private String tel;
	private String addrdec;
	private String isdefault;
	private String memo;
	private String personname;
	private String infomation;
	private String salepersonid;
	private String tradepersonname;
	private String isactive;
	private String stationid;
	private String type;
	private String selecttype;
	private String persontype;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelecttype() {
		return selecttype;
	}

	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
	}

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getSalepersonid() {
		return salepersonid;
	}

	public void setSalepersonid(String salepersonid) {
		this.salepersonid = salepersonid;
	}

	public String getTradepersonname() {
		return tradepersonname;
	}

	public void setTradepersonname(String tradepersonname) {
		this.tradepersonname = tradepersonname;
	}

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public CompStationForm() {
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getSycompsystid() {
		return sycompsystid;
	}

	public void setSycompsystid(String sycompsystid) {
		this.sycompsystid = sycompsystid;
	}

	public String getSycompname() {
		return sycompname;
	}

	public void setSycompname(String sycompname) {
		this.sycompname = sycompname;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLinkname() {
		return linkname;
	}

	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddrdec() {
		return addrdec;
	}

	public void setAddrdec(String addrdec) {
		this.addrdec = addrdec;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPersontype() {
		return persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

}
