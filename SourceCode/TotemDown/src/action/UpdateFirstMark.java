package action;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.PictureDao;
import daoimplement.PictureImplement;
import getFirstMarkUtil.FirstMark;
//import getMarkUtil.PictureContent;
import hibernateutil.SessionAnnotation;
import model.Picture;
import translateUtil.Translate;

/*
 * �����ϴ���ͼƬ�Զ����ɳ�ʼ��ǩ
 */
public class UpdateFirstMark {
	
	public static PictureDao pd=new PictureImplement();
	
	public static void SetFirstMark(){  //�������ݿ���Picture���е��ֶ���states����Ϊ0˵��Ϊ��ʼ����ǩ
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		
		String hql="from Picture where states=?"; //statesΪ0��˵��δ��ʼ����ǩ
		Query query=session.createQuery(hql);
		query.setParameter(0,0);
		//query.setInteger(0,0);
		List<Picture> pictures=query.list();  //��ѯ����δ���ɱ�ǩ��ͼƬ
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		
		for(Picture p:pictures){
			System.out.println("���ɱ�ǩͼƬ��"+p.getPName()+" Pid="+p.getPID());
			String url=p.getPAddress();
			System.out.println("ͼƬ��ַ��"+url);
			String image=FirstMark.getMark(url);  //PictureContent.getPImage(url);//���ɳ�ʼ��ǩ |||||||||||||||||||
			System.out.println("��ʼ��ǩ��1111��"+image); 
			Scanner scanner=new Scanner(image); 
			StringBuilder sb=new StringBuilder(); 
			//scanner.useDelimiter(":");  
			while(scanner.hasNext()){ //��ǩ���Ļ�   �� ��\n�� Ϊ�зֱ�ʾ���з�
				String s=scanner.nextLine();
				String s2=Translate.getTranslate(s);  //.replaceAll(" ","")ȥ�����пո�
				sb.append(s2);
				System.out.println(s+"����ɣ�"+s2);
//				try {    //2
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			System.out.println("���õ���ʼ��ǩ��"+sb.toString());
			boolean b=false;
			if(!sb.toString().equals("")){
				p.setFirstName(sb.toString().trim());
				p.setStates(1); //��״ֵ̬��Ϊ1��ǩ������Ҫ��ʼ����ǩ
//				session.update(p);
				b=pd.updatePicture(p);
			}
			System.out.println("��ʼ�����"+b);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("��ʼ��ǩ�쳣 "+e);
			}
			
		}//for
		
	}

}
