package com.ws.basedata;

import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.zl.base.core.db.Executer;
import com.zl.util.MethodFactory;

/**
 * http://219.142.71.149/gsxt/services/ECS_NAT_BPM_MonthPlanRecvInd?wsdl
 *
 * 8. 接收工业企业月度需求服务
 *
 * 服务标识 ECS_NAT_BPM_MonthPlanRecvInd
 *
 * 服务提供者 国家局
 *
 * 服务使用者 工业企业
 *
 * 服务管理者 国家局信息中心
 *
 * 服务描述 接收工业企业月度需求配货服务
 *
 * @author cdd
 *
 */
public class ECS_NAT_BPM_MonthPlanRecvInd {

	private static Logger log = Logger
			.getLogger(ECS_NAT_BPM_MonthPlanRecvInd.class);

	private static ECS_NAT_BPM_MonthPlanRecvInd instance; // 唯一实例

	static synchronized public ECS_NAT_BPM_MonthPlanRecvInd getInstance() {
		if (instance == null) {
			instance = new ECS_NAT_BPM_MonthPlanRecvInd();
		}
		return instance;
	}

	// 为确保服务安全，调用服务时需要提供服务使用单位的Token信息。Token信息由服务使用单位从卷烟交易系统中申请。
	// 测试环境下的Token信息为“本单位代码_test”。
	private static final String token = "C8AF77E83B83F3D848525A60DDF9C6CF42C05DDE";
	// private static final String token = "20420001_test";
	private static final String indCode = "20420001";// --//武烟国家局编码

	private static String sendXMLDataToDC(String methodName, String comCode,
			String planInfo) throws Exception {
		// 服务命名空间
		String namespaceURI = "http://tobacco/ecs/ind/bpm/plan/month";
		// 服务地址
		// String endPointAddress =
		// "http://10.157.72.231:8486/NatGsxtServices/services/ECS_NAT_BPM_MonthPlanRecvInd";
		String endPointAddress = "http://10.156.0.136:9080/NatGsxtServices/services/ECS_NAT_BPM_MonthPlanRecvInd";
		// String endPointAddress =
		// "http://219.142.71.149/gsxt/services/ECS_NAT_BPM_MonthPlanRecvInd";

		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(endPointAddress));

		// 设置要调用的方法
		call.setOperationName(new QName(namespaceURI, methodName));

