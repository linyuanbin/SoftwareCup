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
			System.out.println("�����"+str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(str);
		StringBuilder sb=new StringBuilder();
		//scanner.useDelimiter(":");  
		while(scanner.hasNext()){ //��ǩ���Ļ�   �� ��\n�� Ϊ�зֱ�ʾ���з�
			String s=scanner.nextLine();
			String s2=Translate.getTranslate(s);  //.replaceAll(" ","")ȥ�����пո�
			sb.append(s2);
			System.out.println(s+"����ɣ�"+s2);
		}
		System.out.println(sb.toString());
//		String s=HanlpUtil.get(sb.toString());
//		System.out.println(s);
		String s=pd.search(sb.toString());
		System.out.println("�������"+s);
	}

}
