package com.gui;

import static com.gui.utils.GuiUtils.getToday;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.gui.components.DBBackup;
import com.gui.components.PrintDirFilesInfo;
import com.gui.components.QnyhAnswer;
import com.gui.components.Wsdl2Java;
import com.gui.examples.DBExportGUI;
import com.gui.utils.GuiUtils;

public class ApplicationLauncher {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private static SystemTray systemTray;
	private static PopupMenu popupMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiUtils.setLookAndFeel(0);
					ApplicationLauncher window = new ApplicationLauncher();
					window.frame
							.setTitle(getToday(GuiUtils.DATE_FORMAT_YMDHMS));
					window.frame.setResizable(false);
					window.frame.setLocale(new Locale("zh", "CN"));
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationLauncher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		// TODO 窗体事件
		frame.addWindowListener(new WindowAdapter() {
			// 窗口关闭
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			// 窗口最小化
			@Override
			public void windowIconified(WindowEvent e) {
				// 判断当前平台是否支持系统托盘
				if (SystemTray.isSupported()) {
					tray();
				}
			}
		});
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 584, 22);
		frame.getContentPane().add(menuBar);

		JMenuItem menuFile = new JMenuItem("文件(F)");
		menuBar.add(menuFile);

		JMenuItem menuEdit = new JMenuItem("编辑(E)");
		menuBar.add(menuEdit);

		JMenuItem menuTool = new JMenuItem("工具(T)");
		menuBar.add(menuTool);

		JMenuItem menuHelp = new JMenuItem("帮助(H)");
		menuBar.add(menuHelp);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 22, 584, 320);
		frame.getContentPane().add(splitPane);

		final JTree tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode note = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent(); // 返回最后选中的结点

				if (note.isLeaf()) {// 叶子节点

					String name = note.toString();// 获得这个节点的名称
					Component[] coms = tabbedPane.getComponents();
					// for (int i = 0; i < coms.length; i++) {
					// Component com= tabbedPane.getComponentAt(i);
					// if (coms[i] instanceof JPanel) {
					// JPanel jp = (JPanel) coms[i];
					// }
					// }
					// System.out.println(coms.length);

					if (coms.length > 1) {
						for (int i = 1; i < coms.length; i++) {
							tabbedPane.remove(i);
						}
					}
					// TODO SWitch
					JPanel newPanel = null;
					if ("Wsdl2Java".equals(name)) {
						newPanel = Wsdl2Java.getJPanel();
					} else if ("QnyhAnswer".equals(name)) {
						newPanel = QnyhAnswer.getJPanel();
					} else if ("DBBackup".equals(name)) {
						newPanel = DBBackup.getJPanel();
					} else if ("DBExport".equals(name)) {
						new DBExportGUI().open();
					} else if ("PrintDirFilesInfo".equals(name)) {
						newPanel = PrintDirFilesInfo.getJPanel();
					}
					if (null != newPanel) {
						tabbedPane.addTab(name, null, newPanel, null);
						tabbedPane.setSelectedIndex(1);
					}
				}

			}
		});
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root") {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode("Demos");
				node_1.add(new DefaultMutableTreeNode("Wsdl2Java"));
				node_1.add(new DefaultMutableTreeNode("QnyhAnswer"));
				node_1.add(new DefaultMutableTreeNode("DBBackup"));
				node_1.add(new DefaultMutableTreeNode("DBExport"));
				node_1.add(new DefaultMutableTreeNode("PrintDirFilesInfo"));
				add(node_1);
			}
		}));
		splitPane.setLeftComponent(tree);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		JPanel panelHome = new JPanel();
		panelHome.setToolTipText("");
		tabbedPane.addTab("Home", null, panelHome, null);

		JLabel labelHome = new JLabel(getAboutAuthor());
		labelHome.setVerticalAlignment(SwingConstants.CENTER);
		labelHome.setHorizontalAlignment(SwingConstants.CENTER);
		panelHome.add(labelHome);

		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 344, 584, 16);
		frame.getContentPane().add(toolBar);

		JLabel lblNewLabel = new JLabel("NowTime:");
		toolBar.add(lblNewLabel);
	}

	private void tray() {
		// 获得本操作系统托盘的实例
		systemTray = SystemTray.getSystemTray();
		createPopupMenu();
		// 定义托盘图标的图片
		Image icon = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("images/logo.gif"));
		// 将菜单添加到托盘图标
		final TrayIcon trayIcon = new TrayIcon(icon, "GUI-Utils", popupMenu);
		// 大图片自适应系统托盘大小
		trayIcon.setImageAutoSize(true);
		/**
		 * 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
		 */
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {// 鼠标双击
					if (frame.getExtendedState() == JFrame.ICONIFIED) {
						frame.setVisible(true);// 设置为可见
						frame.setAlwaysOnTop(true);// 设置置顶
						// 设置窗口状态(在最小化状态弹出显示)
						frame.setExtendedState(JFrame.NORMAL);
						// 从系统的托盘实例中移除托盘图标
						systemTray.remove(trayIcon);
					} else {
						// 设置窗口状态(最小化至托盘)
						frame.setExtendedState(JFrame.ICONIFIED);
					}
				}
			}
		});
		try {
			systemTray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
			frame.setVisible(false); // 使窗口不可视
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}

	private void createPopupMenu() {
		// 创建弹出菜单
		popupMenu = new PopupMenu();
		// 创建关于作者菜单项
		MenuItem aboutAutho = new MenuItem("About Author");
		aboutAutho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// LinkLabel mail = new LinkLabel("qingmuxiang@126.com",
				// "mailto:qingmuxiang@126.com", 1);
				JLabel jl = new JLabel(getAboutAuthor());
				JOptionPane.showMessageDialog(null, jl, "About Author",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// 创建退出应用菜单项
		MenuItem exitMenu = new MenuItem("&Exit");
		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// 将菜单项添加到菜单列表
		popupMenu.add(aboutAutho);
		// 分隔线
		popupMenu.addSeparator();
		popupMenu.add(exitMenu);
	}

	private String getAboutAuthor() {
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("Author: 青木香(QQ:466942844)<br>");
		html.append("E-mail: <a href='mailto:qingmuxiang@126.com'>qingmuxiang@126.com</a>");
		html.append("</html>");
		return html.toString();
	}
}
