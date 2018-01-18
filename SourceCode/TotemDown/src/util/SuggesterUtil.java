package util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hankcs.hanlp.suggest.Suggester;

import hibernateutil.SessionAnnotation;

public class SuggesterUtil {
	
	private static Suggester suggester=new Suggester();
//	private static int a=0;
/*	static {
		try{
			a++;
			if(a==1){
			Session session=SessionAnnotation.getSession();  //���|||||||||||
			session.beginTransaction();
			String sql="select firstName from Picture";
			List<String> list=session.createQuery(sql).list();
			for (String title : list)//  int q=0;q<(list.size()/3);q++
			{
			    suggester.addSentence(title);  //�������ļ������� 
			}
			
			System.out.println("���سɹ�");
			session.getTransaction().commit(); 
			SessionAnnotation.closeSession();
			}
		}catch (Exception e) {
		System.out.println(e);
		}
	}*/
	
//	private static Suggester suggester;// = new Suggester();
	public static void setSuggester(){
	}
	
	public static Suggester getSuggester(){
		//Suggester suggester=suggesters.get();
		try{
//			suggester=new Suggester();
			Session session=SessionAnnotation.getSession();  //���|||||||||||
			session.beginTransaction();
			String sql="select firstName from Picture";
			List<String> list=session.createQuery(sql).list();
			
			for (String title : list)//  int q=0;q<(list.size()/3);q++
			{
			    suggester.addSentence(title);  //�������ļ�������
			}
			System.out.println("���سɹ�");
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			}catch(Exception e){
				System.out.println("setSuggester�����쳣�� "+e);
				SessionAnnotation.closeSession();
			}
		return suggester;
	}
	
}
