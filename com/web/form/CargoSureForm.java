package com.web.form;

import org.apache.struts.action.ActionForm;

/**
 * luogj 2006-06-13
 *
 * 提货单确认form
 *
 */
public class CargoSureForm extends ActionForm {

	private static final long serialVersionUID = -681402795651751279L;
	private String arrayType; // 订单排序方式
	private String warehouse; // 仓库
	private String warehousePosition; // 库位
	private String transportType; // 运输方式
	private String orderId; // 订单号
	private String cargoId; // 提货单号
	private String companyId; // 提货单位
	private String personId; // 操作人id
	private String sureDate; // 确认日期
	private String beginDate; // 开始时间
	private String endDate; // 结束时间
	private String shippingInfo; // 信息
	private String unitId; // 计量单位
	private String tobacco_id; // 计量单位
	private String revised_quantity; // 计量单位
	private String unitName; // 计量单位
	private String allocateprice; // 计量单位
	private String all_money; // 计量单位
	private String shippingId; // 计量单位
	private String shippingIdSet; // 计量单位
	private String cargoIdSet; // 计量单位
	private String arrival_time; // 计量单位
	private String fieldIdSet;
	private String cargoInfo;
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCargoInfo() {
		return cargoInfo;
	}

	public void setCargoInfo(String cargoInfo) {
		this.cargoInfo = cargoInfo;
	}

	public String getFieldIdSet() {
		return fieldIdSet;
	}

	public void setFieldIdSet(String fieldIdSet) {
		this.fieldIdSet = fieldIdSet;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public String getCargoIdSet() {
		return cargoIdSet;
	}

	public void setCargoIdSet(String cargoIdSet) {
		this.cargoIdSet = cargoIdSet;
	}

	public String getShippingIdSet() {
		return shippingIdSet;
	}

	public void setShippingIdSet(String shippingIdSet) {
		this.shippingIdSet = shippingIdSet;
	}

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getTobacco_id() {
		return tobacco_id;
	}

	public void setTobacco_id(String tobacco_id) {
		this.tobacco_id = tobacco_id;
	}

	public String getRevised_quantity() {
		return revised_quantity;
	}

	public void setRevised_quantity(String revised_quantity) {
		this.revised_quantity = revised_quantity;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAllocateprice() {
		return allocateprice;
	}

	public void setAllocateprice(String allocateprice) {
		this.allocateprice = allocateprice;
	}

	public String getAll_money() {
		return all_money;
	}

	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getArrayType() {
		return arrayType;
	}

	public void setArrayType(String arrayType) {
		this.arrayType = arrayType;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehousePosition() {
		return warehousePosition;
	}

	public void setWarehousePosition(String warehousePosition) {
		this.warehousePosition = warehousePosition;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCargoId() {
		return cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getpersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getSureDate() {
		return sureDate;
	}

	public void setSureDate(String sureDate) {
		this.sureDate = sureDate;
	}

}
