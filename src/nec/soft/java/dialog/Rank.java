package nec.soft.java.dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

/**
 * 排行榜相关的类 1.存储得分到排行榜； 2.获取排行榜并显示
 * 存储为store方法，具体请看方法体
 * 显示为show方法，具体请看方法体
 * 此外，还有清空文件的clear方法
 * @author andong(why_not)
 * 
 */
public class Rank extends JFrame {

	private String path = "ranking.ini";
	/*private String bgPath = "resource/bg.jpg";
	private String logoPath = "resource/logo.gif";*/
	private File file;
	private Scanner in;
	private PrintWriter out;
	private FileWriter fw;
	private JTable table;
	private Object[] title = { "名次", "玩家", "得分" };
	private final int COL = 3;
	private Object[][] rank;

	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JLabel titleLabel;
	private JButton[] gotoBtn = new JButton[4];

	public Rank() {
		try {
			/*File file = new File("record");*/
			file = new File(path);
			if(!file.exists())
				file.createNewFile();
			fw = new FileWriter(file, true);
		} catch (IOException e) {
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("连连看排行榜");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension scrSize = kit.getScreenSize();
		setSize(scrSize.width / 2, scrSize.height / 2);
		setLocation(scrSize.width / 4, scrSize.height / 4);
		setResizable(false);

//		ImageIcon bg = new ImageIcon(bgPath);
//		JLabel imgLabel = new JLabel(bg);
//		getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
//		imgLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
//		Container cp = getContentPane();
//		((JPanel) cp).setOpaque(false);

		/*Image img = kit.getImage(logoPath);
		setIconImage(img);*/

		gotoBtn[0] = new JButton("查看简单模式");
		gotoBtn[1] = new JButton("查看中等模式");
		gotoBtn[2] = new JButton("查看困难模式");
		gotoBtn[3] = new JButton("查看疯狂模式");

		gotoBtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(1);
			}
		});
		gotoBtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(2);
			}
		});
		gotoBtn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(3);
			}
		});
		gotoBtn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(4);
			}
		});
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		titleLabel = new JLabel("排行榜");
		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titleLabel.setFont(new Font("华文行楷", 0, 32));
		topPanel.add(titleLabel);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		for (int i = 0; i < 4; i++) {
			bottomPanel.add(gotoBtn[i]);
		}

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(false);

	}

	public void store(int dif, String name, int score) {
		out = new PrintWriter(fw);
		out.println(dif + "," + name + "," + score);
		// out.close();
		out.flush();
	}

	public void clear() {
		out = new PrintWriter(fw);
		out.print("");
		out.flush();
	}
	
	public void show(int dif) {
		try {
			in = new Scanner(file);
			ArrayList<record> rec = new ArrayList<record>();
			while (in.hasNext()) {
				String str = in.nextLine();
				String[] sp = str.split(",");
				if (dif == Integer.parseInt(sp[0])) {
					rec.add(new record(sp[1], Integer.parseInt(sp[2])));
				}
			}
			Collections.sort(rec);
			rank = new Object[rec.size()][COL];
			for (int i = 0; i < rec.size(); i++) {
				record temp = rec.get(i);
				rank[i][0] = i + 1;
				rank[i][1] = temp.name;
				rank[i][2] = temp.score;
			}

			table = new JTable(rank, title);
			centerPanel.removeAll();
			centerPanel.add(new JScrollPane(table));
			table.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			table.setBackground(Color.LIGHT_GRAY);
			table.setFont(new Font("微软雅黑", 0, 14));
			table.setSize(centerPanel.getWidth(), centerPanel.getHeight());
			table.setRowSelectionAllowed(false);
			in.close();
			setVisible(true);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "找不到排行榜记录！");
		}
	}

	private class record implements Comparable<record> {
		private String name;
		private int score;

		public record(String name, int score) {
			this.name = name;
			this.score = score;
		}

		public int compareTo(record o) {
			if (this.score < o.score) {
				return 1;
			} else if (this.score > o.score) {
				return -1;
			} else {
				return this.name.compareTo(o.name);
			}
		}
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Rank f = new Rank();
		f.store(1, "why_not", 100);
		f.store(2, "andong", 100);
		f.store(3, "zhjk", 100);
		f.store(4, "god", 100);
		f.setVisible(true);
		// f.show(1);
	}
}
