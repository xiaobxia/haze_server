package com.info.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.info.constant.Constant;
import com.info.web.dao.IChannelReportDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.*;
import com.info.web.util.DateUtil;
import com.info.web.util.FastJsonUtils;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.info.web.pojo.BorrowOrder.*;

@Slf4j
@Service
public class ChannelReportService implements IChannelReportService {

    @Resource
    private IChannelReportDao channelReportDao;
    @Autowired
    private IPaginationDao paginationDao;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private IChannelInfoService channelInfoService;
    @Autowired
    private IBorrowOrderService iBorrowOrderService;

    // Redis key前缀
    private final static String CHANNEL_REPORT = "channel_report";

    @Override
    public String getChannelIdByCode(String channelCode) {
        return channelReportDao.getChannelIdByCode(channelCode);
    }

    @Override
    public int getRegisterNow(String channelId) {
        return channelReportDao.getRegisterNow(channelId);
    }

    @Override
    public int getRegisterRealNow(String channelId) {
        return channelReportDao.getRegisterRealNow(channelId);
    }

    @Override
    public ChannelReport getChannelReportById(Integer id) {
        return channelReportDao.findChannelById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<ChannelReport> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "ChannelReport");
        PageConfig<ChannelReport> pageConfig;
        pageConfig = paginationDao.findPage("findAll", "findAllCount", params,
                "web");
        return pageConfig;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<OutChannelLook> findPageOut(HashMap<String, Object> params){
        params.put(Constant.NAME_SPACE, "ChannelReport");
        PageConfig<OutChannelLook> pageConfig;
        pageConfig = paginationDao.findPage("findOut", "findOutCount", params,
                "web");
       /* OutChannelLook outChannelLook = new OutChannelLook();
        outChannelLook.setId(0);
        pageConfig.getItems().set(pageConfig.getItems().size()+1,outChannelLook);*/
        List<OutChannelLook> list = new ArrayList<OutChannelLook>();
        //循环处理数据 rengyao fangkuan
        for (OutChannelLook report : pageConfig.getItems()) {
            //通过渠道id 查询出所涉及的用户
             List<String> idList=channelInfoService.findUserId(report.getId());
             if(idList.size()>0){
                 //放款笔数
                 int loanCount =channelInfoService.findLoanCount(report.getReportDate(),idList);
                 report.setLoanCount(loanCount);
                 //还款笔数
                 int repayCount =channelInfoService.findRepayCount(report.getReportDate(),idList);
                 report.setRepaymentCount(repayCount);
                 //当日申请笔数
                 int applyCount = channelInfoService.findApplyCount(report.getReportDate(),idList);
                 report.setBorrowApplyCount(applyCount);
             }
            //注册率 注册数量/点击数量
            int uvCount = report.getUvCount();
            int registCount = report.getRegisterCountResult();
            DecimalFormat df = new DecimalFormat("0.00");
            if(uvCount != 0){
                double registRatio = registCount*(1.0)/uvCount*(1.0);
                report.setRegistRatio(df.format(registRatio));
            }else{
                report.setRegistRatio("0.00");
            }
            //下款率 放款比数/申请总数
            if(report.getRegisterCountResult() != 0){
                double loanRate = report.getLoanCount()*(1.0)/report.getRegisterCountResult()*(1.0);
                report.setLoanRatio(df.format(loanRate));
            }else{
                report.setLoanRatio("0.00");
            }
            //回款率 还款笔数/放款笔数
            if(report.getLoanCount() != 0){
                double repayRate = report.getRepaymentCount()*(1.0)/report.getLoanCount()*(1.0);
                report.setRepayRatio(df.format(repayRate));
            }else{
                report.setRepayRatio("0.00");
            }
            //申请率 申请笔数/注册总数
            if(report.getRegisterCountResult() != 0){
                double applyRate = report.getBorrowApplyCount() * (1.0)/report.getRegisterCountResult()*(1.0);
                report.setApplyRatio(df.format(applyRate));
            }else{
                report.setApplyRatio("0.00");
            }
            list.add(report);
        }
        //自然流量的统计
        pageConfig.setItems(list);
        return pageConfig;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<ChannelReport> findPrPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "ChannelReport");
        PageConfig<ChannelReport> pageConfig;
        pageConfig = paginationDao.findPage("findPrAll", "findPrAllCount",
                params, "web");
        return pageConfig;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<ChannelReport> findSumPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "ChannelReport");
        PageConfig<ChannelReport> pageConfig;
        pageConfig = paginationDao.findPage("findSumAll", "findSumAllCount",
                params, "web");
        return pageConfig;
    }

    @Override
    public Integer findPrParamsCount(Map<String, Object> params) {
        return channelReportDao.findPrAllCount(params);
    }

    @Override
    public Integer findParamsCount(Map<String, Object> params) {
        return channelReportDao.findAllCount(params);
    }

    @Override
    public List<ChannelReport> getAllPrChannelReports(Map<String, Object> params) {
        return channelReportDao.findPrAll(params);
    }

    @Override
    public List<ChannelReport> getAllChannelReports(Map<String, Object> params) {
        return channelReportDao.findAll(params);
    }

    @Override
    public boolean saveChannelReport(String nowTime) {
        StopWatch sw = new StopWatch();
        sw.start();
        log.info("saveChannelReport start:{}", nowTime);
        boolean bool = false;
        Map<String, Object> param = new HashMap<>();
        String key = CHANNEL_REPORT;
        try {
            /* 设置查询时间 */

            Calendar cal = Calendar.getInstance();
            /* 判断当前时间是否在凌晨两点之前，是，重新统计前一天,用于补漏定时任务未统计到的数据*/
            Integer checkDate = 0;

            String time = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
            checkDate = Integer.valueOf(time.substring(11, 13));

            if (checkDate > 0 && checkDate < 3) {
                cal.add(Calendar.DATE, -1);
                jedisCluster.del(key);
            }

            String nowDate = DateUtil
                    .getDateFormat(cal.getTime(), "yyyy-MM-dd");

            if (nowTime != null) {
                nowDate = nowTime;
            }
            String code = jedisCluster.get(key);
            if (code != null) {
                log.info("saveChannelReport start 有任务正在执行，以放弃该任务，任务进入时间：{};key值为:{} ", code, key);
                return bool;
            }
            jedisCluster.setex(key, 10800, time);
            param.put("startDate", nowDate);
            DateUtil.sqlOptimization4DateFormat(param, "startDate");
            //param.put("endDate", nowDate + " 23:59:59");
            param.put("nowDate", cal.getTime());

            /*创建分页查询*/
//			HashMap<String, Object> params = new HashMap<String, Object>();
            PageConfig<Map<String, Object>> pm = new PageConfig<>();
            pm.setTotalPageNum(1);
//			params.put(Constant.PAGE_SIZE, "50");

            List<ChannelReport> channelIdList = channelReportDao
                    .findChannelId();

//            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
            List<List<ChannelReport>> array = null;
            if (CollectionUtils.isNotEmpty(channelIdList)) {
                int pageSize = 100;
                int total = channelIdList.size();
                int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
                array = new ArrayList<>();
                for (int i = 0; i < pageCount; i++) {
                    int start = i * pageSize;
                    int end = start + pageSize > total ? total : start + pageSize;
                    List<ChannelReport> subList = channelIdList.subList(start, end);
                    array.add(subList);
                }
            }
            log.info("saveChannelReport array:{}", JSON.toJSONString(array));
            for (List<ChannelReport> lists : array) {
                Integer[] channelids = new Integer[lists.size()];
                for (int k = 0; k < lists.size(); k++) {
                    channelids[k] = lists.get(k).getChannelid();
                    if(channelids[k] == 0) {
                        System.out.println("1");
                    }
                    jedisCluster.set("channelReport_" + lists.get(k).getChannelid(), FastJsonUtils.toJson(lists.get(k)).toString());
                }
                param.put("channelids", channelids);
                // 注册量
                List<Map<String, Object>> registerCountMap = channelReportDao.findRegisterCount(param);
//						channelReport.setRegisterCount(1);
                for (Map<String, Object> map : registerCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setRegisterCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
                //统计每天uv 数量
                List<Map<String,Object>> uvCountMap = channelReportDao.findUvCount(param);
                for(Map<String,Object> map : uvCountMap){
                    Object val = map.get("channelId");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if(channelReporta != null){
                        channelReporta.setUvCount(map.get("uvCount") == null ? 0 : Integer.parseInt(map.get("uvCount").toString()));
                        //jedisCluster.del("channelReport_" + channelReporta.getChannelid());
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
                //android注册量
                List<Map<String, Object>> androidCountMap = channelReportDao.findAndroidCount(param);
//						channelReport.setAndroidCount(1);
                for (Map<String, Object> map : androidCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setAndroidCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
                //ios注册量
                List<Map<String, Object>> iosCountMap = channelReportDao.findIosCount(param);
//						channelReport.setIosCount(1);
                for (Map<String, Object> map : iosCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setIosCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
                //pc注册量
                List<Map<String, Object>> pcCountMap = channelReportDao.findPcCount(param);
//						channelReport.setPcCount(1);
                for (Map<String, Object> map : pcCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setPcCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
//
                // 实名认证
                log.info("saveChannelReport realNameMap:{}",JSON.toJSONString(param));

                List<Map<String, Object>> realNameCountMap = channelReportDao
                        .findRealNameCount(param);
                log.info("saveChannelReport realNameMap:{}",JSON.toJSONString(realNameCountMap));
                for (Map<String, Object> map : realNameCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setAttestationRealnameCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                // 申请借款
                List<Map<String, Object>> borrowApplyCountMap = channelReportDao
                        .findBorrowApplyCount(param);
//						channelReport.setBorrowApplyCount(borrowApplyCount);
                for (Map<String, Object> map : borrowApplyCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setBorrowApplyCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                //申请借款-第三方展示
                List<Map<String, Object>> borrowApplyOutCountMap = channelReportDao.findBorrowApplyOutCount(param);
                for (Map<String, Object> map : borrowApplyOutCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setBorrowApplyOutCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 申请借款成功
                Integer[] inStatus = new Integer[]{STATUS_HKZ,
                        STATUS_BFHK, BorrowOrder.STATUS_YHK,
                        BorrowOrder.STATUS_YQYHK, STATUS_YYQ, STATUS_YHZ};
                param.put("inStatus", inStatus);
                List<Map<String, Object>> borrowSucCountMap = channelReportDao
                        .findBorrowSucCount(param);
//						channelReport.setBorrowSucCount(borrowSucCount);
                for (Map<String, Object> map : borrowSucCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setBorrowSucCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                //申请借款成功-第三方展示
                List<Map<String, Object>> borrowSucOutCountMap = channelReportDao.findBorrowSucOutCount(param);
                for (Map<String, Object> map : borrowSucOutCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setBorrowSucOutCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                /*当日实名认证*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayRealNameCountMap = channelReportDao
                        .findRealNameCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayRealNameCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayRealnameCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 当日申请借款
                param.put("day", "the_day");
                List<Map<String, Object>> dayBorrowApplyCountMap = channelReportDao
                        .findBorrowApplyCount(param);
                param.remove("day");
//						channelReport.setBorrowApplyCount(borrowApplyCount);
                for (Map<String, Object> map : dayBorrowApplyCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayBorrowApplyCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 当日申请借款成功
                param.put("day", "the_day");
                List<Map<String, Object>> dayBorrowSucCountMap = channelReportDao
                        .findBorrowSucCount(param);
                param.remove("day");
//						channelReport.setBorrowSucCount(borrowSucCount);
                for (Map<String, Object> map : dayBorrowSucCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayBorrowSucCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }
                    //当日首次逾期人数
                List<Map<String, Object>> dayOverdueCountMap = channelReportDao.dayOverdueCount(param);

                //当日首次逾期并还款人数
                List<Map<String, Object>> dayOverdueRepayedCountMap = channelReportDao.dayOverdueRepayedCount(param);
                //当日首次逾期并续期人数
                List<Map<String, Object>> dayOverdueRenewalCountMap = channelReportDao.dayOverdueRenewalCount(param);
                for (Map<String, Object> map : dayOverdueCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayOverdueCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                for (Map<String, Object> map : dayOverdueRepayedCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        Integer num = Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString());
                        channelReporta.setDayOverdueCount(channelReporta.getDayOverdueCount() == null ? num : channelReporta.getDayOverdueCount() + num);
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                for (Map<String, Object> map : dayOverdueRenewalCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        Integer num = Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString());
                        channelReporta.setDayOverdueCount(channelReporta.getDayOverdueCount() == null ? num : channelReporta.getDayOverdueCount() + num);
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 运营商认证
                List<Map<String, Object>> jxlCountMap = channelReportDao.findTDCount(param);//channelReportDao.findJXLCount(param);
//						channelReport.setJxlCount(jxlCount);
                for (Map<String, Object> map : jxlCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setJxlCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日运营商认证*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayJxlCountMap = channelReportDao
                        .findTDCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayJxlCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayTdCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                // 芝麻认证
                List<Map<String, Object>> zhimaCountMap = channelReportDao.findZMCount(param);
//						channelReport.setZhimaCount(zhimaCount);
                for (Map<String, Object> map : zhimaCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setZhimaCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日芝麻认证*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayZhimaCountMap = channelReportDao
                        .findZMCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayZhimaCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayZhimaCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 紧急联系人
                List<Map<String, Object>> contactCountMap = channelReportDao
                        .findContactCount(param);
//						channelReport.setContactCount(contactCount);
                for (Map<String, Object> map : contactCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setContactCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日紧急联系人*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayContactCountMap = channelReportDao
                        .findContactCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayContactCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayContactCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 工作信息
                List<Map<String, Object>> companyCountMap = channelReportDao
                        .findCompanyCount(param);
//						channelReport.setCompanyCount(companyCount);
                for (Map<String, Object> map : companyCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setCompanyCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日工作信息*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayCompanyCountMap = channelReportDao
                        .findCompanyCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayCompanyCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayCompanyCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 银行卡绑定
                List<Map<String, Object>> bankCountMap = channelReportDao.findBankCount(param);
//						channelReport.setAttestationBankCount(bankCount);
                for (Map<String, Object> map : bankCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setAttestationBankCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日银行卡绑定*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayBankCountMap = channelReportDao
                        .findBankCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayBankCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayBankCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 淘宝
                List<Map<String, Object>> alipayCountMap = channelReportDao
                        .findAlipayCount(param);
//						channelReport.setAlipayCount(alipayCount);
                for (Map<String, Object> map : alipayCountMap) {
                    Object val = map.get("userFrom");

                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setAlipayCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                /*当日支付宝*/
                param.put("day", "the_day");
                List<Map<String, Object>> dayAlipayCountMap = channelReportDao
                        .findAlipayCount(param);
                param.remove("day");
                for (Map<String, Object> map : dayAlipayCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayAlipayCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 授信失败人数
//						List<Map<String, Object>> approveErrorCount = channelReportDao
//								.findApproveErrorCount(param);
//						channelReport.setApproveErrorCount(approveErrorCount);


                // 计算百分比
//						BigDecimal a = new BigDecimal(borrowApplyCount);
//						BigDecimal b = new BigDecimal(borrowSucCount);
//						BigDecimal c = BigDecimal.ZERO;
//						if (borrowApplyCount > 0 && borrowSucCount > 0) {
//							c = b.multiply(new BigDecimal(100)).divide(a, 2,
//									BigDecimal.ROUND_HALF_UP);
//							
//							 
//						}
//						channelReport.setPassRate(c);
                //统计借款率
//						BigDecimal d = new BigDecimal(registerCount);
//						BigDecimal e = BigDecimal.ZERO;
//						if (borrowApplyCount > 0 && registerCount > 0) {
//							e = a.multiply(new BigDecimal(100)).divide(d, 2,
//									BigDecimal.ROUND_HALF_UP);
//						}
//						channelReport.setBorrowRate(e);
                // 放款金额
                List<Map<String, Object>> intoMoneyMap = channelReportDao.findIntoMoney(param);
//						channelReport.setIntoMoney(new BigDecimal(intoMoney));

                for (Map<String, Object> map : intoMoneyMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setIntoMoney(new BigDecimal(map.get("moneyAmount") == null ? "0" : map.get("moneyAmount").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 新用户放款金额放款金额
                param.put("newcustomerType", "0");
                List<Map<String, Object>> newintoMoneyMap = channelReportDao.findNewIntoMoney(param);

                for (Map<String, Object> map : newintoMoneyMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setNewIntoMoney(new BigDecimal(map.get("moneyAmount") == null ? "0" : map.get("moneyAmount").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 老用户放款金额放款金额
                param.put("oldcustomerType", "1");
                List<Map<String, Object>> oldintoMoneyMap = channelReportDao.findOldIntoMoney(param);

                for (Map<String, Object> map : oldintoMoneyMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setOldIntoMoney(new BigDecimal(map.get("moneyAmount") == null ? "0" : map.get("moneyAmount").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }


                // 统计黑名单
                List<Map<String, Object>> blackUserCountMap = channelReportDao
                        .findBlackUserCount(param);
//						channelReport.setBlackUserCount(blackUserCount);
                for (Map<String, Object> map : blackUserCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setBlackUserCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 统计当日黑名单
                param.put("day", "the_day");
                List<Map<String, Object>> dayBlackUserCountMap = channelReportDao
                        .findBlackUserCount(param);
                param.remove("day");
//						channelReport.setBlackUserCount(blackUserCount);
                for (Map<String, Object> map : dayBlackUserCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setDayBlackUserCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                // 统计每天逾期人数
                List<Map<String, Object>> lateDayCountMap = channelReportDao
                        .findlateDayCount(param);
//						channelReport.setLateDayCount(lateDayCount);

                for (Map<String, Object> map : lateDayCountMap) {
                    Object val = map.get("userFrom");
                    ChannelReport channelReporta = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + val)), ChannelReport.class);
                    if (channelReporta != null) {
                        channelReporta.setLateDayCount(Integer.valueOf(map.get("count") == null ? "0" : map.get("count").toString()));
                        jedisCluster.set("channelReport_" + channelReporta.getChannelid(), FastJsonUtils.toJson(channelReporta).toString());
                    }
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
                for (ChannelReport list : lists) {
                    Integer channelId = list.getChannelid();
                    HashMap<String, Object> queryMap = new HashMap<>();
                    HashMap<String, Object> queryMap1 = new HashMap<>();
                    queryMap.put("id", channelId);
                    ChannelInfo channelInfo = channelInfoService.findOneChannelInfo(queryMap);
                    BigDecimal money = BigDecimal.ZERO;

                    ChannelReport channelReportA = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + channelId)), ChannelReport.class);
                    /*判断费率入库时间*/
//							int days = DateUtil.getDateFormat(nowDate, "yyyy-MM-dd");
//							if(days<0){
                    if (channelInfo != null && channelInfo.getRateId() != null) {
                        queryMap1.put("id", channelInfo.getRateId());
                        ChannelRate channelRate = channelInfoService.findOneChannelRateInfo(queryMap1);
                        money = new BigDecimal(channelReportA.getBorrowApplyCount()).multiply(channelRate.getChannelRegisterRate())
                                .add(((channelReportA.getNewIntoMoney()).multiply(channelRate.getChannelNewloanRate())).divide(new BigDecimal(100), 0,
                                        BigDecimal.ROUND_HALF_UP));
                    }
//							}

                    channelReportA.setChannelMoney(money);
                    channelReportA.setReportDate(sdf.parse(nowDate));
                    channelReportDao.deleteByChannelReport(channelReportA);
                    channelReportDao.insert(channelReportA);
                    jedisCluster.del("channelReport_" + channelId);
                }
            }
            //自然流量数据新增
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ChannelReport channelReportNatural = new ChannelReport();
            channelReportNatural.setChannelid(0);
            channelReportNatural.setReportDate(sdf.parse(nowDate));
            channelReportNatural.setCreatedAt(new Date());
            channelReportDao.deleteByChannelReport(channelReportNatural);
//			Map<String,Object> crByNaturalMap = channelReportDao.findByNatural(param);
//			BeanUtils.populate(channelReportNatural,crByNaturalMap);
            Map<String, Object> naturalMap = new HashMap<>();
            naturalMap.put("startDate", nowDate);
            DateUtil.sqlOptimization4DateFormat(naturalMap, "startDate");
            naturalMap.put("userFrom", 0);
            channelReportNatural.setRegisterCount(channelReportDao.registerCount(naturalMap));
            channelReportNatural.setAndroidCount(channelReportDao.androidCount(naturalMap));
            channelReportNatural.setIosCount(channelReportDao.iosCount(naturalMap));
            channelReportNatural.setPcCount(channelReportDao.pcCount(naturalMap));
            channelReportNatural.setAttestationRealnameCount(channelReportDao.attestationRealnameCount(naturalMap));
            channelReportNatural.setJxlCount(channelReportDao.jxlCount(naturalMap));
            channelReportNatural.setZhimaCount(channelReportDao.zhimaCount(naturalMap));
            channelReportNatural.setContactCount(channelReportDao.contactCount(naturalMap));
            channelReportNatural.setCompanyCount(channelReportDao.companyCount(naturalMap));
            channelReportNatural.setAttestationBankCount(channelReportDao.attestationBankCount(naturalMap));
            channelReportNatural.setAlipayCount(channelReportDao.alipayCount(naturalMap));
            channelReportNatural.setBorrowApplyCount(channelReportDao.borrowApplyCount(naturalMap));
            channelReportNatural.setBorrowSucCount(channelReportDao.borrowSucCount(naturalMap));
            Integer intoMoney = channelReportDao.intoMoney(naturalMap);
            channelReportNatural.setIntoMoney(intoMoney == null ? BigDecimal.ZERO : new BigDecimal(intoMoney));
            Integer newIntoMoney = channelReportDao.newIntoMoney(naturalMap);
            channelReportNatural.setNewIntoMoney(newIntoMoney == null ? BigDecimal.ZERO : new BigDecimal(newIntoMoney));
            Integer oldIntoMoney = channelReportDao.oldIntoMoney(naturalMap);
            channelReportNatural.setOldIntoMoney(oldIntoMoney == null ? BigDecimal.ZERO : new BigDecimal(oldIntoMoney));
            channelReportNatural.setBlackUserCount(channelReportDao.blackUserCount(naturalMap));
            channelReportNatural.setLateDayCount(channelReportDao.lateDayCount(naturalMap));
            channelReportDao.insert(channelReportNatural);
            jedisCluster.del(key);
            log.info("saveChannelReport end:{}", nowTime);
            sw.stop();
            log.info(sw.prettyPrint());
        } catch (Exception e) {
            log.error("channek saveReport error:{}", e);
        }
        return true;
    }

//    private void dealUlhData(List<Map<String, Object>> userInfoMap, String type) {
//        if (CollectionUtils.isEmpty(userInfoMap)) {
//            return;
//        }
//        Map<String, Integer> countMap = new HashMap<>();
//        userInfoMap.forEach(one -> {
//            Object userPhone = one.get("user_phone");
//            Object userFrom = one.get("user_from");
//            if (userPhone == null || StringUtils.isEmpty(userPhone.toString()) || userFrom == null || StringUtils.isEmpty(userFrom.toString())) {
//                return;
//            }
//            /*数据中心接口调用*/
//            Result<List<String>> projectName = null;
//            try {
//                projectName = operationSystemService.getProjectNames(userPhone.toString());
//            } catch (Exception e) {
//                log.error("operationSystemService getProjectNames error : {}", e);
//            }
//            if (projectName == null || projectName.getModel() == null || !projectName.getModel().contains("jx")) {
//                Integer num = countMap.get(userFrom.toString());
//                countMap.put(userFrom.toString(), num == null ? 1 : num + 1);
//            }
//        });
//        countMap.forEach((userFromStr, countNum) -> {
//            ChannelReport channelReport = (ChannelReport) JSONObject.toBean(JSONObject.fromObject(jedisCluster.get("channelReport_" + userFromStr)), ChannelReport.class);
//            if (channelReport != null) {
//                switch (type) {
//                    case "android":
//                        channelReport.setUlhAndroidCount(countNum);
//                        break;
//                    case "realname":
//                        channelReport.setDayRealnameCount(countNum);
//                        break;
//                    case "borrowApply":
//                        channelReport.setDayBorrowApplyCount(countNum);
//                        break;
//                    case "borrowApplySuc":
//                        channelReport.setDayBorrowSucCount(countNum);
//                        break;
//                    case "regist":
//                        channelReport.setUlhRegisterCount(countNum);
//                }
//                jedisCluster.set("channelReport_" + channelReport.getChannelid(), FastJsonUtils.toJson(channelReport).toString());
//            }
//
//        });
//    }

//    /**
//     * 有零花统计，在借响注册过的用户不再纳入统计
//     */
//    private void countUlh(Map<String, Object> param) {
//        //总注册量
//        param.put("coutRegist", "y");
//        List<Map<String, Object>> ylhRegistCountMap = channelReportDao.findOtherCount(param);
//        param.remove("coutRegist");
//        String type = "regist";
//        dealUlhData(ylhRegistCountMap, type);
//
//        //安卓注册量
//        param.put("countAndroid", "y");
//        List<Map<String, Object>> ylhAndroidCountMap = channelReportDao.findOtherCount(param);
//        param.remove("countAndroid");
//        type = "android";
//        dealUlhData(ylhAndroidCountMap, type);
//
//        //实名认证量
//        param.put("countRealname", "y");
//        List<Map<String, Object>> ylhRealnameCountMap = channelReportDao.findOtherCount(param);
//        param.remove("countRealname");
//        type = "realname";
//        dealUlhData(ylhRealnameCountMap, type);
//
//        // 申请借款
//        List<Map<String, Object>> borrowApplyCountMap = channelReportDao
//                .findOtherBorrowApplyOutCount(param);
//        type = "borrowApply";
//        dealUlhData(borrowApplyCountMap, type);
//
//        //申请借款成功
//        param.put("countSuc", "y");
//        List<Map<String, Object>> borrowApplySucCountMap = channelReportDao
//                .findOtherBorrowApplyOutCount(param);
//        param.remove("countSuc");
//        type = "borrowApplySuc";
//        dealUlhData(borrowApplySucCountMap, type);
//    }
      @Override
      public PageConfig<OveChannelInfo> findOveChannelId(HashMap<String, Object> params){
        Map<String,Object> map = new HashMap<>();
          DecimalFormat df = new DecimalFormat("0.00%");
          //先查询出所有渠道基本信息
          params.put(Constant.NAME_SPACE, "ChannelReport");
          PageConfig<OveChannelInfo> pageConfig;
          pageConfig = paginationDao.findPage("findBaseChannelInfo", "findBaseChannelInfoCount", params, "web");
          List<OveChannelInfo> list = new ArrayList<OveChannelInfo>();
          for(OveChannelInfo oveChannelInfo : pageConfig.getItems()){
              //根据渠道id 查询该渠道的所有用户id
/*
              List<String> idList=channelInfoService.findUserId(oveChannelInfo.getChannelId());
*/
                  //查询首放数量Ncha
                 Integer firstLoanCount = iBorrowOrderService.findOveChannle(oveChannelInfo.getChannelId(),  oveChannelInfo.getLoanTime(), null,null);
                 Integer renewalCount = iBorrowOrderService.findRenewalCount(oveChannelInfo.getChannelId(),oveChannelInfo.getLoanTime());
                 Integer renewal=renewalCount == null?0:renewalCount;
                 oveChannelInfo.setFirstLoanCount(firstLoanCount + renewal);
                 //查询首放已还数量
                  Integer firstRepayCount = iBorrowOrderService.findRepayCount(oveChannelInfo.getChannelId(),new ArrayList<Integer>(){{
                      add(30);add(34);
                  }},oveChannelInfo.getLoanTime(),null);
                  oveChannelInfo.setFirstRepayCount(firstRepayCount);
                  //首放逾期率 (首放数量-首放已还数量)/首放数量
                  if(oveChannelInfo.getFirstLoanCount() != 0){
                      double firstOveRate = (oveChannelInfo.getFirstLoanCount()-oveChannelInfo.getFirstRepayCount())*(1.0)/oveChannelInfo.getFirstLoanCount()*(1.0);
                      oveChannelInfo.setFirstOveRate(df.format(firstOveRate));
                  }else{
                      oveChannelInfo.setFirstOveRate("0.00%");
                  }
                  //复借数量
                  Integer reLoanCount = iBorrowOrderService.findOveChannle(oveChannelInfo.getChannelId(),oveChannelInfo.getLoanTime(),null,"1");
                  oveChannelInfo.setReLoanCount(reLoanCount);
                  //复借已还数量
                  Integer reRepayCount = iBorrowOrderService.findOveChannle(oveChannelInfo.getChannelId(),oveChannelInfo.getLoanTime(),30,"1");
                  //复借逾期率 (复借数量-复借已还数量)/复借数量
                  if(oveChannelInfo.getReLoanCount() != 0){
                      double  reOveRate = ((oveChannelInfo.getReLoanCount()-oveChannelInfo.getReRepayCount())*(1.0)/oveChannelInfo.getReLoanCount()*(1.0));
                      oveChannelInfo.setReOveRate(df.format(reOveRate));
                  }else{
                      oveChannelInfo.setReOveRate("0.00%");
                  }
                  //展期数量
                  Integer extendCount = iBorrowOrderService.findExtendChannel(oveChannelInfo.getChannelId(),oveChannelInfo.getLoanTime());
                  oveChannelInfo.setExtendCount(extendCount);
                  //总放量 展期数量+首放数量+复借数量
                  Integer allLoanCount = oveChannelInfo.getFirstLoanCount()+oveChannelInfo.getReLoanCount()+oveChannelInfo.getExtendCount();
                  oveChannelInfo.setAllLoanCount(allLoanCount);
                  //总还量 首还数量+复还数量
                  Integer allRepayCount = oveChannelInfo.getFirstRepayCount()+oveChannelInfo.getReRepayCount();
                  oveChannelInfo.setAllRepayCount(allRepayCount);
                 //总逾期率 未还数量/总放量
                  Integer waitRepay = allLoanCount - allRepayCount;
                  if(allLoanCount != null && allLoanCount != 0){
                      Double oveRate= waitRepay * (1.0)/allLoanCount*(1.0);
                      oveChannelInfo.setAllOveRate(df.format(oveRate));
                  }else{
                      oveChannelInfo.setAllOveRate("0.00%");
                  }

                 //BigDecimal allOveRate = channelReportDao.findOveChannel(oveChannelInfo.getChannelId());
                 //oveChannelInfo.setAllOveRate(df.format(allOveRate));
                 list.add(oveChannelInfo);
           }
           pageConfig.setItems(list);
           return pageConfig;
        }
}
