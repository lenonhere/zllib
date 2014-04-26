package com.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QnyhAnswer implements ActionListener {
	private static final String filePath = "E:/logs/qnyh.xml";
	private static final String prompt = "关键字词之间用逗号分隔!";
	private JLabel keyJL;
	private JButton keyJB;
	private JScrollPane jScrollPane;
	private JTextArea answerJTA;
	private JButton addJB;
	private JButton updJB;
	private JButton delJB;
	private JTextField keyJTF;
	private static JPanel jp;

	public QnyhAnswer() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				createXMLFile(filePath);
			}
		} catch (IOException e) {
			System.out.println("创建文件 " + filePath + " 失败!!!");
			e.printStackTrace();
		}
		initGUI();
	}

	public static JPanel getJPanel() {
		if (null == jp) {
			new QnyhAnswer();
		}
		return jp;
	}

	private void initGUI() {
		try {
			jp = new JPanel();
			jp.setLayout(null);
			{
				keyJL = new JLabel();
				jp.add(keyJL);
				keyJL.setText("关键字词:");
				keyJL.setBounds(12, 16, 60, 26);
			}
			{
				keyJTF = new JTextField();
				jp.add(keyJTF);
				keyJTF.setText(prompt);
				keyJTF.setBounds(77, 17, 230, 26);
				keyJTF.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						keyJTF.setText("");
					}
				});
			}
			{
				keyJB = new JButton();
				jp.add(keyJB);
				keyJB.setText("\u67e5\u8be2");
				keyJB.setBounds(313, 17, 60, 26);
				keyJB.addActionListener(this);
			}
			{
				delJB = new JButton();
				jp.add(delJB);
				delJB.setText("\u5220\u9664");
				delJB.setBounds(313, 227, 60, 26);
				delJB.addActionListener(this);
			}
			{
				updJB = new JButton();
				jp.add(updJB);
				updJB.setText("\u4fee\u6539");
				updJB.setBounds(240, 227, 60, 26);
				updJB.addActionListener(this);
			}
			{
				addJB = new JButton();
				jp.add(addJB);
				addJB.setText("\u6dfb\u52a0");
				addJB.setBounds(167, 227, 60, 26);
				addJB.addActionListener(this);
			}
			{
				jScrollPane = new JScrollPane();
				jp.add(jScrollPane);
				jScrollPane.setBounds(12, 46, 361, 176);
				jScrollPane.getHorizontalScrollBar().setEnabled(true);
				jScrollPane.setPreferredSize(new java.awt.Dimension(0, 0));
				jScrollPane.setAutoscrolls(true);
				{
					answerJTA = new JTextArea();
					jScrollPane.setViewportView(answerJTA);
					answerJTA.setBounds(14, 48, 358, 173);
					answerJTA.setAutoscrolls(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (this.keyJB.equals(obj)) {
			String keyCode = this.keyJTF.getText().trim();
			if ((keyCode.equals(prompt)) || ("".equals(keyCode))) {
				JOptionPane.showMessageDialog(null, "请输入关键字或词后再点此按钮!");
			} else {
				String answer = exeQuery(filePath, keyCode);
				this.answerJTA.setText(answer);
			}
		}

		if (this.addJB.equals(obj)) {
			String key = this.keyJTF.getText().trim();
			String value = this.answerJTA.getText().trim();
			if (("".equals(key)) || ("".equals(value)))
				JOptionPane.showMessageDialog(null, "查询关键字词或内容不能为空请检查!");
			else {
				addElement(filePath, key, value);
			}
		}
		if (this.updJB.equals(obj)) {
			String key = this.keyJTF.getText().trim();
			String value = this.answerJTA.getText().trim();
			if (("".equals(key)) || ("".equals(value)))
				JOptionPane.showMessageDialog(null, "查询关键字词或内容不能为空请检查!");
			else {
				updElement(filePath, key, value);
			}
		}
		if (this.delJB.equals(obj)) {
			String key = this.keyJTF.getText().trim();
			if ("".equals(key))
				JOptionPane.showMessageDialog(null, "查询关键字词不能为空请检查!");
			else
				delElement(filePath, key);
		}
	}

	private static String exeQuery(String filePath, String keyCode) {
		String answerStr = "";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(filePath);

			Node rootEL = doc.getFirstChild();

			NodeList nodeList = rootEL.getChildNodes();
			int count = 0;
			for (int i = 0; i < nodeList.getLength(); i++) {
				NodeList nodeListItem = nodeList.item(i).getChildNodes();

				String keyValue = nodeListItem.item(0).getTextContent();
				String vValue = nodeListItem.item(1).getTextContent();

				int f = keyValue.indexOf(keyCode);

				if (f != -1) {
					count++;
					answerStr = answerStr
							+ count
							+ "●"
							+ keyValue
							+ "\r\n------------------------------------------------------------\r\n"
							+ vValue + "\r\n\r\n";
				}
			}

			if (answerStr == "")
				answerStr = "Sorry!没有找到相关信息!!!";
			else
				answerStr = "---------===共找到【" + count
						+ "】条相关记录===--------\r\n" + answerStr;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return answerStr;
	}

	private static Document getDoc() throws Exception {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.newDocument();
		return doc;
	}

	private static Document getDoc(String filePath) throws Exception {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		if ((filePath == null) || ("".equals(filePath)))
			doc = db.newDocument();
		else {
			doc = db.parse(filePath);
		}

		return doc;
	}

	/**
	 * 写入
	 *
	 * @param doc
	 * @param filePath
	 */
	private static void writeXMLFile(Document doc, String filePath) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource xmlSource = new DOMSource(doc);
			t.setOutputProperty("encoding", "UTF-8");
			t.setOutputProperty("indent", "true");
			PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
			StreamResult outputTarget = new StreamResult(pw);
			t.transform(xmlSource, outputTarget);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 初始化 E:/logs/qnyh.xml 文件
	 *
	 * @param filePath
	 */
	private static void createXMLFile(String filePath) {
		try {
			Document doc = getDoc();

			Element rootEL = doc.createElement("DataSets");
			Element itemEL = doc.createElement("DataSet");

			Element keyEL = doc.createElement("key");
			keyEL.setTextContent("关键字词:");
			Element valueEL = doc.createElement("value");
			valueEL.setTextContent("相关信息内容");

			itemEL.appendChild(keyEL);
			itemEL.appendChild(valueEL);
			rootEL.appendChild(itemEL);
			doc.appendChild(rootEL);

			writeXMLFile(doc, filePath);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 解析 E:/logs/qnyh.xml 文件
	 *
	 * @param filePath
	 */
	@SuppressWarnings("unused")
	private static void parseXMLFile(String filePath) {
		try {
			Document doc = getDoc(filePath);

			Node rootNode = doc.getFirstChild();
			NodeList nodeList = rootNode.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList item = node.getChildNodes();

				String keyValue = item.item(0).getTextContent();
				String vValue = item.item(1).getTextContent();
				System.out.println(keyValue
						+ "\r\n-------------------------------------\r\n"
						+ vValue + "\r\n\r\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 追加信息
	 *
	 * @param filePath
	 * @param key
	 * @param value
	 */
	private static void addElement(String filePath, String key, String value) {
		try {
			Document doc = getDoc(filePath);

			Node node = doc.getFirstChild();

			Element el = doc.createElement("DataSet");

			Element keyEL = doc.createElement("key");
			keyEL.setTextContent(key);
			Element valueEL = doc.createElement("value");
			valueEL.setTextContent(value);

			el.appendChild(keyEL);
			el.appendChild(valueEL);
			node.appendChild(el);

			writeXMLFile(doc, filePath);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 修改信息
	 *
	 * @param filePath
	 * @param key
	 * @param value
	 */
	private static void updElement(String filePath, String key, String value) {
		try {
			Document doc = getDoc(filePath);

			boolean f = false;
			Node rootNode = doc.getFirstChild();
			NodeList nodeList = rootNode.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList itemList = node.getChildNodes();

				String keyValue = itemList.item(0).getTextContent();
				// String vValue = itemList.item(1).getTextContent();

				if (keyValue.equals(key)) {
					itemList.item(1).setTextContent(value);
					f = true;
				}
			}
			System.out.println("update flag is " + f);
			if (f) {
				writeXMLFile(doc, filePath);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 删除信息
	 *
	 * @param filePath
	 * @param key
	 */
	private static void delElement(String filePath, String key) {
		try {
			Document doc = getDoc(filePath);

			boolean f = false;
			Node rootNode = doc.getFirstChild();
			NodeList nodeList = rootNode.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList itemList = node.getChildNodes();

				String keyValue = itemList.item(0).getTextContent();
				if (keyValue.equals(key)) {
					rootNode.removeChild(node);
					f = true;
				}
			}
			System.out.println("delete flag is " + f);
			if (f)
				writeXMLFile(doc, filePath);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
