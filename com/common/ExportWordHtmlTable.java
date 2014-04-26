/**
 * @author: 朱忠南
 * @date: Jul 30, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: 通过caption 和 result结果集，返回导出word使用的html格式的table
 * @modify_author:
 * @modify_time:
 */
package com.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

/**
 * @author jokin
 * @date Jul 30, 2008
 */
public class ExportWordHtmlTable {

	public static String getHtmlTable(List captions, List results) {
		int allWidth = 550;
		List lockedCaptions = new ArrayList();// 存放锁定列的表头
		List valueCaptions = new ArrayList();// 存放未锁定列的表头
		int lockedWidth = 0;// 锁定表头列长度和
		for (int i = 0; i < captions.size(); i++) {
			DynaBean bean = (DynaBean) captions.get(i);
			Caption caption = new Caption();
			caption.caption = ((String) bean.get("caption")).split("##")[0];
			caption.property = (String) bean.get("property");
			caption.width = ((Integer) bean.get("width")).intValue();
			caption.align = (String) bean.get("align");
			caption.fixcol = ((Integer) bean.get("fixcol")).intValue();
			if (caption.width <= 0)
				continue;
			if (caption.fixcol == 1) {
				lockedCaptions.add(caption);
				lockedWidth += caption.width;
			} else {
				valueCaptions.add(caption);
			}
		}

		StringBuffer html = new StringBuffer("");
		if (valueCaptions.size() == 0) {
			html.append("<table width='550' cellspacing='0'>\n");
			html.append("\t<tr bgcolor='#EAEAEA'>\n");
			for (int i = 0; i < lockedCaptions.size(); i++) {
				Caption caption = (Caption) lockedCaptions.get(i);
				html.append("\t\t<td width='" + caption.width
						+ "' align='center'>" + caption.caption + "</td>\n");
			}
			html.append("\t</tr>\n");

			// 值循环
			for (int n = 0; n < results.size(); n++) {
				DynaBean bean = (DynaBean) results.get(n);
				html.append("\t<tr bgcolor='#FFFFFF'>\n");
				for (int i = 0; i < lockedCaptions.size(); i++) {
					Caption caption = (Caption) lockedCaptions.get(i);
					html.append("\t\t<td width='"
							+ caption.width
							+ "' align='"
							+ caption.align
							+ "'>&nbsp;"
							+ (bean.get(caption.property) != null ? bean
									.get(caption.property) : "") + "</td>\n");
				}
				html.append("\t</tr>\n");
			}
			html.append("</table>\n");
		}

		while (valueCaptions.size() > 0) {
			int thisRowWidth = lockedWidth;
			int index = -1;
			html.append("<table width='550' cellspacing='0'>\n");
			html.append("\t<tr bgcolor='#EAEAEA'>\n");
			for (int i = 0; i < lockedCaptions.size(); i++) {
				Caption caption = (Caption) lockedCaptions.get(i);
				html.append("\t\t<td width='" + caption.width
						+ "' align='center'>" + caption.caption + "</td>\n");
			}
			for (int i = 0; i < valueCaptions.size(); i++) {
				Caption caption = (Caption) valueCaptions.get(i);
				html.append("\t\t<td width='" + caption.width
						+ "' align='center'>" + caption.caption + "</td>\n");
				thisRowWidth += caption.width;
				if (thisRowWidth >= allWidth) {
					index = i;
					break;
				}
			}
			if (index == -1)
				index = valueCaptions.size() - 1;
			html.append("\t</tr>\n");
			thisRowWidth = lockedWidth;
			// 值循环
			for (int n = 0; n < results.size(); n++) {
				DynaBean bean = (DynaBean) results.get(n);
				html.append("\t<tr bgcolor='#FFFFFF'>\n");
				for (int i = 0; i < lockedCaptions.size(); i++) {
					Caption caption = (Caption) lockedCaptions.get(i);
					html.append("\t\t<td width='"
							+ caption.width
							+ "' align='"
							+ caption.align
							+ "'>&nbsp;"
							+ (bean.get(caption.property) != null ? bean
									.get(caption.property) : "") + "</td>\n");
				}
				for (int i = 0; i <= index; i++) {
					Caption caption = (Caption) valueCaptions.get(i);
					html.append("\t\t<td width='"
							+ caption.width
							+ "' align='"
							+ caption.align
							+ "'>&nbsp;"
							+ (bean.get(caption.property) != null ? bean
									.get(caption.property) : "") + "</td>\n");
					thisRowWidth += caption.width;
				}
				html.append("\t</tr>\n");
			}
			html.append("</table>\n");
			// 删除已经打印的td内容
			for (int i = 0; i <= index; i++) {
				valueCaptions.remove(0);
			}
		}
		return html.toString();
	}

}

class Caption {
	/**
	 * 标题
	 */
	public String caption;
	/**
	 * 属性
	 */
	public String property;
	/**
	 * 宽度
	 */
	public int width;
	/**
	 * 是否锁定
	 */
	public int fixcol;
	/**
	 * 居左居右
	 */
	public String align;

	public Caption() {

	}
}