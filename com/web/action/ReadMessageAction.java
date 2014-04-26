package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;

public class ReadMessageAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(ReadMessageAction.class);

	public ActionForward read(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("success");

		try {
			String receiveId = request.getParameter("receiveId");
			if (receiveId != null && receiveId.trim().length() > 0) {
				CallHelper helper = new CallHelper("updateMsgStatusByType");
				helper.setParam("personId", getPersonId(request));
				helper.setParam("msgIds", receiveId);
				helper.setParam("updateType", "1");
				helper.execute();
			}
		} catch (Exception e) {
			log.error("read message error:::" + e.toString());
		}
		return forward;
	}

}
