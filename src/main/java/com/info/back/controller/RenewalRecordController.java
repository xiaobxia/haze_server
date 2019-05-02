package com.info.back.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.utils.ExcelUtil;
import com.info.constant.Constant;
import com.info.web.controller.BaseController;
import com.info.web.pojo.RenewalRecord;
import com.info.web.service.IRenewalRecordService;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

/***
 * 续期
 * 
 * @author Administrator
 * 
 */
@Slf4j
@Controller
@RequestMapping("renewal/")
public class RenewalRecordController extends BaseController {
	@Autowired
	private IRenewalRecordService renewalRecordService;

	@RequestMapping("getRenewalPage")
	public String getRenewalPage(HttpServletRequest request, Integer[] statuses, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			if(null == statuses || statuses.length == 0 || null == statuses[0]){
				statuses = new Integer[]{RenewalRecord.STATUS_PAYING, 
						                 RenewalRecord.STATUS_SUCC, 
						                 RenewalRecord.STATUS_FAIL, 
						                 RenewalRecord.STATUS_HANDLE};
				model.addAttribute("statusesType", "ALL");
			}
			
			if(null == params.get("repaymentTime") || null == params.get("repaymentTimeEnd")){
				 params.put("repaymentTime", DateUtil.getDateFormat("yyyy-MM-dd"));
				 params.put("repaymentTimeEnd", DateUtil.getDateFormat("yyyy-MM-dd"));
				
			}
			params.put("statuses", statuses);
			PageConfig<RenewalRecord> pageConfig = renewalRecordService.findPage(params);
		    Integer renewalTotal = renewalRecordService.renewalTotal(params);
		    Long sumFeeTotal = renewalRecordService.sumFeeTotal(params);
		    Long interestTotal = renewalRecordService.renewalInterestTotal(params);
		    Long renewalFeeTotal = renewalRecordService.renewalFeeTotal(params);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("renewalTotal",renewalTotal);
			model.addAttribute("sumFeeTotal",sumFeeTotal);
			model.addAttribute("interestTotal",interestTotal);
			model.addAttribute("renewalFeeTotal",renewalFeeTotal);

		} catch (Exception e) {
			log.error("getRenewalPage error:{}", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "repayment/renewalRecodList";
	}

	@RequestMapping("reportExcel")
	public void reportExcel(HttpServletRequest request, HttpServletResponse response, Integer statuses[]) {
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			if(null == statuses || statuses.length == 0 || null == statuses[0]){
				statuses = new Integer[]{RenewalRecord.STATUS_PAYING, 
						                 RenewalRecord.STATUS_SUCC, 
						                 RenewalRecord.STATUS_FAIL, 
						                 RenewalRecord.STATUS_HANDLE};
			}
			if(null == params.get("repaymentTime") || null == params.get("repaymentTimeEnd")){
				 params.put("repaymentTime", DateUtil.getDateFormat("yyyy-MM-dd"));
				 params.put("repaymentTimeEnd", DateUtil.getDateFormat("yyyy-MM-dd"));
				
			}
			params.put("statuses", statuses);
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int size = 50000;
			params.put(Constant.PAGE_SIZE, size);
			int totalPageNum = renewalRecordService.findPageCount(params);
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
			ExcelUtil.setFileDownloadHeader(request, response, "续期列表.xls");
			response.setContentType("application/msexcel");// 定义输出类型
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

			String[] titles = { "序号", "订单号", "姓名", "手机号", "续期类型", "续期总额", "服务费", "续期费","续期期限", "续期状态", "续期到期还款时间"};
			for (int i = 1; i <= total; i++) {
				params.put(Constant.CURRENT_PAGE, i);
				PageConfig<RenewalRecord> pm = renewalRecordService.findPage(params);
				List<RenewalRecord> renewalList = pm.getItems();
				List<Object[]> contents = new ArrayList<>();
				int j = 1;
				for (RenewalRecord rr : renewalList) {
					Object[] conList = new Object[titles.length];
					conList[0] = j;
					conList[1] = rr.getOrderId();
					conList[2] = rr.getRealname();
					conList[3] = rr.getUserPhone();
					String type = "自动申请";
					if(rr.getRenewalType() != 1){
						type = "手动申请";
					}
					conList[4] = type;
					conList[5] = rr.getSumFee() / 100.00;
					conList[6] = rr.getRepaymentInterest() / 100.00;
					conList[7] = rr.getRenewalFee() / 100.00;
					conList[8] = rr.getRenewalDay();
					conList[9] = RenewalRecord.RENEWAL_STATUS.get(rr.getStatus());
					conList[10] = sfd.format(rr.getRepaymentTime());
					contents.add(conList);
					j++;
				}
				ExcelUtil.buildExcel(workbook, "续期对账列表", titles, contents, i, pm.getTotalPageNum(), os);
			}
		} catch (Exception e) {
			log.error("reportXQExcel 失败:{}", e);
		}

	}
	
}
