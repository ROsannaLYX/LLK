package nec.soft.java.client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import com.kkf.*;

//��Ϸ����
public class GameHall {
	// �����Ļ�Ĵ�С
	public static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();
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
	private JSplitPane topSlitPane = new JSplitPane(
			JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane horSlitPane = new JSplitPane(
			JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane leftSlitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
			true);
	// �û�������
	// private JLabel userLb;
	// �̳�ģ��
	//private ShopPanel shop = new ShopPanel();

	// ���а�
	//private JDialog sortDialog;
	//private SortTable sortTable;

	public void init() {
		// ��ʼ���û��б�
		model = new ExtendedTableModel(headTile, Client.onlineUser);
		usrTable = new IconTable(model, 0, IconTable.GENERAL){ // ����jtable�ĵ�Ԫ��Ϊ͸����
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		
		usrTable.setOpaque(false);

		//Box topPanel = Box.createVerticalBox();
		ImagePane topPanel=new ImagePane("image/GameHall/back.jpg");
		topPanel.setLayout(new BorderLayout());
		topPanel.add(housList,BorderLayout.CENTER);
		topPanel.add(mb.getToolBar(),BorderLayout.SOUTH);
		// mb.setBackground(new Color(20 , 165 ,77));

		// userLb = new JLabel(new ImageIcon("image/user/boy.jpg"));
		// userLb.setSize(200 , 200);

		JScrollPane showPanel=new JScrollPane(usrTable);
		ImagePane showBackPanel=new ImagePane("image/GameHall/showBack.jpg");
		showPanel.setOpaque(false);
		showPanel.getViewport().setOpaque(false);
		showBackPanel.setLayout(new GridLayout(1,1));
		showBackPanel.add(showPanel);
		
		horSlitPane.add(showBackPanel);
		horSlitPane.add(chatPanel.getPanel());
		horSlitPane.setDividerSize(5);
		horSlitPane.setDividerLocation(250);

		// �������
		leftSlitPane.add(topPanel);
		leftSlitPane.add(horSlitPane);
		// ���÷ָ�����С��λ��
		leftSlitPane.setDividerSize(5);
		jf.setBounds(50, 50, screenSize.width-100, screenSize.height - 100);
		leftSlitPane.setDividerLocation(jf.getWidth()-300);
		// leftSlitPane.disable();
		
		ImagePane title= new ImagePane("image/GameHall/title.jpg");
		topSlitPane.add(title);
		topSlitPane.add(leftSlitPane);
		topSlitPane.setDividerSize(5);
		topSlitPane.setDividerLocation(85);

		jf.add(topSlitPane);
		jf.setBounds(50, 50, screenSize.width-100, screenSize.height - 100);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setResizable(false);
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
				if (e.getX() > housList.getWidth()
						|| e.getY() > housList.getHeight()) {
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

				int result = JOptionPane.showConfirmDialog(null, "�Ƿ񴴽�һ���յķ���?",
						"- -?", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					Client.ps.println(MyProtocol.CREATE_HOUSE
							+ MyProtocol.CREATE_HOUSE);

				}
			}
		});

		/*mb.getPropButton().addActionListener(new ActionListener() {
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
				sortDialog.add(new JScrollPane(sortTable.getTalbe()));
				sortDialog.pack();
				sortDialog.setBounds(150, 150, 200, 200);
				sortDialog.setVisible(true);
			}
		});*/

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
	/*
	 * public static void main(String[] args) { new GameHall().init(); }
	 */
}
