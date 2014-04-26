package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BlankPageAction extends DispatchAction {
	// public ActionForward blankPageInit(ActionMapping mapping, ActionForm
	// form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// return mapping.findForward("init");
	// }

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("init");
	}
}