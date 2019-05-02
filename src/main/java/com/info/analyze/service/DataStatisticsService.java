package com.info.analyze.service;

import com.alibaba.fastjson.JSON;
import com.info.analyze.dao.IDataStatisticsDao;
import com.info.analyze.pojo.DataStatistics;
import com.info.analyze.until.StatisticsDateUntils;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
@Slf4j
@Service
public class DataStatisticsService implements IDataStatisticsService {

    @Autowired
    private IDataStatisticsDao dataStatisticsDao;
    @Autowired
    private IStatisticsService statisticsService;
    @Autowired
    private IPaginationDao paginationDao;

    /**
     * 插入统计数据
     * @param dataStatistics date
     * @return int
     */
    public int insertDataStatics(DataStatistics dataStatistics){
        int num = 0;
        if (dataStatistics != null) {
            num = dataStatisticsDao.insertDataStatics(dataStatistics);
        }
        return num;
    }

    /**
     * 删除所有的数据
     * @return int
     */
    public int deleDataStatisticsAll(){
        return dataStatisticsDao.deleDataStatisticsAll();
    }
    /**
     * 更新统计数据
     * @param dataStatistics date
     * @return int
     */
    public int updateDataStatics(DataStatistics dataStatistics){
        int num = 0;
        if (dataStatistics != null) {
            num = dataStatisticsDao.updateDataStatics(dataStatistics);
        }
        return num;
    }

    /**
     * 查询统计表的数据
     * @param hashMap map
     * @return list
     */
    public List<DataStatistics> selectDataStatistics(Map<String,Object> hashMap) {
        return dataStatisticsDao.selectDataStatistics(hashMap);
    }

