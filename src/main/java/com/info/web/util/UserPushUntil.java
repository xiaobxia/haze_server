package com.info.web.util;

public class UserPushUntil {
	
	//地推认证要素推送
	public static final String ADD_URL = "/back/totalUser/saveUpdateUserInfo";
	
	//每日统计推送
	public static final String REPORT_URL = "/back/totalUser/saveUpdateEveryDayUserInfo";
	
	//用户重新批量推送
	public static final String REPORT_USER_URL = "/back/totalUser/backFillUserInfo";
	
	public static String PUSH_BORROWSUCC="BORROW_SUCC";
	public static String PUSH_REPAYMENTSUCC="REPAYMENT_SUCC";
	public static String PUSH_OVERDUESUCC="OVERDUE_SUCC";

}
