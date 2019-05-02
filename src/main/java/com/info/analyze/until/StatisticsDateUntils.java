package com.info.analyze.until;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class StatisticsDateUntils {
    private static  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 判断在周的最后一天
     * @param date date
     * @return bool
     */
    public static boolean judgeAWeekLastDay(String date){
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            log.error("judgeAWeekLastDay error:{}",e);
            return false;
        }
        int weekFlag = cal.get(Calendar.DAY_OF_WEEK);
        return weekFlag == Calendar.SUNDAY;

    }

    /**
     * 判断是否在月的最后一天
     * @param str str
     * @return bool
     */
    public static boolean judgeAMonthLastDay(String str){
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(str);
            calendar.setTime(date);
            int monthDys = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return day == monthDys;
        } catch (ParseException e) {
            log.error("judgeAMonthLastDay error:{}",e);
        }
        return false;
    }




    /**
     * 获取一个星期中的星期一的日期以及星期日的日期
     * @param str str
     * @return map
     */
    public static Map<String,Object> getAWeekAllDay(String str){
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(str);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-6);
            Date firstDay = calendar.getTime();
            String startWeekTime = simpleDateFormat.format(firstDay);
            map.put("startWeekTime",startWeekTime);
            map.put("endWeekTime",str);
        } catch (ParseException e) {
            log.error("getAWeekAllDay error:{}",e);
        }
        return map;
    }

    public static Map<String,Object> getMonthBetweenStartAndEnd(String str){
        Date date;
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        try {
            date = simpleDateFormat.parse(str);
            //获取前一个月第一天
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            String firstDay = simpleDateFormat.format(calendar.getTime());
            map.put("beginMonthDay",firstDay);

            //获取月的最后一天
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String lastDay = simpleDateFormat.format(calendar.getTime());
            map.put("endMonthDay",lastDay);
        } catch (ParseException e) {
            log.error("getMonthBetweenStartAndEnd error:{}",e);
        }
        return map;
    }

    /**
     * 获取当前时间前一天进行统计
     * @return str
     */
    public static String getBeforeDayTime(){
        Calendar calendar = Calendar.getInstance();
        //获取当前统计时间的前一天的数据
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    public static boolean isTodayTime(String strTime){
        String time = simpleDateFormat.format(new Date());
        return time.equals(strTime);
    }
}
