package com.ghgk.photomanage.Date;

/**
 * Created by 阳瑞 on 2017/6/24.
 */
public class Qualify {

    private int id;
    private int hisNum; //定义用户有多少张标签历史后进行针对性推送
    private int markNum; //定义一张图片有多少用户
    private int Num;  //留用
    private String states; //留用
    private String state; //留用


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getHisNum() {
        return hisNum;
    }
    public void setHisNum(int hisNum) {
        this.hisNum = hisNum;
    }
    public int getMarkNum() {
        return markNum;
    }
    public void setMarkNum(int markNum) {
        this.markNum = markNum;
    }
    public int getNum() {
        return Num;
    }
    public void setNum(int num) {
        Num = num;
    }
    public String getStates() {
        return states;
    }
    public void setStates(String states) {
        this.states = states;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }



}
