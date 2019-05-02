package com.info.risk.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.constant.Constant;
import com.info.risk.dao.IRiskCreditLogDao;
import com.info.risk.dao.IRiskCreditUserDao;
import com.info.risk.dao.IRiskRuleCalDao;
import com.info.risk.pojo.RiskCreditLog;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskManageRule;
import com.info.risk.pojo.RiskRuleCal;
import com.info.risk.pojo.RiskRuleProperty;
import com.info.risk.utils.BeanReflectUtil;
import com.info.risk.utils.ConstantRisk;
import com.info.risk.utils.RiskCreditUserUtil;
import com.info.risk.utils.ThreadPool2;
import com.info.web.dao.IPaginationDao;
import com.info.web.service.IInfoIndexService;
import com.info.web.util.JedisDataClientFK;
import com.info.web.util.PageConfig;
import redis.clients.jedis.JedisCluster;

/**
 * 类描述： 此类的所有更新方法，都会先调用insert方法.insert方法内部逻辑是，先根据userId查询数据库中是否有该记录<br>
 * 如果没有该记录则插入一条仅包含userId、用户名、身份证号码、性别、年龄的数据 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-12-12 下午05:14:12 <br>
 */
@Slf4j
@Service
public class RiskCreditUserService implements IRiskCreditUserService {
	@Autowired
	private IRiskCreditUserDao riskCreditUserDao;
	@Autowired
	private IRiskCreditLogDao riskCreditLogDao;
	@Autowired
	private IBqsService bqsService;
	@Autowired
	private IInfoIndexService infoIndexService;
	@Autowired
	private IRiskManageRuleService riskManageRuleService;
	@Autowired
	private IRiskRuleCalDao riskRuleCalDao;
	@Autowired
	private IInfoIndexService indexService;
	@Autowired
	private IYxService yxService;
	@Autowired
	private IPaginationDao paginationDao;
	@Autowired
	private IZzcService zzcService;
	@Autowired
	JedisCluster jedisCluster;
//	//写入key
//	@Value("#{propSettings['redis.inputkey']}")
//	private String riskIt;
//
//	//读取key
//	@Value("#{propSettings['redis.outputkey']}")
//	private String riskOt;

	@Override
	public int updateUserMyHb(RiskCreditUser riskCreditUser) {
		return riskCreditUserDao.updateUserMyHb(riskCreditUser);
	}

	@Override
	public void updateZm(RiskCreditUser riskCreditUser) {
		riskCreditLogDao
				.insert(new RiskCreditLog(riskCreditUser.getUserId(), riskCreditUser.toString(), ConstantRisk.GET_INDUSTY, ConstantRisk.ZMXY));
		riskCreditUserDao.updateUserZmIndusty(riskCreditUser);
		riskCreditUserDao.updateNewFlag(riskCreditUser.getUserId());
	}

