package com.example.dtlp.Date;

import android.graphics.Picture;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 阳瑞 on 2017/5/1.
 */
public class User {

        private String UserID; //id
        private String UserName; //用户名
        private String UserTel; //电话
        private String UserEmail; //电邮
        private String UserPassword; //密码
        private String UserNickName; //用户所在地
        private String UserSex; //性别
        private Date UserBirthday; //生日
        private String UserMajor; //职业
        private int UserIntegral; //积分
        private String UserUnderWrite;//签名
        private String UserHobby; //爱好
        private String UserHeadPortr;//用户头像
        private String localAddress;//用户本地头像地址
        private String state;
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
    public String getlocalAddress() {
        return localAddress;
    }
    public void setlocalAddress(String localaddress) {
        localAddress = localaddress;
    }


    public Set<Picture> getPictures() {
            System.out.println("getPicture"+a++);
            return pictures;
        }

        public void setPictures(Set<Picture> pictures) {
            this.pictures = pictures;
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
