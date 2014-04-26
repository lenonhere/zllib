package com.zl.base.core.taglib.html;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.util.OptionHold;

public class WrTabsTag extends TagSupport {

	private static final long serialVersionUID = -5806005489689531480L;

	private static final Log logger = LogFactory.getLog(WrTabsTag.class);

	private String id;
	private String width = "100%";
	private String collection;
	private String textField;
	private String onchange;
	private String onclose;
	private String selectedIndex;
	private String canclose;
	private List collectionList;

	public WrTabsTag() {
		super();
	}

	public int doStartTag() throws JspException {
		JspWriter out = null;
		try {
			out = pageContext.getOut();
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			List collectionList = null;
			if (collection != null)
				collectionList = (List) request.getAttribute(collection);
			if (collectionList == null)
				collectionList = new ArrayList();
			CloseTag tabsDiv = new CloseTag("div");
			tabsDiv.setAttribute("id", this.id);
			tabsDiv.setAttribute("class", "tabDiv");
			tabsDiv.setAttribute("style", "width:" + this.width);
			CloseTag scrollDiv = new CloseTag("div");
			scrollDiv.setAttribute("onselectstart", "return false");
			scrollDiv.setAttribute("class", "scrollDiv");
			scrollDiv
					.setInnerHTML("<a nohref class=\"scrollLeftUnfocus\">&nbsp;&nbsp;</a><a nohref class=\"scrollRightUnfocus\">&nbsp;&nbsp;</a>");
			tabsDiv.addInnerHTML(scrollDiv.toString());
			CloseTag tableDiv = new CloseTag("div");
			tableDiv.setAttribute("style",
					"width:100%;height:100%;overflow:hidden;");
			CloseTag table = new CloseTag("table");
			table.setAttribute("height", "100%");
			table.setAttribute("cellspacing", "0");
			table.setAttribute("cellpadding", "0");
			table.setAttribute("border", "0");
			CloseTag tr = new CloseTag("tr");
			tr.setAttribute("height", "100%");
			CloseTag td = new CloseTag("td");
			td.setInnerHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			tr.addInnerHTML(td.toString());
			for (int i = 0, n = collectionList.size(); i < n; i++) {
				td = new CloseTag("td");
				String text = "";
				Object obj = collectionList.get(i);
				if (obj == null)
					continue;
				else if (obj instanceof Map) {
					Map map = (Map) obj;
					Iterator it = map.keySet().iterator();
					while (it.hasNext()) {
						Object property = it.next();
						Object value = map.get(property);
						td.setAttribute(property.toString(),
								value == null ? "null" : value.toString());
					}
					text = map.get(textField) == null ? "" : map.get(textField)
							.toString();
				} else if (obj instanceof DynaBean) {
					DynaBean bean = (DynaBean) obj;
					DynaProperty[] it = bean.getDynaClass().getDynaProperties();
					for (int j = 0, m = it.length; j < m; j++) {
						String property = it[j].getName();
						Object value = bean.get(property);
						td.setAttribute(property, value == null ? "null"
								: value.toString());
					}
					text = bean.get(textField) == null ? "" : bean.get(
							textField).toString();
				} else if (obj instanceof OptionHold) {
					OptionHold bean = (OptionHold) obj;
					text = bean.getName();
				}
				CloseTag divTab = new CloseTag("div");
				divTab.setAttribute("class", "unfocusTabItem");
				CloseTag tableTab = new CloseTag("table");
				tableTab.setAttribute("height", "100%");
				tableTab.setAttribute("cellspacing", "0");
				tableTab.setAttribute("cellpadding", "0");
				tableTab.setAttribute("border", "0");
				CloseTag trTab = new CloseTag("tr");
				CloseTag leftTd = new CloseTag("td");
				leftTd.setAttribute("class", "leftBar");
				leftTd.setInnerHTML("&nbsp;");
				trTab.addInnerHTML(leftTd.toString());
				CloseTag centerTd = new CloseTag("td");
				centerTd.setAttribute("class", "centerBar");
				centerTd.setAttribute("nowrap", "true");
				centerTd.setInnerHTML(text);
				trTab.addInnerHTML(centerTd.toString());
				CloseTag rightTd = new CloseTag("td");
				rightTd.setAttribute("class", "rightBar");
				if (this.canclose == null
						|| this.canclose.trim().equals("true")) {
					OpenTag img = new OpenTag("img");
					img.setAttribute("style",
							"width:12px;height:12px;cursor:hand");
					img.setAttribute("src", request.getContextPath()
							+ "/js/WrTabs/images/btn_close.gif");
					rightTd.setInnerHTML(img.toString() + "&nbsp;");
				} else {
					rightTd.setInnerHTML("&nbsp;&nbsp;&nbsp;");
				}
				trTab.addInnerHTML(rightTd.toString());
				tableTab.addInnerHTML(trTab.toString());
				divTab.addInnerHTML(tableTab.toString());
				td.addInnerHTML(divTab.toString());
				tr.addInnerHTML(td.toString());
			}
			table.addInnerHTML(tr.toString());
			tableDiv.addInnerHTML(table.toString());
			tabsDiv.addInnerHTML(tableDiv.toString());
			out.print(tabsDiv.toString());
			CloseTag script = new CloseTag("script");
			script.setAttribute("defer", "true");
			script.addInnerHTML("var " + this.id + " = new WrTabs(\"" + this.id
					+ "\");");
			if (this.onchange != null && !this.onchange.trim().equals(""))
				script.addInnerHTML(this.id + ".onchange = function(){"
						+ this.onchange + "};");
			if (this.onclose != null && !this.onclose.trim().equals(""))
				script.addInnerHTML(this.id + ".onclose = function(){"
						+ this.onclose + "};");
			if (this.selectedIndex != null)
				script.addInnerHTML(this.id + ".click(" + this.selectedIndex
						+ ");");
			out.print(script.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			// out.close();
			// 不能关闭
		}
		return TagSupport.SKIP_BODY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclose() {
		return onclose;
	}

	public void setOnclose(String onclose) {
		this.onclose = onclose;
	}

	public String getCanclose() {
		return canclose;
	}

	public void setCanclose(String canclose) {
		this.canclose = canclose;
	}

}
