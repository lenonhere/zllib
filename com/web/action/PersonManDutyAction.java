package com.web.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.DutyManCompForm;
import com.zl.base.core.db.CallHelper;

public class PersonManDutyAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(PersonManDutyAction.class);

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

		DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getpersonid());
		setDutyTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonManDuty", form,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.execute();

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getDutyid());
		// System.out.println(psaForm.getDutytype());

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setDutyTree(form, request);

		return mapping.findForward("show");
	}

	public ActionForward initsection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setSectionTree(form, request);
		return mapping.findForward("initsection");
	}

	public ActionForward showsection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getpersonid());
		setSectionDutyTree(form, request);
		return mapping.findForward("showsection");
	}

	public ActionForward savesection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveSectionManDuty", form,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.execute();

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getDutyid());
		// System.out.println(psaForm.getDutytype());

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setSectionDutyTree(form, request);

		return mapping.findForward("showsection");
	}

	public ActionForward dutyleadinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String personName = request.getParameter("personName");
		if (personName == null)
			personName = "";
		CallHelper helper = initializeCallHelper("getPersonidTree", form,
				request, false);
		helper.setParam("type", "0");
		helper.setParam("personName", personName.trim());
		helper.execute();

		request.setAttribute("personName", personName);
		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("dutyleadinit");
	}

	public ActionForward dutyleadshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DutyManCompForm psaForm = (DutyManCompForm) form;

		CallHelper call = initializeCallHelper("getDutyleadBudgetList",
				psaForm, request, false);
		call.execute();
		ArrayList budgetids = (ArrayList) call.getResult(0);
		request.setAttribute("budgetids.list", budgetids);
		return mapping.findForward("dutyleadshow");
	}

	public ActionForward dutyleadsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveDutyleadBudget", form,
				request, false);
		helper.setParam("personid", getPersonId(request));
		helper.execute();

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getDutyid());
		// System.out.println(psaForm.getDutytype());

		request.setAttribute("message.information", helper.getOutput("message"));
		return dutyleadshow(mapping, form, request, response);
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

	protected void setSectionTree(ActionForm form, HttpServletRequest request) {

		// CallHelper helper = initializeCallHelper("getDepareAreaDutyTreeAll",
		// form, request, false);

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// psaForm.setAreaCode("");
		CallHelper helper = initializeCallHelper("getSectionTreeAll", form,
				request, false);
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

	protected void setDutyTree(ActionForm form, HttpServletRequest request) {
		DutyManCompForm psaForm = (DutyManCompForm) form;

		psaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("getPersonManDutyTreeAll",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("allduty.tree", helper.getResult("results"));
	}

	protected void setSectionDutyTree(ActionForm form,
			HttpServletRequest request) {
		DutyManCompForm psaForm = (DutyManCompForm) form;

		psaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("getSectionManDutyTreeAll",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("allduty.tree", helper.getResult("results"));
	}

	/**
	 * 通用选择人员树
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward personTreeSelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setPersonTree(form, request);
		return mapping.findForward("personTreeSelect");
	}

	// 核算主体区域设定
	protected void setProvinceTree(ActionForm form, HttpServletRequest request) {

		// CallHelper helper = initializeCallHelper("getDepareAreaDutyTreeAll",
		// form, request, false);

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// psaForm.setAreaCode("");
		CallHelper helper = initializeCallHelper("getProvinceTreeAll", form,
				request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	protected void setProvinceDutyTree(ActionForm form,
			HttpServletRequest request) {
		DutyManCompForm psaForm = (DutyManCompForm) form;

		psaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("getProvinceManDutyTreeAll",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("allduty.tree", helper.getResult("results"));
	}

	public ActionForward initprovince(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setProvinceTree(form, request);
		return mapping.findForward("initprovince");
	}

	public ActionForward showprovince(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getpersonid());
		setProvinceDutyTree(form, request);
		return mapping.findForward("showprovince");
	}

	public ActionForward saveprovince(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveProvinceManDuty", form,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.execute();

		// DutyManCompForm psaForm = (DutyManCompForm) form;
		// System.out.println(psaForm.getDutyid());
		// System.out.println(psaForm.getDutytype());

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setProvinceDutyTree(form, request);

		return mapping.findForward("showprovince");
	}
}
