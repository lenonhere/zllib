package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.common.WebMessages;
import com.zl.base.core.db.CallHelper;

public class QueryAction
    extends CriterionAction {

    private static final Log log = LogFactory.getLog(QueryAction.class);

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

        DynaActionForm dynaForm = (DynaActionForm)form;
        if(dynaForm.get("code") == null) {
            return mapping.findForward("init");
        }

        String code = dynaForm.get("code").toString().trim();

        if("".equals(code)) {
            return mapping.findForward("init");
        }

        setCriteria(code + ".query.init", form, request);

        String head = WebMessages.getString(code + ".query.init.head");
        request.setAttribute("head", head);

        String target = WebMessages.getString(code + ".query.init.forward");
        return mapping.findForward(target);
    }

    public ActionForward show(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        DynaActionForm dynaForm = (DynaActionForm)form;
        if(dynaForm.get("code") == null) {
            return mapping.findForward("show");
        }

        String code = dynaForm.get("code").toString().trim();

        if("".equals(code)) {
            return mapping.findForward("show");
        }

        CallHelper helper = initializeCallHelper(code + ".query.show.proc", form, request);
        helper.execute();
        request.setAttribute("caption.list", helper.getResult("captions"));
        request.setAttribute("result.list", helper.getResult("results"));

        String head = WebMessages.getString(code + ".query.show.head");
        request.setAttribute("head", head);

        setDetail(code + ".query.show", form, request);

        String target = WebMessages.getString(code + ".query.show.forward");
        return mapping.findForward(target);
    }
}
