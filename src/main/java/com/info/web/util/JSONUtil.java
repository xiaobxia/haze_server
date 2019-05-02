package com.info.web.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * JSON工具类
 */
public class JSONUtil {

	/**
	 * json字符串转换成map
	 * @param jsonStr str
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	/**
	 * JSON 对象字符格式转换成java对象
	 * 
	 * @param jsonString json
	 * @param beanClass class
	 * @return t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * java对象转换成json字符串
	 * 
	 * @param bean bean
	 * @return str
	 */
	public static String beanToJson(Object bean) {
		JSONObject json = JSONObject.fromObject(bean);
		return json.toString();
	}

	/**
	 * java对象List集合转换成json字符串
	 * 
	 * @param beans beans
	 * @return str
	 */

	@SuppressWarnings("rawtypes")
	public static String beanListToJson(List beans) {
		StringBuffer rest = new StringBuffer();
		rest.append("[");
		int size = beans.size();
		for (int i = 0; i < size; i++) {
			rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));
		}
		rest.append("]");
		return rest.toString();
	}

	/**
	 * String 转List<T>
	 * 
	 * @param <T> t
	 * @param jsonArray jsonArray
	 * @param beanClsss class
	 * @return t
	 */
	public static <T> List<T> jsonArrayToBean(String jsonArray,
			Class<T> beanClsss) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArray);
		List<T> beanList = new ArrayList<T>();
		for (int i = 0; i < jsonArr.size(); i++) {
			T bean = jsonToBean(String.valueOf(jsonArr.get(i)), beanClsss);
			beanList.add(bean);
		}
		return beanList;
	}
}
