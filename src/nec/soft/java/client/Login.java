package nec.soft.java.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nec.soft.java.frame.MenuFrame;
import nec.soft.java.utils.ShowHelper;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

import com.kkf.ParseStr;
import com.kkf.UserFactory;
import com.kkf.UserInfo;
import static com.kkf.MyProtocol.*;

public class Login {
	private JFrame jf = new JFrame("��½");
	private ImageIcon backImage;

	private static Login lg;

	// Ip�����ı�
	private JTextField ipText = new JTextField(16);
	// �û���
	private JTextField nameText = new JTextField(16);
	// ����
	private JPasswordField passwdText = new JPasswordField(16);

	private JLabel ipLb = new JLabel("IP:");
	private JLabel nameLb = new JLabel("�˻�:");
	private JLabel passwdLb = new JLabel("����:");

	private JButton registerBt = new JButton("ע��");
	private JButton loginBt = new JButton("��½");
	private JButton cancelBt = new JButton("�˳�");

	private String result;
	public static String ipStr = "127.0.0.1";
	private final int TABLE_WIDTH = 750;
	private final int TABLE_HEIGHT = 505;

	public void init() throws Exception {
		jf.setLayout(null);
		jf.setResizable(false);
		// backImage = new ImageIcon("image/back.jpg");
		backImage = new ImageIcon("image/QinShiMingYue/52.jpg");

		JLabel label = new JLabel("�����������������IP");
		// Label���
		label.setBounds(230, 260, 250, 30);
		label.setForeground(Color.red);
		label.setFont(new Font("Monospaced", Font.ITALIC, 20));
		ipLb.setBounds(230, 290, 50, 30);
		ipLb.setForeground(Color.red);
		ipLb.setFont(new Font("Monospaced", Font.ITALIC, 15));
		nameLb.setBounds(230, 320, 50, 30);
		nameLb.setForeground(Color.red);
		nameLb.setFont(new Font("Monospaced", Font.ITALIC, 15));
		passwdLb.setBounds(230, 350, 50, 30);
		passwdLb.setForeground(Color.red);
		passwdLb.setFont(new Font("Monospaced", Font.ITALIC, 15));
		jf.add(label);
		jf.add(ipLb);
		jf.add(nameLb);
		jf.add(passwdLb);

		// ----------------Text���---------------
		// Ĭ���ʱ���IP
		ipText.setText(ipStr);
		// ��������ʱ���ַ�
		passwdText.setEchoChar('��');
		ipText.setBounds(280, 295, 130, 20);
		nameText.setBounds(280, 325, 130, 20);
		passwdText.setBounds(280, 355, 130, 20);
		jf.add(ipText);
		jf.add(nameText);
		jf.add(passwdText);

		// ��ť���
		registerBt.setBounds(230, 395, 60, 30);
		registerBt.setSize(50, 25);
		jf.add(registerBt);
		loginBt.setBounds(300, 395, 60, 30);
		loginBt.setSize(50, 25);
		jf.add(loginBt);
		cancelBt.setBounds(370, 395, 60, 30);
		cancelBt.setSize(50, 25);
		jf.add(cancelBt);

		JLabel bakcLb = new JLabel();
		bakcLb.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		bakcLb.setIcon(backImage);
		jf.add(bakcLb);

		// ��ʼ������
		initListener();
		// jf.setBounds(250,250,TABLE_WIDTH,TABLE_HEIGHT);
		jf.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		ShowHelper.showCenter(jf);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setVisible(true);

		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MenuFrame.open();
				jf.dispose();
			}
		});
	}

	public void initListener() {
		registerBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipStr = ipText.getText();
				new ReDialog(jf, "ע��", true).setVisible(true);
			}
		});

		loginBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipStr = ipText.getText();
				String name = nameText.getText();
				String passwd = String.valueOf(passwdText.getPassword());
				if (name.length() == 0 || name == null) {
					JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
					return;
				}
				if (passwd.length() == 0 || passwd == null) {
					JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
					return;
				}

				// �������
				Client.initScoket(ipStr);
				// ���͵�½����
				Client.ps.println(LOGIN + name + SPLIT_SIGN + passwd + LOGIN);
				String result = "";
				try {
					// ���շ���������������Ϣ
					result = Client.brServer.readLine();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// ����ظ���½
				if (result.equals(LOGIN_AGAIN)) {
					JOptionPane.showMessageDialog(null, "�ظ���½");
					return;
				}

				if (result.equals(LOGIN_SUCCESS)) {
					java.util.List<Integer> gameInfo = new java.util.ArrayList<Integer>();
					try {
						// ����һ��ѭ�����շ������˷������������û�����Ϣ
						while ((result = Client.brServer.readLine()) != null) {
							if (result.startsWith(UPDATE_ALL) && result.endsWith(UPDATE_ALL)) {
								// ��������Ϣ
								String combMsg = ParseStr.getCombMsg(result);
								// ������Ϣ��������
								UserInfo user = UserFactory.createUserInfo(combMsg);
								// ���������û�
								Client.onlineUser.add(user);
							}

							if (result.startsWith(HOUSE_INFO) && result.endsWith(HOUSE_INFO)) {
								// ��������Ϣ
								String combMsg = ParseStr.getCombMsg(result);
								int houseNum = Integer.valueOf(combMsg);
								gameInfo.add(houseNum);
							}

							// ������յ�ֹͣ������Ϣ���˳�ѭ��
							if (result.equals(STOP)) {
								break;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					for (UserInfo user : Client.onlineUser) {
						// ����ǵ�ǰ�û�,�򱣴浱ǰ�û���Ϣ
						if (user.getName().equals(name)) {
							Client.crrentUser = user;
						}
					}

					JOptionPane.showMessageDialog(null, "��¼�ɹ���");
					GameHall hall = new GameHall();
					try {
						new ClientThread(Client.socket, hall).start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					hall.init();
					hall.initPlayingInfo(gameInfo);
					hall.getChat().setChat("��ӭ����������������Ϸ����!");
					jf.dispose();
				} else if (result.equals(LOGIN_ERROR)) {
					JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û��������벻����");
					// �ر�����
					Client.closeRs();
				}
				// System.out.println(Thread.currentThread().getName());
			}
		});

		cancelBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		final Point origin = new Point();
		// ʵ���϶�
		jf.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		jf.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = jf.getLocation();
				jf.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
	}

	public static void main(String[] args) throws Exception {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Login().init();
	}

	public void dispose() {
		jf.dispose();
	}

	public static void open() {
		lg = new Login();
		try {
			lg.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void close() {
		if (lg != null)
			lg.dispose();

		lg = null;
	}
}