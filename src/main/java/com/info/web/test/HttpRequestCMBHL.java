package com.info.web.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.info.back.utils.ServiceResult;
import com.info.web.pojo.BorrowOrder;
import com.info.web.util.DateUtil;
import com.info.web.util.GenerateNo;

/**
 * 招行 互联付款 目前请求链接是数据库配置，其它配置信息写死的
 * 
 */
@Slf4j
public class HttpRequestCMBHL {

	public static final String CCYNBR = "10";// 币种

	// public static final String TRSTYP = "C210";// 业务类型编码？？
	// public static final String TRSCAT = "09001";// 业务种类编码？？
	// public static final String BUSCOD = "N31010";// 业务类型
	// public static final String BUSMOD = "00003";// 业务模式

	// public static final String BBKNBR = "CB";// 银行号
	// public static final String go_url = "http://180.173.0.188:9090";// 请求地址

	// public static final String BBKNBR = "CB";// 银行号
	// public static final String userName = PropertiesUtil.get("userName_HL");
	// public static final String ACCNBR = PropertiesUtil.get("ACCNBR_HL");//
	// 银行账号
	// public static final String CNVNBR = PropertiesUtil.get("CNVNBR_HL");// //
	// 协议号
	// public static final String go_url = PropertiesUtil.get("go_url_HL");//
	// 请求地址

	/**
	 * 生成请求报文正式 付款NTIBCOPR
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestNTIBCOPRStr(List<BorrowOrder> borrowOrders, HashMap<String, String> params) {

		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket("NTIBCOPR", params.get("HL_LGNNAM"));
		Map mpPodInfo = new Properties();
		mpPodInfo.put("BUSMOD", params.get("HL_BUSMOD"));// 跨行付款？
		xmlPkt.putProperty("NTOPRMODX", mpPodInfo);
		List<Map> xxx = new ArrayList<Map>();
		for (BorrowOrder bo : borrowOrders) {
			String yurref = bo.getYurref();
			String remark = "小鱼儿放款";
			if (yurref.startsWith("A")) {
				remark = "小鱼儿放款";
			} else if (yurref.startsWith("B")) {
				remark = "信审服务费";

			} else if (yurref.startsWith("C")) {
				remark = "信审服务费";
			}
			Map mpPayInfo = new Properties();
			if (bo.getSerialNo() != null) {
				mpPayInfo.put("SQRNBR", bo.getSerialNo());// 流水GenerateNo.generateShortUuid(10)
			} else {
				mpPayInfo.put("SQRNBR", GenerateNo.generateShortUuid(10));// 流水
			}

			mpPayInfo.put("BBKNBR", "CB");// 银行号
			mpPayInfo.put("ACCNBR", params.get("HL_ACCNBR"));// 银行账号

			mpPayInfo.put("CNVNBR", params.get("HL_CNVNBR"));// 协议号？？？
			mpPayInfo.put("YURREF", yurref);// 业务参考号？？？？ 订单号

			mpPayInfo.put("CCYNBR", CCYNBR);// 币种
			//mpPayInfo.put("TRSAMT", "0.1");
			// 打款金额精确到分
			if (yurref != null && yurref.startsWith("H")) {
				mpPayInfo.put("TRSAMT", String.valueOf(bo.getIntoMoney() / 100.00));// 金额//bo.getIntoMoney()/100
			} else {
 		    mpPayInfo.put("TRSAMT", String.valueOf(bo.getIntoMoney() / 100.00));// 金额//bo.getIntoMoney()/100
				 

			}

			mpPayInfo.put("CRTSQN", "");// 收方编号
			mpPayInfo.put("NTFCH1", bo.getUserPhone());// 通知方式一
			mpPayInfo.put("NTFCH2", "");// 通知方式二

			mpPayInfo.put("CDTNAM", bo.getRealname());// 收款人户名
			mpPayInfo.put("CDTEAC", bo.getCardNo());// 收款人账号
			mpPayInfo.put("CDTBRD", bo.getBankNumber());// 收款行行号

			mpPayInfo.put("TRSTYP", params.get("HL_TRSTYP"));// 业务类型编码？？
			mpPayInfo.put("TRSCAT", params.get("HL_TRSCAT"));// 业务种类编码？？
			mpPayInfo.put("RMKTXT", remark);// 附言
			// mpPayInfo.put("RSV30Z", String.valueOf(bo.getId()));// 借款订单id

			xmlPkt.putProperty("NTIBCOPRX", mpPayInfo);
		}

		return xmlPkt.toXmlString();
	}


	/**
	 * 生成请求报文正式 交易查询NTQRYEBP
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestNTQRYEBPStr(HashMap<String, String> params) {

		Date nowDate = new Date();
		String beginDate = DateUtil.fyFormatDate(nowDate);
		String endDate = DateUtil.fyFormatDate(DateUtil.addDay(nowDate, 1));
		int nowHour = nowDate.getHours();
		int monute = nowDate.getMinutes();
		if ((nowHour == 0 || nowHour == 23 || nowHour == 12) && monute < 20) {
			beginDate = DateUtil.fyFormatDate(DateUtil.addDay(nowDate, -1));
		}

		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket("NTQRYEBP", params.get("HL_LGNNAM"));
		Map mpPayInfo = new Properties();

		mpPayInfo.put("BUSCOD", params.get("HL_BUSCOD"));// 业务类型
		mpPayInfo.put("BGNDAT", beginDate);// 开始时间
		mpPayInfo.put("ENDDAT", endDate);// 结束时间
		xmlPkt.putProperty("NTWAUEBPY", mpPayInfo);
		return xmlPkt.toXmlString();
	}

	/*
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws MalformedURLException
	 */
	public static String sendRequest(String data, String goUrl) {
		String result = "";
		try {
			URL url;
			url = new URL(goUrl);

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("contentType", "gbk");
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setReadTimeout(120000);
			conn.setConnectTimeout(120000);
			OutputStream os;
			os = conn.getOutputStream();
			os.write(data.toString().getBytes("gbk"));
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gbk"));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}

			// System.out.println("====" + result);
			br.close();
		} catch (Exception e) {
			log.error("sendRequest error:{}",e);
			result = "NetworkError CMBHL 互联uk网络连接异常:" + goUrl;
		} finally {
			return result;
		}

	}

