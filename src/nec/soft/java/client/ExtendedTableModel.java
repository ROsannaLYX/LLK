package nec.soft.java.client;

import javax.swing.table.*;
import java.util.*;
import com.kkf.*;

class ExtendedTableModel extends DefaultTableModel {
	// �����������еĶ�Ӧ��ϵ
	private ArrayList<String> rowIndex = new ArrayList<String>();
	private final int SCORE_COL = 3;

	public ExtendedTableModel() {
		super();
	}

	// ��дisCellEditable������ʹ�û��޷����ı������
	public boolean isCellEditable(int r, int c) {
		return false;
	}

	// �����ṩһ�����������ù�������ʵ��ί�и�DefaultTableModel����
	public ExtendedTableModel(String[] columnNames, Object[][] cells) {
		super(cells, columnNames);
	}

	public ExtendedTableModel(Object[] columnNames, List<UserInfo> userInfo) {
		super();
		reSetTable(columnNames, userInfo);

	}

	// ����Table
	public void reSetTable(Object[] columnNames, List<UserInfo> userInfo) {
		rowIndex.clear();
		Object[][] cells = new Object[userInfo.size()][columnNames.length];
		System.out.println(userInfo.size() + " " + columnNames.length);
		for (int i = 0; i < userInfo.size(); i++) {
			cells[i] = new Object[] { userInfo.get(i).getIconIndex(),
					userInfo.get(i).getName(), userInfo.get(i).getSex(),
					userInfo.get(i).getScore() };
			rowIndex.add(userInfo.get(i).getName());
			// addRow(cells[i]);
		}
		setDataVector(cells, columnNames);
		setColumnIdentifiers(columnNames);
	}

	// ����з���
	public void addRow(UserInfo user) {
		Object[] rowData = new Object[] { user.getIconIndex(), user.getName(),
				user.getSex(), user.getScore() };
		addRow(rowData);
		rowIndex.add(user.getName());
	}

	// ɾ���з���,�����û�������ɾ����
	public void removeRow(String name) {
		int row = rowIndex.indexOf(name);
		this.removeRow(row);
		rowIndex.remove(row);
	}

	// ��дgetColumnClass����������ÿ�еĵ�һ��ֵ����������ʵ����������
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void updateScore(int socre, String name) {
		int row = rowIndex.indexOf(name);
		setValueAt(socre, row, SCORE_COL);
	}
}
