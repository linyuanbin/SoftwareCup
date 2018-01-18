package test;

import java.util.List;

import org.junit.Test;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import dao.PictureDao;
import daoimplement.PictureImplement;
import util.HanlpUtil;

public class HanlpTest {
	@Test
	public void tes(){
		
		System.out.println("���룺"+CoreSynonymDictionary.distance("��","��")); 
		List<Term> termList2 = NLPTokenizer.segment("�й���ѧԺ���㼼���о������ڳ���������ڽ�����Ȼ���Դ���γ�");//�ִ�
		List<Term> termList = NLPTokenizer.segment("��,this is a cat,��");//�ִ� 
		
		System.out.println(termList);
		/*for(int i=0;i<termList.size();i++){
			for(int j=0;j<termList.size();j++){
				System.out.println(termList.get(i).word+"����"+termList.get(j).word+"="+CoreSynonymDictionary.distance(termList.get(i).word,termList.get(j).word)); 
			} 
		}*/ 
		for(int i=0;i<termList.size();i++){
			if(termList.get(i).toString().indexOf("/n")!=-1){ 
				System.out.println(termList.get(i).toString().substring(0,termList.get(i).toString().lastIndexOf("/")));
				
				System.out.println("bsf   "+termList.get(i).toString().replace("/", ".."));  
				}
		}
	}
	
	@Test
	public void testHanlpl(){
		String s="�������ģ��������";
//		List<Term> termList = NLPTokenizer.segment(s);//�ִ�
//		for(int i=0;i<termList.size();i++){ 
//		System.out.println(termList.get(i));
//		}
		String s2=HanlpUtil.get(s);
		System.out.println(s2);
		
	}
	
	@Test
	public void testFinal(){
		
		PictureDao pd=new PictureImplement();
		
		String s=pd.selectPicturesFFN();
		System.out.println(s);
		
	}
	
	
}
