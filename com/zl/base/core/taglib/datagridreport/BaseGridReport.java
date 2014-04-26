package com.zl.base.core.taglib.datagridreport;

import static com.zl.util.MethodFactory.print;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.taglib.ReadTaglibProperty;
import com.zl.base.core.util.StringBufferTool;
import com.zl.util.MethodFactory;

public class BaseGridReport extends BaseHandlerTag {
	private static final Logger log = Logger.getLogger(BaseGridReport.class);
	private ReadTaglibProperty taglibproperty = ReadTaglibProperty
			.getInstance();
	private String gridExpandImage = "public/images/expand.gif";
	private String gridShrinkImage = "public/images/shrink.gif";
	private String gridSpaceImage = "public/images/space.gif";
	private String splitterstring = "##";
	protected Vector<Object> columns = new Vector<Object>();
	private String headbgcolor = "#5AA3D4";
	private String bordercolor = "#0294C7";
	private String bgcolor = "#EFF5F9";
	private String rowcount = null;
	// private int footerheight = 20;
	private String linecolor = "#736A59";
	private String caption = null;
	private String selectcolor = null;
	private String idproperty = null;
	private String bgcolorcol = null;
	private String fontcolorcol = null;
	private int fixtablewidth = 0;
	private int fixcolnumber = 0;
	private int contentcolnumber = 0;
	private boolean serial = false;
	private int tablewidth = 0;
	private int rowheight = 20;
	private int headRowCount = 1;
	private String colheadgroup = "";
	private String colgroup = "";
	private String Fixcolgroup = "";
	private String Fixcolheadgroup = "";
	private int rstcount = 0;
	private boolean showmenu = true;
	private boolean showcheckbox = false;
	private int funtype = 0;
	// private String propertytype = "0";
	private String rowheadbgcolor = taglibproperty.getgridrowheadbgcolor();
	private String reportcondition = "";
	private ArrayList<String> rowVisible;
	private boolean allowchangecolwidth = false;
	private boolean expand = false;
	private int showrowcount = -1;
	private String mergetype = "0";
	private String checkvalueproperty = null;
	private String rowsplitstring = ";";
	private String colsplitstring = ",";
	private boolean showtiptext = false;
	private boolean merge = false;
	private String onrowchange = null;
	private String reportcaption = null;
	protected String property = null;
	protected String collection = null;
	private boolean showtotal = false;
	private String totalcollection = null;
	protected String oldStyleClass = "tableDataHit";
	protected String newStyleClass = "tableData";
	protected String height = "200";
	protected String width = "400";
	protected boolean cansort = true;
	private int mergecolcount = 0;

	public boolean getallowchangecolwidth() {
		return this.allowchangecolwidth;
	}

	public void setallowchangecolwidth(boolean paramBoolean) {
		this.allowchangecolwidth = paramBoolean;
	}

	public String getreportcondition() {
		return this.reportcondition;
	}

	public boolean getexpand() {
		return this.expand;
	}

	public void setexpand(boolean paramBoolean) {
		this.expand = paramBoolean;
	}

	public void setreportcondition(String paramString) {
		this.reportcondition = paramString;
	}

	public int getfuntype() {
		return this.funtype;
	}

	public int getshowrowcount() {
		return this.showrowcount;
	}

	public void setshowrowcount(int paramInt) {
		if (paramInt <= 0)
			this.showrowcount = -1;
		else
			this.showrowcount = paramInt;
	}

	public void setfuntype(int paramInt) {
		this.funtype = paramInt;
	}

	public String getmergetype() {
		return this.mergetype;
	}

	public void setmergetype(String paramString) {
		this.mergetype = paramString;
	}

	public String getcheckvalueproperty() {
		return this.checkvalueproperty;
	}

	public void setcheckvalueproperty(String paramString) {
		this.checkvalueproperty = paramString;
	}

	public String getrowsplitstring() {
		return this.rowsplitstring;
	}

	public void setrowsplitstring(String paramString) {
		this.rowsplitstring = paramString;
	}

	public String getcolsplitstring() {
		return this.colsplitstring;
	}

	public void setcolsplitstring(String paramString) {
		this.colsplitstring = paramString;
	}

	public boolean getshowtiptext() {
		return this.showtiptext;
	}

	public void setshowtiptext(boolean paramBoolean) {
		this.showtiptext = paramBoolean;
	}

	public boolean getshowcheckbox() {
		return this.showcheckbox;
	}

	public void setshowcheckbox(boolean paramBoolean) {
		this.showcheckbox = paramBoolean;
	}

	public boolean getshowmenu() {
		return this.showmenu;
	}

	public void setshowmenu(boolean paramBoolean) {
		this.showmenu = paramBoolean;
	}

	public boolean getmerge() {
		return this.merge;
	}

	public void setmerge(boolean paramBoolean) {
		this.merge = paramBoolean;
	}

	public void setrowheadbgcolor(String paramString) {
		this.rowheadbgcolor = paramString;
	}

	public String getrowheadbgcolor() {
		return this.rowheadbgcolor;
	}

	public String getonrowchange() {
		return this.onrowchange;
	}

	public void setonrowchange(String paramString) {
		this.onrowchange = paramString;
	}

	public String getreportcaption() {
		return this.reportcaption;
	}

	public void setreportcaption(String paramString) {
		this.reportcaption = paramString;
	}

	public int getrowheight() {
		return this.rowheight;
	}

	public void setrowheight(int paramInt) {
		// if (paramInt > 0)
		// this.rowheight = paramInt;
		// else
		// this.rowheight = this.rowheight;
		if (paramInt > 0) {
			this.rowheight = paramInt;
		}
	}

	public boolean getserial() {
		return this.serial;
	}

	public void setserial(boolean paramBoolean) {
		this.serial = paramBoolean;
	}

	public void setbgcolorcol(String paramString) {
		this.bgcolorcol = paramString;
	}

	public String getbgcolorcol() {
		return this.bgcolorcol;
	}

	public void setfontcolorcol(String paramString) {
		this.fontcolorcol = paramString;
	}

	public String getfontcolorcol() {
		return this.fontcolorcol;
	}

	public void setidproperty(String paramString) {
		this.idproperty = paramString;
	}

	public String getidproperty() {
		return this.idproperty;
	}

	public void setselectcolor(String paramString) {
		this.selectcolor = paramString;
	}

	public String getselectcolor() {
		return this.selectcolor;
	}

	public void setcaption(String paramString) {
		this.caption = paramString;
	}

	public String getcaption() {
		return this.caption;
	}

	public void setlinecolor(String paramString) {
		this.linecolor = paramString;
	}

	public String getlinecolor() {
		return this.linecolor;
	}

	public void setrowcount(String paramString) {
		this.rowcount = paramString;
	}

	public String getrowcount() {
		return this.rowcount;
	}

	public void setbordercolor(String paramString) {
		this.bordercolor = paramString;
	}

	public String getbordercolor() {
		return this.bordercolor;
	}

	public void setbgcolor(String paramString) {
		this.bgcolor = paramString;
	}

	public String getbgcolor() {
		return this.bgcolor;
	}

	public void setheadbgcolor(String paramString) {
		this.headbgcolor = paramString;
	}

	public String getheadbgcolor() {
		return this.headbgcolor;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		if (paramString == null)
			this.property = "sysname";
		else if (paramString.trim() == "")
			this.property = "sysname";
		else
			this.property = paramString.trim();
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String paramString) {
		this.collection = paramString;
	}

	public boolean getshowtotal() {
		return this.showtotal;
	}

	public void setshowtotal(boolean paramBoolean) {
		this.showtotal = paramBoolean;
	}

	public String gettotalcollection() {
		return this.totalcollection;
	}

	public void settotalcollection(String paramString) {
		this.totalcollection = paramString;
	}

	public String getOldStyleClass() {
		return this.oldStyleClass;
	}

	public void setOldStyleClass(String paramString) {
		this.oldStyleClass = paramString;
	}

	public String getNewStyleClass() {
		return this.newStyleClass;
	}

	public void setNewStyleClass(String paramString) {
		this.newStyleClass = paramString;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String paramString) {
		this.height = paramString;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String paramString) {
		this.width = paramString;
	}

	public boolean getCansort() {
		return this.cansort;
	}

	public void setCansort(boolean paramBoolean) {
		this.cansort = paramBoolean;
	}

	public int getmergecolcount() {
		return this.mergecolcount;
	}

	public void setmergecolcount(int paramInt) {
		this.mergecolcount = paramInt;
	}

