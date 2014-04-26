package com.ws.basedata;

import java.io.StringReader;
import java.net.URL;
import java.sql.Types;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.zl.base.core.db.Executer;
import com.zl.base.core.db.ProcedureLauncher;
import com.zl.base.core.db.SqlParam;
import com.zl.base.core.db.SqlReturn;

/**
 * http://219.142.71.149/gsxt/services/ECS_NAT_BPM_ProvMonthPlanQueryInd?wsdl
 *
 * 9. 查询工业企业月度需求服务
 *
 * 服务标识 ECS_NAT_BPM_MonthPlanQueryInd
 *
 * 服务提供者 国家局
 *
 * 服务使用者 商业企业
 *
 * 服务管理者 国家局信息中心
 *
 * 服务描述 查询工业企业月度需求配货服务
 *
 * @author Administrator
 *
 */
public class ECS_NAT_BPM_MonthPlanQueryInd {

	private static Logger log = Logger
			.getLogger(ECS_NAT_BPM_MonthPlanQueryInd.class);

	private static ECS_NAT_BPM_MonthPlanQueryInd instance; // 唯一实例

	static synchronized public ECS_NAT_BPM_MonthPlanQueryInd getInstance() {
		if (instance == null) {
			instance = new ECS_NAT_BPM_MonthPlanQueryInd();
		}
		return instance;
	}

	// 为确保服务安全，调用服务时需要提供服务使用单位的Token信息。Token信息由服务使用单位从卷烟交易系统中申请。
	// 测试环境下的Token信息为“本单位代码_test”。
	private static final String token = "C8AF77E83B83F3D848525A60DDF9C6CF42C05DDE";
	// private static final String token = "20420001_test";
	private static final String indCode = "20420001";// 武烟国家局编码
	private static String deleteSql = "DELETE FROM ECS_MonthPlanQueryInd_Temp";

