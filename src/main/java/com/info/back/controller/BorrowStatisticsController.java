package com.info.back.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.BorrowStatisticsReport;
import com.info.web.service.IBorrowStatisticsReportService;
import com.info.web.util.DateUtil;

@Slf4j
@Controller
@RequestMapping("borrowStatistics/")
public class BorrowStatisticsController  extends BaseController {
	
	@Autowired
	IBorrowStatisticsReportService borrowStatisticsReportService;
	
	@RequestMapping("getBorrowStatisticsPage")
	public String getLoanReportPage(HttpServletRequest request, Model model) {
		try {
			HashMap<String, Object> params = getParametersO(request);
			String beginTime = request.getParameter("beginTime");
			
			if(!StringUtils.isEmpty(beginTime)){
				params.put("beginTime", beginTime);
			}else{
//				Calendar cal = Calendar.getInstance();
				String nowDate = DateUtil
						.getDateFormat(new Date(), "yyyy-MM-dd");
				params.put("beginTime",nowDate );
				params.put("endTime",nowDate );
				
				//borrowStatisticsReportService.createborrowLateReportDateByDate("2017-02-07");
			}
			
			List<BorrowStatisticsReport> list = borrowStatisticsReportService.findBorrowStatisticsReportAll(params);
//			PageConfig<LoanReport> pageConfig = loanReportService.findPage(params);
			//model.addAttribute("pm", pageConfig);
			model.addAttribute("list", list);
			model.addAttribute("params", params);// 用于搜索框保留值

		} catch (Exception e) {
			log.error("getloanReportPage error:{}", e);
		}
		return "userInfo/borrowStatisticsPage";
	}

	
	@RequestMapping("toAgainBorrowStaticsReport")
	public String toAgainBorrowStaticsReport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		
		String url = null;
		try {
			if("toJsp".equals(String.valueOf(params.get("type")))){
			 
				url= "borrow/againBorrowStaticsReport";
			}else{
				if(params.get("nowTime")!=null){
					 borrowStatisticsReportService.createborrowStaticReportDateByDate(String.valueOf(params.get("nowTime")));
					 borrowStatisticsReportService.createborrowLateReportDateByDate(String.valueOf(params.get("nowTime")));
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
//	 * 回算复借报表
//	 * @param request req
//	 * @param response res
//	 */
//	@RequestMapping("calBorrowStaticReport")
//	public void calBorrowStaticReport(HttpServletRequest request,
//			HttpServletResponse response) {
//		HashMap<String, Object> params = this.getParametersO(request);
//		boolean flag = false;
//		try {
//
//			flag=true;
//		} catch (Exception e) {
//			log.error("calBorrowStaticReport error:{}", e);
//		}
//		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功，如要继续操作，请五分钟后再进行！" : "操作出现异常，请查询日志！", DwzResult.CALLBACK_CLOSECURRENTDIALOG,
//				params.get("parentId").toString());
//	}

}
