package com.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.qmx.dateutils.DateUtils;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.file.directory;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;

public class TransportDeal extends CriterionAction {

	private static final Log log = LogFactory.getLog(TransportDeal.class);

	public class fileinfo {
		public String filename;
		public String savefilename;
		public String filetype;
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
		DynaActionForm dynaForm = (DynaActionForm) form;
		// ArrayList List1 = OptionUtil.getWhouseidList();
		ArrayList List2 = OptionUtils.gettranstype();
		ArrayList list = OptionUtils.getExestates();
		request.setAttribute("exestates.list", list);
		// request.setAttribute("whouseid.list", List1);
		request.setAttribute("destid.list", List2);
		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("gettransproblemquery", form,
				request, false);
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show");
	}

	public ActionForward addnew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("new");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList List1 = OptionUtils.gettranstype2();
		ArrayList list = new ArrayList();
		request.setAttribute("destid.list", List1);
		request.setAttribute("files.list", list);
		// request.setAttribute("whouseid.list", List2);
		request.setAttribute("date",
				MethodFactory.replace(DateUtils.getDate(), "/", "-"));
		// dynaForm.set("date", MethodFactory.getDate());
		// request.setAttribute("files.list", List1);
		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("whouseid", "9");//
		CallHelper helper = initializeCallHelper("GetTransProblemAdd", form,
				request, false);
		helper.execute();
		dynaForm.set("transid", helper.getOutput(1));
		dynaForm.set("flags", "0");
		// dynaForm.set("personname", helper.getOutput(0));

		return mapping.findForward("add");
	}

	public ActionForward addsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("SaveTransProblemAdd", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", (String) helper.getOutput(0));

		return add(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("DelTransProblem", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return add(mapping, form, request, response);
	}

	public ActionForward delrow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("DelTransProblem", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return show(mapping, form, request, response);
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList List1 = OptionUtils.gettranstype2();
		// ArrayList List2= OptionUtil.getWhouseidList();//getCKList2();
		request.setAttribute("destid.list", List1);
		// request.setAttribute("whouseid.list", List2);
		CallHelper helper = initializeCallHelper("ModifyTransProblem", form,
				request, false);
		helper.execute();

		dynaForm.set("whousename", helper.getOutput(0));
		dynaForm.set("destid", helper.getOutput(1));
		// dynaForm.set("date", helper.getOutput(2));
		// request.setAttribute("date",
		// MethodFactory.replace((String)helper.getOutput(2),"/","-"));
		request.setAttribute("date", helper.getOutput(2));
		dynaForm.set("destname", helper.getOutput(3));
		dynaForm.set("companyName", helper.getOutput(4));
		dynaForm.set("infomation", helper.getOutput(5));
		dynaForm.set("memo", helper.getOutput(6));
		dynaForm.set("commet2", helper.getOutput(8));
		dynaForm.set("flags", helper.getOutput(9));// 是否处理完毕
		List list = helper.getResult(0);
		request.setAttribute("files.list", list);
		return mapping.findForward("add");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList List1 = OptionUtils.gettranstype2();
		// ArrayList List2= OptionUtil.getWhouseidList();//getCKList2();
		request.setAttribute("destid.list", List1);

		// request.setAttribute("whouseid.list", List2);
		CallHelper helper = initializeCallHelper("ModifyTransProblem", form,
				request, false);
		helper.execute();

		dynaForm.set("whousename", helper.getOutput(0));
		dynaForm.set("destid", helper.getOutput(1));
		// dynaForm.set("date", helper.getOutput(2));
		// request.setAttribute("date",
		// MethodFactory.replace((String)helper.getOutput(2),"/","-"));
		request.setAttribute("date", helper.getOutput(2));
		dynaForm.set("destname", helper.getOutput(3));
		dynaForm.set("companyName", helper.getOutput(4));
		dynaForm.set("infomation", helper.getOutput(5));
		dynaForm.set("memo", helper.getOutput(6));
		dynaForm.set("fieldIdSet", helper.getOutput(7));
		dynaForm.set("commet2", helper.getOutput(8));
		dynaForm.set("flags", helper.getOutput(9));// 是否处理完毕

		List list = helper.getResult(0);
		request.setAttribute("files.list", list);
		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm theForm = (DynaActionForm) form;

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

					CallHelper helper = initializeCallHelper(
							"SaveTransProblemAdd", form, request, false);
					helper.setParam("fieldIdSet", strSaveFileName);
					helper.setParam("userId", getPersonId(request));
					helper.execute();

				} catch (Exception ex) {
					log.error(ex);
					System.out.println("发运问题记录附件上传失败！");
				}
			}
		}
		CallHelper helper = initializeCallHelper("SaveTransProblemAdd", form,
				request, false);

		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));
		return add(mapping, form, request, response);
	}

	public ActionForward viewAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper("getAnnexAliasInServer",
					form, request, false);
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
				strFullPath = uploadPath + strAnnexAliasInServer;

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
			log.error(e);
			return mapping.findForward("error");
		}

		return null;
	}

	public ActionForward delAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("DelAnnex", form, request,
				false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return add(mapping, form, request, response);
	}
}
