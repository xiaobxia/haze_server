package com.info.statistic.service;

import com.google.gson.Gson;
import com.info.constant.Constant;
import com.info.risk.pojo.RiskModelOrder;
import com.info.statistic.dao.IOverdueDao;
import com.info.statistic.util.DateUtil;
import com.info.web.dao.IBorrowOrderDao;
import com.info.web.dao.IChannelInfoDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.dao.IRepaymentDao;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.ChannelInfo;
import com.info.web.pojo.Repayment;
import com.info.web.pojo.User;
import com.info.web.pojo.statistics.Overdue;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.info.web.pojo.RenewalRecord;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by tl on 2018/4/3.
 */
@Slf4j
@Service
public class OverdueService implements IOverdueService {
    private static final int OVERDUE_CHANNEL3 = 4;
    @Resource
    private IRepaymentDao repaymentDao;
    @Resource
    private IBorrowOrderDao borrowOrderDao;
    @Resource
    private IChannelInfoDao channelInfoDao;
    @Resource
    private IOverdueDao overdueDao;

    private static final int OVERDUE_ORDER = 0;
    private static final int OVERDUE_USER = 1;
    private static final int DIRTY_USER = 2;
    private static final int OVERDUE_DAY = -1;
    private static final int OVERDUE_MODEL = -2;
    private static final int OVERDUE_REVIEW = -3;
    private static final int DIRTY_S1 = 3;
    /**
     * 首逾新增的
     */
    private static final int FIRST_OVERDUE = 4;