    /**
     * 分页获取数据
     * @param params params
     * @return page
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<DataStatistics> findDataStatisticsPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "DataStatistics");
        return paginationDao.findPage("selectDataStatistics", "selectDataStatisticsCount", params, "analyze");
    }

    /**
     * 复贷率的统计 （ 根据指定的开始月份以及结束的月份区间计算 ）
     * @param startMonth startMonth
     * @param endMonth endMonth
     * @return map
     */
    @Override
    public Map<String,Object> getLoanAgainRate(String startMonth,String endMonth){
        Map<String,Object> map = new HashMap<>();
        map.put("startMonth",startMonth);
        map.put("endMonth",endMonth);
        //先查询用户借款人数
        Integer allCount = statisticsService.loanAginCount(map);
        //在查询借款两次的（包括两次的人数）
        map.put("loanAginFlag",1);
        Integer loanAginCount = statisticsService.loanAginCount(map);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("loanAginCount",loanAginCount);
        returnMap.put("allCount",allCount);
        if (allCount == 0 || loanAginCount == 0) {
            returnMap.put("loanAgainRate",0);
            return returnMap;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        double rate = (double)loanAginCount/allCount;
        returnMap.put("loanAgainRate",df.format(rate));
        return returnMap;
    }


    /**
     * 根据标志位 是否 获取以前的数据并进行数据插入
     *
     * @param getBeforeFlag
     *      true 插入
     *      false 只插入当天数据
     */
    @Override
    public void insertDataStatisticsByFlag(boolean getBeforeFlag){
        try{
            //首先插入数据
            Map<String,Object> getBeforeDataFlagMap = new HashMap<>();
            if (getBeforeFlag) {
                getBeforeDataFlagMap.put("getBeforeDataFlag",1); // 获取以前数据的标志，否则只获取当天数据
            } else {
                //获取当前统计时间的前一天的数据
                String beforeTime = StatisticsDateUntils.getBeforeDayTime();
                getBeforeDataFlagMap.put("beforeTime",beforeTime);
            }
            List<Map<String,Object>> applyUserCountMapList = statisticsService.selectBorrowApplyCount(getBeforeDataFlagMap);
            if (applyUserCountMapList != null) {
                for (Map<String,Object> applyUserCountMap :applyUserCountMapList ) {
                    Map<String,Object> exitesMap = new HashMap<>();
                    Object createAt = applyUserCountMap.get("createAt");
                    if (createAt == null || applyUserCountMap == null) {
                        continue;
                    } else {
                        exitesMap.put("statisticsTime",createAt);
                        exitesMap.put("flag",DataStatistics.DAYFLAG.toString());
                        //判断数据表是否存在记录
                        List<DataStatistics> dataStatisticsExit = this.selectDataStatistics(exitesMap);
                        if (dataStatisticsExit != null && dataStatisticsExit.size() > 0) {
                            //若存在数据，则进行更新
                            applyUserCountMap.put("id",dataStatisticsExit.get(0).getId());
                            insertApplyUserCountOrUpdateDataStatistics(applyUserCountMap,true);
                        } else {
                            //插入数据(在执行手动任务时，防止将当前时间也统计在内)
                            if (!StatisticsDateUntils.isTodayTime(String.valueOf(createAt))) {
                                insertApplyUserCountOrUpdateDataStatistics(applyUserCountMap,false);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("insertDataStatisticsByFlag error:{}",e);
        }

    }


    /**
     * 插入新老用户申请统计数目
     * @param applyUserCountMap apply
     * @param flag 标志是否进行数据更新 false 插入数据 true 进行数据更新
     */
    public void insertApplyUserCountOrUpdateDataStatistics(Map<String,Object> applyUserCountMap,boolean flag){
        try{
            String timeStr = String.valueOf(applyUserCountMap.get("createAt"));
            //插入数据
            DataStatistics dataStatistics = new DataStatistics();
            Integer newApplyUserCount = applyUserCountMap.get("newApplyBorrowUserCount") == null?0:Integer.parseInt(applyUserCountMap.get("newApplyBorrowUserCount").toString());
            Integer oldApplyUserCount = applyUserCountMap.get("oldApplyBorrowUserCount") == null?0:Integer.parseInt(applyUserCountMap.get("oldApplyBorrowUserCount").toString());
            dataStatistics.setApplyUserNewCount(newApplyUserCount);
            dataStatistics.setApplyUserOldCount(oldApplyUserCount);
            //申请人数汇总
            dataStatistics.setApplyUserCount(newApplyUserCount+oldApplyUserCount);
            dataStatistics.setStatisticsTime(timeStr); //统计时间
            //获取核准率
            DataStatistics dataCheckRate = this.getcheckRateCount(DataStatistics.DAYFLAG,timeStr);
            if (dataCheckRate != null) {
                dataStatistics.setCustomerCheckRate(dataCheckRate.getCustomerCheckRate());
                dataStatistics.setOldCustomerCheckRate(dataCheckRate.getOldCustomerCheckRate());
                dataStatistics.setNewCustomerCheckRate(dataCheckRate.getNewCustomerCheckRate());
            }
            dataStatistics.setFlag(DataStatistics.DAYFLAG);
            dataStatistics.setCreateTime(new Date()); //创建时间
            int insertOrUpdateFlag = 0;
            if (!flag) {
                insertOrUpdateFlag = insertDataStatics(dataStatistics);
                log.info("insert data");
            }if(flag) {
                if (applyUserCountMap.get("id") != null) {
                    Integer id =  Integer.parseInt(applyUserCountMap.get("id").toString());
                    dataStatistics.setId(id);
                    insertOrUpdateFlag = updateDataStatics(dataStatistics);
                    log.info("update exists data");
                }
            }
            log.info("insertOrUpdateFlag =:{},timeStr=:{}",insertOrUpdateFlag,timeStr);
            if (insertOrUpdateFlag == 1) {
                //更新其他的统计数据
                updateBeforeAllDataStatistics(dataStatistics,flag);
            }
        }catch (Exception e){
            log.error("insertApplyUserCountOrUpdateDataStatistics error:{}",e);
        }

    }

    /**
     * 更新 统计表的内容
     * @param dataStatistics date
     */
    public void updateBeforeAllDataStatistics(DataStatistics dataStatistics,boolean flag){
        try{
            //更新的id
            Integer id = dataStatistics.getId();
            DataStatistics updateDataStatistics = new DataStatistics();
            Map<String,Object> map = new HashMap<>();
            //统计时间
            String timeStr = dataStatistics.getStatisticsTime();
            map.put("createAt",timeStr);
            Map<String,Object> loanUserCountMap = statisticsService.selectBorrowLoanUserCount(map); //成功放款的新老用户人数
            Map<String,Object> loanMoneyCountMap = statisticsService.selectBorrowMoneyCount(map); //新老用户的放款钱数
            Integer registCount = statisticsService.selectRegistCount(map); //注册用户数
            updateDataStatistics.setRegistCount(registCount);
            if (loanUserCountMap != null) {
                Integer loanUserOldCount = loanUserCountMap.get("oldBorrowUserLoanCount") == null?0:Integer.parseInt(loanUserCountMap.get("oldBorrowUserLoanCount").toString());
                Integer loanUserNewCount = loanUserCountMap.get("newBorrowUserLoanCount") == null?0:Integer.parseInt(loanUserCountMap.get("newBorrowUserLoanCount").toString());
                updateDataStatistics.setLoanUserNewCount(loanUserNewCount);
                updateDataStatistics.setLoanUserOldCount(loanUserOldCount);
                updateDataStatistics.setLoanUserCount(loanUserNewCount+loanUserOldCount);
            }
            if (loanMoneyCountMap != null) {
                int loanMoneyNewCount = loanMoneyCountMap.get("newMoneyCount") == null?0:(int)Float.parseFloat(loanMoneyCountMap.get("newMoneyCount").toString());
                int loanMoneyOldCount = loanMoneyCountMap.get("oldMoneyCount") == null?0:(int)Float.parseFloat(loanMoneyCountMap.get("oldMoneyCount").toString());
                updateDataStatistics.setLoanMoneyNewCount(loanMoneyNewCount);
                updateDataStatistics.setLoanMoneyOldCount(loanMoneyOldCount);
                updateDataStatistics.setLoanMoneyCount(loanMoneyNewCount+loanMoneyOldCount);
            }
            updateDataStatistics.setUpdateTime(new Date()); //更新时间
            updateDataStatistics.setId(id);
            log.info("updateDataStatistics =:{}" ,JSON.toJSONString(updateDataStatistics));
            //这里进行更新操作

            int dayUpdateFlag = this.updateDataStatics(updateDataStatistics);
            log.info("dayUpdateFlag =:{}",dayUpdateFlag);
            if (dayUpdateFlag == 1) {
                //如果周的最后一天开始统计
                if (StatisticsDateUntils.judgeAWeekLastDay(timeStr)) {
                    //获取这一周所有的数据然后进行统计
                    Map<String,Object> weekDay = StatisticsDateUntils.getAWeekAllDay(timeStr);
                    weekDay.put("flag",DataStatistics.DAYFLAG.toString()); //统计天
                    DataStatistics dataStatisticsWeekOrMonth = this.countMonthOrWeekDataStatistics(weekDay,timeStr,DataStatistics.WEEKFLAG);
                    dataStatisticsWeekOrMonth.setStatisticsTime(timeStr);
                    dataStatisticsWeekOrMonth.setFlag(DataStatistics.WEEKFLAG);
                    //获取核准率
                    DataStatistics dataCheckRate = this.getcheckRateCount(DataStatistics.WEEKFLAG,timeStr);
                    if(dataCheckRate!=null){
                        dataStatisticsWeekOrMonth.setOldCustomerCheckRate(dataCheckRate.getOldCustomerCheckRate());
                        dataStatisticsWeekOrMonth.setNewCustomerCheckRate(dataCheckRate.getNewCustomerCheckRate());
                    }

                    dataStatisticsWeekOrMonth.setStatisticsTime(timeStr);
                    dataStatisticsWeekOrMonth.setUpdateTime(new Date());
                    dataStatisticsWeekOrMonth.setCreateTime(new Date());

                    dataStatisticsWeekOrMonth = this.applyUserCountAndLoanUserCountByWeekOrMonth(dataStatisticsWeekOrMonth,weekDay);
                    int insertWeekFlag = this.insertDataStatics(dataStatisticsWeekOrMonth);
                    if (insertWeekFlag == 1) {
                        log.info("insert week statistics success");
                    } else {
                        log.info("insert week statistics failed");
                    }
                }
                //如果是月的最后的一天开始统计
                if (StatisticsDateUntils.judgeAMonthLastDay(timeStr)) {
                    //获取这个月所有的数据进行统计
                    Map<String,Object> byMonthMap = new HashMap<>();
                    //月日期map
                    Map<String,Object> monthMap = StatisticsDateUntils.getMonthBetweenStartAndEnd(timeStr);
                    byMonthMap.putAll(monthMap);
                    byMonthMap.put("flag",DataStatistics.DAYFLAG.toString());
                    DataStatistics dataStatisticsWeekOrMonth = this.countMonthOrWeekDataStatistics(byMonthMap,timeStr,DataStatistics.MONTHFLAG);
                    dataStatisticsWeekOrMonth.setFlag(DataStatistics.MONTHFLAG);
                    //统计的是未还的钱数（按月统计）
                    Integer noReturnMoney = statisticsService.selectNoReturnMoney();
                    dataStatisticsWeekOrMonth.setLoanBlance(noReturnMoney);
                    //时间
                    dataStatisticsWeekOrMonth.setStatisticsTime(timeStr);
                    //获取核准率
                    DataStatistics dataCheckRate = this.getcheckRateCount(DataStatistics.MONTHFLAG,timeStr);
                    if(dataCheckRate!=null){
                        dataStatisticsWeekOrMonth.setOldCustomerCheckRate(dataCheckRate.getOldCustomerCheckRate());
                        dataStatisticsWeekOrMonth.setNewCustomerCheckRate(dataCheckRate.getNewCustomerCheckRate());
                    }

                    dataStatisticsWeekOrMonth.setUpdateTime(new Date());
                    dataStatisticsWeekOrMonth.setCreateTime(new Date());


                    Map<String,Object> monthParamsMap = new HashMap<>();
                    monthParamsMap.put("monthTime",timeStr);
                    dataStatisticsWeekOrMonth = this.applyUserCountAndLoanUserCountByWeekOrMonth(dataStatisticsWeekOrMonth,monthParamsMap);

                    int insertWeekOrMonth = this.insertDataStatics(dataStatisticsWeekOrMonth);
                    if (insertWeekOrMonth == 1) {
                        log.info("insert month statistics success");
                    }
                }
            }

        }catch (Exception e){
            log.error("update sql error:{}",e);
        }



    }

    /**
     * 按周月统计申请人数以及放款人数
     * @param dataStatisticsWeekOrMonth  date
     * @param map map
     * @return data
     */
    private DataStatistics applyUserCountAndLoanUserCountByWeekOrMonth(DataStatistics dataStatisticsWeekOrMonth,Map<String,Object> map){
        //周统计的借款人数
        HashMap<String,Object> applyUserCountMapByWeek = statisticsService.selectAppyUserCountByMonthOrWeek(map);
        if (applyUserCountMapByWeek != null) {
            Integer applyUserNewCount = applyUserCountMapByWeek.get("applyNewUserCount") == null?0:Integer.parseInt(applyUserCountMapByWeek.get("applyNewUserCount").toString());
            Integer applyUserOldCount = applyUserCountMapByWeek.get("applyOldUserCount") == null?0:Integer.parseInt(applyUserCountMapByWeek.get("applyOldUserCount").toString());
            Integer applyUserCount = applyUserCountMapByWeek.get("applyUserCount") == null?0:Integer.parseInt(applyUserCountMapByWeek.get("applyUserCount").toString());
            dataStatisticsWeekOrMonth.setApplyUserNewCount(applyUserNewCount);
            dataStatisticsWeekOrMonth.setApplyUserOldCount(applyUserOldCount);
            dataStatisticsWeekOrMonth.setApplyUserCount(applyUserCount);
        }
        //周统计的放款人数
        HashMap<String,Object> loanUserCountMapByWeek =  statisticsService.selectLoanUserCountByMonthOrWeek(map);
        if (loanUserCountMapByWeek != null) {
            Integer loanUserOldCount = loanUserCountMapByWeek.get("loanUserOldCount") == null?0:Integer.parseInt(loanUserCountMapByWeek.get("loanUserOldCount").toString());
            Integer loanUserNewCount = loanUserCountMapByWeek.get("loanUserNewCount") == null?0:Integer.parseInt(loanUserCountMapByWeek.get("loanUserNewCount").toString());
            Integer loanUserCount = loanUserCountMapByWeek.get("loanUserCount") == null?0:Integer.parseInt(loanUserCountMapByWeek.get("loanUserCount").toString());
            dataStatisticsWeekOrMonth.setLoanUserNewCount(loanUserNewCount);
            dataStatisticsWeekOrMonth.setLoanUserOldCount(loanUserOldCount);
            dataStatisticsWeekOrMonth.setLoanUserCount(loanUserCount);
        }
        return dataStatisticsWeekOrMonth;
    }




    /**
     * 根据月 周 统计将数据综合起来
     * @param map map
     * @param timeStr str
     * @param weekOrMonthOrDay data
     * @return data
     */
    private DataStatistics countMonthOrWeekDataStatistics(Map<String,Object> map,String timeStr,Integer weekOrMonthOrDay){
        DataStatistics updateMonthOrWeekCount = new DataStatistics();
        List<DataStatistics> monthDataStatisticsList = this.selectDataStatistics(map);
        DataStatistics countMonthMessageData = null;
        if (monthDataStatisticsList != null) {
            countMonthMessageData = this.monthOrWeekCountMessage(monthDataStatisticsList);
            if (countMonthMessageData != null) {
                //贷款钱数
                Integer loanMoneyOldCount = countMonthMessageData.getLoanMoneyOldCount() == null?0:countMonthMessageData.getLoanMoneyOldCount();
                Integer loanMoneyNewCount = countMonthMessageData.getLoanMoneyNewCount() == null?0:countMonthMessageData.getLoanMoneyNewCount();
                updateMonthOrWeekCount.setLoanMoneyOldCount(loanMoneyOldCount);
                updateMonthOrWeekCount.setLoanMoneyNewCount(loanMoneyNewCount);
                //新老用户合计放款钱数
                updateMonthOrWeekCount.setLoanMoneyCount(loanMoneyNewCount+loanMoneyOldCount);
                //注册用户数
                Integer registCount = countMonthMessageData.getRegistCount() == null?0:countMonthMessageData.getRegistCount();
                updateMonthOrWeekCount.setRegistCount(registCount);
            }
        }
        //获取核准率
        DataStatistics dataCheckRate = this.getcheckRateCount(weekOrMonthOrDay,timeStr);
        if (dataCheckRate != null) {
            updateMonthOrWeekCount.setCustomerCheckRate(dataCheckRate.getCustomerCheckRate());
            updateMonthOrWeekCount.setOldCustomerCheckRate(dataCheckRate.getOldCustomerCheckRate());
            updateMonthOrWeekCount.setNewCustomerCheckRate(dataCheckRate.getNewCustomerCheckRate());
        }
        return updateMonthOrWeekCount;
    }

    /**
     * 根据相应参数计算核准率
     * @param customerPassLoan customerPassLoan
     * @param customerNum customerNum
     * @return str
     */
    private String formateCheckRate(Integer customerPassLoan,Integer customerNum){
        DecimalFormat df = new DecimalFormat("######0.0000");
        if (customerPassLoan == 0 || customerNum == 0) {
            return "0";
        }
        double rate = (double)customerPassLoan/customerNum;
        return df.format(rate);
    }

    /**
     * 将核准率所有结果拼装到一个对象中去
     * @param dayOrWeekOrMonthFlag dayOrWeekOrMonthFlag
     * @param strTime strTime
     * @return data
     */
    private DataStatistics getcheckRateCount(Integer dayOrWeekOrMonthFlag,String strTime){
        DataStatistics data = new DataStatistics();
        //获取核准率
        Map<String,String> map = this.getcheckRateCountMap(dayOrWeekOrMonthFlag,strTime);
        if (map == null) {
            return null;
        }
        String newCustomerResult = map.get("newCustomerResult") == null?"0":map.get("newCustomerResult");
        String oldCustomerResult = map.get("oldCustomerResult") == null?"0":map.get("oldCustomerResult");
        String lastCustomerResult = map.get("lastCustomerResult") == null?"0":map.get("lastCustomerResult");
        data.setNewCustomerCheckRate(newCustomerResult);
        data.setOldCustomerCheckRate(oldCustomerResult);
        data.setCustomerCheckRate(lastCustomerResult);
        return data;
    }


    /**
     * 根据标志位计算 天、周、月 的 核准率
     * @param dayOrWeekOrMonthFlag dayOrWeekOrMonthFlag
     * @param strTime str
     * @return map
     */
    public Map<String,String> getcheckRateCountMap(Integer dayOrWeekOrMonthFlag,String strTime){
        Map<String,String> resultMap = new HashMap<>();
        String newCustomerResult = "0",oldCustomerResult = "0",lastCustomerResult = "0";
        Integer tempCustomerNum = 0,tempCustomerPassLoan = 0;
        for (int i = 0;i<=1;i++) {
            Map<String,Object> paramsMap = new HashMap<>();
            //新老用户标志 i = 0 新用户  i = 1 老用户
            paramsMap.put("customerType",i+"");
            if (DataStatistics.DAYFLAG.equals(dayOrWeekOrMonthFlag)) {
                paramsMap.put("dayTime",strTime);
            }
            if (DataStatistics.WEEKFLAG.equals(dayOrWeekOrMonthFlag)) {
                paramsMap.put("weekTime",1);
                paramsMap.putAll(StatisticsDateUntils.getAWeekAllDay(strTime));
            }
            if (DataStatistics.MONTHFLAG.equals(dayOrWeekOrMonthFlag)) {
                paramsMap.put("monthTime",strTime);
            }
            //统计借款人数
            Integer customerNum = statisticsService.selectCheckRateCount(paramsMap);
            //统计新用户通过借款的人数
            paramsMap.put("passLoanFlag",1);
            Integer customerPassLoan = statisticsService.selectCheckRateCount(paramsMap);
            tempCustomerNum += customerNum;
            tempCustomerPassLoan += customerPassLoan;
            if (i == 0) {
                //计算通过数条件： 复审时间 + （放款通过（pay_status=0000）or status = 22 （放款中））
                //新用户核准率 = 新用户通过订单数 / 新用户借款订单数
                newCustomerResult = formateCheckRate(customerPassLoan,customerNum);
                resultMap.put("newCustomerResult",newCustomerResult);
            }
            if (i == 1) {
                //老用户核准率 = 老用户通过订单数 / 新用户借款订单数
                oldCustomerResult = formateCheckRate(customerPassLoan, customerNum);
                resultMap.put("oldCustomerResult",oldCustomerResult);

                //汇总新老用户过后的核准率 = 通过订单数 / 借款订单数
                lastCustomerResult = formateCheckRate(tempCustomerPassLoan, tempCustomerNum);
                resultMap.put("lastCustomerResult",lastCustomerResult);
            }
        }
        return resultMap;
    }


    /**
     * 周 月统计
     * @param list list
     * @return data
     */
    private DataStatistics monthOrWeekCountMessage(List<DataStatistics> list){
        DataStatistics data = new DataStatistics();
        Integer loanOldUserMoneyCount = 0,loanNewUserMoneyCount = 0,registCount = 0;
        for (DataStatistics dataStatistics:list) {
            if (dataStatistics == null) {continue;}
            //申请人数
            Integer applyNew = dataStatistics.getApplyUserNewCount() == null?0:dataStatistics.getApplyUserNewCount();
            Integer applyOld = dataStatistics.getApplyUserOldCount() == null?0:dataStatistics.getApplyUserOldCount();
//            applyNewUserCount += applyNew;
//            applyOldUserCount += applyOld;
            //放款人数
            Integer loanNewCount = dataStatistics.getLoanUserNewCount() == null?0:dataStatistics.getLoanUserNewCount();
            Integer loanOldCount = dataStatistics.getLoanUserOldCount() == null?0:dataStatistics.getLoanUserOldCount();
//            loanOldUserCount += loanOldCount;
//            loanNewUserCount += loanNewCount;
            //放款钱数
            Integer loanNewMoneyCount = dataStatistics.getLoanMoneyNewCount() == null?0:dataStatistics.getLoanMoneyNewCount();
            Integer loanOldMoneyCount = dataStatistics.getLoanMoneyOldCount() == null?0:dataStatistics.getLoanMoneyOldCount();
            loanOldUserMoneyCount += loanOldMoneyCount;
            loanNewUserMoneyCount += loanNewMoneyCount;
            //注册人数
            registCount += dataStatistics.getRegistCount() == null?0:dataStatistics.getRegistCount();
        }
//        //申请人数
//        data.setApplyUserNewCount(applyNewUserCount);
//        data.setApplyUserOldCount(applyOldUserCount);
//        //放款人数
//        data.setLoanUserNewCount(loanNewUserCount);
//        data.setLoanUserOldCount(loanOldUserCount);
        //放款钱数
        data.setLoanMoneyNewCount(loanNewUserMoneyCount);
        data.setLoanMoneyOldCount(loanOldUserMoneyCount);
        //注册人数
        data.setRegistCount(registCount);
        return data;
    }
}