		// 该方法需要的参数
		call.addParameter("token", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("comCode", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("indCode", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("planInfo", XMLType.XSD_STRING, ParameterMode.IN);

		// 方法的返回值类型
		call.setReturnType(XMLType.XSD_STRING);

		// 参数：token信息、市公司编码、工业企业编码、衔接详细信息
		String[] methodArgs = new String[] { token, comCode, indCode, planInfo };

		// 返回状态码
		String status = (String) call.invoke(methodArgs);

		System.out.println("Status >>:" + status);
		// System.out.println("EndPoint >>:" + endPointAddress);
		System.out.println(methodName + "--" + token + "--" + comCode + "--"
				+ indCode);

		boolean flag = ErrorStatusInfo.printServerStateInfo(status);
		String msg = ErrorStatusInfo.getServerStateInfo(status);
		if (flag) {
			String sql = "";

			if ("recvSupplyMonthPlan".equals(methodName)) {
				sql = "update ECS_MonthPlanRecvInd_Supply set sendflag = '1' where comcode = '"
						+ comCode + "'";
			}
			if ("recvJoinMonthPlan".equals(methodName)) {
				sql = "update ECS_MonthPlanRecvInd_Join set sendflag = '1' where comcode = '"
						+ comCode + "'";
			}
			if ("recvAdjustMonthPlan".equals(methodName)) {
				sql = "update ECS_MonthPlanRecvInd_Adjust set sendflag = '1' where comcode = '"
						+ comCode + "'";
			}
			Executer.getInstance().ExecUpdateSQL(sql).getAffectRowCount();
			// System.out.println(comCode + "sendFlag状态更新成功!");

		}

		return msg;

	}

	// Schedule调用
	public void autoSendDataToDC(String year, String month) {
		ECS_NAT_BPM_MonthPlanRecvInd.getInstance().autoSendSupplyDataToDC(year,
				month);
		ECS_NAT_BPM_MonthPlanRecvInd.getInstance().autoSendJoinDataToDC(year,
				month);
		ECS_NAT_BPM_MonthPlanRecvInd.getInstance().autoSendAdjustDataToDC(year,
				month);
	}

	// 工业可供量
	private void autoSendSupplyDataToDC(String year, String month) {

		try {
			String comCodeSql = "select distinct comcode from ECS_MonthPlanRecvInd_Supply where sendflag='0' and plan_year='"
					+ year + "' and plan_month='" + month + "' ";
			List gjCodeList = Executer.getInstance().ExecSeletSQL(comCodeSql)
					.getResultSet();

			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("comcode");
				String sql = "select distinct comcode,plan_year,plan_month,brand_code,brand_name,supply_amount from ECS_MonthPlanRecvInd_Supply where sendflag='0' and comcode='"
						+ comCode
						+ "' and plan_year='"
						+ year
						+ "' and plan_month='" + month + "' ";
				// 导出数据
				List results = Executer.getInstance().ExecSeletSQL(sql)
						.getResultSet();
				if (results.size() > 0) {
					// 发送
					ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
							.recvSupplyMonthPlan(year, month, comCode, results);
				}
			}

		} catch (Exception e) {
			log.debug(e);
		}
	}

	// 工业衔接量
	private void autoSendJoinDataToDC(String year, String month) {

		try {
			String comCodeSql = "select distinct comcode from ECS_MonthPlanRecvInd_Join where sendflag='0' and plan_year='"
					+ year + "' and plan_month='" + month + "' ";
			List gjCodeList = Executer.getInstance().ExecSeletSQL(comCodeSql)
					.getResultSet();

			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("comcode");
				String sql = "select distinct comcode,plan_year,plan_month,brand_code,brand_name,join_amount from ECS_MonthPlanRecvInd_Join where sendflag='0' and comcode='"
						+ comCode
						+ "' and plan_year='"
						+ year
						+ "' and plan_month='" + month + "' ";
				// 导出数据
				List results = Executer.getInstance().ExecSeletSQL(sql)
						.getResultSet();
				if (results.size() > 0) {
					// 发送
					ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
							.recvJoinMonthPlan(year, month, comCode, results);
				}
			}

		} catch (Exception e) {
			log.debug(e);
		}
	}

