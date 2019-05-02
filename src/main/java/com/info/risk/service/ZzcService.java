package com.info.risk.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.risk.dao.IRiskCreditUserDao;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.OrderNoUtil;

/**
 * 中智诚黑名单接口
 *
 * @author Administrator
 *
 */
@Slf4j
@Service
public class ZzcService implements IZzcService {
	@Autowired
	private IRiskOrdersService riskOrdersService;
	@Autowired
	private IRiskCreditUserDao riskCreditUserDao;

	/**
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 */
	@Override
	public ServiceResult getBlack(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userName = params.get("userName");
			Object cardNum = params.get("cardNum");
			Object userId = params.get("userId");
			Object userPhone = params.get("userPhone");
			if (userName != null && cardNum != null && userId != null && userPhone != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.ZZC);
				String url = map2.get("ZZC_URL");
				String name = map2.get("ZZC_NAME");
				String pwd = map2.get("ZZC_PWD");
				String jsonStrData = "{\"name\":\"" + userName + "\",\"pid\":\"" + cardNum + "\",\"mobile\":\"" + userPhone + "\"}";
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZZC);
				orders.setAct(ConstantRisk.ZZC_BLACK);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url + ",jsonStrData=" + jsonStrData);
				log.info("zzc1 card:{}" ,cardNum);
				riskOrdersService.insert(orders);
				HttpClient c = new HttpClient();
				c.getParams().setContentCharset("utf-8");
				c.getParams().setAuthenticationPreemptive(true);

				Credentials defaultcreds = new UsernamePasswordCredentials(name, pwd);
				c.getState().setCredentials(AuthScope.ANY, defaultcreds);

				PostMethod postMethod = new PostMethod(url);
				// 可根据需要变更查询字段值

				RequestEntity r = new StringRequestEntity(jsonStrData, "application/json", "utf-8");
				postMethod.setRequestEntity(r);
				c.executeMethod(postMethod);

				// 可在此获取返回结果json字符串进行处理
				String result = postMethod.getResponseBodyAsString();
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.containsKey("errors") || jsonObject.containsKey("error")) {
						Object errObject = jsonObject.get("error");
						if (errObject == null) {
							errObject = jsonObject.get("errors");
						}
						serviceResult = new ServiceResult("300", errObject + "");
					} else {
						serviceResult = new ServiceResult(ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("td return null");
					serviceResult = new ServiceResult("100", "中智诚返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getRisk error ,params=:{}error:{}" ,params, e);
		}
		return serviceResult;
	}

	@Override
	public ServiceResult getFqz(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userName = params.get("userName");
			Object cardNum = params.get("cardNum");
			Object userId = params.get("userId");
			Object userPhone = params.get("userPhone");
			if (userName != null && cardNum != null && userId != null && userPhone != null) {
				// 查询用户（user_info）表相关信息供风控使用
				RiskCreditUser riskCreditUser = riskCreditUserDao.findUserDetail(Integer.valueOf(userId + ""));
				Integer marital_status = riskCreditUser.getMaritalStatus();
				String marry = ",";
				if (marital_status.intValue() == 1) {
					marry = ",\"marital_status\":0,";
				} else if (marital_status.intValue() == 2 || marital_status.intValue() == 3) {
					marry = ",\"marital_status\":1,";
				} else if (marital_status.intValue() == 4) {
					marry = ",\"marital_status\":2,";
				}
				// 6666用户信息模块信息校验不准确，此处不敢传入
				String work_name = "";
				String work_phone = "";
				String work_address = "";
				String address = "";
				// String work_name =
				// StringUtils.isNotBlank(riskCreditUser.getCompanyName()) ?
				// riskCreditUser.getCompanyName() : "";
				// String work_phone =
				// StringUtils.isNotBlank(riskCreditUser.getCompanyPhone()) ?
				// riskCreditUser.getCompanyPhone() : "";
				// String work_address =
				// StringUtils.isNotBlank(riskCreditUser.getCompanyAddress()) ?
				// riskCreditUser.getCompanyAddress() : "";
				// String address =
				// StringUtils.isNotBlank(riskCreditUser.getPresentAddress()) ?
				// riskCreditUser.getPresentAddress() : "";
//				String email = StringUtils.isNotBlank(riskCreditUser.getEmail()) ? riskCreditUser.getEmail() : "";
				//邮箱验证非常不完善，避免重复复审，不发此参数
				String email = "";
				String wechat = StringUtils.isNotBlank(riskCreditUser.getWechat()) ? riskCreditUser.getWechat() : "";
				String qq = StringUtils.isNotBlank(riskCreditUser.getQq()) ? riskCreditUser.getQq() : "";
				String applicant = "{\"name\":\"" + userName + "\",\"pid\":\"" + cardNum + "\",\"mobile\":\"" + userPhone + "\",\"work_name\":\""
						+ work_name + "\"" + ",\"work_phone\":\"" + work_phone + "\",\"work_address\":\"" + work_address + "\",\"address\":\""
						+ address + "\"" + marry + "\"email\":\"" + email + "\",\"wechat\":\"" + wechat + "\",\"qq\":\"" + qq + "\"}";
				Integer first = riskCreditUser.getFristContactRelation();
				String firstDesc = "";
				if (first.intValue() == 1) {
					firstDesc = "father";
				} else if (first.intValue() == 2) {
					firstDesc = "mother";
				} else if (first.intValue() == 3 || first.intValue() == 4) {
					firstDesc = "child";
				} else if (first.intValue() == 5) {
					firstDesc = "spouse";
				} else {
					firstDesc = "other_relative";
				}
				Integer second = riskCreditUser.getSecondContactRelation();
				String secondDesc = "";
				if (second.intValue() == 1 || second.intValue() == 4) {
					secondDesc = "friend";
				} else if (second.intValue() == 3) {
					secondDesc = "workmate";
				} else if (second.intValue() == 2) {
					secondDesc = "other_relative";
				} else if (second.intValue() == 5) {
					secondDesc = "other";
				}
				String firstName = riskCreditUser.getFirstContactName();
				if (StringUtils.isNotBlank(firstName) && firstName.length() == 1) {
					firstName += firstName;
				}
				String secondName = riskCreditUser.getSecondContactName();
				if (StringUtils.isNotBlank(secondName) && secondName.length() == 1) {
					secondName += secondName;
				}
				//66666API推广方无法保证传递中文姓名和合法手机号
//				String contacts = "[{\"name\":\"" + firstName + "\",\"phone\":\"" + riskCreditUser.getFirstContactPhone() + "\",\"relationship\":\""
//						+ firstDesc + "\"}" + ",{\"name\":\"" + secondName + "\",\"phone\":\"" + riskCreditUser.getSecondContactPhone()
//						+ "\",\"relationship\":\"" + secondDesc + "\"}]";
//				String jsonStrData = "{\"loan_type\":\"信用贷\",\"loan_purpose\":\"资金周转\",\"loan_term\":1,\"applicant\":" + applicant + ",\"contacts\":"
//						+ contacts + "}";
				String jsonStrData = "{\"loan_type\":\"1\",\"loan_purpose\":\"资金周转\",\"loan_term\":1,\"applicant\":" + applicant +"}";
				LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.ZZC);
				String url = map2.get("ZZC_URL"); //https://nezha.intellicredit.cn/api/v2/applications
				String name = map2.get("ZZC_NAME");
				String pwd = map2.get("ZZC_PWD");
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZZC);
				orders.setAct(ConstantRisk.ZZC_FQZ_CREATE);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url + ",jsonStrData=" + jsonStrData);
				log.info("中智诚 url=:{}jsonStrData:{}",url,jsonStrData);
				log.info("zzc2 card:{}" ,cardNum);
				//往表risk_orders中插入数据
				riskOrdersService.insert(orders);
				HttpClient c = new HttpClient();
				c.getParams().setContentCharset("utf-8");
				c.getParams().setAuthenticationPreemptive(true);

				Credentials defaultcreds = new UsernamePasswordCredentials(name, pwd);
				c.getState().setCredentials(AuthScope.ANY, defaultcreds);

				PostMethod postMethod = new PostMethod(url);
				// 可根据需要变更查询字段值

				RequestEntity r = new StringRequestEntity(jsonStrData, "application/json", "utf-8");
				postMethod.setRequestEntity(r);
				c.executeMethod(postMethod);

				// 可在此获取返回结果json字符串进行处理
				String result = postMethod.getResponseBodyAsString();
				log.info("中智诚返回的信息:{}",result);
				if (StringUtils.isNotBlank(result)) {

					if (result.indexOf("errors") != -1) {
						serviceResult = new ServiceResult("300", "中智诚返回空或请求报错"+ "");
					} else {
						serviceResult = new ServiceResult(ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					}
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.containsKey("errors") || jsonObject.containsKey("error")) {
						Object errObject = jsonObject.get("error");
						if (errObject == null) {
							errObject = jsonObject.get("errors");
						}
						serviceResult = new ServiceResult("300", errObject + "");
					} else {
						serviceResult = new ServiceResult(ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					}
					orders.setReturnParams(result);
					//将返回的信息 更新到  risk_orders中
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("getFqz return null");
					serviceResult = new ServiceResult("100", "中智诚返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getFqz error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}

	//todo:现在用的是 getBlackNew，这个现在不用
	@Override
	public ServiceResult findFqz(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userId = params.get("userId");
			Object zzcId = params.get("zzcId");
			if (userId != null && zzcId != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.ZZC);
				String url = map2.get("ZZC_URL_QUERY");
				String name = map2.get("ZZC_NAME");
				String pwd = map2.get("ZZC_PWD");
				url = url.replaceAll("\\{id\\}", zzcId + "");
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZZC);
				orders.setAct(ConstantRisk.ZZC_FQZ_REPORT);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url);
				log.info("zzc3 userId:{}",userId);
				log.info("中智诚 findFqz zzcId:{}用户的id:{}",zzcId,userId);
				//往 risk_orders表中插入数据
				riskOrdersService.insert(orders);
				HttpClient c = new HttpClient();
				c.getParams().setContentCharset("utf-8");
				c.getParams().setAuthenticationPreemptive(true);

				Credentials defaultcreds = new UsernamePasswordCredentials(name, pwd);
				c.getState().setCredentials(AuthScope.ANY, defaultcreds);

				GetMethod getMethod = new GetMethod(url);
				// 可根据需要变更查询字段值
				c.executeMethod(getMethod);

				// 可在此获取返回结果json字符串进行处理
				String result = getMethod.getResponseBodyAsString();
				log.info("中智诚 findFqz 的响应结果:{}",result);
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.containsKey("errors") || jsonObject.containsKey("error")) {
						Object errObject = jsonObject.get("error");
						if (errObject == null) {
							errObject = jsonObject.get("errors");
						}
						serviceResult = new ServiceResult("300", errObject + "");
					} else {
						serviceResult = new ServiceResult(ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("findFqz return null");
					serviceResult = new ServiceResult("100", "中智诚返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("findFqz error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}

	//todo:这个配置在数据库中不存在
	@Override
	public ServiceResult setBlack(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
//			测试环境
//			String url = "https://test.intellicredit.cn/blacklist/api/v2/blacklist";
//			String name = "18643515288@163.com";
//			String pwd = "RIyikeji888";

			String name1 = params.get("realName")+"";
			String phone1 = params.get("userPhone")+"";
			String pid = params.get("idNumber")+"";
			String applied_at = params.get("applied_at")+"";
			String confirmed_at = params.get("confirmed_at")+"";

			String name2 = params.get("firstContactName")+"";
			String phone2 = params.get("firstContactPhone")+"";

			LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.ZZC);
			String url = map2.get("ZZC_URL_BLACK");
			String name = map2.get("ZZC_NAME");
			String pwd = map2.get("ZZC_PWD");




			String contacts = "[{\"name\":\"" + name2 + "\",\"phone\":\"" + phone2 + "\"}]";
			String jsonStrData = "{\"loan_type\":\"2\",\"confirm_type\":\"overdue\",\"name\":\"" + name1 + "\"," +
					"\"pid\":\""+pid+"\"," + "\"applied_at\":\""+applied_at+"\",\"confirm_details\":\"M1\"," +
					"\"confirmed_at\":\""+confirmed_at+"\",\"mobile\":\""+phone1+"\",\"contacts\":" + contacts + "}";
			System.err.println(jsonStrData.toString());
			HttpClient c = new HttpClient();
			c.getParams().setContentCharset("utf-8");
			c.getParams().setAuthenticationPreemptive(true);

			Credentials defaultcreds = new UsernamePasswordCredentials(name, pwd);
			c.getState().setCredentials(AuthScope.ANY, defaultcreds);
			PostMethod postMethod = new PostMethod(url);
			// 可根据需要变更查询字段值
			RequestEntity r = new StringRequestEntity(jsonStrData, "application/json", "utf-8");
			postMethod.setRequestEntity(r);
			c.executeMethod(postMethod);
			// 可在此获取返回结果json字符串进行处理
			String result = postMethod.getResponseBodyAsString();
			JSONObject jsonObject = JSONObject.fromObject(result);
			if (jsonObject.containsKey("uuid")){
				serviceResult = new ServiceResult(ServiceResult.SUCCESS, "提交中智诚黑名单成功！");
			}else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}

			log.info("+=============================:{}" ,result);

		} catch (IOException e) {

			log.error("setblack error:{}", e);
		}


		return serviceResult;
	}


	/**
	 * @param params
	 */
	@Override
	public ServiceResult getBlackNew(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userName = params.get("userName");
			Object cardNum = params.get("cardNum");
			Object userId = params.get("userId");
			Object userPhone = params.get("userPhone");
			if (userName != null && cardNum != null && userId != null && userPhone != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils.getConfigParams(BackConfigParams.ZZC);
				//String url = map2.get("ZZC_URL");
				//TODO tql：此处后期需要加入数据库，后端页面可以对此接口进行相关设置
				String url = "https://nezha.intellicredit.cn/api/v2/blacklist/search";
				String name = map2.get("ZZC_NAME");
				String pwd = map2.get("ZZC_PWD");
				String jsonStrData = "{\"name\":\"" + userName + "\",\"pid\":\"" + cardNum + "\",\"mobile\":\"" + userPhone + "\",\"loan_type\":\"1\"  }" ;
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.ZZC);
				orders.setAct(ConstantRisk.ZZC_BLACK);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("中智诚 黑名单 url=" + url + ",jsonStrData=" + jsonStrData);
				log.info("zzc1 card:{}" ,cardNum);
				riskOrdersService.insert(orders);
				HttpClient c = new HttpClient();
				c.getParams().setContentCharset("utf-8");
				c.getParams().setAuthenticationPreemptive(true);

				Credentials defaultcreds = new UsernamePasswordCredentials(name, pwd);
				c.getState().setCredentials(AuthScope.ANY, defaultcreds);

				PostMethod postMethod = new PostMethod(url);
				// 可根据需要变更查询字段值

				RequestEntity r = new StringRequestEntity(jsonStrData, "application/json", "utf-8");
				postMethod.setRequestEntity(r);
				c.executeMethod(postMethod);

				// 可在此获取返回结果json字符串进行处理
				String result = postMethod.getResponseBodyAsString();
				log.info("中智诚黑名单查询返回的结果:{}",result);
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.containsKey("errors") || jsonObject.containsKey("error")) {
						Object errObject = jsonObject.get("error");
						if (errObject == null) {
							errObject = jsonObject.get("errors");
						}
						serviceResult = new ServiceResult("300", errObject + "");
					} else {
						serviceResult = new ServiceResult(ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("td return null");
					serviceResult = new ServiceResult("100", "中智诚返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getRisk error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}


}
