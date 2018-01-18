package dao;

import java.util.Set;

import model.Picture;

public interface PictureDao {
	
	public boolean AddPicture(Picture p);  //添加服务器图片资源
	public boolean updatePicture(Picture p); //更新图片信息
	public boolean deletePicture(String PID); //删除图片
	public Set<Picture> selectAllPicture(); //查询所有图片       
	public Picture selectSinglePictureFID(String PID);  //根据图片查询图片    
	public Set<Picture> selectPicturesFN(String PName);  //根据相似名字查图片集合    //用于用户收索
	public Set<Picture> selectPicturesFM(String pMark);  //根据最终标签查询图片聚合     用于管理员最终导出标签结果 
	public Picture selectSinglePictureFN(String Pname);
	public String selectPicturesFFN();  //导出最终所有的标签化结果
	public String search(String content);
	public String getSimplepicture(String url);
	public String getSimplepicture2(String url);
}
