package com.info.web.service;


import com.info.base.service.BaseService;
import com.info.web.pojo.UserSmsReceiveLog;
import com.info.web.pojo.UserSmsSendLog;

/**
 * 用户发送短信记录Service
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-06-08 22:02:39
 */
public interface IUserSmsSendLogService extends BaseService<UserSmsSendLog, Integer> {

    void updateUserSmsSendLogBySmsUuid(String smsUuid, int sendDeliver);

    void addUserSmsReceiveLog(UserSmsReceiveLog userSmsReceiveLog);

}
