package nec.soft.java.client;

import java.io.*;
import java.net.*;
import java.util.*;

import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.Constants;

import com.kkf.*;

public class Client {
	public static List<UserInfo> onlineUser = new ArrayList<UserInfo>();
	public static UserInfo crrentUser = new UserInfo();
	public static Socket socket;
	public static PrintStream ps;
	public static BufferedReader brServer;
	public static BufferedReader keyIn;
	// public static String ipStr = "127.0.0.1";
	// ����ͬһ���������Ϣ
	public static ChummeryInfo chummery;

	public static void initScoket(String ipStr) {
		try {
			// ��ü���������
			keyIn = new BufferedReader(new InputStreamReader(System.in));
			if (SharedVar.game_mode == Constants.MODE_TOGETHER)
				socket = new Socket(Login.ipStr, 30001);
			else
				socket = new Socket(Login.ipStr, 30000);
			// ��ȡsocket��Ӧ������������
			brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ps = new PrintStream(socket.getOutputStream());

			// ע���½
		} catch (UnknownHostException ex) {
			System.out.println("�Ҳ���Զ�̷�����,��ȷ���������Ѿ�����.");
			System.out.println("ע����¼֮ǰ���ڵ�¼�������������IP��ַ.");
			closeRs();
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("�����쳣�������µ�½!");
			System.out.println("��ȷ���������Ѿ���!");
			System.out.println("ע����¼֮ǰ���ڵ�¼�������������IP��ַ.");
			closeRs();
			System.exit(1);
		}
	}

	public static void closeRs() {
		try {
			if (keyIn != null) {
				keyIn.close();
			}
			if (brServer != null) {
				brServer.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (socket != null) {
				keyIn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
