package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.*;

public class BorrowOrder {
	private BigDecimal renewalPoundage;

	private BigDecimal renewalFee;
	private Integer id;

	private Integer userId;

	private String outTradeNo;

	private Integer orderType;
	private String orderTypeName;

	private Integer moneyAmount;

	private Integer apr;

	private Integer loanInterests;

	private Integer intoMoney;

	private Integer loanMethod;

	private Integer loanTerm;

	private String operatorName;

	private Date createdAt;

	private Date updatedAt;

	private Date orderTime;

	private Date loanTime;

	private Date loanEndTime;

	private Integer lateFeeApr;

	private Integer receiveCardId;

	private Integer debitCardId;

	private Integer capitalType;

	private String reasonRemark;

	private Integer creditLv;

	private Byte isHitRiskRule;

	private Byte autoRiskCheckStatus;
	private String idNumber;//身份证号
	private Integer bankIscmb;  //对应银行是否是招行；1：是；2：否
	private String userPhone;
	private String realname;
	private String cardNo;
	private String bankNumber;
	private String paystatus;
	private String paystatusRemark;
	private Integer customerType;
	private String customerTypeName;
	private String channelName;

	private Integer reviewBackUserId;//被分派的复审人员id
	private String reviewBackUserName;//被分派的复审人员姓名
	private Integer reviewStatus;//复审分派状态
	private Date distributeTime;
	private String reBackUserName;

	private String projectName;

	private String cfcaContractId;

	private String projectNameVal;
	//用户放款次数
    private String loanCount;

	//用户借款设备类型
	private String clientType;//1、android 2、ios 3、wap

	public String getClientType() { return clientType; }

	public void setClientType(String clientType) { this.clientType = clientType; }

	//***************************************放款账户配置****************
	 public static final Map<Integer, String> LOAN_ACCOUNTMap = new HashMap<Integer, String>();
	
	 /**
	  * 放款账户配置
	  */
	public static final Integer LOAN_ACCOUNT1=1;
	public static final Integer LOAN_ACCOUNT2=2;
	public static final int[] LOAN_ACCOUNTS=new int[]{LOAN_ACCOUNT1,LOAN_ACCOUNT2};
	/**
	 * 放款期限配置
	 */
	public static final int[] LOAN_TREMS=new int[]{7,14};




	/**初始状态**/
	public static final String SUB_PAY_CSZT ="0";
	/**支付成功**/
	public static final String SUB_PAY_SUCC ="0000";
	/**网络异常**/
	public static final String SUB_NERWORK_ERROR ="404";
	/**提交异常**/
	public static final String SUB_ERROR ="500";
	/** 提交成功，需要查询结果  **/
	public static final String SUB_SUBMIT="5101";
	/** 支付失败 **/
	public static final String SUB_PAY_FAIL ="501";
	/** 提交失败，需要重新提交**/
	public static final String SUB_SUB_FAIL ="502";
	/** 报文解析失败，需要查询结果**/
//	public static final String PAY_RETURN_FAIL ="504";
	

	/**打款异常 **/
	public static final String PAY_ERROR ="600";
	/** 支付失败**/
	public static final String PAY_PAY_FAIL ="601";
	/**支付已被银行受理，需要再次查询 **/
	public static final String PAY_SUBMIT ="611";
	
 
 /**
  * 提交打款处理返回结果：5开头的接口
  *  0000支付成功
  * 5101:提交成功，需要查询结果  
  * 501: 支付失败 ,
  * 
  * 502:提交失败，需要重新提交
  * 504:报文解析失败，需要查询结果
  * 502提交失败，需要重新提交
  * 
  * 601 支付失败
  * 611 支付已被银行受理，需要再次查询
  * 
  * 
  */
       
 public static final Map<String, String> paystatusMap = new HashMap<String, String>();
 static {
	 paystatusMap.put(SUB_PAY_CSZT,"待放款");
	 paystatusMap.put(SUB_PAY_SUCC,"放款成功");
	 paystatusMap.put(SUB_SUBMIT,"正在放款处理中");
 }
 
 private String  payRemark; 
	private String yurref;
	private String serialNo;
	private Integer status;
	private String statusName;
	private String remark;
	private User user;

