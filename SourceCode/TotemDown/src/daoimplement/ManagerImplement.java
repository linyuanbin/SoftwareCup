package daoimplement;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.ManagerDao;
import hibernateutil.SessionAnnotation;
import hibernateutil.ValidatorUserNameUtil;
import jsonUtil.CreateJson;
import model.Feedback;
import model.Manager;
import model.Picture;
import model.User;
import util.RandomString;

public class ManagerImplement implements ManagerDao {

	@Override
	public String register(Manager m) {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String mIdCard = "";
		try {

			if (m.getmIdCard() != null) { // ��֤���֤�Ƿ����
				String sql = "select mIdCard from Manager where mIdCard='" + m.getmIdCard() + "'";
				String sql2 = "select mIdCard from Manager where mTel='" + m.getmTel() + "'";
				List list = session.createQuery(sql).list();
				List list2 = session.createQuery(sql2).list();
//				System.out.println("�绰" + list2.get(0) + list2.isEmpty());
				if (!list.isEmpty()) {
					System.out.println("�û����֤�� ע��");
					return "";
				} else {
					if (list2.isEmpty()) {
						session.save(m);
						return m.getCheckId();
					} else {
						System.out.println("�õ绰�����Ѿ�ע��");
						return "";
					}
				} // else
			} else { // if
				return "";
			}

		} catch (Exception e) {
			System.out.println("����Tע���쳣��" + e);
			mIdCard = "";
			return mIdCard;
		} finally {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
		}
	}

	@Override
	public String login(String mName, String mPassword) {

		Session session = SessionAnnotation.getSession();
		try {
			session.beginTransaction();
			String sql;
			String mIdCard = "";
			if (ValidatorUserNameUtil.isEmail(mName)) {
				sql = "select mIdCard from Manager where mEmail='" + mName + "'and mPassword='" + mPassword + "'";
			} else if (ValidatorUserNameUtil.isMobile(mName)) {
				sql = "select mIdCard from Manager where mTel='" + mName + "'and mPassword='" + mPassword + "'";
			} else {
				//sql = "select mIdCard from Manager where mName='" + mName + "'and mPassword='" + mPassword + "'";
			return "";
			}

			List list = session.createQuery(sql).list();
			if (list.isEmpty()) {
				// session.getTransaction().commit();
				// SessionAnnotation.closeSession();
				mIdCard = "";
				System.out.println("��¼ʧ�ܣ�");
				return mIdCard;
			}
			mIdCard = (String) list.iterator().next();
			// session.getTransaction().commit();
			// SessionAnnotation.closeSession();
			System.out.println("��¼�ɹ���");
			return mIdCard;
		} catch (Exception e) {
			System.out.println("�û���¼�쳣" + e);
			return "";
		} finally {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
		}

	}

	@Override
	public boolean deleteManager(String mIdCard) {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String sql = "select mIdCard from Manager where mIdCard='" + mIdCard + "'"; // �����Ƿ�����û�
		List list = session.createQuery(sql).list();

		if (list.isEmpty()) { // �������˵��������
			SessionAnnotation.closeSession();
			return false;
		}
		// "delete from User where User_ID='"+User_ID+"'";
		sql = "delete from Manager where mIdCard='" + mIdCard + "'";
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return true;
	}

	@Override
	public boolean updateManager(Manager m) {
		if (senseManager(m.getmIdCard())) { // ����û����������
			Session session = SessionAnnotation.getSession(); // �����
			try {

				session.beginTransaction();
				session.update(m);
				// session.getTransaction().commit();
				// SessionAnnotation.closeSession();
				return true;
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("updateManagerʧ��");
				// SessionAnnotation.closeSession();
				return false;
			} finally {
				session.getTransaction().commit();
				SessionAnnotation.closeSession();
			}
		} else {
			System.out.println("�û����ڣ�");
			return false;
		}
	}

	@Override
	public Manager selectManager(String mName) {
		Session session = SessionAnnotation.getSession();
		String sql;
		if (ValidatorUserNameUtil.isEmail(mName)) {
			sql = "select mIdCard from Manager where mEmail='" + mName + "'";
		} else if (ValidatorUserNameUtil.isMobile(mName)) {
			sql = "select mIdCard from Manager where mTel='" + mName + "'";

		} else {
			sql = "select mIdCard from Manager where mName='" + mName + "'";
		}
		session.beginTransaction();
		List list = session.createQuery(sql).list();
		System.out.println(list);
		if (!list.isEmpty()) { // �������
			// User user=(User)list.iterator().next();
			String uid = list.toString();
			Manager m = (Manager) session.get(Manager.class, uid.trim());
			System.out.println("selectManager" + m.getmName());
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return m;
		}
		// session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return null;
	}

	@Override
	public boolean senseManager(String mID) {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String sql;
		sql = "select mIdCard from Manager where mIdCard='" + mID + "'";
		List list = session.createQuery(sql).list();
		if (!list.isEmpty()) {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return true;
		}
		SessionAnnotation.closeSession();
		return false;
	}

