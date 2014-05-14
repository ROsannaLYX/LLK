package com.kkf;

//�����û��๤��
public class UserFactory
{
	public static UserInfo createUserInfo(String name , String sex   
		, int score , int iconIndex 
		, int houseNum , int seat)
	{
		return new UserInfo(name , sex   
			, score , iconIndex 
			, houseNum , seat);
	}
	
	//combMsg�����Ϣ
	public static UserInfo createUserInfo(String combMsg)
	{
		String[] sliptMsg = combMsg.split(MyProtocol.SPLIT_SIGN);
		
		//����ǵ�½��Ϣ,��ź�����Ĭ��Ϊ-1
		if(sliptMsg.length == 4)
		{	
			return new UserInfo(sliptMsg[0] , sliptMsg[1]    
				, Integer.valueOf(sliptMsg[2]) , Integer.valueOf(sliptMsg[3]) 
				, -1 , -1);
		}

		return new UserInfo(sliptMsg[0] , sliptMsg[1]    
				, Integer.valueOf(sliptMsg[2]) , Integer.valueOf(sliptMsg[3]) 
				, Integer.valueOf(sliptMsg[4]) , Integer.valueOf(sliptMsg[5]));
	}
}
