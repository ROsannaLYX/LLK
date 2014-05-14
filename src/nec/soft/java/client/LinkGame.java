package nec.soft.java.client;

import static com.kkf.MyProtocol.EXIT_HOUSE;
import static com.kkf.MyProtocol.GAME_PREPARE;
import static com.kkf.MyProtocol.LINK_COOR;
import static com.kkf.MyProtocol.SPLIT_SIGN;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.Checker;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.ImagesFactory;
import nec.soft.java.utils.Mode;
import nec.soft.java.utils.ShowHelper;

import com.kkf.UserInfo;

// ���뷿���Ľ���
public class LinkGame {
	// ״̬:��ʼ,����,�ȴ�
	private final int START = 356;
	private boolean end = false;
	private final int WAITING = 206;
	// Ĭ��Ϊ�ȴ�״̬
	private int status = WAITING;
	// ���������С
	private final int TABLE_WIDTH = 800;
	private final int TABLE_HEIGHT = 600;
	// ����һ��10��10�еĵ�ͼ����
	public static final int ROW = 10;
	public static final int COL = 16;
	public static final int BLOCKS = 160;
	/*
	  ʵ���ϰ�������Ϊ8��8�У�ֻ��Ϊ�˷�����㣬
	  ������Χ��Ԫ��ֵʵ��û�и�ֵ����Ĭ��ֵΪ0.
	*/
	/*private int blocksNum = (ROW - 2) * (COL - 2);*/
	private int[][] blocks = new int[ROW][COL];
	/*// �����ͼ����,��ʼΪ36
	private int blockCounts = 160;*/
	// ÿ����ϰ���Ĵ�С
	private final int BLOCK_SIZE = 50;

	// �ϰ���ͼ����9��
	private final int BLOCK_NUM = 13;
	// �����ϰ���ͼƬ
	private Image[] blockImage = new Image[BLOCK_NUM + 1];
	private Image backGroup;

	// �Է�������������С,����
	private final int RIVAL_WIDTH = 155;
	// ����ͼ�е��ϰ����С
	private final int RIVAL_SIZE = 10;
	private final int RIVAL_NUM = 3;
	// ����Է���ҵ�ͼƬ����
	private java.util.List<int[][]> rivalBlock = new java.util.LinkedList<int[][]>();
	// ����ͼ��ʼ����
	private int rival_x = 50;
	private int rival_y = (ROW - 1) * BLOCK_SIZE + 40;
	private int info_y = rival_y + ROW * RIVAL_SIZE + 10;
	// ����Է���ҵ���Ϣ
	private ChummeryInfo chummeryInfo;

	// �����һ��ѡ�е��ϰ������������
	private int fristRow;
	private int fristCol;
	private int secondRow;
	private int secondCol;

	// ѡ�е��ϰ���ĸ���
	private int selectCount;

	private JFrame f = new JFrame("������");
	private MyTable myTable = new MyTable();
	private Object[] headTile = { "ͷ��", "����", "�Ա�", "����" };
	// ����һ��������
	private LinkLine lkLine = new LinkLine(blocks);
	// �Ƿ��������
	static boolean isLinked;
	// ���ڽ����۵�
	private int[] points;

	// �������
	private ChatPanel chatPanel = new ChatPanel(ChatPanel.GAME_MODEL);
	// �û��б�
	private IconTable usrTable;
	private ExtendedTableModel model;

