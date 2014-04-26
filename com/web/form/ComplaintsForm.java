package com.web.form;

import org.apache.struts.action.ActionForm;

public class ComplaintsForm extends ActionForm {
	private static final long serialVersionUID = -6173896549549765649L;
	private String tsnr;

	public String getTsnr() {
		return tsnr;
	}

	public void setTsnr(String tsnr) {
		this.tsnr = tsnr;
	}

}
