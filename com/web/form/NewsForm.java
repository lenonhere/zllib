package com.web.form;

import org.apache.struts.action.ActionForm;

public class NewsForm extends ActionForm {

	private static final long serialVersionUID = -2116127267565480366L;
	private String newsid;
	private String title;
	private String content;
	private String adddate;
	private String enddate;
	private String inputperson;

	public String getNewsid() {
		return newsid;
	}

	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getInputperson() {
		return inputperson;
	}

	public void setInputperson(String inputperson) {
		this.inputperson = inputperson;
	}

}
