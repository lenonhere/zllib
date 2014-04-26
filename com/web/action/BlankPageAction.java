package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;

public class BlankPageAction
    extends CoreDispatchAction {

    public ActionForward init(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        return mapping.findForward("init");
    }
    public ActionForward initgis(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

   return mapping.findForward("initgis");
}
}
