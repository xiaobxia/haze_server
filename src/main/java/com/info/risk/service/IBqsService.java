package com.info.risk.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.info.back.utils.ServiceResult;

public interface IBqsService {
	/**
	 * 获得同盾贷前审核服务数据，该接口收费
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接同盾异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到白骑士，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析，根据jsonObject
	 *         .getString("finalDecision")的值决定策略
	 *         ：Accept是通过；Reject是建议拒绝，高风险黑名单有击中
	 *         ；Review低风险灰名单有击中，建议人工审核；如果需要使用详情，需要自行解析json结果<br>
	 *         100是白骑士返回空或请求错误<br>
	 */
	public ServiceResult getRisk(HashMap<String, Object> params);
}
