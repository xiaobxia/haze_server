package com.info.web.service;


import com.info.constant.Constant;
import com.info.web.dao.IBackLoanCensusDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackLoanCensus;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BackLoanCensusService implements IBackLoanCensusService {

    @Autowired
    IBackLoanCensusDao backLoanCensusDao;

    @Autowired
    private IPaginationDao paginationDao;

    //贷后统计 每一小时跑一次 针对于当天的数据
    @Override
    public Boolean afterLoanCensus(String repayTime) {
        Boolean b = true;
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            BackLoanCensus backLoanCensus = new BackLoanCensus();
            //查询某日到期笔数，到期金额
            map=backLoanCensusDao.findExpireCountAndMoney(repayTime,null,null);
            backLoanCensus.setExpireMoney(optimic(map,"money"));
            backLoanCensus.setExpireCount(map.get("count") == null ?0: Integer.valueOf(map.get("count").toString()));
            //查询某日到期已还订单笔数，金额 (status = 30)，
            map = backLoanCensusDao.findRepayCountAndMoney(repayTime,30);
            backLoanCensus.setRepayMoney(optimic(map,"money"));
            backLoanCensus.setRepayCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
            //部分还款金额，笔数(status = 23),
            map = backLoanCensusDao.findRepayCountAndMoney(repayTime,23);
            backLoanCensus.setAmortizationLoanMoney(optimic(map,"money"));
            backLoanCensus.setAmortizationLoanCount(map.get("count") == null ? 0: Integer.valueOf(map.get("count").toString()));
            //展期笔数 展期服务费金额
            map = backLoanCensusDao.findExtendCountAndMoney(repayTime);
            backLoanCensus.setExtendMoney(optimic(map,"money"));
            backLoanCensus.setExtendCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
            //判断某天的贷后数据是要添加 还是更新
            BackLoanCensus back = backLoanCensusDao.findBackLoanCensusByTime(repayTime);
            backLoanCensus.setUpdateDate(new Date());
            if(back != null){
                backLoanCensusDao.updateBackLoanCensus(backLoanCensus);
                b = true;
            }else{
                backLoanCensus.setRepayDate(repayTime);
                backLoanCensusDao.insertBackLoanCensus(backLoanCensus);
                b = true;
            }
        }catch(Exception e){
            b = false;
            log.error("贷后统计定时任务报错"+e.getMessage());
        }
        return b;
    }

   //定时任务 每天跑一次
    @Override
    public Boolean BackLoanOveCensus(){
        Boolean b;
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            HashMap<String,Object> params = new HashMap<String,Object>();
            //查询所有贷后
            List<BackLoanCensus> list = backLoanCensusDao.BackLoanCensusResult(params);
                for (BackLoanCensus backLoanCensus :list){
                    if((backLoanCensus.getExpireMoney() != null ? backLoanCensus.getExpireMoney().intValue() : 0) != 0){
                    //首逾
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,1);
                    BigDecimal firstMoney = optimic(map,"money");
                    //逾期率 = 逾期金额/到期总金额
                    BigDecimal firstRate = (firstMoney.divide(backLoanCensus.getExpireMoney(),4)).multiply(BigDecimal.valueOf(10000));
                    backLoanCensus.setOveFirstRate(firstRate.intValue());
                    //2天 3天 7天
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,2);
                    BigDecimal twoMoney = optimic(map,"money");
                    BigDecimal twoRate = twoMoney.divide(backLoanCensus.getExpireMoney(),4).multiply(BigDecimal.valueOf(10000));
                    backLoanCensus.setTwoRate(twoRate.intValue());
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,3);
                    BigDecimal threeMoney = optimic(map,"money");
                    BigDecimal threeRate = threeMoney.divide(backLoanCensus.getExpireMoney(),4).multiply(BigDecimal.valueOf(10000));
                    backLoanCensus.setThreeRate(threeRate.intValue());
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,7);
                    BigDecimal sevenMoney = optimic(map,"money");
                    BigDecimal sevenRate = sevenMoney.divide(backLoanCensus.getExpireMoney(),4).multiply(BigDecimal.valueOf(10000));
                    backLoanCensus.setSevenRate(sevenRate.intValue());
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,null);
                    BigDecimal money = optimic(map,"money");
                    BigDecimal oveRate = money.divide(backLoanCensus.getExpireMoney(),4).multiply(BigDecimal.valueOf(10000));
                    backLoanCensus.setOveFirstRate(oveRate.intValue());
                    }
                    //逾期未处理订单，逾期未处理金额(不包含滞纳金)
                    map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,null);
                    backLoanCensus.setOveWaitMoney(optimic(map,"money"));
                    backLoanCensus.setOveWaitCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
                    //逾期已还笔数，逾期已还金额（status = 34）
                    map = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34);
                    backLoanCensus.setOveRepayMoney(optimic(map,"money"));
                    backLoanCensus.setOveRepayCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
                    //展期笔数 展期服务费金额
                    map = backLoanCensusDao.findExtendCountAndMoney(backLoanCensus.getRepayDate());
                    backLoanCensus.setExtendMoney(optimic(map,"money"));
                    backLoanCensus.setExtendCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
                    //部分还款金额，笔数(status = 23),
                    map = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),23);
                    backLoanCensus.setAmortizationLoanMoney(optimic(map,"money"));
                    backLoanCensus.setAmortizationLoanCount(map.get("count") == null ? 0: Integer.valueOf(map.get("count").toString()));
                    backLoanCensus.setUpdateDate(new Date());
                    backLoanCensusDao.updateBackLoanCensus(backLoanCensus);
                }
                b = true;

        }catch (Exception e){
            b = false;
          log.error("贷后逾期率统计定时任务出错"+e.getMessage());
        }
        return b;
    }


    @Override
    public void insertBackLoanCensus(BackLoanCensus backLoanCensus) {
        backLoanCensusDao.insertBackLoanCensus(backLoanCensus);
    }

    @Override
    public void updateBackLoanCensus(BackLoanCensus backLoanCensus) {
        backLoanCensusDao.updateBackLoanCensus(backLoanCensus);
    }

    @Override
    public BackLoanCensus findBackLoanCensusByTime(String  repayTime) {
        BackLoanCensus backLoanCensus = backLoanCensusDao.findBackLoanCensusByTime(repayTime);
        return backLoanCensus;
    }

    @Override
    public PageConfig<BackLoanCensus> backLoanCensusResult(HashMap<String,Object> params) {
        return paginationDao.findPage("BackLoanCensusResult", "BackLoanCensusCount", params, "web");
    }

    public BigDecimal optimic(Map<String,Object> map, String key){
        BigDecimal money = BigDecimal.valueOf(0);
        if(map.size()>0){
            if(map.get(key) !=null){
                money = new BigDecimal(map.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return money;
    }
}
