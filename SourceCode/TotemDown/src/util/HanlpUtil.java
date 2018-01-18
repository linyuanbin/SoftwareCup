package util;

import java.util.List;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class HanlpUtil {
	
	public static String get(String s){
		StringBuilder sb=new StringBuilder();
		List<Term> termList = NLPTokenizer.segment(s);//·Ö´Ê
		for(int i=0;i<termList.size();i++){ 
			if(termList.get(i).toString().indexOf("/n")!=-1||termList.get(i).toString().indexOf("/vn")!=-1||termList.get(i).toString().indexOf("/nr")!=-1){ 
				String s1=termList.get(i).toString().substring(0,termList.get(i).toString().lastIndexOf("/"));
				if(i==termList.size()-1){//
					sb.append(s1);
				}else{
					sb.append(s1+":");
				}
				System.out.println();
				}
		}
		return sb.toString();
	}

}
