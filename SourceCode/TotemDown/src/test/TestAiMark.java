package test;

import java.net.MalformedURLException;
import java.util.Scanner;

import org.junit.Test;

import dao.PictureDao;
import daoimplement.PictureImplement;
import getFirstMarkUtil.AIMark;
import getFirstMarkUtil.FirstMark;
import translateUtil.Translate;
import util.HanlpUtil;

public class TestAiMark {
	
	@Test
	public void tests(){
		PictureDao pd=new PictureImplement();
		String str="";
		try {    //AIMark.getFirstMark
			str = FirstMark.getMark("http://scimg.jb51.net/allimg/170711/106-1FG111510CI.jpg");
			System.out.println("结果："+str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(str);
		StringBuilder sb=new StringBuilder();
		//scanner.useDelimiter(":");  
		while(scanner.hasNext()){ //标签中文化   以 “\n” 为切分标示符切分
			String s=scanner.nextLine();
			String s2=Translate.getTranslate(s);  //.replaceAll(" ","")去除所有空格
			sb.append(s2);
			System.out.println(s+"翻译成："+s2);
		}
		System.out.println(sb.toString());
//		String s=HanlpUtil.get(sb.toString());
//		System.out.println(s);
		String s=pd.search(sb.toString());
		System.out.println("搜索结果"+s);
	}

}
