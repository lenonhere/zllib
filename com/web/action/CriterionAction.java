package com.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.common.WebMessages;
import com.common.query.Criterion;
import com.common.query.CriterionFactory;
import com.common.util.PropertiesUtil;
import com.web.CoreDispatchAction;

public abstract class CriterionAction extends CoreDispatchAction {

    private static final Log log = LogFactory.getLog(CriterionAction.class);
    /**
     * make a list of Criteria according to properties
     * code + ".criteria" indicates which Criteria should be made
     * code + ".stat.args" + [".suffix"] supplies static arguments
     * code + ".dyna.args" + [".suffix"] supplies dynamic arguments
     * @param code String
     * @param form ActionForm
     * @param request HttpServletRequest
     * @return List
     */
    protected List makeCriteria(String code, ActionForm form,
                               HttpServletRequest request) {

       List criterionList = new ArrayList();

       String criteria = WebMessages.getString(code + ".criteria");
       log.info(":::::::::::::::::::::::::::::criteriaName"+criteria);
       //if no criteria are found, we try to used its referred criteria
       if(isNotFound(criteria)) {
           //treat the criteria block as atomic block, use all or use none
           String codeRef = WebMessages.getString(code + ".criteria.ref");
           if(isNotFound(codeRef)) {//avoid infinite recursion, fail quickly
               log.error("no criteria defined for: " + code );
               return criterionList;
           }
           return makeCriteria(codeRef, form, request);
       }

       String[] criterionTokens = criteria.split(" ");

       Map occurTimes = new HashMap();

       for(int i=0; i<criterionTokens.length; i++) {
           //skip empty token
           if("".equals(criterionTokens[i])) {
               continue;
           }

           Object[] objs = null;
           Object[] dynaObjs = null;

           int times = increment(occurTimes, criterionTokens[i]);

           //static arguments
           String index = code + ".stat.args." + criterionTokens[i];
           String staticArgs =
               WebMessages.getString(index + "." + String.valueOf(times));

           if(isNotFound(staticArgs)) {
               staticArgs = WebMessages.getString(index);
           }

           if(! isNotFound(staticArgs)) { //has static arguments
               String[] staticArgTokens = staticArgs.split(" ");
               objs = new Object[staticArgTokens.length];

               for(int j=0, n=0; j<staticArgTokens.length; j++) {
                   if(! "".equals(staticArgTokens[j])) {
                       objs[n++] = staticArgTokens[j];
                   }
               }
           }

           //dynamic arguments
           String dynaIndex = code + ".dyna.args." + criterionTokens[i];
           String dynaArgs =
               WebMessages.getString(dynaIndex + "." + String.valueOf(times));

           if(isNotFound(dynaArgs)) {
               dynaArgs = WebMessages.getString(dynaIndex);
           }

           if(! isNotFound(dynaArgs)) { //has dyna arguments
               String[] dynaArgTokens = dynaArgs.split(" ");
               dynaObjs = new Object[dynaArgTokens.length * 2];

               for(int j=0, n=0; j<dynaArgTokens.length; j++) {
                   //TODO: need some more special names ?
                   if(! "".equals(dynaArgTokens[j])) {
                       //dynamic arguments' name & value are both used
                       dynaObjs[n++] = dynaArgTokens[j];
                       if("personId".equals(dynaArgTokens[j])) {
                           dynaObjs[n++] = getPersonId(request);
                       } else {
                           dynaObjs[n++] = getProperty(form, dynaArgTokens[j]);
                       }
                   }
               }
           }

           Criterion criterion =
               CriterionFactory.getCriterion(criterionTokens[i]);
           criterion.resolve(objs, dynaObjs);
           criterionList.add(criterion);
       }
       return criterionList;
    }

