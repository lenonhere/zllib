package com.web.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class PublicCodesForm extends ActionForm {

	private static final long serialVersionUID = 2574433654572071154L;
	private String formparentclassid = "";
	private String formclassid = "";
	private String formclasscode = "";
	private String formclassname = "";
	private String formparentclassname = "";
	private String formisactive = "";
	private String formfieldname = "";
	private String formcomment = "";

	private String detailXML;
	protected ArrayList details = null;
	private String deleteIDs = "";
	private String stoString = "";

	public PublicCodesForm() {
	}

	public String getFormparentclassid() {
		return formparentclassid;
	}

	public void setFormparentclassid(String value) {
		this.formparentclassid = value;
	}

	public String getFormclassid() {
		return formclassid;
	}

	public void setFormclassid(String value) {
		this.formclassid = value;
	}

	public String getFormclasscode() {
		return formclasscode;
	}

	public void setFormclasscode(String value) {
		this.formclasscode = value;
	}

	public String getFormclassname() {
		return formclassname;
	}

	public void setFormclassname(String value) {
		this.formclassname = value;
	}

	public String getFormparentclassname() {
		return formparentclassname;
	}

	public void setFormparentclassname(String value) {
		this.formparentclassname = value;
	}

	public String getFormisactive() {
		return formisactive;
	}

	public void setFormisactive(String value) {
		this.formisactive = value;
	}

	public String getFormfieldname() {
		return formfieldname;
	}

	public void setFormfieldname(String value) {
		this.formfieldname = value;
	}

	public String getFormcomment() {
		return formcomment;
	}

	public void setFormcomment(String value) {
		this.formcomment = value;
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
		stoString = stoString + "\n parentclassid=" + formparentclassid;
		stoString = stoString + "\n classid=" + formclassid;
		stoString = stoString + "\n classcode=" + formclasscode;
		stoString = stoString + "\n classname=" + formclassname;
		stoString = stoString + "\n parentclassname=" + formparentclassname;
		stoString = stoString + "\n isactive=" + formisactive;
		stoString = stoString + "\n fieldname=" + formfieldname;
		stoString = stoString + "\n comment=" + formcomment;
		return stoString;
	}

}
