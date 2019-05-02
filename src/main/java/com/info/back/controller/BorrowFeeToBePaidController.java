package com.info.back.controller;

import java.util.Date;
import java.util.HashMap;

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
import com.info.web.dao.IBorrowOrderLoanDao;
import com.info.web.dao.IBorrowOrderLoanPersonDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.BorrowOrderLoan;
import com.info.web.pojo.BorrowOrderLoanPerson;
import com.info.web.service.IBorrowOrderService;
import com.info.web.util.DateUtil;
import com.info.web.util.FastJsonUtils;
import com.info.web.util.PageConfig;

@Slf4j
@Controller
@RequestMapping("borrowFeeToBePaid/")
public class BorrowFeeToBePaidController extends BaseController {

	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IBorrowOrderLoanPersonDao borrowOrderLoanPersonDao;
	@Autowired
	private IBorrowOrderLoanDao borrowOrderLoanDao;
	
	@RequestMapping("saveUpdateBorrowFeePersonView")
	public String saveUpdateBorrowFeePersonView(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		params.put("LOAN_ACCOUNTMap", BorrowOrder.LOAN_ACCOUNTMap);
		model.addAttribute("params", params);
		return "borrow/borrowFeeToBePaidConfirmC";
	}

	@RequestMapping("saveUpdateBorrowFeeCompanyView")
	public String saveUpdateBorrowFeeCompanyView(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		params.put("LOAN_ACCOUNTMap", BorrowOrder.LOAN_ACCOUNTMap);
		model.addAttribute("params", params);
		return "borrow/borrowFeeToBePaidConfirmB";
	}

	
	@RequestMapping("getBorrowFreeToBePaidCPage")
	public String getBorrowFreeToBePaidCPage(HttpServletRequest request,Model model) {
		try {

			HashMap<String, Object> params = getParametersO(request);
			params.put("status","1");
			PageConfig<BorrowOrderLoanPerson> pageConfig = borrowOrderService.findBorrowFreeCPage(params);
			Long moneyAmountSum = borrowOrderService.findloanInterestsSucSumC(params);
			if(moneyAmountSum==null){
				moneyAmountSum=0L;
			}
			model.addAttribute("pm", pageConfig);
			model.addAttribute("allstatus", BorrowOrderLoan.allstatus);
			model.addAttribute("params", params);// 用于搜索框保留值
			model.addAttribute("moneyAmountSum", moneyAmountSum);//打款总金额
		} catch (Exception e) {
			log.error("getBorrowFreeCPage error:{}", e);
		}
		return "borrow/borrowFeeToBePaid_person";
	}


	@RequestMapping("getBorrowFreeToBePaidBPage")
	public String getBorrowFreeToBePaidBPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			HashMap<String, Object> params = getParametersO(request);
			params.put("status","1");
			PageConfig<BorrowOrderLoan> pageConfig = borrowOrderService.findBorrowFreeBPage(params);
			Long moneyAmountSum = borrowOrderService.findloanInterestsSucSumB(params);
			if(moneyAmountSum==null){
				moneyAmountSum=0L;
			}
			model.addAttribute("pm", pageConfig);
			model.addAttribute("allstatus", BorrowOrderLoan.allstatus);
			model.addAttribute("params", params);// 用于搜索框保留值
			model.addAttribute("moneyAmountSum", moneyAmountSum);//打款总金额
		} catch (Exception e) {
			log.error("getBorrowFreeCPage error:{}", e);
		}
		return "borrow/borrowFeeToBePaid_company";
	}
	
	@RequestMapping("saveUpdateBorrowFeePerson")
	public String saveUpdateBorrowFeePerson(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String msg = null;
		 try {
		      if ("toJsp".equals(String.valueOf(params.get("type")))) {
			        params.put("LOAN_ACCOUNTMap", BorrowOrder.LOAN_ACCOUNTMap);
			        url = "borrow/borrowFeeToBePaidConfirmC";
			      } else {
			        params.put("payStatus", "0000");
			        params.put("statusCondition", BorrowOrderLoan.DFK);
			        params.put("updatedAt", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			        int record = this.borrowOrderLoanPersonDao.updateByParams(params);
			        if (record > 0)
			          msg = "操作成功";
			        else {
			          msg = "根据条件查无数据更新";
			        }
			        log.info( "{}update to C:{}",FastJsonUtils.toJson(params).toString(),record);
			        SpringUtils.renderDwzResult(response, true, msg, 
			          "closeCurrent", params.get("parentId")
			          .toString());
			      }
		 } catch (Exception e) {
			msg = "服务器异常，请稍后重试！";
				SpringUtils.renderDwzResult(response, false, "操作失败,原因："
						+ msg, DwzResult.CALLBACK_CLOSECURRENT,
						params.get("parentId").toString());
			log.error("saveUpdateBorrowFeePerson信息失败，异常信息:", e);
		}	
		
		model.addAttribute("params", params);
		return url;
	}

	@RequestMapping("saveUpdateBorrowFeeCompany")
	public String saveUpdateBorrowFeeCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
	    String msg = null;
	    try {
	      if ("toJsp".equals(String.valueOf(params.get("type")))) {
	        params.put("LOAN_ACCOUNTMap", BorrowOrder.LOAN_ACCOUNTMap);
	        url = "borrow/borrowFeeToBePaidConfirmB";
	      } else {
	        params.put("payStatus", "0000");
	        params.put("statusCondition", BorrowOrderLoan.DFK);
	        params.put("updatedAt", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
	        int record = this.borrowOrderLoanDao.updateByParams(params);
	        if (record > 0)
	          msg = "操作成功";
	        else {
	          msg = "根据条件查无数据更新";
	        }
	        log.info("{} update tp B:{}", FastJsonUtils.toJson(params).toString(),record);
	        SpringUtils.renderDwzResult(response, true, msg, 
	          "closeCurrent", params.get("parentId")
	          .toString());
	      }
	    }
	    catch (Exception e) {
	      msg = "服务器异常，请稍后重试！";
	      SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + 
	        msg, "closeCurrent", 
	        params.get("parentId").toString());
	      log.error("saveUpdateBorrowFeeCompany error:{}", e);
	    }
	    model.addAttribute("params", params);
	    return url;
    }
}