    /**
     * set a list of Criteria according to properties into request
     * also @see com.nb.adv.common.CriterionAction#make(String code, ActionForm form, HttpServletRequest request)
     * @param code String
     * @param form ActionForm
     * @param request HttpServletRequest
     */
    protected void setCriteria(String code, ActionForm form,
                               HttpServletRequest request) {

        List criterionList = makeCriteria(code, form, request);

        for(int i=0; i<criterionList.size(); i++) {
            Criterion criterion = (Criterion)criterionList.get(i);
            log.info("::::::::::::::makeCriteria:"+criterion.getKey());
            //set attribute

            request.setAttribute(criterion.getKey(), criterion.getValue());
            //try to set criterion into DynaActionFrom
            try {
                setProperty(form, criterion.getName(), criterion.getProperty());
            } catch(Exception e) {
                log.info(e);
                //no name as criterion.getName() in DynaActionForm, or
                //criterion.getName() is null or
                //criterion.getProperty() is a instance of uncompatible type
                //we need to do nothing
            }
        }
    }

    /**
     * set detail with values replaced
     * code + ".detail.values" indicates which values are used to replace
     * code + ".detail" supplies the string to be replaced
     * @param code String
     * @param form ActionForm
     * @param request HttpServletRequest
     */
    protected void setDetail(String code, ActionForm form,
                               HttpServletRequest request) {

        String values = WebMessages.getString(code + ".detail.values");

        if(isNotFound(values)) { //no values to replace
            String detail = WebMessages.getString(code + ".detail");
            request.setAttribute("detail", detail);
            return;
        }

        String[] valueTokens = values.split(" ");
        List objs = new ArrayList();
        for(int i=0; i<valueTokens.length; i++) {
            //TODO: shall we need some values that aren't in form ?
            if(! "".equals(valueTokens[i])) {
            	try{
            		objs.add(getProperty(form, valueTokens[i]));
            	}catch(Exception ex){

            	}
            }
        }

        String detail =
            WebMessages.getString(code + ".detail", objs.toArray());
        request.setAttribute("detail", detail);
    }

    /**
     * set details to request's attributes
     * @param code String
     * @param form ActionForm
     * @param request HttpServletRequest
     */
    protected void setDetails(String code, ActionForm form,
                                 HttpServletRequest request) {
           String values = WebMessages.getString(code + ".detail.values");
           String details = WebMessages.getString(code + ".detail");

           String[] valueTokens = PropertiesUtil.split(values);
           String[] detailTokens = PropertiesUtil.split(details);

           int limit =
               valueTokens.length < detailTokens.length ? valueTokens.length : detailTokens.length;

           for(int i=0; i<limit; i++) {
               request.setAttribute(detailTokens[i], valueTokens[i]);
           }

           request.setAttribute("personName", getPersonName(request));
    }

    /**
     * set headline of page.
     * given code = "this.is.a.head.key",
     * the searched keys are:
     *     "this.is.a.head.key"
     *     "this.is.a.head"
     *     "this.is.head"
     * given code = "this.is.a.key"
     * the searched keys are:
     *     "this.is.a.key"
     *     "this.is.a.key.head"
     * @param code String (*.*.head.*)
     * @param form ActionForm
     * @param request HttpServletRequest
     */
    protected void setHead(String code, ActionForm form,
                               HttpServletRequest request) {
        String head = WebMessages.getString(code);
        if(isNotFound(head)) {
            String[] tokens = PropertiesUtil.split(code, "\\.");
            int length = tokens.length;

            int pos = 0;
            for(pos=0; pos<length; pos++) {
                if("head".equals(tokens[pos])) {
                    break;
                }
            }
            if(pos < length) { //find String "head"
                 String subKey = PropertiesUtil.subkey(code, 0, pos);
                 head = WebMessages.getString(code);
                 if(isNotFound(head) && pos > 1) {
                     subKey = PropertiesUtil.subkey(code, 0, pos - 2);

                     head = WebMessages.getString(subKey + ".head");
                 }
            } else {
                head = WebMessages.getString(code + ".head");
            }
        }

        request.setAttribute("head", head);
    }

    /**
     * get value of code
     * if code is null return empty string
     * @param form ActionForm
     * @return String
     */
    protected String getCode(ActionForm form) {
        Object code = getProperty(form, "code");
        return code == null ? "" : code.toString();
    }

    protected boolean isNotFound(String value) {
        return (value.startsWith("!") && value.endsWith("!"));
    }

    protected int increment(Map occurTimes, String key) {
        Object times = occurTimes.get(key);
        int newTimes = 1;
        if(times != null) {
             newTimes =  ((Integer)times).intValue() + 1;
        }
        occurTimes.put(key, new Integer(newTimes));
        return newTimes;
    }
}
