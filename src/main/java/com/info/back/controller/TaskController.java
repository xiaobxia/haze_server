package com.info.back.controller;

import com.info.back.service.ITaskJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("task")
@Slf4j
@Controller
public class TaskController {

    @Resource
    private ITaskJob taskJob;

    @RequestMapping("syncMq")
    public void syncMq(){
        log.info("syncMq Start");
        taskJob.mqTask();
        log.info("syncMq End");
    }

    @RequestMapping("userQuota")
    public void userQuota(){
        log.info("userQuota Start");
        taskJob.userQuota();
        log.info("userQuota End");
    }

    @RequestMapping("everyDayUserQuota")
    public void everyDayUserQuota(){
        log.info("everyDayUserQuota Start");
        taskJob.everyDayUserQuota();
        log.info("everyDayUserQuota End");
    }
    @RequestMapping("channelReportLoad")
    public void channelReportLoad(){
        log.info("自动加载渠道统计开始");
        taskJob.channelReport();
        log.info("自动加载渠道统计完毕");
    }


}