	/**
	 * 初始化参数
	 */
	public BaseGridReport() {
		// 表格背景色
		if (this.bgcolor == null || "".equals(this.bgcolor)) {
			this.bgcolor = taglibproperty.getgridbgcolor();
		}
		// 边框线颜色
		if (this.bordercolor == null || "".equals(this.bordercolor)) {
			this.bordercolor = taglibproperty.getgridbordercolor();
		}
		// 选中行颜色 [浅蓝#E2EDFF 深蓝#D1DEF4]
		if (this.selectcolor == null || "".equals(this.selectcolor)) {
			this.selectcolor = taglibproperty.getgridselectcolor();
		}
		// 标题行颜色
		if (this.headbgcolor == null || "".equals(this.headbgcolor)) {
			this.headbgcolor = taglibproperty.getgridheadbgcolor();
		}
		// 边框线颜色
		if (this.linecolor == null || "".equals(this.linecolor)) {
			this.linecolor = taglibproperty.getgridlinecolor();
		}
		// 序号列颜色
		if (this.rowheadbgcolor == null || "".equals(this.rowheadbgcolor)) {
			this.rowheadbgcolor = taglibproperty.getgridrowheadbgcolor();
		}

	}

	/*
	 * (非 Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		// TODO doStartTag
		if (showcheckbox) {
			BaseCheck basecheck = new BaseCheck();
			try {
				basecheck.setcaption("");
				basecheck.setproperty(checkvalueproperty);
				basecheck.setid("syscheck");
				basecheck.setWidth("25");
				columns.add(basecheck);
			} catch (Exception exception) {
				// throw new JspException("\u6CA1\u6709\u5B9A\u4E49id\u5217"
				// + idproperty);
				throw new JspException("没有定义id列" + this.showcheckbox);
			}
		}
		if (idproperty != null) {
			BaseLabel baselabel = new BaseLabel();
			try {
				baselabel.setcaption(idproperty);
				baselabel.setproperty(idproperty);
				baselabel.setid("selectid");
				baselabel.setwidth("0");
				columns.add(baselabel);
			} catch (Exception exception1) {
				// throw new JspException("\u6CA1\u6709\u5B9A\u4E49id\u5217"
				// + idproperty);
				throw new JspException("没有定义id列" + this.idproperty);
			}
		}
		if (bgcolorcol != null) {
			BaseLabel baselabel1 = new BaseLabel();
			try {
				baselabel1.setcaption(bgcolorcol);
				baselabel1.setproperty(bgcolorcol);
				baselabel1.setwidth("0");
				columns.add(baselabel1);
			} catch (Exception exception2) {
				throw new JspException("没有定义id列" + this.bgcolorcol);
			}
		}
		if (merge && mergetype == "1") {
			setserial(false);
			print("merge==true 或者 mergetype==1", "不合并单元格");
		}
		if (caption != null) {
			// 表头
			ArrayList<Object> arraylist = getIterator(caption, null);
			if (arraylist != null) {
				for (int i = 0; i < arraylist.size(); i++) {
					Object obj1 = arraylist.get(i);
					// // 打印CaptionInfo
					// try {
					// BasicDynaBean dynaBean = (BasicDynaBean) obj1;
					// Map dto = BeanUtils.describe(dynaBean);
					// print(dto, "Caption");
					// } catch (Exception e) {
					// e.printStackTrace();
					// }

					// 列宽
					String s1 = "0";
					try {
						String s = String.valueOf(PropertyUtils.getProperty(
								obj1, "width"));
						if (s == null) {
							s1 = "0";
						} else {
							s1 = String.valueOf(s);
						}
					} catch (Exception e) {
						s1 = "0";
						// System.out.println("获取Caption.width异常:" + e);
					}
					// 列类型 label,input,check
					Object obj11 = null;
					try {
						Object obj3 = PropertyUtils.getProperty(obj1, "type");
						if (obj3 == null || s1.trim().equals("0"))
							obj11 = new BaseLabel();
						else if (obj3.toString().toLowerCase().equals("input"))
							obj11 = new BaseText();
						else if (obj3.toString().toLowerCase().equals("check"))
							obj11 = new BaseCheck();
						else
							obj11 = new BaseLabel();
					} catch (Exception e) {
						obj11 = new BaseLabel();
						// System.out.println("获取Caption.type异常:" + e);
					}
					// 列属性名
					try {
						Object obj4 = PropertyUtils.getProperty(obj1,
								"property");
						if (obj4 != null) {
							obj4 = obj4.toString().trim().toLowerCase();
							((BaseInputTag) (obj11)).setproperty(String
									.valueOf(obj4));
							((BaseInputTag) (obj11)).setcaption(String
									.valueOf(obj4));
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.property异常:" + e);
					}
					try {
						Object obj5 = PropertyUtils
								.getProperty(obj1, "caption");
						if (obj5 != null)
							((BaseInputTag) (obj11)).setcaption(String
									.valueOf(obj5));
						if (obj5 != null)
							((BaseInputTag) (obj11)).setWidth(s1);
					} catch (Exception e) {
						// System.out.println("获取Caption.caption异常:" + e);
					}
					try {
						Object obj6 = PropertyUtils.getProperty(obj1, "fixcol");
						// 是否固定 0.活动列(FALSE),1.固定列(TRUE)
						if (obj6 == null || "".equals(obj6)) {
							obj6 = 0;// 0或TRUE表示非固定列
						} else {
							((BaseInputTag) (obj11))
									.setfixcol(readboolean(obj6));
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.fixcol异常:" + e);
					}
					try {
						Object obj7 = PropertyUtils.getProperty(obj1, "format");
						if (obj7 == null) {
							obj7 = "";
						} else {
							((BaseInputTag) (obj11)).setformat(String.valueOf(
									obj7).trim());
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.format异常:" + e);
					}
					try {
						Object obj8 = PropertyUtils.getProperty(obj1,
								"datatype");
						// 数据类型 0.String,1.Numeric,2.Date,3.
						if (obj8 == null) {
							obj8 = "0";
						} else {
							((BaseInputTag) (obj11)).setdatatype(String
									.valueOf(obj8).trim());
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.datatype异常:" + e);
					}
					try {
						Object obj9 = PropertyUtils.getProperty(obj1, "align");
						// 位置 left,conter,right
						if (obj9 == null) {
							obj9 = "left";
						} else {
							((BaseInputTag) (obj11)).setalign(String.valueOf(
									obj9).trim());
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.align异常:" + e);
					}
					try {
						Object obj10 = PropertyUtils.getProperty(obj1,
								"isreturn");
						// 是否返回 true,false
						if (obj10 == null) {
							obj10 = "false";
						} else {
							((BaseInputTag) (obj11)).setIsReturn(String
									.valueOf(obj10).trim());
						}
					} catch (Exception e) {
						// System.out.println("获取Caption.isreturn异常:" + e);
					}

					((BaseInputTag) (obj11)).setgridname(getproperty());
					columns.add(obj11);

				}

			}
		}
		StringBufferTool strBuffer = new StringBufferTool(new StringBuffer());
		/* 暂时无用 */
		// strBuffer.appendln("<!-- CSS01 Begin -->");
		// strBuffer.appendln("<style type='text/css'>");
		// strBuffer.appendln(".HeadClass {");
		// strBuffer
		// .appendln("BORDER-RIGHT: medium none; BORDER-TOP: medium none; FONT-SIZE: 10pt; BORDER-LEFT: medium none; CURSOR: default; COLOR: black; BORDER-BOTTOM: medium none; BACKGROUND-COLOR: transparent");
		// strBuffer.appendln("}");
		// strBuffer.appendln("</style>");
		// strBuffer.appendln("<!-- CSS01 End -->");

		ResponseUtils.write(pageContext, strBuffer.toString());
		pageContext.setAttribute("GridReport", this);
		return 1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@SuppressWarnings("deprecation")
	public int doEndTag() throws JspException {
		// TODO doEndTag
		for (int i = 0; i < columns.size(); i++) {
			((BaseInputTag) columns.get(i)).setgridname(getProperty());
		}
		if (showcheckbox) {
			int j = 1;
			do {
				if (j >= columns.size())
					break;
				((BaseInputTag) columns.get(j)).setgridname(getProperty());
				if (checkvalueproperty != null
						&& checkvalueproperty.equals(((BaseInputTag) columns
								.get(j)).getproperty())) {
					((BaseInputTag) columns.get(0))
							.setproperty(((BaseInputTag) columns.get(j))
									.getproperty());
					((BaseInputTag) columns.get(0))
							.setWidth(((BaseInputTag) columns.get(j))
									.getWidth());
					((BaseInputTag) columns.get(0))
							.setcaption(((BaseInputTag) columns.get(j))
									.getcaption());
					columns.remove(j);
					break;
				}
				j++;
			} while (true);
		}
		pageContext.removeAttribute("GridReport");
		StringBuffer strBuffer = new StringBuffer("");
		headRowCount = calcHeadRowCount();
		// TODO
		calctablepara();
		calctablewidth();
		createcolgroup();
		writejs(strBuffer);
		if (showtiptext) {
			generate_showtiptextcss(strBuffer);
		}
		wirtecss(strBuffer);
		menu(strBuffer);
		writedivhead(strBuffer);
		writeReporthead(strBuffer, 0);//
		writefixcolcontent(strBuffer);
		writecontentdata(strBuffer);
		writetotal(strBuffer);
		writeReporthead(strBuffer, 1);//
		writedivfooter(strBuffer);

		strBuffer.append("<!-- 计算总行数 Begin -->\n");
		strBuffer.append("<script language='JavaScript'>\n");
		strBuffer.append(property.trim() + "calcSize();\n");
		strBuffer.append(property.trim() + "calcSizetimeid=setTimeout('"
				+ property.trim() + "calcSize()',100);\n");
		if (rstcount > 0)
			if (idproperty == null) {
				strBuffer.append(property.trim() + "selectchange('0')\n");
			} else {
				strBuffer.append("var selectidtemp =getCookie(\""
						+ property.trim() + "selectid\")\n");
				strBuffer.append("if (selectidtemp!=\"\")\n");
				strBuffer.append("  " + property.trim()
						+ "setselectbyid(selectidtemp)\n");
				strBuffer.append("else\n");
				strBuffer.append("   " + property.trim()
						+ "selectchange('0');\n");
			}
		strBuffer.append("</script>\n");
		strBuffer.append("<!-- 计算总行数 End -->\n");

		ResponseUtils.write(pageContext, strBuffer.toString());
		release();
		return 6;
	}

