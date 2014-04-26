package com.zl.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.common.Constants;

public class ZoomlgdFilter implements Filter {

	private static final Log log = LogFactory.getLog(ZoomlgdFilter.class);

	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;

		// Conditionally select and set the character encoding to be used
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);

			if (encoding != null)
				request.setCharacterEncoding(encoding); // 设置request编码的地方
		}

		String currentURL = hreq.getRequestURI(); // 返回不带参数URL
		String urlParam = hreq.getQueryString(); // 返回当前URL的参数
		String contextPath = hreq.getContextPath(); // 返回 "/webroot"

		try {

			HttpSession session = hreq.getSession(false);
			// String ip = request.getRemoteAddr();
			// log.debug(currentURL);
			// log.debug(contextPath);

			if (currentURL.endsWith("index.jsp")
					|| currentURL.endsWith("checkImage.jsp")
					|| currentURL.endsWith("/")
					|| currentURL.endsWith("sessionError.jsp")
					|| ((session != null) && (session
							.getAttribute(Constants.SESSION_USER) != null))) {
				// log.debug("success!");
				// if(contextPath.length()>1)
				// currentURL = currentURL.replaceAll(contextPath,"");
				// 此处不能再setForward,不然会造成重复访问
				// setForward(currentURL,request,response);
			} else {
				// log.debug("用户还没登陆!");
				setForward("/common/sessionError.jsp", request, response);
			}

			// Pass control on to the next filter
			// 传递控制到下一个过滤器
			chain.doFilter(request, response);

		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		} catch (Exception ex) {
			filterConfig.getServletContext().log(ex.getMessage());
		}

	}

	/**
	 * Place this filter into service. 从web-app的web.xml文件中读取初始参数的值
	 */

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	/**
	 * Select an appropriate character encoding to be used, based on the
	 * characteristics of the current request and/or filter initialization
	 * parameters. If no character encoding should be set, return
	 * <code>null</code>. 选择request原来的编码
	 */
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

	// forward一个页面
	private void setForward(String url, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest hreq = (HttpServletRequest) request;
		RequestDispatcher dispatcher = hreq.getSession().getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
