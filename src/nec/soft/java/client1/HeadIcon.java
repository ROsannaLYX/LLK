package nec.soft.java.client1;
import javax.swing.ImageIcon;

//Í·Ïñ£¬new Í¼Æ¬
public class HeadIcon
{
	static final int NUM = 20;
	public static ImageIcon[] headIcon = new ImageIcon[NUM];
	
	static
	{
		for(int i = 0; i < NUM ; i++)
		{
			headIcon[i] = new ImageIcon("image/head/" + (i+1) +".jpg");
		}
	}
}
