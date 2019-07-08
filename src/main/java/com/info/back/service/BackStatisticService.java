package com.info.back.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.info.web.pojo.MyPageReportInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IApplyBorrowStatisticDao;
import com.info.back.dao.IPlatformReportDao;
import com.info.back.dao.IRegisterStatisticDao;
import com.info.back.dao.ISendMoneyStatisticDao;
import com.info.back.utils.SysCacheUtils;
import com.info.constant.Constant;
import com.info.risk.dao.IRiskOrdersDao;
import com.info.risk.pojo.RiskBlackUser;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.risk.utils.ThreadPool;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.PlatformReport;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

@Service("backStatisticService")
public class BackStatisticService implements IBackStatisticService {


	@Autowired
	private IRegisterStatisticDao registerStatisticDao;

	@Autowired
	private IApplyBorrowStatisticDao applyBorrowStatisticDao;

	@Autowired
	ISendMoneyStatisticDao sendMoneyStatisticDao;
	@Autowired
	private IPlatformReportDao platformReportDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IRiskOrdersDao riskOrdersDao;

	public Map<String, Long> registerStatistic() {

		Map<String, Long> registerStatisticResultMap = new HashMap<>();

		long sumCount = registerStatisticDao.registerSumCount();
		long todayRegCount = registerStatisticDao.registerTodayCount();

		registerStatisticResultMap.put("sumCount", sumCount);
		registerStatisticResultMap.put("todayRegCount", todayRegCount);
		return registerStatisticResultMap;
	}

	@Override
	public Map<String, Long> findAllApprove(String type) {
		Map<String, Long> map = new HashMap<>();
		Long value = 0l;
		if ("realName".equals(type)) {
			value = registerStatisticDao.findOneApprove1();
		} else if ("bank".equals(type)) {
			value = registerStatisticDao.findOneApprove2();
		} else if ("zm".equals(type)) {
			value = registerStatisticDao.findOneApprove3();
		} else if ("mobile".equals(type)) {
			value = registerStatisticDao.findOneApprove4();
		} else if ("contacat".equals(type)) {
			value = registerStatisticDao.findOneApprove5();
		} else if ("allApprove".equals(type)) {
			value = registerStatisticDao.findAllApprove();
		}
		map.put(type, value);
		return map;
	}

	public Map<String, Long> applyBorrowCountStatistic() {

		Map<String, Long> applyStatisticResultMap = new HashMap<String, Long>();

		long sumCount = applyBorrowStatisticDao.applyBorrowOrderSumCount();
		long todayApplyCount = applyBorrowStatisticDao.applyBorrowOrderTodayCount();
		long applySuccToday = applyBorrowStatisticDao.applySuccTodayCount();

		applyStatisticResultMap.put("applySumCount", sumCount);
		applyStatisticResultMap.put("todayApplyCount", todayApplyCount);
		applyStatisticResultMap.put("applySuccToday", applySuccToday);
		return applyStatisticResultMap;
	}

