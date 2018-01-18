package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import PictureAction.PictureUtil;
import dao.ManagerDao;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.ManagerImplement;
import daoimplement.PictureImplement;
import daoimplement.QualifyImplement;
import daoimplement.UserImplement;
import jsonUtil.CreateJson;
import model.Image;
import model.ImageList;
import model.Manager;
import model.Picture;
import model.Qualify;
import model.User;
import util.RandomString;

public class ManagerServer extends HttpServlet {

	private ManagerDao md = new ManagerImplement();
	private PictureDao pd = new PictureImplement();
	private UserDao ud = new UserImplement();
	private QualifyImplement qd=new QualifyImplement();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	
		String str = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "";
		System.out.println(str);

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(userName + ":" + password);
		PrintWriter out = resp.getWriter();
		String msg = "";
		System.out.println("����Ա��¼");
		if (userName != null && userName.equals("linyuanbin") && password != null && password.equals("123456")) {
			System.out.println("����Ա�ɹ����ӷ�����");
			BufferedReader br = request.getReader();
			String str1 = "";
			StringBuilder sb = new StringBuilder();
			while ((str1 = br.readLine()) != null) {
				sb.append(str1);
				System.out.println("���գ�" + str1);
			}

			Manager m = CreateJson.getManager(sb.toString().trim());
			System.out.println("״ֵ̬��" + m.getState());

			if (m.getState().equals("register")) { // ע��  1
													// ����Աע��ֻ�����ݿ��������Ӧ���֤�ŵĲ�����ע��
				Qualify qualify=qd.showQualify();
				if (m.getCheckId().equals(qualify.getStates().trim())) { // ע��У������ȷ "gb10086"
					String mIdCard = md.register(m);
					if (mIdCard.equals("")) { // �û�id������
						m.setState("false");
						String managerJson = CreateJson.getManagerJson(m);
						System.out.println("ע�����ʧ���û���Ϣ��" + managerJson);
						out.write(managerJson);
					} else {// ע��ɹ�
						m.setState("true");
						String managerJson = CreateJson.getManagerJson(m);
						System.out.println("ע��ɹ��û���Ϣ" + managerJson);
						out.write("{\"mangerData\":" + managerJson + "}");
					}
				} else { // ע��У�������  
					m.setState("false");
					String managerJson = CreateJson.getManagerJson(m);
					System.out.println("У�������ע��ʧ���û���Ϣ��" + managerJson);
					out.write(managerJson);
				}

			} else if (m.getState().trim().equals("login".trim())) {// ��¼ 2
				// ��¼
				String mIdCard = md.login(m.getmName().trim(), m.getmPassword().trim());
				String jsonManager = "";
				if (mIdCard.equals("")) {
					System.out.println("�������");
					msg = mIdCard;// ��ֵ
					Manager m2 = new Manager();// ����һ���յ�User������ͻ���
					m2.setState("false");
					m2.setmIdCard(" ");
					String Managerfile = CreateJson.getManagerJson(m2);
					System.out.println("�e�`�Ñ���Ϣ��" + Managerfile);
					out.write(Managerfile);
				} else {
					System.out.println("������ȷ �û�ID" + mIdCard);
					Manager m6 = md.showManager(mIdCard.trim()); // ��ȡ��ӦID��User
					m6.setState("true");
					System.out.println("name" + m6.getmName());
					System.out.println("name" + m6.toString());
					jsonManager = CreateJson.getManagerJson(m6);
					System.out.println("��¼�ɹ�");
					out.write("{\"mangerData\":" + jsonManager + "}"); // ��¼�����û�����User������Ϣ
					// out.println(msg);
				}
			}else if(m.getState().equals("download".trim())) { // ������ǩ 3
				String finalMark = "";
				try{
					finalMark=pd.selectPicturesFFN(); // �Ѿ���Json��������
				}catch(Exception e){
					System.out.println("������ǩ�쳣��"+e);
					finalMark = "";
				}
				if (finalMark.equals("")) { // �ޱ�ǩ�����
					out.write("");
				} else {
					finalMark = "[" + finalMark + "]";
					System.out.println("���ͱ�ǩ�������" + finalMark);
					out.write(finalMark);
				}

			} else if (m.getState().equals("search".trim())) { // ���Ͷ�Ӧ�绰�����־Ը����Ϣ4
				System.out.println("search 1ִ��");			
				User u = new User();
				try {
					u=CreateJson.getUser(sb.toString().trim());
					String UID= ud.getUserID(u.getUserTel().trim());
					System.out.println(u.getUserTel().trim());
					u=ud.showUser(UID.trim()); 
					u.setState("true");
					String userJson = CreateJson.getUserJson(u);
					System.out.println("����Ա�����û����ҳɹ���" + userJson);
					out.write(userJson);
				} catch (Exception e) {
					u.setState("false");
					String userJson = CreateJson.getUserJson(u);
					System.out.println("����Ա�����û�����ʧ�ܣ�" + userJson);
					out.write(userJson);
				}
			} else if (m.getState().equals("update")) {// ���¹���Ա���� 5
				boolean state = md.updateManager(m);
				Manager m2 = new Manager();
				String ss;
				if (state) {
					ss = "true";
					System.out.println("����Ա���³ɹ���");
				} else {
					ss = "false";
					System.out.println("����Ա����ʧ�ܣ�");
				}
				m2.setState(ss);
				String managerJson = CreateJson.getManagerJson(m2);
				out.write(managerJson);
			} else if (m.getState().equals("updateUser")) {// ����־Ը������ 6
				try{
					System.out.println("updateUserִ��");
					User u = CreateJson.getUser(sb.toString());
					//u = ud.showUser(u.getUserID());
					boolean state = ud.updateUser(u);
					Manager m2 = new Manager();
					String ss;
					if (state) {
						ss = "true";
						System.out.println("־Ը�߸��³ɹ���");
					} else {
						ss = "false";
						System.out.println("־Ը����ʧ�ܣ�");
					}
					m2.setState(ss);
					String managerJson = CreateJson.getManagerJson(m2);
					out.write(managerJson);
				}catch(Exception e){
					System.out.println(e);
					Manager m3=new Manager();
					m3.setState("false");
					String ms=CreateJson.getManagerJson(m3);
					out.write(ms);
				}
			}else if (m.getState().equals("getfeedback")) {// ��ȡ�û��·����쳣���������Ա  7
				String feedback="";//md.selectNewFeedback();
				try{//6.21
				 feedback=md.selectNewFeedback();
				}catch(Exception e){
					System.out.println("getfeedback�쳣"+e);
					feedback="";
				}
				if(!feedback.trim().equals("")){
					System.out.println("{\"userfeedbackdata\":["+feedback+"]}");
					out.write("{\"userfeedbackdata\":["+feedback+"]}");
				}else{
					System.out.println("not found the newFeedback");
					out.write("");
				}
				
			}else if (m.getState().equals("getSolvedFeedback")) {// ��ȡ�û����з����쳣���������Ա 8
				System.out.println("getSolvedFeedbackִ��");
				String feesback="";
				try{//6.21
					feesback=md.selectSolvedFeedback();
				}catch(Exception e){
					System.out.println("getSolvefeesback�쳣");
					feesback="";
				}
				if(!feesback.equals("")){
					System.out.println("getSolvedFeedback: "+feesback);
					out.write("["+feesback+"]");
				}else{
					System.out.println("not found the SolvedFeedback");
					out.write("");
				}
			}else if (m.getState().equals("solveFeedback")){// �쳣������ķ����޸�Ϊ��ʷ����states=1  9
				System.out.println("solveFeedbackִ��");
				boolean b=false;
				try{
					 b=md.solveFeedback(sb.toString());
				}catch(Exception e){
					System.out.println("solveFeedback�쳣"+e);
					b=false;
				}
				if(b){
					Manager mana=new Manager();
					mana.setState("true");
					String ma = CreateJson.getManagerJson(mana);
					out.write(ma);
				}else{
					Manager mana=new Manager();
					mana.setState("false");
					String ma = CreateJson.getManagerJson(mana);
					out.write(ma);
				}
			}else if (m.getState().equals("setHisNum")){//10�޸��û������޶�ֵ
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("����hisNumΪ: "+qualify.getHisNum());
				}catch(Exception e){
					System.out.println("setHisNum�쳣");
					qualify.setHisNum(0);
				}
				if(qualify.getHisNum()==0){
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					System.out.println("setHisNum "+qualifyJson);
					out.write(qualifyJson);
				}else{
					Qualify qu=new Qualify();
					try{
						qu=qd.showQualify();
						qu.setHisNum(qualify.getHisNum());
						boolean b=false;
						b=qd.updateQualify(qu);
						if(b){
							qualify.setState("true");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setHisNum "+qualifyJson);
							out.write(qualifyJson);
						}else{
							qualify.setState("false");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setHisNum "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setHisNum�쳣 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
				
			}else if (m.getState().equals("setMarkNum")){//11�޸�ȷ����ǩ�޶�ֵ
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("����MarkNumΪ: "+qualify.getMarkNum());
				}catch(Exception e){
					System.out.println("setMarkNum�쳣"+e);
					qualify.setMarkNum(0);
				}
				if(qualify.getMarkNum()==0){
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					out.write(qualifyJson);
				}else{
					Qualify qu=new Qualify();
					try{
						qu=qd.showQualify();
						qu.setMarkNum(qualify.getMarkNum());
						boolean b=false;
						b=qd.updateQualify(qu);
						if(b){
							qualify.setState("true");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setMarkNum "+qualifyJson);
							out.write(qualifyJson);
						}else{
							qualify.setState("false");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setMarkNum "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setMarkNum�쳣 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("setSimilarity")){//12�޸����ƶ�
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("����SimilarityΪ: "+qualify.getNum());
				}catch(Exception e){
					System.out.println("setSimilarity�쳣"+e);
					qualify.setNum(0);;
				}
				if(qualify.getNum()==0){
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					out.write(qualifyJson);
				}else{
					Qualify qu=new Qualify();
					try{
						qu=qd.showQualify();
						qu.setNum(qualify.getNum());
						boolean b=false;
						b=qd.updateQualify(qu);
						if(b){
							qualify.setState("true");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setNum "+qualifyJson);
							out.write(qualifyJson);
						}else{
							qualify.setState("false");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setNum����ʧ�� "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setNum�쳣 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("setCheckId")){//13�޸Ĺ���Ա����ֵ  Ĭ��Ϊgb10086
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("����setCheckIdΪ: "+qualify.getStates());
				}catch(Exception e){
					System.out.println("setCheckId�쳣1"+e);
					qualify.setStates("null");
				}
				if(qualify.getStates().trim().equals("null")||qualify.getStates().trim().equals("")){
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					System.out.println("ʧ��null��"+qualifyJson);
					out.write(qualifyJson);
				}else{
					Qualify qu=new Qualify();
					try{
						qu=qd.showQualify();
						qu.setStates(qualify.getStates());
						boolean b=false;
						b=qd.updateQualify(qu);
						if(b){
							qualify.setState("true");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setCheckId "+qualifyJson);
							out.write(qualifyJson);
						}else{
							qualify.setState("false");
							String qualifyJson=CreateJson.getQualifyJson(qualify);
							System.out.println("setCheckId����ʧ�� "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setCheckId�쳣2 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("requestQualify")){//13�鿴�޶�ֵ
				Qualify qualify=new Qualify();
				try{
					qualify=qd.showQualify();
					qualify.setState("true");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					System.out.println("requestQualify "+qualifyJson);
					out.write(qualifyJson);
				}catch(Exception e){
					System.out.println("requestQualify�쳣 "+e);
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					out.write(qualifyJson);
				}
			}else if(m.getState().trim().equals("changePassword")){  //�޸�����  14
				boolean b = md.ChangePassword(m);
				if (b) {
					Manager manager = new Manager();
					manager.setState("true");
					String managerjson = CreateJson.getManagerJson(manager);
					out.write(managerjson);
				} else {
					Manager manager = new Manager();
					manager.setState("false");
					String managerjson = CreateJson.getManagerJson(manager);
					out.write(managerjson);
				}
			}else { // û�ҵ���Ӧstate��ֵ   15
				Manager mana = new Manager();
				mana.setState("false");
				String ma = CreateJson.getManagerJson(mana);
				System.out.println("�]�ҵ�state");
				System.out.println(ma);
				out.write(ma);
			}

		}else { // ���ӷ�����ʧ�ܣ�
//			msg = "404 Not Found! Connection server failed (User name or password error)"; // ���ӷ�����ʧ��
//			out.write(msg);
			Manager mana=new Manager();
			mana.setState("false");
			String mas=CreateJson.getManagerJson(mana);
			out.write(mas);
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

}
