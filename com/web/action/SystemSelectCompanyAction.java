package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.SystemSelectCompanyForm;
import com.zl.base.core.db.CallHelper;

public class SystemSelectCompanyAction extends CriterionAction {

	private static Log log = LogFactory.getLog(SystemSelectCompanyAction.class);

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

		setSystemTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setCompanyTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveSystemSelectCompany",
				form, request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setCompanyTree(form, request);

		return mapping.findForward("show");
	}

	protected void setSystemTree(ActionForm form, HttpServletRequest request) {

		CallHelper helper = initializeCallHelper("getPublicCodes", form,
				request, false);
		helper.setParam("classId", "1");
		helper.execute();

		request.setAttribute("system.tree", helper.getResult(0));
	}

	protected void setCompanyTree(ActionForm form, HttpServletRequest request) {

		SystemSelectCompanyForm aForm = (SystemSelectCompanyForm) form;

		String systemId = aForm.getSystemId();

		CallHelper helper = initializeCallHelper("getSystemSelectCompany",
				form, request, false);
		helper.setParam("systemId", systemId);
		helper.setParam("areaCode", "999999");
		helper.execute();

		request.setAttribute("company.tree", helper.getResult(0));
	}
}
