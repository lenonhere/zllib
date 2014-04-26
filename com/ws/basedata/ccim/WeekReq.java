package com.ws.basedata.ccim;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ws.basedata.basicdata;
import com.zl.base.core.db.CallHelper;

/**
 * @author 刘恒
 * @date 2009-8-3
 */
public class WeekReq {
	private static Log logger = LogFactory.getLog(WeekReq.class);

	public String ParseString(String oid, String inXml) {
		String rtncode = "";
		// logger.debug(inXml);
		try {
			// 头信息
			// String MSGID = basicdata.getHeadInfo(inXml,"MSGID");
			// String MSGCODE = basicdata.getHeadInfo(inXml,"MSGCODE");
			String MSGNAME = basicdata.getHeadInfo(inXml, "MSGNAME");
			// String SOURCESYS = basicdata.getHeadInfo(inXml,"SOURCESYS");
			// String TARGETSYS = basicdata.getHeadInfo(inXml,"TARGETSYS");
			String ACTION = basicdata.getHeadInfo(inXml, "ACTION");
			String CREATETIME = basicdata.getHeadInfo(inXml, "CREATETIME");
			// 明细数据
			String CGT_CARTON_CODE = "";
			String CGT_DEMAND_QTY = "";
			String COM_CODE = "";
			String BEG_DATE = "";
			String END_DATE = "";
			InputStream is = new ByteArrayInputStream(inXml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);

			StringBuffer mysql = new StringBuffer();
			List root = doc.selectNodes("//ROW");

			Node row;
			Node nodeCGT_CARTON_CODE;
			Node nodeCGT_DEMAND_QTY;
			Node nodeCOM_CODE;
			Node nodeBEG_DATE;
			Node nodeEND_DATE;

			// 取出数据
			HashMap hm = null;
			int cnt = 0; // 记录数
			if (root != null && root.size() > 0) {
				for (Iterator it = root.iterator(); it.hasNext();) {
					row = (Node) it.next();
					nodeCGT_CARTON_CODE = row
							.selectSingleNode("CGT_CARTON_CODE");
					if (nodeCGT_CARTON_CODE != null) {
						CGT_CARTON_CODE = nodeCGT_CARTON_CODE.getText();
						logger.debug("CGT_CARTON_CODE:" + CGT_CARTON_CODE);
					} else {
						CGT_CARTON_CODE = "";
						logger.error("CGT_CARTON_CODE传入为空值");
					}
					nodeCGT_DEMAND_QTY = row.selectSingleNode("CGT_DEMAND_QTY");
					if (nodeCGT_DEMAND_QTY != null) {
						CGT_DEMAND_QTY = nodeCGT_DEMAND_QTY.getText();
						logger.debug("CGT_DEMAND_QTY:" + CGT_DEMAND_QTY);
					} else {
						CGT_DEMAND_QTY = "";
						logger.error("CGT_DEMAND_QTY传入为空值");
					}
					nodeCOM_CODE = row.selectSingleNode("COM_CODE");
					if (nodeCOM_CODE != null) {
						COM_CODE = nodeCOM_CODE.getText();
						logger.debug("COM_CODE:" + COM_CODE);
					} else {
						COM_CODE = "";
						logger.debug("COM_CODE传入为空值");
					}
					nodeBEG_DATE = row.selectSingleNode("BEG_DATE");
					if (nodeBEG_DATE != null) {
						BEG_DATE = nodeBEG_DATE.getText();
						logger.debug("BEG_DATE:" + BEG_DATE);
					} else {
						BEG_DATE = "";
						logger.debug("BEG_DATE传入为空值");
					}
					nodeEND_DATE = row.selectSingleNode("END_DATE");
					if (nodeEND_DATE != null) {
						END_DATE = nodeEND_DATE.getText();
						logger.debug("END_DATE:" + END_DATE);
					} else {
						END_DATE = "";
						logger.debug("END_DATE传入为空值");
					}
					hm = new HashMap();
					hm.put("CGT_CARTON_CODE", CGT_CARTON_CODE);
					hm.put("CGT_DEMAND_QTY", CGT_DEMAND_QTY);
					hm.put("COM_CODE", COM_CODE);
					hm.put("BEG_DATE", BEG_DATE);
					hm.put("END_DATE", END_DATE);
					hm.put("CREATEDATE", CREATETIME);
					hm.put("ACTION", ACTION);
					CallHelper helper = new CallHelper("weekReq");
					helper.autoCopy(hm);
					helper.execute();
					String errCode = (String) helper.getOutput("errCode");
					if (errCode.equals("0")) {
						cnt++;
					}
				}
				logger.debug(new StringBuffer("接收").append(MSGNAME)
						.append(root.size()).append("条，实际插入数据库").append(cnt)
						.append("条").toString());
			}

			CallHelper helper = new CallHelper("updateLOG_MQ");
			helper.setParam("OID", oid);
			helper.setParam("ROWS", String.valueOf(root.size()));
			helper.setParam("INSERTROWS", String.valueOf(cnt));
			helper.execute();
			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存失败，此数据流终止执行！");
			return "0";
		}
	}
}
