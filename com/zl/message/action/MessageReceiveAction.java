package com.zl.message.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.util.ToolKit;
import com.common.util.ZipFileUtil;
import com.qmx.dateutils.DateUtils;
import com.qmx.ioutils.FileUtils;
import com.qmx.strutils.StringUtils;
import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.message.form.MessageReceiveForm;
import com.zl.message.pojos.MessageFileVO;
import com.zl.message.pojos.MessageReceiveVO;
import com.zl.message.pojos.MessageVO;
import com.zl.util.MethodFactory;
import com.zl.util.OptionHold;

public class MessageReceiveAction extends CoreDispatchAction {

	private static final Log log = LogFactory
			.getLog(MessageReceiveAction.class);

	public ActionForward inbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("inbox");
		MessageReceiveForm aform = (MessageReceiveForm) form;
		int pageNo = 1;
		int pageSize = 15;
		if (aform.getPageNo() != null)
			pageNo = aform.getPageNo().intValue();
		if (aform.getPageSize() != null)
			pageNo = aform.getPageSize().intValue();
		String pageOffSet = request.getParameter("pager.offset");
		pageSize = aform.getPageSize() == null ? 15 : aform.getPageSize()
				.intValue();
		if (pageOffSet != null && pageOffSet.trim().length() > 0) {
			pageNo = (Integer.parseInt(pageOffSet) + pageSize) / pageSize;
		}
		// pageNo=pageOffSet==null||pageOffSet.trim().length()==0?1:((Integer.parseInt(pageOffSet)+pageSize)/pageSize);
		aform.setPageNo(new Integer(pageNo));
		String keywords = aform.getKeywords();
		CallHelper call = initializeCallHelper("getMsgReceiveList", aform,
				request, false);
		call.setParam("pageNo", String.valueOf(pageNo));
		call.setParam("pageSize", String.valueOf(pageSize));
		call.execute();
		request.setAttribute("pageNo", new Integer(pageNo));

		String totalCount = (String) call.getOutput(0);
		request.setAttribute("MessageCount", new Integer(totalCount));

