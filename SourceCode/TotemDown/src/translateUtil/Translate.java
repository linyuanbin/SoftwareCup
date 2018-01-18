package translateUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.gson.Gson;

public class Translate {
	//http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20170510000047004";
    private static final String SECURITY_KEY = "sntOVNkwNp3e2EjAv0c5";
    
	public static String getTranslate(String content){ //该模块只需调用该函数就实现翻译
		String cn="";
	       TransApi api = new TransApi(APP_ID, SECURITY_KEY);
	        //String query = "";
	        String query = content; 
	        //String qq="";
	       
	        try {
	        	
	        	String s=api.getTransResult(query, "auto", "zh");  //原文语言种自动识别，目的语言种为中文
				System.out.println(s);   
				Gson gson=new Gson();  
				Baidu b=gson.fromJson(s,Baidu.class); 
				List l=b.getTrans_result();
				Dat d=gson.fromJson(l.get(0).toString(),Dat.class);
				cn=d.getDst();
				System.out.println("翻译结果："+cn);
				
				//Data d=gson.fromJson(b.getData().get(0)+"",Data.class);
				//System.out.println(d.getDst());
				return cn;
			} catch (UnsupportedEncodingException e) { 
				e.printStackTrace();
				return "";
			}
		
	}

}
