package com.zl.opt.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zl.base.core.db.CallHelper;
import com.zl.base.core.util.StringBufferTool;
import com.zl.core.HTDispatchAction;
import com.zl.util.MethodFactory;

public class LoginAction extends HTDispatchAction {

	private static Log log = LogFactory.getLog(LoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward execute22(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(request.getAttributeNames());
		return mapping.findForward("error");
	}
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("init");
	}

	public ActionForward reinit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("reinit");
	}

	public ActionForward gotoIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("reinit");
	}

	private List getAutoRights(List rightlist) {
		List list = new ArrayList();
		int len = rightlist == null ? 0 : rightlist.size();
		for (int i = 0; i < len; i++) {
			BasicDynaBean rowBean = (BasicDynaBean) rightlist.get(i);
			String menucode = MethodFactory.getThisString(rowBean.get("menucode"));
			String menuname = MethodFactory.getThisString(rowBean.get("menuname"));
			String operatesn = MethodFactory.getThisString(rowBean.get("operatesn"));
			MenuForm mf = new MenuForm();
			mf.setMenuCode(menucode);
			mf.setMenuName(menuname);
			mf.setOperatesn(operatesn);
			list.add(mf);
		}
		return list;
	}


	/**
	 * �˳���¼����
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// request.getSession().invalidate();
		// request.getSession().setAttribute(Constants.SESSION_USER, null);

		java.util.Vector activeSessions = (java.util.Vector) this.getServlet().getServletContext().getAttribute(
				"activeSessions");
		// ע��
		//LoginForm loginForm = (LoginForm) form;
		if (activeSessions != null) {
			activeSessions.remove(request.getSession());
			this.getServlet().getServletContext().setAttribute("activeSessions", activeSessions);
		}

		java.util.Enumeration e = request.getSession().getAttributeNames();

		while (e.hasMoreElements()) {
			String s = (String) e.nextElement();
			request.getSession().removeAttribute(s);
		}

		return mapping.findForward("init");
	}

	public ActionForward showFirstPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
		//BiShowPageAction.getInidexPageString(request);
		return mapping.findForward("middle_flash");
	}




	private String setAutoRightinfo(List rightlist) {
		StringBuffer str_rights = new StringBuffer();
		int len = rightlist == null ? 0 : rightlist.size();
		int i_height = 0;
		if (len > 0)
			i_height = Math.round(152 / len);
		else
			i_height = 152;
		for (int i = 0; i < len; i++) {
			BasicDynaBean rowBean = (BasicDynaBean) rightlist.get(i);
			//String menucode = MethodFactory.getThisString(rowBean.get("menucode"));
			String menuname = MethodFactory.getThisString(rowBean.get("menuname"));
			//String operatesn = MethodFactory.getThisString(rowBean.get("operatesn"));
			str_rights
					.append("<tr><td width='1' background='bipage/images/border.gif'><img src='bipage/images/border.gif' width='1'></img></td><td width='100%' height="
							+ i_height + " >");
			str_rights.append(menuname);
			str_rights
					.append("</td><td width='1' background='bipage/images/border.gif'><img src='bipage/images/border.gif' width='1'></img></td></tr>");
		}
		return str_rights.toString();
	}
	public ActionForward head(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setHead(request);
		return mapping.findForward("head");
	}
	public ActionForward navigation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CallHelper helper = new CallHelper("getModulesByUser");
		helper.setParam("personId", getPersonId(request));
		helper.execute();
		List codeList = helper.getResult("results");
		if (codeList.size()>0){
			BasicDynaBean rowBean = (BasicDynaBean) codeList.get(0);
			CallHelper call = initializeCallHelper("G_MenuQuery", form, request, false);
			call.setParam("menuCode", rowBean.get("menucode"));
			call.setParam("userId", getPersonId(request));
			call.execute();
			request.setAttribute("menu.list", call.getResult(0));
		}
		return mapping.findForward("navigation");
	}

	public ActionForward modifypasswd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "success";
		LoginForm loginForm = (LoginForm) form;
		String oldpasswd = loginForm.getOldpassword();
		String newpasswd = loginForm.getNewpassword();
		CallHelper call = initializeCallHelper("saveModifyPassword", form, request, false);
		call.setParam("oldPasswd", oldpasswd);
		call.setParam("newPasswd", newpasswd);
		call.setParam("userId", getPersonId(request));
		call.execute();
		Integer checkRet = new Integer((String) call.getOutput("msgCode"));
		String checkMsg = (String) call.getOutput("message");

		if (checkRet.intValue() != 3) {
			request.setAttribute("message", checkMsg);
			// loginForm.setPassword(getUser(request).getPassword());
			forward = "modifypasswd";
		}
		return mapping.findForward(forward);
	}

	public ActionForward menu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// setHeadSystem(request,form);
		LoginForm loginForm = (LoginForm) form;
		List slist = new ArrayList();
		String code = loginForm.getCode();
		// log.debug("code=>"+code);
		if (code != null && code.length() > 0) {
			List mlist = (List) request.getSession().getAttribute("allMenu");
			int size = mlist == null ? 0 : mlist.size();
			// log.debug("code=>"+code+":size="+size);
			for (int i = 0; i < size; i++) {
				MenuForm mf = (MenuForm) mlist.get(i);
				if (code.equals(mf.getParentCode())) {
					//log.debug("1code=>"+code+ ":"+mf.getOperatesn()+
					// ":TRUE:"+mf.getMenuCode()+":mf.getParentCode()==>"+mf.getParentCode());
					slist.add(mf);
				} else {
					//log.debug("2code=>"+code+":"+mf.getOperatesn()+
					//		":mf.getParentCode()==>"+mf.getParentCode());
				}
			}
			request.setAttribute("subMenu", slist);
		}
		return mapping.findForward("menu");
	}

	private void setHead(HttpServletRequest request) {
		CallHelper helper = new CallHelper("getModulesByUser");
		helper.setParam("personId", getPersonId(request));
		helper.execute();
		// add by yeqh ����������ͨ�õĹ��ܲ˵�����ͼƬ���ܲ˵�
		String module = request.getParameter("module");
		if (null == module || "".equals(module) || module.trim().length() == 0)
			module = "image";
		else
			module = module.trim();

		List codeList = helper.getResult("results");
		String contextPath = request.getContextPath();
		String trade = "0";
		StringBufferTool sbt = new StringBufferTool(new StringBuffer());
		boolean blnIndex=false;
		for (int i = 0; i < codeList.size(); i++) {
			BasicDynaBean rowBean = (BasicDynaBean) codeList.get(i);
			if(blnIndex==false){
					sbt.append("<td width='10' class='menu_visited_1'>&nbsp;</td>");
					sbt.append("<td width='"+getwidth(rowBean.get("menuname")+"")+"' nowrap class='menu_visited_2' onmousemove='menuMove()' onmouseout='menuMoveout()' onclick='menClick(\""+contextPath
							+ "/navigation.do?method=queryright&menucode=" + rowBean.get("menucode") + "\")'>"+rowBean.get("menuname")+"</td>");
					sbt.append("<td width='10' class='menu_visited_3'>&nbsp;</td>");
					blnIndex=true;
					request.setAttribute("menuIndex",String.valueOf(i*3+1));
			}else{
				sbt.append("<td width='10' class='menu_1'>&nbsp;</td>");
				sbt.append("<td width='"+getwidth(rowBean.get("menuname")+"")+"' nowrap class='menu_2' onmousemove='menuMove()' onmouseout='menuMoveout()' onclick='menClick(\""+contextPath

							+ "/navigation.do?method=queryright&menucode=" + rowBean.get("menucode") + "\")'>"+rowBean.get("menuname")+"</td>");
				sbt.append("<td width='10' class='menu_3'>&nbsp;</td>");
			}
		}
		request.setAttribute("modulelist", sbt);
		request.setAttribute("trade", trade);
	}
	private  int getwidth(String strTemp){
		return strTemp.length() * 14;
	}
	public static void main(String[] args) {
		LoginAction l = new LoginAction();
	}
}
