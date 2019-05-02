package com.info.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 时间工具
 *
 * @author gaoyuhai 2016-6-14 上午09:23:37
 */
@Slf4j
public class DateUtil {
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String YMD = "yyyy-MM-dd";
    public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(YMD);

    public static synchronized String format_yyyy_MM_dd(Date date) {
        return yyyy_MM_dd.format(date);
    }

    /**
     * 获取当前时间 format 格式
     *
     */
    public static String getDateFormat(String format) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取时间 format 格式
     *
     */
    public static String getDateFormat(Date date,String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    /**
     * 获取某时间-当前时间 format 格式
     */
    public static int getDateFormat(String endDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long days = 0;
        try {
            Date eDate = sdf.parse(endDate);
            Date date = sdf.parse(getDateFormat(format));

            long diff = eDate.getTime() - date.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            log.error("getDateFormat error:{}",e);
        }
        return (int) days;
    }

    /**
     * 获取某时间-某时间 format 格式
     *
     */
    public static String getDateFormat(String endDate0, String endDate1,
                                       String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long days = 0;
        try {
            Date eDate0 = sdf.parse(endDate0);
            Date eDate1 = sdf.parse(endDate1);
            long diff = eDate1.getTime() - eDate0.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            log.error("getDateFormat error:{}",e);
        }
        return String.valueOf(days);
    }

    /**
     * 获取下n月
     *
     */
    public static Date getNextMon(int month) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, +month);
        return calendar.getTime();
    }

    /**
     * 获取前days日
     *
     */
    public static String getDateForDayBefor(int days, String format) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

//    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("dateStart","2014-05-05");
//        sqlOptimization4DateFormat(map,"dateStart");
//        System.out.println(map.get("dateStartChangeEnd"));
//    }

    /**
     * 时间相加
     *
     */
    public static Date addDay(Date date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 月相加
     *
     */
    public static Date addMonth(Date date, int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 年相加
     *
     */
    public static Date addYear(Date date, int year) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 根据字符串和指定格式生成日期
     *
     */
    public static Date getDate(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            log.error("getDate error:{}",e);
        }
        return date;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String fyTimePattern = "yyyyMMdd"; // 富友交易日期
    /**
     *
     * return yyyyMMdd
     *
     */
    public static String fyFormatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(fyTimePattern);
        return f.format(date);
    }

    /**
     *
     * return date
     *
     */
    public static Date  cmbToDateByString(String date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        Date sDate = null;
        try {
            sDate = f.parse(date);
        } catch (ParseException e) {
            log.error("cmbToDateByString error:{}",e);
        }
        return sDate;
    }
    public static boolean checkIsApproved(Date nowDate,Date  updateTime){
        Long  updateTimeLong=  updateTime.getTime();
        Long  diffValue=(nowDate.getTime()-updateTimeLong)/1000;
        //当前时间大于更新时间且时间差小于半个 小时
        return  diffValue>0 && diffValue<1800L;
    }

    public static void sqlOptimization4DateFormat(Map<String,Object> params,String... args ) {
        if (args == null) return;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        for (String dateTime : args) {
            String paramsDateTime = params.get(dateTime) == null ? "" : params.get(dateTime).toString();
            if (StringUtils.isNotEmpty(paramsDateTime)) {
                try {
                    Date date = simpleDateFormat.parse(paramsDateTime);
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, 1);
                    date = calendar.getTime();
                    params.put(dateTime + "ChangeEnd", simpleDateFormat.format(date));
                } catch (Exception e) {
                    log.error("Change error for sqlOptimization4DateFormat:{}",e);
                }
            } else {
                new Exception("Can't get value from paramsMap");
            }
        }
    }
        // 获得某天最大时间 2018-03-20 23:59:59
        public static Date getEndOfDay(Date date) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        }

        // 获得某天最小时间 2018-03-20 00:00:00
        public static Date getStartOfDay(Date date) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
            LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
            return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

        }
    }
