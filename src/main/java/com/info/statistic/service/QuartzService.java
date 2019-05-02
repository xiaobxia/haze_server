package com.info.statistic.service;

import com.info.statistic.dao.IOverdueDao;
import com.info.statistic.util.DateUtil;
import com.info.web.pojo.statistics.Overdue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by tl on 2018/4/19.
 */
@Slf4j
@Service
public class QuartzService implements IQuartzService{
    @Autowired
    private IOverdueService overdueService;
    @Resource
    private IOverdueDao overdueDao;
    public void quartz(){
        doQuartz(new Date());
    }
    public void quartzByChannel(){
        doQuartzByChannel(new Date());
    }
    public void quartzByModel(){
        doQuartzByModel(new Date());
    }


    @Override
    public String perfectQuartz(Date sDate,Date eDate){
        log.info("perfectQuartz start ");
        List<Date> dates = DateUtil.findDates(sDate,eDate);
        overdueDao.deleteBySource(Overdue.ORIGINAL);
        for (Date d : dates) {
            doQuartz(d);
        }
        log.info("perfectQuartz stop");
        return "perfectQuartz完善定时任务结束";
    }
    @Override
    public String perfectQuartzByModel(Date sDate,Date eDate){
        log.info("perfectQuartzByModel start");
        List<Date> dates = DateUtil.findDates(sDate,eDate);
        for (Date d : dates) {
            doQuartzByModel(d);
        }
        log.info("perfectQuartzByModel stop");
        return "perfectQuartzByModel完善定时任务结束";
    }
    @Override
    public String perfectQuartzByChannel(Date sDate,Date eDate){
        log.info("perfectQuartzByChannel start");
        List<Date> dates = DateUtil.findDates(sDate,eDate);
        Map<String, Object> params = new HashMap<>(3);
        params.put("source", Overdue.CHANNEL);
        params.put("date", "2018-05-30");
        overdueDao.deleteParams(params);
        for (Date d : dates) {
            doQuartzByChannel(d);
        }
        log.info("perfectQuartzByChannel stop");
        return "perfectQuartzByChannel完善定时任务结束";
    }
    @Override
    public void doQuartz(Date pdate){
        log.info("doQuartz start pdate=:{}",pdate);
        if(null==pdate){
            pdate = new Date();
        }
        //Map<String,List<Overdue>> map = new HashMap<>();
        List<Overdue> overdues = new ArrayList<>();
        if(DateUtil.isSunday(pdate)){
            Map<Integer, Date> mondayAndSundayMap = DateUtil.getMondayAndSundayMap(pdate);
            for (int i = 1; i < 5; i++) {
                List<Overdue> week = getResult(mondayAndSundayMap.get(-i), mondayAndSundayMap.get(i), pdate);
                //map.put("preWeek"+i,week);
                overdues.addAll(week);
            }
        }
        if(DateUtil.isLastDayOfMonth(pdate)){
            Map<Integer, Date> firstDayAndLastDayMap = DateUtil.getFirstDayAndLastDayMap(pdate);
            for (int i = 1; i < 5; i++) {
                List<Overdue> month = getResult(firstDayAndLastDayMap.get(-i), firstDayAndLastDayMap.get(i),pdate);
                //map.put("preMonth"+(i-1),month);
                overdues.addAll(month);
            }
        }

        Map<Integer, Date> sevenDaysMap = DateUtil.getSevenDaysMap(pdate);
        for (Integer i : sevenDaysMap.keySet()) {
            List<Overdue> day = getResult(sevenDaysMap.get(i), sevenDaysMap.get(i),pdate);
            //map.put("preDay"+i,day);
            overdues.addAll(day);
        }
        if(CollectionUtils.isNotEmpty(overdues)){
            try {

                overdueDao.insertList(overdues);
            } catch (Exception e) {
                log.error("doQuartz batch insert error_:{}",e);
            }
        }
        log.info("doQuartz stop");
    }
    @Override
    public void doQuartzByModel(Date pdate){
        log.info("doQuartzByModel start pdate=:{}",pdate);
        if(null==pdate){
            pdate = new Date();
        }
        List<Overdue> overdues = overdueService.analysisByModel(pdate);
        if(CollectionUtils.isNotEmpty(overdues)){
            try {

                overdueDao.insertList(overdues);
            } catch (Exception e) {
                log.error("doQuartzByModel batch insert error_:{}", e);
            }
        }
        log.info("doQuartzByModel stop");
    }
    @Override
    public void doQuartzByChannel(Date pdate){
        log.info("doQuartzByChannel start pdate=:{}",pdate);
        List<Overdue> overdues = new ArrayList<>();
        if(null==pdate){
            pdate = new Date();
        }
        Date pastDate = DateUtil.getPastDate(pdate, 1);
        List<Overdue> newList = overdueService.getOverdueDaysByChannel(pastDate, pastDate, pdate, Overdue.NEW_CUSTOMER);
        overdues.addAll(newList);
        List<Overdue> oldList = overdueService.getOverdueDaysByChannel(pastDate, pastDate, pdate, Overdue.OLD_CUSTOMER);
        overdues.addAll(oldList);
        if(CollectionUtils.isNotEmpty(overdues)){
            try {

                overdueDao.insertList(overdues);
            } catch (Exception e) {
                log.error("doQuartzByChannel batch insert error_:{}", e);
            }
        }
        //逾期超过三天 逾期率计算
        List<Overdue> list3 = new ArrayList<>();
        List<Overdue> list1 = overdueService.updateByChannel(pdate, Overdue.OLD_CUSTOMER);
        list3.addAll(list1);
        List<Overdue> list2 = overdueService.updateByChannel(pdate, Overdue.NEW_CUSTOMER);
        list3.addAll(list2);
        if (CollectionUtils.isNotEmpty(list3)) {
            try {

                overdueDao.updateRate3(list3);
            } catch (Exception e) {
                log.error("doQuartzByChannel batch updateRate3 error_:{}", e);
            }
        }
        log.info("doQuartzByChannel stop");
    }

    private List<Overdue> getResult(Date sdate, Date edate,Date pdate) {
        List<Overdue> list = new ArrayList<>();
        getByType(sdate, edate,pdate, list, Overdue.NEW_CUSTOMER);
        getByType(sdate, edate,pdate, list, Overdue.OLD_CUSTOMER);
        getByType(sdate, edate,pdate, list, Overdue.ALL_CUSTOMER);
        return list;
    }

    private void getByType(Date sdate, Date edate,Date pdate, List<Overdue> list, int newCustomer) {
        Overdue overdue = new Overdue();
        overdue.setType(newCustomer);
        if(null==pdate){
            pdate = new Date();
        }
        overdue.setStartDate(sdate);
        overdue.setEndDate(edate);
        overdue.setPointDate(pdate);
        Overdue vo = overdueService.analysis(overdue);
        if(null!=vo){
            list.add(vo);
        }
    }

}
