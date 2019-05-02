package com.info.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.web.dao.IRenewalRecordDao;
import com.info.web.dao.IRepaymentDao;
import com.info.web.dao.IRepaymentDetailDao;
import com.info.web.dao.IRepaymentReturnDao;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.RenewalRecord;
import com.info.web.pojo.Repayment;
import com.info.web.pojo.RepaymentDetail;
import com.info.web.pojo.RepaymentReturn;
import com.info.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;


@Service
public class RepaymentReturnService implements IRepaymentReturnService{
	
	@Autowired
    private IRepaymentReturnDao repaymentReturnDao;
	
	@Autowired
	private IRepaymentDetailDao repaymentDetailDao;
	
	@Autowired
	private IRepaymentDao repaymentDao;
	
	@Autowired
	private IRenewalRecordDao renewalRecordDao;

	
	
	@Override
	public int insertSelective(RepaymentReturn record) {
		return repaymentReturnDao.insertSelective(record);
	}


	@Override
	public String returnRepayDetail(RepaymentReturn ret,BackUser backUser) {
		String errorMsg = null;
		RepaymentDetail pd = repaymentDetailDao.selectByPrimaryKey(ret.getAssetRepaymentDetailId());
		if (null == pd) {
			errorMsg = "该条数据无效！";
		} else if (RepaymentDetail.STATUS_SUC != pd.getStatus()) {
			errorMsg = "本条数据不能退款！";
		} else {
			if(StringUtils.isNotBlank(ret.getReturnTimeStr())){
				ret.setReturnTime(DateUtil.getDate(ret.getReturnTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			}
			ret.setRepaymentReturnMoney(ret.getRepaymentReturnMoney() * 100);
			ret.setAdminUsername(backUser.getUserAccount());
			ret.setUserId(pd.getUserId());
			ret.setAssetOrderId(pd.getAssetOrderId());
			ret.setAssetRepaymentId(pd.getAssetRepaymentId());
			repaymentReturnDao.insertSelective(ret);
			RepaymentDetail detail = new RepaymentDetail();
			detail.setId(ret.getAssetRepaymentDetailId());
			detail.setBackOrderId(ret.getRepaymentReturnMoney()+"");
			repaymentDetailDao.updateByPrimaryKeySelective(detail);
		}
	
		return errorMsg;
	}


	@Override
	public String returnRenewal(RepaymentReturn ret,BackUser backUser) {
		String errorMsg = null;
		RenewalRecord rr = renewalRecordDao.selectByPrimaryKey(ret.getAssetRepaymentDetailId());
		Repayment repay = repaymentDao.selectByPrimaryKey(rr.getAssetRepaymentId());
		if (null == rr) {
			errorMsg = "该条数据无效！";
		} else if (RenewalRecord.STATUS_SUCC != rr.getStatus()) {
			errorMsg = "本条数据不能退款！";
		} else {
			if(StringUtils.isNotBlank(ret.getReturnTimeStr())){
				ret.setReturnTime(DateUtil.getDate(ret.getReturnTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			}
			ret.setRepaymentReturnMoney(ret.getRepaymentReturnMoney() * 100);
			ret.setAdminUsername(backUser.getUserAccount());
			ret.setUserId(rr.getUserId());
			ret.setAssetOrderId(repay.getAssetOrderId());
			ret.setAssetRepaymentId(rr.getAssetRepaymentId());
			repaymentReturnDao.insertSelective(ret);
			RenewalRecord r = new RenewalRecord();
			r.setId(ret.getAssetRepaymentDetailId());
			r.setReturnMoney(ret.getRepaymentReturnMoney() );
			renewalRecordDao.updateByPrimaryKeySelective(r);
		}
		return errorMsg;
	}
   
}
