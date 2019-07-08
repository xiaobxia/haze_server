package com.info.back.service;

import com.alibaba.fastjson.JSON;
import com.info.analyze.service.IAppMarketStaticsService;
import com.info.analyze.service.IDataStatisticsService;
import com.info.back.dao.IBackConfigParamsDao;
import com.info.back.pojo.CustomerClassArrange;
import com.info.back.utils.PropertiesUtil;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.ThreadPoolShuntUtil;
import com.info.constant.Constant;
import com.info.web.dao.*;
import com.info.web.pojo.*;
import com.info.web.service.*;
import com.info.web.util.*;
import com.info.web.util.aliyun.RocketMqUtil;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import com.vxianjin.gringotts.dao.util.common.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.info.web.pojo.BorrowOrder.*;
@Slf4j
@Component("taskJob")
public class TaskJob implements ITaskJob {
	@Resource
	private JedisCluster jedisCluster;
	@Autowired
	private IRepaymentService repaymentService;
	@Resource
	private IBackConfigParamsDao backConfigParamsDao;
	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IChannelReportService channelReportService;
	@Autowired
	private ILoanReportService loanReportService;
	@Autowired
	private IReportRepaymentService reportRepaymentService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRepaymentDetailService repaymentDetailService;
	@Autowired
	private IBackStatisticService backStatisticService;
	@Autowired
	private IUserSendMessageService userSendMessageService;
	@Autowired
	private IBorrowStatisticsReportService borrowStatisticsReportService;
	@Autowired
	private IThirdChannelReportService thirdChannelReportService;
	@Autowired
	private IThreeLoanOrderService threeLoanOrderService;
	@Resource
	private IBorrowStatisticsReportDao borrowStatisticsReportDao;

	@Autowired
	private ILoanMoneyReportService loanMoneyReportService;
	@Autowired
	private IDataStatisticsService dataStatisticsService;
	@Autowired
	private IAppMarketStaticsService appMarketStaticsService;
//    @Autowired
//    private CommonProducer commonProducer;
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private IOnlineCustomService onlineCustomService;
	@Autowired
	private ILabelCountService labelCountService;

	@Autowired
	private IBackLoanCensusService backLoanCensusService;

	@Autowired
	private IChannelOveCensusService channelOveCensusService;

	@Override
	public void aiMessage() {
	    log.info("start aiMessage job ");

        List<String> userIdWaitList = borrowOrderService.getUserIdWaitList();
        if(userIdWaitList!=null && !userIdWaitList.isEmpty()){
            log.info("正常流程下，19PM后10AM前没有打AI电话的数量:{}",userIdWaitList.size());
            for(String userId:userIdWaitList){
                User user = userService.getUserPhoneAndName(Integer.valueOf(userId));
                Map<String,String> map = new HashMap<>();
                map.put("phone",user.getUserPhone());
                map.put("name",user.getRealname());
                log.info("send ai message:{}",JSON.toJSONString(map));
                RocketMqUtil.sendAiMessage(JSON.toJSONString(map));
            }
        }

        List<String> userList = borrowOrderService.getUserIdList();
		if(userList!=null && !userList.isEmpty()){
            log.info("两小时内未接通数量:{}",userList.size());

            for(String userId:userList){
		        User user = userService.getUserPhoneAndName(Integer.valueOf(userId));
		        Map<String,String> map = new HashMap<>();
                map.put("phone",user.getUserPhone());
                map.put("name",user.getRealname());
                log.info("send ai message:{}",JSON.toJSONString(map));
                RocketMqUtil.sendAiMessage(JSON.toJSONString(map));
            }
        }
		List<String> userList2 = borrowOrderService.getUserIdList2();

        if(userList2!=null && !userList2.isEmpty()){
            log.info("三小时内内未接通数量:{}",userList2.size());
            for(String userId:userList2){
                User user = userService.getUserPhoneAndName(Integer.valueOf(userId));
                Map<String,String> map = new HashMap<>();
                map.put("phone",user.getUserPhone());
                map.put("name",user.getRealname());
                log.info("send ai message:{}",JSON.toJSONString(map));
                RocketMqUtil.sendAiMessage(JSON.toJSONString(map));
            }
        }
        log.info("end aiMessage job");
	}

	@Override
	public void userQuota() {
		HttpClient httpClient = HttpClients.createDefault();
		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		HttpPost post = new HttpPost(PropertiesUtil.get("APP_HOST_API") + "/" + StringDateUtils.getDrawWithChannel() + "/gringotts/task/userQuota/syn");
		try{
			post.setConfig(requestConfig);
			httpClient.execute(post);
		}catch (Exception e){
			log.error("post api userQuota error:{}",e);
		}
	}

	@Override
	public void everyDayUserQuota() {
		HttpClient httpClient = HttpClients.createDefault();
		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		HttpPost post = new HttpPost(PropertiesUtil.get("APP_HOST_API") + "/" + StringDateUtils.getDrawWithChannel() + "/gringotts/task/everyDayuserQuota/syn");
		try{
			post.setConfig(requestConfig);
			httpClient.execute(post);
		}catch (Exception e){
			log.error("post api  everyDayUserQuota error:{}",e);
		}
	}

	@Override
	public void mqTask() {
		HttpClient httpClient = HttpClients.createDefault();
		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		HttpPost post = new HttpPost(PropertiesUtil.get("APP_HOST_API") + "/" + StringDateUtils.getDrawWithChannel() + "/gringotts/task/mq/syn");
		try{
			post.setConfig(requestConfig);
			httpClient.execute(post);
		}catch (Exception e){
			log.error("post api error:{}",e);
		}
	}

	@Override
	public void createCustomerClass() {
		log.info("createCustomerClass");
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(sf.format(calendar.getTime()));
		Calendar yesToday = Calendar.getInstance();
		yesToday.add(Calendar.DATE, -1);
		try{
			if(customerClassArrange==null){
				CustomerClassArrange yesTodayClass = onlineCustomService.getCustomerClassByDate(sf.format(yesToday.getTime()));
				if(yesTodayClass!=null){
					yesTodayClass.setClassDate(sf.format(calendar.getTime()));
					onlineCustomService.saveCustomerClassArrange(yesTodayClass);
				}
			}
		}catch (Exception e){
			log.error("create class error:{}",e);
		}


	}

//	@Override
//	public void updateRetry() {
//		try {
//			log.info("retry send order");
//			tsOrdersService.updateRetry();
//		} catch (Exception e) {
//			log.error("retry send order error:{}", e);
//		}
//		try {
//			log.info("send user");
//			tsOrdersService.sendSyReg();
//		} catch (Exception e) {
//			log.error("send user error:{}", e);
//		}
//	}

	@Override
	public void overdue() {
		log.info("overdue start");
		Map<String, Object> params = new HashMap<>();
		//已放款，部分还款，已逾期，部分逾期
		Integer statuses[] = new Integer[] { STATUS_HKZ, STATUS_BFHK, STATUS_YYQ, STATUS_YHZ };
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String repaymentTimeEnd = dateFormat.format(date);
        //设置查询状态集合与截止日期
		params.put("statuses", statuses);
		params.put("repaymentTimeEnd", repaymentTimeEnd);
		List<Repayment> repaymentList = repaymentService.findTaskRepayment(params);
		List<String> repaymentIds = new ArrayList<>();
		log.info("repaymentList =:{}",repaymentList.size());
		for (Repayment re : repaymentList) {
			Repayment repayment = repaymentService.selectByPrimaryKey(re.getId());
			if (!repayment.getStatus().equals(BorrowOrder.STATUS_YHK) && !repayment.getStatus().equals(BorrowOrder.STATUS_YQYHK)) {
				repaymentService.overdue(repayment, repaymentIds);
				//tsOrdersService.sendBorrowStatus(repayment.getAssetOrderId());
			}
		}
		if(repaymentIds.size() > 0) {
			log.info("start send repaymentIds ");
			RocketMqUtil.sendMqMessage(PropertiesUtil.get("MQ_APPNAME")+ "_" + Constant.TOPIC_REPAYID,Constant.OVERDUE_TAG,StringUtils.join(repaymentIds, ","));
			log.info("end send repaymentIds ");

		}
		log.info("overdue end total:{}",repaymentList.size());
	}

