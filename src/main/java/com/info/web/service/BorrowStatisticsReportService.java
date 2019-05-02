package com.info.web.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.dao.IRegisterStatisticDao;
import com.info.web.dao.IActiveStatisticsDao;
import com.info.web.dao.IBorrowStatisticsReportDao;
import com.info.web.pojo.ActiveStatisticsInfo;
import com.info.web.pojo.BorrowStatisticsReport;
import com.info.web.util.DateUtil;
@Slf4j
@Service
public class BorrowStatisticsReportService implements IBorrowStatisticsReportService {
	@Autowired
	private IBorrowStatisticsReportDao borrowStatisticsReportDao;

	@Autowired
	IRegisterStatisticDao registerStatisticDao;
	@Resource
	private IActiveStatisticsDao activeStatisticsDao;

	@Override
	public void createborrowStaticReportDateByDate(String nowDate) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			if (nowDate == null) {
				cal.add(Calendar.DATE, -1);
				nowDate = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			}

			//
			Date reportDate = sdf.parse(nowDate);
			log.info("========createborrowStaticReportDateByDate begin");
			List<BorrowStatisticsReport> listCurrentUsercount = borrowStatisticsReportDao.selectCurrentUsercount(nowDate);
			List<BorrowStatisticsReport> listPromotionCountThisDay = borrowStatisticsReportDao.selectPromotionCountThisDay(nowDate);
			List<BorrowStatisticsReport> listPromotionCountThisWeek = borrowStatisticsReportDao.selectPromotionCountThisWeek(nowDate);
			List<BorrowStatisticsReport> listPromotionCountThisMonth = borrowStatisticsReportDao.selectPromotionCountThisMonth(nowDate);

			BorrowStatisticsReport bsReport1 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport2 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport3 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport4 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport5 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport6 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport7 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport8 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport9 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport10 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport10more = new BorrowStatisticsReport();

			Integer allBorrowUserCount = 0;
			// #########第2、3个字段 报表时间
			bsReport1.setReportDate(reportDate);
			bsReport1.setBorrowCount(1);
			bsReport2.setReportDate(reportDate);
			bsReport2.setBorrowCount(2);
			bsReport3.setReportDate(reportDate);
			bsReport3.setBorrowCount(3);
			bsReport4.setReportDate(reportDate);
			bsReport4.setBorrowCount(4);
			bsReport5.setReportDate(reportDate);
			bsReport5.setBorrowCount(5);
			bsReport6.setReportDate(reportDate);
			bsReport6.setBorrowCount(6);
			bsReport7.setReportDate(reportDate);
			bsReport7.setBorrowCount(7);
			bsReport8.setReportDate(reportDate);
			bsReport8.setBorrowCount(8);
			bsReport9.setReportDate(reportDate);
			bsReport9.setBorrowCount(9);
			bsReport10.setReportDate(reportDate);
			bsReport10.setBorrowCount(10);
			bsReport10more.setReportDate(reportDate);
			bsReport10more.setBorrowCount(11);

