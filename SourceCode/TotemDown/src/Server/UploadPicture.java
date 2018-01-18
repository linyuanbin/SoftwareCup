package Server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

import dao.ManagerDao;
import dao.PictureDao;
import dao.UserDao;
import daoimplement.ManagerImplement;
import daoimplement.PictureImplement;
import daoimplement.UserImplement;
import model.Manager;
import model.Picture;
import model.User;

public class UploadPicture extends HttpServlet{ //实现志愿者、管理员用户头像上传
	
	private static final long serialVersionUID = 1L;
	private String path;
	private static String password;
	private static String username;
	
	public static PictureDao pd=new PictureImplement();
	public static UserDao ud=new UserImplement();
	public static ManagerDao md=new ManagerImplement();

	public UploadPicture() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		password = request.getParameter("password");
	 	username = request.getParameter("username");
	 	System.out.println("用戶名"+username+" "+password+"上传头像");
	 	if(username.trim().equals("manager")||username.trim().equals("user")){
	 	// 创建文件项目工厂对象
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 设置文件上传路径
			String upload = this.getServletContext().getRealPath("/");
			
			// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
			String temp = System.getProperty("java.io.tmpdir");
			// 设置缓冲区大小为 5M
			factory.setSizeThreshold(1024 * 1024 * 5);
			// 设置临时文件夹为temp
			factory.setRepository(new File(temp));
			// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

			// 解析结果放在List中
			try {
				List<FileItem> list = servletFileUpload.parseRequest(request);

				for (FileItem item : list) {
					String name = item.getFieldName();
					InputStream is = item.getInputStream();

					if (name.contains("content")) {
						System.out.println(inputStream2String(is));
					} else if (name.contains("img")) {
						try {
							path = upload+"\\"+item.getName();
							path="C:\\web\\head\\"+item.getName();//C:\\nginx\\html\\head\\   //C:\\web\\head
							inputStream2File(is, path);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				out.write("http://114.115.210.8/head/"+list.get(0).getName());  //这里我把服务端成功后，返回给客户端的是上传成功后路径
			} catch (FileUploadException e) {
				e.printStackTrace();
				System.out.println("failure");
				out.write("failure");
			}
	 	}
	 	out.flush();
		out.close();
	}

	// 流转化成字符串
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read())!=-1){
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
		public static void inputStream2File(InputStream is,String savePath) throws Exception {
			System.out.println("文件保存路径为:" + savePath);
			File file = new File(savePath);
			InputStream inputSteam = is;
			BufferedInputStream fis = new BufferedInputStream(inputSteam);
			FileOutputStream fos = new FileOutputStream(file);
			int f;
			while ((f = fis.read()) != -1) {
				fos.write(f);
			}
			
			if(username.trim().equals("manager")){
				try{
					//将上传的头像保存到数据库并更新y用户数据库
					System.out.println("管理员头像上传执行！");
					String MID=md.getManagerID(password.trim());
					System.out.println("上传头像用户电话："+password);
					if(MID.trim().equals("")){
						System.out.println("管理员头像上传失败！UID空");
					}else{
						Manager m=md.showManager(MID);
						System.out.println("上传头像用户id："+MID);
						m.setmHeadPort("http://114.115.210.8/head/"+file.getName().trim());
						md.updateManager(m);
						System.out.println("管理员头像上传成功！");
					}
				}catch(Exception e){
					System.out.println("管理员头像上传失败！"+e);
				}
				
			}else if(username.trim().equals("user")){
				try{
					//将上传的头像保存到数据库并更新y用户数据库
					System.out.println("志愿者上传头像执行");
					System.out.println("上传头像用户电话："+password);
					String UID=ud.getUserID(password.trim());
					System.out.println("上传头像用户电话："+password);
					if(UID.trim().equals("")){
						System.out.println("志愿者头像上传失败！");
					}else{
						User u=ud.showUser(UID);
						System.out.println("上传头像用户id："+UID);
						u.setUserHeadPortr("http://114.115.210.8/head/"+file.getName().trim());
						ud.updateUser(u);
						System.out.println("志愿者头像上传成功！");
					}
				}catch(Exception e){
					System.out.println("志愿者上传头像失败"+e);
				}
			}else{
			System.out.println("修改头像失败，没有username属性");	
			}
			fos.flush();
			fos.close();
			fis.close();
			inputSteam.close();
		}
	

}