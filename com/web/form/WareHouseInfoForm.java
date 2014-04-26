package com.web.form;

import org.apache.struts.action.ActionForm;

public class WareHouseInfoForm extends ActionForm {

	private static final long serialVersionUID = 1186998415975409445L;
	private String whId;
	private String whCode; // 仓库代码
	private String whName;
	private String whAddress;
	private String whCapability;
	private String whCargo;
	private String whTelephone;
	private String information;

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getWhAddress() {
		return whAddress;
	}

	public void setWhAddress(String whAddress) {
		this.whAddress = whAddress;
	}

	public String getWhCapability() {
		return whCapability;
	}

	public void setWhCapability(String whCapability) {
		this.whCapability = whCapability;
	}

	public String getWhCargo() {
		return whCargo;
	}

	public void setWhCargo(String whCargo) {
		this.whCargo = whCargo;
	}

	public String getWhTelephone() {
		return whTelephone;
	}

	public void setWhTelephone(String whTelephone) {
		this.whTelephone = whTelephone;
	}
}
