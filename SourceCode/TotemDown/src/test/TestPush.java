package test;

import java.io.File;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import action.DetermineFinalMark;
import action.PushPicture;
import action.SimUserPicture;
import dao.ManagerDao;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.ManagerImplement;
import daoimplement.PictureImplement;
import daoimplement.QualifyImplement;
import daoimplement.UserImplement;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Feedback;
import model.Mark;
import model.Picture;
import model.Qualify;
import model.User;

public class TestPush {
	PictureDao pd=new PictureImplement();
	@Test
	public void testPush(){
		
		List<String> simP=new ArrayList<>(); //���ڴ洢�������û�����õ�������ͼƬid
		//Thu Apr 27 20:28:09 CST 201731ZDD
//		String str=PushPicture.getPush("Sun May 21 18:13:33 CST 2017mzZxs");
//		System.out.println("history:"+str);
		
//		DetermineFinalMark.WriteFinalMark();
//		String s="C:/nginx/html/head";
//		new File(s);
		
		//ͨ�����������û�
//		PushPicture.getSimUser("Wed May 24 20:49:02 CST 2017b6O8T");
		
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		String hql="from Mark where UserID=?";
		Query q=session.createQuery(hql);
		q.setString(0,"Fri May 26 18:58:59 CST 2017WDDbd");
		List<Mark> mMarks=q.list();//�õ����û���������ʷ��¼
		List<String> mPid=new ArrayList<>(); //���ڱ��浱ǰ�û���ʷͼƬip
		for(int i=0;i<mMarks.size();i++){
			mPid.add(mMarks.get(i).getPicture().getPID());
		}
		for(int y=0;y<mPid.size();y++){
			System.out.println("��ǰ�û���ʷ��"+mPid.get(y));
		}
		
		String sql="select UserID from User where UserID <>'"+"Fri May 26 18:58:59 CST 2017WDDbd"+"'";
		List<String> list2=session.createQuery(sql).list(); //�õ����������û�id
		Map<String,Integer> similar=new HashMap<>();  //���ڴ洢  <uid����ͬͼƬ����>
		for(int i=0;i<list2.size();i++){//����ÿ���û�
			//System.out.println(list2.get(i));
			String h="from Mark where UserID=?";
			Query qu=session.createQuery(h);
			qu.setString(0,list2.get(i));
			List<Mark> uMark=qu.list();//�õ���id�û���������ʷ��¼
			List<String> uPid=new ArrayList<>(); //���ڱ���id��Ӧ���û���ʷͼƬip
			for(int j=0;j<uMark.size();j++){
				uPid.add(uMark.get(j).getPicture().getPID());
				System.out.println("�û�"+i+"ͼƬid: "+uMark.get(j).getPicture().getPID()); 
			}
			//ͨ���Ƚ�mPid(��ǰ�û���ʷͼƬid)��uPidI(�û�id��Ӧ�û���ʷͼƬid)  �ҵ������û� 
			for(int u=0;u<mPid.size();u++){ //������ǰ�û�����Pid��ʷ��¼
				int m=0;
				for(int j=0;j<uPid.size();j++){
					if(mPid.get(u).equals(uPid.get(j))){ 
						m++;
					}
				}
				if(m!=0){
					similar.put(list2.get(i),m); //<�û�id,��ͬͼƬ����>
				}
			}
		}
		
		for(Entry<String, Integer> entry:similar.entrySet()){  //���������û�
            System.out.println("���û�:" + entry.getKey() + "  ��  " + entry.getValue()+" ��ͼƬ��ͬ");
            float si=(float)entry.getValue()/mPid.size();
            System.out.println("�û�:" + entry.getKey() + "  �͸��û����ƶ�   =" + si);
            if(si>=0){
            	String h="from Mark where UserID=?";
    			Query q1=session.createQuery(h);
    			q1.setString(0,entry.getKey());
    			List<Mark> uMark=q1.list();//�õ���id�û���������ʷ��¼
    			List<String> uPid=new ArrayList<>(); //���ڱ���id��Ӧ���û���ʷͼƬip
    			for(int j=0;j<uMark.size();j++){
    				uPid.add(uMark.get(j).getPicture().getPID());
    				
    			}
    			
    			//List<String> c=uPid.Except(mPid).Tolist<String>();
    			//�õ�uPid������mPid�Ĳ���
    			for(int j=0;j<uPid.size();j++){
    				int o=0;
    				System.out.println(uPid.get(j));
    				for(int n=0;n<mPid.size();n++){ //��uPid��ǰ��ͼƬid�����еĵ�ǰ�û��Ķ���һ������ӵ�����
    					if(!uPid.get(j).equals(mPid.get(n))){ ++o; System.out.println("oֵ"+o+"  "+mPid.size());}  //Ѱ�ҵ�ǰ�û�û�е�ͼƬ
    				}
    				if(o==mPid.size()){ //��ʾ������mPid����ͬ����������Ҫ���͵Ĳ���
    					System.out.println("simp.ADD");
    					simP.add(uPid.get(j));
    				}
    			}
    			
    			if(simP.size()>=12){
    				System.out.println("simp.size��12�� "+simP.size());
    				StringBuilder sb=new StringBuilder();
    				System.out.println("sb "+sb.toString());
    				Picture p0=new Picture();
    				for(int j=0;j<12;j++){ //���õ�������ת����json����
    					System.out.println(j+" "+simP.get(j));
    				    //p0=pd.selectSinglePictureFID(simP.get(j));
    					String hql2="from Picture where PID=?";
    					Query qu=session.createQuery(hql2);
    					qu.setParameter(0, simP.get(j));
    					List<Picture> list=qu.list();
    					p0=list.get(0);
    					System.out.println("ͼƬ����"+p0.getPName());
    					String s=CreateJson.getPictureJson(p0);
    					System.out.println("pictureJson"+s);
    					if(!s.equals("")) {sb.append(s); }
    				}
    				System.out.println("�����û��õ������ "+sb.toString()); 
    				break;
    			}else{
    				System.out.println("simp.size "+simP.size());
    				System.out.println("continue");
    				continue;
    			}
    			
            }//������ƶȴ���0.5
            
          
            
        }
		
		  System.out.println("simp.size:"+simP.size());
          for(int i=0;i<simP.size();i++){
          	
          	System.out.println("simp: "+simP.get(i));
          }
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
	}
	
	
	@Test
	public void te(){
//		Session session=SessionAnnotation.getSession();
//		session.beginTransaction();
//		Picture p9=new Picture();
//		//p9=pd.selectSinglePictureFID("Sat May 27 17:12:19 CST 201769125");
//		String hql2="from Picture where PID=?";
//		Query qu=session.createQuery(hql2);
//		qu.setParameter(0,"Sat May 27 17:12:19 CST 201769125");
//		List<Picture> list=qu.list();
//		p9=list.get(0);
//		String s=CreateJson.getPictureJson(p9);
//		System.out.println(p9.getPName()+" "+s);
//		
//		session.getTransaction().commit();
//		SessionAnnotation.closeSession();
//		
		String s=PushPicture.getPush("Fri May 26 18:58:59 CST 2017WDDbd");
		System.out.println("testJson="+s);
		if(s.equals("")){
			String s2=SimUserPicture.getSimU("Fri May 26 18:58:59 CST 2017WDDbd");
			System.out.println(s2);
		}
//		String s=SimUserPicture.getSimU("Fri May 26 18:58:59 CST 2017WDDbd");
//		System.out.println(s);
	}
	
