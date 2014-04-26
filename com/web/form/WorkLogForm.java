package com.web.form;

import org.apache.struts.action.ActionForm;

public class WorkLogForm extends ActionForm {

	private static final long serialVersionUID = 2475115118864867718L;
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String taskid;
	private String content;

}
