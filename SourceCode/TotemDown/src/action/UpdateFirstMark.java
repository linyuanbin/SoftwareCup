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
 * 给新上传的图片自动生成初始标签
 */
public class UpdateFirstMark {
	
	public static PictureDao pd=new PictureImplement();
	
	public static void SetFirstMark(){  //对于数据库中Picture表中的字段在states属性为0说明为初始化标签
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		
		String hql="from Picture where states=?"; //states为0的说明未初始化标签
		Query query=session.createQuery(hql);
		query.setParameter(0,0);
		//query.setInteger(0,0);
		List<Picture> pictures=query.list();  //查询所有未生成标签的图片
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		
		for(Picture p:pictures){
			System.out.println("生成标签图片名"+p.getPName()+" Pid="+p.getPID());
			String url=p.getPAddress();
			System.out.println("图片地址："+url);
			String image=FirstMark.getMark(url);  //PictureContent.getPImage(url);//生成初始标签 |||||||||||||||||||
			System.out.println("初始标签名1111："+image); 
			Scanner scanner=new Scanner(image); 
			StringBuilder sb=new StringBuilder(); 
			//scanner.useDelimiter(":");  
			while(scanner.hasNext()){ //标签中文化   以 “\n” 为切分标示符切分
				String s=scanner.nextLine();
				String s2=Translate.getTranslate(s);  //.replaceAll(" ","")去除所有空格
				sb.append(s2);
				System.out.println(s+"翻译成："+s2);
//				try {    //2
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			System.out.println("最后得到初始标签："+sb.toString());
			boolean b=false;
			if(!sb.toString().equals("")){
				p.setFirstName(sb.toString().trim());
				p.setStates(1); //将状态值置为1标签不再需要初始化标签
//				session.update(p);
				b=pd.updatePicture(p);
			}
			System.out.println("初始化情况"+b);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("初始标签异常 "+e);
			}
			
		}//for
		
	}

}
