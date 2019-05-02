package com.info.risk.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.info.back.utils.ServiceResult;

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
public interface IJxlService {
	/**
	 * 实名认证
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 * @return 500是是服务器异常，本地代码错误或者连接异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送，但未被正常解析<br>
	 *         仅当code=200时，<br>
	 *         100时是用户为授权<br>
	 */
	public ServiceResult realName(HashMap<String, Object> params);

	/**
	 * 获得运营商的token，其他操作需按照token参数
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userPhone用户手机号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接异常<br>
	 *         400是必要参数缺失<br>
	 *         300是解析数据出错<br>
	 *         仅当code=200时，msg是获得的token，ext是website(手机运营商)<br>
	 *         100时，请求成功，返回数据无效<br>
	 */
	public ServiceResult getToken(HashMap<String, Object> params);

	/**
	 * 获取运营商验证码。
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userPhone用户手机号码<br>
	 *            mobilePwd用户用户手机服务密码<br>
	 *            token运营商令牌<br>
	 *            userId用户主键ID<br>
	 *            非必要参数：<br>
	 *            queryPwd北京移动用户查询密码
	 * @return 500是是服务器异常，本地代码错误或者连接芝麻信用异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到运营商，但未被正常解析或其他异常<br>
	 *         仅当code=200时，发送验证码成功<br>
	 *         100时是用户不需要出入验证码，直接进行数据采集<br>
	 */
	public ServiceResult getCaptcha(HashMap<String, Object> params);

	/**
	 * 提交运营商验证码(北京移动需要参数queryPwd)，提交验证码，则开始采集数据
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            userPhone用户手机号码<br>
	 *            mobilePwd用户用户手机服务密码<br>
	 *            token运营商令牌<br>
	 *            userId用户主键ID<br>
	 *            非必要参数：<br>
	 *            queryPwd北京移动用户查询密码
	 * @return 500是是服务器异常，本地代码错误或者连接芝麻信用异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到运营商，但未被正常解析或其他异常(其中包括验证码失效)<br>
	 *         仅当code=200时，提交验证码成功，开始数据采集<br>
	 *         100时是需要输入验证码<br>
	 */
	public ServiceResult applyCollect(HashMap<String, Object> params);

	/**
	 * 查看运营商报告
	 * 
	 * @param params
	 *            必要参数：<br>
	 *            token运营商令牌<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接芝麻信用异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到芝麻，查询报告未成功<br>
	 *         仅当code=200时，查询报告成功，msg是获得的报告信息<br>
	 */
	public ServiceResult findUserReport(HashMap<String, Object> params);

	/**
	 * 创建运营商报告
	 * 
	 * @param params
	 *            必要参数：<br>
	 * 
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接芝麻信用异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到芝麻，但未被正常解析<br>
	 *         仅当code=200时，msg是获得的openId<br>
	 *         100时是用户为授权，需要调用getURL方法进行授权<br>
	 */
	public ServiceResult createJxl(HashMap<String, Object> params);
	

}
