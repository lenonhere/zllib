package com.common.jxl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.PropertyUtils;

import com.zl.util.MethodFactory;

public class JxlReport {

	private String fileName = ""; // 生成文件名
	private String title = ""; // 台头名称
	private WritableWorkbook book = null;
	private WritableSheet sheet = null; // 工作表
	private int defaultColumnWidth = 10; // 默认列宽
	private WritableCellFormat headFormat = null;
	private WritableCellFormat colHeadFormat = null;
	private WritableCellFormat rowHeadFormat = null;
	private WritableCellFormat contentFormat = null;
	private WritableCellFormat dataFormat = null;
	private WritableCellFormat dataBlueFormat = null;
	private WritableCellFormat dataRedFormat = null;
	private WritableCellFormat dataGreenFormat = null;
	private HashMap formats = null;
	private List captionResult = null; // 标题记录集
	private List dataResult = null; // 内容记录集
	private int fixCols = 0; // 锁定列数
	private int fixCount = 0;
	private boolean columnAverage = false;

	private PageOrientation pageOrientation = PageOrientation.LANDSCAPE;
	private PaperSize paperSize = PaperSize.A4;
	private double pageWidth = 0;
	private double pageHeight = 0;
	private int pageCols = 0;
	private int colMergeCount = 1; // 合并列数,默认只合并第一列

	public int getColMergeCount() {
		return colMergeCount;
	}

	public void setColMergeCount(int colMergeCount) {
		this.colMergeCount = colMergeCount;
	}

	public JxlReport(String title, List captionResult, List dataResult) {
		this.title = title;
		List caption_list = createCaption(captionResult);
		this.captionResult = caption_list;
		this.dataResult = dataResult;

		for (int i = 0; i < caption_list.size(); i++) {
			if (((ColStruct) caption_list.get(i)).fixcol) {
				fixCount = fixCount + 1;
			}
		}

		initFormat();
	}

	public PageOrientation getPageOrientation() {
		return pageOrientation;
	}

	public void setPageOrientation(PageOrientation pageOrientation) {
		this.pageOrientation = pageOrientation;
	}

	public PaperSize getPaperSize() {
		return paperSize;
	}

	public void setPaperSize(PaperSize paperSize) {
		this.paperSize = paperSize;
	}