	@Test
	public void testDate() throws ParseException{
//		Date date=new Date();
//		//DateFormat df1 = DateFormat.getDateInstance();
//		date.setYear(2017);
//		date.setMonth(5);
//		date.setDate(31);
//		System.out.println(date);
//		System.out.println(date.getYear());
//		System.out.println(date.getMonth());
//		System.out.println(date.getDate());
	
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//Сд��mm��ʾ���Ƿ��� 
//		String dstr="2017-05-31";
//		try {
//			java.util.Date date=sdf.parse(dstr);
//			System.out.println(date);
//			System.out.println(date.getYear()+1900);
//			System.out.println(date.getMonth()+1);
//			System.out.println(date.getDate());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");//Сд��mm��ʾ���Ƿ��� 
//		String dstr="2017-12-12";
//		
//		java.util.Date date=sdf.parse(dstr);
		Date date1=new Date("Thu Jun 15 19:54:15 CST 2017");
		System.out.println(date1);
		
//		String s=new SimpleDateFormat("yyyy-MM-dd").format(new Date());//SimpleDateFormate("yyyy-MM-dd HH:mm:ss").format(new Date());
//		System.out.println(s);
	}
	
	
	@Test
	public void testPushp(){
//		String uid="Mon Jun 05 16:38:42 CST 2017zntTy";
//		String pushPictureJson = SimUserPicture.getSimU(uid);
//		System.out.println("tui "+pushPictureJson);
//		System.out.println("scsc scs csc ".replaceAll(" ","")+"12");
		
		
//		Session session=SessionAnnotation.getSession();
//		session.beginTransaction();
//		List<String> list=PushPicture.getrandP(session,"Mon Jun 05 16:38:42 CST 2017zntTy",12);
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i));
//		}
//		session.getTransaction().commit();
//		SessionAnnotation.closeSession();
		
		UserDao ud=new UserImplement();
		String s=ud.getHistory("Mon Jun 05 16:38:42 CST 2017zntTy");
		
	}
	
	@Test
	public void testQualify(){
//		Session session=SessionAnnotation.getSession();
//		session.beginTransaction();
//		String s="from user where UserTel='13244237695'";
//		List<User> list=session.createQuery(s).list();
//		System.out.println("sc:"+list.get(0).getUserName());
		
	/*	QualifyImplement qd=new QualifyImplement();
		Qualify qu=qd.showQualify();
		System.out.println(qu.getHisNum()+" "+qu.getMarkNum());
		float sf=(float)qu.getNum()/100;
		System.out.println("sf "+sf);
		float sdd=0.553f;
		System.out.println(sdd);
		*/
//		QualifyImplement qd=new QualifyImplement();
//		Qualify qu=new Qualify();
//		qu.setQid(1);
//		qu.setHisNum(20);
//		qu.setMarkNum(30);
//		qd.saveQuailfy(qu);
		
//		UserDao ud=new UserImplement();
//		String s=ud.getUserID("13244237698");
//		System.out.println("userid "+s);
		
//		ManagerDao md =new ManagerImplement();
//		String s2=md.getManagerID("����");
//		System.out.println("getManagerID "+s2);
		
//		String s1=SimUserPicture.getSimU("Tue Jun 13 11:22:17 CST 2017RPpAg");
//		System.out.println("����1��"+s1);
		
		
		String s2="{\"UserBirthday\":\"Feb 1, 1997 12:00:00\",\"UserEmail\":\"null\",\"UserID\":\"Thu Jun 22 19:37:56 CST 20172kn90\",\"UserIntegral\":5,\"UserMajor\":\"ѧ��\",\"UserName\":\"�н��\",\"UserNickName\":\"null\",\"UserPassword\":\"lin123456\",\"UserSex\":\"��\",\"UserTel\":\"13244237698\",\"accomplish\":5,\"pictures\":[],\"state\":\"updateUser\",\"total\":72}";
		String s3="{\"UserEmail\":\"null\",\"UserHeadPortr\":\"http://114.115.210.8/head/cropped_1498443787632.jpg\",\"UserHobby\":\"null\",\"UserID\":\"Fri Jun 16 20:09:38 CST 2017cjBBB\",\"UserIntegral\":41,\"UserMajor\":\"ѧ��\",\"UserName\":\"Aaronl\",\"UserNickName\":\"null\",\"UserPassword\":\"jiuson\",\"UserSex\":\"��\",\"UserTel\":\"13244237697\",\"accomplish\":41,\"pictures\":[],\"state\":\"updateUser\",\"total\":445}";
		String s1="{\"UserBirthday\":\"Mar 2, 3900 12:00:00\",\"UserHobby\":\"null\",\"UserID\":\"Sun Jun 11 18:48:16 CST 2017tjkfI\",\"UserIntegral\":10,\"UserMajor\":\"ѧ��\",\"UserName\":\"������\",\"UserNickName\":\"null\",\"UserPassword\":\"123456\",\"UserSex\":\"��\",\"UserTel\":\"13166991256\",\"accomplish\":10,\"pictures\":[],\"state\":\"updateUser\",\"total\":156}";
		String s="{\"state\":\"search\",\"UserTel\":\"13765092205\"}";
		String sd="{\"Content\":\"��ʷ��¼����ʧ��\",\"FbId\":\"Mon Jun 12 21:06:47 CST 2017Bb7fWe\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserTel\":\"13944677340\",\"date\":\"Jun 12, 2017 09:06:47\",\"state\":\"solveFeedback\",\"states\":0}";
		String sdd="{\"Content\":\"ͼƬ�����ٶ���\",\"FbId\":\"Mon Jun 12 21:06:36 CST 2017JP0Dpf\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserTel\":\"13944677340\",\"date\":\"Jun 12, 2017 9:06:37 PM\",\"state\":\"solveFeedback\",\"states\":0}";
		Feedback qualify=CreateJson.getFeedback(sdd);
		System.out.println(qualify.getContent());
		
		
//		User user=CreateJson.getUser(s);
//		System.out.println(user.getUserName()+"  "+user.getUserPassword()+user.toString());
//		UserDao ud=new UserImplement();
//		String ss=ud.getUserID(user.getUserTel());
////		ud.updateUser(user);  13944677340   15157358735 15596333973 13244232056  13944677342
//		System.out.println("IDCARD"+ss);
		
		Date date=new Date("Jun 12, 2017 09:06:37 PM");
		System.out.println(date.getHours());
	
	}
	
	@Test
	public void testjSon(){
		String s ="{\"UserBirthday\":\"May 19, 1997 12:00:00\",\"UserEmail\":\"826976640@qq.com\",\"UserHeadPortr\":\"http://114.115.210.8/head/cropped_1498698423933.jpg\",\"UserHobby\":\"null\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserIntegral\":34,\"UserMajor\":\"ѧ��\",\"UserName\":\"����\",\"UserNickName\":\"����ʡ ������\",\"UserPassword\":\"wo8023you\",\"UserSex\":\"��\",\"UserTel\":\"13944677340\",\"accomplish\":34,\"localAddress\":\"/data/user/0/com.example.dtlp/cache/cropped_1498698423933.jpg\",\"pictures\":[],\"state\":\"updateUser\",\"total\":125}";
		User user=CreateJson.getUser(s);
		System.out.println(user.getUserName());
	}
	
	
	
	
	
}
