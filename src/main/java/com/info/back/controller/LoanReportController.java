package com.info.back.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.LoanReport;
import com.info.web.service.ILoanReportService;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("loanreport/")
public class LoanReportController  extends BaseController {
	
	@Autowired
	private ILoanReportService loanReportService;
	
	@RequestMapping("getLoanReportPage")
	public String getLoanReportPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			PageConfig<LoanReport> pageConfig = loanReportService.findPage(params);
			Long moneyCount = loanReportService.findLoanMoneySum(params);
			Integer loanCount = loanReportService.findLoanSum(params);
			model.addAttribute("moneyCount", moneyCount);
			model.addAttribute("loanCount", loanCount);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);// 用于搜索框保留值
//			String nowTime=null;
//			loanReportService.saveLoanReport(nowTime);

		} catch (Exception e) {
			log.error("getloanReportPage error:{}", e);
		}
		return "userInfo/loanReportPage";
	}
	
	@RequestMapping("returnLoanReport")
	public void returnLoanReport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Boolean bool= false;
		try {
			Calendar cal = Calendar.getInstance();
			String nowDate = DateUtil
					.getDateFormat(cal.getTime(), "yyyy-MM-dd");
 			loanReportService.saveLoanReport(nowDate);
 			bool = true;
		} catch (Exception e) {
			bool = false;
			log.error("getUserPage error:{}", e);
		}
		SpringUtils.renderDwzResult(response, bool, bool ? "操作成功" : "操作失败", DwzResult.CALLBACK_RELOADPAGE);
	}
	
	
	@RequestMapping("toAgainLoanReport")
	public String toAgainChannelReport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params =this.getParameters(request);
		model.addAttribute("params", params);
		String url=null;
		try {
			if("toJsp".equals(String.valueOf(params.get("type")))){
				url="userInfo/againLoanReport";
			}else{
				 if(params.get("nowTime")!=null){
					 loanReportService.saveLoanReport(String.valueOf(params.get("nowTime")));
				 }
				 SpringUtils.renderDwzResult(response, true,"操作成功，如要继续操作，请五分钟后再进行！", DwzResult.CALLBACK_CLOSECURRENT,params.get("parentId")
							.toString());
			}
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,"操作出现异常，请查询日志！", DwzResult.CALLBACK_CLOSECURRENT);
			log.error("getUserPage error:{}", e);
		}
		model.addAttribute("params", params);
		return url;
	}
//	/**
//	 * 重新计算统计
//	 * 
//	 * @param nowTime(年月日xxxx-xx-xx)
//	 * @param request
//	 */
//	
//	@RequestMapping("againLoanReport")
//	public void againLoanReport(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		HashMap<String, Object> params = this.getParametersO(request);
//		try {
//			 if(params.get("nowTime")!=null){
//				 loanReportService.saveLoanReport(String.valueOf(params.get("nowTime")));
//			 }
//		} catch (Exception e) {
//			SpringUtils.renderDwzResult(response, false,"服务器异常，请稍后重试！", DwzResult.CALLBACK_CLOSECURRENT);
//			logger.error("getUserPage error", e);
//		}		
//		SpringUtils.renderDwzResult(response, true,"操作成功", DwzResult.CALLBACK_CLOSECURRENT,params.get("parentId")
//				.toString());
//	}
}
