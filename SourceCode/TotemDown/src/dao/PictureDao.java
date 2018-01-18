package dao;

import java.util.Set;

import model.Picture;

public interface PictureDao {
	
	public boolean AddPicture(Picture p);  //��ӷ�����ͼƬ��Դ
	public boolean updatePicture(Picture p); //����ͼƬ��Ϣ
	public boolean deletePicture(String PID); //ɾ��ͼƬ
	public Set<Picture> selectAllPicture(); //��ѯ����ͼƬ       
	public Picture selectSinglePictureFID(String PID);  //����ͼƬ��ѯͼƬ    
	public Set<Picture> selectPicturesFN(String PName);  //�����������ֲ�ͼƬ����    //�����û�����
	public Set<Picture> selectPicturesFM(String pMark);  //�������ձ�ǩ��ѯͼƬ�ۺ�     ���ڹ���Ա���յ�����ǩ��� 
	public Picture selectSinglePictureFN(String Pname);
	public String selectPicturesFFN();  //�����������еı�ǩ�����
	public String search(String content);
	public String getSimplepicture(String url);
	public String getSimplepicture2(String url);
}
