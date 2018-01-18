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

public class UploadPicture extends HttpServlet{ //ʵ��־Ը�ߡ�����Ա�û�ͷ���ϴ�
	
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
	 	System.out.println("�Ñ���"+username+" "+password+"�ϴ�ͷ��");
	 	if(username.trim().equals("manager")||username.trim().equals("user")){
	 	// �����ļ���Ŀ��������
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// �����ļ��ϴ�·��
			String upload = this.getServletContext().getRealPath("/");
			
			// ��ȡϵͳĬ�ϵ���ʱ�ļ�����·������·��ΪTomcat��Ŀ¼�µ�temp�ļ���
			String temp = System.getProperty("java.io.tmpdir");
			// ���û�������СΪ 5M
			factory.setSizeThreshold(1024 * 1024 * 5);
			// ������ʱ�ļ���Ϊtemp
			factory.setRepository(new File(temp));
			// �ù���ʵ�����ϴ����,ServletFileUpload ���������ļ��ϴ�����
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

			// �����������List��
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
				out.write("http://114.115.210.8/head/"+list.get(0).getName());  //�����Ұѷ���˳ɹ��󣬷��ظ��ͻ��˵����ϴ��ɹ���·��
			} catch (FileUploadException e) {
				e.printStackTrace();
				System.out.println("failure");
				out.write("failure");
			}
	 	}
	 	out.flush();
		out.close();
	}

	// ��ת�����ַ���
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read())!=-1){
			baos.write(i);
		}
		return baos.toString();
	}

	// ��ת�����ļ�
		public static void inputStream2File(InputStream is,String savePath) throws Exception {
			System.out.println("�ļ�����·��Ϊ:" + savePath);
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
					//���ϴ���ͷ�񱣴浽���ݿⲢ����y�û����ݿ�
					System.out.println("����Աͷ���ϴ�ִ�У�");
					String MID=md.getManagerID(password.trim());
					System.out.println("�ϴ�ͷ���û��绰��"+password);
					if(MID.trim().equals("")){
						System.out.println("����Աͷ���ϴ�ʧ�ܣ�UID��");
					}else{
						Manager m=md.showManager(MID);
						System.out.println("�ϴ�ͷ���û�id��"+MID);
						m.setmHeadPort("http://114.115.210.8/head/"+file.getName().trim());
						md.updateManager(m);
						System.out.println("����Աͷ���ϴ��ɹ���");
					}
				}catch(Exception e){
					System.out.println("����Աͷ���ϴ�ʧ�ܣ�"+e);
				}
				
			}else if(username.trim().equals("user")){
				try{
					//���ϴ���ͷ�񱣴浽���ݿⲢ����y�û����ݿ�
					System.out.println("־Ը���ϴ�ͷ��ִ��");
					System.out.println("�ϴ�ͷ���û��绰��"+password);
					String UID=ud.getUserID(password.trim());
					System.out.println("�ϴ�ͷ���û��绰��"+password);
					if(UID.trim().equals("")){
						System.out.println("־Ը��ͷ���ϴ�ʧ�ܣ�");
					}else{
						User u=ud.showUser(UID);
						System.out.println("�ϴ�ͷ���û�id��"+UID);
						u.setUserHeadPortr("http://114.115.210.8/head/"+file.getName().trim());
						ud.updateUser(u);
						System.out.println("־Ը��ͷ���ϴ��ɹ���");
					}
				}catch(Exception e){
					System.out.println("־Ը���ϴ�ͷ��ʧ��"+e);
				}
			}else{
			System.out.println("�޸�ͷ��ʧ�ܣ�û��username����");	
			}
			fos.flush();
			fos.close();
			fis.close();
			inputSteam.close();
		}
	

}