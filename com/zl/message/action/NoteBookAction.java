package com.zl.message.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.util.ToolKit;
import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.message.form.MessageReceiveForm;
import com.zl.message.pojos.MessageVO;
import com.zl.util.MethodFactory;

public class NoteBookAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(NoteBookAction.class);

	public ActionForward notebox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = mapping.findForward("notebox");
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
		aform.setPageNo(new Integer(pageNo));
		CallHelper call = initializeCallHelper("GetNoteList", aform, request,
				false);// GetReceiveTrashList GetNoteList
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
						.get("sendtime"));
				String senddate = MethodFactory.getThisString(bean
						.get("senddate"));
				String msgid = MethodFactory.getThisString(bean
						.get("messageid"));

				msg.setMessageId(msgid);
				msg.setSendTime(sendtime);
				msg.setSendDate(senddate);
				msg.setShorttitle(title != null && title.trim().length() > 30 ? title
						.substring(0, 30) : title);
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
		return forward;
	}

	public ActionForward addinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		MessageReceiveForm aForm = (MessageReceiveForm) form;
		aForm.setState(MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		return mapping.findForward("addinit");
	}

	public ActionForward addsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper call = initializeCallHelper("savenote", form, request, false);
		call.setParam("personId", getPersonId(request));
		call.execute();
		request.setAttribute("message.code", String.valueOf(call.getState()));
		request.setAttribute("message.information", call.getOutput(0));
		return mapping.findForward("addinit");
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageReceiveForm dynaForm = (MessageReceiveForm) form;

		CallHelper caller = initializeCallHelper("GetNoteDetail", form,
				request, false);
		caller.execute();

		ArrayList cmc = (ArrayList) caller.getResult(0);
		if (cmc.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) cmc.get(0);
			try {
				MethodFactory.copyProperties(dynaForm, rowBean);
			} catch (Exception e) {
				log.error(e);
			}
		}

		return mapping.findForward("addinit");
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
			CallHelper call2 = initializeCallHelper("DeletNote", form, request,
					false);
			call2.setParam("msgIds", _msgids);
			call2.execute();
		}

		return notebox(mapping, form, request, response);
	}

	public String save(List fileItems, HttpServletRequest request) {
		String title = "";
		String content = "";
		String senddatetime = "";
		String returnvalue = "";

		boolean blnsave = true;

		try {

			Iterator iterator = fileItems.iterator();
			while (iterator.hasNext()) {
				FileItem fileItem = (FileItem) iterator.next();
				// 文件域的表单信息
				String name = fileItem.getFieldName().toLowerCase();
				if (name.equals("title")) {
					title = fileItem.getString("GBK");
				} else if (name.equals("content")) {
					content = fileItem.getString("GBK");
				} else if (name.equals("senddatetime")) {
					senddatetime = fileItem.getString("GBK");
				}
			}
			blnsave = true;
		} catch (Exception ex) {
			returnvalue = "保存失败！";
			log.error(ex);
			blnsave = false;
		}
		if (blnsave == true) {
			CallHelper caller = new CallHelper("savenote");// sendMessage
			caller.setParam("personId", getPersonId(request));
			caller.setParam("title", title);
			caller.setParam("content", content);
			caller.setParam("senddatetime", senddatetime);

			caller.execute();
			returnvalue = (String) caller.getOutput(0);
		}
		return returnvalue;
	}

}