	/**
	 *
	 * 重新计算用户借款额度，计算机审额度,刚认证完的用户调用和申请借款时调用
	 * @param id 征信主键ID<br>
	 */
	public String updateCalUserMoney(Integer id, String dbTime) {
		String result = null;
		try {

			// 根据id查找risk_credit_user的的数据
			RiskCreditUser riskCreditUser = riskCreditUserDao.findById(id);
			//参数：查找user_info表的详细数据
			riskCreditUser.setJxlDetail(riskCreditUserDao.findDetailById(riskCreditUser.getUserId()));
			log.info("聚信立报告:{}",riskCreditUser.getJxlDetail());
			//在数据库risk_manage_rule查找 root_type=4的数据  root_type的含义1.子节点；2.决策树根节点(只能有一个)；3.准入原则根节点(只能有一个);4.信用额度根节点(只能有一个)
			List<RiskManageRule> root = riskManageRuleService.findAllByRootType(RiskManageRule.ROOT_TYPE_ROOT_ED);
			if (root != null && root.size() > 0) {
				Map<String, RiskManageRule> allRiskMap = SysCacheUtils.getAllRule();
				Map<String, String> baseMap = SysCacheUtils.getBaseRule();
				Map<String, String> propertyMap = SysCacheUtils.getConfigMap(ConstantRisk.BASE_RULE_PROPERTY);
				if (propertyMap != null && propertyMap.size() > 0) {
					for (String key : propertyMap.keySet()) {
						try {
							Object value = BeanReflectUtil.invokeGetMethod(RiskCreditUser.class, propertyMap.get(key), riskCreditUser);
							baseMap.put(key, value + "");
							//logger.info("key:"+key +"value:"+value);
						} catch (Exception e) {
							log.error("key error key=:{}error:{}" ,key, e);
						}
					}
					//根据父节点找到所有引用的子节点
					RiskCreditUserUtil.getInstance().findSon(root, allRiskMap, null);
					RiskManageRule riskManageRule = root.get(0);
					List<RiskRuleCal> calList = new ArrayList<>();
					RiskCreditUserUtil.getInstance().calSon2Parent(root, riskManageRule, baseMap, id, riskCreditUser.getAssetId(),
							riskCreditUser.getUserId(), calList);
					HashMap<String, Object> list = new HashMap<>();
					list.put("dbTime", dbTime);
					list.put("list", calList);
					//往risk_rule_cal${dbTime}中插入数据
					riskRuleCalDao.batchInsert(list);
					riskCreditUser.setRiskCalSuf(dbTime);
					//更新 risk_credit_user中 risk_cal_suf(风控过程数据所在表的后缀)  风控过程数据所在表的后缀
					riskCreditUserDao.updateSuf(riskCreditUser);
					//TODO tql 现在额度都改为1000
					//result = riskManageRule.getFormula();
					result = "1000";
					riskCreditUser.setAmountMax(new BigDecimal(result));
					//更新用户的机审额度及状态  操作表为user_info
					//riskCreditUserDao.updateUserMoney(riskCreditUser);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("userId", riskCreditUser.getUserId());
					// indexService.changeUserAmount(map);
					log.info("after update user amount");
					map.put("newAmountMax", new BigDecimal(result).multiply(new BigDecimal(100)).intValue());
					//更新用户的额度
					//borrowOrderService.changeUserLimit(map);
					log.info("after insert limit");
					//更新征信的机审额度及状态  risk_credit_user new_flag = 2
					//riskCreditUserDao.updateMoney(riskCreditUser);
				}
			}
		} catch (Exception e) {
			log.error("updateCalUserMoney error id=:{}error:{}",id, e);
		}
		return result;
	}

	/**
	 * 计算用户的借款额度
	 *
	 * @param riskCreditUser2
	 */
	@Override
	public void updateBorrowMoney(RiskCreditUser riskCreditUser2) {
		try {
			RiskCreditUser tmp = new RiskCreditUser();
			tmp.setUserId(riskCreditUser2.getUserId());//用户ID
			tmp.setAssetId(0);//

			//查询某个用户有没有辅助计算额度的记录 表：risk_credit_user user_id=#{userId} and asset_id=#{assetId}
			Integer id = riskCreditUserDao.findOneCal(tmp);
			log.info("在risk_credit_user中是否有用户数据是，此处的id是否为空:{}",id);
			if (id == null) {
				// 查询数据库没有已计算记录则插入
				riskCreditUser2.setAssetId(ConstantRisk.NO_ID);
				riskCreditUser2.setLastDays(-1);
				riskCreditUser2.setRiskStatus(5);
				riskCreditUserDao.insertCalMoney(riskCreditUser2);
				id = riskCreditUser2.getId();
			}

			//辅助计算额度时发送征信请求，成功的记录不再请求

			//获取发送请求后的状态以及数据
			ServiceResult serviceResult = updateSendZx(id);
			if (serviceResult.isSuccessed()) {
				log.info("更新用户的借款额度-------- ");
				//更新用户的借款额度
				updateCalUserMoney(id, riskRuleCalDao.findDbTime());
			} else if ("300".equals(serviceResult.getCode())) {
				// 更新额度
				String value = "0";
				riskCreditUser2.setAmountMax(new BigDecimal(value));
				//riskCreditUserDao.updateUserMoney(riskCreditUser2);
				HashMap<String, Object> map = new HashMap<>();
				map.put("userId", riskCreditUser2.getUserId());
				log.info("after update user amount");
				map.put("newAmountMax", new BigDecimal(value));
				//borrowOrderService.changeUserLimit(map);
				log.info("after insert limit");
				//riskCreditUserDao.updateMoney(riskCreditUser2);
			}

		} catch (Exception e) {
			log.error("updateBorrowMoney error riskCreditUser2=:{}error:{}",riskCreditUser2.toString(), e);
		}
	}

