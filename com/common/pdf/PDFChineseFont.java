package com.common.pdf;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

//如果无此函数定义，生成的pdf文件中的中文字符将不显示。
/**
 * @author jcoder
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PDFChineseFont {
    private static Font chineseFont;

    public final static Font createChineseFont(int size, int style) {
        try {
            chineseFont = new Font(BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), size, style);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chineseFont;
    }
}

