package com.example.dtlp.Date;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 阳瑞 on 2017/5/1.
 */
public class Picture {
    private String PID; //图片id
    private String PName; //图片文件名字
    private String PAddress;
    private String FinalMarkName; //最终标签
    private Date FinalTime; //确定最终标签时间
    private Set<User> Users=new HashSet<User>(); //存储多对象User的数组set



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

	/*
	 *  <!-- 多对多关系映射 -->
        <set name="Users" table="MARK" inverse="true" lazy="false">
            <key>
                <column name="RPID" />
            </key>
            <many-to-many class="model.User"  column="RUSERID"/>
        </set>
	 */
}
