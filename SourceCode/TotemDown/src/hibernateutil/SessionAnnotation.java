package hibernateutil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionAnnotation {
	
public static final SessionFactory sessionFactory;
	
	static {
		try{		
	 //sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
			//�������ö���
			Configuration config=new Configuration().configure();
			//��������ע�����
			ServiceRegistry serviceRegistry=new ServiceRegistryBuilder()
					.applySettings(config.getProperties())
					.buildServiceRegistry();
			
			//�����Ự����
			sessionFactory=config.buildSessionFactory(serviceRegistry);
	 
		}catch (Throwable e) {
		 throw new ExceptionInInitializerError(e);
		}
	}
	
	public static final ThreadLocal<Session> session =new ThreadLocal<Session>();

	public static Session getSession(){
		Session s=session.get();
		if(s==null){
			s=sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	public static void closeSession(){
		Session s=session.get();
		if(s!=null){
			s.close();
		}
		session.set(null);
	}
	
	
	
}
