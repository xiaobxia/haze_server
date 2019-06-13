package com.info.web.dao;

import com.info.web.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sun.awt.image.IntegerComponentRaster;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public interface IBorrowOrderDao {
	List<BorrowOrder> findOrderIdAndUserIdList(HashMap<String, Object> params);
	int findTerm(Integer id);
	int deleteByPrimaryKey(Integer id);

	int insert(BorrowOrder record);

	int insertSelective(BorrowOrder record);

	BorrowOrder selectByPrimaryKey(Integer id);
	BorrowOrder selectByParam(HashMap<String, Object> params);

	List<BorrowOrder> findParams(HashMap<String, Object> params);

	int updateByPrimaryKeySelective(BorrowOrder record);

	int updateByPrimaryKeyWithBLOBs(BorrowOrder record);

	int updateByPrimaryKey(BorrowOrder record);
	/**
	 * 查询放款成功对应的借款金额
	 * @param params
	 * @return
	 */
	Long findMoneyAmountSucSum(HashMap<String, Object> params);
	/**
	 * 实际放款成功金额
	 * @param params
	 * @return
	 */
	Long findIntoMoneySucSum(HashMap<String, Object> params);
	int insertRiskUser(RiskCreditUser risk);
	int updateRiskCreditUserById(com.info.risk.pojo.RiskCreditUser riskCreditUser);
	int findParamsCount(HashMap<String, Object> params);
	BorrowOrder selectBorrowOrderUseId(Integer userId);
	/**
	 * 根据用户查询申请列表
	 * @param userId
	 * @return
	 */
	List<BorrowOrder> findByUserId(Integer userId);



	List<BorrowOrder> findParamsTLO(HashMap<String, Object> params);
	int updateByPrimaryKeySelectiveTLO(BorrowOrder record);
	BorrowOrder selectByParamTLO(HashMap<String, Object> params);
	/**
	 * 批量插入合同资方数据
	 * @param boNewList
	 * @return
	 */
	int batchInsertBCI(@Param(value = "boNewList") List<BorrowOrder> boNewList);


	public List<BorrowOrder> findAssetBorrowOrderForZhimaFeedback(HashMap<String, Object> paraMap);

	int updateAssetBorrowOrderDevice(HashMap<String, Object> hashMap);

	BorrowOrderDevice searchBorrowOrderDeviceByParams(HashMap<String, Object> param);

	//获取同盾反欺诈的数据
	public BorrowOrderTdDevice selectTdFqzByUserIdAndAssetBorrowId(HashMap<String, Object> param);

	//更新同盾设备指纹以及反欺诈数据
	public  int updateTdDeviceFqzData(HashMap<String, Object> param);

	int findBorrowCount(@Param("userId") String userId, @Param("borrowStatusArray") Integer[] borrowStatusArray);

	Long findOrderLeftMony(Integer statusBfhk);

	//根据身份证号查询 订单信息
	List<BorrowOrder> selectBorrowOrderByIdNumber(HashMap<String, Object> hashMap);


	/**
	 * 根据时间查找订单数据
	 * 计算信审人员的相关数据
	 * @param hashMap
	 * @return
	 */
	List<BorrowOrder> selectBorrowOrderByTime(HashMap<String, Object> hashMap);


	/**
	 *失信率率地域统计
	 * @return
	 */
	List<BorrowOrderLocationStatisticsDo> selectBorrowOrderLocationStatistics(@Param(value = "beginTime") Timestamp beginTime, @Param(value = "endTime") Timestamp endTime);

	/**
	 *通过率地域统计
	 * @return
	 */
	List<BorrowOrderLocationStatisticsDo> selectBorrowOrderLocationStatisticsAll(@Param(value = "beginTime") Timestamp beginTime, @Param(value = "endTime") Timestamp endTime);

	List<BorrowOrder> findByReviewWay(HashMap<String, Object> hashMap);

	List<BorrowOrder> findByReviewUser(HashMap<String, Object> hashMap);

    BorrowOrder selectBorrowOrderByUserIdForLast(String userId);

    void updateBatch(Map<String, Object> params);

	Integer selectDelayNoLoanCunt(Map<String, Object> params);

	Integer	selectDelayAutoReviewCunt(Map<String, Object> params);

	Integer selectLoanFailedCunt(Map<String, Object> params);

	String getBorrowOrderIdByUserId(@Param("userId")Integer userId);

	/**
	 * 查询审核人员信息
	 *
	 * @return
	 */
	List<Map<String, String>> selectReviewUserNames();

	List<String> getUserIdWaitList();
	List<String> getUserIdList();
	List<String> getUserIdList2();
	//针对某些id查询符合某些状态的总数
	Integer findOveChannle(@Param("channelId") Integer channelId,@Param("statusList") List<Integer> statusList,@Param("loanTime") String loanTime,@Param("customerType") String customerType);
	Integer findRepayCount(@Param("channelId") Integer channelId,@Param("statusList") List<Integer> statusList,@Param("loanTime") String loanTime,@Param("customerType") String customerType);
	Integer findExtendChannel(@Param("channelId") Integer channelId,@Param("loanTime") String loanTime);
}