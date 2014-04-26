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

public class PersoninfoAction extends CoreDispatchAction {

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<BasicDynaBean> list = TradeList.getPersonInfo();
			request.setAttribute("list.person", list);
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("init");
	}

	public ActionForward tomodify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<OptionHold> optionList1 = new ArrayList<OptionHold>();
			optionList1.add(new OptionHold("0", "一般用户"));
			optionList1.add(new OptionHold("1", "超级用户"));
			request.setAttribute("list.type", optionList1);
			//
			ArrayList<OptionHold> optionList2 = new ArrayList<OptionHold>();
			optionList2.add(new OptionHold("1", "启用"));
			optionList2.add(new OptionHold("0", "停用"));
			request.setAttribute("list.active", optionList2);
			//
			if (request.getParameter("personid") != null) {
				ArrayList<BasicDynaBean> list = TradeList.getPersonInfo(request
						.getParameter("personid"));
				PersoninfoForm aForm = (PersoninfoForm) form;
				aForm.setPassword((String) TradeList.getvalue(list.get(0),
						"password"));
				aForm.setRepassword((String) TradeList.getvalue(list.get(0),
						"password"));
				aForm.setIsactive(Integer.valueOf(
						TradeList.getvalue(list.get(0), "isactive")).intValue());
				aForm.setLogincode((String) TradeList.getvalue(list.get(0),
						"logincode"));
				aForm.setPersonid(Integer.valueOf(
						TradeList.getvalue(list.get(0), "personid")).intValue());
				aForm.setPersonname((String) TradeList.getvalue(list.get(0),
						"personname"));
				aForm.setPersontype(Integer.valueOf(
						TradeList.getvalue(list.get(0), "persontype"))
						.intValue());
				aForm.setPhonecode((String) TradeList.getvalue(list.get(0),
						"phonecode"));
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("tomodify");
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PersoninfoForm aForm = (PersoninfoForm) form;
			ArrayList<BasicDynaBean> list = null;
			if (aForm.getLogincode().trim().equals("")) {
				request.setAttribute("info", "没有输入人员编码！");
				return actionMapping.findForward("tomodify");
			}
			if (aForm.getPersonid() != 0) {
				list = TradeList.CheckPersonInfoForSave(
						String.valueOf(aForm.getPersonid()),
						aForm.getLogincode());
			} else {
				list = TradeList.CheckPersonInfoForSave("0",
						aForm.getLogincode());
			}
			if (list.size() > 0) {
				request.setAttribute("info", "人员编码不能重复！");
				return actionMapping.findForward("tomodify");
			}
			System.out.println(aForm.getPersonname());
			if (aForm.getPersonid() == 0) {
				TradeList.AddPersonInfo(aForm.getLogincode(),
						aForm.getPassword(), aForm.getPersonname(),
						String.valueOf(aForm.getPersontype()),
						aForm.getPhonecode(),
						String.valueOf(aForm.getIsactive()));
			} else {

				TradeList.SavePersonInfo(String.valueOf(aForm.getPersonid()),
						aForm.getLogincode(), aForm.getPassword(),
						aForm.getPersonname(),
						String.valueOf(aForm.getPersontype()),
						aForm.getPhonecode(),
						String.valueOf(aForm.getIsactive()));
			}
			request.setAttribute("info", "保存成功！");
			request.setAttribute("return", "Y");
		} catch (Exception ex) {
			request.setAttribute("info", "保存失败！");
			log.error(ex);
		}
		return tomodify(actionMapping, form, request, response);
	}
}