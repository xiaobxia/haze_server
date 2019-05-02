package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.RenewalRecord;
import com.info.web.util.PageConfig;

/**
 *
 */
public interface IRenewalRecordService {

	public RenewalRecord selectByPrimaryKey(Integer id);

	public List<RenewalRecord> findParams(Map<String, Object> params);
	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public boolean deleteByPrimaryKey(Integer id);

	/**
	 * 插入 对象
	 * 
	 * @param renewalRecord
	 */
	public boolean insert(RenewalRecord renewalRecord);

	/**
	 * 插入 对象
	 *
	 * @param renewalRecord
	 */
	public boolean insertSelective(RenewalRecord renewalRecord);

	/**
	 * 更新 象
	 * 
	 * @param renewalRecord
	 */
	public boolean updateByPrimaryKey(RenewalRecord renewalRecord);


	/**
	 * 更新 象
	 *
	 * @param renewalRecord
	 */
	public boolean updateByPrimaryKeySelective(RenewalRecord renewalRecord);
	
	/**
	 * 续期详情  --退款管理
	 */
    PageConfig<RenewalRecord> renewalList(HashMap<String, Object> map);
    

	/**
	 * 续期详情--续期管理
	 */
    PageConfig<RenewalRecord> findPage(HashMap<String, Object> map);
    Integer findPageCount(HashMap<String, Object> map);
    
    /**
     * 续期总计
     * @param map
     * @return
     */
    Integer renewalTotal(HashMap<String, Object> map);
    /**
     * 续期总额
     * @param map
     * @return
     */
    Long sumFeeTotal(HashMap<String, Object> map);
    /**
     * 续期服务费总计
     * @param map
     * @return
     */
    Long renewalInterestTotal(HashMap<String, Object> map);
    /**
     * 续期费总计
     * @param map
     * @return
     */
    Long renewalFeeTotal(HashMap<String, Object> map);

	/**
	 * 查询 处理中的续期订单
	 * @return
	 */
	List<RenewalRecord> queryRenewalOrder();

}
