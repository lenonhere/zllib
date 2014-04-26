/**
 * 从数据中心MQ读取基础数据任务
 * @author: 朱忠南
 * @date: Nov 13, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author: 刘恒
 * @modify_time: 2009-06
 */
package com.ws.basedata;

import java.io.IOException;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.mq.MQOpera;
import com.mq.MQbase;
import com.zl.base.core.db.ProcedureManager;

/**
 * @author jokin
 * @date Nov 13, 2008
 */
public class BasicDataJob implements Job {

	private static final Log logger = LogFactory.getLog(BasicDataJob.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection incon = null;
		MQQueueManager mqQueueManager = null;
		MQQueue mqQueue = null;
		try {
			System.out.println("read MQ......");

			incon = ProcedureManager.getConnection();

			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.CCSID = MQbase._CCSID;
			MQEnvironment.port = MQbase._port;
			MQEnvironment.hostname = MQbase._hostname;
			MQEnvironment.channel = MQbase._channel;
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,
					MQC.TRANSPORT_MQSERIES);
			mqQueueManager = new MQQueueManager(MQbase._qMgr);

			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_INPUT_SHARED
					| MQC.MQOO_FAIL_IF_QUIESCING;
			mqQueue = mqQueueManager.accessQueue(MQbase._queue, openOptions);

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
			gmo.options = gmo.options + MQC.MQGMO_WAIT;

			gmo.waitInterval = 30000;// MQC.MQWI_UNLIMITED;
			MQMessage inMsg = new MQMessage();
			mqQueue.get(inMsg, gmo);
			while (inMsg.getTotalMessageLength() > 0) {
				String msg = inMsg.readStringOfByteLength(inMsg
						.getTotalMessageLength());
				// logger.debug(msg);

				mqQueueManager.commit();
				String rtn = "0";
				try {
					// rtn = basicdata.SendXml(msg);
					rtn = MQOpera.saveMQ(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				inMsg = new MQMessage();
				mqQueue.get(inMsg, gmo);
			}
		} catch (MQException e) {
			if (!(e.reasonCode == 2033)) {
				e.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ e.completionCode + " Reasoncode " + e.reasonCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("An error occurred whilst writing to the message buffer: "
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error occurred whilst importing the base data:"
					+ e);
		} finally {
			if (incon != null) {
				ProcedureManager.closeConnection(incon);
			}
			try {
				if (mqQueue != null) {
					mqQueue.close();
					mqQueue = null;
				}
				if (mqQueueManager != null) {
					mqQueueManager.close();
					mqQueueManager.disconnect();
					mqQueueManager = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" ----------------- close MQ ---------------- ");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	// public void execute(JobExecutionContext arg0) throws
	// JobExecutionException {
	// Connection incon = null;
	// try {
	// logger.debug("开始读取......");
	// String flag = "";
	// String myCode = "";
	// String myName = "";
	// basicdata bdata = new basicdata();
	//
	// incon = ProcedureManager.getConnection();
	//
	// MQEnvironment.CCSID = 1208;
	// MQEnvironment.port = 1414;
	// MQEnvironment.hostname = "10.157.224.14";
	// MQEnvironment.channel = "NSAL_SERVER";
	// MQEnvironment.properties.put( MQC.TRANSPORT_PROPERTY,
	// MQC.TRANSPORT_MQSERIES);
	// MQQueueManager qMgr = new MQQueueManager("NSAL_QM");
	// int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_INPUT_SHARED |
	// MQC.MQOO_FAIL_IF_QUIESCING;
	// MQQueue mqQueue = qMgr.accessQueue("NSAL_L_QUEUE",openOptions);
	// MQGetMessageOptions gmo = new MQGetMessageOptions();
	// gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
	// gmo.options = gmo.options + MQC.MQGMO_WAIT;
	//
	// //-1表示无限等待
	// gmo.waitInterval = -1;
	// MQMessage inMsg = new MQMessage();
	// mqQueue.get( inMsg, gmo );
	// while(inMsg.getTotalMessageLength() > 0){
	// String msg = inMsg.readStringOfByteLength( inMsg.getTotalMessageLength()
	// );
	// logger.debug(msg);
	//
	// qMgr.commit();
	// String rtn = "0";
	// //返回处理失败消息
	// String errorMsg = "";
	// try{
	// rtn = bdata.SendXml(msg);
	// }catch(Exception e){
	// errorMsg = e.getMessage();
	// }
	// if (rtn.equals("1"))
	// {
	// //成功
	// flag = "1";
	// }else if(rtn.equals("0"))
	// {
	// //失败
	// flag = "0";
	// }
	//
	// //记录日志
	// //日志表:
	// /*字段（至少包含）：
	// * oid
	// * code 接口代码（即数据中心表名）
	// * name 接口描述(接口中文描述)
	// * logtime日志时间
	// * msg 数据流内容
	// * flag 成功1/失败0 标识
	// * reason 失败原因
	// * */
	// /*表建好后
	// * 插入日志SQL
	// */
	// myCode = bdata.GetPID(msg);
	// myName = bdata.GetPNAME(msg);
	// StringBuffer mySql =new StringBuffer();
	// String sqlStr = "";
	// mySql.append(" insert into nbadv.basicdatalog (");
	// mySql.append(" itcode,");
	// mySql.append(" itname,");
	// mySql.append(" logtime,");
	// mySql.append(" msg,");
	// mySql.append(" flag,");
	// mySql.append(" reason)");
	// mySql.append(" values (");
	// mySql.append("'" + myCode + "',");
	// mySql.append("'" + myName + "',");
	// mySql.append(" current timestamp,");
	// mySql.append("'" + msg + "',");
	// mySql.append(flag + ",");
	// mySql.append("'" + errorMsg + "')");
	// logger.debug( "SQL:" + mySql.toString());
	//
	// sqlStr = mySql.toString();
	// mySql.delete(0, mySql.length());
	// try{
	// DBExecuter.getInstance().ExecUpdateSQL(sqlStr, incon);
	// }catch(Exception e){
	// logger.error("读取mq数据时，插入日志失败：",e);
	// }
	//
	// inMsg = new MQMessage();
	// mqQueue.get( inMsg, gmo );
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error("导入基础数据发生错误:"+e);
	// } finally{
	// if(incon != null){
	// ProcedureManager.closeConnection(incon);
	// }
	// }
	// }

}
