package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.hankcs.hanlp.suggest.Suggester;

import action.DetermineFinalMark;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.PictureImplement;
import daoimplement.UserImplement;
import hibernateutil.SessionAnnotation;
import hibernateutil.SimpleDateFormatUtil;
import jsonUtil.CreateJson;
import model.Mark;
import model.Picture;
import model.Qualify;
import model.User;
import util.RandomString;
import util.SuggesterUtil;

public class TestUser {
	
	UserDao d=new UserImplement(); 
		
	@Test
	public void testuser() throws ParseException{
		
		
		 //模仿注册
	/*	User u=new User();
		u.setUserName("网迦");
		u.setUserTel("13244237897");
		u.setUserBirthday(SimpleDateFormatUtil.getSimpleDateFormat("1996-10-1"));
		u.setUserPassword("666666");
		u.setUserHeadPortr("D:picture\\jiu.jpg");
		u.setUserEmail("888888@qq.com"); 
		if(d.register(u)){
			System.out.println("YES");
		}else{
			System.out.println("NO");
		}*/
		
		//System.out.println("登录情况:"+d.login("13244237695", "666666")); //模仿登录
		
		/*
		User u=new User();  //更新测试
		//u.setUserID("Thu Apr 06 20:25:03 CST 2017GsPbI");
		u.setUserName("孙儿娘"); 
		u.setUserHeadPortr("开心就好");
		u.setUserIntegral(5);
		u.setUserMajor("Android工程师");
		u.setUserPassword("666666"); 
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		//String day="2015-5-1";
		//u.setUserBirthday(sf.parse(day)); 
		u.setUserBirthday(SimpleDateFormatUtil.getSimpleDateFormat("1996-10-1"));
		u.setUserTel("13244236656");  
		d.updateUser(u); */
		
		
		
		/*User u=d.selectUser("13244237695");
		System.out.println(u.getUserName());
		
		u=d.selectUser("张三");
		System.out.println(u.toString());
		
		u=d.selectUser("666666@qq.com");
		System.out.println(u.toString());
		*/
		
		
	/*	Session session=SessionAnnotation.getSession(); //测试显示用户方法
		String sql="select UserID from User";
		List list=session.createQuery(sql).list();
		for(int i=0;i<list.size();i++){
			User u=d.showUser(list.get(i).toString());
			System.out.println(u.toString());
		}*/
		
		
		
		//测试关联关系
		User u1=new User();
		u1.setUserName("三狗子"); 
		u1.setUserHeadPortr("是三生三世次");
		u1.setUserIntegral(6);
		u1.setUserMajor("JAVA工程师");
		u1.setUserPassword("666666"); 
		u1.setUserBirthday(SimpleDateFormatUtil.getSimpleDateFormat("1998-9-1"));
		u1.setUserTel("13244236811");  
		User u2=new User();
		u2.setUserName("四狗子"); 
		u2.setUserHeadPortr("世界次");
		u2.setUserIntegral(6);
		u2.setUserMajor("web工程师");
		u2.setUserPassword("666666"); 
		u2.setUserBirthday(SimpleDateFormatUtil.getSimpleDateFormat("1998-9-2"));
		u2.setUserTel("13244236877");  
		
		Picture p1=new Picture();
		p1.setPID(new Date()+RandomString.getRandomString(5));
	    p1.setPName("");	
	    p1.setPAddress("D:\\picture\\two.jpg");
	    
		Picture p2=new Picture();
		p2.setPID(new Date()+RandomString.getRandomString(5));
	    p2.setPName("广");	
	    p2.setPAddress("D:\\picture\\wwpp.jpg");
		
	   /* u1.getPictures().add(p1);
	    u1.getPictures().add(p2);
	    u2.getPictures().add(p1);*/
	    d.register(u1); //注册
	    d.register(u2);//注册
	    
	    
		
//		Picture p2=new Picture();
//		p2.setPID(new Date()+RandomString.getRandomString(5));
//	    p2.setPName("shijia");	
//	    p2.setPAddress("D:\\picture\\wwpp.jpg"); 
//		User u2=d.showUser("Mon Apr 10 21:27:04 CST 2017F596m"); 
//		if(u2.getPictures()==null){
//			System.out.println("yes");
//		}else{
//			System.out.println("no");
//		}
//		System.out.println(u2.getUserName());
//		System.out.println(u2.getPictures());
//		u2.getPictures().add(p2);
//	    System.out.println(u2.getUserEmail()+"邮件");
//	    d.updateUser(u2);
//	    System.out.println(u2.getUserMajor()+"工作");
	    
//	    
		
	
		
		//System.out.println(d.deleteUser("Mon Apr 10 21:27:04 CST 2017EkBVm")); //测试删除user
		
		/*User u=d.showUser("Mon Apr 10 21:27:04 CST 2017EkBVm");  //测试更新用户
		u.setUserNickName("你粗恶魔那个");
		d.updateUser(u);*/
		
		
/*		try{
			 Session session=SessionAnnotation.getSession();
			    String sql="select RUSERID,RPID from Mark";
//				String sql="select UserID from User";
			    session.beginTransaction();
			    System.out.println("aaaaaaaaaaaa");
			    List list=session.createQuery(sql).list();
			    System.out.println("aaaaaaaaaaaa");
			    System.out.println(list);
			    session.getTransaction().commit();
			    SessionAnnotation.closeSession();
		}catch(Exception e){
			System.out.println(e);
			
		}*/
	}

