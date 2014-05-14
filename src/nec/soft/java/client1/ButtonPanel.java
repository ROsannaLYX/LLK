package nec.soft.java.client1;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;

//按钮面板
class ButtonPanel {
	private JPanel btPanel = new JPanel();
	private JLabel iconLb = new JLabel();
	private JLabel shopLb = new JLabel();
	private ImageIcon buttonIcon;
	private ImageIcon startIcon;
	private ImageIcon faceIcon;
	private ImageIcon changeIcon;
	private ImageIcon exeIcon;
	private ImageIcon shopIcon;

	// static LinkGame lg;

	public ButtonPanel(LinkGame linkGame) {
		final LinkGame lg = linkGame;

		buttonIcon = new ImageIcon("image/GameDesk/button.jpg");
		startIcon = new ImageIcon("image/GameDesk/start.jpg");
		faceIcon = new ImageIcon("image/GameDesk/face.jpg");
		changeIcon = new ImageIcon("image/GameDesk/change.jpg");
		exeIcon = new ImageIcon("image/GameDesk/exe.jpg");
		shopIcon = new ImageIcon("image/GameDesk/shop.jpg");

		iconLb.setIcon(buttonIcon);
		shopLb.setIcon(shopIcon);

		iconLb.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (e.getX() > 10 && e.getX() < 87 && e.getY() > 92 && e.getY() < 125) {
					iconLb.setIcon(startIcon);
				}

				else

				if (e.getX() > 10 && e.getX() < 90 && e.getY() > 50 && e.getY() < 85) {
					iconLb.setIcon(faceIcon);
				}

				else

				if (e.getX() > 95 && e.getX() < 175 && e.getY() > 50 && e.getY() < 85) {
					iconLb.setIcon(changeIcon);
				}

				else

				if (e.getX() > 95 && e.getX() < 174 && e.getY() > 92 && e.getY() < 125) {
					iconLb.setIcon(exeIcon);
				}

				else {
					iconLb.setIcon(buttonIcon);
				}

			}

		});

		iconLb.addMouseListener(new MouseAdapter() {
			// 响应鼠标按下
			public void mousePressed(MouseEvent e) {
				// 准备
				if (e.getX() > 10 && e.getX() < 87 && e.getY() > 92 && e.getY() < 125) {
					iconLb.setIcon(buttonIcon);
					lg.prepare();
				}

				else

				if (e.getX() > 10 && e.getX() < 90 && e.getY() > 50 && e.getY() < 85) {
					iconLb.setIcon(buttonIcon);
				}

				else

				if (e.getX() > 95 && e.getX() < 175 && e.getY() > 50 && e.getY() < 85) {
					iconLb.setIcon(buttonIcon);
				}

				else

				if (e.getX() > 95 && e.getX() < 174 && e.getY() > 92 && e.getY() < 125) {
					iconLb.setIcon(buttonIcon);
				}

			}
		});
	}

	public JLabel getPanel() {
		// JPanel panel = new JPanel();
		// panel.add(iconLb);
		return iconLb;
	}

	public JLabel getShopPanel() {
		// JPanel panel = new JPanel();
		// panel.add(iconLb);
		return shopLb;
	}
}