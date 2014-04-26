package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class SelectDateAction extends CriterionAction {

    private static final Log log = LogFactory.getLog(SelectDateAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        try {
            return super.execute(mapping, form, request, response);
        }
        catch (Exception e) {
            log.error(e);
            return mapping.findForward("error");
        }
    }

    public ActionForward init(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        DynaActionForm DynaForm = (DynaActionForm)form;
        DynaForm.set("date",DynaForm.get("date"));

        return mapping.findForward("init");
    }
}
