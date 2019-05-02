package com.info.web.dao;

import java.util.List;
import java.util.Map;

import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.ChannelReport;
import com.info.web.pojo.ThirdChannelReport;
import org.springframework.stereotype.Repository;

@Repository("thirdChannelReportDao")
public interface IThirdChannelReportDao {
	
	/**
	 * 根据条件查询
	 * 
	 * @param map
	 *            参数名 ： <br>
	 *            参数名 ：
	 * @return list
	 */
	public List<ThirdChannelReport> findAllList(Map<String, Object> params);
	
	public List<ThirdChannelReport> findAllListByTime(Map<String, Object> params);
	
	public List<ThirdChannelReport> findPrAll(Map<String, Object> params);
	
	public List<ThirdChannelReport> findThirdId();
	
	public List<Map<String,Object>> findUserId(String nowTime);
	
	public List<Map<String,Object>> findUserInfoById(String nowTime);
	
	public Map<String,Object> findUserBorrowSucInfo(String userid);
	
	public Map<String,Object> findUserBlack(String userid);
	
	public Integer findPrAllCount(Map<String, Object> params);
	public Integer findAllCount(Map<String, Object> params);

	/**
	 * 插入对象
	 * 
	 * @param backUser
	 */
	public void insert(ThirdChannelReport thirdChannelReport);

	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);
	
	public int deleteByThirdChannelReport(ThirdChannelReport thirdChannelReport);
	

	/**
	 * 更新对象
	 * 
	 * @param backUser
	 */
	public void updateById(ThirdChannelReport thirdChannelReport);

	
	public int findRegisterCount(Map<String, Object> param);
	public int findRealNameCount(Map<String, Object> param);
	public int findJXLCount(Map<String, Object> param);
	public int findTdCount(Map<String, Object> param);
	public int findZMCount(Map<String, Object> param);
	public int findContactCount(Map<String, Object> param);
	public int findCompanyCount(Map<String, Object> param);
	public int findBankCount(Map<String, Object> param);
	public int findAlipayCount(Map<String, Object> param);
	public int findApproveErrorCount(Map<String, Object> param);
	public int findBorrowApplyCount(Map<String, Object> param);
	public int findBorrowSucCount(Map<String, Object> param);
	public int findIntoMoney(Map<String, Object> param);
	public int findBlackUserCount(Map<String, Object> param);

	public int findlateDayCount(Map<String, Object> param);
	public int findoverdueMoneySum(Map<String, Object> param);
	public int findoverdueMoney(Map<String, Object> param);
	public int findbaddebtMoneySum(Map<String, Object> param);
	public int findbaddebtMoney(Map<String, Object> param);
}
