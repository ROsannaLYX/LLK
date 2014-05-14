package nec.soft.java.fight;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AboutTable {
	LinkedList<User> allUser; // 进入大厅的所有用户的数据
	Object[][] tableData; // 表格所展示的数据
	String[] columnNames = { "头像", "昵称", "账号", "积分" }; // 表的每一列的名字
	JTable table; // 所要展示的表格

	public AboutTable(LinkedList<User> all) {
		allUser = all;
		User temp;
		tableData = new Object[allUser.size()][4];
		for (int i = 0; i < allUser.size(); i++) {
			temp = allUser.get(i);
			tableData[i][0] = temp.headURL;
			tableData[i][1] = temp.name;
			tableData[i][2] = temp.number;
			tableData[i][3] = temp.score;
		}
		table = new JTable(getTableModel(tableData, columnNames)){ // 设置jtable的单元格为透明的
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		//table.setShowGrid(false);
		table.setRowSelectionAllowed(false);
		table.setRowHeight(40);
		// 获取第一列
		TableColumn FirstColumn = table.getColumnModel().getColumn(0);
		// 对第一列采用自定义的单元格绘制器
		FirstColumn.setCellRenderer(new GenderTableCellRenderer());
	}

	ExtendedTableModel getTableModel(Object[][] cells, String[] columnNames) {
		return new ExtendedTableModel(cells, columnNames);
	}
}

class ExtendedTableModel extends DefaultTableModel {
	// 重新提供一个构造器，该构造器的实现委托给DefaultTableModel父类
	public ExtendedTableModel(Object[][] cells, String[] columnNames) {
		super(cells, columnNames);
	}

	// 重写getColumnClass方法，根据每列的第一个值来返回其真实的数据类型
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	// 重写isCellEditable方法，使用户无法更改表格数据
	public boolean isCellEditable(int r, int c) {
		return false;
	}
}

class GenderTableCellRenderer extends JPanel implements TableCellRenderer {
	Image image;
	private String cellValue;
	// 定义图标的宽度和高度
	final int ICON_WIDTH = 35;
	final int ICON_HEIGHT = 35;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		cellValue = (String) value;
		// 设置选中状态下绘制边框
		if (hasFocus) {
			// setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(null);
		}
		return this;
	}

	// 重写paint方法，负责绘制该单元格内容
	public void paint(Graphics g) {
		try {
			image = ImageIO.read(new File(cellValue));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, 0, 0, ICON_WIDTH, ICON_HEIGHT, this);
	}

}