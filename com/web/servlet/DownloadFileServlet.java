package com.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.SystemConfig;
import com.common.tackle.UserView;
import com.zl.common.Constants;

public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = -4936664386409881300L;
	private static final Log log = LogFactory.getLog(DownloadFileServlet.class);

	/**
	 * Default constructor.
	 */
	public DownloadFileServlet() {
		super();
	}

	/**
	 * Init method.
	 *
	 * @throws ServletException
	 *             never.
	 */
	public void init() throws ServletException {
		return;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		service(request, response, true);
	}

	public void service(HttpServletRequest request,
			HttpServletResponse response, boolean blnsyn)
			throws ServletException, IOException {
		try {
			UserView user = (UserView) request.getSession().getAttribute(
					Constants.SESSION_USER);
			if (user == null) {
				response.sendRedirect("index.jsp");
				return;
			}
			String mimeType = request.getParameter("mimeType");
			String filename = request.getParameter("filename");
			String oldfilename = request.getParameter("saveasfilename");
			response.setContentType(mimeType);
			if (oldfilename == null) {
				if (mimeType == null || mimeType.indexOf("application") >= 0) {
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
			// + "/"+filename));modify 2007-12-26
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
			log.error("filedownload error:::" + ex.toString());
		}
		return;
	}

}