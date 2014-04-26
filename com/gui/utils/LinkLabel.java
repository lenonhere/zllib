package com.gui.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.JLabel;

/**
 * 超链接文本标签
 *
 */
public class LinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	/** 超链接显示的文字 */
	private String text;
	/** 保存连接 */
	private URL link = null;
	/** 保存标签的默认颜色 */
	private Color preColor = null;

	/**
	 * 构造一个超链接
	 *
	 * @param vText
	 *            显示的文字
	 * @param vLink
	 *            连接地址
	 * @param flag
	 *            标识[0.超链接 1.电子邮件 ]
	 */
	public LinkLabel(String vText, String vLink, final int flag) {
		super("<html>" + vText + "</html>");
		this.text = vText;

		switch (flag) {
		case 0:
			if (!vLink.startsWith("http://"))
				vLink = "http://" + vLink;
			break;
		case 1:
			if (!vLink.startsWith("mailto:"))
				vLink = "mailto:" + vLink;
			break;
		default:
			break;
		}
		try {
			this.link = new URL(vLink);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				LinkLabel.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (preColor != null)
					LinkLabel.this.setForeground(preColor);
				LinkLabel.this.setText("<html>" + text + "</html>");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				LinkLabel.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				preColor = LinkLabel.this.getForeground();
				LinkLabel.this.setForeground(Color.BLUE);
				LinkLabel.this.setText("<html><u>" + text + "</u></html>");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					URI uri = link.toURI();
					if (Desktop.isDesktopSupported()) {
						Desktop d = Desktop.getDesktop();
						switch (flag) {
						case 0:
							d.browse(uri);
							break;
						case 1:
							d.mail(uri);
							break;
						default:
							d.browse(uri);
							break;
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}
}
