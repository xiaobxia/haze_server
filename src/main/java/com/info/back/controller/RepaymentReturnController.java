package com.info.back.controller;

import static com.info.web.pojo.BorrowOrder.STATUS_YHK;
import static com.info.web.pojo.BorrowOrder.STATUS_YQYHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.web.service.IRepaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.back.utils.DwzResult;
import com.info.back.utils.SpringUtils;
import com.info.web.controller.BaseController;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.RenewalRecord;
import com.info.web.pojo.RepaymentDetail;
import com.info.web.pojo.RepaymentReturn;
import com.info.web.service.IRenewalRecordService;
import com.info.web.service.IRepaymentDetailService;
import com.info.web.service.IRepaymentReturnService;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;

/***
 * 退款
 * 
 * @author Administrator
 * 
 */
@Slf4j
@Controller
@RequestMapping("return/")
public class RepaymentReturnController extends BaseController {
	@Autowired
	private IRepaymentDetailService repaymentDetailService;
	@Autowired
    private IRepaymentReturnService repaymentReturnService;

	@Autowired
	private IRenewalRecordService renewalRecordService;

	@Autowired
	private IRepaymentService repaymentService;

	@RequestMapping("getRepaymentReturnPage")
	public String getRepaymentPage(HttpServletRequest request, Integer[] statuses, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			if(null == statuses || statuses.length == 0 || null == statuses[0]){
				statuses = new Integer[] { STATUS_YHK, STATUS_YQYHK };
				model.addAttribute("statusesType", "ALL");
			}
			if(null == params.get("createdAt")){
				 params.put("createdAt", DateUtil.getDateFormat("yyyy-MM-dd"));
				 params.put("createdAtEnd", DateUtil.getDateFormat("yyyy-MM-dd"));
				
			}
			params.put("statuses", statuses);
			PageConfig<RepaymentDetail> pageConfig = repaymentDetailService.repaymentDetilList(params);
			List<RepaymentDetail> list = new ArrayList<RepaymentDetail>();
			for(RepaymentDetail repaymentDetail : pageConfig.getItems()){
				//查询用户成功借款次数
				Integer loanSucCount = repaymentService.userBorrowCount(null,repaymentDetail.getUserId());
				//该用户在还款表中无记录
				if(loanSucCount != null && loanSucCount < 1 ){
					repaymentDetail.setLoanCount("首借");
				}else{
					Integer loanCount = repaymentService.userBorrowCount(99999,repaymentDetail.getUserId());
					//该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
					if(loanCount < 1){
						repaymentDetail.setLoanCount("首借");
					}else{
						repaymentDetail.setLoanCount(loanCount.toString());
					}
				}
				list.add(repaymentDetail);
			}
			pageConfig.setItems(list);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);

		} catch (Exception e) {
			log.error("repaymentReturnPage error:{}", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "repayment/repaymentReturnList";
	}
	
	@RequestMapping("getRenewalReturnPage")
	public String getRenewalReturnPage(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		try {
			if(null == params.get("orderTime")){
				 params.put("orderTime", DateUtil.getDateFormat("yyyy-MM-dd"));
				 params.put("orderTimeEnd", DateUtil.getDateFormat("yyyy-MM-dd"));
				
			}
			PageConfig<RenewalRecord> pageConfig = renewalRecordService.renewalList(params);
			List<RenewalRecord> list= new ArrayList<RenewalRecord>();
			for(RenewalRecord renewalRecord : pageConfig.getItems()){
				//查询用户成功借款次数
				Integer loanSucCount = repaymentService.userBorrowCount(null,renewalRecord.getUserId());
				//该用户在还款表中无记录
				if(loanSucCount != null && loanSucCount < 1 ){
					renewalRecord.setLoanCount("首借");
				}else{
					Integer loanCount = repaymentService.userBorrowCount(99999,renewalRecord.getUserId());
					//该用户在还款表中 没有已还款的记录 但是在还款表中有且仅有一条数据
					if(loanCount < 1){
						renewalRecord.setLoanCount("首借");
					}else{
						renewalRecord.setLoanCount(loanCount.toString());
					}
				}
				list.add(renewalRecord);
			}
			pageConfig.setItems(list);
			model.addAttribute("pm", pageConfig);
			model.addAttribute("params", params);

		} catch (Exception e) {
			log.error("renewalReturnPage error:{}", e);
		}
		model.addAttribute("params", params);// 用于搜索框保留值
		return "repayment/renewalReturnList";
	}
	@RequestMapping("returnRepay")
	public String returnRepay(HttpServletRequest request, HttpServletResponse response, Model model, RepaymentReturn ret) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		BackUser backUser = this.loginAdminUser(request);
		try {
			if ("toJsp".equals(String.valueOf(params.get("type")))) {
				if (null != ret.getAssetRepaymentDetailId()) {
					double returnMoney;
					Integer detailId;
					if(params.get("rs").equals("1")){
						// 更新的页面跳转
						RepaymentDetail details = repaymentDetailService.selectByPrimaryKey(ret.getAssetRepaymentDetailId());
						returnMoney =  details.getTrueRepaymentMoney() / 100.00;
						detailId = ret.getAssetRepaymentDetailId();
					}else{
						RenewalRecord rr = renewalRecordService.selectByPrimaryKey(ret.getAssetRepaymentDetailId());
						returnMoney =  rr.getSumFee() / 100.00;
						detailId = rr.getId();
					}
					model.addAttribute("returnMoney",returnMoney);
					model.addAttribute("detailId", detailId);
					model.addAttribute("rs", params.get("rs"));
				}
				url = "repayment/return_repay";
			} else {
				if (null == ret.getAssetRepaymentDetailId()) {
					erroMsg = "请选择一条需退款的数据！";
				} else {
					if(ret.getReturnSource() == 1){
						erroMsg = repaymentReturnService.returnRepayDetail(ret, backUser);
					}else{
						erroMsg = repaymentReturnService.returnRenewal(ret, backUser);
					}
				}
				SpringUtils.renderDwzResult(response, null == erroMsg, null == erroMsg ? "操作成功！" : erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
			}
		} catch (Exception e) {
			erroMsg = "服务器异常，请稍后重试！";
			SpringUtils.renderDwzResult(response, false, "操作失败,原因：" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId").toString());
			log.error("returnRepay error:{}", e);
		}
		model.addAttribute(MESSAGE, erroMsg);
		model.addAttribute("params", params);
		return url;
	}
	
}
