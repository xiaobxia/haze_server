package com.info.web.service;

import com.info.constant.Constant;
import com.info.web.dao.IIndexDao;
import com.info.web.pojo.InfoIndexInfo;
import com.info.web.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
/**
 * 用户动态数据设置
 * @author gaoyuhai
 * 2016-12-17 下午05:59:16
 */
@Slf4j
@Service
public class InfoIndexService implements IInfoIndexService {
	
	@Autowired
	private IIndexDao indexDao;

	/**
	 * 更新用户额度
	 */
	public void changeUserAmount(HashMap<String,Object> map){
		log.info("提升额度...");
		String userId = null;
		try{
			userId = String.valueOf(map.get("userId"));
		}catch(Exception e){
			log.error("changeUserAmount map.getkey is error:{}",map);
		}
		if(StringUtils.isNotBlank(userId)){
			log.info("提升额度用户 userId:{}",userId);
			Integer uId = Integer.parseInt(userId);
			HashMap<String,Object> userMap = new HashMap<>();
			userMap.put("USER_ID", uId);
			//根据id查找user_info 表中的信息
			User user = this.indexDao.searchUserByIndex(userMap);
//			loger.info("changeUserAmount user:"+user);
			if(null!=user){
				InfoIndexInfo indexInfo = new InfoIndexInfo();
				indexInfo.setUserId(uId);
				indexInfo.setCardAmount(user.getAmountMax());
				indexInfo.setAmountMin(user.getAmountMin());
				log.info("额度提升为:"+user.getAmountMax()+" -- "+user.getAmountMin());
				//操作表 info_index_info 位置：index.xml
				int updateRow = this.indexDao.updateIndexInfoByUserId(indexInfo);
				log.info("changeUserAmount updateRow:{}",updateRow);
			}
		}else{
			log.info("提升额度用户 userId is null");
		}
	}

	/**
	 * 用户手机认证
	 */
	public void authMobile(HashMap<String,Object> map){
		log.info("authMobile start...map:{}",map);
		InfoIndexInfo indexInfo = new InfoIndexInfo();
		indexInfo.setUserId(Integer.parseInt(String.valueOf(map.get("userId"))));
		indexInfo.setAuthMobile(Constant.STATUS_INT_VALID);
		this.indexDao.updateIndexInfoByUserId(indexInfo);
	}

	@Override
	public void saveInfoNotice(HashMap<String,Object> map) {
		this.indexDao.saveInfoNotice(map);
	}
}
