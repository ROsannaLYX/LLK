package nec.soft.java.fight;
public class User {
	public String headURL = null;
	public String name = null;
	public String number = null;
	public int score = 0;
	// public boolean isJoin; // ��ʾ���û��Ƿ��ѽ���һ������
	int roomNumber; // ��ʾ���û������Ǹ����䣨��0������Ϊ-1���ʾ��û���뷿�䣩
	int seat;
	boolean playing;
	boolean ready;

	// public int state; // ��ʾ״̬
	/*-state==-1 ��ʾû������
	state==0 ��ʾ���˴���û������
	state==1 ��ʾ���˷���û׼��
	state==2 ��ʾ׼���˵�û��ʼ��Ϸ
	state==3 ��ʾ�ѿ�ʼ��Ϸ*/

	User() {
		// isJoin=false;
		// state = 0;
		roomNumber = -1;
		seat = -1;
		playing = false;
		ready = false;
	}

	User(String url, String na, String num, int sc) {
		headURL = url;
		number = num;
		name = na;
		score = sc;
		// isJoin=false;
		// state = 0;
		roomNumber = -1;
		seat = -1;
		playing = false;
		ready = false;
	}

	User(String headURL, String name, String number, int score, int roomNumber,
			int seat, boolean playing, boolean ready) {
		this.headURL = headURL;
		this.name = name;
		this.number = number;
		this.score = score;
		this.roomNumber = roomNumber;
		this.seat = seat;
		this.playing = playing;
		this.ready = ready;
	}
}