    private static final int DIRTY_S2 = 15;
    private static final int DIRTY_S3 = 30;
    private static final String NATRUAL_CHANNEL = "自然渠道";
    @Autowired
    private IPaginationDao paginationDao;

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<Overdue> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "Overdue");
        return paginationDao.findPage("findParams", "findParamsCount", params, "statistic");
    }

    @Override
    public List<Overdue> analysisByReviewTime(HashMap<String, Object> params) throws ParseException {
        List<Overdue> overdueList = new ArrayList<>();
        String sDateStr = (String) params.get("sDate");
        String eDateStr = (String) params.get("eDate");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sDate = sdf1.parse(sDateStr);
        Date eDate = new Date();
        if (null != eDateStr) {
            eDate = sdf1.parse(eDateStr);
        }
        Date startOfDay = DateUtil.getStartOfDay(sDate);
        Date endOfDay = DateUtil.getEndOfDay(eDate);
        int customerType = Integer.parseInt((String) params.get("customerType"));
        Overdue overdue = creOverdue(startOfDay, endOfDay, new Date(), customerType);
        List<Integer> sList = new ArrayList<>();
        sList.add(BorrowOrder.STATUS_CSTG);
        sList.add(BorrowOrder.STATUS_CSBH);
        sList.add(BorrowOrder.STATUS_FSBH);
        sList.add(BorrowOrder.STATUS_DCS);
        overdue.setList(sList);
        List<Repayment> repayments = repaymentDao.findByReviewTime(overdue);
        /*if (CollectionUtils.isEmpty(repayments)) {
            return overdueList;
        }*/
        //新老用户
        //repayments = getByCustomerType(overdue, repayments);

        HashMap<String, Object> param = new HashMap<>();
        param.put("sdate", startOfDay);
        param.put("edate", endOfDay);
        param.put("customerType",params.get("customerType"));
        List<BorrowOrder> borrowOrders = borrowOrderDao.findByReviewUser(param);

        //borrowOrders = getOrdersByCustomerType(overdue, borrowOrders);
        Map<String, List<BorrowOrder>> mapByReviewUser = getBorrowOrdersByReview(borrowOrders);
        Map<String, List<Repayment>> mapByModel = getRepaymentsByReview(repayments);
        for (String s : mapByReviewUser.keySet()) {
            if (s.startsWith("自动")) {
                continue;
            }
            List<BorrowOrder> borrowOrderList = mapByReviewUser.get(s);

            Overdue over = creOverdue(startOfDay, endOfDay, new Date(), customerType);
            over.setReviewWay(s);
            over.setReviewUser(s);
            if (mapByModel.containsKey(s)) {
                //计算首逾量 催回量 续期次数
                List<Repayment> list = mapByModel.get(s);
                getOverdue(over, list, OVERDUE_REVIEW);
            }
            getPassByReview(over, borrowOrderList);
            overdueList.add(over);
        }
        return overdueList;
    }

    private List<Repayment> getByCustomerType(Overdue overdue, List<Repayment> repayments) {
        // 根据userId去重
        List<Repayment> uniqueRepayments = getUniqueRepayments(repayments, false);
        //获取新老用户的repayments和uniqueRepayments
        if (overdue.getType() != null && (Overdue.NEW_CUSTOMER == overdue.getType() || Overdue.OLD_CUSTOMER == overdue.getType())) {
            Map<Integer, List<Repayment>> map = handleRepayments(repayments, uniqueRepayments);
            if (Overdue.NEW_CUSTOMER == overdue.getType()) {
                repayments = map.get(Overdue.NEW_CUSTOMER);
            } else {
                repayments = map.get(Overdue.OLD_CUSTOMER);
            }
        }
        return repayments;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(Date pdate, int past) {
        Calendar calendar = Calendar.getInstance();
        if (null != pdate) {
            calendar.setTime(pdate);
        }
        calendar.add(Calendar.DATE, -past);
        Date today = calendar.getTime();
        return today;
    }

    private List<BorrowOrder> getOrdersByCustomerType(Overdue overdue, List<BorrowOrder> borrowOrders) {
        if (overdue.getType() != null && (Overdue.NEW_CUSTOMER == overdue.getType() || Overdue.OLD_CUSTOMER == overdue.getType())) {
            List<BorrowOrder> newList = new ArrayList<>();
            List<BorrowOrder> oldList = new ArrayList<>();
            for (BorrowOrder borrowOrder : borrowOrders) {
                User user = borrowOrder.getUser();
                if (null != user) {
                    String customerType = user.getCustomerType();
                    if (User.CUSTOMER_OLD.equals(customerType)) {
                        oldList.add(borrowOrder);
                    } else if (User.CUSTOMER_NEW.equals(customerType)) {
                        newList.add(borrowOrder);
                    }
                }
            }
            if (Overdue.NEW_CUSTOMER == overdue.getType()) {
                borrowOrders = newList;
            } else {
                borrowOrders = oldList;
            }
        }
        return borrowOrders;
    }

    @Override
    public Overdue analysis(Overdue overdue) {
        handleDate(overdue);
        overdue.setSource(Overdue.ORIGINAL);
        if (null == overdue.getType()) {
            overdue.setType(Overdue.ALL_CUSTOMER);
        }
        List<Repayment> repayments = repaymentDao.findByDates(overdue);
        if (CollectionUtils.isEmpty(repayments)) {
            return null;
        }
        // 根据userId去重
        List<Repayment> uniqueRepayments = getUniqueRepayments(repayments, true);
        //获取新老用户的repayments和uniqueRepayments
        if (Overdue.NEW_CUSTOMER == overdue.getType() || Overdue.OLD_CUSTOMER == overdue.getType()) {
            Map<Integer, List<Repayment>> map = handleRepayments(repayments, uniqueRepayments);
            if (Overdue.NEW_CUSTOMER == overdue.getType()) {
                repayments = map.get(Overdue.NEW_CUSTOMER);
                uniqueRepayments = getUniqueRepayments(repayments, true);
            } else {
                repayments = map.get(Overdue.OLD_CUSTOMER);
                uniqueRepayments = getUniqueRepayments(repayments, true);
            }

        }
        getOverdue(overdue, uniqueRepayments, DIRTY_USER);
        getOverdue(overdue, uniqueRepayments, OVERDUE_USER);
        getOverdue(overdue, repayments, OVERDUE_ORDER);
        return overdue;
    }

    private Map<Integer, List<Repayment>> handleRepayments(List<Repayment> repayments, List<Repayment> uniqueRepayments) {
        List<Integer> ids = getIds(uniqueRepayments);
        Map<Integer, Repayment> userMap = new HashMap<>();
        Map<Integer, List<Repayment>> map = new HashMap<>();
        List<Repayment> newRepayments = new ArrayList<>();
        List<Repayment> oldRepayments = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Repayment> byUserId = repaymentDao.findByUserIds(ids);
            //每个用户的首个repayment
            List<Repayment> unique = getUniqueRepayments(byUserId, false);
            for (Repayment repayment : unique) {
                userMap.put(repayment.getUserId(), repayment);
            }

            for (Repayment repayment : repayments) {
                Repayment r = userMap.get(repayment.getUserId());
                if (DateUtil.daysBetween(r.getCreditRepaymentTime(), repayment.getCreditRepaymentTime()) == 0) {
                    newRepayments.add(repayment);
                } else {
                    oldRepayments.add(repayment);
                }
            }
        }
        map.put(Overdue.NEW_CUSTOMER, newRepayments);
        map.put(Overdue.OLD_CUSTOMER, oldRepayments);
        return map;
    }

    private List<Integer> getIds(List<Repayment> uniqueRepayments) {
        List<Integer> ids = new ArrayList<>();
        for (Repayment uniqueRepayment : uniqueRepayments) {
            Integer userId = uniqueRepayment.getUserId();
            ids.add(userId);
        }
        return ids;
    }

    private void handleDate(Overdue overdue) {
        Date endDate = overdue.getEndDate();
        Date startDate = overdue.getStartDate();
        if (endDate != null) {
            endDate = DateUtil.getEndOfDay(endDate);
            overdue.setEndDate(endDate);
        }
        if (startDate != null) {
            startDate = DateUtil.getStartOfDay(startDate);
            overdue.setStartDate(startDate);
        }
    }

    @Override
    public List<Overdue> getOverdueDaysByChannel(Date sdate, Date edate, Date pdate, int type) {
        List<Overdue> overdueList = new ArrayList<>();
        Overdue overdue = creOverdue(sdate, edate, pdate, type);
        if (null == overdue.getType()) {
            return overdueList;
        }
        List<Repayment> repayments = repaymentDao.findByDatesUser(overdue);
        if (CollectionUtils.isEmpty(repayments)) {
            return overdueList;
        }
        repayments = getRepaymentsByCustomerType(overdue, repayments);
        Map<String, List<Repayment>> mapByChannel = getRepaymentsByChannel(repayments);
        for (String s : mapByChannel.keySet()) {
            List<Repayment> list = mapByChannel.get(s);
            Overdue over = creOverdue(null, null, pdate, type);
            over.setChannelName(s);
            over.setSource(Overdue.CHANNEL);
            getOverdue(over, list, OVERDUE_DAY);
            overdueList.add(over);
        }
        return overdueList;
    }

    @Override
    public List<Overdue> analysisByModel(Date pdate) {
        List<Overdue> overdueList = new ArrayList<>();
        Overdue overdue = creOverdue(null, null, pdate, Overdue.ALL_CUSTOMER);
        List<Repayment> repayments = repaymentDao.findByModelOrder(overdue);
        if (CollectionUtils.isEmpty(repayments)) {
            return overdueList;
        }
        Map<String, List<Repayment>> mapByModel = getRepaymentsByModel(repayments);
        for (String s : mapByModel.keySet()) {
            List<Repayment> list = mapByModel.get(s);
            Overdue over = creOverdue(null, null, pdate, Overdue.ALL_CUSTOMER);
            over.setReviewWay(s);
            over.setSource(Overdue.MODEL);
            getOverdue(over, list, OVERDUE_MODEL);
            getPassRate(over);
            overdueList.add(over);
        }
        return overdueList;
    }

    @Override
    public List<Overdue> updateByChannel(Date pdate, int type) {
        List<Overdue> overdueList = new ArrayList<>();
        Date pastDate = DateUtil.getPastDate(pdate, OVERDUE_CHANNEL3);
        Date endOfDay = DateUtil.getEndOfDay(pastDate);
        Date startOfDay = DateUtil.getStartOfDay(pastDate);
        HashMap<String, Object> params = new HashMap<>(5);
        params.put("sDate", startOfDay);
        params.put("eDate", endOfDay);
        params.put("source", Overdue.CHANNEL);
        params.put("customerType", type);
        List<Overdue> overdues = overdueDao.findParams(params);
        if (CollectionUtils.isEmpty(overdues)) {
            return overdueList;
        }
        Overdue overdue = creOverdue(startOfDay, endOfDay, pdate, type);
        List<Repayment> repayments = repaymentDao.findByDatesUser(overdue);
        if (CollectionUtils.isEmpty(repayments)) {
            return overdueList;
        }
        repayments = getRepaymentsByCustomerType(overdue, repayments);
        Map<String, List<Repayment>> mapByChannel = getRepaymentsByChannel(repayments);
        Map<String, Overdue> oldByModel = getMapByChannel(overdues);
        for (String s : oldByModel.keySet()) {
            Overdue old = oldByModel.get(s);
            List<Repayment> list = mapByChannel.get(s);

            Overdue over = creOverdue(null, null, pdate, type);
            getOverdue(over, list, OVERDUE_CHANNEL3);
            old.setOverdueRate3(over.getOverdueRate3());
            old.setOverdueNum3(over.getOverdueNum3());
            overdueList.add(old);
        }
        return overdueList;
    }

    private Map<String, Overdue> getMapByChannel(List<Overdue> overdues) {
        Map<String, Overdue> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(overdues)) {
            for (Overdue o : overdues) {
                String channelName = o.getChannelName();
                map.put(channelName, o);
            }
        }
        return map;
    }

    private List<Repayment> getRepaymentsByCustomerType(Overdue overdue, List<Repayment> repayments) {
        // 根据userId去重
        List<Repayment> uniqueRepayments = getUniqueRepayments(repayments, false);
        //获取新老用户的repayments和uniqueRepayments
        if (overdue.getType() != null && (Overdue.NEW_CUSTOMER == overdue.getType() || Overdue.OLD_CUSTOMER == overdue.getType())) {
            Map<Integer, List<Repayment>> map = handleRepayments(repayments, uniqueRepayments);
            if (Overdue.NEW_CUSTOMER == overdue.getType()) {
                repayments = map.get(Overdue.NEW_CUSTOMER);
            } else {
                repayments = map.get(Overdue.OLD_CUSTOMER);
            }
        }
        return repayments;
    }

    private void getPassByReview(Overdue over, List<BorrowOrder> borrowOrders) {
        int size = 0;
        double passRate = 0.0;
        //不是 初审通过 待初审 初审驳回 复审驳回 的转态 ，审核通过的数量
        int pass = 0;
        //正常已还款
        int paid = 0;
        //待还款 还款中
        int toPay = 0;
        //已逾期
        int overdueYet = 0;
        //逾期已还款
        int overdueButRepay = 0;
        //部分还款
        int partRepay = 0;
        //已坏账
        int badOrd = 0;
        //放款成功量
        int yesPay = 0;


        if (CollectionUtils.isNotEmpty(borrowOrders)) {
            for (BorrowOrder order : borrowOrders) {
                    size++;
                    // 总还款状态0:待初审(待机审);-3:初审驳回;1:初审通过;-4:复审驳回;20:复审通过,待放款;-5:放款驳回;22:放款中;-10:放款失败;；
                    //223:部分还款;30:已还款;-11:已逾期;-20:已坏账，34逾期已还款
                    //pass++;
                    Integer status = order.getStatus();
                    if (BorrowOrder.STATUS_YHK.equals(status)) {
                        paid++;
                    }
                    if (BorrowOrder.STATUS_HKZ.equals(status)) {
                        toPay++;
                    }
                    if (BorrowOrder.STATUS_YYQ.equals(status)) {
                        overdueYet++;
                    }
                    if (BorrowOrder.STATUS_YQYHK.equals(status)) {
                        overdueButRepay++;
                    }
                    if (BorrowOrder.STATUS_BFHK.equals(status)) {
                        partRepay++;
                    }
                    if (BorrowOrder.STATUS_YHZ.equals(status)) {
                        badOrd++;
                    }
            }
            //过件量 = 正常已还款+待还款+已逾期+逾期已还款
            pass = paid + toPay + overdueYet + overdueButRepay;
            yesPay = pass + partRepay + badOrd;
            if (size != 0) {
                passRate = yesPay * 100.0 / size;
                passRate = new BigDecimal(passRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        over.setPassRate(passRate);
        over.setBorrowNum(size);
        over.setPassNum(yesPay);
        over.setPaidNum(paid);
        over.setToPayNum(toPay);
        over.setOverdueYet(overdueYet);
        over.setOverdueButRepay(overdueButRepay);
        over.setBadOrd(badOrd);
        over.setPartRepay(partRepay);
        double collectRate = 0.0;
        double firstOverdueRate = 0.0;
        Integer firstOverdueNum = over.getFirstOverdueNum();
        if (null == firstOverdueNum) {
            firstOverdueNum = 0;
            over.setFirstOverdueNum(0);
        }
        Integer collectNum = over.getCollectNum();
        if (null == collectNum) {
            collectNum = 0;
            over.setCollectNum(0);
        }
        if (null == over.getRenewalNum()) {
            over.setRenewalNum(0);
        }
        if (firstOverdueNum != 0) {
            collectRate = collectNum * 100.0 / firstOverdueNum;
            collectRate = new BigDecimal(collectRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if (yesPay != 0) {
            firstOverdueRate = firstOverdueNum * 100.0 / yesPay;
            firstOverdueRate = new BigDecimal(firstOverdueRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        over.setCollectRate(collectRate);
        over.setFirstOverdueRate(firstOverdueRate);
    }

    private void getPassRate(Overdue over) {
        HashMap<String, Object> params = new HashMap<>();
        String reviewWay = over.getReviewWay();
        if (reviewWay.contains("-")) {
            String[] split = reviewWay.split("-");
            params.put("modelCode", split[0]);
            params.put("variableVersion", Integer.parseInt(split[1]));
            over.setReviewFlag(Overdue.MODEL);
        } else {
            params.put("userId", Integer.parseInt(reviewWay));
            over.setReviewFlag(Overdue.PERSON);
        }
        List<BorrowOrder> borrowOrders = borrowOrderDao.findByReviewWay(params);
        int size = 0;
        double passRate = 0.0;
        if (CollectionUtils.isNotEmpty(borrowOrders)) {
            int pass = 0;
            for (BorrowOrder order : borrowOrders) {
                if (isPassed(order)) {
                    pass++;
                }
            }
            size = borrowOrders.size();
            passRate = pass * 100.0 / size;
            over.setPassRate(passRate);
            over.setBorrowNum(size);
        }
        over.setPassRate(passRate);
        over.setBorrowNum(size);
    }

    private boolean isPassed(BorrowOrder order) {
        boolean passed = true;
        Integer status = order.getStatus();
        if (status != null) {
            switch (status) {
                case 1:
                case -3:
                case 0:
                case -4:
                    passed = false;
                    break;
                default:
                    break;
            }
        } else {
            passed = false;
        }
        return passed;
    }

    private boolean isReviewed(BorrowOrder order) {
        boolean passed = true;
        Integer status = order.getStatus();
        if (status != null) {
            switch (status) {
                case 1:
                case -3:
                case 0:
                    passed = false;
                    break;
                default:
                    break;
            }
        } else {
            passed = false;
        }
        return passed;
    }

    private Map<String, List<Repayment>> getRepaymentsByModel(List<Repayment> repayments) {
        Map<String, List<Repayment>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(repayments)) {
            for (Repayment r : repayments) {
                RiskModelOrder order = r.getRiskModelOrder();
                if (null == order || StringUtils.isEmpty(order.getModelCode())) {
                    continue;
                }
                String modelCode = order.getModelCode();
                Integer variableVersion = order.getVariableVersion();
                if ("0".equals(modelCode)) {
                    //人工
                    putMapByChannel(map, r.getUserId() + "", r);
                } else {
                    putMapByChannel(map, modelCode + "-" + variableVersion, r);
                }
            }
        }
        return map;
    }

    private Map<String, List<Repayment>> getRepaymentsByReview(List<Repayment> repayments) {
        Map<String, List<Repayment>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(repayments)) {
            for (Repayment r : repayments) {
                BorrowOrder borrowOrder = r.getBorrowOrder();
                if (null == borrowOrder || StringUtils.isEmpty(borrowOrder.getVerifyReviewUser())) {
                    continue;
                }
                String verifyReviewUser = borrowOrder.getVerifyReviewUser();
                putMapByChannel(map, verifyReviewUser, r);
            }
        }
        return map;
    }

    private Map<String, List<BorrowOrder>> getBorrowOrdersByReview(List<BorrowOrder> borrowOrders) {
        Map<String, List<BorrowOrder>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(borrowOrders)) {
            for (BorrowOrder borrowOrder : borrowOrders) {
                if (StringUtils.isEmpty(borrowOrder.getVerifyReviewUser())) {
                    continue;
                }
                String verifyReviewUser = borrowOrder.getVerifyReviewUser();
                putMapByReview(map, verifyReviewUser, borrowOrder);
            }
        }
        return map;
    }

    private Overdue creOverdue(Date sdate, Date edate, Date pdate, int type) {
        Overdue overdue = new Overdue();
        overdue.setEndDate(edate);
        overdue.setStartDate(sdate);
        overdue.setPointDate(pdate);
        overdue.setType(type);
        handleDate(overdue);
        return overdue;
    }

    private Map<String, List<Repayment>> getRepaymentsByChannel(List<Repayment> repayments) {
        Map<String, List<Repayment>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(repayments)) {
            Map<Integer, String> channelInfoMap = new HashMap<>();
            List<ChannelInfo> channelInfos = channelInfoDao.findAllChannels();
            if (CollectionUtils.isNotEmpty(channelInfos)) {
                for (ChannelInfo c : channelInfos) {
                    channelInfoMap.put(c.getId(), c.getChannelName());
                }
                for (Repayment r : repayments) {
                    User u = r.getUser();
                    if (null == u) {
                        continue;
                    }
                    String userFrom = u.getUserFrom();
                    if (null == userFrom || "0".equals(userFrom)) {
                        putMapByChannel(map, NATRUAL_CHANNEL, r);
                    } else {
                        putMapByChannel(map, channelInfoMap.get(Integer.parseInt(userFrom)), r);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 根据type封装overdueVO
     */
    private void getOverdue(Overdue overdue, List<Repayment> repayments, int type) {
        TreeMap<Integer, Integer> result = new TreeMap<>();
        getResult(overdue, repayments, result, type);
        if (OVERDUE_ORDER == type) {
            overdue.setResult(result);
            overdue.setOverdueDistribution(new Gson().toJson(result));
        }
        if (CollectionUtils.isEmpty(repayments)) {
            getRate(overdue, type, 0, 0, 0, 0);
            overdue.setOverdueNum3(0);
            overdue.setOverdueNum(0);
            return;
        }
        Integer sum1 = result.get(-1);
        Integer sum2 = result.get(-2);
        Integer sum3 = result.get(-3);
        Integer sum4 = result.get(-4);
        Integer firstOver = result.get(FIRST_OVERDUE);

        if (null == firstOver) {
            firstOver = 0;
        }

        if (sum1 == null) {
            sum1 = 0;
        }

        overdue.setFirstOverdueNum(firstOver);
        if (sum2 == null) {
            sum2 = 0;
        }
        if (sum3 == null) {
            sum3 = 0;
        }
        if (sum4 == null) {
            sum4 = 0;
        }
        //所有逾期包括坏账预期已还款已逾期等等
        overdue.setOverdueNum(sum1);

        if (OVERDUE_CHANNEL3 == type) {
            overdue.setOverdueNum3(sum1);
        }
        double rate1 = sum1 * 100.0 / repayments.size();
        double rate2 = sum2 * 100.0 / repayments.size();
        double rate3 = sum3 * 100.0 / repayments.size();
        double rate4 = sum4 * 100.0 / repayments.size();
        getRate(overdue, type, rate1, rate2, rate3, rate4);
    }

    /**
     * 计算rate
     */
    private void getRate(Overdue overdue, int type, double rate1, double rate2, double rate3, double rate4) {
        switch (type) {
            case OVERDUE_ORDER:
            case OVERDUE_REVIEW:
                overdue.setOverdueRate(rate1);
                break;
            case OVERDUE_DAY:
                overdue.setOverdueRate(rate1);
                break;
            case OVERDUE_USER:
                overdue.setOverUserRate(rate1);
                break;
            case OVERDUE_CHANNEL3:
                overdue.setOverdueRate3(rate1);
                break;
            case DIRTY_USER:
                overdue.setDirtyUserRateS1(rate1);
                overdue.setDirtyUserRateS2(rate2);
                overdue.setDirtyUserRateS3(rate3);
                overdue.setDirtyUserRateM1(rate4);
                break;
            case OVERDUE_MODEL:
                overdue.setOverdueRate(rate1);
                overdue.setDirtyRate15(rate2);
                overdue.setDirtyUserRateM1(rate3);
                break;
            default:
                break;
        }
    }

    /**
     * 根据userId去重
     */
    private List<Repayment> getUniqueRepayments(List<Repayment> repayments, boolean isSorted) {
        List<Repayment> list;
        if (isSorted) {
            //是否根据creditRepaymentTime排序
            list = repayments.stream().filter(r -> r.getCreditRepaymentTime() != null)
                    .sorted((Repayment r1, Repayment r2) -> r2
                            .getCreditRepaymentTime().compareTo(r1.getCreditRepaymentTime()))
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Repayment::getUserId))), ArrayList::new));
        } else {
            list = repayments.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Repayment::getUserId))), ArrayList::new));
        }
        return list;
    }

    /**
     * 逾期分布
     */
    private void getResult(Overdue overdue, List<Repayment> repayments, TreeMap<Integer, Integer> result, int type) {
        Integer renewalNum = 0;
        int paidYQ = 0;
        if (CollectionUtils.isNotEmpty(repayments)) {
            for (Repayment repayment : repayments) {
                List<RenewalRecord> records = repayment.getRenewalRecords();
                if (OVERDUE_REVIEW == type) {
                    BorrowOrder borrowOrder = repayment.getBorrowOrder();
                    Integer status = borrowOrder.getStatus();
                    if (null != status && BorrowOrder.STATUS_YQYHK.equals(status)) {
                        paidYQ++;
                    }
                }
                if (CollectionUtils.isEmpty(records) || records.get(0).getId() == null) {
                    //处理未续期的还款订单
                    getUnrenewable(overdue.getPointDate(), result, repayment, type);
                } else {
                    if (OVERDUE_REVIEW == type) {
                        renewalNum += records.size();
                    }
                    //处理已续期的还款订单
                    getRenewable(overdue.getPointDate(), result, repayment, records, type);
                }
            }
        }
        if (OVERDUE_REVIEW == type) {
            overdue.setRenewalNum(renewalNum);
            overdue.setCollectNum(paidYQ);
        }
    }

    /**
     * 续期情况
     */
    private void getRenewable(Date pointDate, TreeMap<Integer, Integer> res, Repayment repayment, List<RenewalRecord> records, int type) {
        if (pointDate == null || repayment == null) {
            return;
        }
        //实际还款时间
        Date repaymentRealTime = repayment.getRepaymentRealTime();
        Integer lateDays = 0;
        //累计头
        RenewalRecord lastRecord = records.get(0);
        //续期后预期还款时间
        Date repaymentTime = lastRecord.getRepaymentTime();
        //续期前预期还款时间
        Date oldRepaymentTime = lastRecord.getOldRepaymentTime();

        if (oldRepaymentTime != null && repaymentTime != null) {
            int days = DateUtil.daysBetween(repaymentTime, pointDate);
            if (days > 0) {
                if (repaymentRealTime != null) {
                    //已还和还款日期比较
                    int left = DateUtil.daysBetween(repaymentRealTime, pointDate);
                    if (left > 0) {
                        lateDays = repayment.getLateDay();
                    } else {
                        lateDays = days;
                    }
                } else {
                    lateDays = days;
                }
            }
        }

        int firstOve = null == res.get(FIRST_OVERDUE) ? 0 : res.get(FIRST_OVERDUE);

        for (RenewalRecord record: records) {
            //上一次的预计还款时间大于下次的续期时间意味着逾期续期 算首逾
            Integer planLateFee = record.getPlanLateFee();

        }

        //首逾逻辑只能确定到7天之前的单
        Date date1 = getPastDate(DateUtil.getEndOfDay(pointDate), 7);
        //第一次续期的续期订单存在滞纳金 则认定为首逾
        Integer planLateFee = records.get(records.size() - 1).getPlanLateFee();
        if (null != planLateFee && 0 < planLateFee) {
            //放款时间在7天之前的
            if (repayment.getCreditRepaymentTime().before(date1)) {
                res.put(FIRST_OVERDUE, firstOve + 1);
            }
        }

        for (RenewalRecord record : records) {
            //循环累计尾
            //续期后预期还款时间
            repaymentTime = record.getRepaymentTime();
            //续期前预期还款时间
            oldRepaymentTime = record.getOldRepaymentTime();
            //续期天数
            Integer renewalDay = record.getRenewalDay();
            if (oldRepaymentTime != null && repaymentTime != null) {
                int partDays = DateUtil.daysBetween(oldRepaymentTime, repaymentTime);
                partDays -= renewalDay;
                //和续期前预期还款时间比较
                int left = DateUtil.daysBetween(oldRepaymentTime, pointDate);
                if (left > 0) {
                    if (left < partDays) {
                        lateDays += left;
                    } else {
                        lateDays += partDays;
                    }
                }
            }
        }
        if (lateDays > 0) {
            count(res, type, lateDays);
        }
    }

    private void count(TreeMap<Integer, Integer> res, int type, Integer lateDays) {
        switch (type) {
            case OVERDUE_ORDER:
                //逾期率按订单统计
                putMap(res, lateDays);
                break;
            case OVERDUE_CHANNEL3:
                //逾期率按渠道统计
                if (lateDays >= OVERDUE_CHANNEL3) {
                    putMap(res, -1);
                }
                break;
            case OVERDUE_DAY:
                //逾期率按渠道统计
                if (lateDays == 1) {
                    putMap(res, -1);
                }
                break;
            case OVERDUE_REVIEW:
            case OVERDUE_USER:
                //逾期率按人数统计
                putMap(res, -1);
                break;
            case DIRTY_USER:
                //坏账率按人数统计
                if (lateDays <= DIRTY_S1) {
                    putMap(res, -1);
                } else if (lateDays <= DIRTY_S2) {
                    putMap(res, -2);
                } else if (lateDays <= DIRTY_S3) {
                    putMap(res, -3);
                } else {
                    putMap(res, -4);
                }
                break;
            case OVERDUE_MODEL:
                putMap(res, -1);
                if (lateDays > DIRTY_S2) {
                    putMap(res, -2);
                } else if (lateDays > DIRTY_S3) {
                    putMap(res, -3);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 未续期情况
     */
    private void getUnrenewable(Date pointDate, TreeMap<Integer, Integer> res, Repayment repayment, int type) {
        if (pointDate == null || repayment == null) {
            return;
        }
        //实际还款日期
        Date repaymentRealTime = repayment.getRepaymentRealTime();
        //预期还款时间
        Date repaymentTime = repayment.getRepaymentTime();
        if (repaymentTime != null) {
            //计算预期还款时间和目标时间相差多少天
            int days = DateUtil.daysBetween(repaymentTime, pointDate);
            int lateDays;
            if (days > 0) {
                if (repaymentRealTime != null) {
                    //计算实际还款时间和目标时间相差多少天
                    int left = DateUtil.daysBetween(repaymentRealTime, pointDate);
                    //如果是目标时间之前还完的，可计算逾期天数
                    if (left > 0) {
                        lateDays = repayment.getLateDay();
                    } else {
                        lateDays = days;
                    }
                } else {
                    lateDays = days;
                }
                if (lateDays > 0) {
                    count(res, type, lateDays);
                }
            }

            //首逾逻辑只能确定到7天之前的单
            Date date1 = getPastDate(DateUtil.getEndOfDay(pointDate), 7);
            int firstOve = null == res.get(FIRST_OVERDUE) ? 0 : res.get(FIRST_OVERDUE);
            if (null != repaymentRealTime) {
                //只要预计还款时间在实际还款时间之前就认定为首逾
                if (repaymentTime.before(repaymentRealTime)) {
                    if (repayment.getCreditRepaymentTime().before(date1)) {
                        res.put(FIRST_OVERDUE, firstOve + 1);
                    }
                }
            }else{
                if (repayment.getCreditRepaymentTime().before(date1)) {
                     res.put(FIRST_OVERDUE, firstOve + 1);
                }
            }

        }
    }

    private void putMap(TreeMap<Integer, Integer> res, int days) {
        //days为-1时记录总逾期单数
        if (days == 0) {
            return;
        }
        if (res.containsKey(days)) {
            res.put(days, res.get(days) + 1);
        } else {
            res.put(days, 1);
        }
        if (days > 0) {
            putMap(res, -1);
        }
    }

    private void putMapByChannel(Map<String, List<Repayment>> map, String name, Repayment repayment) {
        if (map.containsKey(name)) {
            List<Repayment> repayments = map.get(name);
            repayments.add(repayment);
            map.put(name, repayments);
        } else {
            List<Repayment> repayments = new ArrayList<>();
            repayments.add(repayment);
            map.put(name, repayments);
        }
    }

    private void putMapByReview(Map<String, List<BorrowOrder>> map, String name, BorrowOrder borrowOrder) {
        if (map.containsKey(name)) {
            List<BorrowOrder> borrowOrders = map.get(name);
            borrowOrders.add(borrowOrder);
            map.put(name, borrowOrders);
        } else {
            List<BorrowOrder> borrowOrders = new ArrayList<>();
            borrowOrders.add(borrowOrder);
            map.put(name, borrowOrders);
        }
    }
}
