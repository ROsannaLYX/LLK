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
	private JFrame jf = new JFrame("登陆");
	private ImageIcon backImage;

	// Ip输入文本
	private JTextField ipText = new JTextField(16);
	// 用户名
	private JTextField nameText = new JTextField(16);
	// 密码
	private JPasswordField passwdText = new JPasswordField(16);

	private JLabel ipLb = new JLabel("主机IP:");
	private JLabel nameLb = new JLabel("用户名:");
	private JLabel passwdLb = new JLabel("密码:");

	private JButton registerBt = new JButton("注册");
	private JButton loginBt = new JButton("登陆");
	private JButton cancelBt = new JButton("退出");

	private String result;
	public static String ipStr = "127.0.0.1";
	private final int TABLE_WIDTH = 407;
	private final int TABLE_HEIGHT = 272;

	public void init() throws Exception {
		jf.setLayout(null);
		jf.setResizable(false);
		backImage = new ImageIcon("image/back.jpg");

		JLabel label = new JLabel("注册或登录之前请先在这里输入主机IP");
		// Label组件
		label.setBounds(60, 35, 250, 30);
		ipLb.setBounds(60, 55, 50, 30);
		nameLb.setBounds(60, 85, 50, 30);
		passwdLb.setBounds(60, 115, 50, 30);
		jf.add(label);
		jf.add(ipLb);
		jf.add(nameLb);
		jf.add(passwdLb);

		// ----------------Text组件---------------
		// 默认问本地IP
		ipText.setText(ipStr);
		// 输入密码时的字符
		passwdText.setEchoChar('●');
		ipText.setBounds(115, 60, 130, 20);
		nameText.setBounds(115, 90, 130, 20);
		passwdText.setBounds(115, 120, 130, 20);
		jf.add(ipText);
		jf.add(nameText);
		jf.add(passwdText);

		// 按钮组件
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

		// 初始化监听
		initListener();
		jf.setBounds(250, 250, TABLE_WIDTH, TABLE_HEIGHT);
		// jf.setUndecorated(true);
		jf.setVisible(true);

	}

	public void initListener() {
		registerBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipStr = ipText.getText();
				new ReDialog(jf, "注册", true).setVisible(true);
			}
		});

		loginBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipStr = ipText.getText();
				String name = nameText.getText();
				String passwd = String.valueOf(passwdText.getPassword());
				if (name.length() == 0 || name == null) {
					JOptionPane.showMessageDialog(null, "用户名不能为空");
					return;
				}
				if (passwd.length() == 0 || passwd == null) {
					JOptionPane.showMessageDialog(null, "密码不能为空");
					return;
				}

				// 获得连接
				Client.initScoket(ipStr);
				// 发送登陆请求
				Client.ps.println(LOGIN + name + SPLIT_SIGN + passwd + LOGIN);
				String result = "";
				try {
					// 接收服务器发过来的信息
					result = Client.brServer.readLine();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// 如果重复登陆
				if (result.equals(LOGIN_AGAIN)) {
					JOptionPane.showMessageDialog(null, "重复登陆");
					return;
				}

				if (result.equals(LOGIN_SUCCESS)) {
					java.util.List<Integer> gameInfo = new java.util.ArrayList<Integer>();
					try {
						// 下面一个循环接收服务器端发过来的所有用户的信息
						while ((result = Client.brServer.readLine()) != null) {
							if (result.startsWith(UPDATE_ALL) && result.endsWith(UPDATE_ALL)) {
								// 获得组合消息
								String combMsg = ParseStr.getCombMsg(result);
								// 根据消息创建对象
								UserInfo user = UserFactory.createUserInfo(combMsg);
								// 保存在线用户
								Client.onlineUser.add(user);
							}

							if (result.startsWith(HOUSE_INFO) && result.endsWith(HOUSE_INFO)) {
								// 获得组合消息
								String combMsg = ParseStr.getCombMsg(result);
								int houseNum = Integer.valueOf(combMsg);
								gameInfo.add(houseNum);
							}

							// 如果接收到停止发送消息则退出循环
							if (result.equals(STOP)) {
								break;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					for (UserInfo user : Client.onlineUser) {
						// 如果是当前用户,则保存当前用户信息
						if (user.getName().equals(name)) {
							Client.crrentUser = user;
						}
					}

					JOptionPane.showMessageDialog(null, "登录成功！");
					GameHall hall = new GameHall();
					try {
						new ClientThread(Client.socket, hall).start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					hall.init();
					hall.initPlayingInfo(gameInfo);
					hall.getChat().setChat("欢迎加入网络连连看游戏大厅!");
					jf.dispose();
				} else if (result.equals(LOGIN_ERROR)) {
					JOptionPane.showMessageDialog(null, "登录失败！用户名和密码不存在");
					// 关闭连接
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
		// 实现拖动
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
