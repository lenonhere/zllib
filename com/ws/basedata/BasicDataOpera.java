package com.ws.basedata;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mq.MQOpera;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.ProcedureManager;

/**
 * 获取未处理的mq接收消息和发送消息列表，并完成操作
 *
 * @author 刘恒
 * @date 2009-9-23
 */
public class BasicDataOpera implements Job {
	private static final Log logger = LogFactory.getLog(BasicDataOpera.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection incon = null;
		try {
			incon = ProcedureManager.getConnection();
			CallHelper helper = new CallHelper("getLOG_MQ");
			helper.execute();
			List getList = helper.getResult("results");// 接收队列
			List sendList = helper.getResult("results1");// 发送队列
			for (int i = 0; i < sendList.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) sendList.get(i);
				String oid = String.valueOf(((Integer) rowBean.get("oid"))
						.intValue());
				String msgid = (String) rowBean.get("msgid");
				Clob content = (Clob) rowBean.get("content");
				MQOpera mqOpera = new MQOpera();
				mqOpera.sendMQ(this.getStringFromClob(content), msgid, oid);
			}
			for (int i = 0; i < getList.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) getList.get(i);
				String oid = String.valueOf(((Integer) rowBean.get("oid"))
						.intValue());
				Clob content = (Clob) rowBean.get("content");
				basicdata.SendXml(this.getStringFromClob(content), oid, incon);
			}
			// System.out.println("将MQ消息写入数据库，消息数量"+mqList.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (incon != null) {
					incon.close();
					incon = null;
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public String getStringFromClob(Clob clobV) {
		Clob clob = clobV;
		String returnValue = "";
		try {
			if (clob != null) {
				Reader is = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(is);
				StringBuffer sb = new StringBuffer();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
				br.close();
				returnValue = sb.toString();
				// br.close();is.close();
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return returnValue;
	}
}
