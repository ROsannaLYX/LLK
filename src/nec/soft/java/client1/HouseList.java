package nec.soft.java.client1;
import static com.kkf.MyProtocol.JOIN_HOUSE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.kkf.UserInfo;

//δ������Ϸ�����б�
class HouseList extends JPanel {
	// ����Ĵ�С
	public final int PANEL_WIDTH = 591;
	public final int PANEL_HEIGHT = 360;
	// �����б�ķֲ�
	public static final int HOUSE_ROW = 3;
	public static final int HOUSE_COL = 2;
	// ÿ���б�����
	private int houseNum = HOUSE_ROW * HOUSE_COL;
	// �ܷ�����,Ĭ��18��,������
	public int allHouserNum = 18;
	// ÿ�����������������
	private static final int perNum = 4;
	private BufferedImage deskImage;
	// BufferedImage selectImage;
	private BufferedImage joinImage;
	private BufferedImage gameImage;
	private BufferedImage selectJoin;

	public static final int HOUSE_WIDTH = 297;
	public static final int HOUSE_HEIGHT = 120;
	// ��ʾ��ǰҳ
	private int crrentPage = 0;
	// ѡ��ķ���
	int selectHouse = -1;
	int selectRow = -1;
	int selectCol = -1;
	// ͷ��ͼ�����ʼλ��
	public int head_x = 102;
	public int head_y = 42;
	private boolean dClicked = false;

	// ����ÿ�������Ƿ����˵���Ϣ
	private java.util.List<Integer> someoneInfo = new java.util.ArrayList<Integer>();

	private java.util.List<Integer> gameInfo = new java.util.ArrayList<Integer>();

	// ��ǰ�����б����·�һҳ
	public void nextPage() {
		if (crrentPage < allHouserNum / houseNum - 1) {
			crrentPage++;
			System.out.println("crrentPage:" + crrentPage);
			repaint();
		}
	}

	// ��ǰ�����б���ǰ��һҳ
	public void prePage() {
		if (crrentPage > 0) {
			crrentPage--;
			repaint();
		}
	}

	// ��ʼ����
	public HouseList() {
		try {
			deskImage = ImageIO.read(new File("image/GameHall/desk.jpg"));
			joinImage = ImageIO.read(new File("image/GameHall/join.jpg"));
			selectJoin = ImageIO.read(new File("image/GameHall/join2.jpg"));
			gameImage = ImageIO.read(new File("image/GameHall/game_ing.jpg"));
			// selectImage = ImageIO.read(
			// new File("ico/deskPane/select.gif"));
		} catch (IOException ex) {
			// ex.printStrack
		}

		reSetHouseInfo();
	}

	public void initGameInfo(java.util.List<Integer> gameInfo) {
		this.gameInfo = gameInfo;
	}

	public void setGameInfo(int HouseNum) {
		gameInfo.add(HouseNum);
	}

	public void removeGameInfo(Integer HouseNum) {
		gameInfo.remove(HouseNum);
	}

	public void reSetHouseInfo() {
		someoneInfo.clear();
		// ��ʼ�������Ƿ�����,�����,�򽫷��ű�������
		for (UserInfo user : Client.onlineUser) {
			someoneInfo.add(user.getHouseNum());
		}

	}

	// ��������ƶ��ķ���
	public void mousedMove(MouseEvent e) {
		// System.out.println(e.getX() + " " +e.getY());
		// ������ת��Ϊ������
		selectCol = e.getX() / HOUSE_WIDTH;
		selectRow = e.getY() / HOUSE_HEIGHT;
		selectHouse = selectRow * HOUSE_COL + selectCol;
		if (selectHouse >= 0 && selectHouse <= houseNum) {
			repaint();
		}
	}

	public void doubleClick() {
		// ʵ�ʷ���
		int realHouserNum = crrentPage * houseNum + selectHouse;
		// ����������ͼ�������
		Client.ps.println(JOIN_HOUSE + realHouserNum + JOIN_HOUSE);

		System.out.println(realHouserNum);

		dClicked = true;
		repaint();
	}

	public void paint(Graphics g) {
		int houseName = crrentPage * 6;

		// ���Ʒ���
		for (int i = 0; i < HOUSE_ROW; i++) {
			for (int j = 0; j < HOUSE_COL; j++) {
				g.drawImage(deskImage, j * HOUSE_WIDTH, i * HOUSE_HEIGHT, null);
				// ���Ʒ�����
				g.setFont(new Font("����", Font.BOLD, 15));
				g.setColor(new Color(255, 0, 0));
				g.drawString("����" + houseName, j * HOUSE_WIDTH + 10, i * HOUSE_HEIGHT + 20);

				String houseInfo = "";
				// ����û����
				if (someoneInfo.indexOf(houseName) == -1) {
					houseInfo = "�շ�";
				} else {
					houseInfo = "����";
				}
				g.setFont(new Font("����", Font.PLAIN, 12));
				g.drawString("״̬:" + houseInfo, j * HOUSE_WIDTH + 100, i * HOUSE_HEIGHT + 20);
				houseName++;
			}
		}

		houseName = crrentPage * 6;
		// ���ư�ťͼ��
		for (int i = 0; i < HOUSE_ROW; i++) {
			for (int j = 0; j < HOUSE_COL; j++) {
				BufferedImage image = joinImage;
				// �Ƿ�����Ϸ��
				if (gameInfo.indexOf(houseName) == -1) {
					image = joinImage;
				} else {
					image = gameImage;
				}
				g.drawImage(image, j * HOUSE_WIDTH + 10, i * HOUSE_HEIGHT + 50, 67, 23, null);
				houseName++;
			}
		}
		// ����ѡ���
		drawsSelect(g);
		// ����ͷ��
		drawsHeadIcon(g);
	}

	public void drawsSelect(Graphics g) {
		// ʵ�ʷ���
		int realHouserNum = crrentPage * houseNum + selectHouse;
		if (selectHouse >= 0 && selectHouse < houseNum) {
			// ����ѡ���
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3.0f));
			g2.setColor(new Color(255, 255, 0));
			g2.drawRect(selectCol * HOUSE_WIDTH, selectRow * HOUSE_HEIGHT, HOUSE_WIDTH,
					HOUSE_HEIGHT);

			// ����÷���δ��ʼ��Ϸ
			if (gameInfo.indexOf(realHouserNum) == -1) {
				g.drawImage(selectJoin, selectCol * HOUSE_WIDTH + 10,
						selectRow * HOUSE_HEIGHT + 50, 67, 23, null);
			}

		}
	}

	// ����ͷ��
	public void drawsHeadIcon(Graphics g) {
		int relativeNum = -1;
		int seat = -1;
		int index = 0;
		// ƫ����
		int offset_x = 0;
		int col = -1;
		int row = -1;
		int x = 0;
		int y = 0;

		for (UserInfo user : Client.onlineUser) {
			// ����������Ų�Ϊ���ҷ����ڵ�ǰ��ҳ��
			if (user.getHouseNum() != -1 && user.getSeat() != -1
					&& user.getHouseNum() / houseNum == crrentPage) {
				// ��Է��� = ʵ�ʷ���%������
				relativeNum = user.getHouseNum() % houseNum;
				offset_x = user.getSeat() * 50;
				row = relativeNum / HOUSE_COL;
				col = relativeNum - row * HOUSE_COL;

				index = user.getIconIndex();
				x = col * HOUSE_WIDTH + head_x + offset_x;
				y = row * HOUSE_HEIGHT + head_y;

				g.drawImage(HeadIcon.headIcon[index].getImage(), x, y, 40, 40, null);
			}
		}
	}
}
