package com.qmx.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.font.TextAttribute;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintEllipse;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRStringUtil;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRStyledTextParser;

import org.xml.sax.SAXException;

public class ZLHtmlExporter extends JRHtmlExporter {
	protected static final String JR_PAGE_ANCHOR_PREFIX = "JR_PAGE_ANCHOR_";
	protected static final String CSS_TEXT_ALIGN_LEFT = "left";
	protected static final String CSS_TEXT_ALIGN_RIGHT = "right";
	protected static final String CSS_TEXT_ALIGN_CENTER = "center";
	protected static final String CSS_TEXT_ALIGN_JUSTIFY = "justify";
	protected static final String HTML_VERTICAL_ALIGN_TOP = "top";
	protected static final String HTML_VERTICAL_ALIGN_MIDDLE = "middle";
	protected static final String HTML_VERTICAL_ALIGN_BOTTOM = "bottom";
	protected Writer writer = null;
	protected JRExportProgressMonitor progressMonitor = null;
	protected Map rendererToImagePathMap = null;
	protected Map imageNameToImageDataMap = null;

	protected int reportIndex = 0;

	protected JRFont defaultFont = null;

	protected JRStyledTextParser styledTextParser = new JRStyledTextParser();

	protected File imagesDir = null;
	protected String imagesURI = null;
	protected boolean isOutputImagesToDir = false;
	protected boolean isRemoveEmptySpace = false;
	protected boolean isWhitePageBackground = true;
	protected String encoding = null;

	protected String htmlHeader = null;
	protected String betweenPagesHtml = null;
	protected String htmlFooter = null;

	protected ZLHtmlExporter.StringProvider emptyCellStringProvider = null;

	protected static final int colorMask = Integer.parseInt("FFFFFF", 16);

	protected JRExporterGridCell[][] grid = null;
	protected boolean[] isRowNotEmpty = null;
	protected List xCuts = null;
	protected List yCuts = null;

	protected boolean isWrapBreakWord = false;

	protected JRFont getDefaultFont() {
		if (this.defaultFont == null) {
			this.defaultFont = this.jasperPrint.getDefaultFont();
			if (this.defaultFont == null) {
				this.defaultFont = new JRBaseFont();
			}
		}

		return this.defaultFont;
	}

