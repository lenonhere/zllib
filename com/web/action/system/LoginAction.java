package com.web.action.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.SystemConfig;
import com.common.tackle.UserView;
import com.common.util.Endecrypt;
import com.msg027.SMS;
import com.web.CoreDispatchAction;
import com.web.form.system.LoginForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import com.zl.base.core.util.StringBufferTool;
import com.zl.common.Constants;

public class LoginAction extends CoreDispatchAction {

	private static Log log = LogFactory.getLog(LoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {

			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("init");
	}

	public ActionForward reinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("init");
	}

	/**
	 * 退出登录方法
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		// request.getSession().setAttribute(Constants.SESSION_USER, null);
		return mapping.findForward("init");
	}

	/**
	 * 增加session失效跳转
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sessionTimeout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("message.information", "Session失效，请重新登录");
		return mapping.findForward("init");
	}

	public ActionForward loginByUrl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String personCode = ((UserView) request.getSession().getAttribute(
				Constants.SESSION_USER)).getPersonCode();
		String password = "";
		CallHelper helper = new CallHelper("getPersonInfo");
		helper.setParam("personCode", personCode);
		helper.execute();
		List dataList = helper.getResult("results");
		if (dataList != null && dataList.size() == 1) {
			BasicDynaBean bean = (BasicDynaBean) dataList.get(0);
			password = (String) bean.get("password");
		}
		LoginForm loginForm = (LoginForm) form;
		loginForm.setPersonCode(personCode);
		loginForm.setPassword(password);
		loginForm.setLoginType("1");
		if ("https".equals(request.getParameter("protocol"))) {
			request.getSession().setAttribute("https", "https");
		}
		// loginForm.setCheckCode("IMISSYOUSOMUCH");
		// request.getSession().setAttribute("checkImg", "IMISSYOUSOMUCH");
		return this.check(mapping, loginForm, request, response);
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LoginForm loginForm = (LoginForm) form;
		String personCode = loginForm.getPersonCode();
		String password = loginForm.getPassword();

		if ((personCode == null || "".equals(personCode)
				&& (password == null || "".equals(password)))) {
			return init(mapping, form, request, response);
		}

		//
		String checkCode = loginForm.getCheckCode();
		String randCode = (String) request.getSession()
				.getAttribute("checkImg");

		if (checkCode != null && !checkCode.equals(randCode)
				&& !"xxxx".equals(randCode)) {
			errors.add("logininfo", new ActionError("errors.detail", "验证码错误！"));
			saveErrors(request, errors);
			request.setAttribute("message.information", "系统提示：验证码错误！");
			return mapping.findForward("failure");
		}

		// 判断是从内网登录还是从外网登录
		int loginType = 1;

		if (request.getRemoteAddr().startsWith("192.")
				|| request.getRemoteAddr().equals("127.0.0.1")) {
			loginType = 1;
		} else {
			loginType = 2;
		}
		// 记录到登陆日志

		CallHelper logHelper = new CallHelper("saveLoginLog");
		logHelper.setParam("IP", request.getRemoteAddr());
		logHelper.setParam("PersonCode", loginForm.getPersonCode());
		logHelper.setParam("Password", password);
		logHelper.setParam("sysname", "myutils");
		if (loginType == 1)
			logHelper.setParam("address", "局域网内");
		else {
			// IpAddressFormater ipf = new
			// IpAddressFormater(request.getRemoteAddr());
			// String address = ipf.getFullAddress();
			// logHelper.setParam("address", address);
			/* editor by wangp 09.5.4 根据表 g_ipinfo_1 来判断 */
			logHelper.setParam("address", "未知");
		}
		logHelper.execute();

		CallHelper helper = new CallHelper("loginMultiMethod");
		helper.setParam("personCode", loginForm.getPersonCode());
		helper.setParam("password", loginForm.getPassword());
		helper.setParam("type", loginForm.getLoginType());
		helper.execute();

		String idcode = (String) helper.getOutput("idcode");
		if (idcode == null)
			idcode = "";
		String pwd = (String) helper.getOutput("pwd");// 初始密码强制变更
		if ("6666".equals(pwd)) {
			return mapping.findForward("modifypasswd");
		}

		String returnform;
		if (helper.getState() == 0) {
			List results = helper.getResult("results");
			UserView user = new UserView();
			this.copyProperties(user, results, 0, true, true);
			user.setPersonCode(loginForm.getPersonCode());
			user.setPassword(loginForm.getPassword());
			user.setIdcode(idcode);
			CallHelper roleHelper = new CallHelper("getRolesByUser");
			roleHelper.setParam("personId", user.getPersonId());
			roleHelper.execute();
			List roleResults = roleHelper.getResult("results");
			if (roleResults != null && roleResults.size() > 0) {
				for (int i = 0; i < roleResults.size(); i++) {
					DynaBean bean = (DynaBean) roleResults.get(i);
					String roleCode = (String) bean.get("rolecode");
					if (roleCode != null) {
						user.addRoleCode(roleCode.trim());
					}
				}
			}

			// request.getSession().setAttribute(Constants.SESSION_USER, null);
			// user.setSessionId(request.getSession().getId());
			// log.info("session id:" + user.getSessionId());
			request.getSession().removeAttribute(Constants.SESSION_USER);
			request.getSession().setAttribute(Constants.SESSION_USER, user);
			request.getSession().setMaxInactiveInterval(3 * 3600);
			Endecrypt crypto = new Endecrypt();
			request.getSession().setAttribute(
					"personCode",
					java.net.URLEncoder.encode(crypto.get3DESEncrypt(
							getUser(request).getPersonCode().trim().intern(),
							"zl")));
			request.getSession().setAttribute(
					"password",
					java.net.URLEncoder.encode(crypto.get3DESEncrypt(
							getUser(request).getPassword().trim().intern(),
							"zl")));
			// request.getSession().setMaxInactiveInterval(30);
			returnform = "success";
		} else {
			String getBackPwd = "";
			if (helper.getState() == 200)
				getBackPwd = "<a href=\"" + request.getContextPath()
						+ "/login.do?method=getBackPwd&personCode="
						+ personCode + "\">通过短信取回密码</a>";
			request.setAttribute("message.information",
					helper.getOutput("message") + "&nbsp;" + getBackPwd);
			returnform = "failure";
		}
		if ("success".equals(returnform)) {
			CallHelper caller = new CallHelper("getisdriver");
			caller.setParam("personId", getPersonId(request));
			caller.execute();
			String isdriver = caller.getOutput(0).toString();
			request.setAttribute("isdriver", isdriver);
			/**
			 * 判断密码有效期
			 */
			CallHelper checkHelper = initializeCallHelper(
					"checkPasswordValidDate", form, request, false);
			checkHelper.setParam("userId", getPersonId(request));
			checkHelper.setParam("checkType", String.valueOf(loginType));
			checkHelper.execute();
			Integer checkRet = new Integer(
					(String) checkHelper.getOutput("msgCode"));
			String checkMsg = (String) checkHelper.getOutput("message");

			if (checkRet.intValue() != 3) {
				request.setAttribute("message", checkMsg);
				// loginForm.setPassword(getUser(request).getPassword());
				returnform = "modifypasswd";
			}
			/**
			 * 判断用户是否能够访问营销电子商务平台
			 */
			CallHelper verifyHelper = initializeCallHelper("verifyPerson",
					form, request, false);
			verifyHelper.setParam("userId", getPersonId(request));
			verifyHelper.execute();
			String verify = (String) verifyHelper.getOutput("message");
			request.getSession().setAttribute("verify", verify);// 1表示不能访问,0表示能够访问

		}

		return mapping.findForward(returnform);
	}

	public ActionForward modifypasswd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "init";// forward="success";
		LoginForm loginForm = (LoginForm) form;
		String personCode = loginForm.getPersonCode();
		String oldpasswd = loginForm.getOldpassword();
		String newpasswd = loginForm.getNewpassword();

		CallHelper call = new CallHelper("ForceModifyPassword");
		call.setParam("oldPasswd", oldpasswd);
		call.setParam("newPasswd", newpasswd);
		call.setParam("personCode", personCode);
		call.execute();
		Integer checkRet = new Integer((String) call.getOutput("msgCode"));
		String checkMsg = (String) call.getOutput("message");
		request.setAttribute("message.information", checkMsg);
		if (checkRet.intValue() != 3) {
			forward = "modifypasswd";
		}

		return mapping.findForward(forward);
	}

	public ActionForward head(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setHead(request);
		return mapping.findForward("head");
	}

	private void setHead(HttpServletRequest request) {
		CallHelper helper = new CallHelper("getModulesByUser");
		helper.setParam("personId", getPersonId(request));
		helper.execute();
		// add by yeqh 用来辨认是通用的功能菜单还是图片功能菜单
		String module = request.getParameter("module");
		if (null == module || "".equals(module) || module.trim().length() == 0)
			module = "image";
		else
			module = module.trim();

		List codeList = helper.getResult("results");
		String contextPath = request.getContextPath();

		StringBufferTool sbt = new StringBufferTool(new StringBuffer());
		if ("image".equals(module)) {
			sbt.appendln("<table width=\"84%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
			sbt.appendln("<tr valign=\"bottom\">\n");
			sbt.appendln("<td width=\"1%\">&nbsp;</td>");
			for (int i = 0; i < codeList.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) codeList.get(i);
				sbt.append("<td width=\"14%\"><a nohref onclick='switchMenu(\""
						+ contextPath
						+ "/navigation.do?method=queryright&menucode="
						+ rowBean.get("menucode")
						+ "\");' onmouseout='MM_swapImgRestore()' onmouseover=\"MM_swapImage('Image"
						+ rowBean.get("menucode")
						+ "','','"
						+ contextPath
						+ "/images/cd2_"
						+ rowBean.get("menucode")
						+ ".gif',1)\"><img src='"
						+ contextPath
						+ "/images/cd1_"
						+ rowBean.get("menucode")
						+ ".gif' name='Image"
						+ rowBean.get("menucode")
						+ "' height='21' border='0' style='cursor:hand;' id='Image"
						+ rowBean.get("menucode") + "' /></a></td>");
				sbt.append("<td width=\"1%\"><img src=\""
						+ contextPath
						+ "/images/spacer.gif\" width=\"5\" height=\"5\" /></td>");
			}
			sbt.appendln("<td>&nbsp;</td></tr> </table>");
		} else {
			for (int i = 0; i < codeList.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) codeList.get(i);
				sbt.append("<a nohref  style='cursor:hand;color: #FFFFFF;' onclick='switchMenu(\""
						+ contextPath
						+ "/navigation.do?method=queryright&menucode="
						+ rowBean.get("menucode") + "\");'>");
				sbt.append(rowBean.get("menuname"));

				if (i < codeList.size() - 1)
					sbt.append("<span class='style2'> | </span></a>");
			}
		}

		// CallHelper call=new CallHelper("getMsgReceiveListcount");
		// call.setParam("personId",getPersonId(request));
		// call.setParam("state","-1");
		// call.execute();

		// Integer mailCount=new Integer((String)call.getOutput("msgCount"));
		CallHelper caller = new CallHelper("getisdriver");
		caller.setParam("personId", getPersonId(request));
		caller.execute();
		String isdriver = "0";
		isdriver = caller.getOutput(0).toString();
		request.setAttribute("isdriver", isdriver);

		request.setAttribute("modulelist", sbt);
		request.setAttribute("personName", getPersonName(request));
		request.setAttribute("personID", getPersonId(request));
		// request.setAttribute("mailCount", mailCount);
		request.setAttribute("idcode", getUser(request).getIdcode());
		request.setAttribute("personCode", getUser(request).getPersonCode());
		request.setAttribute("password", getUser(request).getPassword());
	}

	public ActionForward getBackPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String message = "";
			LoginForm lform = (LoginForm) form;
			String sql = "select coalesce(nullif(mobile,''),nullif(mobile2,'')) as mobile,password from g_personinfo where personcode='"
					+ lform.getPersonCode() + "'";
			SqlReturn ret = Executer.getInstance().ExecSeletSQL(sql);
			String mobile = (String) ((BasicDynaBean) ret.getResultSet().get(0))
					.get("mobile");
			String pwd = (String) ((BasicDynaBean) ret.getResultSet().get(0))
					.get("password");
			SMS sms = null;
			try {
				sms = new SMS();
				// 登陆短信平台账号
				String userid = SystemConfig.getString("MSG_USER");
				String password = SystemConfig.getString("MSG_PWD");
				int balance = sms.login(userid, password);
				if (balance < 0) {
					message = "短信平台暂时无法使用";
					log.error("短信平台目前无法登陆！");
				}
				;
				if (mobile == null || mobile.trim().equals("")) {
					message = "手机号码为空";
				}
				sms.addPhone(mobile);
				// 增加发送内容
				sms.addMsg("您新营销系统的密码为[" + pwd + "],请注意保管！", null);
				// 开始发送
				int state = sms.send();
				// 休眠100毫秒
				Thread.sleep(100);
				if (state >= 0)
					message = "密码以发送，请注意查收";
				request.setAttribute("message.information", message);
			} catch (Exception e) {
				log.error(e);
				request.setAttribute("message.information", "短信发送失败");
			} finally {
				if (sms != null)
					sms.logout();
			}
		} catch (Exception e) {
			request.setAttribute("message.information", "系统发生异常错误，请联系管理员");
		}
		return mapping.findForward("init");
	}
}