	/**
	 * 辅助计算额度时发送征信请求，成功的记录不再请求
	 *
	 * @param id
	 * @return 500是未知异常，200是全部接口正常并且通过准入原则；300是命中准入原则
	 */
	public ServiceResult updateSendZx(Integer id) {
		ServiceResult serviceResult2 = new ServiceResult("500", "未知异常");
		String msg = null;
		try {

			//根据用户id查找risk_credit_user 的数据（一条数据）
			RiskCreditUser riskCreditUser = riskCreditUserDao.findById(id);
			//参数功能：查找 user_info 的jxl_detail（聚信立成功采集后返回的数据） 的信息
			riskCreditUser.setJxlDetail(riskCreditUserDao.findDetailById(riskCreditUser.getUserId()));
			Integer userId = riskCreditUser.getUserId();
			StringBuffer errorMsg = new StringBuffer("");
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userName", riskCreditUser.getUserName());
			params.put("cardNum", riskCreditUser.getCardNum());
			params.put("userPhone", riskCreditUser.getUserPhone());
			params.put("userId", userId);
			String reportId = riskCreditUser.getTdReportId(); //// 同盾贷前审核报告标识

			Integer tdStatus = riskCreditUser.getTdStatus();
			Integer bqsStatus = riskCreditUser.getBqsStatus();
			Integer jyStatus = riskCreditUser.getJyStatus();
			Integer mgStatus = riskCreditUser.getMgStatus();
			Integer zmStatus = riskCreditUser.getZmStatus();
			Integer yxStatus = riskCreditUser.getYxStatus();
			Integer zzcStatus = riskCreditUser.getZzcStatus();
			Integer shStatus = riskCreditUser.getShStatus();
			String zzcId = riskCreditUser.getZzcFqzId(); //中智诚反欺诈ID号
			HashMap<String, Object> params2 = new HashMap<String, Object>();
			params2.putAll(params);
			if (StringUtils.isBlank(zzcId)) {
				// 将信息 发送到中智诚，获取返回的 返回状态的数据状态（成功后也包含返回的数据结果） url=ZZC_URL
				log.info("中智诚运行之前 getFqz-------");
				ServiceResult serviceResult = zzcService.getFqz(params2); // act是 ZZC_BLACK
				log.info("中智诚运行之后 getFqz-------");
				String str = ""; //综合两个 返回信息数据
				if (serviceResult.isSuccessed()) {
					//JSONObject jsonObject = JSONObject.fromObject(serviceResult.getMsg());
					JSONObject jsonFqz = JSONObject.fromObject(serviceResult.getMsg());
					log.info("中智诚 getFqz 执行成功   zzcId:{}",zzcId);
					// 将信息 发送到中智诚，获取返回的 返回状态的数据状态（成功后也包含返回的数据结果）
					log.info("中智诚 findFqz 执行之前-----");
					//中智诚黑名单查询
					serviceResult = zzcService.getBlackNew(params2);
					log.info("中智诚 findFqz 执行之后-----");
					if (serviceResult.isSuccessed()) {
						log.info("中智诚 findFqz 运行成功-----");
						zzcStatus = ConstantRisk.INTERFACE_SUC;
						JSONObject jsonBlack = JSONObject.fromObject(serviceResult.getMsg());
						JSONObject all = new JSONObject();
						all.putAll(jsonBlack);
						all.putAll(jsonFqz);
						log.info("中智诚 合并后的数据:{} ",all.toString());
						JSONObject jsonRuleReslut = all.getJSONObject("rule_result");
						log.info("合并后的json数据:{} count的数值是:{} ",jsonRuleReslut.getString("risk_level"),all.getString("count"));
						//RiskCreditUser riskCreditUser2 = RiskCreditUserUtil.getInstance().createZzc(id, serviceResult.getMsg());
						RiskCreditUser riskCreditUser2 = RiskCreditUserUtil.getInstance().createZzc(id, all.toString());
						riskCreditUser2.setZzcFqzId(zzcId);
						// 更新中智诚及更新时间 risk_credit_user
						riskCreditUserDao.updateZzc(riskCreditUser2);
						if (riskCreditUser2.getZzcBlack().intValue() == 1) {
							msg = "命中中智诚黑名单";
						} else if (riskCreditUser2.getZzcFqz().intValue() == 1) {
							msg = "命中中智诚反欺诈高";
						}
					} else {
						errorMsg.append("中智诚反欺诈查询失败;");
					}
				} else {
					errorMsg.append("生成中智诚ID号失败;");
				}
			}

			RiskCreditUser tmp = riskCreditUserDao.findUserDetail(riskCreditUser.getUserId());
			BigDecimal zmScore = tmp.getZmScore();
			Integer zmIndustyBlack = tmp.getZmIndustyBlack();
			Integer zmOver = tmp.getZmOver();
			Integer zmNoPayOver = tmp.getZmNoPayOver();
			riskCreditUser.setId(id);
			riskCreditUser.setUserId(riskCreditUser.getUserId());
			riskCreditUser.setZmScore(zmScore);
			riskCreditUser.setZmIndustyBlack(zmIndustyBlack);
			riskCreditUser.setZmOver(zmOver);
			riskCreditUser.setZmNoPayOver(zmNoPayOver);
			//更新征信表芝麻所有信息  risk_credit_user
			riskCreditUserDao.updateZm(riskCreditUser);
			zmStatus = ConstantRisk.INTERFACE_SUC;
			log.info("开始运行宜信请求。。。状态:{}",yxStatus);
			if (yxStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
				//发送宜信请求  并返回 请求后的状态以及返回的数据
				log.info("开始运行宜信请求之前。。。 传入参数:{}",params.toString());
				ServiceResult serviceResult = yxService.sendYx(params);
				log.info("开始运行宜信请求之后");
				if (serviceResult.isSuccessed()) {
					log.info("成功运行宜信请求");
					RiskCreditUser riskCreditUser2 = RiskCreditUserUtil.getInstance().createYx(userId, serviceResult.getMsg());
					if (riskCreditUser2 != null) {
						riskCreditUser2.setId(id);
						riskCreditUserDao.updateYx(riskCreditUser2);
						if (riskCreditUser2.getYxFxNum().intValue() > 0) {
							msg = "命中宜信风险名单";
						}
					} else {
						log.info("sendZx create yx return null");
					}
				}
			}
			//测试白骑士
			log.info("开始初始化白骑士");
			if (true) {//StringUtils.isBlank(msg)
				// 白骑士黑名单接口
				if (bqsStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					log.info("白骑士发送数据之前");
					ServiceResult serviceResult = bqsService.getRisk(params2);
					log.info("白骑士调用接口之后");
					if (serviceResult.isSuccessed()) {
						log.info("白骑士申请成功");
						riskCreditUser = RiskCreditUserUtil.getInstance().createBqs(id, serviceResult.getMsg());
						if (riskCreditUser != null) {
							bqsStatus = ConstantRisk.INTERFACE_SUC;
							riskCreditUserDao.updateBqs(riskCreditUser);
							if (riskCreditUser.getBqsBlack().intValue() == 2) {
								msg = "命中白骑士黑名单";
							}
						} else {
							errorMsg.append("本地解析白骑士征信信息错误;");
						}
					} else {
						errorMsg.append("查询白骑士征信信息错误:" + serviceResult.getMsg() + ";");
					}
				}
			}
			/*if (StringUtils.isBlank(msg)) {
				if (jyStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					ServiceResult serviceResult = jyzxService.getBorrow(params2);
					if (serviceResult.isSuccessed()) {
						riskCreditUser = RiskCreditUserUtil.getInstance().createJyzx(id, serviceResult.getMsg());
						if (riskCreditUser != null) {
							jyStatus = ConstantRisk.INTERFACE_SUC;
							riskCreditUserDao.updateJy(riskCreditUser);
							if (riskCreditUser.getJyOverNum().intValue() > 0) {
								msg = "命中91逾期记录";
							}
						} else {
							errorMsg.append("本地解析91征信信息错误;");
						}
					} else {
						errorMsg.append("查询91征信信息错误:" + serviceResult.getMsg() + ";");
					}
				}
			}*/
			//todo 现在跳过91征信
			if (StringUtils.isBlank(msg)) {
				if (jyStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					riskCreditUser = RiskCreditUserUtil.getInstance().createJyzx(id, null);
					if (riskCreditUser != null) {
						jyStatus = ConstantRisk.INTERFACE_SUC;
						//riskCreditUserDao.updateJy(riskCreditUser);
					}
				}
			}

			//测试蜜罐
			/*logger.info("开始初始化蜜罐");
			if (StringUtils.isBlank(msg)) {
				if (mgStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					logger.info("调用蜜罐之前。。。");
					ServiceResult serviceResult = jxlMgService.getDetail(params2);
					logger.info("调用蜜罐之后。。。");
					if (serviceResult.isSuccessed()) {
						logger.info("蜜罐成功");
						riskCreditUser = RiskCreditUserUtil.getInstance().createMg(id, serviceResult.getMsg());
						if (riskCreditUser != null) {
							mgStatus = ConstantRisk.INTERFACE_SUC;
							riskCreditUserDao.updateMg(riskCreditUser);
							if (riskCreditUser.getMgBlack().intValue() == 1) {
								msg = "命中蜜罐黑名单";
							}
						} else {
							errorMsg.append("本地解析密罐征信信息错误;");
						}
					} else {
						errorMsg.append("查询密罐征信信息错误:" + serviceResult.getMsg() + ";");
					}
				}
			}*/
			//干掉蜜罐
			if (StringUtils.isBlank(msg)) {
				if (mgStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					log.info("skip jxlMg");
					riskCreditUser = new RiskCreditUser(id, new BigDecimal("10"), 7, 7, 0);
					if (riskCreditUser != null) {
						log.info("skip jxlMg   ttt");
						mgStatus = ConstantRisk.INTERFACE_SUC;
						//riskCreditUserDao.updateMg(riskCreditUser);
					}
				}
			}


			//TODO 跳过算话
			if (StringUtils.isBlank(msg)) {
				if (shStatus.intValue() != ConstantRisk.INTERFACE_SUC.intValue()) {
					riskCreditUser.setShFqz(1);
					riskCreditUser.setShScore(new BigDecimal("590"));
					shStatus = ConstantRisk.INTERFACE_SUC;
					//riskCreditUserDao.updateSh(riskCreditUser);
				}
			}

			// 更新接口状态  表：risk_credit_user
			riskCreditUserDao.updateStatus(new RiskCreditUser(id, zmStatus, reportId, tdStatus, bqsStatus, jyStatus, mgStatus, zzcStatus, shStatus));
			serviceResult2 = new ServiceResult(ServiceResult.SUCCESS, "征信成功");
		} catch (Exception e) {
			log.error("updateSendZx error id=:{}error:{}",id, e);
		} finally {
			if (StringUtils.isNotBlank(msg)) {
				serviceResult2.setCode("300");
				serviceResult2.setMsg(msg);
			}
		}
		return serviceResult2;
	}



