package com.info.risk.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskRuleProperty;
import com.info.web.util.PageConfig;

public interface IRiskCreditUserService {
	/**
	 * 更新蚂蚁花呗信息
	 * 
	 * @param riskCreditUser
	 *            包含userId、myHb两个信息
	 * @return
	 */
	public int updateUserMyHb(RiskCreditUser riskCreditUser);

	/**
	 * 更新用户表芝麻分
	 * 
	 * @param riskCreditUser
	 *            需要传入userId和zmScore
	 * @return
	 */
	public int updateZmScore(RiskCreditUser riskCreditUser);

	/**
	 * 根据用户主键、芝麻认证状态和芝麻行业关注度接口返回的字符串解析并更新芝麻逾期情况到用户表
	 * 
	 * @param riskCreditUser
	 *            经过工具类构造好的芝麻关注度对象
	 */
	void updateZm(RiskCreditUser riskCreditUser);

	/**
	 * 获得所有的规则标识与征信对象的属性对应关系
	 * 
	 * @param params
	 * @return
	 */
	public List<RiskRuleProperty> findRuleProperty(
            HashMap<String, Object> params);

	/**
	 * 更新同盾详情及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId、tdScore、tdPhoneBlack、tdCardNumBlack、tdMonth1Borrow、
	 *            tdDay7Borrow
	 *            、tdMonth1CardNumDeviceBorrow、tdDay7DeviceCardOrPhoneBorrow
	 *            、tdDay7CardDevice、tdMonth3ApplyCard、tdMonth3CardApply
	 * @return
	 */
	public int updateTdDetail(RiskCreditUser riskCreditUser);

	/**
	 * 更新白骑士黑名单情况及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId,bqsBack(1.通过；2.拒绝（命中黑名单）；3.建议人工审核(命中灰名单))
	 * @return
	 */
	public int updateBqs(RiskCreditUser riskCreditUser);

	/**
	 * 更细91征信数据及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId,jyLoanNum,jyJdNum,jyJdBl,jyOverNum,jyOverBl,,
	 * @return
	 */
	public int updateJy(RiskCreditUser riskCreditUser);

	/**
	 * 更新密罐数据及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId,userId,mgBlackScore, mgDay7Num,mgMonth1Num,mgBlack
	 * @return
	 */
	public int updateMg(RiskCreditUser riskCreditUser);

	/**
	 * 更新用户表聚信立分析数据及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId,jxlZjDkNum,jxlBjDkNum,
	 *            jxlYjHf,jxlLink2Days,jxlLink1days
	 *            ,jxlLink2Num,jxlLink1Num,jxlLink2Order
	 *            ,jxlLink1Order,jxlGjTs,jxl_ht_phone_num、jxlAmthNum
	 * @return
	 */
	public int updateJxl(RiskCreditUser riskCreditUser);

	/**
	 * 更新用户表聚信立Token及更新时间
	 * 
	 * @param riskCreditUser
	 *            需要传入userId,jxlToken
	 * @return
	 */
	public int updateJxlToken(RiskCreditUser riskCreditUser);

//	/**
//	 * 调用所有征信接口并更新相关信息
//	 *
//	 * @param params
//	 */
//	void updateAll(HashMap<String, Object> params, boolean sendRisk);

	/**
	 * 计算用户借款额度
	 * 
	 * @param riskCreditUser
	 */
	void updateBorrowMoney(RiskCreditUser riskCreditUser);

	/**
	 * 根据用户ID查询出现黑名单、逾期情况的记录数
	 * 
	 * @param userId
	 * @return
	 */
	public List<RiskCreditUser> findByUserId(Integer userId);

	PageConfig<HashMap<String, Object>> findJxlPage(
            HashMap<String, Object> params);

	PageConfig<HashMap<String, Object>> findAnalysisPage(
            HashMap<String, Object> params);

	PageConfig<RiskCreditUser> findCalPage(HashMap<String, Object> params);

	void updateFromRisk();

//	//处理诚安聚立
//	public void dealCajl(HashMap<String, Object> params);
	public PageConfig<HashMap<String, Object>> findCajlPage(HashMap<String, Object> params);
}
