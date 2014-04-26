package com.web.servlet;

import static com.zl.util.MethodFactory.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class AjaxGetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String IF005 = "E:/logs/IF005.xml";

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 返回的数据需要拼装成xml格式
		String xml = getXml();
		// xml =
		// "<?xml version="1.0" encoding="UTF-8"?><topPages><sttCd>00</sttCd><toppage><new>from=20121120 to=20121230</new><cooperation>★ダミー_チャレ友協力ページ用の説明文言が入ります</cooperation><registration>★ダミー_チャレ友登録ページの説明文言が入ります</registration></toppage><member><challetomo>yes</challetomo></member></topPages>";

		print(xml);
		String jsoncallback = request.getParameter("jsoncallback");
		String result = jsoncallback + "({msg:'" + xml + "'})";

		response.setCharacterEncoding("UTF-8");
		// 响应的Content-Type必须是text/xml , application/json
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

//		try {
//			int i = 1/0;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	// @Override
	// protected void service(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// response.setCharacterEncoding("UTF-8");
	// // 响应的Content-Type必须是text/xml
	// response.setContentType("text/xml;charset=UTF-8");
	// response.setHeader("Cache-Control", "no-cache");
	// PrintWriter out = response.getWriter();
	//
	// // String username = request.getParameter("username");
	// // String password = request.getParameter("password");
	// // if ("zhangsan".equals(username)) {
	// // // 此处是out.print不是out.println
	// // out.print("不可用");// out.print()里的内容将会输出到jsp页面中callback函数里
	// // } else {
	// // out.print("可以使用");
	// // }
	// // 返回的数据需要拼装成xml格式
	// String xml = getXml();
	// print(xml);
	// out.write(xml);
	// out.flush();
	// out.close();
	// }

	private static String getXml() {
		String xml = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(IF005));

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.VERSION, "1.0");
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//			tf.setOutputProperty(OutputKeys.INDENT, "no");
			tf.transform(new DOMSource(doc), new StreamResult(bos));

			xml = bos.toString("UTF-8");
			xml = xml.replaceAll("\r\n","");
			xml = xml.replaceAll("\t","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public static void main(String[] args) {
		String xml = getXml();
		xml = xml.replaceAll("\r\n","");
		xml = xml.replaceAll("\t","");
		print(xml);
	}
}