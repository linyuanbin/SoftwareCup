package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hankcs.hanlp.suggest.Suggester;

import dao.PictureDao;
import dao.UserDao;
import daoimplement.PictureImplement;
import daoimplement.QualifyImplement;
import daoimplement.UserImplement;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Mark;
import model.Picture;
import model.Qualify;
import model.User;
import util.SuggesterUtil;

public class PushPicture {// ����ͼƬ
	public static UserDao ud = new UserImplement();
	public static PictureDao pd = new PictureImplement();
	public static QualifyImplement qd = new QualifyImplement();

	public static String getPush(String UserId) { // ��¼һ������12��
		Qualify qualify = qd.showQualify();
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		StringBuilder jsonPicture = new StringBuilder();
		// jsonPicture.append("");
		// String sql="select PID from Mark where USERID ='"+UserId+"'";
		String hql = "from Mark where UserId=?";// +UserId+"'";
		Query query = session.createQuery(hql);
		query.setParameter(0, UserId.trim());
		// query.setString(0,UserId);
		List<Mark> list = query.list();
		System.out.println("��ʷ��¼��Ŀ��" + list.size() + "  " + list.toString());
		if (list.size() == 0 || list.size() <= qualify.getHisNum()) { // ���Ñ�����������������ͣ��������

			List<String> mPid = new ArrayList<String>(); // �õ���ǰ�û�������ʷ��¼���������ų�
			for (int i = 0; i < list.size(); i++) {
				mPid.add(list.get(i).getPicture().getPID());
			}

			// String sql2="select PID,PAddress from Picture ORDER BY RAND()";
			// //�����ݿ������ȡ������ͼƬ��Ϣ
			String hql2 = "from Picture where FinalMarkName is null order by rand()";// ֻ��û��ȷ�����ձ�ǩ��ͼƬ��Ҫ����
			Query q = session.createQuery(hql2);
			// q.setFirstResult(0); // �ӵ�0����¼��ʼȡ
			// q.setMaxResults(35); // ȡ32����¼ ȥ��
			List<Picture> pictures = q.list();
			int num = 0;
			for (int i = 0; i < pictures.size(); i++) {
				int n = 0;
				for (int j = 0; j < mPid.size(); j++) {
					if (!mPid.get(j).equals(pictures.get(i))) {// ȥ���û���ʷ��¼�����е�
						n++;
					} else {
						break;
					}
				}
				if (n == mPid.size()) { // ֻ���û�û�д���Ĳ�����
					String s = CreateJson.getPictureJson(pictures.get(i));
					if (!s.equals("")) {
						num++;
						if (num != 12) {
							jsonPicture.append(s + ",");
						} else {
							jsonPicture.append(s);
						}
					}
				}
				if (num == 12)
					break;
			}
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return jsonPicture.toString();

		} else { // ����û��������
			// ����user���Ƽ�����
			System.out.println("��������");
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			System.out.println("jsonPicture  " + jsonPicture.toString());
			return "";
		}
	}

