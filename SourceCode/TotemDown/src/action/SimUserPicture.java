package action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dao.PictureDao;
import daoimplement.PictureImplement;
import jsonUtil.CreateJson;
import model.Picture;
//针对相似用户推送
public class SimUserPicture {
	public static PictureDao pd=new PictureImplement();
	public static String getSimU(String UID){
		StringBuilder sb=new StringBuilder();
		List<String> simP=PushPicture.getSimP(UID);
		
		List<Picture> pictures = new ArrayList<>();
		System.out.println("simp.s2 " + simP.size());
		if (simP.size() > 12) {
			for (int i = 0; i < 12; i++) {
//				 Picture p=pd.selectSinglePictureFID(simP.get(i));
//				String s = "from Picture where PID=?";//+simP.get(i)+"'";
//				Query qu = session.createQuery(s);
//				qu.setParameter(0, simP.get(i));
//				List<Picture> l = qu.list();
//				Picture p =new Picture();
//				p=l.get(0);
				Picture p=pd.selectSinglePictureFID(simP.get(i));
				System.out.println("add" + p.getPName());
				pictures.add(p);
			}

			int num=0;
			for (int i = 0; i < pictures.size(); i++) {
				/*if (i < (pictures.size() - 1)) {
					System.out.println(pictures.get(i).getPName()+"转json");
					String s = CreateJson.getPictureJson(pictures.get(i));
					if (!s.equals("")) {
						sb.append(s + ",");
					}
				} else {
					System.out.println(pictures.get(i).getPName()+"转json");
					String s = CreateJson.getPictureJson(pictures.get(i));
					if (!s.equals("")) {
						sb.append(s);
					}
				}*/
				String s = CreateJson.getPictureJson(pictures.get(i));
				if(!s.equals("")){
					num++;
					if(num==1){
						sb.append(s);
					}else{
						sb.append(","+s);
					}
				}
			}
		} else {

			for (int i = 0; i < simP.size(); i++) {
				Picture p = pd.selectSinglePictureFID(simP.get(i));
				System.out.println("add" + p.getPName());
				pictures.add(p);
			}

			int nu=0;
			for (int i = 0; i < pictures.size(); i++) {
				/*if (i < (pictures.size() - 1)) {
					System.out.println(pictures.get(i).getPName());
					String s = CreateJson.getPictureJson(pictures.get(i));
					if (!s.equals("")) {
						sb.append(s + ",");
					}
				} else {
					System.out.println(pictures.get(i).getPName());
					String s = CreateJson.getPictureJson(pictures.get(i));
					if (!s.equals("")) {
						sb.append(s);
					}
				}*/
				String s = CreateJson.getPictureJson(pictures.get(i));
				if(!s.equals("")){
					nu++;
					if(nu==1){
						sb.append(s);
					}else{
						sb.append(","+s);
					}
				}
			}
		}
		return sb.toString();
	}

}
