package com.qmx.xmlutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.qmx.dbutils.DBInformation;

public class XmlUtils {
	/**
	 * xml转字符串
	 */
	public static String xmlConvertString(Document doc) {

		String xmlStr = null;
		try {
			// XML转字符串
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));
			xmlStr = bos.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlStr;
	}

	/**
	 * string转xml
	 */
	public static Document stringConvertXML(String data, String code) {

		StringBuilder sXML = new StringBuilder(code);
		sXML.append(data);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			InputStream is = new ByteArrayInputStream(sXML.toString().getBytes(
					"utf-8"));
			doc = dbf.newDocumentBuilder().parse(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static Document XMLParse(String xmlFilePath) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(xmlFilePath);
			doc = db.parse(file);
		} catch (Exception localException) {
		}
		return doc;
	}

	public static void XMLreader(Document doc) {
		try {
			System.out.println("haha");
			NodeList nl = doc.getElementsByTagName("customer");
			int len = nl.getLength();
			for (int i = 0; i < len; i++) {
				Element customer = (Element) nl.item(i);
				Node id = customer.getElementsByTagName("id").item(0);
				Node name = customer.getElementsByTagName("name").item(0);
				Node address = customer.getElementsByTagName("address").item(0);
				String newid = id.getFirstChild().getNodeValue();
				String newname = name.getFirstChild().getNodeValue();
				String newAddress = address.getFirstChild().getNodeValue();
				System.out.print("id号:");
				System.out.println(newid);
				System.out.print("名字:");
				System.out.println(newname);
				System.out.print("地址");
				System.out.println(newAddress);
			}
		} catch (Exception localException) {
		}
	}

	public void add(Document doc) {
		try {
			Element customer = doc.createElement("customer");
			Element id = doc.createElement("id");
			Element name = doc.createElement("name");
			Element address = doc.createElement("address");

			Text textId = doc.createTextNode("005");
			Text textName = doc.createTextNode("zhushudong");
			Text textAddress = doc.createTextNode("haerbin");

			id.appendChild(textId);
			name.appendChild(textName);
			address.appendChild(textAddress);

			customer.appendChild(id);
			customer.appendChild(name);
			customer.appendChild(address);

			Element root = doc.getDocumentElement();
			root.appendChild(customer);
		} catch (Exception localException) {
		}
	}

	public void delete(Document doc, int i) {
		NodeList nl = doc.getElementsByTagName("customer");
		Node nodeDel = nl.item(i);
		nodeDel.getParentNode().removeChild(nodeDel);
	}

	public void change(Document doc, int i, String attr, String newValue) {
		NodeList nl = doc.getElementsByTagName("customer");
		Element cha = (Element) nl.item(i);
		Node nodeForCha = cha.getElementsByTagName(attr).item(0);
		nodeForCha.getFirstChild().setNodeValue(newValue);
	}

	public static void ObjectToXML(Document doc)
			throws TransformerConfigurationException {
		try {
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File("newXMLfile.xml"));

			TransformerFactory tff = TransformerFactory.newInstance();

			Transformer tf = tff.newTransformer();

			tf.transform(source, result);
		} catch (Exception localException) {
		}
	}

	public static HashMap parse(String xmlFilePath) {
		HashMap hConns = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(xmlFilePath));
			Element root = doc.getDocumentElement();

			hConns = new HashMap();

			for (Node database = root.getFirstChild(); database != null; database = database
					.getNextSibling()) {
				if ((database instanceof Element)) {
					DBInformation cb = new DBInformation();
					for (Node n = database.getFirstChild(); n != null; n = n
							.getNextSibling()) {
						if ((n instanceof Element)) {
							if (n.getNodeName().equals("id"))
								cb.setDatasource(n.getTextContent().trim());
							else if (n.getNodeName().equals("driver"))
								cb.setDriver(n.getTextContent().trim());
							else if (n.getNodeName().equals("url"))
								cb.setUrl(n.getTextContent().trim());
							else if (n.getNodeName().equals("username"))
								cb.setUsername(n.getTextContent().trim());
							else if (n.getNodeName().equals("password")) {
								cb.setPassword(n.getTextContent().trim());
							}
						}
					}

					hConns.put(cb.getDatasource(), cb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hConns;
	}

	public static synchronized Document newDocument() {
		Document doc = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = db.newDocument();
		} catch (Exception e) {
			System.out.println(e);
		}
		return doc;
	}

	public static synchronized Element createRootElement() {
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.newDocument();
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element getRootElement(String fileName) {
		if ((fileName == null) || (fileName.length() == 0))
			return null;
		try {
			Element rootElement = null;
			FileInputStream fis = new FileInputStream(fileName);
			rootElement = getRootElement(fis);
			fis.close();
			return rootElement;
		} catch (Exception e) {
		}
		return null;
	}

	public static synchronized Element getRootElement(InputStream is) {
		if (is == null) {
			return null;
		}
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(is);
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element getRootElement(InputSource is) {
		if (is == null) {
			return null;
		}
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(is);
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element[] getChildElements(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if ((node instanceof Element)) {
				childs.add((Element) node);
			}
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	public static synchronized Element[] getChildElements(Element element,
			String childName) {
		if ((element == null) || (childName == null)
				|| (childName.length() == 0)) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (((node instanceof Element))
					&& (node.getNodeName().equals(childName))) {
				childs.add((Element) node);
			}
		}

		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	public static synchronized Node[] getChildNodes(Node node) {
		if (node == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			childs.add((Element) n);
		}
		Node[] childNodes = new Element[childs.size()];
		childs.toArray(childNodes);
		return childNodes;
	}

	public static synchronized Element getChildElement(Element element,
			String childName) {
		if ((element == null) || (childName == null)
				|| (childName.length() == 0)) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (((node instanceof Element))
					&& (node.getNodeName().equals(childName))) {
				childs = (Element) node;
				break;
			}
		}

		return childs;
	}

	public static synchronized Element getChildElement(Element element) {
		if (element == null) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if ((node instanceof Element)) {
				childs = (Element) node;
				break;
			}
		}
		return childs;
	}

	public static synchronized String[] getElenentValues(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if ((node instanceof Text)) {
				childs.add(node.getNodeValue());
			}
		}
		String[] values = new String[childs.size()];
		childs.toArray(values);
		return values;
	}

	public static synchronized String getElenentValue(Element element) {
		if (element == null) {
			return null;
		}
		String retnStr = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if ((node instanceof Text)) {
				String str = node.getNodeValue();
				if ((str != null) && (str.length() != 0)
						&& (str.trim().length() != 0)) {
					retnStr = str;
					break;
				}
			}
		}
		return retnStr;
	}

	public static synchronized Element findElementByName(Element e, String name) {
		if ((e == null) || (name == null) || (name.length() == 0)) {
			return null;
		}
		String nodename = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			nodename = childs[i].getNodeName();
			if (name.equals(nodename)) {
				return childs[i];
			}
		}
		for (int i = 0; i < childs.length; i++) {
			Element retn = findElementByName(childs[i], name);
			if (retn != null) {
				return retn;
			}
		}
		return null;
	}

	public static synchronized Element findElementByAttr(Element e,
			String attrName, String attrVal) {
		return findElementByAttr(e, attrName, attrVal, true);
	}

	public static synchronized Element findElementByAttr(Element e,
			String attrName, String attrVal, boolean dept) {
		if ((e == null) || (attrName == null) || (attrName.length() == 0)
				|| (attrVal == null) || (attrVal.length() == 0)) {
			return null;
		}
		String tmpValue = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			tmpValue = childs[i].getAttribute(attrName);
			if (attrVal.equals(tmpValue)) {
				return childs[i];
			}
		}
		if (dept) {
			for (int i = 0; i < childs.length; i++) {
				Element retn = findElementByAttr(childs[i], attrName, attrVal);
				if (retn != null) {
					return retn;
				}
			}
		}
		return null;
	}

	public static synchronized String formatXml(Element e) {
		return formatXml(e, 0);
	}

	public static synchronized String formatXml(Element e, int indent) {
		indent++;
		for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
			appendIndent(e, n, indent);
			if (!n.getNodeName().equals("#text")) {
				formatXml((Element) n, indent);
			}
		}
		indent--;
		appendIndent(e, indent);
		return e.toString();
	}

	private static synchronized void appendIndent(Element e, Node pos,
			int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.insertBefore(doc.createTextNode("\n"), pos);
		}
		for (int i = 0; i < indent; i++)
			if (i == 0)
				e.insertBefore(doc.createTextNode("\n\t"), pos);
			else
				e.insertBefore(doc.createTextNode("\t"), pos);
	}

	private static synchronized void appendIndent(Element e, int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.appendChild(doc.createTextNode("\n"));
		}
		for (int i = 0; i < indent; i++)
			if (i == 0)
				e.appendChild(doc.createTextNode("\n\t"));
			else
				e.appendChild(doc.createTextNode("\t"));
	}

	public static synchronized void setAttribute(Element e, String name,
			String value) {
		if ((e == null) || (name == null) || (name.length() == 0)
				|| (value == null) || (value.length() == 0)) {
			return;
		}
		e.setAttribute(name, value);
	}

	public static synchronized String getAttribute(Element e, String name) {
		return getAttribute(e, name, null);
	}

	public static synchronized String getAttribute(Element e, String name,
			String defval) {
		if ((e == null) || (name == null) || (name.length() == 0)) {
			return defval;
		}
		return e.getAttribute(name);
	}

	public void transformerWrite(Element doc, String filename) throws Exception {
		DOMSource doms = new DOMSource(doc);
		File f = new File(filename);
		StreamResult sr = new StreamResult(f);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, File file) throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(file);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, OutputStream outstream)
			throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outstream);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, Writer outwriter)
			throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outwriter);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(DOMSource doms, StreamResult sr)
			throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty("encoding", "GBK");
		t.transform(doms, sr);
	}
}