package com.info.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.SmsConfigConstant;
import com.info.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信
 * @author gaoyuhai
 * 2016-6-17 下午03:50:44
 */
@Slf4j
public class SendSmsUtil {

	private static final Logger loger = LoggerFactory.getLogger(SendSmsUtil.class);
	private static String apiUrl = SmsConfigConstant.getConstant("apiurl");

	private static String account = SmsConfigConstant.getConstant("account");
	private static String pswd = SmsConfigConstant.getConstant("pswd");
	private static String sign = SmsConfigConstant.getConstant("sign");
	private static String templateld = SmsConfigConstant.getConstant("templateld");


	public static boolean sendSmsCL(String telephone, String sms){
		loger.info("sendSms:" + telephone + "   sms=" + sms);

		try{
			// String msg = Constant.SMS_VERIFY_CONTENT.replace("#cont#", sms);

			//做URLEncoder - UTF-8编码
			String sm = URLEncoder.encode(sms, "utf8");
			//将参数进行封装
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("accesskey", account);
			paramMap.put("secret", pswd);
			paramMap.put("sign", sign);
			paramMap.put("templateId", templateld);

			//单一内容时群发  将手机号用;隔开
			paramMap.put("mobile", telephone);
			paramMap.put("content", sm);
			String result = HttpUtil.sendPost(apiUrl,paramMap);
			System.out.println("result="+result);
			JSONObject jsonObject = JSON.parseObject(result);
			return jsonObject!=null && jsonObject.getInteger("code") == 0;
		}catch (Exception e){
			loger.error("sendSmsCL error:{} ",e);
			return false;
		}
	}


	/**
	 * 创蓝 自定义短信内容
	 *
	 * @param telephone 手机号
	 * @param content   内容
	 * @return boolean b
	 * @throws Exception ex
	 */
	public static boolean sendSmsDiyCL(String telephone, String content) throws Exception{
		loger.info("sendSms:" + telephone + "   sms=" + content);

//        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, content, telephone);
//        String requestJson = JSON.toJSONString(smsSingleRequest);
//        loger.info("before request string is: " + requestJson);
		String sm = URLEncoder.encode(content, "utf8");
		//将参数进行封装
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("un", account);
		paramMap.put("pw", pswd);

		//单一内容时群发  将手机号用;隔开
		paramMap.put("da", telephone);
		paramMap.put("sm", sm);
		String result = HttpUtil.sendPost(apiUrl,paramMap);
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject!=null && jsonObject.getBoolean("success");
//        IZz253ApiService service = CloseableOkHttp.obtainRemoteService(
//                "http://zapi.253.com/", IZz253ApiService.class
//        );
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJson);
//        Call<JSONObject> call = service.msgSendJSON(body);
//        try {
//            Response<JSONObject> response = call.execute();
//            if (response.isSuccessful()) {
//                JSONObject jsonObject = response.body();
//                if (jsonObject != null && Constant.SMS_SEND_SUCC.equals(jsonObject.get("code"))) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
	}
//
//	public static void main(String[] args) {
//		sendSmsDiyCL("13061966998","测试");
//	}
}
