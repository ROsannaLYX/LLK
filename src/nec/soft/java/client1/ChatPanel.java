package nec.soft.java.client1;
import javax.swing.*;
import java.awt.*;
import com.kkf.*;
import java.awt.event.*;
import static com.kkf.MyProtocol.*;

//�������
public class ChatPanel {
	// ����ģʽ
	public static final int HALL_MODEL = 11;
	public static final int GAME_MODEL = 12;

	// ��Ϸ����ģʽ
	private JTextArea chatArea = new JTextArea(12, 12);
	private JComboBox userBox = new JComboBox();
	private JTextField chatText = new JTextField(8);
	private JButton okBt;
	private JPanel chatPanel = new JPanel();

	public ChatPanel(int md) {
		final int model = md;
		String currentName = Client.crrentUser.getName();

		if (model == HALL_MODEL) {
			userBox.addItem("�������");
			for (UserInfo user : Client.onlineUser) {
				userBox.addItem(user.getName());
			}
		} else {
			userBox.addItem("�������");
		}

		okBt = new JButton(new ImageIcon("image/chat/send.gif"));
		okBt.setPreferredSize(new Dimension(16, 16));
		userBox.setPreferredSize(new Dimension(50, 20));

		JPanel bottom = new JPanel();
		bottom.add(userBox);
		bottom.add(chatText);
		bottom.add(okBt);
		bottom.setBackground(new Color(126, 205, 236));
		// chatArea.setBackground(new Color(126,205,236));
		// chatArea.setFont(new Font("����" , Font.BOLD , 14));

		chatPanel.setLayout(new BorderLayout());
		chatPanel.add(chatArea);
		chatPanel.add(bottom, BorderLayout.SOUTH);

		okBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chatMsg = chatText.getText();
				if (chatMsg.length() == 0) {
					return;
				}

				String userItem = (String) userBox.getSelectedItem();
				if (model == HALL_MODEL) {
					if (userItem == "�������") {
						chatMsg = Client.crrentUser.getName() + "����������:" + chatMsg;
						Client.ps.println(USER_ROUND + HALL_ROUND + SPLIT_SIGN + chatMsg
								+ USER_ROUND);
					} else {
						setChat("��������ض�:" + userItem + "˵:" + chatMsg);

						chatMsg = Client.crrentUser.getName() + "�������ض���˵:" + chatMsg;

						Client.ps.println(PRIVATE_ROUND + userItem + SPLIT_SIGN + chatMsg
								+ PRIVATE_ROUND);
					}
				}

				if (model == GAME_MODEL) {
					chatMsg = Client.crrentUser.getName() + "����������:" + chatMsg;
					Client.ps.println(USER_ROUND + GAME_ROUND + SPLIT_SIGN + chatMsg + USER_ROUND);
				}

				chatText.setText("");
			}
		});
	}

	public void setChat(String msg) {
		chatArea.append(msg + "\n");
	}

	public void addUserItem(String item) {
		userBox.addItem(item);
		userBox.updateUI();
	}

	public void removeItem(String item) {
		userBox.removeItem(item);
		userBox.updateUI();
	}

	public void clearText() {
		chatArea.setText("");
	}

	public JPanel getPanel() {
		return chatPanel;
	}

	/*public static void main(String[] args) 
	{
		JFrame jf = new JFrame();
		jf.add(new ChatPanel().getPanel());
		jf.pack();
		jf.setVisible(true);
	}*/
}
