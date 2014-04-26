package com.zl.manage;

import org.apache.struts.action.ActionForm;

public class ChannelForm extends ActionForm {
	private static final long serialVersionUID = 5942632457225047811L;
	private int systemid;
	private String channel_name;
	private String desciption;
	private String templateid;
	private String opindex;
	private String op_top;
	private String sortorder;
	private int parentid;

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public String getOp_top() {
		return op_top;
	}

	public void setOp_top(String op_top) {
		this.op_top = op_top;
	}

	public String getOpindex() {
		return opindex;
	}

	public void setOpindex(String opindex) {
		this.opindex = opindex;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public int getSystemid() {
		return systemid;
	}

	public void setSystemid(int systemid) {
		this.systemid = systemid;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

}
