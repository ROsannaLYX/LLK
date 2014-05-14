package nec.soft.java.ui;

import static com.kkf.MyProtocol.LINK_COOR;
import static com.kkf.MyProtocol.SPLIT_SIGN;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nec.soft.java.client1.Client;
import nec.soft.java.frame.MenuFrame;
import nec.soft.java.frame.SingleGameFrame;
import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.Checker;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.DrawHelper;
import nec.soft.java.utils.EffectSound;

public class DrawArea extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -3833389021518120205L;
	private int[][] nodes;
	private int[] paths;
	private boolean isShowPath;
	private Point currentMovePos = new Point(0, 0);
	private Point lastPos = new Point(0, 0);
	private Point currentPos = new Point(0, 0);
	private TimePanel time;

	public DrawArea() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public DrawArea(int[][] nodes) {
		this.nodes = nodes;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		DrawHelper.drawBackGround(g, this);
		if (SharedVar.can_draw) {
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
			if (i >= 1 && j >= 1 && i <= Constants.NODES_ROW - 2 && j <= Constants.NODES_COLUMN - 2
					&& nodes[i][j] > 0 && nodes[i][j] < 21) {
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
						System.out.println("�������꣺x1:y1::" + i1 + ":" + j1 + "===x2:y2::" + i2 + ":"
								+ j2);
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

	public void back() {
		SharedVar.can_draw = true;
		SharedVar.time = 500;
		SharedVar.score = 0;
		time.pause();
		SingleGameFrame.close();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MenuFrame.open();
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
				Checker.move(nodes);
				if (!Checker.canMove(nodes))
					Checker.reset(nodes);
			}
			/*if (isWin)
				SharedVar.isPause = true;
			for (int i = 0; i < nodes.length; i++) {
				System.out.print("{");
				for (int j = 0; j < nodes[0].length; j++) {
					System.out.print(nodes[i][j] + ",");
				}
				System.out.println("},");
			}*/
			System.out.println();
			System.out.println();
			System.out.println();
			repaint();
			/*if (isBomb) {
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
			}*/
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
					time.repaint();
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
				int state = JOptionPane.showConfirmDialog(null, "��ϲ��ȡ��ʤ�������ε÷�Ϊ" + SharedVar.score
						+ "\n          ��ʼ����Ϸ��", "ʤ��", JOptionPane.OK_CANCEL_OPTION);
				if (state == JOptionPane.OK_OPTION)
					restart();
				else 
					back();
			}

			System.out.println("����������");
		}
	}
}
