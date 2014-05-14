package nec.soft.java.client1;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

//��Ϸ��������  
class  ToolBar 
{
	private JToolBar mb = new JToolBar();
	private JButton creatBt = new JButton();
	//private JButton listBt = new JButton();
	private JButton orderBt = new JButton();
	private JButton propBt = new JButton();
	private JButton giftBt = new JButton();
	private JButton preBt = new JButton();
	private JButton nextBt = new JButton();

	private ImageIcon creatIcon = 
		new ImageIcon("image/GameHall/creat.jpg");
	private ImageIcon frontIcon = 
		new ImageIcon("image/GameHall/front.jpg");
	private ImageIcon nextIcon = 
		new ImageIcon("image/GameHall/next.jpg");
	private ImageIcon orderIcon = 
		new ImageIcon("image/GameHall/paihang.jpg");
	private ImageIcon listIcon = 
		new ImageIcon("image/GameHall/mulu.jpg");
	private ImageIcon propIcon = 
		new ImageIcon("image/GameHall/prop.jpg");
	private ImageIcon giftIcon = 
		new ImageIcon("image/GameHall/gift.jpg");

	public ToolBar()
	{
		//��ť
		creatBt.setPreferredSize(new Dimension(30 ,20));
		creatBt.setIcon(creatIcon);
		orderBt.setIcon(orderIcon);
//		listBt.setIcon(listIcon);
		preBt.setIcon(frontIcon);
		nextBt.setIcon(nextIcon);
		propBt.setIcon(propIcon);
		giftBt.setIcon(giftIcon);
		//��ť��
		mb.add(creatBt);
		mb.add(propBt);
		//mb.add(listBt);
		mb.add(orderBt);
		mb.add(giftBt);
		mb.addSeparator(new Dimension(220 ,20));
		mb.add(preBt);
		mb.add(nextBt);

		preBt.setToolTipText("��һҳ");		
		nextBt.setToolTipText("��һҳ");
		

	}
	

	public JButton getNextPage()
	{
		return nextBt;
	}

	public JButton getPrePage()
	{
		 return preBt;
	}

	public JToolBar getToolBar()
	{
		return mb;
	}

	public JButton getCreatButton()
	{
		return creatBt;
	}
	
	//��õ��߰�ť
	public JButton getPropButton()
	{
		return propBt;
	}

	//��õ��߰�ť
	public JButton getGiftButton()
	{
		return giftBt;
	}

	public JButton getOrderButton()
	{
		return orderBt;
	}
}
