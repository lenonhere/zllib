package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.DepartInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//部门信息维护
public class DepartInfoAction extends CoreDispatchAction {
	static Log logger = LogFactory.getLog(DepartInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			logger.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DepartInfoForm aForm = (DepartInfoForm) form;
		// String isactive =request.getParameter("IsActive");
		// aForm.setIsActive("on");
		String departName = aForm.getDepartName();
		String isActive = aForm.getIsActive();
		String isduty = aForm.getIsduty();
		SqlReturn spReturn = TradeList
				.g_getDeparts(departName, isActive, isduty);
		request.setAttribute("departcaption.list", spReturn.getResultSet(0));
		request.setAttribute("departinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("init");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("depart.list", helper.getResult("results"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("isduty.list", OptionUtils.getIsActiveList());
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DepartInfoForm departInfoForm = (DepartInfoForm) form;
		String departCode = departInfoForm.getDepartCode();
		String departName = departInfoForm.getDepartName();
		String parentId = departInfoForm.getParentId();
		String isActive = departInfoForm.getIsActive();
		String isduty = departInfoForm.getIsduty();
		String pinyin = departInfoForm.getPinyin();
		SqlReturn spReturn = TradeList.g_saveDepart("1", "0", departCode,
				departName, parentId, isActive, isduty, pinyin, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("depart.list", helper.getResult("results"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("isduty.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String departId = MethodFactory.getThisString(request
				.getParameter("departId"));
		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("depart.list", helper.getResult("results"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("isduty.list", OptionUtils.getIsActiveList());

		SqlReturn spReturn = TradeList.g_getDepartInfo(departId);
		List<BasicDynaBean> depart = spReturn.getResultSet(0);
		DepartInfoForm departInfoForm = (DepartInfoForm) form;
		int i = 0;
		if (depart.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) depart.get(i);
			departInfoForm.setDepartId(MethodFactory.getThisString(rowBean
					.get("departid")));
			departInfoForm.setDepartCode(MethodFactory.getThisString(rowBean
					.get("departcode")));
			departInfoForm.setDepartName(MethodFactory.getThisString(rowBean
					.get("departname")));
			departInfoForm.setParentId(MethodFactory.getThisString(rowBean
					.get("parentid")));
			departInfoForm.setIsActive(MethodFactory.getThisString(rowBean
					.get("isactive")));
			departInfoForm.setIsduty(MethodFactory.getThisString(rowBean
					.get("isduty")));
			departInfoForm.setPinyin(MethodFactory.getThisString(rowBean
					.get("pinyin")));
		}

		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DepartInfoForm departInfoForm = (DepartInfoForm) form;
		String departId = departInfoForm.getDepartId();
		String departCode = departInfoForm.getDepartCode();
		String departName = departInfoForm.getDepartName();
		String parentId = departInfoForm.getParentId();
		String isActive = departInfoForm.getIsActive();
		String isduty = departInfoForm.getIsduty();
		String pinyin = departInfoForm.getPinyin();
		SqlReturn spReturn = TradeList.g_saveDepart("2", departId, departCode,
				departName, parentId, isActive, isduty, pinyin, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("depart.list", helper.getResult("results"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("isduty.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("savemodify");
	}

	public ActionForward select(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DepartInfoForm departInfoForm = (DepartInfoForm) form;
		String departName = departInfoForm.getDepartName();// 部门名称
		String isActive = "on";// 可用
		String isduty = "";
		SqlReturn spReturn = TradeList
				.g_getDeparts(departName, isActive, isduty);
		request.setAttribute("departcaption.list", spReturn.getResultSet(0));
		request.setAttribute("departinfo.list", spReturn.getResultSet(1));

		String isMultiSelect = request.getParameter("multiselect");
		if (isMultiSelect != null
				&& isMultiSelect.trim().equalsIgnoreCase("true"))
			request.setAttribute("multiselect", "true");
		return actionMapping.findForward("select");
	}

	public ActionForward selectdepart(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DepartInfoForm departInfoForm = (DepartInfoForm) form;
		String departName = departInfoForm.getDepartName();// 部门名称
		String isActive = "on";// 可用
		String isduty = "";
		SqlReturn spReturn = TradeList
				.g_getDeparts(departName, isActive, isduty);
		request.setAttribute("departcaption.list", spReturn.getResultSet(0));
		request.setAttribute("departinfo.list", spReturn.getResultSet(1));

		String isMultiSelect = request.getParameter("multiselect");
		if (isMultiSelect != null
				&& isMultiSelect.trim().equalsIgnoreCase("true"))
			request.setAttribute("multiselect", "true");
		return actionMapping.findForward("selectdepart");
	}

}
