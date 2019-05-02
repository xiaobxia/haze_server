package com.info.back.utils;

import java.util.ResourceBundle;
/**
 * 读取配置文件
 * @author gaoyuhai
 *
 */
public class SmsConfigConstant {

	public static ResourceBundle resourceBundle;

	public static void initConfig(){
		resourceBundle = ResourceBundle.getBundle("sms");
	}

	public static String getConstant(String key){
		if (resourceBundle == null) initConfig();
		String value = "";
		try {
			value = new String(resourceBundle.getString(key).getBytes("ISO-8859-1"),"UTF-8");
		}catch (Exception UnsupportedEncodingException){

		}
		return value;

	}
	public static String getConstant(String key,String defaultValue){
		String value=null;
		try{
			if(resourceBundle==null) initConfig();
			value = new String(resourceBundle.getString(key).getBytes("ISO-8859-1"),"UTF-8");
		}catch(Exception e){
			return defaultValue;
		}
		return value;
	}


}
