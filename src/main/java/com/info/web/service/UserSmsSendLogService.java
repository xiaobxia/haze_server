package com.info.web.service;

import com.info.base.dao.BaseMapper;
import com.info.base.service.impl.BaseServiceImpl;
import com.info.web.dao.IUserDao;
import com.info.web.dao.IUserSmsReceiveLogDao;
import com.info.web.dao.IUserSmsSendLogDao;
import com.info.web.pojo.UserSmsReceiveLog;
import com.info.web.pojo.UserSmsSendLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * 用户发送短信记录ServiceImpl
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-06-08 22:02:39
 */
 
@Service
public class UserSmsSendLogService extends BaseServiceImpl<UserSmsSendLog, Integer> implements IUserSmsSendLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserSmsSendLogService.class);
   
    @Resource
    private IUserSmsSendLogDao userSmsSendLogDao;

    @Resource
	private IUserSmsReceiveLogDao userSmsReceiveLogDao;

    @Resource
	private IUserDao userDao;


	@Override
	public BaseMapper<UserSmsSendLog, Integer> getMapper() {
		return userSmsSendLogDao;
	}

	@Override
	public void updateUserSmsSendLogBySmsUuid(String smsUuid, int sendDeliver) {
		UserSmsSendLog oldUserSmsSendLog = getMapper().findSelective(new HashMap<String, Object>() {{
			put("smsUuid", smsUuid);
		}});

		if (oldUserSmsSendLog != null) {
			UserSmsSendLog userSmsSendLog = new UserSmsSendLog();
			userSmsSendLog.setId(oldUserSmsSendLog.getId());
			userSmsSendLog.setSendDeliver(sendDeliver);
			getMapper().updateSelective(userSmsSendLog);
		}
	}

	@Override
	public void addUserSmsReceiveLog(UserSmsReceiveLog userSmsReceiveLog) {
		Integer id = userDao.selectUserIdByPhone(userSmsReceiveLog.getReceivePhone());
		userSmsReceiveLog.setUserId(id);
		userSmsReceiveLogDao.save(userSmsReceiveLog);
	}

}