	/**
	 * 计算表格Para
	 */
	private void calctablepara() {
		BaseInputTag localBaseInputTag = null;
		if (getserial() == true)
			this.fixtablewidth = 35;
		for (int i = 0; i < this.columns.size(); i++) {
			localBaseInputTag = (BaseInputTag) this.columns.get(i);
			if ((Integer.parseInt(localBaseInputTag.getWidth().toString()) != 0)
					&& (localBaseInputTag.getStyle().toString().toLowerCase()
							.indexOf("display:none") < 0))
				if (localBaseInputTag.getfixcol() == true)
					this.fixtablewidth += Integer.parseInt(localBaseInputTag
							.getWidth());
				else
					this.tablewidth += Integer.parseInt(localBaseInputTag
							.getWidth());
		}
	}

	/**
	 * 计算表格宽度
	 */
	private void calctablewidth() {
		BaseInputTag localBaseInputTag = null;
		this.fixcolnumber = 0;
		this.contentcolnumber = 0;
		this.fixtablewidth = 0;
		this.tablewidth = 0;
		if (getserial() == true) {
			this.fixtablewidth = 35;
		}
		for (int i = 0; i < this.columns.size(); i++) {
			localBaseInputTag = (BaseInputTag) this.columns.get(i);
			if ((Integer.parseInt(localBaseInputTag.getWidth()) != 0)
					&& (localBaseInputTag.getStyle().toLowerCase()
							.indexOf("display:none") < 0))
				if (localBaseInputTag.getfixcol() == true) {
					this.fixcolnumber += 1;
					this.fixtablewidth += Integer.parseInt(localBaseInputTag
							.getWidth());
				} else {
					this.tablewidth += Integer.parseInt(localBaseInputTag
							.getWidth());
				}
		}
		this.contentcolnumber = (this.columns.size() - this.fixcolnumber);
	}

	/**
	 * 创建列group
	 */
	private void createcolgroup() {
		BaseInputTag localBaseInputTag = null;
		int i = 0;
		this.colgroup = "<colgroup id=\"colgroup\">";
		this.Fixcolgroup = "<colgroup id=\"colgroup\">";
		this.colheadgroup = "<colgroup id=\"colgroup\">";
		this.Fixcolheadgroup = "<colgroup id=\"colgroup\">";
		if (this.serial == true) {
			this.Fixcolgroup += "<col id=\"col\" name =\"serial\" datatype=\"1\" style=\"width:35px\">";
			this.Fixcolheadgroup += "<col id=\"col\" name =\"serial\"  datatype=\"1\" style=\"width:35px\">";
		}
		int j = 0;
		for (int k = 0; k < this.columns.size(); k++) {
			localBaseInputTag = (BaseInputTag) this.columns.get(k);
			String str = "";
			i = 0;
			str = str
					+ "<col id=\"col\"  name =\""
					+ (localBaseInputTag.getproperty() == null ? ""
							: localBaseInputTag.getproperty().trim())
					+ "\" style=\"";
			if ((Integer.parseInt(localBaseInputTag.getWidth()) != 0)
					&& (localBaseInputTag.getStyle().toLowerCase()
							.indexOf("display:none") < 0)) {
				i = 1;
				str = str + "width:" + localBaseInputTag.getWidth() + ";";
				if (localBaseInputTag.getStyle() != "")
					str = str + localBaseInputTag.getStyle();
			} else {
				str = str + "display:none;width:0;";
				localBaseInputTag.setfixcol(false);
			}
			str = str + "\"";
			str = str + " datatype =\""
					+ String.valueOf(localBaseInputTag.getDataTypeToInt())
					+ "\" ";
			if (localBaseInputTag.getalign() != null)
				str = str + " align=\"" + localBaseInputTag.getalign() + "\"";
			str = str + ">";
			if (localBaseInputTag.getfixcol() == true) {
				if (i == 1)
					this.Fixcolheadgroup += str;
				this.Fixcolgroup += str;
			} else {
				if (i == 1) {
					if (this.allowchangecolwidth == true)
						str = MethodFactory.replace(str, ">",
								" contentindex=\"" + j + "\">");
					this.colheadgroup += str;
				}
				this.colgroup += str;
				j += 1;
			}
		}
		this.Fixcolheadgroup += "</colgroup>\n";
		this.colheadgroup += "</colgroup>\n";
		this.Fixcolgroup += "</colgroup>\n";
		this.colgroup += "</colgroup>\n";
	}

	/**
	 * 表格操作相关JS函数
	 *
	 * @param strBuffer
	 */
	private void writejs(StringBuffer strBuffer) {
		// TODO WriteJS
		strBuffer.append("<!-- GridReport.JS Begin -->\n");
		strBuffer.append("<SCRIPT language=JavaScript>\n");

		BaseJSFunctions js = new BaseJSFunctions(this.property);
		if (this.funtype >= 0) {
			js.generate_Para(strBuffer);
			js.generate_gettable(strBuffer);
			js.generate_calcSize(strBuffer);
			js.generate_getcellvalue(strBuffer);
			js.generate_getselectvalue(strBuffer);
			js.generate_setselectvalue(strBuffer);
			js.generate_getcolnumber(strBuffer);
			js.generate_getvalue(strBuffer);
			js.generate_scroll(strBuffer);
			// 点击事件,双击事件
			js.generate_clickselect(strBuffer, getOndblclick(), getOnclick());
			js.generate_selectchange(strBuffer, this.onrowchange);
			js.generate_ChangeColor(strBuffer, this.bgcolor, this.selectcolor);
			js.generate_selectid(strBuffer);
			js.generate_checkUpDown(strBuffer);
			if (this.showtiptext == true) {
				js.generate_showtiptext(strBuffer);
			}
			js.generate_getDetail(strBuffer, this.columns, this.rowsplitstring,
					this.colsplitstring);
			js.generate_getDetailXml(strBuffer);
			js.generate_setselectbyid(strBuffer);
			js.generate_redoserial(strBuffer, this.serial);
			if (this.cansort) {
				js.generate_dosort(strBuffer, this.allowchangecolwidth,
						this.serial);
			}
		}
		if (this.funtype >= 1) {
			js.generate_addline(strBuffer, getOndblclick(), this.showtiptext,
					this.columns, this.serial);
			js.generate_delLine(strBuffer);
			js.generate_delallline(strBuffer);
			js.generate_dellinebycheck(strBuffer);
			js.generate_getselectidbycheck(strBuffer);
			js.generate_setcheckbyid(strBuffer);
			/* 显示右键菜单 */
			// js.generate_setallcheckstate(strBuffer);
			js.generate_getsumbycheck(strBuffer);
			js.generate_getsum(strBuffer);
			js.generate_getsumcallprc(strBuffer);
		}
		/* 显示右键菜单 add by cdd */
		if (this.showcheckbox) {
			js.generate_setallcheckstate(strBuffer);
		}
		if ((this.merge) && (this.mergetype.equals("1"))) {
			js.generate_expand(strBuffer, this.gridExpandImage,
					this.gridShrinkImage);
		}
		if (this.allowchangecolwidth) {
			js.generate_allowchangecolsize(strBuffer);
		}
		strBuffer.append("</SCRIPT>\n");
		strBuffer.append("<!-- GridReport.JS End -->\n");
	}

