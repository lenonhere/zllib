package com.ws.common;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qmx.dateutils.DateUtils;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.util.OptionUtils;

public class ImportDateFromGJ {
	static Log logger = LogFactory.getLog(ImportDateFromGJ.class);

	static private ImportDateFromGJ instance; // 唯一实例

	public boolean alreadyInit = false; // 控制是否初始化成功
	public boolean importProtocol = false;

	static synchronized public ImportDateFromGJ getInstance() {
		if (instance == null) {
			instance = new ImportDateFromGJ();
		}
		return instance;
	}

	/**
	 * 导入协议
	 *
	 * @author 刘恒 2009-12-9
	 * @param xmlstr
	 * @param importdate
	 * @throws Exception
	 */
	public static void HalfProtocolImport(String xmlstr, String importdate,
			String begDate, String endDate) throws Exception {
		String Sql = "";
		String SqlValue = "";
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(xmlstr);
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);
			Element root = document.getDocumentElement();
			NodeList ndblist = root.getChildNodes();
			// Node nodetemp;
			// Executer.getInstance().ExecUpdateSQL("delete from i_BifSales
			// where report_date='" +report_date+"'");
			for (int i = 0; i < ndblist.getLength(); i++) {
				String tablename = ndblist.item(i).getNodeName();
				tablename = tablename.toLowerCase();
				// delete from NBADV.I_XYORDER@
				// delete from NBADV.I_XYORDERPRO@
				// delete from NBADV.I_XYORDPROTEMP@
				// c_xyordpr_final 协议明细 -> i_XYORDERPRO
				// c_xyorder 协议主表 -> i_XYORDER
				// c_xyorder2 作废协议表
				if ("c_xyordpr_final".equals(tablename)
						|| "c_xyorder".equals(tablename)
						|| "c_xyorder2".equals(tablename)) {
					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();
						for (int k = 0; k < ndblisttemp.item(j).getChildNodes()
								.getLength(); k++) {
							if (ndblisttemp.item(j).getChildNodes().item(k)
									.getNodeType() == 3) {
								if (Sql.equals("")) {
									Sql = nodeName;
									SqlValue = "'"
											+ ndblisttemp.item(j)
													.getChildNodes().item(k)
													.getNodeValue() + "'";
								} else {
									Sql = Sql + "," + nodeName;
									SqlValue = SqlValue
											+ ",'"
											+ ndblisttemp.item(j)
													.getChildNodes().item(k)
													.getNodeValue() + "'";
								}
							}
						}
					}
				}
				if (!Sql.equals("")) {
					if ("c_xyordpr_final".equals(tablename)) {
						Sql = "insert into i_XYORDERPRO_temp (" + Sql
								+ ") values (" + SqlValue + ")";
					} else {
						Sql = "insert into i_XYORDER_temp (" + Sql
								+ ") values (" + SqlValue + ")";
					}

					// logger.debug("tablename="+tablename+" :"+Sql);
					Executer.getInstance().ExecUpdateSQL(Sql);
					Sql = "";
					SqlValue = "";
				}
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 导入协议
	 *
	 * @author 刘恒 2009-12-9
	 * @param begDate
	 * @param endDate
	 * @return
	 */
	public boolean importXYOrder(String begDate, String endDate) {
		try {
			// Date beg = MethodFactory.parseDate(begDate, "yyyy-MM-dd");
			// Date end = MethodFactory.parseDate(endDate, "yyyy-MM-dd");
			// String opname = "GetCigaXYDataForJAVA";
			// String outstr = this.getXMLStr(opname, begDate + " 00:00:00",
			// endDate + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void ContractStateImport(String xmlstr) throws Exception {

		String Sql = "";
		String SqlValue = "";

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(xmlstr);
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);
			Element root = document.getDocumentElement();
			NodeList ndblist = root.getChildNodes();
			// Node nodetemp;
			// Executer.getInstance().ExecUpdateSQL("delete from i_BifSales
			// where report_date='" +report_date+"'");
			for (int i = 0; i < ndblist.getLength(); i++) {
				String tablename = ndblist.item(i).getNodeName();
				tablename = tablename.toLowerCase();
				if ("c_order".equals(tablename)) {
					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("list_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("smem_id")
								|| nodeName.equals("sname")
								|| nodeName.equals("bmem_id")
								|| nodeName.equals("bname")
								|| nodeName.equals("order_date")
								|| nodeName.equals("deli_date")
								|| nodeName.equals("deli_date_s")
								|| nodeName.equals("deli_date_e")
								|| nodeName.equals("settleway")
								|| nodeName.equals("tran_type")
								|| nodeName.equals("dest")
								|| nodeName.equals("sbehalf")
								|| nodeName.equals("bbehalf")
								|| nodeName.equals("mbehalf")
								|| nodeName.equals("tcomment")
								|| nodeName.equals("sbank_id")
								|| nodeName.equals("sbank")
								|| nodeName.equals("bbank_id")
								|| nodeName.equals("bbank")
								|| nodeName.equals("kind")
								|| nodeName.equals("pkind")
								|| nodeName.equals("hasright")
								|| nodeName.equals("charg_date")
								|| nodeName.equals("qtotal")
								|| nodeName.equals("parent_id")
								|| nodeName.equals("snode")
								|| nodeName.equals("bnode")
								|| nodeName.equals("dest_id")
								|| nodeName.equals("subkind")
								|| nodeName.equals("consign")
								|| nodeName.equals("consign_id")
								|| nodeName.equals("iname")
								|| nodeName.equals("stax")
								|| nodeName.equals("btax")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {
										Sql = Sql + "," + nodeName;
										SqlValue = SqlValue
												+ ",'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									}
								}
							}

						}
					}

				} else if ("c_ordpr".equals(tablename)) {

					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();

						if (nodeName.equals("list_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("id")
								|| nodeName.equals("product_id")
								|| nodeName.equals("quantity")
								|| nodeName.equals("price")
								|| nodeName.equals("plate")
								|| nodeName.equals("supplier_id")
								|| nodeName.equals("supplier")
								|| nodeName.equals("region")
								|| nodeName.equals("type")
								|| nodeName.equals("tlen")
								|| nodeName.equals("package_")
								|| nodeName.equals("class")
								|| nodeName.equals("mplate")
								|| nodeName.equals("is_famous")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {
										Sql = Sql + "," + nodeName;
										SqlValue = SqlValue
												+ ",'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									}
								}
							}

						}
					}

				}

				if (!Sql.equals("")) {
					if ("c_order".equals(tablename)) {
						Sql = "insert into I_ORDERTEMP (" + Sql + ") values ("
								+ SqlValue + ")";
					} else if ("c_ordpr".equals(tablename)) {
						Sql = "insert into I_ORDERPROTEMP (" + Sql
								+ ") values (" + SqlValue + ")";
					}

					// logger.debug(Sql);

					Executer.getInstance().ExecUpdateSQL(Sql);
					Sql = "";
					SqlValue = "";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}

	}

	public void HalfProtocolImport(String xmlstr) throws Exception {

		String Sql = "";
		String SqlValue = "";

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(xmlstr);
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);
			Element root = document.getDocumentElement();
			NodeList ndblist = root.getChildNodes();
			// Node nodetemp;
			// Executer.getInstance().ExecUpdateSQL("delete from i_BifSales
			// where report_date='" +report_date+"'");
			for (int i = 0; i < ndblist.getLength(); i++) {
				String tablename = ndblist.item(i).getNodeName();
				tablename = tablename.toLowerCase();
				if ("c_xyordpr_final".equals(tablename)) {
					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("list_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("id")
								|| nodeName.equals("product_id")
								|| nodeName.equals("quantity")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {
										Sql = Sql + "," + nodeName;
										SqlValue = SqlValue
												+ ",'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									}
								}
							}

						}
					}

				}

				if (!Sql.equals("")) {
					if ("c_xyordpr_final".equals(tablename)) {
						Sql = "insert into I_XYORDPROTEMP (" + Sql
								+ ") values (" + SqlValue + ")";
					}

					// logger.debug(Sql);

					Executer.getInstance().ExecUpdateSQL(Sql);
					Sql = "";
					SqlValue = "";
				}
			}

		} catch (Exception e) {
			throw e;
		}

	}

	public String getXMLStr(String opName, String stamp_s, String stamp_e)
			throws ServiceException, MalformedURLException, RemoteException {
		// String
		// endpoint="http://219.142.71.142/CigaTransService/CigaData.asmx";
		String endpoint = "http://10.1.93.24/CigaTransService/CigaData.asmx";
		String TokenRing = "C8AF77E83B83F3D848525A60DDF9C6CF42C05DDE";
		String res = null;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("http://localhost/CigaTransService/"
				+ opName + "/SU", opName));
		call.addParameter("TokenRing",
				org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		call.addParameter("stamp_s",
				org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		call.addParameter("stamp_e",
				org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("http://localhost/CigaTransService/" + opName
				+ "/Rpc");

		res = (String) call
				.invoke(new Object[] { TokenRing, stamp_s, stamp_e });
		return res;
	}

	/**
	 * 导回国家局合同
	 *
	 * @author: 朱忠南
	 * @throws Exception
	 */
	public void execute(String startdate, String enddate) throws Exception {

		logger.debug("this.alreadyInit=" + this.alreadyInit);

		if (this.alreadyInit == false) {
			this.alreadyInit = true;

			try {
				String sql = "";
				sql = "DELETE FROM I_ORDERTEMP";
				Executer.getInstance().ExecUpdateSQL(sql);
				logger.debug("删除I_ORDERTEMP成功");
				sql = "DELETE FROM I_ORDERPROTEMP";
				Executer.getInstance().ExecUpdateSQL(sql);
				logger.debug("删除I_ORDERPROTEMP成功");

				// String license="4BCB97659ACD632B085FFBCB16BAF2A01CAA7F81";
				// String startdate =MethodFactory.getDate(-5);
				// String startdate =MethodFactory.getDate(-1).replace('/','-');
				// String enddate = MethodFactory.getDate(0).replace('/','-');

				String predate = OptionUtils.getDateByOffset(startdate, 0);
				String nextdate = OptionUtils.getDateByOffset(startdate, 1);// 开始日期的下一天

				Date start = DateUtils.parseDate(predate, "yyyy-MM-dd");
				Date end = DateUtils.parseDate(enddate, "yyyy-MM-dd");
				// InportDateFromZj.Import(license,startdate, enddate);
				String opname = "GetCigaHTDataForJAVA";
				logger.debug(end.compareTo(start) == -1);
				while (end.compareTo(start) != -1) {

					String outstr = this.getXMLStr(opname, predate
							+ " 00:00:00", nextdate + " 00:00:00");
					logger.debug("outstr=" + outstr);
					this.ContractStateImport(outstr);
					logger.debug(predate + "合同定单数据导入临时表成功！");

					// String Sql="CALL
					// O_ContractStateImport('"+predate+"','"+nextdate+"',?)";
					// Executer.getInstance().(Sql);

					predate = OptionUtils.getDateByOffset(predate, 1);
					nextdate = OptionUtils.getDateByOffset(nextdate, 1);
					start = DateUtils.parseDate(predate, "yyyy-MM-dd");

				}

				CallHelper helper = new CallHelper("contractstateimport");
				helper.setParam("startDate", startdate);
				helper.setParam("endDate", enddate);
				helper.execute();
				if (helper.getSqlCode() != 0 && helper.getSqlCode() != 100)
					throw new Exception(helper.getOutput(0) + ","
							+ helper.getErrorMessage());
				// 有一个100错误，不知道为什么在存储过程中过滤掉了

			} catch (Exception e) {
				logger.debug("导回国家局合同发现错误：" + e);
				e.printStackTrace();
				throw e;
			} finally {
				this.alreadyInit = false;
			}
		}
	}

	/**
	 * 导回国家局准运证
	 */
	public void navicert(String startdate, String enddate) throws Exception {

		logger.debug("this.alreadyInit=" + this.alreadyInit);

		if (this.alreadyInit == false) {
			this.alreadyInit = true;

			try {
				String sql = "";
				sql = "DELETE FROM I_navicertTemp";
				Executer.getInstance().ExecUpdateSQL(sql);
				logger.debug("删除I_navicertTemp成功");
				sql = "DELETE FROM I_navicertTemp2";
				Executer.getInstance().ExecUpdateSQL(sql);
				logger.debug("删除I_navicertTemp2成功");
				sql = "DELETE FROM I_navicertPROTemp";
				Executer.getInstance().ExecUpdateSQL(sql);
				logger.debug("删除I_navicertPROTemp成功");

				// String license="4BCB97659ACD632B085FFBCB16BAF2A01CAA7F81";
				// String startdate =MethodFactory.getDate(-5);
				// String startdate =MethodFactory.getDate(-1).replace('/','-');
				// String enddate = MethodFactory.getDate(0).replace('/','-');

				String predate = OptionUtils.getDateByOffset(startdate, 0);
				String nextdate = OptionUtils.getDateByOffset(startdate, 1);// 开始日期的下一天

				Date start = DateUtils.parseDate(predate, "yyyy-MM-dd");
				Date end = DateUtils.parseDate(enddate, "yyyy-MM-dd");
				// InportDateFromZj.Import(license,startdate, enddate);
				String opname = "GetCigaZYZDataForNGForJAVA";
				logger.debug(end.compareTo(start) == -1);
				while (end.compareTo(start) != -1) {

					String outstr = this.getXMLStr(opname, predate
							+ " 00:00:00", predate + " 02:00:00");
					logger.debug("outstr=" + outstr);
					this.NavicertStateImport(outstr);

					String outstr1 = this.getXMLStr(opname, predate
							+ " 02:00:01", predate + " 04:00:00");
					logger.debug("outstr1=" + outstr1);
					this.NavicertStateImport(outstr1);

					String outstr2 = this.getXMLStr(opname, predate
							+ " 04:00:01", predate + " 06:00:00");
					logger.debug("outstr2=" + outstr2);
					this.NavicertStateImport(outstr2);

					String outstr3 = this.getXMLStr(opname, predate
							+ " 06:00:01", predate + " 08:00:00");
					logger.debug("outstr3=" + outstr3);
					this.NavicertStateImport(outstr3);

					String outstr4 = this.getXMLStr(opname, predate
							+ " 08:00:01", predate + " 10:00:00");
					logger.debug("outstr4=" + outstr4);
					this.NavicertStateImport(outstr4);

					String outstr5 = this.getXMLStr(opname, predate
							+ " 10:00:01", predate + " 12:00:00");
					logger.debug("outstr5=" + outstr5);
					this.NavicertStateImport(outstr5);

					String outstr6 = this.getXMLStr(opname, predate
							+ " 12:00:01", predate + " 14:00:00");
					logger.debug("outstr6=" + outstr6);
					this.NavicertStateImport(outstr6);

					String outstr7 = this.getXMLStr(opname, predate
							+ " 14:00:01", predate + " 16:00:00");
					logger.debug("outstr7=" + outstr7);
					this.NavicertStateImport(outstr7);

					String outstr8 = this.getXMLStr(opname, predate
							+ " 16:00:01", predate + " 18:00:00");
					logger.debug("outstr8=" + outstr8);
					this.NavicertStateImport(outstr8);

					String outstr9 = this.getXMLStr(opname, predate
							+ " 18:00:01", predate + " 20:00:00");
					logger.debug("outstr9=" + outstr9);
					this.NavicertStateImport(outstr9);

					String outstr10 = this.getXMLStr(opname, predate
							+ " 20:00:01", predate + " 22:00:00");
					logger.debug("outstr10=" + outstr10);
					this.NavicertStateImport(outstr10);

					String outstr11 = this.getXMLStr(opname, predate
							+ " 22:00:01", nextdate + " 00:00:00");
					logger.debug("outstr11=" + outstr11);
					this.NavicertStateImport(outstr11);

					logger.debug(predate + "准运证数据导入临时表成功！");

					predate = OptionUtils.getDateByOffset(predate, 1);
					nextdate = OptionUtils.getDateByOffset(nextdate, 1);
					start = DateUtils.parseDate(predate, "yyyy-MM-dd");

				}

				CallHelper helper = new CallHelper("navicertstateimport");
				helper.setParam("startDate", startdate);
				helper.setParam("endDate", enddate);
				helper.execute();
				if (helper.getSqlCode() != 0 && helper.getSqlCode() != 100)
					throw new Exception(helper.getOutput(0) + ","
							+ helper.getErrorMessage());

			} catch (Exception e) {
				logger.debug("导回国家局准运证发现错误：" + e);
				e.printStackTrace();
				throw e;
			} finally {
				this.alreadyInit = false;
			}
		}
	}

	public void NavicertStateImport(String xmlstr) throws Exception {

		String Sql = "";
		String SqlValue = "";

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(xmlstr);
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);
			Element root = document.getDocumentElement();
			NodeList ndblist = root.getChildNodes();
			// Node nodetemp;
			// Executer.getInstance().ExecUpdateSQL("delete from i_BifSales
			// where report_date='" +report_date+"'");
			for (int i = 0; i < ndblist.getLength(); i++) {
				String tablename = ndblist.item(i).getNodeName();
				tablename = tablename.toLowerCase();
				if ("zc_perm".equals(tablename)) {
					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("list_id")
								|| nodeName.equals("perm_id")
								|| nodeName.equals("print_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("scan_id")
								|| nodeName.equals("order_pkind")
								|| nodeName.equals("order_kind")
								|| nodeName.equals("order_subkind")
								|| nodeName.equals("deli_date")
								|| nodeName.equals("sname")
								|| nodeName.equals("bmem_id")
								|| nodeName.equals("bname")
								|| nodeName.equals("perm_date")
								|| nodeName.equals("perm_days")
								|| nodeName.equals("perm_limit_date")
								|| nodeName.equals("tran_type")
								|| nodeName.equals("tran_tool")
								|| nodeName.equals("consign")
								|| nodeName.equals("dest")
								|| nodeName.equals("jbr")
								|| nodeName.equals("pzr")
								|| nodeName.equals("zzr")
								|| nodeName.equals("lzr")
								|| nodeName.equals("quantity")
								|| nodeName.equals("made_in")
								|| nodeName.equals("made_name")
								|| nodeName.equals("cancel")
								|| nodeName.equals("cancel_note")
								|| nodeName.equals("cancel_person")
								|| nodeName.equals("cancel_master")
								|| nodeName.equals("come_type")
								|| nodeName.equals("come_date")
								|| nodeName.equals("come_person")
								|| nodeName.equals("come_master")
								|| nodeName.equals("come_note")
								|| nodeName.equals("canot_note")
								|| nodeName.equals("canot_person")
								|| nodeName.equals("no_perm")
								|| nodeName.equals("no_perm_note")
								|| nodeName.equals("perm_need_dest")
								|| nodeName.equals("approve_id")
								|| nodeName.equals("approve_name")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {
										Sql = Sql + "," + nodeName;
										if (nodeName.equals("canot_note")
												&& ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
														.length() > 30) {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
															.substring(0, 30)
													+ "'";
										} else {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
													+ "'";
										}

									}
								}
							}

						}
					}

				} else if ("zc_perm2".equals(tablename)) {
					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("list_id")
								|| nodeName.equals("perm_id")
								|| nodeName.equals("print_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("scan_id")
								|| nodeName.equals("order_pkind")
								|| nodeName.equals("order_kind")
								|| nodeName.equals("order_subkind")
								|| nodeName.equals("deli_date")
								|| nodeName.equals("sname")
								|| nodeName.equals("bmem_id")
								|| nodeName.equals("bname")
								|| nodeName.equals("perm_date")
								|| nodeName.equals("perm_days")
								|| nodeName.equals("perm_limit_date")
								|| nodeName.equals("tran_type")
								|| nodeName.equals("tran_tool")
								|| nodeName.equals("consign")
								|| nodeName.equals("dest")
								|| nodeName.equals("jbr")
								|| nodeName.equals("pzr")
								|| nodeName.equals("zzr")
								|| nodeName.equals("lzr")
								|| nodeName.equals("quantity")
								|| nodeName.equals("made_in")
								|| nodeName.equals("made_name")
								|| nodeName.equals("cancel")
								|| nodeName.equals("cancel_note")
								|| nodeName.equals("cancel_person")
								|| nodeName.equals("cancel_master")
								|| nodeName.equals("come_type")
								|| nodeName.equals("come_date")
								|| nodeName.equals("come_person")
								|| nodeName.equals("come_master")
								|| nodeName.equals("come_note")
								|| nodeName.equals("canot_note")
								|| nodeName.equals("canot_person")
								|| nodeName.equals("no_perm")
								|| nodeName.equals("no_perm_note")
								|| nodeName.equals("perm_need_dest")
								|| nodeName.equals("approve_id")
								|| nodeName.equals("approve_name")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {

										Sql = Sql + "," + nodeName;
										if (nodeName.equals("canot_note")
												&& ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
														.length() > 30) {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
															.substring(0, 30)
													+ "'";
										} else {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
													+ "'";
										}

									}
								}
							}

						}
					}

				} else if ("zc_perms".equals(tablename)) {

					NodeList ndblisttemp = ndblist.item(i).getChildNodes();
					for (int j = 0; j < ndblisttemp.getLength(); j++) {
						String nodeName = ndblisttemp.item(j).getNodeName();
						nodeName = nodeName.toLowerCase();

						if (nodeName.equals("list_id")
								|| nodeName.equals("perm_id")
								|| nodeName.equals("order_id")
								|| nodeName.equals("id")
								|| nodeName.equals("quantity")
								|| nodeName.equals("price")
								|| nodeName.equals("plate")
								|| nodeName.equals("supplier")
								|| nodeName.equals("region")
								|| nodeName.equals("p_type")
								|| nodeName.equals("tlen")
								|| nodeName.equals("package_")
								|| nodeName.equals("class")
								|| nodeName.equals("mplate")
								|| nodeName.equals("is_famous")
								|| nodeName.equals("product_id")) {
							for (int k = 0; k < ndblisttemp.item(j)
									.getChildNodes().getLength(); k++) {
								if (ndblisttemp.item(j).getChildNodes().item(k)
										.getNodeType() == 3) {
									if (Sql.equals("")) {
										Sql = nodeName;
										SqlValue = "'"
												+ ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
												+ "'";
									} else {
										Sql = Sql + "," + nodeName;
										if (nodeName.equals("canot_note")
												&& ndblisttemp.item(j)
														.getChildNodes()
														.item(k).getNodeValue()
														.length() > 30) {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
															.substring(0, 30)
													+ "'";
										} else {
											SqlValue = SqlValue
													+ ",'"
													+ ndblisttemp.item(j)
															.getChildNodes()
															.item(k)
															.getNodeValue()
													+ "'";
										}

									}
								}
							}

						}
					}

				}

				if (!Sql.equals("")) {
					if ("zc_perm".equals(tablename)) {
						Sql = "insert into I_navicertTemp (" + Sql
								+ ") values (" + SqlValue + ")";
					} else if ("zc_perm2".equals(tablename)) {
						Sql = "insert into I_navicertTemp2 (" + Sql
								+ ") values (" + SqlValue + ")";
					} else if ("zc_perms".equals(tablename)) {
						Sql = "insert into I_navicertPROTemp (" + Sql
								+ ") values (" + SqlValue + ")";
					}

					logger.debug(Sql);

					Executer.getInstance().ExecUpdateSQL(Sql);
					Sql = "";
					SqlValue = "";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}

	}
}
