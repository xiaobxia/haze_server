package com.info.web.test;

 

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import com.mysql.jdbc.Constants;
 

/**
 * xml转换类
 * 
 * @author Liutq
 * 
 */
@Slf4j
public class XmlConvertUtil {
	

//
//	public static void main(String[] args) throws Exception {
//	/*	Map m = new HashMap();
//		m.put("aaa", "叫他");
//		m.put("bbb", "嗯嗯嗯额");
//
//		List l = new ArrayList();
//		l.add(new Tran());
//		l.add(new Tran());
//		Map m2 = new HashMap();
//		m2.put("sub1", "ssssss");
//		m2.put("sub2", "交通银行");
//		m2.put("tranList", m);
//		// String s = list2Xml(l);
//		String s = map2Xml(m2, "root").replaceAll("\r\n", "").replaceAll(" ", "");
//
//	//	Map ret = Dom2Map(string2Doc(s));
//		System.out.println(s);
//	//	System.out.println(ret);
//*/	}

	public static String map2Xml(Map map, String rootName) {
		Document doc = new Document();
		Element root = new Element(rootName);
		convert2Xml(map, root);
		doc.addContent(root);
		return doc2String(doc);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void convert2Xml(Map map, Element parent) {
		Set<Entry> entrySet = map.entrySet();
		for (Entry entry : entrySet) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();

			Element node = new Element(key); // 当前节点
			if (value instanceof String) {

				node.setText(value.toString());
			} else if (value instanceof Map) {
				convert2Xml((Map) value, node);

			} else if (value instanceof List) {
				convert2Xml((List) value, node);
				
			}else if(value instanceof BaseEntity){
				convert2Xml((BaseEntity) value, node);
			}
			parent.addContent(node);
		}
	}
	public static void convert2Xml(BaseEntity entity, Element parent) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
		String classname = entity.getClass().getSimpleName();
		Field[] fields = entity.getClass().getDeclaredFields();
		
		Element node = new Element(classname.toLowerCase());
		for(Field field : fields){
			try {
				String fieldName = field.getName();
				Object fieldVal =  beanWrapper.getPropertyValue(fieldName);
				
				Element fieldNode = new Element(fieldName);
				fieldNode.setText(fieldVal==null?"":fieldVal.toString());
				node.addContent(fieldNode);
			} catch (BeansException e) {
				log.error("build field xml error:{}",e);
			}
		}
		parent.addContent(node);
	}
	@SuppressWarnings("rawtypes")
	public static void convert2Xml(List list, Element parent) {

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof Map) {
				convert2Xml((Map) obj, parent);
			}else if(obj instanceof BaseEntity){
				convert2Xml((BaseEntity) obj, parent);
			}
		}
	}

	/**
	 * 字符串转换为DOCUMENT
	 * 
	 * @param xmlStr
	 *            字符串
	 * @return doc JDOM的Document
	 */
	public static Document string2Doc(String xmlStr) {
		java.io.Reader in = new StringReader(xmlStr);
		Document doc = null;
		try {
			doc = (new SAXBuilder()).build(in);
		} catch (Exception e) {
			log.error("string2Doc error:{}",e);
		}
		return doc;
	}

	/**
	 * Document转换为字符串
	 * 
	 *            XML文件路径
	 * @return xmlStr 字符串
	 */
	public static String doc2String(Document doc) {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			xmlout.output(doc, bo);
		} catch (IOException e) {
			log.error("doc2String error:{}",e);
		}
//		return bo.toString();
		try {
			return new String(bo.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("doc2String error:{}",e);
			return null;
		}
	}

	/**
	 * xml转map
	 */
	public static Map<String, Object> xml2Map(String xml) {
		return Dom2Map(string2Doc(xml));
	}
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.getChildren().iterator(); iterator
				.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List list = e.getChildren();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.getChildren();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				List mapList = new ArrayList();

				if (ele.getChildren().size() > 0) {
					Map m = Dom2Map(ele);
					if (map.get(ele.getName()) != null) {
						Object obj = map.get(ele.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(ele.getName(), mapList);
					} else
						map.put(ele.getName(), m);
				} else {
					if (map.get(ele.getName()) != null) {
						Object obj = map.get(ele.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(ele.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(ele.getText());
						}
						map.put(ele.getName(), mapList);
					} else
						map.put(ele.getName(), ele.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
}