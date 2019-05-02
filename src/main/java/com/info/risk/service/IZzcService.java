package com.info.risk.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.info.back.utils.ServiceResult;

public interface IZzcService {
	/**
	 * 获得中智诚黑名单，
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接中智诚异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到中智诚，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析，根据count数字是否大于0判定是否为黑名单,大于0为黑名单 <br>
	 *         100是中智诚返回空或请求错误<br>
	 */
	public ServiceResult getBlack(HashMap<String, Object> params);

	/**
	 * 获得中智诚发欺诈ID号
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接中智诚异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到中智诚，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析<br>
	 *         100是中智诚返回空或请求错误<br>
	 */
	public ServiceResult getFqz(HashMap<String, Object> params);

	/**
	 * 获得中智诚反欺诈报告
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            zzcId中智诚查询ID号<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接中智诚异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到中智诚，但返回错误信息<br>
	 *         仅当code=200时是成功发出请求并被解析<br>
	 *         100是中智诚返回空或请求错误<br>
	 */
	ServiceResult findFqz(HashMap<String, Object> params);

	/**
	 * 推送中智诚黑名单用户（和系统本身无关，只是免费使用ZZC需要把逾期用户上传过去）
	 *
	 * @param params
	 *
	 *   {"loan_type":"2","confirm_type":"overdue","name":"" + name1 + "\"," +
	 *				"\"pid\":\"510181199111111913\"," + "\"applied_at\":\"2017/06/16\",\"confirm_details\":\"M1\"," +
	 *				"\"confirmed_at\":\"2017/06/16\",\"mobile\":\"18081047656\",\"contacts\":" + contacts + "}
	 *
	 * @return 成功  {"uuid": "df852a89-94b8-49ef-900f-f6b7e1769f0b"}
	 *         失败
	 *
	 */
	ServiceResult setBlack(HashMap<String, Object> params);



	/**
	 * 新接口查找中智诚 黑名单
	 * @see
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 *
	 * @return
	 */
	public ServiceResult getBlackNew(HashMap<String, Object> params);

}
