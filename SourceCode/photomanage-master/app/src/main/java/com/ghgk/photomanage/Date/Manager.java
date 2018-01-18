package com.ghgk.photomanage.Date;

/**
 * Created by cool on 2017-05-09.
 */
public class Manager {
    private String mIdCard;  //管理员身份证
    private String mName; //管理员姓名
    private String mPassword;   //密码
    private String mEmail; //邮箱
    private String mTel;//管理员手机号
    private String mHeadPort; //头像
    private String mSex;//性别
    private int mAge;//年龄（生日）
    private String mWorkNum;//员工号（本地头像）
    private String state;
    private String checkId; //管理员注册时需要用到的校验码


    public String getCheckId() {
        return checkId;
    }
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getmIdCard() {
        return mIdCard;
    }
    public void setmIdCard(String mIdCard) {
        this.mIdCard = mIdCard;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public String getmPassword() {
        return mPassword;
    }
    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
    public String getmEmail() {
        return mEmail;
    }
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public String getmTel() {
        return mTel;
    }
    public void setmTel(String mTel) {
        this.mTel = mTel;
    }
    public String getmHeadPort() {
        return mHeadPort;
    }
    public void setmHeadPort(String mHeadPort) {
        this.mHeadPort = mHeadPort;
    }
    public String getmSex() {
        return mSex;
    }
    public void setmSex(String mSex) {
        this.mSex = mSex;
    }
    public int getmAge() {
        return mAge;
    }
    public void setmAge(int mAge) {
        this.mAge = mAge;
    }
    public String getmWorkNum() {
        return mWorkNum;
    }
    public void setmWorkNum(String mWorkNum) {
        this.mWorkNum = mWorkNum;
    }

    @Override
    public String toString() {
        return "Manager [mIdCard=" + mIdCard + ", mName=" + mName + ", mPassword=" + mPassword + ", mEmail=" + mEmail
                + ", mTel=" + mTel + ", mHeadPort=" + mHeadPort + ", mSex=" + mSex + ", mAge=" + mAge + ", mWorkNum="
                + mWorkNum + ", state=" + state + "]";
    }

}