	public void updateReviewSuc(String dbTime, Integer id, RiskCreditUser riskCreditUser, String msg) {
		// 通过
		String remark = "机审通过，可放款。" + msg;
		HashMap<String, Object> params2 = new HashMap<String, Object>();
		params2.put("remark", remark);
		riskCreditUser.setRiskCalSuf(dbTime);
		riskCreditUserDao.updateSuf(riskCreditUser);
		params2.put("id", riskCreditUser.getAssetId());
		riskCreditUserDao.updateAssetsSuc(params2);
		riskCreditUserDao.updateRiskStatus(new RiskCreditUser(id, ConstantRisk.RISK_STATUS_SUC, remark));
	}



	/**
	 * 修改初审状态(初审不通过，建议复审)
	 *
	 */
	public void updateFirsReview(Integer id, Integer assetId, String remark, Integer status, Integer userId, String dbTime) {
		log.info("updateFirsReview");
		try {
			HashMap<String, Object> params2 = new HashMap<>();
			params2.put("remark", remark);
			params2.put("id", assetId);
			String st = "";
			if (status.intValue() == ConstantRisk.RISK_STATUS_FAIL.intValue()) {
				st = "-3";
				// 审核拒绝后恢复可借金额
				riskCreditUserDao.updateUserAvailable(riskCreditUserDao.findBorrowMoney(id));
				HashMap<String, Object> map = new HashMap<>();
				map.put("userId", userId);
				indexService.changeUserAmount(map);
			} else {
				st = "1";
			}
			final Integer userId2 = userId;
			final Integer assetId2 = assetId;
			final Integer status2 = Integer.valueOf(st);
		/*	ThreadPool3.getInstance().run(new Runnable() {
				@Override
				public void run() {
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("userId", userId2);
					params.put("assetId", assetId2);
					params.put("status", status2);
					params.put("soketOut", "5000");
					params.put("connOut", "5000");
					tsOrdersService.updateSy(params);
				}
			});*/
			RiskCreditUser riskCreditUser = new RiskCreditUser();
			riskCreditUser.setId(id);
			riskCreditUser.setRiskCalSuf(dbTime);
			riskCreditUserDao.updateSuf(riskCreditUser);
			/*params2.put("st", st);
			riskCreditUserDao.updateAssetsFail(params2);*/
			riskCreditUserDao.updateRiskStatus(new RiskCreditUser(id, status, remark));
		} catch (Exception e) {
			log.error("updateFirsReview error id=:{}error:{}",id , e);
		}
	}


