package com.info.web.dao;

import com.info.web.pojo.KefuCensus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAssetKefuCensusDao {
    /**
     * 添加客服统计
     * @param kefuCensus
     */
    void insertAssetKeFuCensus(KefuCensus kefuCensus);

    /**
     * 修改客服统计
     * @param kefuCensus
     */
    void updateAssetKefuCensus(KefuCensus kefuCensus);

    //客服每日派单回款统计
    Integer dayPandanCount(@Param("createTime") String createTime,@Param("status") Integer status, @Param("jobId") Integer jobId,@Param("type") Integer type);

    //客服每日派单展期统计
    Integer extendCount(@Param("createTime") String createTime,@Param("jobId") Integer jobId,@Param("type") Integer type);

    //条件查询客服统计
    List<KefuCensus> kefuCensusResult(@Param("createTime") String createTime,@Param("jobId") Integer jobId);

    //查询是否存在已经派过的单子
    Integer findAssignExits(@Param("repaymentId")Integer repaymentId);


}
