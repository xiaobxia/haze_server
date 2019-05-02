package com.info.back.service;

import com.info.back.dao.IUserCardInfoDao;
import com.info.back.pojo.CustomerClassArrange;
import com.info.back.pojo.UserDetail;
import com.info.constant.Constant;
import com.info.web.dao.*;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.Remark;
import com.info.web.pojo.Repayment;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈在线客服service层〉
 *
 * @author Liubing
 * @create 2018/4/9
 * @since 1.0.0
 */
@Slf4j
@Service
public class OnlineCustomService implements IOnlineCustomService {

    @Resource
    private IRepaymentDao repaymentDao;
    @Resource
    private IRepaymentDetailDao repaymentDetailDao;

    @Resource
    private IBorrowOrderDao borrowOrderDao;

    @Resource
    private IUserDao userDao;

    @Resource
    private IUserCardInfoDao userCardInfoDao;

    @Resource
    private PaginationDao paginationDao;

    @Override
    public UserDetail getUserInfoDetail(Map<String, Object> params) {
        String userPhone = params.get("userPhone") == null ? "" : params.get("userPhone").toString();
        if (StringUtils.isNotEmpty(userPhone)) {
            UserDetail userDetail = repaymentDao.findUserByPhone(userPhone);
            if (userDetail == null) { return null;}
            Map<String, Object> queryCardInfoMap = new HashMap<>();
            queryCardInfoMap.put("userId", userDetail.getId());
            List<Map<String, Object>> banks = userCardInfoDao.findUserBankList(queryCardInfoMap);
            if (CollectionUtils.isNotEmpty(banks)) {
                for (Map<String, Object> bank : banks) {
                    bank.put("cardNo", UserDetail.showNumber(bank.get("cardNo") == null ? "" : bank.get("cardNo").toString()));
                }
                userDetail.setBanks(banks);
            }
            //最近一笔订单的详细信息
            userDetail = this.getLateDayAndRepaymentTime(userDetail);
            //用户借款订单备注信息
            userDetail = this.getRemarks(userDetail);
            userDetail = this.getUserCertification(userDetail);
            //最近三条借款记录
            userDetail = this.getAllBorrowHistory(userDetail);
            //最近三条续期记录
            userDetail = this.getRenewalHistory(userDetail);
            return userDetail;
        } else {
            return null;
        }
    }

    /**
     * 续期记录
     *
     * @param user user
     * @return user
     */
    private UserDetail getRenewalHistory(UserDetail user) {
        try {
            List<Map<String, Object>> renewalHistory = repaymentDetailDao.getRenewalHistory(Integer.valueOf(user.getId()));
            if (renewalHistory != null && !renewalHistory.isEmpty()) {
                user.setRenewalHistory(renewalHistory);
            }
        } catch (Exception e) {
            log.error("getRenewalHistory error:{}", e);
        }

        return user;
    }

    /**
     * 获取用户借款历史记录
     *
     * @param user user
     * @return user
     */
    private UserDetail getAllBorrowHistory(UserDetail user) {
        try {
            //已经还款则加上还款渠道
            List<Map<String, Object>> repaymentHistory = repaymentDetailDao.getRepaymentDetailByUserId(Integer.valueOf(user.getId()));
            if (repaymentHistory != null && !repaymentHistory.isEmpty()) {
                Predicate<Map<String, Object>> historyPred = (repayDetail) -> (repayDetail.get("status") == null ||"2".equals(repayDetail.get("status").toString()));
                repaymentHistory = repaymentHistory.stream().filter(historyPred).collect(Collectors.toList());
                user.setRepaymentHistory(repaymentHistory);
            }
        } catch (Exception e) {
            log.error("get repaymentType error:{}", e);
        }
        return user;

    }

