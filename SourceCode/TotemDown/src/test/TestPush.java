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
		
		List<String> simP=new ArrayList<>(); //用于存储从相似用户那里得到的推送图片id
		//Thu Apr 27 20:28:09 CST 201731ZDD
//		String str=PushPicture.getPush("Sun May 21 18:13:33 CST 2017mzZxs");
//		System.out.println("history:"+str);
		
//		DetermineFinalMark.WriteFinalMark();
//		String s="C:/nginx/html/head";
//		new File(s);
		
		//通过查找相似用户
//		PushPicture.getSimUser("Wed May 24 20:49:02 CST 2017b6O8T");
		
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		String hql="from Mark where UserID=?";
		Query q=session.createQuery(hql);
		q.setString(0,"Fri May 26 18:58:59 CST 2017WDDbd");
		List<Mark> mMarks=q.list();//得到该用户的所有历史记录
		List<String> mPid=new ArrayList<>(); //用于保存当前用户历史图片ip
		for(int i=0;i<mMarks.size();i++){
			mPid.add(mMarks.get(i).getPicture().getPID());
		}
		for(int y=0;y<mPid.size();y++){
			System.out.println("当前用户历史："+mPid.get(y));
		}
		
		String sql="select UserID from User where UserID <>'"+"Fri May 26 18:58:59 CST 2017WDDbd"+"'";
		List<String> list2=session.createQuery(sql).list(); //得到其余所有用户id
		Map<String,Integer> similar=new HashMap<>();  //用于存储  <uid，相同图片数量>
		for(int i=0;i<list2.size();i++){//遍历每个用户
			//System.out.println(list2.get(i));
			String h="from Mark where UserID=?";
			Query qu=session.createQuery(h);
			qu.setString(0,list2.get(i));
			List<Mark> uMark=qu.list();//得到对id用户的所有历史记录
			List<String> uPid=new ArrayList<>(); //用于保存id对应的用户历史图片ip
			for(int j=0;j<uMark.size();j++){
				uPid.add(uMark.get(j).getPicture().getPID());
				System.out.println("用户"+i+"图片id: "+uMark.get(j).getPicture().getPID()); 
			}
			//通过比较mPid(当前用户历史图片id)和uPidI(用户id对应用户历史图片id)  找到相似用户 
			for(int u=0;u<mPid.size();u++){ //遍历当前用户所有Pid历史记录
				int m=0;
				for(int j=0;j<uPid.size();j++){
					if(mPid.get(u).equals(uPid.get(j))){ 
						m++;
					}
				}
				if(m!=0){
					similar.put(list2.get(i),m); //<用户id,相同图片数量>
				}
			}
		}
		
		for(Entry<String, Integer> entry:similar.entrySet()){  //遍历所有用户
            System.out.println("和用户:" + entry.getKey() + "  有  " + entry.getValue()+" 张图片相同");
            float si=(float)entry.getValue()/mPid.size();
            System.out.println("用户:" + entry.getKey() + "  和该用户相似度   =" + si);
            if(si>=0){
            	String h="from Mark where UserID=?";
    			Query q1=session.createQuery(h);
    			q1.setString(0,entry.getKey());
    			List<Mark> uMark=q1.list();//得到对id用户的所有历史记录
    			List<String> uPid=new ArrayList<>(); //用于保存id对应的用户历史图片ip
    			for(int j=0;j<uMark.size();j++){
    				uPid.add(uMark.get(j).getPicture().getPID());
    				
    			}
    			
    			//List<String> c=uPid.Except(mPid).Tolist<String>();
    			//得到uPid区别于mPid的部分
    			for(int j=0;j<uPid.size();j++){
    				int o=0;
    				System.out.println(uPid.get(j));
    				for(int n=0;n<mPid.size();n++){ //当uPid当前的图片id和所有的当前用户的都不一样这添加到里面
    					if(!uPid.get(j).equals(mPid.get(n))){ ++o; System.out.println("o值"+o+"  "+mPid.size());}  //寻找当前用户没有的图片
    				}
    				if(o==mPid.size()){ //表示和所有mPid都不同，则是我们要推送的部分
    					System.out.println("simp.ADD");
    					simP.add(uPid.get(j));
    				}
    			}
    			
    			if(simP.size()>=12){
    				System.out.println("simp.size》12： "+simP.size());
    				StringBuilder sb=new StringBuilder();
    				System.out.println("sb "+sb.toString());
    				Picture p0=new Picture();
    				for(int j=0;j<12;j++){ //将得到的数据转换成json数据
    					System.out.println(j+" "+simP.get(j));
    				    //p0=pd.selectSinglePictureFID(simP.get(j));
    					String hql2="from Picture where PID=?";
    					Query qu=session.createQuery(hql2);
    					qu.setParameter(0, simP.get(j));
    					List<Picture> list=qu.list();
    					p0=list.get(0);
    					System.out.println("图片名："+p0.getPName());
    					String s=CreateJson.getPictureJson(p0);
    					System.out.println("pictureJson"+s);
    					if(!s.equals("")) {sb.append(s); }
    				}
    				System.out.println("相似用户得到结果： "+sb.toString()); 
    				break;
    			}else{
    				System.out.println("simp.size "+simP.size());
    				System.out.println("continue");
    				continue;
    			}
    			
            }//如果相似度大于0.5
            
          
            
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
	
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟 
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
		
//		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");//小写的mm表示的是分钟 
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
//		String s2=md.getManagerID("哈哈");
//		System.out.println("getManagerID "+s2);
		
//		String s1=SimUserPicture.getSimU("Tue Jun 13 11:22:17 CST 2017RPpAg");
//		System.out.println("推送1："+s1);
		
		
		String s2="{\"UserBirthday\":\"Feb 1, 1997 12:00:00\",\"UserEmail\":\"null\",\"UserID\":\"Thu Jun 22 19:37:56 CST 20172kn90\",\"UserIntegral\":5,\"UserMajor\":\"学生\",\"UserName\":\"叫姐姐\",\"UserNickName\":\"null\",\"UserPassword\":\"lin123456\",\"UserSex\":\"男\",\"UserTel\":\"13244237698\",\"accomplish\":5,\"pictures\":[],\"state\":\"updateUser\",\"total\":72}";
		String s3="{\"UserEmail\":\"null\",\"UserHeadPortr\":\"http://114.115.210.8/head/cropped_1498443787632.jpg\",\"UserHobby\":\"null\",\"UserID\":\"Fri Jun 16 20:09:38 CST 2017cjBBB\",\"UserIntegral\":41,\"UserMajor\":\"学生\",\"UserName\":\"Aaronl\",\"UserNickName\":\"null\",\"UserPassword\":\"jiuson\",\"UserSex\":\"男\",\"UserTel\":\"13244237697\",\"accomplish\":41,\"pictures\":[],\"state\":\"updateUser\",\"total\":445}";
		String s1="{\"UserBirthday\":\"Mar 2, 3900 12:00:00\",\"UserHobby\":\"null\",\"UserID\":\"Sun Jun 11 18:48:16 CST 2017tjkfI\",\"UserIntegral\":10,\"UserMajor\":\"学生\",\"UserName\":\"哈哈哈\",\"UserNickName\":\"null\",\"UserPassword\":\"123456\",\"UserSex\":\"男\",\"UserTel\":\"13166991256\",\"accomplish\":10,\"pictures\":[],\"state\":\"updateUser\",\"total\":156}";
		String s="{\"state\":\"search\",\"UserTel\":\"13765092205\"}";
		String sd="{\"Content\":\"历史记录查找失败\",\"FbId\":\"Mon Jun 12 21:06:47 CST 2017Bb7fWe\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserTel\":\"13944677340\",\"date\":\"Jun 12, 2017 09:06:47\",\"state\":\"solveFeedback\",\"states\":0}";
		String sdd="{\"Content\":\"图片加载速度慢\",\"FbId\":\"Mon Jun 12 21:06:36 CST 2017JP0Dpf\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserTel\":\"13944677340\",\"date\":\"Jun 12, 2017 9:06:37 PM\",\"state\":\"solveFeedback\",\"states\":0}";
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
		String s ="{\"UserBirthday\":\"May 19, 1997 12:00:00\",\"UserEmail\":\"826976640@qq.com\",\"UserHeadPortr\":\"http://114.115.210.8/head/cropped_1498698423933.jpg\",\"UserHobby\":\"null\",\"UserID\":\"Mon Jun 05 16:38:42 CST 2017zntTy\",\"UserIntegral\":34,\"UserMajor\":\"学生\",\"UserName\":\"啊啊\",\"UserNickName\":\"安徽省 池州市\",\"UserPassword\":\"wo8023you\",\"UserSex\":\"男\",\"UserTel\":\"13944677340\",\"accomplish\":34,\"localAddress\":\"/data/user/0/com.example.dtlp/cache/cropped_1498698423933.jpg\",\"pictures\":[],\"state\":\"updateUser\",\"total\":125}";
		User user=CreateJson.getUser(s);
		System.out.println(user.getUserName());
	}
	
	
	
	
	
}
