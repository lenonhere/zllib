package com.web.action.business.daily;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.web.form.business.daily.ExpensesClaimForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;

public class ExpensesClaimAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(ExpensesClaimAction.class);

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
		ExpensesClaimForm dynaForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("department.list", helper.getResult("results"));

		// dynaForm.setDepartid("0"); //getDepartId(request)

		dynaForm.setBeginDate(DateUtils.getCurrentMonthFirst()
				.replace("/", "-"));
		dynaForm.setEndDate(DateUtils.getDate().replace("/", "-"));

		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm aForm = (ExpensesClaimForm) form;
		if ("".equals(aForm.getBeginDate())) {
			aForm.setBeginDate(DateUtils.getCurrentMonthFirst().replace("/",
					"-"));
			aForm.setEndDate(DateUtils.getDate().replace("/", "-"));
		}
		CallHelper helper = initializeCallHelper("getexpensesclaimquery",
				aForm, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm dynaForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();
		request.setAttribute("department.list", helper.getResult("results"));
		dynaForm.setDepartid(getDepartId(request));
		dynaForm.setDate(MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		dynaForm.setUserId(getPersonId(request));
		dynaForm.setPersonname(getPersonName(request));

		helper = initializeCallHelper("getExpensesClaimMemo", form, request,
				false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("add");
	}

	public ActionForward delrow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("DelExpensesClaim", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return show(mapping, form, request, response);
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm aForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", aForm,
				request, false);
		helper.execute();
		request.setAttribute("department.list", helper.getResult("results"));

		helper = initializeCallHelper("ModifyExpensesClaim", aForm, request,
				false);
		helper.execute();

		aForm.setDate((String) helper.getOutput("date"));
		aForm.setPersonname((String) helper.getOutput("personname"));
		aForm.setDepartid((String) helper.getOutput("departid"));
		aForm.setReceivepersonid((String) helper.getOutput("receivepersonid"));
		aForm.setReceivepersonname((String) helper
				.getOutput("receivepersonname"));

		helper = initializeCallHelper("getExpensesClaimMemo", aForm, request,
				false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("add");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm aForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", aForm,
				request, false);
		helper.execute();
		request.setAttribute("department.list", helper.getResult("results"));

		helper = initializeCallHelper("ModifyExpensesClaim", aForm, request,
				false);
		helper.execute();

		aForm.setDate((String) helper.getOutput("date"));
		aForm.setPersonname((String) helper.getOutput("personname"));
		aForm.setDepartid((String) helper.getOutput("departid"));
		aForm.setReceivepersonid((String) helper.getOutput("receivepersonid"));
		aForm.setReceivepersonname((String) helper
				.getOutput("receivepersonname"));

		helper = initializeCallHelper("getExpensesClaimMemo", aForm, request,
				false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("SaveExpensesClaimAdd", form,
				request, false);

		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return add(mapping, form, request, response);
	}

	public ActionForward auditsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("SaveExpensesClaimAudit",
				form, request, false);

		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return audit(mapping, form, request, response);
	}

	public ActionForward auditinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ExpensesClaimForm aForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("department.list", helper.getResult("results"));

		// aForm.setDepartid(getDepartId(request));
		aForm.setBeginDate(DateUtils.getCurrentMonthFirst().replace("/", "-"));
		aForm.setEndDate(DateUtils.getDate().replace("/", "-"));

		return mapping.findForward("auditinit");
	}

	public ActionForward auditshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ExpensesClaimForm aForm = (ExpensesClaimForm) form;
		if ("".equals(aForm.getBeginDate())) {
			aForm.setBeginDate(DateUtils.getCurrentMonthFirst().replace("/",
					"-"));
			aForm.setEndDate(DateUtils.getDate().replace("/", "-"));
		}

		CallHelper helper = initializeCallHelper("getexpensesclaimauditquery",
				form, request, false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show");
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm aForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", aForm,
				request, false);
		helper.execute();
		request.setAttribute("department.list", helper.getResult("results"));

		helper = initializeCallHelper("ModifyExpensesClaim", aForm, request,
				false);
		helper.execute();

		aForm.setDate((String) helper.getOutput("date"));
		aForm.setPersonname((String) helper.getOutput("personname"));
		aForm.setDepartid((String) helper.getOutput("departid"));
		aForm.setReceivepersonid((String) helper.getOutput("receivepersonid"));
		aForm.setReceivepersonname((String) helper
				.getOutput("receivepersonname"));

		helper = initializeCallHelper("getExpensesClaimMemo", aForm, request,
				false);
		helper.setParam("querytype", "2");
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("audit");
	}

	public ActionForward checksave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("SaveExpensesClaimCheck",
				form, request, false);

		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return audit(mapping, form, request, response);
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpensesClaimForm dynaForm = (ExpensesClaimForm) form;

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();
		request.setAttribute("department.list", helper.getResult("results"));
		dynaForm.setDepartid(getDepartId(request));
		dynaForm.setDate(MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		dynaForm.setUserId(getPersonId(request));
		dynaForm.setPersonname(getPersonName(request));

		helper = initializeCallHelper("ModifyExpensesClaim", dynaForm, request,
				false);
		helper.execute();

		dynaForm.setDate((String) helper.getOutput("date"));
		dynaForm.setPersonname((String) helper.getOutput("personname"));
		dynaForm.setDepartid((String) helper.getOutput("departid"));
		dynaForm.setReceivepersonid((String) helper
				.getOutput("receivepersonid"));
		dynaForm.setReceivepersonname((String) helper
				.getOutput("receivepersonname"));

		helper = initializeCallHelper("getExpensesClaimMemo", form, request,
				false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("audit");
	}

	public ActionForward subjectselect(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper call = new CallHelper("ECSubjectSelectQuery");
		call.execute();
		request.setAttribute("matcaptions.list", call.getResult("captions"));
		request.setAttribute("matresults.list", call.getResult("results"));

		return actionMapping.findForward("subjectselect");
	}

}
