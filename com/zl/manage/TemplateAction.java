package com.zl.manage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class TemplateAction extends CoreDispatchAction {

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList list = TradeList.getTemplate();
			request.setAttribute("list.template", list);
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("init");
	}

	public ActionForward tomodify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<OptionHold> optionList = new ArrayList<OptionHold>();
			optionList.add(new OptionHold("0", "内容模板"));
			optionList.add(new OptionHold("1", "栏目模板"));
			request.setAttribute("list.type", optionList);
			if (request.getParameter("systemid") != null) {
				ArrayList list = TradeList.getTemplate(request
						.getParameter("systemid"));
				TemplateForm aForm = (TemplateForm) form;
				aForm.setTemplatename((String) TradeList.getvalue(list.get(0),
						"templatename"));
				aForm.setTemplatepath((String) TradeList.getvalue(list.get(0),
						"templatepath"));
				aForm.setTemplatetype((String) TradeList.getvalue(list.get(0),
						"templatetype"));
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("tomodify");
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			TemplateForm aForm = (TemplateForm) form;
			if (aForm.getTemplatepath().trim().equals("")) {
				request.setAttribute("info", "没有输入模板目录！");
				return actionMapping.findForward("tomodify");
			}
			if (aForm.getSystemid() == 0) {
				TradeList.addTemplate(aForm.getTemplatename(),
						aForm.getTemplatetype(), aForm.getTemplatepath());
			} else {

				TradeList.saveTemplate(String.valueOf(aForm.getSystemid()),
						aForm.getTemplatename(), aForm.getTemplatetype(),
						aForm.getTemplatepath());
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
