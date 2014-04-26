package com.mq;

import org.apache.commons.logging.Log;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.common.log.LogFactory;

/**
 * @author 刘恒
 * @date 2009-6-22
 */
public class MQbase {
	public static final int _CCSID = 1208;
	// 正式
	// 营销MQ 业务数据发送队列:NSAL_YW_R_QUEUE
	// 营销MQ 基础数据发送队列:NSAL_JC_R_QUEUE
	// 营销MQ 共用数据接收队列:NSAL_GY_L_QUEUE
	public static final String _hostname = "10.157.33.18";
	public static final String _qMgr = "NSAL_QM";// 队列管理器
	public static final String _queue = "NSAL_L_QUEUE";// 接收队列
	public static final String queue_remote = "NSAL_YW_R_QUEUE";// 业务数据发送队列
	public static final String queue_remote_jc = "NSAL_JC_R_QUEUE";// 基础数据发送队列
	public static final String _channel = "NSAL_SERVER";// 通道服务器连接
	public static final int _port = 1414;
	// 测试
	// public static final String _hostname = "10.157.224.10";
	// public static final String _qMgr = "test";
	// public static final String _queue = "test";
	// public static final String queue_remote = "test";//NSAL_R_QUEUE
	// public static final String _channel = "test_server";
	// public static final int _port = 1400;

	private static MQQueueManager qMgr = null;

	private static MQQueue mqQueueGet = null;

	private static MQQueue mqQueuePut = null;

	private static final Log logger = LogFactory.getLog();

	@SuppressWarnings("deprecation")
	public static void close(String operate) {
		try {
			if (operate.equals("get")) {
				if (mqQueueGet != null) {
					// System.out.println("CLOSE +++++++++++++++++++++++++++++++++++ mqQueueGet : "+mqQueueGet);
					mqQueueGet.close();
					mqQueueGet = null;
				}
				if (qMgr != null) {
					// System.out.println("CLOSE +++++++++++++++++++++++++++++++++++ qMgrGet : "+qMgrGet);
					qMgr.close();
					qMgr.disconnect();
					qMgr = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MQQueue getMQQueue(String operate) {
		if (operate.equals("get")) {
			// System.out.println("CLOSE +++++++++++++++++++++++++++++++++++ mqQueueGet : "+mqQueueGet);
			// if (mqQueueGet == null) {
			getMQQueueGet();
			// }
			return mqQueueGet;
		} else if (operate.equals("put")) {
			if (mqQueuePut == null) {
				getMQQueuePut();
			}
			return mqQueuePut;
		}
		return null;
	}

	public static MQQueueManager getMQQueueManager() {
		try {
			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.CCSID = _CCSID;
			MQEnvironment.port = _port;// 正式1414;测试1400
			MQEnvironment.hostname = _hostname;// 正式10.157.224.14
			MQEnvironment.channel = _channel;
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,
					MQC.TRANSPORT_MQSERIES);
			qMgr = new MQQueueManager(_qMgr);// 正式NSAL_QM，测试t1
		} catch (MQException ex) {
			if (qMgr != null) {
				try {
					qMgr.close();
					qMgr.disconnect();
				} catch (MQException e) {
					e.printStackTrace();
				}
			}
			if (!(ex.reasonCode == 2033)) {
				// ex.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ ex.completionCode + " Reasoncode " + ex.reasonCode);
			}
		}
		return qMgr;
	}

	private static void getMQQueuePut() {
		if (qMgr == null) {
			getMQQueueManager();
		}
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
			mqQueuePut = qMgr.accessQueue(_queue, openOptions);
		} catch (MQException ex) {
			if (mqQueuePut != null) {
				try {
					mqQueuePut.close();
				} catch (MQException e) {
					e.printStackTrace();
				}
			}
			if (!(ex.reasonCode == 2033)) {
				ex.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ ex.completionCode + " Reasoncode " + ex.reasonCode);
			}
		}
	}

	private static void getMQQueueGet() {
		if (qMgr == null || !qMgr.isConnected()) {
			getMQQueueManager();
		}
		try {
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_INPUT_SHARED
					| MQC.MQOO_FAIL_IF_QUIESCING;
			mqQueueGet = qMgr.accessQueue(_queue, openOptions);
		} catch (MQException ex) {
			if (mqQueueGet != null) {
				try {
					mqQueueGet.close();
				} catch (MQException e) {
					e.printStackTrace();
				}
			}
			if (!(ex.reasonCode == 2033)) {
				ex.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ ex.completionCode + " Reasoncode " + ex.reasonCode);
			}
		}
	}
}
