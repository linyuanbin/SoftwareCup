/*package test;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankSentence;

import action.DetermineFinalMark;
import action.PushPicture;
import action.UpdateFirstMark;
import dao.MarkDao;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.MarkImplement;
import daoimplement.PictureImplement;
import daoimplement.UserImplement;
import getMarkUtil.AIMark;
import getMarkUtil.PictureContent;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Feedback;
import model.Mark;
import model.Picture;
import model.User;
import translateUtil.Translate;
import util.HanlpUtil;

public class testMark {
	UserDao ud=new UserImplement();
	PictureDao pd=new PictureImplement();
	MarkDao md=new MarkImplement();
	@Test
	public void test1(){
		
		User u=ud.showUser("Wed May 24 20:49:02 CST 2017b6O8T");
		Picture p=pd.selectSinglePictureFID("Wed May 24 20:33:26 CST 20173lvQp");
		Mark m=new Mark();
		m.setUser(u);
		m.setPicture(p);
		m.setTabId("Wed May 24 20:49:02 CST 2017b6O8T"+"Wed May 24 20:33:26 CST 20173lvQp");
		m.setMarkName("����֮��:����:����֮��");
		m.setMarkDate(new Date());
//		boolean b=md.insertIntoMark("Thu Apr 27 20:28:09 CST 201731ZDD", "Sat Apr 29 15:54:53 CST 2017EDEkG", "atm��");
		boolean b=md.insertMark(m); 
		//boolean b=md.updateMark(m);
		if(b){
			System.out.println("����ɹ���");
		}else{
			System.out.println("����ʧ�ܣ�");
		}
		
		
	}
	
	@Test
	public void GetMark(){
		Mark m=md.getMark("Thu Apr 27 20:28:09 CST 201731ZDD"+"Sat Apr 29 15:54:53 CST 2017EDEkG");
		System.out.println(m.getMarkName());
		
	}
	
	@Test
	public void deleteMark(){
		boolean b=md.deleteOnMark("Thu Apr 27 20:28:09 CST 201731ZDD"+"Sat Apr 29 15:54:53 CST 2017EDEkG");
		if(b){
			System.out.println("ɾ���ɹ�");
		}else{
			System.out.println("ɾ��ʧ��");
		}
	}
	
	@Test 
	public void TestGetHistory(){
		
		//���Ի�ȡ��ʷ��¼
//		String s=ud.getHistory("Sun May 21 18:13:33 CST 2017mzZxs");
//		System.out.println(s);
		
//		User u=ud.showUser("Sat May 27 17:12:24 CST 2017k5Nro");
//		Picture p=pd.selectSinglePictureFID("Wed May 24 20:33:28 CST 2017IbWDS");
//		Mark m=new Mark();
//		m.setPicture(p);
//		m.setUser(u);
//		m.setTabId(u.getPID()+p.getPID());
//		m.setMarkName("�ö�����");
//		md.insertMark(m);
		
//		User u=new User();
//		u.setUserID("ssdsds");
//		u.setUserName("dsd");
//		Picture p=new Picture();
//		p.setPID("sssss"); 
//		p.setPName("ssff");
//		Mark m=new Mark();
//		m.setTabId(u.getUserID()+p.getPID()); 
//		m.setUser(u);
//		m.setPicture(p);
//		m.setMarkName("sddsds");
//		String s=CreateJson.getmarkJson(m);
//		System.out.println(s);
		
//		UpdateFirstMark.SetFirstMark();   //����
		
//		String s=HanlpUtil.get("����һֻ��");  
//		System.out.println(s);
		
//		DetermineFinalMark.WriteFinalMark();
		
		String name=TextRankSentence.getTopSentenceList("����ϲ���������Է�", 10).get(0);
		String s=HanlpUtil.get(name);
		System.out.println(name);
		Set<Picture>pictures =new HashSet<>();
		Scanner scanner=new Scanner(s);
		scanner.useDelimiter(":");//���÷ָ���
		while(scanner.hasNext()){
		pictures=pd.selectPicturesFN(scanner.next());
		for(Picture p:pictures){
			System.out.println(p.getPName());
		}
		}
		
//		String s1="����ϲ���������ϵĲ����Է�",s2="��";  
//		String s=pd.search(s2);
//		System.out.println("���"+s);
		
//		Feedback fb=new Feedback();
//		fb.setContent("�ڴ��ǩʱ�����ˡ�");
//		boolean b=ud.setFeedback(CreateJson.getFeedbackJson(fb));
//		if(b){
//			System.out.println("�����ɹ���");
//		}else{
//			System.out.println("����ʧ�ܣ�");
//		}
		
		try {
//			String s=AIMark.getFirstMark("http://114.115.210.8/photo/atm14.jpg");
			String s=PictureContent.getPImage("http://114.115.210.8/photo/atm10.jpg");
			System.out.println("���"+s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
*/