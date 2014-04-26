package com.web.action.business.daily;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.action.CriterionAction;
import com.web.form.business.daily.PersonDepartForm;
import com.zl.base.core.db.CallHelper;

public class PersonDepartAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(PersonDepartAction.class);

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

		PersonDepartForm psaForm = (PersonDepartForm) form;
		// System.out.println(psaForm.getpersonid());
		setDeptTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersondepart", form,
				request, false);
		helper.execute();

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getDutyid());
		// System.out.println(psaForm.getDutytype());

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setDeptTree(form, request);

		return mapping.findForward("show");
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {

		// CallHelper helper = initializeCallHelper("getDepareAreaDutyTreeAll",
		// form, request, false);

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// psaForm.setAreaCode("");
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	// protected void setAreaCompanyTree(ActionForm form, HttpServletRequest
	// request) {
	// DutyManCompForm psaForm = (DutyManCompForm) form;
	//
	// psaForm.setAreaCode("");
	//
	// CallHelper helper = initializeCallHelper("getAreaCompDutyTreeAll",
	// psaForm, request, false);
	// helper.execute();
	//
	// request.setAttribute("areacompany.tree", helper.getResult("results"));
	// }

	protected void setDeptTree(ActionForm form, HttpServletRequest request) {
		PersonDepartForm psaForm = (PersonDepartForm) form;

		psaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("MP_PersonDeptTreeQuery",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("alldepart.tree", helper.getResult("results"));
	}

	public ActionForward personTreeSelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setPersonTree(form, request);
		return mapping.findForward("personTreeSelect");
	}

}
