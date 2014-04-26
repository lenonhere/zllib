package com.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 *
 * @version 1.0
 * @author lu huanda
 */

public class HalfProtocolForm extends ActionForm {

	private static final long serialVersionUID = -3319796426328118571L;
	private String personID;
	private String in_confirm;
	/** 合同期:年 */
	private String planYear;
	/** 合同期:上半年 or 下半年 */
	private String planHalf;
	/** 省份ID */
	private String provinceId;
	/** 计量单位ID */
	private String unitId;
	/** 计量单位名 */
	private String unitName;
	/** 销售计算起始年月 */
	private String beginYear;
	private String beginMonth;
	private String endYear;
	private String endMonth;
	/** 选中公司ID */
	private String companyId;
	/** 公司名字 */
	private String companyName;
	// 选中合同申请单编号
	private String planCode;
	private String planInfo;
	// 审核否的标识："1"-已审核；"0"或其它-未审核
	private String isConfirmed;
	private String confirmTag;/* 业务员还是审核者标记 */
	private String sycompanySet;/* 选中公司的集合 */
	private String plancodeSet;/* 选中订单的集合 */
	private String type;/* 控制各个阶段 */
	private String halfprotocolCode;
	private String halfprotocolInfo;
	private String halfprotocolId;
	private String behalfId;
	private String behalfName;
	private String provinceInfo;
	private String shippingDate;
	private String Dest;
	private String DestName;
	private String infomation;
	private String infomation1;
	private String infomation2;
	private String begDate;
	private String endDate;

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// Reset values are provided as samples only. Change as appropriate.
		planYear = null;
		planHalf = null;
		beginYear = null;
		beginMonth = null;
		endYear = null;
		endMonth = null;
		provinceId = null;
		infomation = null;
		infomation1 = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		// errors.add("field", new
		// org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;
	}

	public String getBegDate() {
		return begDate;
	}

	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String string) {
		unitId = string;
	}

	public String getProvinceInfo() {
		return provinceInfo;
	}

	public void setProvinceInfo(String string) {
		provinceInfo = string;
	}

	public String gethalfprotocolCode() {
		return halfprotocolCode;
	}

	public void sethalfprotocolCode(String string) {
		halfprotocolCode = string;
	}

	public String gethalfprotocolId() {
		return halfprotocolId;
	}

	public void sethalfprotocolId(String string) {
		halfprotocolId = string;
	}

	public String gethalfprotocolInfo() {
		return halfprotocolInfo;
	}

	public void sethalfprotocolInfo(String string) {
		halfprotocolInfo = string;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String string) {
		provinceId = string;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyId(String string) {
		companyId = string;
	}

	public void setCompanyName(String string) {
		companyName = string;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String string) {
		unitName = string;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(String beginYear) {
		this.beginYear = beginYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getPlanHalf() {
		return planHalf;
	}

	public void setPlanHalf(String planHalf) {
		this.planHalf = planHalf;
	}

	public String getPlanYear() {
		return planYear;
	}

	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanInfo() {
		return planInfo;
	}

	public void setPlanInfo(String planInfo) {
		this.planInfo = planInfo;
	}

	public String getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getConfirmTag() {
		return confirmTag;
	}

	public void setConfirmTag(String confirmTag) {
		this.confirmTag = confirmTag;
	}

	public String getSycompanySet() {
		return sycompanySet;
	}

	public void setSycompanySet(String sycompanySet) {
		this.sycompanySet = sycompanySet;
	}

	public String getPlancodeSet() {
		return plancodeSet;
	}

	public void setPlancodeSet(String plancodeSet) {
		this.plancodeSet = plancodeSet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBehalfId() {
		return behalfId;
	}

	public void setBehalfId(String string) {
		behalfId = string;
	}

	public String getBehalfName() {
		return behalfName;
	}

	public void setBehalfName(String string) {
		behalfName = string;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String string) {
		shippingDate = string;
	}

	public String getDest() {
		return Dest;
	}

	public void setDest(String string) {
		Dest = string;
	}

	public String getDestName() {
		return DestName;
	}

	public void setDestName(String string) {
		DestName = string;
	}

	public String getHalfprotocolCode() {
		return halfprotocolCode;
	}

	public void setHalfprotocolCode(String halfprotocolCode) {
		this.halfprotocolCode = halfprotocolCode;
	}

	public String getHalfprotocolId() {
		return halfprotocolId;
	}

	public void setHalfprotocolId(String halfprotocolId) {
		this.halfprotocolId = halfprotocolId;
	}

	public String getHalfprotocolInfo() {
		return halfprotocolInfo;
	}

	public void setHalfprotocolInfo(String halfprotocolInfo) {
		this.halfprotocolInfo = halfprotocolInfo;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getIn_confirm() {
		return in_confirm;
	}

	public void setIn_confirm(String in_confirm) {
		this.in_confirm = in_confirm;
	}

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getInfomation1() {
		return infomation1;
	}

	public void setInfomation1(String infomation1) {
		this.infomation1 = infomation1;
	}

	public String getInfomation2() {
		return infomation2;
	}

	public void setInfomation2(String infomation2) {
		this.infomation2 = infomation2;
	}

}
