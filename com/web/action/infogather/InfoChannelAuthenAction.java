package com.web.action.infogather;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.infogather.InfoChannelAuthenForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionHold;

public class InfoChannelAuthenAction extends CoreDispatchAction {

	// private static final Log log = LogFactory
	// .getLog(InfoChannelAuthenAction.class);

	protected static final String MAINT_TYPE_ADD = "1";
	protected static final String MAINT_TYPE_MODIFY = "2";
	protected static final String MAINT_TYPE_DELETE = "3";
	protected static final String MAINT_TYPE_GET = "4";

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();
		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward roleinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper helper = new CallHelper("maintRole");
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		List results = helper.getResult("results");
		request.setAttribute("role.list", results);
		return mapping.findForward("roleinit");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelAuthenForm authForm = (InfoChannelAuthenForm) form;
		String queryType = authForm.getQueryType();
		if (queryType == null || queryType.trim().length() == 0)
			queryType = "1";
		String type = authForm.getType();
		if (type == null || type.trim().length() == 0)
			type = "1";
		CallHelper helper = initializeCallHelper("getInfoChannelAuthenTree",
				form, request, false);
		helper.setParam("userId", authForm.getUserId());
		helper.setParam("queryType", queryType);
		helper.setParam("type", type);
		helper.execute();
		authForm.setQueryType(queryType);
		authForm.setType(type);
		request.setAttribute("querytype.list", getQueryTypeList());
		request.setAttribute("channelauth.tree", helper.getResult("results"));
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InfoChannelAuthenForm authForm = (InfoChannelAuthenForm) form;
		String queryType = authForm.getQueryType();
		if (queryType == null || queryType.trim().length() == 0)
			queryType = "1";
		String type = authForm.getType();
		if (type == null || type.trim().length() == 0)
			type = "1";
		CallHelper helper = initializeCallHelper("saveInfoChannelAuthen", form,
				request, false);
		helper.setParam("queryType", queryType);
		helper.setParam("type", type);
		helper.execute();
		String message = (String) helper.getOutput("message");
		// log.info("message:::::"+message);
		request.setAttribute("message", message);
		return show(mapping, form, request, response);
	}

	public ActionForward initbychannel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelAuthenForm authForm = (InfoChannelAuthenForm) form;
		String queryType = authForm.getQueryType();
		if (queryType == null || queryType.trim().length() == 0)
			queryType = "1";// 浏览
		String type = authForm.getType();

		CallHelper helper = initializeCallHelper("getInfoChannelAuthenTree",
				form, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("queryType", queryType);
		helper.setParam("type", "1");// 1，人员保存;2,角色保存
		helper.execute();
		authForm.setQueryType(queryType);
		authForm.setType(type);
		request.setAttribute("querytype.list", getQueryTypeList());
		request.setAttribute("channelauth.tree", helper.getResult("results"));
		return mapping.findForward("initbychannel");
	}

	public ActionForward showbychannel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper helper = initializeCallHelper("InfoChannelPersonTreeQuery",
				form, request, false);
		helper.execute();
		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("showbychannel");
	}

	public ActionForward savebychannel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CallHelper helper = initializeCallHelper("SaveAuthenByChannel", form,
				request, false);
		helper.execute();
		String message = (String) helper.getOutput("message");
		request.setAttribute("message", message);
		return showbychannel(mapping, form, request, response);
	}

	private List<OptionHold> getQueryTypeList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("1", "浏览权限"));
		ret.add(new OptionHold("2", "上报编辑权限"));
		ret.add(new OptionHold("3", "发布权限"));
		return ret;
	}
}
