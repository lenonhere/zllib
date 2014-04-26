package com.web.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class IndentCompForm extends ActionForm {

	private static final long serialVersionUID = 8855275497945592729L;
	private String formsycompsystid = "";
	private String formsycompcode = "";
	private String formsycompname = "";
	private String formdistsystid = "";
	private String formsycompalias = "";
	private String formisactive = "";

	private String detailXML;
	protected ArrayList details = null;
	private String deleteIDs = "";
	private String stoString = "";

	public String getFormsycompsystid() {
		return formsycompsystid;
	}

	public void setFormsycompsystid(String value) {
		this.formsycompsystid = value;
	}

	public String getFormsycompcode() {
		return formsycompcode;
	}

	public void setFormsycompcode(String value) {
		this.formsycompcode = value;
	}

	public String getFormsycompname() {
		return formsycompname;
	}

	public void setFormsycompname(String value) {
		this.formsycompname = value;
	}

	public String getFormdistsystid() {
		return formdistsystid;
	}

	public void setFormdistsystid(String value) {
		this.formdistsystid = value;
	}

	public String getFormsycompalias() {
		return formsycompalias;
	}

	public void setFormsycompalias(String value) {
		this.formsycompalias = value;
	}

	public String getFormisactive() {
		return formisactive;
	}

	public void setFormisactive(String value) {
		this.formisactive = value;
	}

	public ArrayList getDetails() {
		return details;
	}

	public void setDetails(ArrayList details) {
		this.details = details;
	}

	public String getDetailXML() {
		return detailXML;
	}

	public void setDetailXML(String detailXML) {
		this.detailXML = detailXML;
	}

	public void addDetail(Object detail) {
		if (details == null) {
			details = new ArrayList();
		}
		details.add(detail);
	}

	public String getDeleteIDs() {
		return deleteIDs;
	}

	public void setDeleteIDs(String deleteIDs) {
		this.deleteIDs = deleteIDs;
	}

	public String toString() {
		stoString = stoString + this.getClass().getName() + "  property value ";
		stoString = stoString + "\n detailXML=" + detailXML;

		stoString = stoString + "\n sycompsystid=" + formsycompsystid;

		stoString = stoString + "\n sycompcode=" + formsycompcode;
		stoString = stoString + "\n sycompname=" + formsycompname;
		stoString = stoString + "\n distsystid=" + formdistsystid;
		stoString = stoString + "\n sycompalias=" + formsycompalias;
		stoString = stoString + "\n isactive=" + formisactive;
		return stoString;
	}

}
