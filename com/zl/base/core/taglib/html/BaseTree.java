package com.zl.base.core.taglib.html;

import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.zl.base.core.taglib.ReadTaglibProperty;
import com.zl.util.MethodFactory;

public class BaseTree extends BaseHandlerTag {
	private static final long serialVersionUID = 8291862468002634468L;
	private static final Log log = LogFactory.getLog(BaseTree.class);

	private class TreedataStru {

		public Object nodeid;
		public Object nodetext;
		public Object nodeimage;
		public Object nodeparentid;
		public Object nodetype;
		public Object nodeparenttype;
		public Object url;
		public Object enabled;
		public Object checked;
		public ArrayList childnode;
		public Object value1;
		public Object value2;

		public TreedataStru() {
			nodeid = "";
			nodetext = "";
			nodeimage = "";
			nodeparentid = "";
			nodetype = "";
			nodeparenttype = "";
			url = "";
			enabled = "";
			checked = "";
			childnode = null;
			value1 = "";
			value2 = "";
			childnode = new ArrayList();
		}
	}

	private class TraStru {

		String nodeString;
		long number;
		String state;

		public TraStru() {
			nodeString = "";
			number = 0L;
			state = "0";
		}
	}

	public BaseTree() {
		imagepath = taglibproperty.gettreeimagepath();
		selectedIcon = taglibproperty.gettreeselectedicon();
		noselectedIcon = taglibproperty.gettreenoselectedicon();
		noIcon = taglibproperty.gettreenoicon();
		rootIcon = taglibproperty.gettreerooticon();
		openRootIcon = taglibproperty.gettreeopenrooticon();
		folderIcon = taglibproperty.gettreefoldericon();
		openFolderIcon = taglibproperty.gettreeopenfoldericon();
		fileIcon = taglibproperty.gettreefileicon();
		iIcon = taglibproperty.gettreeiicon();
		lIcon = taglibproperty.gettreelicon();
		lMinusIcon = taglibproperty.gettreelminusicon();
		lPlusIcon = taglibproperty.gettreelplusicon();
		tIcon = taglibproperty.gettreeticon();
		tMinusIcon = taglibproperty.gettreetminusicon();
		tPlusIcon = taglibproperty.gettreetplusicon();
		blankIcon = taglibproperty.gettreeblankicon();
	}

	public boolean getshowtitle() {
		return showtitle;
	}

	public void setshowtitle(boolean flag) {
		showtitle = flag;
	}

	public String getnodechecked() {
		return nodechecked;
	}

	public void setnodechecked(String s) {
		nodechecked = s;
	}

	public String getrootname() {
		return rootname;
	}

	public void setrootname(String s) {
		rootname = s;
	}

	public int getexpandlevel() {
		return expandlevel;
	}

	public void setexpandlevel(int i) {
		expandlevel = i;
	}

	public boolean getshowroot() {
		return showroot;
	}

	public void setshowroot(boolean flag) {
		showroot = flag;
	}

	public String getvalue1() {
		return value1;
	}

	public void setvalue1(String s) {
		value1 = s;
	}

	public String getvalue2() {
		return value2;
	}

	public void setvalue2(String s) {
		value2 = s;
	}

	public String getproperty() {
		return property;
	}

	public void setproperty(String s) {
		if (s != null) {
			if (s.equals(""))
				property = "tree";
			else
				property = s;
		} else {
			property = "tree";
		}
		property = property.trim();
	}

	public String getheight() {
		return height;
	}

	public void setheight(String s) {
		height = s;
	}

	public String getwidth() {
		return width;
	}

	public void setwidth(String s) {
		width = s;
	}

	public void setshowcheckbox(boolean flag) {
		showcheckbox = flag;
	}

	public boolean getshowcheckbox() {
		return showcheckbox;
	}

	public void setcollection(String s) {
		collection = s;
	}

	public String getcollection() {
		return collection;
	}

	public String getenabled() {
		return enabled;
	}

	public void setenabled(String s) {
		enabled = s;
	}

	public String getnodeid() {
		return nodeid;
	}

	public void setnodeid(String s) {
		nodeid = s;
	}

