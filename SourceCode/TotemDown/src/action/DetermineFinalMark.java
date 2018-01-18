package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;

import dao.PictureDao;
import daoimplement.PictureImplement;
import daoimplement.QualifyImplement;
import hibernateutil.SessionAnnotation;
import model.Mark;
import model.Picture;
import model.Qualify;
import util.HanlpUtil;

public class DetermineFinalMark {
	// 确定最终标签模块

	private static PictureDao pd = new PictureImplement();
	private static QualifyImplement qd = new QualifyImplement();

	public static void WriteFinalMark() {
		System.out.println("确定标签系统执行！");

		try {
			Qualify qualify = qd.showQualify();
			Session session = SessionAnnotation.getSession();
			session.beginTransaction();
			String hql = "from Mark where MarkName is not null";
			// String hql2="form Mark where MarkName is not null group by PID
			// HAVING count(PID)>1";
			List<Mark> list = session.createQuery(hql).list();
			Map<String, Integer> map = new HashMap<String, Integer>();// 用于记录对应PID
																		// 出现的次数
			for (int i = 0; i < list.size(); i++) {// 计算图片被标签的次数
				// if(list.get(i).getPicture().getFinalMarkName().equals("")||list.get(i).getPicture().getFinalMarkName()==null){
				// //只计算没生成最终标签的
				if (map.get(list.get(i).getPicture().getPID()) != null) {
					map.put(list.get(i).getPicture().getPID(), map.get(list.get(i).getPicture().getPID()) + 1);
				} else {
					map.put(list.get(i).getPicture().getPID(), 1);
				}
				// }
			}

			Map<String, String> maps = new HashMap<>();
			for (Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("map数据: " + entry.getKey() + "= " + entry.getValue());
				if (entry.getValue() >= qualify.getMarkNum()) { // 打到一定数量就可以确定标签20
					Picture p0 = (Picture) session.get(Picture.class, entry.getKey());  //只有最终标签为空时才添加，确地过最终标签的就不需要重新生成了
					if(p0.getFinalMarkName()==null||p0.getFinalMarkName().trim().equals("")){
						maps.put(entry.getKey(),"");
					}
				}
			}

			// maps保存的都是标签数量够的图片id
			for (Entry<String, String> entry : maps.entrySet()) { // maps<PID,"">
				StringBuilder sb=new StringBuilder();
				for (int i = 0; i < list.size(); i++) {//取出此PID的所有标签
					if (entry.getKey().endsWith(list.get(i).getPicture().getPID())) { // list<Mark> 相同为true 不同为false
						System.out.println("biaq:  "+list.get(i).getMarkName());
//						maps.put(entry.getKey(), //
//								getString(HanlpUtil.get(entry.getValue() + list.get(i).getMarkName()))); // 给标签分词去除重复再添加进去
						// maps.put(entry.getKey(),entry.getValue()+list.get(i).getMarkName());
						// //给标签分词在添加进去
						sb.append(HanlpUtil.get(list.get(i).getMarkName())+":");
					}
					
				}//list for
				System.out.println("sb.tostring: "+sb.toString());
				//保存到maps
				maps.put(entry.getKey(),
						getStrings(sb.toString())); //去除只有出现一两次的偶然性标签
				System.out.println("sb.tostring  "+sb.toString());
			}//map for

			for (Entry<String, String> entry : maps.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
				// 将得到的最终标签保存到对应id的表中图片处
				Picture p = (Picture) session.get(Picture.class, entry.getKey());
				System.out.println(p.getPName());
				p.setFinalMarkName(entry.getValue().trim());
				System.out.println(p.getPID()+"的最终标签 ：  "+entry.getValue().trim());
				p.setFinalTime(new Date()); //6.26 
				session.update(p);       //保存最终标签
			}
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
		} catch (Exception e) {
			System.out.println("确定最终标签异常WriteFinalMark：" + e);
			SessionAnnotation.closeSession();
		}
	}

	public static String getString(String s) { // 给标签中重复的去除
		// String fdbs = "狗:毛:数组:数组:狗:狗:狗:收到:困死";
		StringBuilder sb = new StringBuilder();
		String[] str = s.split(":");
		Set<String> set = new TreeSet();
		for (int i = 0; i < str.length; i++) {
			set.add(str[i]);
		}
		str = (String[]) set.toArray(new String[0]);
		for (int i = 0; i < str.length; i++) {
			if (i < str.length - 1) {
				sb.append(str[i] + ":");
			} else {
				sb.append(str[i]);
			}

		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String getStrings(String s) {
		StringBuilder sb = new StringBuilder();
		Map<String,Integer>map=new HashMap<>();
//		String s="阿姨:沙滩:沙滩:老奶奶:老奶奶:大妈:老奶奶:沙滩:游客:老奶奶:老奶奶:老奶奶:沙滩:美女:游客:";
		String[] str = s.split(":");
		int counter=0;
		for(int i=0;i<str.length;i++){ //计算次数循环
			if (map.containsKey(str[i])) {   //判断指定的Key是否存在
	            counter = (Integer)map.get(str[i]);  //根据key取得value
	            map.put(str[i], ++counter);
	        } else {  
	            map.put(str[i], 1);
	        }
		}
		int[] integer=new int[map.size()]; //保存次数
		String[] string=new String[map.size()];//保存 标签
		int q=0,w=0;
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"    "+entry.getValue());
			integer[q]=entry.getValue();
			string[q]=entry.getKey();
			q++;
		}
		int t=0;
		String str2="";
		for(int i=0;i<integer.length;i++)
			for(int j=0;j<integer.length-i-1;j++){
				if(integer[j]<integer[j+1]){
					t=integer[j];
					integer[j]=integer[j+1];
					integer[j+1]=t;
					str2=string[j];
					string[j]=string[j+1];
					string[j+1]=str2;
				}
			}
		
		if(integer.length>=6){
			for(int i=0;i<6;i++){
				if(integer[i]>2){
					System.out.print(string[i]+" ");
					if(i!=0){
						sb.append(":"+string[i]);
					}else{
						sb.append(string[i]);
					}
				}
			}
		}else{
			for(int i=0;i<integer.length;i++){
				if(integer[i]>2){
					System.out.print(string[i]+"  ");
					if(i!=0){
						sb.append(":"+string[i]);
					}else{
						sb.append(string[i]);
					}
				} 
			}
		}
		
		return sb.toString();
	}

}
