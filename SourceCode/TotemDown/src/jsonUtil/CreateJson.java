package jsonUtil;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import model.Feedback;
import model.HistoryMark;
import model.Image;
import model.ImageList;
import model.Manager;
import model.Mark;
import model.Picture;
import model.Qualify;
import model.SearchP;
import model.User;

public class CreateJson {
	
	static {
		try{		
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		}catch (Exception e) {
		System.out.println(e);
		}
	}
	
	public static final ThreadLocal<Gson> gsons =new ThreadLocal<Gson>();

	public static User getUser(String json) { 
		User u = new User();
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			gsons.set(gson);
		}
		u = gson.fromJson(json, User.class);
		return u;
	}
	
	public static String getUserJson(User u){
		
		try{
		//String str="";
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			gsons.set(gson);
		}  
		
		String str=gson.toJson(u);
		System.out.println("createJson"+str);
		return str;
		}catch(Exception e){
			System.out.println("getUserJson异常");
			System.out.println(e);
			return "";
		}
	}
	
	public static Picture getPicture(String json){
		Picture p=new Picture();
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			
			gsons.set(gson);
		}
		
		p=gson.fromJson(json,Picture.class);
		return p;
	}
	
	public static String getPictureJson(Picture p){
		
		try{
		String pictureJson="";
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
		}
		pictureJson=gson.toJson(p);
		return pictureJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getPictureJson异常！");
			return "";
		}
	}
	
	public static String getmarkJson(Mark m){
		
		try{
			Gson gson=gsons.get();
			if(gson==null){
				gson=new Gson();
				gsons.set(gson);
			}
			String markJson=gson.toJson(m);
			System.out.println(markJson);
			return markJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getmarkJson异常");
			return "";
		}
	}
	
	public static Mark getMark(String json){ 
		Mark mark=new Mark(); 
		Gson gson=gsons.get(); 
		if(gson==null){ 
			gson=new Gson();   
			gsons.set(gson);   
		}
		mark=gson.fromJson(json, Mark.class); 
		return mark;
	}
	
	
	public static Manager getManager(String json){ 
		Manager manager=new Manager(); 
		Gson gson=gsons.get(); 
		if(gson==null){ 
			gson=new Gson();   
			gsons.set(gson);   
		}
		manager=gson.fromJson(json, Manager.class); 
		return manager;
	}
	
	public static String getManagerJson(Manager m){  
		try{ 
			String managerJson="";
			Gson gson=gsons.get();
			if(gson==null){
				gson=new Gson();
				gsons.set(gson);
			}
			managerJson=gson.toJson(m);
			return managerJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getManagetJson异常");
			return "";
		}
	}
	
	//对志愿者推送历史标签记录
	public static String getHistoryMarkJson(HistoryMark m){  
		try{ 
			String historyJson="";
			Gson gson=gsons.get();
			if(gson==null){
				gson=new Gson(); 
				gsons.set(gson);
			}
			historyJson=gson.toJson(m);
			return historyJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getHistoryMark异常");
			return "";
		}
	}
	//给志愿者提供收索自己喜欢的图片
	public static SearchP getSearchP(String json){
		SearchP s=new SearchP();
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			gsons.set(gson);
		}
		s=gson.fromJson(json,SearchP.class);
		return s;
	}
	
	
	//解析管理员上传的图片数组
	public static ImageList getImageList(String json){ 
		ImageList imageList=new ImageList(); 
		Gson gson=gsons.get(); 
		if(gson==null){ 
			gson=new Gson();   
			gsons.set(gson);   
		}
		imageList=gson.fromJson(json.toString().trim(), ImageList.class); 
		return imageList;
	}
	
	//将管理员上传的图片json数组解析成每一张
	public static Image getImage(String json){ 
		Image image=new Image(); 
		Gson gson=gsons.get(); 
		if(gson==null){ 
			gson=new Gson();   
			gsons.set(gson);   
		}
		image=gson.fromJson(json.toString().trim(), Image.class); 
		return image;
	}
	
	//反馈
	public static Feedback getFeedback(String FeedbackJson){
		Feedback feedback=new Feedback();
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			gsons.set(gson);
		}
		
		feedback=gson.fromJson(FeedbackJson,Feedback.class);
		return feedback;
	}
	
	public static String getFeedbackJson(Feedback fd){  
		try{ 
			String feedbackJson="";
			Gson gson=gsons.get();
			if(gson==null){
				gson=new Gson(); 
				gsons.set(gson);
			}
			fd.setState("true");
			feedbackJson=gson.toJson(fd);
			return feedbackJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getHistoryMark异常");
			return "";
		}
	}
	
	public static String getQualifyJson(Qualify ql){  
		try{ 
			String qualifyJson="";
			Gson gson=gsons.get();
			if(gson==null){
				gson=new Gson(); 
				gsons.set(gson);
			}
//			ql.setState("true");
			qualifyJson=gson.toJson(ql);
			return qualifyJson;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("getQualifyJson异常");
			return "";
		}
	}
	
	public static Qualify getQualify(String qualifyJson){
		Qualify qualify=new Qualify();
		Gson gson=gsons.get();
		if(gson==null){
			gson=new Gson();
			gsons.set(gson);
		}
		
		qualify=gson.fromJson(qualifyJson,Qualify.class);
		return qualify;
	}
	

}
