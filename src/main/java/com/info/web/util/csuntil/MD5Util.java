package com.info.web.util.csuntil;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class MD5Util {
	private static final String  SECRETKEY = "CS";
	// MD5  32λ Сд
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("utf-8");

			byte[] md5Bytes = md5.digest(byteArray);

			StringBuilder hexValue = new StringBuilder();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} catch (Exception e) {
			log.error("MD5 error:{}",e);
			return "";
		}
	}

	public static String encrypt(Integer orderId){
		return MD5(orderId+SECRETKEY);
	}
}
