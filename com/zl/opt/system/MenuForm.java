package com.zl.opt.system;

import org.apache.struts.action.ActionForm;

public class MenuForm extends ActionForm {

	public MenuForm() {
	}

	private String menuCode;

	private String id;

	private String menuName;

	private String parentCode;

	private String parentName;

	private String type;

	private String level;

	private String operatesn;

	private String counts;

	private String isMenu;

	private String isActive;

	private String description;

	private String ordertag;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperatesn() {
		return operatesn;
	}

	public void setOperatesn(String operatesn) {
		this.operatesn = operatesn;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getOrdertag() {
		return ordertag;
	}

	public void setOrdertag(String menuOrder) {
		this.ordertag = menuOrder;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
