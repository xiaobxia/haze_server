package com.info.risk.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.info.back.utils.ServiceResult;
import com.info.web.pojo.BorrowOrderTdDevice;
import com.info.web.pojo.User;

public interface ITdService {

	/**
	 * 获得同盾贷前审核报告，该接口收费
	 *
	 * @param params
	 *            必要参数：<br>
	 *            reportId报告标识<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接芝麻信用异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到同盾，但未被正常解析<br>
	 *         仅当code=200时，msg是同盾详情,解析规则参见AESUtil类中的getTdReport<br>
	 *         600是同盾返回报告未生成,对应同盾的code是204或者404，考虑等待三秒再次发送<br>
	 *         100是同盾返回空或请求错误<br>
	 */
	public ServiceResult queryReport(HashMap<String, Object> params);


	/**
	 * 同盾运营商数据
	 * @param user
	 * @return
	 */
//	public String getTdYunYinShangData(User user)  throws Exception;
	/**
	 * 获取同盾的 反欺诈的详细数据
	 */
//	public String getTdFqzDetailData(User user, BorrowOrderTdDevice borrowOrderTdDevice);

	/**
	 * 同盾运营商数据详细报告
	 * @param user
	 * @return
	 */
//	public Map<String, Object> getTdYunYinShangDataDetail(User user);
}