	public Map<String, Object> sendMoneyStatistic() {
		Map<String, Object> sendMoneyResultMap = new HashMap<String, Object>();
		Map<String, Object> map1 = sendMoneyStatisticDao.sendMoneyCount();
		if (map1 != null && map1.size() > 0) {
			String key = "borrowMoney";
			BigDecimal money = new BigDecimal(map1.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			map1.put(key, money);
			sendMoneyResultMap.putAll(map1);
		}
		Map<String, Object> map2 = sendMoneyStatisticDao.sendMoneyCountToday();
		if (map2 != null && map2.size() > 0) {
			String key = "borrowMoneyToday";
			BigDecimal money = new BigDecimal(map2.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			map2.put(key, money);
			sendMoneyResultMap.putAll(map2);
		}
		return sendMoneyResultMap;
	}

	@Override
	public Map<String, Object> findLaon() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map1 = sendMoneyStatisticDao.findLoaning();
		if (map1 != null && map1.size() > 0) {
			String key = "loaningMoney";
			BigDecimal money = new BigDecimal(map1.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			map1.put(key, money);
			params.putAll(map1);
		}
		Map<String, Object> map2 = sendMoneyStatisticDao.findLoanFail();
		if (map2 != null && map2.size() > 0) {
			String key = "loanFailMoney";
			BigDecimal money = new BigDecimal(map2.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			map2.put(key, money);
			params.putAll(map2);
		}
		return params;
	}

	@Override
	public Map<String, Object> findRiskOrders() {
		Map<String, Object> params = new HashMap<>();
		List<Integer> list1 = sendMoneyStatisticDao.findRiskOrders();
		if (list1 != null && list1.size() > 0) {
			Integer riskTotalOrders = list1.get(0);
			Integer riskPassOrders = list1.get(1);
			BigDecimal riskPassOrdersRate = BigDecimal.ZERO;
			if (riskTotalOrders != null && riskTotalOrders != 0) {
				riskPassOrdersRate = new BigDecimal(riskPassOrders).multiply(new BigDecimal(100)).divide(new BigDecimal(riskTotalOrders), 2,
						BigDecimal.ROUND_HALF_UP);
			}
			params.put("riskTotalOrders", riskTotalOrders);
			params.put("riskPassOrders", riskPassOrders);
			params.put("riskPassOrdersRate", riskPassOrdersRate);
		}
		return params;

	}

	@Override
	public Map<String, Object> findRiskUser() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> list1 = sendMoneyStatisticDao.findRiskUser();
		if (list1 != null && list1.size() > 0) {
			Integer riskTotalUser = list1.get(0);
			Integer riskPassUser = list1.get(1);
			BigDecimal riskPassUserRate = BigDecimal.ZERO;
			if (riskTotalUser != null && riskTotalUser != 0) {
				riskPassUserRate = new BigDecimal(riskPassUser).multiply(new BigDecimal(100)).divide(new BigDecimal(riskTotalUser), 2,
						BigDecimal.ROUND_HALF_UP);
			}
			params.put("riskTotalUser", riskTotalUser);
			params.put("riskPassUser", riskPassUser);
			params.put("riskPassUserRate", riskPassUserRate);
		}
		return params;

	}

	@Override
	public Map<String, Object> findRiskOrdersToday() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> list1 = sendMoneyStatisticDao.findRiskOrdersToday();
		if (list1 != null && list1.size() > 0) {
			Integer riskOrdersTotalToday = list1.get(0);
			Integer riskOrdersPassToday = list1.get(1);
			BigDecimal riskOrdersPassRateToday = BigDecimal.ZERO;
			if (riskOrdersTotalToday != null && riskOrdersTotalToday!= 0) {
				riskOrdersPassRateToday = new BigDecimal(riskOrdersPassToday).multiply(new BigDecimal(100)).divide(
						new BigDecimal(riskOrdersTotalToday), 2, BigDecimal.ROUND_HALF_UP);
			}
			params.put("riskOrdersTotalToday", riskOrdersTotalToday);
			params.put("riskOrdersPassToday", riskOrdersPassToday);
			params.put("riskOrdersPassRateToday", riskOrdersPassRateToday);
		}
		return params;

	}

	@Override
	public void updateReport(Calendar begin, Calendar end) {
		Map<String, String> intervalMap = SysCacheUtils.getConfigMap(BackConfigParams.INTERVAL);
		String unit = intervalMap.get("FACE_UNIT");
		if (StringUtils.isBlank(unit)) {
			unit = "0";
		}
		String yys = intervalMap.get("YYS_UNIT");
		if (StringUtils.isBlank(yys)) {
			yys = "0";
		}
		BigDecimal faceUnit = new BigDecimal(unit);
		BigDecimal yysUnit = new BigDecimal(yys);
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(begin.getTime());
		HashMap<String, Object> params = new HashMap<>();
		while (!tmp.after(end)) {
			String reportDate = DateUtil.getDateFormat(tmp.getTime(), "yyyy-MM-dd");
			String tbDate = DateUtil.getDateFormat(tmp.getTime(), "yyyyMMdd");
			Integer registerNum = platformReportDao.findRegisterCount(reportDate);
			Integer realPersonNum = platformReportDao.findRealPersonCount(reportDate);
			// 谢亚玲人脸识别有比对参数返回会插入此表
			Integer realNum = platformReportDao.findRealCount(reportDate);
			BigDecimal realMoney = new BigDecimal(realNum).multiply(faceUnit);
			Integer yysNum = platformReportDao.findYysCount(reportDate);
			Integer yysSucNum = platformReportDao.findYysSucCount(reportDate);
			BigDecimal yysMoney = new BigDecimal(yysSucNum).multiply(yysUnit);
			Integer bankNum = platformReportDao.findBankCount(reportDate);

			Integer zmNum = platformReportDao.findZmCount(reportDate);
			// 6666芝麻单价暂时按照0元，中统计次数
			BigDecimal zmMoney = BigDecimal.ZERO;
			// 666，用户认证时没有存入是否认证和认证的时间
			Integer workNum = 0;

			Integer alipayNum = platformReportDao.findAlipayCount(reportDate);

			Integer borrowNum = platformReportDao.findBorrowCount(reportDate);
			Integer borrowSucNum = 0;
			BigDecimal shouldPayMoney = BigDecimal.ZERO;
			List<Map<String, Object>> list = platformReportDao.findBorrowSucCountMoney(reportDate);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				borrowSucNum = Integer.valueOf(String.valueOf(map.get("borrowSucCount")));
				shouldPayMoney = new BigDecimal(map.get("borrowMoney") + "");
			}

			BigDecimal borrowRate = BigDecimal.ZERO;
			if (borrowNum != 0) {
				borrowRate = new BigDecimal(borrowSucNum).multiply(new BigDecimal("100")).divide(new BigDecimal(borrowNum), 2,
						BigDecimal.ROUND_HALF_UP);
			}
			BigDecimal sucPayMoney = BigDecimal.ZERO;
			Integer sucPayNum = 0;
			list = platformReportDao.findPaySucCountMoney(reportDate);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				sucPayNum = Integer.valueOf(String.valueOf(map.get("paySucCount")));
				sucPayMoney = new BigDecimal(map.get("payMoney") + "");
			}
			BigDecimal noPayMoney = BigDecimal.ZERO;
			Integer noPayNum = 0;
			list = platformReportDao.findNoPayCountMoney(reportDate);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				noPayNum = Integer.valueOf(String.valueOf(map.get("noPayCount")));
				noPayMoney = new BigDecimal(map.get("noPayMoney") + "");
			}
			BigDecimal failPayMoney = BigDecimal.ZERO;
			Integer failPayNum = 0;
			list = platformReportDao.findFailPayCountMoney(reportDate);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				failPayNum = Integer.valueOf(String.valueOf(map.get("failPayCount")));
				failPayMoney = new BigDecimal(map.get("failPayMoney") + "");
			}
			BigDecimal waitPayMoney = noPayMoney.add(failPayMoney);
			Integer waitPayNum = noPayNum + failPayNum;
			Integer borrowUserNum = platformReportDao.findBorrowUserCount(reportDate);
			Integer borrowUserSucNum = platformReportDao.findBorrowSucUserCount(reportDate);
			BigDecimal borrowUserRate = BigDecimal.ZERO;
			if (borrowUserNum != 0) {
				borrowUserRate = new BigDecimal(borrowUserSucNum).multiply(new BigDecimal("100")).divide(new BigDecimal(borrowUserNum), 2,
						BigDecimal.ROUND_HALF_UP);
			}
			// 此处同时统计黑名单信息到黑名单表，同时更新状态为已统计
			// 禁止项命中数
			params.put("tbDate", tbDate);
			params.put("reportDate", reportDate);
			params.put("ruleId", "121");
			params.put("sql", "rule_value!=1");
			params.put("sql2", " 1 as blackType");
			Integer stopNum = platformReportDao.findRiskCount(params);
			int total = 0;
			int size = 1000;
			if (stopNum > 0) {
				if (stopNum % size > 0) {
					total = stopNum / size + 1;
				} else {
					total = stopNum / size;
				}
			}
			params.put(Constant.PAGE_SIZE, size);
			for (int i = 0; i < total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.putAll(params);
				ThreadPool.getInstance().run(new Runnable() {
					@Override
					public void run() {
						PageConfig<RiskBlackUser> pm = paginationDao.findPage("findRiskList", "findRiskCount", map, "back");
						List<RiskBlackUser> userList = pm.getItems();
						if (userList != null && userList.size() > 0) {
							platformReportDao.batchDelete(userList);
							platformReportDao.batchInsert(userList);
							userList = null;
						}

					}
				});
			}
			// 命中反欺诈
			params.put("ruleId", "162");
			params.put("sql", "rule_value in('g','e')");
			params.put("sql2", " 2 as blackType");
			Integer fqz = platformReportDao.findRiskCount(params);
			total = 0;
			if (fqz > 0) {
				if (fqz % size > 0) {
					total = fqz / size + 1;
				} else {
					total = fqz / size;
				}
			}
			for (int i = 0; i < total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				final HashMap<String, Object> map = new HashMap<>();
				map.putAll(params);
				ThreadPool.getInstance().run(new Runnable() {
					@Override
					public void run() {
						PageConfig<RiskBlackUser> pm = paginationDao.findPage("findRiskList", "findRiskCount", map, "back");
						List<RiskBlackUser> userList = pm.getItems();
						if (userList != null && userList.size() > 0) {
							platformReportDao.batchDelete(userList);
							platformReportDao.batchInsert(userList);
							userList = null;
						}

					}
				});
			}
			// 命中准入
			params.put("ruleId", "118");
			params.put("sql", "rule_value!=1");
			params.put("sql2", " 3 as blackType");
			Integer zr = platformReportDao.findRiskCount(params);
			params.clear();
			Integer riskNum = 0;
			Integer riskSucNum = 0;
			Integer riskToReview = 0;
			Integer riskToReviewZxErr = 0;
			Integer riskToReviewErr = 0;
			Integer riksToReviewReal = 0;
			List<Integer> list2 = platformReportDao.findRiskOrdersToday(reportDate);
			if (list2 != null && list2.size() > 0) {
				riskNum = list2.get(0);
				riskSucNum = list2.get(1);
				riskToReview = list2.get(2);
				riskToReviewZxErr = list2.get(3);
				riskToReviewErr = list2.get(4);
				riksToReviewReal = list2.get(5);
			}
			Integer riskFailNum = riskNum - riskSucNum;
			// 芝麻信用相关1.芝麻分；2芝麻行业关注度
			int zmScoreSuc = riskOrdersDao
					.findTypeCount(new RiskOrders(ConstantRisk.ZMXY, ConstantRisk.GET_SCORE, reportDate, RiskOrders.STATUS_SUC));
			int zmScoreFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.ZMXY, ConstantRisk.GET_SCORE, reportDate,
					RiskOrders.STATUS_OTHER));
			int zmIndustySuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.ZMXY, ConstantRisk.GET_INDUSTY, reportDate,
					RiskOrders.STATUS_SUC));
			int zmIndustyFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.ZMXY, ConstantRisk.GET_INDUSTY, reportDate,
					RiskOrders.STATUS_OTHER));
			// 同盾报告
			int tdSuc = riskOrdersDao
					.findTypeCount(new RiskOrders(ConstantRisk.TD, ConstantRisk.TD_PRELOAN_APPLY, reportDate, RiskOrders.STATUS_SUC));
			int tdFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.TD, ConstantRisk.TD_PRELOAN_APPLY, reportDate,
					RiskOrders.STATUS_OTHER));
			// 白骑士
			int bqsSuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.BQS, ConstantRisk.BQS_RISK, reportDate, RiskOrders.STATUS_SUC));
			int bqsFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.BQS, ConstantRisk.BQS_RISK, reportDate, RiskOrders.STATUS_OTHER));
			// 中智诚
			int zzcSuc = riskOrdersDao
					.findTypeCount(new RiskOrders(ConstantRisk.ZZC, ConstantRisk.ZZC_FQZ_CREATE, reportDate, RiskOrders.STATUS_SUC));
			int zzcFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.ZZC, ConstantRisk.ZZC_FQZ_CREATE, reportDate,
					RiskOrders.STATUS_OTHER));
			// 91征信
			int jyzxSuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.JYZX, ConstantRisk.JYZX_BORROW, reportDate, RiskOrders.STATUS_SUC));
			int jyzxFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.JYZX, ConstantRisk.JYZX_BORROW, reportDate,
					RiskOrders.STATUS_OTHER));
			// 蜜罐
			int jxlMgSuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.MG, ConstantRisk.MG_BLACK, reportDate, RiskOrders.STATUS_SUC));
			int jxlMgFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.MG, ConstantRisk.MG_BLACK, reportDate, RiskOrders.STATUS_OTHER));
			// 聚信立
			int jxlReportSuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.JXL, ConstantRisk.JXL_REPORT, reportDate,
					RiskOrders.STATUS_SUC));
			int jxlReportFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.JXL, ConstantRisk.JXL_REPORT, reportDate,
					RiskOrders.STATUS_OTHER));
			// 宜信
			int yxSuc = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.YX, ConstantRisk.YX_BORROW, reportDate, RiskOrders.STATUS_SUC));
			int yxFail = riskOrdersDao.findTypeCount(new RiskOrders(ConstantRisk.YX, ConstantRisk.YX_BORROW, reportDate, RiskOrders.STATUS_OTHER));
			platformReportDao.update(reportDate);
			platformReportDao.insert(new PlatformReport(reportDate, registerNum, realPersonNum, realNum, realMoney, yysNum, yysSucNum, yysMoney,
					bankNum, zmNum, zmMoney, workNum, alipayNum, borrowNum, borrowSucNum, borrowRate, shouldPayMoney, sucPayMoney, sucPayNum,
					noPayMoney, noPayNum, failPayMoney, failPayNum, waitPayMoney, waitPayNum, borrowUserNum, borrowUserSucNum, borrowUserRate,
					stopNum, fqz, zr, riskNum, riskSucNum, riskFailNum, riskToReview, riskToReviewZxErr, riskToReviewErr, riksToReviewReal,
					zmScoreSuc, zmScoreFail, zmIndustySuc, zmIndustyFail, tdSuc, tdFail, bqsSuc, bqsFail, zzcSuc, zzcFail, jyzxSuc, jyzxFail,
					jxlMgSuc, jxlMgFail, jxlReportSuc, jxlReportFail, yxSuc, yxFail));
			tmp.add(Calendar.DAY_OF_YEAR, 1);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<PlatformReport> findPlatformPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "PlatformReport");
		PageConfig<PlatformReport> pageConfig = new PageConfig<PlatformReport>();
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params, "back");
		return pageConfig;
	}

	@Override
	public Integer findPlatformCount(HashMap<String, Object> params) {

		return platformReportDao.findAllCount(params);
	}

	/**
	 * 运营后台首页
	 * @param customerType
	 * @return
	 */
	@Override
	public Map<String, Object> findOldToday(String customerType) {
		Map<String, Object> params = new HashMap<>();
		List<Integer> list1 = sendMoneyStatisticDao.findOldToday(customerType);
		if (list1 != null && list1.size() > 0) {
			Integer todayOldOrder = list1.get(0);
			Integer todayOldOrderReview = list1.get(1);
			Integer todayOldOrderSuc = list1.get(2);
			BigDecimal todayOldOrderRate = BigDecimal.ZERO;
			if (todayOldOrderReview != null && todayOldOrderReview != 0) {
				todayOldOrderRate = new BigDecimal(todayOldOrderSuc).multiply(new BigDecimal(100)).divide(new BigDecimal(todayOldOrderReview), 2,
						BigDecimal.ROUND_HALF_UP);
			}
			// params.put("todayOldOrder", todayOldOrder);
			params.put("todayOldOrderReview" + customerType, todayOldOrderReview);
			params.put("todayOldOrderSuc" + customerType, todayOldOrderSuc);
			params.put("todayOldOrderRate" + customerType, todayOldOrderRate);
		}
		return params;

	}
    public BigDecimal optimic(Map<String,Object> map,String key){
        BigDecimal money = BigDecimal.valueOf(0);
         if(map.size()>0){
             if(map.get(key) !=null){
                 money = new BigDecimal(map.get(key) + "").divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
             }
         }
         return money;
    }
	/**
	 * 首页数据统计
	 * @return
	 */
	@Override
	public MyPageReportInfo findMyPageReportInfo() {
		MyPageReportInfo  myPageReportInfo = new MyPageReportInfo();
		Map<String,Object> map = new HashMap<String,Object>();
		//总放款金额 //总放款笔数
        map=sendMoneyStatisticDao.sendMoneyCount();
        myPageReportInfo.setAllLoanMoney(optimic(map,"borrowMoney"));
        myPageReportInfo.setAllLoanCount(map.get("borrowCount") == null ?0:(long) map.get("borrowCount") );
		//总已还款金额 // 总已还款笔数
		map=sendMoneyStatisticDao.findAllPendingRepayMoney(1);
		myPageReportInfo.setAllRepayMoney(optimic(map,"repaymentedAmount"));
		myPageReportInfo.setAllRepayCount(map.get("countnumber") == null ?0:(long) map.get("countnumber"));
        //总待回款金额//总待回款笔数
		map=sendMoneyStatisticDao.findAllPendingRepayMoney(2);
        myPageReportInfo.setAllPendingRepayMoney(optimic(map,"money"));
		myPageReportInfo.setAllRepaymentCount(map.get("countnumber") == null ?0: (long) map.get("countnumber"));
		//总未逾期待收金额//总未逾期待收笔数
        map=sendMoneyStatisticDao.findAllPendingRepayMoney(3);
        myPageReportInfo.setAllOverMoney(optimic(map,"money"));
        myPageReportInfo.setAllOverCount(map.get("countnumber") == null?0:(long) map.get("countnumber"));
        //放款失败金额 //放款失败笔数
		map=sendMoneyStatisticDao.findLoanFail();
        myPageReportInfo.setFailLoanMoney(optimic(map,"loanFailMoney"));
        myPageReportInfo.setFailLoanCount(map.get("loanFailCount") == null?0:(long) map.get("loanFailCount"));
        //逾期1-3天金额
		BigDecimal money1 = sendMoneyStatisticDao.lateMoney(1,3);
		if(money1 != null){
			myPageReportInfo.setS1Money(money1.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		}
        //逾期3-7天金额
		BigDecimal money2 = sendMoneyStatisticDao.lateMoney(4,7);
		if(money2 != null){
			myPageReportInfo.setS2Money(money2.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		}
        //逾期7-15天金额
		BigDecimal money3 = sendMoneyStatisticDao.lateMoney(8,15);
		if(money3 != null){
			myPageReportInfo.setS3Money(money3.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		}
        //逾期15天以上总金额
		BigDecimal money4 = sendMoneyStatisticDao.lateMoney(16,9999);
		if(money4 != null){
			myPageReportInfo.setS4Money(money4.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		}

        //当日放款金额 //当日放款笔数
		map=sendMoneyStatisticDao.loanMoneyToday();
        myPageReportInfo.setLoanMoney(optimic(map,"borrowMoneyToday"));
        myPageReportInfo.setLoanCount(map.get("borrowCountToday") == null ?0:(long) map.get("borrowCountToday"));
		//总用户注册数
		long sumCount = registerStatisticDao.registerSumCount();
		myPageReportInfo.setAllRegistCount((long) sumCount);
		//当天用户注册数
		long todayRegCount = registerStatisticDao.registerTodayCount();
		myPageReportInfo.setRegistCount((int) todayRegCount);
		//当天用户申请笔数
		Integer applyCountToday=sendMoneyStatisticDao.applyCountToday();
		//当天用户申请人数
		Integer applyUserCountToday =sendMoneyStatisticDao.applyUserCount();
		myPageReportInfo.setApplyCount(applyUserCountToday == null?0:applyUserCountToday);
        /*//当日到期金额/当日应还金额
		map=sendMoneyStatisticDao.findMoneyToday();
        myPageReportInfo.setPendingRepayMoney(optimic(map,"repaymentedAmount"));
        myPageReportInfo.setPendingRepayCount(map.get("countNumber") == null?0:(long) map.get("countNumber"));*/
        //当日已还款金额/全额还款金额  //当日已还款订单/当日到期订单
		map=sendMoneyStatisticDao.findTodayMoneyCount(30);
        myPageReportInfo.setRepyMoney(optimic(map,"money"));
		myPageReportInfo.setRepayedMoney(optimic(map,"repaymentedAmount"));
		myPageReportInfo.setRepyCount(map.get("countNumber") == null ?0:(Long) map.get("countNumber"));
        //当日待收金额/当日未还款金额  //当日待收金额/当日未还款金额
		map=sendMoneyStatisticDao.findMoneyToday();
		myPageReportInfo.setPendingMoney(optimic(map,"money"));
        myPageReportInfo.setPendingCount(map.get("countNumber") == null ?0:(long) map.get("countNumber"));
		DecimalFormat df = new DecimalFormat("0.0000");
		//当日放款率
		if(todayRegCount != 0){
			BigDecimal loanPercentage=(new BigDecimal(myPageReportInfo.getLoanCount())).divide(new BigDecimal(todayRegCount),4,BigDecimal.ROUND_DOWN);
			//当日放款笔数/当日注册总数
			myPageReportInfo.setLoanPercentage(df.format(loanPercentage));
		}else{
			myPageReportInfo.setLoanPercentage("0.00");
		}
		if(applyCountToday != 0){
			//当日通过率
			//当日放款笔数/当日申请总笔数
			BigDecimal passPercentage=new BigDecimal(myPageReportInfo.getLoanCount()).divide(new BigDecimal(applyCountToday),4,BigDecimal.ROUND_DOWN);
			myPageReportInfo.setPassPercentage(df.format(passPercentage));
		}else{
			myPageReportInfo.setPassPercentage("0.00");
		}
		/*//当日回款率
        Long repyCount = myPageReportInfo.getRepyCount();
        Long pendingCount = myPageReportInfo.getLoanCount();
        if(pendingCount != 0){
        	//当日成功汇款笔数/当日放款笔数
            double repayPercentage=(double)repyCount/(double)pendingCount;
            myPageReportInfo.setRepayPercentage(df.format(repayPercentage));
        }else{
        	myPageReportInfo.setRepayPercentage("0.00");
		}*/
		//当日展期金额 当日展期订单数
        map = sendMoneyStatisticDao.extendToday();
        /*myPageReportInfo.setExtendMoney(optimic(map,"extendMoney"));*/
		myPageReportInfo.setExtendMoney(optimic(map,"repaymentPrincipal"));
        myPageReportInfo.setExtendCount(map.get("extendCount") == null ?0:(long) map.get("extendCount"));
        //当日复借金额 当日复借订单数
        /*map = sendMoneyStatisticDao.reBorrow();
        myPageReportInfo.setReBorrowMoney(optimic(map,"reBorrowMoney"));
        myPageReportInfo.setReBorrowCount(map.get("reBorrowCount") == null?0:(long) map.get("reBorrowCount"));*/
        //当日复借放款订单数 当日复借放款金额
		map = sendMoneyStatisticDao.reBorrowSuc();
		myPageReportInfo.setReBorrowMoney(optimic(map,"reBorrowMoney"));
		myPageReportInfo.setReBorrowCount(map.get("reBorrowCount") == null?0:(long) map.get("reBorrowCount"));
	/*	//当日复借率 当日复借成功订单数/当日放款订单数
		if(pendingCount != 0){
           double reBorrowRate = (double)myPageReportInfo.getReBorrowCount()/(double)pendingCount;
           myPageReportInfo.setReBorrowReate(df.format(reBorrowRate));
		}else{
			myPageReportInfo.setReBorrowReate("0.00");
		}*/

		//当日到期金额/当日应还金额= 当日已还金额+当日待收金额+当日展期金额 当日到期订单
		myPageReportInfo.setPendingRepayMoney(myPageReportInfo.getRepyMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getRepyMoney()
				.add(myPageReportInfo.getPendingMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getPendingMoney())
				.add(myPageReportInfo.getExtendMoney() == null ? BigDecimal.valueOf(0) :myPageReportInfo.getExtendMoney()));
		myPageReportInfo.setPendingRepayCount(myPageReportInfo.getRepyCount()+myPageReportInfo.getPendingCount()+myPageReportInfo.getExtendCount());
		//三天内即将到期的总金额 (此处为当天往后推两天的两天内金额)
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 2);
		BigDecimal money = sendMoneyStatisticDao.threeMoney(c.getTime());
		if(money != null){
			BigDecimal one=money.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
			BigDecimal two=myPageReportInfo.getPendingRepayMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getPendingRepayMoney();
			myPageReportInfo.setThreeExpireMoney(one.add(two));
		}
		//当日回款率 当日回款金额（展期+回款）/当日到期金额 （以下皆为当日回款率）
		//当日所有正常回款金额
		/*map = sendMoneyStatisticDao.findMoneyTodayAll();
        BigDecimal repay = optimic(map,"money");
        Long repayCount = map.get("countNumber") == null ?0:(long) map.get("countNumber");
		//当日所有展期金额
		map =sendMoneyStatisticDao.extendTodayAll();
		optimic(map,"repaymentPrincipal");
		BigDecimal extend = optimic(map,"repaymentPrincipal");
		Long extendCount = map.get("extendCount") == null ?0:(long) map.get("extendCount");
		BigDecimal allMoney = myPageReportInfo.getPendingRepayMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getPendingRepayMoney();
		if(!allMoney.equals(BigDecimal.ZERO)){
			BigDecimal repayPercentage = (extend.add(repay )).divide(allMoney,4, BigDecimal.ROUND_HALF_UP);
			myPageReportInfo.setRepayPercentage(df.format(repayPercentage));
		}else{
			myPageReportInfo.setRepayPercentage("0.00");
		}*/
		//当日回款率 当日到期回款金额+当日到期展期金额/当日到期金额
		BigDecimal repayedMoneyToday = myPageReportInfo.getRepayedMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getRepayedMoney();
		BigDecimal extendMoneyToday = myPageReportInfo.getExtendMoney() == null ?BigDecimal.valueOf(0):myPageReportInfo.getExtendMoney();
		BigDecimal todayMoney = myPageReportInfo.getPendingRepayMoney() == null?BigDecimal.valueOf(0):myPageReportInfo.getPendingRepayMoney();
		if(todayMoney.intValue() != 0){
			BigDecimal repayPercentage = repayedMoneyToday.add(extendMoneyToday).divide(todayMoney,4,BigDecimal.ROUND_DOWN);
            myPageReportInfo.setRepayPercentage(df.format(repayPercentage));
		}else{
			myPageReportInfo.setRepayPercentage("0.00");
		}
		//当日复借率 = 当日复借数/当日到期回全款数
		Long reBorrowCount = myPageReportInfo.getReBorrowCount();
		Long repayTodyCount = myPageReportInfo.getRepyCount();
		if(repayTodyCount != 0){
			BigDecimal  reBorrowRate = new BigDecimal(reBorrowCount).divide (new BigDecimal(repayTodyCount),4,BigDecimal.ROUND_DOWN);
			myPageReportInfo.setReBorrowReate(df.format(reBorrowRate));
		}else{
			myPageReportInfo.setReBorrowReate("0.00");
		}
		//总用户注册百分比
        Long regist = sumCount - todayRegCount;
        if(!"null".equals(todayRegCount) && regist != 0){
            double allRegistPercentage = (double)todayRegCount / (double)regist;
            myPageReportInfo.setAllRegistPercentage(df.format(allRegistPercentage));
        }else{
        	myPageReportInfo.setAllRegistPercentage("0.00");
		}
		return myPageReportInfo;
	}
}
