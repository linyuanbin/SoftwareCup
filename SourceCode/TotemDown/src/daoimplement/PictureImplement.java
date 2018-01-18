package daoimplement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.summary.TextRankSentence;

import dao.PictureDao;
import getFirstMarkUtil.FirstMark;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Picture;
import translateUtil.Translate;
import util.HanlpUtil;
import util.RandomString;
import util.SuggesterUtil;

public class PictureImplement implements PictureDao {

	@Override
	public boolean AddPicture(Picture p) { // ���ͼƬ��Դ 1
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		p.setPID(new Date() + RandomString.getRandomString(5));
		try {
			session.save(p);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		} catch (Exception e) {
			System.out.println("AddPicture�쳣" + e);
			SessionAnnotation.closeSession();
			return false;
		}
	}

	@Override
	public boolean updatePicture(Picture p) { // 2
		Session session = SessionAnnotation.getSession();
		try {
			session.beginTransaction();
			session.update(p);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			SessionAnnotation.closeSession();
			return false;
		}
	}

	@Override
	public boolean deletePicture(String PID) { // ɾ��ͼƬ3
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String sql = "select PID from Picture where PID='" + PID + "'"; // �����Ƿ���ڸ�ͼƬ
		List list = session.createQuery(sql).list();

		if (list.isEmpty()) { // �������˵��������
			SessionAnnotation.closeSession();
			return false;
		}
		// "delete from User where User_ID='"+User_ID+"'";
		sql = "delete from Picture where UserID='" + PID + "'";
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return true;
	}

