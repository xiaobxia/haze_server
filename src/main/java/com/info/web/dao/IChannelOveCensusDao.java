package com.info.web.dao;

import com.info.web.pojo.ChannelOveCensus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface IChannelOveCensusDao {
    /**
     * 添加渠道逾期统计
     * @param channelOveCensus
     */
    void addChannelOveCensus(ChannelOveCensus channelOveCensus);

    /**
     * 更新渠道逾期统计
     * @param channelOveCensus
     */
    void  updateChannelOveCensus(ChannelOveCensus channelOveCensus);

    /**
     * 查询渠道逾期统计
     * @param map
     * @return
     */
    List<ChannelOveCensus> findChannelOveCensus(HashMap<String,Object> map );

    /**
     * 查询渠道逾期统计数量
     * @param map
     * @return
     */
    Integer findChannelOveCensusCount(HashMap<String,Object> map);

    /**
     * 查询出所有的渠道id
     * @return
     */
    List<Integer> findChannelIdList();



    /**
     * 查询出新/老用户 到期金额 到期数量
     * @return
     */
    Map<String,Object> loanCountAndMoney(@Param("userFrom") Integer userFrom, @Param("repayTime") String repayTime,
                                         @Param("customerType") Integer customerType, @Param("status") Integer status);

    /**
     * 展期数量
     * @param userFrom
     * @param repayTime
     * @return
     */
    Map<String,Object> extendCountAndMoney(@Param("userFrom") Integer userFrom,@Param("repayTime") String repayTime,@Param("customerType") Integer customerType);

    //查询出今日待收/已收所有userId,order_id 以及userFrom
    List<Map<String,Object>> findWait(@Param("repayTime") String repayTime,@Param("status") Integer status);

    //查询出今日展期所有userId,order_id以及userFrom
    List<Map<String,Object>> findExtend(@Param("repayTime") String repayTime,@Param("status") Integer status);
}
