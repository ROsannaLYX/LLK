package nec.soft.java.client1;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.kkf.MyProtocol;
import com.kkf.UserInfo;

//游戏大厅
public class GameHall {
	// 获得屏幕的大小
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame jf = new JFrame("游戏大厅");
	// 房间列表
	private HouseList housList = new HouseList();
	String[] names = { "所有玩家" };
	// 聊天面板
	private ChatPanel chatPanel = new ChatPanel(ChatPanel.HALL_MODEL);

	private Object[] headTile = { "头像", "姓名", "性别", "积分" };
	// 用户列表
	private ExtendedTableModel model;
	private IconTable usrTable;// = new ReDialog().headTable;
	private ToolBar mb = new ToolBar();
	private JSplitPane horSlitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JSplitPane leftSlitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	// 用户形象照
	// private JLabel userLb;
	// 商城模块
	private ShopPanel shop = new ShopPanel();

	// 排行榜
	private JDialog sortDialog;
	private SortTable sortTable;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void init() {
		// 初始化用户列表
		model = new ExtendedTableModel(headTile, Client.onlineUser);
		usrTable = new IconTable(model, 0, IconTable.GENERAL);

		Box topPanel = Box.createVerticalBox();
		topPanel.add(housList);
		topPanel.add(mb.getToolBar());
		// mb.setBackground(new Color(20 , 165 ,77));

		// userLb = new JLabel(new ImageIcon("image/user/boy.jpg"));
		// userLb.setSize(200 , 200);

		horSlitPane.add(chatPanel.getPanel());
		horSlitPane.add(new JScrollPane(usrTable));
		horSlitPane.setDividerSize(5);
		horSlitPane.setDividerLocation(250);

		// 桌面左边
		leftSlitPane.add(topPanel);
		leftSlitPane.add(horSlitPane);
		// 设置分割条大小和位置
		leftSlitPane.setDividerSize(5);
		leftSlitPane.setDividerLocation(400);
		// leftSlitPane.disable();

		jf.getContentPane().add(leftSlitPane);
		jf.setBounds(100, 0, 600, screenSize.height - 100);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		// jf.pack();
		jf.setVisible(true);

		// 鼠标移动事件被Box组件覆盖,所以使用Box组件来响应
		topPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				housList.mousedMove(e);
			}
		});

		topPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getX() > housList.PANEL_WIDTH || e.getY() > housList.PANEL_HEIGHT) {
					return;
				}

				// 响应双击事件
				if (e.getClickCount() >= 2) {
					housList.doubleClick();
				}
			}
		});

		// 下一页按钮监听事件
		mb.getNextPage().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				housList.nextPage();
			}
		});

		// 上一页按钮监听事件
		mb.getPrePage().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				housList.prePage();
			}
		});

		// 创建房间事件
		mb.getCreatButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Client.crrentUser.getHouseNum() != -1) {
					JOptionPane.showMessageDialog(null, "你已经在房间中,不能创建房间！");
					return;
				}

				int result = JOptionPane.showConfirmDialog(null, "是否创建一个空的房间?", "- -?",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					Client.ps.println(MyProtocol.CREATE_HOUSE + MyProtocol.CREATE_HOUSE);

				}
			}
		});

		mb.getPropButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shop.showClothDg();
			}
		});

		mb.getGiftButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shop.showGiftDg();
			}
		});

		mb.getGiftButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shop.showGiftDg();
			}
		});

		mb.getOrderButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortDialog = new JDialog(jf, "积分排行");
				sortTable = new SortTable(1, Client.onlineUser);
				sortDialog.getContentPane().add(new JScrollPane(sortTable.getTalbe()));
				sortDialog.pack();
				sortDialog.setBounds(150, 150, 200, 200);
				sortDialog.setVisible(true);
			}
		});

	}

	public ChatPanel getChat() {
		return chatPanel;
	}

	public void initPlayingInfo(java.util.List<Integer> gameInfo) {
		housList.initGameInfo(gameInfo);
		housList.repaint();
	}

	public void addPlayingInfo(int houseNum) {
		housList.setGameInfo(houseNum);
		housList.repaint();
	}

	public void removePlayingInfo(int houseNum) {
		housList.removeGameInfo((Integer) houseNum);
		housList.repaint();
	}

	public void insertUI(UserInfo user) {
		// 用户表增加一行
		model.addRow(user);
		usrTable.updateUI();
		// 聊天下拉表增加一用户
		chatPanel.addUserItem(user.getName());
	}

	public void removeUI(String exitUser) {
		// 用户表移除一行
		model.removeRow(exitUser);
		usrTable.updateUI();
		// 聊天下拉表移除一用户
		chatPanel.removeItem(exitUser);
	}

	public void setScore(int score, String name) {
		// 更新分数
		model.updateScore(score, name);
		usrTable.updateUI();
	}

	public void showJoinErroe() {
		JOptionPane.showMessageDialog(jf, "房间正在游戏中或人数已满,观战模式未开通");
	}

	public void updateHouseInfo() {
		housList.reSetHouseInfo();
		housList.repaint();
	}
	/*public static void main(String[] args) 
	{
		new GameHall().init();
	}*/
}
