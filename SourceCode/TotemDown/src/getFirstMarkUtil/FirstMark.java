package getFirstMarkUtil;

import java.net.MalformedURLException;

import com.google.gson.Gson;

public class FirstMark {
	
	public static String getMark(String pAddress){
		String json;
		try {
			json = AIMark.getFirstMark(pAddress);  //�õ���ʼ��Json����
			Gson gson=new Gson();
			Content con=gson.fromJson(json,Content.class);
			String image=con.getResult().getAllValue(); //�õ���������
			System.out.println("���"+image);
			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("getMark�쳣");
			return "";
		}
		
	}

}
