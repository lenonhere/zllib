package com.web.action.infogather;

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

import com.web.CoreDispatchAction;
import com.web.pojos.InfoArticle;
import com.web.pojos.InfoChannel;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;

public class InfoRightPageAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(InfoRightPageAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper call = initializeCallHelper("getViewRootChannelList", form,
				request, false);
		call.setParam("userId", getPersonId(request));
		call.execute();
		String birthperson = (String) call.getOutput("message");
		List rootChannels = call.getResult("results");
		List ret = new ArrayList();
		if (rootChannels != null && rootChannels.size() > 0) {
			CallHelper call2 = initializeCallHelper(
					"getViewArticleByRootChannel", form, request, false);
			for (int i = 0; i < rootChannels.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) rootChannels.get(i);
				String channelName = (MethodFactory.getThisString(bean
						.get("channelname")));
				String channelId = (MethodFactory.getThisString(bean
						.get("channelid")));
				InfoChannel channel = new InfoChannel();
				channel.setChannelId(channelId);
				channel.setChannelName(channelName);
				call2.setParam("userId", getPersonId(request));
				call2.setParam("channelId", channelId);
				call2.execute();
				List articleList = call2.getResult("results");
				if (articleList != null && articleList.size() > 0) {
					List _articleList = new ArrayList();
					for (int j = 0; j < articleList.size(); j++) {
						BasicDynaBean bean2 = (BasicDynaBean) articleList
								.get(j);
						InfoArticle article = new InfoArticle();
						article.setArticleId(MethodFactory.getThisString(bean2
								.get("articleid")));
						// log.info("articleId:::"+article.getArticleId());
						article.setTitle(MethodFactory.getThisString(bean2
								.get("title")));
						String title = MethodFactory.getThisString(bean2
								.get("title"));
						/*
						 * if (title.length()>14){
						 * article.setShorttitle(MethodFactory
						 * .getThisString(bean2.get("title")).substring(0,14));
						 * }else{
						 * article.setShorttitle(MethodFactory.getThisString
						 * (bean2.get("title"))); }
						 */
						article.setShorttitle(MethodFactory.getThisString(bean2
								.get("title")));
						article.setBrief(MethodFactory.getThisString(bean2
								.get("brief")));
						article.setPublish_time(MethodFactory
								.getThisString(bean2.get("publish_time")));
						article.setViewcount(MethodFactory.getThisString(bean2
								.get("viewcount")));
						_articleList.add(article);
					}
					channel.setArticleList(_articleList);

				}
				ret.add(channel);
			}
			request.setAttribute("channelList", ret);
			request.getSession().setAttribute("channelList", ret);
			request.setAttribute("birthperson", birthperson);
		}
		return mapping.findForward("init");
	}

	public ActionForward showmore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CallHelper call = initializeCallHelper("getMoreArticles", form,
				request, false);
		call.setParam("userId", getPersonId(request));
		call.setParam("pagenum", request.getParameter("pagenum"));
		call.execute();

		List list = call.getResult("results");
		List articleList = new ArrayList();
		if (list != null && list.size() > 0)
			for (int j = 0; j < list.size(); j++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(j);
				InfoArticle article = new InfoArticle();
				article.setArticleId(MethodFactory.getThisString(bean
						.get("articleid")));
				article.setTitle(MethodFactory.getThisString(bean.get("title")));
				article.setPublish_time(MethodFactory.getThisString(bean
						.get("publish_time")));
				articleList.add(article);
			}
		request.setAttribute("articleList", articleList);

		request.setAttribute("channelId", request.getParameter("channelId"));
		request.setAttribute("channelName", request.getParameter("channelName"));

		String current = request.getParameter("pagenum");
		int pagenum = current == null ? 1 : Integer.parseInt(current);
		int totalnum = Integer.parseInt(call.getOutput("totalnum").toString());
		int pre = (pagenum - 1) > 0 ? pagenum - 1 : 1;
		int next = pagenum < totalnum ? pagenum + 1 : totalnum;

		request.setAttribute("prepage", pre + "");
		request.setAttribute("nextpage", next + "");
		request.setAttribute("totalnum", call.getOutput("totalnum"));
		return mapping.findForward("showmore");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("view");
	}

}
