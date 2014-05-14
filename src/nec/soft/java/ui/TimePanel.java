package nec.soft.java.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.ImagesFactory;

public class TimePanel extends JPanel {
	private int life_length = 500;
	private Image lifeSpare;
	private Image lifeUse;
	private Image time_Image;
	private Image score_Image;
	private Font font = new Font("Times New Roman", Font.BOLD, 20);
	private TimeThread timeThread;
	private DrawArea area;


	private static final long serialVersionUID = -4508741162461514679L;

	public TimePanel() {
		initImage();
		timeThread = new TimeThread();
	}

	public void start() {
		new Thread(timeThread).start();
		SharedVar.isStart = true;
	}

	public void begin() {
		SharedVar.isPause = false;
		timeThread.begin();
	}

	public void pause() {
		SharedVar.isPause = true;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawLife(g);
	}

	private void initImage() {
		lifeSpare = ImagesFactory.getImage(37);
		lifeUse = ImagesFactory.getImage(36);
		time_Image = ImagesFactory.getImage(51);
		score_Image = ImagesFactory.getImage(50);
	}

	/** �������� */
	private void drawLife(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.drawImage(time_Image, 0, 4, 100, 30, null);
		g2d.drawImage(lifeSpare, 100, 10, life_length, 18, null);
		g2d.drawImage(lifeUse, 98 + SharedVar.time, 13, life_length - SharedVar.time - 1, 14, null);
		g2d.drawImage(score_Image, 150 + life_length, 4, 100, 30, null);
		g2d.setColor(Color.BLUE);
		g2d.drawString(SharedVar.score + "", 250 + life_length, 25);
	}
	
	public DrawArea getArea() {
		return area;
	}

	public void setArea(DrawArea area) {
		this.area = area;
	}


	private class TimeThread implements Runnable {


		public TimeThread() {}

		/** ʹ��ǰ������ʱֹͣ */
		public synchronized void pause() {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public synchronized void begin() {
			notify();
		}

		public void run() {
			while (true) {
				while (SharedVar.time > 10) {
					if(SharedVar.isPause)
						pause();
					SharedVar.time -= 1;
					if (SharedVar.time < 100) {
						lifeSpare = ImagesFactory.getImage(38);
					} else {
						lifeSpare = ImagesFactory.getImage(37);
					}
					repaint();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(111);
				}
				/*pause();*/
				lose();
			}
		}
		
		public void lose() {
			int state = JOptionPane.showConfirmDialog(null, "��Ϸʧ�ܡ���ʼ����Ϸ��" , "ʧ��", JOptionPane.OK_CANCEL_OPTION);
			if(state == JOptionPane.OK_OPTION)  {
				area.restart();
				/*SharedVar.isPause = true;*/
			}
			else 
				area.back();
		}
	}
}
