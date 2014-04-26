package com.mq;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueueManager;

/**
 * @author 刘恒
 * @date 2009-9-15
 */
public class SendTest {
	public static void main(String[] args) throws Exception {
		String qManager;
		String queue;
		try {
			// 连接队列管理器
			qManager = "NSAL_QM";
			queue = "NSAL_R_QUEUE";
			MQEnvironment.hostname = "10.157.33.18";
			// MQEnvironment.hostname = "192.168.1.102";
			MQEnvironment.channel = "NSAL_SERVER";
			MQEnvironment.CCSID = 1208;
			MQEnvironment.port = 1414;
			MQEnvironment.addConnectionPoolToken();
			// 输入参数队列管理器名称
			MQQueueManager qm = new MQQueueManager(qManager);
			MQPutMessageOptions opts = new MQPutMessageOptions();
			opts.options = MQC.MQPMO_NONE;
			MQMessage msg = new MQMessage();
			msg.correlationId = MQC.MQCI_NONE;
			msg.messageId = MQC.MQMI_NONE;
			// 1208表示UTF8编码
			msg.characterSet = 1208;
			msg.writeString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><SM_NSAL_TMS><HEAD><MSGCODE>1</MSGCODE><MSGID>SM_NSAL_TMS</MSGID><MSGNAME>客户短信联系人</MSGNAME></HEAD><DATA><ROW><SYCOMPSYSTID>客户id(营销系统内码</SYCOMPSYSTID></ROW></DATA></SM_NSAL_TMS>");
			// 第一个参数为队列名
			qm.put(queue, msg, opts);
			qm.disconnect();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
