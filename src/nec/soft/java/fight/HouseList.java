package nec.soft.java.fight;

public class HouseList {

	public RoomPane[] room;
	private int size;
	private int maxNum; // ��ʾ������ж��ٷ���
	private User curUser; //��ǰ�ͻ��˵�ʹ����
	GameHall gh;
	
	
	HouseList(int max, User root,GameHall gh){
		this.gh=gh;
		curUser=root;
		room=new RoomPane[max+6]; //��6��Ϊ�˷�ֹ��ִ��GameHall��getMainPanel()����ʱԽ��
		for(int i=0;i<max+6;i++){ //��ÿ�����䶼��ʼ��
			room[i]=new RoomPane(curUser,this.gh);
			room[i].roomNumber=i+1;
		}
		maxNum=max;
		size=0;
	}
	
	public RoomPane getRoom(int index){
		return room[index];
	}
	
	public boolean add(){ //����һ���µķ��䣨������true�򴴽��ɹ���
		if(size>maxNum-1){
			System.out.println("�������Ӹ���ķ�����");
			return false;
		}
		if(curUser.roomNumber>=0){
			System.out.println("���ڷ�����");
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
	
	public boolean remove(int index){ //�Ƴ���index�����䣨index��0����
		if(index<0 || index>=size){
			System.out.println("Խ��");
			return false;
		}
		for(int i=index;i<size-1;i++){ //������ķ�����ǰ��
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
