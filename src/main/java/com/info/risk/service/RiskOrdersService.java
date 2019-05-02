package com.info.risk.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.info.risk.pojo.newrisk.ShuJuMoHeVo;
import com.info.risk.pojo.CreditReport;
import com.info.risk.pojo.Reason;
import com.info.risk.pojo.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.RequestUtils;
import com.info.risk.dao.IRiskOrdersDao;
import com.info.risk.pojo.RiskOrders;

import java.util.*;
@Slf4j
@Service
public class RiskOrdersService implements IRiskOrdersService {

	@Autowired
	private IRiskOrdersDao ordersDao;

	@Override
	public int insert(RiskOrders orders) {
		if (StringUtils.isBlank(orders.getAddIp())) {
			orders.setAddIp(RequestUtils.getIpAddr());
		}
		return ordersDao.insert(orders);
	}

	@Override
	public int update(RiskOrders orders) {
		return ordersDao.update(orders);
	}

	@Override
	public RiskOrders findById(Integer id) {
		return ordersDao.findById(id);
	}


	@Override
	public List<RiskOrders> selectAfterTbReport(HashMap<String, Object> parms) {
		return ordersDao.selectAfterTbReport(parms);
	}

	@Override
	public Integer getTbRefuseCount() {
		Integer num = 0;
		//淘宝的 常量
		Set<String> taobaoSet = new HashSet<>();
		taobaoSet.add("支付宝交易半年内低于5条交易记录");
		taobaoSet.add("淘宝交易半年内低于5条交易记录");
		taobaoSet.add("有花呗罚息");
		taobaoSet.add("支付宝注册时长低于90天");
		taobaoSet.add("获取不到淘宝交易");
		taobaoSet.add("获取不到支付宝交易");taobaoSet.add("支付宝注册时长低于180天");
		taobaoSet.add("获取不到收货地址");taobaoSet.add("获取不到淘宝基本信息");
		taobaoSet.add("淘宝数据未获取到");taobaoSet.add("淘宝交易半年内低于10条交易记录");

		HashMap<String,Object> hashMap = new HashMap<>();
		hashMap.put("tbOnlineTime","2018-03-28"); //淘宝上线时间
		List<RiskOrders> riskOrdersList = this.selectAfterTbReport(hashMap);
		if (riskOrdersList == null) {return 0;}
		for (RiskOrders riskOrders : riskOrdersList) {
			num += parseRiskOrder(taobaoSet,riskOrders);
		}
		log.info("最后总人数 = :{}",num);
		return num;
	}


	private Integer parseRiskOrder(Set<String> taoBaoSet, RiskOrders riskOrders){
		Set<String> riskSet = new HashSet<>();
		//解析数据磨盒
		Gson gson = new Gson();
		if (riskOrders == null || riskOrders.getReturnParams() == null || !"AUTO_RISK".equals(riskOrders.getOrderType())) {return 0;}
		//auto 中为空
		boolean autoFlag = false,sjmhFlag = true;
		if (riskOrders.getAutoSjmh() != null) {
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(riskOrders.getAutoSjmh()).getAsJsonObject();
			String sjmhData = root.has("shuJuMoHeVoJson")?root.get("shuJuMoHeVoJson").getAsString():null;
			if (sjmhData != null) {
				ShuJuMoHeVo shuJuMoHeVo = gson.fromJson(sjmhData,ShuJuMoHeVo.class);
				if (shuJuMoHeVo != null) {
					List<String> autoList = shuJuMoHeVo.getAutoList();
					if (autoList != null && autoList.size() > 0) {
						if (autoList.size() == 1 && autoList.get(0).equals("手机号状态：未知")) {
							sjmhFlag = true;
						} else {
							sjmhFlag = false;
						}
					}
				}
			}
		}
		//解析风控信息
		String returnParams = riskOrders.getReturnParams();
		CreditReport creditReport = gson.fromJson(returnParams,CreditReport.class);

		if (creditReport != null) {
			Set<Reason> reasonSet = creditReport.getReasons();
			Iterator<Reason> reasonIterator = reasonSet.iterator();
			while (reasonIterator.hasNext()) {
				Reason reason = reasonIterator.next();
				String supplier = reason.getSupplier();
				Map<String,String> items = reason.getItems();
				if (items == null) {continue;}
				if (Supplier.AUTO.toString().equals(supplier)) {
					if (items.size() == 0) {
						autoFlag = true;
					}
					continue;
				}
				if (Supplier.AUTOLOAN.toString().equals(supplier)) {
					Set<String> keys = items.keySet() ;// 得到全部的key
					Iterator<String> iter = keys.iterator() ;
					while (iter.hasNext()) {
						String key = iter.next();
						riskSet.add(key);
					}
					continue;
				}
			}
		}
		//auto 里没有数据,数据墨盒中 autoList中没有数据 ，且autoLoan 中只有淘宝数据 才会 统计此用户
		log.info("borrowId =:{} " + riskOrders.getAssetBorrowId());
		if (!riskSet.isEmpty() && sjmhFlag && autoFlag && taoBaoSet.containsAll(riskSet)) {
			log.info("count borrowId =:{}" + riskOrders.getAssetBorrowId());
			return 1;
		}
		return 0;
	}

