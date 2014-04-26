package com.zl.base.core.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.taglib.grid.BaseGrid;
import com.zl.base.core.taglib.grid.BaseInputTag;
import com.zl.base.core.util.StringBufferTool;

public class MyGrid extends BaseGrid {

	private static final long serialVersionUID = 9188881144476759482L;

	private static final Logger log = Logger.getLogger(MyGrid.class);

	String caption = null;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zl.base.core.taglib.grid.BaseGrid#doEndTag()
	 */
	@SuppressWarnings("deprecation")
	public int doEndTag() throws JspException {
		// TODO doEndTag()
		if (caption != null) {
			processCaption();
		}

		ResponseUtils.write(pageContext, generateReturnHeadIds());
		ResponseUtils.write(pageContext, generateReturnHeadIdsWithOrder());
		return super.doEndTag();
	}

	/**
	 * @return
	 */
	protected String generateReturnHeadIds() {
		// TODO generateReturnHeadIds()
		String separator = getcolsplitstring();
		String fields = "";

		for (int i = 0; i < columns.size(); i++) {
			BaseInputTag column = (BaseInputTag) columns.get(i);
			if ("true".equalsIgnoreCase(column.getIsReturn())) {
				String property = column.getProperty();
				fields += separator + (property == null ? "" : property);
			}
		}

		StringBufferTool sbT = new StringBufferTool(new StringBuffer());
		sbT.appendln("<SCRIPT LANGUAGE=javascript>");

		sbT.appendln("function getHeadIds(offset) {");
		sbT.appendln("    var separator=\"" + separator + "\";");
		sbT.appendln("    var rtnValue = \"\";");
		sbT.appendln("    var fields =(\""
				+ fields.substring(separator.length())
				+ "\").split(separator);");
		sbT.appendln("    if(offset == null || offset < 0) { ");
		sbT.appendln("        offset = 0; ");
		sbT.appendln("    } ");
		sbT.appendln("    for(var j=offset; j<fields.length; j++) {");
		sbT.appendln("        rtnValue += separator + fields[j]");
		sbT.appendln("    }");
		sbT.appendln("    return rtnValue.substr(separator.length, rtnValue.length)");
		sbT.appendln("}");
		sbT.appendln("</SCRIPT>");
		return sbT.toString();
	}

	/**
	 * @return
	 */
	protected String generateReturnHeadIdsWithOrder() {
		// TODO generateReturnHeadIdsWithOrder()
		String separator = getcolsplitstring();

		String fields = "";

		//收集需要返回到后台的字段
		for (int i = 0; i < columns.size(); i++) {
			BaseInputTag column = (BaseInputTag) columns.get(i);
			if ("true".equalsIgnoreCase(column.getIsReturn())) {
				String property = column.getProperty();
				fields += separator + (property == null ? "" : property);
			}
		}

		String rowSeparator = this.getrowsplitstring();
		StringBufferTool sbT = new StringBufferTool(new StringBuffer());
		sbT.appendln("<SCRIPT LANGUAGE=javascript>");

		sbT.appendln("function getHeadIdsOrder(offset) {");
		sbT.appendln("    var separator=\"" + separator + "\";");
		sbT.appendln("    var rowseparator=\"" + rowSeparator + "\";");
		sbT.appendln("    var index = 1;");
		sbT.appendln("    var rtnValue = \"\";");
		sbT.appendln("    var fields =(\""
				+ fields.substring(separator.length())
				+ "\").split(separator);");
		sbT.appendln("    if(offset == null || offset < 0) { ");
		sbT.appendln("        offset = 0; ");
		sbT.appendln("    } ");
		sbT.appendln("    for(var j=offset; j<fields.length; j++) {");
		sbT.appendln("        rtnValue += separator + fields[j] + rowseparator + index");
		sbT.appendln("        index ++; ");
		sbT.appendln("    }");
		sbT.appendln("    return rtnValue.substr(separator.length, rtnValue.length)");
		sbT.appendln("}");
		sbT.appendln("</SCRIPT>");
		return sbT.toString();
	}

