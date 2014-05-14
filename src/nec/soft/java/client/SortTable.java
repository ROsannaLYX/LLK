package nec.soft.java.client;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.kkf.UserInfo;


// ���а�
public class SortTable
{
	//����һά������Ϊ�б���
	Object[] columnTitle = {"����" , "����"};
	
	//private ExtendedTableModel model;
	//�Զ�ά�����һά����������һ��JTable����
	JTable table;

	//��ԭ������model��װ���µ�SortFilterModel����
	SortableTableModel sorterModel ;
	
	//�������е�����
	private int sortColumn =0;
	//sortColumn�������е���
	public SortTable(int sortColumn , java.util.List<UserInfo> userInfo)
	{
		this.sortColumn = sortColumn;
		//this.model = model;
		Object[][] data = 
			new Object[userInfo.size()][columnTitle.length];
		for(int i = 0; i < userInfo.size(); i++)
		{
			data[i] = new Object[]{userInfo.get(i).getName()
				, userInfo.get(i).getScore()};
		}
		table = new JTable(data , columnTitle);
		sorterModel = new SortableTableModel(table.getModel());
		table.setModel(sorterModel);
		sorterModel.sort(sortColumn);
	}

	public JTable getTalbe()
	{
		return table;
	}
}

class SortableTableModel extends AbstractTableModel
{
	private TableModel model;
	private int sortColumn;
	private Row[] rows;

	//��һ���Ѿ�����TableModel�����װ��SortableTableModel����
	public SortableTableModel(TableModel m)
	{
		//������װ��TableModel����
		model = m;
		rows = new Row[model.getRowCount()];
		//��ԭTableModel�е�ÿ�м�¼������ʹ��Row���鱣������
		for (int i = 0; i < rows.length; i++)
		{  
			rows[i] = new Row(i);
		}
	}

	//ʵ�ָ���ָ���н�������
	public void sort(int c)
	{  
		sortColumn = c;
		Arrays.sort(rows);
		fireTableDataChanged();
	}

	//��������������Ҫ����Model�е����ݣ������漰����Model������
	//�ͱ���װModel�����е�����ת��������ʹ��rows�����������ת����
	public Object getValueAt(int r, int c)
	{
		return model.getValueAt(rows[r].index, c);
	}

	public boolean isCellEditable(int r, int c) 
	{ 
		return model.isCellEditable(rows[r].index, c);
	}

	public void setValueAt(Object aValue, int r, int c) 
	{ 
		model.setValueAt(aValue, rows[r].index, c);
	}

	//���淽����ʵ�ְѸ�model�ķ���ί��Ϊԭ��װ��model��ʵ��
	public int getRowCount() 
	{
		return model.getRowCount(); 
	}
	public int getColumnCount()
	{
		return model.getColumnCount();
	}
	public String getColumnName(int c)
	{
		return model.getColumnName(c); 
	}
	public Class getColumnClass(int c) 
	{
		return model.getColumnClass(c);
	}

	//����һ��Row�࣬�������ڷ�װJTable�е�һ�С�
	//ʵ������������װ�����ݣ���ֻ��װ������
	private class Row implements Comparable<Row>
	{
		//��index�����ű���װModel��ÿ�м�¼��������
		public int index;
		public Row(int index)
		{
			this.index = index;
		}
		//ʵ������֮��Ĵ�С�Ƚ�
		public int compareTo(Row other)
		{  
			Object a = model.getValueAt(index, sortColumn);
			Object b = model.getValueAt(other.index, sortColumn);
			if (a instanceof Comparable)
			{
				return ((Comparable)a).compareTo(b);
			}
			else
			{
				return a.toString().compareTo(b.toString());
			}
		}
	}
}