	public static String getRandPush(String UserId) { // ��ȡ�������
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		StringBuilder jsonPicture = new StringBuilder();
		String hql = "from Mark where UserId=?";// +UserId+"'";
		Query query = session.createQuery(hql);
		query.setParameter(0, UserId.trim());
		List<Mark> list = query.list();
		List<String> mPid = new ArrayList<String>(); // �õ���ǰ�û�������ʷ��¼���������ų�
		for (int i = 0; i < list.size(); i++) {
			mPid.add(list.get(i).getPicture().getPID());
		}
		// //�����ݿ������ȡ������ͼƬ��Ϣ
		String hql2 = "from Picture where FinalMarkName is null order by rand()";// ֻ��û��ȷ�����ձ�ǩ��ͼƬ��Ҫ����
		Query q = session.createQuery(hql2);
		// q.setFirstResult(0); // �ӵ�0����¼��ʼȡ
		// q.setMaxResults(35); // ȡ32����¼ ȥ��
		List<Picture> pictures = q.list();
		int num = 0;
		for (int i = 0; i < pictures.size(); i++) {
			int n = 0;
			for (int j = 0; j < mPid.size(); j++) {
				if (!mPid.get(j).equals(pictures.get(i))) {// ȥ���û���ʷ��¼�����е�
					n++;
				} else {
					break;
				}
			}
			if (n == mPid.size()) { // ֻ���û�û�д���Ĳ�����
				String s = CreateJson.getPictureJson(pictures.get(i));
				if (!s.equals("")) {
					num++;
					if (num != 12) {
						jsonPicture.append(s + ",");
					} else {
						jsonPicture.append(s);
					}
				}
			}
			if (num == 12)
				break;
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return jsonPicture.toString();

	}

	public static List<String> getSimP(String UID) { // �õ������û��ṩ��ͼƬ��Դ
		Qualify qualify = qd.showQualify();
		float similarity = (float) qualify.getNum() / 100; // �������ƶ�
		if (similarity <= 0 || similarity >= 1) { // �������õ������ƶȲ��Ϸ���������ֵ
			similarity = 0.5f;
		}
		List<String> simP = new ArrayList<>(); // ���ڴ洢�������û�����õ�������ͼƬid
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String hql = "from Mark where UserID=?";
		Query q = session.createQuery(hql);
		q.setString(0, UID);
		List<Mark> mMarks = q.list();// �õ����û���������ʷ��¼
		List<String> mPid = new ArrayList<>(); // ���ڱ��浱ǰ�û���ʷͼƬip
		for (int i = 0; i < mMarks.size(); i++) {
			mPid.add(mMarks.get(i).getPicture().getPID());
		}

		String sql = "select UserID from User where UserID <>'" + UID + "'";
		List<String> list2 = session.createQuery(sql).list(); // �õ����������û�id
		Map<String, Integer> similar = new HashMap<>(); // ���ڴ洢 <uid����ͬͼƬ����>
		for (int i = 0; i < list2.size(); i++) {// ����ÿ���û�
			// System.out.println(list2.get(i));
			String h = "from Mark where UserID=?";
			Query qu = session.createQuery(h);
			qu.setString(0, list2.get(i));
			List<Mark> uMark = qu.list();// �õ�list�ж�Ӧid�û���������ʷ��¼
			List<String> uPid = new ArrayList<>(); // ���ڱ���id��Ӧ���û���ʷͼƬip
			for (int j = 0; j < uMark.size(); j++) {
				uPid.add(uMark.get(j).getPicture().getPID());
				// System.out.println("�û�" +i + "ͼƬid: " +
				// uMark.get(j).getPicture().getPID());
			}
			// ͨ���Ƚ�mPid(��ǰ�û���ʷͼƬid)��uPidI(�û�id��Ӧ�û���ʷͼƬid) �ҵ������û�
			int sameNum = 0;
			for (int u = 0; u < mPid.size(); u++) { // ������ǰ�û�����Pid��ʷ��¼
				int m = 0;
				for (int j = 0; j < uPid.size(); j++) {
					if (mPid.get(u).equals(uPid.get(j))) {
						m++;
					}
				}
				if (m != 0) {
					// similar.put(list2.get(i), m); // <�û�id,��ͬͼƬ����>
					sameNum++;
				}
			}
			similar.put(list2.get(i), sameNum); // <�û�id,��ͬͼƬ����>
		}

		for (Entry<String, Integer> entry : similar.entrySet()) {
			System.out.println("����");
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}

		for (Entry<String, Integer> entry : similar.entrySet()) { // ���������û�
			System.out.println("���û�:" + entry.getKey() + "  ��  " + entry.getValue() + " ��ͼƬ��ͬ");
			float si = (float) entry.getValue() / mPid.size();
			System.out.println("�û�:" + entry.getKey() + "  �͸��û����ƶ�   =" + si);
			if (si >= similarity) { // ��ʾ�ȴ���0.5�Ķ���Ϊ�����û�
				System.out.println("�����û�id:" + entry.getKey());
				String h = "from Mark where UserID=?";
				Query q1 = session.createQuery(h);
				// q1.setString(0, entry.getKey());
				q1.setParameter(0, entry.getKey());
				List<Mark> uMark = q1.list();// �õ���id�û���������ʷ��¼
				List<String> uPid = new ArrayList<>(); // ���ڱ���id��Ӧ���û���ʷͼƬip
				for (int j = 0; j < uMark.size(); j++) {
					uPid.add(uMark.get(j).getPicture().getPID());
				}

				// List<String> c=uPid.Except(mPid).Tolist<String>();
				// �õ�uPid������mPid�Ĳ���
				for (int j = 0; j < uPid.size(); j++) {
					int o = 0;
					System.out.println(uPid.get(j));
					for (int n = 0; n < mPid.size(); n++) {// ��uPid��ǰ��ͼƬid�����еĵ�ǰ�û��Ķ���һ������ӵ�����
						if (!uPid.get(j).equals(mPid.get(n))) {
							++o;
						} // Ѱ�ҵ�ǰ�û�û�е�ͼƬ
					}
					if (o == mPid.size()) { // ��ʾ������mPid����ͬ����������Ҫ���͵Ĳ���
						String hql1 = "from Picture where PID=?";
						Query que = session.createQuery(hql1);
						que.setParameter(0, uPid.get(j));
						List<Picture> picture = que.list();
						if (picture.get(0).getFinalMarkName() == null
								|| picture.get(0).getFinalMarkName().trim().equals("")) { // �޸�||||||||
							System.out.println("simp.ADD");
							simP.add(uPid.get(j));
						}
					}
				}

				if (simP.size() >= 12) {
					System.out.println("enogh break");
					break;
				} else {
					System.out.println("simp.size continue" + simP.size());
					System.out.println("continue");
					continue;
				}

			} // ������ƶȴ��� Qualify.Num/100
		} // ���������û�
			// System.out.println("simp.size:" + simP.size());
			// for (int i = 0; i < simP.size(); i++) {
			// System.out.println("simp-: " + simP.get(i));
			// }

		if (simP.size() < 12) {// ������������û����ݵõ��˻�����12����Ҫ�������
			System.out.println("����" + simP.size());
			// ��ͨ���û���ְҵ��������
			String hql3 = "from User where UserID=?";
			Query query = session.createQuery(hql3);
			query.setParameter(0, UID);
			List<User> users = query.list();
			User u = users.get(0);// �õ���ǰ�û�
			if (!u.getUserMajor().equals("")) { // ���
				session.getTransaction().commit(); // |||||||||||||||||||||
				SessionAnnotation.closeSession();
				// SuggesterUtil SugUtil=new SuggesterUtil();
				SuggesterUtil.setSuggester();
				Suggester suggester = SuggesterUtil.getSuggester();
				System.out.println("session�ٴο���");
				System.out.println(u.getUserMajor());
				String con = "";
				if (suggester != null) {
					con = suggester.suggest(u.getUserMajor(), 1).get(0); // ͨ���û�ְҵ�����Ƽ�
				} else {
					con = u.getUserMajor().replace(" ", "");
				}
				System.out.println("con " + con);
				session = SessionAnnotation.getSession();// ||||||||||||||||||||
				session.beginTransaction();
				System.out.println("session�ٴο�����ʾ�û����ݲ���");
				String hql2 = "select PID from Picture where FIRSTNAME like '%" + con + "%'";
				List<String> pid = session.createQuery(hql2).list(); // ����ְҵ�õ���ͼƬ
				for (int j = 0; j < pid.size(); j++) {
					int o = 0;
					System.out.println(pid.get(j));
					for (int n = 0; n < mPid.size(); n++) { // ��uPid��ǰ��ͼƬid�����еĵ�ǰ�û��Ķ���һ������ӵ�����
						if (!pid.get(j).equals(mPid.get(n))) {
							++o;
							System.out.println("oֵ" + o + "  " + mPid.size());
						} // Ѱ�ҵ�ǰ�û�û�е�ͼƬ
					}

					if (o == mPid.size()) { // ��ʾ������mPid����ͬ����������Ҫ���͵Ĳ���
						String hql1 = "select FinalMarkName from Picture where PID='" + pid.get(j) + "'";
						List<String> finalMarkName = session.createQuery(hql1).list();
						System.out.println(finalMarkName.get(0) + "   sds");
						if (finalMarkName.get(0) == null || finalMarkName.get(0).equals("")) {// ֻ�����ձ�ǩû�еĲŻᱻ����
							System.out.println("simp.ADD");
							simP.add(pid.get(j));
						}
					}
					if (simP.size() == 12)
						break;
				}
			}

			if (simP.size() < 12) {
				// �����������̨�����ȡ����
				int n = 12 - simP.size();
				List<String> pis = getrandP(session, UID, n); // ��Ӳ�����
				for (int j = 0; j < pis.size(); j++) {
					simP.add(pis.get(j));
				}
			}
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return simP;
	}

	public static List<String> getrandP(Session sessions, String UserId, int num) {// ����ͼƬ�����Ľ��в���
		// Session session = SessionAnnotation.getSession();
		// session.beginTransaction();
		Session session = sessions;
		List<String> pis = new ArrayList<String>();
		String hql2 = "from Picture where FinalMarkName is null order by rand()";// ֻ��û��ȷ�����ձ�ǩ��ͼƬ��Ҫ����
		Query q = session.createQuery(hql2);
		// q.setFirstResult(0); // �ӵ�0����¼��ʼȡ
		// q.setMaxResults(num*2); // ȡnum*2����¼
		List<Picture> pictures = q.list();
		String hql3 = "from Mark where UserID=?";
		Query q2 = session.createQuery(hql3);
		q2.setParameter(0, UserId);
		List<Mark> marks = q2.list();
		int numb = 0;
		for (int i = 0; i < pictures.size(); i++) {
			int n = 0;
			for (int j = 0; j < marks.size(); j++) {// ����־Ը����ʷ��¼
				if (!pictures.get(i).getPID().equals(marks.get(j).getPicture().getPID())) {
					++n;
				} else {
					break;
				}
			}
			if (n == marks.size()) { // ֻ����ʷ��¼��û�еĲ�����
				pis.add(pictures.get(i).getPID());
				++numb;
			}
			if (numb == num)
				break;
		}
		// session.getTransaction().commit();
		// SessionAnnotation.closeSession();
		return pis;
	}

}
