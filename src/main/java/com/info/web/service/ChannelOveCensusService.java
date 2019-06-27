package com.info.web.service;

import com.info.web.dao.IChannelOveCensusDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.ChannelOveCensus;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChannelOveCensusService implements IChannelOveCensusService{

    @Autowired
    private IChannelOveCensusDao channelOveCensusDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public void addChannelOveCensus(ChannelOveCensus channelOveCensus) {
         channelOveCensusDao.addChannelOveCensus(channelOveCensus);
    }

    @Override
    public void updateChannelOveCensus(ChannelOveCensus channelOveCensus) {
         channelOveCensusDao.updateChannelOveCensus(channelOveCensus);
    }

    @Override
    public PageConfig<ChannelOveCensus> findChannelOveCensus(HashMap<String,Object> params) {
            return paginationDao.findPage("findChannelOveResult", "findChannelOveCountResult", params, "web");
        }

    @Override
    public Boolean channelOveCensusResult(String repayTime) {
        Map<String,Object> map = new HashMap<String,Object>();
        HashMap<String,Object> params = new HashMap<>();
        Boolean b = true;
        try{
            log.info("当日渠道逾期统计开始");
            //查询出所有渠道
            List<Integer> channelIdList = channelOveCensusDao.findChannelIdList();
            if(channelIdList.size() > 0){
                ChannelOveCensus channelOveCensus = new ChannelOveCensus();
                for(int i= 0 ; i<channelIdList.size();i++){
                    channelOveCensus.setChannelId(channelIdList.get(i));
                    channelOveCensus.setRepayTime(repayTime);
                    //查询渠道新用户 还款金额 数量 待收数量 数量
                    map = channelOveCensusDao.loanCountAndMoney(channelIdList.get(i),repayTime,0,30);
                    BigDecimal newRepayMoney = optimic(map,"money") == null? BigDecimal.ZERO:optimic(map,"money");
                    Integer newRepayCount = Integer.valueOf(map.get("count").toString());
                    channelOveCensus.setNewRepayCount(newRepayCount);
                    channelOveCensus.setNewRepayMoney(newRepayMoney);
                    //查询渠道新用户 待收数量 数量
                    map = channelOveCensusDao.loanCountAndMoney(channelIdList.get(i),repayTime,0,21);
                    BigDecimal newWaitMoney = optimic(map,"money") == null? BigDecimal.ZERO:optimic(map,"money");
                    Integer newWaitCount = Integer.valueOf(map.get("count").toString());
                    //新用户 放款数量 放款金额
                    channelOveCensus.setNewLoanCount(channelOveCensus.getNewRepayCount()+newWaitCount);
                    channelOveCensus.setNewLoanMoney(channelOveCensus.getNewRepayMoney().add(newWaitMoney) == null? BigDecimal.ZERO:channelOveCensus.getNewRepayMoney().add(newWaitMoney));
                    //计算新用户逾期率
                    if(channelOveCensus.getNewLoanCount() >0){
                        Integer newOveRate = (newWaitCount * 10000)/channelOveCensus.getNewLoanCount();
                        channelOveCensus.setNewOveRate(newOveRate == null ? 0 :newOveRate);
                    }
                    //查询渠道老用户 还款金额 数量 待收 数量
                    map = channelOveCensusDao.loanCountAndMoney(channelIdList.get(i),repayTime,1,30);
                    BigDecimal oldRepayMoney = optimic(map,"money") == null? BigDecimal.ZERO:optimic(map,"money");
                    Integer oldRepayCount = Integer.valueOf(map.get("count").toString());
                    channelOveCensus.setOldRepayCount(oldRepayCount);
                    channelOveCensus.setOldRepayMoney(oldRepayMoney);
                    map = channelOveCensusDao.loanCountAndMoney(channelIdList.get(i),repayTime,1,21);
                    BigDecimal oldWaitMoney = optimic(map,"money") == null? BigDecimal.ZERO:optimic(map,"money");
                    Integer oldWaitCount = Integer.valueOf(map.get("count").toString());
                    channelOveCensus.setOldRepayCount(oldRepayCount);
                    channelOveCensus.setOldRepayMoney(oldRepayMoney);
                    //老用户放款数量 放款金额
                    channelOveCensus.setOldLoanCount(channelOveCensus.getOldRepayCount()+oldWaitCount);
                    channelOveCensus.setOldLoanMoney(channelOveCensus.getOldRepayMoney().add(oldWaitMoney) == null? BigDecimal.ZERO:channelOveCensus.getNewRepayMoney().add(newWaitMoney));
                    //计算老用户逾期率
                    if(channelOveCensus.getOldLoanCount() >0 ){
                        Integer oldRate = (oldWaitCount *10000)/channelOveCensus.getOldLoanCount();
                        channelOveCensus.setOldOveRate(oldRate == null ?0:oldRate);
                    }
                    //查询用户展期数量 展期服务费 展期产品金额
                    map = channelOveCensusDao.extendCountAndMoney(channelIdList.get(i),repayTime);
                    BigDecimal extendMoney = optimic(map,"sumFee")== null? BigDecimal.ZERO:optimic(map,"sumFee");
                    Integer extendCount = Integer.valueOf(map.get("count").toString());
                    BigDecimal extendProductMoney = optimic(map,"moneyAmount") == null? BigDecimal.ZERO:optimic(map,"moneyAmount");
                    channelOveCensus.setExtendCount(extendCount);
                    channelOveCensus.setExtendMoney(extendMoney);
                    channelOveCensus.setExtendProductMoney(extendProductMoney);
                    //计算总放量 总还量 总逾期率
                    Integer allLoanCount = newRepayCount+newWaitCount+oldRepayCount+oldWaitCount+extendCount;
                    Integer allRepayCount = newRepayCount+oldRepayCount;
                    if(allLoanCount != 0){
                        Integer allOveRate = ((allLoanCount-allRepayCount) * 10000 )/allLoanCount ;
                        channelOveCensus.setAllOveRate(allOveRate);
                    }
                    channelOveCensus.setAllLoanCount(allLoanCount);
                    channelOveCensus.setAllRepayCount(allRepayCount);
                    channelOveCensus.setChannelId(channelIdList.get(i));
                    //判断是新增还是更新
                    params.put("repayTime",repayTime);
                    params.put("channelId",channelIdList.get(i));
                    List<ChannelOveCensus> list = channelOveCensusDao.findChannelOveCensus(params);
                    if(list.size() <= 0){
                        channelOveCensusDao.addChannelOveCensus(channelOveCensus);
                        b = true;
                        log.info("添加当日渠道逾期统计成功");
                    }else{
                        channelOveCensus.setUpdateTime(new Date());
                        channelOveCensusDao.updateChannelOveCensus(channelOveCensus);
                        b = true;
                        log.info("更新当日渠道逾期统计成功");
                    }
                }
            }
        }catch (Exception e){
            b = false;
            log.info("当日渠道逾期统计失败"+e.getMessage());
        }
        return b;
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