	public void exportReport() throws JRException {
		this.progressMonitor = ((JRExportProgressMonitor) this.parameters
				.get(JRExporterParameter.PROGRESS_MONITOR));

		setOffset();

		setInput();

		if (!this.isModeBatch) {
			setPageRange();
		}

		this.htmlHeader = ((String) this.parameters
				.get(JRHtmlExporterParameter.HTML_HEADER));
		this.betweenPagesHtml = ((String) this.parameters
				.get(JRHtmlExporterParameter.BETWEEN_PAGES_HTML));
		this.htmlFooter = ((String) this.parameters
				.get(JRHtmlExporterParameter.HTML_FOOTER));

		this.imagesDir = ((File) this.parameters
				.get(JRHtmlExporterParameter.IMAGES_DIR));
		if (this.imagesDir == null) {
			String dir = (String) this.parameters
					.get(JRHtmlExporterParameter.IMAGES_DIR_NAME);
			if (dir != null) {
				this.imagesDir = new File(dir);
			}
		}

		Boolean isRemoveEmptySpaceParameter = (Boolean) this.parameters
				.get(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS);
		if (isRemoveEmptySpaceParameter != null) {
			this.isRemoveEmptySpace = isRemoveEmptySpaceParameter
					.booleanValue();
		}

		Boolean isWhitePageBackgroundParameter = (Boolean) this.parameters
				.get(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND);
		if (isWhitePageBackgroundParameter != null) {
			this.isWhitePageBackground = isWhitePageBackgroundParameter
					.booleanValue();
		}

		Boolean isOutputImagesToDirParameter = (Boolean) this.parameters
				.get(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR);
		if (isOutputImagesToDirParameter != null) {
			this.isOutputImagesToDir = isOutputImagesToDirParameter
					.booleanValue();
		}

		String uri = (String) this.parameters
				.get(JRHtmlExporterParameter.IMAGES_URI);
		if (uri != null) {
			this.imagesURI = uri;
		}

		this.encoding = ((String) this.parameters
				.get(JRExporterParameter.CHARACTER_ENCODING));
		if (this.encoding == null) {
			this.encoding = "UTF-8";
		}

		System.out
				.println("---------------ZLHtmlExporter--encoding-Row175-----------------");

		this.rendererToImagePathMap = new HashMap();

		this.imageNameToImageDataMap = ((Map) this.parameters
				.get(JRHtmlExporterParameter.IMAGES_MAP));
		if (this.imageNameToImageDataMap == null) {
			this.imageNameToImageDataMap = new HashMap();
		}

		Boolean isWrapBreakWordParameter = (Boolean) this.parameters
				.get(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD);
		if (isWrapBreakWordParameter != null) {
			this.isWrapBreakWord = isWrapBreakWordParameter.booleanValue();
		}

		Boolean isUsingImagesToAlignParameter = (Boolean) this.parameters
				.get(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN);
		if (isUsingImagesToAlignParameter == null) {
			isUsingImagesToAlignParameter = Boolean.TRUE;
		}

		// if (isUsingImagesToAlignParameter.booleanValue())
		// {
		// this.emptyCellStringProvider =
		// new ZLHtmlExporter.1(this);
		//
		// loadPxImage();
		// }
		// else
		// {
		// this.emptyCellStringProvider =
		// new ZLHtmlExporter.2(this);
		// }
		if (isUsingImagesToAlignParameter.booleanValue()) {
			emptyCellStringProvider = new StringProvider() {

				public String getStringForCollapsedTD(Object value) {
					return (new StringBuilder("><img src=\"")).append(value)
							.append("px\"").toString();
				}

				public String getStringForEmptyTD(Object value) {
					return (new StringBuilder("<img src=\"")).append(value)
							.append("px\" border=0>").toString();
				}

			};
			loadPxImage();
		} else {
			emptyCellStringProvider = new StringProvider() {

				public String getStringForCollapsedTD(Object value) {
					return "";
				}

				public String getStringForEmptyTD(Object value) {
					return "";
				}

			};
		}
		StringBuffer sb = (StringBuffer) this.parameters
				.get(JRExporterParameter.OUTPUT_STRING_BUFFER);
		if (sb != null) {
			try {
				this.writer = new StringWriter();
				exportReportToWriter();
				sb.append(this.writer.toString());
			} catch (IOException e) {
				throw new JRException("Error writing to StringBuffer writer : "
						+ this.jasperPrint.getName(), e);
			} finally {
				if (this.writer != null) {
					try {
						this.writer.close();
					} catch (IOException localIOException1) {
					}
				}
			}
			try {
				this.writer.close();
			} catch (IOException localIOException2) {
			}

		} else {
			this.writer = ((Writer) this.parameters
					.get(JRExporterParameter.OUTPUT_WRITER));
			if (this.writer != null) {
				try {
					exportReportToWriter();
				} catch (IOException e) {
					throw new JRException("Error writing to writer : "
							+ this.jasperPrint.getName(), e);
				}
			} else {
				OutputStream os = (OutputStream) this.parameters
						.get(JRExporterParameter.OUTPUT_STREAM);
				if (os != null) {
					try {
						this.writer = new OutputStreamWriter(os, this.encoding);
						exportReportToWriter();
					} catch (IOException e) {
						throw new JRException(
								"Error writing to OutputStream writer : "
										+ this.jasperPrint.getName(), e);
					}
				} else {
					File destFile = (File) this.parameters
							.get(JRExporterParameter.OUTPUT_FILE);
					if (destFile == null) {
						String fileName = (String) this.parameters
								.get(JRExporterParameter.OUTPUT_FILE_NAME);
						if (fileName != null) {
							destFile = new File(fileName);
						} else {
							throw new JRException(
									"No output specified for the exporter.");
						}
					}

					try {
						os = new FileOutputStream(destFile);
						this.writer = new OutputStreamWriter(os, this.encoding);
					} catch (IOException e) {
						throw new JRException(
								"Error creating to file writer : "
										+ this.jasperPrint.getName(), e);
					}

					if (this.imagesDir == null) {
						this.imagesDir = new File(destFile.getParent(),
								destFile.getName() + "_files");
					}

					if (isOutputImagesToDirParameter == null) {
						this.isOutputImagesToDir = true;
					}

					if (this.imagesURI == null) {
						this.imagesURI = (this.imagesDir.getName() + "/");
					}

					try {
						exportReportToWriter();
					} catch (IOException e) {
						throw new JRException("Error writing to file writer : "
								+ this.jasperPrint.getName(), e);
					} finally {
						if (this.writer != null) {
							try {
								this.writer.close();
							} catch (IOException localIOException3) {
							}
						}
					}
				}
			}
		}

		if (this.isOutputImagesToDir) {
			if (this.imagesDir == null) {
				throw new JRException(
						"The images directory was not specified for the exporter.");
			}

			Collection imageNames = this.imageNameToImageDataMap.keySet();
			if ((imageNames != null) && (imageNames.size() > 0)) {
				if (!this.imagesDir.exists()) {
					this.imagesDir.mkdir();
				}

				for (Iterator it = imageNames.iterator(); it.hasNext();) {
					String imageName = (String) it.next();
					byte[] imageData = (byte[]) this.imageNameToImageDataMap
							.get(imageName);

					File imageFile = new File(this.imagesDir, imageName);
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(imageFile);
						fos.write(imageData, 0, imageData.length);
					} catch (IOException e) {
						throw new JRException("Error writing to image file : "
								+ imageFile, e);
					} finally {
						if (fos != null) {
							try {
								fos.close();
							} catch (IOException localIOException5) {
							}
						}
					}
				}
			}
		}
	}

	protected void exportReportToWriter() throws JRException, IOException {
		if (this.htmlHeader == null) {
			this.writer.write("<html>\n");
			this.writer.write("<head>\n");
			this.writer
					.write("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset="
							+ this.encoding + "\">\n");

			this.writer.write("</head>\n");

			this.writer.write("\n");
		} else {
			this.writer.write(this.htmlHeader);
		}

		for (this.reportIndex = 0; this.reportIndex < this.jasperPrintList
				.size(); this.reportIndex += 1) {
			this.jasperPrint = ((JasperPrint) this.jasperPrintList
					.get(this.reportIndex));
			this.defaultFont = null;

			List pages = this.jasperPrint.getPages();
			if ((pages != null) && (pages.size() > 0)) {
				if (this.isModeBatch) {
					this.startPageIndex = 0;
					this.endPageIndex = (pages.size() - 1);
				}

				JRPrintPage page = null;
				for (int pageIndex = this.startPageIndex; pageIndex <= this.endPageIndex; pageIndex++) {
					if (Thread.currentThread().isInterrupted()) {
						throw new JRException("Current thread interrupted.");
					}

					page = (JRPrintPage) pages.get(pageIndex);

					exportPage(page);
					if (pageIndex != this.endPageIndex) {
						if (this.betweenPagesHtml == null) {
							this.writer.write("<br>\n<br>\n");
						} else {
							this.writer.write(this.betweenPagesHtml);
						}
					}
					this.writer.write("\n");
				}
			}
		}

		if (this.htmlFooter == null) {
			this.writer.write("</body>\n");
			this.writer.write("</html>\n");
		} else {
			this.writer.write(this.htmlFooter);
		}

		this.writer.flush();
	}

	protected void exportPage(JRPrintPage page) throws JRException, IOException {
		writer.write((new StringBuilder("<table width="))
				.append(jasperPrint.getPageWidth())
				.append(" cellpadding=0 cellspacing=0 border=0 align=\"center\"\n")
				.toString());
		if (isWhitePageBackground)
			writer.write(" bgcolor=white");
		writer.write(">\n");
		layoutGrid(page);
		writer.write("<tr>\n");
		int width = 0;
		for (int i = 1; i < xCuts.size(); i++) {
			width = ((Integer) xCuts.get(i)).intValue()
					- ((Integer) xCuts.get(i - 1)).intValue();
			writer.write((new StringBuilder("  <td"))
					.append(emptyCellStringProvider
							.getStringForCollapsedTD(imagesURI))
					.append(" width=").append(width)
					.append(" height=1></td>\n").toString());
		}

		writer.write("</tr>\n");
		JRPrintElement element = null;
		for (int y = 0; y < grid.length; y++)
			if (isRowNotEmpty[y] || !isRemoveEmptySpace) {
				writer.write("<tr valign=top>\n");
				int emptyCellColSpan = 0;
				int emptyCellWidth = 0;
				int lastRowHeight = grid[y][0].height;
				for (int x = 0; x < grid[y].length; x++)
					if (grid[y][x].element != null) {
						if (emptyCellColSpan > 0) {
							writer.write("  <td");
							if (emptyCellColSpan > 1)
								writer.write((new StringBuilder(" colspan="))
										.append(emptyCellColSpan).toString());
							writer.write((new StringBuilder(
									String.valueOf(emptyCellStringProvider
											.getStringForCollapsedTD(imagesURI))))
									.append(" width=").append(emptyCellWidth)
									.append(" height=").append(lastRowHeight)
									.append("></td>\n").toString());
							emptyCellColSpan = 0;
							emptyCellWidth = 0;
						}
						element = grid[y][x].element;
						if (element instanceof JRPrintLine)
							exportLine((JRPrintLine) element, grid[y][x]);
						else if (element instanceof JRPrintRectangle)
							exportRectangle(element, grid[y][x]);
						else if (element instanceof JRPrintEllipse)
							exportRectangle(element, grid[y][x]);
						else if (element instanceof JRPrintImage)
							exportImage((JRPrintImage) element, grid[y][x]);
						else if (element instanceof JRPrintText)
							exportText((JRPrintText) element, grid[y][x]);
						x += grid[y][x].colSpan - 1;
					} else {
						emptyCellColSpan++;
						emptyCellWidth += grid[y][x].width;
					}

				if (emptyCellColSpan > 0) {
					writer.write("  <td");
					if (emptyCellColSpan > 1)
						writer.write((new StringBuilder(" colspan=")).append(
								emptyCellColSpan).toString());
					writer.write((new StringBuilder(String
							.valueOf(emptyCellStringProvider
									.getStringForCollapsedTD(imagesURI))))
							.append(" width=").append(emptyCellWidth)
							.append(" height=").append(lastRowHeight)
							.append("></td>\n").toString());
				}
				writer.write("</tr>\n");
			}

		writer.write("</table>\n");
		if (progressMonitor != null)
			progressMonitor.afterPageExport();
	}

	// protected void exportPage(JRPrintPage page) throws JRException,
	// IOException {
	// this.writer.write("<table width=" + this.jasperPrint.getPageWidth()
	// + " cellpadding=0 cellspacing=0 border=0 align=\"center\"\n");
	// if (this.isWhitePageBackground) {
	// this.writer.write(" bgcolor=white");
	// }
	// this.writer.write(">\n");
	//
	// layoutGrid(page);
	//
	// this.writer.write("<tr>\n");
	// int width = 0;
	// for (int i = 1; i < this.xCuts.size(); i++) {
	// width = ((Integer) this.xCuts.get(i)).intValue()
	// - ((Integer) this.xCuts.get(i - 1)).intValue();
	// this.writer.write("  <td"
	// + this.emptyCellStringProvider
	// .getStringForCollapsedTD(this.imagesURI)
	// + " width=" + width + " height=1></td>\n");
	// }
	// this.writer.write("</tr>\n");
	//
	// JRPrintElement element = null;
	// for (int y = 0; y < this.grid.length; y++) {
	// if ((this.isRowNotEmpty[y] != 0) || (!this.isRemoveEmptySpace)) {
	// this.writer.write("<tr valign=top>\n");
	//
	// int emptyCellColSpan = 0;
	// int emptyCellWidth = 0;
	// int lastRowHeight = this.grid[y][0].height;
	//
	// for (int x = 0; x < this.grid[y].length; x++) {
	// if (this.grid[y][x].element != null) {
	// if (emptyCellColSpan > 0) {
	// this.writer.write("  <td");
	// if (emptyCellColSpan > 1) {
	// this.writer.write(" colspan="
	// + emptyCellColSpan);
	// }
	// this.writer.write(this.emptyCellStringProvider
	// .getStringForCollapsedTD(this.imagesURI)
	// + " width="
	// + emptyCellWidth
	// + " height="
	// + lastRowHeight + "></td>\n");
	// emptyCellColSpan = 0;
	// emptyCellWidth = 0;
	// }
	//
	// element = this.grid[y][x].element;
	//
	// if ((element instanceof JRPrintLine)) {
	// exportLine((JRPrintLine) element, this.grid[y][x]);
	// } else if ((element instanceof JRPrintRectangle)) {
	// exportRectangle(element, this.grid[y][x]);
	// } else if ((element instanceof JRPrintEllipse)) {
	// exportRectangle(element, this.grid[y][x]);
	// } else if ((element instanceof JRPrintImage)) {
	// exportImage((JRPrintImage) element, this.grid[y][x]);
	// } else if ((element instanceof JRPrintText)) {
	// exportText((JRPrintText) element, this.grid[y][x]);
	// }
	//
	// x += this.grid[y][x].colSpan - 1;
	// } else {
	// emptyCellColSpan++;
	// emptyCellWidth += this.grid[y][x].width;
	// }
	// }
	//
	// if (emptyCellColSpan > 0) {
	// this.writer.write("  <td");
	// if (emptyCellColSpan > 1) {
	// this.writer.write(" colspan=" + emptyCellColSpan);
	// }
	// this.writer.write(this.emptyCellStringProvider
	// .getStringForCollapsedTD(this.imagesURI)
	// + " width="
	// + emptyCellWidth
	// + " height="
	// + lastRowHeight + "></td>\n");
	// }
	//
	// this.writer.write("</tr>\n");
	// }
	// }
	//
	// this.writer.write("</table>\n");
	//
	// if (this.progressMonitor != null) {
	// this.progressMonitor.afterPageExport();
	// }
	// }

	protected void exportLine(JRPrintLine line, JRExporterGridCell gridCell)
			throws IOException {
		this.writer.write("  <td");
		if (gridCell.colSpan > 1) {
			this.writer.write(" colspan=" + gridCell.colSpan);
		}
		if (gridCell.rowSpan > 1) {
			this.writer.write(" rowspan=" + gridCell.rowSpan);
		}

		if (line.getForecolor().getRGB() != Color.white.getRGB()) {
			this.writer.write(" bgcolor=#");
			String hexa = Integer.toHexString(
					line.getForecolor().getRGB() & colorMask).toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			this.writer.write(hexa);
		}

		this.writer.write(">");

		this.writer.write(this.emptyCellStringProvider
				.getStringForEmptyTD(this.imagesURI));

		this.writer.write("</td>\n");
	}

	protected void exportRectangle(JRPrintElement element,
			JRExporterGridCell gridCell) throws IOException {
		this.writer.write("  <td");
		if (gridCell.colSpan > 1) {
			this.writer.write(" colspan=" + gridCell.colSpan);
		}
		if (gridCell.rowSpan > 1) {
			this.writer.write(" rowspan=" + gridCell.rowSpan);
		}

		if ((element.getBackcolor().getRGB() != Color.white.getRGB())
				&& (element.getMode() == 1)) {
			this.writer.write(" bgcolor=#");
			String hexa = Integer.toHexString(
					element.getBackcolor().getRGB() & colorMask).toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			this.writer.write(hexa);
		}

		this.writer.write(">");

		this.writer.write(this.emptyCellStringProvider
				.getStringForEmptyTD(this.imagesURI));

		this.writer.write("</td>\n");
	}

	protected JRStyledText getStyledText(JRPrintText textElement) {
		JRStyledText styledText = null;

		String text = textElement.getText();
		if (text != null) {
			JRFont font = textElement.getFont();
			if (font == null) {
				font = getDefaultFont();
			}

			Map attributes = new HashMap();
			attributes.putAll(font.getAttributes());
			attributes
					.put(TextAttribute.FOREGROUND, textElement.getForecolor());
			if (textElement.getMode() == 1) {
				attributes.put(TextAttribute.BACKGROUND,
						textElement.getBackcolor());
			}

			if (textElement.isStyledText()) {
				try {
					styledText = this.styledTextParser.parse(attributes, text);
				} catch (SAXException localSAXException) {
				}

			}

			if (styledText == null) {
				styledText = new JRStyledText();
				styledText.append(text);
				styledText.addRun(new JRStyledText.Run(attributes, 0, text
						.length()));
			}
		}

		return styledText;
	}

	protected void exportStyledText(JRStyledText styledText) throws IOException {
		String text = styledText.getText();

		int runLimit = 0;

		AttributedCharacterIterator iterator = styledText.getAttributedString()
				.getIterator();

		while ((runLimit < styledText.length())
				&& ((runLimit = iterator.getRunLimit()) <= styledText.length())) {
			exportStyledTextRun(iterator.getAttributes(),
					text.substring(iterator.getIndex(), runLimit));

			iterator.setIndex(runLimit);
		}
	}

	protected void exportStyledTextRun(Map attributes, String text)
			throws IOException {
		this.writer.write("<span style=\"font-family: ");
		this.writer.write((String) attributes.get(TextAttribute.FAMILY));
		this.writer.write("; ");

		Color forecolor = (Color) attributes.get(TextAttribute.FOREGROUND);
		if (!Color.black.equals(forecolor)) {
			this.writer.write("color: #");
			String hexa = Integer.toHexString(forecolor.getRGB() & colorMask)
					.toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			this.writer.write(hexa);
			this.writer.write("; ");
		}

		Color backcolor = (Color) attributes.get(TextAttribute.BACKGROUND);
		if (backcolor != null) {
			this.writer.write("background-color: #");
			String hexa = Integer.toHexString(backcolor.getRGB() & colorMask)
					.toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			this.writer.write(hexa);
			this.writer.write("; ");
		}

		this.writer.write("font-size: ");
		this.writer.write(String.valueOf(attributes.get(TextAttribute.SIZE)));
		this.writer.write("px;");

		if (TextAttribute.WEIGHT_BOLD.equals(attributes
				.get(TextAttribute.WEIGHT))) {
			this.writer.write(" font-weight: bold;");
		}
		if (TextAttribute.POSTURE_OBLIQUE.equals(attributes
				.get(TextAttribute.POSTURE))) {
			this.writer.write(" font-style: italic;");
		}
		if (TextAttribute.UNDERLINE_ON.equals(attributes
				.get(TextAttribute.UNDERLINE))) {
			this.writer.write(" text-decoration: underline;");
		}
		if (TextAttribute.STRIKETHROUGH_ON.equals(attributes
				.get(TextAttribute.STRIKETHROUGH))) {
			this.writer.write(" text-decoration: line-through;");
		}

		this.writer.write("\">");

		this.writer.write(JRStringUtil.htmlEncode(text));

		this.writer.write("</span>");
	}

	protected void exportText(JRPrintText text, JRExporterGridCell gridCell)
			throws IOException {
		JRStyledText styledText = getStyledText(text);

		int textLength = 0;

		if (styledText != null) {
			textLength = styledText.length();
		}

		this.writer.write("  <td");
		if (gridCell.colSpan > 1) {
			this.writer.write(" colspan=" + gridCell.colSpan);
		}
		if (gridCell.rowSpan > 1) {
			this.writer.write(" rowspan=" + gridCell.rowSpan);
		}

		String verticalAlignment = "top";

		switch (text.getVerticalAlignment()) {
		case 3:
			verticalAlignment = "bottom";
			break;
		case 2:
			verticalAlignment = "middle";
			break;
		case 1:
		default:
			verticalAlignment = "top";
		}

		if (!verticalAlignment.equals("top")) {
			this.writer.write(" valign=\"");
			this.writer.write(verticalAlignment);
			this.writer.write("\"");
		}

		if (text.getRunDirection() == 1) {
			this.writer.write(" dir=\"rtl\"");
		}

		StringBuffer styleBuffer = new StringBuffer();

		if ((text.getBackcolor().getRGB() != Color.white.getRGB())
				&& (text.getMode() == 1)) {
			styleBuffer.append("background-color: #");
			String hexa = Integer.toHexString(
					text.getBackcolor().getRGB() & colorMask).toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			styleBuffer.append(hexa);
			styleBuffer.append("; ");
		}

		if (text.getBox() != null) {
			appendBorder(styleBuffer, text.getBox().getTopBorder(), text
					.getBox().getTopBorderColor() == null ? text.getForecolor()
					: text.getBox().getTopBorderColor(), text.getBox()
					.getTopPadding(), "top");

			appendBorder(
					styleBuffer,
					text.getBox().getLeftBorder(),
					text.getBox().getLeftBorderColor() == null ? text
							.getForecolor() : text.getBox()
							.getLeftBorderColor(), text.getBox()
							.getLeftPadding(), "left");

			appendBorder(
					styleBuffer,
					text.getBox().getBottomBorder(),
					text.getBox().getBottomBorderColor() == null ? text
							.getForecolor() : text.getBox()
							.getBottomBorderColor(), text.getBox()
							.getBottomPadding(), "bottom");

			appendBorder(
					styleBuffer,
					text.getBox().getRightBorder(),
					text.getBox().getRightBorderColor() == null ? text
							.getForecolor() : text.getBox()
							.getRightBorderColor(), text.getBox()
							.getRightPadding(), "right");
		}

		String horizontalAlignment = "left";

		if (textLength > 0) {
			switch (text.getHorizontalAlignment()) {
			case 3:
				horizontalAlignment = "right";
				break;
			case 2:
				horizontalAlignment = "center";
				break;
			case 4:
				horizontalAlignment = "justify";
				break;
			case 1:
			default:
				horizontalAlignment = "left";
			}

			if (((text.getRunDirection() == 0) && (!horizontalAlignment
					.equals("left")))
					|| ((text.getRunDirection() == 1) && (!horizontalAlignment
							.equals("right")))) {
				styleBuffer.append("text-align: ");
				styleBuffer.append(horizontalAlignment);
				styleBuffer.append(";");
			}
		}

		if (this.isWrapBreakWord) {
			styleBuffer.append("width: " + gridCell.width + "px; ");
			styleBuffer.append("word-wrap: break-word; ");
		}

		if (styleBuffer.length() > 0) {
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		if (text.getAnchorName() != null) {
			this.writer.write("<a name=\"");
			this.writer.write(text.getAnchorName());
			this.writer.write("\"/>");
		}

		String href = null;
		switch (text.getHyperlinkType()) {
		case 2:
			if (text.getHyperlinkReference() != null) {
				href = text.getHyperlinkReference();
			}
			break;
		case 3:
			if (text.getHyperlinkAnchor() != null) {
				href = "#" + text.getHyperlinkAnchor();
			}
			break;
		case 4:
			if (text.getHyperlinkPage() != null) {
				href = "#JR_PAGE_ANCHOR_" + this.reportIndex + "_"
						+ text.getHyperlinkPage().toString();
			}
			break;
		case 5:
			if ((text.getHyperlinkReference() != null)
					&& (text.getHyperlinkAnchor() != null)) {
				href = text.getHyperlinkReference() + "#"
						+ text.getHyperlinkAnchor();
			}
			break;
		case 6:
			if ((text.getHyperlinkReference() != null)
					&& (text.getHyperlinkPage() != null)) {
				href = text.getHyperlinkReference() + "#" + "JR_PAGE_ANCHOR_"
						+ "0_" + text.getHyperlinkPage().toString();
			}
			break;
		case 1:
		}

		String target = null;
		switch (text.getHyperlinkTarget()) {
		case 2:
			target = "_blank";
			break;
		case 1:
		}

		if (href != null) {
			this.writer.write("<a href=\"");
			this.writer.write(href);
			this.writer.write("\"");
			if (target != null) {
				this.writer.write(" target=\"");
				this.writer.write(target);
				this.writer.write("\"");
			}
			this.writer.write(">");
		}

		if (textLength > 0) {
			exportStyledText(styledText);
		} else {
			this.writer.write(this.emptyCellStringProvider
					.getStringForEmptyTD(this.imagesURI));
		}

		if (href != null) {
			this.writer.write("</a>");
		}

		this.writer.write("</td>\n");
	}

	protected void exportImage(JRPrintImage image, JRExporterGridCell gridCell)
			throws JRException, IOException {
		this.writer.write("  <td");
		if (gridCell.colSpan > 1) {
			this.writer.write(" colspan=" + gridCell.colSpan);
		}
		if (gridCell.rowSpan > 1) {
			this.writer.write(" rowspan=" + gridCell.rowSpan);
		}

		String horizontalAlignment = "left";

		switch (image.getHorizontalAlignment()) {
		case 3:
			horizontalAlignment = "right";
			break;
		case 2:
			horizontalAlignment = "center";
			break;
		case 1:
		default:
			horizontalAlignment = "left";
		}

		if (!horizontalAlignment.equals("left")) {
			this.writer.write(" align=\"");
			this.writer.write(horizontalAlignment);
			this.writer.write("\"");
		}

		String verticalAlignment = "top";

		switch (image.getVerticalAlignment()) {
		case 3:
			verticalAlignment = "bottom";
			break;
		case 2:
			verticalAlignment = "middle";
			break;
		case 1:
		default:
			verticalAlignment = "top";
		}

		if (!verticalAlignment.equals("top")) {
			this.writer.write(" valign=\"");
			this.writer.write(verticalAlignment);
			this.writer.write("\"");
		}

		StringBuffer styleBuffer = new StringBuffer();

		if ((image.getBackcolor().getRGB() != Color.white.getRGB())
				&& (image.getMode() == 1)) {
			styleBuffer.append("background-color: #");
			String hexa = Integer.toHexString(
					image.getBackcolor().getRGB() & colorMask).toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			styleBuffer.append(hexa);
			styleBuffer.append("; ");
		}

		if (image.getBox() != null) {
			appendBorder(
					styleBuffer,
					image.getBox().getTopBorder(),
					image.getBox().getTopBorderColor() == null ? image
							.getForecolor() : image.getBox()
							.getTopBorderColor(), image.getBox()
							.getTopPadding(), "top");

			appendBorder(
					styleBuffer,
					image.getBox().getLeftBorder(),
					image.getBox().getLeftBorderColor() == null ? image
							.getForecolor() : image.getBox()
							.getLeftBorderColor(), image.getBox()
							.getLeftPadding(), "left");

			appendBorder(
					styleBuffer,
					image.getBox().getBottomBorder(),
					image.getBox().getBottomBorderColor() == null ? image
							.getForecolor() : image.getBox()
							.getBottomBorderColor(), image.getBox()
							.getBottomPadding(), "bottom");

			appendBorder(
					styleBuffer,
					image.getBox().getRightBorder(),
					image.getBox().getRightBorderColor() == null ? image
							.getForecolor() : image.getBox()
							.getRightBorderColor(), image.getBox()
							.getRightPadding(), "right");
		}

		if (styleBuffer.length() > 0) {
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		if (image.getAnchorName() != null) {
			this.writer.write("<a name=\"");
			this.writer.write(image.getAnchorName());
			this.writer.write("\"/>");
		}

		String href = null;
		switch (image.getHyperlinkType()) {
		case 2:
			if (image.getHyperlinkReference() != null) {
				href = image.getHyperlinkReference();
			}
			break;
		case 3:
			if (image.getHyperlinkAnchor() != null) {
				href = "#" + image.getHyperlinkAnchor();
			}
			break;
		case 4:
			if (image.getHyperlinkPage() != null) {
				href = "#JR_PAGE_ANCHOR_" + this.reportIndex + "_"
						+ image.getHyperlinkPage().toString();
			}
			break;
		case 5:
			if ((image.getHyperlinkReference() != null)
					&& (image.getHyperlinkAnchor() != null)) {
				href = image.getHyperlinkReference() + "#"
						+ image.getHyperlinkAnchor();
			}
			break;
		case 6:
			if ((image.getHyperlinkReference() != null)
					&& (image.getHyperlinkPage() != null)) {
				href = image.getHyperlinkReference() + "#" + "JR_PAGE_ANCHOR_"
						+ "0_" + image.getHyperlinkPage().toString();
			}
			break;
		case 1:
		}

		String target = null;
		switch (image.getHyperlinkTarget()) {
		case 2:
			target = "_blank";
			break;
		case 1:
		}

		if (href != null) {
			this.writer.write("<a href=\"");
			this.writer.write(href);
			this.writer.write("\"");
			if (target != null) {
				this.writer.write(" target=\"");
				this.writer.write(target);
				this.writer.write("\"");
			}
			this.writer.write(">");
		}

		this.writer.write("<img");

		String imagePath = "";

		byte scaleImage = image.getScaleImage();
		JRRenderable renderer = image.getRenderer();
		if (renderer != null) {
			if ((renderer.getType() == 0)
					&& (this.rendererToImagePathMap.containsKey(renderer))) {
				imagePath = (String) this.rendererToImagePathMap.get(renderer);
			} else {
				if (renderer.getType() == 1) {
					renderer = new JRWrappingSvgRenderer(renderer,
							new Dimension(image.getWidth(), image.getHeight()),
							image.getBackcolor());
				}

				if (image.isLazy()) {
					imagePath = ((JRImageRenderer) renderer).getImageLocation();
				} else {
					String imageName = "img_"
							+ String.valueOf(this.imageNameToImageDataMap
									.size());
					this.imageNameToImageDataMap.put(imageName,
							renderer.getImageData());

					imagePath = this.imagesURI + imageName;
				}

				this.rendererToImagePathMap.put(renderer, imagePath);
			}
		} else {
			loadPxImage();
			imagePath = this.imagesURI + "px";
			scaleImage = 2;
		}

		this.writer.write(" src=\"");
		this.writer.write(imagePath);
		this.writer.write("\"");

		int borderWidth = 0;
		switch (image.getPen()) {
		case 4:
			borderWidth = 1;
			break;
		case 3:
			borderWidth = 4;
			break;
		case 2:
			borderWidth = 2;
			break;
		case 0:
			borderWidth = 0;
			break;
		case 5:
			borderWidth = 1;
			break;
		case 1:
		default:
			borderWidth = 1;
		}

		this.writer.write(" border=");
		this.writer.write(String.valueOf(borderWidth));

		switch (scaleImage) {
		case 2:
			this.writer.write(" width=");
			this.writer.write(String.valueOf(image.getWidth()));

			this.writer.write(" height=");
			this.writer.write(String.valueOf(image.getHeight()));

			break;
		case 1:
		case 3:
		default:
			double normalWidth = image.getWidth();
			double normalHeight = image.getHeight();

			if (!image.isLazy()) {
				Dimension2D dimension = renderer.getDimension();
				if (dimension != null) {
					normalWidth = dimension.getWidth();
					normalHeight = dimension.getHeight();
				}
			}

			if (image.getHeight() > 0) {
				double ratio = normalWidth / normalHeight;

				if (ratio > image.getWidth() / image.getHeight()) {
					this.writer.write(" width=");
					this.writer.write(String.valueOf(image.getWidth()));
				} else {
					this.writer.write(" height=");
					this.writer.write(String.valueOf(image.getHeight()));
				}
			}
			break;
		}

		this.writer.write(">");

		if (href != null) {
			this.writer.write("</a>");
		}

		this.writer.write("</td>\n");
	}

	protected void layoutGrid(JRPrintPage page) {
		this.xCuts = new ArrayList();
		this.yCuts = new ArrayList();

		this.xCuts.add(new Integer(0));
		this.xCuts.add(new Integer(this.jasperPrint.getPageWidth()));
		this.yCuts.add(new Integer(0));
		this.yCuts.add(new Integer(this.jasperPrint.getPageHeight()));

		Integer x = null;
		Integer y = null;

		JRPrintElement element = null;

		List elems = page.getElements();
		for (Iterator it = elems.iterator(); it.hasNext();) {
			element = (JRPrintElement) it.next();

			x = new Integer(element.getX() + this.globalOffsetX);
			if (!this.xCuts.contains(x)) {
				this.xCuts.add(x);
			}
			x = new Integer(element.getX() + this.globalOffsetX
					+ element.getWidth());
			if (!this.xCuts.contains(x)) {
				this.xCuts.add(x);
			}
			y = new Integer(element.getY() + this.globalOffsetY);
			if (!this.yCuts.contains(y)) {
				this.yCuts.add(y);
			}
			y = new Integer(element.getY() + this.globalOffsetY
					+ element.getHeight());
			if (!this.yCuts.contains(y)) {
				this.yCuts.add(y);
			}

		}

		Collections.sort(this.xCuts);
		Collections.sort(this.yCuts);

		int xCellCount = this.xCuts.size() - 1;
		int yCellCount = this.yCuts.size() - 1;

		this.grid = new JRExporterGridCell[yCellCount][xCellCount];
		this.isRowNotEmpty = new boolean[yCellCount];

		for (int j = 0; j < yCellCount; j++) {
			for (int i = 0; i < xCellCount; i++) {
				this.grid[j][i] = new JRExporterGridCell(null,
						((Integer) this.xCuts.get(i + 1)).intValue()
								- ((Integer) this.xCuts.get(i)).intValue(),
						((Integer) this.yCuts.get(j + 1)).intValue()
								- ((Integer) this.yCuts.get(j)).intValue(), 1,
						1);
			}

		}

		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		int xi = 0;
		int yi = 0;
		boolean isOverlap = false;

		for (int i = elems.size() - 1; i >= 0; i--) {
			element = (JRPrintElement) elems.get(i);

			x1 = this.xCuts.indexOf(new Integer(element.getX()
					+ this.globalOffsetX));
			y1 = this.yCuts.indexOf(new Integer(element.getY()
					+ this.globalOffsetY));
			x2 = this.xCuts.indexOf(new Integer(element.getX()
					+ this.globalOffsetX + element.getWidth()));
			y2 = this.yCuts.indexOf(new Integer(element.getY()
					+ this.globalOffsetY + element.getHeight()));

			isOverlap = false;
			yi = y1;
			while ((yi < y2) && (!isOverlap)) {
				xi = x1;
				while ((xi < x2) && (!isOverlap)) {
					if (this.grid[yi][xi].element != null) {
						isOverlap = true;
					}
					xi++;
				}
				yi++;
			}

			if (!isOverlap) {
				yi = y1;
				while (yi < y2) {
					xi = x1;
					while (xi < x2) {
						this.grid[yi][xi] = JRExporterGridCell.OCCUPIED_CELL;
						xi++;
					}
					this.isRowNotEmpty[yi] = true;
					yi++;
				}

				if ((x2 - x1 != 0) && (y2 - y1 != 0)) {
					this.grid[y1][x1] = new JRExporterGridCell(element,
							element.getWidth(), element.getHeight(), x2 - x1,
							y2 - y1);
				}
			}
		}
	}

	protected void loadPxImage() throws JRException {
		if (!this.imageNameToImageDataMap.containsKey("px")) {
			JRRenderable pxRenderer = JRImageRenderer.getInstance(
					"net/sf/jasperreports/engine/images/pixel.GIF", (byte) 1);

			this.rendererToImagePathMap.put(pxRenderer, this.imagesURI + "px");
			this.imageNameToImageDataMap.put("px", pxRenderer.getImageData());
		}
	}

	private static void appendBorder(StringBuffer sb, byte pen,
			Color borderColor, int padding, String side) {
		String borderStyle = null;
		String borderWidth = null;

		switch (pen) {
		case 4:
			borderStyle = "dashed";
			borderWidth = "1px";
			break;
		case 3:
			borderStyle = "solid";
			borderWidth = "4px";
			break;
		case 2:
			borderStyle = "solid";
			borderWidth = "2px";
			break;
		case 5:
			borderStyle = "solid";
			borderWidth = "1px";
			break;
		case 0:
			break;
		case 1:
		default:
			borderStyle = "solid";
			borderWidth = "1px";
		}

		if (borderWidth != null) {
			sb.append("border-");
			sb.append(side);
			sb.append("-style: ");
			sb.append(borderStyle);
			sb.append("; ");

			sb.append("border-");
			sb.append(side);
			sb.append("-width: ");
			sb.append(borderWidth);
			sb.append("; ");

			sb.append("border-");
			sb.append(side);
			sb.append("-color: #");
			String hexa = Integer.toHexString(borderColor.getRGB() & colorMask)
					.toUpperCase();
			hexa = ("000000" + hexa).substring(hexa.length());
			sb.append(hexa);
			sb.append("; ");
		}

		if (padding > 0) {
			sb.append("padding-");
			sb.append(side);
			sb.append(": ");
			sb.append(padding);
			sb.append("px; ");
		}
	}
}