package com.info.risk.service;

import java.util.HashMap;

import com.info.back.utils.ServiceResult;

public interface IJxlMgService {
	/**
	 * 获得聚信立密罐数据，该接口收费
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户身份证号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接聚信立异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到聚信立，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析，返回的信息是密罐详情，参考AESUtil的getMg方法进行解析<br>
	 *         100是聚信立返回空或请求错误<br>
	 */

	ServiceResult getDetail(HashMap<String, Object> params);

}
