package nec.soft.java.client1;
import static com.kkf.MyProtocol.HOUSE_INFO;
import static com.kkf.MyProtocol.LOGIN;
import static com.kkf.MyProtocol.LOGIN_AGAIN;
import static com.kkf.MyProtocol.LOGIN_ERROR;
import static com.kkf.MyProtocol.LOGIN_SUCCESS;
import static com.kkf.MyProtocol.SPLIT_SIGN;
import static com.kkf.MyProtocol.STOP;
import static com.kkf.MyProtocol.UPDATE_ALL;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.kkf.ParseStr;
import com.kkf.UserFactory;
import com.kkf.UserInfo;

public class Login {
	private JFrame jf = new JFrame("��½");
	private ImageIcon backImage;

	// Ip�����ı�
	private JTextField ipText = new JTextField(16);
	// �û���
	private JTextField nameText = new JTextField(16);
	// ����
	private JPasswordField passwdText = new JPasswordField(16);

	private JLabel ipLb = new JLabel("����IP:");
	private JLabel nameLb = new JLabel("�û���:");
	private JLabel passwdLb = new JLabel("����:");

	private JButton registerBt = new JButton("ע��");
	private JButton loginBt = new JButton("��½");
	private JButton cancelBt = new JButton("�˳�");

	private String result;
	public static String ipStr = "127.0.0.1";
	private final int TABLE_WIDTH = 407;
	private final int TABLE_HEIGHT = 272;

	public void init() throws Exception {
		jf.setLayout(null);
		jf.setResizable(false);
		backImage = new ImageIcon("image/back.jpg");

		JLabel label = new JLabel("ע����¼֮ǰ������������������IP");
		// Label���
		label.setBounds(60, 35, 250, 30);
		ipLb.setBounds(60, 55, 50, 30);
		nameLb.setBounds(60, 85, 50, 30);
		passwdLb.setBounds(60, 115, 50, 30);
		jf.add(label);
		jf.add(ipLb);
		jf.add(nameLb);
		jf.add(passwdLb);

		// ----------------Text���---------------
		// Ĭ���ʱ���IP
		ipText.setText(ipStr);
		// ��������ʱ���ַ�
		passwdText.setEchoChar('��');
		ipText.setBounds(115, 60, 130, 20);
		nameText.setBounds(115, 90, 130, 20);
		passwdText.setBounds(115, 120, 130, 20);
		jf.add(ipText);
		jf.add(nameText);
		jf.add(passwdText);

		// ��ť���
		registerBt.setBounds(60, 180, 60, 30);
		jf.add(registerBt);
		loginBt.setBounds(150, 180, 60, 30);
		jf.add(loginBt);
		cancelBt.setBounds(240, 180, 60, 30);
		jf.add(cancelBt);

		// jf.add(registerBt);

		JLabel bakcLb = new JLabel();
		bakcLb.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		bakcLb.setIcon(backImage);
		jf.add(bakcLb);

		// ��ʼ������
		initListener();
		jf.setBounds(250, 250, TABLE_WIDTH, TABLE_HEIGHT);
		// jf.setUndecorated(true);
		jf.setVisible(true);

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
		new Login().init();
	}
}
