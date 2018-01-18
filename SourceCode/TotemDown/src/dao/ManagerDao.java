package dao;

import model.Manager;
import model.User;

public interface ManagerDao {
	
		//�û�ע�᷽��
		public String register(Manager u);
		//�û���¼
		public String login(String mName,String mPassword);
		public boolean deleteManager(String mId);
		public boolean updateManager(Manager m);
		public Manager selectManager(String mName);
		//�ж��Ƿ���ڸ��û�
		public boolean senseManager(String mID); 
		//չ���û�
		public Manager showManager(String mID);
		public String getManagerID(String mName);
		
		public String selectNewFeedback(); //�õ��û��·������쳣
		public String selectSolvedFeedback();
		public boolean solveFeedback(String resources);//����쳣����states��Ϊ1
		public boolean ChangePassword(Manager m);
}
