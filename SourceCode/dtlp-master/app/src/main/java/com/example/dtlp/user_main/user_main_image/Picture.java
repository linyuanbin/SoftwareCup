package com.example.dtlp.user_main.user_main_image;

import java.util.Date;

/**
 * Created by 阳瑞 on 2017/5/1.
 */
public class Picture {
    private String PID; //图片id
    private String PName; //图片文件名字
    private String PAddress;
    private String FinalMarkName; //最终标签
    private Date FinalTime; //确定最终标签时间
//    private Set<User> Users=new HashSet<User>(); //存储多对象User的数组set



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

//    public Set<User> getUsers() {
//        return Users;
//    }
//    public void setUsers(Set<User> users) {
//        Users = users;
//    }

}
