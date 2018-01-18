package daoimplement;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.UserDao;
import hibernateutil.SessionAnnotation;
import hibernateutil.ValidatorUserNameUtil;
import jsonUtil.CreateJson;
import model.Feedback;
import model.HistoryMark;
import model.Mark;
import model.Picture;
import model.User;
import util.RandomString;

public class UserImplement implements UserDao {

	@Override
	public String register(User u) { // 初次登入添加用户
		Session session = SessionAnnotation.getSession();
		String userId = "";
		if (u.getUserTel() != null) { // 在完成注册之前确定该手机号是否已经注册,一个手机号只能注册一次
			String sql = "select UserID from User where UserTel='" + u.getUserTel() + "'";
			session.beginTransaction();
			List list = session.createQuery(sql).list();
			if (!list.isEmpty()) {
				SessionAnnotation.closeSession();
				return userId;// 空的id
			} else {
				userId = new Date() + RandomString.getRandomString(5);
				u.setUserID(userId.trim()); // 随机产生用户ID
				u.setTotal(0); //总任务情况
				u.setAccomplish(0); //完成总量
				u.setUserIntegral(0);
				try {
					// session.beginTransaction();
					session.save(u);
					session.flush();
					session.getTransaction().commit();
					SessionAnnotation.closeSession();
					return userId;
				} catch (Exception e) {
					System.out.println("register异常"+e);
					SessionAnnotation.closeSession();
					userId="";
					return userId;
				}
			} // else
		}else{ // if
		return userId;
		}
	}

	@Override
	public String login(String UserName, String Password) {
		Session session = SessionAnnotation.getSession();
		String sql;
		String userId = "";

		if (ValidatorUserNameUtil.isEmail(UserName)) {
			sql = "select UserID from User where UserEmail='" + UserName + "'and UserPassword='" + Password + "'";
		} else if (ValidatorUserNameUtil.isMobile(UserName)) {
			sql = "select UserID from User where UserTel='" + UserName + "'and UserPassword='" + Password + "'";
		} else {
			//sql = "select UserID from User where UserName='" + UserName + "'and UserPassword='" + Password + "'";
			return "";
		}
		session.beginTransaction();
		List list = session.createQuery(sql).list();
		if (list.isEmpty()) {
			SessionAnnotation.closeSession();
			userId="";
			return userId;
		}
		userId = (String) list.iterator().next();
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return userId;
	}

	@Override
	public boolean deleteUser(String UserId) {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String sql = "select UserID from User where UserID='" + UserId + "'"; // 擦看是否存在用户
		List list = session.createQuery(sql).list();

		if (list.isEmpty()) { // 如果空则说明不存在
			SessionAnnotation.closeSession();
			return false;
		}
		// "delete from User where User_ID='"+User_ID+"'";
		sql = "delete from User where UserID='" + UserId + "'";
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return true;
	}

