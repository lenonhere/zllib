package com.web.action.business.daily;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.web.form.ComplaintsForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.file.directory;
import com.zl.util.MethodFactory;

public class ComplaintsAction extends CriterionAction {

	private GeneralCacheAdministrator cacheAdmin = new GeneralCacheAdministrator();
	private static String cacheKey = "msgpersontree";
	private static int cachePeriod = 360000;

	// 投诉举报初始化页面
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("files.list", new ArrayList());// 电子附件

		return mapping.findForward("init");
	}

	public ActionForward addsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm theForm = (DynaActionForm) form;
		theForm.set("userId", getPersonId(request));
		// System.out.println("哟嚯嚯...............="+getPersonId(request));

		// 上报时间
		Date date = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		String times = from.format(date);

		request.setAttribute("files.list", new ArrayList());
		// 投诉举报 主体
		CallHelper helper = initializeCallHelper("complaintswhistleaddsave",
				form, request, false);
		helper.setParam("date", times);
		helper.execute();
		String serialid = (String) helper.getOutput(0);
		request.setAttribute("message.info", helper.getOutput(0));
		request.setAttribute("message.code", helper.getOutput(1));

		// 投诉举报 抄送领导
		// System.out.println("yohoho------------------"+theForm.get("personid"));
		helper = initializeCallHelper("complaintswhistleleader", form, request,
				false);
		helper.setParam("serialid", serialid);
		helper.execute();

		// 投诉举报 附件
		String uploadPath = SystemConfig.getFilesUploadPath();
		uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
		uploadPath = MethodFactory.replace(uploadPath, "//", "/");

		String filename = "";
		String allfilename = "";
		String strSaveFileName = "";
		Hashtable fileh = theForm.getMultipartRequestHandler()
				.getFileElements();
		for (Enumeration e = fileh.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			FormFile file = (FormFile) fileh.get(key);
			if (file.getFileName().trim().length() > 0) {
				try {
					int loc = file.getFileName().lastIndexOf(".");
					String type = "";
					if (loc > 0) {
						type = file.getFileName().substring(loc,
								file.getFileName().trim().length());
					}
					filename = "file" + getPersonId(request) + "/"
							+ getfilename() + type;
					allfilename = uploadPath + filename;
					strSaveFileName = filename + "|"
							+ file.getFileName().trim() + "|"
							+ file.getContentType();
					directory.createdir(uploadPath + "file"
							+ getPersonId(request) + "/");
					File savedFile = new File(allfilename);
					OutputStream bos = null;
					bos = new FileOutputStream(savedFile);
					bos.write(file.getFileData());
					bos.close();
					// 保存电子附件
					helper = initializeCallHelper("complaintswhistlesaveFile",
							form, request, false);
					helper.setParam("serialid", serialid);
					helper.setParam("filename", filename);
					helper.setParam("savefilename", file.getFileName().trim());
					helper.setParam("filetype", file.getContentType());
					helper.execute();
				} catch (Exception ex) {
					// log.error(ex);
					System.out.println("新增附件上传失败！");
				}
			}
		}

		return init(mapping, form, request, response);
	}

	public String getfilename() {
		String returnvalue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		// 年
		String stryear = String.valueOf(calendar.get(Calendar.YEAR));
		// 月
		String strmonth = "00"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
		strmonth = strmonth.substring(strmonth.length() - 2, strmonth.length());
		// 日
		String strday = "00"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strday = strday.substring(strday.length() - 2, strday.length());
		// 时
		String strhour = "00"
				+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		strhour = strhour.substring(strhour.length() - 2, strhour.length());
		// 分
		String strminute = "00" + String.valueOf(calendar.get(Calendar.MINUTE));
		strminute = strminute.substring(strminute.length() - 2,
				strminute.length());
		// 秒
		String strsecond = "00" + String.valueOf(calendar.get(Calendar.SECOND));
		strsecond = strsecond.substring(strsecond.length() - 2,
				strsecond.length());
		// 毫秒
		String strmsecond = "000000"
				+ String.valueOf(calendar.get(Calendar.MILLISECOND));
		strmsecond = strmsecond.substring(strmsecond.length() - 6,
				strmsecond.length());
		String[] ychar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
				"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = ychar[random.nextInt(36)];
			sRand += rand;
		}
		returnvalue = stryear + strmonth + strday + strhour + strminute
				+ strsecond + strmsecond + sRand;
		return returnvalue;
	}

	// 人员树
	public ActionForward persontree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List ret = new ArrayList();
		try {
			cacheAdmin.flushAll();
			ret = (List) cacheAdmin.getFromCache(cacheKey, cachePeriod);
		} catch (NeedsRefreshException nre) {
			try {
				CallHelper helper = initializeCallHelper(
						"complaintswhistlePersonTree", form, request, false);
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

	// 投诉信件初始化页面
	public ActionForward initview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());

		return mapping.findForward("initview");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("userId", getPersonId(request));

		String userId = getPersonId(request);
		String beginDate = (String) dynaForm.get("beginDate");
		String endDate = (String) dynaForm.get("endDate");

		CallHelper helper = initializeCallHelper("querycomplaintswhistle",
				form, request, false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult("captions"));
		request.setAttribute("results.list", helper.getResult("results"));

		return mapping.findForward("show");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("queryLogcomplaintswhistle",
				form, request, false);
		helper.setParam("serialid", dynaForm.get("code"));
		helper.execute();

		List tasks = null;
		List logtask = helper.getResult("logtask");
		if (logtask.size() > 0) {
			tasks = new ArrayList();
			for (int i = 0; i < logtask.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) logtask.get(i);
				ComplaintsForm taskvo = new ComplaintsForm();
				MethodFactory.copyProperties(taskvo, rowBean);
				tasks.add(taskvo);
			}
		}
		List logfile = helper.getResult("logfile");
		request.setAttribute("tasks", tasks);
		request.setAttribute("files.list", logfile);

		return mapping.findForward("view");
	}

	// 查看电子附件
	public ActionForward viewAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper(
					"getLogAnnexcomplaintswhistle", form, request, false);
			helper.execute();
			String strAnnexAliasInServer = (String) helper.getOutput(0);

			String strFullPath = "";
			String strFileName = "";
			strFileName = (String) helper.getOutput(1);

			String uploadPath = SystemConfig.getFilesUploadPath();
			uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
			uploadPath = MethodFactory.replace(uploadPath, "//", "/");
			if (uploadPath.endsWith("/"))
				strFullPath = uploadPath + strAnnexAliasInServer;
			else
				strFullPath = uploadPath + "/" + strAnnexAliasInServer;

			response.setHeader("Cache-Control", "max-age=3153600");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(strFileName.getBytes("GBK"), "ISO8859_1"));

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(strFullPath));
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());

			byte[] buff = new byte[4096];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			bis.close();
			bos.close();
			bos = null;
		} catch (Exception e) {
			return mapping.findForward("error");
		}

		return null;
	}

	// 删除投诉信件 by serialid
	public ActionForward delrow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("delLogcomplaintswhistle",
				form, request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));

		return show(mapping, form, request, response);
	}

}