	@Override
	public void updateFromRisk() {
//		log.info("updateFromRisk start");
//		//风控审核
//		String value = "";
//		try {
//			value = JedisDataClientFK.rpop(riskOt);//qbm_risk_O_t
//			log.info("updateFromRisk info data=:{}" ,value);
//			while (value != null) {
//				log.info(" info not null data=" + value);
//				final String tmp = value;
//				ThreadPool2.getInstance().run(new Runnable() {
//					@Override
//					public void run() {
//						JSONObject jsonObject = JSONObject.fromObject(tmp);
//						int flag = jsonObject.getInt("flag");
//						int id = jsonObject.getInt("rId");//征信表ID
//						int bId = jsonObject.getInt("bId");//借款订单ID
//						int uId = jsonObject.getInt("uId");//用户ID
//						String dbTime = jsonObject.getString("dbt");//处理时间
//						// 发送给新模型之前如果没有命中黑名单则以新模型结果为准(更新审核状态),否则只记录返回状态
//						if ("2".equals(jsonObject.get("blk"))) {//黑名单状态
//							// 通过，调用审核通过的方法
//							if (flag == 1) {
//								//
//								RiskCreditUser tmp = new RiskCreditUser();
//								tmp.setRiskCalSuf(dbTime);
//								tmp.setAssetId(bId);
//								updateReviewSuc(dbTime, id, tmp, "新风控审核通过");
//							} else {
//								// 不通过,调用原本审核不通过的方法
//								log.info("updateFromRisk  机审拒绝 ");
//								updateFirsReview(id, bId, "新风控反馈审核不通过", ConstantRisk.RISK_STATUS_FAIL, uId, dbTime);
//							}
//						}
//						riskCreditUserDao.updateNewRisk(new RiskCreditUser(id, tmp, new BigDecimal(jsonObject.getString("score")), flag));
//					}
//				});
//				value = JedisDataClientFK.rpop(riskOt);
//			}
//		} catch (Exception e) {
//			log.error("updateFromRisk error:{}",e);
//		}
	}

