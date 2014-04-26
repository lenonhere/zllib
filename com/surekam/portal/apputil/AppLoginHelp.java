/*
 * 北京联信永益科技股份有限公司　　版权所有
 * $version: 1.0 　2007-12-19  
*  $id$
 */
package com.surekam.portal.apputil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  类说明： 集成到 portal 的应用程序的用户登陆处理代理接口。
 *          实现类必须是线程安全的，应用中只动态创建一个实例。
 *          实现类必须有一个无参构造函数
 * 	
 *  @author JunqingChen　  @version $Revision: 2.0 $
 */
public interface AppLoginHelp {

	/**
	 * 按应用系统的自定义要求进行用户登陆处理
	 * userid 为在应用系统中对应的用户 id ,本部分处理一般直接调用原来的 login方法，但是不要求密码验证
	 * @param userId
	 * @param request
	 * @param response 
	 * @param remoteIp 远程客户机的真实ip 地址。通过代理方式访问后，request 上取得的远程地址是代理服务器的
	 * @return 登陆处理成功，返回 true,出错记录出错信息，返回 false 
	 */
	public boolean processAppLogin(String userId, HttpServletRequest request,HttpServletResponse response,String remoteIp);

	/**
	 * 判断用户是否已经登陆。
	 * userId是从 sso 请求中所到的当前sso 用户 id 。如果当前登陆用户
	 * 
	 * @param userId
	 * @param request
	 * @return 当前用户已经登陆，session 中用户id 与ssoid 相同，返回 true 
	 *         session 中无用户标识，返回fasel.
	 *         session 中的用户标识与sso  id 不同，将session 中的当前用户登出，返回 false 
	 */
	public boolean isAppUserLoged(String userId, HttpServletRequest request);	
	
}
