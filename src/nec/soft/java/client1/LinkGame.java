package nec.soft.java.client1;

import static com.kkf.MyProtocol.EXIT_HOUSE;
import static com.kkf.MyProtocol.GAME_PREPARE;
import static com.kkf.MyProtocol.LINK_COOR;
import static com.kkf.MyProtocol.SPLIT_SIGN;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.kkf.UserInfo;


import nec.soft.java.share.SharedVar;
import nec.soft.java.ui.TimePanel;
import nec.soft.java.utils.Checker;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.DrawHelper;
import nec.soft.java.utils.EffectSound;

public class LinkGame {
	// ״̬:��ʼ,����,�ȴ�
	public final int START = 356;
	public static boolean end = false;
	private final static int WAITING = 206;
	// Ĭ��Ϊ�ȴ�״̬
	private static int status = WAITING;
	// ���������С
	private final int TABLE_WIDTH = 1000;
	private final int TABLE_HEIGHT = 600;
	// ����һ��10��10�еĵ�ͼ����
	public static final int ROW = Constants.NODES_COLUMN;
	public static final int COL = Constants.NODES_ROW;
	public static final int BLOCKS = ROW * COL;
	/*
	  ʵ���ϰ�������Ϊ8��8�У�ֻ��Ϊ�˷�����㣬
	  ������Χ��Ԫ��ֵʵ��û�и�ֵ����Ĭ��ֵΪ0.
	*/
	private int blocksNum = (ROW - 2) * (COL - 2);
	private int[][] blocks = new int[ROW][COL];
	// �����ͼ����,��ʼΪ36
	private int blockCounts = 100;
	// ÿ����ϰ���Ĵ�С
	private final int BLOCK_SIZE = 50;

	// �ϰ���ͼ����9��
	private final int BLOCK_NUM = 8;
	// �����ϰ���ͼƬ
	private Image[] blockImage = new Image[BLOCK_NUM + 1];
	private Image backGroup;
	private Image selected;
	private MyTable myTable;

	// �Է�������������С,����
	private final int RIVAL_WIDTH = 115;
	private final int RIVAL_HEIGTH = 135;
	// ����ͼ�е��ϰ����С
	private final int RIVAL_SIZE = 10;
	private final int RIVAL_NUM = 3;
	// �Է������������
	private Image rivalDesk;
	// ����Է���ҵ�ͼƬ����
	private java.util.List<int[][]> rivalBlock = new java.util.LinkedList<int[][]>();
	// ����ͼ��ʼ����
	private int rival_x = 50;
	private int rival_y = (ROW - 1) * BLOCK_SIZE + 10;
	private int info_y = rival_y + ROW * RIVAL_SIZE + 10;
	// ����Է���ҵ���Ϣ
	private ChummeryInfo chummeryInfo;
	// private Image

	// �����һ��ѡ�е��ϰ������������
	private int fristRow;
	private int fristCol;
	private int secondRow;
	private int secondCol;

	// ѡ�е��ϰ���ĸ���
	private int selectCount;
	private Random rand = new Random();

	private JFrame f = new JFrame("������");
	private Object[] headTile = { "ͷ��", "����", "�Ա�", "����" };
	
	
	// �������
	private ChatPanel chatPanel = new ChatPanel(ChatPanel.GAME_MODEL);
	// �û��б�
	private IconTable usrTable;
	private ExtendedTableModel model;

