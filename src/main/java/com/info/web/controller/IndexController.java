package com.info.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SpringUtils;
import com.info.back.utils.SysCacheUtils;
import com.info.risk.service.IOutOrdersService;
import com.info.web.listener.IndexInit;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.OutOrders;
import com.info.web.service.IBorrowOrderService;
import com.info.web.util.DateUtil;
import com.info.web.util.GenerateNo;
import com.info.web.util.encrypt.MD5coding;

/**
 * 首页
 * @author gaoyuhai
 * 2016-12-10 上午11:29:15
 */
@Slf4j
@Controller
public class IndexController extends BaseController {
	
	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IOutOrdersService outOrdersService;
	/**
	 * 更新系统缓存<br>
	 */
	@RequestMapping("updateCache_otherUpdateMe")
	public void updateCache_otherUpdateMe(HttpServletResponse response) {
		boolean succ = false;
		try {
			new IndexInit().updateCache();
			succ = true;
		} catch (Exception e) {
			log.error("updateCache error:{}", e);
		}
 		SpringUtils.renderDwzResult(response, succ, succ ? "刷新缓存成功" : "刷新缓存失败");
	}
	// 模拟请求推送合同
	@RequestMapping(method = RequestMethod.POST, value = "sendContractInfo")
	public void sendContractInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
		ServiceResult serviceResult = new ServiceResult("200", "成功");
		String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
		String orderNo = GenerateNo.nextOrdId();
		OutOrders orders = new OutOrders();
		try {

			// 插入订单

			orders.setOrderNo(orderNo);
			orders.setOrderType(OutOrders.orderType_ZCM);
			// orders.setAssetOrderId(borrowOrder.getId());
			orders.setReqParams(jsonString);
			orders.setAddTime(new Date());
			orders.setAct("CONTRACT");
			orders.setStatus(OutOrders.STATUS_WAIT);
			orders.setTablelastName(tablelastName);
			outOrdersService.insertByTablelastName(orders);

			String signT;
			String oid_platT;
			String timestamp;
			Object objBorrow_info;
			Object objInvestor_info;
			JSONObject jsonObj;
			try {
				jsonObj = JSONObject.fromObject(jsonString);
				signT = jsonObj.getString("Sign");
				oid_platT = jsonObj.getString("Oid_plat");
				timestamp = jsonObj.getString("Timestamp");
				objBorrow_info = jsonObj.get("Borrow_info");
				objInvestor_info = jsonObj.get("Investor_info");
			} catch (Exception e) {
				log.error("error:{}",e);
				serviceResult = new ServiceResult("4000", "参数异常");
				return;
			}

			LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");

			Date nowDate = new Date();
			String nowDateS = DateUtil.getDateFormat(nowDate, "yyyy-MM-dd");

//			HashMap<String, Object> paramsM = new HashMap<String, Object>();
//			paramsM.put("packetTime", nowDateS);
//			paramsM.put("fillupFlag", 0);

			String oid_plat = mapFee.get("oid_plat_ZCM");
			String business_password = mapFee.get("business_password_ZCM");

			String business_code = mapFee.get("business_code_ZCM");
			String business_license = mapFee.get("business_license_ZCM");

//			HashMap<String, Integer> params = new HashMap<String, Integer>();
//			params.put("soketOut", 5000);
//			params.put("connOut", 5000);

			if (oid_platT.equals(oid_plat)) {

				String sign = MD5coding.MD5(oid_plat + MD5coding.MD5(business_password) + MD5coding.MD5(business_code)
						+ MD5coding.MD5(business_license) + timestamp);
				if (sign.equals(signT)) {

					JSONArray borrow_infos = null;
					if (objBorrow_info instanceof JSONObject) {
						borrow_infos = new JSONArray();
						borrow_infos.add(objBorrow_info);
					} else if (objBorrow_info instanceof JSONArray) {
						borrow_infos = jsonObj.getJSONArray("Borrow_info");
					}
					JSONArray investor_infos = null;
					if (objInvestor_info instanceof JSONObject) {
						investor_infos = new JSONArray();
						investor_infos.add(objInvestor_info);
					} else if (objInvestor_info instanceof JSONArray) {
						investor_infos = jsonObj.getJSONArray("Investor_info");
					}

					if (borrow_infos != null && borrow_infos.size() > 0) {
						List<BorrowOrder> boNewList = new ArrayList<BorrowOrder>();
						int userLength = investor_infos.size();
						int j = 0;
						for (int i = 0; i < borrow_infos.size(); i++) {
							JSONObject obj = borrow_infos.getJSONObject(i);
							JSONObject investor = investor_infos.getJSONObject(j);
							BorrowOrder boNew = new BorrowOrder();
							boNew.setId(obj.getInt("Order_number"));
							boNew.setRealname(investor.getString("Real_name"));
							boNew.setIdNumber(investor.getString("Id_number"));
							boNew.setMoneyAmount((int) Double.parseDouble(investor.getString("Money_amount")));
							boNew.setCapitalType(BorrowOrder.LOAN_ACCOUNT2);
							boNew.setOperatorName(BorrowOrder.LOAN_ACCOUNTMap.get(BorrowOrder.LOAN_ACCOUNT2));
							j++;
							if (j == userLength) {
								j = 0;
							}
							boNewList.add(boNew);
						}
						borrowOrderService.batchInsertBCI(boNewList);
						serviceResult = new ServiceResult("200", "success");
					} else {
						serviceResult = new ServiceResult("4000", "参数异常 null");
					}

				} else {
					serviceResult = new ServiceResult("2000", "验签失败");
				}

			} else {
				serviceResult = new ServiceResult("1000", "商户号不匹配！");
			}
			//
		} catch (Exception e) {
			if (e.getMessage().contains("index_order_id")) {
				serviceResult = new ServiceResult("3000", "订单号重复");
				log.error("assetpackqueryZCM record is exist");
			}
			log.error("checkIsBlack error:{}", e);

		} finally {
			log.info("requestparams:{}",jsonString);
			log.info(" sendContractInfo result ==:{}",serviceResult.toString());
			// 更新订单
			OutOrders ordersNew = new OutOrders();
			ordersNew.setId(orders.getId());
			ordersNew.setOrderNo(orderNo);
			ordersNew.setReturnParams(serviceResult.toString());
			ordersNew.setUpdateTime(new Date());
			ordersNew.setStatus(OutOrders.STATUS_SUC);
			ordersNew.setTablelastName(tablelastName);
			outOrdersService.updateByTablelastName(ordersNew);
			SpringUtils.renderJson(response, serviceResult);

		}

	}
}
