package com.zl.base.core.taglib.html;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.logic.IterateTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class LinkTag extends BaseHandlerTag {

	private static final long serialVersionUID = 3257685608405747375L;
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	// 四选一
	protected String forward = null;
	protected String href = null;
	protected String page = null;
	protected String action = null;
	//
	protected String text = null;
	protected String anchor = null;
	protected String linkName = null;
	protected String name = null;
	protected String paramId = null;
	protected String paramName = null;
	protected String paramProperty = null;
	protected String paramScope = null;
	protected String property = null;
	protected String scope = null;
	protected String target = null;
	protected boolean transaction = false;
	protected String indexId = null;

	public String getAnchor() {
		return this.anchor;
	}

	public void setAnchor(String paramString) {
		this.anchor = paramString;
	}

	public String getForward() {
		return this.forward;
	}

	public void setForward(String paramString) {
		this.forward = paramString;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String paramString) {
		this.href = paramString;
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String paramString) {
		this.linkName = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String paramString) {
		this.page = paramString;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String paramString) {
		this.action = paramString;
	}

	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramString) {
		this.paramId = paramString;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramString) {
		this.paramName = paramString;
	}

	public String getParamProperty() {
		return this.paramProperty;
	}

	public void setParamProperty(String paramString) {
		this.paramProperty = paramString;
	}

	public String getParamScope() {
		return this.paramScope;
	}

	public void setParamScope(String paramString) {
		this.paramScope = paramString;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		this.property = paramString;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String paramString) {
		this.scope = paramString;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String paramString) {
		this.target = paramString;
	}

	public boolean getTransaction() {
		return this.transaction;
	}

	public void setTransaction(boolean paramBoolean) {
		this.transaction = paramBoolean;
	}

	public String getIndexId() {
		return this.indexId;
	}

	public void setIndexId(String paramString) {
		this.indexId = paramString;
	}

	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		if (linkName != null) {
			StringBuffer stringbuffer = new StringBuffer("<a name=\"");
			stringbuffer.append(linkName);
			stringbuffer.append("\">");
			ResponseUtils.write(pageContext, stringbuffer.toString());
			return 2;
		} else {
			StringBuffer stringbuffer1 = new StringBuffer("\"");
			stringbuffer1.append(calculateURL());
			stringbuffer1.append("\"");
			ResponseUtils.write(pageContext, stringbuffer1.toString());
			text = null;
			return 2;
		}
	}

	public int doAfterBody() throws JspException {
		if (this.bodyContent != null) {
			String str = this.bodyContent.getString().trim();
			if (str.length() > 0)
				this.text = str;
		}
		return 0;
	}

	public int doEndTag() throws JspException {
		return 6;
	}

	public void release() {
		super.release();
		this.anchor = null;
		this.forward = null;
		this.href = null;
		this.linkName = null;
		this.name = null;
		this.page = null;
		this.action = null;
		this.paramId = null;
		this.paramName = null;
		this.paramProperty = null;
		this.paramScope = null;
		this.property = null;
		this.scope = null;
		this.target = null;
		this.text = null;
		this.transaction = false;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	protected String calculateURL() throws JspException {
		Map<Object, Object> map = RequestUtils.computeParameters(pageContext,
				paramId, paramName, paramProperty, paramScope, name, property,
				scope, transaction);
		if (indexed) {
			IterateTag iteratetag = (IterateTag) findAncestorWithClass(this,
					org.apache.struts.taglib.logic.IterateTag.class);
			if (iteratetag == null) {
				JspException jspexception = new JspException(
						messages.getMessage("indexed.noEnclosingIterate"));
				RequestUtils.saveException(pageContext, jspexception);
				throw jspexception;
			}
			if (map == null)
				map = new HashMap<Object, Object>();
			if (indexId != null)
				map.put(indexId, Integer.toString(iteratetag.getIndex()));
			else
				map.put("index", Integer.toString(iteratetag.getIndex()));
		}
		String url = null;
		try {
			url = RequestUtils.computeURL(pageContext, forward, href, page,
					action, map, anchor, false);
		} catch (MalformedURLException malformedurlexception) {
			RequestUtils.saveException(pageContext, malformedurlexception);
			throw new JspException(messages.getMessage("rewrite.url",
					malformedurlexception.toString()));
		}
		return url;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.html.LinkTag JD-Core Version: 0.6.1
 */
