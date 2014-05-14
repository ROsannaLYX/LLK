package nec.soft.java.client1;
import static com.kkf.MyProtocol.EXIT_HALL;
import static com.kkf.MyProtocol.EXIT_HOUSE;
import static com.kkf.MyProtocol.GAME_OVER;
import static com.kkf.MyProtocol.GAME_PREPARE;
import static com.kkf.MyProtocol.GAME_ROUND;
import static com.kkf.MyProtocol.GAME_START;
import static com.kkf.MyProtocol.HALL_ROUND;
import static com.kkf.MyProtocol.HOUSE_CHANGE;
import static com.kkf.MyProtocol.JOIN_HALL;
import static com.kkf.MyProtocol.JOIN_HOUSE_ERROR;
import static com.kkf.MyProtocol.JOIN_HOUSE_SUCCESS;
import static com.kkf.MyProtocol.LINK_COOR;
import static com.kkf.MyProtocol.PRIVATE_ROUND;
import static com.kkf.MyProtocol.SERVER_CLOSE;
import static com.kkf.MyProtocol.USER_ROUND;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.kkf.ParseStr;
import com.kkf.UserFactory;
import com.kkf.UserInfo;

public class ClientThread extends Thread {

	// �ÿͻ����̸߳������������
	private BufferedReader br = null;
	private Socket socket;
	// ����һ����Ϸ����������
	private GameHall hall;
	LinkGame lg = new LinkGame();;

	/*
	  ��ǰ�߳ʴ���ʲô״̬,false��ʾδ������Ϸ����
	  true��ʾ�Ѿ�������Ϸ��Ϸ����
	*/
	private boolean playing = false;