	@Override
	public Manager showManager(String mID) {
		try {
			Session session = SessionAnnotation.getSession();
			session.beginTransaction();
			Manager manager = (Manager) session.get(Manager.class, mID);
			session.getTransaction().commit();
			// session.flush();
			SessionAnnotation.closeSession();
			return manager;
		} catch (Exception e) {
			System.out.println("showManager�쳣" + e);
			Manager ma = new Manager();
			ma.setState("false");
			return ma;
		}
	}

	@Override
	public String getManagerID(String mName) { // 2017.6.19
		Session session = SessionAnnotation.getSession();
		// session.beginTransaction(); //2
		String sql = "";// "from Manager where mTel=?";
		try {
			session.beginTransaction();
			if (ValidatorUserNameUtil.isEmail(mName)) {
				// sql = "select mIdCard from Manager where mEmail='" + mName +
				// "'";
				System.out.println("�ǹ���mEmail");
				sql = "from Manager where mEmail=?";
			} else if (ValidatorUserNameUtil.isMobile(mName)) {
				// sql = "select mIdCard from Manager where mTel='" + mName +
				// "'";
				sql = "from Manager where mTel=?";
				System.out.println("�ǹ���Ա�绰��");
			} else {
				// sql = "select mIdCard from Manager where mName='" + mName +
				// "'";
				System.out.println("�ǹ���mName");
				sql = "from Manager where mName=?";
			}

			// List list = session.createQuery(sql).list();
			// String mID = (String) list.iterator().next();
			// String userid=session.createQuery(sql).toString();
			Query query = session.createQuery(sql);
			query.setParameter(0, mName);
			List<Manager> list = query.list();
			String mID = list.get(0).getmIdCard();
			// session.getTransaction().commit();
			if (mID.isEmpty()) {
				// SessionAnnotation.closeSession();
				return "";
			} else {
				// SessionAnnotation.closeSession();
				return mID;
			}
		} catch (Exception e) {
			// SessionAnnotation.closeSession();
			System.out.println("getManagerID�쳣" + e);
			return "";
		} finally {
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
		}

	}

	public String selectNewFeedback() {
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		StringBuilder sb = new StringBuilder();
		try {
			String hql = "from Feedback where states=0";
			List<Feedback> list = session.createQuery(hql).list();
			// String str="";
			for (int i = 0; i < list.size(); i++) {
				if (i < (list.size() - 1)) {
					String s = CreateJson.getFeedbackJson(list.get(i));
					if (!s.equals("")) {
						sb.append(s + ",");
					}
				} else {
					String s = CreateJson.getFeedbackJson(list.get(i));
					if (!s.equals("")) {
						sb.append(s);
					}
				}
			}
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			return sb.toString();
		} catch (Exception e) {
			SessionAnnotation.closeSession();
			System.out.println(e);
			System.out.println("selectFeedback�쳣");
			return "";
		}
	}

	public String selectSolvedFeedback() {
		Session session = SessionAnnotation.getSession();

		StringBuilder sb = new StringBuilder();
		try {
			session.beginTransaction();
			String hql = "from Feedback where states=1";
			List<Feedback> list = session.createQuery(hql).list();
			// String str="";
			for (int i = 0; i < list.size(); i++) {
				if (i < (list.size() - 1)) {
					String s = CreateJson.getFeedbackJson(list.get(i));
					if (!s.equals("")) {
						sb.append(s + ",");
					}
				} else {
					String s = CreateJson.getFeedbackJson(list.get(i));
					if (!s.equals("")) {
						sb.append(s);
					}
				}
			}
			session.getTransaction().commit();
			// SessionAnnotation.closeSession();
			return sb.toString();
		} catch (Exception e) {
			// SessionAnnotation.closeSession();
			System.out.println(e);
			System.out.println("selectAllFeedback�쳣");
			return "";
		} finally {
			// session.getTransaction().commit();
			SessionAnnotation.closeSession();
		}
	}

	public boolean solveFeedback(String resources) { // �쳣�������states��Ϊ1
		Feedback fb = CreateJson.getFeedback(resources);
		Session session = SessionAnnotation.getSession();

		try {
			session.beginTransaction();
			Feedback fb2 = (Feedback) session.get(Feedback.class, fb.getFbId().trim());
			fb2.setStates(1);
			session.save(fb2);
			session.getTransaction().commit();
			// SessionAnnotation.closeSession();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("solveFeedback�쳣");
			// SessionAnnotation.closeSession();
			return false;
		} finally {
			SessionAnnotation.closeSession();
		}
	}

	// �޸�����
	public boolean ChangePassword(Manager m) {
		try {
			String mId = getManagerID(m.getmTel());
			Manager manager = showManager(mId);
			manager.setmPassword(m.getmPassword());
			boolean b = updateManager(manager);
			if (b) {
				System.out.println("����Ա�޸�����ɹ�");
				return true;
			} else {
				System.out.println("����Ա�޸�����ʧ��");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Manager ChangePassword�쳣");
			return false;
		}
	}

}
