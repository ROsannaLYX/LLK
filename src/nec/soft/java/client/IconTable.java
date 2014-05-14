package nec.soft.java.client;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

//��� ��ͷ���������Ա𡢻��֣�
public class IconTable extends JTable {
	// ��ģʽ
	private int tableStyle;

	// ��ʾ��һ����ͼ��,���û��ͼ��Ϊ-1
	private int iconCol = -1;

	// ͼ��ģʽ,�����е�Ԫ����ͼ��
	public static final int ALL_CELL_ICON = 1;

	// ����ģʽ,��Ԫ�񱣴����������
	public static final int GENERAL = 2;

	public IconTable() {
		super();
	}

	// inconCol������һ����ͼ��,tableStyle������ģʽ
	public IconTable(ExtendedTableModel model, int iconCol, int tableStyle) {
		super(model);
		this.tableStyle = tableStyle;
		this.iconCol = iconCol;
		System.out.println("gouzaoqi  IconTable");
		init();
	}

	// ��ʼ����
	public void init() {
		if (tableStyle == ALL_CELL_ICON) {
			System.out.println("ALL_CELL_ICON");

			// ��ȡÿһ��
			TableColumn col;
			for (int i = 0; i < getColumnCount(); i++) {
				col = getColumnModel().getColumn(i);
				// �Ը��в����Զ���ĵ�Ԫ�������
				col.setCellRenderer(new GenderTableCellRenderer());
				System.out.println("ALL_CELL_ICON");
			}
		} else if (tableStyle == GENERAL && iconCol != -1) {
			if (getColumnModel().getColumnCount() == 0) {
				return;
			}
			TableColumn col = getColumnModel().getColumn(iconCol);
			col.setCellRenderer(new GenderTableCellRenderer());
		}
	}

	class GenderTableCellRenderer extends JPanel implements TableCellRenderer {
		private int cellValue;
		// ����ͼ��Ŀ�Ⱥ͸߶�
		final int ICON_WIDTH = 30;
		final int ICON_HEIGHT = 30;

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			cellValue = (Integer) value;
			table.setRowHeight(50);

			// ����ѡ��״̬�»��Ʊ߿�
			if (hasFocus) {
				setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			} else {
				setBorder(null);
			}
			return this;
		}

		// ��дpaint������������Ƹõ�Ԫ������
		public void paint(Graphics g) {
			// ���ݵ�Ԫ��ֵ��Ϊ��������ͼ��
			if (cellValue >= 1 && cellValue <= 20) {
				drawImage(g, HeadIcon.headIcon[cellValue].getImage());

			}
		}

		// ����ͼ��ķ���
		private void drawImage(Graphics g, Image image) {
			if (tableStyle == GENERAL) {
				g.drawImage(image, (getWidth() - ICON_WIDTH) / 6, (getHeight() - ICON_HEIGHT) / 6,
						40, 40, null);
			} else {
				g.drawImage(image, (getWidth() - ICON_WIDTH) / 6, (getHeight() - ICON_HEIGHT) / 6,
						null);
			}

		}
	}
}
