package com.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;

public class MyMenuAction extends CoreDispatchAction {
	private static Log logger = LogFactory.getLog(MyMenuAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			logger.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward initMyMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = new CallHelper("getMyMenuTreePerson");
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("menu.tree", helper.getResult("results"));
		return mapping.findForward("initMyMenu");
	}

	public ActionForward saveMyMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveMyMenu", form, request,
				false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));
		return initMyMenu(mapping, form, request, response);
	}

	public ActionForward getMyMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// CallHelper helper1 = initializeCallHelper("getMyMenu", form, request,
		// false);
		// logger.debug(helper1.toString());
		CallHelper helper = new CallHelper("getMyMenu");
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("myMenu", helper.getResult("results"));
		return mapping.findForward("getMyMenu");
	}
}
