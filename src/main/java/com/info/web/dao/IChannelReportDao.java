package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.ChannelReport;
import com.info.web.pojo.OutChannelLook;

public interface IChannelReportDao {
	int getRegisterNow(String channelId);
	int getRegisterRealNow(String channelId);
	String getChannelIdByCode(String channelCode);
	
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<ChannelReport> findAll(Map<String, Object> params);
	
	public List<ChannelReport> findPrAll(Map<String, Object> params);
	
	public List<ChannelReport> findChannelId();
	
	public ChannelReport findChannelById(Integer id);
	
	
	public List<ChannelReport> findChannelIdAll();
	public List<ChannelReport> findChannelIdCount();
	
	public Integer findPrAllCount(Map<String, Object> params);
	public Integer findAllCount(Map<String, Object> params);

	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(ChannelReport channelReport);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);
	
	public int deleteByChannelReport(ChannelReport channelReport);
	

	/**
	 * 更新对象
	 * 
	 * @param backUser
	 */
	public void updateById(ChannelInfo channelInfo);

	
	public List<Map<String, Object>> findRegisterCount(Map<String, Object> param);
	public List<Map<String, Object>> findRealNameCount(Map<String, Object> param);
	public List<Map<String, Object>> findJXLCount(Map<String, Object> param);
	public List<Map<String, Object>> findTDCount(Map<String, Object> param);
	public List<Map<String, Object>> findZMCount(Map<String, Object> param);
	public List<Map<String, Object>> findContactCount(Map<String, Object> param);
	public List<Map<String, Object>> findCompanyCount(Map<String, Object> param);
	public List<Map<String, Object>> findBankCount(Map<String, Object> param);
	public List<Map<String, Object>> findAlipayCount(Map<String, Object> param);
	public List<Map<String, Object>> findApproveErrorCount(Map<String, Object> param);
	public List<Map<String, Object>> findBorrowApplyCount(Map<String, Object> param);
	public List<Map<String, Object>> findBorrowSucCount(Map<String, Object> param);
	public List<Map<String, Object>> findIntoMoney(Map<String, Object> param);
	public List<Map<String, Object>> findNewIntoMoney(Map<String, Object> param);
	public List<Map<String, Object>> findOldIntoMoney(Map<String, Object> param);
	public List<Map<String, Object>> findBlackUserCount(Map<String, Object> param);
	public List<Map<String, Object>> findlateDayCount(Map<String, Object> param);


	public List<Map<String,Object>> findAndroidCount(Map<String, Object> param);//android注册量
	public List<Map<String,Object>> findIosCount(Map<String, Object> param);//ios注册量
	public List<Map<String,Object>> findPcCount(Map<String, Object> param);//pc注册量

//    Map<String,Object> findByNatural(Map<String, Object> param);
	Integer registerCount(Map<String, Object> map);
	Integer androidCount(Map<String, Object> map);
	Integer iosCount(Map<String, Object> map);
	Integer pcCount(Map<String, Object> map);
	Integer attestationRealnameCount(Map<String, Object> map);
	Integer jxlCount(Map<String, Object> map);
	Integer zhimaCount(Map<String, Object> map);
	Integer contactCount(Map<String, Object> map);
	Integer companyCount(Map<String, Object> map);
	Integer attestationBankCount(Map<String, Object> map);
	Integer alipayCount(Map<String, Object> map);
	Integer borrowApplyCount(Map<String, Object> map);
	Integer borrowSucCount(Map<String, Object> map);
	Integer intoMoney(Map<String, Object> map);
	Integer newIntoMoney(Map<String, Object> map);
	Integer oldIntoMoney(Map<String, Object> map);
	Integer blackUserCount(Map<String, Object> map);
	Integer lateDayCount(Map<String, Object> map);

	List<Map<String,Object>> findBorrowSucOutCount(Map<String, Object> param);

	List<Map<String,Object>> findBorrowApplyOutCount(Map<String, Object> param);

	List<Map<String,Object>> findOtherBorrowApplyOutCount(Map<String, Object> param);

	List<Map<String,Object>> findOtherCount(Map<String, Object> param);

	List<Map<String,Object>> dayOverdueCount(Map<String, Object> param);

	List<Map<String,Object>> dayOverdueRepayedCount(Map<String, Object> param);

	List<Map<String,Object>> dayOverdueRenewalCount(Map<String, Object> param);

	Integer findOutCount(Map<String, Object> params);

	List<OutChannelLook> findOut(Map<String, Object> params);
}
