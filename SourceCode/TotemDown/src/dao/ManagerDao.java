package dao;

import model.Manager;
import model.User;

public interface ManagerDao {
	
		//用户注册方法
		public String register(Manager u);
		//用户登录
		public String login(String mName,String mPassword);
		public boolean deleteManager(String mId);
		public boolean updateManager(Manager m);
		public Manager selectManager(String mName);
		//判断是否存在该用户
		public boolean senseManager(String mID); 
		//展现用户
		public Manager showManager(String mID);
		public String getManagerID(String mName);
		
		public String selectNewFeedback(); //得到用户新反馈的异常
		public String selectSolvedFeedback();
		public boolean solveFeedback(String resources);//解决异常，将states置为1
		public boolean ChangePassword(Manager m);
}
