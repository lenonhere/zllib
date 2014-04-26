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
import com.web.form.infogather.InfoChannelColumnForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class InfoChannelColumnAction extends CoreDispatchAction {

	// private static final Log log = LogFactory
	// .getLog(InfoChannelColumnAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper call = initializeCallHelper("getInfoChanneltreeAll", form,
				request, false);
		call.execute();
		request.setAttribute("result.list", call.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelColumnForm columnForm = (InfoChannelColumnForm) form;
		String channelId = columnForm.getChannelId();
		if (channelId != null && channelId.trim().length() > 0) {
			CallHelper call = initializeCallHelper("getInfoChannelColumnList",
					form, request, false);
			call.execute();
			request.setAttribute("caption.list", call.getResult("captions"));
			request.setAttribute("result.list", call.getResult("results"));
		}
		return mapping.findForward("show");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelColumnForm columnForm = (InfoChannelColumnForm) form;
		if (columnForm.getColumnId() != null
				&& columnForm.getColumnId().trim().length() > 0) {
			TradeList.deteleInfoChannelColumnById(columnForm.getColumnId());
		}
		return show(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoChannelColumnForm columnForm = (InfoChannelColumnForm) form;
		if (columnForm.getColumnId() != null
				&& columnForm.getColumnId().trim().length() > 0) {
			List<BasicDynaBean> ret = TradeList
					.getInfoChannelColumnById(columnForm.getColumnId());
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				columnForm.setColumnName(MethodFactory.getThisString(bean
						.get("columnname")));
				columnForm.setColumnType(MethodFactory.getThisString(bean
						.get("columntype")));
				columnForm.setSortOrder(MethodFactory.getThisString(bean
						.get("sortorder")));
			}
		}
		request.setAttribute("ColumnTypeList", getColunmTypeList());
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// InfoChannelColumnForm columnForm=(InfoChannelColumnForm)form;
		CallHelper call = initializeCallHelper("saveInfoChannelColumn", form,
				request, false);
		call.execute();
		String message = (String) call.getOutput("message");
		request.setAttribute("message", message);
		return show(mapping, form, request, response);
	}

	private List<OptionHold> getColunmTypeList() {
		List<OptionHold> ret = new ArrayList<OptionHold>();
		ret.add(new OptionHold("1", "日期输入框"));
		ret.add(new OptionHold("2", "文本框"));
		ret.add(new OptionHold("3", "附件框"));
		return ret;

	}

}
