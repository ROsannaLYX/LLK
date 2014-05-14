package nec.soft.java.fight;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.applet.Applet;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import nec.soft.java.utils.ShowHelper;

public class GameHall extends JPanel {

	private ImagePane headPanel;
	private ImagePane bigPanel;
	private ImagePane showBackPanel;
	private JScrollPane showPanel;
	private ImagePane chatPanel;

	private JPanel mainPanel;
	private ToolBar toolBar;

	// ����Ϊ������壨���30�����䣩
	// private RoomPane[] room = new RoomPane[30];
	int houseMaxNumber = 30;
	HouseList houseList;

	JPanel chatInputPanel;
	JTextArea chatArea; // ����������ʾ����
	JTextField chatInputArea; // ����������������
	JComboBox choose; // ����������������������˵�

	public int roomNum = 0; // ��������
	public int curRoomNum = 0; // ��ǰҳ�淿����
	public int curPage = 0; // ��ǰ�ǵڼ�ҳ����0����
	private static final int perNum = 4; // ÿ�����������������

	public User user; // �ÿͻ��˵�ʹ����
	// public User[] allUser; // ���������������
	LinkedList<User> allUser = new LinkedList<User>(); // ���������������

	// private Object[][] tableData;
	// private boolean isJoin; // ��ʾ�ÿͻ��˵�ʹ�����Ƿ��Ѽ���һ������

	protected void makePanel(JPanel jp, GridBagLayout gridbag,
			GridBagConstraints c) {
		gridbag.setConstraints(jp, c);
		add(jp);
		
	}

	protected void makePanel(JScrollPane jp, GridBagLayout gridbag,
			GridBagConstraints c) {
		gridbag.setConstraints(jp, c);
		add(jp);
	}

	public void init() {

		user = new User("image/head/6.jpg", "Jack", "123", 100);
		for (int i = 0; i < 4; i++) {
			allUser.add(new User("image/head/6.jpg", "Jack", "123", 100,0,i%4,false,false));
		}
		for (int i = 0; i < 4; i++) {
			allUser.add(new User("image/head/6.jpg", "Jack", "123", 100,1,i%4,false,false));
		}

		houseList = new HouseList(houseMaxNumber, user, this);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setFont(new Font("SansSerif", Font.PLAIN, 14));
		setLayout(gridbag);

		bigPanel = new ImagePane("image/back5.jpg");
		headPanel = new ImagePane("image/head2.jpg");
		mainPanel = new JPanel();
		showBackPanel = new ImagePane("image/green/green4.jpg");
		/*showBackPanel.setPreferredSize(new Dimension(100,100));*/
		showPanel = new JScrollPane();
		chatPanel = new ImagePane("image/chatBack.jpg");
		toolBar = new ToolBar();

		setMainPanel(curPage, roomNum);

		mainPanel.setOpaque(false);
		toolBar.setOpaque(false);
		bigPanel.setLayout(new BorderLayout());
		bigPanel.add(mainPanel, BorderLayout.CENTER);
		bigPanel.add(toolBar, BorderLayout.SOUTH);

		setHeadPanel();

		showPanel.setBackground(Color.DARK_GRAY);
		chatPanel.setBackground(Color.green);

		c.fill = GridBagConstraints.BOTH; // ���ü��������JPanel�����Ӧ���ϲ�����
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
		c.weighty = 0.25;
		makePanel(headPanel, gridbag, c);

		c.gridheight = 2;
		c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last in row
		c.weightx = 3.0;
		c.weighty = 4.0;
		/*makePanel(bigPanel, gridbag, c);*/
		GamePanel panel = new GamePanel();
		panel.setPreferredSize(new Dimension(100,500));
		/*add(panel);*/
		makePanel(panel, gridbag, c);

		setShowPanel();

		c.weightx = 1.0;
		c.weighty = 2.0;
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
		c.gridheight = 1;
		makePanel(showBackPanel, gridbag, c);

		c.weightx = 1.0;
		c.weighty = 2.0;
		c.gridwidth = GridBagConstraints.REMAINDER; // end row
		c.gridheight = 1;
		setChatPanel(); // ��chatPanel��������
		makePanel(chatPanel, gridbag, c);
		
		update();

		/*setSize(1000, 800);*/
	}

	void update() {
		User temp;
		houseList = new HouseList(houseMaxNumber, user, this);
		for (int i = 0; i < allUser.size(); i++) {
			temp = allUser.get(i);
			
			//houseList = new HouseList(houseMaxNumber, user, this);
			houseList.room[temp.roomNumber].exist = true;
			System.out.println(houseList.room[temp.roomNumber]);
			houseList.room[temp.roomNumber].userNum++;
			houseList.room[temp.roomNumber].fighting = temp.playing;
			houseList.room[temp.roomNumber].users[temp.seat] = temp;
		}

		int count = 0;
		for (int i = 0; i < 30; i++) {
			//System.out.println(houseList.room[i].exist);
			if (houseList.room[i].exist)
				count++;
		}
		System.out.println(count);
		roomNum = count;
		setMainPanel(curPage, roomNum);
		setShowPanel();
		this.repaint();
		//System.out.println(roomNum);
		System.out.println("��������");
	}

