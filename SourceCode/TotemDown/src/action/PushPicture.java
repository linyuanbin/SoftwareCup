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

public class PushPicture {// 推送图片
	public static UserDao ud = new UserImplement();
	public static PictureDao pd = new PictureImplement();
	public static QualifyImplement qd = new QualifyImplement();

	public static String getPush(String UserId) { // 登录一次推送12张
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
		System.out.println("历史记录数目：" + list.size() + "  " + list.toString());
		if (list.size() == 0 || list.size() <= qualify.getHisNum()) { // 新用戶，不符合针对性推送，随机推送

			List<String> mPid = new ArrayList<String>(); // 得到当前用户所有历史记录进行推送排除
			for (int i = 0; i < list.size(); i++) {
				mPid.add(list.get(i).getPicture().getPID());
			}

			// String sql2="select PID,PAddress from Picture ORDER BY RAND()";
			// //从数据库随机获取若干条图片信息
			String hql2 = "from Picture where FinalMarkName is null order by rand()";// 只有没有确定最终标签的图片需要推送
			Query q = session.createQuery(hql2);
			// q.setFirstResult(0); // 从第0条记录开始取
			// q.setMaxResults(35); // 取32条记录 去除
			List<Picture> pictures = q.list();
			int num = 0;
			for (int i = 0; i < pictures.size(); i++) {
				int n = 0;
				for (int j = 0; j < mPid.size(); j++) {
					if (!mPid.get(j).equals(pictures.get(i))) {// 去除用户历史记录中已有的
						n++;
					} else {
						break;
					}
				}
				if (n == mPid.size()) { // 只有用户没有打过的才推送
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

		} else { // 针对用户情况推送
			// 基于user的推荐服务
			System.out.println("满足条件");
			session.getTransaction().commit();
			SessionAnnotation.closeSession();
			System.out.println("jsonPicture  " + jsonPicture.toString());
			return "";
		}
	}

	public static String getRandPush(String UserId) { // 获取随机推送
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		StringBuilder jsonPicture = new StringBuilder();
		String hql = "from Mark where UserId=?";// +UserId+"'";
		Query query = session.createQuery(hql);
		query.setParameter(0, UserId.trim());
		List<Mark> list = query.list();
		List<String> mPid = new ArrayList<String>(); // 得到当前用户所有历史记录进行推送排除
		for (int i = 0; i < list.size(); i++) {
			mPid.add(list.get(i).getPicture().getPID());
		}
		// //从数据库随机获取若干条图片信息
		String hql2 = "from Picture where FinalMarkName is null order by rand()";// 只有没有确定最终标签的图片需要推送
		Query q = session.createQuery(hql2);
		// q.setFirstResult(0); // 从第0条记录开始取
		// q.setMaxResults(35); // 取32条记录 去除
		List<Picture> pictures = q.list();
		int num = 0;
		for (int i = 0; i < pictures.size(); i++) {
			int n = 0;
			for (int j = 0; j < mPid.size(); j++) {
				if (!mPid.get(j).equals(pictures.get(i))) {// 去除用户历史记录中已有的
					n++;
				} else {
					break;
				}
			}
			if (n == mPid.size()) { // 只有用户没有打过的才推送
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

	public static List<String> getSimP(String UID) { // 得到相似用户提供的图片资源
		Qualify qualify = qd.showQualify();
		float similarity = (float) qualify.getNum() / 100; // 计算相似度
		if (similarity <= 0 || similarity >= 1) { // 如果计算得到的相似度不合法，给定定值
			similarity = 0.5f;
		}
		List<String> simP = new ArrayList<>(); // 用于存储从相似用户那里得到的推送图片id
		Session session = SessionAnnotation.getSession();
		session.beginTransaction();
		String hql = "from Mark where UserID=?";
		Query q = session.createQuery(hql);
		q.setString(0, UID);
		List<Mark> mMarks = q.list();// 得到该用户的所有历史记录
		List<String> mPid = new ArrayList<>(); // 用于保存当前用户历史图片ip
		for (int i = 0; i < mMarks.size(); i++) {
			mPid.add(mMarks.get(i).getPicture().getPID());
		}

		String sql = "select UserID from User where UserID <>'" + UID + "'";
		List<String> list2 = session.createQuery(sql).list(); // 得到其余所有用户id
		Map<String, Integer> similar = new HashMap<>(); // 用于存储 <uid，相同图片数量>
		for (int i = 0; i < list2.size(); i++) {// 遍历每个用户
			// System.out.println(list2.get(i));
			String h = "from Mark where UserID=?";
			Query qu = session.createQuery(h);
			qu.setString(0, list2.get(i));
			List<Mark> uMark = qu.list();// 得到list中对应id用户的所有历史记录
			List<String> uPid = new ArrayList<>(); // 用于保存id对应的用户历史图片ip
			for (int j = 0; j < uMark.size(); j++) {
				uPid.add(uMark.get(j).getPicture().getPID());
				// System.out.println("用户" +i + "图片id: " +
				// uMark.get(j).getPicture().getPID());
			}
			// 通过比较mPid(当前用户历史图片id)和uPidI(用户id对应用户历史图片id) 找到相似用户
			int sameNum = 0;
			for (int u = 0; u < mPid.size(); u++) { // 遍历当前用户所有Pid历史记录
				int m = 0;
				for (int j = 0; j < uPid.size(); j++) {
					if (mPid.get(u).equals(uPid.get(j))) {
						m++;
					}
				}
				if (m != 0) {
					// similar.put(list2.get(i), m); // <用户id,相同图片数量>
					sameNum++;
				}
			}
			similar.put(list2.get(i), sameNum); // <用户id,相同图片数量>
		}

		for (Entry<String, Integer> entry : similar.entrySet()) {
			System.out.println("遍历");
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}

		for (Entry<String, Integer> entry : similar.entrySet()) { // 遍历所有用户
			System.out.println("和用户:" + entry.getKey() + "  有  " + entry.getValue() + " 张图片相同");
			float si = (float) entry.getValue() / mPid.size();
			System.out.println("用户:" + entry.getKey() + "  和该用户相似度   =" + si);
			if (si >= similarity) { // 显示度大于0.5的定义为相似用户
				System.out.println("相似用户id:" + entry.getKey());
				String h = "from Mark where UserID=?";
				Query q1 = session.createQuery(h);
				// q1.setString(0, entry.getKey());
				q1.setParameter(0, entry.getKey());
				List<Mark> uMark = q1.list();// 得到对id用户的所有历史记录
				List<String> uPid = new ArrayList<>(); // 用于保存id对应的用户历史图片ip
				for (int j = 0; j < uMark.size(); j++) {
					uPid.add(uMark.get(j).getPicture().getPID());
				}

				// List<String> c=uPid.Except(mPid).Tolist<String>();
				// 得到uPid区别于mPid的部分
				for (int j = 0; j < uPid.size(); j++) {
					int o = 0;
					System.out.println(uPid.get(j));
					for (int n = 0; n < mPid.size(); n++) {// 当uPid当前的图片id和所有的当前用户的都不一样这添加到里面
						if (!uPid.get(j).equals(mPid.get(n))) {
							++o;
						} // 寻找当前用户没有的图片
					}
					if (o == mPid.size()) { // 表示和所有mPid都不同，则是我们要推送的部分
						String hql1 = "from Picture where PID=?";
						Query que = session.createQuery(hql1);
						que.setParameter(0, uPid.get(j));
						List<Picture> picture = que.list();
						if (picture.get(0).getFinalMarkName() == null
								|| picture.get(0).getFinalMarkName().trim().equals("")) { // 修改||||||||
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

			} // 如果相似度大于 Qualify.Num/100
		} // 遍历所有用户
			// System.out.println("simp.size:" + simP.size());
			// for (int i = 0; i < simP.size(); i++) {
			// System.out.println("simp-: " + simP.get(i));
			// }

		if (simP.size() < 12) {// 如果所有相似用户数据得到了还少于12这需要另外添加
			System.out.println("不够" + simP.size());
			// 先通过用户的职业补充推送
			String hql3 = "from User where UserID=?";
			Query query = session.createQuery(hql3);
			query.setParameter(0, UID);
			List<User> users = query.list();
			User u = users.get(0);// 得到当前用户
			if (!u.getUserMajor().equals("")) { // 添加
				session.getTransaction().commit(); // |||||||||||||||||||||
				SessionAnnotation.closeSession();
				// SuggesterUtil SugUtil=new SuggesterUtil();
				SuggesterUtil.setSuggester();
				Suggester suggester = SuggesterUtil.getSuggester();
				System.out.println("session再次开启");
				System.out.println(u.getUserMajor());
				String con = "";
				if (suggester != null) {
					con = suggester.suggest(u.getUserMajor(), 1).get(0); // 通过用户职业搜索推荐
				} else {
					con = u.getUserMajor().replace(" ", "");
				}
				System.out.println("con " + con);
				session = SessionAnnotation.getSession();// ||||||||||||||||||||
				session.beginTransaction();
				System.out.println("session再次开启显示用户数据不够");
				String hql2 = "select PID from Picture where FIRSTNAME like '%" + con + "%'";
				List<String> pid = session.createQuery(hql2).list(); // 按照职业得到的图片
				for (int j = 0; j < pid.size(); j++) {
					int o = 0;
					System.out.println(pid.get(j));
					for (int n = 0; n < mPid.size(); n++) { // 当uPid当前的图片id和所有的当前用户的都不一样这添加到里面
						if (!pid.get(j).equals(mPid.get(n))) {
							++o;
							System.out.println("o值" + o + "  " + mPid.size());
						} // 寻找当前用户没有的图片
					}

					if (o == mPid.size()) { // 表示和所有mPid都不同，则是我们要推送的部分
						String hql1 = "select FinalMarkName from Picture where PID='" + pid.get(j) + "'";
						List<String> finalMarkName = session.createQuery(hql1).list();
						System.out.println(finalMarkName.get(0) + "   sds");
						if (finalMarkName.get(0) == null || finalMarkName.get(0).equals("")) {// 只有最终标签没有的才会被推送
							System.out.println("simp.ADD");
							simP.add(pid.get(j));
						}
					}
					if (simP.size() == 12)
						break;
				}
			}

			if (simP.size() < 12) {
				// 若还不够则后台随机抽取补充
				int n = 12 - simP.size();
				List<String> pis = getrandP(session, UID, n); // 添加不够的
				for (int j = 0; j < pis.size(); j++) {
					simP.add(pis.get(j));
				}
			}
		}
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
		return simP;
	}

	public static List<String> getrandP(Session sessions, String UserId, int num) {// 对于图片不够的进行补充
		// Session session = SessionAnnotation.getSession();
		// session.beginTransaction();
		Session session = sessions;
		List<String> pis = new ArrayList<String>();
		String hql2 = "from Picture where FinalMarkName is null order by rand()";// 只有没有确定最终标签的图片需要推送
		Query q = session.createQuery(hql2);
		// q.setFirstResult(0); // 从第0条记录开始取
		// q.setMaxResults(num*2); // 取num*2条记录
		List<Picture> pictures = q.list();
		String hql3 = "from Mark where UserID=?";
		Query q2 = session.createQuery(hql3);
		q2.setParameter(0, UserId);
		List<Mark> marks = q2.list();
		int numb = 0;
		for (int i = 0; i < pictures.size(); i++) {
			int n = 0;
			for (int j = 0; j < marks.size(); j++) {// 遍历志愿者历史记录
				if (!pictures.get(i).getPID().equals(marks.get(j).getPicture().getPID())) {
					++n;
				} else {
					break;
				}
			}
			if (n == marks.size()) { // 只有历史记录中没有的才推送
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
