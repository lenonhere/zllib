package com.ws;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class MakeXML {

	private static final Log logger = LogFactory.getLog(MakeXML.class);

	public String getContactsStr(List contactList) {
		String headStr = "<?xml version='1.0' encoding='GBK'?> \n ";
		StringBuffer sbContactStr = new StringBuffer();
		sbContactStr.append(headStr);
		sbContactStr.append("\n <contacts>");
		try {

			BasicDynaBean contactObj = null;
			for (int m = 0; m < contactList.size(); m++) {
				contactObj = (BasicDynaBean) (contactList.get(m));
				sbContactStr.append(this.makeContactsStr(contactObj));
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		sbContactStr.append("\n </contacts>");
		// logger.debug(sbContactStr.toString());
		return sbContactStr.toString();
	}

	public String getContactsDetailStr(List contactDetailList) {
		String headStr = "<?xml version='1.0' encoding='GBK'?> \n ";
		StringBuffer sbContactDetailStr = new StringBuffer();
		sbContactDetailStr.append(headStr);
		sbContactDetailStr.append("\n <contactdetails>");
		try {

			BasicDynaBean contactDetailObj = null;
			for (int m = 0; m < contactDetailList.size(); m++) {
				contactDetailObj = (BasicDynaBean) (contactDetailList.get(m));
				sbContactDetailStr.append(this
						.makeContactsDetailStr(contactDetailObj));
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		sbContactDetailStr.append("\n </contactdetails>");
		// logger.debug(sbContactDetailStr.toString());
		return sbContactDetailStr.toString();
	}

	/*
	 * provname contractcode sycompany_id shipping_date tcreatetime scanout_date
	 * companyname transdays if_sure shipping_date arrival_date moveoutid
	 * transcorp
	 */
	private String makeContactsStr(BasicDynaBean dynaBean) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("\n <contact>");
			sb.append("\n <contractcode>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"contractcode") + "</contractcode>");
			sb.append("\n <sycompany_id>"
					+ (Integer) PropertyUtils.getProperty(dynaBean,
							"sycompany_id") + "</sycompany_id>");
			sb.append("\n <provname>"
					+ (String) PropertyUtils.getProperty(dynaBean, "provname")
					+ "</provname>");
			sb.append("\n <tcreatetime>"
					+ (java.sql.Timestamp) PropertyUtils.getProperty(dynaBean,
							"tcreatetime") + "</tcreatetime>");
			sb.append("\n <scanout_date>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"scanout_date") + "</scanout_date>");
			sb.append("\n <companyname>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"companyname") + "</companyname>");
			sb.append("\n <no1_company>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"no1_company") + "</no1_company>");
			sb.append("\n <transdays>"
					+ (String) PropertyUtils.getProperty(dynaBean, "transdays")
					+ "</transdays>");
			sb.append("\n <arrival_date>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"arrival_date") + "</arrival_date>");
			sb.append("\n <transcorp>"
					+ (String) PropertyUtils.getProperty(dynaBean, "transcorp")
					+ "</transcorp>");
			sb.append("\n <carno>"
					+ (String) PropertyUtils.getProperty(dynaBean, "carno")
					+ "</carno>");
			sb.append("\n <drivername>"
					+ (String) PropertyUtils
							.getProperty(dynaBean, "drivername")
					+ "</drivername>");
			sb.append("\n <phone>"
					+ (String) PropertyUtils.getProperty(dynaBean, "phone")
					+ "</phone>");
			sb.append("\n <revised_quantity>"
					+ (java.math.BigDecimal) PropertyUtils.getProperty(
							dynaBean, "revised_quantity")
					+ "</revised_quantity>");
			sb.append("\n <all_money>"
					+ (java.math.BigDecimal) PropertyUtils.getProperty(
							dynaBean, "all_money") + "</all_money>");
			sb.append("\n <status>"
					+ (String) PropertyUtils.getProperty(dynaBean, "status")
					+ "</status>");

			sb.append("\n </contact>");
		} catch (Exception e) {
			logger.error("", e);
		}
		return sb.toString().replaceAll("null", "").replaceAll("--", "");
	}

	/*
	 * contractcode tobaname companyname revised_quantity all_money
	 */
	private String makeContactsDetailStr(BasicDynaBean dynaBean) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("\n <contactdetail>");
			sb.append("\n <contractcode>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"contractcode") + "</contractcode>");
			sb.append("\n <tobaname>"
					+ (String) PropertyUtils.getProperty(dynaBean, "tobaname")
					+ "</tobaname>");
			sb.append("\n <no1_toba>"
					+ (String) PropertyUtils.getProperty(dynaBean, "no1_toba")
					+ "</no1_toba>");
			sb.append("\n <companyname>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"companyname") + "</companyname>");
			sb.append("\n <no1_company>"
					+ (String) PropertyUtils.getProperty(dynaBean,
							"no1_company") + "</no1_company>");
			sb.append("\n <revised_quantity>"
					+ (java.math.BigDecimal) PropertyUtils.getProperty(
							dynaBean, "revised_quantity")
					+ "</revised_quantity>");
			sb.append("\n <all_money>"
					+ (java.math.BigDecimal) PropertyUtils.getProperty(
							dynaBean, "all_money") + "</all_money>");
			sb.append("\n </contactdetail>");
		} catch (Exception e) {
			logger.error("", e);
		}
		return sb.toString().replaceAll("null", "").replaceAll("--", "");
	}

	public String makeXml(String rootName, List resultList, String[] resultNames) {

		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println(e);
			return "error";
		}
		Document doc = null;
		doc = db.newDocument();
		Element eRoot = doc.createElement(rootName);
		doc.appendChild(eRoot);
		for (int i = 0; i < resultList.size(); i++) {
			Element eData = doc.createElement("data-part");
			eRoot.appendChild(eData);
			BasicDynaBean bean = (BasicDynaBean) resultList.get(i);
			for (int j = 0; j < resultNames.length; j++) {
				Element element = doc.createElement(resultNames[j]);
				eData.appendChild(element);
				Text tnode = doc.createTextNode(String.valueOf(
						bean.get(resultNames[j])).replace("null", ""));
				element.appendChild(tnode);

			}
		}
		StringWriter sWriter = new StringWriter();
		try {
			// System.setProperty("javax.xml.transform.TransformerFactory",
			// "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
			TransformerFactory transfactory = TransformerFactory.newInstance();
			Transformer trans = transfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = // new StreamResult(
			// new FileOutputStream("c:\\ssprocessed.xml"));
			new StreamResult(sWriter);
			Properties properties = trans.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "GB2312");
			trans.setOutputProperties(properties);

			trans.transform(source, result);
		} catch (Exception e) {
			logger.error("", e);
		}
		return sWriter.getBuffer().toString();
	}
}