	// /**
	// * 表格操作相关JS函数
	// *
	// * @param strBuffer
	// */
	// private void writejs(StringBuffer strBuffer) {
	// strBuffer.append("<!-- GridReport.JS Begin -->\n");
	// strBuffer.append("<SCRIPT language=JavaScript>\n");
	// if (this.funtype >= 0) {
	// generate_Para(strBuffer);
	// generate_gettable(strBuffer);
	// generate_calcSize(strBuffer);
	// generate_getcellvalue(strBuffer);
	// generate_getselectvalue(strBuffer);
	// generate_setselectvalue(strBuffer);
	// generate_getcolnumber(strBuffer);
	// generate_getvalue(strBuffer);
	// generate_scroll(strBuffer);
	// generate_clickselect(strBuffer);
	// generate_selectchange(strBuffer);
	// generate_ChangeColor(strBuffer);
	// generate_selectid(strBuffer);
	// generate_checkUpDown(strBuffer);
	// if (this.showtiptext == true) {
	// generate_showtiptext(strBuffer);
	// }
	// generate_getDetail(strBuffer);
	// generate_getDetailXml(strBuffer);
	// generate_setselectbyid(strBuffer);
	// generate_redoserial(strBuffer);
	// if (this.cansort) {
	// generate_dosort(strBuffer);
	// }
	// }
	// if (this.funtype >= 1) {
	// generate_addline(strBuffer);
	// generate_delLine(strBuffer);
	// generate_delallline(strBuffer);
	// generate_dellinebycheck(strBuffer);
	// generate_getselectidbycheck(strBuffer);
	// generate_setcheckbyid(strBuffer);
	// generate_setallcheckstate(strBuffer);
	// generate_getsumbycheck(strBuffer);
	// generate_getsum(strBuffer);
	// generate_getsumcallprc(strBuffer);
	// }
	// if ((this.merge) && (this.mergetype.equals("1"))) {
	// generate_expand(strBuffer);
	// }
	// if (this.allowchangecolwidth) {
	// generate_allowchangecolsize(strBuffer);
	// }
	// strBuffer.append("</SCRIPT>\n");
	// strBuffer.append("<!-- GridReport.JS End -->\n");
	// }

	/**
	 * @param strBuffer
	 */
	private void generate_showtiptextcss(StringBuffer strBuffer) {
		strBuffer.append("<!-- 显示IPTextCss Begin -->\n");
		strBuffer.append("<style type='text/css'>\n");
		strBuffer
				.append(".cPopText {  background-color: #FFFFCC; border: 1px #000000 solid; font-size: 12px; padding-right: 2px; padding-left: 2px; height: 20px; padding-top: 2px; padding-bottom: 2px; filter: Alpha(Opacity=0)}\n");
		strBuffer.append("</style>\n");
		strBuffer
				.append("<div id='"
						+ this.property
						+ "dypopLayer' style='position:absolute;z-index:1000;' class='cPopText'></div>\n");
		strBuffer.append("<!-- 显示IPTextCss End -->\n");
	}

	/**
	 * GridReport.CSS
	 *
	 * @param strBuffer
	 */
	private void wirtecss(StringBuffer strBuffer) {
		strBuffer.append("<!-- GridReport.CSS Begin -->\n");
		strBuffer.append("<style>");
		strBuffer
				.append(".table,.fixtable {table-layout: fixed;border-width: 0px;border-style: solid;}\n");
		strBuffer.append(".table td ,.fixtable td   {border-style: solid;");
		strBuffer
				.append("font: menu;empty-cells: show;white-space: nowrap;overflow: hidden;");
		strBuffer.append("border-color: " + this.linecolor + ";");
		strBuffer.append("border-width: 0px 1px 1px 0px;");
		strBuffer.append("height: " + String.valueOf(this.rowheight - 1)
				+ "px;");
		strBuffer.append("white-space: nowrap;");
		strBuffer.append("padding: 1px; }\n");
		strBuffer
				.append(".menu{cursor: default; display: none; cursor: hand; position: absolute; top: 0; left: 0; background-color: #CFCFCF; border: 1 solid; border-top-color: #EFEFEF; border-left-color: #EFEFEF; border-right-color: #505050; border-bottom-color: #505050; font-size: 8pt; font-family: Arial;margin:0pt;padding: 3pt;overflow: hidden;}\n");
		strBuffer
				.append(".menu SPAN {width: 100%; cursor: default;padding-left: 10pt}\n");
		strBuffer
				.append(".menu SPAN.selected {background: navy; color:white}\n");
		strBuffer
				.append(".input_report {BACKGROUND-COLOR: transparent;border: 0px solid;width:100%}\n");
		strBuffer.append("</style>");
		strBuffer.append("<!-- GridReport.CSS End -->\n");
	}

	/**
	 * 表格右键菜单列表
	 *
	 * @param strBuffer
	 */
	private void menu(StringBuffer strBuffer) {
		String str1 = "";
		if (this.showmenu == true) {
			String str2 = "";
			str2 = "<xml id=\"" + this.property + "menucontextDef\">";
			str2 = str2 + "<xmldata>";
			str2 = str2 + "<contextmenu id=\"" + this.property + "menu\">";
			str2 = str2 + "<item id=\"openwithexcel('" + this.property + "','";
			if (this.reportcaption == null)
				str2 = str2 + this.property + "','";
			else
				str2 = str2 + this.reportcaption + "','";
			str2 = str2
					+ ((HttpServletRequest) this.pageContext.getRequest())
							.getContextPath() + "'";
			try {
				if (this.reportcondition != null)
					str1 = (String) this.pageContext.getRequest().getAttribute(
							this.reportcondition);
				if (str1 == null)
					str1 = "";
			} catch (Exception localException1) {
				str1 = "";
			}
			try {
				str1 = MethodFactory.replace(str1, "\"", "");
				str1 = MethodFactory.replace(str1, "'", "");
			} catch (Exception localException2) {
			}
			str2 = str2 + ",'" + str1 + "');\"";
			str2 = str2 + " value=\"导出数据到Excel\"/>";
			if (this.showcheckbox) {
				str2 = str2 + "<item id=\"" + this.property
						+ "setallcheckstate('1');\" value=\"全部选中\"/>\n";
				str2 = str2 + "<item id=\"" + this.property
						+ "setallcheckstate('0');\" value=\"全部取消\"/>\n";
				str2 = str2 + "<item id=\"" + this.property
						+ "setallcheckstate('2');\" value=\"全部选反\"/>\n";
				str2 = str2 + "<item id=\"" + this.property
						+ "setallcheckstate('-1');\" value=\"退出\"/>\n";
			}
			str2 = str2 + "</contextmenu>";
			str2 = str2 + "</xmldata>";
			str2 = str2 + "</xml>";

			strBuffer.append("<!-- 表头右键菜单 Begin -->\n");
			strBuffer.append(str2 + "\n");
			strBuffer
					.append("<div status=\"false\" onmouseover=\"fnChirpOn()\" style=\"width:150;zIndex:2\"");
			strBuffer.append(" onmouseout=\"fnChirpOff()\" id=\""
					+ this.property
					+ "menuContextMenu\" class=\"menu\"></div>\n");

			strBuffer.append("<!-- 表头右键菜单 End -->\n");
		}
	}

	/**
	 * 生成表头Div
	 *
	 * @param strBuffer
	 */
	private void writedivhead(StringBuffer strBuffer) {
		strBuffer.append("<!-- WriteDivHead Begin -->\n");
		strBuffer.append("<div style =\"border: 1px solid " + this.bordercolor
				+ ";overflow: hidden;width:" + this.width + ";height:"
				+ this.height + ";");
		if (readshowtotal())
			strBuffer.append("padding: 0px 0px "
					+ String.valueOf(this.rowheight) + "px 0px;");
		strBuffer.append("\"");
		strBuffer.append(" onresize=\"" + this.property + "calcSize();\"");
		strBuffer.append(" id=\"" + this.property + "div000\"");
		if (this.showmenu == true)
			strBuffer
					.append(" oncontextmenu=\"window.event.returnValue=false\"");
		if (this.allowchangecolwidth) {
			if (this.showmenu)
				strBuffer.append(" onmouseup=\"" + this.property
						+ "mouseDragEnd();contextmenu('" + this.property
						+ "menu')\" onmousemove =\"" + this.property
						+ "mouseDrag()\"");
			else
				strBuffer.append(" onmouseup=\"" + this.property
						+ "mouseDragEnd()\" onmousemove =\"" + this.property
						+ "mouseDrag()\"");
		} else if (this.showmenu)
			strBuffer.append(" onmouseup=\"contextmenu('" + this.property
					+ "menu')\" ");
		strBuffer.append(">\n");
		strBuffer.append("<!-- WriteDivHead End -->\n");
	}

