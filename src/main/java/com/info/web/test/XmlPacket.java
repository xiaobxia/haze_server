package com.info.web.test;


import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * 招行XML通讯报文类
 * 
 * @author 徐蓓
 *
 */
@Slf4j
public class XmlPacket{
	protected String FUNNAM;
	protected final String DATTYP="2";//报文类型固定为2
	protected String LGNNAM;
	protected String RETCOD;
	protected String ERRMSG;
	protected Map data; //<String,Vector>
	
	public XmlPacket(){
		data = new Properties();
	}
	
	public XmlPacket(String sFUNNAM){
		FUNNAM = sFUNNAM;
		data = new Properties();
	}
	
	public XmlPacket(String sFUNNAM, String sLGNNAM){
		FUNNAM = sFUNNAM;
		LGNNAM = sLGNNAM;
		data = new Properties();
	}
	
	public String getFUNNAM() {
		return FUNNAM;
	}
	public void setFUNNAM(String fUNNAM) {
		FUNNAM = fUNNAM;
	}
	public String getLGNNAM() {
		return LGNNAM;
	}
	public void setLGNNAM(String lGNNAM) {
		LGNNAM = lGNNAM;
	}
	public String getRETCOD() {
		return RETCOD;
	}
	public void setRETCOD(String rETCOD) {
		RETCOD = rETCOD;
	}
	public String getERRMSG() {
		return ERRMSG;
	}
	public void setERRMSG(String eRRMSG) {
		ERRMSG = eRRMSG;
	}
	
	/**
	 * XML报文返回头中内容是否表示成功
	 */
	public boolean isError(){
		return RETCOD.equals("0");
	}
	
	/**
	 * 插入数据记录
	 * @param mpData <String, String>
	 */
	@SuppressWarnings("unchecked")
	public void putProperty(String sSectionName, Map mpData){
		if(data.containsKey(sSectionName)){
			Vector vt = (Vector)data.get(sSectionName);
			vt.add(mpData);
		}else{
			Vector vt = new Vector();
			vt.add(mpData);
			data.put(sSectionName, vt);	
		}		
	}
	
	/**
	 * 把报文转换成XML字符串
	 * @return
	 */
	public String toXmlString(){
		StringBuffer sfData = new StringBuffer(
				"<?xml version='1.0' encoding = 'GBK'?>");
		sfData.append("<CMBSDKPGK>");
		sfData
				.append("<INFO><FUNNAM>"+FUNNAM+"</FUNNAM><DATTYP>"+DATTYP+"</DATTYP><LGNNAM>"+LGNNAM+"</LGNNAM></INFO>");
		int secSize = data.size();
		Iterator itr = data.keySet().iterator();
		while(itr.hasNext()){
			String secName = (String)itr.next();
			Vector vt = (Vector)data.get(secName);
			for(int i=0; i<vt.size(); i++){
				Map record = (Map)vt.get(i);
				Iterator itr2 = record.keySet().iterator();
				sfData.append("<"+secName+">");
				while(itr2.hasNext()){
					String datakey = (String)itr2.next();
					String dataValue = (String)record.get(datakey);
					sfData.append("<"+datakey+">");
					sfData.append(dataValue);
					sfData.append("</"+datakey+">");
				}
				sfData.append("</"+secName+">");
			}
		}
		sfData.append("</CMBSDKPGK>");		
		return sfData.toString();
	}
	
	/**
	 * 解析xml字符串，并转换为报文对象
	 */
	public static XmlPacket valueOf(String message) {
		SAXParserFactory saxfac = SAXParserFactory.newInstance();
		try {
			SAXParser saxparser = saxfac.newSAXParser();
			ByteArrayInputStream is = new ByteArrayInputStream(message.getBytes());
			XmlPacket xmlPkt= new XmlPacket();
			saxparser.parse(is, new SaxHandler(xmlPkt));
			is.close();
			return xmlPkt;
		} catch (Exception e) {
			log.error("valueOf error:{}",e);
		}	
		return null;
	}
}