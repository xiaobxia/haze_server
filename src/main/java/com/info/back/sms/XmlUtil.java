package com.info.back.sms;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlUtil {

	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);
	/**
	 * 解析消息内容
	 */
	public static Object readMessage(Class objClass,String msg){
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] {objClass});
			Unmarshaller u = jc.createUnmarshaller();
			return objClass.cast(u.unmarshal(new StringReader(msg)));
		} catch (JAXBException e) {
			log.error("解析消息出错",e);
		}
		return null;
	}
	/**
	 * 生成内容
	 */
	public static String bulidMessage(Object mesgBean)
	  {
	    Marshaller m = null;
	    try {
	      JAXBContext jc = JAXBContext.newInstance(new Class[] { mesgBean.getClass() });
	      m = jc.createMarshaller();
	      m.setProperty("jaxb.encoding", "UTF-8");
	      m.setProperty("jaxb.formatted.output",true);

	      m.setProperty("jaxb.fragment",true);
	      //不进行转义字符的处理,fyc2017-03-24注释,认为后台系统不会发送特殊字符，所以转义不会有问题（因为没有需要转义的字符串），mq-alljar包存在时，此代码报错
//	      m.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler", new CharacterEscapeHandler()
//	      {
//	        public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer)
//	          throws IOException
//	        {
//	          writer.write(ch, start, length);
//	        }
//	      });
	      StringWriter ws = new StringWriter();
	      m.marshal(mesgBean, ws);
	      return ws.toString();
	    } catch (JAXBException e) {
	      log.error("生成消息出现错误: [{}]", e);
	    }return null;
	  }
	
}