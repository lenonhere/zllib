package com.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.common.WebMessages;
import com.common.log.LogFactory;
import com.common.tackle.UserView;
import com.zl.base.core.db.CallHelper;
import com.zl.common.Constants;

public class CoreDispatchAction extends DispatchAction {
	// Logger.getLogger(CoreDispatchAction.class);
	protected static Log log = LogFactory.getLog();
	protected ActionErrors errors = null;

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// TODO: it's not thread-safe to use a instance variable. delete it!!!
		errors = new ActionErrors();
		saveErrors(request, null);

		// request.setCharacterEncoding("gb2312");
		// response.setContentType("text/html;charset=gb2312");

		Object user = request.getSession().getAttribute(Constants.SESSION_USER);
		String jsppage = mapping.findForward(mapping.findForwards()[0])
				.getPath();

		// 2006.10.20 zby
		if (user == null && !"/system/login.jsp".equals(jsppage)) {
			return mapping.findForward("relogin");
		}

		/*
		 * if (jsppage == null) { return mapping.findForward("relogin"); } else
		 * { if (user == null && !jsppage.trim().equals("/system/login.jsp")) {
		 * return mapping.findForward("relogin"); } if (user != null &&
		 * !authorized(request)) {
		 * request.getSession().setAttribute(Constants.SESSION_USER, null);
		 * return mapping.findForward("relogin"); } }
		 */
		return super.execute(mapping, form, request, response);
	}

	/**
	 * check authorization based on requested uri + query string
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
	private boolean authorized(HttpServletRequest request) {

		String uri = request.getRequestURI();

		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 2000) {
			queryString = queryString.substring(0, 2000);
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("..");
		buffer.append(uri.substring(uri.lastIndexOf('/')));
		buffer.append('?');
		buffer.append(queryString);

		CallHelper helper = new CallHelper("checkMenu");
		helper.setParam("personId", getPersonId(request));
		helper.setParam("menu", buffer.toString());
		helper.execute();
		boolean authorized = "1".equals(helper.getOutput("authorized")) ? true
				: false;

		// StringBuffer logBuffer = new StringBuffer();
		// logBuffer.append("personid=");
		// logBuffer.append(getPersonId(request));
		// logBuffer.append(" try to accesses " + " full-path=");
		// logBuffer.append(buffer.toString());
		// logBuffer.append(": ");
		// logBuffer.append(authorized ? "accepted" : "rejected");
		// log.info(logBuffer.toString());

		return authorized;
	}

	/**
	 * get public codes belong to *classId*
	 *
	 * @param classId
	 *            int
	 * @return List
	 */
	protected List getPublicCodes(String classId) {
		CallHelper helper = new CallHelper("getPublicCodes");
		helper.setParam("classId", classId);
		helper.execute();
		return helper.getResult("results");
	}

	/**
	 * get session user(person) id
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return int
	 */
	protected String getPersonId(HttpServletRequest request) {
		return getUser(request).getPersonId();
	}

	/**
	 * get session user(person) pwd
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return int
	 */
	protected String getPersonPwd(HttpServletRequest request) {
		return getUser(request).getPassword();
	}

	/**
	 * get session user(person) name
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	protected String getPersonName(HttpServletRequest request) {
		return getUser(request).getPersonName();
	}

	/**
	 * get user department id
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return int
	 */
	protected String getDepartId(HttpServletRequest request) {
		return getUser(request).getDepartId();
	}

	/**
	 * get user department name
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	protected String getDepartName(HttpServletRequest request) {
		return getUser(request).getDepartName();
	}

	/**
	 * check if current user is member of the role with *roleCode*
	 *
	 * @param roleCode
	 *            String
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
	protected boolean isMemberOf(String roleCode, HttpServletRequest request) {
		return getUser(request).isMemberOf(roleCode);
	}

	/**
	 * get user session
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return usersession
	 */
	protected UserView getUser(HttpServletRequest request) {
		return (UserView) request.getSession().getAttribute(
				Constants.SESSION_USER);
	}

	protected String isSuperAdmin(HttpServletRequest request) {
		return ((UserView) request.getSession().getAttribute(
				Constants.SESSION_USER)).isMemberOf("999999")
				|| ((UserView) request.getSession().getAttribute(
						Constants.SESSION_USER)).getPersonId().equals("2500") ? "true"
				: "false";
	}

	/**
	 * set user session
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param user
	 *            usersession
	 */
	protected void setUser(HttpServletRequest request, UserView user) {
		request.getSession().setAttribute(Constants.SESSION_USER, user);
	}

	/**
	 * create a instance of CallHelper, and initialize it
	 *
	 * @param procKey
	 *            String
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @return CallHelper
	 */
	protected CallHelper initializeCallHelper(String procKey, ActionForm form,
			HttpServletRequest request) {
		return initializeCallHelper(procKey, form, request, true);
	}

	/**
	 * create a instance of CallHelper, and initialize it if *useKey* is true
	 * then find procedure name form resources file, else just use *procRef* as
	 * procedure name
	 *
	 * @param procRef
	 *            String
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param useKey
	 *            boolean
	 * @return CallHelper
	 */
	protected CallHelper initializeCallHelper(String procRef, ActionForm form,
			HttpServletRequest request, boolean useKey) {
		String procName = procRef; // may be name, may be key:)

		if (useKey) {
			procName = WebMessages.getString(procRef);
		}

		CallHelper helper = new CallHelper(procName);
		helper.autoCopy(form);
		String personId = getPersonId(request);
		String departId = getDepartId(request);
		helper.setParam("personId", personId);
		helper.setParam("departId", departId);
		return helper;
	}

	/**
	 * wrap BeanUtils.getProperty (ignore exception, and return null).
	 *
	 * @param bean
	 *            Object
	 * @param name
	 *            String
	 * @return Object
	 */
	protected Object getProperty(Object bean, String name) {
		try {
			return BeanUtils.getProperty(bean, name);
		} catch (Exception ex) {
			// log.info("getProperty warn: property=" + name);
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
			// log.info("setProperty warn: property=" + name + " value=" +
			// value);
		}
	}

	/**
	 * copy properties from a bean in *results* which is a result set produced
	 * by procedures
	 *
	 * @param dest
	 *            Object
	 * @param results
	 *            List
	 * @param index
	 *            int
	 * @param srcNames
	 *            List
	 * @param destNames
	 *            List
	 */
	protected void copyProperties(Object dest, List results, int index,
			List srcNames, List destNames) {
		if (results == null || results.size() < index + 1) {
			// log.info("copyProperties error: index is out of bound:" + index);
			return;
		}

		if ((srcNames == null || srcNames.size() == 0)
				|| (destNames == null || destNames.size() == 0)) {
			// log.info("copyProperties warn: no source or destination property name specified");
			return;
		}

		int count = srcNames.size() <= destNames.size() ? srcNames.size()
				: destNames.size();

		Object src = results.get(index);
		for (int i = 0; i < count; i++) {
			Object value = getProperty(src, srcNames.get(i).toString());
			setProperty(dest, destNames.get(i).toString().toString(), value);
		}
	}

	/**
	 * copy properties form a bean in *results* which is a result set produced
	 * by procedures
	 *
	 * @param dest
	 *            Object
	 * @param results
	 *            List
	 * @param index
	 *            int
	 * @param ignoreCase
	 *            boolean
	 * @param ignoreUnderline
	 *            boolean
	 */
	protected void copyProperties(Object dest, List results, int index,
			boolean ignoreCase, boolean ignoreUnderscore) {
		if (results == null || results.size() < index + 1) {
			// log.info("copyProperties error: index is out of bound:" + index);
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
}
