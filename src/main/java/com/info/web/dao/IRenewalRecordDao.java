package com.info.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.web.pojo.RenewalRecord;
import com.info.web.pojo.RepaymentReturn;
import com.info.web.util.PageConfig;
import org.springframework.stereotype.Repository;

@Repository("renewalRecordDao")
public interface IRenewalRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RenewalRecord record);

    int insertSelective(RenewalRecord record);

    RenewalRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RenewalRecord record);

    int updateByPrimaryKeyWithBLOBs(RenewalRecord record);

    int updateByPrimaryKey(RenewalRecord record);

    List<RenewalRecord> findParams(Map<String, Object> map);
    /**
     * 退款》续期列表
     * @param map
     * @return
     */
    PageConfig<RenewalRecord> renewalList(Map<String, Object> map);
    /**
     * 续期管理》续期列表
     * @param map
     * @return
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