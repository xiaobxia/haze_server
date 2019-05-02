package com.info.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.constant.Constant;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IRepaymentDetailDao;
import com.info.web.pojo.RepaymentChecking;
import com.info.web.pojo.RepaymentDetail;
import com.info.web.util.PageConfig;


@Service
public class RepaymentDetailService implements IRepaymentDetailService {

    @Autowired
    private IRepaymentDetailDao repaymentDetailDao;

    @Autowired
	private IPaginationDao paginationDao;
    
    @Override
    public RepaymentDetail selectByPrimaryKey(Integer id) {
        return repaymentDetailDao.selectByPrimaryKey(id);
    }
    
    @Override
	public RepaymentDetail selectByOrderId(String orderId){
        return repaymentDetailDao.selectByOrderId(orderId);
    }

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return repaymentDetailDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean insert(RepaymentDetail detail) {
        return repaymentDetailDao.insert(detail) > 0;
    }

    @Override
    public boolean insertSelective(RepaymentDetail detail) {
        return repaymentDetailDao.insertSelective(detail) > 0;
    }

    @Override
    public boolean updateByPrimaryKey(RepaymentDetail detail) {
        return repaymentDetailDao.updateByPrimaryKey(detail) > 0;
    }

    @Override
    public boolean updateByPrimaryKeySelective(RepaymentDetail detail) {
        return repaymentDetailDao.updateByPrimaryKeySelective(detail) > 0;
    }
    @SuppressWarnings("unchecked")
	@Override
	public PageConfig<RepaymentChecking> checkRepayment(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RepaymentDetail");
		return paginationDao.findPage("checkRepayment", "checkRepaymentCount", params, "web");
	}
    @SuppressWarnings("unchecked")
	@Override
	public PageConfig<RepaymentChecking> checkRenewal(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RepaymentDetail");
		return paginationDao.findPage("checkRenewal", "checkRenewalCount", params, "web");
	}

	@Override
	public int checkRepaymentCount(HashMap<String, Object> params) {
		return repaymentDetailDao.checkRepaymentCount(params);
	}

	@Override
	public int checkRenewalCount(HashMap<String, Object> params) {
	   return repaymentDetailDao.checkRenewalCount(params);
	}
    @SuppressWarnings("unchecked")
	@Override
	public PageConfig<RepaymentDetail> repaymentDetilList(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RepaymentDetail");
		return paginationDao.findPage("repaymentDetilList", "repaymentDetilCount", params, "web");
	}

	@Override
	public List<RepaymentDetail> queryOrderResult(HashMap<String, Object> params) {
		return repaymentDetailDao.queryOrderResult(params);
	}

    @Override
    public void insertStatisticsDetail(List<Map<String,Object>> list){
        repaymentDetailDao.insertStatisticsDetail(list);
    }

    @Override
    public List<Map<String,Object>> selectAssignStatisticsByCondition(Map<String,Object> params) {
        return repaymentDetailDao.selectAssignStatisticsByCondition(params);
    }
    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<Map<String,Object>> assignStatistics(HashMap<String,Object> params) {
        params.put(Constant.NAME_SPACE,"RepaymentDetail");
        return paginationDao.findPage("assignStatistics", "assignStatisticsCount",(HashMap)params , "web");
    }

    @Override
    public List<Map<String, Object>> selectAssignStatisticsBackMoney(Map<String, Object> params) {
        return repaymentDetailDao.selectAssignStatisticsBackMoney(params);
    }

    @Override
    public int assignBackMoneyStatisticsCount(HashMap<String, Object> params) {
        return repaymentDetailDao.assignBackMoneyStatisticsCount(params);
    }
    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<Map<String, Object>> assignBackMoneyStatistics(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE,"RepaymentDetail");
        Object val = params.get(Constant.PAGE_SIZE);
        if(val ==null)params.put(Constant.PAGE_SIZE,20);

        return paginationDao.findPage("assignBackMoneyStatistics", "assignBackMoneyStatisticsCount",(HashMap)params , "web");
    }
}
