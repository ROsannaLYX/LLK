package nec.soft.java.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import nec.soft.java.share.SharedVar;

public class DrawHelper {

	private DrawHelper() {}

	/** ���ݽڵ���Ϣ�����Ƶ�ͼ */
	public static void drawNodes(int[][] nodes, Graphics g) {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				if (nodes[i][j] > 0) {
					g.drawImage(ImagesFactory.getImage(nodes[i][j]), getRealY(j), getRealX(i),
							Constants.NODE_WIDTH, Constants.NODE_HEIGHT, null);
				}
			}
		}
	}

	/** ���ƽڵ�ѡ�е�״̬ */
	public static void drawSelectRect(int[][] nodes, Point point, Graphics g) {
		if (point == null || nodes == null)
			return;
		int i = getI(point.y);
		int j = getJ(point.x);
		if (i < 1 || j < 1 || i > Constants.NODES_ROW - 2 || j > Constants.NODES_COLUMN - 2
				|| nodes[i][j] < 1 || nodes[i][j] > 20)
			return;
		g.drawImage(ImagesFactory.getImage(39), getRealY(j), getRealX(i), Constants.NODE_WIDTH,
				Constants.NODE_HEIGHT, null);
	}

	/** �����������ʱ�ڵ��״̬ */
	public static void drawMoveRect(int[][] nodes, Point point, Graphics g) {
		if (point == null || nodes == null)
			return;
		int i = getI(point.y);
		int j = getJ(point.x);
		if (i < 1 || j < 1 || i > Constants.NODES_ROW - 2 || j > Constants.NODES_COLUMN - 2
				|| nodes[i][j] < 1 || nodes[i][j] > 20)
			return;
		g.setColor(Color.red);
		g.drawRect(getRealX(j), getRealY(i), Constants.NODE_WIDTH, Constants.NODE_WIDTH);
		g.drawRect(getRealX(j) - 2, getRealY(i) - 2, Constants.NODE_WIDTH + 4,
				Constants.NODE_WIDTH + 4);
	}

	/** �������ڵ����������Ϊ��ʼ�㻭�ߣ�������ʶ·�� */
	public static void drawLine(int[][] nodes, int[] nums, Graphics g) {
		if (nums == null || nodes == null) {
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(5));
		for (int i = 1; i <= nums[0] * 2 - 2; i += 2) {
			g2d.drawLine(getCenterX(nums[i + 1]), getCenterY(nums[i]), getCenterX(nums[i + 3]),
					getCenterY(nums[i + 2]));
		}
	}

	/** ���Ʊ�ըʱ��Ч�� */
	public static void drawBomb(Point point, int index, Graphics g) {
		if (point == null)
			return;
		int i = getI(point.y);
		int j = getJ(point.x);
		return;

	}

	/** ������ͼƬ */
	public static void drawBackGround(Graphics g, JPanel panel) {
		URL url = ImagesFactory.class.getResource("../images/bg4.jpg");
		ImageIcon icon = new ImageIcon(url);
		Image image = icon.getImage();
		g.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);
	}

	/** ��ʼ����ͼ */
	public static int[] imageIDArray() {
		int[] images = new int[Constants.NODES_SUM];
		int[] random = new int[Constants.NODES_SUM];
		for (int index = 0, imageId = 1; index < images.length / 2; index++) {
			images[index] = imageId;
			images[images.length - index - 1] = imageId;
			imageId++;
			if (imageId >= Constants.IMAGES_SUM + 1) {
				imageId = 1;
			}
		}
		for (int i = 0, lastIndex = images.length - 1; i < images.length && lastIndex >= 0; i++, lastIndex--) {

			int rand = (int) (Math.random() * (lastIndex + 1));
			random[i] = images[rand];
			images[rand] = images[lastIndex];
		}
		return random;
	}

	/** �Ƿ��ʤ */
	public static boolean isWin(int[][] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				if (nodes[i][j] > 0 && nodes[i][j] < 21)
					return false;
			}
		}
		return true;
	}

	public static int[][] getNodes() {
		switch (SharedVar.game_stage) {
		case Constants.GAME_EASY:
			return Checker.newGame(Constants.NODES_COLUMN - 2, Constants.NODES_ROW - 2,
					Constants.TOTAL_IMAGES - 8, Mode.classic);
		case Constants.GAME_GENERAL:
			return Checker.newGame(Constants.NODES_COLUMN - 2, Constants.NODES_ROW - 2,
					Constants.TOTAL_IMAGES - 4, Mode.classic);
		case Constants.GAME_HARD:
			return Checker.newGame(Constants.NODES_COLUMN - 2, Constants.NODES_ROW - 2,
					Constants.TOTAL_IMAGES - 4, Mode.classic);
		case Constants.GAME_CHARD:
			return Checker.newGame(Constants.NODES_COLUMN - 2, Constants.NODES_ROW - 2,
					Constants.TOTAL_IMAGES - 4, Mode.center);
		default:
			
			break;
		}
		return null;
	}

	public static int getRealX(int j) {
		return j * Constants.NODE_WIDTH;
	}

	public static int getRealY(int i) {
		return i * Constants.NODE_HEIGHT;
	}

	public static int getCenterX(int j) {
		return j * Constants.NODE_WIDTH + Constants.NODE_WIDTH / 2;
	}

	public static int getCenterY(int i) {
		return i * Constants.NODE_HEIGHT + Constants.NODE_HEIGHT / 2;
	}

	public static int getI(int y) {
		return y / Constants.NODE_WIDTH;
	}

	public static int getJ(int x) {
		return x / Constants.NODE_HEIGHT;
	}
}