	@Test
	public void testUserPictures(){
		System.out.println("picture1");     //关联表查询  
	    //String uid=d.getUserID("13244236888");  
	    User u3=d.showUser("Thu Apr 13 19:24:52 CST 2017b3H2V");   
		//User u3=d.selectUser("三狗子"); 
	    System.out.println(u3.getUserName());   
	    Set<Picture> pictures=u3.getPictures(); 
	    System.out.println("pictures是空"+pictures); 
		System.out.println("picture2"+" "+pictures.size());  
	    for (Picture picture : pictures) { 
			System.out.println(picture+"pic"); 
			System.out.println(picture.getPAddress()+" "+picture.getPName()); 
		}
	    
//		Session session=SessionAnnotation.getSession();
//		User user=(User) session.get(User.class, "Thu Apr 13 19:24:52 CST 2017b3H2V");
//		Set<Picture> pictures=user.getPictures();  
//		 for (Picture picture : pictures) { 
//				System.out.println(picture+"pic"); 
//				System.out.println(picture.getPAddress()+" "+picture.getPName()); 
//			}
	}
	 
	 @Test 
	 public void TestMarkTable(){
		 Session session=SessionAnnotation.getSession();
		 String sql="insert into mark values ("+"'Thu Apr 20 19:00:32 CST 2017Qti3c',"+"'Thu Apr 20 19:05:32 CST 2017m71lZ')";
		 session.beginTransaction();
		 session.createQuery(sql);
		 session.getTransaction().commit();
		 SessionAnnotation.closeSession();
	 }
	 
	 @Test
	 public void TestCreateJson(){
		/* User u=d.showUser("Thu Apr 27 20:28:09 CST 201731ZDD");
		 User u2=new User();
		 u2.setUserName("scns");
		 u2.setUserID("scsic");
		 String s=CreateJson.getUserJson(u);  
		 System.out.println(s);*/
//		 
//		 String s="{\"state\":\"mark\",\"TabID\":\"Thu Apr 27 20:28:09 CST 201731ZDDSat Apr 29 15:54:58 CST 2017VGio1\",\"UserID\":\"Thu Apr 27 20:28:09 CST 201731ZDD\",\"PID\":\"Sat Apr 29 15:54:58 CST 2017VGio1\",\"MarkName\":\"天天飞车\"}";
//		 User u=CreateJson.getUser(s);
//		 System.out.println("scsc---"+u.getPID());
		 
		 User u=new User();
		 String s=d.getUserID("13944677348");
		 System.out.println(s);
		 u=d.showUser(s);
		 String str=CreateJson.getUserJson(u);
		 System.out.println(str);
		 
	 }
	 
	@Test
	public void testString(){
		String fdbs = "狗:毛:数组:数组:狗:狗:狗:收到:困死";
		StringBuilder sb=new StringBuilder();
		String[] str = fdbs.split(":");
		Set set = new TreeSet();
		for (int i = 0; i < str.length; i++) {
			set.add(str[i]);
		}
		str = (String[]) set.toArray(new String[0]);
		for (int i = 0; i < str.length; i++) {
			if(i<str.length-1){
				sb.append(str[i]+":");
			}else{
				sb.append(str[i]);
			}
			
		}
		System.out.println(sb.toString());
	}
	
