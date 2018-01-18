package model;

import java.util.Date;

public class Mark {

	private String TabId;
	private User user;
	private Picture picture;
	private String MarkName;
	private Date MarkDate;
	public String getTabId() {
		return TabId;
	}
	public void setTabId(String tabId) { 
		TabId = tabId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) { 
		this.user = user;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	public String getMarkName() {
		return MarkName;
	}
	public void setMarkName(String markName) {
		MarkName = markName;
	}
	public Date getMarkDate() {
		return MarkDate;
	}
	public void setMarkDate(Date markDate) {
		MarkDate = markDate;
	}
	
	
	
}
