package com.zl.base.core.taglib.html;

/**
 * html dom对象接口
 *
 * @author jokin
 *
 */
public interface Tag {
	/**
	 * 设置属性
	 *
	 * @param attrName
	 * @param attrValue
	 */
	public void setAttribute(String attrName, String attrValue);

	/**
	 * 获取属性
	 *
	 * @param attrName
	 * @return
	 */
	public String getAttribute(String attrName);

	/**
	 * 转化成String类型
	 *
	 * @return
	 */
	public String toString();
}