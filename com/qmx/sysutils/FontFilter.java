package com.qmx.sysutils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FontFilter implements Filter {
	String encode = "UTF-8";

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		request.setCharacterEncoding(encode);
		response.setCharacterEncoding(encode);
		response.setContentType("text/http;charset=" + encode);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		arg2.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		encode = config.getInitParameter("code");
		if ("".equals(encode)) {
			encode = "UTF-8";
		}
		// System.out.println("EncodeFilter.enCode:>>>" + encode + "<<<");
		// log.info("EncodeFilter.enCode:>>>" + encode + "<<<");
	}

}
