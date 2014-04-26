package com.web.action.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.common.tackle.UserView;
import com.web.action.CriterionAction;
import com.web.form.system.BBSForm;
import com.zl.base.core.file.directory;
import com.zl.common.Constants;
import com.zl.common.PFValue;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class BBSAction extends CriterionAction {

	private static Log log = LogFactory.getLog(BBSAction.class);

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
		try {
			request.setAttribute("list",
					TradeList.getAllArticle("1", 1000, request));
		} catch (Exception ex) {

		}
		return mapping.findForward("init");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String parentid = request.getParameter("parentid");
		if (parentid != null) {
			BBSForm aForm = (BBSForm) form;
			aForm.setParentid((new Integer(parentid)).intValue());
			request.setAttribute("type", "view");
		}
		return mapping.findForward("add");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			BBSForm aForm = (BBSForm) form;
			request.setAttribute("filename", aForm.getFilename());
			request.setAttribute("type", "modify");
			request.setAttribute("filename", aForm.getFilename());
			String articleid = request.getParameter("articleid");
			ArrayList<BasicDynaBean> list = TradeList.getArticle(articleid);
			if (list.size() > 0) {
				aForm.setArticleid(Integer.parseInt(String
						.valueOf(PropertyUtils.getProperty(list.get(0),
								"articleid"))));
				aForm.setTitle(String.valueOf(PropertyUtils.getProperty(
						list.get(0), "title")));
			}
		} catch (Exception ex) {
			log.error(ex);
		}

		return mapping.findForward("add");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forwardString = "";
		forwardString = String.valueOf(request.getParameter("fun"));
		try {
			// String
			// filepath=request.getSession().getServletContext().getRealPath("/content/article/");
			String filepath = SystemConfig.getFilesUploadPath();
			String filename = "";
			String filename_all = "";
			BBSForm aForm = (BBSForm) form;
			directory.createdir(filepath);
			UserView user = (UserView) request.getSession().getAttribute(
					Constants.SESSION_USER);
			if (aForm.getFilename() == null || aForm.getFilename().equals("")) {
				filename = PFValue.getPathname() + ".html";
			} else {
				filename_all = filepath + filename;
				File filetemp = new File(filename_all);
				if (!filetemp.exists()) {
					filename = PFValue.getPathname() + ".html";
				}
				filename = aForm.getFilename();
			}
			filename_all = filepath + filename;
			File file = new File(filename_all);
			OutputStream bos = null;
			bos = new FileOutputStream(file);
			String contentString = "<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">"
					+ aForm.getContentstring();
			String urL = request.getRequestURL().toString();
			// 改绝对地址为相对地址
			String aa[] = MethodFactory.split(urL, request.getRequestURI());
			contentString = MethodFactory.replace(contentString, aa[0], "");
			bos.write(contentString.getBytes("GBK")); // tomcat编码是UTF-8则contentString.getBytes("UTF-8")
			bos.close();
			if (aForm.getArticleid() == 0) {
				GregorianCalendar calendar = new GregorianCalendar();
				TradeList.AddArticle(aForm.getTitle(), filename, String
						.valueOf(user.getPersonId()), String
						.valueOf(new Timestamp(calendar.getTime().getTime())),
						String.valueOf(aForm.getParentid()));
			} else {
				GregorianCalendar calendar = new GregorianCalendar();
				TradeList.modifyArticle(aForm.getTitle(), String
						.valueOf(new Timestamp(calendar.getTime().getTime())),
						String.valueOf(aForm.getArticleid()));

			}
			if (aForm.getParentid() != 0) {
				request.setAttribute("type", "view");
			}
			request.setAttribute("info", "保存成功！");
			request.setAttribute("return", "Y");
		} catch (Exception ex) {
			log.error(ex);
		}
		return mapping.findForward(forwardString);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String articleid = request.getParameter("articleid");
			ArrayList<BasicDynaBean> list = TradeList.getArticle(articleid);
			if (list.size() > 0) {
				Object obj = list.get(0);
				BBSForm aForm = (BBSForm) form;
				int parentid = Integer.parseInt(String.valueOf(PropertyUtils
						.getProperty(obj, "parentid")));
				if (parentid == 0) {
					aForm.setParentid(Integer.parseInt(String
							.valueOf(PropertyUtils
									.getProperty(obj, "articleid"))));
				} else {
					aForm.setParentid(parentid);
				}
				aForm.setArticleid(Integer.parseInt(String
						.valueOf(PropertyUtils.getProperty(obj, "articleid"))));
				aForm.setFilename(String.valueOf(PropertyUtils.getProperty(obj,
						"filename")));
				aForm.setPersonid(Integer.parseInt(String.valueOf(PropertyUtils
						.getProperty(obj, "personid"))));
				request.setAttribute("filename", aForm.getFilename());
				request.setAttribute("personname", String.valueOf(PropertyUtils
						.getProperty(obj, "personname")));
				request.setAttribute("title",
						String.valueOf(PropertyUtils.getProperty(obj, "title")));
				request.setAttribute("operatdatetime", String
						.valueOf(PropertyUtils.getProperty(obj,
								"operatdatetime")));
				UserView user = (UserView) request.getSession().getAttribute(
						Constants.SESSION_USER);
				aForm.setArticleid(Integer.parseInt(articleid));
				if (user.getPersonId().equals(
						String.valueOf(aForm.getPersonid()))) {
					request.setAttribute("allowdelete", "true");
				}
			} else {
				request.setAttribute("filename", "");
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			BBSForm aForm = (BBSForm) form;
			if (aForm.getArticleid() > 0) {
				TradeList.DeleteArticle(String.valueOf(aForm.getArticleid()));
			}
			request.setAttribute("close", "true");
		} catch (Exception ex) {
			log.error(ex);
		}
		return mapping.findForward("view");
	}

	public ActionForward upload(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			BBSForm theForm = (BBSForm) form;
			FormFile file = theForm.getUploadFormFile();// 取得上传的文件
			// String
			// filepath=request.getSession().getServletContext().getRealPath("/content/images/");
			String filepath = SystemConfig.getFilesUploadPath();
			String filename = "";
			String filename_all = "";
			directory.createdir(filepath);
			int loc = file.getFileName().indexOf(".");
			String type = "";
			if (loc > 0) {
				type = file.getFileName().substring(loc,
						file.getFileName().length());
			}
			filename = PFValue.getPathname() + type;
			filename_all = filepath + filename;
			File filesave = new File(filename_all);
			OutputStream bos = null;
			bos = new FileOutputStream(filesave);
			bos.write(file.getFileData());
			bos.close();
			theForm.setFilename(filename);
		} catch (Exception ex) {
			request.setAttribute("err.info", "上传失败！");
		}
		return actionMapping.findForward("upload");
	}

	public ActionForward downfile(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String mimeType = request.getParameter("mimeType");
			String filename = request.getParameter("filename");
			String oldfilename = request.getParameter("saveasfilename");
			response.setContentType(mimeType);
			if (oldfilename == null) {
				if (mimeType.indexOf("application") >= 0 || mimeType == null) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + filename);
				}
			} else {
				String tempfilename = java.net.URLEncoder.encode(oldfilename,
						"UTF-8");
				if (tempfilename.length() > 150) {
					tempfilename = new String(oldfilename.getBytes("gb2312"),
							"ISO8859-1");
				}
				response.setHeader("Content-Disposition",
						"attachment; filename=" + tempfilename);
			}
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(SystemConfig.getFilesUploadPath()
							+ filename));
			// request.getSession().getServletContext().getRealPath("/content/images/")
			// + "/"+filename));modify 2007-12-28
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
		} catch (Exception ex) {

		}
		return null;
	}

	public ActionForward uploaddownloadfile(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BBSForm theForm = (BBSForm) form;
			FormFile file = theForm.getUploadFormFile();// 取得上传的文件
			// String
			// filepath=request.getSession().getServletContext().getRealPath("/content/images/");
			String filepath = SystemConfig.getFilesUploadPath();
			String filename = "";
			String filename_all = "";
			directory.createdir(filepath);
			int loc = file.getFileName().indexOf(".");
			String type = "";
			if (loc > 0) {
				type = file.getFileName().substring(loc,
						file.getFileName().length());
			}
			filename = PFValue.getPathname() + type;
			filename_all = filepath + filename;
			File filesave = new File(filename_all);
			OutputStream bos = null;
			bos = new FileOutputStream(filesave);
			bos.write(file.getFileData());
			bos.close();
			theForm.setFilename(filename);
			theForm.setMimeType(file.getContentType());
			// theForm.setOldfilename(new
			// String(file.getFileName().getBytes("GBK"),"UTF-8"));
			theForm.setOldfilename(file.getFileName());
			request.setAttribute("close", "true");
			request.setAttribute("filename", file.getFileName());
		} catch (Exception ex) {
			request.setAttribute("err.info", "上传失败！");
		}
		return actionMapping.findForward("uploaddownloadfile");
	}

	public ActionForward uploadinit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("uploadinit");
	}

	public static void downcontent(HttpServletRequest request, JspWriter out,
			String filename) throws IOException {
		BufferedInputStream bis = null;
		try {
			File file = new File(request.getSession().getServletContext()
					.getRealPath("/content/article/")
					+ "/" + filename);
			if (file.exists()) {
				bis = new BufferedInputStream(new FileInputStream(request
						.getSession().getServletContext()
						.getRealPath("/content/article/")
						+ "/" + filename));

				boolean eof = false;
				byte[] buff = new byte[2048];
				ByteBuffer bbf = ByteBuffer.allocate(Integer.parseInt(String
						.valueOf(file.length())));
				while (!eof) {
					int length = bis.read(buff);
					if (length == -1) {
						eof = true;
					} else {
						bbf.put(buff, 0, length);
					}
				}
				out.write(new String(bbf.array(), "GBK"));
			} else {
				out.write("文章已经不存在!");
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if (bis != null)
				bis.close();
		}
		return;
	}

}
