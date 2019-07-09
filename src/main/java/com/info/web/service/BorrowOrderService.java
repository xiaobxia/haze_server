package com.info.web.service;

import com.alibaba.fastjson.JSON;
import com.info.back.dao.IBackConfigParamsDao;
import com.info.back.dao.IBackUserDao;
import com.info.back.service.ReviewDistributionService;
import com.info.back.utils.PropertiesUtil;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.constant.Constant;
import com.info.risk.service.IOutOrdersService;
import com.info.risk.service.ShService;
import com.info.risk.utils.ThreadPool;
import com.info.web.common.reslult.Status;
import com.info.web.dao.*;
import com.info.web.listener.IndexInit;
import com.info.web.pojo.*;
import com.info.web.test.HttpRequestCMBDF;
import com.info.web.test.HttpRequestCMBHL;
import com.info.web.test.HttpRequestYLDF;
import com.info.web.util.*;
import com.info.web.util.encrypt.MD5coding;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhangjb
 * @version V1.0
 * @ClassName: BorrowService.java
 * @Description: TODO
 * @Date 2016年12月12日 下午7:22:00
 */
@Slf4j
@Service
public class BorrowOrderService implements IBorrowOrderService {

    @Autowired
    private IBackConfigParamsDao backConfigParamsDao;

    @Autowired
    @Qualifier("userBankDaoImpl")
    private IUserBankDao userBankDao;

    @Resource
    private IBorrowOrderDao borrowOrderDao;
    @Autowired
    private IPaginationDao paginationDao;

    @Autowired
    private IUserDao userDao;
    @Resource
    private IUserLimitRecordDao userLimitRecordDao;

    @Resource
    private IRepaymentDao repaymentDao;
    @Autowired
    private IInfoIndexService infoIndexService;
    @Autowired
    private IOutOrdersService outOrdersService;

    @Autowired
    private IRepaymentService repaymentService;

    @Resource
    private IBorrowOrderLoanDao borrowOrderLoanDao;
    @Resource
    private IBorrowOrderLoanPersonDao borrowOrderLoanPersonDao;

    @Resource
    private IBorrowOrderCheckingDao borrowOrderCheckingDao;

    @Resource
    private IBorrowOrderCheckingExtDao borrowOrderCheckingExtDao;
    @Resource
    private IBorrowAssetPacketDao borrowAssetPacketDao;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private IBackConfigParamsService backConfigParamsService;

    @Autowired
    private IUserService userService;

    @Resource
    private IBackReviewDistributionDao backReviewDistributionDao;

    @Autowired
    private IBackUserDao backUserDao;

    @Autowired
    private IAssetKefuCensusDao kefuCensusDao;

    @Override
    public List<BorrowOrder> findOrderIdAndUserIdList(HashMap<String, Object> params) {
        return borrowOrderDao.findOrderIdAndUserIdList(params);
    }

    @Override
    public List<BorrowOrder> findAll(HashMap<String, Object> params) {
        return borrowOrderDao.findParams(params);
    }

    @Override
    public BorrowOrder findOneBorrow(Integer id) {

        return borrowOrderDao.selectByPrimaryKey(id);

    }

