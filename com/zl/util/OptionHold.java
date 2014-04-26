package com.zl.util;

/**
 * <p>
 * Description:
 * </p>
 * hold an option item, mainly used in tag lib
 *
 */
public class OptionHold {

	protected String id;
	protected String name;
	protected String value;
	protected String value1;
	protected String value2;
	protected String group;

	/**
	 * 默认构造函数
	 */
	public OptionHold() {

	}

	/**
	 * @param id
	 * @param name
	 */
	public OptionHold(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @param id
	 * @param name
	 * @param value
	 */
	public OptionHold(String id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	/**
	 * @param id
	 * @param name
	 * @param value1
	 * @param value2
	 */
	public OptionHold(String id, String name, String value1, String value2) {
		this.id = id;
		this.name = name;
		this.value1 = value1;
		this.value2 = value2;
	}

	/**
	 * @param id
	 * @param name
	 * @param value1
	 * @param value2
	 * @param group
	 * @param showGroup
	 *            值为true时将group的值赋给group,值为false时将group的值赋给value
	 */
	public OptionHold(String id, String name, String value1, String value2,
			String group, boolean showGroup) {
		this.id = id;
		this.name = name;
		this.value1 = value1;
		this.value2 = value2;
		if (showGroup) {
			this.group = group;
		} else {
			this.value = group;
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param value
	 * @param value1
	 * @param value2
	 * @param group
	 */
	public OptionHold(String id, String name, String value, String value1,
			String value2, String group) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.value1 = value1;
		this.value2 = value2;
		this.group = group;
	}

	public String getGroup() {
		return group;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[OptionHold >>> ");
		buffer.append("id=");
		buffer.append(id);
		buffer.append(", name=");
		buffer.append(name);
		buffer.append(", value=");
		buffer.append(value);
		buffer.append(", value1=");
		buffer.append(value1);
		buffer.append(", value2=");
		buffer.append(value2);
		buffer.append(", group=");
		buffer.append(group);
		buffer.append("]");

		return buffer.toString();
	}
}
