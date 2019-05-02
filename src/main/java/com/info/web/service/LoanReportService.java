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

import com.info.constant.Constant;
import com.info.web.dao.ILoanReportDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.LoanReport;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

import javax.annotation.Resource;

@Slf4j
@Service
public class LoanReportService implements ILoanReportService {
	@Resource
	private ILoanReportDao loanReportDao;
	@Autowired
	private IPaginationDao paginationDao;
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<LoanReport> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "LoanReport");
		PageConfig<LoanReport> pageConfig = new PageConfig<LoanReport>();
		DateUtil.sqlOptimization4DateFormat(params,"beginTime","endTime");
		pageConfig = paginationDao.findPage("findAll", "findAllCount", params,"web");
		return pageConfig;
	}
	@Override
	public Long findLoanMoneySum(HashMap<String, Object> params) {
		DateUtil.sqlOptimization4DateFormat(params,"beginTime","endTime");
		return loanReportDao.findAllMoneySum(params);
	}
	
	@Override
	public Integer findLoanSum(HashMap<String, Object> params) {
		DateUtil.sqlOptimization4DateFormat(params,"beginTime","endTime");
		return loanReportDao.findAllLoanSum(params);
	}
	
	@Override
	public boolean saveLoanReport(String nowTime) {
		Map<String, Object> param = new HashMap<>();
		LoanReport loanReport =new LoanReport();
		try {
			/*设置查询时间*/
			
			Calendar cal = Calendar.getInstance();
			 
			
			Integer checkDate = 0;
			String time = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
			checkDate=Integer.valueOf(time.substring(11, 13));
			 
			if(checkDate>0&&checkDate<3){
				cal.add(Calendar.DATE, -1);
			}
			String nowDate = DateUtil.getDateFormat(cal.getTime(),"yyyy-MM-dd");
			if (!StringUtils.isEmpty(nowTime)) {
				nowDate = nowTime;
			}
			//21,23,30,34,-11,-20
			Integer[] inStatus = new Integer[] { STATUS_HKZ,
					STATUS_BFHK, BorrowOrder.STATUS_YHK,
					BorrowOrder.STATUS_YQYHK, STATUS_YYQ, STATUS_YHZ };
			String startDate=nowDate;
			//String endDate = nowDate + " 23:59:59";
			
			param.put("startDate", startDate);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");
			//param.put("endDate",endDate);
			param.put("inStatus", inStatus);
			
			
			//放款单数
			Integer loanOrderCount = loanReportDao.findLoanCount(param);
			//添加续期放款单数(续期一笔计算为成功放款一笔)
			Integer loanOrderRenewalCount = loanReportDao.findRenewalLoanCount(param);
			loanReport.setLoanOrderCount(loanOrderCount+loanOrderRenewalCount);
			//放款总额
			Long moneyAmountCount = loanReportDao.findMoneyCount(param);
			//   /100
			BigDecimal a = new BigDecimal(moneyAmountCount);
			BigDecimal a1 = BigDecimal.ZERO;
			a1 = a.divide(new BigDecimal(100), 0,
						BigDecimal.ROUND_HALF_UP);
		
			//添加续期放款总额(续期一笔计算为成功放款一笔)
			Long RenewalmoneyAmountCount = loanReportDao.findRenewalMoneyCount(param);
			BigDecimal b = new BigDecimal(RenewalmoneyAmountCount);
			BigDecimal b1 = BigDecimal.ZERO;
			b1 = b.divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			
			loanReport.setMoneyAmountCount((a1.add(b1)).longValue());
			
			param.clear();
			param.put("startDate", startDate);
			//param.put("endDate",endDate);
			param.put("loanTerm", "7");
			param.put("inStatus", inStatus);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");

			//7天放款单数
			Integer loanSevendayCount = loanReportDao.findLoanCount(param);
			Integer loanSevendayRenewalCount = loanReportDao.findRenewalLoanCount(param);//添加7天续期放款单数
			loanReport.setLoanSevendayCount(loanSevendayCount+loanSevendayRenewalCount);
			//7天放款总额
			Long sevendayMoenyCount = loanReportDao.findMoneyCount(param);
			Long sevendayMoenyRenewalCount = loanReportDao.findRenewalMoneyCount(param);//添加7天续期放款总额
			
			BigDecimal seven1 = new BigDecimal(sevendayMoenyCount);
			BigDecimal seven2 = new BigDecimal(sevendayMoenyRenewalCount);
			BigDecimal seven3 = BigDecimal.ZERO;
			seven3 = (seven1.add(seven2)).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			loanReport.setSevendayMoenyCount(seven3.longValue());
			
			param.clear();
			param.put("startDate", startDate);
			//param.put("endDate",endDate);
			param.put("loanTerm", "14");
			param.put("inStatus", inStatus);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");
			//14天放款单数
			Integer loanFourdayCount = loanReportDao.findLoanCount(param);
			Integer loanFourdayRenewalCount = loanReportDao.findRenewalLoanCount(param);//添加14天续期放款单数
			loanReport.setLoanFourdayCount(loanFourdayCount + loanFourdayRenewalCount);
			//14天放款总额
			Long fourdayMoneyCount = loanReportDao.findMoneyCount(param);
			Long fourdayMoneyRenewalCount = loanReportDao.findRenewalMoneyCount(param);//添加7天续期放款总额
			
			BigDecimal four1 = new BigDecimal(fourdayMoneyCount);
			BigDecimal four2 = new BigDecimal(fourdayMoneyRenewalCount);
			BigDecimal four3 = BigDecimal.ZERO;
			four3 = (four1.add(four2)).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			loanReport.setFourdayMoneyCount(Integer.valueOf(four3.toString()));
			
			param.clear();
			param.put("startDate", startDate);
//			param.put("endDate",endDate);
			param.put("customerType", "1");
			param.put("inStatus", inStatus);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");
			//老用户放款单数
			Integer oldLoanOrderCount = loanReportDao.findLoanCount(param);
			/* 老用户续期放款单数(只要续期，都视为老用户)*/
			loanReport.setOldLoanOrderCount(oldLoanOrderCount+loanOrderRenewalCount);
			//老用户放款总额
			Long oldLoanMoneyCount = loanReportDao.findMoneyCount(param);
			/* 老用户续期放款金额(只要续期，都视为老用户)*/
			BigDecimal old1 = new BigDecimal(oldLoanMoneyCount);
		
			BigDecimal old3 = BigDecimal.ZERO;
			old3 = (old1.add(b)).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			loanReport.setOldLoanMoneyCount(old3.longValue());
			
			param.clear();
			param.put("startDate", startDate);
//			param.put("endDate",endDate);
			param.put("customerType", "0");
			param.put("inStatus", inStatus);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");
			//新用户放款单数
			Integer newLoanOrderCount = loanReportDao.findLoanCount(param);
			loanReport.setNewLoanOrderCount(newLoanOrderCount);
			
			//新用户放款总额
			Long newLoanMoneyCount = loanReportDao.findMoneyCount(param);
			
			BigDecimal new1 = new BigDecimal(newLoanMoneyCount);
			
			BigDecimal new3 = BigDecimal.ZERO;
			new3 = (new1).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_UP);
			
			loanReport.setNewLoanMoneyCount(new3.longValue());
			
			/*新加字段属性*/
			//注册人数
			param.clear();
			param.put("startDate", startDate);
			DateUtil.sqlOptimization4DateFormat(param,"startDate");
//			param.put("endDate",endDate);
			Integer registerCount = loanReportDao.findRegistCount(param);
			loanReport.setRegisterCount(registerCount);
			
			//借款人数
			Integer borrowApplyCount = loanReportDao.findBorrowApplyCount(param);
			loanReport.setBorrowApplyCount(borrowApplyCount);
			
			//借款成功人数
			
			param.put("inStatus", inStatus);
			Integer borrowSucCount = loanReportDao.findBorrowSucCount(param);
			loanReport.setBorrowSucCount(borrowSucCount);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			loanReport.setReportDate(sdf.parse(nowDate));
			//先删后保存
			loanReportDao.deleteByLoanReport(loanReport);
			 
			loanReportDao.insert(loanReport);

			
		} catch (Exception e) {
			log.error("loanReport saveReport error:{}", e);
		}
		return false;
	}

}
