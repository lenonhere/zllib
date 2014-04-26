package com.zl.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.base.core.db.config.ProcedureConfig;

public class ResourceListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(ResourceListener.class);
	private ServletContext context = null;

	public void contextInitialized(ServletContextEvent event) {
		log.info("ResourceListener ------开始执行!");
		context = event.getServletContext();
		String absolutePath = context.getInitParameter("procedureBasePath");
		String basePath = context.getRealPath(absolutePath.toString());
		log.info("resource listener initialized: base path = " + basePath);
		//
		ProcedureConfig procConfig = ProcedureConfig.getInstance();
		procConfig.setBasePath(basePath);
		procConfig.build();
		log.info("procConfig.build()-----成功完成!");
	}

	public void contextDestroyed(ServletContextEvent event) {
		log.info("resource listener destroyed");
	}

}
