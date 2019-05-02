package com.info.risk.pojo;

public class FeedBackData {

	private String userCredentialsType; // 	证件类型
	
	private String userCredentialsNo; // 证件号码  
	
	private String userName; //用户名
	
	private String orderNo; //申请单号
	
	private String bizType; //业务种类  1-贷款

	/**********新增start************/
	private String bizDate;//数据统计日期 格式：YYYY-MM-DD

	private String linkedMerchantId;//二级商户编号

	private String sceneType;//场景类型

	private String sceneDesc;//场景描述

	private String sceneStatus;//场景状态

	private String rectifyFlag;//数据订正标识

	/**********新增end************/

	private String payMonth; //还款月份
	
	private String gmtOvdDate;//各阶段时间日期  数据格式：yyyy-mm-dd

	private String installmentDueDate;//业务阶段日期 数据格式：yyyy-mm-dd
	
	private String orderStatus; //01-审批通过 	02-审批否决 	03-用户放弃	04-已放款
	
	private String createAmt; // 授信额度 / 放款金额  单位为元，最多保留2位小数
	
	private String overDueDays; //当前逾期天数  填写当前贷款应还未还的连续天数：当order_status=01，02，03时，置空反馈；当order_status=04且订单当前未逾期，填0；当order_status=04且订单当前逾期，填写当前贷款应还未还的连续天数；
	
	private String overdueAmt; //当前贷款应还未还的本息之和 	当order_status=01，02，03时，置空反馈；当order_status=04且订单当前未逾期，填0；当order_status=04且订单当前逾期，填写当前贷款应还未还的本息之和；单位为元，最多保留2位小数
	
	private String gmtPay;//整笔贷款结清日期，结清日填写，若贷款未全部结清，则置空反馈	数据格式：yyyy-mm-dd
	
	private String memo;

	/*************新增start***************/
	public String getLinkedMerchantId() { return linkedMerchantId; }

	public void setLinkedMerchantId(String linkedMerchantId) { this.linkedMerchantId = linkedMerchantId; }

	public String getBizDate() { return bizDate; }

	public void setBizDate(String bizDate) { this.bizDate = bizDate; }

	public String getSceneType() { return sceneType; }

	public void setSceneType(String sceneType) { this.sceneType = sceneType; }

	public String getSceneDesc() { return sceneDesc; }

	public void setSceneDesc(String sceneDesc) { this.sceneDesc = sceneDesc; }

	public String getSceneStatus() { return sceneStatus; }

	public void setSceneStatus(String sceneStatus) { this.sceneStatus = sceneStatus; }

	public String getRectifyFlag() { return rectifyFlag; }

	public void setRectifyFlag(String rectifyFlag) { this.rectifyFlag = rectifyFlag; }

	public String getInstallmentDueDate() { return installmentDueDate; }

	public void setInstallmentDueDate(String installmentDueDate) { this.installmentDueDate = installmentDueDate; }
	/*************新增end***************/

	public String getUserCredentialsType() {
		return userCredentialsType;
	}

	public void setUserCredentialsType(String userCredentialsType) {
		this.userCredentialsType = userCredentialsType;
	}

	public String getUserCredentialsNo() {
		return userCredentialsNo;
	}

	public void setUserCredentialsNo(String userCredentialsNo) {
		this.userCredentialsNo = userCredentialsNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public String getGmtOvdDate() {
		return gmtOvdDate;
	}

	public void setGmtOvdDate(String gmtOvdDate) {
		this.gmtOvdDate = gmtOvdDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCreateAmt() {
		return createAmt;
	}

	public void setCreateAmt(String createAmt) {
		this.createAmt = createAmt;
	}

	public String getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(String overDueDays) {
		this.overDueDays = overDueDays;
	}

	public String getOverdueAmt() {
		return overdueAmt;
	}

	public void setOverdueAmt(String overdueAmt) {
		this.overdueAmt = overdueAmt;
	}

	public String getGmtPay() {
		return gmtPay;
	}

	public void setGmtPay(String gmtPay) {
		this.gmtPay = gmtPay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
