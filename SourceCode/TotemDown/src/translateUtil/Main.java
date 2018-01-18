package translateUtil;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.junit.Test;
import com.google.gson.Gson;


public class Main {

    //  http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20170510000047004";
    private static final String SECURITY_KEY = "sntOVNkwNp3e2EjAv0c5";

/*    @Test
    public void testApi(){
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        //String query = "高600米";
        
        String query = "weight"; 
        String qq="中国";
       
        try {
        	
        	String s=api.getTransResult(qq, "auto", "zh");
			System.out.println(s);   
			Gson gson=new Gson();  
			Baidu b=gson.fromJson(s,Baidu.class); 
			List l=b.getTrans_result();
			Dat d=gson.fromJson(l.get(0).toString(),Dat.class); 
			System.out.println(d.getDst());
			
			//Data d=gson.fromJson(b.getData().get(0)+"",Data.class);
			//System.out.println(d.getDst());
			
		} catch (UnsupportedEncodingException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
    }*/
    
    @Test
    public void testGet(){
    	
    	String s=Translate.getTranslate("I love china");
    	System.out.println("翻译结果： "+s);
    }

}
