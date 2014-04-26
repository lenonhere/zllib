package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.PersonFactoryForm;
import com.zl.base.core.db.CallHelper;

public class PersonFactoryAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(PersonFactoryAction.class);

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

		setPersonTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setFactoryTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonFactory", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setFactoryTree(form, request);

		return mapping.findForward("show");
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {

		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	protected void setFactoryTree(ActionForm form, HttpServletRequest request) {
		PersonFactoryForm psaForm = (PersonFactoryForm) form;

		CallHelper helper = initializeCallHelper("getFactoryTree", psaForm,
				request, false);
		helper.execute();

		request.setAttribute("factory.tree", helper.getResult("results"));
	}
}
