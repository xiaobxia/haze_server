package com.info.statistic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by tl on 2018/3/20.
 */
public class DateUtil {
    // 获得某天最大时间 2018-03-20 23:59:59
    public static Date getEndOfDay(Date date) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    // 获得某天最小时间 2018-03-20 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 是否月末
     * @param date date
     * @return b
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }
    /**
     * 是否周末
     * @param date date
     * @return b
     */
    public static boolean isSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            return true;
        }
        return false;
    }
    /**
     * 获取过去第几天的日期
     * @param past past
     * @return date
     */
    public static Date getPastDate(Date pdate,int past) {
        Calendar calendar = Calendar.getInstance();
        if(null != pdate){
            calendar.setTime(pdate);
        }
        calendar.add(Calendar.DATE, -past);
        return calendar.getTime();
    }
    /**
     * 前past个月的第一天和最后一天
     */
    public static Map<Integer, Date> getFirstDayAndLastDayOfMonth(Date pdate,int past) {
        Calendar calendar = Calendar.getInstance();
        if(null != pdate){
            calendar.setTime(pdate);
        }
        calendar.add(Calendar.MONTH, -past);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDay = gcLast.getTime();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DAY_OF_MONTH, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        Date lastDay = calendar.getTime();

        Map<Integer, Date> map = new HashMap<Integer, Date>();
        map.put(-past-1, firstDay);
        map.put(past+1, lastDay);
        return map;
    }
    /**
     * 前past个周的周一和周末
     */
    public static Map<Integer, Date> getMondayAndSunday(Date pdate,int past) {
        Calendar calendar = Calendar.getInstance();
        if(null != pdate){
            calendar.setTime(pdate);
        }
        calendar.add(Calendar.DATE,-1);
		calendar.add(Calendar.DATE, - (past*7));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = calendar.getTime();
        calendar.add(Calendar.DATE,6);
        Date sunday = calendar.getTime();
        Map<Integer, Date> map = new HashMap<>();
        map.put(-past, monday);
        map.put(past, sunday);
        return map;
    }
    /**
     * 前3个月的第一天和最后一天
     */
    public static Map<Integer, Date> getFirstDayAndLastDayMap(Date pdate) {
        Map<Integer, Date> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            Map<Integer, Date> dayOfMonth = getFirstDayAndLastDayOfMonth(pdate,i);
            map.putAll(dayOfMonth);
        }
        return map;
    }
    /**
     * 前4周的周一和周末
     */
    public static Map<Integer, Date> getMondayAndSundayMap(Date pdate) {
        Map<Integer, Date> map = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            Map<Integer, Date> dayOfMonth = getMondayAndSunday(pdate,i);
            map.putAll(dayOfMonth);
        }
        return map;
    }
    /**
     * 前1周的七天
     */
    public static Map<Integer, Date> getSevenDaysMap(Date pdate) {
        Map<Integer, Date> map = new HashMap<>();
        for (int i = 7; i < 14; i++) {
            Date pastDate = getPastDate(pdate,i);
            map.put(i,pastDate);
        }
        return map;
    }
    /**
     * 获取某段时这里写代码片间内的所有日期
     * @param dBegin d
     * @param dEnd d
     * @return l
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        //calEnd.add(Calendar.DATE,-1);
        dEnd=calEnd.getTime();
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }
    // 得到完整的日期，格式为：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
    public static String getDateComplete(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        return sdf.format(date);
    }
}
