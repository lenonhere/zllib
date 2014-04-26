package com.zl.base.core.taglib.grid;

import com.zl.base.core.util.StringBufferTool;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

public class BaseGrid extends BaseHandlerTag {
	protected Vector columns = new Vector();
	protected String property = null;
	protected String collection = null;
	protected String oldStyleClass = "tableDataHit";
	protected String newStyleClass = "tableData";
	protected String height = "200";
	protected String width = "400";
	protected String footer = "true";
	private String rowsplitstring = ",";
	private String colsplitstring = ";";
	private boolean autoaddline = false;
	private String border = "1";
	private String headercolor = "#000000";
	private String headercolor2 = "#FF0000";

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		this.property = paramString;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String paramString) {
		this.collection = paramString;
	}

	public String getOldStyleClass() {
		return this.oldStyleClass;
	}

	public void setOldStyleClass(String paramString) {
		this.oldStyleClass = paramString;
	}

	public String getNewStyleClass() {
		return this.newStyleClass;
	}

	public void setNewStyleClass(String paramString) {
		this.newStyleClass = paramString;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String paramString) {
		this.height = paramString;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String paramString) {
		this.width = paramString;
	}

	public String getFooter() {
		return this.footer;
	}

	public void setFooter(String paramString) {
		this.footer = paramString;
	}

	public String getrowsplitstring() {
		return this.rowsplitstring;
	}

	public void setrowsplitstring(String paramString) {
		this.rowsplitstring = paramString;
	}

	public String getcolsplitstring() {
		return this.colsplitstring;
	}

	public void setcolsplitstring(String paramString) {
		this.colsplitstring = paramString;
	}

	public boolean getautoaddline() {
		return this.autoaddline;
	}

	public void setautoaddline(boolean paramBoolean) {
		this.autoaddline = paramBoolean;
	}

	public String getborder() {
		return this.border;
	}

	public void setborder(String paramString) {
		this.border = paramString;
	}

	public String getheadercolor() {
		return this.headercolor;
	}

	public void setheadercolor(String paramString) {
		this.headercolor = paramString;
	}

	public String getheadercolor2() {
		return this.headercolor2;
	}

	public void setheadercolor2(String paramString) {
		this.headercolor2 = paramString;
	}

	public int doStartTag() throws JspException {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<style>");
		localStringBufferTool.appendln(".HeadClass {");
		localStringBufferTool
				.appendln("BORDER-RIGHT: medium none; BORDER-TOP: medium none; FONT-SIZE: 10pt; BORDER-LEFT: medium none; CURSOR: default; COLOR: black; BORDER-BOTTOM: medium none; BACKGROUND-COLOR: transparent");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</style>");
		ResponseUtils.write(this.pageContext, localStringBufferTool.toString());
		this.pageContext.setAttribute("Grid", this);
		return 1;
	}

	public int doEndTag() throws JspException {
		this.pageContext.removeAttribute("Grid");
		StringBuffer localStringBuffer = new StringBuffer("");
		head(localStringBuffer);
		Object localObject1 = null;
		Object localObject2 = null;
		if (this.collection != null) {
			Iterator localIterator = getIterator(this.collection, null);
			if (localIterator != null) {
				if ((this.footer != "false") && (localIterator.hasNext()))
					localObject1 = localIterator.next();
				while (localIterator.hasNext()) {
					localStringBuffer.append("<tr class=\"");
					localStringBuffer.append(this.oldStyleClass);
					localStringBuffer.append("\" id=\"tdt");
					localStringBuffer
							.append("\" onclick=lightOn() onkeydown=checkUpDown()>");
					if (this.footer != "false") {
						localObject2 = localObject1;
						if (localIterator.hasNext())
							localObject1 = localIterator.next();
					} else {
						localObject2 = localIterator.next();
					}
					Object localObject3 = null;
					for (int i = 0; i < this.columns.size(); i++) {
						BaseInputTag localBaseInputTag = (BaseInputTag) this.columns
								.get(i);
						String str = localBaseInputTag.getProperty();
						try {
							localObject3 = PropertyUtils.getProperty(
									localObject2, str);
							if (localObject3 == null)
								localObject3 = "";
						} catch (Exception localException) {
							localObject3 = "";
						}
						localBaseInputTag.generateRows(localStringBuffer,
								String.valueOf(localObject3));
					}
				}
				localStringBuffer.append("</tr>");
			}
		}
		localStringBuffer.append("</table>");
		localStringBuffer.append("</div>");
		if ((this.footer != "false") && (this.collection != null))
			foot(localStringBuffer, localObject1);
		localStringBuffer.append("</div>");
		generateOnscroll(localStringBuffer);
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		ResponseUtils.write(this.pageContext, generateAddLine());
		ResponseUtils.write(this.pageContext, generateReturnValue());
		ResponseUtils.write(this.pageContext, generateReturnSQL());
		ResponseUtils.write(this.pageContext, generateLightOn());
		ResponseUtils.write(this.pageContext, generateGetObjectIndex());
		ResponseUtils.write(this.pageContext, generateLight());
		ResponseUtils.write(this.pageContext, generateDelLine());
		ResponseUtils.write(this.pageContext, generateDeLight());
		ResponseUtils.write(this.pageContext, generateUpDown());
		ResponseUtils.write(this.pageContext, generateReturnDetail());
		release();
		return 6;
	}

	protected String generateAddLine() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("var intTotalRow=0");
		localStringBufferTool.appendln("intTotalRow=" + this.property
				+ ".rows.length");
		localStringBufferTool.append("var ");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln("_currentLine=0");
		localStringBufferTool.appendln("function addLine(){");
		localStringBufferTool.appendln("var Item");
		localStringBufferTool.appendln("intTotalRow++");
		localStringBufferTool.append("newRow=");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append(".insertRow(");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length)");
		localStringBufferTool.appendln("newRow.id=\"tdt\"");
		localStringBufferTool.appendln("newRow.onclick=lightOn");
		localStringBufferTool.appendln("newRow.ln=intTotalRow;");
		localStringBufferTool.appendln("newRow.bgColor=\"#e0e0e0\";");
		localStringBufferTool.append("newRow.className=\"");
		localStringBufferTool.append(this.newStyleClass);
		localStringBufferTool.appendln("\";");
		localStringBufferTool.appendln("newRow.onkeydown=checkUpDown;");
		String str = "cell";
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			str = str + i;
			localStringBufferTool.append(str);
			localStringBufferTool.append("=newRow.insertCell(");
			localStringBufferTool.append(i);
			localStringBufferTool.appendln(");");
			localStringBufferTool.append(str);
			localStringBufferTool.appendln(".ln=intTotalRow;");
			localStringBufferTool.append(str);
			localStringBufferTool.append(".innerHTML='");
			localStringBufferTool.append(localBaseInputTag.generateInnerHTML());
			localStringBufferTool.appendln("'");
		}
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateReturnValue() {
		String str1 = "";
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("function getDetailXml(){");
		localStringBufferTool.appendln("var strReturn");
		localStringBufferTool.appendln("strReturn =\"<details>\"");
		localStringBufferTool.append("for(var i=1;i<=");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length;i++){");
		localStringBufferTool.append("strReturn +=");
		localStringBufferTool.append("\"<detail>\"");
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			String str2 = localBaseInputTag.generateReturnValue();
			if (!str2.trim().equalsIgnoreCase("")) {
				localStringBufferTool.append("+");
				localStringBufferTool.append(str2);
				if (str1.equals(""))
					str1 = localBaseInputTag.getproperty();
				else
					str1 = str1 + "," + localBaseInputTag.getproperty();
			}
		}
		localStringBufferTool.append("+");
		localStringBufferTool.append("\"</detail>\"");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("strReturn +=\"</details>\"");
		localStringBufferTool.appendln("return strReturn");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("function getDetail2(){");
		localStringBufferTool.appendln("var rowsplitstring=\""
				+ this.rowsplitstring + "\";");
		localStringBufferTool.appendln("var colsplitstring=\""
				+ this.colsplitstring + "\";");
		localStringBufferTool.appendln("var fields =(\"" + str1
				+ "\").split(\",\")");
		localStringBufferTool.appendln("var strReturn");
		localStringBufferTool.appendln("strReturn =\"\"");
		localStringBufferTool.appendln("for(var j =0;j<fields.length;j++){");
		localStringBufferTool.appendln("\tvar row=\"\";");
		localStringBufferTool.appendln("\tfor(var i=1;i<="
				+ getProperty().trim() + ".rows.length;i++){");
		localStringBufferTool.appendln("\t     ");
		localStringBufferTool.appendln("\t var valuetemp=\"\";");
		localStringBufferTool.appendln("var obj=document.all[fields[j]][i]");
		localStringBufferTool.appendln("if(obj.tagName==\"INPUT\"){");
		localStringBufferTool.appendln("if(obj.type==\"text\"){");
		localStringBufferTool.appendln("valuetemp=obj.value;");
		localStringBufferTool.appendln("}else if(obj.type==\"checkbox\"){");
		localStringBufferTool
				.appendln("\tif(obj.checked==true){valuetemp=\"1\";}else{valuetemp=\"0\";}");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("}else if (obj.tagName==\"SELECT\"){");
		localStringBufferTool.appendln("\t\t\tvaluetemp=obj.value;");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("\t\tif(i==1)");
		localStringBufferTool.appendln("\t\t\trow =valuetemp;");
		localStringBufferTool.appendln("\t\telse");
		localStringBufferTool
				.appendln("\t\t\trow =row+rowsplitstring+valuetemp;");
		localStringBufferTool.appendln("\t}");
		localStringBufferTool.appendln("\tif(j==0)");
		localStringBufferTool.appendln("\t\tstrReturn=row;");
		localStringBufferTool.appendln("\telse");
		localStringBufferTool
				.appendln("\t\tstrReturn=strReturn+colsplitstring+row;");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("return strReturn");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("function getsum(field){");
		localStringBufferTool.appendln("    var returnvalue=0;");
		localStringBufferTool.appendln("\tvar obj =document.all[field]");
		localStringBufferTool.appendln("\tif(obj!=null){");
		localStringBufferTool.appendln("\t\tfor(var i=1;i<="
				+ getProperty().trim() + ".rows.length;i++){");
		localStringBufferTool
				.appendln("\t\t\t\tvar cellvalue =document.all[field][i].value;");
		localStringBufferTool
				.appendln("\t\t\t\tvar valuetemp=parseFloat(cellvalue);");
		localStringBufferTool
				.appendln("\t\t\t\tif(isNaN(valuetemp)) valuetemp=0;");
		localStringBufferTool
				.appendln("\t\t\t\treturnvalue=returnvalue+valuetemp;");
		localStringBufferTool.appendln("\t\t}");
		localStringBufferTool.appendln("\t\treturn returnvalue;");
		localStringBufferTool.appendln("\t}else{");
		localStringBufferTool.appendln("\t\treturn 0;");
		localStringBufferTool.appendln("\t}");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateReturnDetail() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("function getDetail(){");
		localStringBufferTool.appendln("var strReturn");
		localStringBufferTool.appendln("strReturn =\"\"");
		localStringBufferTool.append("for(var i=1;i<=");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length;i++){");
		localStringBufferTool.append("strReturn +=");
		int i = 0;
		for (int j = 0; j < this.columns.size(); j++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(j);
			String str = localBaseInputTag.generateReturnDetail();
			if (!str.trim().equalsIgnoreCase("")) {
				if (i != 0) {
					localStringBufferTool.append("+");
					localStringBufferTool.append("\"" + this.colsplitstring
							+ "\"");
					localStringBufferTool.append("+");
				}
				i = 1;
				localStringBufferTool.append(str);
			}
		}
		localStringBufferTool.appendln("");
		localStringBufferTool.append("if(i!=");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append(".rows.length){");
		localStringBufferTool.append("strReturn +=");
		localStringBufferTool.append("\"" + this.rowsplitstring + "\"");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("return strReturn");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateReturnSQL() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("function getDetailSQL(){");
		localStringBufferTool.appendln("var strReturn");
		localStringBufferTool.appendln("strReturn =\"\"");
		localStringBufferTool.append("for(var i=1;i<=");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length;i++){");
		localStringBufferTool.append("strReturn +=");
		int i = 0;
		for (int j = 0; j < this.columns.size(); j++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(j);
			String str = localBaseInputTag.generateReturnSQL();
			if (!str.trim().equalsIgnoreCase("")) {
				if (i == 0) {
					localStringBufferTool.append(str);
				} else {
					localStringBufferTool.append("+");
					localStringBufferTool.append("\",\"");
					localStringBufferTool.append("+");
					localStringBufferTool.append(str);
				}
				i = 1;
			}
		}
		localStringBufferTool.append("+");
		localStringBufferTool.append("\";\"");
		localStringBufferTool.appendln("}");
		localStringBufferTool
				.appendln("strReturn=strReturn.substr(0,strReturn.length-1)");
		localStringBufferTool.appendln("return strReturn");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateLightOn() {
		StringBufferTool stringbuffertool = new StringBufferTool(
				new StringBuffer());
		String s = property + "_currentLine";
		stringbuffertool.appendln("<SCRIPT LANGUAGE=javascript>");
		stringbuffertool.appendln("function lightOn(){");
		stringbuffertool.appendln("");
		stringbuffertool.appendln("var item");
		stringbuffertool.appendln("var obj");
		stringbuffertool.appendln("var index");
		stringbuffertool
				.appendln("if(event.srcElement.tagName.toUpperCase()==\"TD\"){");
		stringbuffertool.appendln("return");
		stringbuffertool.appendln("}");
		stringbuffertool
				.appendln("if(event.srcElement.tagName.toUpperCase()==\"TR\"){");
		stringbuffertool.appendln("obj =event.srcElement");
		stringbuffertool.appendln("index = obj.rowIndex");
		stringbuffertool.appendln("} else {");
		stringbuffertool.appendln("obj =event.srcElement");
		stringbuffertool.appendln("index = getObjectIndex(obj)");
		stringbuffertool.appendln("}");
		stringbuffertool.append("if(index==");
		stringbuffertool.append(s);
		stringbuffertool.appendln("){");
		stringbuffertool.appendln("return ");
		stringbuffertool.appendln("}");
		stringbuffertool.append("if(");
		stringbuffertool.append(s);
		stringbuffertool.appendln("!=0&&");
		stringbuffertool.append(s);
		stringbuffertool.appendln("<=document.all." + property
				+ ".rows.length){");
		for (int i = 0; i < columns.size(); i++) {
			BaseInputTag baseinputtag = (BaseInputTag) columns.get(i);
			stringbuffertool.append(baseinputtag.generateLightOn(newStyleClass,
					s));
		}

		stringbuffertool.appendln("}");
		for (int j = 0; j < columns.size(); j++) {
			BaseInputTag baseinputtag1 = (BaseInputTag) columns.get(j);
			stringbuffertool.append(baseinputtag1.generateLightOn(
					oldStyleClass, "index"));
		}

		stringbuffertool.append(s);
		stringbuffertool.appendln("=index");
		stringbuffertool.appendln("return true");
		stringbuffertool.appendln("}");
		stringbuffertool.appendln("</SCRIPT>");
		return stringbuffertool.toString();
	}

	// protected String generateLightOn() {
	// StringBufferTool localStringBufferTool = new StringBufferTool(
	// new StringBuffer());
	// String str = this.property + "_currentLine";
	// localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
	// localStringBufferTool.appendln("function lightOn(){");
	// localStringBufferTool.appendln("");
	// localStringBufferTool.appendln("var item");
	// localStringBufferTool.appendln("var obj");
	// localStringBufferTool.appendln("var index");
	// localStringBufferTool
	// .appendln("if(event.srcElement.tagName.toUpperCase()==\"TD\"){");
	// localStringBufferTool.appendln("return");
	// localStringBufferTool.appendln("}");
	// localStringBufferTool
	// .appendln("if(event.srcElement.tagName.toUpperCase()==\"TR\"){");
	// localStringBufferTool.appendln("obj =event.srcElement");
	// localStringBufferTool.appendln("index = obj.rowIndex");
	// localStringBufferTool.appendln("} else {");
	// localStringBufferTool.appendln("obj =event.srcElement");
	// localStringBufferTool.appendln("index = getObjectIndex(obj)");
	// localStringBufferTool.appendln("}");
	// localStringBufferTool.append("if(index==");
	// localStringBufferTool.append(str);
	// localStringBufferTool.appendln("){");
	// localStringBufferTool.appendln("return ");
	// localStringBufferTool.appendln("}");
	// localStringBufferTool.append("if(");
	// localStringBufferTool.append(str);
	// localStringBufferTool.appendln("!=0&&");
	// localStringBufferTool.append(str);
	// localStringBufferTool.appendln("<=document.all." + this.property
	// + ".rows.length){");
	// BaseInputTag localBaseInputTag;
	// for (int i = 0; i < this.columns.size(); i++) {
	// localBaseInputTag = (BaseInputTag) this.columns.get(i);
	// localStringBufferTool.append(localBaseInputTag.generateLightOn(
	// this.newStyleClass, str));
	// }
	// localStringBufferTool.appendln("}");
	// for (i = 0; i < this.columns.size(); i++) {
	// localBaseInputTag = (BaseInputTag) this.columns.get(i);
	// localStringBufferTool.append(localBaseInputTag.generateLightOn(
	// this.oldStyleClass, "index"));
	// }
	// localStringBufferTool.append(str);
	// localStringBufferTool.appendln("=index");
	// localStringBufferTool.appendln("return true");
	// localStringBufferTool.appendln("}");
	// localStringBufferTool.appendln("</SCRIPT>");
	// return localStringBufferTool.toString();
	// }

	protected String generateLight() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		String str = this.property + "_currentLine";
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("function light(index){");
		localStringBufferTool.appendln("var item");
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			localStringBufferTool.append(localBaseInputTag.generateLightOn(
					this.oldStyleClass, "index"));
		}
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateDeLight() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		String str = this.property + "_currentLine";
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.appendln("function deLight(index){");
		localStringBufferTool.appendln("var item");
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			localStringBufferTool.append(localBaseInputTag.generateLightOn(
					this.newStyleClass, "index"));
		}
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</SCRIPT>");
		return localStringBufferTool.toString();
	}

	protected String generateGetObjectIndex() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.appendln("<script language=\"JavaScript\">");
		localStringBufferTool.appendln("function getObjectIndex(objValue){");
		localStringBufferTool.appendln("var obj");
		localStringBufferTool.appendln("obj =objValue");
		localStringBufferTool.appendln("objTemp =document.all[obj.name]");
		localStringBufferTool.appendln("var index,i");
		localStringBufferTool.appendln("for( i=0;i<objTemp.length;i++){");
		localStringBufferTool.appendln("if(obj==objTemp[i]){");
		localStringBufferTool.appendln("index=i;");
		localStringBufferTool.appendln("return index;");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</script>");
		return localStringBufferTool.toString();
	}

	protected String generateDelLine() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		String str = this.property + "_currentLine";
		localStringBufferTool.appendln("<script language=\"JavaScript\">");
		localStringBufferTool.appendln("function delLine()");
		localStringBufferTool.appendln("{ // 删除一行");
		localStringBufferTool.append("    if (");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length<1){");
		localStringBufferTool.appendln("      return");
		localStringBufferTool.appendln("     } ");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append(".deleteRow(");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln("-1)");
		localStringBufferTool.appendln("intTotalRow--");
		localStringBufferTool.append("if(");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln(">1){");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln("--");
		localStringBufferTool.appendln("    }");
		localStringBufferTool.append("    if (");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length>=1){");
		localStringBufferTool.append("\tlight(");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln(");");
		localStringBufferTool.appendln("     }");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</script>");
		return localStringBufferTool.toString();
	}

	protected String generateUpDown() {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		String str = this.property + "_currentLine";
		localStringBufferTool.appendln("<script language=\"JavaScript\">");
		localStringBufferTool.appendln("function checkUpDown()");
		localStringBufferTool.appendln("{//检测上下键");
		localStringBufferTool.appendln("\tvar obj ");
		localStringBufferTool.appendln("obj =event.srcElement");
		localStringBufferTool.appendln("var index = getObjectIndex(obj)");
		localStringBufferTool.append("\tvar length = document.all.");
		localStringBufferTool.append(this.property);
		localStringBufferTool.appendln(".rows.length");
		localStringBufferTool.appendln("\tvar keyCode = event.keyCode\t");
		localStringBufferTool.appendln("\tvar objTemp=document.all[obj.name]");
		localStringBufferTool.appendln("\tif(keyCode==38){//up");
		localStringBufferTool.appendln("\t\tif(index >= 2){//非第一行");
		localStringBufferTool.appendln("\t\tindex--");
		localStringBufferTool.appendln("\t\tobjTemp[index].focus()");
		localStringBufferTool.appendln("\t\tlight(index)");
		localStringBufferTool.appendln("\t\tdeLight(index+1)");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln("--");
		localStringBufferTool.appendln("\t\t}");
		localStringBufferTool.appendln("\t}else{if(keyCode==40){");
		localStringBufferTool.appendln("\t\tif(index <= length-1){//非最后一行");
		localStringBufferTool.appendln("\t\tindex++");
		localStringBufferTool.appendln("\t\tobjTemp[index].focus()");
		localStringBufferTool.appendln("\t\tlight(index)");
		localStringBufferTool.appendln("\t\tdeLight(index-1)");
		localStringBufferTool.append(str);
		localStringBufferTool.appendln("++");
		localStringBufferTool.appendln("\t\t}");
		if ((getOnkeydown() != null) && (getOnkeydown().trim().length() > 0))
			localStringBufferTool.appendln(getOnkeydown());
		localStringBufferTool.appendln("}else{ if(keyCode==13){");
		localStringBufferTool.appendln("          var Grid = document.all."
				+ this.property);
		localStringBufferTool.appendln("          var currentindex ");
		localStringBufferTool.appendln("          var nextline=false;");
		localStringBufferTool
				.appendln("                        var nextindex=-1");
		localStringBufferTool
				.appendln("                        var myGrid_currentLineTemp");
		localStringBufferTool
				.appendln("                          myGrid_currentLineTemp= index-1");
		localStringBufferTool
				.appendln("                         for(cellindex =0;cellindex<Grid.rows(0).cells.length;cellindex++){");
		localStringBufferTool
				.appendln("                                 if(Grid.rows(myGrid_currentLineTemp).cells(cellindex).all[0].name==obj.name){");
		localStringBufferTool
				.appendln("                                         currentindex =cellindex");
		localStringBufferTool.appendln("                                 }");
		localStringBufferTool.appendln("                         }");
		localStringBufferTool
				.appendln("                         if (currentindex>=Grid.rows(0).cells.length){");
		localStringBufferTool
				.appendln("                                 nextline=false;");
		localStringBufferTool.appendln("                         }else{");
		localStringBufferTool
				.appendln("                                 //查出下一个得到焦点的对象\t\t\t");
		localStringBufferTool
				.appendln("                                for (rowindex =0;rowindex<=1;rowindex++){");
		localStringBufferTool
				.appendln("                                        if (rowindex ==1 ) currentindex=-1");
		localStringBufferTool
				.appendln("                                        if (myGrid_currentLineTemp+rowindex<length){");
		localStringBufferTool
				.appendln("                                                for(cellindex=currentindex+1;cellindex<Grid.rows(0).cells.length;cellindex++){");
		localStringBufferTool
				.appendln("                                                        if(Grid.rows(myGrid_currentLineTemp+rowindex).cells(cellindex).all[0].readOnly!=true&&");
		localStringBufferTool
				.appendln("                                                                Grid.rows( myGrid_currentLineTemp+rowindex).cells(cellindex).all[0].type!=\"hidden\"&&");
		localStringBufferTool
				.appendln("                                                                Grid.rows( myGrid_currentLineTemp+rowindex).cells(cellindex).all[0].style.display!=\"none\"){");
		localStringBufferTool
				.appendln("                                                                        nextindex=cellindex");
		localStringBufferTool
				.appendln("                                                                        if(rowindex ==1) nextline=true;");
		localStringBufferTool
				.appendln("                                                                        break;");
		localStringBufferTool
				.appendln("                                                                }");
		localStringBufferTool
				.appendln("                                                        }");
		localStringBufferTool
				.appendln("                                                }");
		localStringBufferTool
				.appendln("                                        if (nextindex>=0) break;");
		localStringBufferTool.appendln("                                }");
		localStringBufferTool
				.appendln("                                if( nextindex>=0){");
		localStringBufferTool
				.appendln("                                        if(nextline==true){");
		localStringBufferTool
				.appendln("                                               light(index+1)");
		localStringBufferTool
				.appendln("                                               deLight(index) ");
		localStringBufferTool
				.appendln("                                                "
						+ this.property + "_currentLine++");
		localStringBufferTool
				.appendln("                                                index=index+1;");
		localStringBufferTool
				.appendln("                                        }");
		localStringBufferTool
				.appendln("                                        Grid.rows(index-1).cells(nextindex).all[0].focus();");
		localStringBufferTool.appendln("                                }");
		if (this.autoaddline == true) {
			localStringBufferTool
					.appendln("                                else{");
			localStringBufferTool
					.appendln("                                    addLine();");
			localStringBufferTool
					.appendln("                                checkUpDown();");
			localStringBufferTool.appendln("                                }");
		}
		localStringBufferTool.appendln("                         }");
		localStringBufferTool.appendln("                }");
		localStringBufferTool.appendln("        }");
		localStringBufferTool.appendln("        }");
		localStringBufferTool.appendln("}");
		localStringBufferTool.appendln("</script>");
		return localStringBufferTool.toString();
	}

	private void head(StringBuffer paramStringBuffer) {
		paramStringBuffer.append("<DIV style=\"");
		paramStringBuffer.append(" border:" + getborder()
				+ "px solid #0294C7; ");
		paramStringBuffer.append(" overflow:hidden ");
		if (this.width != null) {
			paramStringBuffer.append(";width=");
			paramStringBuffer.append(this.width);
			paramStringBuffer.append("px");
		}
		paramStringBuffer.append(" \">");
		paramStringBuffer.append(" <div style=\"POSITION: relative");
		paramStringBuffer.append(" \">");
		paramStringBuffer
				.append("<table bgcolor=\"#85A8C3\" style=\"POSITION: relative;left:0px\" cellpadding=0 cellspacing=1 border=0");
		paramStringBuffer.append(" name=\"");
		paramStringBuffer.append(this.property);
		paramStringBuffer.append("\"");
		paramStringBuffer.append(">");
		paramStringBuffer
				.append("<tr id=tdt borderColorLight=black bgColor=buttonface borderColorDark=white>");
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			localBaseInputTag.genetateHead(paramStringBuffer, "Hello",
					getheadercolor(), getheadercolor2());
		}
		paramStringBuffer.append("</tr>");
		paramStringBuffer.append("</table>");
		paramStringBuffer.append("</DIV>");
		paramStringBuffer
				.append(" <div onscroll=\"div_onscroll(this)\" style=\"overflow:auto;POSITION: relative");
		if (this.height != null) {
			paramStringBuffer.append(";height=");
			paramStringBuffer.append(this.height);
			paramStringBuffer.append("px");
		}
		if (this.width != null) {
			paramStringBuffer.append(";width=");
			paramStringBuffer.append(this.width);
			paramStringBuffer.append("px");
		}
		paramStringBuffer.append(" \">");
		paramStringBuffer
				.append("<table bgcolor=\"#85A8C3\" cellpadding=0 cellspacing=1 border=0 id=\"");
		paramStringBuffer.append(this.property);
		paramStringBuffer.append("\">");
	}

	private void foot(StringBuffer paramStringBuffer, Object paramObject) {
		paramStringBuffer.append(" <div style=\"POSITION: relative");
		paramStringBuffer.append(" \">");
		paramStringBuffer
				.append("<table bgcolor=\"#85A8C3\" style=\"POSITION: relative;left:0px\" cellpadding=0 cellspacing=1 border=0");
		paramStringBuffer.append(" name=");
		paramStringBuffer.append(this.property);
		paramStringBuffer.append("\"");
		paramStringBuffer.append(">");
		paramStringBuffer.append("<tr class=\"");
		paramStringBuffer.append(this.oldStyleClass);
		paramStringBuffer.append("\" id=\"tdt\"");
		paramStringBuffer.append(">");
		Object localObject = null;
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			String str = localBaseInputTag.getProperty();
			try {
				localObject = PropertyUtils.getProperty(paramObject, str);
				if (localObject == null)
					localObject = "";
			} catch (Exception localException) {
				localObject = "";
			}
			localBaseInputTag.genetateFoot(paramStringBuffer,
					String.valueOf(localObject));
		}
		paramStringBuffer.append("</tr>");
		paramStringBuffer.append("</table>");
		paramStringBuffer.append("</DIV>");
	}

	private void generateCheckBoxOnclick(StringBuffer paramStringBuffer) {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				paramStringBuffer);
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.append("function checkbox_onclick(obj){");
		localStringBufferTool.append("");
		localStringBufferTool.append("\tif( parseInt(obj.value) == 0){");
		localStringBufferTool.append("\t\tobj.value = 1;");
		localStringBufferTool.append("\t\t}");
		localStringBufferTool.append("\telse{");
		localStringBufferTool.append("\t\tobj.value = 0;");
		localStringBufferTool.append("\t}");
		localStringBufferTool.append("}");
		localStringBufferTool.appendln("</script>");
	}

	private void generateOnscroll(StringBuffer paramStringBuffer) {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				paramStringBuffer);
		localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
		localStringBufferTool.append("function div_onscroll(obj){");
		localStringBufferTool
				.append("obj.parentElement.children[0].children[0].style.pixelLeft=1-obj.scrollLeft;");
		if (this.footer != "false")
			localStringBufferTool
					.append("obj.parentElement.children[2].children[0].style.pixelLeft=1-obj.scrollLeft;");
		localStringBufferTool.append("}");
		localStringBufferTool.appendln("</script>");
	}

	protected Iterator getIterator(String paramString1, String paramString2)
			throws JspException {
		String str = paramString1;
		if (str == null)
			str = "BEAN_KEY";
		Object localObject1 = this.pageContext.findAttribute(str);
		if (localObject1 == null)
			return null;
		Object localObject2 = localObject1;
		if (paramString2 != null)
			try {
				localObject2 = PropertyUtils.getProperty(localObject1,
						paramString2);
				if (localObject2 == null)
					throw new JspException(messages.getMessage(
							"getter.property", paramString2));
			} catch (IllegalAccessException localIllegalAccessException) {
				throw new JspException(messages.getMessage("getter.access",
						paramString2, paramString1));
			} catch (InvocationTargetException localInvocationTargetException) {
				Throwable localThrowable = localInvocationTargetException
						.getTargetException();
				throw new JspException(messages.getMessage("getter.result",
						paramString2, localThrowable.toString()));
			} catch (NoSuchMethodException localNoSuchMethodException) {
				throw new JspException(messages.getMessage("getter.method",
						paramString2, paramString1));
			}
		if (localObject2.getClass().isArray())
			localObject2 = Arrays.asList((Object[]) localObject2);
		if ((localObject2 instanceof Collection))
			return ((Collection) localObject2).iterator();
		if ((localObject2 instanceof Iterator))
			return (Iterator) localObject2;
		if ((localObject2 instanceof Map))
			return ((Map) localObject2).entrySet().iterator();
		if ((localObject2 instanceof Enumeration))
			return new IteratorAdapter((Enumeration) localObject2);
		throw new JspException(messages.getMessage("optionsTag.iterator",
				localObject2.toString()));
	}

	public void release() {
		super.release();
		this.columns.removeAllElements();
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.grid.BaseGrid JD-Core Version: 0.6.1
 */