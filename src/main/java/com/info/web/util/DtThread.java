package com.info.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import lombok.extern.slf4j.Slf4j;

import com.info.web.pojo.BorrowOrder;
import com.info.web.service.IBorrowOrderService;
import com.info.web.service.IPushUserService;
import com.info.web.service.IUserService;

/**
 * 地推线程
 * 
 * @author Administrator
 * 
 */
@Slf4j
public class DtThread extends Thread {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
	private Integer assetId;// 订单id
	private Integer userId;// 用户id
	private String pushType;// 推送类型
	private String dateTime;// 数据时间
	private IBorrowOrderService borrowOrderService;
	private IUserService userService;
	private IPushUserService pushUserService;
	public DtThread(String pushType,Integer userId, Integer assetId, Date dateTime, IUserService userService,IPushUserService pushUserService
			,IBorrowOrderService borrowOrderService) {
 
		this.assetId = assetId;
		this.userId = userId;
		if (dateTime == null) {
			dateTime = new Date();
		}
		this.dateTime = dateFormat.format(dateTime);
		this.userService = userService;

		this.borrowOrderService = borrowOrderService;
		this.pushType = pushType;
		this.pushUserService=pushUserService;
	}

	public void run() {
		try {
			if (pushType != null) {
				HashMap<String, Object> pushMap = new HashMap<String, Object>();
				pushMap = userService.selectPushId(Integer.valueOf(userId.toString()));
				if (pushMap==null||pushMap.get("pushId") == null) {
					return;
				}
				if (pushType.equals(UserPushUntil.PUSH_BORROWSUCC)||pushType.equals(UserPushUntil.PUSH_REPAYMENTSUCC)||pushType.equals(UserPushUntil.PUSH_OVERDUESUCC)) {
					BorrowOrder borrow=	borrowOrderService.findOneBorrow(assetId);
					if(borrow==null){
						return;
					}
					//只判断用户第一次借款订单
					if(borrow.getCustomerType().intValue()!=BorrowOrder.CUSTOMER_NEW){
						return;
					}
				}  
				 
				HashMap<String, Object> map = new HashMap<>();
				map.put("userId", userId);
				map.put("pushId", pushMap.get("pushId"));
				map.put("approveTime", dateTime);
				map.put("pushType", pushType);
				pushUserService.addPushUserApprove(map);
			}

		} catch (Exception e) {
			log.error(" Dt info error pushType:{}, error:{}",pushType ,e);
		}
	}
}
