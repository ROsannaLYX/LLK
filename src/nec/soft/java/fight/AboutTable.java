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
	LinkedList<User> allUser; // ��������������û�������
	Object[][] tableData; // �����չʾ������
	String[] columnNames = { "ͷ��", "�ǳ�", "�˺�", "����" }; // ���ÿһ�е�����
	JTable table; // ��Ҫչʾ�ı��

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
		table = new JTable(getTableModel(tableData, columnNames)){ // ����jtable�ĵ�Ԫ��Ϊ͸����
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
		// ��ȡ��һ��
		TableColumn FirstColumn = table.getColumnModel().getColumn(0);
		// �Ե�һ�в����Զ���ĵ�Ԫ�������
		FirstColumn.setCellRenderer(new GenderTableCellRenderer());
	}

	ExtendedTableModel getTableModel(Object[][] cells, String[] columnNames) {
		return new ExtendedTableModel(cells, columnNames);
	}
}

class ExtendedTableModel extends DefaultTableModel {
	// �����ṩһ�����������ù�������ʵ��ί�и�DefaultTableModel����
	public ExtendedTableModel(Object[][] cells, String[] columnNames) {
		super(cells, columnNames);
	}

	// ��дgetColumnClass����������ÿ�еĵ�һ��ֵ����������ʵ����������
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	// ��дisCellEditable������ʹ�û��޷����ı������
	public boolean isCellEditable(int r, int c) {
		return false;
	}
}

class GenderTableCellRenderer extends JPanel implements TableCellRenderer {
	Image image;
	private String cellValue;
	// ����ͼ��Ŀ�Ⱥ͸߶�
	final int ICON_WIDTH = 35;
	final int ICON_HEIGHT = 35;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		cellValue = (String) value;
		// ����ѡ��״̬�»��Ʊ߿�
		if (hasFocus) {
			// setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(null);
		}
		return this;
	}

	// ��дpaint������������Ƹõ�Ԫ������
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