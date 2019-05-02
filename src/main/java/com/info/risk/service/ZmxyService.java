package com.info.risk.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.OrderNoUtil;

/**
 *
 * 类描述：芝麻信用相关接口 <br>
 * 每次发出请求之前插入订单信息，返回后更新订单信息<br>
 * 此类不做任何业务处理，仅拼接参数请求第三方，必须使用trycath，并且不向上抛出异常以保证插入或更新的订单不会回滚<br>
 * 创建人：wison<br>
 * 创建时间：2017-10-10 上午11:08:28 <br>
 * 
 *
 */
@Slf4j
@Service
public class ZmxyService implements IZmxyService {
	@Autowired
	private IRiskOrdersService riskOrdersService;
	

	private String BASE_URL="/files/zm_feedback/";
	private String FILE_DEPARTURE="/";

	private static final Integer timeOut = 10000;

	@Override
	public ServiceResult getOpenId(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			String userName = (String) params.get("userName");//用户真实姓名
			String cardNum = (String) params.get("cardNum");//用户身份证号
			Object userId = params.get("userId");//用户ID
			if (userName != null && cardNum != null && userId != null) {
				Map<String, String> keys = SysCacheUtils
						.getConfigParams(BackConfigParams.ZMXY);
				//TODO wison 白骑士-芝麻信用查询接口相关配置
				String zmUrl = "https://api.baiqishi.com/credit/zhima/search";

				JSONObject req = new JSONObject();
				req.put("partnerId", "luanniao"); //luanniao
				req.put("verifyKey", "e8561377a71b40d0bf64c932b1c13d94"); //e8561377a71b40d0bf64c932b1c13d94
				req.put("linkedMerchantId", "2088821306257161"); //2088821306257161
				req.put("productId","102002");

				JSONObject extParam = new JSONObject();
				extParam.put("identityType","2");
				extParam.put("name",userName);
				extParam.put("certNo",cardNum);

				req.put("extParam", extParam);

				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZMXY);
				orders.setAct(ConstantRisk.GET_OPENID);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams(req.toJSONString());
				log.info("send to get request=:{}",req.toJSONString());
				riskOrdersService.insert(orders);
				String resp = sendPost(false, req.toJSONString(), zmUrl);
				log.info("get openId response=:{}",resp);

				JSONObject respJson = JSONObject.parseObject(resp);
				JSONObject respBody = JSONObject.parseObject(respJson.getString("resultData"));
				Boolean isSuccess = respBody.getBoolean("success");
				if(isSuccess){
					Boolean isAuthorized = respBody.getBoolean("authorized");
					if(isAuthorized){//已授权
						serviceResult = new ServiceResult(
								ServiceResult.SUCCESS, respBody.getString("openId"));
						orders.setStatus(RiskOrders.STATUS_SUC);
					}else{//未授权
						serviceResult = new ServiceResult("100",
								"用户未授权，请发起授权请求！");
					}

				}else{
					serviceResult = new ServiceResult("300",respBody.getString("errorMessage"));
				}

				orders.setReturnParams(req.toJSONString());
				riskOrdersService.update(orders);
			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getOpenId error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}


