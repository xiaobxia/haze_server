package com.info.web.service;


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
            //查询某日到期已还订单笔数，金额 (status = 30)，
            map = backLoanCensusDao.findRepayCountAndMoney(repayTime,30,null);
            backLoanCensus.setRepayMoney(optimic(map,"money"));
            backLoanCensus.setRepayCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
            //部分还款金额，笔数(status = 23),
            /*map = backLoanCensusDao.findRepayCountAndMoney(repayTime,23,null);
            backLoanCensus.setAmortizationLoanMoney(optimic(map,"money"));
            backLoanCensus.setAmortizationLoanCount(map.get("count") == null ? 0: Integer.valueOf(map.get("count").toString()));*/
            //展期笔数 展期服务费金额
            map = backLoanCensusDao.findExtendCountAndMoney(repayTime);
            backLoanCensus.setExtendMoney(optimic(map,"money"));
            backLoanCensus.setExtendProductMoney(optimic(map,"moneyAmount"));
            backLoanCensus.setExtendCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
            //查询某日到期笔数，到期金额
            map=backLoanCensusDao.findExpireCountAndMoney(repayTime,null,null);
            backLoanCensus.setExpireMoney(optimic(map,"money").add(backLoanCensus.getExtendProductMoney()));
            backLoanCensus.setExpireCount(map.get("count") == null ?0: Integer.valueOf(map.get("count").toString())+backLoanCensus.getExtendCount());
            backLoanCensus.setRepayDate(repayTime);
           // BackLoanCensus loanCensus =  backCensusLoan(backLoanCensus);
         /*   //查询出默认产品金额
            map = backLoanCensusDao.findProductMoney();
            BigDecimal  productMoney = optimic(map,"productMoney").multiply(BigDecimal.valueOf(100));
            String finallyMoney = productMoney.stripTrailingZeros().toPlainString();*/
            if((backLoanCensus.getExpireMoney() != null ? backLoanCensus.getExpireMoney().intValue() : 0) != 0){
               /*//首借回款率 正常全额还款+展期金额/到期金额 （全部新用户）
                map = backLoanCensusDao.findRepayCountAndMoney(repayTime,30,finallyMoney,null);
                BigDecimal firstRepayMoney = optimic(map,"money");
                map = backLoanCensusDao.findExtendCountAndMoney(repayTime,finallyMoney,null);
                BigDecimal firstExtendMoney = optimic(map,"moneyAmount");
                map = backLoanCensusDao.findExpireCountAndMoney(repayTime,null,null,finallyMoney,null);
                BigDecimal newExpireMoney = optimic(map,"money");
                BigDecimal repayRate = (firstRepayMoney.add(firstExtendMoney)).divide(newExpireMoney,4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
                backLoanCensus.setRepayRate(repayRate.intValue());
                //复借回款率 正常全额还款+展期金额/到期金额  （全部老用户）
                map = backLoanCensusDao.findRepayCountAndMoney(repayTime,30,finallyMoney,1);
                BigDecimal twoRepayMoney = optimic(map,"money");
                map = backLoanCensusDao.findExtendCountAndMoney(repayTime,finallyMoney,1);
                BigDecimal twoExtendMoney = optimic(map,"moneyAmount");
                map = backLoanCensusDao.findExpireCountAndMoney(repayTime,null,null,finallyMoney,1);
                BigDecimal oldExpireMoney = optimic(map,"money");
                BigDecimal reRepayRate = (twoRepayMoney.add(twoExtendMoney)).divide(oldExpireMoney,4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
                backLoanCensus.setReRepayRate(reRepayRate.intValue());*/
                //回款率 正常全额还款+展期金额/到期金额
                BigDecimal repayRate = (backLoanCensus.getRepayMoney().add(backLoanCensus.getExtendProductMoney())).divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
                backLoanCensus.setRepayRate(repayRate.intValue());
            }
            //判断某天的贷后数据是要添加 还是更新
            BackLoanCensus back = backLoanCensusDao.findBackLoanCensusByTime(repayTime);
            backLoanCensus.setUpdateDate(new Date());
            if(back != null){
                backLoanCensusDao.updateBackLoanCensus(backLoanCensus);
                b = true;
            }else{
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
                    BackLoanCensus loanCensus = backCensusLoan(backLoanCensus);
                    backLoanCensusDao.updateBackLoanCensus(loanCensus);
                }
                b = true;

        }catch (Exception e){
            b = false;
          log.error("贷后逾期率统计定时任务出错"+e.getMessage());
        }
        return b;
    }

    /**
     * 计算逾期变化的数据
     * @param backLoanCensus
     * @return
     */
    @Override
    public BackLoanCensus backCensusLoan(BackLoanCensus backLoanCensus){
        Map<String,Object> map = new HashMap<String,Object>();
        //逾期未处理订单，逾期未处理金额(不包含滞纳金)
        map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,null);
        backLoanCensus.setOveWaitMoney(optimic(map,"money"));
        backLoanCensus.setOveWaitCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
        //逾期已还笔数，逾期已还金额（status = 34）
        map = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34,null);
        backLoanCensus.setOveRepayMoney(optimic(map,"money"));
        backLoanCensus.setOveRepayCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));
      /*  //展期笔数 展期服务费金额
        map = backLoanCensusDao.findExtendCountAndMoney(backLoanCensus.getRepayDate());
        backLoanCensus.setExtendMoney(optimic(map,"money"));
        backLoanCensus.setExtendProductMoney(optimic(map,"moneyAmount"));
        backLoanCensus.setExtendCount(map.get("count") == null?0: Integer.valueOf(map.get("count").toString()));*/
        /*//部分还款金额，笔数(status = 23),
        map = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),23,null);
        backLoanCensus.setAmortizationLoanMoney(optimic(map,"money"));
        backLoanCensus.setAmortizationLoanCount(map.get("count") == null ? 0: Integer.valueOf(map.get("count").toString()));*/
        if((backLoanCensus.getExpireMoney() != null ? backLoanCensus.getExpireMoney().intValue() : 0) != 0){
         //逾期总金额 逾期未还金额+逾期已还的应还金额（均不含滞纳金）
          map  = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34,null);
          BigDecimal oveRPMoney = optimic(map,"moneyAmount");
          BigDecimal oveAllMoney = backLoanCensus.getOveWaitMoney().add(oveRPMoney);
        //首逾
        map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,1);
        BigDecimal firstMoney = optimic(map,"money");
        //逾期率 = 逾期金额/到期总金额
        BigDecimal firstRate = oveAllMoney.divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
        backLoanCensus.setOveFirstRate(firstRate.intValue());
        //2天 3天 7天
        //逾期未还金额/到期金额
        map  = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34,2);
        BigDecimal twoMoney = optimic(map,"moneyAmount");
        BigDecimal twoRate = (oveAllMoney.subtract(twoMoney)).divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
        backLoanCensus.setTwoRate(twoRate.intValue());
        map  = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34,3);
        BigDecimal threeMoney = optimic(map,"moneyAmount");
        BigDecimal threeRate = (oveAllMoney.subtract(threeMoney)).divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
        backLoanCensus.setThreeRate(threeRate.intValue());
        map  = backLoanCensusDao.findRepayCountAndMoney(backLoanCensus.getRepayDate(),34,7);
        BigDecimal sevenMoney = optimic(map,"moneyAmount");
        BigDecimal sevenRate = (oveAllMoney.subtract(sevenMoney)).divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
        backLoanCensus.setSevenRate(sevenRate.intValue());
        map = backLoanCensusDao.findExpireCountAndMoney(backLoanCensus.getRepayDate(),1,null);
        BigDecimal money = optimic(map,"money");
        BigDecimal oveRate = money.divide(backLoanCensus.getExpireMoney(),4,BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(10000));
        backLoanCensus.setOveRate(oveRate.intValue());
        }
        backLoanCensus.setUpdateDate(new Date());
      return backLoanCensus;
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
