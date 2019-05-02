package com.info.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.ServiceResult;
import com.info.web.dao.IBorrowOrderDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.OutOrders;

/**
 * 
 * @author Administrator
 * 
 */
@Slf4j
@Service
public class ThreeLoanOrderService implements IThreeLoanOrderService {

	@Autowired
	private IBorrowOrderDao borrowOrderDao;
	@Autowired
	private IBorrowOrderService borrowOrderService;

	@Override
	public ServiceResult updateThreeLoanTerm(HashMap<String, Object> mapParam) {

		log.info("begin   updateThreeLoanTerm  ======");
		// 非招行打款
		HashMap<String, Object> paramsM = new HashMap<String, Object>();
		paramsM.put("borrowStatus", BorrowOrder.STATUS_FKZ);
		paramsM.put("payStatus", BorrowOrder.SUB_PAY_CSZT);
		paramsM.put("queryType", "subPay");

		try {
			paramsM.put("bankIscmb", 2);
			paramsM.put("querylimit", 28);
			List<BorrowOrder> bos = borrowOrderDao.findParamsTLO(paramsM);
			if (bos != null && bos.size() > 0) {

				log.info("begin   Loan  ======:{}", OutOrders.act_NTQRYEBP_H);

				requestPaysNotCmbUserThree(bos);

				log.info("end   Loan  ======:{}",OutOrders.act_NTQRYEBP_H);
			}
		} catch (Exception e) {
			log.error("error   Loan  ======:{}",e);
		}
		try {
			// 招行打款
			paramsM.put("bankIscmb", 1);
			paramsM.put("querylimit", 1);
			// 招行同行打款一次只能打一笔，循环打10次。（每次打10笔）
//			for (int i = 0; i < 10; i++) {
				List<BorrowOrder> bocs = borrowOrderDao.findParamsTLO(paramsM);
				if (bocs != null && bocs.size() > 0) {
					log.info("begin   Loan  ======:{}" ,OutOrders.act_AgentRequest_H);

					requestPaysCmbUserThree(bocs);
					log.info("end   Loan  ======:{}",OutOrders.act_AgentRequest_H);
				}
	
//			}
			// Thread.sleep(1000);
			// 查询招行打款状态
			// queryPaysStateCmb();
		} catch (Exception e) {
			log.error("error   Loan  ======:{}",e);
		}

		log.error("end   updateThreeLoanTerm  ======");

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void requestPaysNotCmbUserThree(List<BorrowOrder> borrowOrders) {
		try {
			ServiceResult serviceResult = borrowOrderService.subPayNotCmb(borrowOrders, OutOrders.act_NTQRYEBP_H);
			String paystatusAll = serviceResult.getCode();
			String payRemarkAll = serviceResult.getMsg();
			// 更新借款表
			Date nowDate = new Date();
			if (paystatusAll.equals("5000")) {// 返回每个订单的结果
				List<ServiceResult> resultS = (List<ServiceResult>) serviceResult.getExt();
				for (BorrowOrder bo : borrowOrders) {
					BorrowOrder borrowOrderNew = new BorrowOrder();
					borrowOrderNew.setId(bo.getId());
					borrowOrderNew.setUpdatedAt(nowDate);
					if (bo.getSerialNo() != null) {
						for (ServiceResult result : resultS) {
							String paystatus = result.getCode();
							String payRemark = result.getMsg();
							String serialNo = result.getExt().toString();
							if (bo.getSerialNo().equals(serialNo)) {
								borrowOrderNew.setPaystatus(paystatus);
								borrowOrderNew.setPayRemark(payRemark);
								// resultS.remove(result);
							}
						}
					} else {
						borrowOrderNew.setPaystatus(paystatusAll);
						borrowOrderNew.setPayRemark(payRemarkAll);
					}
					borrowOrderDao.updateByPrimaryKeySelectiveTLO(borrowOrderNew);
				}

			} else {

				for (BorrowOrder bo : borrowOrders) {
					BorrowOrder borrowOrderNew = new BorrowOrder();
					borrowOrderNew.setId(bo.getId());
					// 网络失败不改变付款状态，自动重新打款
					if (!paystatusAll.equals(BorrowOrder.SUB_NERWORK_ERROR)) {
						borrowOrderNew.setPaystatus(paystatusAll);
					}
					borrowOrderNew.setPayRemark(payRemarkAll);
					borrowOrderNew.setUpdatedAt(nowDate);
					borrowOrderDao.updateByPrimaryKeySelectiveTLO(borrowOrderNew);
				}
			}

		} catch (Exception e) {
			log.error("requestPaysNotCmbUserThree  Loan  error......:{}", e);
		}

	}

	@Override
	public void requestPaysCmbUserThree(List<BorrowOrder> borrowOrders) {
		try {
			ServiceResult serviceResult = borrowOrderService.subPayCmb(borrowOrders, OutOrders.act_AgentRequest_H);
			String paystatus = serviceResult.getCode();
			String payRemark = serviceResult.getMsg();
			// 更新借款表
			Date fkDate = new Date();

			for (BorrowOrder bo : borrowOrders) {
				BorrowOrder borrowOrderNew = new BorrowOrder();
				borrowOrderNew.setId(bo.getId());
				// 网络失败不改变付款状态，自动重新打款
				if (!paystatus.equals(BorrowOrder.SUB_NERWORK_ERROR)) {
					borrowOrderNew.setPaystatus(paystatus);
				}
				borrowOrderNew.setPayRemark(payRemark);
				borrowOrderNew.setUpdatedAt(fkDate);
				borrowOrderDao.updateByPrimaryKeySelectiveTLO(borrowOrderNew);
			}

		} catch (Exception e) {
			log.error("requestPay   Loan  error......:{}",OutOrders.act_AgentRequest_H);
		}

	}
}