	/**
	 * “明日”还款短信提醒业务
	 */
	@Override
	public void repayVoiceRemindToday(){
		log.error("repayVoiceRemindToday start");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String repaymentTimeStart = dateFormat.format(date);
		String repaymentTimeEnd = dateFormat.format(DateUtil.addDay(date, 1));
		// String repaymentTimeStart = "2017-01-11";
		// String repaymentTimeEnd = "2017-01-12";
		Map<String, Object> params = new HashMap<>();
		params.put("repaymentTimeStart", repaymentTimeStart);
		params.put("repaymentTimeEnd", repaymentTimeEnd);
		Integer[] statuses = new Integer[] { STATUS_HKZ };
		params.put("statuses", statuses);
		List<Repayment> repayments = repaymentService.findByRepaymentSmsRemind(params);

		//一次传50个用户
		Integer size = 50;
		//本次任务循环多少次
		Integer loop = (repayments.size() % size == 0 ? repayments.size() / size : repayments.size() / size + 1);

		Integer repaymentsSize = 0;
		if (!ArrayUtil.isEmpty(repayments)){
			repaymentsSize = repayments.size();
		}

		for(int j = 0; j < loop; j++) {
			List<Repayment> tmpList = new ArrayList<>();
			if(repayments.size() >= size) {//如果list里面剩下的还大于等于需要取出的
				for(int n = 0; n < size; n++) {//则重复执行size次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			} else {//list里面剩下的已经小于需要去取出的
				int lastSize = repayments.size();
				for(int n = 0; n < lastSize; n++) {//则重复执行lastSize()次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			}
			List<Map<String,Object>> multiList = new ArrayList<>();
			for (Repayment re : tmpList) {
				Map<String, String> user = repaymentService.findUserPhoneName(re.getUserId());
				Map<String, String> card = repaymentService.findCardNo(re.getUserId());
				Map<String,Object> multiMap = new HashMap<>();
				multiMap.put("to", user.get("userPhone"));
				Map<String,String> userInfo = new HashMap<>();
				userInfo.put("name", user.get("realname"));
				String cardNo = card.get("cardNo");
				if (StringUtils.isNotBlank(cardNo)){
					cardNo = cardNo.substring(cardNo.length() - 4);
				}
				userInfo.put("code", cardNo);
				multiMap.put("vars", userInfo);
				multiList.add(multiMap);
			}
			String multi = JSON.toJSONString(multiList);
			VoiceNoticeUtil.vocieNoticeMultixsendToday(multi);
		}
		log.info("repayVoiceRemindToday end total:{}",repaymentsSize);
	}


    /**
     * “明日”还款短信提醒业务
     */
    @Override
    public void repayVoiceRemindTomorrow(){
        log.error("repayVoiceRemindTomorrow start");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String repaymentTimeStart = dateFormat.format(DateUtil.addDay(date, 1));
        String repaymentTimeEnd = dateFormat.format(DateUtil.addDay(date, 2));
        // String repaymentTimeStart = "2017-01-11";
        // String repaymentTimeEnd = "2017-01-12";
        Map<String, Object> params = new HashMap<>();
        params.put("repaymentTimeStart", repaymentTimeStart);
        params.put("repaymentTimeEnd", repaymentTimeEnd);
        Integer[] statuses = new Integer[] { STATUS_HKZ };
        params.put("statuses", statuses);
        List<Repayment> repayments = repaymentService.findByRepaymentSmsRemind(params);

        //一次传50个用户
        Integer size = 50;
        //本次任务循环多少次
		Integer loop = (repayments.size() % size == 0 ? repayments.size() / size : repayments.size() / size + 1);

        Integer repaymentsSize = 0;
        if (!ArrayUtil.isEmpty(repayments)){
            repaymentsSize = repayments.size();
        }
		for(int j = 0; j < loop; j++) {
			List<Repayment> tmpList = new ArrayList<>();
			if(repayments.size() >= size) {//如果list里面剩下的还大于等于需要取出的
				for(int n = 0; n < size; n++) {//则重复执行size次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			} else {//list里面剩下的已经小于需要去取出的
				int lastSize = repayments.size();
				for(int n = 0; n < lastSize; n++) {//则重复执行lastSize()次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			}
			List<Map<String,Object>> multiList = new ArrayList<>();
			for (Repayment re : tmpList) {
				Map<String, String> user = repaymentService.findUserPhoneName(re.getUserId());
                Map<String, String> card = repaymentService.findCardNo(re.getUserId());
				Map<String,Object> multiMap = new HashMap<>();
				multiMap.put("to", user.get("userPhone"));
				Map<String,String> userInfo = new HashMap<>();
				userInfo.put("name", user.get("realname"));
				String cardNo = card.get("cardNo");
				if (StringUtils.isNotBlank(cardNo)){
					cardNo = cardNo.substring(cardNo.length() - 4);
				}
				userInfo.put("code", cardNo);
				multiMap.put("vars", userInfo);
				multiList.add(multiMap);
			}
			String multi = JSON.toJSONString(multiList);
			VoiceNoticeUtil.vocieNoticeMultixsendTomorrow(multi);
		}
        log.info("repayVoiceRemindTomorrow end total:{}",repaymentsSize);
    }



	/**
	 * “后天”还款短信提醒业务
	 */
	@Override
	public void repayVoiceRemindTheDayAfterTomorrow(){
		log.error("repayVoiceRemindTheDayAfterTomorrow start");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String repaymentTimeStart = dateFormat.format(DateUtil.addDay(date, 2));
		String repaymentTimeEnd = dateFormat.format(DateUtil.addDay(date, 3));
		// String repaymentTimeStart = "2019-2-24";
		// String repaymentTimeEnd = "2019-2-25";
		Map<String, Object> params = new HashMap<>();
		params.put("repaymentTimeStart", repaymentTimeStart);
		params.put("repaymentTimeEnd", repaymentTimeEnd);
		Integer[] statuses = new Integer[] { STATUS_HKZ };
		params.put("statuses", statuses);
		List<Repayment> repayments = repaymentService.findByRepaymentSmsRemind(params);

		//一次传50个用户
		Integer size = 50;
		//本次任务循环多少次
		Integer loop = (repayments.size() % size == 0 ? repayments.size() / size : repayments.size() / size + 1);

        Integer repaymentsSize = 0;
        if (!ArrayUtil.isEmpty(repayments)){
            repaymentsSize = repayments.size();
        }
		for(int j = 0; j < loop; j++) {
			List<Repayment> tmpList = new ArrayList<>();
			if(repayments.size() >= size) {//如果list里面剩下的还大于等于需要取出的
				for(int n = 0; n < size; n++) {//则重复执行size次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			} else {//list里面剩下的已经小于需要去取出的
				int lastSize = repayments.size();
				for(int n = 0; n < lastSize; n++) {//则重复执行lastSize()次下面的两句代码
					tmpList.add(repayments.get(0));
					repayments.remove(0);
				}
			}
			List<Map<String,Object>> multiList = new ArrayList<>();
			for (Repayment re : tmpList) {
				Map<String, String> user = repaymentService.findUserPhoneName(re.getUserId());
				Map<String, String> card = repaymentService.findCardNo(re.getUserId());
				Map<String,Object> multiMap = new HashMap<>();
				multiMap.put("to", user.get("userPhone"));
				Map<String,String> userInfo = new HashMap<>();
				userInfo.put("name", user.get("realname"));
				String cardNo = card.get("cardNo");
				if (StringUtils.isNotBlank(cardNo)){
					cardNo = cardNo.substring(cardNo.length() - 4);
				}
				userInfo.put("code", cardNo);
				multiMap.put("vars", userInfo);
				multiList.add(multiMap);
			}
			String multi = JSON.toJSONString(multiList);
			VoiceNoticeUtil.vocieNoticeMultixsendTheDayAfterTomorrow(multi);
		}
		log.info("repayVoiceRemindTheDayAfterTomorrow end total:{}",repaymentsSize);
	}
	/**
	 * “明日”还款短信提醒业务
	 */
	@Override
	public void repaySmsRemind() {
		log.error("repaySmsRemind start");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String repaymentTimeStart = dateFormat.format(DateUtil.addDay(date, 1));
		String repaymentTimeEnd = dateFormat.format(DateUtil.addDay(date, 2));
		// String repaymentTimeStart = "2017-01-11";
		// String repaymentTimeEnd = "2017-01-12";
		Map<String, Object> params = new HashMap<>();
		params.put("repaymentTimeStart", repaymentTimeStart);
		params.put("repaymentTimeEnd", repaymentTimeEnd);
		Integer[] statuses = new Integer[] { STATUS_HKZ };
		params.put("statuses", statuses);
		List<Repayment> repayments = repaymentService.findByRepaymentSmsRemind(params);
		for (Repayment re : repayments) {
			try {
				Map<String, String> user = repaymentService.findUserPhoneName(re.getUserId());
				Map<String, String> card = repaymentService.findCardNo(re.getUserId());
				String cardNo = card.get("cardNo");
				String content = user.get("realname") + "##" + (re.getRepaymentAmount() / 100) + "##"
							+ cardNo.substring(cardNo.length() - 4);

				SendSmsUtil.sendSmsDiyCL(user.get("userPhone"), SendSmsUtil.templateld44639, content);
				UserSendMessage message = new UserSendMessage();
				message.setPhone(user.get("userPhone"));// 接收的用户
				message.setMessageCreateTime(new Date());
				message.setMessageContent(content);
				message.setMessageStatus(UserSendMessage.STATUS_SUCCESS);// 发送成功
				// 2
				userSendMessageService.saveUserSendMsg(message);// 添加短息记录
			} catch (Exception e) {
				log.error("sendsms error:{}", e);
			}

		}
		log.error("repaySmsRemind end total:{}",repayments.size());
	}



	@Override
	public void reviewRemind(String username) {
		log.info("reviewRemind start");
		Date date = new Date();
		HashMap<String, Object> params = new HashMap<>();
		params.put("sysName","REVIEW_PHONE");
		List<BackConfigParams> configParams = backConfigParamsDao.findParams(params);
		if(CollectionUtils.isEmpty(configParams)){
			log.info("isEmpty(configParams)");
			return;
		}
		String phoneStr = configParams.get(0).getSysValue();
		log.info("phoneStr:{}",phoneStr);
		if(StringUtils.isBlank(phoneStr)){
			log.info("isBlank(phoneStr)");
			return;
		}
		String[] split = phoneStr.split(",");
		try {
			for (int i = 0; i < split.length; i++) {
				String phone = split[i];
				String content = "平台人员" + username + "于" + com.info.statistic.util.DateUtil.getDateComplete(date) + "登陆操作信审。";
				//SendSmsUtil.sendSmsDiyCL(phone, content);
				log.info("content:{}",content);
				UserSendMessage message = new UserSendMessage();
				message.setPhone(phone);// 接收的用户
				message.setMessageCreateTime(new Date());
				message.setMessageContent(content);
				// 发送成功
				message.setMessageStatus(UserSendMessage.STATUS_SUCCESS);
				// 2
				userSendMessageService.saveUserSendMsg(message);// 添加短息记录
			}
		} catch (Exception e) {
			log.error("reviewRemind error_:{}", e);
		}
		log.info("reviewRemind end");
	}

	/**
	 * “当日”还款短信提醒业务
	 */
	@Override
	public void repaySmsRemind9() {
		log.info("repaySmsRemind9 start");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String repaymentTimeStart = dateFormat.format(date);
		String repaymentTimeEnd = dateFormat.format(DateUtil.addDay(date, 1));
		// String repaymentTimeStart = "2017-01-11";
		// String repaymentTimeEnd = "2017-01-12";
		Map<String, Object> params = new HashMap<>();
		params.put("repaymentTimeStart", repaymentTimeStart);
		params.put("repaymentTimeEnd", repaymentTimeEnd);
		Integer[] statuses = new Integer[] { STATUS_HKZ };
		params.put("statuses", statuses);
		List<Repayment> repayments = repaymentService.findByRepaymentSmsRemind(params);

		for (Repayment re : repayments) {
			try {
				Map<String, String> user = repaymentService.findUserPhoneName(re.getUserId());
				Map<String, String> card = repaymentService.findCardNo(re.getUserId());
				String cardNo = card.get("cardNo");
				String content = user.get("realname") + "##" + (re.getRepaymentAmount() / 100) + "##"
							+ cardNo.substring(cardNo.length() - 4);

				SendSmsUtil.sendSmsDiyCL(user.get("userPhone"), SendSmsUtil.templateld44638, content);
				UserSendMessage message = new UserSendMessage();
				message.setPhone(user.get("userPhone"));// 接收的用户
				message.setMessageCreateTime(new Date());
				message.setMessageContent(content);
				message.setMessageStatus(UserSendMessage.STATUS_SUCCESS);// 发送成功
				// 2
				userSendMessageService.saveUserSendMsg(message);// 添加短息记录
			} catch (Exception e) {
				log.error("sendsms error:{}", e);
			}
//			try{
//				log.info("send notice userId:{}",re.getUserId());
//				GeTuiJson geTuiJson = new GeTuiJson();
//				String clientId = repaymentService.getClientByUserId(re.getUserId());
//				geTuiJson.setClientId(clientId);
//				geTuiJson.setNoticeType(1);
//				geTuiJson.setIsNotification("1");
//				geTuiJson.setChannelType("CHANNEL_NOTICE");
//				geTuiJson.setTitle("到期通知");
//				geTuiJson.setContent("您好，您的" + (re.getRepaymentAmount() / 100) +"元借款今日到期，请确保银行卡资金充足，或至APP进行还款。");
//				geTuiJson.setSummary("您好，您的" + (re.getRepaymentAmount() / 100) +"元借款今日到期，请确保银行卡资金充足，或至APP进行还款。");
//				commonProducer.sendMessage(PropertiesUtil.get("MQ_APPNAME") + "_" +Constant.TOPIC_REPAYID,Constant.TAG_NOTICE,geTuiJson.toJson());
//			}catch (Exception e){
//				log.error("notice app error:{}",e);
//			}


		}
		log.info("repaySmsRemind end total:{} ",repayments.size());
	}

    /**
     * 定时任务代扣
     */
    @Override
    public void withhold() {
    	//获取全局配置参数，AM，PM
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int time = c.get(Calendar.HOUR_OF_DAY);
		Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.WITHHOLD_TIME);
		int am = Integer.parseInt(keys.get("WITHHOLD_AM"));
		int pm = Integer.parseInt(keys.get("WITHHOLD_PM"));
		log.info("withhold定时任务执行,time:" + time + ",AM:" + am + ",PM:" + pm);
		if (time == am || time == pm) {
			log.info("withhold time:{} ",time);
			withhold2();
		}
    }

    private void withhold2() {
        try {
			log.info("withhold start");

            String repaymentTime = DateUtil.format_yyyy_MM_dd(Calendar.getInstance().getTime());

            Map<String, Object> params = new HashMap<>();
            params.put("repaymentTime", repaymentTime);
            params.put("statuses", new Integer[]{STATUS_HKZ});

            List<Repayment> repayments = repaymentService.findTaskRepayment(params);
            log.info("withhold count:{} ",repayments.size());


            List<WithholdThread> list = new ArrayList<>();
            for (Repayment repayment : repayments) {
                WithholdThread task = new WithholdThread(log, repayment.getId(), repaymentTime, DateUtil.yyyy_MM_dd, userService, repaymentService,
                        repaymentDetailService, borrowOrderService, userSendMessageService, jedisCluster);
                list.add(task);
            }
            ThreadPoolShuntUtil.shunt(list);
        } catch (Exception e) {
            log.error("repay withhold error:{}", e);
        }
        log.info("withhold end");
    }

	@Override
	public void reportRepayment(String firstRepaymentTime, boolean showOverdute) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		log.info("reportRepayment start:{}:{} ",firstRepaymentTime ,showOverdute);
		Map<String, Object> params = new HashMap<>();
		params.put("firstRepaymentTime", firstRepaymentTime);
		List<Repayment> repayments = repaymentService.findByRepaymentReport(params);

		// 逾期、正常还款、部分还款
		Long expireAmount = 0L; // 到期金额
		Long expireCount = (long) repayments.size(); // 总数量
		Long overdueAmount = 0L; // 新增逾期金额
		Long overdueCount = 0L; // 逾期数量
		Long overdueCountSeven = 0L; // 逾期7天数量
		Long overdueAmountSeven = 0L; // 逾期7天金额
		Long overdueCountFourteen = 0L; // 逾期14天数量
		Long overdueAmountFourteen = 0L; // 逾期14天金额
		Long countSeven = 0L; // 7天数量
		Long countFourteen = 0L; // 14天数量
		Long amountSeven = 0L; // 7天金额
		Long amountFourteen = 0L; // 14天金额
		Long repayCount = 0L; // 今日还款数量
		Long repayAmount = 0L; // 今日还款金额
		Long oldOverdueCount = 0L; // 今日老用户逾期数量
		Long oldOverdueAmount = 0L; // 今日老用户逾期金额
		Long newOverdueCount = 0L; // 今日新用户逾期数量
		Long newOverdueAmount = 0L; // 今日新用户逾期金额
		Long oldRepayCount = 0L; // 今日老用户还款数量
		Long oldRepayAmount = 0L; // 今日老用户还款金额
		Long newRepayCount = 0L; // 今日新用户还款数量
		Long newRepayAmount = 0L; // 今日新用户还款金额
		Long newExpireAmount = 0L; // 今日新用户到期金额
		Long newExpireCount = 0L; // 今日新用户到期数量
		Long oldExpireAmount = 0L; // 今日老用户到期金额
		Long oldExpireCount = 0L; // 今日老用户到期数量
        //新增字段
        Long repayAllMoney = 0L; //实际还款总金额
        Long repayNewMoney = 0L; //新用户实际还款总额
        Long repayOldMoney = 0L; //老用户实际还款总额

		for (Repayment re : repayments) {
			int moneyAmount = (int) (re.getRepaymentPrincipal() + re.getRepaymentInterest());
			int repaymentedAmount = re.getRepaymentedAmount().intValue();
			int status = re.getStatus();
			String repaymentTime = dateFormat.format(re.getRepaymentTime());
			String firstRepayment = dateFormat.format(re.getFirstRepaymentTime());
			if (status == STATUS_YHK || !repaymentTime.equals(firstRepayment)) {
				// 新增还款金额 还款数量
				repayAmount += moneyAmount;
				//实际还款金额
                repayAllMoney += repaymentedAmount;
				repayCount++;
				// 新旧用户已还数量
				if (User.CUSTOMER_OLD.equals(String.valueOf(re.getCustomerType()))) {
					oldRepayCount++;
					oldRepayAmount += moneyAmount;
					//老用户实际还款金额
                    repayOldMoney += repaymentedAmount;
				} else {
					newRepayCount++;
					newRepayAmount += moneyAmount;
					//新用户实际还款金额
                    repayNewMoney  += repaymentedAmount;

				}

			} else {
				// 新增逾期金额 逾期数量
				overdueAmount += moneyAmount;
				overdueCount++;
				// 新旧用户逾期数量 逾期金额
				if (User.CUSTOMER_OLD.equals(String.valueOf(re.getCustomerType()))) {
					oldOverdueCount++;
					oldOverdueAmount += moneyAmount;
				} else {
					newOverdueCount++;
					newOverdueAmount += moneyAmount;
				}
				// 7天14天逾期数量
				if (re.getLoanTerm() == 7) {
					overdueCountSeven++;
					overdueAmountSeven += moneyAmount;
				} else {
					overdueCountFourteen++;
					overdueAmountFourteen += moneyAmount;
				}
			}
			// 计算总数量
			if (re.getLoanTerm() == 7) {
				countSeven++;
				amountSeven += moneyAmount;
			} else {
				countFourteen++;
				amountFourteen += moneyAmount;
			}
			// 到期金额
			expireAmount += moneyAmount;
			// 新老用户到期金额
			if (User.CUSTOMER_OLD.equals(String.valueOf(re.getCustomerType()))) {
				oldExpireAmount += moneyAmount;
				oldExpireCount++;
			} else {
				newExpireAmount += moneyAmount;
				newExpireCount++;
			}
		}

		List<Map<String, Object>> renewals = reportRepaymentService.findRenewalByRepaymentReport(params);
		expireCount += renewals.size();
		for (Map<String, Object> renewal : renewals) {
			String repaymentTime = dateFormat.format(renewal.get("repaymentTime"));
			String repaymentTimeRe = dateFormat.format(renewal.get("repaymentTimeRe"));
			int moneyAmount = Integer.parseInt(renewal.get("moneyAmount").toString());
			int status = Integer.parseInt(renewal.get("status").toString());
			int loanTerm = Integer.parseInt(renewal.get("loanTerm").toString());
			if (status == STATUS_YHK || !repaymentTime.equals(repaymentTimeRe)) {
				// 新增还款金额 还款数量
				repayAmount += moneyAmount;
				repayCount++;
                //实际还款金额
                repayAllMoney += moneyAmount;

				// 旧用户已还数量
				oldRepayCount++;
				oldRepayAmount += moneyAmount;
				//老用户展期金额
                repayOldMoney += moneyAmount;
			} else {
				// 新增逾期金额 逾期数量
				overdueAmount += moneyAmount;
				overdueCount++;
				// 旧用户逾期数量 逾期金额
				oldOverdueCount++;
				oldOverdueAmount += moneyAmount;
				// 7天14天逾期数量
				if (loanTerm == 7) {
					overdueCountSeven++;
					overdueAmountSeven += moneyAmount;
				} else {
					overdueCountFourteen++;
					overdueAmountFourteen += moneyAmount;
				}
			}
			// 计算总数量
			if (loanTerm == 7) {
				countSeven++;
				amountSeven += moneyAmount;
			} else {
				countFourteen++;
				amountFourteen += moneyAmount;
			}
			// 到期金额
			expireAmount += moneyAmount;
			// 老用户到期金额
			oldExpireAmount += moneyAmount;
			oldExpireCount++;

		}

		ReportRepayment report = new ReportRepayment();
		report.setReportDate(firstRepaymentTime);
		if (expireCount > 0) {
			if (showOverdute) {
				report.setOverdueRate(overdueCount * 10000 / expireCount);
				if (countSeven > 0) {
					report.setOverdueRateSeven(overdueCountSeven * 10000 / countSeven);
				}
				if (countFourteen > 0) {
					report.setOverdueRateFourteen(overdueCountFourteen * 10000 / countFourteen);
				}
				if (oldExpireCount > 0) {
					report.setOverdueRateOld(oldOverdueCount * 10000 / oldExpireCount);
				}
				if (newExpireCount > 0) {
					report.setOverdueRateNew(newOverdueCount * 10000 / newExpireCount);
				}

				report.setOverdueAmount(overdueAmount);
				report.setOverdueRateAmount(overdueAmount * 10000 / expireAmount);
				if (amountSeven > 0) {
					report.setOverdueRateSevenAmount(overdueAmountSeven * 10000 / amountSeven);
				}
				if (amountFourteen > 0) {
					report.setOverdueRateFourteenAmount(overdueAmountFourteen * 10000 / amountFourteen);
				}
				report.setOverdueAmountOld(oldOverdueAmount);
				report.setOverdueAmountNew(newOverdueAmount);
				if (oldExpireAmount > 0) {
					report.setOverdueRateOldAmount(oldOverdueAmount * 10000 / oldExpireAmount);
				}
				if (newExpireAmount > 0) {
					report.setOverdueRateNewAmount(newOverdueAmount * 10000 / newExpireAmount);
				}
			}
			report.setRepayRate(repayCount * 10000 / expireCount);

			if (oldExpireCount > 0) {
				report.setRepayRateOld(oldRepayCount * 10000 / oldExpireCount);
			}
			if (newExpireCount > 0) {
				report.setRepayRateNew(newRepayCount * 10000 / newExpireCount);
			}

			report.setExpireAmount(expireAmount);
			report.setExpireAmountOld(oldExpireAmount);
			report.setExpireAmountNew(newExpireAmount);
			report.setRepayAllMoney(repayAllMoney);
			report.setRepayNewMoney(repayNewMoney);
			report.setRepayOldMoney(repayOldMoney);
			if(expireAmount >0){
                report.setRepayRateAmount(repayAmount * 10000 / expireAmount);
                report.setRepaySjRate(repayAllMoney * 10000 / expireAmount);
            }
			if (oldExpireAmount > 0) {
				report.setRepayRateOldAmount(oldRepayAmount * 10000 / oldExpireAmount);
				report.setRepayOldSjRate(oldRepayAmount * 10000 / oldExpireAmount);
			}
			if (newExpireAmount > 0) {
				report.setRepayRateNewAmount(newRepayAmount * 10000 / newExpireAmount);
				report.setRepayNewSjRate(repayNewMoney * 10000 / newExpireAmount);
			}
		}
		params.clear();
		Integer[] statuses = new Integer[] { STATUS_HKZ, STATUS_BFHK, STATUS_YHZ, STATUS_YYQ, STATUS_YHK, STATUS_YQYHK };
		params.put("statuses", statuses);
		params.put("firstRepaymentTimeEnd", firstRepaymentTime);
		// repayments = repaymentService.findByRepaymentReport(params);

		Long allCount = 0L; // 总借款数量
		Long allAmount = 0L; // 总借款金额
		Long allBorrowCount = 0L; // 当前借款数量
		Long allBorrowAmount = 0L; // 当前借款金额
		Long allRepayCount = 0L; // 当前还款数量
		Long allRepayAmount = 0L; // 当前还款金额
		Long allOverdueCount = 0L; // 当前逾期数量
		Long allOverdueAmount = 0L; // 当前逾期金额
		Long overdueS1Count = 0L; // 当前逾期S1数量
		Long overdueS1Amount = 0L; // 当前逾期S1金额
		Long overdueS2Count = 0L; // 当前逾期S2数量
		Long overdueS2Amount = 0L; // 当前逾期S2金额
		Long overdueS3Count = 0L; // 当前逾期S3数量
		Long overdueS3Amount = 0L; // 当前逾期S3金额
		Long overdueM3Count = 0L; // 当前逾期M3数量
		Long overdueM3Amount = 0L; // 当前逾期M3金额

		// 总借款数量、金额
		Map<String, Object> map = reportRepaymentService.findAllByReport(firstRepaymentTime);
		allCount += (Long) map.get("countAll");
		allAmount += ((BigDecimal) map.get("amountAll")).longValue();
		map = reportRepaymentService.findAllRenewalByReport(firstRepaymentTime);
		allCount += (Long) map.get("countAll");
		allAmount += ((BigDecimal) map.get("amountAll")).longValue();

		// 当前借款数量、金额
		map = reportRepaymentService.findAllBorrowByReport(firstRepaymentTime);
		allBorrowCount += (Long) map.get("countAll");
		allBorrowAmount += ((BigDecimal) map.get("amountAll")).longValue();
		map = reportRepaymentService.findAllRenewalBorrowByReport(firstRepaymentTime);
		allBorrowCount += (Long) map.get("countAll");
		allBorrowAmount += ((BigDecimal) map.get("amountAll")).longValue();
		/*//截至到目前放款总量
         map = reportRepaymentService.findAllByReport(firstRepaymentTime);
         allBorrowCount += (Long) map.get("countAll");
         allBorrowAmount += ((BigDecimal) map.get("amountAll")).longValue();*/

       // 当前还款数量、金额
		map = reportRepaymentService.findAllRepayByReport(firstRepaymentTime);
		allRepayCount += (Long) map.get("countAll");
		allRepayAmount += ((BigDecimal) map.get("amountAll")).longValue();
		map = reportRepaymentService.findAllRenewalRepayByReport(firstRepaymentTime);
		allRepayCount += (Long) map.get("countAll");
		allRepayAmount += ((BigDecimal) map.get("amountAll")).longValue();
       /* //截至到目前 还款数量，金额
        map = reportRepaymentService.findRepayReport(firstRepaymentTime);
        allRepayCount += (Long) map.get("countAll");
        allRepayAmount += ((BigDecimal) map.get("amountAll")).longValue();*/


        // 当前逾期数量、金额
		map = reportRepaymentService.findAllOverdueByReport(firstRepaymentTime);
		allOverdueCount += (Long) map.get("countAll");
		allOverdueAmount += ((BigDecimal) map.get("amountAll")).longValue();

		// S1S2S3数量、金额
		params.clear();
		params.put("reportTime", firstRepaymentTime);
		params.put("reportTimeOneHour", firstRepaymentTime + " 01:00:00");
		List<Map<String, Object>> list = reportRepaymentService.findOverdueRateSByReport(params);

		for (Map<String, Object> temp : list) {
			long lateDay = Long.parseLong(temp.get("lateDay").toString());
			long countAll = (Long) temp.get("countAll");
			long amountAll = ((BigDecimal) temp.get("amountAll")).longValue();
			if (lateDay > 0 && lateDay <= 10) {
				overdueS1Count += countAll;
				overdueS1Amount += amountAll;
			} else if (lateDay > 10 && lateDay <= 30) {
				overdueS2Count += countAll;
				overdueS2Amount += amountAll;
			} else if (lateDay > 30 && lateDay <= 60) {
				overdueS3Count += countAll;
				overdueS3Amount += amountAll;
			} else if (lateDay > 60 && lateDay <= 90) {
				overdueM3Count += countAll;
				overdueM3Amount += amountAll;
			}
		}

		/*
		 * for (Repayment re : repayments) { int status = re.getStatus(); int
		 * moneyAmount = (int) (re.getRepaymentPrincipal() +
		 * re.getRepaymentInterest()); allCount++; allAmount += moneyAmount; //
		 * 还款完成金额、数量 if (STATUS_YHK == status || STATUS_YQYHK == status) {
		 * allRepayCount++; allRepayAmount += moneyAmount; } else { // 当前借款金额、数量
		 * allBorrowCount++; allBorrowAmount += moneyAmount; // 逾期中数量、金额 if
		 * (STATUS_YYQ == status) { allOverdueCount++; allOverdueAmount +=
		 * moneyAmount;
		 *
		 * if (re.getLateDay() > 0 && re.getLateDay() <= 10) { overdueS1Count++;
		 * overdueS1Amount += moneyAmount; } else if (re.getLateDay() > 10 &&
		 * re.getLateDay() <= 30) { overdueS2Count++; overdueS2Amount +=
		 * moneyAmount; } else if (re.getLateDay() > 30 && re.getLateDay() <=
		 * 60) { overdueS3Count++; overdueS3Amount += moneyAmount; } } } }
		 *
		 * renewals =
		 * reportRepaymentService.findRenewalByRepaymentReport(params); for
		 * (Map<String, Object> renewal : renewals) { int moneyAmount =
		 * Integer.parseInt(renewal.get("moneyAmount").toString()); int status =
		 * Integer.parseInt(renewal.get("status").toString()); int lateDay =
		 * Integer.parseInt(renewal.get("lateDay").toString()); allCount++;
		 * allAmount += moneyAmount; // 还款完成金额、数量 if (STATUS_YHK == status ||
		 * STATUS_YQYHK == status) { allRepayCount++; allRepayAmount +=
		 * moneyAmount; } else { // 当前借款金额、数量 allBorrowCount++; allBorrowAmount
		 * += moneyAmount; // 逾期中数量、金额 if (STATUS_YYQ == status) {
		 * allOverdueCount++; allOverdueAmount += moneyAmount;
		 *
		 * if (lateDay > 0 && lateDay <= 10) { overdueS1Count++; overdueS1Amount
		 * += moneyAmount; } else if (lateDay > 10 && lateDay <= 30) {
		 * overdueS2Count++; overdueS2Amount += moneyAmount; } else if (lateDay
		 * > 30 && lateDay <= 60) { overdueS3Count++; overdueS3Amount +=
		 * moneyAmount; } } } }
		 */

		if (allCount > 0) {
			report.setAllBorrowCount(allBorrowCount);
			report.setAllBorrowAmount(allBorrowAmount);
			report.setAllRepayCount(allRepayCount);
			report.setAllRepayAmount(allRepayAmount);
			report.setAllOverdueCount(allOverdueCount);
			report.setAllOverdueAmount(allOverdueAmount);
			report.setOverdueRateS1Count(overdueS1Count * 10000 / allCount);
			report.setOverdueRateS1Amount(overdueS1Amount * 10000 / allAmount);
			report.setOverdueRateS2Count(overdueS2Count * 10000 / allCount);
			report.setOverdueRateS2Amount(overdueS2Amount * 10000 / allAmount);
			report.setOverdueRateS3Count(overdueS3Count * 10000 / allCount);
			report.setOverdueRateS3Amount(overdueS3Amount * 10000 / allAmount);
			report.setOverdueRateM3Count(overdueM3Count * 10000 / allCount);
			report.setOverdueRateM3Amount(overdueM3Amount * 10000 / allAmount);
		}

		Integer id = reportRepaymentService.findIdByDate(report.getReportDate());
		if (null == id) {
			reportRepaymentService.insertSelective(report);
		} else {
			report.setId(id);
			reportRepaymentService.updateByPrimaryKeySelective(report);
		}

		log.info("reportRepayment end:{}",firstRepaymentTime);
	}

	/**
	 * 每日还款、逾期报表 中午12点
	 */
	@Override
	public void reportRepayment12() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String firstRepaymentTime;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, -1);
		firstRepaymentTime = dateFormat.format(now.getTime());
		// 12点后的定时 更新前一日数据 （带逾期信息）
		reportRepayment(firstRepaymentTime, true);
	}

	/**
	 * 每日还款、逾期报表 每两个小时
	 */
	@Override
	public void reportRepaymentE2() {
		// ZCM每两个小时查询明天资产包信息 zjb
//		try {
//			assetsDivisionExt();
			// Date nowDate = new Date();
			// String nextDate = DateUtil.getDateFormat(DateUtil.addDay(nowDate,
			// 1), "yyyy-MM-dd");
			// borrowOrderService.assetpackqueryZCM(nextDate);
//		} catch (Exception e) {
//
//		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String firstRepaymentTime;
		Calendar now = Calendar.getInstance();
		// 12点后 只更新当日 （不带逾期信息）
		if (now.get(Calendar.HOUR_OF_DAY) >= 4) {
			firstRepaymentTime = dateFormat.format(now.getTime());
			reportRepayment(firstRepaymentTime, false);
		}
		// 12点前 更新昨日、当日 （不带逾期信息）
		else {
			firstRepaymentTime = dateFormat.format(now.getTime());
			reportRepayment(firstRepaymentTime, false);

			now.add(Calendar.DAY_OF_YEAR, -1);
			firstRepaymentTime = dateFormat.format(now.getTime());
			reportRepayment(firstRepaymentTime, false);
		}

	}


	@Override
	public void updateLoanTermForBatch() {
		Date nowDate = new Date();
		int nowHour = nowDate.getHours();
		int monutes = nowDate.getMinutes();
		// 避免交替日打款错误，23点55到第二天0点05分期间，不打款
		if (nowHour == 23 ) {
			if(monutes > 55){
				return;
			}
		}
		if (nowHour == 0 ) {
			if(monutes < 5){
				return;
			}
		}
		HashMap<String, Object> paramsM = new HashMap<String, Object>();
		paramsM.put("borrowStatus", BorrowOrder.STATUS_FKZ);//订单状态：放款中
		paramsM.put("payStatus", BorrowOrder.SUB_PAY_CSZT);//付款状态：初始状态
		paramsM.put("queryType", "subPay");//查询类型

		log.info("begin   updateLoanTermNew");

		try {
			int loanTerms[] = BorrowOrder.LOAN_TREMS;//借款期限[7、14]
			try {

				for (int loanTerm : loanTerms) {
					paramsM.put("loanTerm", loanTerm);//借款期限
					paramsM.put("querylimit", 10);//每次查询28条数据
					List<BorrowOrder> bos = borrowOrderService.findAll(paramsM);
					if (bos != null && bos.size() > 0) {

						log.info("begin   Loan:{}" ,OutOrders.act_NTQRYEBP_A);

						borrowOrderService.requestPaysForBatch(bos);

						log.info("end   Loan:{}",OutOrders.act_NTQRYEBP_A);
					}
				}
			} catch (Exception e) {
				log.error("error   Loan :{}error:{}",OutOrders.act_NTQRYEBP_A,e);
			}

		} catch (Exception e) {
			log.error("updateLoanTermNew error:{}", e);
		}
		log.info("end   updateLoanTermNew");

	}
	@Override
	public void updateLoanTermForSimgle() {
		Date nowDate = new Date();
		int nowHour = nowDate.getHours();
		int monutes = nowDate.getMinutes();
		// 避免交替日打款错误，23点55到第二天0点05分期间，不打款
		if (nowHour == 23 ) {
			if(monutes > 55){
				return;
			}
		}
		if (nowHour == 0 ) {
			if(monutes < 5){
				return;
			}
		}
		HashMap<String, Object> paramsM = new HashMap<>();
		//订单状态：放款中
		paramsM.put("borrowStatus", BorrowOrder.STATUS_FKZ);
		//付款状态：初始状态
		paramsM.put("payStatus", BorrowOrder.SUB_PAY_CSZT);
		//查询类型
		paramsM.put("queryType", "subPay");

		log.info("begin updateLoanTermForSimgle");
		//借款期限[7、14]
//		int [] loanTerms = ;
		try {
			final String signKey = "621c76126e4940e8d7de8b5cce65bf7c";
			//借款期限
			//paramsM.put("loanTerm", 7);
			//每次查询50条数据
			paramsM.put("querylimit", 50);
			List<BorrowOrder> bos = borrowOrderService.findOrderIdAndUserIdList(paramsM);
			if (bos != null && bos.size() > 0) {
				log.info("begin updateLoanTermForSimgle size=:{}",bos.size());
				for (BorrowOrder order : bos){
					log.info("begin start order_id=:{}",order.getId());
					String uuid = IdGen.uuid();
					String sign = MD5coding.MD5(AESUtil.encrypt(order.getUserId().toString()+order.getId().toString()+uuid,signKey));
					log.info("userId=" + order.getUserId() + " borrowId=" + order.getId() + " uuid=" + uuid + " sign=" + sign);
					log.info("order=" + JSON.toJSONString(order));

					String requestUrl = PropertiesUtil.get("APP_HOST_API") + "/" + StringDateUtils.getDrawWithChannel() + "/withdraw/" + order.getUserId().toString() + "/" + order.getId().toString() + "/" + uuid + "/" + sign;
					log.info("requestUrl = " + requestUrl);
					//发送代付请求
					try {
						String resultStr = HttpUtil.doGet(requestUrl,"UTF-8",null);
						log.info("begin end order_id=" + order.getId() + " result=" + resultStr);
					} catch (IOException e) {
						log.info("请求代付接口异常，记录异常信息：order_id={}, error={}", order.getId() ,e.getMessage());
					}
//						borrowOrderService.autoFangkuan(order);
//						borrowOrderService.requestPaysForSimgle(order);
				}
			}
//			for (int loanTerm : BorrowOrder.LOAN_TREMS) {
//
//				log.info("end   updateLoanTermForSimgle size:{}" , bos.size());
//			}
		} catch (Exception e) {
			log.error("error   updateLoanTermForSimgle :{}",e);
		}
		log.info("end   updateLoanTermForSimgle");
	}

	@Override
	public void channelReport() {

		try {

			Calendar cal = Calendar.getInstance();
			Integer checkDate = 0;
			String time = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
			checkDate = Integer.valueOf(time.substring(11, 13));
			log.info("thirdChannelReport start time--->:{}" ,time);
			if (checkDate > 1 && checkDate < 6) {

				final String nowDate = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");

				try {

					ThreadPool4.getInstance().run(() -> thirdChannelReportService.saveThirdChannelReport(nowDate));
				} catch (Exception e) {
					log.error("thirdChannelReport  thread error:{}", e);
				}


				// if (checkDate == 2) {
				// logger.error("liangdianle  begin  begin  fuwufeu Thread");
				// ThreadPool3.getInstance().run(new Runnable() {
				// @Override
				// public void run() {
				// updateThreeLoanTerm();
				// }
				// });
				//
				// }

			}
			log.info("loanReport end");
		} catch (Exception e) {
			log.error("thirdChannelReportTaskJob error:{}", e);
		}

		log.info("loanReport start");
		try {
			    final String nowTime = null;

				ThreadPool4.getInstance().run(() -> loanReportService.saveLoanReport(nowTime));


		} catch (Exception e) {
			log.error("loanReportTaskJob error:{}", e);
		}
		log.info("loanReport end");
		log.info("thirdChannelReport start");
		log.info("channelReport start");
		try {
			final String nowTime = null;
			ThreadPool4.getInstance().run(() -> channelReportService.saveChannelReport(nowTime));

		} catch (Exception e) {
			log.error("channelReportTaskJob error:{}", e);
		}
		log.info("channelReport end");
}

	@Override
	public void thirdReport() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String nowTime = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			log.info("thirdChannelReport pushreport start:{}",nowTime);
			thirdChannelReportService.pushUserReport(nowTime);
			log.info("thirdChannelReport pushreport end :{}",nowTime);
		} catch (Exception e) {
			log.error("thirdChannelReport error:{}", e);
		}

		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String nowTime = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			log.info("saveLoanMoneyReport start:{}" ,nowTime);
			loanMoneyReportService.saveLoanMoneyReport(nowTime);
			log.info("saveLoanMoneyReport end:{}",nowTime);
		} catch (Exception e) {
			log.error("saveLoanMoneyReport error:{}", e);
		}
	}

