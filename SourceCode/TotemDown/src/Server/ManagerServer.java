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
		System.out.println("管理员登录");
		if (userName != null && userName.equals("linyuanbin") && password != null && password.equals("123456")) {
			System.out.println("管理员成功连接服务器");
			BufferedReader br = request.getReader();
			String str1 = "";
			StringBuilder sb = new StringBuilder();
			while ((str1 = br.readLine()) != null) {
				sb.append(str1);
				System.out.println("接收：" + str1);
			}

			Manager m = CreateJson.getManager(sb.toString().trim());
			System.out.println("状态值：" + m.getState());

			if (m.getState().equals("register")) { // 注册  1
													// 管理员注册只有数据库中有其对应身份证号的才允许注册
				Qualify qualify=qd.showQualify();
				if (m.getCheckId().equals(qualify.getStates().trim())) { // 注册校验码正确 "gb10086"
					String mIdCard = md.register(m);
					if (mIdCard.equals("")) { // 用户id不存在
						m.setState("false");
						String managerJson = CreateJson.getManagerJson(m);
						System.out.println("注册存入失败用户信息：" + managerJson);
						out.write(managerJson);
					} else {// 注册成功
						m.setState("true");
						String managerJson = CreateJson.getManagerJson(m);
						System.out.println("注册成功用户信息" + managerJson);
						out.write("{\"mangerData\":" + managerJson + "}");
					}
				} else { // 注册校验码错误  
					m.setState("false");
					String managerJson = CreateJson.getManagerJson(m);
					System.out.println("校验码错误注册失败用户信息：" + managerJson);
					out.write(managerJson);
				}

			} else if (m.getState().trim().equals("login".trim())) {// 登录 2
				// 登录
				String mIdCard = md.login(m.getmName().trim(), m.getmPassword().trim());
				String jsonManager = "";
				if (mIdCard.equals("")) {
					System.out.println("密码错误！");
					msg = mIdCard;// 空值
					Manager m2 = new Manager();// 创建一个空的User对象给客户端
					m2.setState("false");
					m2.setmIdCard(" ");
					String Managerfile = CreateJson.getManagerJson(m2);
					System.out.println("錯誤用戶信息：" + Managerfile);
					out.write(Managerfile);
				} else {
					System.out.println("密码正确 用户ID" + mIdCard);
					Manager m6 = md.showManager(mIdCard.trim()); // 获取对应ID的User
					m6.setState("true");
					System.out.println("name" + m6.getmName());
					System.out.println("name" + m6.toString());
					jsonManager = CreateJson.getManagerJson(m6);
					System.out.println("登录成功");
					out.write("{\"mangerData\":" + jsonManager + "}"); // 登录是向用户反馈User基本信息
					// out.println(msg);
				}
			}else if(m.getState().equals("download".trim())) { // 导出标签 3
				String finalMark = "";
				try{
					finalMark=pd.selectPicturesFFN(); // 已经是Json化的数据
				}catch(Exception e){
					System.out.println("导出标签异常："+e);
					finalMark = "";
				}
				if (finalMark.equals("")) { // 无标签化结果
					out.write("");
				} else {
					finalMark = "[" + finalMark + "]";
					System.out.println("发送标签化结果：" + finalMark);
					out.write(finalMark);
				}

			} else if (m.getState().equals("search".trim())) { // 推送对应电话号码的志愿者信息4
				System.out.println("search 1执行");			
				User u = new User();
				try {
					u=CreateJson.getUser(sb.toString().trim());
					String UID= ud.getUserID(u.getUserTel().trim());
					System.out.println(u.getUserTel().trim());
					u=ud.showUser(UID.trim()); 
					u.setState("true");
					String userJson = CreateJson.getUserJson(u);
					System.out.println("管理员查找用户查找成功：" + userJson);
					out.write(userJson);
				} catch (Exception e) {
					u.setState("false");
					String userJson = CreateJson.getUserJson(u);
					System.out.println("管理员查找用户查找失败：" + userJson);
					out.write(userJson);
				}
			} else if (m.getState().equals("update")) {// 更新管理员资料 5
				boolean state = md.updateManager(m);
				Manager m2 = new Manager();
				String ss;
				if (state) {
					ss = "true";
					System.out.println("管理员更新成功！");
				} else {
					ss = "false";
					System.out.println("管理员更新失败！");
				}
				m2.setState(ss);
				String managerJson = CreateJson.getManagerJson(m2);
				out.write(managerJson);
			} else if (m.getState().equals("updateUser")) {// 更新志愿者资料 6
				try{
					System.out.println("updateUser执行");
					User u = CreateJson.getUser(sb.toString());
					//u = ud.showUser(u.getUserID());
					boolean state = ud.updateUser(u);
					Manager m2 = new Manager();
					String ss;
					if (state) {
						ss = "true";
						System.out.println("志愿者更新成功！");
					} else {
						ss = "false";
						System.out.println("志愿更新失败！");
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
			}else if (m.getState().equals("getfeedback")) {// 获取用户新反馈异常情况给管理员  7
				String feedback="";//md.selectNewFeedback();
				try{//6.21
				 feedback=md.selectNewFeedback();
				}catch(Exception e){
					System.out.println("getfeedback异常"+e);
					feedback="";
				}
				if(!feedback.trim().equals("")){
					System.out.println("{\"userfeedbackdata\":["+feedback+"]}");
					out.write("{\"userfeedbackdata\":["+feedback+"]}");
				}else{
					System.out.println("not found the newFeedback");
					out.write("");
				}
				
			}else if (m.getState().equals("getSolvedFeedback")) {// 获取用户所有反馈异常情况给管理员 8
				System.out.println("getSolvedFeedback执行");
				String feesback="";
				try{//6.21
					feesback=md.selectSolvedFeedback();
				}catch(Exception e){
					System.out.println("getSolvefeesback异常");
					feesback="";
				}
				if(!feesback.equals("")){
					System.out.println("getSolvedFeedback: "+feesback);
					out.write("["+feesback+"]");
				}else{
					System.out.println("not found the SolvedFeedback");
					out.write("");
				}
			}else if (m.getState().equals("solveFeedback")){// 异常解决将改反馈修改为历史反馈states=1  9
				System.out.println("solveFeedback执行");
				boolean b=false;
				try{
					 b=md.solveFeedback(sb.toString());
				}catch(Exception e){
					System.out.println("solveFeedback异常"+e);
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
			}else if (m.getState().equals("setHisNum")){//10修改用户推送限定值
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("更新hisNum为: "+qualify.getHisNum());
				}catch(Exception e){
					System.out.println("setHisNum异常");
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
						System.out.println("setHisNum异常 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
				
			}else if (m.getState().equals("setMarkNum")){//11修改确定标签限定值
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("更新MarkNum为: "+qualify.getMarkNum());
				}catch(Exception e){
					System.out.println("setMarkNum异常"+e);
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
						System.out.println("setMarkNum异常 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("setSimilarity")){//12修改相似度
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("更新Similarity为: "+qualify.getNum());
				}catch(Exception e){
					System.out.println("setSimilarity异常"+e);
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
							System.out.println("setNum更新失败 "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setNum异常 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("setCheckId")){//13修改管理员鉴定值  默认为gb10086
				Qualify qualify=new Qualify();
				try{
					qualify=CreateJson.getQualify(sb.toString());
					System.out.println("更新setCheckId为: "+qualify.getStates());
				}catch(Exception e){
					System.out.println("setCheckId异常1"+e);
					qualify.setStates("null");
				}
				if(qualify.getStates().trim().equals("null")||qualify.getStates().trim().equals("")){
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					System.out.println("失败null："+qualifyJson);
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
							System.out.println("setCheckId更新失败 "+qualifyJson);
							out.write(qualifyJson);
						}
					}catch(Exception e){
						System.out.println("setCheckId异常2 "+e);
						qualify.setState("false");
						String qualifyJson=CreateJson.getQualifyJson(qualify);
						out.write(qualifyJson);
					}
				}
			}else if (m.getState().trim().equals("requestQualify")){//13查看限定值
				Qualify qualify=new Qualify();
				try{
					qualify=qd.showQualify();
					qualify.setState("true");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					System.out.println("requestQualify "+qualifyJson);
					out.write(qualifyJson);
				}catch(Exception e){
					System.out.println("requestQualify异常 "+e);
					qualify.setState("false");
					String qualifyJson=CreateJson.getQualifyJson(qualify);
					out.write(qualifyJson);
				}
			}else if(m.getState().trim().equals("changePassword")){  //修改密码  14
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
			}else { // 没找到对应state的值   15
				Manager mana = new Manager();
				mana.setState("false");
				String ma = CreateJson.getManagerJson(mana);
				System.out.println("沒找到state");
				System.out.println(ma);
				out.write(ma);
			}

		}else { // 连接服务器失败！
//			msg = "404 Not Found! Connection server failed (User name or password error)"; // 连接服务器失败
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
