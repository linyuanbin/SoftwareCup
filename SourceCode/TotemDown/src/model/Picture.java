package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Picture {

	private String PID; //ͼƬid
	private String PName; //ͼƬ�ļ�����
	private String PAddress; 
	private String FinalMarkName; //���ձ�ǩ
	private Date FinalTime; //ȷ�����ձ�ǩʱ��
	private int states;
	private String firstName;//ϵͳ�����ĳ�ʼ��ǩ
	private Set<User> Users=new HashSet<User>(); //�洢�����User������set
	
	
	
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getPName() {
		return PName;
	}
	public void setPName(String pName) {
		PName = pName;
	}
	public String getPAddress() { 
		return PAddress;
	}
	public void setPAddress(String pAddress) {
		PAddress = pAddress;
	}
	public String getFinalMarkName() {
		return FinalMarkName;
	}
	public void setFinalMarkName(String finalMarkName) {
		FinalMarkName = finalMarkName;
	}
	public Date getFinalTime() {
		return FinalTime;
	}
	public void setFinalTime(Date finalTime) {
		FinalTime = finalTime;
	}
	
	public Set<User> getUsers() {
		return Users;
	}
	public void setUsers(Set<User> users) {
		Users = users;
	}
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	
	
	/*
	 *  <!-- ��Զ��ϵӳ�� -->
        <set name="Users" table="MARK" inverse="true" lazy="false">
            <key>
                <column name="RPID" />
            </key>
            <many-to-many class="model.User"  column="RUSERID"/>
        </set>
	 */
	
	
}