	@Override
	public void insertReport() {
		log.info("insertReport is running");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, -1);
		backStatisticService.updateReport(now, now);

		try {
			log.info("thirdChannelReport pushreport start ");
			String nowTime = DateUtil.getDateFormat(now.getTime(), "yyyy-MM-dd");
			thirdChannelReportService.pushUserReport(nowTime);
			log.info("thirdChannelReport pushreport end ");
		} catch (Exception e) {
			log.error("thirdChannelReport error:{}", e);
		}
	}


	@Override
	public void restartAlarm() {
		String keyJsTgv = "keyJsTgv";
		String keyJsTgvalarmFlag = "keyJsTgvFlag";

		String keyloaning = "keyloaning";
		String keyalarmFlag = "keyalarmFlag";

		String jsCount = "keyDjsCount";
		String keyDjsalarmFlag = "keyDjsCountFlag";

		jedisCluster.set(keyJsTgv, "0");
		jedisCluster.set(keyloaning, "0");
		jedisCluster.set(jsCount, "0");

		jedisCluster.set(keyJsTgvalarmFlag, "true");
		jedisCluster.set(keyalarmFlag, "true");
		jedisCluster.set(keyDjsalarmFlag, "true");

	}

	@Override
	public void createBorrowStaticsReport() {
		try {
			borrowStatisticsReportService.createborrowStaticReportDateByDate(null);
		} catch (Exception e) {
			log.error("createBorrowStaticsReport error:{}", e);
		}

	}

	@Override
	public void updateThreeLoanTerm() {
		log.info("begin updateThreeLoanTerm");

		LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams(BackConfigParams.FEE_ACCOUNT);
		String jujiusongIsopen = mapFee.get("jujiusong_isopen");
		if (PropertiesUtil.get("CMB_ISRUN").equals("YES") || jujiusongIsopen.equals("1")) {
			threeLoanOrderService.updateThreeLoanTerm(null);
		}

		log.info("end updateThreeLoanTerm");
	}

	@Override
	public void acitveClickStatistics() {
		try {
			log.info("处理每天的活动统计数据开始.....");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date nowdate = new Date();
			calendar.setTime(nowdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			String date = sdf.format(calendar.getTime());
			String cachekey = "youGetMoneyMeRepayActivePageClickNum_" + date;
			List<String> clickdevices = jedisCluster.lrange(cachekey, 0, -1);
			int totalclicknum = 0;
			if (clickdevices == null || clickdevices.size() == 0) {
				log.info("key:{}不存在活动统计数据...:{}", date);
			} else {
				totalclicknum = clickdevices.size();
			}
			log.info("{}查询出活动点击统计数据{}条....", date, totalclicknum);
			ActiveStatisticsInfo activeStatisticsInfo = new ActiveStatisticsInfo();
			activeStatisticsInfo.setActiveType("1");// 你借钱我来还活动
			long applycount = borrowStatisticsReportDao.selectTotalApplyforDay(sdf.format(calendar.getTime()));
			if (applycount != 0) {
				activeStatisticsInfo.setApplyNum(applycount);
			} else {
				activeStatisticsInfo.setApplyNum(0L);
			}
			long successcount = borrowStatisticsReportDao.selectTotalSuceesstforDay(sdf.format(calendar.getTime()));
			if (successcount != 0) {
				activeStatisticsInfo.setBorrowNum(successcount);
			} else {
				activeStatisticsInfo.setBorrowNum(0L);
			}
			activeStatisticsInfo.setClickNum(totalclicknum);
			activeStatisticsInfo.setCreateDate(nowdate);
			activeStatisticsInfo.setUpdateDate(nowdate);
			activeStatisticsInfo.setStatisticsDate(sdf.format(calendar.getTime()));
			activeStatisticsInfo.setIsDel("0");
			borrowStatisticsReportService.activeInsertBySelective(activeStatisticsInfo);
			log.info("处理每天的活动统计数据完成.....");
		} catch (Exception e) {
			log.error("新增活动统计数据异常....:{}", e);
		}

	}

  /* @PostConstruct
   public void assignOrderOnly(){
	   log.info("派单开始仅此一次");
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("onlyAssign",1);
			List<Repayment> repaymentList = repaymentService.findByRepaymentReport(params);
			//查询客服
			HashMap<String, Object> paramsRole = new HashMap<>();
			paramsRole.put("roleName", "普通客服");
			paramsRole.put("status",1);
			List<BackUser> backUserList = backUserService.findKeFuList(paramsRole);
			//然后将自动分单信息插入到 asset_borrow_assign
			Integer len=backUserList.size();
			int index=0;
			if(repaymentList!=null && !repaymentList.isEmpty()){
				for (Repayment repayment:repaymentList) {
					if(index>=len){
						index=0;
					}
					BackUser backUser =backUserList.get(index);
					saveAssetBorrowAssign(backUser,repayment,0);
					index++;
				}
			}
			log.info("派单结束");
		}catch(Exception e){
		log.error("派单异常结束");
	}
   }*/
	/**
	 * 自动分派订单给所有客服
	 */
	@Override
	public void autoAssignOrder(){
		log.info("start autoAssignOrder job");
		try{
			//先查询repayment
			String expectedRepaymentTime = null; //预期还款时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			expectedRepaymentTime = dateFormat.format(now.getTime());
			//预期还款时间
			//status 30:已还款
			Map<String, Object> params = new HashMap<>();
			params.put("expectedRepaymentTime", expectedRepaymentTime);
			//params.put("noPayStatus",1);
            //只分配当日到期未还订单 以及部分还款订单
            //params.put("status",21);
			params.put("waitStatus",1);
			List<Repayment> repaymentList = repaymentService.findByRepaymentReport(params);
			//List<Repayment> resultList = new ArrayList();
			//resultList.addAll(repaymentList);
			List<Repayment> resultList = repaymentList.stream().filter(repayment -> repaymentService.findAssignExits(repayment.getId()).intValue() <= 0).collect(Collectors.toList());
			//查询当日到期订单是否已经存在分配过的（之前分配 后展期）
			/*for(Repayment repayment :repaymentList){
				Integer id = repaymentService.findAssignExits(repayment.getId());
				if (id >0){
					resultList.remove(repayment);
				}
			}*/
			List<BackUser> backUserList;
			//如果有排班，则按照排班进行派单
			CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(expectedRepaymentTime);
			if(customerClassArrange!=null){
				String[] morCustomerIds = org.springframework.util.StringUtils.tokenizeToStringArray(customerClassArrange.getClassMorCustomers(),",");
				backUserList = backUserService.selectBackUserByIds(morCustomerIds);
				if(resultList!=null && !resultList.isEmpty()){
					int index=0;
					Map<Integer,Object> tempOrderMap = new HashMap<>();
					for (Repayment repayment:resultList) {
						Integer len=backUserList.size();
						if(index>=len){
							index=0;
						}
						BackUser backUser =backUserList.get(index);
						saveAssetBorrowAssign(backUser,repayment,0);
						Integer userTempOrderSize = tempOrderMap.get(backUser.getId())==null?0:Integer.valueOf(tempOrderMap.get(backUser.getId()).toString());
						userTempOrderSize++;
						//若此客服分配单量超过限制则不再分配
						if(backUser.getOrderLimitFlag()==1 && userTempOrderSize.equals(backUser.getOrderLimitMor())){
							tempOrderMap.remove(backUser.getId());
							backUserList.remove(backUser);
						}else{
							tempOrderMap.put(backUser.getId(),userTempOrderSize);
						}
						index++;
					}
				}

			}else{
				//查询客服
				HashMap<String, Object> paramsRole = new HashMap<>();
				paramsRole.put("roleName", "普通客服");
				paramsRole.put("status",1);
				backUserList = backUserService.findKeFuList(paramsRole);
				//然后将自动分单信息插入到 asset_borrow_assign
				Integer len=backUserList.size();
				int index=0;
				if(resultList!=null && !resultList.isEmpty()){
					for (Repayment repayment:resultList) {
						if(index>=len){
							index=0;
						}
						BackUser backUser =backUserList.get(index);
						saveAssetBorrowAssign(backUser,repayment,0);
						index++;
					}
				}
			}
		}catch (Exception e){
			log.error("autoAssignOrder error:{}",e);
		}

		log.info("end autoAssignOrder job");

	}

	/**
	 * 自动分派订单给所有客服(晚班)
	 *//*
	@Override
	public void autoAssignOrderForNig(){
		log.info("start autoAssignOrderForNig job");
		try{
			//先查询repayment
			String expectedRepaymentTime = null; //预期还款时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			expectedRepaymentTime = dateFormat.format(now.getTime());
			//预期还款时间
			//status 30:已还款
			Map<String, Object> params = new HashMap<>();
			params.put("expectedRepaymentTime", expectedRepaymentTime);
			//params.put("noPayStatus",1);
            //只分配当日未还订单
            params.put("status",21);
			List<Repayment> repaymentList = repaymentService.findByRepaymentReport(params);
			List<BackUser> backUserList;
			//如果有排班，则按照排班进行派单
			CustomerClassArrange customerClassArrange = onlineCustomService.getCustomerClassByDate(expectedRepaymentTime);
			if(customerClassArrange!=null){
				String[] nigCustomerIds = org.springframework.util.StringUtils.tokenizeToStringArray(customerClassArrange.getClassNigCustomers(),",");
				backUserList = backUserService.selectBackUserByIds(nigCustomerIds);
				if(repaymentList!=null && !repaymentList.isEmpty()){
					int index=0;
					Map<Integer,Object> tempOrderMap = new HashMap<>();
					for (Repayment repayment:repaymentList) {
						Integer len=backUserList.size();
						if(index>=len){
							index=0;
						}
						BackUser backUser =backUserList.get(index);
						saveAssetBorrowAssign(backUser,repayment,1);
						Integer userTempOrderSize = tempOrderMap.get(backUser.getId())==null?0:Integer.valueOf(tempOrderMap.get(backUser.getId()).toString());
						userTempOrderSize++;
						//若此客服分配单量超过限制则不再分配
						if(backUser.getOrderLimitFlag()==1 && userTempOrderSize.equals(backUser.getOrderLimitNig())){
							tempOrderMap.remove(backUser.getId());
							backUserList.remove(backUser);
						}else{
							tempOrderMap.put(backUser.getId(),userTempOrderSize);
						}
						index++;
					}
				}
			}else{
				//查询客服
				HashMap<String, Object> paramsRole = new HashMap<>();
				paramsRole.put("roleName", "普通客服");
				paramsRole.put("status",1);
				backUserList = backUserService.findKeFuList(paramsRole);
				//然后将自动分单信息插入到 asset_borrow_assign
				Integer len=backUserList.size();
				int index=0;
				if(repaymentList!=null && !repaymentList.isEmpty()){
					for (Repayment repayment:repaymentList) {
						if(index>=len){
							index=0;
						}
						BackUser backUser =backUserList.get(index);
						saveAssetBorrowAssign(backUser,repayment,1);
						index++;
					}
				}
			}
		}catch (Exception e){
			log.error("autoAssignOrderForNig error:{}",e);
		}
		log.info("end autoAssignOrderForNig job");

	}*/


	private void saveAssetBorrowAssign(BackUser backUser,Repayment repayment,Integer assignType){
		AssetBorrowAssign assetBorrowAssign = new AssetBorrowAssign();
		assetBorrowAssign.setBorrowOrderId(repayment.getAssetOrderId());
		assetBorrowAssign.setJobName(backUser.getUserName());
		assetBorrowAssign.setJobId(backUser.getId());
		assetBorrowAssign.setCreateTime(new Date());
		assetBorrowAssign.setUpdateTime(new Date());
		assetBorrowAssign.setAssignType(assignType);
		assetBorrowAssign.setDelFlag(0);
		log.info("save assign :{}",JSON.toJSONString(assetBorrowAssign));
		repaymentService.insertAssetBorrowAssign(assetBorrowAssign);
	}

	@Override
	public void insertAssignStatisticForArtificialSend(){
		log.info("客服统计人工分派数据生成");
		//获取前一天的日期
		Calendar c   =   Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(c.getTime());
		Map<String,Object> taskParams = new HashMap<>();
		taskParams.put("createDate",date);
		try{
			List<Map<String,Object>> assign =  repaymentService.selectAssignStatisticsForArtificialSend(taskParams);
			if (assign!=null && !assign.isEmpty()){
				repaymentDetailService.insertStatisticsDetail(assign);
			}
		}catch (Exception e){
			log.error("insertAssignStatisticForArtificialSend error:{}",e);
		}
		log.info("客服统计人工分派数据生成结束");
	}
	@Override
	public void insertAssignStatisticForSystemSend(){
		log.info("客服统计系统分派数据生成");
		Calendar c   =   Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(c.getTime());
		Map<String,Object> taskParams = new HashMap<>();
		taskParams.put("createDate",date);
		try {
			List<Map<String, Object>> assign = repaymentService.selectAssignStatisticsForSystemSend(taskParams);
			if(assign!=null && !assign.isEmpty()){
				repaymentDetailService.insertStatisticsDetail(assign);
			}
		}catch (Exception e){
			log.error("insertAssignStatisticForSystemSend error:{}",e);
		}
		log.info("客服统计系统分派数据生成结束");
	}

	/**
	 * 数据分析统计
	 */
	@Override
	public void dataAnalyze(){
		log.info("数据分析统计开始");
		//进行每天统计
		dataStatisticsService.insertDataStatisticsByFlag(false);
	}

	/**
	 * 应用市场自然流量分析初始化
	 * @Author ：tgy
	 */
	@Override
	public void appMarketInitialize() throws Exception {
		log.info("应用市场自然流量初始化开始");
		appMarketStaticsService.inserAppMarketTypeEveryDay();
		log.info("应用市场自然流量初始化结束");
	}
	/**
	 * 应用市场自然流量分析
	 * @Author ：tgy
	 */
	@Override
	public void appMarketAnalyze() throws Exception{
		log.info("应用市场自然流量统计开始");
		try {
			appMarketStaticsService.insertToAppmarketAnalyze();
		}catch (Exception e) {
			if (null != jedisCluster.get("insertToAppmarketAnalyze")) {
				jedisCluster.del("insertToAppmarketAnalyze");
			}
			log.error("insertToAppmarketAnalyze happened error,so exit!:{}", e);
		}
		log.info("应用市场自然流量统计结束");
	}

	@Override
	public void customerLabelCount() throws Exception {
		String countDate = DateUtil.getDateFormat(new Date(), DateUtil.YMD);
		labelCountService.labelCount(countDate);
	}

	@Override
	public void afterLoanCensusNew() throws Exception {
		afterLoanCensus(null);
	}

	//贷后统计 每天1小时统计一次
    @Override
    public void afterLoanCensus(String expectedRepaymentTime) throws Exception{
		log.info("贷后统计1小时一次 开始");
        //预期还款时间
        if (expectedRepaymentTime == null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Date nowDate = new Date();
            int nowHour = nowDate.getHours();
            if (nowHour == 0 ) {
                calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
               expectedRepaymentTime = dateFormat.format(calendar.getTime());
            }else{
                expectedRepaymentTime = dateFormat.format(calendar.getTime());
            }
        }
		if(backLoanCensusService.afterLoanCensus(expectedRepaymentTime))
			log.info("贷后统计1小时一次 统计成功结束");
		else
			log.info("贷后统计1小时一次 统计失败结束");

	}

	@Override
	public void BackLoanOveCensus() throws Exception{
		log.info("贷后逾期统计每天一次 开始");
		if(backLoanCensusService.BackLoanOveCensus()){
			log.info("贷后逾期统计每天一次 统计成功结束");
		}else{
			log.info("贷后逾期统计每天一次 统计失败结束");
		}
	}

    /**
     * 渠道每日逾期统计 每天12点十分跑一次
     * @throws Exception
     */
    @Override
	public void channelOveCensusResult() throws Exception{
		String  repayTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
		Date nowDate = new Date();
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		repayTime = dateFormat.format(calendar.getTime());
		if(channelOveCensusService.channelOveCensusResult(repayTime))
			log.info("当日渠道统计结束,成功");
		else
			log.info("当日渠道统计结束,失败");
    }

	/**\
	 * 客服每日派单及回款统计
	 */
	@Override
    public void kefuCensus(){
		log.info("客服每日派单及回款统计");
		String  createTime = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date nowDate = new Date();
		int nowHour = nowDate.getHours();
		if (nowHour == 0 ) {
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			createTime = dateFormat.format(calendar.getTime());
		}else{
			createTime = dateFormat.format(calendar.getTime());
		}
		if(borrowOrderService.pandanCount(createTime))
			log.info("客服当日统计成功");
		else
			log.info("客服当日统计结束");
	}
}
