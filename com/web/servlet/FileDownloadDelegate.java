package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileDownloadDelegate extends HttpServlet {

	private static final long serialVersionUID = -2738702402165242937L;
	private static final Log log = LogFactory
			.getLog(FileDownloadDelegate.class);

	public FileDownloadDelegate() {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		request.setCharacterEncoding("GBK");
		String queryString = null;
		queryString = new ParamAssemble(request).getQueryString();
		PrintWriter out = null;
		try {
			HttpSession session = request.getSession(false);
			int templateid = 0;
			response.setContentType("text/html;charset=gb2312");
			out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title> ");
			out.println("文件下载");
			out.println("</title>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
			out.println("<link rel=\"stylesheet\" href=\""
					+ request.getContextPath()
					+ "/css/common.css\" type=\"text/css\">");
			out.println("</head>");
			out.println("<body background=\""
					+ request.getContextPath()
					+ "/images/bgmain.gif\" text=\"#000000\" leftmargin=\"0\" topmargin=\"0\">");
			out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\">");
			out.println("  <tr> ");
			out.println("    <td> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"369\" align=\"center\">");
			out.println("        <!-- fwtable fwsrc=\"mes.png\" fwbase=\"mes.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"0\" -->");
			out.println("        <tr> ");
			out.println("          <td><img src=\""
					+ request.getContextPath()
					+ "/images/spacer.gif\" width=\"14\" height=\"1\" border=\"0\" alt=\"\"></td>");
			out.println("          <td><img src=\""
					+ request.getContextPath()
					+ "/images/spacer.gif\" width=\"367\" height=\"1\" border=\"0\" alt=\"\"></td>");
			out.println("          <td><img src=\""
					+ request.getContextPath()
					+ "/images/spacer.gif\" width=\"15\" height=\"1\" border=\"0\" alt=\"\"></td>");
			out.println("          <td><img src=\""
					+ request.getContextPath()
					+ "/images/spacer.gif\" width=\"1\" height=\"1\" border=\"0\" alt=\"\"></td>");
			out.println("        </tr>");
			out.println("        <tr> ");
			out.println("          <td><img name=\"mes_r1_c1\" src=\""
					+ request.getContextPath()
					+ "/images/mes_r1_c1.gif\" width=\"14\" height=\"31\" border=\"0\" alt=\"\"></td>");
			out.println("          <td background=\""
					+ request.getContextPath()
					+ "/images/mes_r1_c2.gif\" class=\"white\"><b>信息窗口</b></td>");
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
			out.println("                <td height=\"52\" align=\"center\">下载完成后请关闭页面</td>");
			out.println("              </tr>");
			out.println("              <tr> ");
			out.println("                <td height=\"45\" align=\"center\">&nbsp;<input type=\"button\" name=\"Button\" value=\"关闭\" class=\"bor1\" onClick=\"self.close();\"></td>");
			out.println("              </tr>");
			out.println("            </table></td>");
			out.println("          <td height=\"142\" background=\""
					+ request.getContextPath() + "/images/mes_r2_c3.gif\">");
			out.println("                 <img name=\"mes_r2_c3\" src=\""
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
			out.println("<script language=\"javascript\">");
			out.println("   window.location=\"" + request.getContextPath()
					+ "/im/filedownload?" + queryString + "\";");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");
			response.flushBuffer();
			log.info("queryString:::" + queryString);
		} catch (IOException ex) {
		} finally {

			if (out != null) {
				out.close(); // Close the ServletOutputStream
			}
		}

		return;

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		doPost(request, response);
	}

	/**
	 * 将HttpServletRequest 中的参数取出，组合成相当于 HttpServletRequest.getQueryString()
	 * 的形式，因为直接调用HttpServletRequest.getQueryString()返回乱码
	 *
	 * @version 1.0
	 */
	class ParamAssemble {
		HttpServletRequest req = null;

		ParamAssemble(HttpServletRequest request) {
			this.req = request;
		}

		/**
		 * HttpServletRequest中所有参数组合成param1=value1&param1=value2&param2=value
		 * 的形式
		 *
		 * @return
		 */
		String getQueryString() {
			StringBuffer buffer = new StringBuffer();
			Map parameterMap = req.getParameterMap();

			boolean needSeparator = false;
			for (Iterator iterator = parameterMap.entrySet().iterator(); iterator
					.hasNext();) {
				if (needSeparator) {
					buffer.append('&');
				}

				buffer.append(makeString((Map.Entry) iterator.next()));
				needSeparator = true;
			}

			return buffer.toString();
		}

		/**
		 * 将参数 名－值对 组合成 param=value1&param=value2的形式
		 *
		 * @param entry
		 * @return
		 */
		private String makeString(Map.Entry entry) {
			StringBuffer buffer = new StringBuffer();
			String name = (String) entry.getKey();

			String[] values = (String[]) entry.getValue();

			boolean needSeparator = false;
			for (int i = 0; i < values.length; i++) {
				if (needSeparator) {
					buffer.append('&');
				}

				buffer.append(name + "=" + values[i]);
				needSeparator = true;
			}

			return buffer.toString();
		}

	}
}
