package com.info.web.dao;

import com.info.web.pojo.BackLoanCensus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷后统计
 */
@Repository
public interface IBackLoanCensusDao {
     //查询贷后统计
     List<BackLoanCensus> BackLoanCensusResult(HashMap<String,Object> params);
     //查询贷后统计数量
     int BackLoanCensusCount(HashMap<String,Object> params);

     //查询某日到期笔数，到期金额 ,逾期未处理订单，逾期未处理金额(不包含滞纳金) 首逾 2天 3天 7天
     Map<String,Object> findExpireCountAndMoney(@Param("repayTime") String  repayTime,@Param("status") Integer status,@Param("lateDay") Integer lateDay);

     //查询某日到期已还订单笔数，金额 (status = 30)，部分还款金额，笔数(status = 23),逾期已还笔数，逾期已还金额（status = 34）
     Map<String,Object> findRepayCountAndMoney(@Param("repayTime") String  reapyTime, @Param("status") Integer status);

     //展期笔数 展期服务费金额
     Map<String,Object> findExtendCountAndMoney(@Param("repayTime") String  repayTime);

     //添加贷后统计
     void insertBackLoanCensus(BackLoanCensus backLoanCensus);

     //更新贷后统计
     void updateBackLoanCensus(BackLoanCensus backLoanCensus);

     //根据还款日期查找贷后记录
     BackLoanCensus findBackLoanCensusByTime(@Param("repayTime") String  repayTime);

     Map<String,Object> findProductMoney();

}
