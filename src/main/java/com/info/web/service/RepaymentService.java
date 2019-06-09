package com.info.web.service;

import static com.info.web.pojo.BorrowOrder.STATUS_YYQ;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.info.back.dao.IBackDictionaryDao;
import com.info.back.utils.PropertiesUtil;
import com.info.web.dao.*;
import com.info.web.pojo.*;
import com.info.web.util.*;
import com.info.web.util.aliyun.RocketMqUtil;
import com.vxianjin.gringotts.pay.enums.OperateType;
import com.vxianjin.gringotts.pay.enums.OrderChangeAction;
import com.vxianjin.gringotts.pay.pojo.OrderLogModel;
import com.vxianjin.gringotts.pay.service.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.constant.CollectionConstant;
import com.info.constant.Constant;
import com.info.risk.service.IOutOrdersService;
import com.info.web.test.ThreadPool;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
@Slf4j
@Service
public class RepaymentService implements IRepaymentService {

	@Resource
	private IRepaymentDao repaymentDao;
	@Resource
	private IRenewalRecordDao renewalRecordDao;
	@Resource
	private IBorrowOrderDao borrowOrderDao;
	@Resource
	private IRepaymentDetailDao repaymentDetailDao;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IIndexDao indexDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserContactsService userContactsService;
	@Autowired
	private IUserBankService userBankService;
	@Autowired
	private IOutOrdersService outOrdersService;
	@Autowired
	private IRepaymentDetailService repaymentDetailService;
	@Autowired
	private IPushUserService pushUserService;
	@Resource
	private BorrowProductConfigDao borrowProductConfigDao;

	@Autowired
	JedisCluster jedisCluster;

//    @Autowired
//    private CommonProducer commonProducer;

    @Autowired
    private IBackDictionaryDao backDictionaryDao;

    @Autowired
    private OrderLogService orderLogService;

    @Override
    public List<Repayment> findAll(HashMap<String, Object> params) {
        return null;
    }

	@Override
	public List<RepaymentDetail> findDetailsByRepId(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("assetRepaymentId", id);
		return repaymentDetailDao.findParams(params);
	}

	@Override
	public Repayment findOneRepayment(Map<String, Object> params) {
		List<Repayment> repayments = repaymentDao.findParams(params);
		return null != repayments ? repayments.get(0) : null;
	}

	@Override
	public List<Map<String, Object>> findMyLoan(Map<String, Object> params) {
		return repaymentDao.findMyLoan(params);
	}

