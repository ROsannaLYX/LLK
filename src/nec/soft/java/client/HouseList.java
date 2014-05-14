package nec.soft.java.client;

import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import static com.kkf.MyProtocol.*;
import com.kkf.*;

//δ������Ϸ�����б�
class HouseList extends JPanel {
	// ����Ĵ�С
	//public final int PANEL_WIDTH = 591;
	//public final int PANEL_HEIGHT = 360;
	// �����б�ķֲ�
	public static final int HOUSE_ROW = 2;
	public static final int HOUSE_COL = 3;
	// ÿ���б�����
	private int houseNum = HOUSE_ROW * HOUSE_COL;
	// �ܷ�����,Ĭ��18��,������
	public int allHouserNum = 18;
	// ÿ�����������������
	private static final int perNum = 4;
	private BufferedImage deskImage;
	private BufferedImage highDeskImage;
	//private BufferedImage backImage;
	private BufferedImage gameImage;
	// private BufferedImage selectJoin;
	
	// ��ʾ��ǰҳ
	private int crrentPage = 0;
	// ѡ��ķ���
	int selectHouse = -1;
	int selectRow = -1;
	int selectCol = -1;
	
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
		setOpaque(false);
		try {
			deskImage = ImageIO.read(new File("image/GameHall/back2.png"));
			highDeskImage = ImageIO.read(new File("image/GameHall/back3.png"));
			gameImage = ImageIO.read(new File("image/GameHall/game_ing.jpg"));
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
		
		// ������ת��Ϊ������
		selectCol = e.getX() / (this.getWidth()/3);
		selectRow = e.getY() / (this.getHeight()/2);
		selectHouse = selectRow * HOUSE_COL + selectCol;
		//System.out.println("gaegegredzsgvrdhbrthg");
		//System.out.println(selectRow + " " +selectCol);
		//System.out.println(selectHouse);
		if (selectHouse >= 0 && selectHouse <= houseNum) {
			repaint();
		}
	}
	

	public void doubleClick() {
		// ʵ�ʷ���
		int realHouserNum = crrentPage * houseNum + selectHouse;
		System.out.println(realHouserNum);
		System.out.println("             gaegegredzsgvrdhbrthg");
		// ����������ͼ�������
		Client.ps.println(JOIN_HOUSE + realHouserNum + JOIN_HOUSE);

		System.out.println(realHouserNum);
		System.out.println("             gaegegredzsgvrdhbrthg");
		dClicked = true;
		repaint();
	}

	public void paint(Graphics g) {
		int houseName = crrentPage * 6;
		//g.drawImage(backImage, 0, 0, this.getWidth(), this.getHeight(), null);
		// ���Ʒ���
		for (int i = 0; i < HOUSE_ROW; i++) {
			for (int j = 0; j < HOUSE_COL; j++) {
				if (i == selectRow && j == selectCol) {
					g.drawImage(highDeskImage, j * (this.getWidth()/3), i
							* (this.getHeight()/2), this.getWidth() / 3,
							this.getHeight() / 2, null);
				} else {
					g.drawImage(deskImage, j * (this.getWidth()/3), i * (this.getHeight()/2),
							this.getWidth() / 3, this.getHeight() / 2, null);
				}
				// ���Ʒ�����
				g.setFont(new Font("����", Font.BOLD, 15));
				g.setColor(new Color(255, 0, 0));
				g.drawString("����" + houseName, j * (this.getWidth()/3) + 10, i
						* (this.getHeight()/2) + 20);

				String houseInfo = "";
				// ����û����
				if (someoneInfo.indexOf(houseName) == -1) {
					houseInfo = "�շ�";
				} else {
					houseInfo = "����";
				}
				g.setFont(new Font("����", Font.PLAIN, 12));
				g.drawString("״̬:" + houseInfo, j * (this.getWidth()/3) + 100, i
						* (this.getHeight()/2) + 20);
				houseName++;
			}
		}

		houseName = crrentPage * 6;

		// ����ͷ��
		drawsHeadIcon(g);
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
		
		int width=this.getWidth()/3;
		int height=this.getHeight()/2;
		//System.out.println(width+"  "+height);

		for (UserInfo user : Client.onlineUser) {
			// ����������Ų�Ϊ���ҷ����ڵ�ǰ��ҳ��
			if (user.getHouseNum() != -1 && user.getSeat() != -1
					&& user.getHouseNum() / houseNum == crrentPage) {
				// ��Է��� = ʵ�ʷ���%������
				relativeNum = user.getHouseNum() % houseNum;
				offset_x = user.getSeat() * (width/6+7);
				row = relativeNum / HOUSE_COL;
				col = relativeNum - row * HOUSE_COL;

				index = user.getIconIndex();
				//x = col * HOUSE_WIDTH + head_x + offset_x;
				//y = row * HOUSE_HEIGHT + head_y;

				x = col * width + (width/6-10)+offset_x;
				y = row * height + height/3+height/6;
				
				g.drawImage(HeadIcon.headIcon[index].getImage(), x, y, width/6, height/5,
						null);
			}
		}
	}
}
