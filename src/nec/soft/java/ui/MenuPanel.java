package nec.soft.java.ui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nec.soft.java.client.Login;
import nec.soft.java.dialog.ModeDialog;
import nec.soft.java.dialog.SetDialog;
import nec.soft.java.frame.MenuFrame;
import nec.soft.java.frame.SingleGameFrame;
import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.EffectSound;
import nec.soft.java.utils.ImagesFactory;

public class MenuPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 6610707050976659696L;
	private int firstX = 260;
	private int firstY = 200;
	private int space = 45;
	private int space_1 = 7;
	private int item_width = 200;
	private int item_height = 50;
	private Image start;
	private Image mode;
	private Image sort;
	private Image set;
	private Image about;
	private boolean play = true;

	public MenuPanel() {
		initImage();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void initImage() {
		start = ImagesFactory.getImage(53);
		mode = ImagesFactory.getImage(54);
		sort = ImagesFactory.getImage(55);
		set = ImagesFactory.getImage(56);
		about = ImagesFactory.getImage(57);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawMenu(g);
	}

	private void drawMenu(Graphics g) {
		g.drawImage(ImagesFactory.getImage(52), 0, 0, 750, 500, null);
		g.drawImage(start, firstX, firstY, item_width, item_height, null);
		g.drawImage(mode, firstX, firstY + space, item_width, item_height, null);
		g.drawImage(sort, firstX, firstY + space * 2, item_width, item_height, null);
		g.drawImage(set, firstX, firstY + space * 3, item_width, item_height, null);
		g.drawImage(about, firstX, firstY + space * 4, item_width, item_height, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (getIndex(e)) {
		case 1:
			start = ImagesFactory.getImage(58);
			if (SharedVar.effct_music) {
				if (play) {
					EffectSound.getAudio(EffectSound.CLICK).play();
					play = false;
				}
			}

			break;
		case 2:
			mode = ImagesFactory.getImage(59);
			if (SharedVar.effct_music) {
				if (play) {
					EffectSound.getAudio(EffectSound.CLICK).play();
					play = false;
				}
			}
			break;
		case 3:
			sort = ImagesFactory.getImage(60);
			if (SharedVar.effct_music) {
				if (play) {
					EffectSound.getAudio(EffectSound.CLICK).play();
					play = false;
				}
			}
			break;
		case 4:
			set = ImagesFactory.getImage(61);
			if (SharedVar.effct_music) {
				if (play) {
					EffectSound.getAudio(EffectSound.CLICK).play();
					play = false;
				}
			}
			break;
		case 5:
			about = ImagesFactory.getImage(62);
			if (SharedVar.effct_music) {
				if (play) {
					EffectSound.getAudio(EffectSound.CLICK).play();
					play = false;
				}
			}
			break;
		default:
			initImage();
			play = true;
			break;
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (getIndex(e)) {
		case 1:
			MenuFrame.close();
			if (SharedVar.game_mode == Constants.MODE_SINGLE) {

				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				SingleGameFrame.open();
			} else if (SharedVar.game_mode == Constants.MODE_FIGHT || SharedVar.game_mode == Constants.MODE_TOGETHER) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Login.open();
			}
			break;
		case 2:
			ModeDialog.open();
			break;
		case 3:
			break;
		case 4:
			SetDialog.open();
			break;
		case 5:
			about();
			break;
		default:
			initImage();
			break;
		}
		initImage();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	private void about() {
		JOptionPane.showMessageDialog(this, "���ߣ��ż���  �޿�Ԫ  ����  ������", "����",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private int getIndex(MouseEvent e) {
		if (e.getX() > firstX && e.getX() < firstX + item_width && e.getY() > firstY + space_1
				&& e.getY() < firstY + item_height - space_1) {
			return 1;
		}
		if (e.getX() > firstX && e.getX() < firstX + item_width
				&& e.getY() > firstY + space + space_1
				&& e.getY() < firstY + space + item_height - space_1) {
			return 2;
		}
		if (e.getX() > firstX && e.getX() < firstX + item_width
				&& e.getY() > firstY + space * 2 + space_1
				&& e.getY() < firstY + space * 2 + item_height - space_1) {
			return 3;
		}
		if (e.getX() > firstX && e.getX() < firstX + item_width
				&& e.getY() > firstY + space * 3 + space_1
				&& e.getY() < firstY + space * 3 + item_height - space_1) {
			return 4;
		}
		if (e.getX() > firstX && e.getX() < firstX + item_width
				&& e.getY() > firstY + space * 4 + space_1
				&& e.getY() < firstY + space * 4 + item_height - space_1) {
			return 5;
		}
		return -1;
	}

}
