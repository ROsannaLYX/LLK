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

//未进入游戏房间列表
class HouseList extends JPanel {
	// 组件的大小
	public final int PANEL_WIDTH = 591;
	public final int PANEL_HEIGHT = 360;
	// 房间列表的分布
	public static final int HOUSE_ROW = 3;
	public static final int HOUSE_COL = 2;
	// 每个列表房间数
	private int houseNum = HOUSE_ROW * HOUSE_COL;
	// 总房间数,默认18个,可增加
	public int allHouserNum = 18;
	// 每个房间可以容纳人数
	private static final int perNum = 4;
	private BufferedImage deskImage;
	// BufferedImage selectImage;
	private BufferedImage joinImage;
	private BufferedImage gameImage;
	private BufferedImage selectJoin;

	public static final int HOUSE_WIDTH = 297;
	public static final int HOUSE_HEIGHT = 120;
	// 表示当前页
	private int crrentPage = 0;
	// 选择的房间
	int selectHouse = -1;
	int selectRow = -1;
	int selectCol = -1;
	// 头像图标的起始位置
	public int head_x = 102;
	public int head_y = 42;
	private boolean dClicked = false;

	// 保存每个房间是否有人的信息
	private java.util.List<Integer> someoneInfo = new java.util.ArrayList<Integer>();

	private java.util.List<Integer> gameInfo = new java.util.ArrayList<Integer>();

	// 当前房间列表向下翻一页
	public void nextPage() {
		if (crrentPage < allHouserNum / houseNum - 1) {
			crrentPage++;
			System.out.println("crrentPage:" + crrentPage);
			repaint();
		}
	}

	// 当前房间列表向前翻一页
	public void prePage() {
		if (crrentPage > 0) {
			crrentPage--;
			repaint();
		}
	}

	// 初始化块
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
		// 初始化房间是否有人,如果有,则将房号保存起来
		for (UserInfo user : Client.onlineUser) {
			someoneInfo.add(user.getHouseNum());
		}

	}

	// 处理鼠标移动的方法
	public void mousedMove(MouseEvent e) {
		// System.out.println(e.getX() + " " +e.getY());
		// 将坐标转换为相对左边
		selectCol = e.getX() / HOUSE_WIDTH;
		selectRow = e.getY() / HOUSE_HEIGHT;
		selectHouse = selectRow * HOUSE_COL + selectCol;
		if (selectHouse >= 0 && selectHouse <= houseNum) {
			repaint();
		}
	}

	public void doubleClick() {
		// 实际房号
		int realHouserNum = crrentPage * houseNum + selectHouse;
		// 向服务器发送加入请求
		Client.ps.println(JOIN_HOUSE + realHouserNum + JOIN_HOUSE);

		System.out.println(realHouserNum);

		dClicked = true;
		repaint();
	}

	public void paint(Graphics g) {
		int houseName = crrentPage * 6;

		// 绘制房间
		for (int i = 0; i < HOUSE_ROW; i++) {
			for (int j = 0; j < HOUSE_COL; j++) {
				g.drawImage(deskImage, j * HOUSE_WIDTH, i * HOUSE_HEIGHT, null);
				// 绘制房间名
				g.setFont(new Font("宋体", Font.BOLD, 15));
				g.setColor(new Color(255, 0, 0));
				g.drawString("房间" + houseName, j * HOUSE_WIDTH + 10, i * HOUSE_HEIGHT + 20);

				String houseInfo = "";
				// 房间没有人
				if (someoneInfo.indexOf(houseName) == -1) {
					houseInfo = "空房";
				} else {
					houseInfo = "有人";
				}
				g.setFont(new Font("宋体", Font.PLAIN, 12));
				g.drawString("状态:" + houseInfo, j * HOUSE_WIDTH + 100, i * HOUSE_HEIGHT + 20);
				houseName++;
			}
		}

		houseName = crrentPage * 6;
		// 绘制按钮图标
		for (int i = 0; i < HOUSE_ROW; i++) {
			for (int j = 0; j < HOUSE_COL; j++) {
				BufferedImage image = joinImage;
				// 是否在游戏中
				if (gameInfo.indexOf(houseName) == -1) {
					image = joinImage;
				} else {
					image = gameImage;
				}
				g.drawImage(image, j * HOUSE_WIDTH + 10, i * HOUSE_HEIGHT + 50, 67, 23, null);
				houseName++;
			}
		}
		// 绘制选择框
		drawsSelect(g);
		// 绘制头像
		drawsHeadIcon(g);
	}

	public void drawsSelect(Graphics g) {
		// 实际房号
		int realHouserNum = crrentPage * houseNum + selectHouse;
		if (selectHouse >= 0 && selectHouse < houseNum) {
			// 绘制选择框
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3.0f));
			g2.setColor(new Color(255, 255, 0));
			g2.drawRect(selectCol * HOUSE_WIDTH, selectRow * HOUSE_HEIGHT, HOUSE_WIDTH,
					HOUSE_HEIGHT);

			// 如果该房间未开始游戏
			if (gameInfo.indexOf(realHouserNum) == -1) {
				g.drawImage(selectJoin, selectCol * HOUSE_WIDTH + 10,
						selectRow * HOUSE_HEIGHT + 50, 67, 23, null);
			}

		}
	}

	// 绘制头像
	public void drawsHeadIcon(Graphics g) {
		int relativeNum = -1;
		int seat = -1;
		int index = 0;
		// 偏移量
		int offset_x = 0;
		int col = -1;
		int row = -1;
		int x = 0;
		int y = 0;

		for (UserInfo user : Client.onlineUser) {
			// 如果房号座号不为空且房号在当前面页面
			if (user.getHouseNum() != -1 && user.getSeat() != -1
					&& user.getHouseNum() / houseNum == crrentPage) {
				// 相对房号 = 实际房号%房间数
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
