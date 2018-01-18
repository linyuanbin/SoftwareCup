package dao;

import java.util.Set;

import model.Mark;
import model.User;

public interface UserDao {
	//实现CURD方法规范
	//用户注册方法
	public String register(User u);
	//用户登录
	public String login(String UserName,String Password);
	public boolean deleteUser(String UserId);
	public boolean updateUser(User u);
	public User selectUser(String UserName);
	//判断是否存在该用户
	public boolean senseUser(String UserID); 
	//展现用户
	public User showUser(String UserID);
	public String getUserID(String UserName);
	public boolean ChangePassword(User u);
	
	//查找历史记录
	public String getHistory(String UserID);
	
	//添加用户反馈
	public boolean setFeedback(String fb);
	

}
