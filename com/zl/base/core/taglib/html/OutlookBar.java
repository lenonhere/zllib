package com.zl.base.core.taglib.html;

import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OutlookBar extends BaseHandlerTag {
	// private MethodFactory datatypechang = new MethodFactory();
	private String collection = null;
	private ArrayList basetreedata = null;
	private String nodeid = null;
	private String nodetext = null;
	private String nodeimage = null;
	private String nodeparentid = null;
	private String nodetype = null;
	private String nodeparenttype = null;
	private String url = null;
	private String enabled = null;
	private boolean showcheckbox = false;
	private String property = "tree";
	private String rootname = "root";
	private String nodechecked = null;
	// private String checkednodeid = null;
	// private int expandlevel = 1;
	private boolean showroot = true;
	private String value1 = null;
	private String value2 = null;
	private String height = "100%";
	private String width = "100%";
	private String xmltype = "1";
	private String xmlcollection = null;
	private int num = 0;

	// public static void main(String[] paramArrayOfString) {
	// outlookbar localoutlookbar = new outlookbar();
	// }

	public String getnodechecked() {
		return this.nodechecked;
	}

	public void setnodechecked(String paramString) {
		this.nodechecked = paramString;
	}

	public String getrootname() {
		return this.rootname;
	}

	public void setrootname(String paramString) {
		this.rootname = paramString;
	}

	// public int getexpandlevel() {
	// return this.expandlevel;
	// }
	//
	// public void setexpandlevel(int paramInt) {
	// this.expandlevel = paramInt;
	// }

	public boolean getshowroot() {
		return this.showroot;
	}

	public void setshowroot(boolean paramBoolean) {
		this.showroot = paramBoolean;
	}

	public String getvalue1() {
		return this.value1;
	}

	public void setvalue1(String paramString) {
		this.value1 = paramString;
	}

	public String getvalue2() {
		return this.value2;
	}

	public void setvalue2(String paramString) {
		this.value2 = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		if (paramString != null) {
			if (paramString.equals(""))
				this.property = "tree";
			else
				this.property = paramString;
		} else
			this.property = "tree";
		this.property = this.property.trim();
	}

	public String getheight() {
		return this.height;
	}

	public void setheight(String paramString) {
		this.height = paramString;
	}

	public String getwidth() {
		return this.width;
	}

	public void setwidth(String paramString) {
		this.width = paramString;
	}

	public void setshowcheckbox(boolean paramBoolean) {
		this.showcheckbox = paramBoolean;
	}

	public boolean getshowcheckbox() {
		return this.showcheckbox;
	}

	public void setcollection(String paramString) {
		this.collection = paramString;
	}

	public String getcollection() {
		return this.collection;
	}

	public String getenabled() {
		return this.enabled;
	}

	public void setenabled(String paramString) {
		this.enabled = paramString;
	}

	public String getnodeid() {
		return this.nodeid;
	}

	public void setnodeid(String paramString) {
		this.nodeid = paramString;
	}

	public String getnodetext() {
		return this.nodetext;
	}

	public void setnodetext(String paramString) {
		this.nodetext = paramString;
	}

	public String getnodeimage() {
		return this.nodeimage;
	}

	public void setnodeimage(String paramString) {
		this.nodeimage = paramString;
	}

	public String getnodeparentid() {
		return this.nodeparentid;
	}

	public void setnodeparentid(String paramString) {
		this.nodeparentid = paramString;
	}

	public String getnodetype() {
		return this.nodetype;
	}

	public void setnodetype(String paramString) {
		this.nodetype = paramString;
	}

	public String getnodeparenttype() {
		return this.nodeparenttype;
	}

	public void setnodeparenttype(String paramString) {
		this.nodeparenttype = paramString;
	}

	public String geturl() {
		return this.url;
	}

	public void seturl(String paramString) {
		this.url = paramString;
	}

	public String getxmltype() {
		return this.xmltype;
	}

	public void setxmltype(String paramString) {
		this.xmltype = paramString;
	}

	public String getxmlcollection() {
		return this.xmlcollection;
	}

	public void setxmlcollection(String paramString) {
		this.xmlcollection = paramString;
	}

	private TreedataStru findparent(ArrayList arraylist, Object obj, Object obj1) {
		TreedataStru TreedataStru1 = null;
		if (arraylist != null) {
			int i = 0;
			do {
				if (i >= arraylist.size())
					break;
				TreedataStru TreedataStru2 = (TreedataStru) arraylist.get(i);
				if (nodeparenttype != null) {
					if (TreedataStru2.nodeid.equals(obj)
							&& TreedataStru2.nodetype.equals(obj1))
						TreedataStru1 = TreedataStru2;
				} else if (TreedataStru2.nodeid.equals(obj))
					TreedataStru1 = TreedataStru2;
				if (TreedataStru1 == null && TreedataStru2.childnode.size() > 0)
					TreedataStru1 = findparent(TreedataStru2.childnode, obj,
							obj1);
				if (TreedataStru1 != null)
					break;
				i++;
			} while (true);
		}
		return TreedataStru1;
	}

	private void writenode(ArrayList paramArrayList,
			StringBuffer paramStringBuffer, int paramInt) {
		for (int i = 0; i < paramArrayList.size(); i++) {
			TreedataStru treeDataStru = (TreedataStru) paramArrayList.get(i);
			if (paramInt == 0) {
				this.num += 1;
				paramStringBuffer.append("outlookbar.addtitle(\""
						+ treeDataStru.nodetext + "\");\n");
			} else {
				paramStringBuffer.append("outlookbar.additem(\""
						+ treeDataStru.nodetext + "\"," + this.num + "," + "\""
						+ treeDataStru.url + "\"");
				if (treeDataStru.nodeimage != null)
					paramStringBuffer.append(",\"" + treeDataStru.nodeimage
							+ "\"");
				paramStringBuffer.append(");\n");
			}
			if (treeDataStru.childnode.size() > 0)
				if (paramInt == 0)
					writenode(treeDataStru.childnode, paramStringBuffer, 1);
				else
					writenode(treeDataStru.childnode, paramStringBuffer, 2);
		}
	}

	public int doEndTag() throws JspException {
		if (this.collection != null)
			readArrayList();
		else if ((this.xmlcollection != null) && (this.xmltype == "1"))
			readXmlType1();
		this.num = -1;
		if (this.basetreedata.size() > 0) {
			StringBuffer localStringBuffer = new StringBuffer("");
			localStringBuffer.append(" <script language =\"javascript\">;\n");
			localStringBuffer.append(" var outlookbar=new outlook();\n");
			writenode(this.basetreedata, localStringBuffer, 0);
			localStringBuffer.append("outlookbar.show();\n");
			localStringBuffer.append("  </script>\n");
			ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		}
		return 6;
	}

	private void readArrayList() throws JspException {
		Object obj1 = this.pageContext.findAttribute(this.collection);
		if (obj1 != null) {
			ArrayList localArrayList = (ArrayList) obj1;
			Object obj2 = null;
			Object obj3 = null;
			Object obj4 = null;
			Object obj5 = null;
			Object obj6 = null;
			Object obj7 = null;
			Object obj8 = null;
			Object obj9 = null;
			Object obj10 = null;
			Object obj11 = null;
			for (int i = 0; i < localArrayList.size(); i++) {

				obj1 = localArrayList.get(i);
				try {
					obj2 = PropertyUtils.getProperty(obj1, this.nodeid);
					if (obj2 != null)
						obj2 = obj2.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString() + "没有发现节点的id字段");
				}
				try {
					obj4 = PropertyUtils.getProperty(obj1, this.nodetext);
					if (obj4 != null)
						obj4 = obj4.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString() + "没有发现节点的text字段");
				}
				try {
					obj5 = PropertyUtils.getProperty(obj1, this.nodeparentid);
					if (obj5 != null)
						obj5 = obj5.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString()
							+ "没有发现节点的nodeparentid字段");
				}
				if (this.nodetype != null) {
					try {
						obj3 = PropertyUtils.getProperty(obj1, this.nodetype);
						if (obj3 != null)
							obj3 = obj3.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodetype字段");
					}
				}
				if (this.value1 != null) {
					try {
						obj10 = PropertyUtils.getProperty(obj1, this.value1);
						if (obj10 != null)
							obj10 = obj10.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的value1字段");
					}
				}
				if (this.value2 != null) {
					try {
						obj11 = PropertyUtils.getProperty(obj1, this.value2);
						if (obj11 != null)
							obj11 = obj11.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的value2字段");
					}
				}
				if (this.nodeparenttype != null) {
					try {
						obj6 = PropertyUtils.getProperty(obj1,
								this.nodeparenttype);
						if (obj6 != null)
							obj6 = obj6.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodeparenttype字段");
					}
				}
				if (this.nodechecked != null) {
					try {
						obj9 = PropertyUtils
								.getProperty(obj1, this.nodechecked);
						if (this.nodechecked != null)
							obj9 = obj9.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodechecked字段");
					}
				}
				if (this.url != null) {
					try {
						obj7 = PropertyUtils.getProperty(obj1, this.url);
						if (obj7 != null)
							obj7 = obj7.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的url字段");
					}
				}
				if (this.nodeimage != null) {
					try {
						obj8 = PropertyUtils.getProperty(obj1, this.nodeimage);
						if (obj8 != null)
							obj8 = obj8.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodeimage字段");
					}
				}
				//
				TreedataStru treeDataStru1 = findparent(this.basetreedata,
						obj5, obj6);
				TreedataStru treeDataStru2 = new TreedataStru();
				treeDataStru2.nodeid = obj2;
				treeDataStru2.nodeimage = obj8;
				treeDataStru2.nodeparentid = obj5;
				treeDataStru2.nodeparenttype = obj6;
				treeDataStru2.nodetext = obj4;
				treeDataStru2.nodetype = obj3;
				treeDataStru2.url = obj7;
				treeDataStru2.checked = obj9;
				treeDataStru2.value1 = obj10;
				treeDataStru2.value2 = obj11;
				if (treeDataStru1 != null)
					treeDataStru1.childnode.add(treeDataStru2);
				else
					this.basetreedata.add(treeDataStru2);
			}
		}
	}

	private void readXmlType1() {
		try {
			Object obj = null;
			obj = this.pageContext.findAttribute(this.xmlcollection);
			DocumentBuilder localDocumentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			StringReader localStringReader = new StringReader(obj.toString());
			InputSource localInputSource = new InputSource(localStringReader);
			Document localDocument = localDocumentBuilder
					.parse(localInputSource);
			NodeList localNodeList = localDocument.getChildNodes();
			readnodelist(localNodeList, null);
		} catch (Exception localException) {
		}
	}

	private void readnodelist(NodeList paramNodeList,
			TreedataStru paramTreedataStru) {
		for (int i = 0; i < paramNodeList.getLength(); i++) {
			Node localNode = paramNodeList.item(i);
			if (localNode.getNodeType() == 1) {
				Object obj = null;
				String str1 = null;
				String str2 = null;
				String str3 = null;
				String str4 = null;
				String str5 = null;
				String str6 = null;
				String str7 = null;
				//
				str1 = localNode.getAttributes().getNamedItem(this.nodeid)
						.getNodeValue();
				str3 = localNode.getAttributes().getNamedItem(this.nodetext)
						.getNodeValue();
				if (this.nodetype != null)
					str2 = localNode.getAttributes()
							.getNamedItem(this.nodetype).getNodeValue();
				if (this.value1 != null)
					str6 = localNode.getAttributes().getNamedItem(this.value1)
							.getNodeValue();
				if (this.value2 != null)
					str7 = localNode.getAttributes().getNamedItem(this.value2)
							.getNodeValue();
				if ((this.nodechecked != null)
						&& (localNode.getAttributes().getNamedItem(
								this.nodechecked) != null))
					str5 = localNode.getAttributes()
							.getNamedItem(this.nodechecked).getNodeValue();
				if (this.url != null)
					str4 = localNode.getAttributes().getNamedItem(this.url)
							.getNodeValue();
				//
				TreedataStru treeDataStru = new TreedataStru();
				treeDataStru.nodeid = str1;
				treeDataStru.nodeimage = obj;
				treeDataStru.nodetext = str3;
				treeDataStru.nodetype = str2;
				treeDataStru.url = str4;
				treeDataStru.checked = str5;
				treeDataStru.value1 = str6;
				treeDataStru.value2 = str7;
				if (paramTreedataStru != null)
					paramTreedataStru.childnode.add(treeDataStru);
				else
					this.basetreedata.add(treeDataStru);
				if (localNode.hasChildNodes()) {
					NodeList localNodeList = localNode.getChildNodes();
					int m = localNodeList.getLength();
					readnodelist(localNodeList, treeDataStru);
				}
			}
		}
	}

	public int doStartTag() throws JspException {
		// this.checkednodeid = null;
		this.basetreedata = new ArrayList();
		return 0;
	}

	private class TreedataStru {
		public Object nodeid = "";
		public Object nodetext = "";
		public Object nodeimage = "";
		public Object nodeparentid = "";
		public Object nodetype = "";
		public Object nodeparenttype = "";
		public Object url = "";
		public Object enabled = "";
		public Object checked = "";
		public ArrayList childnode = null;
		public Object value1 = "";
		public Object value2 = "";

		public TreedataStru() {
		}
	}

}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.html.outlookbar JD-Core Version: 0.6.1
 */