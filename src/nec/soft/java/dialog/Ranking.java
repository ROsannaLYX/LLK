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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 这个类有两个方法:
 * 1.store:将数据存入ranking.txt中。参数见方法体
 * 2.show:显示排行榜窗口，无参数
 * @author andong
 *
 */

public class Ranking {
	private String path = "ranking.ini";
	/*private String bgPath = "resource/bg.jpg";
	private String logoPath = "resource/logo.gif";*/
	private File file;
	private PrintWriter out;
	private FileWriter fw;

	public Ranking(){
		file = new File(path);
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
		}
		
	}
	/**
	 * 存储游戏记录到ranking.txt
	 * @param dif 难度，从1到4
	 * @param name 玩家姓名，如果为空字符串或null，将显示“无名氏”
	 * @param score 得分
	 */
	public void store(int dif, String name, int score) {
		out = new PrintWriter(fw);
		if(name==null||name.equals("")){
			name = "无名氏";
		}
		out.println(dif + "," + name + "," + score);
		// out.close();
		out.flush();
	}
	
	public void clear() {
		try {
			out = new PrintWriter(file);
		} catch (FileNotFoundException e) {
		}
		out.print("");
		out.flush();
	}
	
	public void show() {
		Rank rank = new Rank(path);
		rank.show(1);
	}
	
	class Rank extends JFrame{
		private String bgPath = "resource/bg.jpg";
		private String logoPath = "resource/logo.gif";
		private File file;
		private Scanner in;
		
		private JTable table;
		private Object[] title = { "名次", "玩家", "得分" };
		private final int COL = 3;
		private Object[][] rank;
		private int which;

		private JPanel topPanel;
		private JPanel centerPanel;
		private JPanel bottomPanel;
		private JLabel titleLabel;
		private JButton[] gotoBtn = new JButton[5];
		
		public Rank(String path){
			file = new File(path);
			try {
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

			Image img = kit.getImage(logoPath);
			setIconImage(img);

			gotoBtn[0] = new JButton("查看简单模式");
			gotoBtn[1] = new JButton("查看中等模式");
			gotoBtn[2] = new JButton("查看困难模式");
			gotoBtn[3] = new JButton("查看疯狂模式");
			gotoBtn[4] = new JButton("清空排行榜");

			gotoBtn[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					which = 1;
					btnCtrl(which-1);
					show(1);
				}
			});
			gotoBtn[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					which = 2;
					btnCtrl(which-1);
					show(2);
				}
			});
			gotoBtn[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					which = 3;
					btnCtrl(which-1);
					show(3);
				}
			});
			gotoBtn[3].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					which = 4;
					btnCtrl(which-1);
					show(4);
				}
			});
			gotoBtn[4].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int b = JOptionPane.showConfirmDialog(null, "您确定要清空排行榜吗？这将无法撤销！");
					if(b==JOptionPane.YES_OPTION){
						clear();
						show(which);
					}
				}
			});
			
			topPanel = new JPanel();
			centerPanel = new JPanel();
			bottomPanel = new JPanel();
			titleLabel = new JLabel("排行榜");
			titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			titleLabel.setFont(new Font("华文行楷", 0, 32));
			topPanel.add(titleLabel);
//			bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
			for (int i = 0; i < gotoBtn.length; i++) {
				bottomPanel.add(gotoBtn[i]);
			}

			setLayout(new BorderLayout());
			add(topPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.SOUTH);
			
			setVisible(true);
		}
		
		public void btnCtrl(int des){
			for(int i=0;i<gotoBtn.length;i++){
				if(i!=des)
					gotoBtn[i].setEnabled(true);
				else
					gotoBtn[i].setEnabled(false);
			}
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
				centerPanel.setLayout(new BorderLayout());
				centerPanel.add(new JScrollPane(table),BorderLayout.CENTER);
//				table.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
//				table.setBackground(Color.LIGHT_GRAY);
				DefaultTableCellRenderer r = new DefaultTableCellRenderer(); 
				r.setHorizontalAlignment(JLabel.CENTER); 
				table.setDefaultRenderer(Object.class, r);
				table.setRowHeight(20);
				table.setFont(new Font("微软雅黑", 0, 14));
				table.setGridColor(Color.BLUE);
				table.setSize(centerPanel.getWidth(), centerPanel.getHeight());
				table.setRowSelectionAllowed(false);
				in.close();
				setVisible(true);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "找不到排行榜记录！");
			}
		}
	}
	
	class record implements Comparable<record> {
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
		Ranking r = new Ranking();
		r.store(1, "lile", 0);
		r.store(1, null, 90);
		r.store(1, "", 80);
		r.store(1, "why_not", 100);
		r.show();
	}
}
