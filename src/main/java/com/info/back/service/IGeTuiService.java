package com.info.back.service;

import com.info.back.pojo.GeTuiJson;

/**
 * 个推发送消息服务接口
 *
 * @author tgy
 * @version [版本号, 2018年2月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IGeTuiService {

	/**
	 * 消息推送
	 *
	 * @param json
	 */
	public void pushToAPP(GeTuiJson json);
}
