package com.info.web.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.info.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Base64Utils {
	private static final Logger log =LoggerFactory.getLogger(Base64Utils.class);

	/**
	 * 
	 * 创建日期2014-4-24上午10:12:38 修改日期 作者： TODO 使用Base64加密算法加密字符串 return
	 */
	public static String encodeStr(String plainText) {
		try {
			return new String(Base64.encodeBase64(
					plainText.getBytes(Constant.UTF8), true));
		} catch (UnsupportedEncodingException e) {
			log.error("encodeStr error:{}",e);
		}
		return "";
	}

	/**
	 * 
	 * 创建日期2014-4-24上午10:15:11 修改日期 作者： TODO 使用Base64加密 return
	 */
	public static String decodeStr(String encodeStr) {
		try {
			return new String(Base64.decodeBase64(encodeStr
					.getBytes(Constant.UTF8)));
		} catch (UnsupportedEncodingException e) {
			log.error("decodeStr error:{}",e);
		}
		return "";
	}
//	public static void main(String[] args) {
//
//		System.out.println(Base64Utils.encodeStr("390"));
//	}
}