    @Override
    public BorrowOrder findByParam(HashMap<String, Object> param) {
        return borrowOrderDao.selectByParam(param);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void insert(BorrowOrder borrowOrder) {

    }

    @Override
    public void updateById(BorrowOrder borrowOrder) {

        borrowOrderDao.updateByPrimaryKeySelective(borrowOrder);

    }

    @Override
    public void updateLoanNew(BorrowOrder borrowOrder, String code, String desc) {

        Date nowDate = new Date();

        BorrowOrder borrowOrderNew = new BorrowOrder();
        borrowOrderNew.setId(borrowOrder.getId());
        borrowOrderNew.setUpdatedAt(nowDate);
        Date zhfkDate = null;// 真正的放款时间

        if (borrowOrder.getYurref().startsWith("A")) {//借款订单
            if (code.equals("SUCCESS")) {//放款成功

                final User user = userService.searchByUserid(borrowOrder.getUserId());

                zhfkDate = new Date();
                borrowOrderNew.setOutTradeNo(borrowOrder.getOutTradeNo());
                borrowOrderNew.setPaystatus(BorrowOrder.SUB_PAY_SUCC);
                borrowOrderNew.setLoanTime(zhfkDate);
                borrowOrderNew.setStatus(BorrowOrder.STATUS_HKZ);//已放款
                borrowOrderNew.setPayRemark(desc);
//				borrowOrderNew.setLoanEndTime(DateUtil.addDay(zhfkDate, borrowOrder.getLoanTerm()));// 放款时间加上借款期限，T+7或T+14
                borrowOrderNew.setLoanEndTime(DateUtil.addDay(zhfkDate, (borrowOrder.getLoanTerm() - 1)));// 放款时间加上借款期限，T+6

                // 放款成功插入还款记录
                borrowOrder.setPaystatus(code);
                borrowOrder.setStatus(BorrowOrder.STATUS_HKZ);
                borrowOrder.setLoanTime(zhfkDate);
                borrowOrder.setLoanEndTime(borrowOrderNew.getLoanEndTime());// 放款时间加上借款期限

                repaymentService.insertByBorrorOrder(borrowOrder);
                // 插入资产信息
//				this.addBorrowChecking(borrowOrder);

                String cardNo = borrowOrder.getCardNo();
//				Random random = new Random();
//				int time = random.nextInt(5);
//				HashMap<String,Object> notice = new HashMap<String, Object>();
//				notice.put("notContent","尾号" + cardNo.substring(cardNo.length() - 4) + "成功借款" +(borrowOrder.getMoneyAmount() /100)+ "元，申请至放款耗时"+(time + 3)+"分钟");
//				notice.put("notSelect","1");
//				notice.put("status","1");
//				notice.put("linkUrl","");
//				infoIndexService.saveInfoNotice(notice);

                final String msg = user.getRealname() + "##"+ PropertiesUtil.get("APP_NAME") +"##" + (borrowOrder.getMoneyAmount() / 100) + "##" +
                        cardNo.substring(cardNo.length() - 4);
                com.info.web.test.ThreadPool pool = com.info.web.test.ThreadPool.getInstance();
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            SendSmsUtil.sendSmsDiyCL(user.getUserPhone(), SendSmsUtil.templateld44641, msg);

                        }catch (Exception e){
                            log.error("send sms error:{}",e);
                        }
                    }
                });

            } else if (code.equals("FAIL")) {
                borrowOrderNew.setPayRemark("支付失败:" + desc);
                borrowOrderNew.setPaystatus(BorrowOrder.PAY_PAY_FAIL);
                borrowOrderNew.setStatus(BorrowOrder.STATUS_FKSB);
            } else {
                //TODO wison 放款回调其他状态处理
            }
            borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);
        }
    }

    @Override
    public Map<String, Object> updateLoan(BorrowOrder borrowOrder, Integer userId) {
        Map<String, Object> result = new HashMap<>();
        // 借款服务费率走向，用户拆分服务费
        Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.SYS_FEE);
        Integer fee_company = Integer.parseInt(keys.get("fee_company")); // 公司
        Integer fee_person = Integer.parseInt(keys.get("fee_person")); // 个人

        Integer money = borrowOrder.getMoneyAmount() * 100; // 借款金额
        // 计算服务费率																// ，单位：分
        Map<String, Integer> fee = this.calculateApr(money, borrowOrderDao.findTerm(borrowOrder.getId()));
        Integer intoMoney = money - fee.get("loanApr");
        Date date = new Date();
        BorrowOrder bo1 = new BorrowOrder();
        bo1.setId(borrowOrder.getId());
        bo1.setMoneyAmount(money);
        bo1.setLoanInterests(fee.get("loanApr"));
        bo1.setIntoMoney(intoMoney);
        borrowOrderDao.updateByPrimaryKeySelective(bo1);

        // 计算保存到公司的服务费
        Integer loan_interests = bo1.getLoanInterests() * fee_company / 100;
        BorrowOrderLoan loan = new BorrowOrderLoan();
        loan.setAssetOrderId(borrowOrder.getId());
        loan.setLoanInterests(loan_interests);
        loan.setUpdatedAt(date);
        borrowOrderLoanDao.updateByOrderId(loan);
        // 计算保存到个人的服务费
        Integer interests_p = bo1.getLoanInterests() * fee_person / 100;
        BorrowOrderLoanPerson personLoan = new BorrowOrderLoanPerson();
        personLoan.setAssetOrderId(borrowOrder.getId());
        personLoan.setLoanInterests(interests_p);
        personLoan.setUpdatedAt(date);
        borrowOrderLoanPersonDao.updateByOrderId(personLoan);
        User upd = new User();
        upd.setId(userId + "");
        upd.setAmountAvailable(0 + "");
        upd.setAmountMax(money + "");
        log.info("userId=:{}money is not pattern,new max is:{},available is 0", userId, money);
        userDao.updateByPrimaryKeyUser(upd);
        result.put("code", Status.SUCCESS.getName());
        result.put("msg", Status.SUCCESS.getValue());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrder> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrder");
        return paginationDao.findPage("findParams", "findParamsCount", params, "web");
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderLoan> findBorrowFreeBPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderLoan");
        return paginationDao.findPage("findParams", "findParamsCount", params, "web");
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderLoan> findBorrowFreeBStatisPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderLoan");
        return paginationDao.findPage("findStatisParams", "findParamsStatisCount", params, "web");
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCStatisPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderLoanPerson");
        return paginationDao.findPage("findStatisParams", "findParamsStatisCount", params, "web");
    }

    @Override
    public Integer findBorrowFreeBCount(HashMap<String, Object> params) {
        return borrowOrderLoanDao.findParamsCount(params);
    }

    @Override
    public Integer findBorrowFreeBStatisCount(HashMap<String, Object> params) {

        return borrowOrderLoanDao.findParamsStatisCount(params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderLoanPerson");
        return paginationDao.findPage("findParams", "findParamsCount", params, "web");
    }

    @Override
    public Integer findBorrowFreeCCount(HashMap<String, Object> params) {

        return borrowOrderLoanPersonDao.findParamsCount(params);
    }

    @Override
    public Integer findBorrowFreeCStatisCount(HashMap<String, Object> params) {
        // TODO Auto-generated method stub
        return borrowOrderLoanPersonDao.findParamsStatisCount(params);
    }

    @Override
    public int findParamsCount(HashMap<String, Object> params) {
        return borrowOrderDao.findParamsCount(params);
    }

    @Override
    public Map<String, Object> saveLoan(Map<String, String> params, User user) {
        Map<String, Object> result = new HashMap<>();

        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", user.getId()); //
        param.put("type", "2"); // 借记卡
        List<UserCardInfo> cardInfo = userBankDao.findUserCardByUserId(param);
        UserCardInfo info = new UserCardInfo();
        if (cardInfo != null && cardInfo.size() > 0) {
            info = cardInfo.get(0);
        } else {
            result.put("code", Status.FAILD.getName());
            result.put("msg", "请先绑定银行卡");
            return result;
        }
        // 借款服务费率走向，用户拆分服务费
        Integer fee_company = 0; // 公司
        Integer fee_person = 0; // 个人
        // 获取滞纳金
        HashMap<String, Object> sys = new HashMap<>();
        sys.put("sysType", "SYS_FEE");
        List<BackConfigParams> configParams = backConfigParamsDao.findParams(sys);
        Integer lateApr = 0;
        for (BackConfigParams cp : configParams) {
            if ("fee_lateapr".equals(cp.getSysKey())) {
                lateApr = Integer.parseInt(cp.getSysValue());
            }
            if ("fee_company".equals(cp.getSysKey())) {
                fee_company = Integer.parseInt(cp.getSysValue());
            }
            if ("fee_person".equals(cp.getSysKey())) {
                fee_person = Integer.parseInt(cp.getSysValue());
            }
        }
        // 查询银行信息
        Map<String, String> bankInfo = userBankDao.selectByPrimaryKey(info.getBank_id());
        Integer bank_iscmb = 2; // 是否是招商银行 ，默认：1：是，2：否
        if (bankInfo != null && "0308".equals(bankInfo.get("bankCode"))) {
            bank_iscmb = 1;
        }
        // 计算服务费率
        Integer money = Integer.parseInt(params.get("money")) * 100; // 借款金额
        // ，单位：分
        Map<String, Integer> fee = this.calculateApr(money, Integer.parseInt(params.get("period")));
        Integer intoMoney = money - fee.get("loanApr");
        Date date = new Date();
        BorrowOrder bo = new BorrowOrder();
        bo.setUserId(Integer.parseInt(user.getId()));
        bo.setOutTradeNo(GenerateNo.nextOrdId());
        bo.setMoneyAmount(money);
        bo.setApr(fee.get("apr"));
        bo.setLoanInterests(fee.get("loanApr"));
        bo.setIntoMoney(intoMoney);
        bo.setLoanTerm(Integer.parseInt(params.get("period")));
        bo.setCreatedAt(date);
        bo.setUpdatedAt(date);
        bo.setOrderTime(date);
        bo.setLateFeeApr(lateApr);
        bo.setReceiveCardId(info.getBank_id());
        bo.setUserPhone(user.getUserPhone());
        bo.setRealname(user.getRealname());
        bo.setBankNumber(bankInfo.get("bankNumber"));
        bo.setCardNo(info.getCard_no());
        bo.setBankIscmb(bank_iscmb);
        bo.setYurref(GenerateNo.payRecordNo("A"));
        borrowOrderDao.insertSelective(bo);

        // 计算保存到公司的服务费
        Integer loan_interests = (Integer) bo.getLoanInterests() * fee_company / 100;
        BorrowOrderLoan loan = new BorrowOrderLoan();
        loan.setUserId(Integer.parseInt(user.getId()));
        loan.setAssetOrderId(bo.getId());
        loan.setLoanInterests(loan_interests);
        // loan.setYurref(GenerateNo.payRecordNo("B"));
        loan.setCreatedAt(date);
        loan.setUpdatedAt(date);
        borrowOrderLoanDao.insertSelective(loan);
        // 计算保存到个人的服务费
        Integer interests_p = (Integer) bo.getLoanInterests() * fee_person / 100;
        BorrowOrderLoanPerson personLoan = new BorrowOrderLoanPerson();
        personLoan.setUserId(Integer.parseInt(user.getId()));
        personLoan.setAssetOrderId(bo.getId());
        personLoan.setLoanInterests(interests_p);
        // personLoan.setYurref(GenerateNo.payRecordNo("C"));
        personLoan.setCreatedAt(date);
        personLoan.setUpdatedAt(date);
        borrowOrderLoanPersonDao.insertSelective(personLoan);

        Integer amountAvailable = Integer.parseInt(user.getAmountAvailable());
        User upd = new User();
        upd.setAmountAvailable(String.valueOf(amountAvailable - money));
        upd.setId(user.getId());
        userDao.updateByPrimaryKeyUser(upd);

        Integer addAmount = 0;
        Map<String, BigDecimal> userLimit = userLimitRecordDao.countAddAmount(Integer.parseInt(user.getId()));
        if (userLimit.get("add_amount") != null) {
            addAmount = userLimit.get("add_amount").intValue();
        }
        RiskCreditUser risk = new RiskCreditUser();
        risk.setUserId(Integer.parseInt(user.getId()));
        risk.setAssetId(bo.getId());
        risk.setCardNum(user.getIdNumber());
        risk.setUserName(user.getRealname());
        risk.setAge(user.getUserAge());
        if ("男".equals(user.getUserSex())) {
            risk.setSex(1);
        } else if ("女".equals(user.getUserSex())) {
            risk.setSex(2);
        }
        risk.setUserPhone(user.getUserPhone());

        risk.setAmountAddsum(new BigDecimal(addAmount / 100.00));
        risk.setMoneyAmount(new BigDecimal(bo.getMoneyAmount() / 100.00));
        borrowOrderDao.insertRiskUser(risk);

        result.put("code", Status.SUCCESS.getName());
        result.put("msg", Status.SUCCESS.getValue());
        result.put("orderId", bo.getId());
        return result;
    }

    /**
     * 计算服务费 money 用户借款额度 单位：分
     */
    @Override
    public Map<String, Integer> calculateApr(Integer money, Integer period) {
        Map<String, Integer> result = new HashMap<>();
        double apr = 0;
        Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.SYS_FEE);
        String periodTypes = keys.get("period_type_isopen");
        try {

            String periodTypesArr[] = periodTypes.split(";");
            for (String p : periodTypesArr) {
                String periods[] = p.split(":");
                if (period.toString().equals(periods[0])) {
                    apr = Double.valueOf(periods[2]);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("judge periodsTypeIsOpen error:{}", e);
            // 服务费费率，单位：万分之
            if (7 == period) {
                apr = Constant.RATE_MIN;
            } else if (14 == period) {
                apr = Constant.RATE_MAX;
            }
        }
        Integer loanApr = 0; // 服务费
        loanApr = (int) (money * apr);
        result.put("loanApr", loanApr);
        result.put("apr", (int) (apr * 10000)); // 费率由百分比转换成万分比
        return result;
    }

    @Override
    public void authBorrowState(BorrowOrder borrowOrder) {

        BorrowOrder borrowOrderR = borrowOrderDao.selectByPrimaryKey(borrowOrder.getId());

        // ======================================以下状态需要第三方回调通知放款结果调用，见下方方法creditReCallBack=========================================
        if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_HKZ.intValue()) {// 放款成功
            Date fkDate = new Date();
            borrowOrder.setLoanTime(fkDate);
            borrowOrder.setLoanEndTime(DateUtil.addDay(fkDate, borrowOrderR.getLoanTerm()));// 放款时间加上借款期限
            Repayment repayment = new Repayment();
            repayment.setUserId(borrowOrderR.getUserId());
            repayment.setAssetOrderId(borrowOrderR.getId());
            repayment.setRepaymentAmount(Long.valueOf(borrowOrderR.getMoneyAmount()));
            repayment.setLateFeeApr(borrowOrderR.getLateFeeApr());
            repayment.setRepaymentedAmount(0L);
            repayment.setRepaymentPrincipal(Long.valueOf(borrowOrderR.getIntoMoney()));
            repayment.setRepaymentInterest(Long.valueOf(borrowOrderR.getLoanInterests()));

            repayment.setRepaymentTime(DateUtil.addDay(fkDate, borrowOrderR.getLoanTerm()));// 放款时间加上借款期限
            repayment.setCreatedAt(fkDate);
            repayment.setUpdatedAt(fkDate);
            repayment.setStatus(borrowOrder.getStatus());
            repaymentDao.insert(repayment);

        }

        // ===============================================================================

        // borrowOrderService.updateById(borrowOrder);
        borrowOrderDao.updateByPrimaryKeySelective(borrowOrder);
        // 放款审核通过，需要完善判断，防止前端修改状态
        if (borrowOrder.getStatus().intValue() == BorrowOrder.STATUS_FKZ) {

            log.info("调用第三方放款接口");
            // 调用第三方去放款

        }

    }

    @Override
    public void addUserLimit(User user) {
        // user=new User();
        // user.setId("28");
        // user=userDao.searchByUserid(28);
        log.error("begin add userLimit userId::{}", user.getId());
        Integer tgApr = 0;// 本次提额百分比，7天（sevenAmountaddFee%）、14天(fourteenAmountaddFee%)
        Integer tgJs = 0;// 提额计算提额金额的基数
        Integer tgMoneyType = 0;// 提额等级
        Integer addAmount = 0;// 本次提额金额( (tgJs * tgApr / 100))
        Date lastTeDate = null;// 上次提额时间
        Integer sucCount = 0;
        Integer sucAmount = 0;
        Integer normCount = 0;
        Integer normAmount = 0;// 累计还款金额
        try {
            if (user != null) {

                Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.SYS_FEE);
                // 7天借款额度增加比例
                Integer sevenAmountaddFee = Integer.parseInt(keys.get("seven_amountadd_fee"));
                // 14天借款额度增加比例
                Integer fourteenAmountaddFee = Integer.parseInt(keys.get("fourteen_amountadd_fee"));
                // 系统最大额度
                Integer sysAmountMax = Integer.parseInt(keys.get("max_amount_sys"));
                HashMap<String, Object> params = new HashMap<String, Object>();

                params.put("userId", user.getId());
                params.put("statusList", Arrays.asList(BorrowOrder.STATUS_YHK));
                List<BorrowOrder> normOrders = borrowOrderDao.findParams(params);
                if (normOrders != null && normOrders.size() > 0) {
                    // tgApr = normOrders.get(0).getLoanTerm() == 7 ? 3 : 5;
                    tgApr = normOrders.get(0).getLoanTerm() == 7 ? sevenAmountaddFee : fourteenAmountaddFee;// 2017-02-15
                    // zjb
                    for (BorrowOrder bor : normOrders) {
                        normAmount += bor.getMoneyAmount();
                    }

                    normCount = normOrders.size();
                }
                if (!(normAmount < 100000)) {
                    tgMoneyType = normAmount / 100000;// 获取本次提额等级

                    params.clear();
                    params.put("userId", user.getId());
                    params.put("status", UserLimitRecord.STATUS_PASS_SUCC);
                    List<UserLimitRecord> userlimits = userLimitRecordDao.findListBayParams(params);
                    // 已经有过的提额等级
                    ArrayList<Integer> hasTe = new ArrayList<Integer>();
                    if (userlimits != null && userlimits.size() > 0) {
                        lastTeDate = userlimits.get(0).getUpdatedAt();
                        for (UserLimitRecord bor : userlimits) {
                            hasTe.add(bor.getAddReasonType());
                        }
                    }
                    // 历史提额等级中不包含本次提额等级
                    if (!hasTe.contains(tgMoneyType) && tgApr > 0 && tgMoneyType > 0) {
                        sucAmount = normAmount;
                        sucCount = normCount;

                        params.clear();
                        params.put("userId", user.getId());
                        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_YQYHK));
                        List<BorrowOrder> yqOrders = borrowOrderDao.findParams(params);
                        if (yqOrders != null && yqOrders.size() > 0) {
                            for (BorrowOrder bor : yqOrders) {
                                sucAmount += bor.getMoneyAmount();
                            }
                            sucCount += yqOrders.size();
                        }
                        tgJs = tgMoneyType * 100000;
                        addAmount = (tgJs * tgApr / 100);
                        Integer oldAmountMax = Integer.parseInt(user.getAmountMax());
                        Integer newAmountMax = oldAmountMax + addAmount;

                        if (newAmountMax > sysAmountMax) {
                            log.info("addUserLimit  param old:{},more then sysAmountMax:{},change to sysAmountMax", newAmountMax, sysAmountMax);
                            newAmountMax = sysAmountMax;
                        }
                        if (newAmountMax > oldAmountMax) {
                            Date nowDate = new Date();
                            UserLimitRecord record = new UserLimitRecord();
                            record.setUserId(Integer.parseInt(user.getId()));
                            record.setCreateAt(nowDate);
                            record.setUpdatedAt(nowDate);
                            record.setAddAmount(addAmount);
                            record.setAddReasonType(tgMoneyType);
                            record.setLastApplyAt(lastTeDate);
                            record.setStatus(UserLimitRecord.STATUS_PASS_SUCC);
                            record.setOldAmountMax(oldAmountMax);
                            record.setAuditUser("系统");
                            record.setNewAmountMax(newAmountMax);
                            record.setRemark("累计正常还款" + normAmount / 100 + "元,系统自动提额" + addAmount / 100 + "元");

                            record.setRepaymentSuccCount(sucCount);
                            record.setRepaymentSuccAmount(sucAmount);
                            record.setRepaymentNormCount(normCount);
                            record.setRepaymentNormAmount(normAmount);
                            record.setRealname(user.getRealname());
                            record.setUserPhone(user.getUserPhone());
                            userLimitRecordDao.insertSelective(record);

                            User newUser = new User();
                            newUser.setId(user.getId());
                            newUser.setAmountMax(String.valueOf(record.getNewAmountMax()));
                            newUser.setAmountAvailable(String.valueOf(Integer.valueOf(user.getAmountAvailable()) + addAmount));
                            newUser.setAmountAddsum(String.valueOf(Integer.valueOf(user.getAmountAddsum()) + addAmount));
                            newUser.setUpdateTime(nowDate);
                            userDao.updateByPrimaryKeyUser(newUser);
                            log.info("addUserLimit sucess 提额成功！");
                            // 更新个人信息缓存
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("userId", user.getId());
                            infoIndexService.changeUserAmount(map);

                            final String userPhone = user.getUserPhone();
                            final Integer normAmountT = normAmount / 100;
                            final Integer addAmountT = addAmount / 100;

                            // ThreadPool.getInstance().run(new Runnable() {
                            //
                            // @Override
                            // public void run() {
                            // // 发送提额短信
                            // SendSmsUtil.sendSmsDiyCL(userPhone, "恭喜您已经正常还款累计" +
                            // normAmountT + "元，获得提额：" + addAmountT +
                            // "元，请继续保持良好的还款习惯！");
                            // }
                            // });
                            ThreadPool3.getInstance().run(() -> {
                                try {
                                    SendSmsUtil.sendSmsDiyCL(userPhone, SendSmsUtil.templateld44634, normAmountT + "##提额：" + addAmountT + "元");
                                }catch (Exception e){
                                    log.error("send sms error:{}",e);
                                }
                            });

                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("addUserLimit error 计算提额错误:{}", e);
        }

    }

    @Override
    public void changeUserLimit(HashMap<String, Object> mapParam) {
        Integer newAmountMax = Integer.parseInt(String.valueOf(mapParam.get("newAmountMax")));
        // 系统最大额度
        Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.SYS_FEE);
        Integer sysAmountMax = Integer.parseInt(keys.get("max_amount_sys"));
        if (newAmountMax > sysAmountMax) {
            log.info("changeUserLimit  param old:{},more then sysAmountMax:{},change to sysAmountMax", newAmountMax, sysAmountMax);
            newAmountMax = sysAmountMax;
        }

        log.info("begin change userLimit userId::{}", mapParam.get("userId"));
        UserLimitRecord record = new UserLimitRecord();
        record.setUserId(Integer.parseInt(String.valueOf(mapParam.get("userId"))));
        record.setNewAmountMax(newAmountMax);
        log.info(mapParam.get("userId") + "  risk send amount " + newAmountMax);
        User user = userDao.searchByUserid(record.getUserId());
        log.info(mapParam.get("userId") + " read db amount " + user.getAmountMax());

        Integer tgMoneyType = 0;// 提额等级0为风控提额
        Integer addAmount = 0;// 本次提额金额( (tgJs * tgApr / 100))

        try {
            if (user != null) {

                addAmount = record.getNewAmountMax() - Integer.parseInt(user.getAmountMax());
                log.info("计算额度  传递过来的额度" + newAmountMax + "用户以前的额度" + user.getAmountMax() + "addAmount:" + addAmount + "   ");
                if (addAmount != 0) {

                    Date lastTeDate = null;// 上次提额时间
                    HashMap<String, Object> params = new HashMap<>();
                    params.clear();
                    params.put("userId", user.getId());
                    params.put("status", UserLimitRecord.STATUS_PASS_SUCC);
                    //操作表 user_limit_record   查找 status=1（1审核通过）
                    List<UserLimitRecord> userlimits = userLimitRecordDao.findListBayParams(params);
                    // 已经有过的提额等级
                    if (userlimits != null && userlimits.size() > 0) {
                        lastTeDate = userlimits.get(0).getUpdatedAt();

                    }

                    Date nowDate = new Date();
                    record = new UserLimitRecord();
                    record.setUserId(Integer.parseInt(user.getId()));
                    record.setCreateAt(nowDate);
                    record.setUpdatedAt(nowDate);
                    record.setAddAmount(addAmount);
                    record.setAddReasonType(tgMoneyType);
                    record.setLastApplyAt(lastTeDate);
                    record.setStatus(UserLimitRecord.STATUS_PASS_SUCC);
                    record.setOldAmountMax(Integer.parseInt(user.getAmountMax()));
                    record.setAuditUser("风控");
                    record.setNewAmountMax(Integer.parseInt(user.getAmountMax()) + addAmount);
                    record.setRemark("人工提额" + addAmount / 100 + "元");
                    record.setRealname(user.getRealname());
                    record.setUserPhone(user.getUserPhone());

                    //操作表 user_limit_record
                    userLimitRecordDao.insertSelective(record);

                    User newUser = new User();
                    newUser.setId(user.getId());
                    newUser.setAmountMax(String.valueOf(record.getNewAmountMax()));
                    //newUser.setAmountAvailable(String.valueOf(Integer.valueOf(user.getAmountAvailable()) + addAmount));
                    newUser.setAmountAvailable(String.valueOf(record.getNewAmountMax()));
                    newUser.setAmountAddsum(String.valueOf(Integer.valueOf(user.getAmountAddsum()) + addAmount));
                    newUser.setUpdateTime(nowDate);
                    //操作表user_info  用户申请借款成功后修改用户可借额度
                    userDao.updateByPrimaryKeyUser(newUser);
                    log.info("changeUserLimit sucess 额度修改成功！");
                    // 更新个人信息缓存
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", user.getId());
                    //更新用户额度
                    infoIndexService.changeUserAmount(map);
                    /*final String userPhone = user.getUserPhone();
                    final Integer amountMax = Integer.parseInt(newUser.getAmountMax()) / 100;
                    if (amountMax > 0) {
                        ThreadPool.getInstance().run(() -> {
                            // 发送提额短信
                            try{
                                SendSmsUtil.sendSmsDiyCL(userPhone, SendSmsUtil.templateld44635, String.valueOf(amountMax));

                            }catch (Exception e){
                                log.error("send sms error:{}",e);
                            }
                        });
                    }*/
                }

            } else {

                log.info("begin change userLimit user not  exists:{}", mapParam.get("userId"));
            }
        } catch (Exception e) {
            log.error("changeUserLimit error 额度修改错误:{}", e);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<UserLimitRecord> finduserLimitPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "UserLimitRecord");
        return paginationDao.findPage("findListBayParams", "findParamsCount", params, "web");
    }

    @Override
    public BorrowOrder selectBorrowOrderUseId(Integer userId) {
        return borrowOrderDao.selectBorrowOrderUseId(userId);
    }

    /**
     * 检查当前用户是否存在未还款完成的订单
     *
     * @return 1：是；0：否
     */
    @Override
    public Integer checkBorrow(Integer userId) {
        Integer result = 0;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_DCS,
                // BorrowOrder.STATUS_JSJJ,
                BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_FSTG, BorrowOrder.STATUS_HKZ, BorrowOrder.STATUS_FKZ, BorrowOrder.STATUS_FKSB,
                BorrowOrder.STATUS_BFHK, BorrowOrder.STATUS_YYQ, BorrowOrder.STATUS_YHZ));
        List<BorrowOrder> normOrders = borrowOrderDao.findParams(params);
        if (normOrders != null && normOrders.size() > 0) {
            result = 1;
        }
        return result;
    }

    @Override
    public void updateRiskCreditUserById(com.info.risk.pojo.RiskCreditUser riskCreditUser) {

        borrowOrderDao.updateRiskCreditUserById(riskCreditUser);
    }

    @Override
    public List<BorrowOrder> findByUserId(Integer userId) {
        return borrowOrderDao.findByUserId(userId);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void requestPaysForBatch(List<BorrowOrder> borrowOrders) {

        try {
            ServiceResult serviceResult = subPaysForBatch(borrowOrders, OutOrders.act_NTQRYEBP_A);
            String paystatusAll = serviceResult.getCode();
            String payRemarkAll = serviceResult.getMsg();
            // 更新借款表
            Date nowDate = new Date();

            for (BorrowOrder bo : borrowOrders) {
                BorrowOrder borrowOrderNew = new BorrowOrder();
                borrowOrderNew.setId(bo.getId());
                // 网络失败不改变付款状态，自动重新打款
                if (!paystatusAll.equals(BorrowOrder.SUB_NERWORK_ERROR)) {
                    borrowOrderNew.setPaystatus(paystatusAll);
                }
                borrowOrderNew.setPayRemark(payRemarkAll);
                borrowOrderNew.setUpdatedAt(nowDate);
                borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);
            }

        } catch (Exception e) {
            log.error("requestPaysForBatch NTQRYEBP_A Loan  error:{}", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestPaysForSimgle(BorrowOrder borrowOrder) {

        try {
            ServiceResult serviceResult = subPaysForSimgle(borrowOrder, OutOrders.act_NTQRYEBP_A);
            String paystatusAll = serviceResult.getCode();
            String payRemarkAll = serviceResult.getMsg();
            // 更新借款表
            Date nowDate = new Date();

            BorrowOrder borrowOrderNew = new BorrowOrder();
            borrowOrderNew.setId(borrowOrder.getId());
            // 网络失败不改变付款状态，自动重新打款
            if (!paystatusAll.equals(BorrowOrder.SUB_NERWORK_ERROR)) {
                borrowOrderNew.setPaystatus(paystatusAll);
            }
            borrowOrderNew.setPayRemark(payRemarkAll);
            borrowOrderNew.setUpdatedAt(nowDate);
            borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);

        } catch (Exception e) {
            log.error("requestPaysForSimgle NTQRYEBP_A Loan  error:{}", e);
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void requestPaysNotCmbUser(List<BorrowOrder> borrowOrders) {
        try {
            ServiceResult serviceResult = subPayNotCmb(borrowOrders, OutOrders.act_NTQRYEBP_A);
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
                    borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);
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
                    borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);
                }
            }

        } catch (Exception e) {
            log.error("requestPaysNotCmbUser NTQRYEBP_A Loan  error:{}", e);
        }

    }

    @Override
    public void requestPaysCmbUser(List<BorrowOrder> borrowOrders) {
        try {

            ServiceResult serviceResult = subPayCmb(borrowOrders, OutOrders.act_AgentRequest_A);
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
                // borrowOrderNew.setYurref(borrowOrder.getYurref());
                // if (paystatus.equals(BorrowOrder.SUB_PAY_SUCC)) {//
                // 如果等于成功，更新还款表
                // borrowOrderNew.setLoanTime(fkDate);
                // borrowOrderNew.setStatus(BorrowOrder.STATUS_HKZ);
                // bo.setLoanTime(fkDate);
                // bo.setLoanEndTime(DateUtil.addDay(fkDate,
                // bo.getLoanTerm()));// 放款时间加上借款期限
                // // 放款成功插入还款记录
                // bo.setPaystatus(borrowOrderNew.getPaystatus());
                // bo.setStatus(BorrowOrder.STATUS_HKZ);
                // bo.setLoanTime(fkDate);
                // bo.setLoanEndTime(borrowOrderNew.getLoanEndTime());//
                // 放款时间加上借款期限
                // repaymentService.insertByBorrorOrder(bo);
                // }
                borrowOrderDao.updateByPrimaryKeySelective(borrowOrderNew);

            }

        } catch (Exception e) {
            log.error("requestPay   Loan  error:{}", e);
        }

    }


    /**
     * 判断当前7天、14天用哪个账户打款
     */
    public Integer getCurrentLoanAccout(Integer loanTerm, String subType) {
        Integer curLoanAccount = null;
        if (subType.equals(OutOrders.act_NTQRYEBP_A) || subType.equals(OutOrders.act_AgentRequest_A)) {
            curLoanAccount = BorrowOrder.LOAN_ACCOUNT1;
            LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams(BackConfigParams.FEE_ACCOUNT);
            String whichLoanAccount = mapFee.get("whichLoanAccount");
            if (Integer.parseInt(whichLoanAccount) == BorrowOrder.LOAN_ACCOUNT2) {
                LinkedHashMap<String, String> mapFeeZCM = SysCacheUtils.getConfigParams("ASSEST_ZCM");
                String curLoanTermIsOpen = mapFeeZCM.get("LOANISOPEN_ZCM_" + loanTerm);
                if (curLoanTermIsOpen.equals("1")) {
                    curLoanAccount = BorrowOrder.LOAN_ACCOUNT2;
                }
            }
        }
        return curLoanAccount;
    }

    @Override
    public ServiceResult subPayNotCmb(List<BorrowOrder> borrowOrders, String subType) {
        ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "subPayNotCmb未知异常，请稍后重试！");
        try {
            //获取放款账户类型
            Integer curLoanAccount = getCurrentLoanAccout(borrowOrders.get(0).getLoanTerm(), subType);
            //获取放款账户信息
            HashMap<String, String> hLAccountInfo = getHLAccountInfo(curLoanAccount);
            //生成放款请求报文正式 付款NTIBCOPR
            String requestParams = HttpRequestCMBHL.getRequestNTIBCOPRStr(borrowOrders, hLAccountInfo);
            // logger.error("requestParams  Loan  :"+subType + requestParams);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(GenerateNo.nextOrdId());//订单编号
            orders.setOrderType(OutOrders.orderType_cmb);//订单类型
            orders.setReqParams(requestParams);//请求参数
            orders.setAddTime(new Date());//请求时间
            orders.setAct(subType);//操作
            orders.setStatus(OutOrders.STATUS_WAIT);//请求状态
            orders.setTablelastName(tablelastName);//
            outOrdersService.insertByTablelastName(orders);

            String goUrl = hLAccountInfo.get("HL_URL");
            // 连接前置机，发送请求报文，获得返回报文
            String result = HttpRequestCMBHL.sendRequest(requestParams, goUrl);
            // logger.error("resutParams  Loan  :"+subType + result);
            // 更新订单
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());

            // ordersNew.setOrderNo(borrowOrder.getOutTradeNo());
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);
            // 网络异常特殊判断
            if (result.startsWith("NetworkError")) {
                serviceResult.setCode(BorrowOrder.SUB_NERWORK_ERROR);
                serviceResult.setMsg(result);
                return serviceResult;
            }
            // 处理返回的结果
            serviceResult = HttpRequestCMBHL.processPayResult(result);
            log.info("result Loan Loan   " + subType + serviceResult.getCode() + ">>" + serviceResult.getMsg());

        } catch (Exception e) {
            log.error("requestPay   Loan  error......", e);
        } finally {

            return serviceResult;
        }
    }

    @Override
    public ServiceResult subPayCmb(List<BorrowOrder> borrowOrders, String subType) {
        ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "subPayCmb未知异常，请稍后重试！");
        try {
            Integer curLoanAccount = getCurrentLoanAccout(borrowOrders.get(0).getLoanTerm(), subType);
            HashMap<String, String> dFAccountInfo = getDFAccountInfo(curLoanAccount);
            String requestParams = HttpRequestCMBDF.getRequestNTIBCOPRStr(borrowOrders, dFAccountInfo);
            // logger.error("requestParams  Loan  :"+subType + requestParams);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(GenerateNo.nextOrdId());
            orders.setOrderType(OutOrders.orderType_cmb);
            orders.setReqParams(requestParams);
            orders.setAddTime(new Date());
            orders.setAct(subType);
            orders.setStatus(OutOrders.STATUS_WAIT);
            orders.setTablelastName(tablelastName);
            outOrdersService.insertByTablelastName(orders);
            String goUrl = dFAccountInfo.get("DF_URL");
            // 连接前置机，发送请求报文，获得返回报文
            String result = HttpRequestCMBDF.sendRequest(requestParams, goUrl);
            // logger.error("requestParams  Loan  :"+subType + result);
            // 更新订单
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());

            // ordersNew.setOrderNo(borrowOrder.getOutTradeNo());
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);
            // 网络异常特殊判断
            if (result.startsWith("NetworkError")) {
                serviceResult.setCode(BorrowOrder.SUB_NERWORK_ERROR);
                serviceResult.setMsg(result);
                return serviceResult;
            }
            // 处理返回的结果
            serviceResult = HttpRequestCMBDF.processPayResult(result);
            log.info("requestParams  Loan  :" + subType + serviceResult.getCode() + ">>" + serviceResult.getMsg());

        } catch (Exception e) {
            log.error("requestPay   Loan  error......:{}", e);
        } finally {

            return serviceResult;
        }
    }

    @Override
    public ServiceResult subPaysForBatch(List<BorrowOrder> borrowOrders, String subType) {
        ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "subPayNotCmb未知异常，请稍后重试！");
        try {
            String callbackUrl = "http://oss.jx-money.com/back/backBorrowOrder/borrowCallBack";
            //生成放款请求字符串
            String requestParams = HttpRequestYLDF.getRequestBatchStr(borrowOrders, callbackUrl);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(GenerateNo.nextOrdId());//订单编号
            orders.setOrderType(OutOrders.orderType_cmb);//订单类型
            orders.setReqParams(requestParams);//请求参数
            orders.setAddTime(new Date());//请求时间
            orders.setAct(subType);//操作
            orders.setStatus(OutOrders.STATUS_WAIT);//请求状态
            orders.setTablelastName(tablelastName);//
            outOrdersService.insertByTablelastName(orders);

            // 连接前置机，发送请求报文，获得返回报文
            String result = HttpRequestYLDF.batchAgentPay(requestParams);
            log.info("resutParams  Loan  ::{};{}", subType, new String(result.getBytes("gbk")));
            // 更新订单
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());

            // ordersNew.setOrderNo(borrowOrder.getOutTradeNo());
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);
            // 网络异常特殊判断
            if (result.startsWith("NetworkError")) {
                serviceResult.setCode(BorrowOrder.SUB_NERWORK_ERROR);
                serviceResult.setMsg(result);
                return serviceResult;
            }
            // 处理返回的结果
            serviceResult = HttpRequestYLDF.processPayResult(result);
            log.info("result Loan Loan   " + subType + serviceResult.getCode() + ">>" + serviceResult.getMsg());

        } catch (Exception e) {
            log.error("requestPayForBatch   Loan  error......:{}", e);
        } finally {

            return serviceResult;
        }
    }

    @Override
    public ServiceResult subPaysForSimgle(BorrowOrder borrowOrder, String subType) {
        ServiceResult serviceResult = new ServiceResult(BorrowOrder.SUB_ERROR, "subPayNotCmb未知异常，请稍后重试！");
        try {
            String callbackUrl = "http://oss.jx-money.com/back/backBorrowOrder/borrowCallBackSimgle";
            //生成放款请求字符串
            String requestParams = HttpRequestYLDF.getRequestSimgleStr(borrowOrder, callbackUrl);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(GenerateNo.nextOrdId());//订单编号
            orders.setOrderType(OutOrders.orderType_cmb);//订单类型
            orders.setReqParams(requestParams);//请求参数
            orders.setAddTime(new Date());//请求时间
            orders.setAct(subType);//操作
            orders.setStatus(OutOrders.STATUS_WAIT);//请求状态
            orders.setTablelastName(tablelastName);//
            outOrdersService.insertByTablelastName(orders);
            // 连接前置机，发送请求报文，获得返回报文
            String result = HttpRequestYLDF.simgleAgentPay(requestParams);
            // 更新订单
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());

            // ordersNew.setOrderNo(borrowOrder.getOutTradeNo());
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);
            // 网络异常特殊判断
            if (result.startsWith("NetworkError")) {
                serviceResult.setCode(BorrowOrder.SUB_NERWORK_ERROR);
                serviceResult.setMsg(result);
                return serviceResult;
            }
            log.info("result :{}", result);
            // 处理返回的结果
            serviceResult = HttpRequestYLDF.processPayResult(result);
            log.info("result Loan Loan   " + subType + serviceResult.getCode() + ">>" + serviceResult.getMsg());

        } catch (Exception e) {
            log.error("requestPayForSimgle   Loan  error......:{}", e);
        } finally {
            return serviceResult;
        }

    }

    @Override
    public ServiceResult addDocking(BorrowOrderChecking bo) {
        ServiceResult serviceResult = new ServiceResult(String.valueOf(BorrowOrderChecking.STATUS_TSSB), "系统异常");
        try {
            // logger.error("koudai docking begin");
            LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_KD");
            String userAccount = mapFee.get("userAccount_KD");
            String userPwd = mapFee.get("userPwd_KD");
            String url = mapFee.get("GO_URL_KD") + "interface/create-order";
            String id_number = bo.getIdNumber();
            String timestamp = String.valueOf(new Date().getTime() / 1000);
            String card_no = bo.getCardNo();
            String name = bo.getRealname();
            String phone = bo.getUserPhone();
            Integer counter_fee = bo.getLoanInterests();// 手续费,单位：分
            String loan_method = String.valueOf(bo.getLoanMethod());// 贷款方式(0:按天,1:按月,2:按年)
            Integer loan_term = bo.getLoanTerm();// 期限
            String apr = "0";// //贷款年化率利率
            Integer money_amount = bo.getMoneyAmount();// 贷款金额，单位：分
            String loan_interests = "0";// 总利息
            Integer repayment_amount = bo.getMoneyAmount();// 总还款金额,单位：分
            String repayment_principal = String.valueOf(bo.getIntoMoney());// 待还本金
            String repayment_interest = "0";// 利息
            String bank_id = bo.getBanksequence();

            String repayment_time = String.valueOf(bo.getLoanEndTime().getTime() / 1000);// 还款时间
            String order_time = String.valueOf(bo.getOrderTime().getTime() / 1000);
            String loan_time = String.valueOf(bo.getLoanTime().getTime() / 1000);
            String orderNo = GenerateNo.nextOrdId();

            Map<String, Object> objj = new HashMap<String, Object>();
            String sign = MD5coding.MD5(userAccount + MD5coding.MD5(userPwd) + MD5coding.MD5(id_number) + timestamp);
            objj.put("sign", sign);// /?????????????????????????md5(account+md5(pwd)+md5(id_number)+timestamp)
            objj.put("account", userAccount);
            objj.put("id_number", id_number);
            objj.put("timestamp", timestamp);
            // user_base用户必填信息
            Map<String, Object> user_base = new HashMap<String, Object>();
            user_base.put("id_number", id_number);
            user_base.put("name", name);
            user_base.put("phone", phone);
            // user_base.put("education_level", "3");// 学历非必填
            user_base.put("property", "男");
            user_base.put("type", "2");// 借款人类型(1:企业，2:个人)
            objj.put("user_base", user_base);
            // receive_card打款银行卡
            Map<String, Object> receive_card = new HashMap<String, Object>();
            receive_card.put("bank_id", bank_id);// 银行卡id
            receive_card.put("card_no", card_no);
            receive_card.put("phone", phone);
            receive_card.put("name", name);// 学历非必填
            objj.put("receive_card", receive_card);

            // debit_card扣款银行卡，不代扣可不传
            Map<String, Object> debit_card = new HashMap<String, Object>();
            debit_card.put("bank_id", bank_id);// 银行卡id
            debit_card.put("card_no", card_no);
            debit_card.put("phone", phone);
            objj.put("debit_card", debit_card);

            // order_base借款订单信息
            Map<String, Object> order_base = new HashMap<String, Object>();
            order_base.put("out_trade_no", orderNo);// 各厂商自己平台申请单号
            order_base.put("money_amount", money_amount);// 贷款金额，单位：分
            order_base.put("loan_method", loan_method);
            order_base.put("loan_term", loan_term);// 贷款期限,
            order_base.put("loan_interests", loan_interests);// 总共利息,单位：分
            order_base.put("apr", apr);// 贷款年化率利率
            order_base.put("order_time", order_time);// 下单时间时间戳表示
            order_base.put("loan_time", loan_time);// 放款时间时间戳表示
            order_base.put("counter_fee", counter_fee);// 手续费,单位：分

            objj.put("order_base", order_base);

            // repayment_base总还款信息
            Map<String, Object> repayment_base = new HashMap<String, Object>();
            repayment_base.put("repayment_type", 2);// 还款方式，0:等本等息,1:等额本息,2:一次性还款
            repayment_base.put("repayment_amount", repayment_amount);// 总还款金额,单位：分
            repayment_base.put("repayment_time", repayment_time);// 最迟还款日期时间戳
            repayment_base.put("period", 1);// 总还款期数
            repayment_base.put("repayment_principal", repayment_principal);// 还款本金,单位：分
            repayment_base.put("repayment_interest", repayment_interest);// 还款利息,单位：分
            objj.put("repayment_base", repayment_base);

            Map[] period_base = new Map[1];
            // period_base还款计划信息
            Map<String, Object> period_base1 = new HashMap<String, Object>();

            period_base1.put("plan_repayment_money", repayment_amount);// 预期还款金额,单位：分
            period_base1.put("plan_repayment_time", repayment_time);// 预期还款时间戳
            period_base1.put("period", 1);// 总还款期数
            period_base1.put("plan_repayment_principal", repayment_principal);// 还款本金,单位：分
            period_base1.put("plan_repayment_interest", repayment_interest);// 还款利息,单位：分\
            period_base1.put("apr", apr);// 还款利息,单位：分\
            period_base[0] = period_base1;
            objj.put("period_base", period_base);

            String paramsJosn = JSONUtil.beanToJson(objj);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(orderNo);
            orders.setOrderType(OutOrders.orderType_KD);
            // orders.setAssetOrderId(borrowOrder.getId());
            orders.setReqParams(paramsJosn);
            orders.setAddTime(new Date());
            orders.setAct("orderType_Order");
            orders.setStatus(OutOrders.STATUS_WAIT);
            orders.setTablelastName(tablelastName);
            outOrdersService.insertByTablelastName(orders);

            HashMap<String, Integer> params = new HashMap<String, Integer>();
            params.put("soketOut", 5000);
            params.put("connOut", 5000);

            String result = new WebClient().postJsonData(url, paramsJosn, null);
            if (result == null) {
                serviceResult.setMsg("连接超时！");
                return serviceResult;
            }
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());
            ordersNew.setOrderNo(orderNo);
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);
            // logger.error("koudai docking success");
            JSONObject jsonObj = null;
            try {
                jsonObj = JSONObject.fromObject(result);
            } catch (Exception e) {
                serviceResult.setMsg("请求地址错误导致返回结果解析错误");
                log.error("koudai docking  toJson error 请求地址错误导致返回结果解析错误:{} ", e);
            }

            String code = jsonObj.getString("code");
            if (code.equals("0")) {
                String outNo = jsonObj.getString("order_id");
                serviceResult.setExt(outNo);
                serviceResult.setMsg("推送成功");
                serviceResult.setCode(String.valueOf(BorrowOrderChecking.STATUS_TSCG));
            } else {
                String message = jsonObj.getString("message");
                serviceResult.setCode(String.valueOf(BorrowOrderChecking.STATUS_TSSB));
                serviceResult.setMsg(message);
            }
        } catch (Exception e) {
            log.error("koudai docking error:{} ", e);
        } finally {
            return serviceResult;
        }
    }

    @Override
    public Boolean loanAgainFee(String yurref, String Loantype) {
        Boolean flag = false;
        if (Loantype.equals("company")) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("yurref", yurref);
            List<BorrowOrderLoan> lists = borrowOrderLoanDao.findParams(params);
            if (lists != null && lists.size() > 0) {
                BorrowOrderLoan dd = lists.get(0);
                // 非打款成功、非初始状态
                if (!dd.getPayStatus().equals(BorrowOrder.SUB_PAY_SUCC) && !dd.getPayStatus().equals(BorrowOrder.SUB_PAY_CSZT)) {
                    BorrowOrderLoan record = new BorrowOrderLoan();
                    record.setYurref(yurref);
                    record.setPayStatus(BorrowOrder.SUB_PAY_CSZT);
                    record.setStatus(BorrowOrderLoan.DFK);
                    record.setPayRemark("等待重新放款");
                    record.setUpdatedAt(new Date());
                    borrowOrderLoanDao.updateByYurref(record);
                    flag = true;
                }

            }

        } else if (Loantype.equals("person")) {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("yurref", yurref);
            List<BorrowOrderLoanPerson> lists = borrowOrderLoanPersonDao.findParams(params);
            if (lists != null && lists.size() > 0) {
                BorrowOrderLoanPerson dd = lists.get(0);
                // 非打款成功、非初始状态
                if (!dd.getPayStatus().equals(BorrowOrder.SUB_PAY_SUCC) && !dd.getPayStatus().equals(BorrowOrder.SUB_PAY_CSZT)) {
                    BorrowOrderLoanPerson record = new BorrowOrderLoanPerson();
                    record.setYurref(yurref);
                    record.setPayStatus(BorrowOrder.SUB_PAY_CSZT);
                    record.setStatus(BorrowOrderLoan.DFK);
                    record.setUpdatedAt(new Date());
                    record.setPayRemark("重新放款：等待重新放款");
                    borrowOrderLoanPersonDao.updateByYurref(record);
                    flag = true;
                }

            }

        }
        return flag;
    }

    @Override
    public void addBorrowChecking(BorrowOrder bo) {
        try {
            Date nowDate = new Date();
            BorrowOrderChecking record = new BorrowOrderChecking();
            try {
                BeanUtils.copyProperties(record, bo);
            } catch (Exception e) {
                log.error("addBorrowChecking copyProperties error:{}", e);
            }
            record.setAssetOrderId(bo.getId());
            record.setBanksequence(BorrowOrderChecking.banksequenceMap.get(bo.getBankNumber()));
            record.setCreatedAt(nowDate);
            record.setUpdatedAt(nowDate);
            record.setStatus(BorrowOrderChecking.STATUS_DTS);
            record.setCapitalType(0);
            // 插入资产信息
            borrowOrderCheckingDao.insert(record);

            List<BorrowOrder> boNewList = new ArrayList<BorrowOrder>();
            BorrowOrder boNew = new BorrowOrder();
            boNew.setId(bo.getId());
            boNew.setRealname("上海富蜀商务信息咨询有限公司");
            boNew.setIdNumber("");
            boNew.setMoneyAmount(0);
            boNew.setCapitalType(BorrowOrder.LOAN_ACCOUNT1);
            boNew.setOperatorName(BorrowOrder.LOAN_ACCOUNTMap.get(BorrowOrder.LOAN_ACCOUNT1));
            boNewList.add(boNew);

            batchInsertBCI(boNewList);
        } catch (Exception e) {
            log.error("addBorrowChecking error:{}", e);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderChecking> findBorrowOrderCheckPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderChecking");
        return paginationDao.findPage("selectAll", "selectAllCount", params, "web");
    }

    @Override
    public Integer findBorrowOrderCheckCount(HashMap<String, Object> params) {

        return borrowOrderCheckingDao.selectAllCount(params);
    }

    @Override
    public Long findMoneyAmountSucSum(HashMap<String, Object> params) {
        return borrowOrderDao.findMoneyAmountSucSum(params);
    }

    @Override
    public Long findIntoMoneySucSum(HashMap<String, Object> params) {
        return borrowOrderDao.findIntoMoneySucSum(params);
    }

    @Override
    public Long findloanInterestsSucSumB(HashMap<String, Object> params) {
        return borrowOrderLoanDao.findloanInterestsSucSumB(params);
    }

    @Override
    public Long findloanInterestsSucSumC(HashMap<String, Object> params) {
        return borrowOrderLoanPersonDao.findloanInterestsSucSumC(params);
    }

    /**
     * 获取网银互联账户信息
     */
    @Override
    public HashMap<String, String> getHLAccountInfo(Integer curLoanAccount) {

        HashMap<String, String> params = new HashMap<>();
        try {
            LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams(BackConfigParams.FEE_ACCOUNT);

            String whichLoanAccount = mapFee.get("whichLoanAccount");
            if (curLoanAccount != null) {
                whichLoanAccount = String.valueOf(curLoanAccount);
            }
            String acountInfoS = mapFee.get("loanAccount_" + whichLoanAccount);
            JSONObject acountInfoJ = JSONObject.fromObject(acountInfoS);
            params.put("HL_LGNNAM", acountInfoJ.getString("HL_LGNNAM"));// 登陆用户名
            params.put("HL_ACCNBR", acountInfoJ.getString("HL_ACCNBR"));// 付款账号
            params.put("HL_CNVNBR", acountInfoJ.getString("HL_CNVNBR"));// 协议号
            params.put("HL_TRSTYP", acountInfoJ.getString("HL_TRSTYP"));// 业务类型编码
            params.put("HL_TRSCAT", acountInfoJ.getString("HL_TRSCAT"));// 业务种类编码
            params.put("HL_BUSCOD", acountInfoJ.getString("HL_BUSCOD"));// 业务类型(交易查询)
            params.put("HL_BUSMOD", acountInfoJ.getString("HL_BUSMOD"));// 业务模式
            params.put("HL_URL", acountInfoJ.getString("HL_URL"));// 互联-客户端地址
//			params.put("HL_ISOPEN", acountInfoJ.getString("HL_ISOPEN"));// 互联是否停用
            params.put("HL_ISOPEN", "1");// 互联是否停用
        } catch (Exception e) {
            log.error("getHLAccountInfo error:{}", e);
        }

        return params;
    }

    /**
     * 获取代发账户信息
     */
    @Override
    public HashMap<String, String> getDFAccountInfo(Integer curLoanAccount) {

        HashMap<String, String> params = new HashMap<>();
        try {

            LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams(BackConfigParams.FEE_ACCOUNT);

            String whichLoanAccount = mapFee.get("whichLoanAccount");
            if (curLoanAccount != null) {
                whichLoanAccount = String.valueOf(curLoanAccount);
            }

            String acountInfoS = mapFee.get("loanAccount_" + whichLoanAccount);
            JSONObject acountInfoJ = JSONObject.fromObject(acountInfoS);
            params.put("DF_LGNNAM", acountInfoJ.getString("DF_LGNNAM"));// 代发-登陆用户名
            params.put("DF_BBKNBR", acountInfoJ.getString("DF_BBKNBR"));// 代发-分行代码
            params.put("DF_DBTACC", acountInfoJ.getString("DF_DBTACC"));// 代发-银行账号
            params.put("DF_BUSCOD", acountInfoJ.getString("DF_BUSCOD"));// 业务类别
            params.put("DF_BUSMOD", acountInfoJ.getString("DF_BUSMOD"));// 业务模式编号
            params.put("DF_MODALS", acountInfoJ.getString("DF_MODALS"));// 业务模式名称
            params.put("DF_TRSTYP", acountInfoJ.getString("DF_TRSTYP"));// 交易代码
            params.put("DF_C_TRSTYP", acountInfoJ.getString("DF_C_TRSTYP"));// 交易代码名称
            params.put("DF_URL", acountInfoJ.getString("DF_URL"));// 代发-客户端地址
//			params.put("DF_ISOPEN", acountInfoJ.getString("DF_ISOPEN"));// 代发是否停用
            params.put("DF_ISOPEN", "1");// 代发是否停用
        } catch (Exception e) {
            log.error("getDFAccountInfo error:{}", e);
        }
        return params;
    }


    /**
     * 维护招财猫开关
     *
     * @param loanTerm 期限
     * @param isOpen   开关
     * @param nowDate  日期标识
     *                 没有开关或者
     */
    public void judgeZCMLoanDetail(int loanTerm, Integer isOpen, String nowDate, Integer MoneyAmount) {
        // 每次资金进入判断是否需要急需放款，包括7天放款开关、14天放款开关。总的放款账户切换
        try {
            // int loanTerm = bo.getLoanTerm();
            // String key = "ZCM_PACKAGE_NEED_" + loanTerm + "_" +
            // DateUtil.getDateFormat(nowDate, "yyyyMMdd");
            String key = "ZCM_PACKAGE_NEED_" + loanTerm + "_" + nowDate;
            String needmoney = jedisCluster.get(key);
            if (needmoney != null) {
                int totalAmount = Integer.parseInt(needmoney);
                if (isOpen != null && isOpen == 0) {
                    totalAmount = 0;
                } else {
                    totalAmount = totalAmount - (MoneyAmount / 100);
                }
                // 招财猫7天或者14天金额积满修改缓存对应值。
                if (!(totalAmount > 0)) {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("sysKey", "LOANISOPEN_ZCM_7");
                    List<BackConfigParams> LOANISOPEN_ZCM_7s = backConfigParamsService.findParams(params);
                    params.put("sysKey", "LOANISOPEN_ZCM_14");
                    List<BackConfigParams> LOANISOPEN_ZCM_14s = backConfigParamsService.findParams(params);
                    BackConfigParams LOANISOPEN_ZCM_7 = LOANISOPEN_ZCM_7s.get(0);// 7天放款开关
                    BackConfigParams LOANISOPEN_ZCM_14 = LOANISOPEN_ZCM_14s.get(0);// 14天放款开关
                    if (loanTerm == 7) {
                        LOANISOPEN_ZCM_7.setSysValue("0");
                    } else if (loanTerm == 14) {
                        LOANISOPEN_ZCM_14.setSysValue("0");
                    }
                    List<BackConfigParams> list = new ArrayList<BackConfigParams>();
                    list.add(LOANISOPEN_ZCM_14);
                    list.add(LOANISOPEN_ZCM_7);
                    if (!LOANISOPEN_ZCM_7.getSysValue().equals("1") && !LOANISOPEN_ZCM_14.getSysValue().equals("1")) {
                        params.clear();
                        params.put("sysKey", "whichLoanAccount");
                        List<BackConfigParams> bcfps = backConfigParamsService.findParams(params);
                        if (bcfps != null && bcfps.size() > 0) {
                            BackConfigParams whichLoanAccount = bcfps.get(0);
                            whichLoanAccount.setSysValue(String.valueOf(BorrowOrder.LOAN_ACCOUNT1));// 招财猫金额打满切换账户打款
                            list.add(whichLoanAccount);
                        }
                        log.info("judgeZCMLoanDetail   cmb  thisday  allCLose", nowDate);
                    }
                    backConfigParamsService.updateValue(list, null);
                    // 刷新系统缓存
                    new IndexInit().updateCache();
                }

            }
        } catch (Exception e) {
            log.error("ZCM judge 14or7 is full error:{}", e);
        }
    }

    @Override
    public void addBorrowCheckingExt(BorrowOrder bo) {

        try {
            Date nowDate = new Date();
            BorrowOrderCheckingExt record = new BorrowOrderCheckingExt();
            try {
                BeanUtils.copyProperties(record, bo);
            } catch (Exception e) {
                log.error("addBorrowCheckingExt copyProperties error:{}", e);
            }
            record.setAssetOrderId(bo.getId());
            record.setBanksequence(BorrowOrderChecking.banksequenceMap.get(bo.getBankNumber()));
            record.setCreatedAt(nowDate);
            record.setUpdatedAt(nowDate);
            record.setStatus(BorrowOrderChecking.STATUS_DTS);
            record.setAffiliationUkey(BorrowOrder.LOAN_ACCOUNT2);
            record.setSignStatus(0);// 默认未标记
            record.setCapitalType(0);// 默认谁也不属于
            // 插入资产信息
            borrowOrderCheckingExtDao.insertSelective(record);
            judgeZCMLoanDetail(bo.getLoanTerm(), null, DateUtil.getDateFormat(bo.getLoanTime(), "yyyyMMdd"), bo.getMoneyAmount());

        } catch (Exception e) {
            log.error("addBorrowCheckingExt error:{}", e);
        }

    }

    @Override
    public ServiceResult addDockingExtZCM(BorrowOrderCheckingExt bo) {
        ServiceResult serviceResult = new ServiceResult(String.valueOf(BorrowOrderChecking.STATUS_TSSB), "系统异常");
        try {
            // logger.error("koudai docking begin");
            LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");

            String oid_plat = mapFee.get("oid_plat_ZCM");
            String business_password = mapFee.get("business_password_ZCM");
            String business_code = mapFee.get("business_code_ZCM");
            String business_license = mapFee.get("business_license_ZCM");

            String url = mapFee.get("GO_URL_ZCM") + "x1/asset/assettradecreate";
            String id_number = bo.getIdNumber();
            String timestamp = DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
            String card_no = bo.getCardNo();
            String name = bo.getRealname();
            String phone = bo.getUserPhone();
			/*if(StringUtils.isNotBlank(phone)){
				phone=phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");  
			}*/
            String counter_fee = String.valueOf(bo.getLoanInterests() / 100);// 手续费,单位：元
            String loan_method = String.valueOf(bo.getLoanMethod());// 贷款方式(0:按天,1:按月,2:按年)
            String loan_term = String.valueOf(bo.getLoanTerm());// 期限
            String apr = "0";// //贷款年化率利率
            String money_amount = String.valueOf(bo.getMoneyAmount() / 100);// 贷款金额，单位：分
            String loan_interests = "0";// 总利息
            String repayment_amount = String.valueOf(bo.getMoneyAmount() / 100);// 总还款金额,单位：分
            String repayment_principal = String.valueOf(bo.getIntoMoney() / 100);// 待还本金
            String repayment_interest = "0";// 利息
            String bank_id = bo.getBanksequence();
            String bank_name = BorrowOrderChecking.banksequenceNameMap.get(bo.getBankNumber());

            String repayment_time = DateUtil.getDateFormat(bo.getLoanEndTime(), "yyyy-MM-dd HH:mm:ss");// 还款时间
            String order_time = DateUtil.getDateFormat(bo.getOrderTime(), "yyyy-MM-dd HH:mm:ss");
            String loan_time = DateUtil.getDateFormat(bo.getLoanTime(), "yyyy-MM-dd HH:mm:ss");
            String orderNo = GenerateNo.nextOrdId();

            Map<String, Object> objj = new HashMap<String, Object>();
            String sign = MD5coding.MD5(oid_plat + MD5coding.MD5(business_password) + MD5coding.MD5(business_code) + MD5coding.MD5(business_license)
                    + timestamp);

            objj.put("Version", "1.0");
            objj.put("Timestamp", timestamp);
            objj.put("Sign", sign);
            objj.put("Oid_plat", oid_plat);
            objj.put("Order_number", String.valueOf(bo.getAssetOrderId()));
            objj.put("Package_code", String.valueOf(bo.getPacketId()));
            objj.put("Period", loan_term);
            objj.put("Repayment_type", "2");
            objj.put("Repayment_amount", money_amount);
            objj.put("Loan_time", loan_time);
            objj.put("Repayment_time", repayment_time);
            objj.put("Repayment_principal", repayment_principal);
            objj.put("Repayment_interest", repayment_interest);
            objj.put("Other", loan_time);

            // Borrower_base借款人详细信息
            Map<String, Object> borrower_base = new HashMap<String, Object>();
            borrower_base.put("Name", name);
            borrower_base.put("Id_card", id_number);
            borrower_base.put("Phone", phone);

            borrower_base.put("Borrower_type", "2");// 借款人类型(1:企业，2:个人)

            borrower_base.put("Sex", "M");// 性别 商量中
            borrower_base.put("Education_level", "3");// 学历非必填 商量中
            borrower_base.put("Birthday", "19991111");// 出生日期 商量中
            borrower_base.put("Contact_username", "张三");// 紧急联系人姓名 商量中
            borrower_base.put("Contact_phone", "17711111111");// 紧急联系人手机号 商量中
            borrower_base.put("Contact_relation", "无");// 关系 商量中
            objj.put("Borrower_base", borrower_base);

            // Order_base借款订单信息
            Map<String, Object> order_base = new HashMap<String, Object>();

            order_base.put("Money_amount", money_amount);// 贷款金额，单位：分
            order_base.put("Loan_method", loan_method);
            order_base.put("Loan_term", loan_term);// 贷款期限,
            order_base.put("Loan_interests", loan_interests);// 总共利息,单位：分
            order_base.put("Annual_rate", apr);// 贷款年化率利率

            order_base.put("Dt_time", order_time);// 下单时间时间戳表示
            // order_base.put("loan_time", loan_time);// 放款时间时间戳表示
            order_base.put("Counter_fee", counter_fee);// 手续费,
            objj.put("Order_base", order_base);

            // debit_card扣款银行卡，不代扣可不传
            Map<String, Object> debit_card = new HashMap<String, Object>();
            debit_card.put("Bank_type", bank_id);// 银行卡id
            debit_card.put("Bank_name", bank_name);// 银行卡id
            debit_card.put("Cord_no", card_no);
            debit_card.put("Bank_phone", phone);
            debit_card.put("Name", name);
            debit_card.put("Bank_address", "非必填");// 支行名称或开户行地址 非必填
            objj.put("Debit_card", debit_card);

            // receive_card打款银行卡
            Map<String, Object> receive_card = new HashMap<String, Object>();
            receive_card.put("Bank_type", bank_id);// 银行卡id
            receive_card.put("Bank_name", bank_name);// 银行卡id
            receive_card.put("Cord_no", card_no);
            receive_card.put("Bank_phone", phone);
            receive_card.put("Name", name);
            receive_card.put("Bank_address", "非必填");// 支行名称或开户行地址 非必填
            objj.put("Receive_card", receive_card);

            // period_base还款计划信息
            Map<String, Object> back_plan = new HashMap<String, Object>();
            back_plan.put("Period", loan_term);
            back_plan.put("Plan_repayment_money", repayment_amount);
            back_plan.put("Plan_repayment_time", repayment_time);
            back_plan.put("Plan_repayment_principal", repayment_principal);
            back_plan.put("Plan_repayment_interest", repayment_interest);
            back_plan.put("Annual_rate", apr);

            objj.put("Back_plan", back_plan);

            String paramsJosn = JSONUtil.beanToJson(objj);
            String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            // 插入订单
            OutOrders orders = new OutOrders();
            orders.setOrderNo(orderNo);
            orders.setOrderType(OutOrders.orderType_ZCM);
            orders.setAssetOrderId(bo.getId());
            orders.setReqParams(paramsJosn);
            orders.setAddTime(new Date());
            orders.setAct("orderType_Order");
            orders.setStatus(OutOrders.STATUS_WAIT);
            orders.setTablelastName(tablelastName);
            outOrdersService.insertByTablelastName(orders);

//            HashMap<String, Integer> params = new HashMap<String, Integer>();
//            params.put("soketOut", 5000);
//            params.put("connOut", 5000);

//			String result = new WebClient().postJsonData(url, paramsJosn, null);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
//			String result = sendPost(url, jsonStrData.toString(), "utf-8", headers);
            String result = ShService.sendPost(url, paramsJosn, "utf-8", headers);
            if (result == null) {
                serviceResult.setMsg("连接超时！");
                return serviceResult;
            }
            OutOrders ordersNew = new OutOrders();
            ordersNew.setId(orders.getId());
            ordersNew.setOrderNo(orderNo);
            ordersNew.setReturnParams(result);
            ordersNew.setUpdateTime(new Date());
            ordersNew.setStatus(OutOrders.STATUS_SUC);
            ordersNew.setTablelastName(tablelastName);
            outOrdersService.updateByTablelastName(ordersNew);

            JSONObject jsonObj = null;
            try {
                jsonObj = JSONObject.fromObject(result);
            } catch (Exception e) {
                serviceResult.setMsg("请求地址错误导致返回结果解析错误");
                log.error("zcm docking  toJson error 请求地址错误导致返回结果解析错误:{}", e);
            }

            BorrowOrderCheckingExt noNew = new BorrowOrderCheckingExt();
            noNew.setId(bo.getId());
            String code = jsonObj.getString("ret_code");
            if (code.equals("0000")) {

                serviceResult.setMsg("推送成功");
                serviceResult.setCode(String.valueOf(BorrowOrderChecking.STATUS_TSCG));
                noNew.setStatus(BorrowOrderChecking.STATUS_TSCG);
                noNew.setPacketId(bo.getPacketId());
                noNew.setCapitalType(1);// 标识为招财猫
                noNew.setSignStatus(1);// 设置已被标记

            } else if (code.equals("501000")) {// 推满进行标识,需要判断最后一笔是否有多的
                String remark = jsonObj.getString("ret_msg");
                noNew.setStatus(BorrowOrderChecking.STATUS_TSCG);
                noNew.setPacketId(bo.getPacketId());
                noNew.setCapitalType(1);// 标识为招财猫
                noNew.setSignStatus(1);// 设置已被标记
                // 满包后是否有多余的金额
                Boolean isTwo = false;
                int superMoney = 0;
                try {
                    superMoney = (int) Double.parseDouble(jsonObj.getString("supermoney")) * 100;
                    if (superMoney > 0) {
                        isTwo = true;
                        if (superMoney == bo.getMoneyAmount()) {
                            noNew.setRemark("多余推送，不改变状态！");
                            noNew.setCapitalType(0);// 标识为招财猫
                            noNew.setStatus(BorrowOrderChecking.STATUS_DTS);
                            noNew.setSignStatus(0);// 设置已被标记
                        }
                    }
                } catch (Exception e) {
                    log.error("error:{}", e);
                }

                if (isTwo) {
                    noNew.setManyAssetpack(1);// 是否是多资产包（0-否 1-是）
                    noNew.setOnePushStatus(2);
                    noNew.setRemark(noNew.getRemark() + remark);
                    noNew.setTwoAssetpackMoney(superMoney);// 第二笔剩余金额???????????????????
                }
                // 推满的时候修改包的状态
                BorrowAssetPacket bapNew = new BorrowAssetPacket();
                bapNew.setId(bo.getPacketId());
                bapNew.setFillupFlag(1);
                bapNew.setPushStatus(1);
                borrowAssetPacketDao.updateByPrimaryKeySelective(bapNew);
                serviceResult.setCode("501000");

                judgeZCMLoanDetail(bo.getLoanTerm(), 0, DateUtil.getDateFormat(bo.getLoanTime(), "yyyyMMdd"), null);
            } else {
                String message = jsonObj.getString("ret_msg");
                serviceResult.setCode(String.valueOf(BorrowOrderChecking.STATUS_TSSB));
                serviceResult.setMsg(message);
                noNew.setRemark(message);
            }
            borrowOrderCheckingExtDao.updateByPrimaryKeySelective(noNew);
        } catch (Exception e) {
            log.error("zcm docking error:{}", e);
        } finally {
            return serviceResult;
        }
    }

    @Override
    public ServiceResult assetpackqueryZCM(String curDate) {

        ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
        LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");
        try {

            String check_isOpenLoan_ZCM = mapFee.get("packgeQuery_isOpen_ZCM");
            if (!check_isOpenLoan_ZCM.equals("1")) {
                serviceResult = new ServiceResult("500", "招财猫资产包查询已关闭！");
                log.info("assetpackqueryZCM is close  招财猫资产包查询已关闭！");
                return serviceResult;
            }

            Date nowDate = new Date();
            String nowDateS = DateUtil.getDateFormat(nowDate, "yyyy-MM-dd");
            if (curDate != null) {
                nowDateS = curDate;
            }
            Boolean isExistNowDate = false;
            HashMap<String, Object> paramsM = new HashMap<>();
            paramsM.put("packetTime", nowDateS);
            paramsM.put("fillupFlag", 0);
            int loanTerms[] = BorrowOrder.LOAN_TREMS;// 循环推送7天14天
            for (int loanTerm : loanTerms) {
                log.info("begin assetsDivisionExt loanTerm:{}", loanTerm);
                paramsM.put("borrowCycle", loanTerm);
                BorrowAssetPacket bap = borrowAssetPacketDao.selectByParam(paramsM);
                if (bap != null) {
                    isExistNowDate = true;
                    log.info("assetpackqueryZCM  isExistNowDate:{}执行查询时发现包已经存在，不会继续查询包", nowDateS);
                    break;
                }
            }
            if (!isExistNowDate) {
                String oid_plat = mapFee.get("oid_plat_ZCM");
                String business_password = mapFee.get("business_password_ZCM");
                String business_code = mapFee.get("business_code_ZCM");
                String business_license = mapFee.get("business_license_ZCM");

//                HashMap<String, Integer> params = new HashMap<String, Integer>();
//                params.put("soketOut", 5000);
//                params.put("connOut", 5000);
                String url = mapFee.get("GO_URL_ZCM") + "x1/asset/assetfundquery";
                String timestamp = DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
                Map<String, Object> objj = new HashMap<>();
                String sign = MD5coding.MD5(oid_plat + MD5coding.MD5(business_password) + MD5coding.MD5(business_code)
                        + MD5coding.MD5(business_license) + timestamp);
                objj.put("Version", "1.0");
                objj.put("Timestamp", timestamp);
                objj.put("Sign", sign);
                objj.put("Oid_plat", oid_plat);
                objj.put("Valueday", nowDateS);

                String paramsJosn = JSONUtil.beanToJson(objj);
                String orderNo = GenerateNo.nextOrdId();
                String tablelastName = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
                // 插入订单
                OutOrders orders = new OutOrders();
                orders.setOrderNo(orderNo);
                orders.setOrderType(OutOrders.orderType_ZCM);

                orders.setReqParams(paramsJosn);
                orders.setAddTime(new Date());
                orders.setAct("QUERY_PACKGE");
                orders.setStatus(OutOrders.STATUS_WAIT);
                orders.setTablelastName(tablelastName);
                outOrdersService.insertByTablelastName(orders);
//				String result = new WebClient().postJsonData(url, paramsJosn, null);
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//				String result = sendPost(url, jsonStrData.toString(), "utf-8", headers);
                String result = ShService.sendPost(url, paramsJosn, "utf-8", headers);
                OutOrders ordersNew = new OutOrders();
                ordersNew.setId(orders.getId());
                ordersNew.setOrderNo(orderNo);
                ordersNew.setReturnParams(result);
                ordersNew.setUpdateTime(new Date());
                ordersNew.setStatus(OutOrders.STATUS_SUC);
                ordersNew.setTablelastName(tablelastName);
                outOrdersService.updateByTablelastName(ordersNew);
                if (result != null) {
                    JSONObject jsonObj = JSONObject.fromObject(result);
                    String code = jsonObj.getString("ret_code");
                    if (code.equals("0000")) {
                        Object objs = jsonObj.get("fundlist");
                        JSONArray arrays = null;
                        if (objs instanceof JSONObject) {
                            arrays = new JSONArray();
                            arrays.add(objs);
                        } else if (objs instanceof JSONArray) {
                            arrays = jsonObj.getJSONArray("fundlist");
                        }
                        if (arrays != null && arrays.size() > 0) {
                            List<BackConfigParams> list = new ArrayList<BackConfigParams>();
                            BackConfigParams LOANISOPEN_ZCM_7 = null;// 7天放款开关
                            BackConfigParams LOANISOPEN_ZCM_14 = null;// 14天放款开关
                            for (int i = 0; i < arrays.size(); i++) {
                                JSONObject obj = arrays.getJSONObject(i);
                                int totalAmount = (int) Double.parseDouble(obj.getString("Allmoney"));
                                int period = obj.getInt("Period");
                                BorrowAssetPacket bap = new BorrowAssetPacket();
                                bap.setBorrowCycle(period);
                                bap.setPacketTime(DateUtil.getDate(obj.getString("Begin_time"), "yyyy-MM-dd"));
                                bap.setPacketTotalamount(totalAmount);
                                bap.setPacketSignamount((int) Double.parseDouble(obj.getString("Curr_money")));
                                bap.setFillupFlag(obj.getInt("State_flag") == 2 ? 1 : 0);// 2表示推满或1标识未推满
                                borrowAssetPacketDao.insertSelective(bap);
                                // 当前包没有推满才需要开启
                                if (bap.getFillupFlag() != null && bap.getFillupFlag() != 1) {
                                    // 将每日需要的资产包金额放入redis做记录
                                    String key = "ZCM_PACKAGE_NEED_" + period + "_" + DateUtil.getDateFormat(nowDate, "yyyyMMdd");
                                    // 有效时间两天
                                    jedisCluster.setex(key, BorrowOrder.ALARM_ONEDAY * 2, String.valueOf(totalAmount));
                                    // 查询到包，切换到招财猫账户打款
                                    paramsM.clear();
                                    paramsM.put("sysKey", "LOANISOPEN_ZCM_" + period);
                                    List<BackConfigParams> LOANISOPEN_ZCM_s = backConfigParamsService.findParams(paramsM);
                                    if (period == 7) {
                                        LOANISOPEN_ZCM_7 = LOANISOPEN_ZCM_s.get(0);// 7天放款开关
                                        LOANISOPEN_ZCM_7.setSysValue("1");
                                        list.add(LOANISOPEN_ZCM_7);
                                    } else if (period == 14) {
                                        LOANISOPEN_ZCM_14 = LOANISOPEN_ZCM_s.get(0);// 7天放款开关
                                        LOANISOPEN_ZCM_14.setSysValue("1");
                                        list.add(LOANISOPEN_ZCM_14);
                                    }

                                }

                            }
                            if (LOANISOPEN_ZCM_7 != null || LOANISOPEN_ZCM_14 != null) {
                                paramsM.clear();
                                paramsM.put("sysKey", "whichLoanAccount");
                                List<BackConfigParams> whichLoanAccounts = backConfigParamsService.findParams(paramsM);
                                BackConfigParams whichLoanAccount = whichLoanAccounts.get(0);
                                whichLoanAccount.setSysValue(String.valueOf(BorrowOrder.LOAN_ACCOUNT2));// 招财猫金额打满切换账户打款
                                list.add(whichLoanAccount);
                                backConfigParamsService.updateValue(list, null);
                                // 刷新系统缓存
                                new IndexInit().updateCache();
                                log.info("=========ZCM loanStatus is open:{}", nowDateS);
                            }

                        }
                    } else {
                        log.info("assetpackqueryZCM query not Success because queryDate:" + nowDateS + "code:" + code + ",mgs:" + jsonObj.getString("ret_msg"));
                    }
                } else {
                    log.info("assetpackqueryZCM  result is null  请求到对方没有查询到包数据！");
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("uq_one_day")) {
                serviceResult.setMsg("已经存在该记录");
                log.error("assetpackqueryZCM record is exist");
            }
            log.error("assetpackqueryZCM error:{} ", e);
        }

        return null;
    }

    @Override
    public int batchInsertBCI(List<BorrowOrder> boNewList) {
        return borrowOrderDao.batchInsertBCI(boNewList);
    }

    @Override
    public List<BorrowOrder> findAssetBorrowOrderForZhimaFeedback(HashMap<String, Object> paraMap) {
        return borrowOrderDao.findAssetBorrowOrderForZhimaFeedback(paraMap);
    }

    /**
     */
    @Override
    public Integer findBorrowOrderCheckExtCount(HashMap<String, Object> params) {

        return borrowOrderCheckingExtDao.selectAllCount(params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<BorrowOrderCheckingExt> findBorrowOrderCheckExtPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "BorrowOrderCheckingExt");
        return paginationDao.findPage("selectAll", "selectAllCount", params, "web");
    }

    @Override
    public void autoFangkuan(BorrowOrder borrowOrder) {
        log.info("autoFangkuan borrowOrder=" + (borrowOrder != null ? JSON.toJSONString(borrowOrder) : "null"));
        if (borrowOrder != null && !borrowOrder.getPaystatus().equals(BorrowOrder.SUB_PAY_SUCC)
                && borrowOrder.getStatus() == BorrowOrder.STATUS_FKZ) {
            log.info("autoFangkuan borrowOrderId = " + borrowOrder.getId() + " userId=" + borrowOrder.getUserId());

            borrowOrder.setOutTradeNo("");
            this.updateLoanNew(borrowOrder, "SUCCESS", "线下支付成功");
        }
    }

    @Override
    public boolean distributeBorrowOrderForReview(BackUser backUser) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG));
        params.put("numPerPage", "10");
        params.put("reviewBackUserId", backUser.getId());
        PageConfig<BorrowOrder> borrowOrderPageConfig = findPage(params);
        int totalResultSize = borrowOrderPageConfig.getTotalResultSize();
        if (totalResultSize != 0) {
            /**
             * 有尚未处理的复审订单
             */
            return false;
        }
        HashMap<String, Object> updateParams = new HashMap<>();
        updateParams.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG));
        updateParams.put("numPerPage", "20");
        updateParams.put("reviewStatus", "0");
        //update 升序
        updateParams.put("queryType", "reverse");
        PageConfig<BorrowOrder> updatePageConfig = findPage(updateParams);
        for (BorrowOrder borrowOrder : updatePageConfig.getItems()) {
            borrowOrder.setReviewBackUserId(backUser.getId());
            borrowOrder.setReviewBackUserName(backUser.getUserName());
            borrowOrder.setReviewStatus(1);
            borrowOrder.setDistributeTime(new Date());
            borrowOrder.setUpdatedAt(new Date());
            borrowOrderDao.updateByPrimaryKeySelective(borrowOrder);
        }
        /**
         * 风控订单转派成功
         */
        return true;
    }

    @Override
    public int handDistributeOrderForReview(Map<Integer, List<BorrowOrder>> userDisOrder) {
        log.info("handDistributeOrderForReview start");
        Map<String, Object> params = new HashMap<>();
        params.put("moduleUrl", ReviewDistributionService.MODULE_URL);
        List<BackReviewDistribution> backReviewDistributions = backReviewDistributionDao.selectInUseBakRevDist(params);

        if (CollectionUtils.isEmpty(backReviewDistributions)) {
            log.info("isEmpty(backReviewDistributions)");
            return 0;
        }
        HashMap<String, Object> updateParams = new HashMap<>(4);
        updateParams.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG));
        updateParams.put("reviewStatus", "0");
        //update 升序
        updateParams.put("queryType", "reverse");
        List<BorrowOrder> borrowOrders = borrowOrderDao.findParams(updateParams);
        //LinkedList<BorrowOrder> borrowOrdList = new LinkedList<>(borrowOrders);


        if (CollectionUtils.isEmpty(borrowOrders)) {
            log.info("isEmpty(borrowOrders)");
            return 0;
        }
        //按照比例进行排序，从大到小降序
        Collections.sort(backReviewDistributions, new Comparator<BackReviewDistribution>() {
            @Override
            public int compare(BackReviewDistribution o1, BackReviewDistribution o2) {
                return o2.getDistributionRate().compareTo(o1.getDistributionRate());
            }
        });

        //按照比例进行排序，从小到大升序
        Collections.sort(borrowOrders, new Comparator<BorrowOrder>() {
            @Override
            public int compare(BorrowOrder o1, BorrowOrder o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

        int sumRate = 0;
        for (BackReviewDistribution backDis : backReviewDistributions) {
            if (null != backDis.getDistributionRate()) {
                sumRate += backDis.getDistributionRate();
            }
        }

        if (0 < sumRate) {
            for (BackReviewDistribution backDis : backReviewDistributions) {
                backDis.setDisRate(backDis.getDistributionRate() / (sumRate + 0.0));
            }
        }

        List<BorrowOrder> newBorrowOrders = new ArrayList<>();
        calculateCountNew(borrowOrders, backReviewDistributions, newBorrowOrders, userDisOrder);
        log.info("-------------newBorrowOrders 大小:{}", newBorrowOrders.size());
        if (CollectionUtils.isNotEmpty(newBorrowOrders)) {
            Map<String, Object> map = new HashMap<>();
            map.put("list", newBorrowOrders);
            borrowOrderDao.updateBatch(map);
        }
        return newBorrowOrders.size();
    }

    @Override
    public void autoDistributeOrderForReview() {
        log.info("autoDistributeOrderForReview start---------------------");
        if ("open".equals(jedisCluster.get("distributeSwitch"))) {
            Calendar cal = Calendar.getInstance();
            //小时 24小时制
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            //分
            int minute = cal.get(Calendar.MINUTE);
            HashMap<String, Object> sys = new HashMap<>();
            sys.put("sysType", "SWITCH_TYPE");
            List<BackConfigParams> bakconfigs = backConfigParamsDao.findParams(sys);
            String dateCfg = "";
            for (BackConfigParams bankCfg : bakconfigs) {
                if ("AUTO_DISTRIBUTE_TIME".equals(bankCfg.getSysKey())) {
                    dateCfg = bankCfg.getSysValue();
                }
            }
            try {
                //9:00-17:30
                //17:00-00:00 00:00-15:30
                String[] split = dateCfg.split("-");
                if (null != split && 2 == split.length) {

                    String[] startSplit = split[0].split(":");
                    String[] endSplit = split[1].split(":");

                    //跨0点的情况即 起始时间大于结束时间
                    if (Integer.valueOf(startSplit[0]) > Integer.valueOf(endSplit[0]) || (Integer.valueOf(startSplit[0]).equals(Integer.valueOf(endSplit[0])) && Integer.valueOf(startSplit[1]) > Integer.valueOf(endSplit[1]))) {
                        //时钟比起始时间小，但是比结束时间大的
                        if (hour < Integer.valueOf(startSplit[0]) && hour > Integer.valueOf(endSplit[0])) {
                            log.info("autoDistributeOrderForReview stop-------no execute--------------");
                            return;
                        }
                        //时钟相等，分钟不在此范围内的
                        if (hour == Integer.valueOf(startSplit[0])) {
                            if (minute < Integer.valueOf(startSplit[1])) {
                                log.info("autoDistributeOrderForReview stop-------no execute--------------");
                                return;
                            }
                        }
                        if (hour == Integer.valueOf(endSplit[0])) {
                            if (minute > Integer.valueOf(endSplit[1])) {
                                log.info("autoDistributeOrderForReview stop-------no execute--------------");
                                return;
                            }
                        }
                    }
                    //起始时间等于结束时间全天派单
                    else if (Integer.valueOf(startSplit[0]).equals(Integer.valueOf(endSplit[0])) && Integer.valueOf(startSplit[1]).equals(Integer.valueOf(endSplit[1]))) {

                    }
                    //起始时间小于于结束时间在区间内派单
                    else {
                        //不跨零点的情况
                        //如果时间小时小于起始时间并大于结束时间，则不执行派单
                        if (hour < Integer.valueOf(startSplit[0]) || hour > Integer.valueOf(endSplit[0])) {
                            log.info("autoDistributeOrderForReview stop-------no execute--------------");
                            return;
                        }
                        //如果小时等于小时，但分钟不在此范围内，则依旧不执行派单
                        if (hour == Integer.valueOf(startSplit[0])) {
                            if (minute < Integer.valueOf(startSplit[1])) {
                                log.info("autoDistributeOrderForReview stop-------no execute--------------");
                                return;
                            }
                        }
                        if (hour == Integer.valueOf(endSplit[0])) {
                            if (minute > Integer.valueOf(endSplit[1])) {
                                log.info("autoDistributeOrderForReview stop-------no execute--------------");
                                return;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("please check table back_config_params column sys_value where sys_key=AUTO_DISTRIBUTE_TIME:{}", e);
                return;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("moduleUrl", ReviewDistributionService.MODULE_URL);
            List<BackReviewDistribution> backReviewDistributions = backReviewDistributionDao.selectInUseBakRevDist(params);
            if (CollectionUtils.isEmpty(backReviewDistributions)) {
                log.info("isEmpty(backReviewDistributions)");
                return;
            }
            HashMap<String, Object> updateParams = new HashMap<>(4);
            updateParams.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG));
            updateParams.put("reviewStatus", "0");
            //update 升序
            updateParams.put("queryType", "reverse");
            List<BorrowOrder> borrowOrders = borrowOrderDao.findParams(updateParams);


            if (CollectionUtils.isEmpty(borrowOrders)) {
                log.info("isEmpty(borrowOrders)");
                return;
            }
            //按照比例进行排序，从大到小降序
            Collections.sort(backReviewDistributions, new Comparator<BackReviewDistribution>() {
                @Override
                public int compare(BackReviewDistribution o1, BackReviewDistribution o2) {
                    return o2.getDistributionRate().compareTo(o1.getDistributionRate());
                }
            });

            //按照比例进行排序，从小到大升序
            Collections.sort(borrowOrders, new Comparator<BorrowOrder>() {
                @Override
                public int compare(BorrowOrder o1, BorrowOrder o2) {
                    return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                }
            });

            int sumRate = 0;
            for (BackReviewDistribution backDis : backReviewDistributions) {
                if (null != backDis.getDistributionRate()) {
                    sumRate += backDis.getDistributionRate();
                }
            }

            if (0 < sumRate) {
                for (BackReviewDistribution backDis : backReviewDistributions) {
                    backDis.setDisRate(backDis.getDistributionRate() / (sumRate + 0.0));
                }
            }
            List<BorrowOrder> newBorrowOrders = new ArrayList<>();
            Map<Integer, List<BorrowOrder>> userDisOrder = new HashMap<>();
            calculateCountNew(borrowOrders, backReviewDistributions, newBorrowOrders, userDisOrder);
            log.info("-------------newBorrowOrders 大小:{}", newBorrowOrders.size());
            if (CollectionUtils.isNotEmpty(newBorrowOrders)) {
                Map<String, Object> map = new HashMap<>();
                map.put("list", newBorrowOrders);
                borrowOrderDao.updateBatch(map);
            }
        }
        log.info("autoDistributeOrderForReview stop---------------------");
    }

    /**
     * \
     * @param borrowOrders
     * @param backReviewDistributions
     * @param newBorrowOrders
     * @param userDisOrder 存储每个用户应该派哪些单

     */
    public void calculateCountNew(List<BorrowOrder> borrowOrders,
                                  List<BackReviewDistribution> backReviewDistributions, List<BorrowOrder> newBorrowOrders, Map<Integer, List<BorrowOrder>> userDisOrder) {
        int count = borrowOrders.size();
        // 存储用户集合
        Map<Integer, BackReviewDistribution> userIdMap = new HashMap<>();
        List<Integer> userIds = new ArrayList<>();
        // 存储每个用户最小应派单数
        Map<Integer, Integer> minCountList = new HashMap<>();
        // 最小派单总数
        int minDisTotalCunt = 0;
        //无订单返回
        if (0 >= count) {
            log.info("borrow cunt is empty!");
            return;
        }


        int distributionRate = 0;
        //计算最小派单数
        for (BackReviewDistribution distribution : backReviewDistributions) {
            distributionRate = distribution.getDistributionRate();
            if (distributionRate > 0) {
                minDisTotalCunt += distributionRate;
            }
        }

        //如果数量小于最小派单数 或者最小派单数为0 则不派
        if (count < minDisTotalCunt || 0 == minDisTotalCunt) {
            log.info("borrow cunt is not enough for minDisTotalCunt's cunt!minDisTotalCunt is:"+minDisTotalCunt);
            return;
        }


        //如果是大于等于minDisTotalCunt最小派单数的倍数，则派其倍数，多余的下次派单  ，能整除则派所有
        if (0 != count % minDisTotalCunt) {
            int tcnt = count / minDisTotalCunt;
            count = minDisTotalCunt * tcnt;
            borrowOrders = borrowOrders.subList(0, count);
        }

        //计算每个人应该派多少笔
        for (BackReviewDistribution distribution : backReviewDistributions) {
            int userId = distribution.getUserId();
            distributionRate = distribution.getDistributionRate();
            if (distributionRate > 0) {
                //只会出现 12 * 1/6  出现任何 11 * 1/6类似的都是异常情况  count 必须是 minDisTotalCunt的倍数
                if (0 != count % minDisTotalCunt) {
                    log.error("distribute borrow order error for count is not correct to  minDisTotalCunt");
                    return;
                }
                minCountList.put(userId, count * distributionRate / minDisTotalCunt);
                userIdMap.put(userId, distribution);
                userIds.add(userId);
            }
        }

        LinkedList<BorrowOrder> borrowOrdList = new LinkedList<>(borrowOrders);

        while (!borrowOrdList.isEmpty()) {
            //执行一次必须全部派完，否则会出现死循环
            for (Integer userId : userIds) {
                int mincount = minCountList.get(userId);
                List<BorrowOrder> disList = userDisOrder.get(userId);
                if (null == disList) {
                    disList = new ArrayList<BorrowOrder>();
                }
                if (mincount > 0) {
                    disList.add(borrowOrdList.poll());
                    userDisOrder.put(userId, disList);
                    // 最小数量减一
                    mincount--;
                    minCountList.put(userId, mincount);
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        //最后将对应后台用户的单打上印记标签
        for (Integer userId : userDisOrder.keySet()) {
            StringBuilder builder = new StringBuilder("auto aistribute borrowders plan is:{");
            builder.append("userId:" + userId + "[");
            for (BorrowOrder borrowOrder : userDisOrder.get(userId)) {
                builder.append(sdf.format(borrowOrder.getCreatedAt()) + "|");
                borrowOrder.setReviewBackUserId(userId);
                borrowOrder.setReviewBackUserName(userIdMap.get(userId).getUserName());
                borrowOrder.setReviewStatus(1);
                borrowOrder.setDistributeTime(new Date());
                borrowOrder.setUpdatedAt(new Date());
                newBorrowOrders.add(borrowOrder);
            }
            builder.append("]}");
            log.info(builder.toString());
        }
    }

    /**
     * 动态计算每个用户应该分配那些复审订单
     */
    public void calculateCount(List<BorrowOrder> borrowOrders,
                               List<BackReviewDistribution> backReviewDistributions, List<BorrowOrder> newBorrowOrders, Map<Integer, List<BorrowOrder>> userDisOrder) {
        LinkedList<BorrowOrder> borrowOrdList = new LinkedList<>(borrowOrders);
        int count = borrowOrdList.size();
        // 存储用户集合
        Map<Integer, BackReviewDistribution> userIdMap = new HashMap<>();
        List<Integer> userIds = new ArrayList<>();
        // 存储每个用户最小应派单数
        Map<Integer, Integer> minCountList = new HashMap<>();
        // 存储每个用户应该派哪些单
        //Map<Integer, List<BorrowOrder>> userDisOrder = new HashMap<>();
        // 最小派单总数
        int minDisTotalCunt = 0;

        if (0 < count) {
            // 计算每个比例的最小应分配数值
            for (BackReviewDistribution distribution : backReviewDistributions) {
                int userId = distribution.getUserId();
                double rate = distribution.getDisRate();
                if (rate > 0) {
                    minCountList.put(userId, (int) (count * rate));
                    minDisTotalCunt += (int) (count * rate);
                    userIdMap.put(userId, distribution);
                    userIds.add(userId);
                }
            }
            // 最小派单后余数
            int remainDisCunt = count - minDisTotalCunt;
            while (remainDisCunt < borrowOrdList.size()) {
                // 此处执行将把最小派单填充完毕
                for (Integer userId : userIds) {
                    int mincount = minCountList.get(userId);
                    List<BorrowOrder> disList = userDisOrder.get(userId);
                    if (null == disList) {
                        disList = new ArrayList<BorrowOrder>();
                    }

                    if (mincount > 0) {
                        disList.add(borrowOrdList.poll());
                        userDisOrder.put(userId, disList);
                        // 最小数量减一
                        mincount--;
                        minCountList.put(userId, mincount);
                    }
                }
            }

            // 余数派单进行竞争算法进行派单
            while (!borrowOrdList.isEmpty()) {
                // 第一优先将该用户虽然具有分配比例，但此次并未向其派单的用户分发订单
                for (Integer userId : userIds) {
                    if (null == userDisOrder.get(userId)) {
                        if (!borrowOrdList.isEmpty()) {
                            List<BorrowOrder> disList = new ArrayList<BorrowOrder>();
                            disList.add(borrowOrdList.poll());
                            userDisOrder.put(userId, disList);
                        } else {
                            break;
                        }
                    }
                }
                // 第二若全部都有了，但仍有单没派完，则再按照比例派发余下的单数，直到为空
                for (Integer userId : userIds) {
                    if (!borrowOrdList.isEmpty()) {
                        List<BorrowOrder> disList = userDisOrder.get(userId);
                        disList.add(borrowOrdList.poll());
                        userDisOrder.put(userId, disList);
                    } else {
                        break;
                    }
                }

            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            //最后将对应后台用户的单打上印记标签
            for (Integer userId : userDisOrder.keySet()) {
                StringBuilder builder = new StringBuilder("auto aistribute borrowders plan is:{");
                builder.append("userId:" + userId + "[");
                for (BorrowOrder borrowOrder : userDisOrder.get(userId)) {
                    builder.append(sdf.format(borrowOrder.getUpdatedAt()) + "|");
                    borrowOrder.setReviewBackUserId(userId);
                    borrowOrder.setReviewBackUserName(userIdMap.get(userId).getUserName());
                    borrowOrder.setReviewStatus(1);
                    borrowOrder.setDistributeTime(new Date());
                    borrowOrder.setUpdatedAt(new Date());
                    newBorrowOrders.add(borrowOrder);
                }
                builder.append("]}");
                log.info(builder.toString());
            }
        }

    }

    private void getUpdateList(List<BorrowOrder> borrowOrders, List<BorrowOrder> newBorrowOrders, BackReviewDistribution reviewDistribution, int num) {
        Iterator<BorrowOrder> iter = borrowOrders.iterator();
        while (iter.hasNext()) {
            if (num-- == 0) {
                break;
            }
            BorrowOrder borrowOrder = iter.next();
            borrowOrder.setReviewBackUserId(reviewDistribution.getUserId());
            borrowOrder.setReviewBackUserName(reviewDistribution.getUserName());
            borrowOrder.setReviewStatus(1);
            borrowOrder.setDistributeTime(new Date());
            borrowOrder.setUpdatedAt(new Date());
            newBorrowOrders.add(borrowOrder);
            iter.remove();
        }
    }

    @Override
    public Map<String, String> getStatisticMap(Integer id) {
        Map<String, String> map = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();

        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_DCS));
        int toAutoReview = findParamsCount(params);
        map.put("toAutoReview", toAutoReview + "");
        params.put("statusList", Arrays.asList(BorrowOrder.STATUS_CSTG));
        int autoPassed = findParamsCount(params);
        map.put("autoPassed", autoPassed + "");
        params.put("reviewBackUserId", id);
        params.put("startReviewTime", DateUtil.getStartOfDay(new Date()));
        params.put("endReviewTime", DateUtil.getEndOfDay(new Date()));
        params.remove("statusList");
        params.put("noStatusList", Arrays.asList(BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_DCS, BorrowOrder.STATUS_CSBH, BorrowOrder.STATUS_FSBH));
        int passedToday = findParamsCount(params);
        map.put("passedToday", passedToday + "");
        params.put("noStatusList", Arrays.asList(BorrowOrder.STATUS_CSTG, BorrowOrder.STATUS_DCS, BorrowOrder.STATUS_CSBH));
        int reviewedToday = findParamsCount(params);
        map.put("reviewedToday", reviewedToday + "");
        double passedRateToday = 0.0;
        if (reviewedToday != 0) {
            passedRateToday = (passedToday * 100.0) / reviewedToday;
        }
        double value = new BigDecimal(passedRateToday).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        map.put("passedRateToday", value + "%");
        map.put("delayNoLoanCunt", String.valueOf(findDelayNoLoanCunt(120)));
        map.put("delayAutoReviewCunt", String.valueOf(findDelayAutoReviewCunt(30)));
        map.put("loanFailedCunt", String.valueOf(findLoanFailedCunt()));
        return map;
    }

    /**
     * 查询延时未放款笔数
     */
    private int findDelayNoLoanCunt(int minutes) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", BorrowOrder.STATUS_FKZ);
        map.put("minutes", minutes);
        return borrowOrderDao.selectDelayNoLoanCunt(map);
    }

    /**
     * 查询延时待机审笔数
     */
    private int findDelayAutoReviewCunt(int minute) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", BorrowOrder.STATUS_DCS);
        map.put("minutes", minute);
        return borrowOrderDao.selectDelayAutoReviewCunt(map);
    }

    /**
     * 查询放款失败笔数
     */
    private int findLoanFailedCunt() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", BorrowOrder.STATUS_FKSB);
        return borrowOrderDao.selectLoanFailedCunt(map);
    }

    /**
     * 查询后台复审用户
     *
     * @return
     */
    @Override
    public List<Map<String, String>> findBackReviewUserName() {
        return borrowOrderDao.selectReviewUserNames();
    }

    @Override
    public List<String> getUserIdList() {
        return borrowOrderDao.getUserIdList();
    }
    @Override
    public List<String> getUserIdList2() {
        return borrowOrderDao.getUserIdList2();
    }

    @Override
    public List<String> getUserIdWaitList() {
        return borrowOrderDao.getUserIdWaitList();
    }

    @Override
    public Integer findOveChannle(Integer channelId,String loanTime,Integer status,String customerType) {
        return borrowOrderDao.findOveChannle(channelId,loanTime,status,customerType);
    }

    @Override
    public Integer findRepayCount(Integer channelId, List<Integer> statusList, String loanTime,String customerType) {
        return borrowOrderDao.findRepayCount(channelId,statusList,loanTime,customerType);
    }

    @Override
    public Integer findExtendChannel(Integer channelId, String loanTime) {
        return borrowOrderDao.findExtendChannel(channelId,loanTime);
    }

    @Override
    public Integer findRenewalCount(Integer channelId, String loanTime) {
        return borrowOrderDao.findRenewalCount(channelId,loanTime);
    }

    @Override
    public Boolean pandanCount(String createTime) {
        Boolean b = true;
        try{
            HashMap<String, Object> params = new HashMap<String, Object>();
            Map<String,Object> map = new HashMap<>();
            //查询出目前所有客服
            params.put("roleName","普通客服");
            List<BackUser> userList = backUserDao.findKeFuList(params);
            params.put("roleName","客服主管");
            List<BackUser>   kefu = backUserDao.findKeFuList(params);
            userList.addAll(kefu);
            for(BackUser backUser : userList){
                //客服当日派单数量以及回款数量
                Integer dayCount = kefuCensusDao.dayPandanCount(createTime,null,backUser.getId(),0);
                Integer dayRepayCount = kefuCensusDao.dayPandanCount(createTime,1,backUser.getId(),0);
                //用户展期数量 （展期也算为回款）
                Integer dayExtendCount = kefuCensusDao.extendCount(createTime,backUser.getId(),0);
                //查询出每个客服截至到目前所有的总数量以及总还款数量
                Integer allCount = kefuCensusDao.dayPandanCount(createTime,null,backUser.getId(),1);
                Integer allRepayCount = kefuCensusDao.dayPandanCount(createTime,1,backUser.getId(),1);
                //用户总展期数量
                Integer allExtendCount = kefuCensusDao.extendCount(createTime,backUser.getId(),1);
                KefuCensus kefuCensus = new KefuCensus();
                kefuCensus.setJobId(backUser.getId());
                kefuCensus.setDayCount(dayCount);
                kefuCensus.setDayRepayCount(dayRepayCount + dayExtendCount);
                kefuCensus.setAllCount(allCount);
                kefuCensus.setAllRepayCount(allRepayCount + allExtendCount);
                kefuCensus.setCreateTime(createTime);
                //判断新增还是更新
                List<KefuCensus> list = kefuCensusDao.kefuCensusResult(createTime,backUser.getId());
                if(list.size() > 0){
                    //更新
                    kefuCensusDao.updateAssetKefuCensus(kefuCensus);
                }else{
                    //新增
                    kefuCensusDao.insertAssetKeFuCensus(kefuCensus);
                }
            }
        }catch(Exception e){
            b = false;
          log.info("客服统计出错"+e.getMessage());
        }
        return b;
    }

    @Override
    public PageConfig<KefuCensus> kefuCensusList(HashMap<String, Object> map) {
        return paginationDao.findPage("findKefuCensus", "findKefuCensusCount", map, "web");
    }


}
