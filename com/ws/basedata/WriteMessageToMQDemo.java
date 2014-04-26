package com.ws.basedata;

import java.io.IOException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueueManager;

public class WriteMessageToMQDemo {
	public static void main(String[] argvs) {
		MQEnvironment.hostname = null;
		MQEnvironment.addConnectionPoolToken();
		try {

			// 输入参数队列管理器名称
			MQEnvironment.CCSID = 1208;// 编码格式
			MQEnvironment.port = 1400;
			MQEnvironment.hostname = "10.157.224.10";
			MQEnvironment.channel = "test_server";
			MQQueueManager qm = new MQQueueManager("test");
			MQPutMessageOptions opts = new MQPutMessageOptions();
			opts.options = MQC.MQPMO_NONE;

			MQMessage msg = new MQMessage();
			msg.correlationId = MQC.MQCI_NONE;
			msg.messageId = MQC.MQMI_NONE;
			// 1208表示UTF8编码
			msg.characterSet = 1208;
			msg.writeString("<?xml....此处传入固定格式的XML文件内容，根据内容动态生成/>");
			// 第一个参数为队列名
			qm.put("test", msg, opts);
			qm.disconnect();

		} catch (MQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
