package com.zl.base.core.taglib.chart.vml.data;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DataSet {
	public ArrayList m_Column = new ArrayList();
	public ArrayList m_Caption = new ArrayList();
	public ArrayList m_Row = new ArrayList();

	public DataSet() {
		Clear();
	}

	public void Clear() {
		this.m_Column.clear();
		this.m_Caption.clear();
		this.m_Row.clear();
	}

	public void LoadDataSet(String paramString) {
		try {
			Clear();
			DocumentBuilder localDocumentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			StringReader localStringReader = new StringReader(paramString);
			InputSource localInputSource = new InputSource(localStringReader);
			Document localDocument = localDocumentBuilder
					.parse(localInputSource);
			Element localElement = localDocument.getDocumentElement();
			NodeList localNodeList1 = localElement.getChildNodes();
			if (localNodeList1.getLength() >= 2) {
				NodeList localNodeList2 = localNodeList1.item(0)
						.getChildNodes();
				Node localNode2;
				Object localObject1;
				Object localObject2;
				Object localObject3;
				Object localObject4;
				Node localNode4;
				for (int i = 0; i < localNodeList2.getLength(); i++) {
					Node localNode1 = localNodeList2.item(i);
					localNode2 = localNode1.getAttributes().getNamedItem(
							"caption");
					localObject1 = new Column();
					if (localNode2 != null)
						((Column) localObject1).SetCaption(localNode2
								.getNodeValue());
					localObject2 = localNode1.getAttributes().getNamedItem(
							"property");
					if (localObject2 != null)
						((Column) localObject1)
								.SetProperty(((Node) localObject2)
										.getNodeValue());
					Node localNode3 = localNode1.getAttributes().getNamedItem(
							"shapetype");
					if (localNode3 != null)
						((Column) localObject1).SetShapeType(new Integer(
								localNode3.getNodeValue()).intValue());
					localObject3 = localNode1.getAttributes()
							.getNamedItem("id");
					if (localObject3 != null)
						((Column) localObject1).SetID(((Node) localObject3)
								.getNodeValue());
					localObject4 = localNode1.getAttributes().getNamedItem(
							"href");
					if (localObject4 != null)
						((Column) localObject1).SetHref(((Node) localObject4)
								.getNodeValue());
					localNode4 = localNode1.getAttributes().getNamedItem(
							"onclick");
					if (localNode4 != null)
						((Column) localObject1).SetScript(localNode4
								.getNodeValue());
					Node localNode5 = localNode1.getAttributes().getNamedItem(
							"sign");
					if (localNode5 != null)
						((Column) localObject1).SetSign(Boolean.valueOf(
								localNode5.getNodeValue()).booleanValue());
					this.m_Column.add(localObject1);
				}
				NodeList localNodeList3 = localNodeList1.item(1)
						.getChildNodes();
				for (int j = 0; j < localNodeList3.getLength(); j++) {
					localNode2 = localNodeList3.item(j);
					localObject1 = localNode2.getAttributes().getNamedItem(
							"caption");
					if (localObject1 != null)
						this.m_Caption
								.add(((Node) localObject1).getNodeValue());
					else
						this.m_Caption.add("");
					localObject2 = new ArrayList();
					for (int k = 0; k < this.m_Column.size(); k++) {
						localObject3 = (Column) this.m_Column.get(k);
						localObject4 = ((Column) localObject3).GetProperty();
						localNode4 = localNode2.getAttributes().getNamedItem(
								(String) localObject4);
						if (localNode4 != null)
							((ArrayList) localObject2).add(localNode4
									.getNodeValue());
						else
							((ArrayList) localObject2).add("");
					}
					this.m_Row.add(localObject2);
				}
			}
		} catch (Exception localException) {
		}
	}

	public String DumpDataSet() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>\n");
		stringbuffer.append("<root>\n");
		stringbuffer.append("<COLUMN_RECORDSET>\n");
		for (int i = 0; i < m_Column.size(); i++) {
			stringbuffer.append("<row");
			Column column = (Column) m_Column.get(i);
			if (column.GetCaption() != null) {
				stringbuffer.append(" caption=\"");
				stringbuffer.append(column.GetCaption());
				stringbuffer.append("\"");
			}
			if (column.GetProperty() != null) {
				stringbuffer.append(" property=\"");
				stringbuffer.append(column.GetProperty());
				stringbuffer.append("\"");
			}
			if (column.GetID() != null) {
				stringbuffer.append(" id=\"");
				stringbuffer.append(column.GetID());
				stringbuffer.append("\"");
			}
			if (column.GetShapeType() != 0) {
				stringbuffer.append(" shapetype=\"");
				stringbuffer.append(column.GetShapeType());
				stringbuffer.append("\"");
			}
			if (column.GetHref() != null) {
				stringbuffer.append(" href=\"");
				stringbuffer.append(column.GetHref());
				stringbuffer.append("\"");
			}
			if (column.GetScript() != null) {
				stringbuffer.append(" script=\"");
				stringbuffer.append(column.GetScript());
				stringbuffer.append("\"");
			}
			stringbuffer.append("/>\n");
		}

		stringbuffer.append("</COLUMN_RECORDSET>\n");
		stringbuffer.append("<DATA_RECORDSET>\n");
		for (int j = 0; j < m_Row.size(); j++) {
			stringbuffer.append("<row");
			stringbuffer.append(" caption=\"");
			stringbuffer.append((String) m_Caption.get(j));
			stringbuffer.append("\"");
			ArrayList arraylist = (ArrayList) m_Row.get(j);
			for (int k = 0; k < arraylist.size(); k++) {
				Column column1 = (Column) m_Column.get(k);
				if (column1.GetProperty() != null) {
					stringbuffer.append(" ");
					stringbuffer.append(column1.GetProperty());
					stringbuffer.append("=\"");
					stringbuffer.append((String) arraylist.get(k));
					stringbuffer.append("\"");
				}
			}

			stringbuffer.append("/>\n");
		}

		stringbuffer.append("</DATA_RECORDSET>\n");
		stringbuffer.append("</root>");
		return stringbuffer.toString();
	}

	// public String DumpDataSet()
	// {
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>\n");
	// localStringBuffer.append("<root>\n");
	// localStringBuffer.append("<COLUMN_RECORDSET>\n");
	// Object localObject;
	// for (int i = 0; i < this.m_Column.size(); i++)
	// {
	// localStringBuffer.append("<row");
	// localObject = (Column)this.m_Column.get(i);
	// if (((Column)localObject).GetCaption() != null)
	// {
	// localStringBuffer.append(" caption=\"");
	// localStringBuffer.append(((Column)localObject).GetCaption());
	// localStringBuffer.append("\"");
	// }
	// if (((Column)localObject).GetProperty() != null)
	// {
	// localStringBuffer.append(" property=\"");
	// localStringBuffer.append(((Column)localObject).GetProperty());
	// localStringBuffer.append("\"");
	// }
	// if (((Column)localObject).GetID() != null)
	// {
	// localStringBuffer.append(" id=\"");
	// localStringBuffer.append(((Column)localObject).GetID());
	// localStringBuffer.append("\"");
	// }
	// if (((Column)localObject).GetShapeType() != 0)
	// {
	// localStringBuffer.append(" shapetype=\"");
	// localStringBuffer.append(((Column)localObject).GetShapeType());
	// localStringBuffer.append("\"");
	// }
	// if (((Column)localObject).GetHref() != null)
	// {
	// localStringBuffer.append(" href=\"");
	// localStringBuffer.append(((Column)localObject).GetHref());
	// localStringBuffer.append("\"");
	// }
	// if (((Column)localObject).GetScript() != null)
	// {
	// localStringBuffer.append(" script=\"");
	// localStringBuffer.append(((Column)localObject).GetScript());
	// localStringBuffer.append("\"");
	// }
	// localStringBuffer.append("/>\n");
	// }
	// localStringBuffer.append("</COLUMN_RECORDSET>\n");
	// localStringBuffer.append("<DATA_RECORDSET>\n");
	// for (i = 0; i < this.m_Row.size(); i++)
	// {
	// localStringBuffer.append("<row");
	// localStringBuffer.append(" caption=\"");
	// localStringBuffer.append((String)this.m_Caption.get(i));
	// localStringBuffer.append("\"");
	// localObject = (ArrayList)this.m_Row.get(i);
	// for (int j = 0; j < ((ArrayList)localObject).size(); j++)
	// {
	// Column localColumn = (Column)this.m_Column.get(j);
	// if (localColumn.GetProperty() != null)
	// {
	// localStringBuffer.append(" ");
	// localStringBuffer.append(localColumn.GetProperty());
	// localStringBuffer.append("=\"");
	// localStringBuffer.append((String)((ArrayList)localObject).get(j));
	// localStringBuffer.append("\"");
	// }
	// }
	// localStringBuffer.append("/>\n");
	// }
	// localStringBuffer.append("</DATA_RECORDSET>\n");
	// localStringBuffer.append("</root>");
	// return localStringBuffer.toString();
	// }

	public double GetMaxValue() {
		double d1 = 0.0D;
		for (int i = 0; i < this.m_Row.size(); i++) {
			ArrayList localArrayList = (ArrayList) this.m_Row.get(i);
			for (int j = 0; j < localArrayList.size(); j++) {
				String str = (String) localArrayList.get(j);
				if (str.length() > 0) {
					double d2 = Double.valueOf(str).doubleValue();
					if (d2 > d1)
						d1 = d2;
				}
			}
		}
		return d1;
	}

	public double GetMinValue() {
		double d1 = 999999999.0D;
		for (int i = 0; i < this.m_Row.size(); i++) {
			ArrayList localArrayList = (ArrayList) this.m_Row.get(i);
			for (int j = 0; j < localArrayList.size(); j++) {
				String str = (String) localArrayList.get(j);
				if (str.length() > 0) {
					double d2 = Double.valueOf(str).doubleValue();
					if (d2 < d1)
						d1 = d2;
				}
			}
		}
		return d1;
	}

	public static void main(String[] paramArrayOfString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		localStringBuffer.append("<root>");
		localStringBuffer.append("<CONTENT_RECORDSET>");
		localStringBuffer
				.append("<row caption=\"1.得到付坚峰\" property=\"c1\"  shapetype=\"0\"/>");
		localStringBuffer
				.append("<row caption=\"2.分分德国队\"  property=\"c2\" shapetype=\"1\"/>");
		localStringBuffer
				.append("<row caption=\"3.交易山东队\"  property=\"c3\" shapetype=\"1\"/>");
		localStringBuffer.append("</CONTENT_RECORDSET>");
		localStringBuffer.append("<CONTENT1_RECORDSET>");
		localStringBuffer
				.append("<row caption=\"第一消毒法\" c1=\"167\" c2=\"87\" c3=\"89\"/>");
		localStringBuffer
				.append("<row caption=\"第尔大师傅个到\" c1=\"667\" c2=\"607\" c3=\"567\" />");
		localStringBuffer
				.append("<row caption=\"第上斯多夫固定法\" c1=\"66\" c2=\"77\" c3=\"454\" />");
		localStringBuffer
				.append("<row caption=\"第豆腐干大发市\" c1=\"345\" c2=\"34\" c3=\"67\" />");
		localStringBuffer.append("</CONTENT1_RECORDSET>");
		localStringBuffer.append("</root>");
		DataSet localDataSet = new DataSet();
		localDataSet.LoadDataSet(localStringBuffer.toString());
		System.out.println(localDataSet.DumpDataSet());
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.chart.vml.data.DataSet JD-Core Version: 0.6.1
 */