	private String verifyTrialUser; // 初审人
	private Date verifyTrialTime;// 初审时间
	private String verifyTrialRemark;// 初审意见

	private String verifyReviewUser;// 复审人
	private Date verifyReviewTime;// 复审时间
	private String verifyReviewRemark;// 复审意见
	
	private String verifyLoanUser;// 放款审核人
	private Date verifyLoanTime;// 放款审核时间
	private String verifyLoanRemark;// 放款意见

	public static final Map<Integer, String> loanMethed = new HashMap<>();

	public static final Map<Integer, String> orderTypes = new HashMap<>();

 
   /**机审或人工审核借款信息的标识*/
	public static final String REVIEW_BORROW = "REVIEW_BORROW_";
	/**
	 * 有效时间，单位秒
	 */
	public static final int REVIEW_BORROW_SECOND = 10;
	/**
	 * 一天
	 */
	public static final int ALARM_ONEDAY = 60*60*24;
	
 
	static {
		orderTypes.put(1, "多米优");
		orderTypes.put(0, "其它平台");

		loanMethed.put(0, "天");
		loanMethed.put(1, "月");
		loanMethed.put(2, "年");
		
		
		LOAN_ACCOUNTMap.put(LOAN_ACCOUNT1, "上海皖湘网络信息科技有限公司;;上海市");
		LOAN_ACCOUNTMap.put(LOAN_ACCOUNT2, "上海皖湘网络信息科技有限公司;;上海市");
	}
	// 状态：:、:、:；
	/**
	 * 初审通过
	 */
	public static final Integer STATUS_CSTG = 1;
	/**
	 * 待初审(待机审)
	 */
	public static final Integer STATUS_DCS = 0;
//	/**
//	 * 机审拒绝
//	 */
//	public static final Integer STATUS_JSJJ = 10001;
	/** 初审驳回
	 */
	public static final Integer STATUS_CSBH = -3;
	/**复审驳回 
	 */
	public static final Integer STATUS_FSBH = -4;
	/**复审驳回并拉黑
	 */
	public static final Integer STATUS_FSBHLH = -50;

	public static final Integer STATUS_YHQXJK = -40;
	/***复审通过,待放款
	 */
	public static final Integer STATUS_FSTG = 20;
	/** 放款驳回
	 */
	public static final Integer STATUS_FKBH = -5;
	/** 放款中
	 */
	public static final Integer STATUS_FKZ = 22;
	/**
	 * 放款失败
	 */
	public static final Integer STATUS_FKSB = -10;
	/**
	 * 已放款
	 */
	public static final Integer STATUS_HKZ = 21;
	/**
	 * 部分还款
	 */
	public static final Integer STATUS_BFHK = 23;
	/**
	 * 已还款
	 */
	public static final Integer STATUS_YHK = 30;
	/**
	 * 逾期已还款
	 */
	public static final Integer STATUS_YQYHK = 34;
	
	
	/**
	 * 已逾期
	 */
	public static final Integer STATUS_YYQ = -11;
	/**
	 * 已坏账
	 */
	public static final Integer STATUS_YHZ = -20;
	/**
	 * 扣款中
	 */
	public static final Integer STATUS_KKZ = 12;
	/**
	 * 扣款失败
	 */
	public static final Integer STATUS_KKSB = -7;

	public static final Map<Integer, String> borrowStatusMap = new HashMap<Integer, String>();