	/**
	 * 写表头数据
	 *
	 * @param strBuffer
	 * @param i
	 */
	private void writeReporthead(StringBuffer strBuffer, int i) {
		int j = 0;
		// Object obj = null;
		// Object obj1 = null;
		int k = 0;
		if (i == 1) {
			if (getserial())
				k = fixcolnumber + 1;
			else
				k = fixcolnumber;
		} else {
			k = contentcolnumber;
		}
		BaseInputTag baseinputtag = null;
		int l = 0;
		if (headRowCount > 0) {
			colstruct acolstruct[][] = new colstruct[headRowCount][k];
			colstruct acolstruct1[][] = new colstruct[headRowCount][k];
			for (int i1 = 0; i1 < headRowCount; i1++) {
				for (int j1 = 0; j1 < k; j1++) {
					colstruct colstruct1 = new colstruct();
					acolstruct1[i1][j1] = colstruct1;
				}

			}

			// boolean flag = false;
			if (i == 1 && getserial()) {
				l = 1;
				for (int k1 = 0; k1 < headRowCount; k1++) {
					colstruct colstruct2 = new colstruct();
					// colstruct2.caption = "\u5E8F\u53F7";
					colstruct2.caption = "序号";
					colstruct2.type = 0;
					acolstruct[k1][0] = colstruct2;
				}

			}
			for (int l1 = 0; l1 < columns.size(); l1++) {
				j = 0;
				baseinputtag = (BaseInputTag) columns.get(l1);
				boolean flag1 = false;
				if (i == 1) {
					if (baseinputtag.getfixcol())
						flag1 = true;
				} else if (!baseinputtag.getfixcol())
					flag1 = true;
				if (!flag1)
					continue;
				if (baseinputtag.getcaption() != null) {
					String s1 = baseinputtag.getcaption();
					String s = baseinputtag.getcaption();
					while (s1.indexOf(splitterstring) != -1) {
						s = s1.substring(0, s1.indexOf(splitterstring));
						if (s.trim().equals(""))
							s = "&nbsp;";
						colstruct colstruct3 = new colstruct();
						colstruct3.id = getsortname(i, l1);
						colstruct3.name = baseinputtag.getproperty() != null ? baseinputtag
								.getproperty().trim() : "";
						colstruct3.caption = s;
						colstruct3.colnumber = l1;
						colstruct3.type = 1;
						acolstruct[j][l] = colstruct3;
						s1 = s1.substring(s1.indexOf(splitterstring)
								+ splitterstring.length());
						s = s1;
						j++;
					}
					for (int i2 = j; i2 < headRowCount; i2++) {
						colstruct colstruct4 = new colstruct();
						colstruct4.id = getsortname(i, l1);
						colstruct4.name = baseinputtag.getproperty() != null ? baseinputtag
								.getproperty().trim() : "";
						colstruct4.caption = s;
						colstruct4.colnumber = l1;
						colstruct4.type = 1;
						acolstruct[i2][l] = colstruct4;
					}

				}
				l++;
			}

			if (i == 0) {
				strBuffer.append("<!-- WriteReportHead.0 Begin -->\n");
				strBuffer.append("<div style=\"");
				strBuffer.append("padding:0px 0px 0px " + fixtablewidth
						+ "px;width:100%;");
				strBuffer
						.append("height: "
								+ String.valueOf(getrowheight() * headRowCount)
								+ "px;overflow: hidden;background:window;position: absolute;top: 0px;left:0px\"");
				strBuffer.append("id =\"" + property + "div101\"");
				strBuffer.append("> <table id=\"" + property.trim()
						+ "contenthead\"  height =\""
						+ String.valueOf(getrowheight() * headRowCount)
						+ "px\" bgcolor =\"" + headbgcolor
						+ "\" cellspacing=\"0\" class =\"table\">");
				strBuffer.append(colheadgroup);
				strBuffer.append("<!-- WriteReportHead.0 End -->\n");
			} else {
				strBuffer.append("<!-- WriteReportHead.1 Begin -->\n");
				strBuffer.append("<div style=\"position:absolute;");
				strBuffer.append(" width:" + String.valueOf(fixtablewidth)
						+ "px;");
				strBuffer.append("border-width: 1px;top:0px;left:0px;");
				strBuffer
						.append("height: "
								+ String.valueOf(getrowheight() * headRowCount)
								+ "px;");
				strBuffer.append("overflow: hidden;background: window;\"");
				strBuffer.append(" id =\"" + property + "div100\">\n");
				strBuffer.append("<table id=\"" + property.trim()
						+ "fixhead\" bgcolor =\"" + headbgcolor
						+ "\" class =\"fixtable\" cellspacing=\"0\" height =\""
						+ String.valueOf(getrowheight() * headRowCount)
						+ "px\">");
				strBuffer.append(Fixcolheadgroup);
				strBuffer.append("<!-- WriteReportHead.1 End -->\n");
			}

			for (int j2 = 0; j2 < headRowCount; j2++) {
				if (i == 0 && allowchangecolwidth && j2 == headRowCount - 1)
					strBuffer.append("<tr id =\"head\"  onmousemove=\""
							+ property + "onmousemove();\" onmousedown=\""
							+ property + "setDrag();\">");
				else
					strBuffer.append("<tr id =\"head\">");
				for (int k2 = 0; k2 < k; k2++) {
					boolean flag7 = false;
					if (acolstruct[j2][k2].type == 0) {
						if (acolstruct1[j2][k2].caption == null)
							flag7 = true;
					} else {
						baseinputtag = (BaseInputTag) columns
								.get(acolstruct[j2][k2].colnumber);
						if (acolstruct1[j2][k2].caption == null
								&& Integer.parseInt(baseinputtag.getWidth()) != 0
								&& baseinputtag.getStyle().toLowerCase()
										.indexOf("display:none") < 0)
							flag7 = true;
					}
					if (!flag7)
						continue;
					strBuffer.append("<td ");
					strBuffer.append(" align =\"center\" ");
					boolean flag3 = false;
					boolean flag5 = false;
					for (int l2 = k2 + 1; l2 < k; l2++) {
						BaseInputTag baseinputtag1 = (BaseInputTag) columns
								.get(acolstruct[0][l2].colnumber);
						if (Integer.parseInt(baseinputtag1.getWidth()) == 0
								|| baseinputtag1.getStyle().toLowerCase()
										.indexOf("display:none") >= 0)
							break;
						if (j2 == 0) {
							if (acolstruct[j2][l2].caption
									.equals(acolstruct[j2][k2].caption)) {
								acolstruct1[j2][l2].caption = "";
								flag3 = true;
								j = l2;
							}
							continue;
						}
						if (!acolstruct[j2][l2].caption
								.equals(acolstruct[j2][k2].caption)
								|| !acolstruct[j2 - 1][l2].caption
										.equals(acolstruct[j2 - 1][k2].caption))
							break;
						acolstruct1[j2][l2].caption = "";
						flag3 = true;
						j = l2;
					}

					if (!flag3) {
						for (int i3 = j2 + 1; i3 < headRowCount; i3++)
							if (acolstruct[i3][k2].caption
									.equals(acolstruct[j2][k2].caption)) {
								acolstruct1[i3][k2].caption = "";
								flag5 = true;
								j = i3;
							}

					}
					if (flag3) {
						j = (j - k2) + 1;
						strBuffer.append(" colspan=\"");
						strBuffer.append(j);
						strBuffer.append("\" ");
					}
					if (flag5) {
						j = (j - j2) + 1;
						strBuffer.append(" rowspan=\"");
						strBuffer.append(j);
						strBuffer.append("\" ");
					}
					if (baseinputtag.getStyle() != "") {
						strBuffer.append("style=\"");
						strBuffer.append(baseinputtag.getStyle() + "\"");
					}
					if (cansort
							&& !merge
							&& (flag5 && j + j2 == headRowCount || !flag3
									&& j2 == headRowCount - 1))
						strBuffer.append("id=\"" + acolstruct[j2][k2].id
								+ "\" name=\"" + acolstruct[j2][k2].name
								+ "\" onclick=\"javascript:return " + property
								+ "dosort(this," + String.valueOf(i) + ")\"");
					strBuffer.append(" >");
					if (!(baseinputtag instanceof BaseLabel)
							&& (flag5 && j + j2 == headRowCount || !flag3
									&& j2 == headRowCount - 1)) {
						strBuffer.append("<font color=\"red\">");
						if ("".equals(acolstruct[j2][k2].caption))
							strBuffer.append("&nbsp;");
						else
							strBuffer.append(acolstruct[j2][k2].caption);
						strBuffer.append("</font>\n");
					} else if ("".equals(acolstruct[j2][k2].caption))
						strBuffer.append("&nbsp;");
					else
						strBuffer.append(acolstruct[j2][k2].caption);
					strBuffer.append("</td>\n");
					acolstruct1[j2][k2].caption = "";
				}

				strBuffer.append("</tr>\n");
			}

			strBuffer.append("</table>");
			writedivfooter(strBuffer);
		}
	}

