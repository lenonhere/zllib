package com.web.action.business.daily;

import java.io.IOException;
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
import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.exception.DbException;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class FeedbackAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(FeedbackAction.class);

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

	public ActionForward answerinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate", DateUtils.getDate(-30));
		dynaForm.set("endDate", DateUtils.getDate());
		// request.setAttribute("deptlist.list", TradeList.GetDept());
		getDeptInfo(request);

		ArrayList<OptionHold> statuslist = getStatusList();
		request.setAttribute("statuslist.list", statuslist);

		return mapping.findForward("answerinit");
	}

	public ActionForward questinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		try {
			dynaForm.set("userId", getPersonId(request));
			dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
			dynaForm.set("endDate", DateUtils.getDate());
			// request.setAttribute("depart.list", TradeList.GetDept());// 针对部门
			getDeptInfo(request);
		} catch (Exception e) {
			log.debug(e);
		}
		return mapping.findForward("questinit");

	}

	public ActionForward answershow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper call = initializeCallHelper("getFeedbackAnswer", form,
				request, false);
		call.setParam("queryId", request.getParameter("queryId"));
		call.setParam("selfid", request.getParameter("selfid"));
		call.setParam("beginDate", request.getParameter("beginDate"));
		call.setParam("endDate", request.getParameter("endDate"));
		call.setParam("status", request.getParameter("status"));
		call.setParam("userId", getPersonId(request));

		call.execute();
		request.setAttribute("captions.list", call.getResult(0));
		request.setAttribute("results.list", call.getResult(1));
		return mapping.findForward("answershow");
	}

	public ActionForward answeraddinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String code = request.getParameter("code");
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("getQuestoninfo", dynaForm,
				request, false);
		helper.setParam("code", code);
		helper.execute();

		copyProperties(dynaForm, helper.getResult(0), 0, true, true);

		return mapping.findForward("answeraddinit");
	}

	public ActionForward evaluationaddinit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("getResultsinfo", dynaForm,
				request, false);
		helper.setParam("code", code);
		helper.execute();

		copyProperties(dynaForm, helper.getResult(0), 0, true, true);

		return mapping.findForward("evaluationaddinit");
	}

	public ActionForward answeraddsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String code = (String) dynaForm.get("code");
		String infomation = (String) dynaForm.get("commet2");
		CallHelper helper = initializeCallHelper("savefbansweradd", dynaForm,
				request, false);
		helper.setParam("code", code);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("infomation", infomation);
		helper.execute();
		request.setAttribute("message.information", helper.getOutput("message"));
		return answeraddinit(mapping, form, request, response);
	}

	public ActionForward evaluationsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String code = (String) dynaForm.get("code");
		String infomation = (String) dynaForm.get("textinfo");
		System.out.println(code + "111");
		System.out.println(infomation);
		CallHelper helper = initializeCallHelper("saveevaluation", dynaForm,
				request, false);
		helper.setParam("code", code);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("infomation", infomation);
		helper.setParam("flags", request.getParameter("flags"));
		helper.execute();
		request.setAttribute("message.information", helper.getOutput("message"));
		return evaluationaddinit(mapping, form, request, response);
	}

	public ActionForward auditinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate", DateUtils.getDate(-30));
		dynaForm.set("endDate", DateUtils.getDate());
		// request.setAttribute("deptlist.list", TradeList.GetDept());
		getDeptInfo(request);

		ArrayList statuslist = getStatusList();
		request.setAttribute("statuslist.list", statuslist);

		return mapping.findForward("auditinit");
	}

	public ActionForward auditshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper call = initializeCallHelper("getFeedbackAudit", form,
				request, false);
		call.setParam("queryId", request.getParameter("queryId"));
		call.setParam("selfid", request.getParameter("selfid"));
		call.setParam("beginDate", request.getParameter("beginDate"));
		call.setParam("endDate", request.getParameter("endDate"));
		call.setParam("status", request.getParameter("status"));
		call.setParam("userId", getPersonId(request));

		call.execute();
		request.setAttribute("captions.list", call.getResult(0));
		request.setAttribute("results.list", call.getResult(1));
		return mapping.findForward("auditshow");
	}

	public static ArrayList<OptionHold> getStatusList() {
		ArrayList<OptionHold> list = new ArrayList<OptionHold>();

		list.add(new OptionHold("3", "全部"));
		list.add(new OptionHold("0", "未回复"));
		list.add(new OptionHold("1", "已回复"));

		return list;
	}

	private void department(HttpServletRequest request, ActionForm form) {

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("department.list", helper.getResult("results"));
	}

	public ActionForward evaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate", DateUtils.getDate(-30));
		dynaForm.set("endDate", DateUtils.getDate());
		// request.setAttribute("deptlist.list", TradeList.GetDept());
		getDeptInfo(request);

		ArrayList statuslist = getStatusList();
		request.setAttribute("statuslist.list", statuslist);

		return mapping.findForward("evaluation");
	}

	public ActionForward evaluationshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper call = initializeCallHelper("getFeedbackEvaluation", form,
				request, false);
		call.setParam("beginDate", request.getParameter("beginDate"));
		call.setParam("endDate", request.getParameter("endDate"));
		call.setParam("userId", getPersonId(request));
		call.execute();
		request.setAttribute("captions.list", call.getResult(0));
		request.setAttribute("results.list", call.getResult(1));
		return mapping.findForward("evaluationshow");
	}

	// 新增意见
	public ActionForward questadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("userId", getPersonId(request));// 记录员ID

		try {
			request.setAttribute("date",
					MethodFactory.replace(DateUtils.getDate(), "/", "-"));// 意见日期
			// request.setAttribute("depart.list", TradeList.GetDept());// 针对部门
			getDeptInfo(request);
		} catch (Exception e) {
			log.debug(e);
		}

		return mapping.findForward("questadd");
	}

	// 意见反馈_新增保存
	public ActionForward questsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveFeedbackInfo", form,
				request, false);
		// helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));
		return questadd(mapping, form, request, response);
	}

	// 意见反馈_查询
	public ActionForward questshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getFeedbackInfo", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));
		return mapping.findForward("questshow");
	}

	// 留言内容已回复,不能修改
	public ActionForward checkmodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			CallHelper helper = initializeCallHelper("getFeedbackInfoByid",
					form, request, false);
			helper.setParam("code", code);
			helper.execute();
			String isanswer = (String) helper.getOutput("isanswer");
			response.getWriter().write(isanswer);
		} catch (IOException e) {
			log.debug(e);
		}
		return null;
	}

	// 意见反馈_修改
	public ActionForward questmodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		try {
			String code = request.getParameter("code");
			request.setAttribute("date",
					MethodFactory.replace(DateUtils.getDate(), "/", "-"));// 当前修改时日期
			// request.setAttribute("depart.list", TradeList.GetDept());// 岗位
			getDeptInfo(request);

			CallHelper helper = initializeCallHelper("getFeedbackInfoByid",
					form, request, false);
			helper.setParam("code", code);
			helper.execute();
			dynaForm.set("code", code);
			dynaForm.set("userId", getPersonId(request));
			String mainId = (String) helper.getOutput("mainid");
			dynaForm.set("queryId", mainId);
			request.setAttribute("depart.list", getDeptInfoByMainid(mainId));
			dynaForm.set("departid", helper.getOutput("departid"));
			dynaForm.set("infomation", helper.getOutput("infomation"));
		} catch (Exception e) {
			log.debug(e);
		}
		return mapping.findForward("questadd");
	}

	// 意见反馈_记录查看
	public ActionForward questview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("getFeedbackInfoByid", form,
				request, false);
		helper.execute();
		dynaForm.set("queryName", helper.getOutput("mainname"));
		dynaForm.set("departname", helper.getOutput("departname"));
		request.setAttribute("date", helper.getOutput("date"));
		dynaForm.set("infomation", helper.getOutput("infomation"));

		request.setAttribute("isanswer", helper.getOutput("isanswer"));

		dynaForm.set("personname", helper.getOutput("personname"));
		request.setAttribute("days", helper.getOutput("days"));
		dynaForm.set("textinfo", helper.getOutput("textinfo"));

		return mapping.findForward("questview");
	}

	// 审核
	public ActionForward auditsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("saveFeedbackAudit", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("activeId", request.getParameter("activeId"));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));
		return auditinit(mapping, form, request, response);
	}

	private void getDeptInfo(HttpServletRequest request) throws Exception {
		List mainList = TradeList.GetDeptMain();
		BasicDynaBean bean = (BasicDynaBean) mainList.get(0);
		String mainId = bean.get("id").toString();
		List deptList = getDeptInfoByMainid(mainId);
		request.setAttribute("departmain.list", mainList);
		request.setAttribute("depart.list", deptList);
	}

	private List getDeptInfoByMainid(String mainId) throws Exception {

		List deptList = TradeList.GetDeptByMainid(mainId);
		return deptList;
	}

	public ActionForward getDeptXMLByMainid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flag = (String) request.getParameter("flag");
		String mainId = (String) request.getParameter("mainId");
		List deptList = TradeList.GetDeptByMainid(mainId);
		StringBuffer xml = new StringBuffer();

		if ("-1".equals(flag)) {
			xml.append("<select name=\"departid\" style=\"width:150;\" onchange=\"departChange();\">");
			xml.append("<option value=\"-1\">全部</option>");
		} else {
			xml.append("<select name=\"departid\" style=\"width:150;\" >");
		}
		for (Object obj : deptList) {
			BasicDynaBean bean = (BasicDynaBean) obj;
			String id = bean.get("id").toString();
			String name = bean.get("name").toString();
			xml.append("<option value=\"" + id + "\">" + name + "</option>");
		}
		xml.append("</select>");
		response.getWriter().write(xml.toString());
		return null;
	}

	public ActionForward setInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = new CallHelper("msgBoardDepartmentInfo");
		helper.setParam("flags", "get");
		helper.execute();
		List results = helper.getResult("results");
		request.setAttribute("department.list", results);

		return mapping.findForward("setInit");
	}

	// 显示部门所属的责任岗位
	public ActionForward showPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setPostTree(form, request);
		return mapping.findForward("showPost");
	}

	// 显示责任岗位对应的责任人
	public ActionForward showReperson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setRePersonTree(form, request);
		return mapping.findForward("showReperson");
	}

	// 责任岗位树
	protected void setPostTree(ActionForm form, HttpServletRequest request) {
		String mainId = request.getParameter("mainId");// dynaForm.get("id").toString();
		CallHelper helper = new CallHelper("msgBoardPostInfo");
		helper.setParam("flags", "tree");
		helper.setParam("mainid", mainId);
		helper.execute();
		request.setAttribute("post.tree", helper.getResult("results"));
	}

	// 责任人树
	protected void setRePersonTree(ActionForm form, HttpServletRequest request) {
		CallHelper helper = initializeCallHelper("msgBoardRePersonInfo", form,
				request, false);
		helper.setParam("flags", "tree");
		helper.execute();
		request.setAttribute("reperson.tree", helper.getResult("results"));
	}

	// 人员树
	protected void setPersonTree(ActionForm form, HttpServletRequest request) {
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();
		request.setAttribute("person.tree", helper.getResult("results"));
	}

	/* 添加 */
	public ActionForward addDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("id", getMaxIDBy("0"));
		return mapping.findForward("addDepart");
	}

	public ActionForward addPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("departid", getMaxIDBy("1"));
		return mapping.findForward("addPost");
	}

	public ActionForward addReperson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("addReperson");
	}

	/* MAX_ID */
	private String getMaxIDBy(String flag) {
		String maxId = "";
		String sql = "";
		if ("0".equals(flag)) {
			sql = "select max(mainid)+1 as maxid from MP_FEEDBACKDEPTMAIN r";

		}
		if ("1".equals(flag)) {
			sql = "select max(deptid)+1 as maxid from MP_FEEDBACKDEPT r";
		}

		try {
			BasicDynaBean bean = (BasicDynaBean) Executer.getInstance()
					.ExecSeletSQL(sql).getResultSet().get(0);
			maxId = bean.get("maxid").toString();
		} catch (Exception e) {
			log.debug(e);
		}
		return maxId;
	}

	/* 保存 */
	public ActionForward saveAddDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mainId = dynaForm.get("id").toString();
		String mainName = dynaForm.get("nameabc").toString();
		String memo = dynaForm.get("memo").toString();

		CallHelper helper = initializeCallHelper("msgBoardDepartmentInfo",
				form, request, false);
		helper.setParam("flags", "add");
		helper.setParam("mainid", mainId);
		helper.setParam("mainname", mainName);
		helper.setParam("memo", memo);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addDepart");
	}

	public ActionForward saveAddPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mainId = dynaForm.get("id").toString();

		CallHelper helper = initializeCallHelper("msgBoardPostInfo", form,
				request, false);
		helper.setParam("flags", "add");
		helper.setParam("mainid", mainId);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addPost");
	}

	public ActionForward saveAddReperson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("msgBoardRePersonInfo", form,
				request, false);
		helper.setParam("flags", "add");
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addReperson");
	}

	/* 修改查询 */
	public ActionForward updDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mainId = request.getParameter("mainId");

		CallHelper helper = initializeCallHelper("msgBoardDepartmentInfo",
				form, request, false);
		helper.setParam("flags", "get");
		helper.setParam("mainid", mainId);
		helper.execute();

		BasicDynaBean bean = (BasicDynaBean) helper.getResult("results").get(0);
		String mainName = bean.get("mainname").toString();
		String memo = bean.get("memo").toString();
		dynaForm.set("id", mainId);
		dynaForm.set("nameabc", mainName);
		dynaForm.set("memo", memo);

		return mapping.findForward("updDepart");
	}

	public ActionForward updPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("msgBoardPostInfo", form,
				request, false);
		helper.setParam("flags", "get");
		helper.execute();

		copyProperties(form, helper.getResult("results"), 0, true, true);

		return mapping.findForward("updPost");
	}

	public ActionForward updReperson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("msgBoardRePersonInfo", form,
				request, false);
		helper.setParam("flags", "get");
		helper.execute();

		List list = helper.getResult("results");
		copyProperties(form, list, 0, true, true);

		BasicDynaBean bean = (BasicDynaBean) list.get(0);
		String personname = bean.get("personname").toString();
		dynaForm.set("personname", personname);

		return mapping.findForward("updReperson");
	}

	/* 修改保存 */
	public ActionForward saveUpdDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mainId = dynaForm.get("id").toString();
		String mainName = dynaForm.get("nameabc").toString();
		String memo = dynaForm.get("memo").toString();

		CallHelper helper = initializeCallHelper("msgBoardDepartmentInfo",
				form, request, false);
		helper.setParam("flags", "upd");
		helper.setParam("mainid", mainId);
		helper.setParam("mainname", mainName);
		helper.setParam("memo", memo);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addDepart");
	}

	public ActionForward saveUpdPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mainId = dynaForm.get("id").toString();

		CallHelper helper = initializeCallHelper("msgBoardPostInfo", form,
				request, false);
		helper.setParam("flags", "upd");
		helper.setParam("mainid", mainId);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addPost");
	}

	public ActionForward saveUpdReperson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("msgBoardRePersonInfo", form,
				request, false);
		helper.setParam("flags", "upd");
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return mapping.findForward("addReperson");
	}

	/* 删除 */
	public ActionForward delDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String mainId = request.getParameter("mainId");

		CallHelper helper = initializeCallHelper("msgBoardDepartmentInfo",
				form, request, false);
		helper.setParam("flags", "del");
		helper.setParam("mainid", mainId);
		helper.execute();

		return setInit(mapping, form, request, response);
	}

	public ActionForward delPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String mainId = request.getParameter("mainId");

		CallHelper helper = initializeCallHelper("msgBoardPostInfo", form,
				request, false);
		helper.setParam("flags", "del");
		helper.setParam("mainid", mainId);
		helper.execute();

		return setInit(mapping, form, request, response);
	}

	public ActionForward delReperson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("msgBoardRePersonInfo", form,
				request, false);
		helper.setParam("flags", "del");
		helper.execute();

		return setInit(mapping, form, request, response);
	}

	// 意见反馈_转发
	public ActionForward questForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		try {
			// dynaForm.set("beginDate", request.getParameter("beginDate"));
			// dynaForm.set("endDate", request.getParameter("endDate"));
			// dynaForm.set("status", request.getParameter("status"));

			String code = request.getParameter("code");
			getDeptInfo(request);

			CallHelper helper = initializeCallHelper("getQuestoninfo", form,
					request, false);
			helper.setParam("code", code);
			helper.execute();

			copyProperties(dynaForm, helper.getResult(0), 0, true, true);

			dynaForm.set("userId", getPersonId(request));
			String mainId = dynaForm.get("queryId").toString();
			request.setAttribute("depart.list", getDeptInfoByMainid(mainId));

		} catch (Exception e) {
			log.debug(e);
		}
		return mapping.findForward("questForward");
	}

	// 意见反馈_转发保存
	public ActionForward questForwardSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String msg = "保存失败!";
		try {
			String code = dynaForm.get("code").toString();
			String deptId = dynaForm.get("departid").toString();
			String sql = "UPDATE MP_Feedback r SET r.deptid = " + deptId
					+ " WHERE r.id = " + code; // --修改责任岗位ID
			int i = Executer.getInstance().ExecUpdateSQL(sql)
					.getAffectRowCount();
			if (i > 0) {
				msg = "保存成功!";
			}
		} catch (DbException e) {
			log.debug(e);
		}

		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", msg);
		return questForward(mapping, dynaForm, request, response);
	}

}