	static {
		borrowStatusMap.put(STATUS_DCS, "待机审");
//		borrowStatusMap.put(STATUS_JSJJ, "机审拒绝"); 
		borrowStatusMap.put(STATUS_CSBH, "初审驳回");
		
		borrowStatusMap.put(STATUS_CSTG, "初审通过/待复审");
		borrowStatusMap.put(STATUS_FSBH, "复审驳回");
		borrowStatusMap.put(STATUS_FSTG, "复审通过/待放款审核");
		borrowStatusMap.put(STATUS_FKBH, "放款驳回");
		borrowStatusMap.put(STATUS_FKZ, "放款中");
		borrowStatusMap.put(STATUS_FKSB, "放款失败");
		borrowStatusMap.put(STATUS_HKZ, "已放款/待还款");
		borrowStatusMap.put(STATUS_BFHK, "部分还款");
		borrowStatusMap.put(STATUS_YHK, "正常已还款");
		borrowStatusMap.put(STATUS_YQYHK, "逾期已还款");
		borrowStatusMap.put(STATUS_YYQ, "已逾期");
		borrowStatusMap.put(STATUS_YHZ, "已坏账");
		// 二级状态
		borrowStatusMap.put(STATUS_KKZ, "扣款中");// +
		borrowStatusMap.put(STATUS_KKSB, "扣款失败");// +

		/*//添加新用户和老用户
		borrowStatusMap.put(88,"老用户");
		borrowStatusMap.put(66,"新用户");*/

		//添加AI状态
		borrowStatusMap.put(666, "待AI验证");
		borrowStatusMap.put(667, "AI验证失败");
		borrowStatusMap.put(-50, "驳回并拉黑");
		borrowStatusMap.put(-40, "用户取消借款");
	}

 
//前台展示
	
	
	public static final String shenhezhong="审核中";
	public static final String fangkanSucee="待还款";
	public static final String shenheFail="审核失败";
	public static final String yiyuqi="已逾期";
	public static final String finish="已还款";
	/**
	 * 前端
	 */
	public static final Map<Integer, String> borrowStatusMap_front = new HashMap<Integer, String>();

	static {
		 //审核中
		borrowStatusMap_front.put(STATUS_DCS, shenhezhong);
//		borrowStatusMap_front.put(STATUS_JSJJ, shenhezhong);
		borrowStatusMap_front.put(STATUS_CSTG, shenhezhong);
		borrowStatusMap_front.put(STATUS_FSTG, shenhezhong);
		//审核失败
		borrowStatusMap_front.put(STATUS_CSBH, shenheFail);
		borrowStatusMap_front.put(STATUS_FSBH, shenheFail);
		borrowStatusMap_front.put(STATUS_FKBH, shenheFail);
		//已还款
		borrowStatusMap_front.put(STATUS_YHK, finish);
		borrowStatusMap_front.put(STATUS_YQYHK, finish);
		
		borrowStatusMap_front.put(STATUS_HKZ,fangkanSucee);
		
		borrowStatusMap_front.put(STATUS_FKZ, "放款中");
		borrowStatusMap_front.put(STATUS_FKSB, "放款中");
		
		borrowStatusMap_front.put(STATUS_HKZ,fangkanSucee);
		borrowStatusMap_front.put(STATUS_BFHK, "部分还款");

		borrowStatusMap_front.put(STATUS_YYQ, yiyuqi);
		borrowStatusMap_front.put(STATUS_YHZ, yiyuqi);
}
	/**
	 * 审核中
	 */
	public static final Map<Integer, String> borrowStatusMap_shenhezhong = new HashMap<Integer, String>();
//	/**
//	 * 放款成功,等待还款
//	 */
//	public static final Map<Integer, String> borrowStatusMap_fangkanSucee = new HashMap<Integer, String>();
//	/**
//	 * 审核失败
//	 */
	public static final Map<Integer, String> borrowStatusMap_shenheFail = new HashMap<Integer, String>();
//	/**
//	 * 已逾期
//	 */
//	public static final Map<Integer, String> borrowStatusMap_yiyuqi = new HashMap<Integer, String>();
//	/**
//	 * 已还款
//	 */
//	public static final Map<Integer, String> borrowStatusMap_finish = new HashMap<Integer, String>();

