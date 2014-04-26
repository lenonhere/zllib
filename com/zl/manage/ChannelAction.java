package com.zl.manage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class ChannelAction extends CoreDispatchAction {
	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return actionMapping.findForward("init");
	}

	public ActionForward menu(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<BasicDynaBean> list = TradeList.getChannelMenu(3);
			request.setAttribute("list.channelmenu", list);
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("menu");
	}

	public ActionForward filemenu(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<BasicDynaBean> list = TradeList.getChannelMenu(3);
			request.setAttribute("list.channelmenu", list);
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("filemenu");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<OptionHold> optionList = new ArrayList<OptionHold>();
			optionList.add(new OptionHold("0", "不显示在首页"));
			optionList.add(new OptionHold("1", "在频道中显示"));
			optionList.add(new OptionHold("2", "在首页中单独显示"));
			request.setAttribute("list.opindex", optionList);

			request.setAttribute("list.parent",
					TradeList.getChannelforSelect(2));

			request.setAttribute("list.template",
					TradeList.getTemplateBytype("1"));
			if (request.getParameter("systemid") != null) {

				ArrayList<BasicDynaBean> list2 = TradeList.getChannel(request
						.getParameter("systemid"));
				ChannelForm aForm = (ChannelForm) form;
				aForm.setChannel_name((String) TradeList.getvalue(list2.get(0),
						"channel_name"));
				aForm.setDesciption((String) TradeList.getvalue(list2.get(0),
						"desciption"));
				if (TradeList.getvalue(list2.get(0), "op_top").equals("1")) {
					aForm.setParentid(-1);
				} else {
					aForm.setParentid(Integer.valueOf(
							TradeList.getvalue(list2.get(0), "parentid"))
							.intValue());
				}

				aForm.setSystemid(Integer.valueOf(
						TradeList.getvalue(list2.get(0), "systemid"))
						.intValue());
				aForm.setTemplateid(String.valueOf(TradeList.getvalue(
						list2.get(0), "templateid")));
				aForm.setSortorder(String.valueOf(TradeList.getvalue(
						list2.get(0), "sortorder")));
				aForm.setOpindex(String.valueOf(TradeList.getvalue(
						list2.get(0), "opindex")));
			}

		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("tomodify");
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ChannelForm aForm = (ChannelForm) form;
			if ("true".equals(request.getParameter("todelete"))) {
				boolean isParent = TradeList.deleteChannel(String.valueOf(aForm
						.getSystemid()));
				if (isParent) {
					request.setAttribute("info", "父结点不能删除！");
				} else {
					request.setAttribute("return", "Y");
					request.setAttribute("info", "删除成功！");
				}
			} else {
				if (aForm.getParentid() == -1) {
					aForm.setOp_top("1");
				} else {
					aForm.setOp_top("0");
				}

				if (aForm.getSystemid() == 0) {
					TradeList.addChannel(aForm.getChannel_name(),
							aForm.getDesciption(),
							String.valueOf(aForm.getOpindex()),
							aForm.getOp_top(), aForm.getSortorder(),
							String.valueOf(aForm.getParentid()));
				} else {
					TradeList.saveChannel(String.valueOf(aForm.getSystemid()),
							aForm.getChannel_name(), aForm.getDesciption(),
							String.valueOf(aForm.getOpindex()),
							aForm.getOp_top(), aForm.getSortorder(),
							String.valueOf(aForm.getParentid()));
				}
				request.setAttribute("return", "Y");
				request.setAttribute("info", "保存成功！");
			}
		} catch (Exception ex) {
			request.setAttribute("info", "保存失败!");
			log.error(ex);
		}
		return modify(actionMapping, form, request, response);
	}
}
