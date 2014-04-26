package com.common.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.zl.util.MethodFactory;

//自动分页,会出现断裂感觉,效果不佳

public class PDFReport {

	protected String title = ""; // 台头名称
	protected List captionResult = null; // 标题记录集
	protected List dataResult = null; // 内容记录集
	protected int colMergeCount = 1; // 合并列数,默认只合并第一列
	private int pageCols = 10; // 每页显示列数
	protected Rectangle paperType = PageSize.A4.rotate(); // 纸型
	protected Document document = null;
	protected FileOutputStream out = null;
	protected BaseFont bfChinese = null;
	protected Font fontChinese = null;
	private int fixCols = 0;

	protected Cell cell = null;
	protected Paragraph header = null;
	protected Paragraph prg = null;
	protected Table table = null;

	protected float paddingtop = 0;
	protected float paddingbottom = 0;
	protected float paddingleft = 0;
	protected float paddingright = 0;
	protected float fixedHeight = 0;

	public PDFReport(String title, List captionResult, List dataResult) {
		this.title = title;
		List caption_list = createCaption(captionResult);
		this.captionResult = caption_list;
		this.dataResult = dataResult;
	}

	private void initColumnWidth() {

	}

	private List createCaption(List caption) {

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
		public String width = null; // name
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

	private PdfPCell makeCell(String innertext, Font font, int Halig,
			int Valig, float[] line, int Height, int Colspan, int flag) {
		PdfPCell Cell = new PdfPCell(new Phrase(innertext, font));

		if (Colspan != 0) {
			Cell.setColspan(Colspan);
		}

		// cell
		Rectangle Border = new Rectangle(0, 0);
		Border.setBorderWidthTop(line[0]);
		Border.setBorderWidthBottom(line[1]);
		Border.setBorderWidthLeft(line[2]);
		Border.setBorderWidthRight(line[3]);
		Border.setBorderColor(Color.black);
		Cell.cloneNonPositionParameters(Border);
		// 行高
		Cell.setFixedHeight(Height);
		// cell内容
		Cell.setHorizontalAlignment(Halig);
		Cell.setVerticalAlignment(Valig);
		Cell.setPaddingTop(paddingtop);
		Cell.setPaddingBottom(paddingbottom);
		Cell.setPaddingLeft(paddingleft);
		Cell.setPaddingRight(paddingright);
		return Cell;
	}

	public void writeDataToPDF(String inputDate, OutputStream bos) {
		if (captionResult == null || dataResult == null)
			return;
		int intShowColCount = captionResult.size();
		if (intShowColCount == 0) {
			return;
		} else {
			try {
				// System.out.println("attachment; filename="+title+inputDate+".pdf");
				// response.setContentType("application/pdf");
				// response.setHeader("Content-disposition","attachment;filename="+this.getfilename()+".xls");
				// bos =response.getOutputStream();// new
				// FileOutputStream(file);

				try {
					bfChinese = BaseFont.createFont("STSong-Light",
							"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				writeData(bos);
			} catch (Exception ex) {

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
		try {

			fixCols = 0;
			int headRowCount = 0;
			int totalCols = captionResult.size();
			System.out.println("totalCols:" + totalCols);
			// 计算固定行,补齐每列数据
			for (int i = 0; i < totalCols; i++) {
				ColStruct ColStructtemp = (ColStruct) captionResult.get(i);
				if (ColStructtemp.caption.size() > headRowCount) {
					headRowCount = ColStructtemp.caption.size();
				}
				if (ColStructtemp.fixcol) {
					fixCols++;
				}
			}
			System.out.println("fixCols:" + fixCols);
			document = new Document(paperType);
			PdfWriter.getInstance(document, bos);
			document.open();

			// 计算横向拆分页数
			int hPages = (totalCols - fixCols) / (pageCols - fixCols);
			if (totalCols - fixCols > hPages * (pageCols - fixCols))
				hPages++;
			System.out.println("hPages:" + hPages);
			for (int n = 0; n < hPages; n++) {
				// define table
				int thisPageCols = n < (hPages - 1) ? pageCols
						: (totalCols - (hPages - 1) * (pageCols - fixCols));
				float[] widths = new float[thisPageCols];
				for (int i = 0; i < thisPageCols; i++) {
					widths[i] = 1.00f / pageCols;
					System.out.println("widths[" + i + "]:" + widths[i]);
				}
				/*
				 * for(int i=0;i<fixCols;i++){ widths[i]=new
				 * Float(((ColStruct)captionResult.get(i)).width).floatValue();
				 * } for(int i=fixCols;i<pageCols-fixCols;i++){ widths[i]=new
				 * Float
				 * (((ColStruct)captionResult.get(n*(pageCols-fixCols)+fixCols
				 * +i)).width).floatValue(); }
				 */
				if (n > 0)
					document.newPage();

				header = new PDFParagragh(title, Element.ALIGN_CENTER, 12);
				document.add(header);

				// new 一个pageCols列的table
				// PdfPTable table = new PdfPTable(pageCols);
				Table table = new Table(thisPageCols);
				// 设置table每一列的宽度，widths里写的是百分比，他们加和需要是1
				table.setWidths(widths);

				// 设置表格在页面上的宽度，设成100表示可以表格填满页面，但是要去掉页面margin
				table.setWidth(thisPageCols * 100 / pageCols);

				List pageCaptionList = new ArrayList();
				for (int i = 0; i < fixCols; i++) {
					pageCaptionList.add(captionResult.get(i));
				}
				for (int i = 0; i < thisPageCols - fixCols; i++) {
					pageCaptionList.add(captionResult.get(n
							* (pageCols - fixCols) + fixCols + i));
				}

				writeReportHead(table, pageCaptionList, thisPageCols);

				String strvalue = "";
				String strProperty = "";
				int rowMerge = 0;

				int control[][] = new int[dataResult.size()][pageCaptionList
						.size()];
				for (int j = 0; j < dataResult.size(); j++) {
					Object obj = dataResult.get(j);

					for (int i = 0; i < pageCaptionList.size(); i++) {
						if (control[j][i] != 1) {
							ColStruct rows = (ColStruct) pageCaptionList.get(i);

							try {
								strProperty = rows.property;
							} catch (Exception ex) {
								strProperty = "";
							}
							try {
								strvalue = MethodFactory
										.getThisString(PropertyUtils
												.getProperty(obj,
														strProperty.trim()));
							} catch (Exception Ex) {
								System.out.println(strProperty + "::"
										+ Ex.toString());
								strvalue = "";
							}
							if (i < colMergeCount) {
								for (int k = j + 1; k < dataResult.size(); k++) {
									try {
										obj = dataResult.get(k);
										String strvaluetemp = MethodFactory
												.getThisString(PropertyUtils
														.getProperty(obj,
																strProperty
																		.trim()));
										if (!strvaluetemp.equals(strvalue)) {
											break;
										} else {
											control[k][i] = 1;
											rowMerge++;
										}
									} catch (Exception Ex) {
										System.out.println(strProperty + "::"
												+ Ex.toString());
										strvalue = "";
									}
								}
							}

							if (rows.type == 0) {
								table.addCell(new PDFCell(strvalue,
										rowMerge + 1, 1, Element.ALIGN_LEFT,
										Color.GRAY));
							} else {
								if (!strvalue.trim().equals("")) {
									table.addCell(new NumberCell(strvalue,
											rowMerge + 1, 1,
											Element.ALIGN_RIGHT));

								} else {
									table.addCell(new PDFCell(strvalue,
											rowMerge + 1, 1,
											Element.ALIGN_RIGHT,
											rows.fixcol ? Color.GRAY
													: Color.WHITE));
								}
							}

							rowMerge = 0;
						}
					}
				}
				document.add(table);

			}

			document.close();

		} catch (Exception ex) {
			throw ex;
		} finally {

		}
	}

	private void writeReportHead(Table table, List pageCaptionList,
			int thisPageCols) {
		int itemp = 0;

		int colnumber = pageCaptionList.size();
		int headRowCount = 0;
		// 每列补齐数据
		for (int i = 0; i < pageCaptionList.size(); i++) {
			ColStruct ColStructtemp = (ColStruct) pageCaptionList.get(i);
			if (ColStructtemp.caption.size() > headRowCount) {
				headRowCount = ColStructtemp.caption.size();
			}
		}
		for (int i = 0; i < pageCaptionList.size(); i++) {
			ColStruct ColStructtemp = (ColStruct) pageCaptionList.get(i);
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
								if (((ColStruct) pageCaptionList.get(j)).caption
										.get(i)
										.equals(((ColStruct) pageCaptionList
												.get(k)).caption.get(i))) {
									rowunit = true;
									itemp = itemp + 1;
								} else
									break;
							}
							// 如果横向不合并时，计算是否纵向合并
							if (rowunit == false) {
								for (int k = i + 1; k < headRowCount; k++) {
									if (((ColStruct) pageCaptionList.get(j)).caption
											.get(i)
											.equals(((ColStruct) pageCaptionList
													.get(j)).caption.get(k))) {
										control[j][k] = 1;
										colunit = true;
										itemp = itemp + 1;
									}
								}
							}
							String value = String
									.valueOf(((ColStruct) pageCaptionList
											.get(j)).caption.get(i));
							boolean fixcol = ((ColStruct) pageCaptionList
									.get(j)).fixcol;

							if (rowunit == true) {
								control[j][i] = 1;
								table.addCell(new PDFCell(value, 1, itemp + 1,
										1, Color.GRAY));
								j = j + itemp;
							}
							if (colunit == true) {
								control[j][i] = 1;
								table.addCell(new PDFCell(value, itemp + 1, 1,
										1, Color.GRAY));
							}
							if (!rowunit && !colunit) {
								table.addCell(new PDFCell(value, 1, 1, 1,
										Color.GRAY));
							}

						}
					}

				}
				table.endHeaders();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public int getPageCols() {
		return pageCols;
	}

	public void setPageCols(int pageCols) {
		this.pageCols = pageCols;
	}

	public Rectangle getPaperType() {
		return paperType;
	}

	public void setPaperType(Rectangle paperType) {
		this.paperType = paperType;
	}

}