	/**
	 * 写固定列的内容
	 *
	 * @param strBuffer
	 */
	private void writefixcolcontent(StringBuffer strBuffer) {
		strBuffer.append("<!-- WriteFixColContent Begin -->\n");
		strBuffer.append("<div style =\"width: "
				+ String.valueOf(fixtablewidth) + "px;");
		strBuffer.append("height: 100%;");
		strBuffer
				.append("overflow: hidden;background: windwow;position: absolute;left: 0px;");
		strBuffer.append("top:" + String.valueOf(getrowheight() * headRowCount)
				+ "px;\"");
		strBuffer.append(" id =\"" + property + "div102\">");
		strBuffer.append("<table  id =\"" + property
				+ "fixcolcontent\" bgcolor=\"" + rowheadbgcolor
				+ "\" cellspacing=\"0\" class =\"fixtable\">\n");
		strBuffer.append(Fixcolgroup);

		int i = 0;
		int j = 0;
		if (collection != null) {
			ArrayList<Object> arraylist = getIterator(collection, null);
			if (merge && mergetype == "1") {
				writetype1rowhead(strBuffer, arraylist);
				strBuffer.append("</table>");
				strBuffer.append("</div>");
				return;
			}
			if (arraylist != null) {
				int l = 0;
				for (int i1 = 0; i1 < columns.size(); i1++) {
					BaseInputTag baseinputtag = (BaseInputTag) columns.get(i1);
					if (baseinputtag.getfixcol())
						l++;
				}

				String as[][] = new String[arraylist.size()][l];
				int ai[][] = new int[arraylist.size()][l];
				if (totalcollection == null)
					totalcollection = "";
				for (int i2 = 0; i2 < arraylist.size()
						&& (showrowcount == -1 || i2 < showrowcount)
						&& (!showtotal || !collection.equals(totalcollection) || i2 != arraylist
								.size() - 1); i2++) {
					int j1 = 0;
					Object obj2 = arraylist.get(i2);
					for (int k2 = 0; k2 < columns.size(); k2++) {
						BaseInputTag baseinputtag1 = (BaseInputTag) columns
								.get(k2);
						if (!baseinputtag1.getfixcol())
							continue;
						String s = baseinputtag1.getproperty();
						Object obj4;
						try {
							obj4 = PropertyUtils.getProperty(obj2, s);
							if (obj4 == null)
								obj4 = "&nbsp;";
						} catch (Exception exception) {
							obj4 = "&nbsp;";
						}
						as[i2][j1] = obj4.toString();
						ai[i2][j1] = 0;
						j1++;
					}

				}

				int l2 = 1;
				if (merge) {
					for (int k3 = 0; k3 < arraylist.size()
							&& (showrowcount == -1 || k3 < showrowcount)
							&& (!showtotal
									|| !collection.equals(totalcollection) || k3 != arraylist
									.size() - 1); k3++) {
						i++;
						int k1 = 0;
						strBuffer.append("<tr ");
						changeSelectColor(strBuffer, i, j, false);
						strBuffer.append(">\n");
						for (int i4 = 0; i4 < columns.size(); i4++) {
							BaseInputTag baseinputtag2 = (BaseInputTag) columns
									.get(i4);
							if (i4 == 0 && serial) {
								strBuffer.append("<td valign='middle'>");
								strBuffer.append(i);
								strBuffer.append("</td>");
							}
							if (!baseinputtag2.getfixcol())
								continue;
							int j2 = 1;
							boolean flag6 = true;
							if (ai[k3][k1] > 0) {
								flag6 = false;
							} else {
								for (int k4 = k3 + 1; k4 < arraylist.size(); k4++) {
									if (k1 == 0) {
										String s2 = as[k4][0];
										if (s2 == null)
											s2 = "";
										String s5 = as[k3][0];
										if (s5 == null)
											s5 = "";
										if (s2.equals(s5)) {
											j2++;
											ai[k4][0] = l2;
										} else {
											k4 = arraylist.size() + 1;
										}
										continue;
									}
									String s3 = as[k4][k1];
									String s6 = as[k3][k1];
									if (s3 == null)
										s3 = "";
									if (s6 == null)
										s6 = "";
									int i3 = ai[k4][k1 - 1];
									int j3 = ai[k3][k1 - 1];
									if (s3.equals(s6) && i3 == j3) {
										j2++;
										ai[k4][k1] = l2;
									} else {
										k4 = arraylist.size() + 1;
									}
								}

							}
							if (flag6) {
								ai[k3][k1] = l2;
								l2++;
								if (j2 == 1) {
									baseinputtag2.setrowspan(null);
									baseinputtag2.generateRows(strBuffer,
											as[k3][k1]);
								} else {
									baseinputtag2
											.setrowspan(String.valueOf(j2));
									baseinputtag2.generateRows(strBuffer,
											as[k3][k1]);
								}
							}
							k1++;
						}

						strBuffer.append("</tr>");
					}

				}
				if (!merge) {
					int k = arraylist.size();
					for (int l3 = 0; l3 < arraylist.size()
							&& (showrowcount == -1 || l3 < showrowcount)
							&& (!showtotal
									|| !collection.equals(totalcollection) || l3 != arraylist
									.size() - 1); l3++) {
						i++;
						int l1 = 0;
						strBuffer.append("<tr ");
						changeSelectColor(strBuffer, i, k, false);
						strBuffer.append(">\n");
						for (int j4 = 0; j4 < columns.size(); j4++) {
							BaseInputTag baseinputtag3 = (BaseInputTag) columns
									.get(j4);
							if (j4 == 0 && serial) {
								strBuffer.append("<td valign='middle'>");
								strBuffer.append(i);
								strBuffer.append("</td>");
							}
							if (baseinputtag3.getfixcol()) {
								baseinputtag3.generateRows(strBuffer,
										as[l3][l1]);
								l1++;
							}
						}

						strBuffer.append("</tr>");
					}

				}
			}
		}
		strBuffer.append("</table>");
		strBuffer.append("</div>");
		strBuffer.append("<!-- WriteFixColContent End -->\n");
	}

	/**
	 * 写活动列内容数据
	 *
	 * @param strBuffer
	 */
	private void writecontentdata(StringBuffer strBuffer) {
		BaseInputTag localBaseInputTag = null;
		strBuffer.append("<!-- WriteContentData Begin -->\n");
		strBuffer
				.append("<div  style=\"width: 100%;background: window;overflow: scroll;");
		strBuffer.append("height: 100%;");
		strBuffer.append("padding: "
				+ String.valueOf(getrowheight() * this.headRowCount)
				+ "px 0px 0px ");
		strBuffer.append(String.valueOf(this.fixtablewidth)
				+ "px;\"  onscroll=\"" + this.property.trim()
				+ "scroll()\" id =\"" + this.property + "div103\">");
		strBuffer.append("<table onkeydown=\"return " + this.property
				+ "keyDown()\" onkeyup=\"return " + this.property
				+ "checkUpDown()\" id =\"" + this.property + "\" bgcolor =\""
				+ this.bgcolor + "\"cellspacing=\"0\" class =\"table\">\n");
		strBuffer.append(this.colgroup);
		int i = 0;
		Object localObject1 = null;
		Object localObject2 = null;
		int j = 0;
		if (this.collection != null) {
			ArrayList<Object> localArrayList = getIterator(this.collection,
					null);
			if (localArrayList != null) {
				j = localArrayList.size();
				if (this.totalcollection == null)
					this.totalcollection = "";
				for (int k = 0; (k < localArrayList.size())
						&& ((this.showrowcount == -1) || (k < this.showrowcount))
						&& ((this.showtotal != true)
								|| (!this.collection
										.equals(this.totalcollection)) || (k != localArrayList
								.size() - 1)); k++) {
					localObject1 = localArrayList.get(k);
					i++;
					strBuffer.append("<tr ");
					if ((this.merge == true) && (this.mergetype == "1")
							&& (((String) this.rowVisible.get(k)).equals("0")))
						strBuffer.append(" style=\"display:none\"");
					if (this.showtiptext == true) {
						strBuffer.append(" onmouseover=\"");
						strBuffer.append(this.property + "showPopupText();");
						strBuffer.append("\"");
					}
					changeSelectColor(strBuffer, i, j, true);
					try {
						if (this.bgcolorcol != null) {
							localObject2 = PropertyUtils.getProperty(
									localObject1, this.bgcolorcol);
							if (localObject2 == null)
								localObject2 = "";
							if (!localObject2.equals("")) {
								strBuffer.append(" bgcolor=\"");
								strBuffer.append(localObject2);
								strBuffer.append("\"");
								strBuffer.append(" savebgcolor=\"");
								strBuffer.append(localObject2);
								strBuffer.append("\"");
							}
						}
					} catch (Exception localException1) {
					}
					strBuffer.append(">\n");
					for (int m = 0; m < this.columns.size(); m++) {
						localBaseInputTag = (BaseInputTag) this.columns.get(m);
						if (localBaseInputTag.getfixcol() != true) {
							try {
								if (this.fontcolorcol != null) {
									localObject2 = PropertyUtils.getProperty(
											localObject1, this.fontcolorcol);
									if (localObject2 == null)
										localObject2 = "";
									if (!localObject2.equals(""))
										localBaseInputTag.setfontcolor(String
												.valueOf(localObject2));
									else
										localBaseInputTag.setfontcolor(null);
								}
							} catch (Exception localException2) {
							}
							String str = localBaseInputTag.getproperty();
							try {
								localObject2 = PropertyUtils.getProperty(
										localObject1, str);
								if (localObject2 == null)
									localObject2 = "";
							} catch (Exception localException3) {
								localObject2 = "";
							}
							localBaseInputTag.generateRows(strBuffer,
									String.valueOf(localObject2));
						}
					}
					strBuffer.append("</tr>");
				}
				this.rstcount = i;
			}
		}
		strBuffer.append("</table>");
		strBuffer.append("</div>");
		strBuffer.append("<!-- WriteContentData End -->\n");
	}

