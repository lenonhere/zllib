package com.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class DiffInfo {

	/** 二者之和 */
	private Collection union;

	/** 二者共同部分 */
	private Collection common;

	/** 只有左边有 */
	private Collection leftOnly;

	/** 只有右边有 */
	private Collection rightOnly;

	private Collection left;

	private Collection right;

	public DiffInfo() {
	}

	public Collection getUnion() {

		if (union == null) {

			return Collections.EMPTY_LIST;
		}

		return union;
	}

	public void setUnion(Collection union) {
		this.union = union;
	}

	public void setCommon(Collection common) {
		this.common = common;
	}

	public Collection getCommon() {

		if (common == null) {

			return Collections.EMPTY_LIST;
		}

		return common;
	}

	public void setLeftOnly(Collection leftOnly) {
		this.leftOnly = leftOnly;
	}

	public Collection getLeftOnly() {

		if (leftOnly == null) {

			return Collections.EMPTY_LIST;
		}

		return leftOnly;
	}

	public void setRightOnly(Collection rightOnly) {
		this.rightOnly = rightOnly;
	}

	public Collection getRightOnly() {

		if (rightOnly == null) {

			return Collections.EMPTY_LIST;
		}

		return rightOnly;
	}

	public static DiffInfo makeDiffInfo(Collection left, Collection right) {

		DiffInfo diff = new DiffInfo();
		diff.setLeft(left);
		diff.setRight(right);

		Set tmp = new HashSet(left);
		tmp.addAll(right);
		diff.setUnion(tmp);
		tmp = new HashSet(left);
		tmp.retainAll(right);
		diff.setCommon(tmp);
		tmp = new HashSet(left);
		tmp.removeAll(right);
		diff.setLeftOnly(tmp);
		tmp = new HashSet(right);
		tmp.removeAll(left);
		diff.setRightOnly(tmp);

		return diff;
	}

	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("左边\n");
		buffer.append(getSeparator());
		buffer.append(getLeftString());
		buffer.append(getSeparator());

		buffer.append("右边\n");
		buffer.append(getSeparator());
		buffer.append(getRightString());
		buffer.append(getSeparator());

		buffer.append("合集\n");
		buffer.append(getSeparator());
		buffer.append(getUnionString());
		buffer.append(getSeparator());

		buffer.append("交集\n");
		buffer.append(getSeparator());
		buffer.append(getCommonString());
		buffer.append(getSeparator());

		buffer.append("只在左边\n");
		buffer.append(getSeparator());
		buffer.append(getLeftOnlyString());
		buffer.append(getSeparator());

		buffer.append("只在右边\n");
		buffer.append(getSeparator());
		buffer.append(getRightOnlyString());
		buffer.append(getSeparator());

		return buffer.toString();
	}

	public String toXMLString() {
		try {
			Element root = new Element("diffinfo");
			Element leftElement = new Element("left");
			addContent(leftElement, left);

			Element rightElement = new Element("right");
			addContent(rightElement, right);

			Element commonElement = new Element("common");
			addContent(commonElement, common);

			Element unionElement = new Element("union");
			addContent(unionElement, union);

			Element leftOnlyElement = new Element("leftonly");
			addContent(leftOnlyElement, leftOnly);

			Element rightOnlyElement = new Element("rightonly");
			addContent(rightOnlyElement, rightOnly);

			root.addContent(leftElement);
			root.addContent(rightElement);
			root.addContent(commonElement);
			root.addContent(unionElement);
			root.addContent(leftOnlyElement);
			root.addContent(rightOnlyElement);
			Document doc = new Document(root);

			XMLOutputter outputter = new XMLOutputter();// true, "gb2312");
			return outputter.outputString(doc);
		} catch (Exception e) {
		}
		return new String("");

	}

	private void addContent(Element leftElement, Collection collection) {
		if (collection != null) {

			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				leftElement.addContent((new Element("content")).setText(item
						.toString()));

			}
		}
	}

	public String getRightOnlyString() {

		StringBuffer buffer = new StringBuffer();

		if (rightOnly != null) {

			for (Iterator iterator = rightOnly.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	public String getLeftOnlyString() {

		StringBuffer buffer = new StringBuffer();

		if (leftOnly != null) {

			for (Iterator iterator = leftOnly.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	public String getCommonString() {

		StringBuffer buffer = new StringBuffer();

		if (common != null) {

			for (Iterator iterator = common.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	public String getUnionString() {

		StringBuffer buffer = new StringBuffer();

		if (union != null) {

			for (Iterator iterator = union.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	public String getRightString() {

		StringBuffer buffer = new StringBuffer();

		if (right != null) {

			for (Iterator iterator = right.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	public String getLeftString() {

		StringBuffer buffer = new StringBuffer();

		if (left != null) {

			for (Iterator iterator = left.iterator(); iterator.hasNext();) {

				Object item = iterator.next();
				buffer.append(item.toString() + "\n");
			}
		}

		return buffer.toString();
	}

	String getSeparator() {

		return "**************************************************\n";
	}

	public void setLeft(Collection left) {
		this.left = left;
	}

	public Collection getLeft() {

		return left;
	}

	public void setRight(Collection right) {
		this.right = right;
	}

	public Collection getRight() {

		return right;
	}

}
