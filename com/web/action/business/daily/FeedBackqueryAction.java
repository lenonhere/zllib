package com.web.action.business.daily;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.web.action.CriterionAction;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class FeedBackqueryAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(FeedBackqueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		request.setAttribute("type.list", OptionUtils.getthdtype());
		dynaForm.set("userId", getPersonId(request));
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getfeedbacklist", form,
				request, false);
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));
		return mapping.findForward("show");
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String id = request.getParameter("id");
		CallHelper helper = initializeCallHelper("getfeedbackquery", form,
				request, false);
		helper.execute();
		dynaForm.set("memo", helper.getOutput("memo"));
		dynaForm.set("memoType", helper.getOutput("memoType"));
		return mapping.findForward("detail");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String id = request.getParameter("id");

		CallHelper helper = initializeCallHelper("getfeedbacksave", form,
				request, false);
		helper.execute();

		request.setAttribute("message", helper.getOutput("message"));
		return show(mapping, form, request, response);
	}

}
