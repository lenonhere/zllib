package com.common.pdf;

import java.awt.Color;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

public class NumberCell extends Cell{

	public NumberCell(String content) throws BadElementException {
		super(new Chunk(content, FontFactory.getFont(
                FontFactory.HELVETICA, 10, Font.NORMAL, Color.BLACK)));
		setNoWrap(true);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
    }

	public NumberCell(String content, int hAlign) throws BadElementException {
		super(new Chunk(content, FontFactory.getFont(
                FontFactory.HELVETICA, 10, Font.NORMAL, Color.BLACK)));
		setNoWrap(true);
		//1为居中对齐、2为右对齐、3为左对齐，默认为左对齐
		setHorizontalAlignment(hAlign);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
    }

	public NumberCell(String content, int rowspan, int colspan, int hAlign) throws BadElementException {
		super(new Chunk(content, FontFactory.getFont(
                FontFactory.HELVETICA, 10, Font.NORMAL, Color.BLACK)));
		setNoWrap(true);
		//1为居中对齐、2为右对齐、3为左对齐，默认为左对齐
		setHorizontalAlignment(hAlign);
		setRowspan(rowspan);
        setColspan(colspan);
        setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
    }

	public NumberCell(String content, int hAlign, Color color)
    throws BadElementException {
		super(new Chunk(content, FontFactory.getFont(
                FontFactory.HELVETICA, 10, Font.NORMAL, color)));
		setNoWrap(true);
		//1为居中对齐、2为右对齐、3为左对齐，默认为左对齐
		setHorizontalAlignment(hAlign);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
		setHeader(false);
    }
}
