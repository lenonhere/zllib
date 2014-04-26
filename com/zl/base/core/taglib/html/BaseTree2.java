package com.zl.base.core.taglib.html;

import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
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

import com.zl.base.core.taglib.ReadTaglibProperty;

public class BaseTree2 extends BaseHandlerTag {
	private final String rowsliptString = "\n";
	private ReadTaglibProperty taglibproperty = ReadTaglibProperty
			.getInstance();
	private String urlpath = "";
	private String imagepath = "public/xtree/";
	private String selectedIcon = "select.bmp";
	private String noselectedIcon = "noselect.bmp";
	private String noIcon = "no.bmp";
	private String rootIcon = "foldericon.png";
	private String openRootIcon = "openfoldericon.png";
	private String folderIcon = "foldericon.png";
	private String openFolderIcon = "openfoldericon.png";
	private String fileIcon = "file.png";
	private String iIcon = "I.png";
	private String lIcon = "L.png";
	private String lMinusIcon = "Lminus.png";
	private String lPlusIcon = "Lplus.png";
	private String tIcon = "T.png";
	private String tMinusIcon = "Tminus.png";
	private String tPlusIcon = "Tplus.png";
	private String blankIcon = "blank.png";
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
	private String checkednodeid = null;
	private boolean showtitle = false;
	private int expandlevel = 1;
	private boolean showroot = true;
	private String value1 = null;
	private String value2 = null;
	private String height = "100%";
	private String width = "100%";
	private String xmltype = "1";
	private String xmlcollection = null;

	public boolean getshowtitle() {
		return showtitle;
	}

	public void setshowtitle(boolean paramBoolean) {
		showtitle = paramBoolean;
	}

	public String getnodechecked() {
		return nodechecked;
	}

	public void setnodechecked(String paramString) {
		nodechecked = paramString;
	}

	public String getrootname() {
		return rootname;
	}

	public void setrootname(String paramString) {
		rootname = paramString;
	}

	public int getexpandlevel() {
		return expandlevel;
	}

	public void setexpandlevel(int paramInt) {
		expandlevel = paramInt;
	}

	public boolean getshowroot() {
		return showroot;
	}

	public void setshowroot(boolean paramBoolean) {
		showroot = paramBoolean;
	}

	public String getvalue1() {
		return value1;
	}

	public void setvalue1(String paramString) {
		value1 = paramString;
	}

	public String getvalue2() {
		return value2;
	}

	public void setvalue2(String paramString) {
		value2 = paramString;
	}

	public String getproperty() {
		return property;
	}

	public void setproperty(String paramString) {
		if (paramString != null) {
			if (paramString.equals(""))
				property = "tree";
			else
				property = paramString;
		} else
			property = "tree";
		property = property.trim();
	}

	public String getheight() {
		return height;
	}

	public void setheight(String paramString) {
		height = paramString;
	}

	public String getwidth() {
		return width;
	}

	public void setwidth(String paramString) {
		width = paramString;
	}

	public void setshowcheckbox(boolean paramBoolean) {
		showcheckbox = paramBoolean;
	}

	public boolean getshowcheckbox() {
		return showcheckbox;
	}

	public void setcollection(String paramString) {
		collection = paramString;
	}

	public String getcollection() {
		return collection;
	}

	public String getenabled() {
		return enabled;
	}

	public void setenabled(String paramString) {
		enabled = paramString;
	}

	public String getnodeid() {
		return nodeid;
	}

	public void setnodeid(String paramString) {
		nodeid = paramString;
	}

	public String getnodetext() {
		return nodetext;
	}

	public void setnodetext(String paramString) {
		nodetext = paramString;
	}

	public String getnodeimage() {
		return nodeimage;
	}

	public void setnodeimage(String paramString) {
		nodeimage = paramString;
	}

	public String getnodeparentid() {
		return nodeparentid;
	}

	public void setnodeparentid(String paramString) {
		nodeparentid = paramString;
	}

	public String getnodetype() {
		return nodetype;
	}

	public void setnodetype(String paramString) {
		nodetype = paramString;
	}

	public String getnodeparenttype() {
		return nodeparenttype;
	}

	public void setnodeparenttype(String paramString) {
		nodeparenttype = paramString;
	}

	public String geturl() {
		return url;
	}

	public void seturl(String paramString) {
		url = paramString;
	}

	public String getxmltype() {
		return xmltype;
	}

