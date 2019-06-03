package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.info.web.dao.IOutOrdersDao;
import com.info.web.pojo.*;
import com.info.web.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IRenewalRecordDao;
import com.info.web.util.PageConfig;

import javax.annotation.Resource;


@Service
public  class RenewalRecordService implements IRenewalRecordService {

	@Resource
	private IRenewalRecordDao renewalRecordDao;
    @Autowired
	private IPaginationDao paginationDao;
    @Autowired
	private IOutOrdersDao outOrdersDao;
    @Autowired
	private IRepaymentService repaymentService;

	@Override
	public RenewalRecord selectByPrimaryKey(Integer id) {
		return renewalRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		 return renewalRecordDao.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public boolean insert(RenewalRecord repayment) {
		return renewalRecordDao.insert(repayment) > 0;
	}

	@Override
	public boolean insertSelective(RenewalRecord repayment) {
		return renewalRecordDao.insertSelective(repayment) > 0;
	}

	@Override
	public boolean updateByPrimaryKey(RenewalRecord repayment) {
		return renewalRecordDao.updateByPrimaryKey(repayment) > 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(RenewalRecord repayment) {
		return renewalRecordDao.updateByPrimaryKeySelective(repayment) > 0;
	}


	@Override
	public List<RenewalRecord> findParams(Map<String, Object> params) {
		return renewalRecordDao.findParams(params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<RenewalRecord> renewalList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RenewalRecord");
		DateUtil.sqlOptimization4DateFormat(params,"orderTime","orderTimeEnd");
		return paginationDao.findPage("renewalList", "renewalCount", params, "web");
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<RenewalRecord> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RenewalRecord");
		//DateUtil.sqlOptimization4DateFormat(params,"repaymentTime","repaymentTimeEnd");
		return paginationDao.findPage("findPage", "findPageCount", params, "web");
	}

	@Override
	public Integer renewalTotal(HashMap<String, Object> map) {
		//DateUtil.sqlOptimization4DateFormat(map,"repaymentTime","repaymentTimeEnd");
		return renewalRecordDao.renewalTotal(map);
	}

	@Override
	public Long sumFeeTotal(HashMap<String, Object> map) {
		//DateUtil.sqlOptimization4DateFormat(map,"repaymentTime","repaymentTimeEnd");
		return renewalRecordDao.sumFeeTotal(map);
	}

	@Override
	public Long renewalInterestTotal(HashMap<String, Object> map) {
		//DateUtil.sqlOptimization4DateFormat(map,"repaymentTime","repaymentTimeEnd");
		return renewalRecordDao.renewalInterestTotal(map);
	}

	@Override
	public Long renewalFeeTotal(HashMap<String, Object> map) {
		//DateUtil.sqlOptimization4DateFormat(map,"repaymentTime","repaymentTimeEnd");
		return renewalRecordDao.renewalFeeTotal(map);
	}

	@Override
	public Integer findPageCount(HashMap<String, Object> map) {
		return renewalRecordDao.findPageCount(map);
	}
	@Override
	public List<RenewalRecord> queryRenewalOrder(){
		return renewalRecordDao.queryRenewalOrder();
	}


	public void setQueryOrderRB(OutOrders oo, String reqParam, RenewalRecord rr, Map map) {
		String string = map.get("batch_content").toString();
		String[] strings = string.split(",");
		String result_msg = strings[12];

		//状态是I FF 不作处理
		if (!result_msg.equals("处理中")) {
			String resultPay = "";
			oo.setReturnParams(reqParam);
			oo.setOrderNo(rr.getOrderId());

			if (result_msg.equals("成功")) {   // 支付成功
				oo.setStatus(OutOrders.STATUS_SUC);
				repaymentService.renewal(null, rr);
			} else if (result_msg.equals("失败")) {   //支付失败
				oo.setStatus(OutOrders.STATUS_OTHER);
			}
		}
		outOrdersDao.updateByOrderNo(oo);
	}

}