	/**
	 * 写合计行
	 *
	 * @param strBuffer
	 */
	private void writetotal(StringBuffer strBuffer) {
		if (readshowtotal()) {

			if (totalcollection != null) {
				ArrayList<Object> arraylist = getIterator(totalcollection, null);
				if (arraylist != null) {
					int i = arraylist.size();
					if (i > 0) {
						Object obj2 = arraylist.get(arraylist.size() - 1);
						strBuffer.append("<div style=\"");
						strBuffer.append("padding:0px 0px 0px " + fixtablewidth
								+ "px;width:100%;");
						strBuffer
								.append("height: "
										+ String.valueOf(rowheight)
										+ "px;overflow: hidden;background:window;position: absolute;top:0px;left:0px;\"");
						strBuffer.append("id =\"" + property + "div105\"");
						strBuffer.append("> <table id=\"" + property.trim()
								+ "total\"  height =\""
								+ String.valueOf(rowheight)
								+ "px\" bgcolor =\"" + bgcolor
								+ "\" cellspacing=\"0\" class =\"table\">");
						strBuffer.append(colgroup);
						strBuffer.append("<tr ");
						strBuffer.append(">\n");
						for (int j = 0; j < columns.size(); j++) {
							BaseInputTag baseinputtag = (BaseInputTag) columns
									.get(j);
							if (baseinputtag.getfixcol())
								continue;
							try {
								if (fontcolorcol != null) {
									Object obj4 = PropertyUtils.getProperty(
											obj2, fontcolorcol);
									if (obj4 == null)
										obj4 = "";
									if (!obj4.equals(""))
										baseinputtag.setfontcolor(String
												.valueOf(obj4));
									else
										baseinputtag.setfontcolor(null);
								}
							} catch (Exception exception) {
							}
							String s = baseinputtag.getproperty();
							Object obj5;
							try {
								obj5 = PropertyUtils.getProperty(obj2, s);
								if (obj5 == null)
									obj5 = "&nbsp;";
							} catch (Exception exception1) {
								obj5 = "&nbsp;";
							}
							writetotalcell(strBuffer, baseinputtag,
									String.valueOf(obj5));
						}

						strBuffer.append("</tr>");
						strBuffer.append("</table>");
						strBuffer.append("</div>");
						strBuffer.append("<div style =\"width: "
								+ String.valueOf(fixtablewidth) + "px;");
						strBuffer.append("height: " + String.valueOf(height)
								+ "px;");
						strBuffer
								.append("overflow: hidden;background: windwow;position: absolute;left: 0px;");
						strBuffer.append("top:0px;\"");
						strBuffer.append("id =\"" + property + "div104\"");
						strBuffer.append("\">");
						strBuffer.append("<table  id =\"" + property
								+ "fixtotal\" bgcolor=\"" + headbgcolor
								+ "\" cellspacing=\"0\" class =\"table\">\n");
						strBuffer.append(Fixcolgroup);
						strBuffer.append("<tr ");
						strBuffer.append(">\n");
						for (int k = 0; k < columns.size(); k++) {
							if (k == 0 && serial) {
								strBuffer.append("<td >");
								strBuffer.append("&nbsp;");
								strBuffer.append("</td>");
							}

							BaseInputTag baseinputtag1 = (BaseInputTag) columns
									.get(k);
							if (!baseinputtag1.getfixcol())
								continue;
							String s1 = baseinputtag1.getproperty();
							Object obj6;
							try {
								obj6 = PropertyUtils.getProperty(obj2, s1);
								if (obj6 == null)
									obj6 = "&nbsp;";
							} catch (Exception exception2) {
								obj6 = "&nbsp;";
							}
							writetotalcell(strBuffer, baseinputtag1,
									String.valueOf(obj6));
						}

						strBuffer.append("</tr>");
					}
					strBuffer.append("</table>");
					strBuffer.append("</div>");
				}
			}
		}
	}

	/**
	 * 关闭div标签
	 *
	 * @param strBuffer
	 */
	private void writedivfooter(StringBuffer strBuffer) {
		strBuffer.append("</div>\n");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zl.base.core.taglib.datagridreport.BaseHandlerTag#release()
	 */
	public void release() {
		super.release();
		this.columns.removeAllElements();
	}

	private void writetype1rowhead(StringBuffer strBuffer,
			ArrayList<Object> paramArrayList) {
		ArrayList<Object> localArrayList1 = new ArrayList<Object>();
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			if ((localBaseInputTag.getfixcol() == true)
					&& (!localBaseInputTag.getWidth().trim().equals("0")))
				localArrayList1.add(localBaseInputTag);
		}
		ArrayList<Object> localArrayList2 = CreateExpandHeadList(
				localArrayList1, paramArrayList);
		this.rowVisible = new ArrayList<String>();
		writerowheadType1(strBuffer, localArrayList2, localArrayList1, 0, 0, 0,
				0);
	}

	private int writerowheadType1(StringBuffer strBuffer,
			ArrayList<Object> arraylist, ArrayList<Object> arraylist1, int i,
			int j, int k, int l) {

		for (int l1 = 0; l1 < arraylist.size(); l1++) {
			rowheadstruct rowheadstruct1 = (rowheadstruct) arraylist.get(l1);
			int i1 = readMerageColCount(rowheadstruct1.childlist);
			if (!expand && j != 0) {
				rowVisible.add("0");
				strBuffer
						.append("<tr style=\"display:none\" onclick=\"javascript:"
								+ property.trim()
								+ "clickselect(this);\" id=\"tr"
								+ String.valueOf(k) + "\">");
			} else {
				rowVisible.add("1");
				strBuffer.append("<tr onclick=\"javascript:" + property.trim()
						+ "clickselect(this);\" id=\"tr" + String.valueOf(k)
						+ "\">");
			}
			if (l1 == 0 && i > 0) {
				strBuffer.append("<td");
				if (i > 1)
					strBuffer.append(" rowspan=" + String.valueOf(i));
				strBuffer.append(" >&nbsp;</td>");
			}
			int j1 = readchildcount(rowheadstruct1.childlist);
			if (i1 > 0 || j1 > 0) {
				if (i1 == 0)
					i1 = 1;
				strBuffer
						.append("<td colspan=" + String.valueOf(i1 + 1)
								+ " ondblclick=\"" + property + "expand("
								+ String.valueOf(k) + "," + String.valueOf(j1)
								+ ")\">");
				if (!expand) {
					if (j == 0)
						strBuffer.append("<img border=\"0\" src=\""
								+ gridExpandImage + "\"" + " exp=0 ");
					else
						strBuffer.append("<img border=\"0\" src=\""
								+ gridShrinkImage + "\"" + " exp=1 ");
				} else {
					strBuffer.append("<img border=\"0\" src=\""
							+ gridShrinkImage + "\"" + " exp=1 ");
				}
				strBuffer.append("  onclick=\"" + property + "expand("
						+ String.valueOf(k) + "," + String.valueOf(j1)
						+ ")\"  ID=\"img\" child=\"" + String.valueOf(j1)
						+ "\"");
				if (l == 0)
					strBuffer.append(" parent=\"\"");
				else
					strBuffer.append(" parent=\"" + String.valueOf(l) + "\"");
				strBuffer.append(" cel=\"" + String.valueOf(j) + "\" >&nbsp;"
						+ rowheadstruct1.value.get(j) + "</td>");
			} else {
				int k1 = 0;
				if (j == 0) {
					for (int i2 = 0; i2 < arraylist1.size() - 1
							&& rowheadstruct1.value.get(i2).equals(
									rowheadstruct1.value.get(i2 + 1)); i2++)
						k1++;

				}
				if (k1 > 0) {
					strBuffer.append("<td colSpan=" + String.valueOf(k1 + 1)
							+ ">");
					i1 = k1;
				} else {
					strBuffer.append("<td >");
				}
				strBuffer.append("<img border=\"0\" src =\"" + gridSpaceImage
						+ "\""
						+ " exp=0 ID=\"img\" child=\"0\" parent=\"\" cel=\""
						+ String.valueOf(j) + "\" >&nbsp;");
				strBuffer.append(rowheadstruct1.value.get(j));
				strBuffer.append("</td>");
			}
			for (int j2 = j + i1 + 1; j2 < arraylist1.size(); j2++) {
				strBuffer.append("<td >" + rowheadstruct1.value.get(j2)
						+ "</td>");
			}

			strBuffer.append("</tr>\n");
			k++;
			if (rowheadstruct1.childlist.size() > 0) {
				i = j1;
				k = writerowheadType1(strBuffer, rowheadstruct1.childlist,
						arraylist1, i, j + 1, k, k - 1);
			}
		}

		return k;
	}

