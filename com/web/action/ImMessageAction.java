package com.web.action;

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

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.web.CoreDispatchAction;
import com.web.form.MessageForm;
import com.web.pojos.MessageData;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;
import com.zl.util.TradeList;

public class ImMessageAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(ImMessageAction.class);

	private static String cacheKey = "msgpersontree";

	private static int cachePeriod = 360000;

	private GeneralCacheAdministrator cacheAdmin = new GeneralCacheAdministrator();

	/**
	 * 收件箱
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward inbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("inbox");
		MessageForm aform = (MessageForm) form;

		String pageOffSet = request.getParameter("pager.offset");
		int pageSize = aform.getPageSize() == null
				|| aform.getPageSize().trim().length() == 0 ? 10 : Integer
				.parseInt(aform.getPageSize());
		int pageNo = pageOffSet == null || pageOffSet.trim().length() == 0 ? 1
				: ((Integer.parseInt(pageOffSet) + pageSize) / pageSize);
		CallHelper helper = initializeCallHelper("getImReceiveHistory", form,
				request, false);
		helper.setParam("pageNo", new Integer(pageNo));
		helper.setParam("pageSize", new Integer(pageSize));
		helper.setParam("receiverId", getPersonId(request));
		helper.execute();
		log.info("执行开始");
		List list = helper.getResult("results");
		Integer msgCount = new Integer((String) helper.getOutput("msgCount"));
		List ret = new ArrayList();
		MessageData msg = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				msg = new MessageData();
				DynaBean bean = (DynaBean) list.get(i);
				msg.setMessageContent(MethodFactory.getThisString(bean
						.get("content")));
				msg.setSenderName(MethodFactory.getThisString(bean
						.get("sender")));
				msg.setSendTime(MethodFactory.getThisString(bean
						.get("send_time")));
				msg.setMsgTypeName(MethodFactory.getThisString(bean
						.get("msgtypename")));
				msg.setMsgType(MethodFactory.getThisString(bean.get("msgtype")));
				ret.add(msg);
			}
		}
		request.setAttribute("isAdmin", isSuperAdmin(request));
		log.info("执行结束");
		request.setAttribute("MsgCount", msgCount);
		request.setAttribute("ImMessages", ret);
		return forward;

	}

	/**
	 * 发件箱
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward outbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("outbox");
		MessageForm aform = (MessageForm) form;
		String pageOffSet = request.getParameter("pager.offset");
		int pageSize = aform.getPageSize() == null
				|| aform.getPageSize().trim().length() == 0 ? 10 : Integer
				.parseInt(aform.getPageSize());
		int pageNo = pageOffSet == null || pageOffSet.trim().length() == 0 ? 1
				: ((Integer.parseInt(pageOffSet) + pageSize) / pageSize);
		CallHelper helper = initializeCallHelper("getImSendHistory", form,
				request, false);
		helper.setParam("pageNo", new Integer(pageNo));
		helper.setParam("pageSize", new Integer(pageSize));
		helper.setParam("senderId", getPersonId(request));
		helper.execute();
		List list = helper.getResult("results");
		Integer msgCount = new Integer((String) helper.getOutput("msgCount"));
		List ret = new ArrayList();
		MessageData msg = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DynaBean bean = (DynaBean) list.get(i);
				msg = new MessageData();
				msg.setReceiveId(MethodFactory.getThisString(bean
						.get("receiveid")));
				msg.setMessageContent(MethodFactory.getThisString(bean
						.get("content")));
				msg.setReceiverName(MethodFactory.getThisString(bean
						.get("receiver")));
				msg.setSendTime(MethodFactory.getThisString(bean
						.get("send_time")));
				msg.setMsgTypeName(MethodFactory.getThisString(bean
						.get("msgtypename")));
				msg.setMsgType(MethodFactory.getThisString(bean.get("msgtype")));
				ret.add(msg);
			}
		}
		request.setAttribute("isAdmin", isSuperAdmin(request));
		request.setAttribute("MsgCount", msgCount);
		request.setAttribute("ImMessages", ret);
		return forward;
	}

	/**
	 * 站内消息发送给多人
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward msgmultisendinit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("msgmultisendinit");
	}

	/**
	 * 系统消息发送
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward systemmsginit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("systemmsginit");
	}

	/**
	 * 每日一报
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward dailymsginit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("dailymsginit");
	}

	/**
	 * 一月一报
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward monthlymsginit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("monthlymsginit");
	}

	/**
	 * 人员树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward persontree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List ret = new ArrayList();
		try {
			cacheAdmin.flushAll();
			ret = (List) cacheAdmin.getFromCache(cacheKey, cachePeriod);
		} catch (NeedsRefreshException nre) {
			try {
				CallHelper helper = initializeCallHelper(
						"getDepareAreaPersonTreeAll", form, request, false);
				helper.execute();
				ret = helper.getResult("results");
				// Store in the cache
				cacheAdmin.putInCache(cacheKey, ret);
			} catch (Exception ex) {
				// We have the current content if we want fail-over.
				// myValue = (String) nre.getCacheContent();
				// It is essential that cancelUpdate is called if the
				// cached content is not rebuilt
				cacheAdmin.cancelUpdate(cacheKey);
			}

		}
		request.setAttribute("person.tree", ret);
		ActionForward forward = mapping.findForward("persontree");
		return forward;
	}

	/**
	 * 文件发送给单人
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward msgsendinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		request.setAttribute("person.list", getPersonInfo());
		ActionForward forward = mapping.findForward("msgsendinit");
		return forward;
	}

	/**
	 * 发送信件
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sendmsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("sendmsg");

		MessageForm aform = (MessageForm) form;
		CallHelper helper = initializeCallHelper("sendImMsg", form, request,
				false);
		String receiverIds = aform.getReceiverIds();
		String content = aform.getMsgContent();
		String msgType = aform.getMsgType();
		msgType = msgType == null || msgType.trim().length() == 0 ? "1"
				: msgType;
		helper.setParam("senderId", getPersonId(request));
		helper.setParam("receiveType", "1");
		helper.setParam("receierIds", receiverIds);
		helper.setParam("msgContent", content);
		helper.setParam("receiveType", "1");
		helper.setParam("msgType", msgType);
		helper.setParam("msgTitle", aform.getMsgTitle());
		helper.execute();
		String message = (String) helper.getOutput("message");
		request.setAttribute("message", message);
		request.setAttribute("msgType", msgType);
		return forward;

	}

	/**
	 * 常用消息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward commonMsgInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("comonMsgInit");
		CallHelper helper = initializeCallHelper("getIMCommonMsg", form,
				request, false);
		helper.execute();
		List list = helper.getResult("results");
		MessageData msg = null;
		List ret = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				msg = new MessageData();
				DynaBean bean = (DynaBean) list.get(i);
				msg.setMessageContent(MethodFactory.getThisString(bean
						.get("msgcontent")));
				msg.setMsgId(MethodFactory.getThisString(bean.get("id")));
				ret.add(msg);
			}
		}
		return forward;
	}

	/**
	 * 保存常用消息设定
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savecommonmsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("comonMsgInit");
		CallHelper helper = initializeCallHelper("saveIMCommonMsg", form,
				request, false);
		helper.execute();
		return forward;
	}

	private static List<OptionHold> personInfoList;

	private List<OptionHold> getPersonInfo() {
		if (personInfoList == null) {
			try {
				personInfoList = new ArrayList<OptionHold>();
				List<BasicDynaBean> ret = TradeList.getPersonInfoList();
				if (personInfoList != null && ret.size() > 0) {
					for (int i = 0; i < ret.size(); i++) {
						DynaBean bean = (DynaBean) ret.get(i);
						String personId = MethodFactory.getThisString(bean
								.get("personid"));
						String personName = MethodFactory.getThisString(bean
								.get("personname"));
						String departName = MethodFactory.getThisString(bean
								.get("departname"));
						personInfoList.add(new OptionHold(personId, departName
								+ " | " + personName));
						log.info("personName=" + departName + "|" + personName);
					}
				}
			} catch (Exception e) {
				log.error("getPersonInfoList error:::" + e.toString());
			}

		}
		return personInfoList;
	}

	public ActionForward getpersonbyrole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List ret = new ArrayList();
		CallHelper helper = initializeCallHelper("persontreebyrole", form,
				request, false);
		helper.execute();
		ret = helper.getResult("results");

		request.setAttribute("person.tree", ret);
		return mapping.findForward("persontree");
	}
}
