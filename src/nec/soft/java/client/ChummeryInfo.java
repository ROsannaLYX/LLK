package nec.soft.java.client;
import com.kkf.*;
import javax.imageio.*;

//ͬ���������Ϣ
class ChummeryInfo {
	public static final int USER_NUM = 4;
	private String[] name = new String[USER_NUM];
	// true��ʾ׼�� ,false��ʾδ׼��
	private boolean[] userState = new boolean[USER_NUM];
	// ��ɶ�
	private String[] done = new String[USER_NUM];
	// �ϰ�������
	private int[][][] blocks = new int[USER_NUM][LinkGame.ROW][LinkGame.COL];
	private UserInfo[] userInfo = new UserInfo[USER_NUM];

	public ChummeryInfo() {
		// ��ʼ����ɶ�
		for (int i = 0; i < done.length; i++) {
			done[i] = "0%";
		}

	}

	// ����δ��ʼǰ�û�״̬
	public void changeInfo(int seat, String name, boolean state) {
		this.name[seat] = name;
		this.userState[seat] = state;
	}

	// ���¿�ʼ��ӵ��״̬
	public void changeGameInfo(int seat, int done, int clearCoor) {
		// this.done[seat] = done;
		// this.clearCoor[seat] = clearCoor;
	}

	public String getName(int seat) {
		return name[seat];
	}

	public boolean getState(int seat) {
		return userState[seat];
	}

	public void setName(int seat, String name) {

		this.name[seat] = name;
	}

	public void setState(int seat, boolean state) {
		userState[seat] = state;
	}

	public void setUserInfo(int seat, UserInfo userInfo) {
		this.userInfo[seat] = userInfo;
	}

	public UserInfo getUserInfo(int seat) {
		return userInfo[seat];
	}

	public int getScore(int seat) {
		return userInfo[seat].getScore();
	}

	public int getIcon(int seat) {
		return userInfo[seat].getIconIndex();
	}

	public void removeUser(int seat) {
		name[seat] = "null";
		userState[seat] = false;
		done[seat] = "0%";
		blocks[seat] = new int[LinkGame.ROW][LinkGame.COL];
		userInfo[seat] = null;
	}

	// ��ʼ���ϰ���
	public void initBlock(int seat, int blocks[][]) {

		for (int i = 0; i < LinkGame.ROW; i++) {
			for (int j = 0; j < LinkGame.COL; j++) {
				this.blocks[seat][i][j] = blocks[i][j];
				System.out.print(this.blocks[seat][i][j]);
			}
			System.out.println("------------------------");
		}

	}

	public int[][] getBlock(int seat) {
		return this.blocks[seat];
	}

	// ����ϰ���
	public void setClear(String[] clearBlock) {
		int seat = Integer.valueOf(clearBlock[0]);
		int fristRow = Integer.valueOf(clearBlock[1]);
		int fristCol = Integer.valueOf(clearBlock[2]);
		int secondRow = Integer.valueOf(clearBlock[3]);
		int secondCol = Integer.valueOf(clearBlock[4]);

		blocks[seat][fristRow][fristCol] = 0;
		blocks[seat][secondRow][secondCol] = 0;
	}

	// ������ɶ�
	public void setDone(int seat, String done) {
		this.done[seat] = done;
	}

	public String getDone(int seat) {
		return done[seat];
	}

	// ���÷�����Ϣ
	public void reSetInfo() {
		userState = new boolean[USER_NUM];
		blocks = new int[USER_NUM][LinkGame.ROW][LinkGame.COL];

	}

	// ������ɶ�
	public void reSetDone() {
		for (int i = 0; i < USER_NUM; i++) {
			done[i] = "0%";
		}
	}

}