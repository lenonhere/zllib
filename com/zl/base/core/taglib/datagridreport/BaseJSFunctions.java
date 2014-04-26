package com.zl.base.core.taglib.datagridreport;

import java.util.Vector;

import com.zl.util.MethodFactory;

public class BaseJSFunctions {

	// private StringBuffer strBuffer = null;
	private String property = "";

	public BaseJSFunctions(String property) {
		this.property = property;
	}

	// public BaseJSFunctions(StringBuffer strBuffer, String property) {
	// this.strBuffer = strBuffer;
	// this.property = property;
	// }

	/**
	 * @param strBuffer
	 */
	public void generate_Para(StringBuffer strBuffer) {
		strBuffer.append("// 生成全局变量 Begin\n");
		strBuffer.append("var " + property + "uprow=\"\"; //上一次选择的行索引号\n");
		strBuffer.append("var " + property + "reportrownumber; //选择的行索引号\n");
		strBuffer.append("var " + property + "table00; //固定列头部表格\n");
		strBuffer.append("var " + property + "table01; //固定列内容\n");
		strBuffer.append("var " + property + "table10; //活动列头部表格\n");
		strBuffer.append("var " + property + "table11; //活动列内容\n");
		strBuffer.append("var " + property + "table20; //GridObject表头\n");
		strBuffer.append("var " + property + "table21; //GridObject内容\n");
		strBuffer.append("var " + property + "div000;");
		strBuffer.append("var " + property + "div100;");
		strBuffer.append("var " + property + "div101;");
		strBuffer.append("var " + property + "div102;");
		strBuffer.append("var " + property + "div103;");
		strBuffer.append("var " + property + "div104;");
		strBuffer.append("var " + property + "div105;\n");
		strBuffer.append("// 生成全局变量 End\n");
	}