	@Override
	public ServiceResult getZmScore(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");

		try {
			Object userId = params.get("userId");
			Object openId = params.get("openId");
			if (userId != null && openId != null) {
				Map<String, String> keys = SysCacheUtils
						.getConfigParams(BackConfigParams.ZMXY);
				//TODO wison 白骑士-芝麻信用评分查询接口相关配置
				String zmUrl = "https://api.baiqishi.com/credit/zhima/search";

				JSONObject req = new JSONObject();
				req.put("partnerId", "luanniao");
				req.put("verifyKey", "e8561377a71b40d0bf64c932b1c13d94");
				req.put("linkedMerchantId", "2088821306257161");
				req.put("productId","102004");

				JSONObject extParam = new JSONObject();
				extParam.put("openId",openId);
				req.put("extParam",extParam);

				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZMXY);
				orders.setAct(ConstantRisk.GET_SCORE);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams(req.toJSONString());
				log.info("getZmScore request =:{} " ,req.toJSONString());
				riskOrdersService.insert(orders);
				String resp = sendPost(false, req.toJSONString(),zmUrl);
				log.info("getZmScore response =:{}",resp);

				JSONObject respJson = JSONObject.parseObject(resp);
				JSONObject respBody = JSONObject.parseObject(respJson.getString("resultData"));
				Boolean isSuccess = respBody.getBoolean("success");

				if (isSuccess) {
					serviceResult = new ServiceResult(ServiceResult.SUCCESS,
							respBody.getString("zmScore"));
					orders.setStatus(RiskOrders.STATUS_SUC);
				} else {
					serviceResult = new ServiceResult("300", respBody
							.getString("errorMessage"));
				}
				orders.setReturnParams(resp);
				riskOrdersService.update(orders);
			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getZmScore error ,params=:{}error:{}",params, e);
		}


		return serviceResult;
	}

	@Override
	public ServiceResult getCreditWatchList(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userId = params.get("userId");
			Object openId = params.get("openId");
			if (userId != null && openId != null) {
				Map<String, String> keys = SysCacheUtils
						.getConfigParams(BackConfigParams.ZMXY);
				//TODO wison 白骑士-芝麻信用行业关注名单查询接口相关配置
				String zmUrl = "https://api.baiqishi.com/credit/zhima/search";

				JSONObject req = new JSONObject();
				req.put("partnerId", "luanniao");
				req.put("verifyKey", "e8561377a71b40d0bf64c932b1c13d94");
				req.put("linkedMerchantId", "2088821306257161");
				req.put("productId","102006");

				JSONObject extParam = new JSONObject();
				extParam.put("openId",openId);
				req.put("extParam",extParam);

				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZMXY);
				orders.setAct(ConstantRisk.GET_INDUSTY);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams(req.toJSONString());
				log.info("getCreditWatchList request =:{} " ,req.toJSONString());
				riskOrdersService.insert(orders);
				String resp = sendPost(false,req.toJSONString(),zmUrl);
				log.info("getCreditWatchList response =:{}",resp);

				JSONObject respJson = JSONObject.parseObject(resp);
				JSONObject respBody = JSONObject.parseObject(respJson.getString("resultData"));
				Boolean isSuccess = respBody.getBoolean("success");

				if (isSuccess) {
					serviceResult = new ServiceResult(ServiceResult.SUCCESS,
							respBody.toJSONString());
					orders.setStatus(RiskOrders.STATUS_SUC);
				} else {
					serviceResult = new ServiceResult("300", respBody
							.getString("errorMessage"));
				}
				orders.setReturnParams(resp);
				riskOrdersService.update(orders);
			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getCreditWatchList error ,params=:{}error:{}",params, e);
		}


		return serviceResult;
	}

	public static String sendPost(boolean isSsl, String param, String url) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		URL targetUrl = null;
		StringBuffer result = new StringBuffer();

		try {
			if (isSsl) {
				// 信任所有证书
				targetUrl = new URL(url);
				//   SslUtils.ignoreSsl();
			} else {
				targetUrl = new URL(url);
			}

			// 打开和URL之间的连接
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			// 设置超时
			httpConnection.setConnectTimeout(timeOut);
			httpConnection.setReadTimeout(timeOut);
			// 设置通用的请求属性
			httpConnection.setRequestProperty("connection", "Keep-Alive");
			httpConnection.setRequestProperty("Charset", "UTF-8");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);

			out = new PrintWriter(httpConnection.getOutputStream());
			out.print(param);
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		}
		//使用finally块来关闭输出流、输入流
		finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					log.error("sendPost error:{}",e);
				}
			}

			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					log.error("sendPost error:{}",e);
				}
			}
		}
		return result.toString();
	}

}
