package nec.soft.java.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Suanfa {

	private JFrame frame;
	public Suanfa() {
		init();
	}

	
	private void init() {
		frame = new JFrame();
		addPanel();
		frame.setTitle("≤‚ ‘");
		frame.setLocation(400,100);
		frame.setSize(500,500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void addPanel() {
		JPanel main = new JPanel();
		frame.add(main, BorderLayout.CENTER);
		
		GridLayout grid = new GridLayout(8,8);
		main.setLayout(grid);
		
		JButton[][] buttons = new JButton[8][8];
		for(int i = 0;i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setText(i+j+"");
				main.add(buttons[i][j]);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Suanfa s = new Suanfa();
	}
}
