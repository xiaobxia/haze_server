package com.info.back.service;

public interface ITaskJob {
	void userQuota();

	void everyDayUserQuota();

	void mqTask();

	void createCustomerClass();

	/**
	 * 逾期
	 */
	void overdue();


	void repayVoiceRemindToday();

	void repayVoiceRemindTheDayAfterTomorrow();

	void repayVoiceRemindTomorrow();

	/**
	 * 短信提醒
	 */
	void repaySmsRemind();

	/**
	 * 复审短信提醒
	 */
	void reviewRemind(String username);
	/**
	 * 短信提醒 当日
	 */
	void repaySmsRemind9();

	/**
	 * 代扣
	 */
	void withhold();

	void reportRepayment(String firstRepaymentTime, boolean showOverdute);

	/**
	 * 每日还款、逾期报表 中午12点
	 */
	void reportRepayment12();

	/**
	 * 每日还款、逾期报表 每两个小时
	 */
	void reportRepaymentE2();

	/**
	 * 定时查询放款订单状态（new）
	 */
//	void queryPaysState();

	/**
	 * 定时查询非招行订单状态
	 */
//	void queryPaysStateNotCmb();

	/**
	 * 定时查询招行订单状态
	 */
//	void queryPaysStateCmb();

	/**
	 * 定时打款服务费 每天一点运行
	 */
//	void payLoanInterestPays(String loanType);
//	void payLoanInterestPays();

	/**
	 * 放款---用户(包括招行、非招行)
	 */
//	void updateLoanTerm();

	/**
	 * 放款---用户（批量 - 新增）
	 */
	void updateLoanTermForBatch();
	/**
	 * 放款---用户（单条 - 新增）
	 */
	void updateLoanTermForSimgle();

	/**
	 * 第三方活动放款---用户(包括招行、非招行)
	 */
	void updateThreeLoanTerm();

	/**
	 * 定时查询推广统计（每两小时）
	 */
	void channelReport();

	/**
	 * 平台全报告
	 */
	void insertReport();

	/**
	 * 资产划分
	 */
//	void assetsDivision();
	/**
	 * 打款账户2资产推送划分(招财猫)目前每两个小时推送一次当天需要推送的数据
	 */
//	void assetsDivisionExt();

	/**
	 * 
	 * <p>Description:活动统计</p>
	 * @author lixingxing
	 * @date 2017年3月14日 下午4:59:49
	 */
	void acitveClickStatistics();
		
	//
	// /**
	// * 短信提醒后台人员
	// */
	// void sendAlarmMsg();
	/**
	 * 重新开启报警提醒，处理完报警需要调用
	 */
	void restartAlarm();

//	void reSendKDDocking();
	/**
	 * 招财猫资产补推送
	 */
//	void reSendKDDockingExt();
	/**
	 * 复借统计
	 */
	void createBorrowStaticsReport();

//	void updateRetry();

	void thirdReport();
	
//	/**
//	 * 订单结果查询接口
//	 */
//	void queryOrderResult(String orderId);
//
	
//	/**
//	 * 芝麻反馈
//	 */
//	void zhimaDataBatchFeedback();
	/**
	 * 续期代扣处理中查询
	 */
//	void queryRenewalOrder();
	/**
	 * 富有订单查询
	 */
//	void queryOrderResultFuyou();
	/**
	 * 推送黑名单到zzc
	 */
//	void pushZzcBlacklist();

	void autoAssignOrder();
	void autoAssignOrderForNig();

	void insertAssignStatisticForArtificialSend();

	void insertAssignStatisticForSystemSend();

	public void dataAnalyze();

	/**
	 * 应用市场自然流量分析初始化
	 */
	public void appMarketInitialize() throws Exception;

	/**
	 * 应用市场自然流量分析
	 */
	public void appMarketAnalyze() throws Exception;

	/**
	 * 客服标签统计
	 * @throws Exception
	 */
	public void customerLabelCount() throws Exception;

	void aiMessage();

	void afterLoanCensusNew() throws Exception;

	void afterLoanCensus(String expectedRepaymentTime) throws Exception;

	void BackLoanOveCensus() throws Exception;
}
