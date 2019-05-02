package com.info.web.dao;

import org.apache.ibatis.annotations.Param;

import com.info.web.pojo.ActiveStatisticsInfo;
import org.springframework.stereotype.Repository;

/** 
 * <p>Description: 活动统计数据访问层接口</p> 
 * <p>Company:小鱼儿</p>
 * @author lixingxing
 * @version V1.0 
 */
@Repository("activeStatisticsDao")
public interface IActiveStatisticsDao{

     /**
      * 
      * <p>Description:新增活动统计数据</p>
      * @param activeStatisticsInfo
      * @return 
      * @author lixingxing
      * @date 2017年3月14日 下午1:11:25
      */
     public int activeInsertBySelective(ActiveStatisticsInfo activeStatisticsInfo);
     
     /**
      * 
      * <p>Description:更新活动统计数据</p>
      * @param activeStatisticsInfo
      * @return 
      * @author lixingxing
      * @date 2017年3月14日 下午1:11:25
      */
     public int activeUpdateSelective(ActiveStatisticsInfo activeStatisticsInfo);
     
     /**
      * 
      * <p>Description:根据统计日期查询对应日期的统计数据</p>
      * @param statisticsDate 统计日期
      * @param activeType 活动类型
      * @return 统计实体数据
      * @author lixingxing
      * @date 2017年3月14日 下午2:27:10
      */
     public ActiveStatisticsInfo selectByStatisticsDate(@Param("statisticsDate") String statisticsDate, @Param("activeType") String activeType);
}
