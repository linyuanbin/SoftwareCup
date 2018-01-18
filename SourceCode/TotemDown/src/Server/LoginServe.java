package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import PictureAction.PictureUtil;
import action.PushPicture;
import action.SimUserPicture;
import dao.MarkDao;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.MarkImplement;
import daoimplement.PictureImplement;
import daoimplement.QualifyImplement;
import daoimplement.UserImplement;
import jsonUtil.CreateJson;
import model.Feedback;
import model.Image;
import model.Mark;
import model.Picture;
import model.Qualify;
import model.SearchP;
import model.User;
import translateUtil.Translate;
import util.HanlpUtil;
import util.RandomString;

public class LoginServe extends HttpServlet {

	private UserDao d = new UserImplement();
	private MarkDao md = new MarkImplement();
	private PictureDao pd = new PictureImplement();
	private QualifyImplement qd = new QualifyImplement();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��ȡ��http�� //��ȡ������ //��ȡ�˿ں�
		String str = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "";
		System.out.println(str);

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		// request.setCharacterEncoding("utf-8");
		// response.setContentType("text/html");
		// response.setContentType("text/html;charset=utf8"); //�����������
		// ����д�ڵõ���֮ǰgbk
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
		// String username = request.getParameter("username");
		// username = new String(username.getBytes("iso-8859-1"),"utf-8��);
		String password = request.getParameter("password");
		System.out.println(username + ":" + password);

