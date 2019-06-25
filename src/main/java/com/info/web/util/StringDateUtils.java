package com.info.web.util;

import com.info.back.utils.SysCacheUtils;
import com.info.web.pojo.BackConfigParams;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 下划线 转 驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(param.charAt(i));
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getDrawWithChannel() {
        List<BackConfigParams> drawwithchannel1 = SysCacheUtils.getListConfigParams("DRAWWITHCHANNEL");
        String result = "chanpay";
        try {
            if (drawwithchannel1.size() <= 0) return result;
            for (BackConfigParams backConfigParams : drawwithchannel1) {
                if (Integer.parseInt(backConfigParams.getSysValue()) == 0) {
                    result = backConfigParams.getSysValueBig();
                }
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }
}