	public List<RiskOrders> selectRiskOrdersByTime(HashMap<String, Object> parms) {
		return ordersDao.selectRiskOrdersByStartTimeAndEndTime(parms);
	}
	public Integer selectRiskOrdersByStartTimeAndEndTimeCount(HashMap<String, Object> parms){
		Integer count = ordersDao.selectRiskOrdersByStartTimeAndEndTimeCount(parms);
		if (count == null) {
			return 0;
		}
		return count;
	}

	private Map<String, Map<String,Integer>> parseRiskOrdersCount(HashMap<String,Object> params) {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		//统计所有的命中新信
		Set<String> setRiskMessageAll = new HashSet<>();
		//统计用户
		//根据订单统计的命中数
		Map<String,Integer> countOrderMap = new HashMap<>();
		//根据用户统计被拒人数
		Map<String,Integer> countUserMap = new HashMap<>();
		//将用户的信息都存储起来
		Map<String, Map<String,Integer>> userListMessageMap = new HashMap<>();
		//获取用户总量
		int num = this.selectRiskOrdersByStartTimeAndEndTimeCount(params);
		//分页大小
		int pageSize = 50;
		params.put("pageSize",pageSize);
		//页数
		int pageNum = 0;
		if (num <= pageSize) {
			pageNum = 1;
		} else if (num % pageSize == 0) {
			pageNum = num / pageSize;
		} else {
			pageNum = num / pageSize + 1;
		}
		for (int i=0;i<=pageNum -1;i++) {
			params.put("pageNum",i);
			List<RiskOrders> riskOrdersList = this.selectRiskOrdersByTime(params);
			if (riskOrdersList == null) {continue;}
			for (RiskOrders riskOrders : riskOrdersList) {
				ShuJuMoHeVo shuJuMoHeVo = new ShuJuMoHeVo();
				CreditReport creditReport = new CreditReport();
				if (riskOrders == null) {continue;}
				String userId = riskOrders.getUserId();
				String tempUserId = null;
				String returnParams = riskOrders.getReturnParams();
				String sjmh = riskOrders.getAutoSjmh();
				if (sjmh != null) {
					JsonObject root = parser.parse(sjmh).getAsJsonObject();
					String sjmhData = root.has("shuJuMoHeVoJson")?root.get("shuJuMoHeVoJson").getAsString():null;
					if (sjmhData != null) {
						shuJuMoHeVo = gson.fromJson(sjmhData,ShuJuMoHeVo.class) == null?shuJuMoHeVo : gson.fromJson(sjmhData,ShuJuMoHeVo.class);
					}
				}
				if (returnParams != null) {
					creditReport = gson.fromJson(returnParams,CreditReport.class);
				}
				List<String> list = this.parseCreditReportAndSjmh(creditReport,shuJuMoHeVo);
				//加入set 进行去重
				for (String riskStr : list) {
					//统计被拒订单情况订单
					if (!setRiskMessageAll.contains(riskStr)) {
						//加入到 setRiskMessageAll
						setRiskMessageAll.add(riskStr);
						countOrderMap.put(riskStr,1);
					} else {
						//进行统计
						countOrderMap.replace(riskStr,countOrderMap.get(riskStr)+1);
					}
					//统计被拒用户的情况
					if (userId != null && !userId.equals(tempUserId)) {
						if (countUserMap.containsKey(riskStr)) {
							countUserMap.replace(riskStr,countUserMap.get(riskStr)+1);
						} else {
							countUserMap.put(riskStr,1);
						}
					} else {
						tempUserId = userId;
						//同一个用户
						if (!countUserMap.containsKey(riskStr)) {
							countUserMap.put(riskStr,1);
						}
					}
				}
			}
		}
		log.info("orderCount = :{}",String.valueOf(countOrderMap));
		log.info("userCount =:{} ",String.valueOf(countUserMap));
		userListMessageMap.put("orderCount",countOrderMap);
		userListMessageMap.put("userCount",countUserMap);
		return userListMessageMap;
	}

	@Override
	public Map<String,Map<String,Integer>> riskMessageStatistics(HashMap<String,Object> parms) {
		return this.parseRiskOrdersCount(parms);
	}

	private List<String> parseCreditReportAndSjmh(CreditReport creditReport, ShuJuMoHeVo shuJuMoHeVo){
		//收集所有的信息 AUTO + AUTOLOAN + 数据磨盒
		List<String> list = new ArrayList<>();
		List<String> sjmhAutoList = shuJuMoHeVo.getAutoList();
		if (sjmhAutoList != null && sjmhAutoList.size()>0) {
			list.addAll(sjmhAutoList);
		}
		Set<Reason> reasonSet = creditReport.getReasons();
		if (reasonSet == null) {return list;}
		for (Reason reason : reasonSet) {
			if (reason == null) {continue;}
			Map<String,String> map = reason.getItems();
			Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
			if (reason.getSupplier().equals(Supplier.AUTO.toString())){
				while (entries.hasNext()) {
					Map.Entry<String, String> entry = entries.next();
					list.add(entry.getKey());
				}
				continue;
			}
			if (reason.getSupplier().equals(Supplier.AUTOLOAN.toString())){
				while (entries.hasNext()) {
					Map.Entry<String, String> entry = entries.next();
					list.add(entry.getKey());
				}
				continue;
			}
		}
		return list;
	}
}