	protected String generateReturnDetail() {
		String fields = "";
		StringBufferTool sbT = new StringBufferTool(new StringBuffer());
		sbT.appendln("<SCRIPT LANGUAGE=javascript>");
		for (int i = 0; i < columns.size(); i++) {
			BaseInputTag col = (BaseInputTag) columns.get(i);
			String temp = col.generateReturnValue();
			if (!temp.trim().equalsIgnoreCase("")) {
				if (fields.equals("")) {
					fields = col.getproperty();
				} else {
					fields = fields + "," + col.getproperty();
				}
			}
		}
		sbT.appendln("function getDetail(){");
		sbT.appendln("var rowsplitstring=\"" + getrowsplitstring() + "\";");
		sbT.appendln("var colsplitstring=\"" + getcolsplitstring() + "\";");
		sbT.appendln("var fields =(\"" + fields + "\").split(\",\");");
		sbT.appendln("var strReturn=\"\";");
		sbT.appendln("var row=\"\";");
		sbT.appendln("for(var i=1;i<=" + getProperty().trim()
				+ ".rows.length;i++){");
		sbT.appendln("    for(var j =0;j<fields.length;j++){");
		sbT.appendln("\tvar valuetemp=\"\";");
		sbT.appendln("\tvar obj=document.all[fields[j]][i];");
		sbT.appendln("\tif(obj.tagName==\"INPUT\"){");
		sbT.appendln("\t    if(obj.type==\"text\"){");
		sbT.appendln("\t\tvaluetemp=obj.value;");
		sbT.appendln("\t    }else if(obj.type==\"checkbox\"){");
		sbT.appendln("\t\tif(obj.checked==true)");
		sbT.appendln("\t\t    valuetemp=\"1\";");
		sbT.appendln("\t\telse");
		sbT.appendln("\t\t    valuetemp=\"0\";");
		sbT.appendln("\t    }");
		sbT.appendln("\t}else if(obj.tagName==\"SELECT\"){");
		sbT.appendln("\t    valuetemp=obj.value;");
		sbT.appendln("\t}");
		sbT.appendln("\tif(j==0)");
		sbT.appendln("\t    row =valuetemp;");
		sbT.appendln("\telse");
		sbT.appendln("\t    row =row+colsplitstring+valuetemp;");
		sbT.appendln("    }");
		sbT.appendln("    if(i==1)");
		sbT.appendln("\tstrReturn=row;");
		sbT.appendln("    else");
		sbT.appendln("\tstrReturn=strReturn+rowsplitstring+row;");
		sbT.appendln("}");
		sbT.appendln("return strReturn;");
		sbT.appendln("}");
		sbT.appendln("</SCRIPT>");
		return sbT.toString();
	}

	/**
	 * 解析表头数据集caption.list
	 */
	protected void processCaption() {
		// TODO processCaption()
		Object captions = pageContext.findAttribute(caption);
		if (captions == null || !(captions instanceof List)) {
			return;
		}

		int tabIndex = 1;
		List<Object> captionList =  (List<Object>) captions;
		log.debug("CaptionList.size::" + captionList.size());
		for (int i = 0; i < captionList.size(); i++) {
			Object bean = captionList.get(i);
			BaseInputTag textTag = new MyText();

			String style = "";

			Object capital = "";
			Object isReturn = "";
			Object readonly = "";
			Object property = "";
			Object datatype = "";
			Object width = "";
			Object format = "";

			try {
				capital = PropertyUtils.getProperty(bean, "capital");
			} catch (Exception e) {
			}

			try {
				isReturn = PropertyUtils.getProperty(bean, "isreturn");
			} catch (Exception e) {
			}

			try {
				readonly = PropertyUtils.getProperty(bean, "readonly");
			} catch (Exception e) {
			}

			try {
				property = PropertyUtils.getProperty(bean, "property");
			} catch (Exception e) {
			}

			try {
				datatype = PropertyUtils.getProperty(bean, "datatype");
			} catch (Exception e) {
			}

			try {
				width = PropertyUtils.getProperty(bean, "width");
			} catch (Exception e) {
			}

			try {
				format = PropertyUtils.getProperty(bean, "format");
			} catch (Exception e) {
			}

			if (!"".equals(capital.toString())) {
				textTag.setCapital(capital.toString());
			}
			if (!"".equals(isReturn.toString())) {
				textTag.setIsReturn(isReturn.toString());
			}
			if (!"".equals(readonly.toString())) {
				boolean only = Boolean.valueOf(readonly.toString())
						.booleanValue();
				textTag.setReadonly(only);
				if (!only) {
					textTag.setTabindex(String.valueOf(tabIndex++));
				} else {
					textTag.setTabindex("-1");
				}
			} else {
				textTag.setTabindex("-1");
			}
			if (!"".equals(property.toString())) {
				textTag.setProperty(property.toString());
			}
			if ("1".equals(datatype.toString())) {
				textTag.setOnkeypress("checkcharacter();");
			}
			if ("0".equals(width.toString())) {
				style = "display:none";
			} else if (!"".equals(width.toString())) {
				style = "width:" + width.toString();
			}
			if (!"".equals(style.toString())) {
				textTag.setStyle(style);
			}
			if (!"".equals(format.toString())) {
				textTag.setformat(format.toString());
			}
			this.columns.add(textTag);
		}
	}
}
