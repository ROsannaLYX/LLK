package nec.soft.java.client;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class  ToolBar 
{
	private JPanel mb = new JPanel();
	private JButton creatBt = new JButton();
	private JButton preBt = new JButton();
	private JButton nextBt = new JButton();

	public ToolBar()
	{
		//��ť
		creatBt=myButton("image/GameHall/create.png");
		preBt=myButton("image/GameHall/front.png");
		nextBt=myButton("image/GameHall/next.png");
		
		//��ť��
		mb.add(creatBt);
		//mb.addSeparator(new Dimension(220 ,20));
		mb.add(preBt);
		mb.add(nextBt);
		mb.setOpaque(false);

		preBt.setToolTipText("��һҳ");		
		nextBt.setToolTipText("��һҳ");
		

	}
	
	static JButton myButton(String url) { // ��ȡ����ͼƬ���Զ��尴ť
		ImageIcon ii = new ImageIcon(url);
		JButton jb = new JButton(ii);
		jb.setOpaque(false);
		jb.setContentAreaFilled(false);
		jb.setMargin(new Insets(0, 0, 0, 0));
		jb.setFocusPainted(false);
		// btn1.setBorderPainted(false);
		// btn1.setBorder(null);
		return jb;
	}

	public JButton getNextPage()
	{
		return nextBt;
	}

	public JButton getPrePage()
	{
		 return preBt;
	}

	public JPanel getToolBar()
	{
		return mb;
	}

	public JButton getCreatButton()
	{
		return creatBt;
	}
	
}
