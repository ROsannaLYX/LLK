package nec.soft.java.fight;

public class HouseList {

	public RoomPane[] room;
	private int size;
	private int maxNum; // 表示最多能有多少房间
	private User curUser; //当前客户端的使用者
	GameHall gh;
	
	
	HouseList(int max, User root,GameHall gh){
		this.gh=gh;
		curUser=root;
		room=new RoomPane[max+6]; //加6是为了防止在执行GameHall的getMainPanel()方法时越界
		for(int i=0;i<max+6;i++){ //将每个房间都初始化
			room[i]=new RoomPane(curUser,this.gh);
			room[i].roomNumber=i+1;
		}
		maxNum=max;
		size=0;
	}
	
	public RoomPane getRoom(int index){
		return room[index];
	}
	
	public boolean add(){ //创建一个新的房间（若返回true则创建成功）
		if(size>maxNum-1){
			System.out.println("不能增加更多的房间了");
			return false;
		}
		if(curUser.roomNumber>=0){
			System.out.println("已在房间中");
			return false;
		}
		//room[size]=new RoomPane();
		//curUser.state=1;
		room[size].roomNumber = size+1;
		room[size].exist = true;
		room[size].users[0]=curUser;
		room[size].userNum=1;
		size++;
		gh.update();
		return true;
	}
	
	public boolean remove(int index){ //移除第index个房间（index从0计起）
		if(index<0 || index>=size){
			System.out.println("越界");
			return false;
		}
		for(int i=index;i<size-1;i++){ //将后面的房间往前移
			//room[i].roomNumber=room[i+1].roomNumber;
			room[i].users=room[i+1].users;
			room[i].exist=room[i+1].exist;
			room[i].fighting=room[i+1].fighting;
		}
		room[size-1].exist=false;
	
		size--;
		return true;	
	}
	
	

}
