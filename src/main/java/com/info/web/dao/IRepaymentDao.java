package com.info.web.dao;

import com.info.back.pojo.UserDetail;
import com.info.web.pojo.AssetBorrowAssign;
import com.info.web.pojo.Remark;
import com.info.web.pojo.Repayment;
import com.info.web.pojo.ShowKeFuMessage;
import com.info.web.pojo.statistics.Overdue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public interface IRepaymentDao {
	int deleteByPrimaryKey(Integer id);

	int insert(Repayment record);

	int insertSelective(Repayment record);

	Repayment selectByPrimaryKey(Integer id);

	Integer findUserIdByPhone(String userPhone);

	List<Repayment> findParams(Map<String, Object> map);

	List<Repayment> findRepayingByUserId(Integer id);

	List<Repayment> findTaskRepayment(Map<String, Object> map);

	int findParamsCount(HashMap<String, Object> params);

	List<Repayment> findByRepaymentReport(Map<String, Object> map);

	/**
	 * 短信发送需要的字段查询
	 * @param map
	 * @return
     */
	List<Repayment> findByRepaymentSmsRemind(Map<String, Object> map);

	Map<String, String> findUserPhoneName(Integer id);

	Map<String, String> findCardNo(Integer id);
	/**
	 * 根据借款主键查询还款信息
	 *
	 * @param borrowId
	 * @return
	 */
	List<Repayment> findAllByBorrowId(Integer borrowId);

	List<Map<String, Object>> findMyLoan(Map<String, Object> map);

	int updateByPrimaryKeySelective(Repayment record);

	int updateByPrimaryKeyWithBLOBs(Repayment record);

	int updateByPrimaryKey(Repayment record);

	int updateRenewalByPrimaryKey(Repayment re);

    List<HashMap<String,Object>> findBlackList(Map<String, Object> map);
	/**
	 * 根据用户id和订单id 查询还款的时间
	 * @param map
	 * @return
	 */
	Repayment selectAssetRepaymentByUserIdAndBorrowId(HashMap<String, Object> map);


	/**
	 * 根据创建时间查询 AssetBorrowAssign
	 * @param hashMap
	 * @return
	 */
	List<AssetBorrowAssign> findAssetBorrowAssignByCreateTime(Map<String, Object> hashMap);

	/**
	 * 插入AssetBorrowAssign
	 * @param assetBorrowAssign
	 * @return
	 */
	int insertAssetBorrowAssign(AssetBorrowAssign assetBorrowAssign);

	List<ShowKeFuMessage> seleShowKeFuList(Map<String, Object> map);

	int updateAssetBorrowAssignById(AssetBorrowAssign assetBorrowAssign);


	/**
	 * 插入备注记录
	 * @param remark
	 * @return
	 */
	int insertIntoRemak(Remark remark);

	/**
	 * 根据派单ID查询备注ID集合
	 * @param borrowOrderId 派单ID
	 * @return 备注ID集合
	 */
	List<String> getRemarkIdByOrderId(@Param("borrowOrderId")String borrowOrderId);

	void updateRemarkStatus(@Param("ids")List<String> ids);

	/**
	 * 根据分配订单的id查询所有的备注信息
	 * @param hashMap
	 * @return
	 */
	List<Remark> selectRemarkByCondition(HashMap<String, Object> hashMap);


	List<Map<String,Object>> assignStatistics(Map<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsByCondition(Map<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsForSystemSend(Map<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsForArtificialSend(Map<String, Object> params);

	UserDetail findUserByPhone(String userPhone);

	Repayment selectAssetRepaymentByUserId(@Param(value = "userId") String userId, @Param(value = "assetOrderId") int assetOrderId);

	List<Remark> queryBorrowRemark(String userId);

//	String queryAssignJobNameByUserId(String userId);

	List<Repayment> findByDates(Overdue overdue);

	List<Repayment> findByDatesUser(Overdue overdue);

	List<Repayment> findByModelOrder(Overdue overdue);

    List<Repayment> findByReviewTime(Overdue overdue);

	List<Repayment> findByUserIds(List<Integer> list);

    List<Integer> queryAssignByCondition(HashMap<String, Object> params);
	String selectBorrowOrderIdByAssignId(@Param("assignId")Integer assignId);

    String getClientByUserId(@Param("userId")Integer userId);

    void updateAssignDelFlag(@Param("assignId")Integer assignId);

    Integer userBorrowCount(@Param("status") Integer status,@Param("userId") Integer userId);

	Integer selectAssetBorrowAssign(Integer id);

}