	public ClientThread(Socket socket, GameHall hall) throws Exception {
		this.socket = socket;
		this.hall = hall;
		// ��ʼ�����
		lg.initComponent();
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			// ���ϴ��������ж�ȡ���ݣ�������Щ���ݴ�ӡ���
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				// ����յ�˽����Ϣ
				if (line.startsWith(PRIVATE_ROUND) && line.endsWith(PRIVATE_ROUND)) {

					System.out.println("�յ�˽����Ϣ");
					// ���ȥ��Э�����Ϣ
					String realMsg = ParseStr.getCombMsg(line);
					hall.getChat().setChat(realMsg);
				}

				else

				// ����յ�Ⱥ����Ϣ
				if (line.startsWith(USER_ROUND) && line.endsWith(USER_ROUND)) {
					System.out.println("�յ�Ⱥ��Ϣ");
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					String model = splitMsg[0];
					String chatMsg = splitMsg[1];

					// ����Ǵ�����Ϣ
					if (model.equals(HALL_ROUND)) {
						hall.getChat().setChat(chatMsg);
					}

					// �������Ϸ��Ϣ
					if (model.equals(GAME_ROUND)) {
						lg.getChat().setChat(chatMsg);
					}
				}

				else

				// ����յ����û����������Ϣ
				if (line.startsWith(JOIN_HALL) && line.endsWith(JOIN_HALL)) {
					System.out.println("�յ����û����������Ϣ");
					// ���ȥ��Э�����Ϣ
					String combMsg = ParseStr.getCombMsg(line);
					// ������Ϣ��������
					UserInfo user = UserFactory.createUserInfo(combMsg);
					// ���������û�
					Client.onlineUser.add(user);
					// �����촰����ʾ
					String joinMsg = user.getName() + "��������Ϸ����!";
					hall.getChat().setChat(joinMsg);
					// �ڴ����ɸ��µ���������û�����Ϣ
					hall.insertUI(user);
				}

				else

				// ����յ����뷿��ɹ���Ϣ
				if (line.startsWith(JOIN_HOUSE_SUCCESS) && line.endsWith(JOIN_HOUSE_SUCCESS)) {
					System.out.println("�յ�����ɹ���Ϣ");
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					String name = splitMsg[0];
					int houseNum = Integer.valueOf(splitMsg[1]);
					int seat = Integer.valueOf(splitMsg[2]);

					UserInfo joinUser = null;
					// ���������û��е���Ϣ
					for (UserInfo user : Client.onlineUser) {
						if (user.getName().equals(name)) {
							user.setHouseNum(houseNum);
							user.setSeat(seat);
							joinUser = user;
							break;
						}
					}
					// ������뷿���û��Ǳ��ͻ��˵��û�
					if (Client.crrentUser.getName().equals(name)) {
						// ����һ��������Ϣ
						Client.chummery = new ChummeryInfo();
						Client.chummery.setName(seat, name);
						Client.chummery.setUserInfo(seat, Client.crrentUser);
						// ��ʼ������
						lg.init();
						// ���õȴ�״̬
						lg.setWaiting();
						lg.getChat().setChat("��ӭ���뷿��" + houseNum + "!");
					} else
					// �����ͬһ������������û�
					if (Client.crrentUser.getHouseNum() == houseNum && Client.chummery != null) {
						Client.chummery.setName(seat, name);
						Client.chummery.setUserInfo(seat, joinUser);
						lg.addRow(joinUser);
						lg.getChat().setChat(name + "�����˷���!");
					}

					// ���·����б���Ϣ
					hall.updateHouseInfo();
				}

				else

				// ����յ�������Ϣ������Ϣ
				if (line.startsWith(HOUSE_CHANGE) && line.endsWith(HOUSE_CHANGE)) {
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					int seat = Integer.valueOf(splitMsg[0]);
					String name = splitMsg[1];
					boolean state = Boolean.valueOf(splitMsg[2]);

					UserInfo joinUser = null;

					for (UserInfo user : Client.onlineUser) {
						if (user.getName().equals(name)) {
							joinUser = user;
							break;
						}
					}

					if (Client.chummery != null) {
						Client.chummery.changeInfo(seat, name, state);
						Client.chummery.setUserInfo(seat, joinUser);
						// ����ͬ�����û���
						lg.reSetUserTable();
					}
				}

				else

				// �յ�����
				if (line.startsWith(LINK_COOR) && line.endsWith(LINK_COOR)) {
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					System.out.println(splitMsg[0] + "----:" + splitMsg[1] + splitMsg[2]
							+ splitMsg[3] + splitMsg[4] + splitMsg[5]);
					// ���ͬ����ҵ��ϰ���
					Client.chummery.setClear(splitMsg);
					int seat = Integer.valueOf(splitMsg[0]);
					// ����ͬ����ҵ���ɶ�
					Client.chummery.setDone(seat, splitMsg[5]);
					lg.repaint();
				}

				else

				// ����յ�׼����Ϣ
				if (line.startsWith(GAME_PREPARE) && line.endsWith(GAME_PREPARE)) {
					System.out.println("׼����Ϣ");
					// ���ȥ��Э�����Ϣ
					String combMsg = ParseStr.getCombMsg(line);
					int seat = Integer.valueOf(combMsg);

					if (Client.chummery != null) {
						Client.chummery.setState(seat, true);
						for (int i = 0; i < 4; i++) {
							System.out.println(Client.chummery.getName(i)
									+ Client.chummery.getState(i));
						}
					}
				}

				else

				// ����յ���Ϸ��ʼ
				if (line.startsWith(GAME_START) && line.endsWith(GAME_START)) {
					System.out.println("�յ���Ϸ��ʼ��Ϣ");
					// ���ȥ��Э�����Ϣ
					String combMsg = ParseStr.getCombMsg(line);
					int houseNum = Integer.valueOf(combMsg);
					System.out.println("��Ϸ����:" + houseNum);
					hall.addPlayingInfo(houseNum);

					// �����ͬһ����������
					if (Client.crrentUser.getHouseNum() == houseNum && Client.chummery != null) {
						if (lg != null) {
							// ��ɶ�ȫ������Ϊ0
							Client.chummery.reSetDone();
							// ��ʼ����ͼ
							lg.initMap();
							lg.start();
						}
					}
				}

				else

				// ����յ��˳�������Ϣ
				if (line.startsWith(EXIT_HOUSE) && line.endsWith(EXIT_HOUSE)) {
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					String name = splitMsg[0];
					int houseNum = Integer.valueOf(splitMsg[1]);
					int seat = Integer.valueOf(splitMsg[2]);

					UserInfo exitUser = null;
					// ���������û��е���Ϣ
					for (UserInfo user : Client.onlineUser) {
						if (user.getName().equals(name)) {
							user.setHouseNum(-1);
							user.setSeat(-1);
							exitUser = user;
							break;
						}
					}

					// ����˳�������û����ͻ��˵��û�
					if (Client.crrentUser.getName().equals(name)) {
						Client.chummery = null;
					} else
					// �����ͬһ������������û�
					if (Client.crrentUser.getHouseNum() == houseNum && Client.chummery != null) {
						// ɾ��������Ϣ����˳�����ҵ���Ϣ
						Client.chummery.removeUser(seat);
						// ɾ�����������˳�����ҵ���Ϣ
						lg.removeUI(name);
						lg.getChat().setChat(name + "�˳��˷���!");
						for (int i = 0; i < 4; i++) {
							System.out.println(Client.chummery.getName(i)
									+ Client.chummery.getState(i));
						}
					}

					// ���·����б���Ϣ
					hall.updateHouseInfo();
				}

				else

				// ����յ���Ϸ������Ϣ
				if (line.startsWith(GAME_OVER) && line.endsWith(GAME_OVER)) {
					// ���ȥ��Э��ͷָ�������ʵ��Ϣ
					String[] splitMsg = ParseStr.getRealMsg(line);
					for (String str : splitMsg) {
						System.out.println(str);
					}
					System.out.println("�յ���Ϸ������Ϣ");
					String name = "";
					int score = 0;
					int houseNum = -1;

					// ��������������Ķ�Ӧ��ϵ
					// ���������û��еķ���
					for (int i = 0; i < splitMsg.length; i += 2) {
						name = splitMsg[i];
						score = Integer.valueOf(splitMsg[i + 1]);
						for (UserInfo user : Client.onlineUser) {
							if (user.getName().equals(name)) {
								user.addScore(score);
								houseNum = user.getHouseNum();
								System.out.println(user.getName() + ":" + user.getScore());
								// �ڴ������·���
								hall.setScore(user.getScore(), user.getName());
								break;
							}
						}
					}
					// ɾ���÷���"��Ϸ��"ͼ��
					hall.removePlayingInfo(houseNum);

					// ������û�ǿ��,���ǵ�ǰ���
					if (Client.crrentUser.getName().equals(name) && score == -50) {
						continue;
					}

					// ����Ǹ÷�������
					if (Client.crrentUser.getHouseNum() == houseNum && Client.chummery != null
							&& houseNum != -1) {
						// ���÷�����Ϣ
						Client.chummery.reSetInfo();
						System.out.println("���÷�����Ϣ");
						lg.setEnd(splitMsg);
						try {
							Thread.sleep(4000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						// ���·�����ķ���
						for (int i = 0; i < ChummeryInfo.USER_NUM; i++) {
							if (!"null".equals(Client.chummery.getName(i))
									&& Client.chummery.getName(i) != null) {
								lg.setScore(Client.chummery.getScore(i), Client.chummery.getName(i));
							}
						}

						// �������в���
						lg.init();
						// ���õȴ�״̬
						lg.setWaiting();
						// ���³�ʼ����ͼ
						lg.initMap();
					}
				}

				else

				if (line.startsWith(JOIN_HOUSE_ERROR) && line.endsWith(JOIN_HOUSE_ERROR)) {
					hall.showJoinErroe();
				}

				else

				// ����յ��û��˳�������Ϣ
				if (line.startsWith(EXIT_HALL) && line.endsWith(EXIT_HALL)) {
					// ���ȥ��Э�����Ϣ
					String exitName = ParseStr.getCombMsg(line);
					String exitMsg = exitName + "�˳�����Ϸ����!";
					hall.getChat().setChat(exitMsg);
					UserInfo exitUser = null;
					// ɾ���˳��û���Ϣ
					for (UserInfo user : Client.onlineUser) {
						if (user.getName().equals(exitName)) {
							exitUser = user;
							Client.onlineUser.remove(user);
							break;
						}
					}

					// ����Ǹ÷�������
					if (Client.crrentUser.getHouseNum() == exitUser.getHouseNum()
							&& Client.chummery != null && exitUser.getHouseNum() != -1) {
						Client.chummery.removeUser(exitUser.getSeat());
						// ɾ�����������˳�����ҵ���Ϣ
						lg.removeUI(exitName);
					}

					// ɾ���˳��û�����������Ϣ
					hall.removeUI(exitName);
					// ���·����б���Ϣ
					hall.updateHouseInfo();
					System.out.println("�յ��û��˳�������Ϣ");
				}

				else

				if (line.equals(SERVER_CLOSE)) {
					JOptionPane.showMessageDialog(null, "11111111111", "server close", 1);
					System.out.println("server close");

					// �˳���·ģʽ�����ؿ�ʼ����ѡ��Ϸģʽ
					System.exit(0);
				}
			}// while
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// ʹ��finally�����رո��̶߳�Ӧ��������
		finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
