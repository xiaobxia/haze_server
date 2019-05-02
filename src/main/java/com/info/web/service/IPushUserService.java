package com.info.web.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public interface IPushUserService {
	/**
	 * 推送注册用户
	 * @return
	 */
	public ServiceResult addPushUserApprove(HashMap<String, Object> map);
	
	
}
