package com.info.back.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.service.IBackStatisticService;
import com.info.back.utils.ExcelUtil;
import com.info.back.utils.SpringUtils;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.pojo.PlatformReport;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("summary/")
public class SummaryController extends BaseController {
	@Autowired
	private IBackStatisticService backStatisticService;

	@RequestMapping("summaryUser")
	public void summaryUser(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Long> params = null;
		try {
			String type = request.getParameter("type");
			if ("regUser".equals(type)) {
				params = backStatisticService.registerStatistic();
			} else if ("approve".equals(type)) {
				params = backStatisticService.findAllApprove(request.getParameter("secondType"));
			}
		} catch (Exception e) {
			log.error("summaryUser error:{}", e);
		}
		SpringUtils.renderJson(response, params);
	}

	@RequestMapping("summaryBorrow")
	public void summaryBorrow(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = null;
		try {
			String type = request.getParameter("type");
			if ("borrow".equals(type)) {
				params = backStatisticService.sendMoneyStatistic();
			} else if ("loan".equals(type)) {
				params = backStatisticService.findLaon();
			} else if ("risk".equals(type)) {
				params = backStatisticService.findRiskOrders();
			} else if ("riskUser".equals(type)) {
				params = backStatisticService.findRiskUser();
			}else if ("riskToday".equals(type)) {
				params = backStatisticService.findRiskOrdersToday();
			}else if ("old".equals(type)) {
				String customerType = request.getParameter("customerType");
				params = backStatisticService.findOldToday(customerType);
			}
		} catch (Exception e) {
			log.error("summaryUser error:{}", e);
		}
		SpringUtils.renderJson(response, params);
	}
	
	

	@RequestMapping("getPlatformPage")
	public String getPlatformPage(HttpServletRequest request,Model model) {

		try {
			HashMap<String, Object> params = getParametersO(request);
			PageConfig<PlatformReport> pageConfig = backStatisticService.findPlatformPage(params);
			model.addAttribute("pm", pageConfig);
//			model.addAttribute("allstatus", BorrowOrderChecking.allstatus);
//			model.addAttribute("capitalTypeMap", BorrowOrderChecking.capitalTypeMap);
			
			if(params.get("beginTime")!=null){
				
				Date  beginTime = DateUtil.getDate(params.get("beginTime").toString(), "yyyy-MM-dd");
				params.put("beginTime",beginTime);
			}
			if(params.get("endTime")!=null){
				Date  endTime = DateUtil.getDate(params.get("endTime").toString(), "yyyy-MM-dd");
				params.put("endTime",endTime);
			}
			model.addAttribute("params", params);// 用于搜索框保留值
		} catch (Exception e) {
			log.error("getBorrowOrderCheckPage error:{}", e);
		}
		return "borrow/platformPage";
	}
	
	/**
	 * 导出平台数据Excel
	 */
	@RequestMapping("toPlatformExcel")
	public void toPlatformExcel(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			int size = 50000;
			params.put(Constant.PAGE_SIZE, size);
			int totalPageNum = backStatisticService.findPlatformCount(params);
			int total = 0;
			if (totalPageNum > 0) {
				if (totalPageNum % size > 0) {
					total = totalPageNum / size + 1;
				} else {
					total = totalPageNum / size;
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			ExcelUtil.setFileDownloadHeader(request, response, "平台数据.xls");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			String[] titles = { "日期", "注册人数", "实名认证人数", "实名认证次数", "实名费用", "运营商认证人数", "成功生成报告人数","运营商费用", "绑卡人数",
					"完成芝麻认证人数", "芝麻认证费用","认证工作信息人数","支付宝认证人数","借款申请总数","通过审核的总数","订单通过率","应放款总额","放款成功总额","放款成功笔数",
					"未到账金额","未到账笔数","打款失败总订单数","等待放款金额","等待放款笔数","申请用户总数","审核通过用户总数","用户通过率","命中禁止项人数","命中反欺诈人数",
					"准入原则被拒人数","风控审核订单","风控审核通过订单","风控拒绝订单","风控推到复审订单总数","风控征信失败订单数","风控征信本地异常订单数","规则判定复审订单数",
					"添加时间","是否有效"};
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<PlatformReport> pm = backStatisticService.findPlatformPage(params);
				List<PlatformReport> borrowList = pm.getItems();
				List<Object[]> contents = new ArrayList<>();
				for (PlatformReport pr : borrowList) {
					Object[] conList = new Object[titles.length];
					conList[0] = pr.getReportDate();
					conList[1] = pr.getRegisterNum();
					conList[2] = pr.getRealPersonNum();
					conList[3] = pr.getRealNum();
					conList[4] = pr.getRealMoney();
					conList[5] = pr.getYysNum();
					conList[6] = pr.getYysSucNum();
					conList[7] = pr.getYysMoney();
					conList[8] = pr.getBankNum();
					conList[9] = pr.getZmNum();
					conList[10] = pr.getZmMoney();
					conList[11] = pr.getWorkNum();
					conList[12] = pr.getAlipayNum();
					conList[13] = pr.getBorrowNum();
					conList[14] = pr.getBorrowSucNum();
					conList[15] = pr.getBorrowRate();
					conList[16] = pr.getShouldPayMoney();
					conList[17] = pr.getSucPayMoney();
					conList[18] = pr.getSucPayNum();
					conList[19] = pr.getNoPayMoney();
					conList[20] = pr.getNoPayNum();
					conList[21] = pr.getFailPayMoney();
					conList[22] = pr.getFailPayNum();
					conList[23] = pr.getWaitPayMoney();
					conList[24] = pr.getWaitPayNum();
					conList[25] = pr.getBorrowUserNum();
					conList[26] = pr.getBorrowUserSucNum();
					conList[27] = pr.getBorrowUserRate();
					conList[28] = pr.getStopNum();
					conList[29] = pr.getFqz();
					conList[30] = pr.getZr();
					conList[31] = pr.getRiskNum();
					conList[32] = pr.getRiskSucNum();
					conList[33] = pr.getRiskFailNum();
					conList[34] = pr.getRiskToReview();
					conList[35] = pr.getRiskToReviewZxErr();
					conList[36] = pr.getRiskToReviewErr();
					conList[37] = pr.getRiksToReviewReal();
					conList[38] = pr.getAddTime();
					//conList[39] = pr.getStatus();
				 
					contents.add(conList);
				}
				ExcelUtil.buildExcel(workbook, "信息列表", titles, contents, i, pm.getTotalPageNum(), os);
			}
		} catch (Exception e) {
			log.error("导出excel失败:{}", e);
		}

	}
}