    /**
     * 获取最近一次订单的详细信息
     *
     * @param user user
     * @return user
     */
    private UserDetail getLateDayAndRepaymentTime(UserDetail user) {
        String userId = user.getId();
        BorrowOrder borrowOrder = borrowOrderDao.selectBorrowOrderByUserIdForLast(userId);
        if (borrowOrder == null) {
            return user;
        } else {
            //8.28 加入第三方流水号展示 和订单号
            user.setOutTradeNo(borrowOrder.getOutTradeNo());
            user.setYurref(borrowOrder.getYurref());
            user.setMoneyAmount(borrowOrder.getMoneyAmount());
            if(borrowOrder.getLoanEndTime() != null) {
                user.setLoanTime(DateUtil.getDateFormat(borrowOrder.getLoanTime(), DateUtil.YMDHMS));
            }
        }
        Repayment repayment = repaymentDao.selectAssetRepaymentByUserId(userId, borrowOrder.getId());
        if (repayment != null) {
            if (!repayment.getStatus().equals(30) && !repayment.getStatus().equals(34)) {
                user.setLateDay(repayment.getLateDay());
                user.setRepaymentTime(repayment.getRepaymentTime() == null ? "" : DateUtil.getDateFormat(repayment.getRepaymentTime(), "yyyy-MM-dd HH:mm:ss"));
                user.setAssetOrderId(repayment.getAssetOrderId() == null ? "" : repayment.getAssetOrderId().toString());
            }
            user.setRepaymentStatus(repayment.getStatus());
        } else {
            user.setRepaymentStatus(borrowOrder.getStatus());
        }
        return user;
    }

    /**
     * 查询用户对应的所有订单备注信息
     *
     * @param user user
     * @return user
     */
    private UserDetail getRemarks(UserDetail user) {
        String userId = user.getId();
        List<Remark> list = repaymentDao.queryBorrowRemark(userId);
        user.setRemarks(list);
        //已经还款则重新获取一次
        if (user.getAssetOrderId() == null || "".equals(user.getAssetOrderId())) {
            String borrowOrderId = borrowOrderDao.getBorrowOrderIdByUserId(Integer.valueOf(userId));
            user.setAssetOrderId(borrowOrderId);
        }
        return user;
    }

    /**
     * 用户认真信息
     *
     * @param user user
     * @return user
     */
    private UserDetail getUserCertification(UserDetail user) {
        Map<String, Object> userIdMap = new HashMap<>();
        userIdMap.put("userId", user.getId());
        Map<String, Object> resultMap = userDao.selectCertificationPage(userIdMap);
        if (resultMap != null) {
            user.setRealnameStatus(resultMap.get("realNameStatus") == null ? "" : resultMap.get("realNameStatus").toString());
            user.setEmergencyInfo(resultMap.get("emergencyInfo") == null ? "" : resultMap.get("emergencyInfo").toString());
            user.setZmStatus(resultMap.get("zmStatus") == null ? "" : resultMap.get("zmStatus").toString());
            user.setTdStatus(resultMap.get("tdStatus") == null ? "" : resultMap.get("tdStatus").toString());
        }
        return user;
    }

    @Override
    public CustomerClassArrange getCustomerClassById(String id) {
        return userDao.getCustomerClassById(id);
    }

    @Override
    public CustomerClassArrange getCustomerClassByDate(String date) {
        return userDao.getCustomerClassByDate(date);
    }

    @Override
    public void saveCustomerClassArrange(CustomerClassArrange customerClassArrange) {
        userDao.saveCustomerClass(customerClassArrange);
    }

    @Override
    public void updateCustomerClassArrange(CustomerClassArrange customerClassArrange) {
        userDao.updateCustomerClass(customerClassArrange);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<Map<String, Object>> getCustomerClassList(Map<String, Object> params) {
        params.put(Constant.NAME_SPACE, "User");
        return paginationDao.findPage("getCustomerClass", "getCustomerClassCount", (HashMap) params, "web");
    }

    @Override
    public List<BackUser> getCustomerList() {
        return userDao.getCustomerList();
    }


    @Override
    public List<Map<String, Object>> getCustomerClassForExcel(String[] idArray) {
        return userDao.getCustomerClassForExcel(idArray);
    }

    @Override
    public String getLastClassDate() {
        return userDao.getLastClassDate();
    }
}