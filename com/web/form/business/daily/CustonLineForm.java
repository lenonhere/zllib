package com.web.form.business.daily;

import org.apache.struts.action.ActionForm;

public class CustonLineForm extends ActionForm {

	private static final long serialVersionUID = 424988418072660403L;
	private String shopid;
	private String sycompsystId;
	private String personid;
	private String shopcode;
	private String shopname;
	private String shopacreage;
	private String shoptype;
	private String tobacard;
	private String shopkeeper;
	private String address;
	private String phone;
	private String callnumber;
	private String postcode;
	private String date;
	private String companyId;
	private String memo;
	private String fieldIdSet;
	private String seatno = "";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCallnumber() {
		return callnumber;
	}

	public void setCallnumber(String callnumber) {
		this.callnumber = callnumber;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFieldIdSet() {
		return fieldIdSet;
	}

	public void setFieldIdSet(String fieldIdSet) {
		this.fieldIdSet = fieldIdSet;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getSeatno() {
		return seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public String getShopacreage() {
		return shopacreage;
	}

	public void setShopacreage(String shopacreage) {
		this.shopacreage = shopacreage;
	}

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getShopkeeper() {
		return shopkeeper;
	}

	public void setShopkeeper(String shopkeeper) {
		this.shopkeeper = shopkeeper;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShoptype() {
		return shoptype;
	}

	public void setShoptype(String shoptype) {
		this.shoptype = shoptype;
	}

	public String getSycompsystId() {
		return sycompsystId;
	}

	public void setSycompsystId(String sycompsystId) {
		this.sycompsystId = sycompsystId;
	}

	public String getTobacard() {
		return tobacard;
	}

	public void setTobacard(String tobacard) {
		this.tobacard = tobacard;
	}

}
