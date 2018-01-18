package getFirstMarkUtil;

import java.net.MalformedURLException;

import com.google.gson.Gson;

public class FirstMark {
	
	public static String getMark(String pAddress){
		String json;
		try {
			json = AIMark.getFirstMark(pAddress);  //得到初始的Json数据
			Gson gson=new Gson();
			Content con=gson.fromJson(json,Content.class);
			String image=con.getResult().getAllValue(); //得到具体内容
			System.out.println("结果"+image);
			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("getMark异常");
			return "";
		}
		
	}

}
