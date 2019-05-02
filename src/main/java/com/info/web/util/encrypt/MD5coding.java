package com.info.web.util.encrypt;

import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5
 * 
 * @author gyh
 * 
 */
public class MD5coding {
	private static final Logger logger = LoggerFactory.getLogger(MD5coding.class);
	public static MD5coding md5coding;

	public String code(String str) {
		String hs = "";
		try {
			MessageDigest alga;
			String myinfo = str;
			alga = MessageDigest.getInstance("MD5");
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();
			String stmp = "";
			for (int n = 0; n < digesta.length; n++) {
				stmp = (Integer.toHexString(digesta[n] & 0XFF));
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
		} catch (Exception e) {
			logger.error("code error:{}", e);
		}
		return hs.toUpperCase();
	}


	public static MD5coding getInstance() {
		if (md5coding == null) {
			md5coding = new MD5coding();
		}
		return md5coding;
	}

	/**
	 * md5,32位小写
	 */
	public static String MD5(String inStr) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("utf-8");

			byte[] md5Bytes = md5.digest(byteArray);

			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} catch (Exception e) {
			logger.error("MD5 error:{}",e);
			return "";
		}
	}

//	public static void main(String[] args) throws ParseException,
//			NoSuchAlgorithmException {
//		System.out.println(new MD5coding().encryptImportMsg(
//				"1109059780@qq.com", 5, "begin"));
//		System.out.println(new MD5coding().encryptImportMsg(
//				"1109059780@qq.com", 5, "end"));
//		Date date = new Date();
//		String passMD5 = "";
//		passMD5 = new MD5coding().code("1");
//		System.out.println(passMD5);
//		System.out.println(passMD5.substring(8, passMD5.length() - 8));
//		System.out.println(passMD5.substring(16, passMD5.length() - 8));
//		AESUtil aesEncrypt = new AESUtil();
//
//		System.out.println(MD5coding.MD5(aesEncrypt.encrypt("123456", "")));
////		passMD5 = new MD5coding().code("03d38ba763609474dfc838b0effc6f12");
////		System.out.println("tql的加密密码："+passMD5.toLowerCase().length());
////		System.out.println("621c76126e4940e8d7de8b5cce65bf7c".length());
//	}
}