	public String getnodetext() {
		return nodetext;
	}

	public void setnodetext(String s) {
		nodetext = s;
	}

	public String getnodeimage() {
		return nodeimage;
	}

	public void setnodeimage(String s) {
		nodeimage = s;
	}

	public String getnodeparentid() {
		return nodeparentid;
	}

	public void setnodeparentid(String s) {
		nodeparentid = s;
	}

	public String getnodetype() {
		return nodetype;
	}

	public void setnodetype(String s) {
		nodetype = s;
	}

	public String getnodeparenttype() {
		return nodeparenttype;
	}

	public void setnodeparenttype(String s) {
		nodeparenttype = s;
	}

	public String geturl() {
		return url;
	}

	public void seturl(String s) {
		url = s;
	}

	public String getxmltype() {
		return xmltype;
	}

	public void setxmltype(String s) {
		xmltype = s;
	}

	public String getxmlcollection() {
		return xmlcollection;
	}

	public void setxmlcollection(String s) {
		xmlcollection = s;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		// TODO doStartTag()
		checkednodeid = null;
		basetreedata = new ArrayList();
		return 0;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		// TODO doEndTag()
		// 取得工程名称(/myutils/)
		urlpath = ((HttpServletRequest) pageContext.getRequest())
				.getContextPath() + "/";
		if (collection != null) {
			// 使用数据集作为数据源
			readArrayList();
		} else if (xmlcollection != null && xmltype == "1") {
			// 使用xml字符串作为数据源
			readXmlType1();
		}
		StringBuffer stringbuffer = new StringBuffer("");
		// js01
		writescript(stringbuffer);

		boolean flag = false;
		stringbuffer.append("<div id=\"" + property
				+ "\" class =\"treeC\" style=\"height:" + height + ";width:"
				+ width + ";overflow:auto;\">");

		ArrayList arraylist = new ArrayList();
		TraStru TraStru1 = new TraStru();
		TreedataStru TreedataStru1 = new TreedataStru();

		if (showroot || basetreedata.size() != 1) {
			TreedataStru1.childnode = basetreedata;
			TreedataStru1.nodetext = rootname;
			TreedataStru1.nodeid = "-1";
			TreedataStru1.nodetype = "-1";
			TreedataStru1.value1 = "";
			TreedataStru1.value2 = "";
			TreedataStru1.url = "";
			arraylist.add(TreedataStru1);
		} else {
			arraylist = basetreedata;
		}
		TraStru1.number = -1L;
		stringbuffer.append(writenode("", arraylist, TraStru1, 0).nodeString);
		writenodefoot(stringbuffer);
		ResponseUtils.write(pageContext, stringbuffer.toString());
		stringbuffer = null;
		return 6;
	}

	/**
	 * 打印JavaScript函数
	 *
	 * @param stringbuffer
	 */
	private void writescript(StringBuffer stringbuffer) {
		stringbuffer.append("<!-- ----js01 Begin---- -->\n");
		stringbuffer.append("<script language=\"javascript\"> \n");
		stringbuffer.append("var " + property + " = { \n");
		stringbuffer.append("imagepath:\"" + urlpath + imagepath + "\", \n");
		stringbuffer.append("selectedIcon:\"" + selectedIcon + "\", \n");
		stringbuffer.append("noselectedIcon:\"" + noselectedIcon + "\", \n");
		stringbuffer.append("noIcon:\"" + noIcon + "\", \n");
		stringbuffer.append("rootIcon:\"" + rootIcon + "\", \n");
		stringbuffer.append("openRootIcon:\"" + openRootIcon + "\", \n");
		stringbuffer.append("folderIcon:\"" + folderIcon + "\", \n");
		stringbuffer.append("openFolderIcon:\"" + openFolderIcon + "\", \n");
		stringbuffer.append("fileIcon:\"" + fileIcon + "\", \n");
		stringbuffer.append("lMinusIcon:\"" + lMinusIcon + "\", \n");
		stringbuffer.append("lPlusIcon:\"" + lPlusIcon + "\", \n");
		stringbuffer.append("tMinusIcon:\"" + tMinusIcon + "\", \n");
		stringbuffer.append("tPlusIcon:\"" + tPlusIcon + "\", \n");
		stringbuffer.append("\tobjectname:\"" + property + "\", \n");
		stringbuffer.append("\tshowcheckbox:" + showcheckbox + ", \n");
		stringbuffer.append("\tobjfocus:null, \n");
		stringbuffer.append("\tsplitstring:\",\", \n");
		stringbuffer.append("\t//读取选中的系统编码 \n");
		/* getselectid */
		stringbuffer.append("\tgetselectid:function(nodetype,state){ \n");
		stringbuffer.append("if(this.showcheckbox==false)\n");
		stringbuffer
				.append("          return getvaluebytype2(this.objfocus,\"id\");\n");
		stringbuffer.append("else\n");
		stringbuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"id\",this.splitstring,state); \n");
		stringbuffer.append("\t}, \n");

