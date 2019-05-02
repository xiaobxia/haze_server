package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.utils.ServiceResult;
import com.info.risk.pojo.RiskCreditUser;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.BorrowOrderChecking;
import com.info.web.pojo.BorrowOrderLoan;
import com.info.web.pojo.BorrowOrderLoanPerson;
import com.info.web.pojo.User;
import com.info.web.pojo.UserLimitRecord;
import com.info.web.util.PageConfig;

/**
 * 
 * @ClassName: IBorrowService.java
 * @Description: TODO
 * @author zhangjb
 * @version V1.0
 * @Date 2016年12月12日 下午7:22:16
 */
public interface IThreeLoanOrderService {
	
	/**
	 * 第三方活动打款
	 * @param mapParam
	 * @return
	 */
	public ServiceResult  updateThreeLoanTerm(HashMap<String, Object> mapParam);
	
	
//
//	/**
//	 * 根据条件查找借款
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<BorrowOrder> findAll(HashMap<String, Object> params);
//
//	/**
//	 * 根据主键查找一条
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public BorrowOrder findOneBorrow(Integer id);
//
//	/**
//	 * 根据主键删除对象
//	 * 
//	 * @param id
//	 */
//	public void deleteById(Integer id);
//
//	/**
//	 * 插入 对象
//	 * 
//	 * @param backUser
//	 */
//	public void insert(BorrowOrder borrowOrder);
//
//	/**
//	 * 更新 象
//	 * 
//	 * @param backUser
//	 */
//	public void updateById(BorrowOrder borrowOrder);
//
//	/**
//	 * 更新 象
//	 * 
//	 * @param backUser
//	 */
//	public void authBorrowState(BorrowOrder borrowOrder);
//
//	/**
//	 * 分页查询
//	 * 
//	 * @param params
//	 * @return
//	 */
//	PageConfig<BorrowOrder> findPage(HashMap<String, Object> params);
//
//	/**
//	 * 分页查询B类服务费
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public PageConfig<BorrowOrderLoan> findBorrowFreeBPage(HashMap<String, Object> params);
//	/**
//	 * 分页查询B类服务费-统计
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public PageConfig<BorrowOrderLoan> findBorrowFreeBStatisPage(HashMap<String, Object> params);
//	/**
//	 * 分页查询C类服务费-统计
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCStatisPage(HashMap<String, Object> params);
//	
//	public Integer findBorrowFreeBCount(HashMap<String, Object> params);
//	public Integer findBorrowFreeBStatisCount(HashMap<String, Object> params);
//
//	/**
//	 * 分页查询C类服务费
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCPage(HashMap<String, Object> params);
//	
//	public Integer findBorrowFreeCCount(HashMap<String, Object> params);
//	public Integer findBorrowFreeCStatisCount(HashMap<String, Object> params);
//	public Map<String, Object> saveLoan(Map<String, String> params, User user);
//
//	/**
//	 * 计算用户借款服务费
//	 * 
//	 * @param configParams
//	 * @param money
//	 *            单位：分
//	 * @return
//	 */
//	public Map<String, Integer> calculateApr(Integer money, Integer period);
//
//	// ************************用户额度管理
//	/**
//	 * 提額
//	 * 
//	 * @param backUser
//	 */
//	public void addUserLimit(User user);
//
//	/**
//	 * 修改额度
//	 * 
//	 * @param mapParam
//	 *            userId，newAmountMax
//	 */
//	public void changeUserLimit(HashMap<String, Object> mapParam);
//
//	/**
//	 * 分页查询
//	 * 
//	 * @param params
//	 * @return
//	 */
//	PageConfig<UserLimitRecord> finduserLimitPage(HashMap<String, Object> params);
//
//	/**
//	 * 查询用户最近借款信息
//	 * 
//	 * @param parseInt
//	 * @return
//	 */
//	public BorrowOrder selectBorrowOrderUseId(Integer userId);
//
//	/**
//	 * 检查当前用户是否存在未还款完成的订单
//	 * 
//	 * @param userId
//	 * @return 1：是；0：否
//	 */
//	public Integer checkBorrow(Integer userId);
//
//	/**
//	 * 更新risk
//	 * 
//	 * @param riskCreditUser
//	 */
//	public void updateRiskCreditUserById(RiskCreditUser riskCreditUser);
//
//	/**
//	 * 根据用户查询申请列表
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	List<BorrowOrder> findByUserId(Integer userId);
//
//	/**
//	 * 请求打款
//	 * 
//	 * @param borrowOrder
//	 */
//	public void requestPay(BorrowOrder borrowOrder);
//
	/**
	 * 请求打款（非招行）
	 * 
	 * @param borrowOrders
	 */
	public void requestPaysNotCmbUserThree(List<BorrowOrder> borrowOrders);

	/**
	 * 请求打款(招商用户)
	 * 
	 * @param borrowOrders
	 */
	public void requestPaysCmbUserThree(List<BorrowOrder> borrowOrders);
//
//	/**
//	 * 请求打款(非招商)
//	 * 
//	 * @param borrowOrders
//	 */
//	public ServiceResult subPayNotCmb(List<BorrowOrder> borrowOrders, String subType);
//
//
//	/**
//	 * 请求打款(招商)
//	 * 
//	 * @param borrowOrders
//	 */
//	public ServiceResult subPayCmb(List<BorrowOrder> borrowOrders, String subType);
//
//	/**
//	 * 统计借款记录数
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public int findParamsCount(HashMap<String, Object> params);
//
//	/**
//	 * 口袋资产接口调用
//	 * 
//	 * @param bo
//	 * @param user
//	 * @param bankAllInfo
//	 */
//	public ServiceResult addDocking(BorrowOrderChecking bo);
// /**
//  * 
//  * @param bo
//  */
//	public void addBorrowChecking(BorrowOrder bo);
//
//	/**
//	 * 重新放款（服务费）
//	 * 
//	 * @param yurref
//	 */
//	public Boolean loanAgainFee(String yurref, String Loantype);
//	
//	/**
//	 * 分页查询BorrowOrderCheck
//	 * 
//	 * @param params
//	 * @return
//	 */
//	PageConfig<BorrowOrderChecking> findBorrowOrderCheckPage(HashMap<String, Object> params);
//	
//	Integer findBorrowOrderCheckCount(HashMap<String, Object> params);
//	
//	/**
//     * 查询放款成功对应的公司服务费金额
//     * @param params
//     * @return
//     */
//    Long findloanInterestsSucSumB(HashMap<String,Object> params);
//	/**
//     * 查询放款成功对应的个人服务费金额
//     * @param params
//     * @return
//     */
//    Long findloanInterestsSucSumC(HashMap<String,Object> params);
//	/**
//     * 查询放款成功对应的借款金额
//     * @param params
//     * @return
//     */
//    Long findMoneyAmountSucSum(HashMap<String,Object> params);
//    /**
//     * 实际放款成功金额
//     * @param params
//     * @return
//     */
//    Long findIntoMoneySucSum(HashMap<String,Object> params);
}
