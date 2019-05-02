package com.info.back.controller;

import com.info.analyze.service.IAppMarketStaticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 手动触发定时器入口控制器类
 *
 * @author tgy
 * @version [版本号, 2018年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Slf4j
@RestController
@RequestMapping("manual/")
public class ManualScheduleController {

    @Autowired
    private IAppMarketStaticsService appMarketStaticsService;

    @RequestMapping("appmarket")
    @ResponseBody
    public String appMarketManualTrigger(HttpServletRequest request,HttpServletResponse respons) throws Exception {
        log.info("manual trigger start schedule start");
        appMarketStaticsService.insertToAppmarketAnalyze();
        log.info("manual trigger start schedule end");
        return "SUCCESS";
    }
}