	/**
	 * 处理代付响应结果
	 */
	public static ServiceResult processPayResult(String result) {

		ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "processPayResult 未知异常，请稍后重试！");
		try {

			if (result != null && result.length() > 0) {
				//将响应结果转换为json字符串
				String jsonstring = HttpRequestCMBDF.xml2JSON(result);
				//将响应结果转换为json对象
				JSONObject jsonObj = JSONObject.fromObject(jsonstring);

				JSONObject INFO = jsonObj.getJSONObject("INFO");//返回信息

				String RETCOD = INFO.getString("RETCOD");//返回代码

				if (RETCOD.equals("0")) {
					serviceResult.setCode("5000");
					serviceResult.setMsg("有订单提交成功（部分或者所有）请等待返回状态！");
					if (jsonObj.containsKey("NTOPRRTNZ")) {
						Object objj = jsonObj.get("NTOPRRTNZ");
						JSONArray arrays = null;
						if (objj instanceof JSONObject) {
							arrays = new JSONArray();
							arrays.add(objj);
						} else if (objj instanceof JSONArray) {
							arrays = jsonObj.getJSONArray("NTOPRRTNZ");
						}
						List<ServiceResult> resultS = new ArrayList<ServiceResult>();
						for (int i = 0; i < arrays.size(); i++) {
							ServiceResult st = new ServiceResult();
							String reCode;
							String reMsg;
							JSONObject propPayResult = arrays.getJSONObject(i);
							String eERRCOD = propPayResult.getString("ERRCOD");
							String eSQRNBR = propPayResult.getString("SQRNBR");

							String sREQSTS = "";
							String remark = "";
							if (propPayResult.containsKey("REQSTS")) {
								sREQSTS = propPayResult.getString("REQSTS");
							}
							if (propPayResult.containsKey("ERRTXT")) {
								remark = propPayResult.getString("ERRTXT");
							}

							if (eERRCOD.equals("SUC0000")) {
								reCode = BorrowOrder.SUB_SUBMIT;
								reMsg = "支付已被银行受理,返回状态*：" + sREQSTS;
							} else {
								reCode = BorrowOrder.SUB_PAY_FAIL;
								reMsg = "错误码*：" + eERRCOD + ";备注*：" + remark;
							}
							st.setCode(reCode);
							st.setMsg(reMsg+remark);
							st.setExt(eSQRNBR);
							resultS.add(st);
						}
						serviceResult.setExt(resultS);
					}
				} else if (RETCOD.equals("-9")) {
					serviceResult.setCode(BorrowOrder.SUB_SUBMIT);
					serviceResult.setMsg("支付未知异常，请查询支付结果确认支付状态，错误信息：" + INFO.getString("ERRMSG"));

				} else {
					serviceResult.setCode(BorrowOrder.SUB_SUB_FAIL);
					serviceResult.setMsg("支付失败：" + INFO.getString("ERRMSG"));
					// System.out.println(serviceResult.getCode() + ">>" +
					// serviceResult.getMsg());
				}

			}

		} catch (Exception e) {
			log.error("processPayResult error:{}",e);
		}
		return serviceResult;
	}

}