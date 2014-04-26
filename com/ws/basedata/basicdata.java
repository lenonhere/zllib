package com.ws.basedata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ws.basedata.ccim.WeekReq;
import com.zl.base.core.db.Executer;

/**
 * @author Administrator
 *
 */
public class basicdata {
	private static Log logger = LogFactory.getLog(basicdata.class);

	private static WeekReq weekReq = null;

	public static String SendXml(String inXml, String oid, Connection incon)
			throws SQLException {
		// Connection incon = null;
		String rtnstr = "";
		try {
			// logger.debug("Service print:Client input String is " + inXml);
			// 连接数据库
			// incon = ProcedureManager.getConnection();
			// if ( incon == null )
			// {
			// logger.error("连接数据库失败！");
			// return "0";
			// }
			String MSGID = getHeadInfo(inXml, "MSGID");
			// System.out.println(MSGID);

			// 判断接口
			if (MSGID.equals("WEEKREQ")) {// 周需求计划反馈信息
				if (weekReq == null) {
					weekReq = new WeekReq();
				}
				rtnstr = weekReq.ParseString(oid, inXml);
				return rtnstr;
			} else {
				logger.error("没有此接口服务");
				return "0";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally{
		// if(incon!=null)
		// incon.close();
		// }
		return rtnstr;
	}

	public static String getHeadInfo(String inXml, String node) {
		String nodeValue = "";
		try {
			// InputStream is = new ByteArrayInputStream(inXml.getBytes());
			InputStream is = new ByteArrayInputStream(inXml.getBytes("UTF-8"));

			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Node root = doc.selectSingleNode("//" + node);
			if (root != null) {
				nodeValue = root.getText();
				// logger.debug(node+"接口代码" + nodeValue);
			} else {
				nodeValue = "";
				logger.debug("没有找到" + nodeValue);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return nodeValue;
	}

	public static void writeLogMQ(Connection incon, String inXml, String rows,
			String comCodeFirst, Executer dbExecuter) {
		String MSGID = getHeadInfo(inXml, "MSGID");
		String MSGCODE = getHeadInfo(inXml, "MSGCODE");
		String MSGNAME = getHeadInfo(inXml, "MSGNAME");
		String SOURCESYS = getHeadInfo(inXml, "SOURCESYS");
		String TARGETSYS = getHeadInfo(inXml, "TARGETSYS");
		String ACTION = getHeadInfo(inXml, "ACTION");
		String CREATETIME = getHeadInfo(inXml, "CREATETIME");

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into NBADV.LOG_MQ (MSGID,MSGCODE,MSGNAME,SOURCESYS,TARGETSYS,ACTION,CREATETIME,COM_CODE,ROWS)");
			sb.append(" values('").append(MSGID).append("','").append(MSGCODE)
					.append("','").append(MSGNAME).append("','");
			sb.append(SOURCESYS).append("','").append(TARGETSYS).append("','")
					.append(ACTION).append("','").append(CREATETIME);
			sb.append("','").append(comCodeFirst).append("',").append(rows)
					.append(") ");
			dbExecuter.ExecUpdateSQL(sb.toString(), incon);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MQ日志保存失败！" + e.getMessage());
		}
	}
}