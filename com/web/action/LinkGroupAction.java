package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.LinkGroupForm;
import com.zl.base.core.db.CallHelper;

public class LinkGroupAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(LinkGroupAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String personId = getPersonId(request);
		String switchid = request.getParameter("switchid");
		if (switchid == null)
			switchid = "1";
		CallHelper call = initializeCallHelper("getPersonLinkGroup", form,
				request, false);
		call.setParam("personId", personId);
		call.setParam("switchid", switchid);
		call.execute();
		List linkGroup = call.getResult(0);
		List unlinkGroup = call.getResult(1);
		request.setAttribute("linkgroup.list", linkGroup);
		request.setAttribute("unlinkgroup.list", unlinkGroup);
		return mapping.findForward("init");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LinkGroupForm aform = (LinkGroupForm) form;
		CallHelper call = initializeCallHelper("savePersonLinkGroup", aform,
				request, false);
		call.execute();
		String message = (String) call.getOutput("message");
		log.info("message:::" + message);
		request.setAttribute("message", message);
		return mapping.findForward("submit");
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("submit");
	}

}
