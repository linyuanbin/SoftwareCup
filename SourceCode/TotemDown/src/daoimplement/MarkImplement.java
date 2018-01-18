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
	
	public boolean insertIntoMark(String UserId,String Pid,String markName){//������
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
			System.out.println("����mark��ʧ��");
			//session.getTransaction().rollback();//����ع�
			SessionAnnotation.closeSession();
			return false;
		}
	}
	 
	public boolean insertMark(Mark m){  //����һ����ǩ��¼
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			session.saveOrUpdate(m);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("����mark��ʧ�ܣ�");
			SessionAnnotation.closeSession();
			return false;
		}
	}
	
	public boolean deleteOnMark(String TabId){  //����idɾ��mark���¼
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			String sql="select TabId from Mark where TabId='"+TabId+"'";
			List list=session.createQuery(sql).list();
			if(list.isEmpty()){ //��������ھ�ɾ��ʧ��
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
			System.out.println("ɾ��mark��ʧ�ܣ�");
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
			System.out.println("�޸�mark�ɹ���");
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("����mark��ʧ��");
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
			System.out.println("updateMarkName�ɹ�");
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("updateMarkNameʧ��");
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
			System.out.println("getMarkʧ��");
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
