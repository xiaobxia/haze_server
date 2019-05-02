package com.info.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 获取请求特征
 * 
 * @author yangguangliang
 * @date 20170323
 */
public class FastJsonUtils {
	 
	 private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue,// 输出空置字段
	         SerializerFeature.WriteNullListAsEmpty,// list字段如果为null，输出为[]，而不是null
	         SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
	         SerializerFeature.WriteNullBooleanAsFalse,// Boolean字段如果为null，输出为false，而不是null
	         SerializerFeature.WriteNullStringAsEmpty,
	         SerializerFeature.DisableCircularReferenceDetect
	 };
	 
	public static Object toJson(Object object){
		return  FastJsonUtils.paser(object);
	}

	private static Object paser(Object object) {
		ValueFilter filter = new ValueFilter() {

			@Override
			public Object process(Object object, String name, Object value) {
				Field field = ReflectionUtils
						.findField(object.getClass(), name);

				if (field == null) {
					String method = name.replaceFirst(name.substring(0, 1),
							name.substring(0, 1).toUpperCase());
					Method m = ReflectionUtils.findMethod(object.getClass(),
							"get" + method);
					if (object != null && m != null
							&& m.getGenericReturnType().equals(Date.class)) {
						if (value == null) {
							return "";
						}
					}
				}

				if (object != null && field != null
						&& field.getGenericType() != null
						&& field.getGenericType().toString().equals("T")) {
					if (value == null) {
						return new HashMap<String, Object>();
					}
				}
				if (object != null && field != null
						&& field.getType().equals(Date.class)) {
					if (value == null) {
						return "";
					}
				}
				if (object != null && field != null
						&& field.getType().equals(List.class)) {
					if (value == null) {
						return new ArrayList<Object>();
					}
				}
				if (object != null && field != null
						&& field.getType().equals(Map.class)) {
					if (value == null) {
						return new HashMap<Object, Object>();
					}
				}
				return value;

			}
		};
		List<SerializeFilter> filters = new ArrayList<SerializeFilter>();

		filters.add(filter);
		return toJson(object, filters, features);
	}


	private static Object toJson(Object object, List<SerializeFilter> filters,
			SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();

		try {
			JSONSerializer serializer = new JSONSerializer(out);
			for (SerializerFeature feature : features) {
				serializer.config(feature, true);
			}

			serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
			for (SerializeFilter filter : filters) {
				if (filter != null) {
					if (filter instanceof PropertyPreFilter) {
						serializer.getPropertyPreFilters().add(
								(PropertyPreFilter) filter);
					}

					if (filter instanceof NameFilter) {
						serializer.getNameFilters().add((NameFilter) filter);
					}

					if (filter instanceof ValueFilter) {
						serializer.getValueFilters().add((ValueFilter) filter);
					}

					if (filter instanceof PropertyFilter) {
						serializer.getPropertyFilters().add(
								(PropertyFilter) filter);
					}

					if (filter instanceof BeforeFilter) {
						serializer.getBeforeFilters()
								.add((BeforeFilter) filter);
					}

					if (filter instanceof AfterFilter) {
						serializer.getAfterFilters().add((AfterFilter) filter);
					}
				}
			}
			serializer.write(object);

			return out.toString();
		} finally {
			out.close();
		}
	}


}