	/**
	 * @param strBuffer
	 */
	public void generate_gettable(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property + "gettable(type){\n");
		strBuffer.append("\t/*固定列头部表格*/");
		strBuffer.append("\tif(type==\"00\"){");
		strBuffer.append("\t\tif(" + this.property + "table00==null) "
				+ this.property + "table00=document.all[\"" + this.property
				+ "fixhead\"];");
		strBuffer.append("\t\treturn " + this.property + "table00;");
		strBuffer.append("\t}");
		strBuffer.append("\t/*固定列的内容*/");
		strBuffer.append("\tif(type==\"01\"){");
		strBuffer.append("\t\tif(" + this.property + "table01==null) "
				+ this.property + "table01=document.all[\"" + this.property
				+ "fixcolcontent\"];");
		strBuffer.append("\t\treturn " + this.property + "table01;");
		strBuffer.append("\t}");
		strBuffer.append("\t/*内容头部*/");
		strBuffer.append("\tif(type==\"10\"){");
		strBuffer.append("\t\tif(" + this.property + "table10==null) "
				+ this.property + "table10=document.all[\"" + this.property
				+ "contenthead\"];");
		strBuffer.append("\t\treturn " + this.property + "table10;");
		strBuffer.append("\t}");
		strBuffer.append("\t/*内容*/");
		strBuffer.append("\tif(type==\"11\"){");
		strBuffer.append("\t\tif(" + this.property + "table11==null)"
				+ this.property + "table11=document.all[\"" + this.property
				+ "\"];");
		strBuffer.append("\t\treturn " + this.property + "table11;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"20\"){");
		strBuffer.append("\t\tif(" + this.property + "table20==null)"
				+ this.property + "table20=document.all[\"" + this.property
				+ "fixtotal\"];");
		strBuffer.append("\t\treturn " + this.property + "table20;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"21\"){");
		strBuffer.append("\t\tif(" + this.property + "table21==null)"
				+ this.property + "table21=document.all[\"" + this.property
				+ "total\"];");
		strBuffer.append("\t\treturn " + this.property + "table21;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"000\"){");
		strBuffer.append("\t\tif(" + this.property + "div000==null)"
				+ this.property + "div000=document.all[\"" + this.property
				+ "div000\"];");
		strBuffer.append("\t\treturn " + this.property + "div000;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"100\"){");
		strBuffer.append("\t\tif(" + this.property + "div100==null)"
				+ this.property + "div100=document.all[\"" + this.property
				+ "div100\"];");
		strBuffer.append("\t\treturn " + this.property + "div100;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"101\"){");
		strBuffer.append("\t\tif(" + this.property + "div101==null)"
				+ this.property + "div101=document.all[\"" + this.property
				+ "div101\"];");
		strBuffer.append("\t\treturn " + this.property + "div101;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"102\"){");
		strBuffer.append("\t\tif(" + this.property + "div102==null)"
				+ this.property + "div102=document.all[\"" + this.property
				+ "div102\"];");
		strBuffer.append("\t\treturn " + this.property + "div102;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"103\"){\n");
		strBuffer.append("\t\tif(" + this.property + "div103==null)"
				+ this.property + "div103=document.all[\"" + this.property
				+ "div103\"] ;");
		strBuffer.append("\t\treturn " + this.property + "div103;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"104\"){\n");
		strBuffer.append("\t\tif(" + this.property + "div104==null)"
				+ this.property + "div104=document.all[\"" + this.property
				+ "div104\"];");
		strBuffer.append("\t\treturn " + this.property + "div104;");
		strBuffer.append("\t}");
		strBuffer.append("\tif(type==\"105\"){\n");
		strBuffer.append("\t\tif(" + this.property + "div105==null)"
				+ this.property + "div105=document.all[\"" + this.property
				+ "div105\"];");
		strBuffer.append("\t\treturn " + this.property + "div105;");
		strBuffer.append("\t}");
		strBuffer.append("}\n");
	}

	/**
	 * 计算大小
	 * 
	 * @param strBuffer
	 */
	public void generate_calcSize(StringBuffer strBuffer) {
		strBuffer.append("// 计算大小\n");
		strBuffer.append("var " + this.property + "calcSizetimeid;\n");
		strBuffer.append("function " + this.property + "calcSize()  {\n");
		strBuffer.append("\tvar div000 =" + this.property
				+ "gettable(\"000\");\n");
		strBuffer.append("\tvar div100 =" + this.property
				+ "gettable(\"100\");\n");
		strBuffer.append("\t//内容\n");
		strBuffer.append("\tvar div103 =" + this.property
				+ "gettable(\"103\")\n");
		strBuffer.append("\t//内容标题\n");
		strBuffer.append("\tvar div101 =" + this.property
				+ "gettable(\"101\")\n");
		strBuffer.append("\tvar div102 =" + this.property
				+ "gettable(\"102\")\n");
		strBuffer.append("\tvar div104 =" + this.property
				+ "gettable(\"104\")\n");
		strBuffer.append("\tvar div105 =" + this.property
				+ "gettable(\"105\")\n");
		strBuffer.append("\tif (div000==\"[object]\"){\n");
		strBuffer.append("       try{\n");
		strBuffer.append("\t\tif(" + this.property
				+ "calcSizetimeid!=null) clearTimeout(" + this.property
				+ "calcSizetimeid);\n");
		strBuffer.append("\t\t\tdiv103.style.width =div000.clientWidth;\n");
		strBuffer.append("\t\t\tdiv101.style.width = div000.clientWidth-16;\n");
		strBuffer
				.append("\t\t\tdiv102.style.height =  div103.clientHeight-div101.clientHeight;\n");
		strBuffer.append("\t\t\tif (div105==\"[object]\") {\n");
		strBuffer.append("\t\t\t\tdiv105.style.width = div000.clientWidth;\n");
		strBuffer
				.append("\t\t\t\tdiv105.style.top =div000.clientHeight-div105.clientHeight+1;\n");
		strBuffer.append("\t\t\t\tdiv104.style.top =div105.style.top;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t}catch(ex){\n");
		strBuffer.append(this.property + "calcSizetimeid=setTimeout('"
				+ this.property + "calcSize()',100)}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\n");
	}

	/**
	 * 生成列的值
	 * 
	 * @param strBuffer
	 */
	public void generate_getcellvalue(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property
				+ "getdatatype(type,number){\n");
		strBuffer.append("\t//type为0表示内容中的字段，如果为1时表示固定列的年内容；\n");
		strBuffer.append("\tvar main   =null;\n");
		strBuffer.append("\tvar returnvalue =\"0\";\n");
		strBuffer.append("\tvar cols =null;\n");
		strBuffer.append("\tvar col=null;\n");
		strBuffer.append("\tnumber=number.toString();\n");
		strBuffer.append("\tif(type ==0) main=" + this.property
				+ "gettable(\"11\"); else main =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tif (main==\"[object]\"){\n");
		strBuffer.append("\t\tcols=main.all[\"colgroup\"].all[\"col\"];\n");
		strBuffer.append("\t\tif (cols==\"[object]\"){\n");
		strBuffer.append("\t\t\tif(cols.length==null){\n");
		strBuffer
				.append("\t\t\t\tif (\"1\"==number){returnvalue=cols.datatype;}\n");
		strBuffer.append("\t\t\t}else{\n");
		strBuffer.append("\t\t\t\tcol=cols[parseInt(number)]\n");
		strBuffer.append("\t\t\t\treturnvalue=col.datatype;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\treturn returnvalue;\n");
		strBuffer.append("}\n");
		strBuffer.append("//读取单元格的值\n");
		strBuffer.append("function " + this.property
				+ "getcellvalue(cell,datatype){\n");
		strBuffer.append("\t//需要修改，添加各种输入类型的判断\n");
		strBuffer.append("\tvar returnvalue;\n");
		strBuffer.append("\tif (cell.childNodes.length==0)\n");
		strBuffer.append("\t\treturnvalue=cell.innerText;\n");
		strBuffer.append("\telse{\n");
		strBuffer.append("\tif(cell.childNodes[0].tagName==null)\n");
		strBuffer.append("\t\treturnvalue=cell.innerText;\n");
		strBuffer
				.append("\telse{ if(cell.childNodes[0].tagName.toUpperCase()==\"INPUT\"){\n");
		strBuffer.append("\t\tvar type\n");
		strBuffer.append("\t\ttype=cell.childNodes[0].type;\n");
		strBuffer.append("\t\tif (type==null) type =\"\";\n");
		strBuffer.append("\t\tswitch (type.toUpperCase()){\n");
		strBuffer.append("\t\t   case \"CHECKBOX\": \n");
		strBuffer.append("\t\t\t  if(cell.childNodes[0].checked==true)\n");
		strBuffer.append("\t\t\t\treturnvalue=\"1\";\n");
		strBuffer.append("\t\t\t  else\n");
		strBuffer.append("\t\t\t\treturnvalue=\"0\";\n");
		strBuffer.append("\t\t\t  break;\n");
		strBuffer.append("\t\t   case \"TEXT\": \n");
		strBuffer.append("\t\t\t  returnvalue=cell.childNodes[0].value\n");
		strBuffer.append("\t\t\t  break;\n");
		strBuffer.append("\t\tdefault : \n");
		strBuffer.append("\t\t\treturnvalue=cell.innerText;\n");
		strBuffer.append("\t\t} \n");
		strBuffer.append("\t}else{returnvalue=cell.innerText;}}}\n");
		strBuffer.append("if(datatype!=null){\n");
		strBuffer.append("    if(datatype==\"0\")returnvalue=returnvalue;\n");
		strBuffer.append("    else if(datatype==\"1\"){\n");
		strBuffer
				.append("                                    returnvalue=parseFloat(returnvalue);\n");
		strBuffer
				.append("                                    if (isNaN(returnvalue)) returnvalue=0;\n");
		strBuffer.append("                                    }}\n");
		strBuffer.append("\treturn returnvalue;\t\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取选中行指定列的值
	 * 
	 * @param strBuffer
	 */
	public void generate_getselectvalue(StringBuffer strBuffer) {
		strBuffer.append("// 获取选中行指定列的值\n");
		strBuffer.append("function " + this.property
				+ "getselectvalue(colname)\n");
		strBuffer.append("{\n");
		strBuffer.append("\tvar returnvalue=null;\n");
		strBuffer.append("\tif ( " + this.property
				+ "reportrownumber!=null){\n");
		strBuffer.append("\t\treturnvalue = " + this.property
				+ "getvalue(0,colname);\n");
		strBuffer.append("\tif (returnvalue==null) returnvalue = "
				+ this.property + "getvalue(1,colname);}\n");
		strBuffer.append("\treturn \treturnvalue;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 设置选中行指定列的值
	 * 
	 * @param strBuffer
	 */
	public void generate_setselectvalue(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property
				+ "setselectvalue(colname,value)\n");
		strBuffer.append("{\n");
		strBuffer.append("\t" + this.property + "getvalue(0,colname,value);\n");
		strBuffer.append("\t" + this.property + "getvalue(1,colname,value);\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取指定列的序号 [type为0表示内容中的字段，如果为1时表示固定列的年内容；]
	 * 
	 * @param strBuffer
	 */
	public void generate_getcolnumber(StringBuffer strBuffer) {
		strBuffer.append("// 读取列的序号\n");
		strBuffer.append("function " + this.property
				+ "getcolnumber(type,colname){\n");
		strBuffer.append("\t//type为0表示内容中的字段，如果为1时表示固定列的年内容；\n");
		strBuffer.append("\tvar main   =null;\n");
		strBuffer.append("\tvar colnumber =null;\n");
		strBuffer.append("\tvar cols =null;\n");
		strBuffer.append("\tvar col=null;\n");
		strBuffer.append("\tif(type ==0) main=" + this.property
				+ "gettable(\"11\"); else main =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tif (main==\"[object]\"){\n");
		strBuffer.append("\t\tcols=main.all[\"colgroup\"].all[\"col\"];\n");
		strBuffer.append("\t\tif (cols==\"[object]\"){\n");
		strBuffer.append("\t\t\tif(cols.length==null){\n");
		strBuffer.append("\t\t\t\tif (cols.name==colname){colnumber=0;}\n");
		strBuffer.append("\t\t\t}else{\n");
		strBuffer.append("\t\t\tfor(var i =0 ;i<cols.length;i++){\n");
		strBuffer.append("\t\t\t\tcol= cols[i];\n");
		strBuffer
				.append("\t\t\t\tif (col.name.toUpperCase()==colname.toUpperCase()){colnumber=i;break;}}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\treturn colnumber;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取选中行的数据 [type为0表示内容中的字段，如果为1时表示固定列的年内容；]
	 * 
	 * @param strBuffer
	 */
	public void generate_getvalue(StringBuffer strBuffer) {
		strBuffer.append("//读取选中行的数据\n");
		strBuffer.append("function " + this.property
				+ "getvalue(type ,colname,value)\n");
		strBuffer.append("{\n");
		strBuffer.append("\tvar main   =null;\n");
		strBuffer.append("\tvar colnumber =null;\n");
		strBuffer.append("\tvar cols =null;\n");
		strBuffer.append("\tvar col=null;\n");
		strBuffer.append("\tvar returnvalue=null;\n");
		strBuffer.append("\tvar rownumber  =null;\n");
		strBuffer.append("\t\trownumber=parseInt(" + this.property
				+ "reportrownumber);\n");
		strBuffer.append("if(type ==0) main=" + this.property
				+ "gettable(\"11\"); else main =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tif (rownumber!=null&&main.rows.length>0){\n");
		strBuffer.append("\t        colnumber=" + this.property
				+ "getcolnumber(type,colname);\n");
		strBuffer.append("\t\tif (rownumber!=null&&colnumber!=null){\n");
		strBuffer.append("\t\t\tcol=main.rows(rownumber).cells(colnumber);\n");
		strBuffer.append("\t\t\tif (col==\"[object]\"){\n");
		strBuffer.append("\t\t\t\tif(value!=null){\n");
		strBuffer
				.append("\t\t\t\t  if(col.childNodes[0].tagName!=null&& col.childNodes[0].tagName.toUpperCase()==\"INPUT\"){\n");
		strBuffer.append("\t\t\t\t  var type;\n");
		strBuffer.append("\t\t\t\t  type=col.childNodes[0].type;\n");
		strBuffer.append("if (type==null) type =\"\";\n");
		strBuffer.append("switch (type.toUpperCase()){\n");
		strBuffer.append("case \"CHECKBOX\": \n");
		strBuffer.append("if(value==\"1\"||value.toUpperCase()==\"TRUE\")\n");
		strBuffer.append("  col.childNodes[0].checked=true;\n");
		strBuffer.append(" else\n");
		strBuffer.append("  col.childNodes[0].checked=false;\n");
		strBuffer.append(" break;\n");
		strBuffer.append("case \"TEXT\": \n");
		strBuffer.append("  col.childNodes[0].value=value;\n");
		strBuffer.append("  break;\n");
		strBuffer.append("default : \n");
		strBuffer.append("\tcol.innerText=value;\n");
		strBuffer.append("} \n");
		strBuffer.append("\t\t\t\t  }else{col.innerText=value;}\n");
		strBuffer.append("\t\t\t    }else return value=" + this.property
				+ "getcellvalue(col);}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("        }\n");
		strBuffer.append("}\n");
	}

	/**
	 * 生成滚动条
	 * 
	 * @param strBuffer
	 */
	public void generate_scroll(StringBuffer strBuffer) {
		strBuffer.append("// 生成滚动条\n");
		strBuffer.append("function " + this.property + "scroll(){\n");
		strBuffer.append("\tvar main   = " + this.property
				+ "gettable(\"103\")\n");
		strBuffer.append("\tvar head   = " + this.property
				+ "gettable(\"101\")\n");
		strBuffer.append("\tvar idcol  = " + this.property
				+ "gettable(\"102\")\n");
		strBuffer.append("\tvar total  = " + this.property
				+ "gettable(\"105\")\n");
		strBuffer.append("\thead.scrollLeft = main.scrollLeft;\n");
		strBuffer
				.append("if (total==\"[object]\") total.scrollLeft = main.scrollLeft;");
		strBuffer
				.append("\tif (idcol==\"[object]\") idcol.scrollTop = main.scrollTop;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 表格点击事件(选中一行)
	 * 
	 * @param strBuffer
	 */
	public void generate_clickselect(StringBuffer strBuffer, String ondblclick,
			String onclick) {
		// 鼠标双击事件
		if (ondblclick != null) {
			strBuffer.append("//鼠标双击事件\n");
			strBuffer.append("function " + this.property + "ondblclick(){\n");
			strBuffer.append(ondblclick + ";\n");
			strBuffer.append("}\n");
		}
		// 鼠标点击事件
		strBuffer.append("//鼠标点击事件\n");
		strBuffer.append("function " + this.property + "clickselect(obj){\n");
		strBuffer.append("\tif(obj==\"[object]\"){\n");
		strBuffer.append("\t\tif (obj.tagName.toUpperCase()==\"TR\"){\n");
		strBuffer.append("\t\t\tindex = obj.rowIndex;\n");
		strBuffer.append("\t\t\t" + this.property + "selectchange(index);\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}else{\n");
		strBuffer.append("\t\tif(obj==null){\n");
		strBuffer.append("\t\t\t\tobj=event.srcElement;\n");
		strBuffer
				.append("\t\t\tif (event.srcElement.tagName.toUpperCase()!=\"TR\"){\n");
		strBuffer
				.append("\t\t\t\twhile(obj.parentElement.tagName.toUpperCase()!=\"TR\"){\n");
		strBuffer
				.append("\t\t\t\t\tif (obj.parentElement==null) break; else obj=obj.parentElement;\n");
		strBuffer.append("\t\t\t\t}\t\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t\tif(obj.parentElement!=null){\n");
		strBuffer
				.append("\t\t\t\tif (obj.parentElement.tagName.toUpperCase()==\"TR\"){\n");
		strBuffer.append("\t\t\t\t\tindex = obj.parentElement.rowIndex;\n");
		strBuffer.append("\t\t\t\t\t" + this.property
				+ "selectchange(index);\n");
		strBuffer.append("\t\t\t\t}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		if (onclick != null) {
			String str = onclick;
			try {
				str = MethodFactory.replace(str.trim(), "\"", "'");
			} catch (Exception localException) {
			}
			strBuffer.append("eval(\"" + str + "\")\n");
		}
		strBuffer.append("}\n");
	}

	/**
	 * 选中表格中的一行后的事件
	 * 
	 * @param strBuffer
	 */
	public void generate_selectchange(StringBuffer strBuffer, String onrowchange) {
		strBuffer.append("function " + this.property + "selectchange(trNo){\n");
		strBuffer.append("\n");
		strBuffer.append("    " + this.property + "ChangeColor(trNo);\n");
		strBuffer
				.append("var rowTemp =" + this.property + "reportrownumber;\n");
		strBuffer.append("    " + this.property + "reportrownumber=trNo;\n");
		if (onrowchange != null) {
			strBuffer.append("  if(rowTemp!=trNo)");
			strBuffer.append("        " + onrowchange + "\n");
		}
		strBuffer.append("if(" + this.property + "reportrownumber!=null){\n");
		strBuffer.append("\tvar objname=\"" + this.property + "selectid\"\n");
		strBuffer.append("\tvar selectidtemp;\n");
		strBuffer.append("\tselectidtemp= " + this.property + "selectid();\n");
		strBuffer.append("    var days = 7;\n");
		strBuffer.append("    var expdate = new Date();\n");
		strBuffer
				.append("expdate.setTime (expdate.getTime() + (86400 * 1000 * days));\n");
		strBuffer.append("    setCookie(objname, selectidtemp, expdate);\n");
		strBuffer.append("}\n");
		strBuffer.append("\n");
		strBuffer.append("}\n");
	}

	/**
	 * 改变选中行的背景色
	 * 
	 * @param strBuffer
	 */
	public void generate_ChangeColor(StringBuffer strBuffer, String bgcolor,
			String selectcolor) {
		strBuffer.append("//改变选中行的背景色\n");
		strBuffer.append("function " + this.property.trim()
				+ "ChangeColor(id) {\n");
		strBuffer.append("var main =document.getElementById(\"" + this.property
				+ "\");");
		strBuffer.append("if(main!=null){");
		strBuffer.append("if (main.rows(parseInt(" + this.property
				+ "uprow))==\"[object]\")");
		//背景色
		if (bgcolor == null) {
			//默认为白色#FFFFFF
			strBuffer.append("main.rows(parseInt(" + this.property
					+ "uprow)).style.backgroundColor=\"#FFFFFF\";\n");
		} else {
			//自定义背景色 taglib.properties
			strBuffer.append("main.rows(parseInt(" + this.property
					+ "uprow)).style.backgroundColor=\"" + bgcolor + "\";\n");
		}
		strBuffer.append("  if(id!=null) \n");
		strBuffer.append("          " + this.property + "uprow =id;\n");
		strBuffer.append("   if (main.rows(parseInt(" + this.property
				+ "uprow))==\"[object]\") ");
		//选择行的颜色
		if (selectcolor == null) {
			//默认为白色#FFFFFF
			strBuffer.append("main.rows(parseInt(" + this.property
					+ "uprow)).style.backgroundColor=\"#FFFFFF\";\n}\n");
		} else {
			//自定义背景色 taglib.properties
			strBuffer.append("main.rows(parseInt(" + this.property
					+ "uprow)).style.backgroundColor=\"" + selectcolor
					+ "\";\n}\n");
		}
		strBuffer.append("}\n");
	}

	/**
	 * 生成SelectId
	 * 
	 * @param strBuffer
	 */
	public void generate_selectid(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property + "selectid ()\n");
		strBuffer.append("{\n");
		strBuffer.append("var main=" + this.property + "gettable('11');\n");
		strBuffer.append("if (" + this.property + "reportrownumber!=null){\n");
		strBuffer.append("\tif (main==\"[object]\"){\n");
		strBuffer.append("\tif (main.all[\"selectid\"]==\"[object]\"){\n");
		strBuffer.append("\t\tif(main.all[\"selectid\"].length==null){\n");
		strBuffer.append("\t\t\treturn main.all[\"selectid\"].innerText;\n");
		strBuffer.append("\t\t}else{\n");
		strBuffer.append("\t\tif (main.all[\"selectid\"](parseInt("
				+ this.property + "reportrownumber))==\"[object]\"){\n");
		strBuffer.append("\t\t\treturn main.all[\"selectid\"](parseInt("
				+ this.property + "reportrownumber)).innerText\n");
		strBuffer.append("      }else{return \"\";}\n");
		strBuffer.append("\t  }\n");
		strBuffer.append("      }else{return \"\";}\n");
		strBuffer.append("}else{return \"\";}\n");
		strBuffer.append("}else{return \"\"}\n");
		strBuffer.append("}\n");
	}

	/**
	 * 生成键盘下下键事件
	 * 
	 * @param strBuffer
	 */
	public void generate_checkUpDown(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property + "keyDown(){\n");
		strBuffer
				.append("  if (event.keyCode==9) return false; else return true;}\n");
		strBuffer.append("function " + this.property + "checkUpDown()\n");
		strBuffer.append("{//检测上下键\n");
		strBuffer.append("var obj\n");
		strBuffer.append("obj =document.all[\"" + this.property + "\"];\n");
		strBuffer.append("var rowcount=obj.rows.length\n");
		strBuffer
				.append("var rownumber=" + this.property + "reportrownumber\n");
		strBuffer.append("if (rowcount>0){\n");
		strBuffer.append("        var keyCode = event.keyCode\n");
		strBuffer.append("        var main   = " + this.property
				+ "gettable(\"103\")\n");
		strBuffer.append("        var mainHeight =main.offsetHeight\n");
		strBuffer.append("        var rowheight=19\n");
		strBuffer
				.append("        var PageRow = parseInt((mainHeight-obj.offsetTop)/rowheight-0.5)\n");
		strBuffer.append("        var rowchange =0;\n");
		strBuffer.append("        var cellchange =0;\n");
		strBuffer.append("\t\t   var cellindex=-1;");
		strBuffer
				.append("        var tagname =event.srcElement.tagName.toUpperCase();\n");
		strBuffer.append("        if(rownumber==null ) rownumber=1\n");
		strBuffer.append("        if(keyCode==38){rowchange=-1\n");
		strBuffer.append("        }else if(keyCode==40){rowchange=1\n");
		strBuffer
				.append("        }else if(keyCode==33){rowchange=-1*PageRow\n");
		strBuffer.append("        }else if(keyCode==34){rowchange=PageRow\n");
		strBuffer.append("        }else if(keyCode==13){cellchange=1;}\n");
		strBuffer.append("\t\tif(cellchange!=0&&tagname==\"INPUT\"){\n");
		strBuffer.append("\t\t  var rowindex=-1;");
		strBuffer
				.append("\t\t  for(i=event.srcElement.parentElement.cellIndex+1;i<obj.rows(0).cells.length;i++){\n");
		strBuffer
				.append("\t\t\tif(obj.rows(0).cells(i).childNodes.length>0&&obj.rows(0).cells(i).childNodes[0].tagName!=null){\n");
		strBuffer
				.append("\t\t\t  if(obj.rows(0).cells(i).childNodes[0].tagName.toUpperCase()==\"INPUT\"){");
		strBuffer.append("\t\t        rowindex=0;\n");
		strBuffer.append("\t\t        cellindex=i;\n");
		strBuffer.append("\t\t        break;\n");
		strBuffer.append("\t\t      }\n");
		strBuffer.append("\t\t    }\n");
		strBuffer.append("\t\t  }\n");
		strBuffer
				.append("\t\t  if(cellindex==-1&&rownumber<obj.rows.length-1){\n");
		strBuffer
				.append("\t\t   for(i=0;i<=event.srcElement.parentElement.cellIndex;i++){\n");
		strBuffer
				.append("\t\t\tif(obj.rows(0).cells(i).childNodes.length>0&&obj.rows(0).cells(i).childNodes[0].tagName!=null){\n");
		strBuffer
				.append("\t\t\t  if(obj.rows(0).cells(i).childNodes[0].tagName.toUpperCase()==\"INPUT\"){");
		strBuffer.append("\t\t        rowindex=1;\n");
		strBuffer.append("\t\t        cellindex=i;\n");
		strBuffer.append("\t\t        break;\n");
		strBuffer.append("\t\t      }\n");
		strBuffer.append("\t\t    }\n");
		strBuffer.append("\t\t   }\n");
		strBuffer.append("\t\t  }\n");
		strBuffer.append("\t\t if(cellindex!=-1){\n");
		strBuffer
				.append("\t\t   var cell=obj.rows(rownumber).cells(cellindex);\n");
		strBuffer.append("\t\t   cell.childNodes[0].focus();\n");
		strBuffer.append("\t\t   cell.childNodes[0].select();\n");
		strBuffer.append("\t\t }\n");
		strBuffer.append("\t\t if(rowindex==1){\n");
		strBuffer.append("\t\t   rowchange=1;\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t  }\n");
		strBuffer.append("        rownumber=parseInt(rownumber)+rowchange;\n");
		strBuffer.append("\t\t   if (rowchange!=0){\n");
		strBuffer.append("        if (rownumber<=0) rownumber=0;\n");
		strBuffer
				.append("        if (rownumber>=rowcount-1) rownumber=rowcount-1;\n");
		strBuffer.append("        " + this.property
				+ "selectchange(rownumber);\n");
		strBuffer.append("        var uprow =rownumber\n");
		strBuffer.append("        var o = obj.rows(parseInt(uprow))\n");
		strBuffer.append("        if(rowchange==-1){\n");
		strBuffer
				.append("\t\t\tif(main.scrollTop>o.offsetTop) main.scrollTop=main.scrollTop+rowchange*rowheight;\n");
		strBuffer.append("        }\n");
		strBuffer.append("\t\tif (rowchange==1){\n");
		strBuffer
				.append("\t\t\tif(main.scrollTop+mainHeight<o.offsetTop+obj.offsetTop+rowheight)\n");
		strBuffer.append("\t\t\t\tmain.scrollTop=main.scrollTop+rowheight;\n");
		strBuffer.append("\t        }\n");
		strBuffer.append("\t\tif (rowchange!=1&&rowchange!=-1){\n");
		strBuffer
				.append("\t\t\tmain.scrollTop=main.scrollTop+rowchange*rowheight\n");
		strBuffer.append("\t\t}\n");
		strBuffer
				.append("      if (cellindex==-1){cellindex=event.srcElement.parentElement.cellIndex;}\n");
		strBuffer.append("\t\t if(tagname==\"INPUT\"){\n");
		strBuffer.append("\t\t   var cell=o.cells(cellindex);\n");
		strBuffer.append("\t\t   cell.childNodes[0].select();\n");
		strBuffer.append("\t\t   cell.childNodes[0].focus();\n");
		strBuffer.append("\t\t }\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("     }\n");
		strBuffer
				.append("    if (event.keyCode==9) return false; else return true;}\n");
	}

	/**
	 * 显示tip提示信息事件
	 * 
	 * @param strBuffer
	 */
	public void generate_showtiptext(StringBuffer strBuffer) {
		strBuffer.append("// 显示IPText Begin \n");
		strBuffer.append("var " + this.property + "oldobj\n");
		strBuffer.append("var " + this.property + "timeid\n");
		strBuffer.append("var " + this.property
				+ "tPopWait=1000;//停留tWait豪秒后显示提示。\n");
		strBuffer.append("var " + this.property
				+ "tPopShow=4000;//显示tShow豪秒后关闭提示\n");
		strBuffer.append("var " + this.property + "poptimeid;\n");
		strBuffer.append("var " + this.property + "newobj;\n");
		/* showPopupText */
		strBuffer.append("function " + this.property + "showPopupText(){\n");
		strBuffer.append("\t" + this.property + "newobj=event.srcElement;\n");
		strBuffer.append("\tif (" + this.property + "oldobj!=" + this.property
				+ "newobj)\n");
		strBuffer.append("\t{\n");
		strBuffer.append("\t\t" + this.property + "fadeOut();\n");
		strBuffer.append("\t\tclearTimeout(" + this.property + "poptimeid);\n");
		strBuffer.append("\t\tvar x=event.x\n");
		strBuffer.append("\t\tvar y=event.y\n");
		strBuffer.append("\t\t" + this.property + "poptimeid = setTimeout(\""
				+ this.property + "showIt(\"+x+\",\"+y+\")\"," + this.property
				+ "tPopWait);\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\n");
		/* showIt */
		strBuffer.append("function " + this.property + "showIt(x,y){\n");
		strBuffer.append("\t\tvar MouseX=x;\n");
		strBuffer.append("\t\tvar MouseY=y;\n");
		strBuffer.append("\t\tvar text =" + this.property + "showPopupTipText("
				+ this.property + "newobj);\n");
		/* 去除前后空格 */
		strBuffer.append("\t\t// 注意去除前后空格\n");
		strBuffer.append("\t\ttext=text.replace(' ','');\n");
		//
		strBuffer.append("\t\tif(text!=\"\"){\n");
		strBuffer.append("\t\t\t" + this.property
				+ "dypopLayer.innerHTML=text;\n");
		strBuffer.append("\t\t\tpopWidth=" + this.property
				+ "dypopLayer.clientWidth;\n");
		strBuffer.append("\t\t\tpopHeight=" + this.property
				+ "dypopLayer.clientHeight;\n");
		strBuffer
				.append("\t\t\tif(MouseX+12+popWidth>document.body.clientWidth) popLeftAdjust=-popWidth-24\n");
		strBuffer.append("\t\t\t\telse popLeftAdjust=0;\n");
		strBuffer
				.append("\t\t\tif(MouseY+12+popHeight>document.body.clientHeight) popTopAdjust=-popHeight-24\n");
		strBuffer.append("\t\t\t\telse popTopAdjust=0;\n");
		strBuffer
				.append("\t\t\t"
						+ this.property
						+ "dypopLayer.style.left=MouseX+12+document.body.scrollLeft+popLeftAdjust;\n");
		strBuffer
				.append("\t\t\t"
						+ this.property
						+ "dypopLayer.style.top=MouseY+12+document.body.scrollTop+popTopAdjust;\n");
		strBuffer.append("\t\t\t" + this.property
				+ "dypopLayer.style.filter=\"Alpha(Opacity=0)\";\n");
		strBuffer.append("\t\t\t" + this.property
				+ "dypopLayer.filters.Alpha.opacity=80;\n");
		strBuffer.append("\t\t\tclearTimeout(" + this.property + "timeid);\n");
		strBuffer.append("\t\t\t" + this.property + "timeid = setTimeout(\""
				+ this.property + "fadeOut()\"," + this.property
				+ "tPopShow);\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("}\n");
		/* mouseOut */
		strBuffer.append("function " + this.property + "mouseout(){\n");
		strBuffer.append("\t" + this.property + "fadeOut()\n");
		strBuffer.append("\tclearTimeout(" + this.property + "timeid);\n");
		strBuffer.append("}\n");
		/* fadeOut */
		strBuffer.append("function " + this.property + "fadeOut(){\n");
		strBuffer.append("\t" + this.property
				+ "dypopLayer.filters.Alpha.opacity=0;\n");
		strBuffer.append("\t" + this.property + "oldobj=null;\n");
		strBuffer.append("}\n");
		/* showPopupTipText */
		strBuffer.append("\tfunction " + this.property
				+ "showPopupTipText(obj){\n");
		strBuffer.append("\t\tvar text\n");
		strBuffer.append("\t\tvar cell\n");
		strBuffer.append("\t\tvar obj\n");
		strBuffer.append("\t\tif(obj==\"[object]\"){\n");
		strBuffer.append("\t\t\tif (obj.tagName.toUpperCase()==\"TD\"){\n");
		strBuffer.append("\t\t\t\tcell =obj;\n");
		strBuffer.append("\t\t\t}else{");
		strBuffer.append("while(obj!=null&&obj.parentElement!=null){");
		strBuffer
				.append("if (obj.parentElement.tagName.toUpperCase()==\"TD\"){");
		strBuffer.append("cell =obj.parentElement;");
		strBuffer.append("obj=null;");
		strBuffer.append("}else{");
		strBuffer.append("obj=obj.parentElement;");
		strBuffer.append("}");
		strBuffer.append("}");
		strBuffer.append("}\n");
		strBuffer.append("\t\t}else{\n");
		strBuffer.append("\t\t\tif(obj==null){\n");
		strBuffer
				.append("\t\t\t\tif (event.srcElement.tagName.toUpperCase()!=\"TD\"){\n");
		strBuffer
				.append("\t\t\t\t\twhile(obj.parentElement.tagName.toUpperCase()!=\"TD\"){\n");
		strBuffer
				.append("\t\t\t\t\t\tif (obj.parentElement==null) break; else obj=obj.parentElement;\n");
		strBuffer.append("\t\t\t\t\t}\t\n");
		strBuffer.append("\t\t\t\t}\n");
		strBuffer.append("\t\t\t\tif(obj.parentElement!=null){\n");
		strBuffer
				.append("\t\t\t\t\tif (obj.parentElement.tagName.toUpperCase()==\"TD\"){\n");
		strBuffer.append("\t\t\t\t\t\tcell =obj;\n");
		strBuffer.append("\t\t\t\t\t}\n");
		strBuffer.append("\t\t\t\t}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t\tif(cell!=null){\n");
		strBuffer.append("\t\t\ttext =" + this.property
				+ "getcellvalue(cell)\n");
		strBuffer
				.append("\t\t\t//if(text.tostring().length!=0) showPopupTipText(text);\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t\tif(text==null) text =\"\";\n");
		strBuffer.append("\t\treturn text.toString();\n");
		strBuffer.append("\t}\n");
		strBuffer.append("// 显示IPText End \n");
	}

	/**
	 * 获取详细信息
	 * 
	 * @param strBuffer
	 */
	public void generate_getDetail(StringBuffer strBuffer,
			Vector<Object> columns, String rowsplitstring, String colsplitstring) {
		String str = "";
		for (int i = 0; i < columns.size(); i++) {
			BaseInputTag localBaseInputTag = (BaseInputTag) columns.get(i);
			if (localBaseInputTag.getIsReturn().equalsIgnoreCase("true"))
				if (str.equals(""))
					str = localBaseInputTag.getproperty();
				else
					str = str + "," + localBaseInputTag.getproperty();
		}
		strBuffer.append("// 获取isReturn=true的列的值\n");
		strBuffer.append(" function " + this.property + "getsavevalue(){\n");
		if ((str == null) || (str.equals("")))
			strBuffer.append(" \treturn  \"\"");
		else
			strBuffer.append(" \treturn  " + this.property + "getdetail(\""
					+ str + "\")");
		strBuffer.append(" }\n");
		strBuffer.append("// 获取指定列的详细信息\n");
		strBuffer.append(" function " + this.property
				+ "getdetail(fieldsname){\n");
		strBuffer.append(" \treturn  " + this.property
				+ "getdetailbycheck(fieldsname);\n");
		strBuffer.append(" }\n");
		strBuffer.append("// 获取选中行的指定列的详细信息\n");
		strBuffer.append(" function " + this.property
				+ "getdetailbycheck(fieldsname,state){\n");
		strBuffer.append(" \tvar col;\n");
		strBuffer.append(" \tvar\tcheckvalue;\n");
		strBuffer.append(" \tif (state==null) state=\"-1\";\n");
		strBuffer.append(" \tstate=state.toString();\n");
		strBuffer.append("// state=true/1表示选中的行\n");
		strBuffer
				.append(" \tif(state.toUpperCase()==\"TRUE\"||state==\"1\")\n");
		strBuffer.append(" \t\tcheckvalue=1;\n");
		strBuffer
				.append(" \telse if (state.toUpperCase()==\"FALSE\"||state==\"0\")\n");
		strBuffer.append(" \t\tcheckvalue=0;\n");
		strBuffer.append(" \telse\n");
		strBuffer.append(" \t\tcheckvalue=-1;\n");
		strBuffer.append(" \tfieldsname=fieldsname.toString();\n");
		strBuffer.append(" \tcol=fieldsname.split(\",\")\n");
		strBuffer.append(" \tvar main   = " + this.property
				+ "gettable(\"11\")\n");
		strBuffer.append(" \tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append(" \tvar colnumber =[col.length];\n");
		strBuffer.append(" \tvar i;\n");
		strBuffer.append(" \tvar colnumbertemp;\n");
		strBuffer.append("  var Data = new Array();\n");
		strBuffer.append(" \tvar rowTemp\n");
		strBuffer.append(" \tvar rowcount =0;\n");
		strBuffer.append(" \tfor (i =0 ;i<col.length;i++){\n");
		strBuffer.append(" \t\tcolnumbertemp=" + this.property
				+ "getcolnumber(0,col[i]);\n");
		strBuffer.append(" \t\t//如果是固定列中读取时，采用负号表示\n");
		strBuffer.append(" \t\tif(colnumbertemp==null){\n");
		strBuffer.append(" \t\t\tcolnumbertemp=" + this.property
				+ "getcolnumber(1,col[i]);\n");
		strBuffer.append(" \t\t\tcolnumbertemp=parseFloat(colnumbertemp)\n");
		strBuffer.append(" \t\t\tif (isNaN(colnumbertemp)==false) {\n");
		strBuffer.append(" \t\t\t\tcolnumbertemp=-1*(colnumbertemp+1);\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append(" \t\t}\n");
		strBuffer.append(" \t\tcolnumber[i]=colnumbertemp\n");
		strBuffer.append(" \t}\n");
		strBuffer.append(" \trowsplitstring=\"" + rowsplitstring + "\";\n");
		strBuffer.append("\tcolsplitstring =\"" + colsplitstring + "\";\n");
		strBuffer.append(" \tvar j;\n");
		strBuffer.append(" \tvar blnrun;\n");
		strBuffer.append(" \tvar valueTemp;\n");
		strBuffer.append(" \tvar cell;\n");
		strBuffer.append(" \tvar frow;\n");
		strBuffer.append(" \tnum =main.rows.length;\n");
		strBuffer.append(" \tfor (i =0 ;i<num;i++){\n");
		strBuffer.append(" \t\tblnrun=false;\n");
		strBuffer.append(" \t\tfrow=main.rows(i);\n");
		strBuffer.append(" \t\t//检查是否符合条件的数据\n");
		strBuffer.append(" \t\tswitch(checkvalue){\n");
		strBuffer.append(" \t\t\t\tcase -1:\n");
		strBuffer.append(" \t\t\t\t\tblnrun=true;\t\t\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tcase 0:\n");
		strBuffer.append(" \t\t\t\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append(" \t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append(" \t\t\t\t\t\tif (fcheck.checked==false)\n");
		strBuffer.append(" \t\t\t\t\t\t\tblnrun=true;\t\t\t\t\t\t\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tcase 1:\n");
		strBuffer.append(" \t\t\t\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append(" \t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append(" \t\t\t\t\t\tif (fcheck.checked  ==true)\n");
		strBuffer.append(" \t\t\t\t\t\t\tblnrun=true;\t\t\t\t\t\t\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tdefault: break;\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append(" \t\t//如果符合条件\n");
		strBuffer.append(" \t\tif(blnrun==true){\n");
		strBuffer.append(" \t\t\trowcount=rowcount+1;\n");
		strBuffer.append(" \t\t\tfor (j =0;j<colnumber.length;j++){\n");
		strBuffer.append(" \t\t\t\tif (colnumber[j]==null)\n");
		strBuffer.append(" \t\t\t\t\tvalueTemp=\"\";\n");
		strBuffer.append(" \t\t\t\telse{\n");
		strBuffer.append(" \t\t\t\t\tif(parseInt(colnumber[j])>=0){\n");
		strBuffer.append(" \t\t\t\t\t\tfrow=main.rows(i)\n");
		strBuffer
				.append(" \t\t\t\t\t\tcell=frow.cells(parseInt(colnumber[j]));\n");
		strBuffer.append(" \t\t\t\t\t}else{\n");
		strBuffer.append(" \t\t\t\t\t\tfrow=idcol.rows(i)\n");
		strBuffer
				.append(" \t\t\t\t\t\tcell=frow.cells(-1*parseInt(colnumber[j])-1);\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tvalueTemp=" + this.property
				+ "getcellvalue(cell);\n");
		strBuffer.append(" \t\t\t\t}\n");
		strBuffer.append("\t\t\t\tif(j==0){\n");
		strBuffer.append("\t\t\t\t\tData[Data.length]=valueTemp;\n");
		strBuffer.append("\t\t\t\t}else{\n");
		strBuffer
				.append("\t\t\t\t\tData[Data.length]=colsplitstring +valueTemp;\n");
		strBuffer.append("\t\t\t\t}");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append("\t\tif(i!=num-1 && blnrun==true){\n");
		strBuffer.append("\t\t\tData[Data.length]=rowsplitstring;\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append(" \t}\n");
		strBuffer.append(" \treturn Data.join(\"\")\n");
		strBuffer.append(" }\n");
	}

	/**
	 * 获取xml格式的详细信息
	 * 
	 * @param strBuffer
	 */
	public void generate_getDetailXml(StringBuffer strBuffer) {
		strBuffer.append("// 获取指定列xml格式的详细信息\n");
		strBuffer.append(" function " + this.property
				+ "getdetailxml(fieldsname){\n");
		strBuffer.append(" \treturn  " + this.property
				+ "getdetailxmlbycheck(fieldsname);\n");
		strBuffer.append(" }\n");
		strBuffer.append("// 获取选中行指定列xml格式的详细信息\n");
		strBuffer.append(" function " + this.property
				+ "getdetailxmlbycheck(fieldsname,state){\n");
		strBuffer.append(" \tvar col;\n");
		strBuffer.append(" \tvar\tcheckvalue;\n");
		strBuffer.append(" \tif (state==null) state=\"-1\";\n");
		strBuffer.append(" \tstate=state.toString();\n");
		strBuffer.append("// state=true/1表示选中的行\n");
		strBuffer
				.append(" \tif(state.toUpperCase()==\"TRUE\"||state==\"1\")\n");
		strBuffer.append(" \t\tcheckvalue=1;\n");
		strBuffer
				.append(" \telse if (state.toUpperCase()==\"FALSE\"||state==\"0\")\n");
		strBuffer.append(" \t\tcheckvalue=0;\n");
		strBuffer.append(" \telse\n");
		strBuffer.append(" \t\tcheckvalue=-1;\n");
		strBuffer.append(" \tfieldsname=fieldsname.toString();\n");
		strBuffer.append(" \tcol=fieldsname.split(\",\")\n");
		strBuffer.append(" \tvar main   = " + this.property
				+ "gettable(\"11\")\n");
		strBuffer.append(" \tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append(" \tvar colnumber =[col.length];\n");
		strBuffer.append(" \tvar i;\n");
		strBuffer.append(" \tvar colnumbertemp;\n");
		strBuffer.append(" \tvar returnXml;\n");
		strBuffer.append(" \tvar rowTemp\n");
		strBuffer.append(" \tvar rowcount =0;\n");
		strBuffer.append(" \tfor (i =0 ;i<col.length;i++){\n");
		strBuffer.append(" \t\tcolnumbertemp=" + this.property
				+ "getcolnumber(0,col[i]);\n");
		strBuffer.append(" \t\t//如果是固定列中读取时，采用负号表示\n");
		strBuffer.append(" \t\tif(colnumbertemp==null){\n");
		strBuffer.append(" \t\t\tcolnumbertemp=" + this.property
				+ "getcolnumber(1,col[i]);\n");
		strBuffer.append(" \t\t\tcolnumbertemp=parseFloat(colnumbertemp)\n");
		strBuffer.append(" \t\t\tif (isNaN(colnumbertemp)==false) {\n");
		strBuffer.append(" \t\t\t\tcolnumbertemp=-1*colnumbertemp-1;\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append(" \t\t}\n");
		strBuffer.append(" \t\tcolnumber[i]=colnumbertemp\n");
		strBuffer.append(" \t}\n");
		strBuffer.append(" \treturnXml=\"<details>\"\n");
		strBuffer.append(" \tvar j;\n");
		strBuffer.append(" \tvar blnrun;\n");
		strBuffer.append(" \tvar valueTemp;\n");
		strBuffer.append(" \tvar cell;\n");
		strBuffer.append(" \tvar frow;\n");
		strBuffer.append(" \tnum =main.rows.length;\n");
		strBuffer.append(" \tfor (i =0 ;i<num;i++){\n");
		strBuffer.append(" \t\tblnrun=false;\n");
		strBuffer.append(" \t\tfrow=main.rows(i);\n");
		strBuffer.append(" \t\t//检查是否符合条件的数据\n");
		strBuffer.append(" \t\tswitch(checkvalue){\n");
		strBuffer.append(" \t\t\t\tcase -1:\n");
		strBuffer.append(" \t\t\t\t\tblnrun=true;\t\t\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tcase 0:\n");
		strBuffer.append(" \t\t\t\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append(" \t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append(" \t\t\t\t\t\tif (fcheck.checked==false)\n");
		strBuffer.append(" \t\t\t\t\t\t\tblnrun=true;\t\t\t\t\t\t\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tcase 1:\n");
		strBuffer.append(" \t\t\t\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append(" \t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append(" \t\t\t\t\t\tif (fcheck.checked  ==true)\n");
		strBuffer.append(" \t\t\t\t\t\t\tblnrun=true;\t\t\t\t\t\t\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tbreak;\n");
		strBuffer.append(" \t\t\t\tdefault: break;\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append(" \t\t//如果符合条件\n");
		strBuffer.append(" \t\tif(blnrun==true){\n");
		strBuffer.append(" \t\t\trowcount=rowcount+1;\n");
		strBuffer.append(" \t\t\treturnXml=returnXml+\"<detail>\";\n");
		strBuffer.append(" \t\t\tfor (j =0;j<colnumber.length;j++){\n");
		strBuffer.append(" \t\t\t\tif (colnumber[j]==null)\n");
		strBuffer.append(" \t\t\t\t\tvalueTemp=\"\";\n");
		strBuffer.append(" \t\t\t\telse{\n");
		strBuffer.append(" \t\t\t\t\tif(parseInt(colnumber[j])>=0){\n");
		strBuffer.append(" \t\t\t\t\t\tfrow=main.rows(i)\n");
		strBuffer
				.append(" \t\t\t\t\t\tcell=frow.cells(parseInt(colnumber[j]));\n");
		strBuffer.append(" \t\t\t\t\t}else{\n");
		strBuffer.append(" \t\t\t\t\t\tfrow=idcol.rows(i)\n");
		strBuffer
				.append(" \t\t\t\t\t\tcell=frow.cells(-1*parseInt(colnumber[j])-1);\n");
		strBuffer.append(" \t\t\t\t\t}\n");
		strBuffer.append(" \t\t\t\t\tvalueTemp=" + this.property
				+ "getcellvalue(cell);\n");
		strBuffer.append(" \t\t\t\t}\n");
		strBuffer.append(" \t\t\t\treturnXml=returnXml+\"<\"+col[j]+\">\";\n");
		strBuffer
				.append(" \t\t\t\tvalueTemp=valueTemp.replace(/&/gi, \"&amp;\");\n");
		strBuffer
				.append(" \t\t\t\tvalueTemp=valueTemp.replace(/</gi, \"&lt;\");\n");
		strBuffer
				.append(" \t\t\t\tvalueTemp=valueTemp.replace(/>/gi, \"&gt;\");\n");
		strBuffer.append(" \t\t\t\treturnXml=returnXml+valueTemp;\n");
		strBuffer.append(" \t\t\t\treturnXml=returnXml+\"</\"+col[j]+\">\";\n");
		strBuffer.append(" \t\t\t}\n");
		strBuffer.append(" \t\t\treturnXml=returnXml+\"<detail>\"\n");
		strBuffer.append(" \t\t}\n");
		strBuffer.append(" \t}\n");
		strBuffer.append(" \tif (rowcount>0)\n");
		strBuffer.append(" \t\treturnXml=returnXml+\"</details>\"\n");
		strBuffer.append(" \telse\n");
		strBuffer.append(" \t\treturnXml=\"\";\n");
		strBuffer.append(" \treturn returnXml;\n");
		strBuffer.append(" }\n");
	}

	/**
	 * 设置选中行
	 * 
	 * @param strBuffer
	 */
	public void generate_setselectbyid(StringBuffer strBuffer) {
		strBuffer.append("// 设置选中行的信息\n");
		strBuffer.append("function " + this.property
				+ "setselectbyid(value){\n");
		strBuffer.append("\tif(value==null) value=\"\"\n");
		strBuffer.append("\tvar i ;\n");
		strBuffer.append("\tvar main   = " + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("\tvar num =main.rows.length;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fselect;\n");
		strBuffer.append("\tvar fselect\n");
		strBuffer.append("\tvar valueTemp=\"\";\n");
		strBuffer.append("\tvar  blnfind=false;\n");
		strBuffer.append("\tfor (i =0 ;i<num;i++){\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tfselect = frow.all[\"selectid\"];\n");
		strBuffer.append("\t\tif (fselect==\"[object]\"){\n");
		strBuffer.append("\t\t\tvaluetemp = " + this.property
				+ "getcellvalue(fselect)\n");
		strBuffer.append("\t\t\tif(valuetemp==value){\n");
		strBuffer.append("\t\t\t\t" + this.property + "selectchange(i);\n");
		strBuffer.append("\t\t\t\tvar mainscroll   = " + this.property
				+ "gettable(\"103\")\n");
		strBuffer.append("\t\t\t    mainscroll.scrollTop=frow.offsetTop\n");
		strBuffer.append("\t\t\t\tblnfind=true;");
		strBuffer.append("\t\t\t\tbreak;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("if(blnfind==false) " + this.property
				+ "selectchange('0');\n");
		strBuffer.append("}\n");
	}

	/**
	 * 删除行后重新排序序号列
	 * 
	 * @param strBuffer
	 */
	public void generate_redoserial(StringBuffer strBuffer, boolean serial) {
		strBuffer.append("// 如果存在删除时，重新排序序号\n");
		strBuffer.append("function " + this.property + "redoserial(){\n");
		if (serial == true) {
			strBuffer.append("\tvar idcol  = " + this.property
					+ "gettable(\"01\");\n");
			strBuffer.append("\tvar num =idcol.rows.length;\n");
			strBuffer.append("\tvar i\n");
			strBuffer.append("\tfor (i =0;i<num;i++){\n");
			strBuffer.append("\t\tidcol.rows(i).cells(0).innerHTML=i+1\n");
			strBuffer.append("\t}\n");
		}
		strBuffer.append("}\n");
	}

	/**
	 * 排序
	 * 
	 * @param strBuffer
	 */
	public void generate_dosort(StringBuffer strBuffer,
			boolean allowchangecolwidth, boolean serial) {
		strBuffer.append("// 排序数组\n");
		strBuffer.append("var ary" + this.property + "SortCont=null;\n");
		strBuffer.append("function " + this.property + "dosort(obj,type){\n");
		if (allowchangecolwidth)
			strBuffer.append("\tif(" + this.property + "drag_flag) return;");
		strBuffer.append("\tvar i=0;\n");
		strBuffer.append("\tvar main   =" + this.property
				+ "gettable(\"10\");\n");
		strBuffer.append("\tvar maincont = " + this.property
				+ "gettable(\"00\");\n");
		strBuffer.append("\tvar colname = obj.name;\n");
		strBuffer.append("\tvar colnumber =null;\n");
		strBuffer.append("\tvar cols =null;\n");
		strBuffer.append("\tvar col=null;\n");
		strBuffer.append("\tvar rownumber =null;\n");
		strBuffer.append("\tvar sorttype=0;\n");
		strBuffer.append("\t/*取排序列数值位置*/\n");
		strBuffer.append("\tcolnumber=" + this.property
				+ "getcolnumber(type,colname);\n");
		strBuffer.append("\t/*清除显示*/\n");
		strBuffer.append("\tvar strTitle;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar ffixrow;\n");
		strBuffer.append("\tfor(i=0;i<main.rows.length;i++){\n");
		strBuffer.append("        frow = main.rows[i];\n");
		strBuffer.append("\t\tffixrow=maincont.rows[i]\n");
		strBuffer.append("\t    for(var j=0;j<frow.cells.length;j++){\n");
		strBuffer.append("\t\tif(frow.cells[j].id!=obj.id){\n");
		strBuffer.append("\t\t strTitle = frow.cells[j].innerHTML;\n");
		strBuffer.append("\t\t strTitle = strTitle.replace(\"△\",\"\");\n");
		strBuffer.append("\t\t strTitle = strTitle.replace(\"▽\",\"\");\n");
		strBuffer.append("\t\t frow.cells[j].innerHTML=strTitle;}\n");
		strBuffer.append("\t\telse{\n");
		strBuffer.append("\t\tstrTitle = obj.innerHTML;\n");
		strBuffer.append("\t\tif(strTitle.indexOf(\"△\")>=0){\n");
		strBuffer.append("\t\t\tstrTitle = strTitle.replace(\"△\",\"▽\");\n");
		strBuffer.append("\t\t\tsorttype = 0;}\n");
		strBuffer.append("\t\telse if(strTitle.indexOf(\"▽\")>=0){\n");
		strBuffer.append("\t\t\tstrTitle = strTitle.replace(\"▽\",\"△\");\n");
		strBuffer.append("\t\t\tsorttype = 1;}\n");
		strBuffer.append("\t\telse{\n");
		strBuffer.append("\t\t\tstrTitle = strTitle + \"△\";\n");
		strBuffer.append("\t\t\tsorttype = 1;}\n");
		strBuffer.append("\t\tobj.innerHTML = strTitle;}}\n");
		strBuffer.append("\t\t for(var j=0;j<ffixrow.cells.length;j++){\n");
		strBuffer.append("\t\tif(ffixrow.cells[j].id!=obj.id){\n");
		strBuffer.append("\t\t strTitle = ffixrow.cells[j].innerHTML;\n");
		strBuffer.append("\t\t strTitle = strTitle.replace(\"△\",\"\");\n");
		strBuffer.append("\t\t strTitle = strTitle.replace(\"▽\",\"\");\n");
		strBuffer.append("\t\t ffixrow.cells[j].innerHTML=strTitle;}\n");
		strBuffer.append("\t\telse{\n");
		strBuffer.append("\t\tstrTitle = obj.innerHTML;\n");
		strBuffer.append("\t\tif(strTitle.indexOf(\"△\")>=0){\n");
		strBuffer.append("\t\t\tstrTitle = strTitle.replace(\"△\",\"▽\");\n");
		strBuffer.append("\t\t\tsorttype = 0;}\n");
		strBuffer.append("\t\telse if(strTitle.indexOf(\"▽\")>=0){\n");
		strBuffer.append("\t\t\tstrTitle = strTitle.replace(\"▽\",\"△\");\n");
		strBuffer.append("\t\t\tsorttype = 1;}\n");
		strBuffer.append("\t\telse{\n");
		strBuffer.append("\t\t\tstrTitle = strTitle + \"△\";\n");
		strBuffer.append("\t\t\tsorttype = 1;}\n");
		strBuffer.append("\t\tobj.innerHTML = strTitle;}}}\n");
		strBuffer.append("\t\t//if (ary" + this.property + "SortCont==null)\n");
		strBuffer.append("\t\t\t" + this.property
				+ "creatarray(type,colnumber);\n");
		strBuffer.append("\t\t" + this.property + "sort(sorttype);\n");
		strBuffer.append("\t\t" + this.property + "reload();\n");
		if (serial == true)
			strBuffer.append("\t\t" + this.property + "redoserial();\n");
		strBuffer.append("}\n");
		strBuffer.append("\n");
		strBuffer.append("//构建排序数值\t\t\n");
		strBuffer.append("function " + this.property
				+ "creatarray(type,colnumber){\t\t\n");
		strBuffer.append("\t\tif(ary" + this.property + "SortCont==null)  ary"
				+ this.property + "SortCont=new Array();\n");
		strBuffer
				.append("\t\tvar main=" + this.property + "gettable(\"11\")\n");
		strBuffer.append("\t\tvar fixmain=" + this.property
				+ "gettable(\"01\")\n");
		strBuffer.append("\t\tvar aryRow,aryCol;\n");
		strBuffer.append("\t\tvar num =main.rows.length;\n");
		strBuffer.append("\t\tvar frow,cell;\n");
		strBuffer.append("\t\tvar ffixrow;\n");
		strBuffer.append("\t\tvar key;\n");
		strBuffer.append("         var datatype;");
		strBuffer.append("  datatype=" + this.property
				+ "getdatatype(type,colnumber);");
		strBuffer.append("\t\tfor (var i =0;i<num;i++){\n");
		strBuffer.append("\t\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\t\tffixrow=fixmain.rows(i);\n");
		strBuffer.append("\t\t\tif(type==0) \n");
		strBuffer.append("\t\t\t\tcell=frow.cells(colnumber);\n");
		strBuffer.append("\t\t\telse\n");
		strBuffer.append("\t\t\t\tcell=ffixrow.cells(colnumber);\n");
		strBuffer.append("\t\t\tkey=" + this.property
				+ "getcellvalue(cell,datatype);\n");
		strBuffer.append("\t\t\taryRow=new Array();\n");
		strBuffer.append("\t\t\taryCol=new Array();\t\n");
		strBuffer.append("\t\t\taryfixCol=new Array();\t\n");
		strBuffer
				.append("\t\t\tfor(var j=0;j<frow.cells.length;j++){aryCol[j] = frow.cells[j].innerHTML;}\n");
		strBuffer
				.append("\t\t\tfor(var j=0;j<ffixrow.cells.length;j++){aryfixCol[j] = ffixrow.cells[j].innerHTML;}\n");
		strBuffer.append("\t\t\taryRow[0] = key;\n");
		strBuffer.append("\t\t\taryRow[1] = aryCol;\n");
		strBuffer.append("\t\t\taryRow[2] = aryfixCol;\n");
		strBuffer.append("\t\t\tary" + this.property
				+ "SortCont[i]=aryRow;}\t\n");
		strBuffer.append("}\n");
		strBuffer.append("//排序\t\t\n");
		strBuffer.append("function " + this.property
				+ "sort(sorttype){\t\t\t\n");
		strBuffer.append("\tvar item1,item2;\t\t\n");
		strBuffer.append("\tfor(var i=0;i<ary" + this.property
				+ "SortCont.length-1;i++){\t\n");
		strBuffer.append("\titem1 = ary" + this.property + "SortCont[i];\t\n");
		strBuffer.append("\tfor(var j=i+1;j<ary" + this.property
				+ "SortCont.length;j++){\t\n");
		strBuffer.append("\titem2 = ary" + this.property + "SortCont[j];\t\n");
		strBuffer.append("\tif(sorttype==0){//降序\t\n");
		strBuffer.append("\t\tif(item1[0]<item2[0]){\n");
		strBuffer
				.append("\t\t\tary" + this.property + "SortCont[i] = item2;\n");
		strBuffer
				.append("\t\t\tary" + this.property + "SortCont[j] = item1;\n");
		strBuffer.append("\t\t\titem1 = ary" + this.property
				+ "SortCont[i];}}\n");
		strBuffer.append("\telse{//升序\n");
		strBuffer.append("\t\tif(item1[0]>item2[0]){\n");
		strBuffer
				.append("\t\t\tary" + this.property + "SortCont[i] = item2;\n");
		strBuffer
				.append("\t\t\tary" + this.property + "SortCont[j] = item1;\n");
		strBuffer.append("\t\t\titem1 = ary" + this.property
				+ "SortCont[i];}}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\t\t\n");
		strBuffer.append("function " + this.property + "reload(){\t\t\n");
		strBuffer.append("\tvar main=" + this.property + "gettable(\"11\")\n");
		strBuffer.append("    var fixmain=" + this.property
				+ "gettable(\"01\")\n");
		strBuffer.append("\tvar num =main.rows.length;\t\n");
		strBuffer.append("\tvar frow;\t\n");
		strBuffer.append("\tvar ffixrow;\n");
		strBuffer.append("\tvar mainString=\"\";\n");
		strBuffer.append("\tvar fixString=\"\";\n");
		strBuffer.append("\tfor (var i =0;i<num;i++){\t\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tffixrow=fixmain.rows(i);\n");
		strBuffer.append("\t\tmainString=mainString+ary" + this.property
				+ "SortCont[i][1][0];\n");
		strBuffer.append("\t\tfixString=ary" + this.property
				+ "SortCont[i][2][0];\n");
		strBuffer
				.append("\t\tfor(var j=0;j<frow.cells.length;j++){frow.cells[j].innerHTML = ary"
						+ this.property + "SortCont[i][1][j];}\n");
		strBuffer
				.append("\t\tfor(var j=0;j<ffixrow.cells.length;j++){ffixrow.cells[j].innerHTML = ary"
						+ this.property + "SortCont[i][2][j];}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\n");
		strBuffer.append("\n");
	}

	/**
	 * 新增一行(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_addline(StringBuffer strBuffer, String ondblclick,
			boolean showtiptext, Vector<Object> columns, boolean serial) {
		strBuffer.append("// 新增一行\n");
		strBuffer.append("function " + property + "addline(type){");
		strBuffer.append("\tif (type==null) type=\"last\";");
		strBuffer.append("\tvar main   = " + property + "gettable(\"11\");");
		strBuffer.append("\tvar idcol  = " + property + "gettable(\"01\");");
		strBuffer.append("var addRowIndex=0;");
		strBuffer.append("if(type==\"last\") addRowIndex=main.rows.length;");
		strBuffer.append("if(type==\"after\") addRowIndex=" + property
				+ "reportrownumber+1;");
		strBuffer.append("if(type==\"before\") addRowIndex=" + property
				+ "reportrownumber;");
		strBuffer.append("\tif( main==\"[object]\"){\n");
		strBuffer.append("\t\tvar newRow ;\n");
		strBuffer.append("\t\tvar newcolhdead\n");
		strBuffer.append("\t\tvar cell0\n");
		strBuffer.append("\t\tnewRow=main.insertRow(addRowIndex);\n");
		strBuffer.append("\t\tnewRow.onclick=" + property + "clickselect;\n");
		if (ondblclick != null)
			strBuffer.append(" newRow.ondblclick =" + property
					+ "ondblclick;\n");
		if (showtiptext)
			strBuffer.append("\t\tnewRow.nmouseover=" + property
					+ "showPopupText;\n");
		int i = 0;
		for (int j = 0; j < columns.size(); j++) {
			BaseInputTag baseinputtag = (BaseInputTag) columns.get(j);
			if (baseinputtag.getfixcol())
				continue;
			strBuffer.append("var cell0=document.createElement('td')\n");
			strBuffer.append("cell0.innerHTML=\""
					+ baseinputtag.generateInnerHTML() + "\"\n");
			strBuffer.append("newRow.appendChild(cell0);\n");
			if (baseinputtag.getType() == "checkbox")
				strBuffer.append("cell0.align=\"center\"\n");
			if (baseinputtag.getId() != null)
				strBuffer.append("cell0.id=\"selectid\";\n");
			i++;
		}

		strBuffer.append("}");
		strBuffer.append("if(idcol==\"[object]\"){\n");
		// strBuffer.append("\t\t\t//\u6DFB\u52A0\u56FA\u5B9A\u5217\n");
		strBuffer.append("\t\t\t//添加固定列\n");
		strBuffer.append("\t\t\tnewRow=idcol.insertRow(addRowIndex);");
		strBuffer.append("\t\t\tnewRow.onclick=" + property + "clickselect;");
		if (showtiptext)
			strBuffer.append("\t\tnewRow.nmouseover=" + property
					+ "showPopupText;");
		i = 0;
		if (i == 0 && serial) {
			strBuffer.append("var cell0=document.createElement('td');");
			strBuffer.append("newRow.appendChild(cell0);");
			i = 1;
		}
		for (int k = 0; k < columns.size(); k++) {
			BaseInputTag baseinputtag1 = (BaseInputTag) columns.get(k);
			if (baseinputtag1.getfixcol()) {
				strBuffer.append("var cellall=document.createElement('td');");
				strBuffer.append("cellall.innerHTML=\""
						+ baseinputtag1.generateInnerHTML() + "\";");
				strBuffer.append("newRow.appendChild(cellall);");
				i++;
			}
		}

		if (serial)
			strBuffer.append(property + "redoserial();");
		strBuffer.append("\t\t\tmain.scrollTop= main.scrollHeight +20;");
		strBuffer.append("if(type==\"before\") " + property
				+ "selectchange(addRowIndex+1);");
		strBuffer.append("\t\t\t" + property + "selectchange(addRowIndex);");
		strBuffer.append("\t\t} ");
		strBuffer.append("\t\t} \n");
	}

	/**
	 * 删除一行(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_delLine(StringBuffer strBuffer) {
		strBuffer.append("// 删除一行\n");
		strBuffer.append("function " + this.property + "delline(){\n");
		strBuffer.append("\tvar main   = " + this.property
				+ "gettable(\"11\")\n");
		strBuffer.append("\tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tif( main==\"[object]\" && idcol==\"[object]\"){\n");
		strBuffer.append("\t\tvar index \n");
		strBuffer.append("\t\tindex=main.rows.length;\n");
		strBuffer.append("\t\tif (" + this.property
				+ "reportrownumber<=index &&" + this.property
				+ "reportrownumber>=0&& " + this.property
				+ "reportrownumber!=null){\n");
		strBuffer.append("\t\t\tmain.deleteRow(" + this.property
				+ "reportrownumber);\n");
		strBuffer.append("\t\t\tidcol.deleteRow(" + this.property
				+ "reportrownumber);\n");
		strBuffer.append("\t\t\t" + this.property + "redoserial();\n");
		strBuffer.append("\t\t\tif(" + this.property
				+ "reportrownumber==0&&main.rows.length>=1)\n");
		strBuffer.append("\t\t\t\t" + this.property + "selectchange(0);\n");
		strBuffer.append("\t\t\telse{\n");
		strBuffer.append("\t\t\t\tif(" + this.property
				+ "reportrownumber-1>=0)\n");
		strBuffer.append("\t\t\t\t\t" + this.property + "selectchange("
				+ this.property + "reportrownumber-1);\n");
		strBuffer.append("\t\t\t\telse\n");
		strBuffer
				.append("\t\t\t\t" + this.property + "reportrownumber=null;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\t} \n");
		strBuffer.append("}\n");
	}

	/**
	 * 删除所有行(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_delallline(StringBuffer strBuffer) {
		strBuffer.append("// 删除所有行\n");
		strBuffer.append("\tfunction " + this.property + "delallline(){\n");
		strBuffer.append("\t\tvar main   = " + this.property
				+ "gettable(\"11\")\n");
		strBuffer.append("\t\tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\t\tvar i \n");
		strBuffer.append("\t\tvar num =main.rows.length\n");
		strBuffer.append("\t\tfor (i =0 ;i<num;i++){\n");
		strBuffer.append("\t\t\tmain.deleteRow(0);\n");
		strBuffer.append("\t\t\tidcol.deleteRow(0);\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
	}

	/**
	 * 删除选中的行(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_dellinebycheck(StringBuffer strBuffer) {
		strBuffer.append("// 删除选中的行\n");
		strBuffer.append("function " + this.property
				+ "dellinebycheck(blncheck){\n");
		strBuffer.append("\t//如果为空时\n");
		strBuffer.append("\tvar checkvalue;\n");
		strBuffer.append("\tif (blncheck==null) blncheck =\"1\";\n");
		strBuffer.append("\t//判断传入参数\n");
		strBuffer
				.append("\tif(blncheck.toUpperCase()==\"TRUE\"||blncheck==\"1\")\n");
		strBuffer.append("\t\tcheckvalue=true;\n");
		strBuffer.append("\telse\n");
		strBuffer.append("\t\tcheckvalue=false;\n");
		strBuffer.append("\t//删除数据\n");
		strBuffer.append("\tvar i ;\n");
		strBuffer.append("\tvar main   = " + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("\tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tvar num =main.rows.length;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fcheck;\n");
		strBuffer.append("\tvar blndelete=false; \n");
		strBuffer.append("\tfor (i =num-1 ;i>=0;i--){\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tfcheck = frow.all[\"syscheck\"]\n");
		strBuffer.append("\t\tif (fcheck!=null){\n");
		strBuffer.append("\t\t\tif (fcheck.checked==checkvalue){\n");
		strBuffer.append("\t\t\t\tmain.deleteRow(i);\n");
		strBuffer.append("\t\t\t\tidcol.deleteRow(i);\n");
		strBuffer.append("\t\t\t\t blndelete=true;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\tif (blndelete ==true) " + this.property
				+ "redoserial()\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取选中行的ID(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_getselectidbycheck(StringBuffer strBuffer) {
		strBuffer.append("// 获取选中行的ID\n");
		strBuffer.append("function " + this.property
				+ "getselectidbycheck(blncheck){\n");
		strBuffer.append("\t//如果为空时\n");
		strBuffer.append("\tvar checkvalue;\n");
		strBuffer.append("\tif (blncheck==null) blncheck =\"1\";\n");
		strBuffer.append("\t//判断传入参数\n");
		strBuffer
				.append("\tif(blncheck.toUpperCase()==\"TRUE\"||blncheck==\"1\")\n");
		strBuffer.append("\t\tcheckvalue=true;\n");
		strBuffer.append("\telse\n");
		strBuffer.append("\t\tcheckvalue=false;\n");
		strBuffer.append("\t//删除数据\n");
		strBuffer.append("\tvar i ;\n");
		strBuffer.append("\tvar main   = " + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("\tvar idcol  = " + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tvar num =main.rows.length;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fcheck;\n");
		strBuffer.append("\tvar fselect;\n");
		strBuffer.append("\tvar returnvalue=\"\"\n");
		strBuffer.append("\tfor (i =num-1 ;i>=0;i--){\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append("\t\tif (fcheck!=null){\n");
		strBuffer.append("\t\t\tif (fcheck.checked==checkvalue){\n");
		strBuffer.append("\t\t\t\tfselect =frow.all[\"selectid\"];\n");
		strBuffer.append("\t\t\t\tif(fselect!=null)\n");
		strBuffer.append("\t\t\t\t\tif (fselect.innerHTML!=\"\"){\n");
		strBuffer.append("\t\t\t\t\t\tif (returnvalue==\"\")\n");
		strBuffer.append("\t\t\t\t\t\t\treturnvalue=fselect.innerHTML;\n");
		strBuffer.append("\t\t\t\t\t\telse\n");
		strBuffer
				.append("\t\t\t\t\t\t\treturnvalue=returnvalue +\",\" +fselect.innerHTML;\n");
		strBuffer.append("\t\t\t\t\t}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("return returnvalue;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 设置某行为选中状态(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_setcheckbyid(StringBuffer strBuffer) {
		strBuffer.append("// 设置某行为选中状态\n");
		strBuffer
				.append("function " + this.property + "setcheckbyid(value){\n");
		strBuffer.append("\tif(value==null) return;\n");
		strBuffer.append("\tvar i ;\n");
		strBuffer.append("\tvar j;\n");
		strBuffer.append("\tvar main  = " + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("\tvar num =main.rows.length;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fselect\n");
		strBuffer.append("\tvar valueTemp=\"\";\n");
		strBuffer.append("\tvar fcheck\n");
		strBuffer.append("\tvar col=value.toString().split(\",\");\n");
		strBuffer.append("\tfor (i =0 ;i<num;i++){\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tfselect = frow.all[\"selectid\"];\n");
		strBuffer.append("\t\tif (fselect==\"[object]\"){\n");
		strBuffer.append("\t\t\tvaluetemp = " + this.property
				+ "getcellvalue(fselect)\n");
		strBuffer.append("\t\t\tfor(j=0;j<col.length;j++){\n");
		strBuffer.append("\t\t\t\tif(valuetemp==col[j]){\n");
		strBuffer.append("\t\t\t\t\tfcheck=frow.all[\"syscheck\"];\n");
		strBuffer.append("\t\t\t\t\tfcheck.checked=true;\n");
		strBuffer.append("\t\t\t\t\tfcheck.value='1';\n");
		strBuffer.append("\t\t\t\t\tbreak;\n");
		strBuffer.append("\t\t\t\t}\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\n");
	}

	/**
	 * 全部选中或取消(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_setallcheckstate(StringBuffer strBuffer) {
		// TODO 右键菜单选择项
		strBuffer.append("// 如果存在选择框时，用户可以1.全部选中，0.全部取消，2.反向选择\n");
		strBuffer.append("function " + this.property
				+ "setallcheckstate(type){\n");
		strBuffer.append("\tvar value\n");
		strBuffer.append("\tvar main=" + this.property + "gettable(\"11\");\n");
		strBuffer.append("\tvar num =main.rows.length;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fcheck;\n");
		strBuffer.append("\tvar fselect;\n");
		strBuffer.append("\tvar i\n");
		strBuffer.append("\tfor (i =num-1 ;i>=0;i--){\n");
		strBuffer.append("\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\tfcheck = frow.all[\"syscheck\"];\n");
		strBuffer.append("\t\tif (fcheck!=null){\n");
		strBuffer.append("\t\t\tswitch(type){\n");
		strBuffer.append("\t\t\t\tcase  '1': fcheck.checked =true; break;\n");
		strBuffer.append("\t\t\t\tcase  '0': fcheck.checked =false; break;\n");
		strBuffer
				.append("\t\t\t\tcase  '2': fcheck.checked = fcheck.checked?false:true;break;\n");
		strBuffer.append("\t\t\t\t//调用gridreport.js文件中的方法\n");
		strBuffer.append("\t\t\t\tcase '-1': fnDetermine();\n");
		strBuffer.append("\t\t\t\tdefault :break;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\n");
		strBuffer.append("\t}\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取选中行指定列的合计值(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_getsumbycheck(StringBuffer strBuffer) {
		strBuffer.append("// 获取选中行指定列的合计值\n");
		strBuffer.append("function " + this.property
				+ "getsumbycheck(colname,state){\n");
		strBuffer.append("\tvar checkvalue=0;\n");
		strBuffer.append("\tstate=state.toString();\n");
		strBuffer.append("\tif (state==null) state =\"0\";\n");
		strBuffer.append("\t//判断传入参数\n");
		strBuffer.append("\tif(state.toUpperCase()==\"TRUE\"||state==\"1\")\n");
		strBuffer.append("\t\tcheckvalue=1;\n");
		strBuffer.append("\telse\n");
		strBuffer.append("\t\tcheckvalue=0;\n");
		strBuffer.append("\tvar colnumber= null;\n");
		strBuffer.append("\tvar returnvalue =0;\n");
		strBuffer.append("\tcolnumber=" + this.property
				+ "getcolnumber(0,colname);\n");
		strBuffer.append("\tif (colnumber !=null)\n");
		strBuffer.append("\t\treturnvalue=" + this.property
				+ "getsumcallprc(0,checkvalue,colnumber);\n");
		strBuffer.append("\tif(returnvalue==0){\n");
		strBuffer.append("\t\tcolnumber=" + this.property
				+ "getcolnumber(1,colname);\n");
		strBuffer.append("\t\tif (colnumber !=null)\n");
		strBuffer.append("\t\t\treturnvalue=" + this.property
				+ "getsumcallprc(1,checkvalue,colnumber);\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\treturn returnvalue;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取指定列的合计值(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_getsum(StringBuffer strBuffer) {
		strBuffer.append("// 读取某列的合计数，如果该列不能没有数据行时，返回为0\n");
		strBuffer.append("function " + this.property + "getsum(colname){\n");
		strBuffer.append("\tvar colnumber= null;\n");
		strBuffer.append("\tvar returnvalue =0;\n");
		strBuffer.append("\tcolnumber=" + this.property
				+ "getcolnumber(0,colname);\n");
		strBuffer.append("\tif (colnumber !=null)\n");
		strBuffer.append("\t\treturnvalue=" + this.property
				+ "getsumcallprc(0,-1,colnumber);\n");
		strBuffer.append("\tif(returnvalue==0){\n");
		strBuffer.append("\t\tcolnumber=" + this.property
				+ "getcolnumber(1,colname);\n");
		strBuffer.append("\t\tif (colnumber !=null)\n");
		strBuffer.append("\t\t\treturnvalue=" + this.property
				+ "getsumcallprc(1,-1,colnumber);\n");
		strBuffer.append("\t}\n");
		strBuffer.append("\treturn returnvalue;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 获取选定行指定列的合计值(funtype>=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_getsumcallprc(StringBuffer strBuffer) {
		strBuffer.append("function " + this.property
				+ "getsumcallprc(type,check,colnumber){\n");
		strBuffer.append("\t//type为0表示内容中的字段，如果为1时表示固定列的年内容；\n");
		strBuffer.append("\t//如果check为－1时全部，1时表示选中，0时表示不选中\n");
		strBuffer.append("\tvar main\n");
		strBuffer.append("\tvar returnvalue=0.00;\n");
		strBuffer.append("\tvar num ;\n");
		strBuffer.append("\tvar frow;\n");
		strBuffer.append("\tvar fcheck;\n");
		strBuffer.append("\tvar cell;\n");
		strBuffer.append("\tvar valuetemp;\n");
		strBuffer.append("\tvar checkmain=" + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("\tif(type ==0) main=" + this.property
				+ "gettable(\"11\"); else main =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("\tif (main==\"[object]\"){\n");
		strBuffer.append("\t\tnum =main.rows.length;\n");
		strBuffer.append("\t\tfor (i =0 ;i<num;i++){\n");
		strBuffer
				.append("\t\t\tfcheck = checkmain.rows(i).all[\"syscheck\"];\n");
		strBuffer.append("\t\t\tfrow=main.rows(i);\n");
		strBuffer.append("\t\t\tcell=frow.cells(parseInt(colnumber));\n");
		strBuffer.append("\t\t\tvaluetemp=parseFloat(" + this.property
				+ "getcellvalue(cell));\n");
		strBuffer.append("\t\t\tif(isNaN(valuetemp)) valuetemp=0;\n");
		strBuffer.append("\t\t\t\n");
		strBuffer.append("\t\t\tswitch(check){\n");
		strBuffer.append("\t\t\t\tcase -1:\n");
		strBuffer.append("\t\t\t\t\treturnvalue =returnvalue+valuetemp;\n");
		strBuffer.append("\t\t\t\t\tbreak;\n");
		strBuffer.append("\t\t\t\tcase 0:\n");
		strBuffer.append("\t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append("\t\t\t\t\t\tif (fcheck.checked==false)\n");
		strBuffer.append("\t\t\t\t\t\t\treturnvalue =returnvalue+valuetemp;\n");
		strBuffer.append("\t\t\t\t\t}\n");
		strBuffer.append("\t\t\t\t\tbreak;\n");
		strBuffer.append("\t\t\t\tcase 1:\n");
		strBuffer.append("\t\t\t\t\tif (fcheck!=null){\n");
		strBuffer.append("\t\t\t\t\t\tif (fcheck.checked  ==true)\n");
		strBuffer
				.append("\t\t\t\t\t\t\treturnvalue =returnvalue+valuetemp;\t\t\t\t\t\t\t\n");
		strBuffer.append("\t\t\t\t\t}\n");
		strBuffer.append("\t\t\t\t\tbreak;\n");
		strBuffer.append("\t\t\t\tdefault: break;\n");
		strBuffer.append("\t\t\t}\n");
		strBuffer.append("\t\t}\t\n");
		strBuffer.append("\t}\n");
		strBuffer.append("return returnvalue;\n");
		strBuffer.append("}\n");
	}

	/**
	 * 合并行(merge=true||mergetype=1)
	 * 
	 * @param strBuffer
	 */
	public void generate_expand(StringBuffer strBuffer, String gridExpandImage,
			String gridShrinkImage) {
		strBuffer.append("function " + this.property
				+ "expand(rowindex,rownumber){\n");
		strBuffer.append("var tabletemp =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("var tablecontenttemp =" + this.property
				+ "gettable(\"11\");\n");
		strBuffer.append("var trtemp;\n");
		strBuffer.append("var i;\n");
		strBuffer.append("var expandtemp;\n");
		strBuffer.append("var intcontrol;\n");
		strBuffer.append("var objimg;\n");
		strBuffer.append("var cel;\n");
		strBuffer.append("var parent;\n");
		strBuffer.append("trtemp=tabletemp.rows[rowindex];\n");
		strBuffer.append("objimg=trtemp.all[\"img\"];\n");
		strBuffer.append("expandtemp=objimg.exp;\n");
		strBuffer.append("cel =objimg.cel;\n");
		strBuffer.append("parent=objimg.parent;\n");
		strBuffer.append("if(expandtemp==\"1\"){\n");
		strBuffer.append("objimg.exp=\"0\"\n");
		strBuffer.append("objimg.src=\"" + gridExpandImage + "\"\n");
		strBuffer.append("}else{\n");
		strBuffer.append("objimg.exp=\"1\"\n");
		strBuffer.append("objimg.src=\"" + gridShrinkImage + "\"\n");
		strBuffer.append("}\n");
		strBuffer.append("intcontrol=0;\n");
		strBuffer.append("for(i=rowindex+1;i<=rowindex+rownumber;i++){\n");
		strBuffer.append("trtemp=tabletemp.rows[i];\n");
		strBuffer.append("if(expandtemp==\"1\"){\n");
		strBuffer.append("trtemp.style.display=\"none\";\n");
		strBuffer.append("tablecontenttemp.rows[i].style.display=\"none\";\n");
		strBuffer.append("}else{\n");
		strBuffer.append("if(intcontrol==0){\n");
		strBuffer.append("if(trtemp.all[\"img\"]){\n");
		strBuffer.append("if(trtemp.all[\"img\"].exp==\"0\"){\n");
		strBuffer.append("intcontrol=trtemp.all[\"img\"].child;\n");
		strBuffer.append("}\n");
		strBuffer.append("trtemp.style.display=\"\";\n");
		strBuffer.append("tablecontenttemp.rows[i].style.display=\"\";\n");
		strBuffer.append("}else{\n");
		strBuffer.append("trtemp.style.display=\"\";\n");
		strBuffer.append("tablecontenttemp.rows[i].style.display=\"\";\n");
		strBuffer.append("}\n");
		strBuffer.append("}else{\n");
		strBuffer.append("intcontrol=intcontrol-1;\n");
		strBuffer.append("}\n");
		strBuffer.append("}\n");
		strBuffer.append("}\n");
		strBuffer
				.append(this.property
						+ "contralp(rowindex,rownumber,expandtemp,cel,parent,rownumber);\n");
		strBuffer.append("}\n");
		strBuffer.append("\n");
		strBuffer.append("function " + this.property
				+ "contralp(rowindex,rownumber,exp,cel,parent,aa){\n");
		strBuffer.append("if(cel==0||parent==\"\") return ;\n");
		strBuffer.append("var tabletemp =" + this.property
				+ "gettable(\"01\");\n");
		strBuffer.append("var current_tr=tabletemp.all[\"tr\"+rowindex];\n");
		strBuffer.append("var current_img=current_tr.all[\"img\"]\n");
		strBuffer
				.append("var parent_img=tabletemp.all[\"tr\"+parent].all[\"img\"];\n");
		strBuffer.append("var parent_child=parent_img.child;\n");
		strBuffer
				.append("var trparent =tabletemp.all[\"tr\"+(parseInt(parent)+1)];\n");
		strBuffer.append("var current_next_tr;\n");
		strBuffer.append("var current_next_cel;\n");
		strBuffer
				.append("if(parseInt(rowindex)+parseInt(current_img.child)+1>=tabletemp.rows.length){\n");
		strBuffer.append("current_next_cel=0;\n");
		strBuffer.append("}else{\n");
		strBuffer
				.append("current_next_tr=tabletemp.all[\"tr\"+(parseInt(rowindex)+parseInt(current_img.child)+1)]\n");
		strBuffer
				.append("current_next_cel =current_next_tr.all[\"img\"].cel;\n");
		strBuffer.append("}\n");
		strBuffer.append("\n");
		strBuffer.append("if(cel>current_next_cel){\n");
		strBuffer.append("var i;\n");
		strBuffer.append("var blnexp =false;\n");
		strBuffer.append("if(exp==\"0\"){\n");
		strBuffer
				.append("for(i=parseInt(rowindex)+parseInt(rownumber);i>=parseInt(rowindex)+1;i--){\n");
		strBuffer.append("if(tabletemp.all[\"tr\"+i].all[\"img\"]){\n");
		strBuffer
				.append("if(tabletemp.all[\"tr\"+i].all[\"img\"].exp==\"0\"){\n");
		strBuffer.append("blnexp=true;\n");
		strBuffer.append("}\n");
		strBuffer.append("break;\n");
		strBuffer.append("}\n");
		strBuffer.append("}\n");
		strBuffer.append("if(blnexp==true){\n");
		strBuffer
				.append("trparent.cells[0].rowSpan=parseInt(current_img.child)-tabletemp.all[\"tr\"+i].all[\"img\"].child;\n");
		strBuffer.append("}else{\n");
		strBuffer
				.append("trparent.cells[0].rowSpan=parseInt(current_img.child);\n");
		strBuffer.append("}\n");
		strBuffer.append("}else{\n");
		strBuffer.append("if (parseInt(current_img.child)-aa==0)\n");
		strBuffer.append("trparent.cells[0].rowSpan=1;\n");
		strBuffer.append("else\n");
		strBuffer
				.append("trparent.cells[0].rowSpan=parseInt(current_img.child)-aa;\n");
		strBuffer.append("}\n");
		strBuffer
				.append(this.property
						+ "contralp(parent,parent_img.child,exp,current_img.cel,current_img.parent,aa)\n");
		strBuffer.append("\n");
		strBuffer.append("}\n");
		strBuffer.append("}\n");
	}

	/**
	 * 允许改变列大小(allowchangecolwidth=true)
	 * 
	 * @param strBuffer
	 */
	public void generate_allowchangecolsize(StringBuffer strBuffer) {
		strBuffer
				.append("\nvar "
						+ this.property
						+ "alphaDiv=\"<div id='"
						+ this.property
						+ "alphaDiv' "
						+ "onmouseup='"
						+ this.property
						+ "mouseDragEnd()' "
						+ " style='position:absolute;display:none;BORDER-RIGHT: #ccc 1px solid; BACKGROUND: #eee; OVERFLOW: hidden;"
						+ "BORDER-LEFT: #ccc 1px solid; WIDTH: 4px; CURSOR: e-resize;'>&nbsp;</div>\"\n");
		strBuffer.append("var " + this.property + "begin_x;");
		strBuffer.append("var " + this.property + "drag_flag = false;");
		strBuffer.append("var " + this.property + "objalphaDiv;");
		strBuffer.append("var " + this.property + "cellIndex;");
		strBuffer.append("var " + this.property + "sourceLeft;\n");
		strBuffer.append("function " + this.property + "onmousemove(){");
		strBuffer.append("var now_x=event.offsetX;");
		strBuffer
				.append("if ((event.srcElement.offsetLeft>0 && 3>=now_x) || event.srcElement.offsetWidth-3<=now_x){");
		strBuffer
				.append("event.srcElement.parentElement.style.cursor=\"e-resize\"");
		strBuffer.append("}else{");
		strBuffer
				.append("event.srcElement.parentElement.style.cursor=\"default\"");
		strBuffer.append("}");
		strBuffer.append("}\n");
		strBuffer.append("function " + this.property + "setDrag(){");
		strBuffer
				.append("if(event.srcElement.parentElement.style.cursor==\"e-resize\"){");
		strBuffer.append(this.property + "drag_flag=true;");
		strBuffer.append("now_x=event.offsetX;");
		strBuffer.append(this.property + "begin_x=event.x;");
		strBuffer.append("if (" + this.property + "objalphaDiv==null){");
		strBuffer.append(this.property + "objalphaDiv=document.createElement("
				+ this.property + "alphaDiv);");
		strBuffer.append("}");
		strBuffer.append(this.property
				+ "gettable(\"000\").insertAdjacentElement(\"BeforeBegin\","
				+ this.property + "objalphaDiv);");
		strBuffer.append(this.property + "objalphaDiv.style.height="
				+ this.property + "gettable(\"000\").offsetHeight;");
		strBuffer.append("if (3>=now_x){");
		strBuffer
				.append(this.property
						+ "objalphaDiv.style.left=document.body.scrollLeft+(event.x-now_x-4)+\"px\";");
		strBuffer.append(this.property
				+ "cellIndex=event.srcElement.cellIndex-1;");
		strBuffer.append("}else{");
		strBuffer
				.append(this.property
						+ "objalphaDiv.style.left=document.body.scrollLeft+(event.x-now_x-4+event.srcElement.offsetWidth)+\"px\";");
		strBuffer.append(this.property
				+ "cellIndex=event.srcElement.cellIndex;");
		strBuffer.append("}");
		strBuffer.append(this.property + "objalphaDiv.style.display=\"\";");
		strBuffer.append(this.property + "sourceLeft=" + this.property
				+ "objalphaDiv.style.left;");
		strBuffer.append("}");
		strBuffer.append("}\n");
		strBuffer.append("function " + this.property + "mouseDragEnd(){");
		strBuffer.append("if(" + this.property + "drag_flag==true){");
		strBuffer.append(this.property + "objalphaDiv.style.display=\"none\";");
		strBuffer.append(this.property
				+ "gettable(\"000\").style.cursor=\"default\";");
		strBuffer.append("var index=" + this.property
				+ "gettable(\"10\").all[\"colgroup\"].all[\"col\"]["
				+ this.property + "cellIndex].contentindex;");
		strBuffer
				.append(" changewidth=parseInt("
						+ this.property
						+ "gettable(\"11\").all[\"colgroup\"].all[\"col\"][index].style.width.replace(\"px\",\"\"))-"
						+ "(parseInt(" + this.property
						+ "sourceLeft.replace(\"px\",\"\"))-parseInt("
						+ this.property
						+ "objalphaDiv.style.left.replace(\"px\",\"\")));");
		strBuffer.append("if (changewidth<=10) changewidth=10;");
		strBuffer
				.append(this.property
						+ "gettable(\"11\").all[\"colgroup\"].all[\"col\"][index].style.width=changewidth;");
		strBuffer.append(this.property
				+ "gettable(\"10\").all[\"colgroup\"].all[\"col\"]["
				+ this.property + "cellIndex].style.width=changewidth;");
		strBuffer.append("}");
		strBuffer.append(this.property + "drag_flag=false;");
		strBuffer.append("}\n");
		strBuffer.append("function " + this.property + "mouseDrag(){");
		strBuffer.append("if(" + this.property + "drag_flag==true){");
		strBuffer.append("if (window.event.button==1){");
		strBuffer.append("var now_x=event.x;");
		strBuffer.append("var value=parseInt(" + this.property
				+ "objalphaDiv.style.left.replace(\"px\",\"\"))+now_x-"
				+ this.property + "begin_x;");
		strBuffer
				.append(this.property + "objalphaDiv.style.left=value+\"px\";");
		strBuffer.append(this.property + "begin_x=now_x;");
		strBuffer.append("}");
		strBuffer.append(this.property
				+ "gettable(\"000\").style.cursor=\"e-resize\";");
		strBuffer.append("}else{");
		strBuffer.append("try{");
		strBuffer.append(this.property + "objalphaDiv.style.display=\"none\";");
		strBuffer.append(this.property
				+ "gettable(\"000\").style.cursor=\"default\";");
		strBuffer.append("}catch(e){}");
		strBuffer.append("}");
		strBuffer.append("}");
	}

}
