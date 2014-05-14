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

//��Ϸ����
public class GameHall {
	// �����Ļ�Ĵ�С
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame jf = new JFrame("��Ϸ����");
	// �����б�
	private HouseList housList = new HouseList();
	String[] names = { "�������" };
	// �������
	private ChatPanel chatPanel = new ChatPanel(ChatPanel.HALL_MODEL);

	private Object[] headTile = { "ͷ��", "����", "�Ա�", "����" };
	// �û��б�
	private ExtendedTableModel model;
	private IconTable usrTable;// = new ReDialog().headTable;
	private ToolBar mb = new ToolBar();
	private JSplitPane horSlitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JSplitPane leftSlitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	// �û�������
	// private JLabel userLb;
	// �̳�ģ��
	private ShopPanel shop = new ShopPanel();

	// ���а�
	private JDialog sortDialog;
	private SortTable sortTable;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void init() {
		// ��ʼ���û��б�
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

		// �������
		leftSlitPane.add(topPanel);
		leftSlitPane.add(horSlitPane);
		// ���÷ָ�����С��λ��
		leftSlitPane.setDividerSize(5);
		leftSlitPane.setDividerLocation(400);
		// leftSlitPane.disable();

		jf.getContentPane().add(leftSlitPane);
		jf.setBounds(100, 0, 600, screenSize.height - 100);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		// jf.pack();
		jf.setVisible(true);

		// ����ƶ��¼���Box�������,����ʹ��Box�������Ӧ
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

				// ��Ӧ˫���¼�
				if (e.getClickCount() >= 2) {
					housList.doubleClick();
				}
			}
		});

		// ��һҳ��ť�����¼�
		mb.getNextPage().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				housList.nextPage();
			}
		});

		// ��һҳ��ť�����¼�
		mb.getPrePage().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				housList.prePage();
			}
		});

		// ���������¼�
		mb.getCreatButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Client.crrentUser.getHouseNum() != -1) {
					JOptionPane.showMessageDialog(null, "���Ѿ��ڷ�����,���ܴ������䣡");
					return;
				}

				int result = JOptionPane.showConfirmDialog(null, "�Ƿ񴴽�һ���յķ���?", "- -?",
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
				sortDialog = new JDialog(jf, "��������");
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
		// �û�������һ��
		model.addRow(user);
		usrTable.updateUI();
		// ��������������һ�û�
		chatPanel.addUserItem(user.getName());
	}

	public void removeUI(String exitUser) {
		// �û����Ƴ�һ��
		model.removeRow(exitUser);
		usrTable.updateUI();
		// �����������Ƴ�һ�û�
		chatPanel.removeItem(exitUser);
	}

	public void setScore(int score, String name) {
		// ���·���
		model.updateScore(score, name);
		usrTable.updateUI();
	}

	public void showJoinErroe() {
		JOptionPane.showMessageDialog(jf, "����������Ϸ�л���������,��սģʽδ��ͨ");
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
