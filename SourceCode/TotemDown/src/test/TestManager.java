package test;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.google.gson.Gson;
import com.hankcs.hanlp.suggest.Suggester;

import dao.ManagerDao;
import dao.PictureDao;
import daoimplement.ManagerImplement;
import daoimplement.PictureImplement;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Manager;
import model.Picture;
import model.User;
import util.SuggesterUtil;

public class TestManager {
	ManagerDao md =new ManagerImplement();
	@Test
	public void testmanager(){
		
		Manager m=new Manager();
		/*m.setmIdCard("12212");
		m.setmName("scjs");
		m.setmEmail("sdsd");
		String s=CreateJson.getManagerJson(m);
		System.out.println(s);*/
		/*String s="{\"mIdCard\":\"12212\",\"mName\":\"scjs\",\"mEmail\":\"sdsd\",\"mAge\":0}";
		String sd="{\"mTel\":\"13244235890\",\"mHeadPort\":\"scs\",\"mWorkNum\":\"wang15201\",\"mSex\":\"Ů\",\"mAge\":\"21\",\"mEmail\":\"206666@qq.com\",\"mName\":\"zhong\",\"mIdCard\":\"2252\",\"state\":\"register\",\"mPassword\":\"666666\"}";
		String sdsds="{\"state\":\"register\",\"mName\":\"aaa\",\"mPassword\":\"maomao1314\",\"mTel\":\"13244234908\",\"mSex\":\"Ů\",\"checkId\":\"gb10086\",\"mIdCard\":\"500225\"}";
		m=CreateJson.getManager(sdsds);
		System.out.println("managerName:"+m.getmName());*/
		m.setmName("ëë��");
		m.setmPassword("maomao1314");
		m.setmIdCard("15361631");
		m.setmTel("13244234665");
		md.register(m);
	}
	
	@Test
	public void testGetFinalMark(){
//		PictureDao pd=new PictureImplement();
//		String ss=pd.selectPicturesFFN();
//		System.out.println("22  "+ss);
		
		String s="{\"state\":\"search\",\"UserTel\":\"13944677348\"}";
		User u=CreateJson.getUser(s);
		System.out.println(u.getUserTel());
//		
		
	}
	
	@Test
	public void testFeedback(){
//		String s=md.selectNewFeedback(); 
//		System.out.println(s);
//		boolean b=md.solveFeedback(s);
//		System.out.println(b);
		
		//��Q�쳣
//		String s="{\"FbId\":\"Mon Jun 05 19:58:10 CST 2017ZML2tN\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"Content\":\"ȫ��ô��bug����3\",\"UserTel\":\"13944677345\",\"date\":\"Jun 5, 2017 7:58:11 PM\",\"states\":0}";
//		boolean b=md.solveFeedback(s);
//		System.out.println(b);
		
		try{
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		Suggester suggester = new Suggester();
		String sql="select firstName from Picture";
		System.out.println("kais");
		List<String> list=session.createQuery(sql).list();
		for (String title : list)
		{
		    suggester.addSentence(title);
		}
		System.out.println("jiesu");
		
	/*	System.out.println(suggester.suggest("����", 1));       // ����
		System.out.println(suggester.suggest("�й�", 1));   // �ַ�
		System.out.println(suggester.suggest("ȡ �� ��", 1));      // ƴ��
		System.out.println(suggester.suggest("ɳ̲", 1));
		System.out.println(suggester.suggest("shatan", 1));
		String s=suggester.suggest("ɳ̲", 1).get(0);
		List<Picture> li=session.createQuery("from Picture where firstName like '%"+s+"%'").list();
		System.out.println("  12"+li.get(0).getFirstName()+"  "+li.size());
		for(int j=0;j<li.size();j++){
			System.out.println(j+" "+li.get(j).getFirstName()+"  "+li.get(j).getPAddress());
		}*/
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	@Test
	public void testSearch(){
//		SuggesterUtil.setSuggester();
		PictureDao pd=new PictureImplement();
		String s=pd.search("ѧ��");
		System.out.println(s);
		
	}
	
	
	@Test
	public void testRegister(){
		
		Manager m=new Manager();
		m.setmIdCard("16666662351");
		m.setCheckId("GB10086");
		m.setmName("sfsf");
		m.setmTel("13244237160");
		String s=md.register(m);
		System.out.println("��Ϣ"+s);
		if(s.trim().equals("")){
			System.out.println("ע��ʧ��");
		}else{
			System.out.println("ע��ɹ�");
		}
	}
	
	
}
