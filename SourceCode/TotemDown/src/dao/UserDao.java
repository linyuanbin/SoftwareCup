package dao;

import java.util.Set;

import model.Mark;
import model.User;

public interface UserDao {
	//ʵ��CURD�����淶
	//�û�ע�᷽��
	public String register(User u);
	//�û���¼
	public String login(String UserName,String Password);
	public boolean deleteUser(String UserId);
	public boolean updateUser(User u);
	public User selectUser(String UserName);
	//�ж��Ƿ���ڸ��û�
	public boolean senseUser(String UserID); 
	//չ���û�
	public User showUser(String UserID);
	public String getUserID(String UserName);
	public boolean ChangePassword(User u);
	
	//������ʷ��¼
	public String getHistory(String UserID);
	
	//����û�����
	public boolean setFeedback(String fb);
	

}
