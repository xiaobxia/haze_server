package com.info.back.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class SerializeUtil {

	public static byte[] serialize(Object object) {

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			//序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
		}finally{
			if(null!=oos){
				try {
					oos.close();
				} catch (IOException e) {
					log.error("serialize error:{}",e);
				}
			}
			if(null!=oos){
				try {
					oos.close();
				} catch (IOException e) {
					log.error("serialize error:{}",e);
				}
			}
		}
		return null;
	}

	 

	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			//反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			
		}finally{
			if(null!=bais){
				try {
					bais.close();
				} catch (IOException e) {
					log.error("unserialize error:{}",e);
				}
			}
			if(null!=ois){
				try {
					ois.close();
				} catch (IOException e) {
					log.error("unserialize error:{}",e);
				}
			}
		}
		return null;
	}

}