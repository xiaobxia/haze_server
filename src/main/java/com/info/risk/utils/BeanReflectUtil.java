package com.info.risk.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * 类描述：封装反射方法，主要利用反射调用对象的get方法 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-12-15 下午01:59:19 <br>
 * 
 * @version
 * 
 */
public class BeanReflectUtil {
	/**
	 * 组装类中某个属性的get方法名称 StringUtil.upperFirst(String str)是自己封装的，将首字母变大写的小方法；
	 * 
	 * @param c
	 *            所反射方法所在的类的Class
	 * @param filedName
	 *            ：字段名称
	 * @return 返回该字段的get方法
	 */
	public static Method assembleGetMethod(Class<?> c, String filedName)
			throws SecurityException, NoSuchMethodException {
		String methodName = "get" + BeanReflectUtil.upperFirst(filedName);// 组装方法名
		Class<?>[] nullClasses = null;// getter方法所需要的参数类型的Class
		return c.getDeclaredMethod(methodName, nullClasses);
	}

	/**
	 * 调用组装类中某个属性的getter方法
	 * 
	 * @param c
	 *            ：所反射方法所在的类的Class
	 * @param filedName
	 *            ：字段名称
	 * @param obj
	 *            ：所反射方法所在的类的实体对象
	 * @return ：getter的返回值
	 */
	public static Object invokeGetMethod(Class<?> c, String filedName,
			Object obj) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method method = assembleGetMethod(c, filedName); // 得到属性的getter
		Object[] nullObjects = null; // 定义方法所要的参数值
		return method.invoke(obj, nullObjects);
	}

	/**
	 * 将首字母转化为大写的小方法
	 * 
	 * @param str
	 *            :需要转化的字符
	 * @return 转化后的结果
	 */
	public static String upperFirst(String str) {
		String first = str.substring(0, 1);
		String last = str.substring(1);
		return first.toUpperCase() + last;
	}
}