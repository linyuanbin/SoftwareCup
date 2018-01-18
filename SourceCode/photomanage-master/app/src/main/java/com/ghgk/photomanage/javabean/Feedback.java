package com.ghgk.photomanage.javabean;

import java.util.Date;


public class Feedback {
	//用户反馈
	private String FbId;
	private String UserID;
	private String Content;
	private String UserTel;
	private Date date;
	private int states;
	private String state;
	public String getFbId() {
		return FbId;
	}
	public void setFbId(String fbId) {
		FbId = fbId;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserTel() {
		return UserTel;
	}
	public void setUserTel(String userTel) {
		UserTel = userTel;
	}
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", UserTel=" + UserTel + ",Content="+UserTel+",state="+state+"]";
	}
}
