package com.web.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DemosForm extends ActionForm {

	private static final long serialVersionUID = 7383458608764207923L;

	//
	private String name;
	private String code;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// DateTag
	private String date;
	private String startDate;
	private String endDate;
	private String year;
	private String startYear;
	private String endYear;
	private String selectedYear;
	private String month;
	private String startMonth;
	private String endMonth;
	private String selectedMonth;
	private String yearmonth;
	private String halfyear;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public String getHalfyear() {
		return halfyear;
	}

	public void setHalfyear(String halfyear) {
		this.halfyear = halfyear;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	// SelectTag
	private String selectOptions;
	private String selectGroupOptions;

	public String getSelectOptions() {
		return selectOptions;
	}

	public void setSelectOptions(String selectOptions) {
		this.selectOptions = selectOptions;
	}

	public String getSelectGroupOptions() {
		return selectGroupOptions;
	}

	public void setSelectGroupOptions(String selectGroupOptions) {
		this.selectGroupOptions = selectGroupOptions;
	}

	// com.qmx.taglibs.html.BaseColorpick
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	// UploadFile
	protected FormFile uploadFormFile;

	public FormFile getUploadFormFile() {
		return uploadFormFile;
	}

	public void setUploadFormFile(FormFile uploadFormFile) {
		this.uploadFormFile = uploadFormFile;
	}

	/* basetree */
	private String collention = null;
	private String nodeid = null;
	private String nodetext = null;
	private String nodeimage = null;
	private String nodeparentid = null;
	private String nodetype = null;
	private String nodeparenttype = null;
	private String url = null;
	private String enabled = null;

	public void setcollention(String paramString) {
		this.collention = paramString;
	}

	public String getcollention() {
		return this.collention;
	}

	public String getenabled() {
		return this.enabled;
	}

	public void setenabled(String paramString) {
		this.enabled = paramString;
	}

	public String getnodeid() {
		return this.nodeid;
	}

	public void setnodeid(String paramString) {
		this.nodeid = paramString;
	}

	public String getnodetext() {
		return this.nodetext;
	}

	public void setnodetext(String paramString) {
		this.nodetext = paramString;
	}

	public String getnodeimage() {
		return this.nodeimage;
	}

	public void setnodeimage(String paramString) {
		this.nodeimage = paramString;
	}

	public String getnodeparentid() {
		return this.nodeparentid;
	}

	public void setnodeparentid(String paramString) {
		this.nodeparentid = paramString;
	}

	public String getnodetype() {
		return this.nodetype;
	}

	public void setnodetype(String paramString) {
		this.nodetype = paramString;
	}

	public String getnodeparenttype() {
		return this.nodeparenttype;
	}

	public void setnodeparenttype(String paramString) {
		this.nodeparenttype = paramString;
	}

	public String geturl() {
		return this.url;
	}

	public void seturl(String paramString) {
		this.url = paramString;
	}

	/* chartform */
	private String caption = null;
	private String property = null;
	private String width = null;
	private String type = null;
	private String xvalue = null;
	private String value1 = null;
	private String value2 = null;
	private String value3 = null;
	private String value4 = null;
	private String value5 = "";
	private String value6 = "";
	private String value7 = "";
	private String value8 = "";
	private String detailXML;
	protected ArrayList details = null;
	private String deleteIDs = "";

	public String getcaption() {
		return this.caption;
	}

	public void setcaption(String paramString) {
		this.caption = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		this.property = paramString;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getxvalue() {
		return this.xvalue;
	}

	public void setxvalue(String paramString) {
		this.xvalue = paramString;
	}

	public String getvalue1() {
		return this.value1;
	}

	public void setvalue1(String paramString) {
		this.value1 = paramString;
	}

	public String getvalue2() {
		return this.value2;
	}

	public void setvalue2(String paramString) {
		this.value2 = paramString;
	}

	public String getvalue3() {
		return this.value3;
	}

	public void setvalue3(String paramString) {
		this.value3 = paramString;
	}

	public String getvalue4() {
		return this.value4;
	}

	public void setvalue4(String paramString) {
		this.value4 = paramString;
	}

	public String getvalue5() {
		return this.value5;
	}

	public void setvalue5(String paramString) {
		this.value5 = paramString;
	}

	public String getvalue6() {
		return this.value6;
	}

	public void setvalue6(String paramString) {
		this.value6 = paramString;
	}

	public String getvalue7() {
		return this.value7;
	}

	public void setvalue7(String paramString) {
		this.value7 = paramString;
	}

	public String getvalue8() {
		return this.value8;
	}

	public void setvalue8(String paramString) {
		this.value8 = paramString;
	}

	public ArrayList getDetails() {
		return this.details;
	}

	public void setDetails(ArrayList paramArrayList) {
		this.details = paramArrayList;
	}

	public String getDetailXML() {
		return this.detailXML;
	}

	public void setDetailXML(String paramString) {
		this.detailXML = paramString;
	}

	public void addDetail(Object paramObject) {
		if (this.details == null)
			this.details = new ArrayList();
		this.details.add(paramObject);
	}

	public String getDeleteIDs() {
		return this.deleteIDs;
	}

	public void setDeleteIDs(String paramString) {
		this.deleteIDs = paramString;
	}

}
