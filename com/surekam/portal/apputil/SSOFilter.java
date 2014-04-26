/*
 * 北京联信永益科技股份有限公司　　版权所有
 * $version: 1.0 　2007-12-18  
*  $id$
 */
package com.surekam.portal.apputil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Administrator
 */
public class SSOFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(SSOFilter.class);
	
	private Set webSealProxyIps = new HashSet();
	private boolean checkProxyIP = true;
	private AppLoginHelp loginHelp = null;
	private boolean useGsoBa = false;
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		//直接从http 头读取用户标识的验证方式 
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		// iv_server_name 本 http 头存在，表示是通过 webseal 反向的
		// 否则，本过滤器中不做处理
		if (request.getHeader("iv_server_name") == null ){
			filterChain.doFilter(req, resp);
			return;
		}
		
		String userId = null;
		if (useGsoBa){
			//gso 处理方式   begin 
			String auth = request.getHeader("authorization");
			if (auth != null ){
				auth = auth.substring(auth.indexOf(' ')+1);
				auth = Base64.decode(auth);
				userId = auth.split(":")[0];
			}
		} else{
			userId = request.getHeader("iv-user");
			// 用户未在webseal 上登陆，不做sso 处理。
			if (userId != null && userId.equals("Unauthenticated")){
				userId = null;
			}
		}
		
		if (userId == null){ // SSO 无效，不做处理
			filterChain.doFilter(req,resp);
			return;
		}
				
		String remoteAdd = request.getHeader("iv-remote-address"); // 客户机的 ip 地址。
		String proxyIp = request.getRemoteAddr();
		if (checkProxyIP && !webSealProxyIps.contains(proxyIp)){
			logger.warn("未受信任的代理服务器地址：" + proxyIp);
			return;
		}
		// 检查是否已经登陆
		if (! loginHelp.isAppUserLoged(userId, request)){
			boolean loginOk = loginHelp.processAppLogin(userId, request,response,remoteAdd);
			if (! loginOk){
				logger.error("用户"+userId+"的 sso 登陆失败");
			    return;
			}
		}
		filterChain.doFilter(req, resp);
	}

	public void init(FilterConfig conf) throws ServletException {
		String ips = conf.getInitParameter("AUTH_PROXY_IP");
		if (ips == null || ips.length() == 0 ){
			checkProxyIP = false;
			return;
		}
		 String[] ipArray = ips.split(";|:|,");
		 webSealProxyIps.addAll(Arrays.asList(ipArray));
		 logger.debug("webSealProxyIps:" + webSealProxyIps);
		 String  gso = conf.getInitParameter("SSO_TYPE");
		 if (gso != null && gso.length() != 0){
			 if (gso.equalsIgnoreCase("GSO")){
				 useGsoBa = true;
			 }
		 }
		 
		String className = conf.getInitParameter("APP_HELP_CLASS");
		try {
			loginHelp= (AppLoginHelp)Class.forName(className).newInstance();
		} catch (Exception e) {
			logger.error("自定义 login help 类装载失败，请检查配置参数",e);
			throw new ServletException("自定义 login help 类装载失败" + e.getMessage(),e);
		}
	}
}
