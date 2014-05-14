package nec.soft.java.fight;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.DrawHelper;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = -968691140618124476L;
	
	public GamePanel() {
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		SharedVar.area.setNodes(DrawHelper.getNodes());
		SharedVar.timePanel.setPreferredSize(new Dimension(100, 40));
		SharedVar.area.setTime(SharedVar.timePanel);
		SharedVar.timePanel.setArea(SharedVar.area);
		add(SharedVar.timePanel, BorderLayout.NORTH);
		add(SharedVar.area, BorderLayout.CENTER);
	}
}
