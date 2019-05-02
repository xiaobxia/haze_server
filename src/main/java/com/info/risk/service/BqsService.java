package com.info.risk.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import com.info.web.pojo.UserCardInfo;
import com.info.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.web.pojo.BackConfigParams;
import com.info.web.util.OrderNoUtil;
@Slf4j
@Service
public class BqsService implements IBqsService {
	@Autowired
	private IRiskOrdersService riskOrdersService;
	@Autowired
	private IZmxyService zmxyService;
	@Autowired
	private IUserService userService;

	@Override
	public ServiceResult getRisk(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userName = params.get("userName");
			Object cardNum = params.get("cardNum");
			Object userId = params.get("userId");
			Object userPhone = params.get("userPhone");
			//获取zmOpenId
			ServiceResult serviceOpenId= zmxyService.getOpenId(params);
			String zmOpenId =  serviceOpenId.getMsg();
			log.info("zmOpenId = :{}",zmOpenId);
			//添加一个用户的
			//银行卡号的数据
			UserCardInfo userCardInfo = userService.findUserBankCard(Integer.parseInt(userId.toString()));
			String phone = userCardInfo.getPhone();
			String openName = userCardInfo.getOpenName();
			if (userName != null && cardNum != null && userId != null && userPhone != null && userCardInfo != null && zmOpenId != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils
						.getConfigParams(BackConfigParams.BQS);
				String url = map2.get("BQS_URL");
				String jsonStrData = "{\"partnerId\":\""
						+ map2.get("BQS_PARENT_ID") + "\",\"verifyKey\":\""
						+ map2.get("BQS_VERIFY_KEY") + "\",\"appId\":\""
						+ map2.get("BQS_APP_ID") + "\",\"eventType\":\""
						+ map2.get("BQS_EVENT_TYPE") + "\",\"certNo\":\""
						+ cardNum + "\",\"mobile\":\"" + userPhone
						+ "\",\"name\":\"" + userName + "\",\"zmOpenId\":\""+zmOpenId+"\",";
				jsonStrData += "\"bankCardNo\":\""+userCardInfo.getCard_no()+"\","+"\"bankCardMobile\":\""+phone+"\","+"\"bankCardName\":\""+openName+"\"}";

				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.BQS);
				orders.setAct(ConstantRisk.BQS_RISK);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url + ",jsonStrData="
						+ jsonStrData);
				log.info("白骑士 bqs card:{}",cardNum);
//				logger.info("getRisk send  " + orders.toString());
				riskOrdersService.insert(orders);
				String result = WebClient.getInstance().postJsonData(url,
						jsonStrData,null);
				log.info("白骑士 getRisk return:{}",result);
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if ("BQS000".equals(jsonObject.getString("resultCode"))) {
						serviceResult = new ServiceResult(
								ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					} else {
						serviceResult = new ServiceResult("300", jsonObject
								.getString("resultCode")
								+ jsonObject.getString("resultDesc"));
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("td return null");
					serviceResult = new ServiceResult("100", "同盾返回空或请求报错");
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