	@Test
	public void testSetMark(){
		DetermineFinalMark.WriteFinalMark();
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		String sql="from Mark group by PID";
		//String sql="select PID,MARKNAME,count(*) from　Mark　group　by PID";
		List<Mark> list=session.createQuery(sql).list();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getPicture().getPName());
		}
		
		session.getTransaction().commit();
		SessionAnnotation.closeSession();  
	}
	
	@Test
	public void testuid(){
		String sql="select UserID from User where UserID <>'" + "Tue Jun 13 11:22:17 CST 2017RPpAg" +"'";
		String sql2="from Picture where PID=?";
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		Query q=session.createQuery(sql2);
		q.setString(0,"Mon Jun 05 16:01:17 CST 2017VJj5w");//Mon Jun 05 16:01:16 CST 20170Yknt
		List<Picture> picture =q.list();
		System.out.println("ming "+picture.get(0).getPName()+"final:"+picture.get(0).getFinalMarkName());
		
		if(picture.get(0).getFinalMarkName()==null||picture.get(0).getFinalMarkName().trim().equals("")){
			System.out.println("false");
		}else{
			System.out.println("true");
		}
		/*List<String> list=session.createQuery(sql).list(); //Mon Jun 05 16:01:17 CST 2017VJj5w
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i)+list.size());
		}*/
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		
	}
	
	@Test
	public void testSearch(){
//		PictureDao pd=new PictureImplement();
//		String s=pd.search("美女");
//		System.out.println("jieguo "+s);
		
//		Suggester suggester=SuggesterUtil.getSuggester();
//		String s=suggester.suggest("沙滩",1).get(0);getClass();
//		System.out.println(s);
		

	}
	
	@Test
	public void tesMap(){
		StringBuilder sb=new StringBuilder();
		Map<String,Integer>map=new HashMap<>();
		String s="阿姨:沙滩:沙滩:老奶奶:老奶奶:大妈:老奶奶:沙滩:游客:老奶奶:老奶奶:老奶奶:沙滩:美女:游客:";
		String[] str = s.split(":");
		int counter=0;
		for(int i=0;i<str.length;i++){ //计算次数循环
			if (map.containsKey(str[i])) {   //判断指定的Key是否存在
	            counter = (Integer)map.get(str[i]);  //根据key取得value
	            map.put(str[i], ++counter);  
	        } else {  
	            map.put(str[i], 1);
	        }
		}
		int[] integer=new int[map.size()]; //保存次数
		String[] string=new String[map.size()];//保存 标签
		int q=0,w=0;
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"    "+entry.getValue());
			integer[q]=entry.getValue();
			string[q]=entry.getKey();
			q++;
		}
		int t=0;
		String str2="";
		for(int i=0;i<integer.length;i++)
			for(int j=0;j<integer.length-i-1;j++){
				if(integer[j]<integer[j+1]){
					t=integer[j];
					integer[j]=integer[j+1];
					integer[j+1]=t;
					str2=string[j];
					string[j]=string[j+1];
					string[j+1]=str2;
				}
			}
		
		if(integer.length>=6){
			for(int i=0;i<6;i++){
				if(integer[i]>2){
					System.out.print(string[i]+" ");
					if(i!=0){
						sb.append(":"+string[i]);
					}else{
						sb.append(string[i]);
					}
				}
			}
		}else{
			for(int i=0;i<integer.length;i++){
				if(integer[i]>2){
					System.out.print(string[i]+"  ");
					if(i!=0){
						sb.append(":"+string[i]);
					}else{
						sb.append(string[i]);
					}
				}
			
			}
		}
		System.out.println();
		System.out.println(sb.toString());
	
	}
	
	
	@Test
	public void testLog(){
		
		try{
			System.out.println(10/0);
			System.out.println("123132");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		try{
			System.out.println("csciosjcs");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("2"+e);
		}
		
	}
	
	
	

}

////////////////////////////////////////////////////////////////////

/**
*                             _ooOoo_
*                            o8888888o
*                            88" . "88
*                            (| -_- |)
*                            O\  =  /O
*                         ____/`---'\____
*                       .'  \\|     |//  `.
*                      /  \\|||  :  |||//  \
*                     /  _||||| -:- |||||-  \
*                     |   | \\\  -  /// |   |
*                     | \_|  ''\---/''  |   |
*                     \  .-\__  `-`  ___/-. /
*                   ___`. .'  /--.--\  `. . __
*                ."" '<  `.___\_<|>_/___.'  >'"".
*               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
*               \  \ `-.   \_ __\ /__ _/   .-` /  /
*          ======`-.____`-.___\_____/___.-`____.-'======
*                             `=---='
*          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
*                     佛祖保佑        永无BUG
*/

//┏┓　   ┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//┃　　　┃ 神兽保佑　　　　　　　　
//┃　　　┃ 代码无BUG！
//┃　　　┗━━━┓
//┃　　　　　　　┣┓
//┃　　　　　　　┏┛
//┗┓┓┏━┳┓┏┛
//┃┫┫　┃┫┫
//┗┻┛　┗┻┛