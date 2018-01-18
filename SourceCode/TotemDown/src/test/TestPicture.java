/*package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Session;
import org.junit.Test;

import PictureAction.PictureUtil;
import PictureAction.SearchPicture;
import dao.PictureDao;
import daoimplement.PictureImplement;
import getMarkUtil.PictureContent;
import hibernateutil.SessionAnnotation;
import jsonUtil.CreateJson;
import model.Image;
import model.ImageList;
import model.Picture;
import translateUtil.Translate;
import util.RandomString;

public class TestPicture {

	private static PictureDao pd = new PictureImplement();
	//private String mPath = "F:\\picture\\photo";// \\����
	private String mPath="C:\\nginx\\html\\photo";

	@Test
	public void testPicture() { // �����ļ���������
		// +"Pictures");
		File file = new File("D:\\Mypicture");
		String writePath = "D:\\Mypicture\\";
		String readPath = "C:\\Users\\lin\\Pictures\\";
		Vector<String> pictures = SearchPicture.SearchPName("F:\\picture\\photo");// ("c:"+System.getProperty("file.separator")
		for (String fileName : pictures) { // �����fileName��ʵ�Ǿ���·��
			File f = new File(fileName.toString());
			System.out.println(fileName);
			// save(readPath + fileName, writePath + fileName);
		}
		
		Vector<String> picturesPath = SearchPicture.SearchPPath("F:\\picture\\photo");
		for (String fileName : picturesPath) { // �����fileName��ʵ�Ǿ���·��
			File f = new File(fileName.toString());
			System.out.println(fileName);
			// save(readPath + fileName, writePath + fileName);
		}
	}

	@Test   //������ͼƬ���ص����ݿ�
	public void testAdd() throws IOException { // ���Ի�ȡ�������� 

		DirectoryStream<Path> paths = SearchPicture.GetPath(mPath);
		
		Picture picture=new Picture();
		for (Path p : paths) {
			System.out.println(p.toString() + "-----" + p.getFileName().toString());
			picture.setPName(p.getFileName().toString());
			picture.setPAddress("http://114.115.210.8/photo/"+p.getFileName());
			try{
			pd.AddPicture(picture);
			
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}

	@Test
	public void testGetAllFile() throws IOException { // �ӷ�������ȡ

		DirectoryStream<Path> paths = SearchPicture.getAllfileFList(mPath);
		System.out.println("��ʾͼƬscssccscs��");

		for (Path p : paths) {
			System.out.println(p.toString() + "-----" + p.getFileName().toString());

		}
	}

	@Test
	public void testGetAPicture() { // �����ݿ���

		Set<Picture> Pictures = new HashSet<Picture>();
		System.out.println("����");
		Pictures = pd.selectAllPicture();
		System.out.println("------picture" + Pictures);
		for (Picture p : Pictures) {
			System.out.println(p.getPName());
		}
		 
//		Picture p=pd.selectSinglePictureFN("����");
//		System.out.println(p.getPAddress());

		Set<Picture> Pictures = new HashSet<Picture>(); //�������Ʋ��� �ɹ�
		System.out.println("����");
		Pictures = pd.selectPicturesFN("����");
		System.out.println("------picture" + Pictures);
		for (Picture p : Pictures) {
			System.out.println(p.getPName());
		}
		
		
		 * Session s = SessionAnnotation.getSession(); s.beginTransaction();
		 * String sql = "select PID from Picture"; List<String> list =
		 * (List<String>) s.createQuery(sql).list();
		 * s.getTransaction().commit(); for (String li : list) {
		 * s.beginTransaction(); Picture p = (Picture) s.get(Picture.class, li);
		 * s.getTransaction().commit(); System.out.println(p.getPName());
		 * System.out.println(li); }
		 

		//�������ɳ�ʼ��ǩ
		Picture p=pd.selectSinglePictureFID("Wed May 24 21:08:40 CST 2017YCne0");
		String image=PictureContent.getPImage(p.getPAddress());//���ɳ�ʼ��ǩ
		Scanner scanner=new Scanner(image);
		StringBuilder sb=new StringBuilder();
		//scanner.useDelimiter(":");
		while(scanner.hasNext()){  //��ǩ���Ļ�
			String s=scanner.nextLine();
			String s2=Translate.getTranslate(s);
			sb.append(s2+":");
			System.out.println(s+":"+s2);
			
		}
		p.setFirstName(sb.toString());
		boolean b=pd.updatePicture(p);
		System.out.println("����firstMark :"+b);
	}
	
	@Test
	public void selectAllPictureOnDB(){
		Set<Picture> pictures= pd.selectPicturesFN("��"); //pd.selectAllPicture();
		for(Picture p:pictures){
			System.out.println(p.getPAddress());
		}
	}
	
	@Test
	public void testFinalmark(){
		
		String s=pd.selectPicturesFFN();
		s="["+s+"]";
		System.out.println(s);
		
		
//		Picture p=pd.selectSinglePictureFID("Sat Apr 29 15:54:58 CST 2017VGio1");
//		System.out.println(p.getPName());
		
	}
	
	@Test
	public void testFirstMark(){
		Picture p=new Picture();
		p=pd.selectSinglePictureFID("Wed May 24 21:08:40 CST 2017YCne0");
		System.out.println(p.getPName());
		String url=p.getPAddress();
		String image=PictureContent.getPImage(url);//���ɳ�ʼ��ǩ
		Scanner scanner=new Scanner(image);
		StringBuilder sb=new StringBuilder();
		//scanner.useDelimiter(":");
		while(scanner.hasNext()){  //��ǩ���Ļ�
			String s=scanner.nextLine();
			String s2=Translate.getTranslate(s);
			sb.append(s2+":");
			System.out.println(s+":"+s2);
		}
		p.setFirstName(sb.toString()); 
		if(sb.toString().equals("")){
			p.setStates(0);
		}else{
			p.setStates(1);
		}
		pd.updatePicture(p);
		
	}
	
}
*/