	private void initColumnWidth() {
		System.out.println("====preparing to initColumnWidth ......");
		if (columnAverage) {
			// 列宽均分方式

			if (paperSize.equals(PaperSize.A4)
					&& pageOrientation.equals(PageOrientation.LANDSCAPE)) {
				// A4横向 210 x 297 毫米
				pageWidth = 297;
				pageHeight = 210;
			} else if (paperSize.equals(PaperSize.A4)
					&& pageOrientation.equals(PageOrientation.PORTRAIT)) {
				// A4纵向 210 x 297 毫米
				pageWidth = 210;
				pageHeight = 297;
			} else if (paperSize.equals(PaperSize.A3)
					&& pageOrientation.equals(PageOrientation.LANDSCAPE)) {
				// A3横向 297×420mm
				pageWidth = 420;
				pageHeight = 297;
			} else if (paperSize.equals(PaperSize.A3)
					&& pageOrientation.equals(PageOrientation.PORTRAIT)) {
				// A3纵向 297×420mm
				pageWidth = 297;
				pageHeight = 420;
			} else if (paperSize.equals(PaperSize.B5)
					&& pageOrientation.equals(PageOrientation.LANDSCAPE)) {
				// B5横向 182 x 257 毫米
				pageWidth = 257;
				pageHeight = 182;
			} else if (paperSize.equals(PaperSize.B5)
					&& pageOrientation.equals(PageOrientation.PORTRAIT)) {
				// B5纵向 182 x 257 毫米
				pageWidth = 182;
				pageHeight = 257;
			}

			if (pageCols == 0)
				return;

			double lm = sheet.getSettings().getLeftMargin();
			double rm = sheet.getSettings().getRightMargin();
			double a = sheet.getSettings().getDefaultWidthMargin();
			System.out.println("a:" + a);
			System.out.println("lm:" + lm);
			System.out.println("rm:" + rm);

			lm = Arith.mul(lm, 25.4);
			rm = Arith.mul(rm, 25.4);

			double pwidth = pageWidth - lm - rm;
			System.out.println("pwidth:" + pwidth);
			double cwidth = Arith.div(pwidth, pageCols); // 单位:mm
			System.out.println("cwidth:" + cwidth + "mm");
			cwidth = Arith.div(cwidth, 2.29); // 单位转换为磅
			System.out.println("cwidth:" + cwidth + "磅");
			defaultColumnWidth = new BigDecimal(String.valueOf(cwidth))
					.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

			System.out.println("defaultColumnWidth:" + defaultColumnWidth);

			while (Arith.mul(Arith.mul(defaultColumnWidth + 0.38, 2.29),
					pageCols) > pwidth) {
				defaultColumnWidth = defaultColumnWidth - 1;
			}
			System.out.println("defaultColumnWidth:" + defaultColumnWidth);

			double plus = pwidth
					- Arith.mul(Arith.mul(defaultColumnWidth + 0.38, 2.29),
							pageCols);
			System.out.println("plus:" + plus);
			int perplus = new BigDecimal(String.valueOf(Arith.div(
					Arith.div(plus, fixCols), 2.29))).setScale(0,
					BigDecimal.ROUND_HALF_UP).intValue();
			System.out.println("perplus:" + perplus);
			// 设置默认列宽
			sheet.getSettings().setDefaultColumnWidth(defaultColumnWidth);
			for (int i = 0; i < fixCols; i++) {
				// 通过各锁定列宽度调整控制每页列数
				sheet.setColumnView(i, defaultColumnWidth + perplus);
			}

		} else {
			// 各列按所给标题记录集设定宽度
			for (int i = 0; i < captionResult.size(); i++) {
				ColStruct cap = (ColStruct) captionResult.get(i);
				if (cap.pound == 0) {
					double width = Arith.div(Arith.mul(
							new BigDecimal(cap.width).doubleValue(), 0.353),
							2.29);
					System.out.println("width[" + i + "]:" + width);
					sheet.setColumnView(
							i,
							new BigDecimal(String.valueOf(width)).setScale(0,
									BigDecimal.ROUND_HALF_UP).intValue());
				} else {
					System.out.println("width[" + i + "]:" + cap.pound);
					sheet.setColumnView(i, cap.pound);
				}
			}
		}

	}