	@Override
	public int updateZmScore(RiskCreditUser riskCreditUser) {
		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.GET_SCORE,
		// ConstantRisk.ZMXY));
		return riskCreditUserDao.updateUserZmScore(riskCreditUser);
		// riskCreditUserDao.updateZmScore(riskCreditUser);
	}

	@Override
	public int updateTdDetail(RiskCreditUser riskCreditUser) {
		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.TD_PRELOAN_REPORT,
		// ConstantRisk.TD));

		return riskCreditUserDao.updateTdDetail(riskCreditUser);
	}

	@Override
	public int updateBqs(RiskCreditUser riskCreditUser) {

		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getId(),
		// riskCreditUser.toString(), ConstantRisk.BQS_RISK,
		// ConstantRisk.BQS));
		return riskCreditUserDao.updateBqs(riskCreditUser);
	}

	@Override
	public int updateJy(RiskCreditUser riskCreditUser) {

		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.JYZX_BORROW,
		// ConstantRisk.JYZX));
		return riskCreditUserDao.updateJy(riskCreditUser);
	}

	@Override
	public int updateMg(RiskCreditUser riskCreditUser) {

		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.MG_BLACK,
		// ConstantRisk.MG));
		return riskCreditUserDao.updateMg(riskCreditUser);
	}

