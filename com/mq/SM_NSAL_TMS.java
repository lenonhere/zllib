package com.mq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mq.MQOpera;
import com.zl.base.core.db.CallHelper;
import common.Logger;

/**
 * 发送信息测试
 *
 * @author 刘恒
 * @date 2009-9-14
 */
public class SM_NSAL_TMS implements Job {
	private static Logger logger = Logger.getLogger(SM_NSAL_TMS.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String xml = "";
		try {
			CallHelper helper = new CallHelper("SM_NSAL_TMS");
			helper.execute();
			List dataList = helper.getResult("results");
			int n = dataList.size() / 500;
			String[] resultNames = new String[] { "SYCOMPSYSTID",
					"HY_EAS_CODE", "GJ_CODE", "SYCOMPALIAS", "TSTAFFNAME",
					"TSTAFFTEL", "DESTNAME", "TADDRDESC", "TLINKMAN", "TTEL",
					"TSTAFFUPTIME", "ACTION" };
			String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
					"SOURCESYS", "TARGETSYS", "CREATETIME" };
			HashMap hm = null;
			List resultList = null;
			for (int j = 0; j <= n; j++) {
				if (j < n) {
					resultList = new ArrayList();
					for (int i = j * 500; i < (j + 1) * 500; i++) {
						hm = new HashMap();
						BasicDynaBean rowBean = (BasicDynaBean) dataList.get(i);
						hm.put("SYCOMPSYSTID", rowBean.get("sycompsystid"));
						hm.put("HY_EAS_CODE", rowBean.get("hy_eas_code"));
						hm.put("GJ_CODE", rowBean.get("gj_code"));
						hm.put("SYCOMPALIAS", rowBean.get("sycompalias"));
						hm.put("TSTAFFNAME", rowBean.get("tstaffname"));
						hm.put("TSTAFFTEL", rowBean.get("tstafftel"));
						hm.put("DESTNAME", rowBean.get("destname"));
						hm.put("TADDRDESC", rowBean.get("taddrdesc"));
						hm.put("TLINKMAN", rowBean.get("tlinkman"));
						hm.put("TTEL", rowBean.get("ttel"));
						hm.put("TSTAFFUPTIME", rowBean.get("tstaffuptime"));
						hm.put("ACTION", rowBean.get("action"));
						resultList.add(hm);
					}
				} else {
					resultList = new ArrayList();
					for (int i = j * 500; i < dataList.size(); i++) {
						hm = new HashMap();
						BasicDynaBean rowBean = (BasicDynaBean) dataList.get(i);
						hm.put("SYCOMPSYSTID", rowBean.get("sycompsystid"));
						hm.put("HY_EAS_CODE", rowBean.get("hy_eas_code"));
						hm.put("GJ_CODE", rowBean.get("gj_code"));
						hm.put("SYCOMPALIAS", rowBean.get("sycompalias"));
						hm.put("TSTAFFNAME", rowBean.get("tstaffname"));
						hm.put("TSTAFFTEL", rowBean.get("tstafftel"));
						hm.put("DESTNAME", rowBean.get("destname"));
						hm.put("TADDRDESC", rowBean.get("taddrdesc"));
						hm.put("TLINKMAN", rowBean.get("tlinkman"));
						hm.put("TTEL", rowBean.get("ttel"));
						hm.put("TSTAFFUPTIME", rowBean.get("tstaffuptime"));
						hm.put("ACTION", rowBean.get("action"));
						resultList.add(hm);
					}
				}
				Calendar rightNow = Calendar.getInstance();
				SimpleDateFormat fmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sysDatetime = fmt.format(rightNow.getTime());
				SimpleDateFormat fmtCode = new SimpleDateFormat(
						"yyyyMMdd_HHmmss");
				String sysDatetimeCode = fmtCode.format(rightNow.getTime());

				Hashtable hData = new Hashtable();
				hData.put("MSGCODE", "JK_NSAL_TMS_DXLXR_" + sysDatetimeCode);
				hData.put("MSGID", "JK_NSAL_TMS_DXLXR");
				hData.put("MSGNAME", "客户短信联系人");
				hData.put("SOURCESYS", "NSAL");
				hData.put("TARGETSYS", "TMS");
				hData.put("CREATETIME", sysDatetime);
				xml = MQOpera.makeXml("JK_NSAL_TMS_DXLXR", resultList,
						resultNames, hData, headNames);
				logger.debug(xml);
				MQOpera.sendMQ(xml, "JK_NSAL_TMS_DXLXR");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取发送信息失败！");
		}
	}
}