	public String importMonthPlanQueryIndData(String year, String month,
			String comCode) throws Exception {

		// 服务命名空间
		String namespaceURI = "http://tobacco/ecs/ind/bpm/plan/month";

		// 服务地址
		// String endPointAddress =
		// "http://10.157.72.149:8486/NatGsxtServices/services/ECS_NAT_BPM_MonthPlanQueryInd";
		// String endPointAddress =
		// "http://219.142.71.149/gsxt/services/ECS_NAT_BPM_MonthPlanQueryInd";
		String endPointAddress = "http://10.156.0.136:9080/NatGsxtServices/services/ECS_NAT_BPM_MonthPlanQueryInd";

		// 服务方法名
		String methodName = "queryMonthPlan";

		Service service = new Service();
		Call call = (Call) service.createCall();

		// 请求服务地址
		call.setTargetEndpointAddress(new URL(endPointAddress));

		// 设置要调用的方法
		call.setOperationName(new QName(namespaceURI, methodName));

		// 输入参数
		call.addParameter("token", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("planYear", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("planMonth", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("comCode", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("indCode", XMLType.XSD_STRING, ParameterMode.IN);

		// 输出参数类型
		call.setReturnType(XMLType.XSD_STRING);

		// 参数：token信息、年份、月份、市公司编码、工业企业编码
		String[] methodArgs = new String[] { token, year, month, comCode,
				indCode };

		// 返回参数
		String planInfo = (String) call.invoke(methodArgs);

		// System.out.println("Sources >>:" + planInfo);
		// System.out.println("EndPoint >>:" + endPointAddress);
		System.out.println(methodName + "--" + token + "--" + year + "--"
				+ month + "--" + comCode + "--" + indCode);

		// 输出状态信息
		boolean f = ErrorStatusInfo.printServerStateInfo(planInfo);
		String msg = ErrorStatusInfo.getServerStateInfo(planInfo);
		// 保存数据到数据库
		if (f) {
			// System.out.println("planInfo >>:: " + planInfo);
			ECS_NAT_BPM_MonthPlanQueryInd.insertDB(comCode, planInfo);
		}
		return msg;
	}

	private static void insertDB(String comCode, String planInfo)
			throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		StringReader sd = new StringReader(planInfo);
		InputSource inp = new InputSource(sd);
		Document document = builder.parse(inp);
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		// log.info("数据内容长度: "+nodeList.getLength());

		if (nodeList.getLength() > 0) {

			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlValues = new StringBuffer();
			StringBuffer titleItem = null;
			StringBuffer brandItem = null;
			StringBuffer titleItemValues = null;
			StringBuffer brandItemValues = null;

			for (int i = 0; i < nodeList.getLength(); i++) {

				NodeList nodeListTemp = nodeList.item(i).getChildNodes();
				titleItem = new StringBuffer();
				titleItemValues = new StringBuffer();

				for (int j = 0; j < nodeListTemp.getLength(); j++) {

					String nodeName = nodeListTemp.item(j).getNodeName()
							.toLowerCase();

					if (!nodeName.equals("brands")) {

						titleItem.append(nodeName + ",");
						String nodeValue = nodeListTemp.item(j).getFirstChild()
								.getNodeValue().trim();
						if ("plan_month".equals(nodeName)) {
							nodeValue = (nodeValue.length() == 1) ? "0"
									+ nodeValue : nodeValue;
						}
						titleItemValues.append("'" + nodeValue + "',");

					} else {
						NodeList ndListTemp = nodeListTemp.item(j)
								.getChildNodes();
						for (int k = 0; k < ndListTemp.getLength(); k++) {

							NodeList ndTemp = ndListTemp.item(k)
									.getChildNodes();
							brandItem = new StringBuffer();
							brandItemValues = new StringBuffer();

							for (int l = 0; l < ndTemp.getLength(); l++) {
								String ndName = ndTemp.item(l).getNodeName()
										.toLowerCase();
								brandItem.append(ndName + ",");

								Node node = ndTemp.item(l).getFirstChild();
								String ndValue = "";
								if (null != node) {
									ndValue = node.getNodeValue().trim();
								}

								if (ndName.equals("brand_code")
										|| ndName.equals("brand_name")) {
									brandItemValues
											.append("'" + ndValue + "',");
								} else {
									if (null == ndValue || "".equals(ndValue)) {
										ndValue = "0";
									}
									brandItemValues.append(ndValue + ",");
								}
							}
							sqlStr.append(titleItem);
							sqlStr.append(brandItem + ";");

							sqlValues.append(titleItemValues);
							sqlValues.append(brandItemValues + ";");
						}
					}

				}

			}

			String title = sqlStr.toString().replace(",;", ";").split(";")[0];
			// log.info("Title >>: " + title);
			// log.info("---------------------------------");
			// log.info("Values >>: "
			// + sqlValues.toString().replace(",;", ";"));
			// log.info("---------------------------------");

			String[] values = sqlValues.toString().replace(",;", ";")
					.split(";");

			// 删除临时表中的数据
			int i = Executer
					.getInstance()
					.ExecUpdateSQL(
							deleteSql + " WHERE comcode='" + comCode + "'")
					.getAffectRowCount();
			// if (i > 0) {
			// System.out.println("清除临时表中的数据!");
			// }
			ECS_NAT_BPM_MonthPlanQueryInd.exeCall(comCode, title, values);

		} else {
			System.out.println(comCode + "数据内容为空!!!");
		}

	}

	private static void exeCall(String comCode, String title, String[] values)
			throws Exception {
		String procName = "ws_saveMonthPlanQueryInd";
		SqlParam[] params = getSqlParams(3);
		params[0].setAll(0, Types.VARCHAR, comCode);
		params[2].setAll(1, Types.VARCHAR, "");

		int rowCount = values.length;
		int count = 0;
		// for (int i = 0; i < rowCount; i++) {
		// String sql =
		// "INSERT INTO ECS_MonthPlanQueryInd_Temp(createtime,comcode,"
		// + title
		// + ") VALUES (CURRENT TIMESTAMP,'"
		// + comCode
		// + "',"
		// + values[i] + ")";
		//
		// params[1].setAll(0, Types.VARCHAR, sql);
		// SpRuturn call = ProcedureLauncher.execute(procName, params);
		// String msg = call.getOutputParam(0);
		//
		// if ("0".equals(msg)) {
		// count++;
		// } else {
		// System.out.println(comCode + "插入数据库操作失败!!! SQLCODE=" + msg);
		// }
		// }
		String strSQL = "INSERT INTO ECS_MonthPlanQueryInd_Temp(createtime,comcode,"
				+ title + ") VALUES";
		String sql = "";
		for (int i = 0; i < rowCount; i++) {
			sql += " (CURRENT TIMESTAMP,'" + comCode + "'," + values[i];
			if (i == rowCount - 1) {
				sql += ")";
			} else {
				sql += "),";
			}
			count++;
		}
		// System.out.println(strSQL + sql);
		params[1].setAll(0, Types.VARCHAR, strSQL + sql);
		SqlReturn call = ProcedureLauncher.execute(procName, params);
		String msg = call.getOutputParam(0);

		if (!"0".equals(msg)) {
			System.out.println(count + " [" + comCode
					+ "] 插入数据库操作失败!!! SQLCODE=" + msg);
		}

	}

	private static SqlParam[] getSqlParams(int paramsCount) {
		int length = paramsCount;
		SqlParam[] params = new SqlParam[length];
		// 0表示输入参数,1表示输出参数
		for (int i = 0; i < length; i++) {
			params[i] = new SqlParam();
		}
		return params;
	}

}