	// ��ť���
	private JSplitPane verSlitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	// ������
	private JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 112);
	// ���浱ǰ����
	private int crrentPro = 0;
	private String crrentName = null;
	private Animation animation;
	// ���������Ϣ
	private String[] endInfo;
	// ������Ϣ��ʼλ��
	private final int END_X = 100;
	private final int END_Y = 150;

	public LinkGame() {
		backGroup = ImagesFactory.getImage(65);
		// ��ȡ�ϰ���ͼƬ
		for (int i = 1; i < BLOCK_NUM + 1; i++) {
			blockImage[i] = ImagesFactory.getImage(i);
		}
	}

	// ��ΪҪ�ظ�ʹ�ñ������,�÷��������ظ���ʼ����
	public void init() {
		crrentName = Client.crrentUser.getName();
		fristRow = -1;
		fristCol = -1;
		secondRow = -1;
		secondCol = -1;
		selectCount = 0;
		isLinked = false;
		crrentPro = 0;
		progressBar.setValue(crrentPro);
		animation = new Animation(myTable);
		endInfo = null;
		chummeryInfo = Client.chummery;
		chatPanel.clearText();
	}

	public void reSetUserTable() {
		java.util.List<UserInfo> houseUser = new java.util.ArrayList<UserInfo>();
		for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
			if (!"null".equals(chummeryInfo.getName(i)) && chummeryInfo.getName(i) != null) {
				houseUser.add(chummeryInfo.getUserInfo(i));
			}
		}
		System.out.println(houseUser.size());
		model.reSetTable(headTile, houseUser);
		usrTable.init();
		usrTable.updateUI();
	}

	public void addRow(UserInfo user) {
		model.addRow(user);
		usrTable.updateUI();
	}

	public void setScore(int score, String name) {
		// ���·���
		model.updateScore(score, name);
		usrTable.updateUI();
	}

	public void removeUI(int index) {
		model.removeRow(index);
		usrTable.updateUI();
	}

	public void removeUI(String exitUser) {
		// �û����Ƴ�һ��
		model.removeRow(exitUser);
		usrTable.updateUI();
	}

	public void initMap(String[] lines) {
		int index = 1;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				blocks[i][j] = Integer.parseInt(lines[index++]);
			}
		}

		// ��ʼ��ͬ����������ҵ�����
		if (chummeryInfo != null) {
			for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
				if (crrentName.equals(chummeryInfo.getName(i))) {
					continue;
				}
				if (!"null".equals(chummeryInfo.getName(i)) && chummeryInfo.getName(i) != null) {
					chummeryInfo.initBlock(i, blocks);
				}
			}
		}
		myTable.repaint();
	}

	// ��ʼ����ͼ
	public void initMap() {
		int[][] block = Checker.newGame(14, 8, 12, Mode.classic);
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				blocks[i][j] = block[i][j];
			}
		}
		// ��ʼ��ͬ����������ҵ�����
		if (chummeryInfo != null) {
			for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
				if (crrentName.equals(chummeryInfo.getName(i))) {
					continue;
				}
				if (!"null".equals(chummeryInfo.getName(i)) && chummeryInfo.getName(i) != null) {
					chummeryInfo.initBlock(i, blocks);
				}
			}
		}
		myTable.repaint();
	}

	// ��ʼ�����
	public void initComponent() {
		model = new ExtendedTableModel();
		usrTable = new IconTable(model, 0, IconTable.GENERAL);

		myTable.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(chatPanel.getPanel());
		JButton button = new JButton("��ʼ");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prepare();
			}
		});
		panel.add(button, BorderLayout.SOUTH);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(new JScrollPane(usrTable));

		verSlitPane.add(topPanel);
		verSlitPane.add(panel);
		verSlitPane.setDividerSize(1);
		verSlitPane.setDividerLocation(300);

		// ���ø��������С��С
		verSlitPane.setMinimumSize(new Dimension(300, TABLE_HEIGHT));
		verSlitPane.setPreferredSize(new Dimension(300, TABLE_HEIGHT));

		progressBar.setStringPainted(true);
		progressBar.setBackground(new Color(100, 255, 236));
		progressBar.setPreferredSize(new Dimension(100, 20));
		f.setLayout(new BorderLayout());
		f.add(verSlitPane, BorderLayout.EAST);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(myTable, BorderLayout.CENTER);
		panel2.add(progressBar, BorderLayout.NORTH);
		panel2.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT + 20));
		f.add(panel2, BorderLayout.CENTER);
		f.pack();
		f.setResizable(false);
		ShowHelper.showCenter(f);
		f.setVisible(false);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// ֪ͨ�������˳�����
				Client.ps.println(EXIT_HOUSE + Client.crrentUser.getHouseNum() + SPLIT_SIGN
						+ Client.crrentUser.getSeat() + EXIT_HOUSE);
				animation.stop();
				f.setVisible(false);
			}
		});

		// ��Ϸ������갴���¼�
		myTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (status == WAITING) {
					return;
				}
				// ����������ת��Ϊ��������
				int xPos = e.getX() / BLOCK_SIZE;
				int yPos = e.getY() / BLOCK_SIZE;
				// ���������ͼ��Χ,ֱ���˳�����
				if (xPos < 1 || xPos > COL - 2 || yPos < 1 || yPos > ROW - 2) {
					return;
				}
				selectCount++;
				switch (selectCount) {
				case 1: // ��һ��ѡ��
					fristRow = yPos;
					fristCol = xPos;
					break;
				case 2: // �ڶ���ѡ��
					secondRow = yPos;
					secondCol = xPos;
					break;
				default:
					break;
				}

				if (selectCount == 2) {
					// �ж�ѡ�е��ϰ����Ƿ��������
					isLinked = lkLine.linkAble(fristCol, fristRow, secondCol, secondRow);
					// ������߳ɹ�
					if (isLinked) {
						// ����ϰ���
						blocks[fristRow][fristCol] = 0;
						blocks[secondRow][secondCol] = 0;
						crrentPro += 2;
						progressBar.setValue(crrentPro);
						// ��ȡ�۵�
						points = lkLine.getPoints();
						// ��������Ч��
						animation.start(Animation.BOMB);
						// ���������������
						Client.ps.println(LINK_COOR + fristRow + SPLIT_SIGN + fristCol + SPLIT_SIGN
								+ secondRow + SPLIT_SIGN + secondCol + SPLIT_SIGN
								+ progressBar.getString() + LINK_COOR);
					}
				}
				myTable.repaint();
			}

			// ��Ϸ��������ɿ��¼�
			public void mouseReleased(MouseEvent e) {
				if (status == WAITING) {
					return;
				}
				// ����
				if (selectCount == 2) {
					selectCount = 0;
				}
				myTable.repaint();
			}
		});
	}

	public void setWaiting() {
		status = WAITING;
		end = false;
		// �����ȴ���׼������
		animation.start(Animation.WAIT + Animation.PREPARE);
		f.revalidate();
		f.setVisible(true);
	}

	// ׼��
	public void prepare() {
		// �������Ϸ������׼��
		if (status == START) {
			return;
		}
		int seat = Client.crrentUser.getSeat();
		// ����Ѿ�׼�������ٴ�׼��
		if (chummeryInfo.getState(seat)) {
			return;
		}
		// ֪ͨ������׼�����
		Client.ps.println(GAME_PREPARE + Client.crrentUser.getHouseNum() + SPLIT_SIGN
				+ Client.crrentUser.getSeat() + GAME_PREPARE);
	}

	public void start() {
		status = START;
		myTable.repaint();
	}

	public void setEnd(String[] splitMsg) {
		end = true;
		endInfo = splitMsg;
		animation.start(Animation.WAIT + Animation.PREPARE);
		myTable.repaint();
	}

	public ChatPanel getChat() {
		return chatPanel;
	}

	public void repaint() {
		myTable.repaint();
	}

	public void drawSelected(Graphics g) {
		int fristX;
		int fristY;
		int secondX;
		int secondY;

		// ��һ��ѡ�л��2��ѡ��
		if (selectCount == 1 || selectCount == 2) {
			g.drawImage(ImagesFactory.getImage(39), fristCol * BLOCK_SIZE, fristRow * BLOCK_SIZE,
					BLOCK_SIZE, BLOCK_SIZE, null);
		}
		// ��2��ѡ��
		if (selectCount == 2) {
			g.drawImage(ImagesFactory.getImage(39), secondCol * BLOCK_SIZE, secondRow * BLOCK_SIZE,
					BLOCK_SIZE, BLOCK_SIZE, null);
			// ������ӳɹ�
			if (isLinked) {
				// ������ӵ�Ϊ��ֱ�ӷ���
				if (points == null) {
					return;
				}
				// ����������
				int index = 0;
				fristX = points[index++];
				fristY = points[index++];

				for (int i = index; i < points.length;) {
					secondX = fristX;
					secondY = fristY;
					fristX = points[i++];
					fristY = points[i++];
					Graphics2D g2d = (Graphics2D) g;
					g2d.setStroke(new BasicStroke(5));
					g2d.setColor(Color.red);
					g.drawLine(fristX * BLOCK_SIZE + 25, fristY * BLOCK_SIZE + 25, secondX
							* BLOCK_SIZE + 25, secondY * BLOCK_SIZE + 25);
				}
			}
		}
	}

	public void drawBomb(Graphics g) {
		// ���Ʊ���Ч��
		if (isLinked && blocks[fristRow][fristCol] == 0 && blocks[secondRow][secondCol] == 0) {
			g.drawImage(animation.getBomb(), fristCol * BLOCK_SIZE, fristRow * BLOCK_SIZE,
					BLOCK_SIZE, BLOCK_SIZE, null);
			g.drawImage(animation.getBomb(), secondCol * BLOCK_SIZE, secondRow * BLOCK_SIZE,
					BLOCK_SIZE, BLOCK_SIZE, null);
		}
	}

	// �����ϰ���
	public void drawBlock(Graphics g) {
		if (end) {
			return;
		}
		// �����ϰ���
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				// �Ƿ����ϰ���
				if (blocks[i][j] != 0) {
					drawBomb(g);
					int index = blocks[i][j];
					g.drawImage(blockImage[index], j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE,
							BLOCK_SIZE, null);
				}
			}
		}
	}

	class MyTable extends JPanel {

		private static final long serialVersionUID = 3315904850681547972L;

		public void paint(Graphics g) {
			// ����
			g.drawImage(backGroup, 0, 0, TABLE_WIDTH + 10, TABLE_HEIGHT + 10, null);

			// �����ϰ���
			if (status == START) {
				drawBlock(g);
			}

			if (SharedVar.game_mode == Constants.MODE_FIGHT) {
				// ������������
				for (int i = 0; i < RIVAL_NUM; i++) {
					int x = rival_x + i * RIVAL_WIDTH + i * 50;
					g.drawRect(x, rival_y, 150, 100);
				}
			}
			// ���ƶԷ���ҵ��ϰ���
			if (status == START) {
				drowRivalBlock(g);
			}
			if (status == WAITING) {
				// ����������ҵĵȴ���Ϣ
				drawWaiting(g);
			}
			// ���ƽ���״̬
			if (end) {
				drawEndInfo(g);
			}
			// ����ѡ�е�ĺ��
			if (status == START) {
				drawSelected(g);
			}
		}
	}

	public void drawWaiting(Graphics g) {
		// ƫ����
		int off_x = 0;
		for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
			// ����ǵ�ǰ���
			if (crrentName.equals(chummeryInfo.getName(i))) {
				// �Ƿ�׼��
				if (chummeryInfo.getState(i)) {
					g.drawString("׼��..", 350, 300);
					g.drawImage(animation.getPreImage(), 430, 280, null);
				}
				continue;
			}

			if ("null".equals(chummeryInfo.getName(i)) || chummeryInfo.getName(i) == null) {
				int x = rival_x + off_x * RIVAL_WIDTH + off_x * 40 + 30;
				int y = rival_y + 20;
				g.drawString("����λ,�ȴ����...", x, y);
				x = rival_x + off_x * RIVAL_WIDTH + off_x * 40 + 50;
				y = y + 10;
				// �ȴ�����
				g.drawImage(animation.getWaitImage(), x, y, null);
				off_x++;
			} else {
				int x = rival_x + off_x * RIVAL_WIDTH + off_x * 20 + 10;
				int y = rival_y + 10;
				int index = chummeryInfo.getIcon(i);
				g.drawImage(HeadIcon.headIcon[index].getImage(), x, y, null);

				y = y + 70;
				g.drawString("���: " + chummeryInfo.getName(i), x, y);
				y = y + 20;
				// �Ƿ�׼��
				if (chummeryInfo.getState(i)) {
					g.drawString("׼��..", x, y);
					x = x + 30;
					y = y - 20;
					g.drawImage(animation.getPreImage(), x, y, null);
				}
				off_x++;
			}

		}
	}

	// ���ƶԷ���ҵ��ϰ���
	public void drowRivalBlock(Graphics g) {
		// ƫ����
		int off_x = 0;
		for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
			if (crrentName.equals(chummeryInfo.getName(i))) {
				continue;
			}
			if ("null".equals(chummeryInfo.getName(i)) || chummeryInfo.getName(i) == null) {
				continue;
			}

			// ��ȡ����ҵ��ϰ���
			int[][] rivalBlock = chummeryInfo.getBlock(i);
			int x = rival_x + off_x * RIVAL_WIDTH + off_x * 20;
			// �ϰ���
			for (int row = 0; row < ROW; row++) {
				for (int col = 0; col < COL; col++) {
					if (SharedVar.game_mode == Constants.MODE_TOGETHER) {
						if (rivalBlock[row][col] == 0) {
							if (blocks[row][col] != 0) {
								crrentPro += 1;
								progressBar.setValue(crrentPro);
							}
							blocks[row][col] = 0;
						}
					} else if (rivalBlock[row][col] != 0) {
						int index = rivalBlock[row][col];
						g.drawImage(blockImage[index], x + col * RIVAL_SIZE, rival_y + row
								* RIVAL_SIZE, RIVAL_SIZE, RIVAL_SIZE, null);
					}
				}
			}

			// ����
			g.drawString("���: " + chummeryInfo.getName(i), x, info_y);
			g.setColor(new Color(255, 255, 255));
			if (SharedVar.game_mode == Constants.MODE_FIGHT) {
				// ��ɶ�
				g.drawString(chummeryInfo.getDone(i), x + 80, info_y);
				off_x++;
			}
		}
	}

	public void drawEndInfo(Graphics g) {
		g.setColor(new Color(255, 110, 15));
		g.setFont(new Font("Fixedsys", Font.PLAIN, 30));
		g.drawString("���ֽ���!", END_X, END_Y);

		int y = END_Y + 50;
		if (SharedVar.game_mode == Constants.MODE_FIGHT) {
			String name = "";
			String score = "";

			g.setFont(new Font("Fixedsys", Font.PLAIN, 13));
			for (int i = 0; i < endInfo.length; i += 2) {
				name = endInfo[i];
				score = endInfo[i + 1];
				g.drawString(name + "�÷�: " + score, END_X, y);
				y += 20;
			}
			y += 30;
		}
		g.setFont(new Font("Fixedsys", Font.PLAIN, 15));
		g.drawString("����������Ϸ...", END_X, y);
		y -= 20;
		g.drawImage(animation.getPreImage(), END_X + 100, y, null);
	}

}