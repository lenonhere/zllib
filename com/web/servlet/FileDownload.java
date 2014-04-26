package com.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.util.ToolKit;
import com.zl.util.TradeList;

public class FileDownload extends HttpServlet {

	private static final long serialVersionUID = 7267485403934253456L;

	private static final Log log = LogFactory.getLog(FileDownload.class);

	public static final String PARAM_DOMESTICFILE = "domesticfile";

	public static final String PARAM_FILEDEMANDED = "filedemanded";

	public static final String PARAM_WILDFILE = "wildfile";

	private ServletConfig config;

	/**
	 * Init the servlet
	 */
	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	final public ServletConfig getServletConfig() {

		return config;
	}

	/**
	 * Handles GET requests
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * Handles POST requests
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		boolean isDomestic = false;

		String domesticFile = request.getParameter("domesticfile");
		String wildFile = request.getParameter("wildfile");
		String msgId = request.getParameter("msgId");
		String fileDemanded = null;

		if (msgId != null && msgId.trim().length() > 0) {
			try {
				String msgContent = TradeList.getMsgContentById(msgId);
				fileDemanded = "/im/files/" + msgContent;
			} catch (Exception e) {
				log.error("取得消息内容出错:::" + e.toString());
			}

		} else {
			fileDemanded = wildFile;
		}

		// 取得文件路径
		File file = null;
		File base = new java.io.File(getServletContext().getRealPath("/"));

		file = new File(base, fileDemanded);

		log.info("fileName:" + file.getName() + "filePath:" + file.getPath());
		if (!file.isFile()) {
			PrintWriter out = null;
			try {

				HttpSession session = request.getSession(false);
				int templateid = 0;
				// Sets the content type of the response
				response.setContentType("text/html;charset=gbk");
				// Create a ServletOutputStream to write the output
				out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title> ");
				out.println("文件不存在");
				out.println("</title>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
				out.println("<link rel=\"stylesheet\" href=\""
						+ request.getContextPath()
						+ "/css/main.css\" type=\"text/css\">");
				out.println("<link rel=\"stylesheet\" href=\""
						+ request.getContextPath()
						+ "/css/common.css\" type=\"text/css\">");
				out.println("</head>");
				out.println("<body background=\""
						+ request.getContextPath()
						+ "/images/bgmain.gif\" text=\"#000000\" leftmargin=\"0\" topmargin=\"0\">");
				out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\">");
				out.println("  <tr> ");
				out.println("    <td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"369\" align=\"center\">");
				out.println("        <!-- fwtable fwsrc=\"mes.png\" fwbase=\"mes.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"0\" -->");
				out.println("        <tr> ");
				out.println("           <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"14\" height=\"1\" border=\"0\" alt=\"\"></td>");
				out.println("           <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"367\" height=\"1\" border=\"0\" alt=\"\"></td>");
				out.println("           <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"15\" height=\"1\" border=\"0\" alt=\"\"></td>");
				out.println("           <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"1\" height=\"1\" border=\"0\" alt=\"\"></td>");
				out.println("        </tr>");
				out.println("        <tr> ");
				out.println("          <td><img name=\"mes_r1_c1\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r1_c1.gif\" width=\"14\" height=\"31\" border=\"0\" alt=\"\"></td>");
				out.println("          <td background=\""
						+ request.getContextPath()
						+ "/images/mes_r1_c2.gif\" class=\"white\"><b>信息提示窗口</b></td>");
				out.println("          <td><img name=\"mes_r1_c3\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r1_c3.gif\" width=\"15\" height=\"31\" border=\"0\" alt=\"\"></td>");
				out.println("          <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"1\" height=\"31\" border=\"0\" alt=\"\"></td>");
				out.println("        </tr>");
				out.println("        <tr> ");
				out.println("          <td height=\"142\" background=\""
						+ request.getContextPath() + "/images/mes_r2_c1.gif\">");
				out.println("               <img name=\"mes_r2_c1\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r2_c1.gif\" width=\"14\" height=\"132\" border=\"0\" alt=\"\"></td>");
				out.println("          <td background=\""
						+ request.getContextPath()
						+ "/images/mes_r2_c2.gif\" height=\"142\"> ");
				out.println("            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				out.println("              <tr> ");
				out.println("                <td height=\"52\" align=\"center\">文件不存在</td>");
				out.println("              </tr>");
				out.println("              <tr> ");
				out.println("                <td height=\"45\" align=\"center\">&nbsp;<input type=\"button\" name=\"Button\" value=\"返回\" class=\"btn\" onClick=\"self.location.href='"
						+ request.getContextPath()
						+ "/im/msgManage.do?method=inbox';\"></td>");
				out.println("              </tr>");
				out.println("            </table></td>");
				out.println("          <td height=\"142\" background=\""
						+ request.getContextPath() + "/images/mes_r2_c3.gif\">");
				out.println("              <img name=\"mes_r2_c3\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r2_c3.gif\" width=\"15\" height=\"132\" border=\"0\" alt=\"\"></td>");
				out.println("          <td height=\"142\"><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"1\" height=\"132\" border=\"0\" alt=\"\"></td>");
				out.println("        </tr>");
				out.println("        <tr> ");
				out.println("          <td><img name=\"mes_r3_c1\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r3_c1.gif\" width=\"14\" height=\"17\" border=\"0\" alt=\"\"></td>");
				out.println("          <td><img name=\"mes_r3_c2\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r3_c2.gif\" width=\"367\" height=\"17\" border=\"0\" alt=\"\"></td>");
				out.println("          <td><img name=\"mes_r3_c3\" src=\""
						+ request.getContextPath()
						+ "/images/mes_r3_c3.gif\" width=\"15\" height=\"17\" border=\"0\" alt=\"\"></td>");
				out.println("          <td><img src=\""
						+ request.getContextPath()
						+ "/images/spacer.gif\" width=\"1\" height=\"17\" border=\"0\" alt=\"\"></td>");
				out.println("        </tr>");
				out.println("      </table></td>");
				out.println("  </tr>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
				response.flushBuffer();
			} catch (IOException ex) {
				log.error("filedownload error:::" + ex.toString());
			} finally {
				if (out != null) {
					out.close(); // Close the ServletOutputStream
				}
			}
			return;
		}

		String promptedName = "";
		if (ToolKit.isASCII(file.getName())) {
			promptedName = file.getName();
		} else {
			promptedName = java.net.URLEncoder.encode(file.getName(), "UTF-8");
		}

		long fileLen = file.length();
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) fileLen);
		response.setHeader("Content-Disposition", "attachment;" + " filename="
				+ promptedName);
		InputStream input = null;
		try {
			int readBytes = 0;
			int blockSize = 1024;
			byte[] b = new byte[blockSize];
			input = new FileInputStream(file);
			OutputStream output = response.getOutputStream();
			int remain = 0;
			while ((remain = input.available()) > 0) {
				readBytes = input.read(b, 0, Math.min(remain, blockSize));
				output.write(b, 0, readBytes);
			}
		} catch (IOException ex) {
			log.error(ex);
		} finally {

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
				}
			}

			response.flushBuffer();
		}

	}

	/**
	 * Destroy the servlet
	 */
	public void destroy() {
	}

	public static String getParamName(int baseid) {
		switch (baseid) {
		case 1:
			return PARAM_DOMESTICFILE;

		case 2:
			return PARAM_WILDFILE;

		default:
			return PARAM_FILEDEMANDED;

		}

	}

	static class Codec {
		String encode(String rawStr) {
			if (rawStr == null) {
				return null;
			}
			try {
				return new String(rawStr.getBytes("8859_1"), "GBK");
			} catch (Exception ex) {
				return null;
			}
		}

		String decode(String rawStr) {
			return "";
		}
	}

}
