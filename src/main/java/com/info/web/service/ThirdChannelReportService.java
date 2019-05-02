package com.info.web.service;

import static com.info.web.pojo.BorrowOrder.STATUS_BFHK;
import static com.info.web.pojo.BorrowOrder.STATUS_HKZ;
import static com.info.web.pojo.BorrowOrder.STATUS_YHZ;
import static com.info.web.pojo.BorrowOrder.STATUS_YYQ;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IThirdChannelReportDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.ChannelReport;
import com.info.web.pojo.ThirdChannelReport;
import com.info.web.util.DateUtil;
import com.info.web.util.JSONUtil;
import com.info.web.util.PageConfig;
import com.info.web.util.UserPushUntil;

@Slf4j
@Service
public class ThirdChannelReportService implements IThirdChannelReportService {

	@Autowired
	private IThirdChannelReportDao thirdChannelReportDao;
	@Autowired
	private IPaginationDao paginationDao;


	@Override
	public ChannelReport getChannelReportById(Integer id) {
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ThirdChannelReport> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ThirdChannelReport");
		PageConfig<ThirdChannelReport> pageConfig = new PageConfig<ThirdChannelReport>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,
				"web");
		return pageConfig;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ThirdChannelReport> findPrPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ThirdChannelReport");
		PageConfig<ThirdChannelReport> pageConfig = new PageConfig<ThirdChannelReport>();
		pageConfig = paginationDao.findPage("findPrAll", "findPrAllCount",
				params, "web");
		return pageConfig;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ThirdChannelReport> findSumPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "ChannelReport");
		PageConfig<ThirdChannelReport> pageConfig = new PageConfig<ThirdChannelReport>();
		pageConfig = paginationDao.findPage("findSumAll", "findSumAllCount",
				params, "web");
		return pageConfig;
	}

	@Override
	public Integer findPrParamsCount(Map<String, Object> params) {
		return thirdChannelReportDao.findPrAllCount(params);
	}
	
	@Override
	public Integer findParamsCount(Map<String, Object> params) {
		return thirdChannelReportDao.findAllCount(params);
	}
	
	@Override
	public List<ThirdChannelReport> getAllPrChannelReports(Map<String, Object> params) {
		return thirdChannelReportDao.findPrAll(params);
	}

	@Override
	public List<ThirdChannelReport> getAllChannelReports(Map<String, Object> params) {
		return null;
	}
	
	@Override
	public boolean pushUserReport(String nowTime) {
		log.info("pushUserReport  time:{}",nowTime);
		boolean bool = false;
		try {
			Map<String, Object> param = new HashMap<>();
			/* 设置查询时间 */
			Calendar cal = Calendar.getInstance();
			/* 判断当前时间是否在凌晨两点之前，是，重新统计前一天,用于补漏定时任务未统计到的数据*/
			Integer checkDate = 0;
			String time = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
			checkDate=Integer.valueOf(time.substring(11, 13));
			if(checkDate>0&&checkDate<3){
				cal.add(Calendar.DATE, -1);
			}
			String nowDate = DateUtil
					.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			if (nowTime != null) {
				nowDate = nowTime;
			}
			param.put("startDate", nowDate);
			
			List<ThirdChannelReport> reportList = thirdChannelReportDao.findAllListByTime(param);

			Map<String, String> mapPush = SysCacheUtils.getConfigParams("PUSH_USER");
			String pushUrl = mapPush.get("push_user_url");
			String urlAdd = UserPushUntil.REPORT_URL;
			log.info("url:{},{} ",pushUrl,urlAdd);
			log.info("pushUserReport  reportSize:{} ",reportList.size());
			if(reportList.size()>0){
				for(ThirdChannelReport tcr :reportList){
					if(tcr.getThirdId()!=null){
						Map<String,String> oneMap = new HashMap<>();
						oneMap.put("pushId", tcr.getThirdId().toString());
						oneMap.put("reportDate", tcr.getReportDate()==null?nowDate:DateUtil
								.getDateFormat(tcr.getReportDate(), "yyyy-MM-dd")); 
						oneMap.put("registerCount",tcr.getRegisterCount().toString());
						oneMap.put("realnameCount",tcr.getAttestationRealnameCount().toString());
						oneMap.put("contactCount",tcr.getContactCount().toString());
						oneMap.put("bankCount",tcr.getAttestationBankCount().toString());
						oneMap.put("jxlCount",tcr.getJxlCount().toString());
						oneMap.put("zhimaCount",tcr.getZhimaCount().toString());
						oneMap.put("companyCount",tcr.getCompanyCount().toString());
						oneMap.put("alipayCount",tcr.getAlipayCount().toString());
						oneMap.put("borrowApplyCount",tcr.getBorrowApplyCount().toString());
						oneMap.put("borrowSucCount",tcr.getBorrowSucCount().toString());
						oneMap.put("blackUserCount",tcr.getBlackUserCount().toString());
						oneMap.put("borrowRate",tcr.getBorrowRate().toString());
						oneMap.put("passRate",tcr.getPassRate().toString());
						oneMap.put("intoMoney",tcr.getIntoMoney().toString());
						oneMap.put("overdueMoneySum",tcr.getOverdueMoneySum().toString());
						oneMap.put("overdueMoney",tcr.getOverdueMoney().toString());
						oneMap.put("baddebtMoneySum",tcr.getBaddebtMoneySum().toString());
						oneMap.put("baddebtMoney",tcr.getBaddebtMoney().toString());
						String url = pushUrl+ urlAdd;
//						logger.error("addPushUserApprove URL error :"+"url！"+url);
						try {
							String result=WebClient.getInstance().doPost(url, oneMap);
							if (result == null) {
								log.info("addPushUserApprove URL error:{}连接超时！",url);
								break;
							}
							bool = true;
						} catch (Exception e) {
//							serviceResult.setMsg("请求地址错误导致返回结果解析错误");
							log.error("addPushUserReport error :{}", e);
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("addPushUserReport error:{} ", e);
		}
		return bool;
	}
	//

	@Override
	public boolean saveThirdChannelReport(String nowTime) {
		Map<String, Object> param = new HashMap<>();
//		ChannelReport channelReport = new ChannelReport();
		ThirdChannelReport thirdChannelReport = new ThirdChannelReport();
		try {
			/* 设置查询时间 */

			Calendar cal = Calendar.getInstance();
			/* 判断当前时间是否在凌晨两点之前，是，重新统计前一天,用于补漏定时任务未统计到的数据*/
			Integer checkDate = 0;
			 
			String time = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
			checkDate=Integer.valueOf(time.substring(11, 13));
			 
			if(checkDate>0&&checkDate<3){
				cal.add(Calendar.DATE, -1);
			}
			String nowDate = DateUtil
					.getDateFormat(cal.getTime(), "yyyy-MM-dd");

			if (nowTime != null) {
				nowDate = nowTime;
			}

			param.put("startDate", nowDate);
			//param.put("endDate", nowDate + " 23:59:59");
			param.put("nowDate", cal.getTime());
			List<ThirdChannelReport> thirdChannelReportList = thirdChannelReportDao.findThirdId();

			for (ThirdChannelReport tid : thirdChannelReportList) {
				Integer thirdId = tid.getThirdId();
				if (thirdId != null) {
					param.put("thirdId", thirdId);
					// 注册量
					Integer registerCount = thirdChannelReportDao
							.findRegisterCount(param);
					thirdChannelReport.setRegisterCount(registerCount);
					// 实名认证
					Integer realNameCount = thirdChannelReportDao
							.findRealNameCount(param);
					thirdChannelReport.setAttestationRealnameCount(realNameCount);
					// 运营商认证
					Integer jxlCount = thirdChannelReportDao.findTdCount(param);
					thirdChannelReport.setJxlCount(jxlCount);

					// 芝麻认证
					Integer zhimaCount = thirdChannelReportDao.findZMCount(param);
					thirdChannelReport.setZhimaCount(zhimaCount);
					// 紧急联系人
					Integer contactCount = thirdChannelReportDao
							.findContactCount(param);
					thirdChannelReport.setContactCount(contactCount);
					// 工作信息
					Integer companyCount = thirdChannelReportDao
							.findCompanyCount(param);
					thirdChannelReport.setCompanyCount(companyCount);

					// 银行卡绑定
					Integer bankCount = thirdChannelReportDao.findBankCount(param);
					thirdChannelReport.setAttestationBankCount(bankCount);
					// 支付宝
					Integer alipayCount = thirdChannelReportDao
							.findAlipayCount(param);
					thirdChannelReport.setAlipayCount(alipayCount);

					// 授信失败人数
					Integer approveErrorCount = thirdChannelReportDao
							.findApproveErrorCount(param);
					thirdChannelReport.setApproveErrorCount(approveErrorCount);

					// 申请借款
					Integer borrowApplyCount = thirdChannelReportDao
							.findBorrowApplyCount(param);
					thirdChannelReport.setBorrowApplyCount(borrowApplyCount);

					// 申请借款成功(21,23,30,34,-11,-20)
					Integer[] inStatus = new Integer[] { STATUS_HKZ,
							STATUS_BFHK, BorrowOrder.STATUS_YHK,
							BorrowOrder.STATUS_YQYHK, STATUS_YYQ, STATUS_YHZ };
					param.put("inStatus", inStatus);
					Integer borrowSucCount = thirdChannelReportDao
							.findBorrowSucCount(param);
					thirdChannelReport.setBorrowSucCount(borrowSucCount);

					// 计算百分比
					BigDecimal a = new BigDecimal(borrowApplyCount);
					BigDecimal b = new BigDecimal(borrowSucCount);
					BigDecimal c = BigDecimal.ZERO;
					if (borrowApplyCount > 0 && borrowSucCount > 0) {
						c = b.multiply(new BigDecimal(100)).divide(a, 2,
								BigDecimal.ROUND_HALF_UP);
						
						 
					}
					thirdChannelReport.setPassRate(c);
					//统计借款率
					BigDecimal d = new BigDecimal(registerCount);
					BigDecimal e = BigDecimal.ZERO;
					if (borrowApplyCount > 0 && registerCount > 0) {
						e = a.multiply(new BigDecimal(100)).divide(d, 2,
								BigDecimal.ROUND_HALF_UP);
					}
					thirdChannelReport.setBorrowRate(e);
					// 放款金额
					Integer intoMoney = thirdChannelReportDao.findIntoMoney(param);
					thirdChannelReport.setIntoMoney(new BigDecimal(intoMoney));

					// 统计黑名单
					Integer blackUserCount = thirdChannelReportDao
							.findBlackUserCount(param);
					thirdChannelReport.setBlackUserCount(blackUserCount);

					// 统计每天逾期人数
					Integer lateDayCount = thirdChannelReportDao
							.findlateDayCount(param);
					thirdChannelReport.setLateDayCount(lateDayCount);

					// 统计每天逾期人数总数
					// Integer aalateDayCount =
					// channelReportDao.findlateDayCount(param);
					// channelReport.setLateDayCount(lateDayCount);
					
					//统计每天逾期金额
					Integer overdueMoney =thirdChannelReportDao.findoverdueMoney(param);
					if(overdueMoney==null){
						overdueMoney=0;
					}
					thirdChannelReport.setOverdueMoney(new BigDecimal(overdueMoney));
					if(thirdChannelReport.getOverdueMoney().compareTo(new BigDecimal(0))==0){
						thirdChannelReport.setOverdueMoney(new BigDecimal(0.00));
					}
					
					Integer overdueMoneySum =thirdChannelReportDao.findoverdueMoneySum(param);
					if(overdueMoneySum==null){
						overdueMoneySum=0;
					}
					thirdChannelReport.setOverdueMoneySum(new BigDecimal(overdueMoneySum));
					if(thirdChannelReport.getOverdueMoneySum().compareTo(new BigDecimal(0))==0){
						thirdChannelReport.setOverdueMoneySum(new BigDecimal(0.00));
					}
					
					Integer baddebtMoney = thirdChannelReportDao.findbaddebtMoney(param);
					if(baddebtMoney==null){
						baddebtMoney=0;
					}
					thirdChannelReport.setBaddebtMoney(new BigDecimal(baddebtMoney));
					if(thirdChannelReport.getBaddebtMoney().compareTo(new BigDecimal(0))==0){
						thirdChannelReport.setBaddebtMoney(new BigDecimal(0.00));
					}
					
					Integer baddebtMoneySum = thirdChannelReportDao.findbaddebtMoneySum(param);
					if(baddebtMoneySum==null){
						baddebtMoneySum=0;
					}
					thirdChannelReport.setBaddebtMoneySum(new BigDecimal(baddebtMoneySum));
					if(thirdChannelReport.getBaddebtMoneySum().compareTo(new BigDecimal(0))==0){
						thirdChannelReport.setBaddebtMoneySum(new BigDecimal(0.00));
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar calendardate = Calendar.getInstance();
					Date sdfdate = sdf.parse(nowTime);
					calendardate.setTime(sdfdate);
					calendardate.add(Calendar.DATE, -1);
					
					thirdChannelReport.setThirdId(thirdId);
					thirdChannelReport.setChannelid(tid.getChannelid());
					thirdChannelReport.setReportDate(calendardate.getTime());
					thirdChannelReport.setThirdType("0");
					thirdChannelReportDao.deleteByThirdChannelReport(thirdChannelReport);

					thirdChannelReportDao.insert(thirdChannelReport);
				}
			}

		} catch (Exception e) {
			log.error("channek saveReport error:{}", e);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean pushAginUserReport(String nowTime) {

		
		HashMap<String, Object> params = new HashMap<>();
		JSONObject json = new JSONObject();
		String result;
		PageConfig<Map<String,Object>> pm = new PageConfig<>();
		pm.setTotalPageNum(1);
		params.put(Constant.PAGE_SIZE, "100");
		try {
			Map<String, String> mapPush = SysCacheUtils.getConfigParams("PUSH_USER");
			String pushUrl = mapPush.get("push_user_url");
			String urlAdd = UserPushUntil.REPORT_USER_URL;
			
			for (int i = 1; i <= pm.getTotalPageNum(); i++) {
//				idList = thirdChannelReportDao.findUserId(nowTime);
				params.put(Constant.NAME_SPACE, "ThirdChannelReport");
				params.put(Constant.CURRENT_PAGE, i);
				params.put("nowTime", nowTime);
				try {
					
					pm=paginationDao.findPage("findUserInfoById", "findUserInfoByIdCount", params,"web");
				} catch (Exception e) {
					log.error("1",e);
				}
				
				List<Map<String,Object>> borrowList = pm.getItems();
				List<Map<String,Object>> pushMap = new ArrayList<>();
				if(borrowList.size()>0){
					
				
					for (Map<String, Object> map : borrowList) {
						Map<String,Object> resultMap =new HashMap<>();
						resultMap.put("backUserId", map.get("inviteUserid")==null?"":map.get("inviteUserid"));//pushid
						resultMap.put("userId",  map.get("id")==null?"":map.get("id"));//userid
						resultMap.put("userPhone", map.get("userPhone")==null?"":map.get("userPhone"));//用户手机号
						resultMap.put("registeredDate", map.get("createTime")==null?"":map.get("createTime").toString());//注册时间
						resultMap.put("realName", map.get("realname")==null?"":map.get("realname"));//姓名
						resultMap.put("relNameDate", map.get("realnameTime")==null?"":map.get("id").toString());//实名认证时间
						if(map.get("firstContactPhone")!=null){
							resultMap.put("contactDate", map.get("realnameTime")==null?"":map.get("realnameTime").toString());//紧急联系人认证时间
						}
						if(map.get("bankCreateTime")!=null&&map.get("bankStatus")!=null){
								resultMap.put("bankCardDate", map.get("bankCreateTime")==null?"":map.get("bankCreateTime").toString());//绑卡时间
						}
						resultMap.put("operatorDate", map.get("jxlTokenTime")==null?"":map.get("jxlTokenTime").toString());//JXL认证时间
						resultMap.put("zhimaDate", map.get("zmScoreTime")==null?"":map.get("zmScoreTime").toString());//芝麻认证时间
						if(map.get("companyPhone")!=null&&map.get("companyName")!=null){
							resultMap.put("workInfoDate",  map.get("zmScoreTime")==null?"":map.get("zmScoreTime").toString());//工作信息认证时间
						}
						if(map.get("id")!=null){
							Map<String,Object> borrowInfoMap = thirdChannelReportDao.findUserBorrowSucInfo(map.get("id").toString());
							
							if(borrowInfoMap!=null&&borrowInfoMap.size()>0){
								if(borrowInfoMap.get("borrowStatus")!=null){
									Integer borrowStatus = Integer.valueOf(borrowInfoMap.get("borrowStatus").toString());
									if (borrowStatus==(STATUS_HKZ)
											|| borrowStatus==(STATUS_BFHK)
											|| borrowStatus==(BorrowOrder.STATUS_YHK)
											|| borrowStatus==(STATUS_YYQ)
											|| borrowStatus==(STATUS_YHZ)
											|| borrowStatus==(BorrowOrder.STATUS_YQYHK)) {
										resultMap.put("isLoanSuccessfulDate", borrowInfoMap.get("loanTime")==null?"":borrowInfoMap.get("loanTime").toString());//借款成功时间
										if(borrowStatus==(STATUS_HKZ)
												|| borrowStatus==(STATUS_BFHK)||borrowStatus==(BorrowOrder.STATUS_YHK)|| borrowStatus==(BorrowOrder.STATUS_YQYHK)){
											resultMap.put("isRepaymentDate", borrowInfoMap.get("repaymentRealTime")==null?"":borrowInfoMap.get("repaymentRealTime").toString());//还款时间
										}
										
										  if(borrowStatus==(STATUS_YYQ)
													|| borrowStatus==(STATUS_YHZ)|| borrowStatus==(BorrowOrder.STATUS_YQYHK)){
											resultMap.put("isOverdueDate", borrowInfoMap.get("repaymentRealTime")==null?"":borrowInfoMap.get("repaymentRealTime").toString());//是否逾期
										}
									}
								}
							}
							
						}
						
						resultMap.put("cardNum", map.get("idNumber"));//身份证号
						resultMap.put("isBlack", null);//是否是黑名单
						pushMap.add(resultMap);
					}
					
					json.put("date", new Date());
					json.put("resjson",pushMap);
					String jsonMap= JSONUtil.beanToJson(json);
					
					String url = pushUrl+ urlAdd;
					result = WebClient.getInstance().postJsonData(url, jsonMap,null);
					try {
						JSONObject jsonObject = JSONObject.fromObject(result);
					} catch (Exception e) {
						log.error("addPushUserReport error:{}",e);
					}
				}
				
				
				
			}
		} catch (Exception e) {
			log.error("addPushUserReport error:{} ", e);
		}
		return false;
		
	}

	

}
