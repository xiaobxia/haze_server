package com.info.web.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.info.back.dao.IChannelDynamicDao;
import com.info.web.dao.*;
import com.info.web.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.util.PageConfig;
@Slf4j
@Service
public class ChannelInfoService implements IChannelInfoService {

	@Autowired
	private IChannelInfoDao channelInfoDao;
	@Autowired
	private IChannelSuperInfoDao channelSuperInfoDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IChannelRateDao channelRateDao;
	@Autowired
	private IChannelDynamicDao channelDynamicDao;
	@Autowired
	private IBorrowOrderDao borrowOrderDao;

	@Override
	public int getCountSuperChannelCode(String code) {
		return channelInfoDao.getCountSuperChannelCode(code);
	}

	@Override
	public List<ChannelInfo> findAll(Map<String, Object> params) {
		return channelInfoDao.findAll(params);
	}

	@Override
	public List<ChannelInfo> findAll2(Map<String, Object> params) {
		return channelInfoDao.findAll2(params);
	}
	@Override
	public List<String> findAllChUser(Map<String, Object> params) {
		return channelInfoDao.findAllChUser(params);
	}
	@Override
	public ChannelInfo findOneChannelInfo(HashMap<String, Object> params) {
		List<ChannelInfo> list = this.findAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ChannelInfo findOneChannelInfoNew(HashMap<String, Object> params) {
		List<ChannelInfo> list = this.findAll2(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insert(ChannelInfo channelInfo) {

		channelInfoDao.insert(channelInfo);
	}
	@Override
	public void insertChannelUserInfo(Map<String, Object> param) {
		channelInfoDao.insertChannelUserInfo(param);
	}

	@Override
	public void insertChannelDynamic(ChannelDynamic channelDynamic, User user) {
		channelDynamic.setCreateTime(new Date());
		channelDynamic.setApkUrl(user.getApkUrl());
		channelDynamic.setChannelTag(user.getChannelTag());
		channelDynamic.setToutiaoConvertId(user.getToutiaoConvertId());
		channelDynamic.setPicCodeNum(user.getPicCodeNum()	);
		channelDynamicDao.insertSelective(channelDynamic);
	}

	@Override
	public void updateById(ChannelInfo channelInfo) {
		channelInfoDao.updateById(channelInfo);
	}

	@Override
	public void deleteChannelInfoById(Integer id) {
		channelInfoDao.deleteById(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelInfo> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelInfo");
		PageConfig<ChannelInfo> pageConfig = new PageConfig<ChannelInfo>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,"web");
		return pageConfig;

	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelInfo> findChannelUserPage(
			HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelInfo");
		PageConfig<ChannelInfo> pageConfig = new PageConfig<ChannelInfo>();
		pageConfig = paginationDao.findPage("findUserAll", "findUserAllCount", params,"web");
		return pageConfig;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelInfo> findChannelRecordPage(
			HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelInfo");
		PageConfig<ChannelInfo> pageConfig = new PageConfig<ChannelInfo>();
		pageConfig = paginationDao.findPage("findRecordAll", "findRecordAllCount", params,"web");
		return pageConfig;
	}

	@Override
	public ChannelInfo findChannelCode(String channelCode) {
		return channelInfoDao.findChannelCode(channelCode);
	}

	@Override
	public Integer findParamsCount(Map<String, Object> params) {
		return channelInfoDao.findRecordAllCount(params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<HashMap<String, Object>> findChannelUserBorrowRecord(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelInfo");
		PageConfig<HashMap<String, Object>> pageConfig = new PageConfig<HashMap<String, Object>>();
		pageConfig = paginationDao.findPage("findChanellBroowRecord", "findChanellBroowRecordCount", params,"web");
		return pageConfig;
	}

	@Override
	public HashMap<String, Object> countChanellUser(Integer channelId) {
		return channelInfoDao.countChanellUser(channelId);
	}


	/*************************添加推广渠道上级菜单**********************************/

	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelSuperInfo> findChannelSuperPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelSuperInfo");
		PageConfig<ChannelSuperInfo> pageConfig = new PageConfig<ChannelSuperInfo>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,"web");
		return pageConfig;

	}

	@Override
	public void insertChannelSuperInfo(ChannelSuperInfo channelSuperInfo) {
		channelSuperInfoDao.insert(channelSuperInfo);
	}

	@Override
	public void updateChannelSuperById(ChannelSuperInfo channelSuperInfo) {

		channelSuperInfoDao.updateById(channelSuperInfo);
	}

	@Override
	public List<ChannelSuperInfo> findSuperAll(Map<String, Object> params) {
		return channelSuperInfoDao.findAll(params);
	}
	@Override
	public ChannelSuperInfo findOneChannelSuperInfo(HashMap<String, Object> params) {
		List<ChannelSuperInfo> list = this.findSuperAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**********************添加推广渠道费率菜单**************************/
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelRate> findChannelRatePage(
			HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelRate");
		PageConfig<ChannelRate> pageConfig = new PageConfig<ChannelRate>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,"web");
		return pageConfig;
	}

	@Override
	public void insertChannelRate(ChannelRate channelRate) {
		channelRateDao.insert(channelRate);
	}

	@Override
	public void updateChannelRateById(ChannelRate channelRate) {
		channelRateDao.updateById(channelRate);
	}

	@Override
	public ChannelRate findOneChannelRateInfo(HashMap<String, Object> params) {
		List<ChannelRate> list = this.findChannelRateAll(params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ChannelRate> findChannelRateAll(Map<String, Object> params) {
		return channelRateDao.findAll(params);
	}

	@Override
	public List<ChannelInfo> findAllUserList(Map<String, Object> params) {
		return channelInfoDao.findAllUserList(params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelInfo> findUserAllPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelInfo");
		PageConfig<ChannelInfo> pageConfig = new PageConfig<ChannelInfo>();
		pageConfig = paginationDao.findPage("findUserAllPage", "findUserAllPageCount", params,"web");
		return pageConfig;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ChannelGetUserInfo> findChannelGetUserInfo(HashMap<String, Object> paramMap) {
		paramMap.put(Constant.NAME_SPACE, "User");
		PageConfig<User> userPage = paginationDao.findPage("selectByChannelId","selectByChannelIdCount",paramMap,"web");
		PageConfig<ChannelGetUserInfo> resultPage = new PageConfig<>();

		resultPage.setPageSize(userPage.getPageSize());
		resultPage.setTotalPageNum(userPage.getTotalPageNum());
		resultPage.setTotalResultSize(userPage.getTotalResultSize());
		resultPage.setCurrentPage(userPage.getCurrentPage());
		List<User> users = userPage.getItems();

		List<ChannelGetUserInfo> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(users)){
			ChannelGetUserInfo info;
			String isComplete;
			HashMap<String,Object> params = new HashMap<>();
			Integer[] status = {BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_YYQ, BorrowOrder.STATUS_YHZ,};
			Long borrowedMoney;
			Long leftMoney;
			for(User user:users){
				isComplete = "否";
				params.clear();
				if(user == null){
					continue;
				}
				info = new ChannelGetUserInfo();
				info.setUserId(Integer.parseInt(user.getId()));
				info.setRealName(user.getRealname());
				info.setMobile(user.getUserPhone());
				if("1".equals(user.getZmStatus())&&"1".equals(user.getRealnameStatus())&&"1".equals(user.getTdStatus())
						&& StringUtils.isNotEmpty(user.getFirstContactPhone())&&StringUtils.isNotEmpty(user.getSecondContactPhone())){
					isComplete = "是";
				}
				info.setIsCompleteInfo(isComplete);

				params.put("borrowStatusArray", status);
				params.put("userId", user.getId());
				borrowedMoney = borrowOrderDao.findMoneyAmountSucSum(params);
				//部分还款
				leftMoney = borrowOrderDao.findOrderLeftMony(BorrowOrder.STATUS_BFHK);
				borrowedMoney = borrowedMoney==null?0:borrowedMoney;
				leftMoney = leftMoney==null?0:leftMoney;
				Long totalMoney = leftMoney+borrowedMoney;
				info.setBorrowTotal(new BigDecimal(totalMoney));
				Integer count = borrowOrderDao.findBorrowCount(user.getId(),status)-1;
				count = count>0?count:0;
				info.setBorrowCount(count);
				info.setRegistTime(paseDate(user.getCreateTime().toString()));
				result.add(info);
			}

		}
		resultPage.setItems(result);
		return resultPage;
	}
	private String paseDate(String dateStr){
		try {
			Date date=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(dateStr);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} catch (ParseException e) {
			log.error("paseDate error:{}",e);
		}
		return "";
	}

	@Override
	public Map<String,Object> selectChannelByUserId(Map<String,Object> map){
		return channelInfoDao.selectChannelByUserId(map);
	}
}