	// 工业调整量
	private void autoSendAdjustDataToDC(String year, String month) {

		try {
			String comCodeSql = "select distinct comcode from ECS_MonthPlanRecvInd_Adjust where sendflag='0' and plan_year='"
					+ year + "' and plan_month='" + month + "' ";
			List gjCodeList = Executer.getInstance().ExecSeletSQL(comCodeSql)
					.getResultSet();

			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("comcode");
				String sql = "select distinct comcode,plan_year,plan_month,brand_code,brand_name,adjust_amount from ECS_MonthPlanRecvInd_Adjust where sendflag='0' and comcode='"
						+ comCode
						+ "' and plan_year='"
						+ year
						+ "' and plan_month='" + month + "' ";
				// 导出数据
				List results = Executer.getInstance().ExecSeletSQL(sql)
						.getResultSet();
				if (results.size() > 0) {
					// 发送
					ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
							.recvAdjustMonthPlan(year, month, comCode, results);
				}
			}

		} catch (Exception e) {
			log.debug(e);
		}
	}

	// Action调用
	public String MonthPlanRecvInd(String sendFlag, String year, String month,
			String comCode, List results) throws Exception {
		String msg = "";
		if ("1".equals(sendFlag)) {
			msg = ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
					.recvSupplyMonthPlan(year, month, comCode, results);
		}
		if ("2".equals(sendFlag)) {
			msg = ECS_NAT_BPM_MonthPlanRecvInd.getInstance().recvJoinMonthPlan(
					year, month, comCode, results);
		}
		if ("3".equals(sendFlag)) {
			msg = ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
					.recvAdjustMonthPlan(year, month, comCode, results);
		}
		return msg;
	}

	// 1. 接收工业企业可供量服务
	private String recvSupplyMonthPlan(String year, String month,
			String comCode, List results) throws Exception {

		// 服务方法名
		String methodName = "recvSupplyMonthPlan";
		// 生成XML格式
		String planInfo = ECS_NAT_BPM_MonthPlanRecvInd.markXML(1, year, month,
				results);
		// System.out.println("planInfo >>:1: " + planInfo);

		// 输出状态信息
		String msg = ECS_NAT_BPM_MonthPlanRecvInd.sendXMLDataToDC(methodName,
				comCode, planInfo);
		return msg;
	}

	// 2. 接收工业企业月度衔接量服务
	private String recvJoinMonthPlan(String year, String month, String comCode,
			List results) throws Exception {
		// 服务方法名
		String methodName = "recvJoinMonthPlan";// 生成XML格式
		String planInfo = ECS_NAT_BPM_MonthPlanRecvInd.markXML(2, year, month,
				results);
		// System.out.println("planInfo >>:2: " + planInfo);

		// 输出状态信息
		String msg = ECS_NAT_BPM_MonthPlanRecvInd.sendXMLDataToDC(methodName,
				comCode, planInfo);
		return msg;
	}

	// 3. 接收工业企业月度调整量服务
	private String recvAdjustMonthPlan(String year, String month,
			String comCode, List results) throws Exception {
		// 服务方法名
		String methodName = "recvAdjustMonthPlan";// 生成XML格式
		String planInfo = ECS_NAT_BPM_MonthPlanRecvInd.markXML(3, year, month,
				results);
		// System.out.println("planInfo >>:3: " + planInfo);

		// 输出状态信息
		String msg = ECS_NAT_BPM_MonthPlanRecvInd.sendXMLDataToDC(methodName,
				comCode, planInfo);
		return msg;
	}

	private static String markXML(int methodFlag, String year, String month,
			List resultsList) {
		String info = "";
		Document document = null;
		String elName = "";

		switch (methodFlag) {
		case 1:
			elName = "supply_amount";// 工业可供量
			break;
		case 2:
			elName = "join_amount";// 工业衔接量
			break;
		case 3:
			elName = "adjust_amount";// 工业调整量
			break;
		}
		// System.out.println("elName: "+elName);
		try {
			document = DocumentHelper.createDocument();
			Element rootElement = document.addElement("DATASETS");

			if (resultsList != null && resultsList.size() > 0) {

				Element element1 = rootElement.addElement("DATASET");

				Element element2 = element1.addElement("PLAN_YEAR");
				element2.setText(year);
				Element element3 = element1.addElement("PLAN_MONTH");
				element3.setText(month);

				Element element4 = element1.addElement("BRANDS");
				for (int i = 0; i < resultsList.size(); i++) {

					BasicDynaBean bean = (BasicDynaBean) resultsList.get(i);
					Element element5 = element4.addElement("BRAND");

					Element element6 = element5.addElement("BRAND_CODE");
					element6.setText(MethodFactory.getThisString(bean
							.get("brand_code")));

					Element element7 = element5.addElement("BRAND_NAME");
					element7.setText(MethodFactory.getThisString(bean
							.get("brand_name")));

					Element element8 = element5
							.addElement(elName.toUpperCase());
					element8.setText(MethodFactory.getThisString(bean
							.get(elName)));

				}
				XMLWriter writer = null;
				StringWriter strWriter = new StringWriter();
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");

				writer = new XMLWriter(strWriter, format);
				writer.write(document);

				info = strWriter.toString();
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.debug(e);
		}
		// System.out.println("planInfo >>:" + info);
		return info;
	}

}