	@Override
	public Set<Picture> selectAllPicture() { // �������ݿ������е�ͼƬ���� ���Գɹ� 4
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		Set<Picture> pictures = new HashSet<Picture>();
		String sql = "select PID from Picture";
		List<String> list = new ArrayList<>();
		list = (List<String>) session.createQuery(sql).list();
		System.out.println("list" + list);
		for (String li : list) {
			System.out.println("Pid  " + li);
			Picture p = (Picture) session.get(Picture.class, li);
			pictures.add(p);
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return pictures;
	}

	@Override
	public Picture selectSinglePictureFID(String PID) { // ID��ѯͼƬ5
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		try {

			Picture p = (Picture) session.get(Picture.class, PID);
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return p;
		} catch (Exception e) {
			System.out.println(e);
			SessionAnnotation.closeSession();
			return null;
		}
	}

	@Override
	public Set<Picture> selectPicturesFN(String firstName) { // ��������ƥ������ͼƬ6
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		Set<Picture> pictures = new HashSet<Picture>();
		String sql = "select PID from Picture where FIRSTNAME like '%" + firstName + "%'";
		List<String> list = session.createQuery(sql).list();
		for (String li : list) {
			Picture p = (Picture) session.get(Picture.class, li);
			pictures.add(p);
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return pictures;
	}

	@Override
	public Set<Picture> selectPicturesFM(String pMark) { // ͨ����ǩ��ѯͼƬ 7
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		Set<Picture> pictures = new HashSet<Picture>();
		String sql = "select PID from Mark where MarkName like '%" + pMark + "%'";
		List<String> list = session.createQuery(sql).list();
		for (String li : list) {
			Picture p = (Picture) session.get(Picture.class, li);
			pictures.add(p);
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return pictures;
	}

	@Override
	public Picture selectSinglePictureFN(String Pname) { // 8
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		try {
			String sql = "select PID from picture where Pname like '" + "%" + Pname + "%" + "'";
			List list = session.createQuery(sql).list();
			Picture p = (Picture) session.get(Picture.class, list.get(0).toString());
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return p;
		} catch (Exception e) {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return null;
		}

	}

	@Override
	public String selectPicturesFFN() { // �������ձ�ǩ����� 9
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		// Set<Picture> pictures=new HashSet<Picture>();
		List<Picture> pictures = new ArrayList<>();
		try {
			String hql = "from Picture where FinalMarkName is not null";
			pictures = session.createQuery(hql).list();
			StringBuilder sb2 = new StringBuilder();
			if (pictures.size() != 0) {
				for (int i = 0; i < pictures.size(); i++) {
					if (i < (pictures.size() - 1)) {
						String s = CreateJson.getPictureJson(pictures.get(i));
						sb2.append(s + ",");
					} else {
						String s = CreateJson.getPictureJson(pictures.get(i));
						sb2.append(s);
					}
				}
				System.out.println("��ǩ�����" + sb2);
				session.getTransaction().commit();
				SessionAnnotation.closeSession();
				return sb2.toString();
			} else {
				System.out.println("�ޱ�ǩ�������");
				SessionAnnotation.closeSession();
				return "";
			}
		} catch (Exception e) {
			SessionAnnotation.closeSession();
			System.out.println("������ǩ����쳣��" + e);
			return "";
		}
	}

	@Override
	public String search(String content) { // ־Ը����Ҫ�����Լ�ϲ����ͼƬ
		// try{
		Suggester suggester = new Suggester();
		SuggesterUtil.setSuggester();
		suggester = SuggesterUtil.getSuggester();
		String name = TextRankSentence.getTopSentenceList(content, 10).get(0);
		String s = HanlpUtil.get(name);
		System.out.println("�����ؼ��֣�" + s);
		if (s.equals("")) {
			s = name.replaceAll(" ", "");
		}
		Set<Picture> pictures = new HashSet<>();
		Scanner scanner = new Scanner(s);// ����������ؼ����и�
		scanner.useDelimiter(":");// ���÷ָ���
		Set<Picture> ps = new HashSet<>();
		while (scanner.hasNext()) {
			String key = scanner.next();
			System.out.println("�����ؼ��֣� " + key);
			String con = "";
			if (suggester != null) {
				con = suggester.suggest(key, 1).get(0); // �޸Ĳ���||||||||||
			} else {
				con = key;
			}
			// ps=selectPicturesFN(scanner.next());
			ps = selectPicturesFN(con.trim()); // ͨ��FirstName����
			if (ps.size() != 0) {
				for (Picture pi : ps) {
					pictures.add(pi);
				}
			}
			if (pictures.size() >= 120)
				break;
		} // while
		if (pictures.size() != 0) {
			StringBuilder sb = new StringBuilder();
			String str = "";
			List<Picture> list = new ArrayList<Picture>(pictures);
			for (int i = 0; i < list.size(); i++) {
				str = CreateJson.getPictureJson(list.get(i));
				if (i == 0) {
					if (str != null) {
						sb.append(str);
					}
				} else {
					if (str != null) {
						sb.append("," + str);
					}
				}
			}
			return sb.toString();
		}
		return "";

	}

	@Override
	public String getSimplepicture(String url) {
		System.out.println("��ͼ��ͼִ��");
		String str = "";
		try {
			str = FirstMark.getMark(url);
			System.out.println("�����" + str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!str.trim().equals("")) {
			Scanner scanner = new Scanner(str);
			StringBuilder sb = new StringBuilder();
			// scanner.useDelimiter(":");
			while (scanner.hasNext()) { // ��ǩ���Ļ� �� ��\n�� Ϊ�зֱ�ʾ���з�
				String s = scanner.nextLine();
				String s2 = Translate.getTranslate(s); // .replaceAll("
														// ","")ȥ�����пո�
				sb.append(s2);
				System.out.println(s + "����ɣ�" + s2);
			}
			System.out.println(sb.toString());
			String s = search(sb.toString());
			System.out.println("�������" + s);
			return s;
		} else {
			return "";
		}
	}

	@Override
	public String getSimplepicture2(String url) {
		
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String pJson = "";
		List<Picture> list = new ArrayList<>();
		try {
			String sql = "from Picture where PAddress='" + url + "'";
			Query query = session.createQuery(sql);
			// query.setParameter("1",url);
			list = query.list();
			Picture p = list.get(0);

			if (list.isEmpty()) {
				session.getTransaction().commit();
				SessionAnnotation.closeSession();
				return "";
			} else {
				String s = p.getFirstName();
				System.out.println(s);
				if (s.trim().equals("")) {
					session.getTransaction().commit();
					SessionAnnotation.closeSession();
					return "";
				} else {
					session.getTransaction().commit();
					SessionAnnotation.closeSession();
					pJson = search(s);
					return pJson;
				}
			}
		} catch (Exception e) {
			System.out.println("��ͼ��ͼ�쳣" + e);
			SessionAnnotation.closeSession();
			return "";
		}
	}
}
