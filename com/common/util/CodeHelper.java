package com.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public class CodeHelper {

	// 查询XML文档，将符合条件的代码生成弹出式下拉列表的html
	public String getComboHTMLFromXML(Document document, String condition) {

		StringBuffer result = new StringBuffer();
		try {

			// 构造查询条件
			StringBuffer sf = new StringBuffer("/Zomml/Code[starts-with(@PY,'")
					.append(condition).append("') or starts-with(@Name,'")
					.append(condition).append("') or starts-with(@ID,'")
					.append(condition).append("')]");
			// 查询
			List list = document.selectNodes(sf.toString());
			int row = 0;
			// 根据查询结果构造成HTML内容
			if (list != null && list.size() > 0) {
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					String codeName = element.attribute("Name").getValue();
					String codeID = element.attribute("ID").getValue();
					result.append(
							"<tr><td onmouseover=HoverCell('on',this)   onmouseout=HoverCell('off',this)")
							.append(" onclick=returnItemValue('")
							.append(codeName).append("',").append(row)
							.append(")>").append(codeName).append("(")
							.append(codeID).append(")")
							.append("<input type='hidden'  name='CODE_VALUE")
							.append(row).append("' value='").append(codeID)
							.append("'/></td></tr>");
					row = row + 1;
				}
			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public List getIDFromXML(Document document, String condition) {
		List result = new ArrayList();
		try {
			// 有条件才查
			if (condition != null && condition.length() > 0) {
				// 查询
				StringBuffer sf = new StringBuffer("/Nci/Code[@Name='").append(
						condition).append("']");
				List list = document.selectNodes(sf.toString());

				int row = 0;
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Element element = (Element) list.get(i);
						result.add(element.attribute("ID").getValue());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List getCodeListFromXML(Document document) {
		List result = new ArrayList();
		try {
			// 查询
			StringBuffer sf = new StringBuffer("/Zooml/Code");
			List list = document.selectNodes(sf.toString());

			int row = 0;
			if (list != null) {
				int length = list.size();
				for (int i = 0; i < length; i++) {
					Element element = (Element) list.get(i);
					Code code = new Code();
					code.setCodeName(element.attribute("Name").getValue());
					code.setCodeValue(element.attribute("ID").getValue());
					result.add(code);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 从XML文档中获得代码值对应的代码名称
	public String getNameFromXML(Document document, String condition) {
		String result = "";
		try {
			// 有条件才查
			if (condition != null && condition.length() > 0) {
				// 查询
				StringBuffer sf = new StringBuffer("/Nci/Code[@ID='").append(
						condition).append("']");
				List list = document.selectNodes(sf.toString());

				int row = 0;
				if (list != null && list.size() > 0) {
					Element element = (Element) list.get(0);
					result = element.attribute("Name").getValue();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getChildNodesFromXML(Document document) throws Exception {
		// 定义
		StringBuffer result = new StringBuffer();

		try {
			StringBuffer sf = new StringBuffer("/Nci/Code");
			List list = document.selectNodes(sf.toString());

			int row = 0;
			if (list != null) {
				int length = list.size();
				for (int i = 0; i < length; i++) {
					Element element = (Element) list.get(i);
					String id = element.attribute("ID").getValue();
					String name = element.attribute("Name").getValue();
					result.append("<tr><td noWrap>")
							.append("<IMG height=12 width=12 src='")
							.append("/jfw/images/empty.gif' border=0><IMG id='Button_")
							.append(id)
							.append("' style='CURSOR: hand'  src='")
							.append("/jfw/images/minus.gif' border=0>")
							.append("<a href='#' onclick=\"ReturnSearchResult('")
							.append(name).append("|").append(id)
							.append("');\">").append(name).append("|")
							.append(id).append("</a></td></tr>")
							.append("<TR ><td noWrap  id='expand_").append(id)
							.append("'  style='display:none'></td></tr>");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
		}

		return result.toString();
	}
}
