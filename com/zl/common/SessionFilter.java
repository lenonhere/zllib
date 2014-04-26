/**
 * @author: 朱忠南
 * @date: Sep 1, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * 		本来最好是把登录页面用到的文件都放到一个固定的文件夹里面，然后在filter中过滤掉这个文件夹的限制
 *   	但是因为这个是后来加的，所以在这个里面过滤了登陆页用到的一些图片和jsp
 *   	如果修改了登录界面，需要修改此类，修改登录页面的时候最好将登陆页用到的文件放到一个固定文件夹下
 * @modify_author:
 * @modify_time:
 */
package com.zl.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * @author jokin
 * @date Sep 1, 2008
 */
public class SessionFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SessionFilter.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Object user = request.getSession().getAttribute(Constants.SESSION_USER);
		if (user != null) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		String path = request.getRequestURI();
		if (path.indexOf("/login.do") != -1
				|| path.equalsIgnoreCase("/index.jsp")
				|| path.equals("/index-relogin.jsp")
				|| path.indexOf("/system/checkImage.jsp") != -1
				|| path.indexOf("/hbimages") != -1) {// 如果是登录页面，不校验
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		response.sendRedirect(request.getContextPath()
				+ "/login.do?method=sessionTimeout");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
