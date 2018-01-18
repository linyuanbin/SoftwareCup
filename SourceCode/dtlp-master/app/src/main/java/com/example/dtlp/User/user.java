package com.example.dtlp.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by 阳瑞 on 2017/3/20.
 */
public class user extends BmobObject {

    private String name;
    private String password;
    private String job;
    private String sex;
    private String number;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public String getJob() {
        return job;
    }

    public String getSex() {
        return sex;
    }
}
