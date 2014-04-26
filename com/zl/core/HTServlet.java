package com.zl.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;

/**
 * Severlet
 *
 * @author
 * @version 1.0
 */
public class HTServlet extends ActionServlet {
	public HTServlet() {
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		super.init();
		ServletContext sct = getServletContext();
		// org.apache.log4j.PropertyConfigurator.configure(sct.getRealPath("/")
		// + getServletConfig().getInitParameter("log4j"));
	}

	/**
	 * Process an HTTP "GET" request.
	 *
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 *
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet exception occurs
	 */
	private void dealsession(HttpServletRequest request) {
		try {
			// Logger.error("ffffffffffffff"+request.getSession().getAttribute(Globals.J2EE_USER_NAME));
			/*
			 * if(request.getSession().getAttribute(Globals.J2EE_USER_NAME)!=null
			 * ){ //Globals.
			 * request.getSession().setAttribute(Globals.J2EE_USER_NAME
			 * ,request.getSession().getAttribute(Globals.J2EE_USER_NAME));
			 * request
			 * .getSession().setAttribute("dbmarket.person",request.getSession
			 * ().getAttribute(Globals.J2EE_USER_NAME)); }else{
			 * request.getSession().removeAttribute("dbmarket.person");
			 * Logger.error("û�еõ���¼�û���"); }
			 */
			/*
			 * UserInfo UserInfoTemp = new UserInfo();
			 * UserInfoTemp.setCnName("ϵͳ����Ա");
			 * UserInfoTemp.setEnName("admin"); UserInfoTemp.setPersonUuid(
			 * "1d313a71-106587ae3bf-72da7b205ea572ad2c70f691483a8704"); if
			 * (request.getSession().getAttribute("dbmarket.person")==null){
			 * request
			 * .getSession().setAttribute("dbmarket.person",UserInfoTemp); }
			 *
			 * if(request.getSession().getAttribute("dbmarket.person")==null){
			 * Context ctx = Context.getInstance(); UserInfo user =
			 * ctx.getCurrentLoginInfo(); if( user != null){
			 * request.getSession().setAttribute("dbmarket.person",user); }else{
			 * Logger.error("û�еõ���¼�û���"); } }
			 */
		} catch (Exception ex) {
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 去缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		dealsession(request);
		process(request, response);
	}

	/**
	 * Process an HTTP "POST" request.
	 *
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 *
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet exception occurs
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 去缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		dealsession(request);
		process(request, response);

	}

}
