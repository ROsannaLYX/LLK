package com.kkf;
//Э��ӿ�
public interface MyProtocol
{
	//Э���ַ����ĳ���
	int PROTOCOL_LEN = 2;
	
	//������һЩЭ���ַ������������Ϳͻ��˽�������Ϣ����Ӧ����ǰ����������������ַ�����
	String MSG_ROUND = "���";			//��Ϣ
	String USER_ROUND = "�ǡ�";			//Ⱥ��
	String PRIVATE_ROUND = "�";		//˽��
	String SPLIT_SIGN = "��";			//�ָ��
	String HALL_ROUND = "����";			//�ڴ���������
	String GAME_ROUND = "�ѡ�" ;			//����Ϸ����������
	
	String LOGIN = "��#";				//��½
	String LOGIN_SUCCESS = "fs";		//��½�ɹ�
	String LOGIN_ERROR = "&*";			//��½ʧ��
	String LOGIN_AGAIN = "����";			//�ظ���½

	String NAME_REP = "-1";				//�����ظ�ע��
	String REGISTER = "���";				//ע��
	String REGISTER_SUCCESS = "sc";		//ע��ɹ�
	String REGISTER_ERROR = "�ߡ�";		//ע��ʧ��
	
	String UPDATE_ALL = "���";			//���������û���Ϣ
	String STOP = "����";				//ֹͣ��������
	String JOIN_HALL = "�ơ�";			//�������
	String EXIT_HALL = "�ɡ�";			//�˳�����
	String JOIN_HOUSE = "J*";			//���뷿��
	String JOIN_HOUSE_ERROR = "���";		//���뷿��ʧ��
	String JOIN_HOUSE_SUCCESS = "��s";	//���뷿��ɹ�
	String HOUSE_INFO = "�ˡ�";			//������Ϣ
	
	String HOUSE_CHANGE = "���";		//ͬ�����û���Ϣ�ı�
	String EXIT_HOUSE = "e*";			//�˳���
	String GAME_PREPARE = "ZB";			//׼��
	String GAME_START = "VK";			//��Ϸ��ʼ
	String LINK_COOR = "���";			//��������
	String GAME_OVER = "����";				//��Ϸ����
	String CREATE_HOUSE = "����";    		//��������
	
	String SERVER_CLOSE = "�ġ�";		//�������رգ��ͻ��˵�����ʾ��ֻ��һ��ȷ���������ͻ����˳�
}


