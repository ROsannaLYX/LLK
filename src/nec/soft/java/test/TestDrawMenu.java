package nec.soft.java.test;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nec.soft.java.dialog.SetDialog;
import nec.soft.java.ui.MenuPanel;
import nec.soft.java.ui.TimePanel;
import nec.soft.java.utils.ShowHelper;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

public class TestDrawMenu {

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

	public TestDrawMenu() {
		init();
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			/*UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());*/
			/*UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());*/
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new TestDrawMenu();
		/*SetDialog.open();*/

	}

	private void init() {
		JFrame frame = new JFrame();
		testDrawArea(frame);
		frame.setTitle("≤‚ ‘");
		/*frame.setLocation(100, 100);*/
		frame.setSize(750, 500);
		ShowHelper.showCenter(frame);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void testDrawArea(JFrame frame) {
		MenuPanel panel = new MenuPanel();
		frame.add(panel,BorderLayout.CENTER);
	}
}
