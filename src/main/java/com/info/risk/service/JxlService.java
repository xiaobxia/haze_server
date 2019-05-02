package com.info.risk.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.info.back.utils.ServiceResult;

import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;

import com.info.web.pojo.BackConfigParams;
import com.info.web.util.OrderNoUtil;

/**
 * 
 * 类描述：芝麻信用相关接口 <br>
 * 每次发出请求之前插入订单信息，返回后更新订单信息<br>
 * 此类不做任何业务处理，仅拼接参数请求第三方，必须使用trycath，并且不向上抛出异常以保证插入或更新的订单不会回滚<br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-12-12 下午03:41:28 <br>
 * 
 * @version
 * 
 */
@Slf4j
@Service
public class JxlService implements IJxlService {
	@Autowired
	private IRiskOrdersService riskOrdersService;

	/**
	 * 实名认证
	 * 
	 */
	@Override
	public ServiceResult realName(HashMap<String, Object> params) {
		return new ServiceResult("500", "未知异常，请稍后重试！");
	}

	@Override
	public ServiceResult getToken(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		String result = "";
		try {
		} catch (Exception e) {
			log.error("getToken error ,params=:{}error:{}" ,params, e);
		}
		return serviceResult;
	}

	@Override
	public ServiceResult getCaptcha(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		String result = "";
		try {
		} catch (Exception e) {
			log.error("getOpenId error ,params=:{}error:{}" ,params, e);
		}
		return serviceResult;
	}

	/**
	 * 提交验证码<br>
	 * code为200时，msg是聚信立信息；<br>
	 * 提交验证码请求，则开始采集数据
	 * 
	 */
	@Override
	public ServiceResult applyCollect(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		String result = "";
		try {
		} catch (Exception e) {
			log.error("getOpenId error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}

	/**
	 * 查询报告
	 */
	@Override
	public ServiceResult findUserReport(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		String result = "";
		try {
			Object userId = params.get("userId");
			Object token = params.get("token");
			if (token != null && userId != null) {

				Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.JXL);
				String jxlUrl = keys.get("JXL_cj_url") + "api/access_report_data_by_token?";
				RiskOrders orders = new RiskOrders();
				String orderNo = OrderNoUtil.getInstance().getUUID();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.JXL);
				orders.setAct(ConstantRisk.GET_USER_REPORT);
				orders.setOrderNo(orderNo);
				orders.setReqParams(JSON.toJSONString(params));
				// logger.info("send to get applyCollect " + orders.toString());
				String value = "access_token=" + keys.get("JXL_token") + "&token=" + token + "&client_secret=" + keys.get("JXL_BZM");
				jxlUrl = jxlUrl + value;
				riskOrdersService.insert(orders);
				result = new WebClient().doGet(jxlUrl);
				log.info("send to findUserReport:{} ",orders.toString());
				try {
					orders.setReturnParams(result);
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.getBoolean("success")) {
						orders.setStatus(RiskOrders.STATUS_SUC);
						serviceResult = new ServiceResult("200", result);
					} else {
						serviceResult = new ServiceResult("300", "查询报告失败");
					}
					riskOrdersService.update(orders);
					log.info("get findUserReport result=:{}",result);
				} catch (Exception e) {
					log.error("getHZ findUserReport error:{} ", e);
					serviceResult = new ServiceResult("300", "解析数据出错");
				}
			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("getOpenId error ,params=:{}error{}" ,params, e);
		}
		return serviceResult;
	}

	@Override
	public ServiceResult createJxl(HashMap<String, Object> params) {
		return null;
	}

}