	// ��ť���
	private ButtonPanel btPanel;
	private JSplitPane horSlitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JSplitPane verSlitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	// ������
	private JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 32);// gai
	// ���浱ǰ����
	private int crrentPro = 0;
	private String crrentName = null;
	private Animation animation;
	// private javax.swing.Timer time;
	// ���������Ϣ
	private String[] endInfo;
	// ������Ϣ��ʼλ��
	private final int END_X = 100;
	private final int END_Y = 150;

	public LinkGame() throws Exception {
		backGroup = ImageIO.read(new File("image/GameDesk/Bg.jpg"));
		/*	backGroup = ImagesFactory.*/
		selected = ImageIO.read(new File("image/GameDesk/selected.gif"));
		rivalDesk = ImageIO.read(new File("image/GameDesk/rivalDesk.jpg"));
		btPanel = new ButtonPanel(this);
		// ��ȡ�ϰ���ͼƬ
		for (int i = 1; i < BLOCK_NUM + 1; i++) {
			blockImage[i] = ImageIO.read(new File("image/GameDesk/" + i + ".jpg"));
		}
		blocks = DrawHelper.getNodes();
		myTable = new MyTable(blocks);
	}

	// ��ΪҪ�ظ�ʹ�ñ������,�÷��������ظ���ʼ����
	public void init() {
		crrentName = Client.crrentUser.getName();
		fristRow = -1;
		fristCol = -1;
		secondRow = -1;

		secondCol = -1;
		selectCount = 0;
		crrentPro = 0;
		progressBar.setValue(crrentPro);
		animation = new Animation(myTable);
		endInfo = null;
		chummeryInfo = Client.chummery;
		chatPanel.clearText();
	}

	public void reSetUserTable() {
		java.util.List<UserInfo> houseUser = new java.util.ArrayList<UserInfo>();
		for (int i = 0; i < chummeryInfo.USER_NUM; i++) {
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

	public void removeUI(String exitUser) {
		// �û����Ƴ�һ��
		model.removeRow(exitUser);
		usrTable.updateUI();
	}

	// ��ʼ����ͼ
	public void initMap() {
		/*
			��ʼ���ϰ�������,��ͼƬ����ֵ���θ�������Ԫ��,
			��֤�������͵�ͼƬ������ż��;�����Ԫ��ȫ��Ϊ0,
			��˲��ø�ֵ,����ģ������:
					0 0 0 0 0 0 0 0 
					0 1 7 3 4 5 6 0 
					0 7 8 6 1 2 3 0  
					0 4 5 6 7 8 4 0 
					0 1 2 3 4 5 2 0  
					0 7 8 6 5 7 3 0  
					0 8 5 2 7 8 8 0 
					0 0 0 0 0 0 0 0 
		
		int index = 1;
		for (int i = 1; i < ROW - 1; i++) {
			for (int j = 1; j < COL - 1; j++)// gai
			{
				blocks[i][j] = index;// gai
				index++;
				// �������ֵ����ͼƬ����������������
				if (index > BLOCK_NUM) {
					index = 1;
				}
			}
		}
		// ����������������20��
		for (int k = 0; k < 20; k++) {
			for (int i = 1; i < ROW - 1; i++) {
				for (int j = 1; j < COL - 1; j++) {
					// ��������к�
					int tempRow = rand.nextInt(ROW - 2) + 1;

					// ��������к�
					int tempCol = rand.nextInt(COL - 2) + 1;

					// �������ͬһ��Ԫ��,�򽻻�����Ԫ��
					if (tempRow != i || tempCol != j) {
						int temp = blocks[tempRow][tempCol];
						blocks[tempRow][tempCol] = blocks[i][j];
						blocks[i][j] = temp;
					}
				}
			}
		}*/

		// ��ʼ��ͬ����������ҵ�����
		if (chummeryInfo != null) {
			for (int i = 0; i < chummeryInfo.USER_NUM; i++) {
				if (crrentName.equals(chummeryInfo.getName(i))) {
					continue;
				}
				if (!"null".equals(chummeryInfo.getName(i)) && chummeryInfo.getName(i) != null) {
					System.out.println("--------------seat:" + i + "----------");
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

		// f.setMenuBar(mb);
		myTable.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(chatPanel.getPanel());
		panel.add(btPanel.getPanel(), BorderLayout.SOUTH);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(new JScrollPane(usrTable));
		topPanel.add(btPanel.getShopPanel(), BorderLayout.SOUTH);

		verSlitPane.add(topPanel);
		verSlitPane.add(panel);
		verSlitPane.setDividerLocation(300);

		// ���ø��������С��С
		verSlitPane.setMinimumSize(new Dimension(180, TABLE_HEIGHT));
		verSlitPane.setPreferredSize(new Dimension(180, TABLE_HEIGHT));

		horSlitPane.add(myTable);
		horSlitPane.add(verSlitPane);
		// horSlitPane.setDividerLocation(200);
		horSlitPane.setDividerSize(5);

		progressBar.setStringPainted(true);
		progressBar.setBackground(new Color(100, 255, 236));
		f.setLayout(new BorderLayout());
		f.add(horSlitPane);
		f.add(progressBar, BorderLayout.SOUTH);
		f.pack();
		f.setResizable(true);
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
	}

	public void setWaiting() {
		status = WAITING;
		end = false;
		// �����ȴ���׼������
		animation.start(Animation.WAIT + Animation.PREPARE);
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

	public void drawWaiting(Graphics g) {
		// ƫ����
		int off_x = 0;
		for (int i = 0; i < chummeryInfo.USER_NUM; i++) {
			// ����ǵ�ǰ���
			if (crrentName.equals(chummeryInfo.getName(i))) {
				// �Ƿ�׼��
				if (chummeryInfo.getState(i)) {
					g.drawString("׼��..", 250, 300);
					g.drawImage(animation.getPreImage(), 280, 280, null);
				}
				continue;
			}

			if ("null".equals(chummeryInfo.getName(i)) || chummeryInfo.getName(i) == null) {
				int x = rival_x + off_x * RIVAL_WIDTH + off_x * 20 + 10;
				int y = rival_y + 20;
				g.drawString("����λ,�ȴ����...", x, y);
				x = rival_x + off_x * RIVAL_WIDTH + off_x * 20 + 30;
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
		for (int i = 0; i < chummeryInfo.USER_NUM; i++) {
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
					if (rivalBlock[row][col] != 0) {
						int index = rivalBlock[row][col];
						g.drawImage(blockImage[index], x + col * RIVAL_SIZE, rival_y + row
								* RIVAL_SIZE, RIVAL_SIZE, RIVAL_SIZE, null);
					}
				}
			}

			// ����
			g.drawString("���: " + chummeryInfo.getName(i), x, info_y);
			g.setColor(new Color(255, 255, 255));
			// ��ɶ�
			g.drawString(chummeryInfo.getDone(i), x + 80, info_y);
			off_x++;
		}
	}

	public void drawEndInfo(Graphics g) {
		g.setColor(new Color(255, 110, 15));
		g.setFont(new Font("Fixedsys", Font.PLAIN, 30));
		g.drawString("���ֽ���!", END_X, END_Y);

		String name = "";
		String score = "";
		int y = END_Y + 50;
		g.setFont(new Font("Fixedsys", Font.PLAIN, 13));
		for (int i = 0; i < endInfo.length; i += 2) {
			name = endInfo[i];
			score = endInfo[i + 1];
			g.drawString(name + "�÷�: " + score, END_X, y);
			y += 20;
		}
		y += 30;
		g.setFont(new Font("Fixedsys", Font.PLAIN, 15));
		g.drawString("����������Ϸ...", END_X, y);
		y -= 20;
		g.drawImage(animation.getPreImage(), END_X + 100, y, null);
	}

	class MyTable extends JPanel implements MouseListener, MouseMotionListener {
		private TimePanel time;
		private static final long serialVersionUID = -3833389021518120205L;
		private int[][] nodes;
		private int[] paths;
		private boolean isShowPath;
		private Point currentMovePos = new Point(0, 0);
		private Point lastPos = new Point(0, 0);
		private Point currentPos = new Point(0, 0);

		public MyTable() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public MyTable(int[][] nodes) {
			this.nodes = nodes;
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			DrawHelper.drawBackGround(g, this);

			// ������������
			for (int i = 0; i < RIVAL_NUM; i++) {
				int x = rival_x + i * RIVAL_WIDTH + i * 20;
				g.drawImage(rivalDesk, x, rival_y, null);
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

			if (status == START) {
				DrawHelper.drawNodes(nodes, g);
				DrawHelper.drawSelectRect(nodes, currentPos, g);
				DrawHelper.drawMoveRect(nodes, currentMovePos, g);
				if (isShowPath) {
					DrawHelper.drawLine(nodes, paths, g);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SharedVar.can_draw) {
				int i = DrawHelper.getI(e.getY());
				int j = DrawHelper.getJ(e.getX());
				if (i >= 1 && j >= 1 && i <= Constants.NODES_ROW - 2
						&& j <= Constants.NODES_COLUMN - 2 && nodes[i][j] > 0 && nodes[i][j] < 21) {
					lastPos.setLocation(currentPos.getX(), currentPos.getY());
					if (SharedVar.effct_music)
						EffectSound.getAudio(EffectSound.SELECT).play();
				}
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SharedVar.can_draw) {
				int i1 = DrawHelper.getI(e.getY());
				int j1 = DrawHelper.getJ(e.getX());
				int i2 = 0;
				int j2 = 0;
				if (lastPos != null) {
					i2 = DrawHelper.getI((int) lastPos.getY());
					j2 = DrawHelper.getJ((int) lastPos.getX());
				}
				if (i1 >= 1 && j1 >= 1 && i1 <= Constants.NODES_ROW - 2
						&& j1 <= Constants.NODES_COLUMN - 2 && nodes[i1][j1] > 0) {
					currentPos.setLocation(e.getX(), e.getY());
					if (i1 != i2 || j1 != j2) {
						System.out.println("����ֵ" + nodes[i1][j1] + "======" + nodes[i2][j2]);
						if (Checker.canRemove(nodes, i1, j1, i2, j2)) {
							System.out.println("�������꣺x1:y1::" + i1 + ":" + j1 + "===x2:y2::" + i2
									+ ":" + j2);
							System.out.println("CanRemove");
							if (nodes[i1][j1] > 20 || nodes[i2][j2] > 20)
								return;
							paths = Checker.getPath();
							for (int i = 0; i < paths.length; i++) {
								System.out.print(paths[i] + "<>");
							}
							System.out.println();
							if (SharedVar.time < 490)
								SharedVar.time += 10;
							SharedVar.score += 5;
							isShowPath = true;
							nodes[i1][j1] = 0;
							nodes[i2][j2] = 0;
							lastPos.setLocation(0, 0);
							currentPos.setLocation(0, 0);
							if (SharedVar.effct_music)
								EffectSound.playAudio(EffectSound.BOMB);
							showpahtAndBomb(true, i1, j1, i2, j2);
							Client.ps.println(LINK_COOR + i1 + SPLIT_SIGN + j1 + SPLIT_SIGN + i2
									+ SPLIT_SIGN + j2 + SPLIT_SIGN + "1" + LINK_COOR);
						}
					}
					repaint();
				}
			}
		}

		/** ����·�� */
		public void clearPath() {
			new RepaintThread().start();
		}

		/** ����·���Լ���ʾ��ըЧ�� */
		public void showpahtAndBomb(boolean isBomb, int i1, int j1, int i2, int j2) {
			System.out.println("ִ������");
			new RepaintThread(isBomb, i1, j1, i2, j2).start();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (SharedVar.can_draw) {
				currentMovePos.setLocation(e.getX(), e.getY());
				repaint();
			}
		}

		public boolean isShowPath() {
			return isShowPath;
		}

		public void setShowPath(boolean isShowPath) {
			this.isShowPath = isShowPath;
		}

		public int[] getPaths() {
			return paths;
		}

		public void setPaths(int[] paths) {
			this.paths = paths;
		}

		public int[][] getNodes() {
			return nodes;
		}

		public void setNodes(int[][] nodes) {
			this.nodes = nodes;
		}

		public TimePanel getTime() {
			return time;
		}

		public void setTime(TimePanel time) {
			this.time = time;
		}

		public void restart() {
			SharedVar.time = 500;
			SharedVar.score = 0;
			SharedVar.can_draw = true;
			nodes = DrawHelper.getNodes();
			time.begin();
			repaint();
		}

		/** ��������·�� */
		private class RepaintThread extends Thread {
			private boolean isBomb;
			private int i1;
			private int i2;
			private int j1;
			private int j2;

			public RepaintThread() {}

			public RepaintThread(boolean isBomb, int i1, int j1, int i2, int j2) {
				this.isBomb = isBomb;
				this.i1 = i1;
				this.i2 = i2;
				this.j1 = j1;
				this.j2 = j2;
			}

			public void run() {
				boolean isWin = DrawHelper.isWin(nodes);
				try {
					Thread.sleep(150);
				} catch (Exception e) {
				}
				isShowPath = false;

				if (!isWin) {
					/*Checker.move(nodes);*/
					if (!Checker.canMove(nodes))
						Checker.reset(nodes);
				}
				if (isWin)
					SharedVar.isPause = true;
				for (int i = 0; i < nodes.length; i++) {
					System.out.print("{");
					for (int j = 0; j < nodes[0].length; j++) {
						System.out.print(nodes[i][j] + ",");
					}
					System.out.println("},");
				}
				System.out.println();
				System.out.println();
				System.out.println();
				repaint();
				if (isBomb) {
					for (int i = 21; i <= 35; i++) {
						nodes[i1][j1] = i;
						nodes[i2][j2] = i;
						repaint();
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
					nodes[i1][j1] = 0;
					nodes[i2][j2] = 0;
					repaint();
				}
				if (isWin) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
					if (SharedVar.effct_music)
						EffectSound.getAudio(EffectSound.SCORE).play();
					SharedVar.isPause = true;
					for (int i = SharedVar.time; i >= 10; i--) {
						SharedVar.score += 3;
						SharedVar.time--;
						System.out.println(i);
						/*time.repaint();*/
						try {
							Thread.sleep(3);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					if (SharedVar.effct_music)
						EffectSound.getAudio(EffectSound.SCORE).stop();

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (SharedVar.effct_music)
						EffectSound.getAudio(EffectSound.WIN).play();
					/*JOptionPane.showMessageDialog(null, "��ϲ��ȡ��ʤ�������ε÷�Ϊ" + SharedVar.score
							+ "\n       ��ʼ����Ϸ��");*/
					int state = JOptionPane.showConfirmDialog(null, "��ϲ��ȡ��ʤ�������ε÷�Ϊ"
							+ SharedVar.score + "\n          ��ʼ����Ϸ��", "ʤ��",
							JOptionPane.OK_CANCEL_OPTION);
					if (state == JOptionPane.OK_OPTION)
						restart();
				}
			}
		}
	}
}
