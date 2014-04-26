package com.zl.manage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.util.TradeList;

public class DisplayAction extends CoreDispatchAction {
	public ActionForward dis(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList list = TradeList.getarticle(request
					.getParameter("filename"));

			request.setAttribute("content",
					TradeList.getvalue(list.get(0), "content"));

		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("display");
	}

}
