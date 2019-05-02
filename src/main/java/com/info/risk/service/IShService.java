package com.info.risk.service;

import java.util.HashMap;

import com.info.back.utils.ServiceResult;

public interface IShService {
	/**
	 * 获得算话反欺诈数据
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到第三方，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析，返回的信息是借款详情，<br>
	 *         100是第三方返回返回空或请求错误<br>
	 */
	public ServiceResult getFqz(HashMap<String, Object> params);
}
