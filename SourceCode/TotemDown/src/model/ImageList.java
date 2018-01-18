package model;

import java.util.List;

//接收上传的图片数组
public class ImageList {
	
	List files;
	String imagebyte;

	public List getList() {
		return files;
	}

	public void setList(List list) {
		this.files = list;
	}

	public String getImagebyte() {
		return imagebyte;
	}

	public void setImagebyte(String imagebyte) {
		this.imagebyte = imagebyte;
	}
	

}
