package com.web.form;

import org.apache.struts.action.ActionForm;

public class SycompanyInfoForm extends ActionForm {

	private static final long serialVersionUID = 7658575992000767764L;
	private String sycompSystId;
	private String sycompCode;
	private String sycompName;
	private String distSystId;
	private String sycompAlias;
	private String isActive;
	private String isProvince;
	private String sycompGradeId;
	private String address;
	private String compPhone;
	private String linkman;
	private String linkmanPhone;
	private String bankSystid;
	private String bankAccount;
	private String taxNo;
	private String memberNo;
	private String fax;
	private String mailAddress;
	private String arriveStation;
	private String telegraph;
	private String bpcsCode;
	private String transType;
	private String balanceType;
	private String orderTag;
	private String dataCent_Code;
	private String postcode;
	private String bankName;
	private String contractCode;
	private String area_Code;
	private String areaName;
	private String transDays;
	private String sycompany_Code;
	private String cserpcode;
	private String inkeycity;

	private String dutyleadid;
	private String dutyleadtype;
	private String dutyleadname;
	private String personName;

	public String getCrtdate() {
		return crtdate;
	}

	public void setCrtdate(String crtdate) {
		this.crtdate = crtdate;
	}

	private String crtdate;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDutyleadid() {
		return dutyleadid;
	}

	public void setDutyleadid(String dutyleadid) {
		this.dutyleadid = dutyleadid;
	}

	public String getDutyleadname() {
		return dutyleadname;
	}

	public void setDutyleadname(String dutyleadname) {
		this.dutyleadname = dutyleadname;
	}

	public String getDutyleadtype() {
		return dutyleadtype;
	}

	public void setDutyleadtype(String dutyleadtype) {
		this.dutyleadtype = dutyleadtype;
	}

	public String getinkeycity() {
		return inkeycity;
	}

	public void setinkeycity(String inkeycity) {
		this.inkeycity = inkeycity;
	}

	public String getTransDays() {
		return transDays;
	}

	public void setTransDays(String transDays) {
		this.transDays = transDays;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public SycompanyInfoForm() {
	}

	public String getDistSystId() {
		return distSystId;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getIsProvince() {
		return isProvince;
	}

	public String getSycompAlias() {
		return sycompAlias;
	}

	public String getSycompCode() {
		return sycompCode;
	}

	public String getSycompGradeId() {
		return sycompGradeId;
	}

	public String getSycompName() {
		return sycompName;
	}

	public String getSycompSystId() {
		return sycompSystId;
	}

	public void setSycompSystId(String sycompSystId) {
		this.sycompSystId = sycompSystId;
	}

	public void setSycompName(String sycompName) {
		this.sycompName = sycompName;
	}

	public void setSycompGradeId(String sycompGradeId) {
		this.sycompGradeId = sycompGradeId;
	}

	public void setSycompCode(String sycompCode) {
		this.sycompCode = sycompCode;
	}

	public void setSycompAlias(String sycompAlias) {
		this.sycompAlias = sycompAlias;
	}

	public void setIsProvince(String isProvince) {
		this.isProvince = isProvince;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setDistSystId(String distSystId) {
		this.distSystId = distSystId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArriveStation() {
		return arriveStation;
	}

	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankSystid() {
		return bankSystid;
	}

	public void setBankSystid(String bankSystid) {
		this.bankSystid = bankSystid;
	}

	public String getBpcsCode() {
		return bpcsCode;
	}

	public void setBpcsCode(String bpcsCode) {
		this.bpcsCode = bpcsCode;
	}

	public String getCompPhone() {
		return compPhone;
	}

	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}

	public String getDataCent_Code() {
		return dataCent_Code;
	}

	public void setDataCent_Code(String dataCent_Code) {
		this.dataCent_Code = dataCent_Code;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(String orderTag) {
		this.orderTag = orderTag;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTelegraph() {
		return telegraph;
	}

	public void setTelegraph(String telegraph) {
		this.telegraph = telegraph;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getArea_Code() {
		return area_Code;
	}

	public void setArea_Code(String area_Code) {
		this.area_Code = area_Code;
	}

	public String getsycompany_Code() {
		return sycompany_Code;
	}

	public void setsycompany_Code(String sycompany_Code) {
		this.sycompany_Code = sycompany_Code;
	}

	public String getCserpcode() {
		return cserpcode;
	}

	public void setCserpcode(String cserpcode) {
		this.cserpcode = cserpcode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