	@Override
	public Repayment selectByPrimaryKey(Integer id) {
		return repaymentDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return repaymentDao.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public boolean insert(Repayment repayment) {
		return repaymentDao.insert(repayment) > 0;
	}

	@Override
	public boolean insertSelective(Repayment repayment) {
		return repaymentDao.insertSelective(repayment) > 0;
	}

	@Override
	public boolean updateByPrimaryKey(Repayment repayment) {
		return repaymentDao.updateByPrimaryKey(repayment) > 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(Repayment repayment) {
		return repaymentDao.updateByPrimaryKeySelective(repayment) > 0;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Repayment> findPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Repayment");
		return paginationDao.findPage("findParams", "findParamsCount", params, "web");
	}

	/*
	 * @Override public List<Repayment> findParams(Map<String, Object> params) {
	 * return repaymentDao.findParams(params); }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Repayment> findWriteOffPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Repayment");
		return paginationDao.findPage("findParams", "findParamsCountPrecise", params, "web");
	}

	/*
	 * @Override public List<Repayment> findParams(Map<String, Object> params) {
	 * return repaymentDao.findParams(params); }
	 */

    @Override
    public List<Repayment> findTaskRepayment(Map<String, Object> params) {
        return repaymentDao.findTaskRepayment(params);
    }

	@Override
	public List<Repayment> findByRepaymentReport(Map<String, Object> params) {
		return repaymentDao.findByRepaymentReport(params);
	}

	@Override
	public List<Repayment> findByRepaymentSmsRemind(Map<String, Object> params) {
		return repaymentDao.findByRepaymentSmsRemind(params);
	}

	@Override
	public List<Repayment> findRepayingByUserId(Integer id) {
		return repaymentDao.findRepayingByUserId(id);
	}

	@Override
	public Map<String, String> findUserPhoneName(Integer id) {
		return repaymentDao.findUserPhoneName(id);
	}

	@Override
	public Map<String, String> findCardNo(Integer id) {
		return repaymentDao.findCardNo(id);
	}

	@Override
	public Integer findUserIdByPhone(String userPhone) {
		return repaymentDao.findUserIdByPhone(userPhone);
	}

    @Override
    public List<HashMap<String, Object>> findBlackList(Map<String, Object> params) {

		return  repaymentDao.findBlackList(params);
    }

	/**
	 * 查询代扣 rongbao
	 * @param oo 订单信息
	 * @param reqParam 请求参数
	 * @param detail 代扣细节
	 * @param map 请求信息
	 */
	public void setQueryOrderRB(OutOrders oo, String reqParam, RepaymentDetail detail, Map map) {
		String string = map.get("batch_content").toString();
		String[] strings = string.split(",");
		String result_msg = strings[12];

		//状态是I FF 不作处理
		if (!result_msg.equals("处理中")) {
			String resultPay = "";
			oo.setReturnParams(reqParam);
			oo.setOrderNo(detail.getOrderId());
			RepaymentDetail pd = new RepaymentDetail();
			pd.setId(detail.getId());

			if (result_msg.equals("成功")) {   // 支付成功
				oo.setStatus(OutOrders.STATUS_SUC);
				pd.setStatus(RepaymentDetail.STATUS_SUC);
				Repayment re = repaymentDao.selectByPrimaryKey(detail.getAssetRepaymentId());
				this.repay(re, detail);
			} else if (result_msg.equals("失败")) {   //支付失败
				oo.setStatus(OutOrders.STATUS_OTHER);
				pd.setStatus(RepaymentDetail.STATUS_OTHER);
			}
			repaymentDetailService.updateByPrimaryKeySelective(pd);
			outOrdersService.updateByOrderNo(oo);
		}

	}

	private boolean getIsOverdue(Integer repayId) {
		boolean isOverdue = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetRepaymentId", repayId);
		param.put("status", RenewalRecord.STATUS_SUCC);
		List<RenewalRecord> records = renewalRecordDao.findParams(param);
		Integer planLateFeeAgo = null;
		if(CollectionUtils.isNotEmpty(records)) {
			for (RenewalRecord renewalRecord : records) {
				planLateFeeAgo = renewalRecord.getPlanLateFee();
				if(planLateFeeAgo != null &&  planLateFeeAgo > 0) {
					isOverdue = true;
					break;
				}
			}
		}
		return isOverdue;
	}

	@Override
	public void repay(Repayment re, RepaymentDetail detail) {
		BorrowOrder bo = new BorrowOrder();
		bo.setId(re.getAssetOrderId());
		Repayment copy = new Repayment();
		copy.setId(re.getId());
		copy.setRepaymentedAmount(re.getRepaymentedAmount() + detail.getTrueRepaymentMoney());

		User user = userDao.selectCollectionByUserId(re.getUserId());
		User userCopy = new User();
		userCopy.setId(user.getId());

		// 全部还清
		if (copy.getRepaymentedAmount() >= re.getRepaymentAmount()) {
			if (re.getLateDay() > 0) {
				copy.setStatus(BorrowOrder.STATUS_YQYHK);
				bo.setStatus(BorrowOrder.STATUS_YQYHK);
				// 逾期已还款 告知催收
				/*collection(user, re, detail, Repayment.REPAY_COLLECTION);*/
				try {
					jedisCluster.set("REPAY_" + re.getId(), "" + re.getId());
					jedisCluster.del("OVERDUE_" + re.getId());
					log.info("collection repay success YQYHK REPAY_" + re.getId() + " value=" + jedisCluster.get("REPAY_" + re.getId()) + " OVERDUE=" + jedisCluster.get("OVERDUE_" + re.getId()));
				} catch (Exception e) {
					log.error("collection repay error YQYHK repaymentId=:{}", e);
				}
			} else {
//				BorrowOrder borrowOrder = borrowOrderDao.selectByPrimaryKey(re.getAssetOrderId());
				try {
					/*判断逾期续期后还款*/
					boolean isOverdueRenewal = getIsOverdue(re.getId());
					if(isOverdueRenewal){
						jedisCluster.set("REPAY_" + re.getId(), "" + re.getId());
						jedisCluster.del("OVERDUE_" + re.getId());
						log.info("collection repay success YQYHK REPAY_" + re.getId() + " value=" + jedisCluster.get("REPAY_" + re.getId()) + " OVERDUE=" + jedisCluster.get("OVERDUE_" + re.getId()));
					}
				} catch (Exception e) {
					log.error("push overdueRepay toCs error:{}", e);
				}
				copy.setStatus(BorrowOrder.STATUS_YHK);
				bo.setStatus(BorrowOrder.STATUS_YHK);
			}
			ThreadPool pool = ThreadPool.getInstance();
			pool.execute(new DtThread(UserPushUntil.PUSH_REPAYMENTSUCC, re.getUserId(), re.getAssetOrderId(), detail.getCreatedAt(), userService,
					pushUserService, borrowOrderService));
			if (User.CUSTOMER_NEW.equals(user.getCustomerType())) {
				userCopy.setCustomerType(User.CUSTOMER_OLD);
			}
			// 全部还款-更新info_user_info borrow_status 状态为不可见
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("USER_ID", user.getId());
			map.put("BORROW_STATUS", "0");
			// System.out.println("map=" + map + " 已还款，更新为不显示");
			indexDao.updateInfoUserInfoBorrowStatus(map);

			// 还款成功后提交催收
			copy.setRepaymentRealTime(null != detail.getCreatedAt() ? detail.getCreatedAt() : new Date());
			// try {
			// // 设置参数 可设置多个抽奖
			// HashMap<String, String> stepMap = new HashMap<String, String>();
			// stepMap.put("userPhone", user.getUserPhone());
			// stepMap.put("awardType", "REPAYMENT");
			//
			// logger.error(" addUserStep   REPAYMENT 请求参数：" +
			// stepMap.toString());
			// ServiceResult serviceResult =
			// jsStepRecordService.addUserStep(stepMap);
			// logger.error("addUserStep 抽奖返回>>>：" + serviceResult.getCode() +
			// ":" + serviceResult.getMsg());
			//
			// } catch (Exception e) {
			// logger.error("addUserStep error REPAYMENT",e);
			// }

		} else {
			if (re.getLateDay() > 0) {
				try {
					jedisCluster.set("OVERDUE_" + re.getId(), "" + re.getId());
					log.info("collection repay success BFHK OVERDUE_" + re.getId() + " value=" + jedisCluster.get("REPAY_" + re.getId()));
				} catch (Exception e) {
					log.error("collection repay error BFHK repaymentId={}", e);
				}
				// 逾期部分还款 告知催收
//				collection(user, re, detail, Repayment.BREPAY_COLLECTION);
			} else {
				copy.setStatus(BorrowOrder.STATUS_BFHK);
				bo.setStatus(BorrowOrder.STATUS_BFHK);
			}
		}

		// 还款金额
		Long money = detail.getTrueRepaymentMoney();
		// 已付的逾期费 = 所有还款金额 - 借款金额 - 手续费
		Long payedOver = copy.getRepaymentedAmount() - (re.getRepaymentInterest() + re.getRepaymentPrincipal());
		money = payedOver > 0 ? money - payedOver : money;
		if (money > 0) {
			// 把用户可借额度加上
			userCopy.setAmountAvailable(String.valueOf(Long.parseLong(user.getAmountAvailable()) + money));
			log.info("用户额度变回成功userId=" + userCopy.getId() + ",amountAvailable=" + userCopy.getAmountAvailable());
		} else {
			log.info("用户额度变回失败userId=" + userCopy.getId() + ",money=" + money + ",payedOver=" + payedOver + ",trueRepaymentMoney="
					+ detail.getTrueRepaymentMoney());
		}
		if (StringUtils.isNotBlank(userCopy.getCustomerType()) || StringUtils.isNotBlank(userCopy.getAmountAvailable())) {
			userDao.updateAmountAvailableByUserId(userCopy);
			log.error("用户额度保存成功userId=" + userCopy.getId() + ",amountAvailable=" + userCopy.getAmountAvailable());
		} else {
			log.error("用户额度保存失败userId=" + userCopy.getId() + ",customerType=" + userCopy.getCustomerType() + ",amountAvailable="
					+ userCopy.getAmountAvailable());
		}
		if (null != bo.getStatus()) {
			borrowOrderDao.updateByPrimaryKeySelective(bo);
		}
		repaymentDao.updateByPrimaryKeySelective(copy);

	}


	/**
	 * 续期
	 *
	 */
	@Override
	public void renewal(Repayment repayment, RenewalRecord record) {
		if (null == repayment) {
			repayment = this.selectByPrimaryKey(record.getAssetRepaymentId());
		}
		// 更新续期为成功
		record.setStatus(RenewalRecord.STATUS_SUCC);
		renewalRecordDao.updateByPrimaryKeySelective(record);

		BorrowOrder borrowOrder = new BorrowOrder();
		borrowOrder.setId(repayment.getAssetOrderId());
		borrowOrder.setStatus(BorrowOrder.STATUS_HKZ);
		borrowOrderService.updateById(borrowOrder);

		Repayment re = new Repayment();
		// 如果申请续期成功
		re.setId(repayment.getId());
		re.setRepaymentAmount(repayment.getRepaymentAmount() - repayment.getPlanLateFee());
		re.setPlanLateFee(repayment.getRepaymentAmount().intValue() - repayment.getPlanLateFee());
		re.setPlanLateFee(0);
		re.setTrueLateFee(0);
		// 还款日期 延后 （逾期天数+续期天数）
		re.setRepaymentTime(DateUtil.addDay(repayment.getRepaymentTime(), record.getRenewalDay()));
		re.setLateFeeStartTime(null);
		re.setInterestUpdateTime(null);
		re.setLateDay(0);
		re.setRenewalCount(repayment.getRenewalCount() + 1);
		re.setStatus(BorrowOrder.STATUS_HKZ);
		re.setCollection(Repayment.COLLECTION_NO);
		this.updateRenewalByPrimaryKey(re);

		// 如果是已逾期的续期（调用催收同步）
		if (repayment.getStatus().equals(BorrowOrder.STATUS_YYQ)) {
			try {
				jedisCluster.set("RENEWAL_" + re.getId(), "" + re.getId());
				log.info("collection renewal success RENEWAL_:{}",re.getId());
			} catch (Exception e) {
				log.error("collection renewal error repaymentId=:{}", e);
			}
		}
	}

	@Override
	public void updateRenewalByPrimaryKey(Repayment re) {
		repaymentDao.updateRenewalByPrimaryKey(re);
	}

    /**
     * 逾期
     *
     * @param re
     */
    @Override
    public void overdue(Repayment re, List<String> repaymentIds) {
        Date now = new Date();
        try {
            //计算两个日期之间相差的天数（预计还款时间，当前时间）
            int between = DateUtil.daysBetween(re.getRepaymentTime(), now);
            User userT = new User();
            userT.setId(re.getUserId() + "");
			userT.setLastOverDays(String.valueOf(between));
			userService.updateByPrimaryKeyUser(userT);
            if (between > 0) {
                // 滞纳金 = （借款到账金额 + 服务费） * 滞纳金服务费 / 10000 * 滞纳天数
				Integer lateFee = (int) ((re.getRepaymentPrincipal() + re.getRepaymentInterest()) * re.getLateFeeApr() / 10000 * between);
				BigDecimal productLateFee = borrowProductConfigDao.queryByOrderId(re.getAssetOrderId());
				if (productLateFee != null) {
					lateFee = productLateFee.intValue();
				}
                // 更新用户最近一次逾期总天数、历史逾期总记录数
                User user = userService.selectCollectionByUserId(re.getUserId());
                if (re.getLateDay() == 0) {
                    userT.setHistoryOverNum(String.valueOf(Integer.parseInt(user.getHistoryOverNum()) + 1));
                    re.setLateFeeStartTime(now);
                }

				// 更新repay的
				re.setLateDay(between);
				re.setInterestUpdateTime(now);

				re.setPlanLateFee(lateFee);
				//总还款金额（本金+服务费+滞纳金）
				re.setRepaymentAmount(re.getRepaymentPrincipal() + re.getRepaymentInterest() + lateFee);

                BorrowOrder borrowOrder = new BorrowOrder();
                borrowOrder.setId(re.getAssetOrderId());
                borrowOrder.setStatus(STATUS_YYQ);

                if(!STATUS_YYQ.equals(re.getStatus())){
                    try{
                        OrderLogModel orderLogModel = new OrderLogModel();
                        orderLogModel.setUserId(String.valueOf(re.getUserId()));
                        orderLogModel.setCreateTime(now);
                        orderLogModel.setUpdateTime(now);
                        orderLogModel.setBorrowId(String.valueOf(borrowOrder.getId()));
//                                orderLogModel.setOperateId(OperateType.BORROW.getCode());
                        orderLogModel.setOperateType(OperateType.BORROW.getCode());
                        orderLogModel.setBeforeStatus(String.valueOf(re.getStatus()));
                        orderLogModel.setAfterStatus(String.valueOf(STATUS_YYQ));

                        orderLogModel.setAction(OrderChangeAction.OVERDUE.getCode());
                        orderLogModel.setRemark(OrderChangeAction.OVERDUE.getMessage());
                        orderLogService.addNewOrderChangeLog(orderLogModel);
                    }catch (Exception e){
                        //log.error("add order log error:{}",e);
                    }
                }
//				if (between == 1) {
//					re.setStatus(STATUS_YYQ);
//					ThreadPool pool = ThreadPool.getInstance();
//					pool.execute(new DtThread(UserPushUntil.PUSH_OVERDUESUCC, re.getUserId(), re.getAssetOrderId(), now, userService,
//							pushUserService, borrowOrderService));
//				}


				borrowOrderService.updateById(borrowOrder);

                // 如果未催收 进入催收
                if (Repayment.COLLECTION_NO == re.getCollection()) {
                    try {
                        repaymentIds.add(re.getId().toString());
                        re.setCollection(Repayment.COLLECTION_YES);
                        /*String msg_limit = backDictionaryDao.findDictionary(Constant.MSG_LIMIT).get(0).getDataValue();
                        if (repaymentIds.size() == Integer.parseInt(msg_limit)) {
                            //加入催收队列
                            RocketMqUtil.sendMqMessage(PropertiesUtil.get("MQ_APPNAME")+ "_" + Constant.TOPIC_REPAYID, Constant.OVERDUE_TAG, StringUtils.join(repaymentIds, ","));
                            repaymentIds.clear();
                        }*/
                    } catch (Exception e) {
                        log.error("collection overdue error repaymentId=:{}", e);
                    }
                }
                this.updateByPrimaryKeySelective(re);
            }
        } catch (Exception e) {
            log.error("overdue error repaymentId =:{}",re.getId(), e);
        }
    }

	/**
	 * 催收调用
	 *
	 * @param u  用户信息
	 * @param re 还款信息
	 */
	@Override
	public Repayment collection(User u, Repayment re, RepaymentDetail detail, int collType) {
		log.info("collection applying userId = " + u.getId() + " - repaymentId " + re.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BorrowOrder bo = borrowOrderService.findOneBorrow(re.getAssetOrderId());

		List<Map> loanList = new ArrayList<Map>();

		Map<String, Object> loanMap = new HashMap<String, Object>();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", u.getId());
		List<UserContacts> userContacts = userContactsService.selectUserContacts(params);
		// 应还本金
		int receivablePrinciple = re.getRepaymentAmount().intValue() - re.getPlanLateFee();
		// 实收的利息 = 已还金额 - 应还本金
		int realPenlty = re.getRepaymentedAmount().intValue() - receivablePrinciple;

		if (collType == Repayment.OVERDUE_COLLECTION) {
			// 紧急联系人
			List<Map> mmanUserRelas = new ArrayList<Map>();
			Map<String, String> mmanUserRela = new HashMap<String, String>();
			mmanUserRela.put("id", "");
			mmanUserRela.put("userId", u.getId());
			mmanUserRela.put("contactsKey", "1");
			mmanUserRela.put("relaKey", u.getFristContactRelation());
			mmanUserRela.put("infoName", u.getFirstContactName());
			mmanUserRela.put("infoValue", u.getFirstContactPhone());
			mmanUserRela.put("contactsFlag", "1");
			mmanUserRelas.add(mmanUserRela);

			mmanUserRela = new HashMap<String, String>();
			mmanUserRela.put("id", "");
			mmanUserRela.put("userId", u.getId());
			mmanUserRela.put("contactsKey", "2");
			mmanUserRela.put("relaKey", u.getSecondContactRelation());
			mmanUserRela.put("infoName", u.getSecondContactName());
			mmanUserRela.put("infoValue", u.getSecondContactPhone());
			mmanUserRela.put("contactsFlag", "1");
			mmanUserRelas.add(mmanUserRela);

			for (UserContacts contacts : userContacts) {
				mmanUserRela = new HashMap<String, String>();
				mmanUserRela.put("id", contacts.getId());
				mmanUserRela.put("userId", u.getId());
				mmanUserRela.put("contactsKey", "2");
				mmanUserRela.put("infoName", contacts.getContactName());
				mmanUserRela.put("infoValue", contacts.getContactPhone());
				mmanUserRelas.add(mmanUserRela);
			}

			loanMap.put("mmanUserInfo", u);
			loanMap.put("mmanUserRelas", mmanUserRelas);

			// 银行卡信息
			Map<String, Object> bankCard = new HashMap<String, Object>();
			params.put("limit", 1);
			List<UserCardInfo> userCardInfos = userBankService.findUserCardByUserId(params);
			if (null != userCardInfos && userCardInfos.size() > 0) {
				UserCardInfo cardInfo = userCardInfos.get(0);
				bankCard.put("id", cardInfo.getId());
				bankCard.put("userId", cardInfo.getUserId());
				bankCard.put("bankCard", cardInfo.getCard_no());
				bankCard.put("depositBank", cardInfo.getBankName());
				bankCard.put("mobile", cardInfo.getPhone());
			}

			loanMap.put("bankCard", bankCard);

			// 还款详情
			List<Map> creditLoanPayDetails = new ArrayList<Map>();
			List<RepaymentDetail> rds = this.findDetailsByRepId(re.getId());
			Map<String, Object> creditLoanPayDetail;
			for (RepaymentDetail rd : rds) {
				creditLoanPayDetail = new HashMap<String, Object>();
				creditLoanPayDetail.put("id", rd.getId());
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", re.getId());
				creditLoanPayDetail.put("payId", temp);

				if (realPenlty <= 0) {
					creditLoanPayDetail.put("realMoney", rd.getTrueRepaymentMoney() / 100.00);
					creditLoanPayDetail.put("realPenlty", 0);

					creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
					creditLoanPayDetail.put("realInterest", re.getPlanLateFee() / 100.00);
				} else {
					creditLoanPayDetail.put("realMoney", (rd.getTrueRepaymentMoney() - realPenlty) / 100.00);
					creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);

					creditLoanPayDetail.put("realPrinciple", 0);
					creditLoanPayDetail.put("realInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);
				}
				creditLoanPayDetail.put("returnType", rd.getRepaymentType());
				creditLoanPayDetail.put("remark", rd.getRemark());

				creditLoanPayDetails.add(creditLoanPayDetail);
			}

			loanMap.put("creditLoanPayDetails", creditLoanPayDetails);
		}

		if (collType == Repayment.REPAY_COLLECTION || collType == Repayment.OVERDUE_COLLECTION || collType == Repayment.RENEWAL_COLLECTION) {

			// 借款信息
			Map<String, Object> mmanUserLoan = new HashMap<String, Object>();
			mmanUserLoan.put("id", bo.getId());
			mmanUserLoan.put("userId", u.getId());
			mmanUserLoan.put("loanPyId", bo.getOutTradeNo());
			mmanUserLoan.put("loanMoney", bo.getMoneyAmount() / 100.00);
			mmanUserLoan.put("loanRate", bo.getApr());
			mmanUserLoan.put("loanPenalty", re.getPlanLateFee() / 100.00);
			mmanUserLoan.put("loanPenaltyRate", bo.getLateFeeApr());
			mmanUserLoan.put("loanStartTime", sdf.format(re.getCreditRepaymentTime()));
			mmanUserLoan.put("loanEndTime", sdf.format(re.getRepaymentTime()));
			mmanUserLoan.put("loanStatus", re.getStatus());

			loanMap.put("mmanUserLoan", mmanUserLoan);
		}

		// 还款信息
		Map<String, Object> creditLoanPay = new HashMap<String, Object>();
		creditLoanPay.put("id", re.getId());
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("id", bo.getId());

		creditLoanPay.put("loanId", temp);
		creditLoanPay.put("receivableStartdate", sdf.format(re.getCreditRepaymentTime()));
		creditLoanPay.put("receivableDate", sdf.format(re.getRepaymentTime()));
		creditLoanPay.put("receivableMoney", re.getRepaymentAmount() / 100.00);
		// creditLoanPay.put("receivablePrinciple", receivablePrinciple /
		// 100.00);
		// creditLoanPay.put("receivableInterest", re.getPlanLateFee() /
		// 100.00);
		creditLoanPay.put("realMoney", re.getRepaymentedAmount() / 100.00);
		if (realPenlty <= 0) {
			creditLoanPay.put("receivablePrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
			creditLoanPay.put("receivableInterest", re.getPlanLateFee() / 100.00);
			creditLoanPay.put("realgetPrinciple", re.getRepaymentedAmount() / 100.00);
			creditLoanPay.put("realgetInterest", 0);
		} else {
			creditLoanPay.put("receivablePrinciple", 0);
			creditLoanPay.put("receivableInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);
			creditLoanPay.put("realgetPrinciple", (re.getRepaymentedAmount().intValue() - realPenlty) / 100.00);
			creditLoanPay.put("realgetInterest", realPenlty / 100.00);
		}

		creditLoanPay.put("status", re.getStatus());

		loanMap.put("creditLoanPay", creditLoanPay);

		if (collType == Repayment.BREPAY_COLLECTION || collType == Repayment.REPAY_COLLECTION) {
			// 还款详情
			List<Map> creditLoanPayDetails = new ArrayList<Map>();
			Map<String, Object> creditLoanPayDetail;
			creditLoanPayDetail = new HashMap<String, Object>();
			creditLoanPayDetail.put("id", detail.getId());
			temp = new HashMap<String, Object>();
			temp.put("id", re.getId());
			creditLoanPayDetail.put("payId", temp);

			if (realPenlty <= 0) {
				creditLoanPayDetail.put("realMoney", detail.getTrueRepaymentMoney() / 100.00);
				creditLoanPayDetail.put("realPenlty", 0);

				creditLoanPayDetail.put("realPrinciple", (receivablePrinciple - re.getRepaymentedAmount()) / 100.00);
				creditLoanPayDetail.put("realInterest", re.getPlanLateFee() / 100.00);
			} else {
				creditLoanPayDetail.put("realMoney", (detail.getTrueRepaymentMoney() - realPenlty) / 100.00);
				creditLoanPayDetail.put("realPenlty", realPenlty / 100.00);

				creditLoanPayDetail.put("realPrinciple", 0);
				creditLoanPayDetail.put("realInterest", (re.getRepaymentAmount() - re.getRepaymentedAmount()) / 100.00);
			}
			creditLoanPayDetail.put("returnType", detail.getRepaymentType());
			creditLoanPayDetail.put("remark", detail.getRemark());

			creditLoanPayDetails.add(creditLoanPayDetail);

			loanMap.put("creditLoanPayDetails", creditLoanPayDetails);
		}

		loanList.add(loanMap);

		Map<String, Object> collectionRelevantJson = new HashMap<String, Object>();
		collectionRelevantJson.put("collectionRelevantJson", loanList);

		// 设置参数 可设置多个
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("collectionRelevantJson", JSONObject.fromObject(collectionRelevantJson).toString()));
		try {
			String result = HttpUtil.getInstance().post(CollectionConstant.getCollectionPath(), postParams);
			JSONObject obj = JSONObject.fromObject(result);
			if (obj.getString("code").equals("0")) {
				re.setCollection(Repayment.COLLECTION_YES);
			}
		} catch (Exception e) {
			log.error("collection error:{}", e);
		}
		return re;
	}

	@Override
	public List<Repayment> findAllByBorrowId(Integer borrowId) {
		return repaymentDao.findAllByBorrowId(borrowId);
	}

	@Override
	public boolean insertByBorrorOrder(BorrowOrder borrowOrder) {
		try {
			Date fkDate = borrowOrder.getLoanTime();
			Repayment repayment = new Repayment();
			repayment.setUserId(borrowOrder.getUserId());
			repayment.setAssetOrderId(borrowOrder.getId());
			repayment.setRepaymentAmount(Long.valueOf(borrowOrder.getMoneyAmount()));
			repayment.setLateFeeApr(borrowOrder.getLateFeeApr());
			repayment.setRepaymentedAmount(0L);
			repayment.setRepaymentPrincipal(Long.valueOf(borrowOrder.getIntoMoney()));
			repayment.setRepaymentInterest(Long.valueOf(borrowOrder.getLoanInterests()));

			repayment.setFirstRepaymentTime(DateUtil.addDay(fkDate, borrowOrder.getLoanTerm() - 1));// 放款时间加上借款期限
			repayment.setRepaymentTime(DateUtil.addDay(fkDate, borrowOrder.getLoanTerm() - 1));// 放款时间加上借款期限
			repayment.setCreditRepaymentTime(fkDate);
			// repayment.setCreatedAt(fkDate);
			// repayment.setUpdatedAt(fkDate);
			repayment.setStatus(borrowOrder.getStatus());
			repaymentDao.insertSelective(repayment);
			//地推 借款成功
			ThreadPool pool = ThreadPool.getInstance();
			pool.execute(new DtThread(UserPushUntil.PUSH_BORROWSUCC, repayment.getUserId(), repayment.getAssetOrderId(), fkDate, userService,
					pushUserService, borrowOrderService));
			final String userPhone = borrowOrder.getUserPhone();
			final double intoMoney = (borrowOrder.getIntoMoney() / 100.00);
			ThreadPool3.getInstance().run(new Runnable() {
				@Override
				public void run() {
					try {
						SendSmsUtil.sendSmsDiyCL(userPhone, SendSmsUtil.templateld44636, PropertiesUtil.get("APP_NAME") + "##" + intoMoney);

					}catch (Exception e){
						log.error("send sms error:{}",e);
					}
				}
			});
		} catch (Exception e) {
			log.error("create repayment insertByBorrorOrder:{}", e);
		}

		// //借款成功
		// try{
		// //生成大抽奖的抽奖码
		// HashMap<String,String> stepMap=new HashMap<String,String>();
		// stepMap.put("userPhone", borrowOrder.getUserPhone());
		// stepMap.put("awardType", "BORROW");
		// logger.error("addUserStep   BORROW  请求参数："+stepMap.toString());
		// ServiceResult serviceResult=
		// jsStepRecordService.addUserStep(stepMap);
		// logger.error("addUserStep 抽奖返回>>>："+serviceResult.getCode()+":"+serviceResult.getMsg());
		// }catch (Exception e) {
		// logger.error("addUserStep error BORROW",e);
		// }

		return true;
	}


	@Override
	public int findParamsCount(HashMap<String, Object> params) {
		return repaymentDao.findParamsCount(params);
	}


	@Override
	public List<AssetBorrowAssign> findAssetBorrowAssignByCreateTime(Map<String, Object> hashMap) {
		return repaymentDao.findAssetBorrowAssignByCreateTime(hashMap);
	}

	@Override
	public int insertAssetBorrowAssign(AssetBorrowAssign assetBorrowAssign) {
		return repaymentDao.insertAssetBorrowAssign(assetBorrowAssign);
	}




	@Override
	public int updateAssetBorrowAssignById(AssetBorrowAssign assetBorrowAssign) {
		return repaymentDao.updateAssetBorrowAssignById(assetBorrowAssign);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<ShowKeFuMessage> findAssignPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "Repayment");
		return paginationDao.findPage("seleShowKeFuList", "findAssignParamsCount", params, "web");
	}



	@Override
	public List<ShowKeFuMessage> seleShowKeFuList(Map<String, Object> map) {
		return repaymentDao.seleShowKeFuList(map);
	}


	@Override
	public int insertIntoRemak(Remark remark) {
		return repaymentDao.insertIntoRemak(remark);
	}
	@Override
	public List<String> getRemarkIdByOrderId(String assignId){
		return repaymentDao.getRemarkIdByOrderId(assignId);
	}

	@Override
	public void updateRemarkStatus(List<String> ids) {
		repaymentDao.updateRemarkStatus(ids);
	}

	@Override
	public List<Remark> selectRemarkByCondition(HashMap<String, Object> hashMap) {
		List<Remark> remarkList = repaymentDao.selectRemarkByCondition(hashMap);
		if (remarkList == null) {
			remarkList = new ArrayList<>();
		}
		return remarkList;
	}

	@Override
	public int reAssign(BackUser backUser,AssetBorrowAssign result){

		AssetBorrowAssign assetBorrowAssign = new AssetBorrowAssign();
		assetBorrowAssign.setBorrowOrderId(result.getBorrowOrderId());
		assetBorrowAssign.setJobName(backUser.getUserName());
		assetBorrowAssign.setJobId(backUser.getId());
		assetBorrowAssign.setCreateTime(new Date());
//		assetBorrowAssign.setDelFlag(1);
		assetBorrowAssign.setRemarkId(0);
		assetBorrowAssign.setUpdateTime(new Date());
		assetBorrowAssign.setAssetId(result.getAssetId());
		assetBorrowAssign.setAssignType(1);
		return repaymentDao.insertAssetBorrowAssign(assetBorrowAssign);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<Map<String,Object>> assignStatistics(HashMap<String,Object> params) {
		params.put(Constant.NAME_SPACE,"Repayment");
		return paginationDao.findPage("assignStatistics", "assignStatisticsCount",(HashMap)params , "web");
		//return repaymentDao.assignStatistics(assetBorrowAssign);
	}

	@Override
	public AssetBorrowAssign findAssetBorrowAssignById(Integer id) {
		Map<String,Object> assignMap = new HashMap<>();
		assignMap.put("id",id);
		assignMap.put("assignType",Constant.ASSIGN_TYPE_SYSTEM);
		List<AssetBorrowAssign> assetBorrowAssignList = repaymentDao.findAssetBorrowAssignByCreateTime(assignMap);
		if (assetBorrowAssignList != null && assetBorrowAssignList.size() > 0) {
			return assetBorrowAssignList.get(0);
		}
		return null;
	}

	@Override
	public List<ShowKeFuMessage> getNoPayAssetBorrowAssign(HashMap<String, Object> params){
		return repaymentDao.seleShowKeFuList(params);
	}

	@Override
	public List<Map<String,Object>> selectAssignStatisticsByCondition(Map<String,Object> params) {
		return repaymentDao.selectAssignStatisticsByCondition(params);
	}

	@Override
	public List<Map<String,Object>> selectAssignStatisticsForSystemSend(Map<String,Object> params) {
		return repaymentDao.selectAssignStatisticsForSystemSend(params);
	}

	@Override
	public List<Map<String,Object>> selectAssignStatisticsForArtificialSend(Map<String,Object> params) {
		return repaymentDao.selectAssignStatisticsForArtificialSend(params);
	}

	@Override
	public List<Integer> queryAssignByCondition(HashMap<String, Object> params) {
		return repaymentDao.queryAssignByCondition(params);
	}

	@Override
	public String getClientByUserId(Integer userId) {
		return repaymentDao.getClientByUserId(userId);
	}

	@Override
	public String selectBorrowOrderIdByAssignId(Integer assignId) {
		return repaymentDao.selectBorrowOrderIdByAssignId(assignId);
	}

	@Override
	public void updateAssignDelFlag(Integer assignId) {
		repaymentDao.updateAssignDelFlag(assignId);
	}

	@Override
	public Integer userBorrowCount(Integer status,Integer userId) {
		return repaymentDao.userBorrowCount(status,userId);
	}

	@Override
	public Integer selectAssetBorrowAssign(Integer id) {
		return repaymentDao.selectAssetBorrowAssign(id);
	}
}
