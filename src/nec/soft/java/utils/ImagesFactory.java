package nec.soft.java.utils;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/** ���ͼƬ */
public class ImagesFactory {

	private static Image[] images = new Image[100];

	public static Image getImage(int index) {
		if (images[index] == null) {
			URL url = ImagesFactory.class.getResource("../images/" + index + ".png");
			ImageIcon icon = new ImageIcon(url);
			images[index] = icon.getImage();
		}
		return images[index];
	}
}