		PrintWriter out = response.getWriter(); // ͨ��response�õ��ֽ������
		String msg = null;
		if (username != null && username.equals("linyuanbin") && password != null && password.equals("123456")) {

			System.out.print("��¼�������ɹ�l ��");
			BufferedReader br = request.getReader();

			String str1 = null;
			StringBuilder resource = new StringBuilder();
			while ((str1 = br.readLine()) != null) // �ж����һ�в����ڣ�Ϊ�ս���ѭ��
			{
				resource.append(str1);
				System.out.println(str1);// ԭ���������������
			}
			User u = CreateJson.getUser(resource.toString());

			if ((u.getState().trim()).equals("login")) { // ��¼ 1
				String UserId = d.login(u.getUserName(), u.getUserPassword());
				String jsonUser = "";
				if (UserId.equals("")) {
					System.out.println("�������");
					msg = UserId;// ��ֵ
					User u2 = new User();// ����һ���յ�User������ͻ���
					u2.setState("false");
					u2.setUserID(" ");
					String userfile = CreateJson.getUserJson(u2);
					System.out.println("�e�`�Ñ���Ϣ��" + userfile);
					out.write(userfile);
				} else {
					System.out.println("������ȷ �û�ID" + UserId);
					User u6 = d.showUser(UserId.trim()); // ��ȡ��ӦID��User
					u6.setState("true");
					System.out.println("name" + u6.getUserName());
					System.out.println("name" + u6.toString());
					jsonUser = CreateJson.getUserJson(u6);
					msg = u6.toString();
					// out.write(jsonUser); //��¼�����û�����User������Ϣ
					out.write("{\"userData\":" + jsonUser + "}");
					// out.println(msg);
				}
			} else if ((u.getState().trim()).equals("register")) { // ע�� 2
				String userID = d.register(u);
				System.out.println("ע�������" + userID);
				if ((!userID.equals(""))) {// ���ע��ɹ�
					// msg = u.getUserID(); //ע��ɹ�����UserId
					u.setState("true");
					String userfile = CreateJson.getUserJson(u);
					System.out.println("ע��ɹ��û���Ϣ��" + userfile);
					out.write("{\"userData\":" + userfile + "}");
				} else {
					u.setState("false");
					String s = CreateJson.getUserJson(u);
					System.out.println("ע��ʧ���û���Ϣ��" + s);
					out.write("{\"userData\":" + s + "}");
				}
			} else if ((u.getState().trim()).equals("update")) { // �����û� 3
				d.updateUser(u);
				// System.out.println("�û����գ�" +
				// u.getUserBirthday().toLocaleString());
			} else if ((u.getState().trim()).equals("request".trim())) { // ��������12��
																			// 4
				// try{
				if (u.getUserID().equals("")) {
					System.out.println("����ʧ��,�û�δ��¼");
					out.write("");
				} else {
					String pushPictureJson = PushPicture.getPush(u.getUserID());
					if (pushPictureJson.equals("")) {
						pushPictureJson = SimUserPicture.getSimU(u.getUserID()); // �������û�����
					}
					pushPictureJson = "[" + pushPictureJson + "]";
					System.out.println("���ͣ�" + pushPictureJson);
					out.write(pushPictureJson);
					Qualify qualify = qd.showQualify();
					User user1 = d.showUser(u.getUserID().trim());
					if (user1.getAccomplish() < qualify.getHisNum()) { // ֻ��ϵͳ�������ʱÿ��ʮ����
						System.out.println("��������12" + u.getUserID() + user1.getUserName());
						user1.setTotal(user1.getTotal() + 12); // ��������12
						d.updateUser(user1);
					}
				}
				// }catch(Exception e){
				// System.out.println(e);
				// System.out.println("pushpicture�쳣��");
				// out.write("");
				// }
			} else if ((u.getState().trim()).equals("requestRand".trim())) { // �����������12��  5
		
				if (u.getUserID().equals("")) {
					System.out.println("����ʧ��,�û�δ��¼");
					out.write("");
				} else {
					String pushPictureJson = PushPicture.getRandPush(u.getUserID());
					pushPictureJson = "[" + pushPictureJson + "]";
					System.out.println("������ͣ�" + pushPictureJson);
					out.write(pushPictureJson);
				}
			} else if (u.getState().trim().equals("mark".trim())) { // ���ǩ 6
				System.out.println("���ǩģ��ִ��");
				User user = d.showUser(u.getUserID());
				System.out.println("���ǩ���Ñ�" + user.getUserName());
				System.out.println("PID:" + u.getPID());
				Picture p = pd.selectSinglePictureFID(u.getPID().trim());
				System.out.println("����ǩ��ͼƬ" + p.getPName());
				Mark m = new Mark();
				String theMarkName = Translate.getTranslate(u.getMarkName().replaceAll(" ", ""));// ɾ�����пո�
				System.out.println("����" + theMarkName);
				// theMarkName = HanlpUtil.get(theMarkName);
				// System.out.println("�ֽ⣺" + theMarkName);
				m.setMarkName(theMarkName.trim());// ���������Ա�ǩ��ת��������
				m.setUser(user);
				m.setMarkDate(new Date());
				m.setPicture(p);
				m.setTabId(user.getUserID().trim() + p.getPID().trim());
				boolean b = md.insertMark(m);
				// boolean
				// b=md.insertIntoMark(u.getUserID().trim(),u.getPID().trim(),u.getMarkName().trim());
				// //�����ǩ
				if (b) {// ��ǩ�ɹ�
					try {
						user.setUserIntegral(user.getUserIntegral() + 1);// ���ּ�һ
						user.setAccomplish(user.getAccomplish() + 1); // �������һ
						Qualify qualify = qd.showQualify();
						if (user.getAccomplish() > qualify.getHisNum()) {
							user.setTotal(user.getTotal() + 1);
						}
						d.updateUser(user);
						user.setState("true");
						System.out.println("��ǩ�ɹ�");
						String s = CreateJson.getUserJson(user);
						out.write(s);
						// user.setAccomplish(user.getAccomplish()+1); //�������һ
						// d.updateUser(user);
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("���ǩ�쳣");
						// out.write("");
					}
				} else { // ��ǩʧ��
					System.out.println("��ǩʧ��");
					User user2 = new User();
					user2.setState("false");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} // ��ǩʧ��

			} else if (u.getState().trim().equals("updatemark".trim())) { // ���±�ǩ
																			// 7
				System.out.println("���±�ǩģ��ִ��");
				User user = d.showUser(u.getUserID());
				System.out.println("���±�ǩ���Ñ�" + user.getUserName());
				System.out.println("PID:" + u.getPID());
				Picture p = pd.selectSinglePictureFID(u.getPID().trim());
				System.out.println("�����±�ǩ��ͼƬ" + p.getPName());
				Mark m = new Mark();
				String theMarkName = Translate.getTranslate(u.getMarkName().trim());
				System.out.println("����" + theMarkName);
				m.setMarkName(theMarkName.trim());// ���������Ա�ǩ��ת��������
				m.setUser(user);
				m.setMarkDate(new Date());
				m.setPicture(p);
				m.setTabId(user.getUserID().trim() + p.getPID().trim());
				boolean b = md.insertMark(m);
				// boolean
				// b=md.insertIntoMark(u.getUserID().trim(),u.getPID().trim(),u.getMarkName().trim());
				// //�����ǩ
				if (b) {// ���±�ǩ�ɹ�
					User user2 = new User();
					user2.setState("true");
					System.out.println("���±�ǩ�ɹ�");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} else { // ��ǩʧ��
					System.out.println("���±�ǩʧ��");
					User user2 = new User();
					user2.setState("false");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} // ��ǩʧ��

			} else if (u.getState().trim().equals("history".trim())) {// ��ѯ��ʷ��ǩ��¼
																		// 8
				if (!u.getUserID().equals("")) {
					// �����û���ʷ��ǩ��¼
					String historyJson = "";// d.getHistory(u.getUserID());
					try {
						historyJson = d.getHistory(u.getUserID());
					} catch (Exception e) {
						System.out.println("history�쳣a�� " + e);
						historyJson = "";
					}
					if (!historyJson.equals("")) {
						out.write("[" + historyJson + "]");
					} else {
						// User user=new User();
						// user.setState("false");
						// String userJosn=CreateJson.getUserJson(user);
						out.write("");
					}
				} else {
					out.write("");
				}
			} else if (u.getState().trim().equals("search".trim())) { // 9 ����ͼƬ
				SearchP s = new SearchP();
				s = CreateJson.getSearchP(resource.toString());
				System.out.println("�������ݣ� " + s.getContent());
				String picturejson = "";
				if (!s.getContent().trim().equals("null")) {
					picturejson = pd.search(s.getContent().trim());
					if (!picturejson.equals("")) {
						System.out.println("[" + picturejson + "]");
						out.write("[" + picturejson + "]");
					} else {
						System.out.println("δ�ҵ���Ҫ��ͼƬ");
						out.write("");
					}
				} else {
					System.out.println("�������ݿ�");
					out.write("");
				}
			} else if (u.getState().trim().equals("feedback".trim())) { // 10 ����
				boolean b = d.setFeedback(resource.toString());
				if (b) {
					User user = new User();
					user.setState("true");
					String userjson = CreateJson.getUserJson(user);
					out.write(userjson);
				} else {
					User user = new User();
					user.setState("false");
					String userjson = CreateJson.getUserJson(user);
					out.write(userjson);
				}
			} else if (u.getState().trim().equals("changePassword".trim())) { // 11�޸�����
				boolean b = d.ChangePassword(u);
				if (b) {
					User user = new User();
					user.setState("true");
					String userjson = CreateJson.getUserJson(user);
					out.write(userjson);
				} else {
					User user = new User();
					user.setState("false");
					String userjson = CreateJson.getUserJson(user);
					out.write(userjson);
				}
			} else if (u.getState().trim().equals("getHead".trim())) { // 12 ����
				if (!u.getUserTel().trim().equals("") && (u.getUserTel().length() == 11)) {
					String uid = d.getUserID(u.getUserTel().trim());
					if (uid.trim().equals("")) { // �û�������
						System.out.println("�û������ڣ���ȡͷ��ʧ��");
						User user2 = new User();
						user2.setState("false");
						String userJosn = CreateJson.getUserJson(user2);
						out.write(userJosn);
					} else { // �û�����
						User user = d.showUser(uid);
						if (!user.getLocalAddress().equals("")) { // ����б���ͷ���ַ
							User user2 = new User();
							user2.setState("true");
							user2.setLocalAddress(user.getLocalAddress());
							String userJosn = CreateJson.getUserJson(user2);
							System.out.println("��ȡ����ͷ��" + userJosn);
							out.write(userJosn);
						} else {
							System.out.println("��ȡ����ͷ��Ϊ��");
							User user2 = new User();
							user2.setState("false");
							String userJosn = CreateJson.getUserJson(user2);
							out.write(userJosn);
						}
					}
				} else {
					System.out.println("��ȡ����ͷ��ʧ�ܣ��绰����Ϊ�գ�");
					User user2 = new User();
					user2.setState("false");
					String userJosn = CreateJson.getUserJson(user2);
					out.write(userJosn);
				}
				
			} else if (u.getState().trim().equals("getSimplePicture".trim())) { // 13 ��ͼ��ͼ
				Picture picture=CreateJson.getPicture(resource.toString());
				String url=picture.getPAddress();
				String s=pd.getSimplepicture2(url);
				if(s.equals("")){
					System.out.println("û���ҵ�����ͼƬ");
					out.write("");
				}else{
					System.out.println("��������� "+"["+s+"]");
					out.write("["+s+"]");
				}
			}else {
				System.out.println("û�ҵ�state"); // 13  
				User user = new User();
				user.setState("false");
				String userjson = CreateJson.getUserJson(user);
				out.write(userjson);
				// String str2="500 NotFound the state";
				// out.write(str2);
			}
		} else {
			System.out.println("���������ʧ��");
			User u = new User();
			u.setState("false");
			String userjson = CreateJson.getUserJson(u);
			out.write(userjson);
			// msg = "404 NotFound connect username or password error"; //ע��ʧ�ܻ���
			// out.write(msg);
		}
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