	private void initFormat() {
		System.out.println("====preparing to initFormat ......");
		/************** 设置单元格字体 ***************/
		WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 6);
		WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 6,
				WritableFont.BOLD);
		try {
			headFormat = new WritableCellFormat(BoldFont);
			headFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			headFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			headFormat.setAlignment(Alignment.CENTRE); // 文字水平对齐
			headFormat.setWrap(false); // 文字是否换行
			headFormat.setBackground(Colour.GREY_25_PERCENT);

			colHeadFormat = new WritableCellFormat(BoldFont);
			colHeadFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			colHeadFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			colHeadFormat.setAlignment(Alignment.CENTRE); // 文字水平对齐
			colHeadFormat.setWrap(false); // 文字是否换行
			colHeadFormat.setBackground(Colour.GREY_25_PERCENT);
			colHeadFormat.setShrinkToFit(true);

			rowHeadFormat = new WritableCellFormat(BoldFont);
			rowHeadFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			rowHeadFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			rowHeadFormat.setAlignment(Alignment.LEFT); // 文字水平对齐
			rowHeadFormat.setWrap(false); // 文字是否换行
			rowHeadFormat.setBackground(Colour.GREY_25_PERCENT);

			contentFormat = new WritableCellFormat(NormalFont);
			contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			contentFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			contentFormat.setAlignment(Alignment.CENTRE); // 文字水平对齐
			contentFormat.setWrap(false); // 文字是否换行

			NumberFormat numFormat = new NumberFormat("###,##0.00"); // NumberFormat是jxl.write.NumberFormat
			dataFormat = new WritableCellFormat(NormalFont, numFormat);
			dataFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			dataFormat.setAlignment(Alignment.RIGHT); // 文字水平对齐
			dataFormat.setWrap(false); // 文字是否换行

			dataBlueFormat = new WritableCellFormat(NormalFont, numFormat);
			dataBlueFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			dataBlueFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			dataBlueFormat.setAlignment(Alignment.RIGHT); // 文字水平对齐
			dataBlueFormat.setWrap(false); // 文字是否换行
			dataBlueFormat.setBackground(Colour.BLUE);

			dataRedFormat = new WritableCellFormat(NormalFont, numFormat);
			dataRedFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			dataRedFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			dataRedFormat.setAlignment(Alignment.RIGHT); // 文字水平对齐
			dataRedFormat.setWrap(false); // 文字是否换行
			dataRedFormat.setBackground(Colour.RED);

			dataGreenFormat = new WritableCellFormat(NormalFont, numFormat);
			dataGreenFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			dataGreenFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			dataGreenFormat.setAlignment(Alignment.RIGHT); // 文字水平对齐
			dataGreenFormat.setWrap(false); // 文字是否换行
			dataGreenFormat.setBackground(Colour.GREEN);

			formats = new HashMap();
			formats.put("red", dataRedFormat);
			formats.put("blue", dataBlueFormat);
			formats.put("green", dataGreenFormat);

		} catch (Exception e) {

		}
	}

	public void exportDataToExcel(OutputStream bos) {
		System.out.println("====preparing to export ......");
		if (captionResult == null || dataResult == null)
			return;
		int intShowColCount = captionResult.size();
		if (intShowColCount == 0) {
			return;
		} else {

			try {
				// System.out.println("attachment; filename="+title+inputDate+".xls");
				// response.setContentType("application/vnd.ms-excel");
				// response.setHeader("Content-disposition","attachment;filename="+this.getfilename()+".xls");
				// bos =response.getOutputStream();// new
				// FileOutputStream(file);
				writeData(bos);
			} catch (Exception ex) {
				System.out.println("====exportDataToExcel error :"
						+ ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (Exception ex) {
				}
			}
		}
		return;
	}

	private void writeData(OutputStream bos) throws Exception {
		System.out.println("====preparing to writeData ......");
		try {

			fixCols = 0;
			int headRowCount = 0;
			// 计算固定行,补齐每列数据
			for (int i = 0; i < captionResult.size(); i++) {
				ColStruct ColStructtemp = (ColStruct) captionResult.get(i);
				if (ColStructtemp.caption.size() > headRowCount) {
					System.out.println(ColStructtemp.property + ":"
							+ ColStructtemp.caption.size());
					headRowCount = ColStructtemp.caption.size();
				}
				if (ColStructtemp.fixcol) {
					fixCols++;
				}
			}
			for (int i = 0; i < captionResult.size(); i++) {
				ColStruct ColStructtemp = (ColStruct) captionResult.get(i);
				for (int j = 0; j < headRowCount - ColStructtemp.caption.size(); j++) {
					ColStructtemp.caption.add(ColStructtemp.caption
							.get(ColStructtemp.caption.size() - 1));
				}
			}

			// 采用预先设置好的模板写入数据,主要解决打印时固定标题行的问题
			Workbook wb = Workbook.getWorkbook(new File(
					readReportTemplatePath() + "blank"
							+ String.valueOf(fixCols)
							+ String.valueOf(headRowCount) + ".xls"));
			book = Workbook.createWorkbook(bos, wb);
			sheet = book.getSheet(0);
			sheet.setName(title);

			// book = Workbook.createWorkbook(bos);
			// sheet = book.createSheet(title, 0);

			jxl.SheetSettings sheetset = sheet.getSettings();

			sheetset.setPageBreakPreviewMode(true);
			sheet.setPageSetup(pageOrientation, paperSize, 0, 0);

			sheetset.setHeaderMargin(0.4);
			sheetset.setFooterMargin(0.4);

			sheetset.setLeftMargin(0.3);
			sheetset.setRightMargin(0.3);

			initColumnWidth();

			sheetset.setTopMargin(0.8);
			sheetset.setBottomMargin(0.8);

			sheetset.setHorizontalPrintResolution(0);
			sheetset.setVerticalPrintResolution(0);

			HeaderFooter header = new HeaderFooter();
			header.getCentre().setFontSize(18);
			header.getCentre().setFontName("黑体");
			header.getCentre().append(title);
			sheetset.setHeader(header);

			HeaderFooter footer = new HeaderFooter();
			footer.getRight().append("第 ");
			footer.getRight().appendPageNumber();
			footer.getRight().append(" 页");
			sheetset.setFooter(footer);

			sheetset.setProtected(false);

			sheetset.setVerticalFreeze(headRowCount);
			sheetset.setHorizontalFreeze(fixCols);

			writeReportHead();

			String strvalue = "";
			String strProperty = "";
			int rowMerge = 0;

			int control[][] = new int[dataResult.size()][captionResult.size()];
			for (int j = 0; j < dataResult.size(); j++) {
				Object obj = dataResult.get(j);

				for (int i = 0; i < captionResult.size(); i++) {
					if (control[j][i] != 1) {
						ColStruct rows = (ColStruct) captionResult.get(i);

						try {
							strProperty = rows.property;
						} catch (Exception ex) {
							System.out.println(strProperty + "::"
									+ ex.toString());
							strProperty = "";
						}
						try {
							strvalue = MethodFactory
									.getThisString(PropertyUtils.getProperty(
											obj, strProperty.trim()));
						} catch (Exception Ex) {
							System.out.println(strProperty + "::"
									+ Ex.toString());
							strvalue = "";
						}
						if (i < colMergeCount) {
							for (int k = j + 1; k < dataResult.size(); k++) {
								try {
									Object obj2 = dataResult.get(k);
									String strvaluetemp = MethodFactory
											.getThisString(PropertyUtils
													.getProperty(obj2,
															strProperty.trim()));
									if (!strvaluetemp.equals(strvalue)) {
										break;
									} else {
										control[k][i] = 1;
										rowMerge++;
									}
								} catch (Exception Ex) {
									System.out.println(strProperty + "::"
											+ Ex.toString());
								}
							}
						}

						if (rows.type == 0) {
							sheet.addCell(new Label(i, j + headRowCount,
									strvalue, rows.fixcol ? rowHeadFormat
											: contentFormat)); // 第j行第i列
						} else {
							if (!strvalue.trim().equals("")) {

								try {
									WritableCellFormat format = getColourFormat(getFontColour(strvalue));

									sheet.addCell(new Number(i, j
											+ headRowCount, new Double(
											getLabelValue(strvalue))
											.doubleValue(),
											rows.fixcol ? rowHeadFormat
													: format)); // 第j行第i列
								} catch (Exception err) {
									System.out.println("strvalue:"
											+ err.getMessage());
									// 数据格式列却返回非数字内容,则当字符处理
									sheet.addCell(new Label(i,
											j + headRowCount,
											getLabelValue(strvalue),
											rows.fixcol ? rowHeadFormat
													: dataFormat));
								}
							} else {
								sheet.addCell(new Label(i, j + headRowCount,
										"", rows.fixcol ? rowHeadFormat
												: dataFormat));
							}
						}
						if (rowMerge > 0) {
							sheet.mergeCells(i, j + headRowCount, i, j
									+ headRowCount + rowMerge);
						}
						rowMerge = 0;
					}
				}
			}

			/************ 以上所写的内容都是写在缓存中的，下一句将缓存的内容写到文件中 *********/
			book.write();
			/*********** 关闭文件 **************/
			book.close();
			bos.close();
			wb.close();

		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				bos.close();
			} catch (Exception ex) {
			}
		}
	}

	private void writeReportHead() {
		System.out.println("====preparing to writeReportHead ......");
		int itemp = 0;

		int colnumber = captionResult.size();
		int headRowCount = 0;
		// 每列补齐数据
		for (int i = 0; i < captionResult.size(); i++) {
			ColStruct ColStructtemp = (ColStruct) captionResult.get(i);
			if (ColStructtemp.caption.size() > headRowCount) {
				headRowCount = ColStructtemp.caption.size();
			}
		}
		for (int i = 0; i < captionResult.size(); i++) {
			ColStruct ColStructtemp = (ColStruct) captionResult.get(i);
			for (int j = 0; j < headRowCount - ColStructtemp.caption.size(); j++) {
				ColStructtemp.caption.add(ColStructtemp.caption
						.get(ColStructtemp.caption.size() - 1));
			}
		}
		int control[][] = new int[colnumber][headRowCount];

		if (headRowCount > 0) {
			// 生成行头开始
			boolean rowunit = false;
			boolean colunit = false;

			try {
				for (int i = 0; i < headRowCount; i++) {
					for (int j = 0; j < colnumber; j++) {
						// 计算是否合并
						rowunit = false;
						colunit = false;
						itemp = 0;
						if (control[j][i] == 0) {
							// 计算横向合并
							for (int k = j + 1; k < colnumber; k++) {
								if (((ColStruct) captionResult.get(j)).caption
										.get(i)
										.equals(((ColStruct) captionResult
												.get(k)).caption.get(i))) {
									rowunit = true;
									itemp = itemp + 1;
								} else
									break;
							}
							// 如果横向不合并时，计算是否纵向合并
							if (rowunit == false) {
								for (int k = i + 1; k < headRowCount; k++) {
									if (((ColStruct) captionResult.get(j)).caption
											.get(i).equals(
													((ColStruct) captionResult
															.get(j)).caption
															.get(k))) {
										control[j][k] = 1;
										colunit = true;
										itemp = itemp + 1;
									}
								}
							}

							String value = String
									.valueOf(((ColStruct) captionResult.get(j)).caption
											.get(i));
							boolean fixcol = ((ColStruct) captionResult.get(j)).fixcol;
							if (fixcol) {
								sheet.addCell(new Label(j, i, value, headFormat));
							} else {
								sheet.addCell(new Label(j, i, value,
										colHeadFormat));
							}
							if (rowunit == true) {
								control[j][i] = 1;
								sheet.mergeCells(j, i, j + itemp, i);
								j = j + itemp;
							}
							if (colunit == true) {
								control[j][i] = 1;
								sheet.mergeCells(j, i, j, i + itemp);
							}

						}
					}
				}
			} catch (Exception ex) {
				System.out.println("writeReportHead error::" + ex.getMessage());
			}
		}
	}

	private List createCaption(List caption) {
		System.out.println("====preparing to createCaption ......");
		int colnumber = 0;
		int headRowCount = 0;
		boolean fixcol = true;
		// 读取符合条件的caption
		List caption_list = new ArrayList();
		// ArrayList caption_Temp = new ArrayList();
		for (int i = 0; i < caption.size(); i++) {
			Object rows = caption.get(i);
			String strCaption = null;
			try {
				strCaption = MethodFactory.getThisString(PropertyUtils
						.getProperty(rows, "caption"));
				strCaption = getStringValue(strCaption, 0);
			} catch (Exception ex) {
				strCaption = "";
			}
			String property = "";
			try {
				property = MethodFactory.getThisString(PropertyUtils
						.getProperty(rows, "property"));
				property = getStringValue(property, 0);
			} catch (Exception ex) {
				property = "";
			}
			String width = null;
			try {
				width = MethodFactory.getThisString(PropertyUtils.getProperty(
						rows, "width"));
				width = getStringValue(width, 0);
			} catch (Exception ex) {
				width = "";
			}
			String strPound = null;
			try {
				strPound = MethodFactory.getThisString(PropertyUtils
						.getProperty(rows, "pound"));
				strPound = getStringValue(strPound, 0);
			} catch (Exception ex) {
				strPound = "0";
			}
			String fix = null;
			try {
				fix = MethodFactory.getThisString(PropertyUtils.getProperty(
						rows, "fixcol"));
				fix = getStringValue(fix, 1);
			} catch (Exception ex) {
				fix = "";
			}
			String type = null;
			try {
				type = MethodFactory.getThisString(PropertyUtils.getProperty(
						rows, "datatype"));
				type = getStringValue(type, 1);
			} catch (Exception ex) {
				type = "0";
			}
			if (!(strCaption == null || strCaption.equals("") || width == null || width
					.equals("0"))) {
				colnumber = colnumber + 1;
				String strtemp[] = MethodFactory.split(strCaption, "##");
				if (strtemp.length > headRowCount) {
					headRowCount = strtemp.length;
				}
				ColStruct ColStructtemp = new ColStruct();
				for (int j = 0; j < strtemp.length; j++) {
					ColStructtemp.caption.add(strtemp[j]);
				}
				ColStructtemp.property = property;
				ColStructtemp.width = width;
				ColStructtemp.pound = new Integer(strPound).intValue();
				if (fix != null
						&& fixcol == true
						&& (fix.trim().toLowerCase().equals("true") || fix
								.trim().equals("1"))) {
					ColStructtemp.fixcol = true;
				} else {
					ColStructtemp.fixcol = false;
					fixcol = false;
				}
				if (type != null) {
					if (type.equals("1")) {
						/* 数子 */
						ColStructtemp.type = 1;
					} else {
						ColStructtemp.type = 0;
					}
				} else {
					type = "0";
				}

				caption_list.add(ColStructtemp);
			}
		}
		return caption_list;
	}

	private String getStringValue(String value, int type) {
		try {
			value = MethodFactory.replace(value, "&", "&amp;");
			value = MethodFactory.replace(value, "<", "&lt;");
			value = MethodFactory.replace(value, ">", "&gt;");
			String valueTemp = getStyleId(value, "");
			if (!value.equals("") && type == 1) {
				value = MethodFactory.replace(value, "[style-" + valueTemp
						+ "]", "");
			}
		} catch (Exception ex) {
		}
		return value;
	}

	private String getFontColour(String value) {

		return RegxStrReplace(value, "<[^>]*color=['\"]*([a-z]*)['\"]*.*>",
				"$1");
	}

	private WritableCellFormat getColourFormat(String value) {

		if (formats.containsKey(value)) {
			// System.out.println(value+":::::1");
			return (WritableCellFormat) formats.get(value);
		} else {
			// System.out.println(value+":::::2");
			return dataFormat;
		}

	}

	private String getLabelValue(String value) {

		return RegxStrReplace(value, "<[^>]*>([^<]*)<[^>]*>", "$1");

	}

	public static String RegxStrReplace(String content, String oldRegex,
			String newRegex) {
		Pattern p = Pattern.compile(oldRegex, Pattern.CASE_INSENSITIVE); // 在查找时忽略大小写
		// 用Pattern类的matcher()方法生成一个Matcher对象
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		// 使用find()方法查找第一个匹配的对象
		boolean result = m.find();
		// 使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
		while (result) {
			i++;
			m.appendReplacement(sb, newRegex);
			// System.out.println("第"+i+"次匹配后sb的内容是："+sb);
			// 继续查找下一个匹配对象
			result = m.find();
		}
		// 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
		m.appendTail(sb);
		return sb.toString();
	}

	private String getStyleId(String Value, String DefaultId) {
		String returnValue = DefaultId;
		String styleValue = "[style-";
		/* [style-s02] */
		if (Value.length() > 7) {
			String stylevalue = Value.substring(0, 7);
			try {
				if (stylevalue.toLowerCase().equals(styleValue)) {
					int index = Value.indexOf("]");
					stylevalue = Value.substring(0, index + 1);
					stylevalue = MethodFactory.replace(stylevalue, "[style-",
							"");
					stylevalue = MethodFactory.replace(stylevalue, "]", "");
					returnValue = stylevalue;
				}
			} catch (Exception ex) {
				returnValue = DefaultId;
			}
		}
		return returnValue;
	}

	private class ColStruct {
		public ColStruct() {
		}

		public String property = null;
		public String width = null;
		public int pound = 0; // 磅,专用于导出报表时列宽
		public ArrayList caption = new ArrayList();
		public boolean fixcol = false;
		public int colnumber = 0;
		public int type = 0;
	}

	private String getfilename() {
		String returnvalue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		// 年
		String stryear = String.valueOf(calendar.get(Calendar.YEAR));
		// 月
		String strmonth = "00"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
		strmonth = strmonth.substring(strmonth.length() - 2, strmonth.length());
		// 日
		String strday = "00"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strday = strday.substring(strday.length() - 2, strday.length());
		// 时
		String strhour = "00"
				+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		strhour = strhour.substring(strhour.length() - 2, strhour.length());
		// 分
		String strminute = "00" + String.valueOf(calendar.get(Calendar.MINUTE));
		strminute = strminute.substring(strminute.length() - 2,
				strminute.length());
		// 秒
		String strsecond = "00" + String.valueOf(calendar.get(Calendar.SECOND));
		strsecond = strsecond.substring(strsecond.length() - 2,
				strsecond.length());
		// 毫秒
		String strmsecond = "00"
				+ String.valueOf(calendar.get(Calendar.MILLISECOND));
		strmsecond = strmsecond.substring(strmsecond.length() - 2,
				strmsecond.length());
		returnvalue = stryear + strmonth + strday + strhour + strminute
				+ strsecond + strmsecond;
		return returnvalue;
	}

	public String readReportTemplatePath() {
		InputStream is = getClass().getResourceAsStream("/taglib.properties");
		Properties dbProps = new Properties();
		try {
			dbProps.load(is);
		} catch (Exception e) {
			System.err.println("不能读取属性文件. "
					+ "请确保taglib.properties在CLASSPATH指定的路径中");
			return "";
		}
		String repositorypath = dbProps.getProperty("reportpath", "c:/");

		return repositorypath;
	}

	public int getPageCols() {
		return pageCols;
	}

	public void setPageCols(int pageCols) {
		this.pageCols = pageCols;
		columnAverage = true;
	}
}