		/* getselecttype */
		stringbuffer.append("\tgetselecttype:function(nodetype,state){ \n");
		stringbuffer.append("if(this.showcheckbox==false)\n");
		stringbuffer
				.append("          return getvaluebytype2(this.objfocus,\"type\");\n");
		stringbuffer.append("else\n");
		stringbuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"type\",this.splitstring,state); \n");
		stringbuffer.append("\t}, \n");

		/* getselectvalue1 */
		stringbuffer.append("\tgetselectvalue1:function(nodetype,state){ \n");
		stringbuffer.append("if(this.showcheckbox==false)\n");
		stringbuffer
				.append("          return getvaluebytype2(this.objfocus,\"value1\");\n");
		stringbuffer.append("else\n");
		stringbuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"value1\",this.splitstring,state); \n");
		stringbuffer.append("\t}, \n");

		/* getselectvalue2 */
		stringbuffer.append("\tgetselectvalue2:function(nodetype,state){ \n");
		stringbuffer.append("if(this.showcheckbox==false)\n");
		stringbuffer
				.append("          return getvaluebytype2(this.objfocus,\"value2\");\n");
		stringbuffer.append("else\n");
		stringbuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"value2\",this.splitstring,state); \n");
		stringbuffer.append("\t}, \n");

		/* getselecttext */
		stringbuffer.append("\tgetselecttext:function(nodetype,state){ \n");
		stringbuffer.append("if(this.showcheckbox==false)\n");
		stringbuffer
				.append("          return getvaluebytype2(this.objfocus,\"text\");\n");
		stringbuffer.append("else\n");
		stringbuffer
				.append("\t\treturn getselectedid(this.objectname,nodetype,\"text\",this.splitstring,state); \n");
		stringbuffer.append("\t}, \n");

		/* setselectid */
		stringbuffer.append("\tsetselectid:function (nodeid,nodetype){ \n");
		stringbuffer
				.append("\t\tsetselectedid(this,nodeid,nodetype,this.splitstring); \n");
		stringbuffer.append("\t}, \n");

		/*
		 * controlonblur 节点失去焦点
		 */
		stringbuffer.append("\tcontrolonblur:function(objimg){ \n");
		stringbuffer.append("\t\tobjimg.className=\"selected-inactive\"; \n");
		stringbuffer.append("\t\treturn \"\"; \n");
		stringbuffer.append("\t}, \n");

		/*
		 * controlfocus 节点获得焦点
		 */
		stringbuffer.append("\tcontrolfocus:function(objTemp){ \n");
		stringbuffer.append("\t\tvar objimg; \n");
		stringbuffer.append("\t\tvar id; \n");
		stringbuffer.append("\t\tif(this.objfocus!=null){ \n");
		stringbuffer.append("\t\t\tthis.objfocus.className=\"\"; \n");
		stringbuffer.append("\t\t} \n");
		stringbuffer.append("\t\tobjTemp=objTemp.parentElement; \n");
		stringbuffer.append("\t\tid=objTemp.id; \n");
		stringbuffer.append("\t\tobjTemp = objTemp.all[id+\"l\"]; \n");
		stringbuffer.append("\t\tobjTemp.focus(); \n");
		stringbuffer.append("\t\tthis.objfocus= objTemp;\n");
		stringbuffer.append("\t\treturn \"\"; \n");
		stringbuffer.append("\t}, \n");

		/* checkselectall */
		stringbuffer.append("checkselectall:function(){\n");
		stringbuffer.append("if(this.showcheckbox==true)\n");
		stringbuffer.append("return checkselectall(this.objectname);\n");
		stringbuffer.append("else\n");
		stringbuffer.append("return false;\n");
		stringbuffer.append("},\n");

		/* getnodetype */
		stringbuffer.append("getnodetype:function(nodetype){\n");
		stringbuffer.append("   return this.getselecttype(nodetype);\n");
		stringbuffer.append("},\n");

		/* getnodeid */
		stringbuffer.append("getnodeid:function(nodetype){\n");
		stringbuffer.append("   return  this.getselectid(nodetype);\n");
		stringbuffer.append("},\n");

		/* getnodetext */
		stringbuffer.append("getnodetext:function(nodetype){\n");
		stringbuffer.append("   return  this.getselecttext(nodetype);\n");
		stringbuffer.append("},\n");

		/* getnodevalue1 */
		stringbuffer.append("getnodevalue1:function(nodetype){\n");
		stringbuffer.append("   return  this.getselectvalue1(nodetype);\n");
		stringbuffer.append("},\n");

		/* getnodevalue2 */
		stringbuffer.append("getnodevalue2:function(nodetype){\n");
		stringbuffer.append("   return this.getselectvalue2(nodetype);\n");
		stringbuffer.append("},\n");

		/* getchildcount */
		stringbuffer.append("getchildcount:function(){\n");
		stringbuffer.append("   return getchildcount(this.objfocus);\n");
		stringbuffer.append("},\n");

		/* getvalue2 */
		stringbuffer.append("getvalue2:function(nodetype){\n");
		stringbuffer.append("   return this.getnodevalue2(nodetype);\n");
		stringbuffer.append("},\n");

		/* getvalue1 */
		stringbuffer.append("getvalue1:function(nodetype){\n");
		stringbuffer.append("   return this.getnodevalue1(nodetype);\n");
		stringbuffer.append("}\n");
		//
		stringbuffer.append("}; \n");
		stringbuffer.append("</script> \n");
		stringbuffer.append("<!-- ----js01 End---- -->\n");
	}

	/**
	 * 打印结节信息
	 *
	 * @param paramString
	 * @param paramArrayList
	 * @param paramTraStru
	 * @param paramInt
	 * @return
	 */
	private TraStru writenode(String paramString, ArrayList paramArrayList,
			TraStru paramTraStru, int paramInt) {
		// TODO writenode()
		String str1 = null;
		TreedataStru localTreedataStru = null;
		StringBuffer localStringBuffer1 = new StringBuffer("");
		boolean bool1 = false;
		boolean bool2 = false;
		int i = 0;
		int j = 0;
		String str2 = "0";
		String str3 = "1";
		for (i = 0; i < paramArrayList.size(); i++) {
			paramTraStru.number += 1L;
			localTreedataStru = (TreedataStru) paramArrayList.get(i);
			StringBuffer localStringBuffer2 = new StringBuffer("");
			TraStru localTraStru = new TraStru();
			localTraStru.number = paramTraStru.number;
			if (this.showcheckbox == true) {
				str2 = MethodFactory.getThisString(localTreedataStru.checked);
				if (str2 == null)
					str2 = "0";
				if ((!str2.equals("1")) && (!str2.equals("0"))
						&& (!str2.equals("-1")))
					str2 = "0";
				localTraStru.state = str2;
			}
			if (i == paramArrayList.size() - 1)
				bool2 = false;
			else
				bool2 = true;
			str1 = writenodeimage(localStringBuffer2, str2, paramString, bool1,
					bool2, paramTraStru.number, paramInt);
			if (localTreedataStru.childnode.size() > 0) {
				localTraStru = writenode(str1, localTreedataStru.childnode,
						localTraStru, paramInt + 1);
				bool1 = true;
			} else {
				bool1 = false;
			}
			if (this.showcheckbox == true) {
				if (localTreedataStru.childnode.size() > 0)
					str2 = localTraStru.state;
				if (i == 0)
					str3 = str2;
				else if (!str3.equals(str2))
					str3 = "-1";
			}
			writenodehead(localStringBuffer1, paramTraStru.number, str2,
					paramInt);
			str1 = writenodeimage(localStringBuffer1, str2, paramString, bool1,
					bool2, paramTraStru.number, paramInt);
			writenodeA(localStringBuffer1,
					MethodFactory.getThisString(localTreedataStru.nodeid),
					MethodFactory.getThisString(localTreedataStru.nodetype),
					MethodFactory.getThisString(localTreedataStru.nodetext),
					MethodFactory.getThisString(localTreedataStru.value1),
					MethodFactory.getThisString(localTreedataStru.value2),
					paramTraStru.number,
					MethodFactory.getThisString(localTreedataStru.url));
			if (localTreedataStru.childnode.size() > 0) {
				writenodecontenthead(localStringBuffer1, paramTraStru.number,
						paramInt);
				localStringBuffer1.append(localTraStru.nodeString);
				writenodefoot(localStringBuffer1);
			}
			writenodefoot(localStringBuffer1);
			paramTraStru.number = localTraStru.number;
		}
		paramTraStru.state = str3;
		paramTraStru.nodeString = localStringBuffer1.toString();
		return paramTraStru;
	}

	private TreedataStru findparent(ArrayList arraylist, Object obj, Object obj1) {
		TreedataStru TreedataStru1 = null;
		if (arraylist != null) {
			int i = 0;
			Object obj2 = null;
			i = 0;
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

	// /**
	// *
	// * @param s
	// * @param s1
	// */
	// private void addchecknode(String s, String s1) {
	// if (nodechecked != null
	// && s1 != null
	// && (s1.trim().equals("1") || s1.toLowerCase().trim()
	// .equals("true")))
	// if (checkednodeid == null)
	// checkednodeid = s;
	// else
	// checkednodeid = checkednodeid + "," + s;
	// }

	private void writenodehead(StringBuffer stringbuffer, long l, String s,
			long l1) {
		stringbuffer.append("<div id=\"n" + l
				+ "\"  class=\"treeI\" style =\"cursor:hand\"");
		if (showcheckbox) {
			if (s == null)
				s = "0";
			if (!s.equals("1") && !s.equals("0") && !s.equals("-1"))
				s = "0";
			if (s.equals("0"))
				stringbuffer.append("checkvalue =\"0\"");
			else if (s.equals("1"))
				stringbuffer.append("checkvalue =\"1\"");
			else if (s.equals("-1"))
				stringbuffer.append("checkvalue =\"-1\"");
		}
		if (l1 < (long) expandlevel)
			stringbuffer.append(" open=\"1\"> \n");
		else
			stringbuffer.append(" open=\"0\"> \n");
	}

	private String writenodeimage(StringBuffer stringbuffer, String s,
			String s1, boolean flag, boolean flag1, long l, int i) {
		StringBuffer stringbuffer1 = new StringBuffer();
		String s2 = "";
		if (i > 0 && s1.equals("")) {
			for (int j = 0; j < i; j++)
				stringbuffer1.append("<img class=\"icon\" src=\"" + urlpath
						+ imagepath + blankIcon + "\"/>\n");

		} else {
			stringbuffer1.append(s1);
		}
		if (flag1)
			s2 = stringbuffer1.toString() + "<img class=\"icon\" src=\""
					+ urlpath + imagepath + iIcon + "\"/>\n";
		else
			s2 = stringbuffer1.toString() + "<img class=\"icon\" src=\""
					+ urlpath + imagepath + blankIcon + "\"/>\n";
		stringbuffer.append(stringbuffer1);
		if (flag) {
			if (flag1) {
				if (i < expandlevel)
					stringbuffer.append("<img id=\"n" + l
							+ "i0\"  class=\"icon\" src=\"" + urlpath
							+ imagepath + lMinusIcon + "\" onclick =\"toggle("
							+ property + ",this);\"/> \n");
				else
					stringbuffer.append("<img id=\"n" + l
							+ "i0\"  class=\"icon\" src=\"" + urlpath
							+ imagepath + tPlusIcon + "\" onclick =\"toggle("
							+ property + ",this);\"/> \n");
			} else if (i < expandlevel)
				stringbuffer.append("<img id=\"n" + l
						+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
						+ lMinusIcon + "\" onclick =\"toggle(" + property
						+ ",this);\"/> \n");
			else
				stringbuffer.append("<img id=\"n" + l
						+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
						+ lPlusIcon + "\" onclick =\"toggle(" + property
						+ ",this);\"/> \n");
		} else if (flag1)
			stringbuffer.append("<img id=\"n" + l
					+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
					+ tIcon + "\" onclick =\"toggle(" + property
					+ ",this);\"/> \n");
		else
			stringbuffer.append("<img id=\"n" + l
					+ "i0\"  class=\"icon\" src=\"" + urlpath + imagepath
					+ lIcon + "\" onclick =\"toggle(" + property
					+ ",this);\"/> \n");
		if (flag) {
			if (i < expandlevel)
				stringbuffer.append("<img id=\"n" + l
						+ "i1\" class=\"icon\" onclick=\"" + property
						+ ".controlfocus(this);" + "toggle(" + property
						+ ",this);" + "\" src=\"" + urlpath + imagepath
						+ openFolderIcon + "\" /> \n");
			else
				stringbuffer.append("<img id=\"n" + l
						+ "i1\" class=\"icon\" onclick=\"" + property
						+ ".controlfocus(this);" + "toggle(" + property
						+ ",this);" + "\" src=\"" + urlpath + imagepath
						+ folderIcon + "\"/> \n");
		} else {
			stringbuffer.append("<img id=\"n" + l
					+ "i1\" class=\"icon\" onclick=\"" + property
					+ ".controlfocus(this);" + "toggle(" + property + ",this);"
					+ "\" src=\"" + urlpath + imagepath + fileIcon + "\"/> \n");
		}
		if (showcheckbox) {
			if (s == null)
				s = "0";
			if (!s.equals("1") && !s.equals("0") && !s.equals("-1"))
				s = "0";
			if (s.equals("0"))
				stringbuffer.append("<img  id=\"n" + l
						+ "i2\" class=\"icon\"  src=\"" + urlpath + imagepath
						+ noIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
			else if (s.equals("1"))
				stringbuffer.append("<img  id=\"n" + l
						+ "i2\" class=\"icon\" src=\"" + urlpath + imagepath
						+ selectedIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
			else if (s.equals("-1"))
				stringbuffer.append("<img  id=\"n" + l
						+ "i2\" class=\"icon\" src=\"" + urlpath + imagepath
						+ noselectedIcon + "\" onclick=\"" + property
						+ ".controlfocus(this);clickselect(" + property
						+ ",this);\"/> \n");
		}
		return s2;
	}

	private void writenodeA(StringBuffer stringbuffer, String s, String s1,
			String s2, String s3, String s4, long l, String s5) {
		stringbuffer.append("<a id=\"n" + l + "l\"  class =\"\" onblur=\""
				+ property + ".controlonblur(this);\" ");
		if (s5.equals(""))
			stringbuffer.append(" href='javascript:void(0)' ");
		else
			stringbuffer.append(" href='" + s5 + "' ");
		if (showtitle)
			stringbuffer.append("  title='" + s2 + "' ");
		if (getOnclick() != null)
			stringbuffer.append(" onclick=\"" + property
					+ ".controlfocus(this)" + ";toggle(" + property + ",this);"
					+ getOnclick() + "\" ");
		else
			stringbuffer.append(" onclick=\"" + property
					+ ".controlfocus(this);" + "toggle(" + property
					+ ",this);\"");
		if (getOndblclick() != null)
			stringbuffer.append(" ondblclick='" + getOndblclick() + "' ");
		stringbuffer.append(" nodeid=\"" + s + "\" nodetype=\"" + s1
				+ "\" nodevalue1=\"" + s3 + "\" nodevalue2 =\"" + s4 + "\">"
				+ s2 + "</a> \n");
	}

	private void writenodecontenthead(StringBuffer stringbuffer, long l, long l1) {
		if (l1 < (long) expandlevel)
			stringbuffer.append("\t<div style=\"display:;\" id=\"n" + l
					+ "c\"  class=\"treeI\"> \n");
		else
			stringbuffer.append("\t<div style=\"display:none;\" id=\"n" + l
					+ "c\"  class=\"treeI\"> \n");
	}

	private void writenodefoot(StringBuffer stringbuffer) {
		stringbuffer.append("</div>\n");
	}

	/**
	 * 读取ArrayList数据集
	 *
	 * @throws JspException
	 */
	private void readArrayList() throws JspException {
		// TODO readArrayList()
		Object obj1 = pageContext.findAttribute(collection);
		if (obj1 != null) {
			ArrayList arraylist = (ArrayList) obj1;
			Object obj2 = null;
			Object obj4 = null;
			Object obj5 = null;
			Object obj7 = null;
			Object obj9 = null;
			Object obj10 = null;
			Object obj11 = null;
			Object obj12 = null;
			Object obj13 = null;
			Object obj14 = null;
			Object obj15 = null;
			for (int i = 0; i < arraylist.size(); i++) {
				TreedataStru TreedataStru1 = null;
				TreedataStru TreedataStru2 = null;
				obj2 = arraylist.get(i);
				// try {
				// Map<String, Object> dto = BeanUtils.describe(obj2);
				// log.error(dto.toString()+"========"); // TODO Test
				// } catch (Exception e1) {
				// log.error(e1);
				// }
				try {
					obj4 = PropertyUtils.getProperty(obj2, nodeid);
					if (obj4 != null)
						obj4 = obj4.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString() + "没有发现节点的id字段");
				}

				try {
					obj7 = PropertyUtils.getProperty(obj2, nodetext);
					if (obj7 != null)
						obj7 = obj7.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString() + "没有发现节点的text字段");
				}

				try {
					obj9 = PropertyUtils.getProperty(obj2, nodeparentid);
					if (obj9 != null)
						obj9 = obj9.toString().trim();
				} catch (Exception e) {
					throw new JspException(e.toString()
							+ "没有发现节点的nodeparentid字段");
				}
				if (nodetype != null)
					try {
						obj5 = PropertyUtils.getProperty(obj2, nodetype);
						if (obj5 != null)
							obj5 = obj5.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodetype字段");
					}
				if (value1 != null)
					try {
						obj14 = PropertyUtils.getProperty(obj2, value1);
						if (obj14 != null)
							obj14 = obj14.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的value1字段");
					}
				if (value2 != null)
					try {
						obj15 = PropertyUtils.getProperty(obj2, value2);
						if (obj15 != null)
							obj15 = obj15.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的value2字段");
					}
				if (nodeparenttype != null)
					try {
						obj10 = PropertyUtils.getProperty(obj2, nodeparenttype);
						if (obj10 != null)
							obj10 = obj10.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString()
								+ "没有发现节点的nodeparenttype字段");
					}
				if (nodechecked != null)
					try {
						obj13 = PropertyUtils.getProperty(obj2, nodechecked);
						if (nodechecked != null)
							obj13 = obj13.toString().trim();
					} catch (Exception e) {
						log.debug(e + "没有发现节点的nodechecked字段");
					}
				if (url != null)
					try {
						obj11 = PropertyUtils.getProperty(obj2, url);
						if (obj11 != null)
							obj11 = obj11.toString().trim();
					} catch (Exception e) {
						throw new JspException(e.toString() + "没有发现节点的url字段");
					}
				//
				TreedataStru1 = findparent(basetreedata, obj9, obj10);
				TreedataStru2 = new TreedataStru();
				TreedataStru2.nodeid = obj4;
				TreedataStru2.nodeimage = obj12;
				TreedataStru2.nodeparentid = obj9;
				TreedataStru2.nodeparenttype = obj10;
				TreedataStru2.nodetext = obj7;
				TreedataStru2.nodetype = obj5;
				TreedataStru2.url = obj11;
				TreedataStru2.checked = obj13;
				TreedataStru2.value1 = obj14;
				TreedataStru2.value2 = obj15;
				if (TreedataStru1 != null)
					TreedataStru1.childnode.add(TreedataStru2);
				else
					basetreedata.add(TreedataStru2);
			}

		}
	}

	/**
	 * 读取xml字符串数据
	 */
	private void readXmlType1() {
		try {
			Object obj = null;
			obj = pageContext.findAttribute(xmlcollection);
			DocumentBuilder documentbuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			StringReader stringreader = new StringReader(obj.toString());
			InputSource inputsource = new InputSource(stringreader);
			Document document = documentbuilder.parse(inputsource);
			NodeList nodelist = document.getChildNodes();
			readnodelist(nodelist, null);
		} catch (Exception exception) {
		}
	}

	private void readnodelist(NodeList nodelist, TreedataStru TreedataStru1) {
		Object obj3 = null;
		String s1 = null;
		String s3 = null;
		String s4 = null;
		String s5 = null;
		String s6 = null;
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			if (node.getNodeType() != 1)
				continue;
			TreedataStru TreedataStru2 = null;
			String s = node.getAttributes().getNamedItem(nodeid).getNodeValue();
			String s2 = node.getAttributes().getNamedItem(nodetext)
					.getNodeValue();
			if (nodetype != null)
				s1 = node.getAttributes().getNamedItem(nodetype).getNodeValue();
			if (value1 != null)
				s5 = node.getAttributes().getNamedItem(value1).getNodeValue();
			if (value2 != null)
				s6 = node.getAttributes().getNamedItem(value2).getNodeValue();
			if (nodechecked != null
					&& node.getAttributes().getNamedItem(nodechecked) != null)
				s4 = node.getAttributes().getNamedItem(nodechecked)
						.getNodeValue();
			if (url != null)
				s3 = node.getAttributes().getNamedItem(url).getNodeValue();
			TreedataStru2 = new TreedataStru();
			TreedataStru2.nodeid = s;
			TreedataStru2.nodeimage = obj3;
			TreedataStru2.nodetext = s2;
			TreedataStru2.nodetype = s1;
			TreedataStru2.url = s3;
			TreedataStru2.checked = s4;
			TreedataStru2.value1 = s5;
			TreedataStru2.value2 = s6;
			if (TreedataStru1 != null)
				TreedataStru1.childnode.add(TreedataStru2);
			else
				basetreedata.add(TreedataStru2);
			if (node.hasChildNodes()) {
				NodeList nodelist1 = node.getChildNodes();
				int k = nodelist1.getLength();
				readnodelist(nodelist1, TreedataStru2);
			}
		}

	}

	private ReadTaglibProperty taglibproperty = ReadTaglibProperty
			.getInstance();
	private String urlpath = "";
	private String imagepath;
	private String selectedIcon;
	private String noselectedIcon;
	private String noIcon;
	private String rootIcon;
	private String openRootIcon;
	private String folderIcon;
	private String openFolderIcon;
	private String fileIcon;
	private String iIcon;
	private String lIcon;
	private String lMinusIcon;
	private String lPlusIcon;
	private String tIcon;
	private String tMinusIcon;
	private String tPlusIcon;
	private String blankIcon;
	//
	private String nodeid;
	private String nodetext;
	private String nodeimage;
	private String nodeparentid;
	private String nodetype;
	private String nodeparenttype;
	private String nodechecked;
	private String checkednodeid;
	private String url;
	private String value1;
	private String value2;
	private String enabled;
	private String rootname = "root";
	private String property = "tree";
	private String width = "100%";
	private String height = "100%";
	private int expandlevel = 1;
	private boolean showroot = true;
	private boolean showtitle = false;
	private boolean showcheckbox = false;
	private ArrayList basetreedata = null;
	private String collection = null;
	private String xmlcollection = null;
	private String xmltype = "1";// 使用xml字符串作为数据源

}
