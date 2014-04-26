package com.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReceiveDataServlet
 */
public class ReceiveDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiveDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static String getXml() {
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<topPages>");
		xml.append("<sttCd>00</sttCd>");
		xml.append("<toppage>");
		xml.append("<new>from=20121120 to=20121127</new>");
		xml.append("<cooperation>AAA</cooperation>");
		xml.append("<registration>BBB</registration>");
		xml.append("</toppage>");
		xml.append("<member>");
		xml.append("<challetomo>yes</challetomo>");
		xml.append("</member>");
		xml.append("</topPages>");

		return xml.toString();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------get--------------");
		String username = new String(request.getParameter("username").getBytes(
				"iso-8859-1"), "utf-8");
		String password = new String(request.getParameter("password").getBytes(
				"iso-8859-1"), "utf-8");
		System.out.println(username);
		System.out.println(password);
		OutputStream os = response.getOutputStream();
		// os.write("get".getBytes("utf-8"));
		os.write(getXml().getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------post--------------");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		System.out.println(sb.toString());
		br.close();
		/*
		 * String username = new
		 * String(request.getParameter("username").getBytes
		 * ("iso-8859-1"),"utf-8"); String password = new
		 * String(request.getParameter
		 * ("password").getBytes("iso-8859-1"),"utf-8");
		 * System.out.println(username); System.out.println(password);
		 */
		OutputStream os = response.getOutputStream();
		// os.write("post".getBytes("utf-8"));
		os.write(getXml().getBytes());
	}

	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------delete--------------");
		String username = new String(request.getParameter("username").getBytes(
				"iso-8859-1"), "utf-8");
		String password = new String(request.getParameter("password").getBytes(
				"iso-8859-1"), "utf-8");
		System.out.println(username);
		System.out.println(password);
		OutputStream os = response.getOutputStream();
		// os.write("delete".getBytes("utf-8"));
		os.write(getXml().getBytes());
	}

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------put--------------");

		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader((ServletInputStream)request.getInputStream()));
		 * String line = null; StringBuilder sb = new StringBuilder();
		 * while((line = br.readLine())!=null){ sb.append(line); }
		 * System.out.println(sb.toString()); br.close();
		 */

		String username = new String(request.getParameter("username").getBytes(
				"iso-8859-1"), "utf-8");
		String password = new String(request.getParameter("password").getBytes(
				"iso-8859-1"), "utf-8");
		System.out.println(username);
		System.out.println(password);
		OutputStream os = response.getOutputStream();
		// os.write("put".getBytes("utf-8"));
		os.write(getXml().getBytes());
	}

}
