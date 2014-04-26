package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.web.form.NewsForm;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class NewsAction extends CoreDispatchAction {
	private static Log log = LogFactory.getLog(NewsAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SqlReturn myReturn = TradeList.G_GetAllNews();
		List<BasicDynaBean> captionList = myReturn.getResultSet(0);
		List<BasicDynaBean> contentList = myReturn.getResultSet(1);
		request.setAttribute("news.caption", captionList);
		request.setAttribute("news.list", contentList);
		return mapping.findForward("init");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SqlReturn myReturn = TradeList.G_GetAllNews();
		List<BasicDynaBean> captionList = myReturn.getResultSet(0);
		List<BasicDynaBean> contentList = myReturn.getResultSet(1);
		request.setAttribute("news.caption", captionList);
		request.setAttribute("news.list", contentList);
		return mapping.findForward("query");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsForm aForm = (NewsForm) form;
		aForm.setEnddate(DateUtils.getDate(15));
		return mapping.findForward("add");
	}

	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsForm aForm = (NewsForm) form;
		aForm.setInputperson(getPersonName(request));
		SqlReturn myReturn = TradeList.G_NewsAdd(aForm);
		request.setAttribute("message.information", "保存成功!");
		return mapping.findForward("saveadd");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String formnewsid = request.getParameter("formnewsid");
		SqlReturn spReturn = TradeList.G_GetNewsInfo(formnewsid);
		List<BasicDynaBean> news = spReturn.getResultSet(0);

		int i = 0;
		if (news.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) news.get(i);
			String newsid = MethodFactory.getThisString(rowBean.get("newsid"));
			String title = MethodFactory.getThisString(rowBean.get("title"));
			String adddate = MethodFactory
					.getThisString(rowBean.get("adddate"));
			String content = MethodFactory
					.getThisString(rowBean.get("content"));
			content = content.replaceAll("\r\n", "<br>");
			String inputperson = MethodFactory.getThisString(rowBean
					.get("inputperson"));

			request.setAttribute("title", title);
			request.setAttribute("inputperson", inputperson);
			request.setAttribute("adddate", adddate);
			request.setAttribute("content", content);
		}

		return mapping.findForward("view");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String formnewsid = request.getParameter("formnewsid");
		SqlReturn spReturn = TradeList.G_GetNewsInfo(formnewsid);
		List<BasicDynaBean> news = spReturn.getResultSet(0);
		NewsForm newsForm = (NewsForm) form;
		int i = 0;
		if (news.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) news.get(i);
			newsForm.setNewsid(MethodFactory.getThisString(rowBean
					.get("newsid")));
			newsForm.setTitle(MethodFactory.getThisString(rowBean.get("title")));
			newsForm.setContent(MethodFactory.getThisString(rowBean
					.get("content")));
			newsForm.setEnddate(MethodFactory.getThisString(rowBean
					.get("enddate")));
		}

		return mapping.findForward("add");
	}

	public ActionForward savemodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NewsForm newsForm = (NewsForm) form;
		SqlReturn myReturn = TradeList.G_NewsModify(newsForm);
		request.setAttribute("message.information", "保存成功!");
		return mapping.findForward("savemodify");

	}

	public ActionForward delete(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String formnewsid = request.getParameter("formnewsid");

		SqlReturn myReturn = TradeList.G_NewsDelete(formnewsid);
		request.setAttribute("message.information", "删除成功!");

		return query(actionMapping, form, request, response);
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return actionMapping.findForward("save");
	}

}
