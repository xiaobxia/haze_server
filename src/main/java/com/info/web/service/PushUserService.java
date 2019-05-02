package com.info.web.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.web.util.JSONUtil;
import com.info.web.util.UserPushUntil;

/**
 * 推送用户认证信息
 * 
 * @author Administrator
 * 
 */
@Slf4j
@Service
public class PushUserService implements IPushUserService {

	@Override
	public ServiceResult addPushUserApprove(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "系统异常");
		try {

			Map<String, String> mapPush = SysCacheUtils.getConfigParams("PUSH_USER");
			String pushUrl = mapPush.get("push_user_url");
			String pushIsopen = mapPush.get("push_isopen");
			if (!pushIsopen.equals("1")) {
				serviceResult.setMsg("addPushUserApprove pushIsopen close 推送未开启");
				return serviceResult;
			}
			String urlAdd = UserPushUntil.ADD_URL;
			Object userId = params.get("userId");
			if (userId != null && pushUrl != null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("pushId", params.get("pushId").toString());
				map.put("userId", params.get("userId").toString());
				map.put("pushType", params.get("pushType").toString());
				map.put("approveTime", params.get("approveTime").toString());
				// 设置连接5秒超时
//				HashMap<String, Object> paramsTime = new HashMap<String, Object>();
//				paramsTime.put("soketOut", 5000);
//				paramsTime.put("connOut", 5000);
				String url = pushUrl + urlAdd;
				 String result = WebClient.getInstance().doPost(url, map);
				String paramsJosn = JSONUtil.beanToJson(map);
//				String result = new WebClient().postJsonData(url, paramsJosn, paramsTime);

				if (result == null) {
					serviceResult.setMsg("连接超时！");
					return serviceResult;
				}
				try {
					serviceResult = JSONUtil.jsonToBean(result, ServiceResult.class);
				} catch (Exception e) {
					serviceResult.setMsg("请求地址错误导致返回结果解析错误");
					log.error("addPushUserApprove error:{}",e);

				}
			} else {
				serviceResult.setMsg("参数不足");
				return serviceResult;
			}

		} catch (Exception e) {
			log.error(
					" Dt info error pushType:" + params.get("pushType") + ", userId:" + params.get("userId") + ",dateTime:"
							+ params.get("approveTime"), e);

		}
		return serviceResult;
	}
	//

}