			bsReport1.setCurrentUsercount(0);
			bsReport2.setCurrentUsercount(0);
			bsReport3.setCurrentUsercount(0);
			bsReport4.setCurrentUsercount(0);
			bsReport5.setCurrentUsercount(0);
			bsReport6.setCurrentUsercount(0);
			bsReport7.setCurrentUsercount(0);
			bsReport8.setCurrentUsercount(0);
			bsReport9.setCurrentUsercount(0);
			bsReport10.setCurrentUsercount(0);
			bsReport10more.setCurrentUsercount(0);
			// ######### 第4、5个字段 当前用户数、占总借款人数比例
			if (listCurrentUsercount != null && listCurrentUsercount.size() > 0) {
				for (BorrowStatisticsReport bsr : listCurrentUsercount) {
					Integer currentUsercount = bsr.getCurrentUsercount();
					allBorrowUserCount += currentUsercount;
				}
				for (BorrowStatisticsReport bsr : listCurrentUsercount) {
					Integer borrowCount = bsr.getBorrowCount();
					Integer currentUsercount = bsr.getCurrentUsercount();
					if (currentUsercount > 0) {
						// 此计算方式之适合小于等于10次的计算
						BigDecimal allBorrowRate = BigDecimal.ZERO;
						allBorrowRate = new BigDecimal(currentUsercount).multiply(new BigDecimal(1000000)).divide(new BigDecimal(allBorrowUserCount),
								2, BigDecimal.ROUND_HALF_UP);

						if (borrowCount == 1) {
							bsReport1.setCurrentUsercount(currentUsercount);
							bsReport1.setAllBorrowRate(allBorrowRate);

						} else if (borrowCount == 2) {
							bsReport2.setCurrentUsercount(currentUsercount);
							bsReport2.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 3) {

							bsReport3.setCurrentUsercount(currentUsercount);
							bsReport3.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 4) {
							bsReport4.setCurrentUsercount(currentUsercount);
							bsReport4.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 5) {
							bsReport5.setCurrentUsercount(currentUsercount);
							bsReport5.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 6) {
							bsReport6.setCurrentUsercount(currentUsercount);
							bsReport6.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 7) {
							bsReport7.setCurrentUsercount(currentUsercount);
							bsReport7.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 8) {
							bsReport8.setCurrentUsercount(currentUsercount);
							bsReport8.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 9) {
							bsReport9.setCurrentUsercount(currentUsercount);
							bsReport9.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount == 10) {
							bsReport10.setCurrentUsercount(currentUsercount);
							bsReport10.setAllBorrowRate(allBorrowRate);
						} else if (borrowCount > 10) {
							bsReport10more.setCurrentUsercount(bsReport10more.getCurrentUsercount() + currentUsercount);
						}
					}

				}
				// 占总借款人数比例 借款大于10次占总借款人数比例
				BigDecimal allBorrowRate10more = BigDecimal.ZERO;
				if (bsReport10more.getCurrentUsercount() > 0) {
					allBorrowRate10more = new BigDecimal(bsReport10more.getCurrentUsercount()).multiply(new BigDecimal(1000000)).divide(
							new BigDecimal(allBorrowUserCount), 2, BigDecimal.ROUND_HALF_UP);
				}
				bsReport10more.setAllBorrowRate(allBorrowRate10more);
			}

