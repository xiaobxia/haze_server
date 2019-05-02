package com.info.analyze.service;

import com.info.back.dao.IAppMarketFlowRecordDao;
import com.info.back.dao.IAppMarketTypeDao;
import com.info.back.pojo.AppMarketFlowRecord;
import com.info.back.pojo.AppMarketType;
import com.info.back.utils.DateUtils;
import com.info.web.dao.IReportDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.InfoReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 应用市场流量统计服务层实现
 *
 * @author tgy
 * @version [版本号, 2018年5月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Slf4j
@Service
public class AppMarketStaticsServiceImpl implements IAppMarketStaticsService {

    @Autowired
    private IAppMarketFlowRecordDao appMarketDao;

    @Autowired
    private IReportDao reportDao;

    @Autowired
    private IAppMarketTypeDao appMarketTypeDao;

    @Autowired
    private JedisCluster jedisCluster;


    /**
     * 定时器调用插入数据
     */
    @Override
    public void insertToAppmarketAnalyze() throws Exception {
        if (null == jedisCluster.get("insertToAppmarketAnalyze")) {
            jedisCluster.set("insertToAppmarketAnalyze", "running");
            //查询条件map
            Map<String, Object> params = new HashMap<String, Object>();
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowDay = sdf.format(now);
            Date nowDayDate = sdf.parse(nowDay);
            params.put("nowdayStart", DateUtils.getTheDayStart(now));
            params.put("nowdayEnd", DateUtils.getTommorrowStart(now));
            params.put("twodayEnd", DateUtils.getTommorrowEnd(now));
            params.put("nowday", nowDayDate);
            //查询当日的注册信息
            List<InfoReport> reportList = reportDao.selectInfoToday(params);
            //应用商店类型
            Set<String> appType = new HashSet<String>();
            //key 应用市场 value用户集合
            Map<String, List<String>> appMap = new LinkedHashMap<String, List<String>>();

            List<AppMarketFlowRecord> insertRecordList = new ArrayList<AppMarketFlowRecord>();
            List<AppMarketFlowRecord> updateRecordList = new ArrayList<AppMarketFlowRecord>();

            //遍历应用市场统计
            for (InfoReport report : reportList) {
                String marketName = report.getAppMarket();
                String uid = report.getUid();
                //统计有多少个应用市场
                appType.add(marketName);
                //如果是有效用户的话
                if (StringUtils.isNotEmpty(report.getUid())) {
                    //如果已经有该应用市场,则原基础加入
                    if (appMap.containsKey(marketName)) {
                        List<String> uidList = appMap.get(marketName);
                        uidList.add(uid);

                    }
                    //否则新增应用以及用户集合
                    else {
                        List<String> uidList = new ArrayList<String>();
                        uidList.add(uid);
                        appMap.put(marketName, uidList);
                    }
                }
            }

            //往apptype里面插入数据，如果有新的则插入，否则更改更新时间
            List<String> list1 = new ArrayList<String>(appType);
            List<AppMarketType> marketList = new ArrayList<AppMarketType>();
            List<AppMarketType> appMarketTypeList = appMarketTypeDao.selectAllType();
            List<String> list2 = new ArrayList<String>();

            for (AppMarketType aType : appMarketTypeList) {
                list2.add(aType.getAppType());
            }

            for (String type : list1) {
                if (!list2.contains(type)) {
                    AppMarketType appMarketType = new AppMarketType();
                    appMarketType.setAppType(type);
                    marketList.add(appMarketType);
                }
            }
            if (0 == marketList.size()) {
                log.info("no appType to insert or update!");
            } else {
                appMarketTypeDao.insertOrUpdate(marketList);
            }

            List<AppMarketType> appMarketTypes = appMarketTypeDao.selectAllType();

            //如果当前所有应用市场无数据，则返回
            if (0 == appMap.size()) {
                log.info("nowDay no appmarket register statistics data:{}", nowDay);
            }
            try {
                /*******数据查询 begin  list map key (userId ,appMarket)*******/
                //查询当天哪些用户做过实名认证
                List<Map<String, String>> realNameAuthIds = appMarketDao.selectTheDayNameAuthCunt(params);

                //查询当天哪些用户做过绑卡
                List<Map<String, String>> BindCardIds = appMarketDao.selectTheDayBindCardCunt(params);

                //查询当天哪些用户申请借款
                List<Map<String, String>> relyLoanIds = appMarketDao.selectTheDayRelyLoanCunt(params);

                //查询当天哪些用户放款成功
                params.put("sucStatus", Collections.singletonList(BorrowOrder.STATUS_HKZ));
                List<Map<String, String>> yesLoanIds = appMarketDao.selectTheDayyesLoanCunt(params);

                //查询当天哪些用户放款失败
                params.put("failStatus", Collections.singletonList(BorrowOrder.STATUS_FKSB));
                List<Map<String, String>> failLoanIds = appMarketDao.selectTheDayFailLoanCunt(params);

                //查询当天的总订单数
                int allOrdersCunt = appMarketDao.selectTheDayAllOrdersCunt(params);

                //查询当天逾期的用户数
                List<Map<String, String>> delayIds = appMarketDao.selectTheDayDelayCunt(params);

                //查询当日所有的注册量
                int allRegisterCunt = reportDao.selectTheDayAllRegisterCunt(params);
                /*******数据查询 end  list map key (end)*******/
                //装载统计数据 开始
                log.info("sum everyday appmarket info begin");
                for (AppMarketType appmarketType : appMarketTypes) {


                    String key = appmarketType.getAppType();
                    Map<String, Object> map = new HashMap<>();
                    map.put("appMarket", key);
                    map.put("installTime", nowDayDate);
                    AppMarketFlowRecord appMarketFlowRecord = appMarketDao.selectByMarketAndDate(map);
                    AppMarketFlowRecord record = null;
                    if (null != appMarketFlowRecord) {
                        record = appMarketFlowRecord;
                    } else {
                        record = new AppMarketFlowRecord();
                    }


                    //设置应用市场
                    record.setAppMarket(key);
                    //设置时间 今天
                    record.setInstallTime(nowDayDate);
                    //设置该应用市场今日注册量
                    int theDayRegCunt = 0;
                    if (null != appMap.get(key)) {
                        theDayRegCunt = appMap.get(key).size();
                    }
                    record.setRegisterCunt(theDayRegCunt);
                    //设置今日总注册量
                    record.setAllRegisterCunt(allRegisterCunt);
                    //设置更新时间
                    record.setUpdateTime(now);

                    //统计当天截至到now用户的实名认证情况
                    for (Map<String, String> dataMap : realNameAuthIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int authCunt = (null == record.getRealnameAuthCunt()) ? 0 : record.getRealnameAuthCunt();
                            record.setRealnameAuthCunt(authCunt + 1);
                        }
                    }
                    //统计当天截至到now用户的绑卡情况
                    for (Map<String, String> dataMap : BindCardIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int bindCardCunt = (null == record.getBindCardCunt()) ? 0 : record.getBindCardCunt();
                            record.setBindCardCunt(bindCardCunt + 1);
                        }
                    }

                    //统计当天截止到now的借款申请笔数
                    for (Map<String, String> dataMap : relyLoanIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int relyLoanCunt = (null == record.getRelyLoanCunt()) ? 0 : record.getRelyLoanCunt();
                            record.setRelyLoanCunt(relyLoanCunt + 1);
                        }
                    }

                    //统计当天截止到now的放款成功笔数
                    for (Map<String, String> dataMap : yesLoanIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int yesLoanCunt = (null == record.getYesLoanCunt()) ? 0 : record.getYesLoanCunt();
                            record.setYesLoanCunt(yesLoanCunt + 1);
                        }
                    }

                    //统计当天截止到now的放款失败笔数
                    for (Map<String, String> dataMap : failLoanIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int failLoanCunt = (null == record.getFailLoanCunt()) ? 0 : record.getFailLoanCunt();
                            record.setFailLoanCunt(failLoanCunt + 1);
                        }
                    }

                    //统计当天截止到now的逾期人数
                    for (Map<String, String> dataMap : delayIds) {
                        //同属于一个应用市场的用户
                        if (key.equals(dataMap.get("appMarket"))) {
                            int delayCunt = (null == record.getDelayCunt()) ? 0 : record.getDelayCunt();
                            record.setDelayCunt(delayCunt + 1);
                        }
                    }

                    //计算注册转化率 该应用市场下当日申请借款量/当日注册量
                    double regTrnRate = 0.0;
                    int relyCunt = (null == record.getRelyLoanCunt()) ? 0 : record.getRelyLoanCunt();
                    int registerCunt = (null == record.getRegisterCunt()) ? 0 : record.getRegisterCunt();
                    if (relyCunt > 0 && registerCunt > 0) {
                        regTrnRate = relyCunt / (registerCunt + 0.0);
                    }
                    record.setRegisterTransRate(regTrnRate);

                    //计算过件率:（成功放款的订单数+放款失败的订单数）/申请笔数
                    double crossRate = 0.0;
                    int yesLoanCunt = (null == record.getYesLoanCunt()) ? 0 : record.getYesLoanCunt();
                    int failLoanCunt = (null == record.getFailLoanCunt()) ? 0 : record.getFailLoanCunt();
                    int fsCount = yesLoanCunt + failLoanCunt;
                    if (fsCount > 0 && relyCunt > 0) {
                        crossRate = fsCount / (relyCunt + 0.0);
                    }
                    record.setCrossRate(crossRate);
                    dealDefaultValue(record);

                    if (null != record.getId()) {
                        updateRecordList.add(record);
                    } else {
                        insertRecordList.add(record);
                    }
                }
                if (0 < insertRecordList.size()) {
                    appMarketDao.insertAppmarket(insertRecordList);
                }
                if (0 < updateRecordList.size()) {
                    appMarketDao.updateAppmarket(updateRecordList);
                }

                log.info("sum everyday appmarket info end,record list:{}", insertRecordList.size() + updateRecordList.size());
                //装载统计数据 结束
                jedisCluster.del("insertToAppmarketAnalyze");

            } catch (Exception e) {
                log.error("nowDay appmarket register statistics error:{}", e);
                jedisCluster.del("insertToAppmarketAnalyze");
            }
        } else {
            log.info("last insertToAppmarketAnalyze task have not finish,so return!");
        }
    }

    /**
     * 处理默认值，防止为空
     *
     * @param record record
     */
    public void dealDefaultValue(AppMarketFlowRecord record) throws Exception {
        Class<? extends Object> clazz = record.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            // 获取属性的名字
            String name = f.getName();
            // 将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method mget = clazz.getMethod("get" + name);
            // 调用getter方法获取属性值
            Object invoke = mget.invoke(record);
            String type = f.getType().toString();
            int defaultVal = 0;
            //给int类型的设置值 其他的之前设置过
            if (type.endsWith("Integer") && null == invoke) {
                Method mset = clazz.getMethod("set" + name, Integer.class);
                // f.set(record, defaultVal);
                mset.invoke(record, defaultVal);
            }
        }
    }

    /**
     * 定时器每日插入应用市场类型数据
     */
    @Override
    public void inserAppMarketTypeEveryDay() throws Exception {
        List<AppMarketType> appMarketTypes = appMarketTypeDao.selectAllType();
        List<AppMarketFlowRecord> appMarketRecords = new ArrayList<>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDay = sdf.format(now);
        Date nowDayDate = sdf.parse(nowDay);
        log.info("every day insert AppMarketType record begin");
        if (null == appMarketTypes || 0 == appMarketTypes.size()) {
            log.info("every day insert AppMarketType record end for no appType");
            return;
        }
        for (AppMarketType appType : appMarketTypes) {
            AppMarketFlowRecord record = new AppMarketFlowRecord();
            record.setAppMarket(appType.getAppType());
            record.setCrossRate(0.0);
            record.setRegisterTransRate(0.0);
            record.setDelayCunt(0);
            record.setAllRegisterCunt(0);
            record.setYesLoanCunt(0);
            record.setRelyLoanCunt(0);
            record.setBindCardCunt(0);
            record.setRealnameAuthCunt(0);
            record.setRegisterCunt(0);
            record.setInstallTime(nowDayDate);
            record.setFailLoanCunt(0);
            record.setUpdateTime(now);
            appMarketRecords.add(record);
        }
        appMarketDao.insertList(appMarketRecords);
        log.info("every day insert AppMarketType record end");
    }

    /**
     * 分页查询应用市场流量统计
     *
     * @param params params
     * @return return
     */
    @Override
    public List<AppMarketFlowRecord> selectPageList(Map<String, Object> params) {
        List<AppMarketFlowRecord> appMarketFlowRecords = appMarketDao.selectPageList(params);
        if (null == appMarketFlowRecords) {
            return new ArrayList<>();
        }
        return appMarketFlowRecords;
    }

    /**
     * 分页查询应用市场流量统计数量
     *
     * @param params params
     * @return int
     */
    @Override
    public int selectPageCunt(Map<String, Object> params) {
        Integer count = appMarketDao.selectPageCunt(params);
        if (null == count) {
            return 0;
        }
        return count;
    }

    /**
     * 查询应用商店列表
     *
     * @return list
     */
    @Override
    public List<AppMarketType> selectMarketTypeList() {
        List<AppMarketType> appMarketTypes = appMarketTypeDao.selectAllType();
        if (null == appMarketTypes) {
            return new ArrayList<>();
        }
        return appMarketTypes;
    }

    /**
     * 查询所有的app应用市场统计
     *
     * @return list
     */
    @Override
    public List<AppMarketFlowRecord> selectAllList(Map<String, Object> params) {
        List<AppMarketFlowRecord> appMarketFlowRecords = appMarketDao.selectAllList(params);
        if (null == appMarketFlowRecords) {
            return new ArrayList<>();
        }
        return appMarketFlowRecords;
    }

    /**
     * 查询所有的app应用市场统计数量
     *
     * @return list
     */
    @Override
    public Integer selectAllListCunt(Map<String, Object> params) {
        Integer count = appMarketDao.selectAllListCunt(params);
        if (null == count) {
            return 0;
        }
        return count;
    }
}
