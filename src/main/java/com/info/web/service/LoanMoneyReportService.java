package com.info.web.service;

import static com.info.web.pojo.BorrowOrder.STATUS_BFHK;
import static com.info.web.pojo.BorrowOrder.STATUS_HKZ;
import static com.info.web.pojo.BorrowOrder.STATUS_YHZ;
import static com.info.web.pojo.BorrowOrder.STATUS_YYQ;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.ILoanMoneyReportDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.LoanMoneyReport;
import com.info.web.util.DateUtil;
@Slf4j
@Service
public class LoanMoneyReportService implements ILoanMoneyReportService {
	@Autowired
	private ILoanMoneyReportDao loanMoneyReportDao;
	 
	 
	 
	@Override
	public boolean saveLoanMoneyReport(String nowTime) {
		boolean bool = false;
		Map<String, Object> param = new HashMap<String, Object>();
		LoanMoneyReport loanMoneyReport =new LoanMoneyReport();
		try {
			/*设置查询时间*/
			
			Calendar cal = Calendar.getInstance();
			Integer checkDate = 0;
			String time = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
			checkDate=Integer.valueOf(time.substring(11, 13));
			 
//			if(checkDate>0&&checkDate<3){
//				cal.add(Calendar.DATE, -1);
//			}
			String nowDate = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd");
			if (!StringUtils.isEmpty(nowTime)) {
				nowDate = nowTime;
			}
			Integer[] inStatus = new Integer[] { STATUS_HKZ,
					STATUS_BFHK, BorrowOrder.STATUS_YHK,
					BorrowOrder.STATUS_YQYHK, STATUS_YYQ, STATUS_YHZ };
			String startDate=nowDate;
			
			param.put("startDate", startDate);
			param.put("inStatus", inStatus);
			
			 
			//放款总额
			Long moneyAmountCount = loanMoneyReportDao.findMoneyCount(param);
			//   /100
			BigDecimal a = new BigDecimal(moneyAmountCount);
			BigDecimal a1 = BigDecimal.ZERO;
			a1 = a.divide(new BigDecimal(100), 0,
						BigDecimal.ROUND_HALF_UP);
		
			//添加续期放款总额(续期一笔计算为成功放款一笔)
			Long RenewalmoneyAmountCount = loanMoneyReportDao.findRenewalMoneyCount(param);
			BigDecimal b = new BigDecimal(RenewalmoneyAmountCount);
			BigDecimal b1 = BigDecimal.ZERO;
			b1 = b.divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			
			loanMoneyReport.setMoneyAmountCount((a1.add(b1)).longValue());
			
			param.clear();
			param.put("startDate", startDate);
			//param.put("endDate",endDate);
			param.put("loanTerm", "7");
			param.put("inStatus", inStatus);
			 
			//7天放款总额
			Long sevendayMoenyCount = loanMoneyReportDao.findMoneyCount(param);
			Long sevendayMoenyRenewalCount = loanMoneyReportDao.findRenewalMoneyCount(param);//添加7天续期放款总额
			
			BigDecimal seven1 = new BigDecimal(sevendayMoenyCount);
			BigDecimal seven2 = new BigDecimal(sevendayMoenyRenewalCount);
			BigDecimal seven3 = BigDecimal.ZERO;
			seven3 = (seven1.add(seven2)).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			loanMoneyReport.setSevendayMoenyCount(seven3.longValue());
			
			param.clear();
			param.put("startDate", startDate);
			//param.put("endDate",endDate);
			param.put("loanTerm", "14");
			param.put("inStatus", inStatus);
			 
			//14天放款总额
			Long fourdayMoneyCount = loanMoneyReportDao.findMoneyCount(param);
			Long fourdayMoneyRenewalCount = loanMoneyReportDao.findRenewalMoneyCount(param);//添加7天续期放款总额
			
			BigDecimal four1 = new BigDecimal(fourdayMoneyCount);
			BigDecimal four2 = new BigDecimal(fourdayMoneyRenewalCount);
			BigDecimal four3 = BigDecimal.ZERO;
			four3 = (four1.add(four2)).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			loanMoneyReport.setFourteendayMoneyCount(four3.longValue());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			loanMoneyReport.setReportDate(sdf.parse(nowDate));
			
			param.clear();
			param.put("startDate", startDate);
			//param.put("endDate",endDate);
			Long expireAmount = loanMoneyReportDao.findExpireAmount(param);
			loanMoneyReport.setExpireAmount(expireAmount/100);
			
			param.clear();
			param.put("startDate", startDate);
			Long overdueAmount = loanMoneyReportDao.findOverdueRateAmount(param);
			loanMoneyReport.setOverdueAmount(overdueAmount/100);
			
			
			param.clear();
			param.put("startDate", startDate);
			param.put("loanTerm", "7");
			Long overdueRateSevenAmount =loanMoneyReportDao.findOverdueRateAmount(param);
			loanMoneyReport.setOverdueRateSevenAmount(overdueRateSevenAmount/100);
			
			param.clear();
			param.put("startDate", startDate);
			param.put("loanTerm", "14");
			Long overdueRateFourteenAmount =loanMoneyReportDao.findOverdueRateAmount(param);
			loanMoneyReport.setOverdueRateFourteenAmount(overdueRateFourteenAmount/100);
			
			//先删后保存
			loanMoneyReportDao.deleteByLoanReport(loanMoneyReport);
			 
			loanMoneyReportDao.insert(loanMoneyReport);

			
		} catch (Exception e) {
			log.error("loanReport saveReport error:{}", e);
		}
		return false;
	}

	 
}
