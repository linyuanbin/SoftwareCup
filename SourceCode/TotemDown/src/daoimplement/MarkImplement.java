package daoimplement;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.MarkDao;
import dao.PictureDao;
import dao.UserDao;
import hibernateutil.SessionAnnotation;
import model.Mark;
import model.Picture;
import model.User;

public class MarkImplement implements MarkDao{
	
	public boolean insertIntoMark(String UserId,String Pid,String markName){//有问题
		Session session=SessionAnnotation.getSession();
		session.beginTransaction().begin();;
		try{
			UserDao d=new UserImplement();
			PictureDao pd=new PictureImplement();
			Mark m=new Mark();
			m.setTabId(UserId+Pid);
			User u=d.showUser(UserId);
			Picture p= pd.selectSinglePictureFID(Pid);
			m.setUser(u);
			m.setPicture(p);
			m.setMarkName(markName);
			session.save(m);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("插入mark表失败");
			//session.getTransaction().rollback();//事务回滚
			SessionAnnotation.closeSession();
			return false;
		}
	}
	 
	public boolean insertMark(Mark m){  //插入一条标签记录
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			session.saveOrUpdate(m);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("插入mark表失败！");
			SessionAnnotation.closeSession();
			return false;
		}
	}
	
	public boolean deleteOnMark(String TabId){  //根据id删除mark表记录
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			String sql="select TabId from Mark where TabId='"+TabId+"'";
			List list=session.createQuery(sql).list();
			if(list.isEmpty()){ //如果不存在就删除失败
				SessionAnnotation.closeSession();
				return false;
			}
			sql="delete from Mark where TabId='"+TabId+"'";
			Query query=session.createQuery(sql);
			query.executeUpdate();
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("删除mark表失败！");
			SessionAnnotation.closeSession();
			return false;
		}
	}
	
	public boolean updateMark(Mark m){
		UserDao ud=new UserImplement();
		PictureDao pd=new PictureImplement();
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		try{
//			User u=ud.showUser(m.getUser().getUserID());
//			Picture p=pd.selectSinglePictureFID(m.getPicture().getPID());
			  
			session.update(m);
			System.out.println("修改mark成功！");
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("更行mark表失败");
			SessionAnnotation.closeSession();
			return false;
		}
	}
	
	public boolean updateMarkName(String TabId,String markName){
		Session session=SessionAnnotation.getSession(); 
		session.beginTransaction();
		try{
			Mark m=(Mark)session.get(Mark.class,TabId);
			m.setMarkName(markName);
			session.update(m);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			System.out.println("updateMarkName成功");
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("updateMarkName失败");
			return false;
		}
	}

	
	public Mark getMark(String TabId){
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		Mark m=new Mark();
		try{
			m=(Mark)session.get(Mark.class,TabId);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return m;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getMark失败");
			SessionAnnotation.closeSession();
			return m;
		}
		
	}
	
	
	
	
/*	public static Manager setManager(Manager u){
		String Stat;
		Manager m=new Manager();
		
		return m;
		
	}
*/

	

}
