package com.zl.base.core.taglib.datagridreport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import com.zl.util.MethodFactory;

public class ExportExcel {
	private String title;
	private String condition;
	private int pageCol = 0;
	private int pageRow = 0;
	private boolean ShowPageBreak = false;
	private ArrayList cellstyle_list = new ArrayList();
	private int titleHeight = 30;
	private int rowHeight = 15;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String paramString) {
		this.condition = paramString;
	}

	public int getPageCol() {
		return this.pageCol;
	}

	public void setPageCol(int paramInt) {
		this.pageCol = paramInt;
	}

	public int getPageRow() {
		return this.pageRow;
	}

	public void setPageRow(int paramInt) {
		this.pageRow = paramInt;
	}

	public boolean getShowPageBreak() {
		return this.ShowPageBreak;
	}

	public void setShowPageBreak(boolean paramBoolean) {
		this.ShowPageBreak = paramBoolean;
	}

	public void addcellstyle(CellStyle paramcellstyle) throws Exception {
		if (paramcellstyle.name == null)
			throw new Exception("cellstyle 鍚嶇О娌℃湁杈撳叆");
		this.cellstyle_list.add(paramcellstyle);
	}

	public int getTitleHeight() {
		return this.titleHeight;
	}

	public void seTitleHeight(int paramInt) {
		this.titleHeight = paramInt;
	}

	public int getRowHeight() {
		return this.rowHeight;
	}

	public void seRowHeight(int paramInt) {
		this.rowHeight = paramInt;
	}

	private String getfilename() {
		String str1 = "";
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		String str2 = String.valueOf(localGregorianCalendar.get(1));
		String str3 = "00" + String.valueOf(localGregorianCalendar.get(2) + 1);
		str3 = str3.substring(str3.length() - 2, str3.length());
		String str4 = "00" + String.valueOf(localGregorianCalendar.get(5));
		str4 = str4.substring(str4.length() - 2, str4.length());
		String str5 = "00" + String.valueOf(localGregorianCalendar.get(11));
		str5 = str5.substring(str5.length() - 2, str5.length());
		String str6 = "00" + String.valueOf(localGregorianCalendar.get(12));
		str6 = str6.substring(str6.length() - 2, str6.length());
		String str7 = "00" + String.valueOf(localGregorianCalendar.get(13));
		str7 = str7.substring(str7.length() - 2, str7.length());
		String str8 = "00" + String.valueOf(localGregorianCalendar.get(14));
		str8 = str8.substring(str8.length() - 2, str8.length());
		str1 = str2 + str3 + str4 + str5 + str6 + str7 + str8;
		return str1;
	}

	private ArrayList createcaption(ArrayList paramArrayList) {
		int i = 0;
		int j = 0;
		int k = 1;
		ArrayList localArrayList = new ArrayList();
		for (int m = 0; m < paramArrayList.size(); m++) {
			Object localObject = paramArrayList.get(m);
			String str1 = null;
			try {
				str1 = MethodFactory.getThisString(PropertyUtils.getProperty(
						localObject, "caption"));
				str1 = getStringValue(str1, 0);
			} catch (Exception localException1) {
				str1 = "";
			}
			String str2 = "";
			try {
				str2 = MethodFactory.getThisString(PropertyUtils.getProperty(
						localObject, "property"));
				str2 = getStringValue(str2, 0);
			} catch (Exception localException2) {
				str2 = "";
			}
			String str3 = null;
			try {
				str3 = MethodFactory.getThisString(PropertyUtils.getProperty(
						localObject, "width"));
				str3 = getStringValue(str3, 0);
			} catch (Exception localException3) {
				str3 = "";
			}
			String str4 = null;
			try {
				str4 = MethodFactory.getThisString(PropertyUtils.getProperty(
						localObject, "fixcol"));
				str4 = getStringValue(str4, 1);
			} catch (Exception localException4) {
				str4 = "";
			}
			String str5 = null;
			try {
				str5 = MethodFactory.getThisString(PropertyUtils.getProperty(
						localObject, "datatype"));
				str5 = getStringValue(str5, 1);
			} catch (Exception localException5) {
				str5 = "0";
			}
			if ((str1 != null) && (!str1.equals("")) && (str3 != null)
					&& (!str3.equals("0"))) {
				i += 1;
				String[] arrayOfString = MethodFactory.split(str1, "##");
				if (arrayOfString.length > j)
					j = arrayOfString.length;
				ColStruct localColStruct = new ColStruct();
				for (int n = 0; n < arrayOfString.length; n++)
					localColStruct.caption.add(arrayOfString[n]);
				localColStruct.property = str2;
				localColStruct.width = str3;
				if ((str4 != null)
						&& (k == 1)
						&& ((str4.trim().toLowerCase().equals("true")) || (str4
								.trim().equals("1")))) {
					localColStruct.fixcol = true;
				} else {
					localColStruct.fixcol = false;
					k = 0;
				}
				if (str5 != null) {
					if (str5.equals("1"))
						localColStruct.type = 1;
					else
						localColStruct.type = 0;
				} else
					str5 = "0";
				localArrayList.add(localColStruct);
			}
		}
		return localArrayList;
	}

	private void writeReporthead(OutputStream outputstream, ArrayList arraylist) {
		boolean flag = false;
		Object obj = null;
		Object obj1 = null;
		int j = arraylist.size();
		int k = 0;
		for (int l = 0; l < arraylist.size(); l++) {
			ColStruct ColStruct1 = (ColStruct) arraylist.get(l);
			if (ColStruct1.caption.size() > k)
				k = ColStruct1.caption.size();
		}

		for (int i1 = 0; i1 < arraylist.size(); i1++) {
			ColStruct ColStruct2 = (ColStruct) arraylist.get(i1);
			for (int j1 = 0; j1 < k - ColStruct2.caption.size(); j1++)
				ColStruct2.caption.add(ColStruct2.caption
						.get(ColStruct2.caption.size() - 1));

		}

		int ai[][] = new int[j][k];
		Object obj2 = null;
		boolean flag1 = false;
		if (k > 0) {
			boolean flag2 = false;
			boolean flag4 = false;
			boolean flag6 = false;
			Object obj3 = null;
			try {
				for (int k1 = 0; k1 < k; k1++) {
					outputstream.write("<Row>\n".getBytes());
					for (int l1 = 0; l1 < j; l1++) {
						boolean flag3 = false;
						boolean flag5 = false;
						int i = 0;
						if (ai[l1][k1] == 0) {
							for (int i2 = l1 + 1; i2 < j
									&& ((ColStruct) arraylist.get(l1)).caption
											.get(k1).equals(
													((ColStruct) arraylist
															.get(i2)).caption
															.get(k1)); i2++) {
								flag3 = true;
								i++;
							}

							if (!flag3) {
								for (int j2 = k1 + 1; j2 < k; j2++)
									if (((ColStruct) arraylist.get(l1)).caption
											.get(k1).equals(
													((ColStruct) arraylist
															.get(l1)).caption
															.get(j2))) {
										ai[l1][j2] = 1;
										flag5 = true;
										i++;
									}

							}
							String s = String.valueOf(((ColStruct) arraylist
									.get(l1)).caption.get(k1));
							if (flag3) {
								ai[l1][k1] = 1;
								outputstream
										.write(("<Cell ss:StyleID=\""
												+ getStyleId(s, "s03")
												+ "\" ss:MergeAcross=\""
												+ i
												+ "\" ><Data ss:Type=\"String\">"
												+ getStringValue(s, 1) + "</Data></Cell>\n")
												.getBytes());
								l1 += i;
							}
							if (flag5) {
								ai[l1][k1] = 1;
								outputstream
										.write(("<Cell ss:StyleID=\""
												+ getStyleId(s, "s03")
												+ "\" ss:MergeDown=\""
												+ i
												+ "\" ><Data ss:Type=\"String\">"
												+ getStringValue(s, 1) + "</Data></Cell>\n")
												.getBytes());
							}
							if (!flag5 && !flag3) {
								if (flag6)
									outputstream
											.write(("<Cell  ss:StyleID=\""
													+ getStyleId(s, "s03")
													+ "\" ss:Index=\""
													+ (l1 + 1) + "\">")
													.getBytes());
								else
									outputstream.write(("<Cell  ss:StyleID=\""
											+ getStyleId(s, "s03") + "\">")
											.getBytes());
								outputstream
										.write(("<Data ss:Type=\"String\">"
												+ getStringValue(s, 1) + "</Data></Cell>\n")
												.getBytes());
							}
							flag6 = false;
						} else {
							flag6 = true;
						}
					}

					outputstream.write("</Row>\n".getBytes());
				}

			} catch (Exception exception) {
			}
		}
	}

	// private void writeReporthead(OutputStream paramOutputStream, ArrayList
	// paramArrayList)
	// {
	// int i = 0;
	// Object localObject1 = null;
	// Object localObject2 = null;
	// int j = paramArrayList.size();
	// int k = 0;
	// for (int m = 0; m < paramArrayList.size(); m++)
	// {
	// localColStruct = (ColStruct)paramArrayList.get(m);
	// if (localColStruct.caption.size() > k)
	// k = localColStruct.caption.size();
	// }
	// for (m = 0; m < paramArrayList.size(); m++)
	// {
	// localColStruct = (ColStruct)paramArrayList.get(m);
	// for (n = 0; n < k - localColStruct.caption.size(); n++)
	// localColStruct.caption.add(localColStruct.caption.get(localColStruct.caption.size()
	// - 1));
	// }
	// int[][] arrayOfInt = new int[j][k];
	// ColStruct localColStruct = null;
	// int n = 0;
	// if (k > 0)
	// {
	// int i1 = 0;
	// int i2 = 0;
	// int i3 = 0;
	// Object localObject3 = null;
	// try
	// {
	// for (int i4 = 0; i4 < k; i4++)
	// {
	// paramOutputStream.write("<Row>\n".getBytes());
	// for (int i5 = 0; i5 < j; i5++)
	// {
	// i1 = 0;
	// i2 = 0;
	// i = 0;
	// if (arrayOfInt[i5][i4] == 0)
	// {
	// for (int i6 = i5 + 1; (i6 < j) &&
	// (((ColStruct)paramArrayList.get(i5)).caption.get(i4).equals(((ColStruct)paramArrayList.get(i6)).caption.get(i4)));
	// i6++)
	// {
	// i1 = 1;
	// i += 1;
	// }
	// if (i1 == 0)
	// for (i6 = i4 + 1; i6 < k; i6++)
	// if
	// (((ColStruct)paramArrayList.get(i5)).caption.get(i4).equals(((ColStruct)paramArrayList.get(i5)).caption.get(i6)))
	// {
	// arrayOfInt[i5][i6] = 1;
	// i2 = 1;
	// i += 1;
	// }
	// String str =
	// String.valueOf(((ColStruct)paramArrayList.get(i5)).caption.get(i4));
	// if (i1 == 1)
	// {
	// arrayOfInt[i5][i4] = 1;
	// paramOutputStream.write(("<Cell ss:StyleID=\"" + getStyleId(str, "s03") +
	// "\" ss:MergeAcross=\"" + i + "\" ><Data ss:Type=\"String\">" +
	// getStringValue(str, 1) + "</Data></Cell>\n").getBytes());
	// i5 += i;
	// }
	// if (i2 == 1)
	// {
	// arrayOfInt[i5][i4] = 1;
	// paramOutputStream.write(("<Cell ss:StyleID=\"" + getStyleId(str, "s03") +
	// "\" ss:MergeDown=\"" + i + "\" ><Data ss:Type=\"String\">" +
	// getStringValue(str, 1) + "</Data></Cell>\n").getBytes());
	// }
	// if ((i2 != 1) && (i1 != 1))
	// {
	// if (i3 == 1)
	// paramOutputStream.write(("<Cell  ss:StyleID=\"" + getStyleId(str, "s03")
	// + "\" ss:Index=\"" + (i5 + 1) + "\">").getBytes());
	// else
	// paramOutputStream.write(("<Cell  ss:StyleID=\"" + getStyleId(str, "s03")
	// + "\">").getBytes());
	// paramOutputStream.write(("<Data ss:Type=\"String\">" +
	// getStringValue(str, 1) + "</Data></Cell>\n").getBytes());
	// }
	// i3 = 0;
	// }
	// else
	// {
	// i3 = 1;
	// }
	// }
	// paramOutputStream.write("</Row>\n".getBytes());
	// }
	// }
	// catch (Exception localException)
	// {
	// }
	// }
	// }

	public void exportDataToExcel(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse,
			ArrayList paramArrayList1, ArrayList paramArrayList2,
			String paramString1, String paramString2, int paramInt) {
		ArrayList localArrayList = createcaption(paramArrayList1);
		int i = localArrayList.size();
		int j = 0;
		if ((i != 0) && (paramArrayList2 != null)) {
			BufferedOutputStream localBufferedOutputStream = null;
			try {
				addStyleSystem();
				for (int k = 0; k < localArrayList.size(); k++)
					if (((ColStruct) localArrayList.get(k)).fixcol)
						j += 1;
				String str1 = getfilename() + ".xls";
				String str2 = paramHttpServletRequest.getParameter("mimeType");
				paramHttpServletResponse.setContentType(str2);
				paramHttpServletResponse.setHeader("Content-Disposition",
						"attachment; filename=" + str1);
				localBufferedOutputStream = new BufferedOutputStream(
						paramHttpServletResponse.getOutputStream());
				writeheader(localBufferedOutputStream);
				writedata(paramArrayList1, paramArrayList2, paramString1,
						paramString2, localBufferedOutputStream,
						localArrayList, j, paramInt);
				writefooter(localBufferedOutputStream);
				localBufferedOutputStream.flush();
			} catch (Exception localException2) {
			} finally {
				try {
					localBufferedOutputStream.close();
				} catch (Exception localException4) {
				}
			}
		}
	}

	private void addStyleSystem() {
		CellStyle localcellstyle = new CellStyle();
		localcellstyle.align = "Center";
		localcellstyle.Bold = true;
		localcellstyle.name = "s01";
		localcellstyle.FontSize = 24;
		localcellstyle.FontName = "瀹嬩綋";
		localcellstyle.valign = "Center";
		this.cellstyle_list.add(localcellstyle);
		localcellstyle = new CellStyle();
		localcellstyle.name = "s02";
		localcellstyle.align = "Right";
		localcellstyle.valign = "Center";
		this.cellstyle_list.add(localcellstyle);
		localcellstyle = new CellStyle();
		localcellstyle.name = "s03";
		localcellstyle.align = "Center";
		localcellstyle.valign = "Center";
		localcellstyle.BottomLineStyle = "Continuous";
		localcellstyle.TopLineStyle = "Continuous";
		localcellstyle.LeftLineStyle = "Continuous";
		localcellstyle.RightLineStyle = "Continuous";
		this.cellstyle_list.add(localcellstyle);
		localcellstyle = new CellStyle();
		localcellstyle.name = "s04";
		localcellstyle.align = "Left";
		localcellstyle.valign = "Center";
		localcellstyle.BottomLineStyle = "Continuous";
		localcellstyle.TopLineStyle = "Continuous";
		localcellstyle.LeftLineStyle = "Continuous";
		localcellstyle.RightLineStyle = "Continuous";
		this.cellstyle_list.add(localcellstyle);
	}

	private void deleteDir(String paramString) {
		try {
			GregorianCalendar localGregorianCalendar = new GregorianCalendar();
			String str1 = String.valueOf(localGregorianCalendar.get(1));
			String str2 = "00"
					+ String.valueOf(localGregorianCalendar.get(2) + 1);
			str2 = str2.substring(str2.length() - 2, str2.length());
			String str3 = "00" + String.valueOf(localGregorianCalendar.get(5));
			str3 = str3.substring(str3.length() - 2, str3.length());
			String str4 = str1 + str2 + str3;
			int i = Integer.parseInt(str4);
			File localFile = new File(paramString);
			if (localFile.exists()) {
				File[] arrayOfFile = localFile.listFiles();
				for (int j = 0; j < arrayOfFile.length; j++) {
					String str5 = arrayOfFile[j].getName();
					if (str5.length() == 20) {
						String str6 = str5.substring(0, 8);
						String str7 = str5.substring(17, 20);
						if ((str7.equals("xls"))
								&& (Integer.parseInt(str6) < i))
							arrayOfFile[j].delete();
					}
				}
			}
		} catch (Exception localException) {
		}
	}

	private String replaceStringXml2(String paramString) {
		try {
			paramString = MethodFactory.replace(paramString, "&", "&amp;");
			paramString = MethodFactory.replace(paramString, "<", "&lt;");
			paramString = MethodFactory.replace(paramString, ">", "&gt;");
			String str = getStyleId(paramString, "");
			if (!paramString.equals(""))
				paramString = MethodFactory.replace(paramString, "[style-"
						+ str + "]", "");
		} catch (Exception localException) {
		}
		return paramString;
	}

	private String getStringValue(String paramString, int paramInt) {
		try {
			paramString = MethodFactory.replace(paramString, "&", "&amp;");
			paramString = MethodFactory.replace(paramString, "<", "&lt;");
			paramString = MethodFactory.replace(paramString, ">", "&gt;");
			String str = getStyleId(paramString, "");
			if ((!paramString.equals("")) && (paramInt == 1))
				paramString = MethodFactory.replace(paramString, "[style-"
						+ str + "]", "");
		} catch (Exception localException) {
		}
		return paramString;
	}

	private String getStyleId(String s, String s1) {
		String s2 = s1;
		String s3 = "[style-";
		if (s.length() > 7) {
			String s4 = s.substring(0, 7);
			try {
				if (s4.toLowerCase().equals(s3)) {
					int i = s.indexOf("]");
					String s5 = s.substring(0, i + 1);
					s5 = MethodFactory.replace(s5, "[style-", "");
					s5 = MethodFactory.replace(s5, "]", "");
					s2 = s5;
				}
			} catch (Exception exception) {
				s2 = s1;
			}
		}
		return s2;
	}

	// private String getStyleId(String paramString1, String paramString2)
	// {
	// Object localObject = paramString2;
	// String str1 = "[style-";
	// if (paramString1.length() > 7)
	// {
	// String str2 = paramString1.substring(0, 7);
	// try
	// {
	// if (str2.toLowerCase().equals(str1))
	// {
	// int i = paramString1.indexOf("]");
	// str2 = paramString1.substring(0, i + 1);
	// str2 = MethodFactory.replace(str2, "[style-", "");
	// str2 = MethodFactory.replace(str2, "]", "");
	// localObject = str2;
	// }
	// }
	// catch (Exception localException)
	// {
	// localObject = paramString2;
	// }
	// }
	// return localObject;
	// }

	// private void writedata(ArrayList arraylist, ArrayList arraylist1, String
	// s, String s1, OutputStream outputstream, ArrayList arraylist2, int i,
	// int j)
	// throws Exception
	// {
	// int j2;
	// boolean flag;
	// int ai[][];
	// int k2;
	// int k = 0;
	// for(int l = 0; l < arraylist2.size(); l++)
	// {
	// ColStruct ColStruct1 = (ColStruct)arraylist2.get(l);
	// if(ColStruct1.caption.size() > k)
	// k = ColStruct1.caption.size();
	// }
	//
	// k += 2;
	// outputstream.write("<Worksheet ss:Name=\"Book1\">\n".getBytes());
	// outputstream.write("<Names>\n".getBytes());
	// outputstream.write(("<NamedRange ss:Name=\"Print_Titles\" ss:RefersTo=\"=Book1!R1:R"
	// + k).getBytes());
	// if(i > 0)
	// outputstream.write((",C1:C" + i).getBytes());
	// outputstream.write("\"/>\n</Names>\n".getBytes());
	// outputstream.write("<Table>\n".getBytes());
	// for(int i1 = 0; i1 < arraylist2.size(); i1++)
	// {
	// ColStruct ColStruct2 = (ColStruct)arraylist2.get(i1);
	// outputstream.write(("<Column ss:AutoFitWidth=\"0\" ss:Width=\"" +
	// ColStruct2.width + "\"/>\n").getBytes());
	// }
	//
	// outputstream.write(("<Row ss:Height=\"" + getTitleHeight() +
	// "\">\n").getBytes());
	// for(int j1 = 0; j1 < i; j1++)
	// outputstream.write("<Cell ss:StyleID=\"s01\"><NamedCell ss:Name=\"Print_Titles\"/></Cell>".getBytes());
	//
	// if(getPageCol() > 0 && getPageCol() > i)
	// {
	// for(int k1 = 0; k1 < arraylist2.size();
	// outputstream.write("</Cell>\n".getBytes()))
	// {
	// outputstream.write("<Cell ".getBytes());
	// if(getPageCol() - i > 1 && arraylist2.size() - k1 > 1)
	// {
	// if(arraylist2.size() - k1 < getPageCol() - i && arraylist2.size() - k1 -
	// i > 0)
	// outputstream.write((" ss:MergeAcross=\"" + (arraylist2.size() - k1 - i -
	// 1) + "\"").getBytes());
	// else
	// outputstream.write((" ss:MergeAcross=\"" + (getPageCol() - i - 1) +
	// "\"").getBytes());
	// k1 = (k1 + getPageCol()) - i;
	// } else
	// {
	// k1++;
	// }
	// outputstream.write((" ss:StyleID=\"" + getStyleId(s, "s01") +
	// "\">").getBytes());
	// writeDate(outputstream, s, 0);
	// }
	//
	// } else
	// {
	// outputstream.write(("<Cell ss:MergeAcross=\"" +
	// String.valueOf(arraylist2.size() - i) +
	// "\" ss:StyleID=\"s01\">").getBytes());
	// writeDate(outputstream, s, 0);
	// outputstream.write("</Cell>\n".getBytes());
	// }
	// outputstream.write("</Row>\n".getBytes());
	// outputstream.write("<Row>\n".getBytes());
	// for(int l1 = 0; l1 < i; l1++)
	// outputstream.write("<Cell ss:StyleID=\"s02\"><NamedCell ss:Name=\"Print_Titles\"/></Cell>".getBytes());
	//
	// if(getPageCol() > 0 && getPageCol() > i)
	// {
	// for(int i2 = 0; i2 < arraylist2.size();
	// outputstream.write("</Cell>\n".getBytes()))
	// {
	// outputstream.write("<Cell ".getBytes());
	// if(getPageCol() - i > 1 && arraylist2.size() - i2 > 1)
	// {
	// if(arraylist2.size() - i2 < getPageCol() - i && arraylist2.size() - i2 -
	// i > 0)
	// outputstream.write((" ss:MergeAcross=\"" + (arraylist2.size() - i2 - i -
	// 1) + "\"").getBytes());
	// else
	// outputstream.write((" ss:MergeAcross=\"" + (getPageCol() - i - 1) +
	// "\"").getBytes());
	// i2 = (i2 + getPageCol()) - i;
	// } else
	// {
	// i2++;
	// }
	// outputstream.write((" ss:StyleID=\"" + getStyleId(s1, "s02") +
	// "\">").getBytes());
	// writeDate(outputstream, s1, 0);
	// }
	//
	// } else
	// {
	// outputstream.write(("<Cell ss:MergeAcross=\"" +
	// String.valueOf(arraylist2.size() - i) +
	// "\" ss:StyleID=\"s01\">").getBytes());
	// writeDate(outputstream, s1, 0);
	// outputstream.write("</Cell>\n".getBytes());
	// }
	// outputstream.write("</Row>\n".getBytes());
	// writeReporthead(outputstream, arraylist2);
	// String s2 = "";
	// String s4 = "";
	// j2 = 0;
	// flag = false;
	// ai = new int[arraylist1.size()][arraylist2.size()];
	// k2 = 0;
	// _L11:
	// Object obj;
	// int l2;
	// if(k2 >= arraylist1.size())
	// break; /* Loop/switch isn't completed */
	// obj = arraylist1.get(k2);
	// outputstream.write("<Row>\n".getBytes());
	// l2 = 0;
	// _L9:
	// if(l2 >= arraylist2.size()) goto _L2; else goto _L1
	// _L1:
	// if(ai[k2][l2] == 1) goto _L4; else goto _L3
	// _L3:
	// String s3;
	// String s5;
	// ColStruct ColStruct3;
	// ColStruct3 = (ColStruct)arraylist2.get(l2);
	// try
	// {
	// s5 = ColStruct3.property;
	// }
	// catch(Exception exception1)
	// {
	// s5 = "";
	// }
	// try
	// {
	// s3 = MethodFactory.getThisString(PropertyUtils.getProperty(obj,
	// s5.trim()));
	// }
	// catch(Exception exception2)
	// {
	// System.out.println(s5 + "::" + exception2.toString());
	// s3 = "";
	// }
	// if(l2 >= j) goto _L6; else goto _L5
	// _L5:
	// int i3 = k2 + 1;
	// _L8:
	// if(i3 >= arraylist1.size()) goto _L6; else goto _L7
	// _L7:
	// String s6;
	// obj = arraylist1.get(i3);
	// s6 = MethodFactory.getThisString(PropertyUtils.getProperty(obj,
	// s5.trim()));
	// if(!s6.equals(s3))
	// break; /* Loop/switch isn't completed */
	// try
	// {
	// ai[i3][l2] = 1;
	// j2++;
	// continue; /* Loop/switch isn't completed */
	// }
	// catch(Exception exception3)
	// {
	// System.out.println(s5 + "::" + exception3.toString());
	// }
	// s3 = "";
	// i3++;
	// goto _L8
	// _L6:
	// if(flag)
	// outputstream.write(("<Cell  ss:Index=\"" + (l2 + 1) + "\"").getBytes());
	// else
	// outputstream.write("<Cell ".getBytes());
	// if(j2 == 0)
	// {
	// outputstream.write((" ss:StyleID=\"" + getStyleId(s3, "s04") +
	// "\">").getBytes());
	// writeDate(outputstream, s3, ColStruct3.type);
	// } else
	// {
	// outputstream.write((" ss:StyleID=\"" + getStyleId(s3, "s04") +
	// "\" ss:MergeDown=\"" + j2 + "\">").getBytes());
	// writeDate(outputstream, s3, ColStruct3.type);
	// }
	// if(ColStruct3.fixcol)
	// outputstream.write("<NamedCell ss:Name=\"Print_Titles\"/>".getBytes());
	// outputstream.write("</Cell>\n".getBytes());
	// flag = false;
	// j2 = 0;
	// continue; /* Loop/switch isn't completed */
	// _L4:
	// flag = true;
	// l2++;
	// goto _L9
	// _L2:
	// outputstream.write("</Row>\n".getBytes());
	// k2++;
	// if(true) goto _L11; else goto _L10
	// _L10:
	// outputstream.write("</Table>\n".getBytes());
	// break MISSING_BLOCK_LABEL_1549;
	// Exception exception;
	// exception;
	// throw exception;
	// }
	private void writedata(ArrayList paramArrayList1,
			ArrayList paramArrayList2, String paramString1,
			String paramString2, OutputStream paramOutputStream,
			ArrayList paramArrayList3, int paramInt1, int paramInt2)
			throws Exception {
		try {
			int i = 0;
			int j = 0;
			for (j = 0; j < paramArrayList3.size(); j++) {
				ColStruct localObject1 = (ColStruct) paramArrayList3.get(j);
				if (((ColStruct) localObject1).caption.size() > i)
					i = ((ColStruct) localObject1).caption.size();
			}
			i += 2;
			paramOutputStream.write("<Worksheet ss:Name=\"Book1\">\n"
					.getBytes());
			paramOutputStream.write("<Names>\n".getBytes());
			paramOutputStream
					.write(("<NamedRange ss:Name=\"Print_Titles\" ss:RefersTo=\"=Book1!R1:R" + i)
							.getBytes());
			if (paramInt1 > 0)
				paramOutputStream.write((",C1:C" + paramInt1).getBytes());
			paramOutputStream.write("\"/>\n</Names>\n".getBytes());
			paramOutputStream.write("<Table>\n".getBytes());
			for (j = 0; j < paramArrayList3.size(); j++) {
				ColStruct localObject1 = (ColStruct) paramArrayList3.get(j);
				paramOutputStream
						.write(("<Column ss:AutoFitWidth=\"0\" ss:Width=\""
								+ ((ColStruct) localObject1).width + "\"/>\n")
								.getBytes());
			}
			paramOutputStream
					.write(("<Row ss:Height=\"" + getTitleHeight() + "\">\n")
							.getBytes());
			for (j = 0; j < paramInt1; j++)
				paramOutputStream
						.write("<Cell ss:StyleID=\"s01\"><NamedCell ss:Name=\"Print_Titles\"/></Cell>"
								.getBytes());
			if ((getPageCol() > 0) && (getPageCol() > paramInt1))
				j = 0;
			while (j < paramArrayList3.size()) {
				paramOutputStream.write("<Cell ".getBytes());
				if ((getPageCol() - paramInt1 > 1)
						&& (paramArrayList3.size() - j > 1)) {
					if ((paramArrayList3.size() - j < getPageCol() - paramInt1)
							&& (paramArrayList3.size() - j - paramInt1 > 0))
						paramOutputStream
								.write((" ss:MergeAcross=\""
										+ (paramArrayList3.size() - j
												- paramInt1 - 1) + "\"")
										.getBytes());
					else
						paramOutputStream.write((" ss:MergeAcross=\""
								+ (getPageCol() - paramInt1 - 1) + "\"")
								.getBytes());
					j = j + getPageCol() - paramInt1;
				} else {
					j += 1;
				}
				paramOutputStream.write((" ss:StyleID=\""
						+ getStyleId(paramString1, "s01") + "\">").getBytes());
				writeDate(paramOutputStream, paramString1, 0);
				paramOutputStream.write("</Cell>\n".getBytes());
				// continue;
				paramOutputStream
						.write(("<Cell ss:MergeAcross=\""
								+ String.valueOf(paramArrayList3.size()
										- paramInt1) + "\" ss:StyleID=\"s01\">")
								.getBytes());
				writeDate(paramOutputStream, paramString1, 0);
				paramOutputStream.write("</Cell>\n".getBytes());
			}
			paramOutputStream.write("</Row>\n".getBytes());
			paramOutputStream.write("<Row>\n".getBytes());
			for (j = 0; j < paramInt1; j++)
				paramOutputStream
						.write("<Cell ss:StyleID=\"s02\"><NamedCell ss:Name=\"Print_Titles\"/></Cell>"
								.getBytes());
			if ((getPageCol() > 0) && (getPageCol() > paramInt1))
				j = 0;
			while (j < paramArrayList3.size()) {
				paramOutputStream.write("<Cell ".getBytes());
				if ((getPageCol() - paramInt1 > 1)
						&& (paramArrayList3.size() - j > 1)) {
					if ((paramArrayList3.size() - j < getPageCol() - paramInt1)
							&& (paramArrayList3.size() - j - paramInt1 > 0))
						paramOutputStream
								.write((" ss:MergeAcross=\""
										+ (paramArrayList3.size() - j
												- paramInt1 - 1) + "\"")
										.getBytes());
					else
						paramOutputStream.write((" ss:MergeAcross=\""
								+ (getPageCol() - paramInt1 - 1) + "\"")
								.getBytes());
					j = j + getPageCol() - paramInt1;
				} else {
					j += 1;
				}
				paramOutputStream.write((" ss:StyleID=\""
						+ getStyleId(paramString2, "s02") + "\">").getBytes());
				writeDate(paramOutputStream, paramString2, 0);
				paramOutputStream.write("</Cell>\n".getBytes());
				// continue;
				paramOutputStream
						.write(("<Cell ss:MergeAcross=\""
								+ String.valueOf(paramArrayList3.size()
										- paramInt1) + "\" ss:StyleID=\"s01\">")
								.getBytes());
				writeDate(paramOutputStream, paramString2, 0);
				paramOutputStream.write("</Cell>\n".getBytes());
			}
			paramOutputStream.write("</Row>\n".getBytes());
			writeReporthead(paramOutputStream, paramArrayList3);
			String str1 = "";
			Object localObject1 = "";
			int k = 0;
			int m = 0;
			int[][] arrayOfInt = new int[paramArrayList2.size()][paramArrayList3
					.size()];
			for (int n = 0; n < paramArrayList2.size(); n++) {
				Object localObject2 = paramArrayList2.get(n);
				paramOutputStream.write("<Row>\n".getBytes());
				for (int i1 = 0; i1 < paramArrayList3.size(); i1++)
					if (arrayOfInt[n][i1] != 1) {
						ColStruct localColStruct = (ColStruct) paramArrayList3
								.get(i1);
						try {
							localObject1 = localColStruct.property;
						} catch (Exception localException2) {
							localObject1 = "";
						}
						try {
							str1 = MethodFactory.getThisString(PropertyUtils
									.getProperty(localObject2,
											((String) localObject1).trim()));
						} catch (Exception localException3) {
							System.out.println((String) localObject1 + "::"
									+ localException3.toString());
							str1 = "";
						}
						if (i1 < paramInt2)
							for (int i2 = n + 1; i2 < paramArrayList2.size(); i2++)
								try {
									localObject2 = paramArrayList2.get(i2);
									String str2 = MethodFactory
											.getThisString(PropertyUtils
													.getProperty(
															localObject2,
															((String) localObject1)
																	.trim()));
									if (!str2.equals(str1))
										break;
									arrayOfInt[i2][i1] = 1;
									k += 1;
								} catch (Exception localException4) {
									System.out
											.println((String) localObject1
													+ "::"
													+ localException4
															.toString());
									str1 = "";
								}
						if (m == 1)
							paramOutputStream.write(("<Cell  ss:Index=\""
									+ (i1 + 1) + "\"").getBytes());
						else
							paramOutputStream.write("<Cell ".getBytes());
						if (k == 0) {
							paramOutputStream.write((" ss:StyleID=\""
									+ getStyleId(str1, "s04") + "\">")
									.getBytes());
							writeDate(paramOutputStream, str1,
									localColStruct.type);
						} else {
							paramOutputStream.write((" ss:StyleID=\""
									+ getStyleId(str1, "s04")
									+ "\" ss:MergeDown=\"" + k + "\">")
									.getBytes());
							writeDate(paramOutputStream, str1,
									localColStruct.type);
						}
						if (localColStruct.fixcol)
							paramOutputStream
									.write("<NamedCell ss:Name=\"Print_Titles\"/>"
											.getBytes());
						paramOutputStream.write("</Cell>\n".getBytes());
						m = 0;
						k = 0;
					} else {
						m = 1;
					}
				paramOutputStream.write("</Row>\n".getBytes());
			}
			paramOutputStream.write("</Table>\n".getBytes());
		} catch (Exception localException1) {
			throw localException1;
		}
	}

	private void writeDate(OutputStream paramOutputStream, String paramString,
			int paramInt) throws Exception {
		paramString = getStringValue(paramString, 1);
		paramOutputStream.write("<Data ss:Type=\"".getBytes());
		if (paramInt == 1)
			try {
				if ("".equals(paramString)) {
					paramOutputStream.write("String".getBytes());
				} else {
					Double.valueOf(paramString).intValue();
					paramOutputStream.write("Number".getBytes());
				}
			} catch (Exception localException) {
				paramOutputStream.write("String".getBytes());
			}
		else
			paramOutputStream.write("String".getBytes());
		paramOutputStream.write("\">".getBytes());
		paramOutputStream.write((paramString + "</Data>").getBytes());
	}

	private void writestyle(OutputStream paramOutputStream) throws Exception {
		for (int i = 0; i < this.cellstyle_list.size(); i++) {
			CellStyle localcellstyle = (CellStyle) this.cellstyle_list.get(i);
			if ((localcellstyle != null) && (localcellstyle.name != null)
					&& (!localcellstyle.name.trim().equals(""))) {
				paramOutputStream.write(("<Style ss:ID=\""
						+ localcellstyle.name.trim().toLowerCase() + "\">\n")
						.getBytes());
				if ((localcellstyle.align != null)
						|| (localcellstyle.valign != null)) {
					paramOutputStream.write("<Alignment".getBytes());
					if (localcellstyle.align != null)
						paramOutputStream.write((" ss:Horizontal=\""
								+ localcellstyle.align + "\"").getBytes());
					if (localcellstyle.valign != null)
						paramOutputStream.write((" ss:Vertical=\""
								+ localcellstyle.valign + "\"").getBytes());
					paramOutputStream.write("/>\n".getBytes());
				}
				if ((localcellstyle.FontSize > 0)
						|| (localcellstyle.FontName != null)
						|| (localcellstyle.Color != null)
						|| (!localcellstyle.Bold)) {
					paramOutputStream.write("<Font x:CharSet=\"134\""
							.getBytes());
					if (localcellstyle.FontSize > 0)
						paramOutputStream.write((" ss:Size=\""
								+ localcellstyle.FontSize + "\"").getBytes());
					if (localcellstyle.FontName != null)
						paramOutputStream.write((" ss:FontName=\""
								+ localcellstyle.FontName + "\"").getBytes());
					if (localcellstyle.Color != null)
						paramOutputStream.write((" ss:Color=\""
								+ localcellstyle.Color + "\"").getBytes());
					if (localcellstyle.Bold)
						paramOutputStream.write(" ss:Bold=\"1\"".getBytes());
					paramOutputStream.write("/>\n".getBytes());
				}
				if ((localcellstyle.BottomLineStyle != null)
						|| (localcellstyle.LeftLineStyle != null)
						|| (localcellstyle.RightLineStyle != null)
						|| (localcellstyle.TopLineStyle != null)
						|| (localcellstyle.BottomLineWidth > 1)
						|| (localcellstyle.LeftLineWidth > 1)
						|| (localcellstyle.RightLineWidth > 1)
						|| (localcellstyle.TopLineWidth > 1)) {
					if (localcellstyle.BottomLineWidth < 1)
						localcellstyle.BottomLineWidth = 1;
					if (localcellstyle.LeftLineWidth < 1)
						localcellstyle.LeftLineWidth = 1;
					if (localcellstyle.RightLineWidth < 1)
						localcellstyle.RightLineWidth = 1;
					if (localcellstyle.TopLineWidth > 1)
						localcellstyle.TopLineWidth = 1;
					paramOutputStream.write("<Borders>\n".getBytes());
					if (localcellstyle.BottomLineStyle != null) {
						paramOutputStream
								.write("<Border ss:Position=\"Bottom\""
										.getBytes());
						paramOutputStream.write((" ss:LineStyle=\""
								+ localcellstyle.BottomLineStyle + "\"")
								.getBytes());
						paramOutputStream.write((" ss:Weight=\""
								+ localcellstyle.BottomLineWidth + "\"/>\n")
								.getBytes());
					}
					if (localcellstyle.LeftLineStyle != null) {
						paramOutputStream.write("<Border ss:Position=\"Left\" "
								.getBytes());
						paramOutputStream.write((" ss:LineStyle=\""
								+ localcellstyle.LeftLineStyle + "\"")
								.getBytes());
						paramOutputStream.write((" ss:Weight=\""
								+ localcellstyle.LeftLineWidth + "\"/>\n")
								.getBytes());
					}
					if (localcellstyle.RightLineStyle != null) {
						paramOutputStream.write("<Border ss:Position=\"Right\""
								.getBytes());
						paramOutputStream.write((" ss:LineStyle=\""
								+ localcellstyle.RightLineStyle + "\"")
								.getBytes());
						paramOutputStream.write((" ss:Weight=\""
								+ localcellstyle.RightLineWidth + "\"/>\n")
								.getBytes());
					}
					if (localcellstyle.TopLineStyle != null) {
						paramOutputStream.write("<Border ss:Position=\"Top\""
								.getBytes());
						paramOutputStream.write((" ss:LineStyle=\""
								+ localcellstyle.TopLineStyle + "\"")
								.getBytes());
						paramOutputStream.write((" ss:Weight=\""
								+ localcellstyle.TopLineWidth + "\"/>\n")
								.getBytes());
					}
					paramOutputStream.write("</Borders>\n".getBytes());
				}
				if (localcellstyle.BGColor != null)
					paramOutputStream
							.write(("<Interior ss:Color=\""
									+ localcellstyle.BGColor + "\" ss:Pattern=\"Solid\"/>\n")
									.getBytes());
				paramOutputStream.write("</Style>\n".getBytes());
			}
		}
	}

	private void writeheader(OutputStream paramOutputStream) throws Exception {
		try {
			paramOutputStream
					.write("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n"
							.getBytes());
			paramOutputStream
					.write("<?mso-application progid=\"Excel.Sheet\"?>\n"
							.getBytes());
			paramOutputStream
					.write("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
							.getBytes());
			paramOutputStream
					.write("\txmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
							.getBytes());
			paramOutputStream
					.write("\txmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
							.getBytes());
			paramOutputStream
					.write("\txmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
							.getBytes());
			paramOutputStream
					.write("\txmlns:html=\"http://www.w3.org/TR/REC-html40\">\n"
							.getBytes());
			paramOutputStream
					.write("<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\n"
							.getBytes());
			paramOutputStream.write("<WindowHeight>9000</WindowHeight>\n"
					.getBytes());
			paramOutputStream.write("<WindowWidth>14940</WindowWidth>\n"
					.getBytes());
			paramOutputStream
					.write("<WindowTopX>240</WindowTopX>\n".getBytes());
			paramOutputStream.write("<WindowTopY>15</WindowTopY>\n".getBytes());
			paramOutputStream
					.write("<ProtectStructure>False</ProtectStructure>\n"
							.getBytes());
			paramOutputStream.write("<ProtectWindows>False</ProtectWindows>\n"
					.getBytes());
			paramOutputStream.write("</ExcelWorkbook>\n".getBytes());
			paramOutputStream.write("<Styles>\n".getBytes());
			paramOutputStream
					.write("<Style ss:ID=\"Default\" ss:Name=\"Normal\">\n"
							.getBytes());
			paramOutputStream.write("<Alignment ss:Vertical=\"Center\"/>\n"
					.getBytes());
			paramOutputStream.write("<Borders/>\n".getBytes());
			paramOutputStream
					.write("<Font ss:FontName=\"瀹嬩綋\" x:CharSet=\"134\" ss:Size=\"12\"/>\n"
							.getBytes());
			paramOutputStream.write("<Interior/>\n".getBytes());
			paramOutputStream.write("<NumberFormat/>\n".getBytes());
			paramOutputStream.write("<Protection/>\n".getBytes());
			paramOutputStream.write("</Style>\n".getBytes());
			writestyle(paramOutputStream);
			paramOutputStream.write("</Styles>\n".getBytes());
		} catch (Exception localException) {
			throw localException;
		}
	}

	private void writefooter(OutputStream paramOutputStream) throws Exception {
		try {
			paramOutputStream
					.write("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">\n"
							.getBytes());
			paramOutputStream.write("<Print>\n".getBytes());
			paramOutputStream.write("<ValidPrinterInfo/>\n".getBytes());
			paramOutputStream.write("<PaperSizeIndex>9</PaperSizeIndex>\n"
					.getBytes());
			paramOutputStream
					.write("<HorizontalResolution>180</HorizontalResolution>\n"
							.getBytes());
			paramOutputStream
					.write("<VerticalResolution>180</VerticalResolution>\n"
							.getBytes());
			paramOutputStream.write("</Print>\n".getBytes());
			if (getShowPageBreak()) {
				paramOutputStream.write("<ShowPageBreakZoom/>".getBytes());
				paramOutputStream.write("<PageBreakZoom>100</PageBreakZoom>"
						.getBytes());
			}
			paramOutputStream.write("<Selected/>\n".getBytes());
			paramOutputStream.write("<Panes>\n".getBytes());
			paramOutputStream.write("<Pane>\n".getBytes());
			paramOutputStream.write("<Number>3</Number>\n".getBytes());
			paramOutputStream.write("<ActiveRow>2</ActiveRow>\n".getBytes());
			paramOutputStream.write("<ActiveCol>2</ActiveCol>\n".getBytes());
			paramOutputStream.write("<RangeSelection>C3</RangeSelection>\n"
					.getBytes());
			paramOutputStream.write("</Pane>\n".getBytes());
			paramOutputStream.write("</Panes>\n".getBytes());
			paramOutputStream.write("<ProtectObjects>False</ProtectObjects>\n"
					.getBytes());
			paramOutputStream
					.write("<ProtectScenarios>False</ProtectScenarios>\n"
							.getBytes());
			paramOutputStream.write("</WorksheetOptions>\n".getBytes());
			if ((getPageCol() > 0) || (getPageRow() > 0)) {
				paramOutputStream
						.write("<PageBreaks xmlns=\"urn:schemas-microsoft-com:office:excel\">\n"
								.getBytes());
				if (getPageCol() > 0) {
					paramOutputStream.write("<ColBreaks>\n".getBytes());
					paramOutputStream.write("<ColBreak>\n".getBytes());
					paramOutputStream
							.write(("<Column>" + getPageCol() + "</Column>\n")
									.getBytes());
					paramOutputStream.write("</ColBreak>\n".getBytes());
					paramOutputStream.write("</ColBreaks>\n".getBytes());
				}
				if (getPageRow() > 0) {
					paramOutputStream.write("<RowBreaks>\n".getBytes());
					paramOutputStream.write("<RowBreak>\n".getBytes());
					paramOutputStream
							.write(("<Row>" + getPageRow() + "</Row>\n")
									.getBytes());
					paramOutputStream.write("</RowBreak>\n".getBytes());
					paramOutputStream.write("</RowBreaks>\n".getBytes());
				}
				paramOutputStream.write("</PageBreaks>\n".getBytes());
			}
			paramOutputStream.write("</Worksheet>\n".getBytes());
			paramOutputStream.write("</Workbook>\n".getBytes());
		} catch (Exception localException) {
			throw localException;
		}
	}

	public static void main(String[] paramArrayOfString) {
		String str1 = "aaaa";
		String str2 = "[sty";
		String str3 = str2.substring(0, 7);
		System.out.print(str3);
		if (str3.toLowerCase().equals("[style-")) {
			int i = str2.indexOf("]");
			str3 = str2.substring(0, i + 1);
			System.out.println(str3);
			try {
				str3 = MethodFactory.replace(str3, "[style-", "");
				str3 = MethodFactory.replace(str3, "]", "");
			} catch (Exception localException) {
			}
			System.out.println(str3);
		}
	}

	private class ColStruct {
		public String property = null;
		public String width = null;
		public ArrayList caption = new ArrayList();
		public boolean fixcol = false;
		public int colnumber = 0;
		public int type = 0;

		public ColStruct() {
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.exportExcel JD-Core Version: 0.6.1
 */