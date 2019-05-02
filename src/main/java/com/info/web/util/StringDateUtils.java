package com.info.web.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用String表示Date的工具类
 * Created by Phi on 2017/7/27.
 */
@Slf4j
public class StringDateUtils {


    /**
     * 日期加天数 减天数为负数
     *
     * @param sdfString  时间格式
     * @param stringDate 时间字符串
     * @param days       天数 加天数为正数 减天数为负数
     */
    public static String daysAdd(String sdfString, String stringDate, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(sdfString);
        String res = "";
        try {
            Date sdfDate = sdf.parse(stringDate);
            res = sdf.format(daysAddDays(sdfDate, days));
        } catch (ParseException e) {
            log.error("daysAdd error:{}",e);
        }
        return res;
    }


    private static Date daysAddDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, days);
            return calendar.getTime();
        } catch (RuntimeException e) {
            log.error("daysAddDays error:{}",e);
            return null;
        }
    }
}
