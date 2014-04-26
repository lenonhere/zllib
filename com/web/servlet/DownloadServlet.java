package com.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1861461264771031981L;
	private static final String CONTENT_TYPE = "text/html; charset=GB2312";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String fileName = request.getParameter("fileName");
		String newFileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		fileName = new String(fileName.getBytes("iso-8859-1"), "gb2312");
		String filePath = request.getSession().getServletContext()
				.getRealPath("/price");
		// 构建被下载的文件
		File file = new File(filePath + fileName);
		FileInputStream fs = new FileInputStream(file);
		OutputStream bos = response.getOutputStream();
		if (newFileName != null && newFileName.length() != 0) {
			if (newFileName.endsWith(".jpg") || (newFileName.endsWith(".gif"))) {
				// no need setting content type and header
			} else if (newFileName.endsWith(".txt")) {
				response.setContentType("text;charset=GB2312");
				response.setHeader("Content-disposition",
						"attachment; filename=" + newFileName);
			} else {
				response.setContentType("application/doc;charset=GB2312");
				response.setHeader("Content-disposition",
						"attachment; filename=" + newFileName);// 这个名字save as
																// 的默认名
			}

			response.setContentLength(fs.available());
		}
		byte[] buff = new byte[1024];
		int readCount = 0;
		while ((readCount = fs.read(buff)) != -1) {
			bos.write(buff, 0, readCount);// 输出到客户端
		}
		if (fs != null)
			fs.close();
		if (bos != null)
			bos.close();
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Clean up resources
	public void destroy() {
	}
}
