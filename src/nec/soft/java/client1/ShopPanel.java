package nec.soft.java.client1;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*; 

class ShopPanel 
{
	private Image clothImage;
	private Image giftImage;
	private Image clothIcon;
	private Image giftIcon;

	private ClothPanel clothPanel;
	private GiftPanel giftPanel;
	
	private final int WIDTH = 564;
	private final int HEIGHT = 530;
	//每个商品块的大小
	private final int PER_WIDTH = 282;
	private final int PRE_HEIGTH = 115;
	
	private JToolBar mb = new JToolBar();
	private JToolBar giftMb = new JToolBar();
	
	private JFrame jf = null;
	private JDialog clothDg = new JDialog(jf  , "道具");
	private JDialog giftDg = new JDialog(jf  , "礼物");
	
	private JButton butBt = new JButton("购买");
	private JButton preBt = new JButton("上一页");
	private JButton nexBt = new JButton("下一页");
	private JButton presentBt = new JButton("赠送");
		
	private int selectRow = 0;
	private int selectCol = 0;
	private boolean click = false;
	
	public ShopPanel()
	{
		try
		{
			clothImage = ImageIO.read(
				new File("image/GameHall/clothes.jpg"));
			giftImage = ImageIO.read(
				new File("image/GameHall/gifts.jpg"));
			clothIcon = ImageIO.read(
				new File("image/GameHall/prop.jpg"));
			giftIcon = ImageIO.read(
				new File("image/GameHall/gift2.jpg"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		mb.add(butBt);
		mb.add(preBt);
		mb.add(nexBt);
		giftMb.add(presentBt);
	
		clothPanel = new ClothPanel();
		giftPanel = new GiftPanel();
		
		clothDg.setLayout(new BorderLayout());
		clothDg.add(clothPanel);
		clothDg.add(mb , BorderLayout.SOUTH);
		clothDg.pack();
		
		giftDg.setLayout(new BorderLayout());
		giftDg.add(giftPanel);
		giftDg.add(giftMb , BorderLayout.SOUTH);
		giftDg.pack();
		
		clothDg.setIconImage(clothIcon); 
		clothDg.setBounds(150,150,WIDTH,HEIGHT);
		clothDg.setResizable(false);
		
		giftDg.setIconImage(giftIcon); 
		giftDg.setBounds(150,150,WIDTH,HEIGHT);
		giftDg.setResizable(false);

		butBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(
						null, "此功能暂未开通！"); 
			}
		});

		presentBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(
						null, "此功能暂未开通！"); 
			}
		});

		MouseMotion moveListener = new MouseMotion();
		MouseClick clickListener = new MouseClick();

		clothPanel.addMouseListener(clickListener);
		giftPanel.addMouseListener(clickListener);
	}
	
	public void showClothDg()
	{
		click = false;
		clothDg.setVisible(true);
	}
	
	public void showGiftDg()
	{
		click = false;
		giftDg.setVisible(true);
	}

	class MouseMotion extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e) 
		{
			if (!click)
			{
				selectRow = e.getY() / PRE_HEIGTH;
				selectCol = e.getX() / PER_WIDTH;
				clothPanel.repaint();
			}
		
		}
	}
	
	class MouseClick extends  MouseAdapter
	{
		public void mouseClicked(MouseEvent e) 
		{
			click = true;
			selectRow = e.getY() / PRE_HEIGTH;
			selectCol = e.getX() / PER_WIDTH;
			System.out.println(e.getX() + e.getY());
			clothPanel.repaint();
			giftPanel.repaint();
		}
	}

	class ClothPanel extends JPanel
	{
		public void paint(Graphics g)
		{
			g.drawImage(clothImage , 0 , 0 , null);

			if(selectRow >= 0 && selectCol >= 0)
			{
				//绘制选择框
				Graphics2D g2 = (Graphics2D)g;
				g2.setStroke(new BasicStroke(3.0f));
				g2.setColor(new Color(255,255,0));
				g2.drawRect(selectCol * PER_WIDTH
				, selectRow *  PRE_HEIGTH
				, PER_WIDTH
				, PRE_HEIGTH);
			}
		}
	}

	class GiftPanel extends JPanel 
	{
		public void paint(Graphics g)
		{
			g.drawImage(giftImage , 0 , 0 , null);

			if(selectRow >= 0 && selectCol >= 0)
			{
				//绘制选择框
				Graphics2D g2 = (Graphics2D)g;
				g2.setStroke(new BasicStroke(3.0f));
				g2.setColor(new Color(255,255,0));
				g2.drawRect(selectCol * PER_WIDTH
				, selectRow *  PRE_HEIGTH
				, PER_WIDTH
				, PRE_HEIGTH);
			}
		}
	}
}