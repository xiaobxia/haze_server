package com.info.web.service;

import com.info.web.pojo.ChannelOveCensus;
import com.info.web.util.PageConfig;

import java.util.HashMap;

/**
 * 渠道逾期统计
 */
public interface IChannelOveCensusService {
    /**
     *  添加渠道逾期统计
     * @param channelOveCensus
     */
      void addChannelOveCensus(ChannelOveCensus channelOveCensus);
    /**
     * 更新渠道逾期统计
     * @param channelOveCensus
     */
    void  updateChannelOveCensus(ChannelOveCensus channelOveCensus);

    /**
     * 分页查询
     * @return
     */
     PageConfig<ChannelOveCensus> findChannelOveCensus(HashMap<String,Object> params);

    /**
     * 定时任务每日渠道逾期统计
     * @param repayTime
     * @return
     */
     Boolean channelOveCensusResult(String repayTime );

}
