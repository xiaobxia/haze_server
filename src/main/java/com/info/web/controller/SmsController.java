package com.info.web.controller;


import com.info.back.utils.Result;
import com.info.web.pojo.UserSmsReceiveLog;
import com.info.web.service.IUserSmsSendLogService;
import com.info.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信单独模块API
 * @author fully 2019-06-08 21:07:00
 */

@Slf4j
@Controller
@RequestMapping("trSms")
public class SmsController {

    @Autowired
    private IUserSmsSendLogService userSmsSendLogService;

    /**
     * 群发短信
     * @return
     */
    /*@RequestMapping("massSms")
    @ResponseBody
    public Result massSms(HttpServletRequest request) {
        String smUuid = request.getParameter("smUuid");
        //String deliverTime = request.getParameter("deliverTime");
        String deliverResult = request.getParameter("deliverResult");
        //String mobile = request.getParameter("mobile");
        //String batchId = request.getParameter("batchId");

        userSmsSendLogService.updateUserSmsSendLogBySmsUuid(smUuid, "DELIVRD".equals(deliverResult) ? 1 : 0);
        return Result.success();
    }*/

    /**
     * 接收天瑞推送的短信发送状态
     * @return
     */
    @RequestMapping(value = "pushSendDeliver", method = RequestMethod.POST)
    @ResponseBody
    public Map pushSendDeliver(HttpServletRequest request) {
        String smUuid = request.getParameter("smUuid");
        //String deliverTime = request.getParameter("deliverTime");
        String deliverResult = request.getParameter("deliverResult");
        //String mobile = request.getParameter("mobile");
        //String batchId = request.getParameter("batchId");

        userSmsSendLogService.updateUserSmsSendLogBySmsUuid(smUuid, "DELIVRD".equals(deliverResult) ? 1 : 0);

        return new HashMap(){{
            put("code", "0");
            put("msg", "SUCCESS");
        }};
    }

    /**
     * 接收天瑞推送的短信回复内容
     * @return
     */
    @RequestMapping(value = "pushReceive", method = RequestMethod.POST)
    @ResponseBody
    public Map pushReceive(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String content = request.getParameter("content");
        String receiveTime = request.getParameter("receiveTime");
        //String extNo = request.getParameter("extNo");

        UserSmsReceiveLog userSmsReceiveLog = new UserSmsReceiveLog();
        userSmsReceiveLog.setReceivePhone(mobile);
        userSmsReceiveLog.setReceiveContent(content);
        userSmsReceiveLog.setReceiveTime(DateUtil.getDate(receiveTime, "yyyy-MM-dd HH:mm:ss"));
        userSmsSendLogService.addUserSmsReceiveLog(userSmsReceiveLog);

        return new HashMap(){{
            put("code", "0");
            put("msg", "SUCCESS");
        }};
    }

}