	public void setxmltype(String paramString) {
		xmltype = paramString;
	}

	public String getxmlcollection() {
		return xmlcollection;
	}

	public void setxmlcollection(String paramString) {
		xmlcollection = paramString;
	}

	private treedatastru findparent(ArrayList paramArrayList,
			Object paramObject1, Object paramObject2) {
		Object localObject = null;
		if (paramArrayList != null) {
			int i = 0;
			treedatastru localtreedatastru = null;
			for (i = 0; i < paramArrayList.size(); i++) {
				localtreedatastru = (treedatastru) paramArrayList.get(i);
				if (nodeparenttype != null) {
					if ((localtreedatastru.nodeid.equals(paramObject1))
							&& (localtreedatastru.nodetype.equals(paramObject2)))
						localObject = localtreedatastru;
				} else if (localtreedatastru.nodeid.equals(paramObject1))
					localObject = localtreedatastru;
				if ((localObject == null)
						&& (localtreedatastru.childnode.size() > 0))
					localObject = findparent(localtreedatastru.childnode,
							paramObject1, paramObject2);
				if (localObject != null)
					break;
			}
		}
		return (treedatastru) localObject;
	}

	private void writescript(StringBuffer paramStringBuffer) {
		paramStringBuffer.append("<script language=\"javascript\"> \n");
		paramStringBuffer.append("var " + property + " = { \n");
		paramStringBuffer.append("imagepath:\"" + urlpath + imagepath
				+ "\", \n");
		paramStringBuffer.append("selectedIcon:\"" + selectedIcon + "\", \n");
		paramStringBuffer.append("noselectedIcon:\"" + noselectedIcon
				+ "\", \n");
		paramStringBuffer.append("noIcon:\"" + noIcon + "\", \n");
		paramStringBuffer.append("rootIcon:\"" + rootIcon + "\", \n");
		paramStringBuffer.append("openRootIcon:\"" + openRootIcon + "\", \n");
		paramStringBuffer.append("folderIcon:\"" + folderIcon + "\", \n");
		paramStringBuffer.append("openFolderIcon:\"" + openFolderIcon
				+ "\", \n");
		paramStringBuffer.append("fileIcon:\"" + fileIcon + "\", \n");
		paramStringBuffer.append("lMinusIcon:\"" + lMinusIcon + "\", \n");
		paramStringBuffer.append("lPlusIcon:\"" + lPlusIcon + "\", \n");
		paramStringBuffer.append("tMinusIcon:\"" + tMinusIcon + "\", \n");
		paramStringBuffer.append("tPlusIcon:\"" + tPlusIcon + "\", \n");
		paramStringBuffer.append("\tobjectname:\"" + property + "\", \n");
		paramStringBuffer.append("\tshowcheckbox:" + showcheckbox + ", \n");
		paramStringBuffer.append("\tobjfocus:null, \n");
		paramStringBuffer.append("\tsplitstring:\",\", \n");
		paramStringBuffer.append("\t//读取选中的系统编码 \n");
		paramStringBuffer.append("\tgetselectid:function(nodetype,state){ \n");
		paramStringBuffer.append("if(this.showcheckbox==false)\n");
		paramStringBuffer
				.append("          return getvaluebytype2(this.objfocus,\"id\");\n");
		paramStringBuffer.append("else\n");
		paramStringBuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"id\",this.splitstring,state); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer
				.append("\tgetselecttype:function(nodetype,state){ \n");
		paramStringBuffer.append("if(this.showcheckbox==false)\n");
		paramStringBuffer
				.append("          return getvaluebytype2(this.objfocus,\"type\");\n");
		paramStringBuffer.append("else\n");
		paramStringBuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"type\",this.splitstring,state); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer
				.append("\tgetselectvalue1:function(nodetype,state){ \n");
		paramStringBuffer.append("if(this.showcheckbox==false)\n");
		paramStringBuffer
				.append("          return getvaluebytype2(this.objfocus,\"value1\");\n");
		paramStringBuffer.append("else\n");
		paramStringBuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"value1\",this.splitstring,state); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer
				.append("\tgetselectvalue2:function(nodetype,state){\n");
		paramStringBuffer.append("if(this.showcheckbox==false)\n");
		paramStringBuffer
				.append("          return getvaluebytype2(this.objfocus,\"value2\");\n");
		paramStringBuffer.append("else \n");
		paramStringBuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"value2\",this.splitstring,state); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer
				.append("\tgetselecttext:function(nodetype,state){ \n");
		paramStringBuffer.append("if(this.showcheckbox==false)\n");
		paramStringBuffer
				.append("          return getvaluebytype2(this.objfocus,\"text\");\n");
		paramStringBuffer.append("else\n");
		paramStringBuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"text\",this.splitstring,state); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer
				.append("\tsetselectid:function (nodeid,nodetype){ \n");
		paramStringBuffer
				.append("\t\tsetselectedid(this,nodeid,nodetype,this.splitstring); \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer.append("\tcontrolonblur:function(objimg){ \n");
		paramStringBuffer
				.append("\t\tobjimg.className=\"selected-inactive\"; \n");
		paramStringBuffer.append("\t\treturn \"\"; \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer.append("\tcontrolfocus:function(objTemp){ \n");
		paramStringBuffer.append("\t\tvar objimg; \n");
		paramStringBuffer.append("\t\tvar id; \n");
		paramStringBuffer.append("\t\tif(this.objfocus!=null){ \n");
		paramStringBuffer
				.append("\t\t\tthis.objfocus.className=\"treeNode\"; \n");
		paramStringBuffer.append("\t\t} \n");
		paramStringBuffer.append("\t\tobjTemp=objTemp.parentNode; \n");
		paramStringBuffer.append("\t\tid=objTemp.id; \n");
		paramStringBuffer
				.append("\t\tobjTemp = getNodeById(objTemp,id+\"l\"); \n");
		paramStringBuffer.append("\t\tobjTemp.focus(); \n");
		paramStringBuffer.append("\t\tthis.objfocus= objTemp;\n");
		paramStringBuffer.append("\t\tobjTemp.className=\"treeNodeLink\"; \n");
		paramStringBuffer.append("\t\treturn \"\"; \n");
		paramStringBuffer.append("\t}, \n");
		paramStringBuffer.append("checkselectall:function(){\n");
		paramStringBuffer.append("if(this.showcheckbox==true)\n");
		paramStringBuffer.append("return checkselectall(this.objectname);\n");
		paramStringBuffer.append("else\n");
		paramStringBuffer.append("return false;\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getnodetype:function(nodetype){\n");
		paramStringBuffer.append("   return this.getselecttype(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getnodeid:function(nodetype){\n");
		paramStringBuffer.append("   return  this.getselectid(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getnodetext:function(nodetype){\n");
		paramStringBuffer.append("   return  this.getselecttext(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getnodevalue1:function(nodetype){\n");
		paramStringBuffer
				.append("   return  this.getselectvalue1(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getnodevalue2:function(nodetype){\n");
		paramStringBuffer.append("   return this.getselectvalue2(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getchildcount:function(){\n");
		paramStringBuffer.append("   return getchildcount(this.objfocus);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getvalue2:function(nodetype){\n");
		paramStringBuffer.append("   return this.getnodevalue2(nodetype);\n");
		paramStringBuffer.append("},\n");
		paramStringBuffer.append("getvalue1:function(nodetype){\n");
		paramStringBuffer.append("   return this.getnodevalue1(nodetype);\n");
		paramStringBuffer.append("}\n");
		paramStringBuffer.append("}; \n");
		paramStringBuffer.append("</script> \n");
	}

	private trastru writenode(String paramString, ArrayList paramArrayList,
			trastru paramtrastru, int paramInt) {
		int i = 0;
		String str1 = null;
		treedatastru localtreedatastru = null;
		boolean bool1 = false;
		boolean bool2 = false;
		StringBuffer localStringBuffer1 = new StringBuffer("");
		int j = 0;
		String str2 = "0";
		String str3 = "1";
		for (i = 0; i < paramArrayList.size(); i++) {
			paramtrastru.number += 1L;
			localtreedatastru = (treedatastru) paramArrayList.get(i);
			StringBuffer localStringBuffer2 = new StringBuffer("");
			trastru localtrastru = new trastru();
			localtrastru.number = paramtrastru.number;
			if (showcheckbox == true) {
				str2 = (String) localtreedatastru.checked;
				if (str2 == null)
					str2 = "0";
				if ((!str2.equals("1")) && (!str2.equals("0"))
						&& (!str2.equals("-1")))
					str2 = "0";
				localtrastru.state = str2;
			}
			if (i == paramArrayList.size() - 1)
				bool2 = false;
			else
				bool2 = true;
			str1 = writenodeimage(localStringBuffer2, str2, paramString, bool1,
					bool2, paramtrastru.number, paramInt);
			if (localtreedatastru.childnode.size() > 0) {
				localtrastru = writenode(str1, localtreedatastru.childnode,
						localtrastru, paramInt + 1);
				bool1 = true;
			} else {
				bool1 = false;
			}
			if (showcheckbox == true) {
				if (localtreedatastru.childnode.size() > 0)
					str2 = localtrastru.state;
				if (i == 0)
					str3 = str2;
				else if (!str3.equals(str2))
					str3 = "-1";
			}
			writenodehead(localStringBuffer1, paramtrastru.number, str2,
					paramInt);
			str1 = writenodeimage(localStringBuffer1, str2, paramString, bool1,
					bool2, paramtrastru.number, paramInt);
			writenodeA(localStringBuffer1, localtreedatastru.nodeid.toString(),
					localtreedatastru.nodetype.toString(),
					localtreedatastru.nodetext.toString(),
					localtreedatastru.value1.toString(),
					localtreedatastru.value2.toString(), paramtrastru.number,
					localtreedatastru.url.toString());
			if (localtreedatastru.childnode.size() > 0) {
				writenodecontenthead(localStringBuffer1, paramtrastru.number,
						paramInt);
				localStringBuffer1.append(localtrastru.nodeString);
				writenodefoot(localStringBuffer1);
			}
			writenodefoot(localStringBuffer1);
			paramtrastru.number = localtrastru.number;
		}
		paramtrastru.state = str3;
		paramtrastru.nodeString = localStringBuffer1.toString();
		return paramtrastru;
	}

	private void addchecknode(String paramString1, String paramString2) {
		if ((nodechecked != null)
				&& (paramString2 != null)
				&& ((paramString2.trim().equals("1")) || (paramString2
						.toLowerCase().trim().equals("true"))))
			if (checkednodeid == null)
				checkednodeid = paramString1;
			else
				checkednodeid = (checkednodeid + "," + paramString1);
	}

	public int doEndTag() throws JspException {
		urlpath = (((HttpServletRequest) pageContext.getRequest())
				.getContextPath() + "/");
		if (collection != null)
			readArrayList();
		else if ((xmlcollection != null) && (xmltype == "1"))
			readXmlType1();
		StringBuffer localStringBuffer = new StringBuffer("");
		writescript(localStringBuffer);
		int i = 0;
		localStringBuffer.append("<div id=\"" + property
				+ "\" class =\"treeC\" style=\"height:" + height + ";width:"
				+ width + ";overflow:auto;\">");
		ArrayList localArrayList = new ArrayList();
		trastru localtrastru = new trastru();
		treedatastru localtreedatastru = new treedatastru();
		if ((showroot) || (basetreedata.size() != 1)) {
			localtreedatastru.childnode = basetreedata;
			localtreedatastru.nodetext = rootname;
			localtreedatastru.nodeid = "-1";
			localtreedatastru.nodetype = "-1";
			localtreedatastru.value1 = "";
			localtreedatastru.value2 = "";
			localtreedatastru.url = "";
			localArrayList.add(localtreedatastru);
		} else {
			localArrayList = basetreedata;
		}
		localtrastru.number = -1L;
		localStringBuffer
				.append(writenode("", localArrayList, localtrastru, 0).nodeString);
		writenodefoot(localStringBuffer);
		ResponseUtils.write(pageContext, localStringBuffer.toString());
		localStringBuffer = null;
		return 6;
	}

	private void writenodehead(StringBuffer paramStringBuffer, long paramLong1,
			String paramString, long paramLong2) {
		paramStringBuffer.append("<div id=\"n" + paramLong1
				+ "\"  class=\"treeI\"");
		if (showcheckbox == true) {
			if (paramString == null)
				paramString = "0";
			if ((!paramString.equals("1")) && (!paramString.equals("0"))
					&& (!paramString.equals("-1")))
				paramString = "0";
			if (paramString.equals("0"))
				paramStringBuffer.append("checkvalue =\"0\"");
			else if (paramString.equals("1"))
				paramStringBuffer.append("checkvalue =\"1\"");
			else if (paramString.equals("-1"))
				paramStringBuffer.append("checkvalue =\"-1\"");
		}
		if (paramLong2 < expandlevel)
			paramStringBuffer.append(" open=\"1\"> \n");
		else
			paramStringBuffer.append(" open=\"0\"> \n");
	}

	private String writenodeimage(StringBuffer paramStringBuffer,
			String paramString1, String paramString2, boolean paramBoolean1,
			boolean paramBoolean2, long paramLong, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		String str = "";
		int i = 0;
		if ((paramInt > 0) && (paramString2.equals(""))) {
			while (i < paramInt) {
				localStringBuffer.append("<img class=\"icon\" src=\"" + urlpath
						+ imagepath + blankIcon + "\"/>\n");
				i++;
				continue;
			}
		} else {
			localStringBuffer.append(paramString2);
		}
		if (paramBoolean2 == true)
			str = localStringBuffer.toString() + "<img class=\"icon\" src=\""
					+ urlpath + imagepath + iIcon + "\"/>\n";
		else
			str = localStringBuffer.toString() + "<img class=\"icon\" src=\""
					+ urlpath + imagepath + blankIcon + "\"/>\n";
		paramStringBuffer.append(localStringBuffer);
		if (paramBoolean1 == true) {
			if (paramBoolean2 == true) {
				if (paramInt < expandlevel)
					paramStringBuffer.append("<img id=\"n" + paramLong
							+ "i0\"  class=\"icon\" src=\"" + urlpath
							+ imagepath + lMinusIcon + "\" onclick =\"toggle("
							+ property + ",this);\"/> \n");
				else
					paramStringBuffer.append("<img id=\"n" + paramLong
							+ "i0\"  class=\"icon\" src=\"" + urlpath
							+ imagepath + tPlusIcon + "\" onclick =\"toggle("
							+ property + ",this);\"/> \n");
			} else if (paramInt < expandlevel)
				paramStringBuffer.append("<img id=\"n" + paramLong
						+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
						+ lMinusIcon + "\" onclick =\"toggle(" + property
						+ ",this);\"/> \n");
			else
				paramStringBuffer.append("<img id=\"n" + paramLong
						+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
						+ lPlusIcon + "\" onclick =\"toggle(" + property
						+ ",this);\"/> \n");
		} else if (paramBoolean2 == true)
			paramStringBuffer.append("<img id=\"n" + paramLong
					+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
					+ tIcon + "\" onclick =\"toggle(" + property
					+ ",this);\"/> \n");
		else
			paramStringBuffer.append("<img id=\"n" + paramLong
					+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
					+ lIcon + "\" onclick =\"toggle(" + property
					+ ",this);\"/> \n");
		if (paramBoolean1 == true) {
			if (paramInt < expandlevel)
				paramStringBuffer.append("<img id=\"n" + paramLong
						+ "i1\" class=\"icon\" onclick=\"" + property
						+ ".controlfocus(this);" + "toggle(" + property
						+ ",this);" + "\" src=\"" + urlpath + imagepath
						+ openFolderIcon + "\" /> \n");
			else
				paramStringBuffer.append("<img id=\"n" + paramLong
						+ "i1\" class=\"icon\" onclick=\"" + property
						+ ".controlfocus(this);" + "toggle(" + property
						+ ",this);" + "\" src=\"" + urlpath + imagepath
						+ folderIcon + "\"/> \n");
		} else
			paramStringBuffer.append("<img id=\"n" + paramLong
					+ "i1\" class=\"icon\" onclick=\"" + property
					+ ".controlfocus(this);" + "toggle(" + property + ",this);"
					+ "\" src=\"" + urlpath + imagepath + fileIcon + "\"/> \n");
		if (showcheckbox == true) {
			if (paramString1 == null)
				paramString1 = "0";
			if ((!paramString1.equals("1")) && (!paramString1.equals("0"))
					&& (!paramString1.equals("-1")))
				paramString1 = "0";
			if (paramString1.equals("0"))
				paramStringBuffer.append("<img  id=\"n" + paramLong
						+ "i2\" class=\"icon\"  src=\"" + urlpath + imagepath
						+ noIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
			else if (paramString1.equals("1"))
				paramStringBuffer.append("<img  id=\"n" + paramLong
						+ "i2\" class=\"icon\" src=\"" + urlpath + imagepath
						+ selectedIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
			else if (paramString1.equals("-1"))
				paramStringBuffer.append("<img  id=\"n" + paramLong
						+ "i2\" class=\"icon\" src=\"" + urlpath + imagepath
						+ noselectedIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
		}
		return str;
	}

	private void writenodeA(StringBuffer paramStringBuffer,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, long paramLong,
			String paramString6) {
		paramStringBuffer.append("<label id=\"n" + paramLong
				+ "l\" class=\"treeNode\" ");
		if (geturl() != null)
			paramStringBuffer.append(" href='" + paramString6 + "' ");
		if (showtitle)
			paramStringBuffer.append("  title='" + paramString3 + "' ");
		if (getOnclick() != null)
			paramStringBuffer.append(" onclick=\"" + property
					+ ".controlfocus(this)" + ";toggle(" + property + ",this);"
					+ getOnclick() + "\" ");
		else
			paramStringBuffer.append(" onclick=\"" + property
					+ ".controlfocus(this);" + "toggle(" + property
					+ ",this);\"");
		if (getOndblclick() != null)
			paramStringBuffer.append(" ondblclick='" + getOndblclick() + "' ");
		paramStringBuffer.append(" nodeid=\"" + paramString1 + "\" nodetype=\""
				+ paramString2 + "\" nodevalue1=\"" + paramString4
				+ "\" nodevalue2 =\"" + paramString5 + "\">" + paramString3
				+ "</label> \n");
	}

	private void writenodecontenthead(StringBuffer paramStringBuffer,
			long paramLong1, long paramLong2) {
		if (paramLong2 < expandlevel)
			paramStringBuffer.append("\t<div style=\"display:;\" id=\"n"
					+ paramLong1 + "c\"  class=\"treeI\"> \n");
		else
			paramStringBuffer.append("\t<div style=\"display:none;\" id=\"n"
					+ paramLong1 + "c\"  class=\"treeI\"> \n");
	}

	private void writenodefoot(StringBuffer paramStringBuffer) {
		paramStringBuffer.append("</div>\n");
	}

	private void readArrayList() throws JspException {
		ArrayList localArrayList = null;
		Object localObject1 = pageContext.findAttribute(collection);
		if (localObject1 != null) {
			localArrayList = (ArrayList) localObject1;
			Object localObject2 = null;
			Object localObject3 = null;
			Object localObject4 = null;
			Object localObject5 = null;
			Object localObject6 = null;
			Object localObject7 = null;
			Object localObject8 = null;
			Object localObject9 = null;
			Object localObject10 = null;
			Object localObject11 = null;
			for (int i = 0; i < localArrayList.size(); i++) {
				treedatastru localtreedatastru1 = null;
				treedatastru localtreedatastru2 = null;
				localObject1 = localArrayList.get(i);
				try {
					localObject2 = PropertyUtils.getProperty(localObject1,
							nodeid);
					if (localObject2 != null)
						localObject2 = localObject2.toString().trim();
				} catch (Exception localException1) {
					throw new JspException(localException1.toString()
							+ "没有发现节点的id的字段");
				}
				try {
					localObject4 = PropertyUtils.getProperty(localObject1,
							nodetext);
					if (localObject4 != null)
						localObject4 = localObject4.toString().trim();
				} catch (Exception localException2) {
					throw new JspException(localException2.toString()
							+ "没有发现节点的text的字段");
				}
				try {
					localObject5 = PropertyUtils.getProperty(localObject1,
							nodeparentid);
					if (localObject5 != null)
						localObject5 = localObject5.toString().trim();
				} catch (Exception localException3) {
					throw new JspException(localException3.toString()
							+ "没有发现节点的父节点id的字段");
				}
				if (nodetype != null)
					try {
						localObject3 = PropertyUtils.getProperty(localObject1,
								nodetype);
						if (localObject3 != null)
							localObject3 = localObject3.toString().trim();
					} catch (Exception localException4) {
					}
				if (value1 != null)
					try {
						localObject10 = PropertyUtils.getProperty(localObject1,
								value1);
						if (localObject10 != null)
							localObject10 = localObject10.toString().trim();
					} catch (Exception localException5) {
					}
				if (value2 != null)
					try {
						localObject11 = PropertyUtils.getProperty(localObject1,
								value2);
						if (localObject11 != null)
							localObject11 = localObject11.toString().trim();
					} catch (Exception localException6) {
					}
				if (nodeparenttype != null)
					try {
						localObject6 = PropertyUtils.getProperty(localObject1,
								nodeparenttype);
						if (localObject6 != null)
							localObject6 = localObject6.toString().trim();
					} catch (Exception localException7) {
					}
				if (nodechecked != null)
					try {
						localObject9 = PropertyUtils.getProperty(localObject1,
								nodechecked);
						if (nodechecked != null)
							localObject9 = localObject9.toString().trim();
					} catch (Exception localException8) {
					}
				if (url != null)
					try {
						localObject7 = PropertyUtils.getProperty(localObject1,
								url);
						if (localObject7 != null)
							localObject7 = localObject7.toString().trim();
					} catch (Exception localException9) {
					}
				localtreedatastru1 = findparent(basetreedata, localObject5,
						localObject6);
				localtreedatastru2 = new treedatastru();
				localtreedatastru2.nodeid = localObject2;
				localtreedatastru2.nodeimage = localObject8;
				localtreedatastru2.nodeparentid = localObject5;
				localtreedatastru2.nodeparenttype = localObject6;
				localtreedatastru2.nodetext = localObject4;
				localtreedatastru2.nodetype = localObject3;
				localtreedatastru2.url = localObject7;
				localtreedatastru2.checked = localObject9;
				localtreedatastru2.value1 = localObject10;
				localtreedatastru2.value2 = localObject11;
				if (localtreedatastru1 != null)
					localtreedatastru1.childnode.add(localtreedatastru2);
				else
					basetreedata.add(localtreedatastru2);
			}
		}
	}

	private void readXmlType1() {
		try {
			Object localObject = null;
			localObject = pageContext.findAttribute(xmlcollection);
			DocumentBuilder localDocumentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			StringReader localStringReader = new StringReader(
					localObject.toString());
			InputSource localInputSource = new InputSource(localStringReader);
			Document localDocument = localDocumentBuilder
					.parse(localInputSource);
			NodeList localNodeList = localDocument.getChildNodes();
			readnodelist(localNodeList, null);
		} catch (Exception localException) {
		}
	}

	private void readnodelist(NodeList paramNodeList,
			treedatastru paramtreedatastru) {
		Node localNode = null;
		String str1 = null;
		String str2 = null;
		String str3 = null;
		String str4 = null;
		Object localObject = null;
		String str5 = null;
		String str6 = null;
		String str7 = null;
		for (int i = 0; i < paramNodeList.getLength(); i++) {
			localNode = paramNodeList.item(i);
			int j = localNode.getNodeType();
			if (localNode.getNodeType() == 1) {
				int k = paramNodeList.getLength();
				treedatastru localtreedatastru = null;
				str1 = localNode.getAttributes().getNamedItem(nodeid)
						.getNodeValue();
				str3 = localNode.getAttributes().getNamedItem(nodetext)
						.getNodeValue();
				if (nodetype != null)
					str2 = localNode.getAttributes().getNamedItem(nodetype)
							.getNodeValue();
				if (value1 != null)
					str6 = localNode.getAttributes().getNamedItem(value1)
							.getNodeValue();
				if (value2 != null)
					str7 = localNode.getAttributes().getNamedItem(value2)
							.getNodeValue();
				if ((nodechecked != null)
						&& (localNode.getAttributes().getNamedItem(nodechecked) != null))
					str5 = localNode.getAttributes().getNamedItem(nodechecked)
							.getNodeValue();
				if (url != null)
					str4 = localNode.getAttributes().getNamedItem(url)
							.getNodeValue();
				localtreedatastru = new treedatastru();
				localtreedatastru.nodeid = str1;
				localtreedatastru.nodeimage = localObject;
				localtreedatastru.nodetext = str3;
				localtreedatastru.nodetype = str2;
				localtreedatastru.url = str4;
				localtreedatastru.checked = str5;
				localtreedatastru.value1 = str6;
				localtreedatastru.value2 = str7;
				if (paramtreedatastru != null)
					paramtreedatastru.childnode.add(localtreedatastru);
				else
					basetreedata.add(localtreedatastru);
				if (localNode.hasChildNodes()) {
					NodeList localNodeList = localNode.getChildNodes();
					int m = localNodeList.getLength();
					readnodelist(localNodeList, localtreedatastru);
				}
			}
		}
	}

	public int doStartTag() throws JspException {
		checkednodeid = null;
		basetreedata = new ArrayList();
		return 0;
	}

	private class treedatastru {
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

		public treedatastru() {
		}
	}

	private class trastru {
		String nodeString = "";
		long number = 0L;
		String state = "0";

		public trastru() {
		}
	}
}
