package com.web.form;

import org.apache.struts.action.ActionForm;

public class TobaccoInfoForm extends ActionForm {

	private static final long serialVersionUID = -6041645836915631989L;
	private String tobaSystId;
	private String tobaCode;
	private String dataCentCode;
	private String tobaName;
	private String quickCode;
	private String orderTag;
	private String isActive;
	private String crmTobaCode;
	private String bpcsCode;
	private String brandCode;
	private String factoryCode;
	private String bpcsDatabase;
	private String priceLevelId;
	private String tradePrice;
	private String retailPrice;
	private String allocatePrice;
	private String classCode;
	private String bpcsDB;
	private String rank;
	private String style;
	private String spec;
	private String provinceid;
	private String provincecode;
	private String bandcode;
	private String hy_eas_code;
	private String dwdm;
	private String serialcode;/* ����ϵ�д��� */
	private String gj_code;
	private String includePrice;
	private String contractPrice;// ��˰��

	private String year;
	private String month;
	private String reportstatu;

	private String banddish; /* ��֧���� */
	private String wpjc; /* ��� Ӳ�� */
	private String cpdm; /* ���۷�Χ ʡ��ʡ�� */

	private String selecttobacco;
	private String unselecttobacco;

	public String getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(String contractPrice) {
		this.contractPrice = contractPrice;
	}

	public String getReportstatu() {
		return reportstatu;
	}

	public void setReportstatu(String reportstatu) {
		this.reportstatu = reportstatu;
	}

	public String getincludePrice() {
		return includePrice;
	}

	public void setincludePrice(String includePrice) {
		this.includePrice = includePrice;
	}

	public String getgj_code() {
		return gj_code;
	}

	public void setgj_code(String gj_code) {
		this.gj_code = gj_code;
	}

	public String getSerialcode() {
		return serialcode;
	}

	public void setSerialcode(String serialcode) {
		this.serialcode = serialcode;
	}

	public String getAllocatePrice() {
		return allocatePrice;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public String getCrmTobaCode() {
		return crmTobaCode;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getPriceLevelId() {
		return priceLevelId;
	}

	public String getQuickCode() {
		return quickCode;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public String getTobaCode() {
		return tobaCode;
	}

	public String getTobaSystId() {
		return tobaSystId;
	}

	public String getTradePrice() {
		return tradePrice;
	}

	public String getTobaName() {
		return tobaName;
	}

	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}

	public void setTobaSystId(String tobaSystId) {
		this.tobaSystId = tobaSystId;
	}

	public void setTobaCode(String tobaCode) {
		this.tobaCode = tobaCode;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public void setQuickCode(String quickCode) {
		this.quickCode = quickCode;
	}

	public void setPriceLevelId(String priceLevelId) {
		this.priceLevelId = priceLevelId;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public void setCrmTobaCode(String crmTobaCode) {
		this.crmTobaCode = crmTobaCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public void setAllocatePrice(String allocatePrice) {
		this.allocatePrice = allocatePrice;
	}

	public void setTobaName(String tobaName) {
		this.tobaName = tobaName;
	}

	public String getBpcsCode() {
		return bpcsCode;
	}

	public void setBpcsCode(String bpcsCode) {
		this.bpcsCode = bpcsCode;
	}

	public String getBpcsDatabase() {
		return bpcsDatabase;
	}

	public void setBpcsDatabase(String bpcsDatabase) {
		this.bpcsDatabase = bpcsDatabase;
	}

	public String getBpcsDB() {
		return bpcsDB;
	}

	public void setBpcsDB(String bpcsDB) {
		this.bpcsDB = bpcsDB;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDataCentCode() {
		return dataCentCode;
	}

	public void setDataCentCode(String dataCentCode) {
		this.dataCentCode = dataCentCode;
	}

	public String getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(String orderTag) {
		this.orderTag = orderTag;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}

	public String getBandcode() {
		return bandcode;
	}

	public void setBandcode(String bandcode) {
		this.bandcode = bandcode;
	}

	public String gethy_eas_code() {
		return hy_eas_code;
	}

	public void sethy_eas_code(String hy_eas_code) {
		this.hy_eas_code = hy_eas_code;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBanddish() {
		return banddish;
	}

	public void setBanddish(String banddish) {
		this.banddish = banddish;
	}

	public String getCpdm() {
		return cpdm;
	}

	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}

	public String getGj_code() {
		return gj_code;
	}

	public void setGj_code(String gj_code) {
		this.gj_code = gj_code;
	}

	public String getHy_eas_code() {
		return hy_eas_code;
	}

	public void setHy_eas_code(String hy_eas_code) {
		this.hy_eas_code = hy_eas_code;
	}

	public String getIncludePrice() {
		return includePrice;
	}

	public void setIncludePrice(String includePrice) {
		this.includePrice = includePrice;
	}

	public String getWpjc() {
		return wpjc;
	}

	public void setWpjc(String wpjc) {
		this.wpjc = wpjc;
	}

	public String getSelecttobacco() {
		return selecttobacco;
	}

	public void setSelecttobacco(String selecttobacco) {
		this.selecttobacco = selecttobacco;
	}

	public String getUnselecttobacco() {
		return unselecttobacco;
	}

	public void setUnselecttobacco(String unselecttobacco) {
		this.unselecttobacco = unselecttobacco;
	}

}
