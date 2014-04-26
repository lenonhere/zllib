package com.web.action.business.daily;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.web.form.business.daily.PhoneAttendanceForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class PhoneAttendanceAction extends CoreDispatchAction {

	private static final Log log = LogFactory
			.getLog(PhoneAttendanceAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward setinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("setinit");
	}

	public ActionForward setshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PhoneAttendanceForm bform = (PhoneAttendanceForm) form;
		CallHelper call = initializeCallHelper("getPhoneAttendanceSet", bform,
				request, false);
		call.setParam("personid", getPersonId(request));
		call.execute();

		request.setAttribute("captions.list", call.getResult("captions"));
		request.setAttribute("results.list", call.getResult("results"));

		return mapping.findForward("setshow");
	}

	public ActionForward setsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String departids = request.getParameter("departids") != null ? request
				.getParameter("departids").trim() : "";
		String departIdSet = request.getParameter("departIdSet") != null ? request
				.getParameter("departIdSet").trim() : "";
		CallHelper call = initializeCallHelper("getPhoneAttendanceSetSave",
				form, request, false);
		call.setParam("departids", departids);
		call.setParam("departIdSet", departIdSet);
		call.setParam("personid", getPersonId(request));
		call.execute();

		request.setAttribute("message", call.getOutput(0));

		return setshow(mapping, form, request, response);
	}

	public ActionForward dayinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PhoneAttendanceForm bform = (PhoneAttendanceForm) form;
		bform.setBeginDate(DateUtils.getDate().replace("/", "-"));
		// CallHelper helper = initializeCallHelper("getPhoneDepartGradeList",
		// form, request, false);
		// helper.setParam("personid", getPersonId(request));
		// helper.execute();
		// request.setAttribute("depart.list", helper.getResult("results"));
		getdepartlist(request, form);
		return mapping.findForward("dayinit");
	}

	public ActionForward dayshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PhoneAttendanceForm bform = (PhoneAttendanceForm) form;
		CallHelper call = initializeCallHelper("getPhoneAttendanceDayQuery",
				bform, request, false);
		call.setParam("personid", getPersonId(request));
		call.execute();

		request.setAttribute("captions.list", call.getResult("captions"));
		request.setAttribute("results.list", call.getResult("results"));

		return mapping.findForward("dayshow");
	}

	public ActionForward daydetail(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper("getPhoneAttendanceDayDetail",
				form, request, false);
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("daydetail");
	}

	public ActionForward monthinit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PhoneAttendanceForm aForm = (PhoneAttendanceForm) form;
		request.setAttribute("year.list", OptionUtils.getYearList(-2, 2));
		request.setAttribute("month.list", OptionUtils.getMonthList(1, 12));
		aForm.setStartYear(DateUtils.getYear(0));
		aForm.setStartMonth(DateUtils.getMonth().substring(4, 6));
		getdepartlist(request, form);
		// CallHelper helper = initializeCallHelper("getPhoneDepartGradeList",
		// form, request, false);
		// helper.setParam("personid", getPersonId(request));
		// helper.execute();
		// request.setAttribute("depart.list", helper.getResult("results"));
		return actionMapping.findForward("monthinit");
	}

	public ActionForward monthshow(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper(
				"getPhoneAttendanceMonthQuery", form, request, false);
		caller.setParam("personid", getPersonId(request));
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("monthshow");
	}

	public ActionForward monthdetail(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper(
				"getPhoneAttendanceMonthDetail", form, request, false);
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("monthdetail");
	}

	private void getdepartlist(HttpServletRequest request, ActionForm form) {

		CallHelper helper = initializeCallHelper("GetDepartListphone", form,
				request, false);
		helper.setParam("personid", getPersonId(request));
		helper.execute();

		request.setAttribute("depart.list", helper.getResult("results"));
	}

	public ActionForward attencedayinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PhoneAttendanceForm bform = (PhoneAttendanceForm) form;
		bform.setBeginDate(DateUtils.getDate().replace("/", "-"));
		// CallHelper helper = initializeCallHelper("getPhoneDepartGradeList",
		// form, request, false);
		// helper.setParam("personid", getPersonId(request));
		// helper.execute();
		// request.setAttribute("depart.list", helper.getResult("results"));
		getdepartlist(request, form);
		return mapping.findForward("attencedayinit");
	}

	public ActionForward attencedayshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PhoneAttendanceForm bform = (PhoneAttendanceForm) form;
		CallHelper call = initializeCallHelper("getAttendanceDayQuery", bform,
				request, false);
		call.setParam("personid", getPersonId(request));
		call.execute();

		request.setAttribute("captions.list", call.getResult("captions"));
		request.setAttribute("results.list", call.getResult("results"));

		return mapping.findForward("attencedayshow");
	}

	public ActionForward attencemonthinit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PhoneAttendanceForm aForm = (PhoneAttendanceForm) form;
		request.setAttribute("year.list", OptionUtils.getYearList(-2, 2));
		request.setAttribute("month.list", OptionUtils.getMonthList(1, 12));
		aForm.setStartYear(DateUtils.getYear(0));
		aForm.setStartMonth(DateUtils.getMonth().substring(4, 6));
		getdepartlist(request, form);
		// CallHelper helper = initializeCallHelper("getPhoneDepartGradeList",
		// form, request, false);
		// helper.setParam("personid", getPersonId(request));
		// helper.execute();
		// request.setAttribute("depart.list", helper.getResult("results"));
		return actionMapping.findForward("attencemonthinit");
	}

	public ActionForward attencemonthshow(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper("getttendanceMonthQuery",
				form, request, false);
		caller.setParam("personid", getPersonId(request));
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("attencemonthshow");
	}

	public ActionForward attencemonthdetail(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper("getAttendanceMonthDetail",
				form, request, false);
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("attencemonthdetail");
	}

	public ActionForward dayreportloginit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PhoneAttendanceForm aForm = (PhoneAttendanceForm) form;
		request.setAttribute("year.list", OptionUtils.getYearList(-2, 2));
		request.setAttribute("month.list", OptionUtils.getMonthList(1, 12));
		aForm.setStartYear(DateUtils.getYear(0));
		aForm.setStartMonth(DateUtils.getMonth().substring(4, 6));
		getdepartlist(request, form);
		return actionMapping.findForward("dayreportloginit");
	}

	public ActionForward dayreportlogshow(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper("getdayreportlog", form,
				request, false);
		caller.setParam("personid", getPersonId(request));
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("dayreportlogshow");
	}

	public ActionForward companyloginit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PhoneAttendanceForm aForm = (PhoneAttendanceForm) form;
		request.setAttribute("year.list", OptionUtils.getYearList(-2, 2));
		request.setAttribute("month.list", OptionUtils.getMonthList(1, 12));
		aForm.setStartYear(DateUtils.getYear(0));
		aForm.setStartMonth(DateUtils.getMonth().substring(4, 6));
		// getdepartlist(request,form);
		return actionMapping.findForward("companyloginit");
	}

	public ActionForward companylogshow(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = initializeCallHelper("getcompanylog", form,
				request, false);
		caller.setParam("personid", getPersonId(request));
		caller.execute();
		request.setAttribute("caption.list", caller.getResult("captions"));
		request.setAttribute("results.list", caller.getResult("results"));
		return actionMapping.findForward("companylogshow");
	}

}
