package com.web.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.SystemConfig;
import com.jasson.im.api.APIClient;
import com.jasson.im.api.MOItem;
import com.jasson.im.api.RPTItem;
import com.ldsoft.esms.client.Reply;
import com.ldsoft.esms.client.Send;
import com.msg027.SMS;
import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.web.form.system.SmsManageForm;
import com.ws.basedata.cmpp.MsgWServiceImpl;
import com.ws.basedata.cmpp.MsgWServiceImplService;
import com.ws.basedata.cmpp.MsgWServiceImplServiceLocator;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class SmsManageAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(SmsManageAction.class);
	protected static final String NO_ROLE_AVAILABLE_YET = "-1";
	protected static final String MAINT_TYPE_ADD = "1";
	protected static final String MAINT_TYPE_MODIFY = "2";
	protected static final String MAINT_TYPE_DELETE = "3";
	protected static final String MAINT_TYPE_GET = "4";

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

	/* com.cd.im.actions.SmsManageAction.java Begin */

	public ActionForward smssendinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		request.setAttribute("PersonInfoList", getPersonInfo());
		return mapping.findForward("smssendinit");
	}

	public ActionForward smsmultisendinit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("smsmultisendinit");
	}

	public ActionForward getlinkgroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String personId = getPersonId(request);
		String switchid = request.getParameter("switchid");
		if (switchid == null)
			switchid = "1";
		CallHelper call = initializeCallHelper("getPersonLinkGroup", form,
				request, false);
		call.setParam("personId", personId);
		call.setParam("switchid", switchid);
		call.execute();
		List linkGroup = call.getResult(0);
		List unlinkGroup = call.getResult(1);
		request.setAttribute("linkgroup.list", linkGroup);
		request.setAttribute("unlinkgroup.list", unlinkGroup);
		return mapping.findForward("linkgroup");
	}

	public ActionForward smssend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SmsManageForm smsForm = (SmsManageForm) form;
		String receiverIds = smsForm.getReceiverIds();
		String mobilePhones = smsForm.getMobilePhones();
		String msgContent = smsForm.getSmsContent();
		boolean sendMessageReturn = false;
		try {
			MsgWServiceImplService service = new MsgWServiceImplServiceLocator();
			MsgWServiceImpl test = service.getMsgWServiceImpl();
			sendMessageReturn = test.sendMessage(mobilePhones, msgContent,
					"default");
		} catch (Exception e) {
			log.error("send sms error:" + e.toString());
		}

		CallHelper caller = new CallHelper("sendMessage");
		caller.setParam("personId", getPersonId(request));
		caller.setParam("title", msgContent);
		caller.setParam("content", msgContent);
		caller.setParam("type", "1");
		caller.setParam("receivePersonid", receiverIds);

		caller.execute();

		CallHelper helper = initializeCallHelper("sendImMsg", form, request,
				false);
		helper.setParam("senderId", getPersonId(request));
		helper.setParam("receiveType", "1");
		helper.setParam("receierIds", receiverIds);
		helper.setParam("msgContent", msgContent);
		helper.setParam("receiveType", "1");
		helper.setParam("msgType", "4");
		helper.setParam("msgTitle", "title");
		helper.execute();
		String message = (String) helper.getOutput("message");
		request.setAttribute("message", message);
		request.setAttribute("msgType", "4");
		String forwardto = request.getParameter("forwardto");
		if (forwardto != null && forwardto.trim().length() > 0)
			request.setAttribute("forwardto", forwardto);
		return mapping.findForward("smssendsuccess");

	}

	private static List<OptionHold> personInfoList;

	private List<OptionHold> getPersonInfo() {
		if (personInfoList == null) {
			try {
				List<BasicDynaBean> ret = TradeList.getPersonInfoList2();
				personInfoList = new ArrayList<OptionHold>();
				// SimplePersonData personData=new SimplePersonData();
				if (personInfoList != null && ret.size() > 0) {
					for (int i = 0; i < ret.size(); i++) {
						DynaBean bean = (DynaBean) ret.get(i);
						String personId = MethodFactory.getThisString(bean
								.get("personid"));
						String personName = MethodFactory.getThisString(bean
								.get("personname"));
						String departName = MethodFactory.getThisString(bean
								.get("departname"));
						String mobile = MethodFactory.getThisString(bean
								.get("mobile"));
						personInfoList.add(new OptionHold(personId, departName
								+ " | " + personName, mobile, mobile));
						// log.info("personName="+departName+"|"+personName);
					}
				}
			} catch (Exception e) {
				log.error("getPersonInfoList error:::" + e.toString());
			}

		}
		return personInfoList;
	}

	public ActionForward getsearchperson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String personName = (String) request.getParameter("personName");
		CallHelper call = initializeCallHelper("getSearchPerson", form,
				request, false);
		call.setParam("personName", personName);
		// System.out.println("-----------presonname:" + personName);
		call.execute();
		List personlist = call.getResult(0);
		// List unlinkGroup=call.getResult(1);
		request.setAttribute("persongroup.list", personlist);
		// request.setAttribute("unlinkgroup.list",unlinkGroup);
		return mapping.findForward("searchperson");
	}

	/* com.cd.im.actions.SmsManageAction.java End */

	// =================================================================================================================================
	/* com.nb.adv.system.SmsManageAction.java Begin */

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getDepareAreaPersonTree");
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward initnew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getDepareAreaPersonTree");
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("initnew");
	}

	public ActionForward initByDepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SmsManageForm smsForm = (SmsManageForm) form;
		CallHelper helper = new CallHelper("getDepareAreaPersonTreeDepart");
		helper.setParam("depart", smsForm.getComment());
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("initdepart");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getDepareAreaPersonTree");
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("show");
	}

	// 群发
	public ActionForward gsinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getDepareAreaPersonTree",
				form, request, false);
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));

		return mapping.findForward("gsinit");
	}

	public ActionForward gsinitnew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getDepareAreaPersonTree",
				form, request, false);
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));

		return mapping.findForward("gsinitnew");
	}

	public ActionForward gsshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getDepareAreaPersonTree");
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("gsshow");
	}

	public ActionForward sinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("sinit");
	}

	public ActionForward slist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getSmsGroupInit", form,
				request, false);
		helper.setParam("personId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));

		return mapping.findForward("slist");
	}

	public ActionForward sshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getDepareAreaPersonTree");
		helper.setParam("type", "1");
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("sshow");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SmsManageForm smsForm = (SmsManageForm) form;
		smsForm.setBegindate(DateUtils.getDate().replace("/", "-"));
		smsForm.setEnddate(DateUtils.getDate().replace("/", "-"));
		return mapping.findForward("query");
	}

	public ActionForward query2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getSendQuery", form, request,
				false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("results.list", helper.getResult(0));
		return mapping.findForward("query2");
	}

	public ActionForward querydetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getSendQueryDetail", form,
				request, false);
		// helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("caption.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("querydetail");
	}

	public ActionForward sendMsg11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SmsManageForm smsForm = (SmsManageForm) form;
		// CallHelper helper = new CallHelper("getSendInfo");
		CallHelper helper = initializeCallHelper("getSendInfo", smsForm,
				request, false);
		helper.setParam("userId", getPersonId(request));

		helper.execute();
		int rtState = helper.getState();
		switch (rtState) {
		case 2: // 密码不对
			request.setAttribute("message.code", String.valueOf(rtState));
			request.setAttribute("message.information",
					helper.getOutput("message"));
			break;
		case 5:
			request.setAttribute("message.information",
					helper.getOutput("message"));
		case 0:
			/*
			 * List mobileList=helper.getResult(0); if(mobileList.size()==0) {
			 * request.setAttribute("message.information","列表中无手机号码可发送！"); }
			 * else {
			 */
			request.setAttribute("message.information", "发送成功！");
			/*
			 * //String []mobileSet=new String[mobileList.size()]; for(int
			 * i=0;i<mobileList.size();i++) { BasicDynaBean
			 * bean=(BasicDynaBean)mobileList.get(i); String
			 * sCode=String.valueOf(bean.get("scode")); String
			 * sName=String.valueOf(bean.get("sname")); String
			 * moblie=String.valueOf(bean.get("mobile")); String
			 * rCode=String.valueOf(bean.get("rcode")); String
			 * rName=String.valueOf(bean.get("rname")); String
			 * content=smsForm.getMsgContent();
			 * //mobileSet[i]=String.valueOf(bean.get("mobile"));
			 * sendToHsDb("web",sCode,sName,moblie,rCode,rName,content); }
			 * //sendAll("9901", mobileSet, smsForm.getMsgContent(), 1);
			 */
			// }
			break;
		}
		String type = smsForm.getSettype();
		if (type == null)
			return init(mapping, form, request, response);
		else if ("gs".equals(type))
			return gsinit(mapping, form, request, response); // 群发
		else
			// ("fs".equals(type))
			return sinit(mapping, form, request, response);
	}

	/**
	 * 移动短信发送方法
	 *
	 * @param smsid
	 *            类型为int 短信的唯一标识，0-999999999之间，不重复
	 * @param mobile
	 *            手机号
	 * @param content
	 *            内容
	 */
	public void send(long smsid, String[] mobile, String content) {
		APIClient apiClient = new APIClient();

		if (mobile.length != 0) {
			for (int i = 0; i < mobile.length; i++) {

				if (apiClient.init("10.156.1.18", "xtbgxt", "hbycxtbgxt",
						"xtbg") == 0)// 初始化方法,其参数为ip,loginName,loginPwd,apicode

				{
					// apiClient.sendSM((String)mobile[i],content,Long.valueOf(smsid));
					int j = apiClient.sendSM(mobile[i], content, smsid);
					if (j == 0) {
						System.out.println("ok");

					} else {
						System.out.println("发送失败");
					}
				} else {
					System.out.println("init error...");
				}

			}
		}
	}

	/**
	 * 联通短信的发送方法
	 *
	 * @param smsid
	 *            类型为String 短信的唯一标识，0-999999999之间，不重复
	 * @param mobile
	 *            手机号
	 * @param content
	 *            内容
	 * @param timeout
	 *            短信的有效时间,超过该时间回复将,无法收到.以小时为单位.
	 */
	public void send(String smsid, String[] mobile, String content, int timeout) {

		Send send = new Send("10.156.1.26", "xtbg", "hbycxtbg");
		int k = send.Sendr(mobile, content, smsid, timeout);
		if (k == 0) {
			System.out.println("ok");
		} else {
			System.out.println("发送失败");
		}

	}

	/**
	 * 综合短信发送 联通移动的合为一个方法.
	 *
	 * @return
	 */
	public void sendAll(String smsid, String[] mobile, String content,
			int timeout) {
		APIClient apiClient = new APIClient();
		if (mobile.length != 0) {
			for (int i = 0; i < mobile.length; i++) {
				if (mobile[i].startsWith("134") || mobile[i].startsWith("135")
						|| mobile[i].startsWith("136")
						|| mobile[i].startsWith("137")
						|| mobile[i].startsWith("138")
						|| mobile[i].startsWith("139")
						|| mobile[i].startsWith("159")) {
					if (apiClient.init("10.156.1.18", "xtbgxt", "hbycxtbgxt",
							"xtbg") == 0)// 初始化成功
					{
						// apiClient.sendSM((String)mobile[i],content,Long.valueOf(smsid));
						int j = apiClient.sendSM(mobile[i], content,
								Long.parseLong(smsid));
						if (j == 0) {
							System.out.println("ok");

						} else {
							System.out.println("发送失败");
						}
					}

				} else {
					String[] sendMobile = new String[1];
					sendMobile[0] = mobile[i];
					Send send = new Send("10.156.1.26", "xtbg", "hbycxtbg");
					int k = send.Sendr(sendMobile, content, smsid, timeout);
					if (k == 0) {
						System.out.println("ok");
					} else {
						System.out.println("发送失败");
					}
				}
			}
		}
	}

	/**
	 * 移动的取回复的方法
	 *
	 * @return
	 */
	public MOItem[] getReceive()// 取回复的方法
	{
		APIClient apiClient = new APIClient();
		ArrayList alist = new ArrayList();
		ArrayList alistL = null;
		MOItem[] moitem = null;
		if (apiClient.init("10.156.1.18", "xtbg", "hbycxtbg", "1") == 0)// 初始化方法,其参数为ip,loginName,loginPwd,apicode
		{
			moitem = apiClient.receiveSM();

		}

		alist.add(alistL);

		return moitem;
	}

	/**
	 * 移动回复数据展示
	 */
	public void show(MOItem[] moitem) {

		if (moitem.length != 0) {
			for (int i = 0; i < moitem.length; i++) {
				String mobile = moitem[i].getMobile();
				String conten = moitem[i].getContent();
				long sms = moitem[i].getSmID();
			}
		}
	}

	/**
	 * 联通取回复数据
	 *
	 * @return
	 */
	public ArrayList getReply() {
		ArrayList alist = null;
		Reply r = new Reply("10.156.1.26", "xtbg", "hbycxtbg");
		alist = r.getReply();
		return alist;
	}

	/**
	 * 联通数据的回复
	 */
	public void showL(ArrayList alist) {
		for (int i = 0; i < alist.size(); i += 3) {
			String smsid = (String) alist.get(i);
			String mobile = (String) alist.get(i + 1);
			String content = (String) alist.get(i + 2);
		}
	}

	/**
	 * 移动取回执
	 *
	 * @return
	 */
	public RPTItem[] getIMReport() {
		APIClient apiClient = new APIClient();
		ArrayList alist = new ArrayList();

		RPTItem[] rptIte = null;
		if (apiClient.init("10.156.1.18", "xtbg", "hbycxtbg", "1") == 0)// 初始化方法,其参数为ip,loginName,loginPwd,apicode
		{
			rptIte = apiClient.receiveRPT();// apiClient.receiveRPT()方法的返回值为
											// RPTItem[]
			alist.add(rptIte);
		}
		return rptIte;
	}

	/**
	 *
	 * 移动回执展示
	 */
	public void reportIMShow(RPTItem[] rpt) {
		for (int i = 0; i < rpt.length; i++) {
			long smsid = rpt[i].getSmID();
			String mobile = rpt[i].getMobile();
			int stat = rpt[i].getCode();
		}
	}

	/**
	 * 联通取回执
	 */
	/*
	 * public ArrayList getReport() { ArrayList alistL=null; Report r=new
	 * Report("10.156.1.26","xtbg","hbycxtbg"); alistL=r.getReport(); return
	 * alistL; }
	 */
	/*
	 * public void reportLShow(ArrayList alist) { for(int
	 * i=0;i<alist.size();i+=3) { String smsid=(String)alist.get(i); String
	 * mobile=(String)alist.get(i+1); String content=(String)alist.get(i+2); } }
	 */
	/* wangp editor 08.12.25 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SmsManageForm smsForm = (SmsManageForm) form;
		// 发送短信前先判断余额是否足够
		byte state;
		// 错误短信个数
		int errnum = 0;
		// 余额
		int balance;
		// 更新短信状态
		int updatestate = 0;
		SMS sms = null;
		// 登陆短信平台账号
		String userid = SystemConfig.getString("MSG_USER");
		String password = SystemConfig.getString("MSG_PWD");
		try {
			sms = new SMS();
			balance = sms.login(userid, password);
			if (balance < 0) {
				request.setAttribute("message.information", "短信平台目前无法登陆！");
			}

			CallHelper helper = initializeCallHelper("getSendInfo", smsForm,
					request, false);
			helper.setParam("userId", getPersonId(request));
			helper.execute();
			int rtState = helper.getState();
			switch (rtState) {
			case 2: // 密码不对
				request.setAttribute("message.code", String.valueOf(rtState));
				request.setAttribute("message.information",
						helper.getOutput("message"));
				break;
			case 5:
				request.setAttribute("message.information",
						helper.getOutput("message"));
			case 0:
				List mobileList = helper.getResult(0);
				if (mobileList.size() == 0) {
					request.setAttribute("message.information", "列表中无手机号码可发送！");
				} else {
					if (balance < mobileList.size() + 10) {
						request.setAttribute("message.information",
								"短信平台目前余额不足，请续费！");
					}
					;
					for (int i = 0; i < mobileList.size(); i++) {
						BasicDynaBean bean = (BasicDynaBean) mobileList.get(i);
						String msgid = String.valueOf(bean.get("msgid"));
						String rMobile = String.valueOf(bean.get("rmobile"));
						String msgContent = String.valueOf(bean
								.get("msgcontent"))
								+ "   --"
								+ String.valueOf(bean.get("spersonname"));
						try {
							// 每次发送前查询余额
							// if (sms.login(userid, password)==0){
							// request.setAttribute("message.information","短信平台目前余额为0！");
							// };
							// 增加发送号码

							// final int maxPhoneLength=sms.getMaxPhoneLength();
							sms.addPhone(rMobile);
							// 增加发送内容
							sms.addMsg(msgContent, null);
							// 开始发送
							state = sms.send();
							// 休眠100毫秒
							Thread.sleep(100);
							// 如果短信发送失败
							if (state < 0) {
								errnum = errnum + 1;
							} else {
								// 更新已经成功发送的短信状态到数据库
								CallHelper updatehelper = initializeCallHelper(
										"updatesmsrecord", smsForm, request,
										false);
								updatehelper.setParam("msgid", msgid);
								updatehelper.execute();
							}
						} catch (Exception ex) {
							updatestate = -1;
						}
					}
					if (errnum > 0)
						request.setAttribute("message.information",
								"有部分短信发送失败，信息已保存，请查询后重新发送！");
					else if (updatestate < 0)
						request.setAttribute("message.information",
								"发送失败！信息已保存，请查询后重新发送");
					else
						request.setAttribute("message.information", "发送成功！");
					//

				}
				break;
			}
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("message.information", "发送失败！");
		} finally {
			if (sms != null)
				sms.logout();
		}
		String type = smsForm.getSettype();
		if (type == null)
			return init(mapping, form, request, response);
		else if ("gs".equals(type))
			return gsinit(mapping, form, request, response); // 群发
		else
			return sinit(mapping, form, request, response);
	}

	public ActionForward sendMsgByDepart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		SmsManageForm smsForm = (SmsManageForm) form;
		CallHelper helper = initializeCallHelper("getSendInfoByDepart",
				smsForm, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		int rtState = helper.getState();
		switch (rtState) {
		case 2: // 密码不对
			request.setAttribute("message.code", String.valueOf(rtState));
			request.setAttribute("message.information",
					helper.getOutput("message"));
			break;
		case 5:
			request.setAttribute("message.information",
					helper.getOutput("message"));
		case 0:
			request.setAttribute("message.information", "短信已发送！");
			break;
		}

		String type = smsForm.getSettype();
		if (type == null)
			return initByDepart(mapping, form, request, response);
		else if ("gs".equals(type))
			return gsinit(mapping, form, request, response); // 群发
		else
			return sinit(mapping, form, request, response);
	}

	public ActionForward sendMsgBynew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SmsManageForm smsForm = (SmsManageForm) form;
		CallHelper helper = initializeCallHelper("getSendInfoByNew", smsForm,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		int rtState = helper.getState();
		switch (rtState) {
		case 2: // 密码不对
			request.setAttribute("message.code", String.valueOf(rtState));
			request.setAttribute("message.information",
					helper.getOutput("message"));
			break;
		case 5:
			request.setAttribute("message.information",
					helper.getOutput("message"));
		case 0:
			request.setAttribute("message.information", "短信已发送！");
			break;
		}

		String type = smsForm.getSettype();
		if (type == null)
			return initnew(mapping, form, request, response);
		else if ("gs".equals(type))
			return gsinitnew(mapping, form, request, response); // 群发
		else
			return sinit(mapping, form, request, response);
	}
	/* com.nb.adv.system.SmsManageAction.java End */
}
