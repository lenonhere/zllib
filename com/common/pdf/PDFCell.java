package com.common.pdf;

import java.awt.Color;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;

/*
 * 由于我需要在pdf中创建表格，要使用到com.lowagie.text.Cell，com.lowagie.text.Paragraph， com.lowagie.text.Table，com.lowagie.text.Cell，
 com.lowagie.text.Chunk，com.lowagie.text.Font等类，cell为表格中的每个单元格的内容，paragraph为段落内容，cell的构造函数有很多，这里不一一列举了，因为我要用到中文字符，所以特别使用了cell(Element e)这个构造函数，Element为一个接口，实现此接口的类有很多，包含chunk,meta等，表明cell里可以添加很多不同的内容，可以实现自己的定制，chunk的构造函数为Chunk(String content,Font f)，在这里我定制了自己的cell，代码如下：
 */

//rowspan和colspan为Cell的两个属性，写过网页的朋友都知道，表格中的行和列有的时候有必要进行合并，这里就实现了这个功能。
/**
 * @author jcoder
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PDFCell extends Cell {

	public PDFCell(String content) throws BadElementException {
		super(new Chunk(content,
				PDFChineseFont.createChineseFont(10, Font.BOLD)));
		// System.out.println("PDFCell["+rowspan+","+colspan+"]:"+content);
		setNoWrap(true);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
	}

	public PDFCell(String content, int rowspan, int colspan)
			throws BadElementException {
		super(new Chunk(content,
				PDFChineseFont.createChineseFont(10, Font.BOLD)));
		// System.out.println("PDFCell["+rowspan+","+colspan+"]:"+content);

		setNoWrap(true);
		setRowspan(rowspan);
		setColspan(colspan);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
	}

	public PDFCell(String content, int rowspan, int colspan, int hAlign)
			throws BadElementException {
		super(new Chunk(content,
				PDFChineseFont.createChineseFont(10, Font.BOLD)));
		// 1为居中对齐、2为右对齐、3为左对齐，默认为左对齐
		setHorizontalAlignment(hAlign);
		setNoWrap(true);
		setRowspan(rowspan);
		setColspan(colspan);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
	}

	public PDFCell(String content, int rowspan, int colspan, int hAlign,
			Color bcolor) throws BadElementException {
		super(new Chunk(content,
				PDFChineseFont.createChineseFont(10, Font.BOLD)));
		// 1为居中对齐、2为右对齐、3为左对齐，默认为左对齐
		setHorizontalAlignment(hAlign);
		setNoWrap(true);
		setBackgroundColor(bcolor);
		setRowspan(rowspan);
		setColspan(colspan);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
	}
}