	void setMainPanel(int page, int roomNum) { // page��ʾ���ǵ�pageҳ���ӵ�0ҳ��ʼ����roomNum��ʾ���������
		curRoomNum = roomNum - page * 6;
		mainPanel.removeAll();
		// JPanel mp = new JPanel();
		// setFont(new Font("SansSerif", Font.PLAIN, 14));
		mainPanel.setLayout(new GridLayout(2, 3));

		RoomPane temp;
		if (curRoomNum > 0 || page == 0) {
			for (int i = page * 6; i < page * 6 + 6; i++) {
				temp = houseList.getRoom(i);
				mainPanel.add(temp);
				// System.out.println("gasg   " + temp.roomNumber + "  " +
				// temp.exist);
			}
		}
		this.repaint();

	}

	void setHeadPanel() {
		headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ImageIcon h = new ImageIcon(user.headURL); // ͷ�������
		JLabel head = new JLabel(h);
		headPanel.add(head);

		JPanel message = new JPanel(new BorderLayout());
		JLabel m1 = new JLabel(user.number);
		JLabel m2 = new JLabel(user.name);
		m1.setFont(new Font("����", Font.BOLD, 20));
		m2.setFont(new Font("����", Font.BOLD, 20));
		message.add(m1, BorderLayout.NORTH);
		message.add(m2, BorderLayout.SOUTH);
		message.setOpaque(false);
		headPanel.add(message);
	}

	void setChatPanel() { // ��chatPanel��������
		chatPanel.setLayout(new BorderLayout());

		chatArea = new JTextArea();
		// chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		chatArea.setOpaque(false);
		chatPanel.add(chatArea, BorderLayout.CENTER);

		chatInputPanel = new JPanel();
		chatInputPanel.setLayout(new BorderLayout());
		chatInputPanel.setOpaque(false);

		JPanel childPanel = new JPanel();
		childPanel.setLayout(new BorderLayout());
		childPanel.setOpaque(false);
		chatInputArea = new JTextField();
		// chatInputArea.setLineWrap(true);
		childPanel.add(chatInputArea, BorderLayout.CENTER);

		JButton jb = myButton("image/chat/send.gif");
		childPanel.add(jb, BorderLayout.EAST);

		chatInputPanel.add(childPanel, BorderLayout.SOUTH);

		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new BorderLayout());
		functionPanel.setOpaque(false);

		choose = new JComboBox();
		choose.addItem("ȫ��");
		choose.addItem("������");

		// JButton jb2 = new JButton("aaa");
		functionPanel.add(choose, BorderLayout.WEST);

		chatInputPanel.add(functionPanel, BorderLayout.NORTH);

		chatPanel.add(chatInputPanel, BorderLayout.SOUTH);
	}

	void setShowPanel() {
		showBackPanel.removeAll();
		showBackPanel.setLayout(new GridLayout(1, 1));
		JTable table = new AboutTable(allUser).table;
		table.setOpaque(false);
		showPanel = new JScrollPane(table);
		showPanel.setOpaque(false);
		showPanel.getViewport().setOpaque(false);

		showPanel.setPreferredSize(new Dimension(90, 70));
		showBackPanel.add(showPanel, null);

	}

	static JButton myButton(String url) { // ��ȡ����ͼƬ���Զ��尴ť
		ImageIcon ii = new ImageIcon(url);
		JButton jb = new JButton(ii);
		jb.setOpaque(false);
		jb.setContentAreaFilled(false);
		jb.setMargin(new Insets(0, 0, 0, 0));
		jb.setFocusPainted(false);
		// btn1.setBorderPainted(false);
		// btn1.setBorder(null);
		return jb;
	}

	public class ToolBar extends JPanel {
		JButton create;
		JPanel jp;
		JButton last;
		JButton next;

		ToolBar() {
			create = myButton("image/create2.png");
			last = myButton("image/last.png");
			next = myButton("image/next.png");

			jp = new JPanel();
			jp.setOpaque(false);

			setLayout(new BorderLayout());

			jp.setLayout(new BorderLayout());
			jp.add(last, BorderLayout.WEST);
			jp.add(next, BorderLayout.EAST);

			add(create, BorderLayout.WEST);
			add(jp, BorderLayout.EAST);

			create.addActionListener(new CreateListener());
			last.addActionListener(new LastListener());
			next.addActionListener(new NextListener());
		}
	}

	// ���������䡱��ť������
	class CreateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method sstub
			if (!houseList.add()) {
				System.out.println("������GameHall����⵽�����Ѵﵽ�����Ŀ");
			} else {

				roomNum++;
				// System.out.println(roomNum);
				curPage = (roomNum - 1) / 6;
				setMainPanel(curPage, roomNum);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		}
	}

	// ����һ�����䡱��ť������
	class LastListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (curPage <= 0) {
				System.out.println("������GameHall����һҳû�з�����");
			} else {
				curPage--;
				setMainPanel(curPage, roomNum);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		}
	}

	// ����һ�����䡱��ť������
	class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method sstub
			if ((curPage + 1) * 6 >= roomNum || curPage >= 4) {
				System.out.println("������GameHall����һҳû�з�����");
			} else {
				curPage++;
				setMainPanel(curPage, roomNum);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		}
	}

	public static void main(String args[]) {
		JFrame f = new JFrame("GridBag Layout Example");
		GameHall ex1 = new GameHall();

		ex1.init();

		f.add("Center", ex1);
		f.setSize(1200, 600);
		ShowHelper.showCenter(f);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
