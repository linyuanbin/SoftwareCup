package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mysql.jdbc.Blob;

public class User {

	private String UserID; //id
	private String UserName; //用户名
	private String UserTel; //电话
	private String UserEmail; //电邮
	private String UserPassword; //密码
	private String UserNickName; //昵称（用户所在地）
	private String UserSex; //性别
	private Date UserBirthday; //生日
	private String UserMajor; //职业
	private int UserIntegral; //积分
	private String UserUnderWrite;//签名
	private String UserHobby; //爱好
	private String UserHeadPortr;//用户头像
	private int accomplish; //完成标签量
	private int total; //总任务数量
	private String state;
	private String localAddress;
	private String PID;//
	private String MarkName;
	//存储多对象图片的数组set
	private Set<Picture> pictures=new HashSet<>();
	//private String User_WeChat; //微信号
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	private static int a=0;

	
	
	public String getUserID() {
		return UserID;
	}
	
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserTel() {
		return UserTel;
	}
	public void setUserTel(String userTel) {
		UserTel = userTel;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	public String getUserNickName() {
		return UserNickName;
	}
	public void setUserNickName(String userNickName) {
		UserNickName = userNickName;
	}
	public String getUserSex() {
		return UserSex;
	}
	public void setUserSex(String userSex) {
		UserSex = userSex;
	}
	public Date getUserBirthday() {
		return UserBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		UserBirthday = userBirthday;
	}
	public String getUserMajor() {
		return UserMajor;
	}
	public void setUserMajor(String userMajor) {
		UserMajor = userMajor;
	}
	public int getUserIntegral() {
		return UserIntegral;
	}
	public void setUserIntegral(int userIntegral) {
		UserIntegral = userIntegral;
	}
	public String getUserUnderWrite() {
		return UserUnderWrite;
	}
	public void setUserUnderWrite(String userUnderWrite) {
		UserUnderWrite = userUnderWrite;
	}
	public String getUserHobby() {
		return UserHobby;
	}
	public void setUserHobby(String userHobby) {
		UserHobby = userHobby;
	}
	public String getUserHeadPortr() {
		return UserHeadPortr;
	}
	public void setUserHeadPortr(String userHeadPortr) {
		UserHeadPortr = userHeadPortr;
	}

	public Set<Picture> getPictures() {
		System.out.println("getPicture"+a++);
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	

	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}
	

	public String getMarkName() {
		return MarkName;
	}

	public void setMarkName(String markName) {
		MarkName = markName;
	}
	
	public int getAccomplish() {
		return accomplish;
	}

	public void setAccomplish(int accomplish) {
		this.accomplish = accomplish;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", UserName=" + UserName + ", UserTel=" + UserTel + ", UserEmail=" + UserEmail
				+ ", UserPassword=" + UserPassword + ", UserNickName=" + UserNickName + ", UserSex=" + UserSex
				+ ", UserBirthday=" + UserBirthday + ", UserMajor=" + UserMajor + ", UserIntegral=" + UserIntegral
				+ ", UserUnderWrite=" + UserUnderWrite + ", UserHobby=" + UserHobby + ", UserHeadPortr=" + UserHeadPortr
				+ "]";
	}
	
	

	
	
}