	@Override
	public int updateJxl(RiskCreditUser riskCreditUser) {

		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.JXL_ANALYSIS,
		// ConstantRisk.JXL));
		riskCreditUserDao.updateUserJxl(riskCreditUser);
		return riskCreditUserDao.updateNewFlag(riskCreditUser.getUserId());
		// riskCreditUserDao.updateJxl(riskCreditUser);
	}

	@Override
	public List<RiskRuleProperty> findRuleProperty(HashMap<String, Object> params) {
		return riskCreditUserDao.findRuleProperty(params);
	}

	@Override
	public int updateJxlToken(RiskCreditUser riskCreditUser) {
		// riskCreditLogDao.insert(new RiskCreditLog(riskCreditUser.getUserId(),
		// riskCreditUser.toString(), ConstantRisk.GET_TOKEN,
		// ConstantRisk.JXL));
		// riskCreditUserDao.updateJxlToken(riskCreditUser);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", riskCreditUser.getUserId());
		this.infoIndexService.authMobile(map);// 初始设置
		return riskCreditUserDao.updateUserJxlToken(riskCreditUser);
	}

	@Override
	public List<RiskCreditUser> findByUserId(Integer userId) {
		return riskCreditUserDao.findByUserId(userId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<HashMap<String, Object>> findJxlPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RiskCreditUser");
		return paginationDao.findPage("findJxlWaitReport", "findJxlWaitReportCount", params, "risk");
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<HashMap<String, Object>> findAnalysisPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RiskCreditUser");
		return paginationDao.findPage("findWaitAnalysis", "findWaitAnalysisCount", params, "risk");
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<RiskCreditUser> findCalPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "RiskCreditUser");
		return paginationDao.findPage("findCalMoney", "findCalMoneyCount", params, "risk");
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageConfig<HashMap<String, Object>> findCajlPage(HashMap<String, Object> params) {
		params.put(Constant.NAME_SPACE, "BorrowOrder");
		return paginationDao.findPage("findWaitCajlAnalysis", "countAssetBorrowOrderDevice", params, "web");
	}



}
