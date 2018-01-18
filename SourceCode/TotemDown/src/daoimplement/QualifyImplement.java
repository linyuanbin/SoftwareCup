package daoimplement;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import hibernateutil.SessionAnnotation;
import model.Qualify;

public class QualifyImplement {

	public boolean saveQuailfy(Qualify q){
		Session session= SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			session.save(q);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
		System.out.println("saveQuailfy“Ï≥£"+e);
		SessionAnnotation.closeSession();
			return false;
		}
	}
	public boolean updateQualify(Qualify q){
		Session session =SessionAnnotation.getSession();
		session.beginTransaction();
		try{
			session.saveOrUpdate(q);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}catch(Exception e){
			System.out.println("updateQualify“Ï≥£"+e);
			SessionAnnotation.closeSession();
			return false;
		}
	}
	
	public Qualify showQualify(){
		Session session =SessionAnnotation.getSession();
		session.beginTransaction();
		Qualify qualify=new Qualify();
		try{
			String hql="from Qualify where qid=?";
			Query q=session.createQuery(hql);
			q.setParameter(0,"1");
			List<Qualify> list=q.list();
			qualify=list.get(0);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return qualify;
		}catch(Exception e){
			System.out.println("showQualify“Ï≥££∫"+e);
			SessionAnnotation.closeSession();
			return qualify;
		}
	}
	
}