	public static final List<Integer> borrowStatusMap_kefangkuan = new ArrayList<>();

//	
	static {
//	    //审核中
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_DCS, shenhezhong);
//		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_JSJJ, shenhezhong);
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_CSTG, shenhezhong);
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_FSTG, shenhezhong);
//	    // 还款中 
//		borrowStatusMap_fangkanSucee.put(BorrowOrder.STATUS_HKZ, fangkanSucee);
//		//审核失败
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_CSBH, shenheFail);
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FSBH, shenheFail);
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FKBH, shenheFail);
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_YHQXJK, shenheFail);
//		//已逾期
//		borrowStatusMap_yiyuqi.put(BorrowOrder.STATUS_YYQ, yiyuqi);
//		borrowStatusMap_yiyuqi.put(BorrowOrder.STATUS_YHZ, yiyuqi);
//		
//		//已还款
//		borrowStatusMap_finish.put(BorrowOrder.STATUS_YHK, finish);
//		borrowStatusMap_finish.put(BorrowOrder.STATUS_YQYHK, finish);


		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_HKZ);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_FKZ);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_YHK);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_KKSB);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_KKZ);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_BFHK);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_YQYHK);
		borrowStatusMap_kefangkuan.add(BorrowOrder.STATUS_YYQ);
	}

	/**
	 * 模型编号
	 */
	private String modelCode;
	/**
	 * 变量增删版本号
	 */
	private Integer variableVersion;
	/**
	 * cutoff线版本号
	 */
	private Integer cutoffVersion;
	/**
	 * 分箱得分版本号
	 */
	private Integer binningVersion;
	/**
	 * 不重要修改的版本号
	 */
	private Integer canIgnoreVersion;

	/**
	 * cutoff线
	 */
	private Integer cutoff;
	/**
	 * 复审上限
	 */
	private Integer reviewUp;
	/**
	 * 复审下限
	 */
	private Integer reviewDown;
	/**
	 * 模型放款意见
	 */
	private Integer modelAdvice;
	/**
	 * 模型得分
	 */
	private Integer modelScore;
	/**
	 * 总放款开关状态(0、开启；1、关闭)
	 */
	private Integer loanSwitchStatus;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 存储地域信息，作VO使用
	 */
	private String area;

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public Integer getVariableVersion() {
		return variableVersion;
	}

	public void setVariableVersion(Integer variableVersion) {
		this.variableVersion = variableVersion;
	}

	public Integer getCutoffVersion() {
		return cutoffVersion;
	}

	public void setCutoffVersion(Integer cutoffVersion) {
		this.cutoffVersion = cutoffVersion;
	}

	public Integer getBinningVersion() {
		return binningVersion;
	}

	public void setBinningVersion(Integer binningVersion) {
		this.binningVersion = binningVersion;
	}

	public Integer getCanIgnoreVersion() {
		return canIgnoreVersion;
	}

	public void setCanIgnoreVersion(Integer canIgnoreVersion) {
		this.canIgnoreVersion = canIgnoreVersion;
	}

	public Integer getCutoff() {
		return cutoff;
	}

	public void setCutoff(Integer cutoff) {
		this.cutoff = cutoff;
	}

	public Integer getReviewUp() {
		return reviewUp;
	}

	public void setReviewUp(Integer reviewUp) {
		this.reviewUp = reviewUp;
	}

	public Integer getReviewDown() {
		return reviewDown;
	}

	public void setReviewDown(Integer reviewDown) {
		this.reviewDown = reviewDown;
	}

	public Integer getModelAdvice() {
		return modelAdvice;
	}

	public void setModelAdvice(Integer modelAdvice) {
		this.modelAdvice = modelAdvice;
	}

	public Integer getModelScore() {
		return modelScore;
	}

	public void setModelScore(Integer modelScore) {
		this.modelScore = modelScore;
	}

	public Integer getLoanSwitchStatus() {
		return loanSwitchStatus;
	}

	public void setLoanSwitchStatus(Integer loanSwitchStatus) {
		this.loanSwitchStatus = loanSwitchStatus;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getCustomerType() {
		return customerType;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	
	public static final Integer CUSTOMER_OLD = 1;
	public static final Integer CUSTOMER_NEW = 0;

	public static final Map<Integer, String> customerTypes = new HashMap<Integer, String>();
	static {
		customerTypes.put(CUSTOMER_OLD, "老用户");
		customerTypes.put(CUSTOMER_NEW, "新用户");
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
		this.customerTypeName =  customerTypes.get(customerType);
	}


    /**
     * 1 显示评分卡
     * 2 显示：人工否决：信审人员姓名或者人工否决：空白
     * 3 其他都是默认显示信审人员姓名
     */
    private Integer reviwRiskLabel;

    public Integer getReviwRiskLabel() {
        return reviwRiskLabel;
    }

    public void setReviwRiskLabel(Integer reviwRiskLabel) {
        this.reviwRiskLabel = reviwRiskLabel;
    }

    public String getPaystatus() {
        return paystatus;
    }

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
		String remark = paystatus == null ? "" : paystatusMap.get(paystatus);
		if("".equals(remark)){
			remark = "";
		}

		if(remark == null){
			remark = "放款失败" + "["+paystatus+"]";
		}else{
			remark = remark + "["+paystatus+"]";
		}
		this.paystatusRemark = remark;
	}

	public String getPaystatusRemark() {return paystatusRemark;}

	public void setPaystatusRemark(String paystatusRemark) {this.paystatusRemark = paystatusRemark;}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public String getVerifyReviewUser() {
		return verifyReviewUser;
	}

	public String getVerifyLoanUser() {
		return verifyLoanUser;
	}

	public void setVerifyLoanUser(String verifyLoanUser) {
		this.verifyLoanUser = verifyLoanUser;
	}

	public Date getVerifyLoanTime() {
		return verifyLoanTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getYurref() {
		return yurref;
	}

	public void setYurref(String yurref) {
		this.yurref = yurref;
	}

	public void setVerifyLoanTime(Date verifyLoanTime) {
		this.verifyLoanTime = verifyLoanTime;
	}

	public String getVerifyLoanRemark() {
		return verifyLoanRemark;
	}

	public void setVerifyLoanRemark(String verifyLoanRemark) {
		this.verifyLoanRemark = verifyLoanRemark;
	}

	public void setVerifyReviewUser(String verifyReviewUser) {
		this.verifyReviewUser = verifyReviewUser;
	}

	public Date getVerifyReviewTime() {
		return verifyReviewTime;
	}

	public void setVerifyReviewTime(Date verifyReviewTime) {
		this.verifyReviewTime = verifyReviewTime;
	}

	public String getVerifyReviewRemark() {
		return verifyReviewRemark;
	}

	public void setVerifyReviewRemark(String verifyReviewRemark) {
		this.verifyReviewRemark = verifyReviewRemark;
	}
	public String getVerifyTrialUser() {
		return verifyTrialUser;
	}

	public void setVerifyTrialUser(String verifyTrialUser) {
		this.verifyTrialUser = verifyTrialUser;
	}

	public Date getVerifyTrialTime() {
		return verifyTrialTime;
	}

	public void setVerifyTrialTime(Date verifyTrialTime) {
		this.verifyTrialTime = verifyTrialTime;
	}

	public String getVerifyTrialRemark() {
		return verifyTrialRemark;
	}

	public void setVerifyTrialRemark(String verifyTrialRemark) {
		this.verifyTrialRemark = verifyTrialRemark;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		this.orderTypeName = orderTypes.get(orderType);
	}

	public Integer getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(Integer moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public Integer getApr() {
		return apr;
	}

	public void setApr(Integer apr) {
		this.apr = apr;
	}

	public Integer getLoanInterests() {
		return loanInterests;
	}

	public void setLoanInterests(Integer loanInterests) {
		this.loanInterests = loanInterests;
	}

	public Integer getIntoMoney() {
		return intoMoney;
	}

	public void setIntoMoney(Integer intoMoney) {
		this.intoMoney = intoMoney;
	}

	public Integer getLoanMethod() {
		return loanMethod;
	}

	public void setLoanMethod(Integer loanMethod) {
		this.loanMethod = loanMethod;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Date getLoanEndTime() {
		return loanEndTime;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public Integer getLateFeeApr() {
		return lateFeeApr;
	}

	public void setLateFeeApr(Integer lateFeeApr) {
		this.lateFeeApr = lateFeeApr;
	}

	public Integer getReceiveCardId() {
		return receiveCardId;
	}

	public void setReceiveCardId(Integer receiveCardId) {
		this.receiveCardId = receiveCardId;
	}

	public Integer getDebitCardId() {
		return debitCardId;
	}

	public void setDebitCardId(Integer debitCardId) {
		this.debitCardId = debitCardId;
	}

	public Integer getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(Integer capitalType) {
		this.capitalType = capitalType;
	}

	public String getReasonRemark() {
		return reasonRemark;
	}

	public void setReasonRemark(String reasonRemark) {
		this.reasonRemark = reasonRemark == null ? null : reasonRemark.trim();
	}

	public Integer getCreditLv() {
		return creditLv;
	}

	public void setCreditLv(Integer creditLv) {
		this.creditLv = creditLv;
	}

	public Byte getIsHitRiskRule() {
		return isHitRiskRule;
	}

	public void setIsHitRiskRule(Byte isHitRiskRule) {
		this.isHitRiskRule = isHitRiskRule;
	}

	public Byte getAutoRiskCheckStatus() {
		return autoRiskCheckStatus;
	}

	public void setAutoRiskCheckStatus(Byte autoRiskCheckStatus) {
		this.autoRiskCheckStatus = autoRiskCheckStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusName = borrowStatusMap.get(status);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	

	public Integer getBankIscmb() {
		return bankIscmb;
	}

	public void setBankIscmb(Integer bankIscmb) {
		this.bankIscmb = bankIscmb;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public String toString() {
		return "BorrowOrder [apr=" + apr + ", autoRiskCheckStatus="
				+ autoRiskCheckStatus + ", capitalType=" + capitalType
				+ ", createdAt=" + createdAt + ", creditLv=" + creditLv
				+ ", debitCardId=" + debitCardId + ", id=" + id
				+ ", intoMoney=" + intoMoney + ", isHitRiskRule="
				+ isHitRiskRule + ", lateFeeApr=" + lateFeeApr
				+ ", loanEndTime=" + loanEndTime + ", loanInterests="
				+ loanInterests + ", loanMethod=" + loanMethod + ", loanTerm="
				+ loanTerm + ", loanTime=" + loanTime + ", moneyAmount="
				+ moneyAmount + ", operatorName=" + operatorName
				+ ", orderTime=" + orderTime + ", orderType=" + orderType
				+ ", orderTypeName=" + orderTypeName + ", outTradeNo="
				+ outTradeNo + ", reasonRemark=" + reasonRemark
				+ ", receiveCardId=" + receiveCardId + ", remark=" + remark
				+ ", status=" + status + ", statusName=" + statusName
				+ ", updatedAt=" + updatedAt + ", userId="
				+ userId + ", verifyLoanRemark=" + verifyLoanRemark
				+ ", verifyLoanTime=" + verifyLoanTime + ", verifyLoanUser="
				+ verifyLoanUser + ", verifyReviewRemark=" + verifyReviewRemark
				+ ", verifyReviewTime=" + verifyReviewTime
				+ ", verifyReviewUser=" + verifyReviewUser
				+ ", verifyTrialRemark=" + verifyTrialRemark
				+ ", verifyTrialTime=" + verifyTrialTime + ", verifyTrialUser="
				+ verifyTrialUser + "]";
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	private Integer autoLoanFlag;
	public Integer getAutoLoanFlag() {
		return autoLoanFlag;
	}
	public void setAutoLoanFlag(Integer autoLoanFlag) {
		this.autoLoanFlag = autoLoanFlag;
	}

	public Integer getReviewBackUserId() {
		return reviewBackUserId;
	}

	public void setReviewBackUserId(Integer reviewBackUserId) {
		this.reviewBackUserId = reviewBackUserId;
	}

	public String getReviewBackUserName() {
		return reviewBackUserName;
	}

	public void setReviewBackUserName(String reviewBackUserName) {
		this.reviewBackUserName = reviewBackUserName;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getReBackUserName() {
		return reBackUserName;
	}

	public void setReBackUserName(String reBackUserName) {
		this.reBackUserName = reBackUserName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCfcaContractId() {
		return cfcaContractId;
	}

	public void setCfcaContractId(String cfcaContractId) {
		this.cfcaContractId = cfcaContractId;
	}

	public String getProjectNameVal() {
		return projectNameVal;
	}

	public void setProjectNameVal(String projectNameVal) {
		this.projectNameVal = projectNameVal;
	}

	public BigDecimal getRenewalPoundage() {
		return renewalPoundage;
	}

	public void setRenewalPoundage(BigDecimal renewalPoundage) {
		this.renewalPoundage = renewalPoundage;
	}

	public BigDecimal getRenewalFee() {
		return renewalFee;
	}

	public void setRenewalFee(BigDecimal renewalFee) {
		this.renewalFee = renewalFee;
	}

	public String getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(String loanCount) {
		this.loanCount = loanCount;
	}
}

