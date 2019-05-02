package com.info.back.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取config.properties配置
 *
 * @author LTQ
 */
@Slf4j
public class PropertiesUtil {
    private static Properties properties = new Properties();

    static {
        try (InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"))) {
            properties.load(reader);
        } catch (IOException e) {
            log.error("PropertiesUtil error:{}", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }
}
