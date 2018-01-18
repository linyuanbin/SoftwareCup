package hibernateutil;

import java.util.regex.Pattern;

public class ValidatorUserNameUtil {
	  //ƥ���ǲ����û���
	 // public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
	  //ƥ���ǲ��ǵ绰����
	  public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	  //ƥ���ǲ���email
	  public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	 //ƥ���Ƿ�Ϊ����
	  public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
	  //ƥ���Ƿ�Ϊ΢�ź�
	  public static final String REGEX_WE_CHAT = "^[a-zA-Z]\\w{6,20}$"; 
	  
	  
	  public static boolean isMobile(String mobile) {
	        return Pattern.matches(REGEX_MOBILE, mobile);
	    }
	  public static boolean isEmail(String email) {
	        return Pattern.matches(REGEX_EMAIL, email);
	    }
	  public static boolean isNickName(String nickName){ 
		  return Pattern.matches(REGEX_CHINESE, nickName);
	  }
	  public static boolean isWeChat(String weChat){
		  return Pattern.matches(REGEX_WE_CHAT, weChat);
	  }
	 

 
	}