	@Override
	public boolean updateUser(User u) { // 更新
		if (senseUser(u.getUserID())) { // 如果用户存在则更新
			try{
				Session session = SessionAnnotation.getSession();
				session.beginTransaction();
				session.update(u);
				session.getTransaction().commit();
				SessionAnnotation.closeSession();
				return true;
			}catch(Exception e){
				System.out.println(e);
				System.out.println("updateUser失败");
				SessionAnnotation.closeSession();
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public User selectUser(String UserName) { // 查找用户 该方法有点bug
		Session session = SessionAnnotation.getSession();
		String sql;
		if (ValidatorUserNameUtil.isEmail(UserName)) {
			sql = "select UserID from User where UserEmail='" + UserName + "'";
		} else if (ValidatorUserNameUtil.isMobile(UserName)) {
			sql = "select UserID from User where UserTel='" + UserName + "'";

		} else {
			sql = "select UserID from User where UserName='" + UserName + "'";
		}
		session.beginTransaction();
		List list = session.createQuery(sql).list();
		System.out.println(list);
		if (!list.isEmpty()) { // 如果不空
			// User user=(User)list.iterator().next();
			String uid = list.toString();
			User user = (User) session.get(User.class, uid);
			System.out.println("selectUser" + user.getUserName());
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return user;
		}
		// session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return null;
	}

	@Override
	public boolean senseUser(String UserID) { // 判定用户是否存在
		Session session = SessionAnnotation.getSession();
		String sql;
		sql = "select UserID from User where UserID='" + UserID + "'";
		session.beginTransaction();
		List list = session.createQuery(sql).list();
		if (!list.isEmpty()) {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}
		SessionAnnotation.closeSession();
		return false;
	}

	@Override
	public User showUser(String UserID) {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, UserID);
		session.getTransaction().commit();
		// session.flush();
		SessionAnnotation.closeSession();
		return user;
	}

	@Override
	public String getUserID(String UserName) {
		Session session = SessionAnnotation.getSession();
		try{ //6.25
			session.beginTransaction();	
		
		UserName=UserName.replace(" ","");
		String sql=""; 
		if(UserName.trim().equals("")){
			System.out.println("getUserID()参数为空");
			return "";
		}else if(ValidatorUserNameUtil.isEmail(UserName)) {
//			sql = "select UserID from User where UserEmail='" + UserName + "'";
			System.out.println("是Email"+UserName);
			 sql="from User where UserEmail=?";
		} else if (ValidatorUserNameUtil.isMobile(UserName)) {
//			sql = "select UserID from User where UserTel='" + UserName + "'";
			System.out.println("是电话"+UserName);
			 sql="from User where UserTel=?";
		} else {
//			sql = "select UserID from User where UserName='" + UserName + "'";
			System.out.println("是UserName"+UserName);
			 sql="from User where UserName=?";
		}
		Query query=session.createQuery(sql);
		query.setParameter(0, UserName);
		List<User> list =query.list();
		String userid = "";
//		userid=(String) list.iterator().next();
		userid=list.get(0).getUserID();
		// String userid=session.createQuery(sql).toString();
		session.getTransaction().commit();
//		SessionAnnotation.closeSession();
		if (userid.isEmpty()||userid.trim().equals("")) {
 //		SessionAnnotation.closeSession();
			return "";
		} else {
 //		SessionAnnotation.closeSession();
			return userid;
		}
		}catch(Exception e){
			System.out.println("getUserID异常"+e);
			return "";
		}finally{
			SessionAnnotation.closeSession();
		}
	}

	@Override 
	public String getHistory(String UserID) {
		Session session=SessionAnnotation.getSession();
		try{
			session.beginTransaction();
		}catch(Exception e){
			System.out.println("getHistory beginTransaction 异常："+e);
		}
		try{
			StringBuilder sb=new StringBuilder();
			String hql="from Mark where UserId=?";//+UserID+"'";
			Query query=session.createQuery(hql);
			query.setParameter(0,UserID);
			System.out.println(hql);
			List<Mark> list=query.list();
			//List<String> list=session.createQuery(hql).list();
//			System.out.println(list.get(1).getUser().getUserID()+"  "+list.size());
//			System.out.println(list.get(0).getPicture().getFirstName());
			for(int i=0;i<list.size();i++){ 
				if(i<(list.size()-1)){ 
					Mark m=list.get(i);
					Picture p=m.getPicture();
					HistoryMark hm=new HistoryMark();
					hm.setPID(p.getPID());
					hm.setPAddress(p.getPAddress());
					hm.setMarkName(m.getMarkName());
					hm.setFinalMarkName(p.getFinalMarkName());
					if(p.getFinalMarkName()==null||p.getFinalMarkName().trim().equals("")){ //没有最终标签
						System.out.println("最终:"+p.getFinalMarkName());
						System.out.println("false");
						hm.setState("false"); //如果该图片还没有确定最终标签，则用户可以在历史记录中修改
					}else{
						System.out.println("最终:"+p.getFinalMarkName());
						System.out.println("true");
						hm.setState("true");//如果该图片已经确定最终标签，则用户不可以在历史记录中修改
					}
					String s=CreateJson.getHistoryMarkJson(hm);
					//System.out.println(list.get(i).getUser().getUserName());
					System.out.println(s);
					//String s=CreateJson.getmarkJson(m);
					if(!s.equals(""))
					sb.append(s+",");
				}else{
					Mark m=list.get(i);
					Picture p=m.getPicture();
					HistoryMark hm=new HistoryMark();
					hm.setPID(p.getPID());
					hm.setPAddress(p.getPAddress());
					hm.setMarkName(m.getMarkName());
					hm.setFinalMarkName(p.getFinalMarkName());
					if(p.getFinalMarkName()==null||p.getFinalMarkName().trim().equals("")){ //没有最终标签
						System.out.println("最终:"+p.getFinalMarkName());
						System.out.println("false");
						hm.setState("false"); //如果该图片还没有确定最终标签，则用户可以在历史记录中修改
					}else{
						System.out.println("最终:"+p.getFinalMarkName());
						System.out.println("true");
						hm.setState("true");//如果该图片已经确定最终标签，则用户不可以在历史记录中修改
					}
					String s=CreateJson.getHistoryMarkJson(hm);
					//System.out.println(list.get(i).getUser().getUserName());
					System.out.println(s);
					//String s=CreateJson.getmarkJson(m);
					if(!s.equals(""))
					sb.append(s);
				}
			
		}
			session.getTransaction().commit(); //添加
//			SessionAnnotation.closeSession();
			return sb.toString();
		}catch(Exception e){
			System.out.println("getHistory异常");
//			SessionAnnotation.closeSession();
			return "";
		}finally{
			SessionAnnotation.closeSession();
		}
	}
	
	public boolean setFeedback(String fb){
		Session session=SessionAnnotation.getSession();
		
		try{
			session.beginTransaction();
			Feedback feedback=CreateJson.getFeedback(fb);
			if((!(feedback.getContent().equals("")))&&(!(feedback.getUserTel().equals("")))){ 
				feedback.setFbId(new Date()+RandomString.getRandomString(6));
				feedback.setStates(0);
				feedback.setDate(new Date());
				session.save(feedback);
				session.getTransaction().commit();
//				SessionAnnotation.closeSession();
				return true;
			}else{
				session.getTransaction().commit();
//				SessionAnnotation.closeSession();
				System.out.println("反馈电话号码空或内容是空");
				return false;
			}
		}catch(Exception e){
//			SessionAnnotation.closeSession();
			System.out.println("setFeedback异常");
			System.out.println(e);
			return false;
		}finally{
			SessionAnnotation.closeSession();
		}
	}
	
	//修改密码
	public boolean ChangePassword(User u){
		try{
			String uid=getUserID(u.getUserTel());
			User user=showUser(uid);
			user.setUserPassword(u.getUserPassword());
			boolean b=updateUser(user);
			if(b){
				System.out.println("志愿者修改密码成功");
				return true;
			}else{
				System.out.println("志愿者修改密码失败");
				return false;
			}
		}catch(Exception e){
			System.out.println(e);
			System.out.println("ChangePassword异常");
			return false;
		}
	}

}
