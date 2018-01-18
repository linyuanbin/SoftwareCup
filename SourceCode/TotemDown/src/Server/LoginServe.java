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

		// 获取“http” //获取服务名 //获取端口号
		String str = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "";
		System.out.println(str);

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		// request.setCharacterEncoding("utf-8");
		// response.setContentType("text/html");
		// response.setContentType("text/html;charset=utf8"); //解决中文乱码
		// 必须写在得到流之前gbk
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
		// String username = request.getParameter("username");
		// username = new String(username.getBytes("iso-8859-1"),"utf-8”);
		String password = request.getParameter("password");
		System.out.println(username + ":" + password);

		PrintWriter out = response.getWriter(); // 通过response得到字节输出流
		String msg = null;
		if (username != null && username.equals("linyuanbin") && password != null && password.equals("123456")) {

			System.out.print("登录服务器成功l ！");
			BufferedReader br = request.getReader();

			String str1 = null;
			StringBuilder resource = new StringBuilder();
			while ((str1 = br.readLine()) != null) // 判断最后一行不存在，为空结束循环
			{
				resource.append(str1);
				System.out.println(str1);// 原样输出读到的内容
			}
			User u = CreateJson.getUser(resource.toString());

			if ((u.getState().trim()).equals("login")) { // 登录 1
				String UserId = d.login(u.getUserName(), u.getUserPassword());
				String jsonUser = "";
				if (UserId.equals("")) {
					System.out.println("密码错误！");
					msg = UserId;// 空值
					User u2 = new User();// 创建一个空的User对象给客户端
					u2.setState("false");
					u2.setUserID(" ");
					String userfile = CreateJson.getUserJson(u2);
					System.out.println("錯誤用戶信息：" + userfile);
					out.write(userfile);
				} else {
					System.out.println("密码正确 用户ID" + UserId);
					User u6 = d.showUser(UserId.trim()); // 获取对应ID的User
					u6.setState("true");
					System.out.println("name" + u6.getUserName());
					System.out.println("name" + u6.toString());
					jsonUser = CreateJson.getUserJson(u6);
					msg = u6.toString();
					// out.write(jsonUser); //登录是向用户反馈User基本信息
					out.write("{\"userData\":" + jsonUser + "}");
					// out.println(msg);
				}
			} else if ((u.getState().trim()).equals("register")) { // 注册 2
				String userID = d.register(u);
				System.out.println("注册情况：" + userID);
				if ((!userID.equals(""))) {// 如果注册成功
					// msg = u.getUserID(); //注册成功反馈UserId
					u.setState("true");
					String userfile = CreateJson.getUserJson(u);
					System.out.println("注册成功用户信息：" + userfile);
					out.write("{\"userData\":" + userfile + "}");
				} else {
					u.setState("false");
					String s = CreateJson.getUserJson(u);
					System.out.println("注册失败用户信息：" + s);
					out.write("{\"userData\":" + s + "}");
				}
			} else if ((u.getState().trim()).equals("update")) { // 更新用户 3
				d.updateUser(u);
				// System.out.println("用户生日：" +
				// u.getUserBirthday().toLocaleString());
			} else if ((u.getState().trim()).equals("request".trim())) { // 推送请求12张
																			// 4
				// try{
				if (u.getUserID().equals("")) {
					System.out.println("请求失败,用户未登录");
					out.write("");
				} else {
					String pushPictureJson = PushPicture.getPush(u.getUserID());
					if (pushPictureJson.equals("")) {
						pushPictureJson = SimUserPicture.getSimU(u.getUserID()); // 找相似用户推送
					}
					pushPictureJson = "[" + pushPictureJson + "]";
					System.out.println("推送：" + pushPictureJson);
					out.write(pushPictureJson);
					Qualify qualify = qd.showQualify();
					User user1 = d.showUser(u.getUserID().trim());
					if (user1.getAccomplish() < qualify.getHisNum()) { // 只有系统随机推送时每次十二张
						System.out.println("任务量加12" + u.getUserID() + user1.getUserName());
						user1.setTotal(user1.getTotal() + 12); // 任务量加12
						d.updateUser(user1);
					}
				}
				// }catch(Exception e){
				// System.out.println(e);
				// System.out.println("pushpicture异常外");
				// out.write("");
				// }
			} else if ((u.getState().trim()).equals("requestRand".trim())) { // 随机推送请求12张  5
		
				if (u.getUserID().equals("")) {
					System.out.println("请求失败,用户未登录");
					out.write("");
				} else {
					String pushPictureJson = PushPicture.getRandPush(u.getUserID());
					pushPictureJson = "[" + pushPictureJson + "]";
					System.out.println("随机推送：" + pushPictureJson);
					out.write(pushPictureJson);
				}
			} else if (u.getState().trim().equals("mark".trim())) { // 打标签 6
				System.out.println("打标签模块执行");
				User user = d.showUser(u.getUserID());
				System.out.println("打标签的用戶" + user.getUserName());
				System.out.println("PID:" + u.getPID());
				Picture p = pd.selectSinglePictureFID(u.getPID().trim());
				System.out.println("被标签的图片" + p.getPName());
				Mark m = new Mark();
				String theMarkName = Translate.getTranslate(u.getMarkName().replaceAll(" ", ""));// 删除所有空格
				System.out.println("中文" + theMarkName);
				// theMarkName = HanlpUtil.get(theMarkName);
				// System.out.println("分解：" + theMarkName);
				m.setMarkName(theMarkName.trim());// 将所有语言标签都转换成中文
				m.setUser(user);
				m.setMarkDate(new Date());
				m.setPicture(p);
				m.setTabId(user.getUserID().trim() + p.getPID().trim());
				boolean b = md.insertMark(m);
				// boolean
				// b=md.insertIntoMark(u.getUserID().trim(),u.getPID().trim(),u.getMarkName().trim());
				// //存入标签
				if (b) {// 标签成功
					try {
						user.setUserIntegral(user.getUserIntegral() + 1);// 积分加一
						user.setAccomplish(user.getAccomplish() + 1); // 完成量加一
						Qualify qualify = qd.showQualify();
						if (user.getAccomplish() > qualify.getHisNum()) {
							user.setTotal(user.getTotal() + 1);
						}
						d.updateUser(user);
						user.setState("true");
						System.out.println("标签成功");
						String s = CreateJson.getUserJson(user);
						out.write(s);
						// user.setAccomplish(user.getAccomplish()+1); //完成量加一
						// d.updateUser(user);
					} catch (Exception e) {
						System.out.println(e);
						System.out.println("打标签异常");
						// out.write("");
					}
				} else { // 标签失败
					System.out.println("标签失败");
					User user2 = new User();
					user2.setState("false");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} // 标签失败

			} else if (u.getState().trim().equals("updatemark".trim())) { // 更新标签
																			// 7
				System.out.println("更新标签模块执行");
				User user = d.showUser(u.getUserID());
				System.out.println("更新标签的用戶" + user.getUserName());
				System.out.println("PID:" + u.getPID());
				Picture p = pd.selectSinglePictureFID(u.getPID().trim());
				System.out.println("被更新标签的图片" + p.getPName());
				Mark m = new Mark();
				String theMarkName = Translate.getTranslate(u.getMarkName().trim());
				System.out.println("中文" + theMarkName);
				m.setMarkName(theMarkName.trim());// 将所有语言标签都转换成中文
				m.setUser(user);
				m.setMarkDate(new Date());
				m.setPicture(p);
				m.setTabId(user.getUserID().trim() + p.getPID().trim());
				boolean b = md.insertMark(m);
				// boolean
				// b=md.insertIntoMark(u.getUserID().trim(),u.getPID().trim(),u.getMarkName().trim());
				// //存入标签
				if (b) {// 更新标签成功
					User user2 = new User();
					user2.setState("true");
					System.out.println("更新标签成功");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} else { // 标签失败
					System.out.println("更新标签失败");
					User user2 = new User();
					user2.setState("false");
					String s = CreateJson.getUserJson(user2);
					out.write(s);
				} // 标签失败

			} else if (u.getState().trim().equals("history".trim())) {// 查询历史标签记录
																		// 8
				if (!u.getUserID().equals("")) {
					// 返回用户历史标签记录
					String historyJson = "";// d.getHistory(u.getUserID());
					try {
						historyJson = d.getHistory(u.getUserID());
					} catch (Exception e) {
						System.out.println("history异常a： " + e);
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
			} else if (u.getState().trim().equals("search".trim())) { // 9 搜索图片
				SearchP s = new SearchP();
				s = CreateJson.getSearchP(resource.toString());
				System.out.println("搜索内容： " + s.getContent());
				String picturejson = "";
				if (!s.getContent().trim().equals("null")) {
					picturejson = pd.search(s.getContent().trim());
					if (!picturejson.equals("")) {
						System.out.println("[" + picturejson + "]");
						out.write("[" + picturejson + "]");
					} else {
						System.out.println("未找到想要的图片");
						out.write("");
					}
				} else {
					System.out.println("搜索内容空");
					out.write("");
				}
			} else if (u.getState().trim().equals("feedback".trim())) { // 10 反馈
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
			} else if (u.getState().trim().equals("changePassword".trim())) { // 11修改密码
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
			} else if (u.getState().trim().equals("getHead".trim())) { // 12 反馈
				if (!u.getUserTel().trim().equals("") && (u.getUserTel().length() == 11)) {
					String uid = d.getUserID(u.getUserTel().trim());
					if (uid.trim().equals("")) { // 用户不存在
						System.out.println("用户不存在，获取头像失败");
						User user2 = new User();
						user2.setState("false");
						String userJosn = CreateJson.getUserJson(user2);
						out.write(userJosn);
					} else { // 用户存在
						User user = d.showUser(uid);
						if (!user.getLocalAddress().equals("")) { // 如果有本地头像地址
							User user2 = new User();
							user2.setState("true");
							user2.setLocalAddress(user.getLocalAddress());
							String userJosn = CreateJson.getUserJson(user2);
							System.out.println("获取本地头像" + userJosn);
							out.write(userJosn);
						} else {
							System.out.println("获取本地头像为空");
							User user2 = new User();
							user2.setState("false");
							String userJosn = CreateJson.getUserJson(user2);
							out.write(userJosn);
						}
					}
				} else {
					System.out.println("获取本地头像失败，电话号码为空！");
					User user2 = new User();
					user2.setState("false");
					String userJosn = CreateJson.getUserJson(user2);
					out.write(userJosn);
				}
				
			} else if (u.getState().trim().equals("getSimplePicture".trim())) { // 13 以图搜图
				Picture picture=CreateJson.getPicture(resource.toString());
				String url=picture.getPAddress();
				String s=pd.getSimplepicture2(url);
				if(s.equals("")){
					System.out.println("没有找到相似图片");
					out.write("");
				}else{
					System.out.println("搜索结果： "+"["+s+"]");
					out.write("["+s+"]");
				}
			}else {
				System.out.println("没找到state"); // 13  
				User user = new User();
				user.setState("false");
				String userjson = CreateJson.getUserJson(user);
				out.write(userjson);
				// String str2="500 NotFound the state";
				// out.write(str2);
			}
		} else {
			System.out.println("登入服务器失败");
			User u = new User();
			u.setState("false");
			String userjson = CreateJson.getUserJson(u);
			out.write(userjson);
			// msg = "404 NotFound connect username or password error"; //注册失败回馈
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
