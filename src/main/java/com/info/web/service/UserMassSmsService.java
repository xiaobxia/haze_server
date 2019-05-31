package com.info.web.service;

import com.info.base.dao.BaseMapper;
import com.info.base.service.impl.BaseServiceImpl;
import com.info.web.dao.IUserMassSmsDao;
import com.info.web.pojo.UserMassSms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 群发短信ServiceImpl
 * 
 * @author fully
 * @version 1.0.0
 * @date 2019-05-31 10:54:29
 */
 
@Service
public class UserMassSmsService extends BaseServiceImpl<UserMassSms, Integer> implements IUserMassSmsService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserMassSmsService.class);
   
    @Resource
    private IUserMassSmsDao iUserMassSmsDao;

	@Override
	public BaseMapper<UserMassSms, Integer> getMapper() {
		return iUserMassSmsDao;
	}
	
}