package com.kkf;
//�û���Ϣ��
public class UserInfo 
{
	//����,�Ա�,����,����,�ȼ�,ͼƬ����
	private String name;
	private String sex;
	private String passWord;
	private int score = 100;
	private int iconIndex = 2;
	//����ź���λ��
	private int houseNum = -1;
	private int seat = -1;
	private boolean playing = false;

	public UserInfo()
	{

	}

	public UserInfo(String name , String sex   
		, int score , int iconIndex 
		, int houseNum , int seat)
	{
		this.name = name;
		this.sex = sex;
		this.passWord = passWord;
		this.score = score;
		this.iconIndex = iconIndex;
		this.houseNum= houseNum;
		this.seat= seat;
	}

	//-----------------------��������--------------------------
	public void setName(String name)
	{
		this.name = name;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public void setPassWord(String password)
	{
		this.passWord = passWord;
	}

	/*public void setLevel(int level)
	{
		this.level = level;
	}*/

	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void addScore(int score)
	{
		this.score += score;
	}

	public void setIconIndex(int iconIndex)
	{
		this.iconIndex = iconIndex;
	}

	public void setHouseNum(int houseNum)
	{
		this.houseNum = houseNum;
	}

	public void setSeat(int seat)
	{
		this.seat = seat;
	}
	
	//----------------------��ȡ����----------------------
	public String getName()
	{
		return name;
	}

	public String getSex()
	{
		return sex;
	}

	public String gerPassWord()
	{
		return passWord;
	}
	
	/*public int getLevel()
	{
		return level;
	}*/

	public int getScore()
	{
		return score;
	} 

	public int getIconIndex()
	{
		return iconIndex;
	} 

	public int getHouseNum()
	{
		return houseNum;
	} 

	public int getSeat()
	{
		return seat;
	} 
	
	//��Ϸ״̬
	public void setGameState(boolean state)
	{
		playing = state;
	}

	public boolean getGameState()
	{
		return playing;
	}
}
