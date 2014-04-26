package com.zl.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.common.WebMessages;
import com.zl.base.core.db.CallHelper;

public class HTDispatchAction extends DispatchAction {
	protected ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm arg1,
			HttpServletRequest request, HttpServletResponse arg3)
			throws Exception {
		request.setCharacterEncoding("gb2312");
		arg3.setContentType("text/html;charset=gb2312");
		errors = new ActionErrors();
		if (request.getParameter("personsid") != null) {
			request.getSession().setAttribute("dbmarket.person",
					request.getParameter("personsid"));
		}
		return super.execute(mapping, arg1, request, arg3);
	}

	protected String getPersonId(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("dbmarket.person");
		if (obj instanceof String) {
			return String.valueOf(obj);
		}
		return "2500";
	}

	protected CallHelper initializeCallHelper(String procRef, ActionForm form,
			HttpServletRequest request, boolean useKey) {
		String procName = procRef; // may be name, may be key:)
		if (useKey) {
			procName = WebMessages.getString(procRef);
		}

		CallHelper helper = new CallHelper(procName);
		helper.autoCopy(form);
		return helper;
	}

	protected void copyProperties(Object dest, List results, int index,
			boolean ignoreCase, boolean ignoreUnderscore) {
		if (results == null || results.size() < index + 1) {
			log.info("copyProperties error: index is out of bound:" + index);
			return;
		}

		List origSrcPropertyNames = null;
		List origDestPropertyNames = null;

		Object src = results.get(index);

		try {
			// get original property names
			Map srcProperties = BeanUtils.describe(src);
			origSrcPropertyNames = new ArrayList(((Map) srcProperties).keySet());

			Map destProperties = BeanUtils.describe(dest);
			origDestPropertyNames = new ArrayList(
					((Map) destProperties).keySet());
		} catch (Exception ex) {
			log.error("getPropertyNames error: can't get property names");
		}

		List srcPropertyNames = new ArrayList();
		List destPropertyNames = new ArrayList();

		// walk through the original names
		for (int i = 0; i < origSrcPropertyNames.size(); i++) {
			Object name = origSrcPropertyNames.get(i);
			if (name == null || "".equals(name)) {
				continue;
			}
			if (ignoreCase) {
				name = ((String) name).toLowerCase();
			}
			if (ignoreUnderscore) {
				name = ((String) name).replaceAll("_", "");
			}
			srcPropertyNames.add(name);
		}

		for (int i = 0; i < origDestPropertyNames.size(); i++) {
			Object name = origDestPropertyNames.get(i);
			if (name == null || "".equals(name)) {
				continue;
			}
			if (ignoreCase) {
				name = ((String) name).toLowerCase();
			}
			if (ignoreUnderscore) {
				name = ((String) name).replaceAll("_", "");
			}
			destPropertyNames.add(name);
		}

		// copy propertyies
		for (int i = 0; i < srcPropertyNames.size(); i++) {
			int pos = destPropertyNames.indexOf(srcPropertyNames.get(i));
			if (pos >= 0) {
				Object value = getProperty(src, origSrcPropertyNames.get(i)
						.toString());
				setProperty(dest, origDestPropertyNames.get(pos).toString(),
						value);
			}
		}
	}

	protected Object getProperty(Object bean, String name) {
		try {
			return BeanUtils.getProperty(bean, name);
		} catch (Exception ex) {
			log.info("getProperty warn: property=" + name);
			return null;
		}
	}

	/**
	 * wrap BeanUtils.setProperty (ignore exception, just log it).
	 *
	 * @param bean
	 *            Object
	 * @param name
	 *            String
	 * @param value
	 *            Object
	 */
	protected void setProperty(Object bean, String name, Object value) {
		try {
			BeanUtils.setProperty(bean, name, value);
		} catch (Exception ex) {
			log.info("setProperty warn: property=" + name + " value=" + value);
		}
	}
}