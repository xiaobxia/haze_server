package com.info.web.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 *@author Created by Wzw
 *@date on 2019/2/19 0019 17:28
 */
@Slf4j
public class VoiceNoticeUtil {

    public final static String APP_ID = "20723";
    public final static String APP_KEY = "4f9c269ab73b141710db4a7d2ba6c526";
    public final static String PROJECT_T = "Zy8zu2";
    public final static String PROJECT_A = "x8aYd3";
    public final static String PROJECT_TO = "Au2XZ2";
    public final static String URL = "https://api.mysubmail.com/voice/multixsend.json";


    public static void vocieNoticeMultixsendTomorrow(String multiInfo){
        Map<String,Object> content = new HashMap<>();
        content.put("appid", APP_ID);
        content.put("project", PROJECT_T);
        content.put("multi", multiInfo);
        content.put("signature", APP_KEY);
        String result = HttpUtil.postForm(URL, content);
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-type","application/x-www-form-urlencoded");
//        String jsonString = JSON.toJSONString(content);
//        JSONObject jsonObject = JSON.parseObject(jsonString);
//        String encoding = "utf-8";
//        String sendPost = HttpUtil.sendPost(URL, headers, jsonObject, encoding);
        log.info("vocieNoticeMultixsendTomorrow callback:{}",result);

    }


    public static void vocieNoticeMultixsendTheDayAfterTomorrow(String multiInfo){
        Map<String,Object> content = new HashMap<>();
        content.put("appid", APP_ID);
        content.put("project", PROJECT_A);
        content.put("multi", multiInfo);
        content.put("signature", APP_KEY);
        String result = HttpUtil.postForm(URL, content);
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-type","application/x-www-form-urlencoded");
//        String jsonString = JSON.toJSONString(content);
//        JSONObject jsonObject = JSON.parseObject(jsonString);
//        String encoding = "utf-8";
//        String sendPost = HttpUtil.sendPost(URL, headers, jsonObject, encoding);
        log.info("vocieNoticeMultixsendTheDayAfterTomorrow callback:{}",result);

    }




    public static void vocieNoticeMultixsendToday(String multiInfo){
        Map<String,Object> content = new HashMap<>();
        content.put("appid", APP_ID);
        content.put("project", PROJECT_TO);
        content.put("multi", multiInfo);
        content.put("signature", APP_KEY);
        String result = HttpUtil.postForm(URL, content);
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-type","application/x-www-form-urlencoded");
//        String jsonString = JSON.toJSONString(content);
//        JSONObject jsonObject = JSON.parseObject(jsonString);
//        String encoding = "utf-8";
//        String sendPost = HttpUtil.sendPost(URL, headers, jsonObject, encoding);
        log.info("vocieNoticeMultixsendToday callback:{}",result);

    }

}
