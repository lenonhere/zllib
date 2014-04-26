package com.zl.base.core.taglib.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 不关闭HTML标签类
 *
 * @author jokin
 *
 */
public class OpenTag implements Tag {
	private String name;
	private Map attributes = new HashMap();

	public OpenTag(String name) {
		this.name = name;
	}

	public void setAttribute(String attrName, String attrValue) {
		attributes.put(attrName, attrValue);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("<" + this.name);
		Iterator it = attributes.keySet().iterator();
		while (it.hasNext()) {
			String attrName = (String) it.next();
			String attrValue = (String) attributes.get(attrName);
			if (attrValue != null) {
				sb.append(" ").append(attrName).append("=\"").append(attrValue)
						.append("\"");
			}
		}
		sb.append(">");
		return sb.toString();
	}

	public String getAttribute(String attrName) {
		return (String) this.attributes.get(attrName);
	}
}
