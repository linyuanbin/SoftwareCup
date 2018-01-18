package Server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

import dao.PictureDao;
import daoimplement.PictureImplement;
import model.Picture;

public class UploadShipServlet extends HttpServlet {
	public static int a=0;
	private static final long serialVersionUID = 1L;
	private String path;
	public static PictureDao pd=new PictureImplement();

	public UploadShipServlet() {
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
						path="C:\\web\\photo\\"+item.getName();  //C:\\nginx\\html\\photo     //C:\\web\\photo\\
						inputStream2File(is, path);
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
			}
			out.write(path);  //�����Ұѷ���˳ɹ��󣬷��ظ��ͻ��˵����ϴ��ɹ���·��
		} catch (FileUploadException e) {
			e.printStackTrace();
			System.out.println("failure");
			out.write("failure");
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
			Picture p=new Picture();       //ÿ�ϴ�һ��ͼƬ�ͱ��浽���ݿ�
			p.setPName(file.getName());
			p.setPAddress("http://114.115.210.8/photo/"+file.getName()); //114.115.210.8
			System.out.println("�ϴ�ͼƬ����"+file.getName());
			boolean b=pd.AddPicture(p); 
			System.out.println("ͼƬ�ϴ����ݿ⣺"+b+" "+"�ϴ�ͼƬ������"+ ++a);
			fos.flush();
			fos.close();
			fis.close();
			inputSteam.close();

		}
		
}