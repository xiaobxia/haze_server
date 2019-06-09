package com.info.web.service;

import com.info.web.pojo.*;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IRepaymentService {

	/**
	 * 根据条件查找借款
	 *
	 * @param params
	 * @return
	 */
	List<Repayment> findAll(HashMap<String, Object> params);
	int findParamsCount(HashMap<String, Object> params);

	/**
	 * 根据条件查找一条
	 *
	 * @param params
	 * @return
	 */
	Repayment findOneRepayment(Map<String, Object> params);

	List<RepaymentDetail> findDetailsByRepId(Integer id);

	List<Map<String, Object>> findMyLoan(Map<String, Object> params);

	Repayment selectByPrimaryKey(Integer id);

//	List<Repayment> findParams(Map<String, Object> params);

	List<Repayment> findTaskRepayment(Map<String, Object> params);

	List<Repayment> findByRepaymentReport(Map<String, Object> params);

	List<Repayment> findByRepaymentSmsRemind(Map<String, Object> params);

	Map<String, String> findUserPhoneName(Integer id);

	Map<String, String> findCardNo(Integer id);

	Integer findUserIdByPhone(String userPhone);

	List<Repayment> findRepayingByUserId(Integer id);

	/**
	 * 根据主键删除对象
	 *
	 * @param id
	 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 插入 对象
	 *
	 * @param repayment
	 */
	boolean insert(Repayment repayment);

	/**
	 * 插入 对象
	 *
	 * @param repayment
	 */
	boolean insertSelective(Repayment repayment);

	/**
	 * 更新 象
	 *
	 * @param repayment
	 */
	boolean updateByPrimaryKey(Repayment repayment);


	/**
	 * 更新 象
	 *
	 * @param repayment
	 */
	boolean updateByPrimaryKeySelective(Repayment repayment);

	/**
	 * 分页查询
	 *
	 * @param params
	 * @return
	 */
	PageConfig<Repayment> findPage(HashMap<String, Object> params);

	/**
	 * 待还销账分页查询
	 *
	 * @param params
	 * @return
	 */
	PageConfig<Repayment> findWriteOffPage(HashMap<String, Object> params);

	/**
	 * 还款
	 * @param repayment
	 * @param detail
     * @return
     */
	void repay(Repayment repayment, RepaymentDetail detail);

//	/**
//	 * 代扣
//	 * @param re
//	 * @param user
//	 * @return
//	 */
//	ServiceResult withhold(Repayment re, User user, int withholdType);

	/**
	 * 续期
	 * @param repayment
	 * @param rr
	 * @return
	 */
	void renewal(Repayment repayment, RenewalRecord rr);

	/**
	 * 逾期
	 */
	void overdue(Repayment repayment, List<String> repaymentIds);
	  /**
     * 查询还款信息
     * @param borrowId
     * @return
     */
    List<Repayment> findAllByBorrowId(Integer borrowId);

	void updateRenewalByPrimaryKey(Repayment record);

	Repayment collection(User u, Repayment re, RepaymentDetail detail, int collType);


	/**
	 * 插入对象
	 * @param borrowOrder
	 * @return
     */
	boolean insertByBorrorOrder(BorrowOrder borrowOrder);



	/**
	 * 查找逾期一天的用户
	 * @param params
	 * @return
	 */
	List<HashMap<String ,Object>> findBlackList(Map<String, Object> params);



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

	public PageConfig<ShowKeFuMessage> findAssignPage(HashMap<String, Object> params);


	/**
	 * 插入备注记录
	 * @param remark
	 * @return
	 */
	public int insertIntoRemak(Remark remark);

	/**
	 * 根据派单ID查询备注ID
	 * @param assignId 派单Id
	 * @return 备注ID
	 */
	List<String> getRemarkIdByOrderId(String assignId);

	/**
	 * 更新备注记录
	 * @param ids ids
	 */
	void updateRemarkStatus(List<String> ids);

	/**
	 * 根据分配订单的id查询所有的备注信息
	 * @param hashMap
	 * @return
	 */
	List<Remark> selectRemarkByCondition(HashMap<String, Object> hashMap);


	public AssetBorrowAssign findAssetBorrowAssignById(Integer id);

	/**
	 * 进行随机均匀分配
	 * @param backUser
	 * @param result
	 */
	int reAssign(BackUser backUser, AssetBorrowAssign result);

	/**
	 * 查询统计数据
	 * */
	PageConfig<Map<String,Object>> assignStatistics(HashMap<String, Object> params);

	List<Map<String,Object>>  selectAssignStatisticsByCondition(Map<String, Object> params);

	List<ShowKeFuMessage> getNoPayAssetBorrowAssign(HashMap<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsForSystemSend(Map<String, Object> params);

	List<Map<String,Object>> selectAssignStatisticsForArtificialSend(Map<String, Object> params);

	List<Integer> queryAssignByCondition(HashMap<String, Object> params);

	String getClientByUserId(Integer userId);

	String selectBorrowOrderIdByAssignId(Integer assignId);

	void updateAssignDelFlag(Integer assignId);
	//查找用户借款次数
	Integer userBorrowCount(Integer status,Integer userId);

	Integer selectAssetBorrowAssign(Integer id);

}


