package com.info.web.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BussinessLogUtil {

    private static Logger BIZ_LOGGER = LoggerFactory.getLogger(BussinessLogUtil.class);

    public static void error(String msg, Exception e) {
        BIZ_LOGGER.error(msg + " " + e.getClass().toString() + e.getMessage());
    }

    public static void warn(String msg) {
        BIZ_LOGGER.warn(msg);
    }

    public static void info(String msg) {
        BIZ_LOGGER.info(msg);
    }

}
