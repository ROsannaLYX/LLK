package nec.soft.java.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nec.soft.java.ui.ControlPanel;
import nec.soft.java.ui.DrawArea;
import nec.soft.java.ui.TimePanel;
import nec.soft.java.utils.Checker;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.Mode;
import nec.soft.java.utils.ShowHelper;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

public class TestDraw extends JPanel {

	private static final long serialVersionUID = 1L;
	private TimePanel lifePanel;
	JFrame frame;

	/*@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.red);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 50, 50);	
	}*/

	public TestDraw() {
		init();
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			/*UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
			UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());*/
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		TestDraw draw = new TestDraw();
		draw.lifePanel.start();

	}

	private void init() {
		JFrame frame = new JFrame();
		testDrawArea(frame);
		frame.setTitle("≤‚ ‘");
		/*frame.setLocation(100, 100);*/
		frame.setSize(1000, 600);
		ShowHelper.showCenter(frame);
		/*frame.setResizable(false);*/
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void testDrawArea(JFrame frame) {
		/*Node[][] nodes = new Node[Constants.NODES_ROW][Constants.NODES_COLUMN];
		for(int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				nodes[i][j] = new Node(i, j, new Random().nextInt(10));
			}
		}*/
		/*int[][] nodes = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 15, 17, 4, 4, 0, 5, 15, 12, 11, 13, 3, 6, 1, 18, 0 },
				{ 0, 2, 4, 9, 5, 0, 20, 12, 19, 1, 17, 16, 10, 6, 0, 0, },
				{ 0, 4, 7, 0, 14, 13, 1, 4, 14, 13, 5, 20, 1, 14, 15, 0 },
				{ 0, 11, 12, 0, 0, 5, 10, 6, 7, 17, 6, 16, 3, 9, 0, 0 },
				{ 0, 13, 19, 0, 15, 14, 8, 9, 6, 0, 0, 19, 12, 20, 2, 0 },
				{ 0, 13, 10, 1, 12, 17, 11, 7, 5, 18, 14, 7, 9, 14, 11, 0 },
				{ 0, 19, 3, 9, 6, 5, 12, 2, 9, 11, 16, 16, 4, 18, 7, 0 },
				{ 0, 16, 18, 2, 3, 10, 20, 13, 1, 8, 15, 7, 15, 16, 11, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };*/
		
		
		/*int[][] nodes = new int[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
		{0,0,6,8,14,17,10,14,12,3,8,16,0,0,18,1,1,0,},
		{0,0,10,15,14,5,14,1,9,4,1,13,13,3,3,20,4,0,},
		{0,1,9,9,13,16,13,16,7,18,14,12,7,14,13,1,12,0,},
		{0,0,13,13,6,13,10,5,14,8,1,17,9,20,5,2,14,0,},
		{0,18,1,12,10,3,17,8,15,4,17,15,16,5,7,15,13,0,},
		{0,15,12,5,10,4,1,6,10,11,8,2,1,11,19,8,16,0,},
		{0,6,11,13,11,10,10,10,2,6,1,1,7,11,14,3,3,0,},
		{0,16,5,19,14,1,10,3,6,11,15,6,6,12,3,18,1,0,},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,}
		};*/

		/*for(int i = 1; i < Constants.NODES_ROW -1; i++) {
			for(int j = 1; j < Constants.NODES_COLUMN - 1; j++) {
				nodes[i][j] = random.nextInt(6);
			}
		}*/

		/*int[][] nodes = new int[Constants.NODES_ROW][Constants.NODES_COLUMN];*/
		int[][] nodes = Checker.newGame(Constants.NODES_COLUMN-2, Constants.NODES_ROW-2, Constants.TOTAL_IMAGES, Mode.down);
		/*for(int i = 1; i < nodes.length-1; i++) {
			for (int j  = 1;  j< nodes[0].length-1; j++) {
				nodes[i][j] = 1;
			}
		}*/
		DrawArea area = new DrawArea(nodes);
		lifePanel = new TimePanel();
		lifePanel.setPreferredSize(new Dimension(100, 40));
		ControlPanel controlPanel = new ControlPanel(area, lifePanel);
		controlPanel.setPreferredSize(new Dimension(100, 100));
		frame.add(controlPanel, BorderLayout.EAST);
		/*TopPanel top = new TopPanel();
		top.setPreferredSize(new Dimension(150,80));*/
		area.setTime(lifePanel);
		frame.add(lifePanel, BorderLayout.NORTH);
		frame.add(area, BorderLayout.CENTER);
		
		/*BackMusic music = new BackMusic();
		music.play();*/

		/*for(int i = 0; i < Constants.NODES_ROW; i++) {
			for(int j = 0; j < Constants.NODES_COLUMN; j++) {
				System.out.print(nodes[i][j] +"      ");
			}
			System.out.println();
		}*/
	}

}
