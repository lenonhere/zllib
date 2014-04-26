package com.web.action.business.daily;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class WatchLogAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(WatchLogAction.class);

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
		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getwatchlogquery", form,
				request, false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("DelWatchLog", dynaForm,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return add(mapping, form, request, response);
	}

	public ActionForward delrow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("DelWatchLog", dynaForm,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return show(mapping, form, request, response);
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("ModifyWatchLog", form,
				request, false);
		helper.execute();

		request.setAttribute("date", helper.getOutput(0));
		dynaForm.set("personName", helper.getOutput(1));
		dynaForm.set("destname", helper.getOutput(2));
		dynaForm.set("code", helper.getOutput(3));
		dynaForm.set("memo", helper.getOutput(4));
		dynaForm.set("flags", helper.getOutput(5));
		dynaForm.set("queryName", helper.getOutput(6));
		dynaForm.set("commet1", helper.getOutput(7));
		dynaForm.set("infomation", helper.getOutput(8));

		return mapping.findForward("add");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("ModifyWatchLog", form,
				request, false);
		helper.execute();

		request.setAttribute("date", helper.getOutput(0));
		dynaForm.set("personName", helper.getOutput(1));
		dynaForm.set("destname", helper.getOutput(2));
		dynaForm.set("code", helper.getOutput(3));
		dynaForm.set("memo", helper.getOutput(4));
		dynaForm.set("flags", helper.getOutput(5));
		dynaForm.set("queryName", helper.getOutput(6));
		dynaForm.set("commet1", helper.getOutput(7));
		dynaForm.set("infomation", helper.getOutput(8));

		return mapping.findForward("view");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		request.setAttribute("date",
				MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		dynaForm.set("userId", getPersonId(request));
		CallHelper helper = initializeCallHelper("GetWatchLogAdd", form,
				request, false);
		helper.execute();

		dynaForm.set("selfid", helper.getOutput(1));
		dynaForm.set("personName", helper.getOutput(0));
		return mapping.findForward("add");
	}

	public ActionForward addsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("SaveWatchLogAdd", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		// System.out.println(helper.getOutput(0));
		return add(mapping, form, request, response);
	}

	public ActionForward init800(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());
		return mapping.findForward("init800");
	}

	public ActionForward add800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		request.setAttribute("date",
				MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		dynaForm.set("userId", getPersonId(request));
		CallHelper helper = initializeCallHelper("Get800CallAddId", form,
				request, false);
		helper.execute();

		dynaForm.set("selfid", helper.getOutput(1));
		dynaForm.set("personName", helper.getOutput(0));
		request.setAttribute("itemoption.list", getEventList());
		// dynaForm.set("itemoption","12");
		return mapping.findForward("add800");
	}

	public ActionForward show800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("get800callquery", form,
				request, false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show800");
	}

	public ActionForward save800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("Save800CallAdd", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return add800call(mapping, form, request, response);
	}

	public ActionForward view800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("Modify800Call", form,
				request, false);
		helper.execute();
		request.setAttribute("itemoption.list", getEventList());
		request.setAttribute("date", helper.getOutput(0));
		dynaForm.set("personName", helper.getOutput(1));
		dynaForm.set("destname", helper.getOutput(2));
		dynaForm.set("code", helper.getOutput(3));
		dynaForm.set("memo", helper.getOutput(4));
		dynaForm.set("flags", helper.getOutput(5));
		dynaForm.set("queryName", helper.getOutput(6));
		dynaForm.set("commet1", helper.getOutput(7));
		dynaForm.set("infomation", helper.getOutput(8));
		dynaForm.set("itemoption", helper.getOutput(10));

		return mapping.findForward("view800");
	}

	public ActionForward modify800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("Modify800Call", form,
				request, false);
		helper.execute();
		request.setAttribute("itemoption.list", getEventList());
		request.setAttribute("date", helper.getOutput(0));
		dynaForm.set("personName", helper.getOutput(1));
		dynaForm.set("destname", helper.getOutput(2));
		dynaForm.set("code", helper.getOutput(3));
		dynaForm.set("memo", helper.getOutput(4));
		dynaForm.set("flags", helper.getOutput(5));
		dynaForm.set("queryName", helper.getOutput(6));
		dynaForm.set("commet1", helper.getOutput(7));
		dynaForm.set("infomation", helper.getOutput(8));
		dynaForm.set("itemoption", helper.getOutput(10));

		return mapping.findForward("add800");
	}

	public ActionForward delete800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("Del800Call", dynaForm,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return add800call(mapping, form, request, response);
	}

	public ActionForward delrow800call(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("Del800Call", dynaForm,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return show800call(mapping, form, request, response);
	}

	private List<OptionHold> getEventList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("11", "咨询"));
		ret.add(new OptionHold("12", "投诉"));
		ret.add(new OptionHold("13", "反馈"));
		ret.add(new OptionHold("21", "缺包"));
		ret.add(new OptionHold("22", "透明破损"));
		ret.add(new OptionHold("23", "盒片翘边"));
		ret.add(new OptionHold("24", "其它"));
		return ret;
	}

	// 考勤
	public ActionForward kqInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		try {
			dynaForm.set("beginDate", DateUtils.getDate());
			dynaForm.set("endDate", DateUtils.getDate(1));

			request.setAttribute("itemoption.list",
					TradeList.getAttendanceStatusList());
		} catch (Exception e) {
			log.error(e);
		}
		return mapping.findForward("kqInit");
	}

	public ActionForward kqShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate",
				DateUtils.getCurrentMonthFirst().replaceAll("/", "-"));
		dynaForm.set("endDate", DateUtils.getDate().replaceAll("/", "-"));

		CallHelper helper = initializeCallHelper("getAttendanceInfoBYuid",
				form, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));

		try {
			request.setAttribute("itemoption.list",
					TradeList.getAttendanceStatusList());
		} catch (Exception e) {
			log.error(e);
		}
		return mapping.findForward("kqShow");
	}

	public ActionForward kqShowDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String date = request.getParameter("date");
		CallHelper helper = initializeCallHelper("getAttendanceInfoBYdate",
				form, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("date", date);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("kqShowDetail");
	}

	public ActionForward kqSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveAttendanceInfo", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput("message"));

		return kqShow(mapping, form, request, response);
	}

	// 考勤查询
	public ActionForward kqQueryInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());

		return mapping.findForward("kqQueryInit");
	}

	public ActionForward kqQueryShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("getAttendanceInfo", form,
				request, false);
		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));

		return mapping.findForward("kqQueryShow");
	}

	public ActionForward QueryInitnow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		return mapping.findForward("QueryInitnow");
	}

	public ActionForward QueryShownow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		CallHelper helper = initializeCallHelper("getAttendanceInfonow", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("day", day);
		helper.setParam("year", year);
		helper.setParam("month", month);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));

		return mapping.findForward("QueryShownow");
	}

	public ActionForward initall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		String personid = request.getParameter("personid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		CallHelper helper = initializeCallHelper("getAttendanceInfoall", form,
				request, false);
		helper.setParam("day", day);
		helper.setParam("personid", personid);
		helper.setParam("year", year);
		helper.setParam("month", month);

		helper.execute();
		// String year = (String) dynaForm.get("year");
		// String month = (String) dynaForm.get("month");
		// String day = (String) dynaForm.get("day");
		// System.out.print(year);
		// System.out.print(month);
		// System.out.print(day);

		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));

		return mapping.findForward("initall");
	}

	//
	private ArrayList getGroupInfo() {
		ArrayList list = null;
		try {
			String sql = "select groupid as id,groupname as name from g_AttendanceGroup a order by ordertag";
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			log.debug(e);
		}
		return list;
	}

	public ActionForward roleInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		List results = getGroupInfo();
		request.setAttribute("itemoption.list", results);

		if (results != null && results.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) results.get(0);
			dynaForm.set("itemoption", bean.get("id").toString());
		} else {
			dynaForm.set("itemoption", "-1");
		}

		return mapping.findForward("roleInit");
	}

	public ActionForward roleShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String groupId = (String) request.getParameter("groupId");
		setPersonTree(groupId, form, request);

		return mapping.findForward("roleShow");
	}

	public ActionForward roleAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("roleAdd");
	}

	public ActionForward roleAddSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		String name = (String) request.getParameter("name");
		String remark = (String) request.getParameter("remark");
		CallHelper helper = initializeCallHelper("gAttendanceGroupInfo", form,
				request, false);
		helper.setParam("flags", 1);// 添加分组
		helper.setParam("groupName", name);
		helper.setParam("remark", remark);
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("roleAdd");
	}

	public ActionForward roleModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String groupid = (String) request.getParameter("groupId");

		CallHelper helper = initializeCallHelper("gAttendanceGroupInfo", form,
				request, false);
		helper.setParam("flags", 4);
		helper.setParam("groupId", groupid);
		helper.execute();

		List list = helper.getResult("results");
		if (null != list && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			String id = bean.get("groupid").toString();
			String name = bean.get("groupname").toString();
			String remark = bean.get("remark").toString();

			dynaForm.set("id", id);
			dynaForm.set("name", name);
			dynaForm.set("remark", remark);
		} else {
			request.setAttribute("message.code", "0");
			request.setAttribute("message.information",
					"分组记录不存在." + helper.getOutput("message"));
		}

		return mapping.findForward("roleModify");
	}

	public ActionForward roleModifySave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String id = (String) request.getParameter("id");
		String name = (String) request.getParameter("name");
		String remark = (String) request.getParameter("remark");
		CallHelper helper = initializeCallHelper("gAttendanceGroupInfo", form,
				request, false);
		helper.setParam("flags", 2);// 修改分组
		helper.setParam("groupId", id);
		helper.setParam("groupName", name);
		helper.setParam("remark", remark);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("roleModify");
	}

	public ActionForward roleRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String groupid = (String) request.getParameter("groupId");
		CallHelper helper = initializeCallHelper("gAttendanceGroupInfo", form,
				request, false);
		helper.setParam("flags", 3);
		helper.setParam("groupId", groupid);
		helper.execute();

		return roleInit(mapping, form, request, response);
	}

	public ActionForward groupPersonSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// DynaActionForm dynaForm = (DynaActionForm) form;
		String groupid = (String) request.getParameter("groupId");

		CallHelper helper = initializeCallHelper("saveGroupPersonInfo", form,
				request, false);
		helper.setParam("groupId", groupid);
		helper.execute();

		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput("message"));

		return roleShow(mapping, form, request, response);
	}

	protected void setPersonTree(String groupId, ActionForm form,
			HttpServletRequest request) {
		CallHelper helper = initializeCallHelper("getPersonInfoByGroupid",
				form, request, false);
		helper.setParam("groupId", groupId);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

}