		List ret = (List) call.getResult(0);
		List retu = new ArrayList();
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				MessageVO msg = new MessageVO();
				BasicDynaBean bean = (BasicDynaBean) ret.get(i);
				String title = MethodFactory.getThisString(bean.get("title"));
				String sendtime = MethodFactory.getThisString(bean
						.get("senddatetime"));
				String type = MethodFactory.getThisString(bean.get("typename"));
				String sender = MethodFactory.getThisString(bean.get("sender"));
				String state = MethodFactory.getThisString(bean.get("state"));
				String stateName = MethodFactory.getThisString(bean
						.get("statename"));
				String msgid = MethodFactory.getThisString(bean
						.get("messageid"));
				msg.setMsgType(type);
				msg.setMessageId(msgid);
				msg.setSenderName(sender);
				msg.setSendTime(sendtime);
				msg.setState(state);
				msg.setStateName(stateName);
				msg.setShorttitle(title != null && title.trim().length() > 18 ? title
						.substring(0, 18) : title);
				msg.setTitle(title);
				retu.add(msg);
			}
		}
		if (aform.getStartDate() == null)
			aform.setStartDate(DateUtils.getCurrentMonthFirst());
		if (aform.getEndDate() == null)
			aform.setEndDate(DateUtils.getDate());
		if (aform.getKeywords() == null)
			aform.setKeywords("关键字");
		request.setAttribute("MsgList", retu);
		request.setAttribute("searchtype.list", getInboxSearchType());
		return forward;
	}

	public ActionForward outbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("outbox");
		MessageReceiveForm aform = (MessageReceiveForm) form;
		int pageNo = 1;
		int pageSize = 15;
		if (aform.getPageNo() != null)
			pageNo = aform.getPageNo().intValue();
		if (aform.getPageSize() != null)
			pageNo = aform.getPageSize().intValue();
		String pageOffSet = request.getParameter("pager.offset");
		pageSize = aform.getPageSize() == null ? 15 : aform.getPageSize()
				.intValue();
		if (pageOffSet != null && pageOffSet.trim().length() > 0) {
			pageNo = (Integer.parseInt(pageOffSet) + pageSize) / pageSize;
		}
		request.setAttribute("pageNo", new Integer(pageNo));
		CallHelper call = initializeCallHelper("getMsgSendList", form, request,
				false);
		call.setParam("pageNo", String.valueOf(pageNo));
		call.setParam("pageSize", String.valueOf(pageSize));
		call.execute();

		String totalCount = (String) call.getOutput(0);
		request.setAttribute("MessageCount", new Integer(totalCount));

		List ret = (List) call.getResult(0);
		List ret2 = (List) call.getResult(1);
		List retu = new ArrayList();
		int flag = 0;
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				MessageVO msg = new MessageVO();
				BasicDynaBean bean = (BasicDynaBean) ret.get(i);
				String title = MethodFactory.getThisString(bean.get("title"));
				String sendtime = MethodFactory.getThisString(bean
						.get("senddatetime"));
				// sendtime=ToolKit.filterTimetohm(sendtime);
				String type = MethodFactory.getThisString(bean.get("typename"));
				String receiver = MethodFactory.getThisString(bean
						.get("receiver"));
				String msgid = MethodFactory.getThisString(bean
						.get("messageid"));
				msg.setMsgType(type);
				msg.setMessageId(msgid);
				msg.setReceiverName(receiver);
				msg.setSendTime(sendtime);
				msg.setTitle(title);
				msg.setShorttitle(title != null && title.trim().length() > 18 ? title
						.substring(0, 18) : title);
				String receiveBuffer = "";
				boolean tag = true;
				while (ret2 != null && flag < ret2.size() && tag) {
					BasicDynaBean bean2 = (BasicDynaBean) ret2.get(flag);
					String msgId = MethodFactory.getThisString(bean2
							.get("msgid"));
					String receiverName = MethodFactory.getThisString(bean2
							.get("personname"));
					if (msgId.equals(msgid)) {
						if (receiveBuffer.length() <= 7) {
							if (receiveBuffer.trim().length() == 0)
								receiveBuffer = receiverName;
							else {
								if (receiveBuffer.trim().indexOf("等") == -1) {
									receiveBuffer = receiveBuffer + "等";
								}
							}

						}
						flag++;
					} else
						tag = false;

				}
				if (receiveBuffer.trim().length() > 0) {
					receiveBuffer = receiveBuffer.trim().substring(0,
							receiveBuffer.trim().length());
				}

				msg.setReceiverName(receiveBuffer);
				retu.add(msg);
			}

		}
		if (aform.getStartDate() == null)
			aform.setStartDate(DateUtils.getCurrentMonthFirst());
		if (aform.getEndDate() == null)
			aform.setEndDate(DateUtils.getDate());
		if (aform.getKeywords() == null)
			aform.setKeywords("关键字");
		request.setAttribute("MsgList", retu);
		request.setAttribute("senderid", getPersonId(request));
		request.setAttribute("searchtype.list", getOutboxSearchType());
		return forward;
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("detail");
		MessageReceiveForm aform = (MessageReceiveForm) form;
		String pageNo = aform.getPageNo() == null ? "1" : String.valueOf(aform
				.getPageNo());
		String pageOffSet = request.getParameter("pager.offset");

		String senderpersonid = "";// 发件人
		// BOX可以来判断是否是收件箱 1:收件箱
		String box = "-1";
		if (aform.getBox() != null)
			box = aform.getBox();
		String keywords = request.getParameter("keywords");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchType = request.getParameter("searchType");
		request.setAttribute("keywords", keywords);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("searchType", searchType);
		request.setAttribute("pageNo", new Integer(pageNo));
		request.setAttribute("pageOffSet", pageOffSet);
		CallHelper call = initializeCallHelper("getMsgDetail", form, request,
				false);
		call.setParam("msgId", String.valueOf(aform.getMsgId()));
		call.execute();
		List msgs = (List) call.getResult(0);
		List receiveList = (List) call.getResult(1);
		List files = (List) call.getResult(2);
		if (msgs != null && msgs.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) msgs.get(0);
			String title = MethodFactory.getThisString(bean.get("title"));
			String content = MethodFactory.getThisString(bean.get("content"));
			String sendDate = MethodFactory.getThisString(bean.get("senddate"));
			String sendTime = MethodFactory.getThisString(bean.get("sendtime"));
			String type = MethodFactory.getThisString(bean.get("type"));
			// String
			// typename=MethodFactory.getThisString(bean.get("typename"));
			String msgId = MethodFactory.getThisString(bean.get("messageid"));
			senderpersonid = MethodFactory.getThisString(bean
					.get("sendpersonid"));
			String sender = MethodFactory.getThisString(bean.get("personname"));
			String opshowreceiver = MethodFactory.getThisString(bean
					.get("opshowreceiver"));
			MessageVO msg = new MessageVO();
			msg.setContent(content);
			msg.setMessageId(msgId);
			msg.setMsgType(type);
			msg.setSendDate(sendDate);
			msg.setSendTime(sendTime);
			msg.setTitle(title);
			msg.setSenderName(sender);
			msg.setOpshowreceiver(opshowreceiver);

			request.setAttribute("MessageData", msg);
			// System.out.println(content);
			request.setAttribute("Mycontent", content);

			if (receiveList != null && receiveList.size() > 0) {
				List receretu = new ArrayList();
				for (int i = 0; i < receiveList.size(); i++) {
					MessageReceiveVO receV = new MessageReceiveVO();
					BasicDynaBean receBean = (BasicDynaBean) receiveList.get(i);
					String receiverId = MethodFactory.getThisString(receBean
							.get("receivepersonid"));
					// 是发件人，显示全部收件人；如果是收件人，则收件人仅显示自己
					// if(!getUser(request).getPersonId().equals(senderpersonid)
					// && !getUser(request).getPersonId().equals(receiverId))
					// continue;
					// 如果是收件箱并且密送，则收件人仅显示自己
					if (Integer.parseInt(box) == 1
							&& !getUser(request).getPersonId().equals(
									receiverId)
							&& Integer.parseInt(msg.getOpshowreceiver()) == 0)
						continue;
					String receiverName = MethodFactory.getThisString(receBean
							.get("personname"));
					String state = MethodFactory.getThisString(receBean
							.get("state"));
					String stateName = MethodFactory.getThisString(receBean
							.get("statename"));
					receV.setReceiverId(receiverId);
					receV.setReceiverName(receiverName);
					receV.setState(state);
					receV.setStateName(stateName);
					receretu.add(receV);
				}
				request.setAttribute("MessageReceivers", receretu);
				request.setAttribute("Receiversnum",
						MethodFactory.getThisString(receretu.size()));
				request.setAttribute("personname", getPersonName(request));
			}

			if (files != null && files.size() > 0) {
				List retfiles = new ArrayList();
				for (int i = 0; i < files.size(); i++) {
					BasicDynaBean fileBean = (BasicDynaBean) files.get(i);
					MessageFileVO file = new MessageFileVO();
					String fileid = MethodFactory.getThisString(fileBean
							.get("fileid"));
					String filename = MethodFactory.getThisString(fileBean
							.get("filename"));
					String savefilename = MethodFactory.getThisString(fileBean
							.get("savefilename"));
					String filetype = MethodFactory.getThisString(fileBean
							.get("filetype"));
					file.setFileId(fileid);
					file.setFileName(filename);
					file.setFileSaveName(savefilename);
					file.setFileType(filetype);
					retfiles.add(file);
				}
				request.setAttribute("MessageFiles", retfiles);
			}

			String optrashbox = request.getParameter("optrashbox");
			if (optrashbox != null && optrashbox.trim().length() > 0)
				request.setAttribute("optrashbox", "true");
			/**
			 * 更新信件状态为已读
			 */
			String opinbox = request.getParameter("opinbox");
			if (opinbox != null && opinbox.trim().length() > 0) {
				CallHelper call2 = initializeCallHelper(
						"updateMsgStatusByType", form, request, false);
				call2.setParam("msgIds", String.valueOf(aform.getMsgId()));
				call2.setParam("updateType", "1");
				call2.execute();
				request.setAttribute("opinbox", "true");
			}

		}

		// 商业公司人员阻止看到组织结构树
		CallHelper Helper = new CallHelper("getparentdeparidbyuser");
		Helper.setParam("personId", getPersonId(request));
		Helper.execute();
		List issy = Helper.getResult(0);
		String issycount = "";
		if (issy.size() > 0) {
			for (int i = 0; i < issy.size(); i++) {
				DynaBean bean = (DynaBean) issy.get(i);
				issycount = MethodFactory.getThisString(bean.get("issy"));
			}
		}

		request.setAttribute("issy", issycount);
		return forward;
	}

	public ActionForward recevers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("receivers");
		MessageReceiveForm aform = (MessageReceiveForm) form;
		CallHelper call = initializeCallHelper("getMsgReceiveStatus", form,
				request, false);
		call.setParam("msgId", String.valueOf(aform.getMsgId()));
		call.execute();
		List unreadreceiveList = (List) call.getResult(0);
		List readreceiveList = (List) call.getResult(1);
		if (unreadreceiveList != null && unreadreceiveList.size() > 0) {
			List receretu = new ArrayList();
			for (int i = 0; i < unreadreceiveList.size(); i++) {
				MessageReceiveVO receV = new MessageReceiveVO();
				BasicDynaBean receBean = (BasicDynaBean) unreadreceiveList
						.get(i);
				String receiverId = MethodFactory.getThisString(receBean
						.get("receivepersonid"));
				String receiverName = MethodFactory.getThisString(receBean
						.get("personname"));
				String state = MethodFactory.getThisString(receBean
						.get("state"));
				String stateName = MethodFactory.getThisString(receBean
						.get("statename"));
				receV.setReceiverId(receiverId);
				receV.setReceiverName(receiverName);
				receV.setState(state);
				receV.setStateName(stateName);
				receretu.add(receV);
			}
			request.setAttribute("UnReadReceivers", receretu);
			// request.setAttribute("UnReadReceivers",unreadreceiveList);
		}
		if (readreceiveList != null && readreceiveList.size() > 0) {
			List receretu = new ArrayList();
			for (int i = 0; i < readreceiveList.size(); i++) {
				MessageReceiveVO receV = new MessageReceiveVO();
				BasicDynaBean receBean = (BasicDynaBean) readreceiveList.get(i);
				String receiverId = MethodFactory.getThisString(receBean
						.get("receivepersonid"));
				String receiverName = MethodFactory.getThisString(receBean
						.get("personname"));
				String state = MethodFactory.getThisString(receBean
						.get("state"));
				String stateName = MethodFactory.getThisString(receBean
						.get("statename"));
				String receiveTime = MethodFactory.getThisString(receBean
						.get("receivetime"));
				receV.setReceiverId(receiverId);
				receV.setReceiverName(receiverName);
				receV.setState(state);
				receV.setStateName(stateName);
				receV.setReceiveTime(receiveTime);
				receretu.add(receV);
			}
			request.setAttribute("ReadReceivers", receretu);
		}
		return forward;
	}

	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		// String msgId=aform.getMsgId();
		String updateType = aform.getUpdateType();

		ActionForward forward = mapping.findForward("reply");
		CallHelper call = initializeCallHelper("getMsgDetail", form, request,
				false);
		call.setParam("msgId", String.valueOf(aform.getMsgId()));
		call.execute();
		List msgs = (List) call.getResult(0);
		if (msgs != null && msgs.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) msgs.get(0);
			String title = "回复："
					+ MethodFactory.getThisString(bean.get("title"));
			String content = MethodFactory.getThisString(bean.get("content"));
			String sendDateTime = MethodFactory.getThisString(bean
					.get("senddatetime"));
			String type = MethodFactory.getThisString(bean.get("type"));
			String typename = MethodFactory.getThisString(bean.get("typename"));
			String msgId2 = MethodFactory.getThisString(bean.get("messageid"));
			String senderpersonid = MethodFactory.getThisString(bean
					.get("sendpersonid"));
			String sender = MethodFactory.getThisString(bean.get("personname"));
			MessageVO msg = new MessageVO();
			msg.setContent(content);
			msg.setMessageId(msgId2);
			msg.setMsgType(type);
			msg.setSendTime(sendDateTime);
			msg.setTitle(title);
			msg.setSenderName(sender);
			msg.setSenderId(senderpersonid);

			request.setAttribute("MessageData", msg);
		}
		return forward;

	}

	public ActionForward transmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		String msgId = aform.getMsgId();
		ActionForward forward = mapping.findForward("transmit");
		CallHelper call = initializeCallHelper("getMsgDetail", form, request,
				false);
		call.setParam("msgId", String.valueOf(aform.getMsgId()));
		call.execute();
		List msgs = (List) call.getResult(0);
		List files = (List) call.getResult(2);
		if (msgs != null && msgs.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) msgs.get(0);
			String title = "转发："
					+ MethodFactory.getThisString(bean.get("title"));
			String content = MethodFactory.getThisString(bean.get("content"));
			String sendDateTime = MethodFactory.getThisString(bean
					.get("senddatetime"));
			String type = MethodFactory.getThisString(bean.get("type"));
			String typename = MethodFactory.getThisString(bean.get("typename"));
			String msgId2 = MethodFactory.getThisString(bean.get("messageid"));
			String senderpersonid = MethodFactory.getThisString(bean
					.get("sendpersonid"));
			String sender = MethodFactory.getThisString(bean.get("personname"));
			MessageVO msg = new MessageVO();
			msg.setContent(content);
			msg.setMessageId(msgId2);
			msg.setMsgType(type);
			msg.setSendTime(sendDateTime);
			msg.setTitle(title);
			msg.setSenderName(sender);
			msg.setSenderId(senderpersonid);
			aform.setMsgId(msgId);
			aform.setTitle(title);
			aform.setContent(content);
			request.setAttribute("MessageData", msg);
			if (files != null && files.size() > 0) {
				List retfiles = new ArrayList();
				for (int i = 0; i < files.size(); i++) {
					BasicDynaBean fileBean = (BasicDynaBean) files.get(i);
					MessageFileVO file = new MessageFileVO();
					String fileid = MethodFactory.getThisString(fileBean
							.get("fileid"));
					String filename = MethodFactory.getThisString(fileBean
							.get("filename"));
					String savefilename = MethodFactory.getThisString(fileBean
							.get("savefilename"));
					String filetype = MethodFactory.getThisString(fileBean
							.get("filetype"));
					file.setFileId(fileid);
					file.setFileName(filename);
					file.setFileSaveName(savefilename);
					file.setFileType(filetype);
					retfiles.add(file);
				}
				request.setAttribute("MessageFiles", retfiles);
			}
		}
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		String pageNo = aform.getPageNo() == null ? "1" : String.valueOf(aform
				.getPageNo());
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pager.offset",
				String.valueOf(Integer.parseInt(pageNo) * 15));
		String[] msgids = aform.getMsgids();
		if (msgids != null && msgids.length > 0) {
			String _msgids = ToolKit.join(msgids);
			CallHelper call2 = initializeCallHelper("updateMsgStatusByType",
					form, request, false);
			call2.setParam("msgIds", _msgids);
			call2.setParam("updateType", aform.getUpdateType());
			call2.execute();
		}
		String updateType = aform.getUpdateType();

		if ("2".equals(aform.getUpdateType())) {
			return mapping.findForward("toinbox");
		} else if ("5".equals(aform.getUpdateType())
				|| "0".equals(aform.getUpdateType())) {
			return mapping.findForward("retrashbox");
		} else
			return mapping.findForward("tooutbox");
	}

	public ActionForward dotransmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper call2 = initializeCallHelper("transmitMsg", form, request,
				false);
		call2.setParam("personId", getPersonId(request));
		call2.execute();
		request.setAttribute("message", "发送成功");
		return mapping.findForward("transmit");
	}

	public ActionForward manageinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		aform.setStartDate(DateUtils.getDate(-30));
		aform.setEndDate(DateUtils.getDate());
		return mapping.findForward("manageinit");
	}

	public ActionForward manageshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		int pageNo = 1;
		int pageSize = 15;
		if (aform.getPageNo() != null)
			pageNo = aform.getPageNo().intValue();
		if (aform.getPageSize() != null)
			pageNo = aform.getPageSize().intValue();
		String pageOffSet = request.getParameter("pager.offset");
		pageSize = aform.getPageSize() == null ? 15 : aform.getPageSize()
				.intValue();
		if (pageOffSet != null && pageOffSet.trim().length() > 0) {
			pageNo = (Integer.parseInt(pageOffSet) + pageSize) / pageSize;
		}
		request.setAttribute("pageNo", new Integer(pageNo));

		CallHelper call = initializeCallHelper("getMessageQuery4Manage", form,
				request, false);
		call.setParam("pageNo", String.valueOf(pageNo));
		call.setParam("pageSize", String.valueOf(pageSize));
		call.execute();
		request.setAttribute("caption.list", call.getResult(0));
		request.setAttribute("result.list", call.getResult(1));

		return mapping.findForward("manageshow");
	}

	public ActionForward managedelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		String msgId = aform.getMsgId();
		CallHelper call2 = initializeCallHelper("updateMsgStatusByType", form,
				request, false);
		call2.setParam("msgIds", msgId);
		call2.setParam("updateType", "3");
		call2.execute();
		return manageshow(mapping, form, request, response);
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("submit");
	}

	public ActionForward trashbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("trashbox");
		MessageReceiveForm aform = (MessageReceiveForm) form;
		int pageNo = 1;
		int pageSize = 15;
		if (aform.getPageNo() != null)
			pageNo = aform.getPageNo().intValue();
		if (aform.getPageSize() != null)
			pageNo = aform.getPageSize().intValue();
		String pageOffSet = request.getParameter("pager.offset");
		pageSize = aform.getPageSize() == null ? 15 : aform.getPageSize()
				.intValue();
		if (pageOffSet != null && pageOffSet.trim().length() > 0) {
			pageNo = (Integer.parseInt(pageOffSet) + pageSize) / pageSize;
		}
		// pageNo=pageOffSet==null||pageOffSet.trim().length()==0?1:((Integer.parseInt(pageOffSet)+pageSize)/pageSize);
		aform.setPageNo(new Integer(pageNo));
		CallHelper call = initializeCallHelper("GetReceiveTrashList", aform,
				request, false);
		call.setParam("pageNo", String.valueOf(pageNo));
		call.setParam("pageSize", String.valueOf(pageSize));
		call.execute();
		request.setAttribute("pageNo", new Integer(pageNo));

		String totalCount = (String) call.getOutput(0);
		request.setAttribute("MessageCount", new Integer(totalCount));

		List ret = (List) call.getResult(0);
		List retu = new ArrayList();
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				MessageVO msg = new MessageVO();
				BasicDynaBean bean = (BasicDynaBean) ret.get(i);
				String title = MethodFactory.getThisString(bean.get("title"));
				String sendtime = MethodFactory.getThisString(bean
						.get("senddatetime"));
				String type = MethodFactory.getThisString(bean.get("typename"));
				String sender = MethodFactory.getThisString(bean.get("sender"));
				String state = MethodFactory.getThisString(bean.get("state"));
				String stateName = MethodFactory.getThisString(bean
						.get("statename"));
				String msgid = MethodFactory.getThisString(bean
						.get("messageid"));
				msg.setMsgType(type);
				msg.setMessageId(msgid);
				msg.setSenderName(sender);
				msg.setSendTime(sendtime);
				msg.setState(state);
				msg.setStateName(stateName);
				msg.setShorttitle(title != null && title.trim().length() > 18 ? title
						.substring(0, 18) : title);
				msg.setTitle(title);
				retu.add(msg);
			}
		}
		if (aform.getStartDate() == null)
			aform.setStartDate(DateUtils.getCurrentMonthFirst());
		if (aform.getEndDate() == null)
			aform.setEndDate(DateUtils.getDate());
		if (aform.getKeywords() == null)
			aform.setKeywords("关键字");
		request.setAttribute("MsgList", retu);
		request.setAttribute("searchtype.list", getInboxSearchType());
		return forward;
	}

	public static ArrayList<OptionHold> getInboxSearchType() {
		ArrayList<OptionHold> list = new ArrayList<OptionHold>();

		list.add(new OptionHold("2", "按标题"));
		list.add(new OptionHold("1", "按发件人"));

		return list;
	}

	public static ArrayList<OptionHold> getOutboxSearchType() {
		ArrayList<OptionHold> list = new ArrayList<OptionHold>();
		list.add(new OptionHold("2", "按标题"));
		list.add(new OptionHold("1", "按收件人"));
		return list;
	}

	public ActionForward callback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm aform = (MessageReceiveForm) form;
		String pageNo = aform.getPageNo() == null ? "1" : String.valueOf(aform
				.getPageNo());
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pager.offset",
				String.valueOf(Integer.parseInt(pageNo) * 15));
		String[] msgids = aform.getMsgids();
		if (msgids != null && msgids.length > 0) {
			String _msgids = ToolKit.join(msgids);
			CallHelper call2 = initializeCallHelper("callbackMsg", form,
					request, false);// updateMsgStatusByType
			call2.setParam("msgIds", _msgids);
			call2.execute();
		}

		return mapping.findForward("tooutbox");
	}

	/**
	 * 下载邮件
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MessageReceiveForm aform = (MessageReceiveForm) form;
		String[] msgids = aform.getMsgids();
		String msgidsStr = "";
		if (msgids != null && msgids.length > 0)
			msgidsStr = ToolKit.join(msgids);
		log.debug("msgids=" + msgidsStr);

		CallHelper caller = super.initializeCallHelper("getMessageAndFile",
				form, request, false);
		caller.setParam("msgIds", msgidsStr);
		caller.execute();
		if (caller.getSqlCode() != 0) {
			log.error("执行存储过程发现异常");
			return null;
		}

		List result1 = caller.getResult(0);
		List result2 = caller.getResult(1);
		String tempFileFolder = getFilePath() + "temp/";
		File tempFolder = new File(tempFileFolder);
		if (!tempFolder.exists())
			tempFolder.mkdir();
		// 创建存放文件的根目录
		String userPath = tempFileFolder + StringUtils.getRandomString(10)
				+ "/";
		File userFolder = new File(userPath);
		if (!userFolder.exists())
			userFolder.mkdir();

		String zipFileName = tempFileFolder + StringUtils.getRandomString(10)
				+ ".zip";// 邮件打包
		for (int i = 0, n = result1.size(); i < n; i++) {// 邮件循环

			BasicDynaBean bean = (BasicDynaBean) result1.get(i);
			Integer msgid = (Integer) bean.get("msgid");
			String title = (String) bean.get("title");
			String content = (String) bean.get("content");
			String senddatetime = ((String) bean.get("senddatetime"))
					.substring(0, 16);
			String personname = (String) bean.get("sendperson");

			String mailFolderPath = userPath + title + "/";
			File mailFolder = new File(mailFolderPath);
			if (!mailFolder.exists())
				mailFolder.mkdir();

			String txtName = mailFolderPath + title + ".txt";

			// 写文件
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(txtName));
			String temp = "标题：\t" + title;
			osw.write(temp, 0, temp.length());
			osw.flush();
			temp = "\n发件人：\t" + personname + "\t\t发送时间：\t" + senddatetime;
			osw.write(temp, 0, temp.length());
			osw.flush();
			temp = "\n内容：\n\t" + content;
			osw.write(temp, 0, temp.length());
			osw.flush();
			osw.close();
			String filePath = getFilePath();
			for (int j = 0, m = result2.size(); j < m; j++) {// 循环复制附件
				BasicDynaBean bean1 = (BasicDynaBean) result2.get(j);
				if (msgid.equals((Integer) bean1.get("msgid"))) {
					String fileUrl = filePath + bean1.get("filename");
					String fileCnName = mailFolderPath
							+ bean1.get("savefilename");
					File fj1 = new File(fileUrl);
					File fj2 = new File(fileCnName);
					FileUtils.copyFile(fj1, fj2);
				}
			}
		}
		ZipFileUtil.zip(zipFileName, userPath);// 压缩文件夹
		// 读文件到response中
		String fileName = java.net.URLEncoder.encode("邮件.zip", "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				zipFileName));
		byte[] input = new byte[1024];
		boolean eof = false;
		while (!eof) {
			int length = bis.read(input);
			if (length == -1) {
				eof = true;
			} else {
				bos.write(input, 0, length);
			}
		}
		bos.flush();
		bis.close();
		bos.close();
		// 删除临时文件
		FileUtils.deleteFile(new File(zipFileName));
		FileUtils.deleteFile(userFolder);
		return null;
	}

	public String getFilePath() {
		InputStream is = getClass().getResourceAsStream("/taglib.properties");
		Properties dbProps = new Properties();
		try {
			dbProps.load(is);
		} catch (Exception e) {
			System.err.println("不能读取属性文件. "
					+ "请确保taglib.properties在CLASSPATH指定的路径中");
			return "";
		}
		String uploadfilepath = dbProps.getProperty("uploadfilepath",
				"/mail/upload/");
		return uploadfilepath;
	}

}