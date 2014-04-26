package com.web.action.infogather;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.infogather.InfoChannelForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class InfoChannelAction extends CoreDispatchAction {

	// private static final Log log =
	// LogFactory.getLog(InfoChannelAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("init");
	}

	public ActionForward menu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper call = initializeCallHelper("getInfoChanneltreeAll", form,
				request, false);
		call.execute();
		request.setAttribute("result.list", call.getResult("results"));
		return mapping.findForward("menu");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelForm cform = (InfoChannelForm) form;
		String channelid = cform.getChannelId();
		if (channelid != null) {
			List<BasicDynaBean> ret = TradeList.getInfoChannelById(channelid);
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				cform.setChannelName(MethodFactory.getThisString(bean
						.get("channelname")));
				cform.setCycledays(MethodFactory.getThisString(bean
						.get("cycledays")));
				cform.setIsActive(MethodFactory.getThisString(bean
						.get("isactive")));
				cform.setIsOptional(MethodFactory.getThisString(bean
						.get("isoptional")));
				cform.setParentId(MethodFactory.getThisString(bean
						.get("parentid")));
				cform.setSortOrder(MethodFactory.getThisString(bean
						.get("sortorder")));
				cform.setParentName(MethodFactory.getThisString(bean
						.get("parentname")));
			}

		} else {

			String parentid = cform.getParentId();
			if (parentid == null || parentid.trim().length() == 0)
				parentid = "-1";
			if ("-1".equals(parentid)) {
				cform.setParentName("父栏目");
			} else {
				BasicDynaBean bean = (BasicDynaBean) TradeList
						.getInfoChannelById(parentid).get(0);
				cform.setParentName(MethodFactory.getThisString(bean
						.get("channelname")));
			}

		}
		request.setAttribute("cycledaysList", getCycledaysList());
		request.setAttribute("OptionalList", getOptionalList());
		request.setAttribute("isactiveList", getIsactiveList());
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelForm cform = (InfoChannelForm) form;
		// String channelName = cform.getChannelName();
		String sortOrder = cform.getSortOrder();
		// String parentid = cform.getParentId();
		String isOptional = cform.getIsOptional();
		String isactive = cform.getIsActive();
		String cycledays = cform.getCycledays();
		String view_templateid = cform.getView_templateid();
		String rpt_templateid = cform.getRpt_templateId();

		if (view_templateid == null || view_templateid.trim().length() > 0) {
			view_templateid = "1";
		}
		if (rpt_templateid == null || rpt_templateid.trim().length() > 0) {
			rpt_templateid = "1";
		}
		if (sortOrder == null || sortOrder.trim().length() == 0) {
			sortOrder = "20";
		}
		if (isactive == null || isactive.trim().length() == 0) {
			isactive = "1";
		}
		if (isOptional == null || isOptional.trim().length() == 0) {
			isOptional = "1";
		}

		if (cycledays == null || cycledays.trim().length() == 0) {
			cycledays = "0";
		}
		String channelid = cform.getChannelId();
		if (channelid == null || channelid.trim().length() == 0)
			channelid = "-1";
		CallHelper call = initializeCallHelper("saveInfoChannel", form,
				request, false);
		call.setParam("channelId", channelid);
		call.setParam("channelName", cform.getChannelName());
		call.setParam("parentId", cform.getParentId());
		call.setParam("cycledays", cycledays);
		call.setParam("isActive", isactive);
		call.setParam("isOptional", isOptional);
		call.setParam("sortOrder", sortOrder);
		call.setParam("rptTemplateid", rpt_templateid);
		call.setParam("viewTemplateid", view_templateid);
		call.setParam("personId", getPersonId(request));
		call.execute();
		String message = (String) call.getOutput("message");
		request.setAttribute("message", message);
		return mapping.findForward("submit");
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("submit");
	}

	private List<OptionHold> getOptionalList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("1", "必须上报"));
		ret.add(new OptionHold("0", "不必须上报"));
		return ret;
	}

	private List<OptionHold> getIsactiveList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("1", "启用"));
		ret.add(new OptionHold("0", "停用"));
		return ret;
	}

	private List<OptionHold> getCycledaysList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("1", "日报"));
		ret.add(new OptionHold("7", "一周一报"));
		ret.add(new OptionHold("15", "半月报"));
		ret.add(new OptionHold("30", "月报"));
		return ret;

	}
}