			// #########第6字段 当日升迁率
			if (listPromotionCountThisDay != null && listPromotionCountThisDay.size() > 0) {
				Integer allUsercount = 0;
				BigDecimal promotionRate = BigDecimal.ZERO;
				Integer promotion10moreCount = 0;
				for (BorrowStatisticsReport bsr : listPromotionCountThisDay) {

					Integer borrowCount = bsr.getBorrowCount();
					Integer promotionCount = bsr.getCurrentUsercount();

					if (borrowCount == 1) {
						long todayRegCount = registerStatisticDao.registerThisdayCount(nowDate);
						Integer regCounts = Integer.parseInt(String.valueOf(todayRegCount));
						allUsercount = regCounts + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);

						bsReport1.setDayPromotionRate(promotionRate);

					} else if (borrowCount == 2) {
						allUsercount = bsReport1.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport2.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 3) {
						allUsercount = bsReport2.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport3.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 4) {
						allUsercount = bsReport3.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport4.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 5) {
						allUsercount = bsReport4.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport5.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 6) {
						allUsercount = bsReport5.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport6.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 7) {
						allUsercount = bsReport6.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport7.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 8) {
						allUsercount = bsReport7.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport8.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 9) {
						allUsercount = bsReport8.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport9.setDayPromotionRate(promotionRate);
					} else if (borrowCount == 10) {
						allUsercount = bsReport9.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport10.setDayPromotionRate(promotionRate);
					} else if (borrowCount > 10) {
						promotion10moreCount += promotionCount;
					}
				}
				// 当日升迁率 借款大于10次当日升迁率
				allUsercount = bsReport10.getCurrentUsercount() + promotion10moreCount;
				if (allUsercount > 0) {
				

					promotionRate = new BigDecimal(promotion10moreCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
							BigDecimal.ROUND_HALF_UP);
					bsReport10.setDayPromotionRate(promotionRate);
				}

			}

			// #########第7字段 当周升迁率
			if (listPromotionCountThisWeek != null && listPromotionCountThisWeek.size() > 0) {
				Integer allUsercount = 0;
				BigDecimal promotionRate = BigDecimal.ZERO;
				Integer promotion10moreCount = 0;
				for (BorrowStatisticsReport bsr : listPromotionCountThisWeek) {

					Integer borrowCount = bsr.getBorrowCount();
					Integer promotionCount = bsr.getCurrentUsercount();

					if (borrowCount == 1) {
						long todayRegCount = registerStatisticDao.registerThisWeekCount(nowDate);
						Integer regCounts = Integer.parseInt(String.valueOf(todayRegCount));
						allUsercount = regCounts + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);

						bsReport1.setWeekPromotionRate(promotionRate);

					} else if (borrowCount == 2) {
						allUsercount = bsReport1.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport2.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 3) {
						allUsercount = bsReport2.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport3.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 4) {
						allUsercount = bsReport3.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport4.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 5) {
						allUsercount = bsReport4.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport5.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 6) {
						allUsercount = bsReport5.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport6.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 7) {
						allUsercount = bsReport6.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport7.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 8) {
						allUsercount = bsReport7.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport8.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 9) {
						allUsercount = bsReport8.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport9.setWeekPromotionRate(promotionRate);
					} else if (borrowCount == 10) {
						allUsercount = bsReport9.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport10.setWeekPromotionRate(promotionRate);
					} else if (borrowCount > 10) {
						promotion10moreCount += promotionCount;

					}

				}
				// 当周升迁率 借款大于10次当周升迁率
				allUsercount = bsReport10.getCurrentUsercount() + promotion10moreCount;
				if (allUsercount > 0) {
				promotionRate = new BigDecimal(promotion10moreCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
						BigDecimal.ROUND_HALF_UP);
				bsReport10.setWeekPromotionRate(promotionRate);
				}

			}

			// #########第8字段 当月升迁率
			if (listPromotionCountThisMonth != null && listPromotionCountThisMonth.size() > 0) {
				Integer allUsercount = 0;
				BigDecimal promotionRate = BigDecimal.ZERO;
				Integer promotion10moreCount = 0;
				for (BorrowStatisticsReport bsr : listPromotionCountThisMonth) {

					Integer borrowCount = bsr.getBorrowCount();
					Integer promotionCount = bsr.getCurrentUsercount();

					if (borrowCount == 1) {
						long todayRegCount = registerStatisticDao.registerThisMonthCount(nowDate);
						Integer regCounts = Integer.parseInt(String.valueOf(todayRegCount));
						allUsercount = regCounts + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);

						bsReport1.setMonthPromotionRate(promotionRate);

					} else if (borrowCount == 2) {
						allUsercount = bsReport1.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport2.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 3) {
						allUsercount = bsReport2.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport3.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 4) {
						allUsercount = bsReport3.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport4.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 5) {
						allUsercount = bsReport4.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport5.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 6) {
						allUsercount = bsReport5.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport6.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 7) {
						allUsercount = bsReport6.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport7.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 8) {
						allUsercount = bsReport7.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport8.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 9) {
						allUsercount = bsReport8.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport9.setMonthPromotionRate(promotionRate);
					} else if (borrowCount == 10) {
						allUsercount = bsReport9.getCurrentUsercount() + promotionCount;
						promotionRate = new BigDecimal(promotionCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
								BigDecimal.ROUND_HALF_UP);
						bsReport10.setMonthPromotionRate(promotionRate);
					} else if (borrowCount > 10) {
						promotion10moreCount += promotionCount;

					}

				}
				// 当月升迁率 借款大于10次当月升迁率
				allUsercount = bsReport10.getCurrentUsercount() + promotion10moreCount;
				if (allUsercount > 0) {
				promotionRate = new BigDecimal(promotion10moreCount).multiply(new BigDecimal(100)).divide(new BigDecimal(allUsercount), 2,
						BigDecimal.ROUND_HALF_UP);
				bsReport10.setMonthPromotionRate(promotionRate);
				}
			}
			borrowStatisticsReportDao.delSelective(bsReport1);
			
			borrowStatisticsReportDao.insertSelective(bsReport1);
			borrowStatisticsReportDao.insertSelective(bsReport2);
			borrowStatisticsReportDao.insertSelective(bsReport3);
			borrowStatisticsReportDao.insertSelective(bsReport4);
			borrowStatisticsReportDao.insertSelective(bsReport5);
			borrowStatisticsReportDao.insertSelective(bsReport6);
			borrowStatisticsReportDao.insertSelective(bsReport7);
			borrowStatisticsReportDao.insertSelective(bsReport8);
			borrowStatisticsReportDao.insertSelective(bsReport9);
			borrowStatisticsReportDao.insertSelective(bsReport10);
			borrowStatisticsReportDao.insertSelective(bsReport10more);
			log.info("========createborrowStaticReportDateByDate end");
		} catch (Exception e) {
			log.error("createborrowStaticReportDateByDate error:{}",e);
		}

	}
	
	@Override
	public void createborrowLateReportDateByDate(String nowDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			if (nowDate == null) {
				nowDate = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			}
			if (nowDate == null) {
				cal.add(Calendar.DATE, 1);
				nowDate = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			}
			//
			Date reportDate = sdf.parse(nowDate);

			log.error("========createborrowLateReportDateByDate begin");
			BorrowStatisticsReport bsReport1 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport2 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport3 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport4 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport5 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport6 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport7 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport8 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport9 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport10 = new BorrowStatisticsReport();
			BorrowStatisticsReport bsReport10more = new BorrowStatisticsReport();
			
			bsReport1.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport1.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport2.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport2.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport3.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport3.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport4.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport4.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport5.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport5.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport6.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport6.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport7.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport7.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport8.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport8.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport9.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport9.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport10.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport10.setMoneyDayOverduerate(BigDecimal.ZERO);
			bsReport10more.setDanDayOverduerate(BigDecimal.ZERO);			
			bsReport10more.setMoneyDayOverduerate(BigDecimal.ZERO);

			Integer borrowCount = 0;
			Integer currentUsercount = 0;
			Integer currentUserFMcount = 0;
			Integer currentUsercount10more = 0;
			Integer currentUserFMcount10more = 0;
			BigDecimal rate = BigDecimal.ZERO;
			BigDecimal moneyRate = BigDecimal.ZERO;
			BigDecimal moneyAmount = BigDecimal.ZERO;
			BigDecimal moneyAmountFM = BigDecimal.ZERO;
			BigDecimal moneyAmount10more = BigDecimal.ZERO;
			BigDecimal moneyAmountFM10more = BigDecimal.ZERO;
			
			BigDecimal danAvgOverduerate=BigDecimal.ZERO;
			BigDecimal moneyAvgOverduerate=BigDecimal.ZERO;
			BigDecimal danAvgOverduerate10moren=BigDecimal.ZERO;
			BigDecimal moneyAvgOverduerate10more=BigDecimal.ZERO;
			
			Integer countOne = 1;
			

			List<BorrowStatisticsReport> overdueThisDayCountList = borrowStatisticsReportDao
					.selectOverdueThisDayCountList(nowDate);
			if (overdueThisDayCountList != null
					&& overdueThisDayCountList.size() > 0) {
				for (BorrowStatisticsReport bsr : overdueThisDayCountList) {
					borrowCount = bsr.getBorrowCount();// 借款次数
					currentUsercount = bsr.getCurrentUsercount();// 视为单数
					moneyAmount = bsr.getMoneyAmount();// 金额

					List<BorrowStatisticsReport> fmlist = borrowStatisticsReportDao
							.selectOverdueThisDayCountFMList(nowDate);

					if (fmlist != null && fmlist.size() > 0) {
						for (BorrowStatisticsReport bsrFm : fmlist) {
							Integer borrowCountFm = bsrFm.getBorrowCount();
							currentUserFMcount = bsrFm.getCurrentUsercount();//获得逾期分母单数
							moneyAmountFM = bsrFm.getMoneyAmount();//获得逾期分母金额
							if(borrowCountFm==borrowCount){
								if (currentUserFMcount > 0) {
									rate = new BigDecimal(currentUsercount).multiply(
											new BigDecimal(100)).divide(
											new BigDecimal(currentUserFMcount), 2,
											BigDecimal.ROUND_HALF_UP);
								}
								if (moneyAmountFM.compareTo(BigDecimal.ZERO) == 1) {
									moneyRate = moneyAmount.multiply(
											new BigDecimal(100)).divide(moneyAmountFM,
											2, BigDecimal.ROUND_HALF_UP);
								}
								if (borrowCount == 1) {
									bsReport1.setDanDayOverduerate(rate);
									bsReport1.setMoneyDayOverduerate(moneyRate);
									bsReport1.setDanAvgOverduerate(danAvgOverduerate);
									bsReport1.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 2) {
									bsReport2.setDanDayOverduerate(rate);
									bsReport2.setMoneyDayOverduerate(moneyRate);
									bsReport2.setDanAvgOverduerate(danAvgOverduerate);
									bsReport2.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 3) {
									bsReport3.setDanDayOverduerate(rate);
									bsReport3.setMoneyDayOverduerate(moneyRate);
									bsReport3.setDanAvgOverduerate(danAvgOverduerate);
									bsReport3.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 4) {
									bsReport4.setDanDayOverduerate(rate);
									bsReport4.setMoneyDayOverduerate(moneyRate);
									bsReport4.setDanAvgOverduerate(danAvgOverduerate);
									bsReport4.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 5) {
									bsReport5.setDanDayOverduerate(rate);
									bsReport5.setMoneyDayOverduerate(moneyRate);
									bsReport5.setDanAvgOverduerate(danAvgOverduerate);
									bsReport5.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 6) {
									bsReport6.setDanDayOverduerate(rate);
									bsReport6.setMoneyDayOverduerate(moneyRate);
									bsReport6.setDanAvgOverduerate(danAvgOverduerate);
									bsReport6.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 7) {
									bsReport7.setDanDayOverduerate(rate);
									bsReport7.setMoneyDayOverduerate(moneyRate);
									bsReport7.setDanAvgOverduerate(danAvgOverduerate);
									bsReport7.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 8) {
									bsReport8.setDanDayOverduerate(rate);
									bsReport8.setMoneyDayOverduerate(moneyRate);
									bsReport8.setDanAvgOverduerate(danAvgOverduerate);
									bsReport8.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 9) {
									bsReport9.setDanDayOverduerate(rate);
									bsReport9.setMoneyDayOverduerate(moneyRate);
									bsReport9.setDanAvgOverduerate(danAvgOverduerate);
									bsReport9.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount == 10) {
									bsReport10.setDanDayOverduerate(rate);
									bsReport10.setMoneyDayOverduerate(moneyRate);
									bsReport10.setDanAvgOverduerate(danAvgOverduerate);
									bsReport10.setMoneyAvgOverduerate(moneyAvgOverduerate);
								} else if (borrowCount > 10) {
									currentUsercount10more += currentUsercount;
									currentUserFMcount10more += currentUserFMcount;
									moneyAmount10more = moneyAmount10more.add(moneyAmount);
									moneyAmountFM10more = moneyAmountFM10more
											.add(moneyAmountFM);
								}
							}
						}
					}
					/**/

					
				}

			}
			// 计算10次以上
			if (currentUserFMcount10more > 0) {
				rate = new BigDecimal(currentUsercount10more).multiply(
						new BigDecimal(100)).divide(
						new BigDecimal(currentUserFMcount10more), 2,
						BigDecimal.ROUND_HALF_UP);
				bsReport10more.setDanDayOverduerate(rate);// 
			}
			
			if (moneyAmountFM10more.compareTo(BigDecimal.ZERO) == 1) {
				moneyRate = moneyAmount10more.multiply(new BigDecimal(100))
						.divide(moneyAmountFM10more, 2,
								BigDecimal.ROUND_HALF_UP);
				bsReport10more.setMoneyDayOverduerate(moneyRate); 
			}
			
			bsReport10more.setDanAvgOverduerate(danAvgOverduerate10moren);
			bsReport10more.setMoneyAvgOverduerate(moneyAvgOverduerate10more);
			
			//少10次以上的平均值
			
			
			/*计算M3坏账率*/
			bsReport1.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport1.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport2.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport2.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport3.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport3.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport4.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport4.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport5.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport5.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport6.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport6.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport7.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport7.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport8.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport8.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport9.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport9.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport10.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport10.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			bsReport10more.setDanDayBaddebtrate(BigDecimal.ZERO);
			bsReport10more.setMoneyDayBaddebtrate(BigDecimal.ZERO);
			
			BigDecimal danAvgBaddebtrate=BigDecimal.ZERO;
			BigDecimal moneyAvgBaddebtrate=BigDecimal.ZERO;
			BigDecimal danAvgBaddebtrate10moren=BigDecimal.ZERO;
			BigDecimal moneyAvgBaddebtrate10more=BigDecimal.ZERO;
			
			currentUsercount10more = 0;
			currentUserFMcount10more = 0;
			moneyAmount10more = BigDecimal.ZERO;
			moneyAmountFM10more = BigDecimal.ZERO;
			
			List<BorrowStatisticsReport> baddebtrateThisDayList = borrowStatisticsReportDao.selectBaddebtrateThisDayList(nowDate);
			if(baddebtrateThisDayList.size()>0){
				for (BorrowStatisticsReport badsr :baddebtrateThisDayList) {
					borrowCount = badsr.getBorrowCount();//获得M3借款次数
					currentUsercount = badsr.getCurrentUsercount();
					moneyAmount = badsr.getMoneyAmount();
					List<BorrowStatisticsReport> baddebtrateThisDayFMList = borrowStatisticsReportDao.selectBaddebtrateThisDayFMList(nowDate);
					if(baddebtrateThisDayFMList.size()>0){
						for(BorrowStatisticsReport badsrFM:baddebtrateThisDayFMList){
							Integer borrowCountFM = badsrFM.getBorrowCount();
							currentUserFMcount =badsrFM.getCurrentUsercount();
							moneyAmountFM = badsrFM.getMoneyAmount();
							if(borrowCountFM ==borrowCount ){
								if (currentUserFMcount > 0) {
									rate = new BigDecimal(currentUsercount).multiply(
											new BigDecimal(100)).divide(
											new BigDecimal(currentUserFMcount), 2,
											BigDecimal.ROUND_HALF_UP);
								}
								if (moneyAmountFM.compareTo(BigDecimal.ZERO) == 1) {
									moneyRate = moneyAmount.multiply(
											new BigDecimal(100)).divide(moneyAmountFM,
											2, BigDecimal.ROUND_HALF_UP);
								}
								if (borrowCount == 1) {
									bsReport1.setDanDayBaddebtrate(rate);
									bsReport1.setMoneyDayBaddebtrate(moneyRate);
									bsReport1.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport1.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 2) {
									bsReport2.setDanDayBaddebtrate(rate);
									bsReport2.setMoneyDayBaddebtrate(moneyRate);
									bsReport2.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport2.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 3) {
									bsReport3.setDanDayBaddebtrate(rate);
									bsReport3.setMoneyDayBaddebtrate(moneyRate);
									bsReport3.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport3.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 4) {
									bsReport4.setDanDayBaddebtrate(rate);
									bsReport4.setMoneyDayBaddebtrate(moneyRate);
									bsReport4.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport4.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 5) {
									bsReport5.setDanDayBaddebtrate(rate);
									bsReport5.setMoneyDayBaddebtrate(moneyRate);
									bsReport5.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport5.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 6) {
									bsReport6.setDanDayBaddebtrate(rate);
									bsReport6.setMoneyDayBaddebtrate(moneyRate);
									bsReport6.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport6.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 7) {
									bsReport7.setDanDayBaddebtrate(rate);
									bsReport7.setMoneyDayBaddebtrate(moneyRate);
									bsReport7.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport7.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 8) {
									bsReport8.setDanDayBaddebtrate(rate);
									bsReport8.setMoneyDayBaddebtrate(moneyRate);
									bsReport8.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport8.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 9) {
									bsReport9.setDanDayBaddebtrate(rate);
									bsReport9.setMoneyDayBaddebtrate(moneyRate);
									bsReport9.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport9.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount == 10) {
									bsReport10.setDanDayBaddebtrate(rate);
									bsReport10.setMoneyDayBaddebtrate(moneyRate);
									bsReport10.setDanAvgBaddebtrate(danAvgBaddebtrate);
									bsReport10.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
								} else if (borrowCount > 10) {
									currentUsercount10more += currentUsercount;
									currentUserFMcount10more += currentUserFMcount;
									moneyAmount10more = moneyAmount10more.add(moneyAmount);
									moneyAmountFM10more = moneyAmountFM10more.add(moneyAmountFM);
								}
							}
						}
					}
				}
			}
			// 计算10次以上
			if (currentUserFMcount10more > 0) {
				rate = new BigDecimal(currentUsercount10more).multiply(
						new BigDecimal(100)).divide(
						new BigDecimal(currentUserFMcount10more), 2,
						BigDecimal.ROUND_HALF_UP);
				bsReport10more.setDanDayOverduerate(rate);// 
			}
			
			if (moneyAmountFM10more.compareTo(BigDecimal.ZERO) == 1) {
				moneyRate = moneyAmount10more.multiply(new BigDecimal(100))
						.divide(moneyAmountFM10more, 2,
								BigDecimal.ROUND_HALF_UP);
				bsReport10more.setMoneyDayOverduerate(moneyRate);
			}
			
			bsReport10more.setDanAvgBaddebtrate(danAvgBaddebtrate10moren);
			bsReport10more.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate10more);
			
			
			/*平均值*/
			  
			List<Map<String, Object>> avgThisDayCountList = borrowStatisticsReportDao.selectAvgThisDayCountList();
			if(avgThisDayCountList!=null &&avgThisDayCountList.size()>0){
				for(Map<String,Object> map:avgThisDayCountList){
					Integer borrowCountReport = Integer.parseInt(map.get("borrowCountReport").toString());
					Integer reportCount = Integer.parseInt(map.get("reportCount").toString());
					BigDecimal danDayOverduerate = new BigDecimal(map.get("danDayOverduerate").toString());
					BigDecimal moneyDayOverduerateSum = new BigDecimal(map.get("moneyDayOverduerate").toString()); 
					BigDecimal danDayBaddebtrate = new BigDecimal(map.get("danDayBaddebtrate").toString());
					BigDecimal moneyDayBaddebtrate = new BigDecimal(map.get("moneyDayBaddebtrate").toString());
					if (borrowCountReport == 1) {
						danAvgOverduerate=bsReport1.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport1.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport1.setDanAvgOverduerate(danAvgOverduerate);
						bsReport1.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport1.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport1.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport1.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport1.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 2) {
						danAvgOverduerate=bsReport2.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport2.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport2.setDanAvgOverduerate(danAvgOverduerate);
						bsReport2.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport2.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport2.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport2.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport2.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 3) {
						danAvgOverduerate=bsReport3.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport3.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport3.setDanAvgOverduerate(danAvgOverduerate);
						bsReport3.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport3.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport3.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport3.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport3.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 4) {
						danAvgOverduerate=bsReport4.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport4.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport4.setDanAvgOverduerate(danAvgOverduerate);
						bsReport4.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport4.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport4.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport4.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport4.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 5) {
						danAvgOverduerate=bsReport5.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport5.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport5.setDanAvgOverduerate(danAvgOverduerate);
						bsReport5.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport5.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport5.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport5.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport5.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 6) {
						danAvgOverduerate=bsReport6.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport6.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport6.setDanAvgOverduerate(danAvgOverduerate);
						bsReport6.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport6.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport6.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport6.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport6.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 7) {
						danAvgOverduerate=bsReport7.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport7.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport7.setDanAvgOverduerate(danAvgOverduerate);
						bsReport7.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport7.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport7.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport7.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport7.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 8) {
						danAvgOverduerate=bsReport8.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport8.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport8.setDanAvgOverduerate(danAvgOverduerate);
						bsReport8.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport8.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport8.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport8.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport8.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 9) {
						danAvgOverduerate=bsReport9.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport9.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport9.setDanAvgOverduerate(danAvgOverduerate);
						bsReport9.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport9.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport9.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport9.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport9.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport == 10) {
						danAvgOverduerate=bsReport10.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport10.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport10.setDanAvgOverduerate(danAvgOverduerate);
						bsReport10.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport10.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport10.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport10.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport10.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					} else if (borrowCountReport > 10) {
						danAvgOverduerate=bsReport10more.getDanDayOverduerate().add(danDayOverduerate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgOverduerate=bsReport10more.getMoneyDayOverduerate().add(moneyDayOverduerateSum).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport10more.setDanAvgOverduerate(danAvgOverduerate);
						bsReport10more.setMoneyAvgOverduerate(moneyAvgOverduerate);
						
						danAvgBaddebtrate=bsReport10more.getDanDayBaddebtrate().add(danDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						moneyAvgBaddebtrate=bsReport10more.getMoneyDayBaddebtrate().add(moneyDayBaddebtrate).divide(new BigDecimal(reportCount).add(new BigDecimal(countOne)), 2,BigDecimal.ROUND_HALF_UP);
						bsReport10more.setDanAvgBaddebtrate(danAvgBaddebtrate);
						bsReport10more.setMoneyAvgBaddebtrate(moneyAvgBaddebtrate);
					}
			}}
			
			Calendar reportDate1 = Calendar.getInstance();
			
			reportDate1.setTime(reportDate);
			reportDate1.add(Calendar.DATE, -1);
			
			bsReport1.setReportDate(reportDate1.getTime());
			bsReport1.setBorrowCount(1);
			borrowStatisticsReportDao.updateByReportDate(bsReport1);
			bsReport2.setReportDate(reportDate1.getTime());
			bsReport2.setBorrowCount(2);
			borrowStatisticsReportDao.updateByReportDate(bsReport2);
			bsReport3.setReportDate(reportDate1.getTime());
			bsReport3.setBorrowCount(3);
			borrowStatisticsReportDao.updateByReportDate(bsReport3);
			bsReport4.setReportDate(reportDate1.getTime());
			bsReport4.setBorrowCount(4);
			borrowStatisticsReportDao.updateByReportDate(bsReport4);
			bsReport5.setReportDate(reportDate1.getTime());
			bsReport5.setBorrowCount(5);
			borrowStatisticsReportDao.updateByReportDate(bsReport5);
			bsReport6.setReportDate(reportDate1.getTime());
			bsReport6.setBorrowCount(6);
			borrowStatisticsReportDao.updateByReportDate(bsReport6);
			bsReport7.setReportDate(reportDate1.getTime());
			bsReport7.setBorrowCount(7);
			borrowStatisticsReportDao.updateByReportDate(bsReport7);
			bsReport8.setReportDate(reportDate1.getTime());
			bsReport8.setBorrowCount(8);
			borrowStatisticsReportDao.updateByReportDate(bsReport8);
			bsReport9.setReportDate(reportDate1.getTime());
			bsReport9.setBorrowCount(9);
			borrowStatisticsReportDao.updateByReportDate(bsReport9);
			bsReport10.setReportDate(reportDate1.getTime());
			bsReport10.setBorrowCount(10);
			borrowStatisticsReportDao.updateByReportDate(bsReport10);
			bsReport10more.setReportDate(reportDate1.getTime());
			bsReport10more.setBorrowCount(11);
			borrowStatisticsReportDao.updateByReportDate(bsReport10more);
		
		
			
		} catch (Exception e) {
			log.error("createborrowLateReportDateByDate error:{}",e);
		}
	}
 
	@Override
	public List<BorrowStatisticsReport> findBorrowStatisticsReportAll(Map<String, Object> map) {
		return borrowStatisticsReportDao.findAll(map);

	}

    @Override
    public int activeInsertBySelective(ActiveStatisticsInfo activeStatisticsInfo)
    {
        if(activeStatisticsInfo==null){
            return 0;
        }
        ActiveStatisticsInfo res=activeStatisticsDao.selectByStatisticsDate(activeStatisticsInfo.getStatisticsDate(), activeStatisticsInfo.getActiveType());
        if(res==null){
            int num=activeStatisticsDao.activeInsertBySelective(activeStatisticsInfo);
            if(num>0){
				log.info("新增活动统计数据成功.......");
            }else{
				log.info("新增活动统计数据失败.......");
            }
            return num;
        }else{
            res.setApplyNum(activeStatisticsInfo.getApplyNum());
            res.setBorrowNum(activeStatisticsInfo.getBorrowNum());
            res.setClickNum(activeStatisticsInfo.getClickNum());
            res.setStatisticsDate(activeStatisticsInfo.getStatisticsDate());
            int num=activeStatisticsDao.activeUpdateSelective(res);
            if(num>0){
				log.info("更新活动统计数据成功.......");
            }else{
				log.info("更新活动统计数据失败.......");
            }
            return num;
        }
       
        
    }
}