	private int readMerageColCount(ArrayList<Object> paramArrayList) {
		int i = 0;
		int j = 0;
		for (int k = 0; k < paramArrayList.size(); k++) {
			rowheadstruct localrowheadstruct = (rowheadstruct) paramArrayList
					.get(k);
			if (localrowheadstruct.childlist.size() > 0) {
				i = readchildcount(localrowheadstruct.childlist);
				if (i > 0)
					j += 1;
			}
		}
		return j;
	}

	private int readchildcount(ArrayList<Object> paramArrayList) {
		int i = 0;
		for (int j = 0; j < paramArrayList.size(); j++) {
			rowheadstruct localrowheadstruct = (rowheadstruct) paramArrayList
					.get(j);
			i += 1;
			if (localrowheadstruct.childlist.size() > 0)
				i += readchildcount(localrowheadstruct.childlist);
		}
		return i;
	}

	private ArrayList<Object> CreateExpandHeadList(ArrayList<Object> arraylist,
			ArrayList<Object> arraylist1) {
		ArrayList<Object> arraylist2 = new ArrayList<Object>();
		int i = 0;
		if (mergecolcount == 0 || mergecolcount > arraylist.size() - 2)
			mergecolcount = arraylist.size() - 2;
		String s = "";
		if (arraylist1 != null) {
			int j = arraylist1.size();
			for (int k = 0; k < arraylist1.size()
					&& (showrowcount == -1 || k < showrowcount)
					&& (!showtotal || !collection.equals(totalcollection) || k != arraylist1
							.size() - 1); k++) {
				Object obj = arraylist1.get(k);
				rowheadstruct rowheadstruct2 = new rowheadstruct();
				for (int l = 0; l < arraylist.size(); l++) {
					String s1;
					try {
						s1 = MethodFactory.getThisString(PropertyUtils
								.getProperty(obj, String
										.valueOf(((BaseInputTag) arraylist
												.get(l)).getproperty())));
					} catch (Exception exception) {
						s1 = "";
					}
					if (s1.equals(""))
						s1 = "&nbsp;";
					rowheadstruct2.value.add(s1);
				}

				rowheadstruct rowheadstruct1 = null;
				rowheadstruct1 = readPreantRow(arraylist, arraylist2, 0, i,
						rowheadstruct2);
				if (rowheadstruct1 == null)
					arraylist2.add(rowheadstruct2);
				else
					rowheadstruct1.childlist.add(rowheadstruct2);
			}

		}
		return arraylist2;
	}

	private rowheadstruct readPreantRow(ArrayList arraylist,
			ArrayList arraylist1, int i, int j, rowheadstruct rowheadstruct1) {
		rowheadstruct rowheadstruct2 = new rowheadstruct();
		rowheadstruct rowheadstruct3 = new rowheadstruct();
		boolean flag = false;
		if (i > arraylist1.size() || arraylist1.size() == 0) {
			rowheadstruct2 = null;
		} else {
			rowheadstruct3 = (rowheadstruct) arraylist1
					.get(arraylist1.size() - 1);
			if (rowheadstruct3.value.get(i).equals(rowheadstruct1.value.get(i)))
				if (j > i + 1) {
					flag = true;
				} else {
					rowheadstruct3 = readPreantRow(arraylist,
							rowheadstruct3.childlist, i + 1, j, rowheadstruct1);
					if (rowheadstruct3 == null)
						rowheadstruct3 = (rowheadstruct) arraylist1
								.get(arraylist1.size() - 1);
					flag = true;
				}
		}
		if (flag)
			rowheadstruct2 = rowheadstruct3;
		else
			rowheadstruct2 = null;
		return rowheadstruct2;
	}

	private boolean readboolean(Object paramObject) {
		boolean bool = false;
		String str = null;
		str = paramObject.toString().toUpperCase();
		if ((str.equals("TRUE")) || (str.equals("1")))
			bool = true;
		return bool;
	}

	private String getsortname(int paramInt1, int paramInt2) {
		if (paramInt1 == 0)
			return "col" + String.valueOf(paramInt2);
		return "fixcol" + String.valueOf(paramInt2);
	}

	private boolean readshowtotal() {
		boolean bool = false;
		if ((this.showtotal) && (this.totalcollection != null))
			bool = true;
		else
			bool = false;
		return bool;
	}

	private void writetotalcell(StringBuffer strBuffer,
			BaseInputTag paramBaseInputTag, String paramString) {
		BaseLabel localBaseLabel = new BaseLabel();
		localBaseLabel.setdatatype(paramBaseInputTag.getdatatype());
		localBaseLabel.setalign(paramBaseInputTag.getalign());
		localBaseLabel.setformat(paramBaseInputTag.getformat());
		localBaseLabel.generateRows(strBuffer, String.valueOf(paramString));
	}

	/**
	 * 计算表头的行数
	 *
	 * @return
	 */
	private int calcHeadRowCount() {
		int i = 0;
		int j = 0;
		BaseInputTag localBaseInputTag = null;
		for (int k = 0; k < this.columns.size(); k++) {
			localBaseInputTag = (BaseInputTag) this.columns.get(k);
			j = getarraycount(localBaseInputTag.getcaption(),
					this.splitterstring);
			if (j > i)
				i = j;
			if ((localBaseInputTag instanceof BaseText))
				this.cansort = false;
		}
		return i;
	}

	private int getarraycount(String paramString1, String paramString2) {
		int i = 1;
		if ((paramString1 != null) && (paramString2 != null))
			while (paramString1.indexOf(paramString2) != -1) {
				paramString1 = paramString1.substring(paramString1
						.indexOf(paramString2) + paramString2.length());
				i++;
			}
		return i;
	}

	/**
	 * foot
	 *
	 * @param strBuffer
	 * @param paramObject
	 */
	private void foot(StringBuffer strBuffer, Object paramObject) {
		strBuffer.append("<!-- FootDiv Begin -->\n");
		strBuffer.append(" <div style=\"POSITION: relative");
		strBuffer.append(" \">");
		strBuffer
				.append("<table bgcolor=\"#85A8C3\" style=\"POSITION: relative;left:0px\" cellpadding=0 cellspacing=1 border=0");
		strBuffer.append(" name=");
		strBuffer.append(this.property);
		strBuffer.append("\"");
		strBuffer.append(">");
		strBuffer.append("<tr class=\"");
		strBuffer.append(this.oldStyleClass);
		strBuffer.append("\" id=\"tdt\"");
		strBuffer.append(">");
		Object localObject = null;
		for (int i = 0; i < this.columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) this.columns.get(i);
			String str = localBaseInputTag.getProperty();
			try {
				localObject = PropertyUtils.getProperty(paramObject, str);
				if (localObject == null)
					localObject = "";
			} catch (Exception localException) {
				localObject = "";
			}
			localBaseInputTag.genetateFoot(strBuffer,
					String.valueOf(localObject));
		}
		strBuffer.append("</tr>");
		strBuffer.append("</table>");
		strBuffer.append("</DIV>");
		strBuffer.append("<!-- FootDiv End -->\n");
	}

	/**
	 * 表头ArrayList
	 *
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	protected ArrayList<Object> getIterator(String paramCaption,
			String paramString2) {
		// TODO getIterator
		Object localObject = this.pageContext.findAttribute(paramCaption);
		ArrayList<Object> localArrayList = (ArrayList<Object>) localObject;
		if (localObject == null) {
			print("GridReport没有设置caption参数.表头为空!");
			return null;
		}
		return localArrayList;
	}

	/**
	 * 改变选中行的颜色
	 *
	 * @param strBuffer
	 * @param paramInt1
	 * @param paramInt2
	 * @param paramBoolean
	 */
	private void changeSelectColor(StringBuffer strBuffer, int paramInt1,
			int paramInt2, boolean paramBoolean) {
		strBuffer.append(" onclick=\"javascript:" + this.property.trim()
				+ "clickselect(this);");
		strBuffer.append("\" ");
		if ((getOndblclick() != null) && (paramBoolean == true))
			strBuffer.append(" ondblclick =\"" + getOndblclick() + "\"");
	}

	/**
	 * 表头结构数组
	 *
	 * @author CDD
	 *
	 */
	private class rowheadstruct {

		public ArrayList childlist;
		public ArrayList value;

		private rowheadstruct() {
			childlist = new ArrayList();
			value = new ArrayList();
		}

	}

	/**
	 * 列结构
	 *
	 * @author CDD
	 *
	 */
	private class colstruct {
		public String id = null;
		public String name = null;
		public String caption = null;
		public int colnumber = 0;
		public int type = 0;

		public colstruct() {
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseGridReport JD-Core Version: 0.6.1
 */