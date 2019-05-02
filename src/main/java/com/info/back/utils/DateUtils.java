package com.info.back.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间操作工具类
 *
 * @author tgy
 * @version [版本号, 2018年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DateUtils {

    /**
     * 获取每日起始时间 00:00:00
     */
    public static Date getTheDayStart(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取每日结束时间 23:59:59
     *
     */
    public static Date getTheDayEnd(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取明天的起始时间
     *
     */
    public static Date getTommorrowStart(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取明天的结束时间
     *
     */
    public static Date getTommorrowEnd(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
