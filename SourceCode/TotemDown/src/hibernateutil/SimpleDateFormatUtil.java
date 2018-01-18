package hibernateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SimpleDateFormatUtil {
	public static Date getSimpleDateFormat(String date) {  //获取标标准date格式数据
		 try {
			Date da= new SimpleDateFormat("yyyy-MM-dd").parse(date);
			 //String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
			 System.out.println(da);
			 return da;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
		 }
}