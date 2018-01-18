package PictureAction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Date;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import org.hibernate.Session;
import org.junit.Test;

import hibernateutil.SessionAnnotation;
import model.Picture;
import util.RandomString;

//将本地图片加载到数据库中
public class PictureUtil {  

	public void savePicture2DB(Path p){
		Session session=SessionAnnotation.getSession();
		session.beginTransaction();
		Picture picture=new Picture();
		picture.setPID(new Date()+RandomString.getRandomString(5));
		picture.setPName(p.getFileName().toString());
		picture.setPAddress(p.toString());
		session.getTransaction().commit();
		SessionAnnotation.closeSession();
	}
	
	
	//byte数组到图片
	  public static void byte2image(byte[] data,String path){
		  
	    if(data.length<3||path.equals("")) return;
	    try{
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close();
	    System.out.println("Make Picture success,Please find image in " + path);
	    } catch(Exception ex) {
	      System.out.println("Exception: " + ex);
	      ex.printStackTrace();
	    }
	  }
	  
	//图片到byte数组
	  public static byte[] image2byte(String path){
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }
	    catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	  }
	  
	  @Test
	  public void test(){
		  byte n[]=image2byte("C:\\Users\\lin\\Pictures\\Saved Pictures\\img_1.jpg");
		  String s;
		try {
			s = new String(n,"UTF-8");
			System.out.println("{"+s+"}");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  byte2image(n,"F:\\image\\j.jpg");
		  
	  }
	  
	  
}
