package com.zl.base.core.taglib.list;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

public class OptionsTag extends TagSupport {
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	protected String collection = null;
	protected String labelName = null;
	protected String labelProperty = null;
	protected String name = null;
	protected String property = null;
	private String style = null;
	private String styleClass = null;
	protected String value1;
	protected String value2;

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String paramString) {
		this.collection = paramString;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String paramString) {
		this.labelName = paramString;
	}

	public String getLabelProperty() {
		return this.labelProperty;
	}

	public void setLabelProperty(String paramString) {
		this.labelProperty = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		this.property = paramString;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String paramString) {
		this.style = paramString;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(String paramString) {
		this.styleClass = paramString;
	}

	public String getValue1() {
		return this.value1;
	}

	public void setValue1(String paramString) {
		this.value1 = paramString;
	}

	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String paramString) {
		this.value2 = paramString;
	}

	public int doStartTag() throws JspException {
		return 0;
	}

	public int doEndTag() throws JspException {
		ListTag listtag = (ListTag) pageContext
				.getAttribute("org.apache.struts.taglib.html.SELECT");
		if (listtag == null)
			throw new JspException(messages.getMessage("optionsTag.select"));
		StringBuffer stringbuffer = new StringBuffer();
		if (collection != null) {
			Object obj2;
			Object obj3;
			Object obj4;
			String s2;
			for (Iterator iterator = getIterator(collection, null); iterator
					.hasNext(); addOption(stringbuffer, s2, obj2.toString(),
					obj3.toString(), obj4.toString(), listtag.isMatched(s2))) {
				Object obj = iterator.next();
				Object obj1 = null;
				obj2 = null;
				obj3 = null;
				obj4 = null;
				try {
					obj1 = PropertyUtils.getProperty(obj, property);
					if (obj1 == null)
						obj1 = "";
				} catch (IllegalAccessException illegalaccessexception) {
					throw new JspException(messages.getMessage("getter.access",
							property, collection));
				} catch (InvocationTargetException invocationtargetexception) {
					Throwable throwable = invocationtargetexception
							.getTargetException();
					throw new JspException(messages.getMessage("getter.result",
							property, throwable.toString()));
				} catch (NoSuchMethodException nosuchmethodexception) {
					throw new JspException(messages.getMessage("getter.method",
							property, collection));
				}
				try {
					if (labelProperty != null)
						obj2 = PropertyUtils.getProperty(obj, labelProperty);
					else
						obj2 = obj1;
					if (obj2 == null)
						obj2 = "";
				} catch (IllegalAccessException illegalaccessexception1) {
					throw new JspException(messages.getMessage("getter.access",
							labelProperty, collection));
				} catch (InvocationTargetException invocationtargetexception1) {
					Throwable throwable1 = invocationtargetexception1
							.getTargetException();
					throw new JspException(messages.getMessage("getter.result",
							labelProperty, throwable1.toString()));
				} catch (NoSuchMethodException nosuchmethodexception1) {
					throw new JspException(messages.getMessage("getter.method",
							labelProperty, collection));
				}
				try {
					if (value1 != null)
						obj3 = PropertyUtils.getProperty(obj, value1);
					else
						obj3 = "";
					if (obj3 == null)
						obj3 = "";
				} catch (IllegalAccessException illegalaccessexception2) {
					throw new JspException(messages.getMessage("getter.access",
							value1, collection));
				} catch (InvocationTargetException invocationtargetexception2) {
					Throwable throwable2 = invocationtargetexception2
							.getTargetException();
					throw new JspException(messages.getMessage("getter.result",
							value1, throwable2.toString()));
				} catch (NoSuchMethodException nosuchmethodexception2) {
					throw new JspException(messages.getMessage("getter.method",
							value1, collection));
				}
				try {
					if (value2 != null)
						obj4 = PropertyUtils.getProperty(obj, value2);
					else
						obj4 = "";
					if (obj4 == null)
						obj4 = "";
				} catch (IllegalAccessException illegalaccessexception3) {
					throw new JspException(messages.getMessage("getter.access",
							value2, collection));
				} catch (InvocationTargetException invocationtargetexception3) {
					Throwable throwable3 = invocationtargetexception3
							.getTargetException();
					throw new JspException(messages.getMessage("getter.result",
							value2, throwable3.toString()));
				} catch (NoSuchMethodException nosuchmethodexception3) {
					throw new JspException(messages.getMessage("getter.method",
							value2, collection));
				}
				s2 = obj1.toString();
			}

		} else {
			Iterator iterator1 = getIterator(name, property);
			Iterator iterator2 = null;
			if (labelName == null && labelProperty == null)
				iterator2 = getIterator(name, property);
			else
				iterator2 = getIterator(labelName, labelProperty);
			String s;
			String s1;
			for (; iterator1.hasNext(); addOption(stringbuffer, s, s1,
					listtag.isMatched(s))) {
				s = iterator1.next().toString();
				s1 = s;
				if (iterator2.hasNext())
					s1 = iterator2.next().toString();
			}

		}
		ResponseUtils.write(pageContext, stringbuffer.toString());
		return 6;
	}

	// public int doEndTag()
	// throws JspException
	// {
	// ListTag localListTag =
	// (ListTag)this.pageContext.getAttribute("org.apache.struts.taglib.html.SELECT");
	// if (localListTag == null)
	// throw new JspException(messages.getMessage("optionsTag.select"));
	// StringBuffer localStringBuffer = new StringBuffer();
	// Object localObject2;
	// Object localObject3;
	// if (this.collection != null)
	// {
	// localIterator = getIterator(this.collection, null);
	// while (localIterator.hasNext())
	// {
	// localObject1 = localIterator.next();
	// localObject2 = null;
	// localObject3 = null;
	// Object localObject4 = null;
	// Object localObject5 = null;
	// Throwable localThrowable;
	// try
	// {
	// localObject2 = PropertyUtils.getProperty(localObject1, this.property);
	// if (localObject2 == null)
	// localObject2 = "";
	// }
	// catch (IllegalAccessException localIllegalAccessException1)
	// {
	// throw new JspException(messages.getMessage("getter.access",
	// this.property, this.collection));
	// }
	// catch (InvocationTargetException localInvocationTargetException1)
	// {
	// localThrowable = localInvocationTargetException1.getTargetException();
	// throw new JspException(messages.getMessage("getter.result",
	// this.property, localThrowable.toString()));
	// }
	// catch (NoSuchMethodException localNoSuchMethodException1)
	// {
	// throw new JspException(messages.getMessage("getter.method",
	// this.property, this.collection));
	// }
	// try
	// {
	// if (this.labelProperty != null)
	// localObject3 = PropertyUtils.getProperty(localObject1,
	// this.labelProperty);
	// else
	// localObject3 = localObject2;
	// if (localObject3 == null)
	// localObject3 = "";
	// }
	// catch (IllegalAccessException localIllegalAccessException2)
	// {
	// throw new JspException(messages.getMessage("getter.access",
	// this.labelProperty, this.collection));
	// }
	// catch (InvocationTargetException localInvocationTargetException2)
	// {
	// localThrowable = localInvocationTargetException2.getTargetException();
	// throw new JspException(messages.getMessage("getter.result",
	// this.labelProperty, localThrowable.toString()));
	// }
	// catch (NoSuchMethodException localNoSuchMethodException2)
	// {
	// throw new JspException(messages.getMessage("getter.method",
	// this.labelProperty, this.collection));
	// }
	// try
	// {
	// if (this.value1 != null)
	// localObject4 = PropertyUtils.getProperty(localObject1, this.value1);
	// else
	// localObject4 = "";
	// if (localObject4 == null)
	// localObject4 = "";
	// }
	// catch (IllegalAccessException localIllegalAccessException3)
	// {
	// throw new JspException(messages.getMessage("getter.access", this.value1,
	// this.collection));
	// }
	// catch (InvocationTargetException localInvocationTargetException3)
	// {
	// localThrowable = localInvocationTargetException3.getTargetException();
	// throw new JspException(messages.getMessage("getter.result", this.value1,
	// localThrowable.toString()));
	// }
	// catch (NoSuchMethodException localNoSuchMethodException3)
	// {
	// throw new JspException(messages.getMessage("getter.method", this.value1,
	// this.collection));
	// }
	// try
	// {
	// if (this.value2 != null)
	// localObject5 = PropertyUtils.getProperty(localObject1, this.value2);
	// else
	// localObject5 = "";
	// if (localObject5 == null)
	// localObject5 = "";
	// }
	// catch (IllegalAccessException localIllegalAccessException4)
	// {
	// throw new JspException(messages.getMessage("getter.access", this.value2,
	// this.collection));
	// }
	// catch (InvocationTargetException localInvocationTargetException4)
	// {
	// localThrowable = localInvocationTargetException4.getTargetException();
	// throw new JspException(messages.getMessage("getter.result", this.value2,
	// localThrowable.toString()));
	// }
	// catch (NoSuchMethodException localNoSuchMethodException4)
	// {
	// throw new JspException(messages.getMessage("getter.method", this.value2,
	// this.collection));
	// }
	// String str = localObject2.toString();
	// addOption(localStringBuffer, str, localObject3.toString(),
	// localObject4.toString(), localObject5.toString(),
	// localListTag.isMatched(str));
	// }
	// }
	// Iterator localIterator = getIterator(this.name, this.property);
	// Object localObject1 = null;
	// if ((this.labelName == null) && (this.labelProperty == null))
	// localObject1 = getIterator(this.name, this.property);
	// else
	// localObject1 = getIterator(this.labelName, this.labelProperty);
	// while (localIterator.hasNext())
	// {
	// localObject2 = localIterator.next().toString();
	// localObject3 = localObject2;
	// if (((Iterator)localObject1).hasNext())
	// localObject3 = ((Iterator)localObject1).next().toString();
	// addOption(localStringBuffer, (String)localObject2, (String)localObject3,
	// localListTag.isMatched((String)localObject2));
	// }
	// ResponseUtils.write(this.pageContext, localStringBuffer.toString());
	// return 6;
	// }

	public void release() {
		super.release();
		this.collection = null;
		this.labelName = null;
		this.labelProperty = null;
		this.name = null;
		this.property = null;
		this.style = null;
		this.styleClass = null;
	}

	protected void addOption(StringBuffer paramStringBuffer,
			String paramString1, String paramString2, boolean paramBoolean) {
		paramStringBuffer.append("<option value=\"");
		paramStringBuffer.append(paramString1);
		paramStringBuffer.append("\"");
		if (paramBoolean)
			paramStringBuffer.append(" selected=\"selected\"");
		if (this.style != null) {
			paramStringBuffer.append(" style=\"");
			paramStringBuffer.append(this.style);
			paramStringBuffer.append("\"");
		}
		if (this.styleClass != null) {
			paramStringBuffer.append(" class=\"");
			paramStringBuffer.append(this.styleClass);
			paramStringBuffer.append("\"");
		}
		paramStringBuffer.append(">");
		paramStringBuffer.append(ResponseUtils.filter(paramString2));
		paramStringBuffer.append("</option>\r\n");
	}

	protected void addOption(StringBuffer paramStringBuffer,
			String paramString1, String paramString2, String paramString3,
			String paramString4, boolean paramBoolean) {
		paramStringBuffer.append("<option value=\"");
		paramStringBuffer.append(paramString1);
		paramStringBuffer.append("\"");
		if (paramBoolean)
			paramStringBuffer.append(" selected=\"selected\"");
		if (this.style != null) {
			paramStringBuffer.append(" style=\"");
			paramStringBuffer.append(this.style);
			paramStringBuffer.append("\"");
		}
		if (this.styleClass != null) {
			paramStringBuffer.append(" class=\"");
			paramStringBuffer.append(this.styleClass);
			paramStringBuffer.append("\"");
		}
		if (paramString3 != null) {
			paramStringBuffer.append(" value1=\"");
			paramStringBuffer.append(paramString3);
			paramStringBuffer.append("\"");
		}
		if (paramString4 != null) {
			paramStringBuffer.append(" value2=\"");
			paramStringBuffer.append(paramString4);
			paramStringBuffer.append("\"");
		}
		paramStringBuffer.append(">");
		paramStringBuffer.append(ResponseUtils.filter(paramString2));
		paramStringBuffer.append("</option>\r\n");
	}

	protected Iterator getIterator(String paramString1, String paramString2)
			throws JspException {
		String str = paramString1;
		if (str == null)
			str = "org.apache.struts.taglib.html.BEAN";
		Object localObject1 = this.pageContext.findAttribute(str);
		if (localObject1 == null)
			throw new JspException(messages.getMessage("getter.bean", str));
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
		throw new JspException(messages.getMessage("optionsTag.iterator",
				localObject2.toString()));
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.list.OptionsTag JD-Core Version: 0.6.1
 */