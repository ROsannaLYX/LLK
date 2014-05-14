package nec.soft.java.fight;
public class User {
	public String headURL = null;
	public String name = null;
	public String number = null;
	public int score = 0;
	// public boolean isJoin; // 表示该用户是否已进入一个房间
	int roomNumber; // 表示该用户进了那个房间（从0计起，若为-1则表示还没进入房间）
	int seat;
	boolean playing;
	boolean ready;

	// public int state; // 表示状态
	/*-state==-1 表示没进大厅
	state==0 表示进了大厅没进房间
	state==1 表示进了房间没准备
	state==2 表示准备了但没开始游戏
	state==3 表示已开始游戏*/

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
