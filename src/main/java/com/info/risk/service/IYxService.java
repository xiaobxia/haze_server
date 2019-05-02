package com.info.risk.service;

import java.util.HashMap;
import java.util.List;

import com.info.back.utils.ServiceResult;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface IYxService {
	/**
	 * 发送宜信请求
	 *
	 * @param params
	 *            必要参数：<br>
	 *            userName用户姓名<br>
	 *            cardNum用户身份证号码<br>
	 *            userId用户主键ID<br>
	 * @return 500是是服务器异常，本地代码错误或者连接宜信异常<br>
	 *         400是必要参数缺失<br>
	 *         300是请求成功发送到宜信，但返回错误信息<br>
	 *         仅当code=200时是报告生成成功<br>
	 *         100是宜信返回空或请求错误<br>
	 *         600是宜信未查到该用户的逾期信息<br>
	 */
	public ServiceResult sendYx(HashMap<String, Object> params);

	public void findForYx();


	/**
	 * @param user
	 * @return
	 * 展示宜信的的风控数据
	 */
	public HashMap<String,Object> showYxMessage(User user);


	/**
	 * 宜信数据共享
	 * @param
	 * @return
	 */
	public String shareYxData(HttpServletRequest request);
}
