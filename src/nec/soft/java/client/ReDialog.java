package nec.soft.java.client;
import static com.kkf.MyProtocol.NAME_REP;
import static com.kkf.MyProtocol.REGISTER;
import static com.kkf.MyProtocol.REGISTER_ERROR;
import static com.kkf.MyProtocol.REGISTER_SUCCESS;
import static com.kkf.MyProtocol.SPLIT_SIGN;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

//ע���
class ReDialog extends JDialog
{
	
	private static final long serialVersionUID = 4005245268448235660L;
	private final int WIDTH = 360;
	private final int HEIGHT = 400;
	//ͷ����������
	private final int HEAD_ROW = 4;
	private final int HEAD_COL = 5;
	private JLabel nameLb = new JLabel("�û���:");
	private JLabel passwdLb = new JLabel("����:");
	private JLabel sexLb = new JLabel("�Ա�:");
	private JLabel headLb = new JLabel("ͷ��:");
	private JLabel headIconLb = new JLabel();
	
	
	private String[] sexStr = {"��" , "Ů"};
	//����ͷ����ͼ������
	private Object[][] headsIndex = new Object[HEAD_ROW][HEAD_COL];
	private String[] headTile = {"sdf","sdf","df","fsd"};
	
	
	//�û���
	private JTextField nameText = new JTextField(16);
	//����
	private JPasswordField passwdText = new JPasswordField();
	private JComboBox sexBox = new JComboBox(sexStr); 
	
	
	private JButton okBt = new JButton("�ύ");
	private JButton cancel = new JButton("ȡ��");
	
	
	//Ĭ��ͷ������
	private int indexIcon = 2;
	//ͷ���
	public IconTable headTable;
	//ͷ����Ӧ��modelģ��
	private ExtendedTableModel model;
	
	
//	private static ReDialog rdlg;
	
	
	public ReDialog()
	{
	}

	public ReDialog(Frame owner, String title , boolean modal)
	{
		super(owner , title , modal);
		
		this.setDefaultCloseOperation(0);
	}
	
	//��ʼ����
	{
		int imageNum = 0;
		//��ʼ������ͼ�������
		for(int i = 0 ; i < HEAD_ROW ; i++)
		{
			for(int j = 0; j < HEAD_COL ; j++)
			{
				headsIndex[i][j] = (++ imageNum);
			}
		}

		//��ʼ��ͷ���
		model = new ExtendedTableModel(headTile , headsIndex);
		headTable = new IconTable(
			model , 0 , IconTable.ALL_CELL_ICON);
		//��ʼ�����
		initComment();
		initListener();
	}

	public void initComment()
	{
		setLayout(null);
		passwdText.setEchoChar('��');
		ImageIcon backImage = new ImageIcon("image/QinShiMingYue/registerBG.jpg");
		//Label���
		nameLb.setBounds(10,25,50,30);
		passwdLb.setBounds(10,55,50,30);
		sexLb.setBounds(10,87,50,30);
		
		headLb.setBounds(220,55,55,30);
		add(sexLb);
		add(nameLb);
		add(passwdLb);
		add(headLb);
		
		//�û��������
		nameText.setBounds(60,30,130,20);
		passwdText.setBounds(60,60,130,20);
		sexBox.setBounds(60,90,50,30);
		add(passwdText);
		add(nameText);
		add(sexBox);
		
		//ͷ��
		headIconLb.setBounds(260,40,60,60);
		headIconLb.setIcon(HeadIcon.headIcon[indexIcon]);
		headIconLb.setBorder(
			BorderFactory.createLineBorder(new Color(0 , 0 ,0)));
		headTable.setBounds(0 , 170, WIDTH ,230);
		headTable.setBorder(
			BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(headTable);
		add(headIconLb);
		
		//��ť
		okBt.setSize(50,25);
		okBt.setBounds(230,130,60,30);
		cancel.setBounds(290,130,60,30);
		add(okBt);
		add(cancel);
		
		JLabel bakcLb = new JLabel();
		bakcLb.setBounds(0 , 0 ,WIDTH , 220);
		bakcLb.setIcon(backImage);
		add(bakcLb);
		setBounds(300,250,WIDTH,HEIGHT);
	}
	
	//��ʼ��������
	public void initListener()
	{
		headTable.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//��õ�Ԫ���ֵ
				int col = headTable.getSelectedColumn();
				int row = headTable.getSelectedRow();
				indexIcon = (Integer)headTable.getValueAt(row, col);
				headIconLb.setIcon(HeadIcon.headIcon[indexIcon]);
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		okBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = nameText.getText();
				String sex = (String)sexBox.getSelectedItem();
				char[] passwd = passwdText.getPassword();
				 
				if(name.length() == 0 || name == null)
				{
					 JOptionPane.showMessageDialog(
						 null, "�û�������Ϊ��");
					 return;
				}
				if(passwd.length == 0 || passwd == null)
				{
					JOptionPane.showMessageDialog(
						null, "���벻��Ϊ��"); 
					 return;
				}
				if(name.length() > 10 || name.length() < 3)
				{
					JOptionPane.showMessageDialog(
						null, "�û���Ӧ����3��10λ֮��"); 
					 return;
				}
				if(passwd.length > 10 || passwd.length < 3)
				{
					JOptionPane.showMessageDialog(
						null, "����Ӧ����3��10λ֮��"); 
					 return;
				}
				//�������
				Client.initScoket(Login.ipStr);
				Client.ps.println(REGISTER + name 
					+ SPLIT_SIGN + String.valueOf(passwd) 
					+ SPLIT_SIGN + sex
					+ SPLIT_SIGN + indexIcon
					+ REGISTER);
				
				String result ="";
				try
				{
					result=Client.brServer.readLine();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

				if(result.equals(REGISTER_SUCCESS))
				{
					System.out.println(result);
					JOptionPane.showMessageDialog(
						null, "ע��ɹ���"); 
					//�ر�����
					Client.closeRs();
					dispose();
				}

				if(result.equals(NAME_REP))
				{
					JOptionPane.showMessageDialog(
						null, "�û����ظ�"); 
					System.out.println(result);
						
				}

				if(result.equals(REGISTER_ERROR))
				{
					JOptionPane.showMessageDialog(
						null, "ע��ʧ��,���������ݿ����"); 
						
				}

				//�ر�����
				Client.closeRs();
			}
		});
	}

	
	
	
	/*public void dispose()
	{
		rdlg.dispose();
	}
	
	public static void open()
	{
		rdlg = new ReDialog();
		///////////////
	}
	
	public static void close()
	{
		if(rdlg!=null)
			rdlg.dispose();
		
		rdlg = null;
	}*/
	
	
	
	public static void main(String atgs[])
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new ReDialog().setVisible(true);
	}
}