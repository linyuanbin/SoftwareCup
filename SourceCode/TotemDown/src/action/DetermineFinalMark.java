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
	// ȷ�����ձ�ǩģ��

	private static PictureDao pd = new PictureImplement();
	private static QualifyImplement qd = new QualifyImplement();

	public static void WriteFinalMark() {
		System.out.println("ȷ����ǩϵͳִ�У�");

		try {
			Qualify qualify = qd.showQualify();
			Session session = SessionAnnotation.getSession();
			session.beginTransaction();
			String hql = "from Mark where MarkName is not null";
			// String hql2="form Mark where MarkName is not null group by PID
			// HAVING count(PID)>1";
			List<Mark> list = session.createQuery(hql).list();
			Map<String, Integer> map = new HashMap<String, Integer>();// ���ڼ�¼��ӦPID
																		// ���ֵĴ���
			for (int i = 0; i < list.size(); i++) {// ����ͼƬ����ǩ�Ĵ���
				// if(list.get(i).getPicture().getFinalMarkName().equals("")||list.get(i).getPicture().getFinalMarkName()==null){
				// //ֻ����û�������ձ�ǩ��
				if (map.get(list.get(i).getPicture().getPID()) != null) {
					map.put(list.get(i).getPicture().getPID(), map.get(list.get(i).getPicture().getPID()) + 1);
				} else {
					map.put(list.get(i).getPicture().getPID(), 1);
				}
				// }
			}

			Map<String, String> maps = new HashMap<>();
			for (Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("map����: " + entry.getKey() + "= " + entry.getValue());
				if (entry.getValue() >= qualify.getMarkNum()) { // ��һ�������Ϳ���ȷ����ǩ20
					Picture p0 = (Picture) session.get(Picture.class, entry.getKey());  //ֻ�����ձ�ǩΪ��ʱ����ӣ�ȷ�ع����ձ�ǩ�ľͲ���Ҫ����������
					if(p0.getFinalMarkName()==null||p0.getFinalMarkName().trim().equals("")){
						maps.put(entry.getKey(),"");
					}
				}
			}

			// maps����Ķ��Ǳ�ǩ��������ͼƬid
			for (Entry<String, String> entry : maps.entrySet()) { // maps<PID,"">
				StringBuilder sb=new StringBuilder();
				for (int i = 0; i < list.size(); i++) {//ȡ����PID�����б�ǩ
					if (entry.getKey().endsWith(list.get(i).getPicture().getPID())) { // list<Mark> ��ͬΪtrue ��ͬΪfalse
						System.out.println("biaq:  "+list.get(i).getMarkName());
//						maps.put(entry.getKey(), //
//								getString(HanlpUtil.get(entry.getValue() + list.get(i).getMarkName()))); // ����ǩ�ִ�ȥ���ظ�����ӽ�ȥ
						// maps.put(entry.getKey(),entry.getValue()+list.get(i).getMarkName());
						// //����ǩ�ִ�����ӽ�ȥ
						sb.append(HanlpUtil.get(list.get(i).getMarkName())+":");
					}
					
				}//list for
				System.out.println("sb.tostring: "+sb.toString());
				//���浽maps
				maps.put(entry.getKey(),
						getStrings(sb.toString())); //ȥ��ֻ�г���һ���ε�żȻ�Ա�ǩ
				System.out.println("sb.tostring  "+sb.toString());
			}//map for

			for (Entry<String, String> entry : maps.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
				// ���õ������ձ�ǩ���浽��Ӧid�ı���ͼƬ��
				Picture p = (Picture) session.get(Picture.class, entry.getKey());
				System.out.println(p.getPName());
				p.setFinalMarkName(entry.getValue().trim());
				System.out.println(p.getPID()+"�����ձ�ǩ ��  "+entry.getValue().trim());
				p.setFinalTime(new Date()); //6.26 
				session.update(p);       //�������ձ�ǩ
			}
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
		} catch (Exception e) {
			System.out.println("ȷ�����ձ�ǩ�쳣WriteFinalMark��" + e);
			SessionAnnotation.closeSession();
		}
	}

	public static String getString(String s) { // ����ǩ���ظ���ȥ��
		// String fdbs = "��:ë:����:����:��:��:��:�յ�:����";
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
//		String s="����:ɳ̲:ɳ̲:������:������:����:������:ɳ̲:�ο�:������:������:������:ɳ̲:��Ů:�ο�:";
		String[] str = s.split(":");
		int counter=0;
		for(int i=0;i<str.length;i++){ //�������ѭ��
			if (map.containsKey(str[i])) {   //�ж�ָ����Key�Ƿ����
	            counter = (Integer)map.get(str[i]);  //����keyȡ��value
	            map.put(str[i], ++counter);
	        } else {  
	            map.put(str[i], 1);
	        }
		}
		int[] integer=new int[map.size()]; //�������
		String[] string=new String[map.size()];//���� ��ǩ
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
