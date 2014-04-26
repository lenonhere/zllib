package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.AreaInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

public class AreaInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(AreaInfoAction.class.getName());

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
		setAreaTree(form, request);
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));
		return actionMapping.findForward("init");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setAreaTree(form, request);
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));
		return actionMapping.findForward("edit");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("addAreaInfo", form, request,
				false);
		helper.execute();

		// setAreaTree(form, request);

		// request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", helper.getOutput(0));

		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));

		return actionMapping.findForward("edit");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("updateAreaInfo", form,
				request, false);
		helper.execute();

		// setAreaTree(form, request);

		// request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", helper.getOutput(0));

		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));

		return actionMapping.findForward("edit");
	}

	// 删除
	public ActionForward delete(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("deleteAreaInfo", form,
				request, false);
		helper.execute();

		// setAreaTree(form, request);
		// request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", helper.getOutput(0));

		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));

		return actionMapping.findForward("edit");
	}

	public ActionForward edit(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AreaInfoForm areaForm = (AreaInfoForm) form;
		// String areaCode = areaForm.getAreaCode();

		SqlReturn spReturn = TradeList.G_getAreaByCode(areaForm.getAreaCode());
		// CallHelper helper = initializeCallHelper("getAreaByCode",areaForm,
		// request, false);
		// helper.execute();
		// setAreaTree(form, request);
		List<BasicDynaBean> area = spReturn.getResultSet(0);

		int i = 0;
		if (area.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) area.get(i);
			areaForm.setAreaCode(MethodFactory.getThisString(rowBean
					.get("areacode")));
			areaForm.setAreaName(MethodFactory.getThisString(rowBean
					.get("areaname")));
			areaForm.setIfArea(MethodFactory.getThisString(rowBean
					.get("ifarea")));
			areaForm.setParentCode(MethodFactory.getThisString(rowBean
					.get("parentcode")));
			areaForm.setOrderTag(MethodFactory.getThisString(rowBean
					.get("ordertag")));
			areaForm.setIsActive(MethodFactory.getThisString(rowBean
					.get("isactive")));
		}
		request.setAttribute("ifarea.list",
				OptionUtils.getOptions("ifarea_list"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		return actionMapping.findForward("edit");
	}

	protected void setAreaTree(ActionForm form, HttpServletRequest request) {
		AreaInfoForm areaForm = (AreaInfoForm) form;
		String areaCode = areaForm.getAreaCode();
		areaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("getAreaTreeUnSelected",
				areaForm, request, false);
		helper.execute();
		areaForm.setAreaCode(areaCode);
		request.setAttribute("area.tree", helper.getResult("results"));
	}
}
