package com.info.risk.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.info.web.pojo.BorrowOrder;
import com.info.web.service.IBackConfigParamsService;
import com.info.web.service.IBorrowOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.constant.Constant;
import com.info.risk.dao.IRiskCreditUserDao;
import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.utils.ConstantRisk;
import com.info.risk.utils.GzipUtil;
import com.info.risk.utils.RiskCreditUserUtil;
import com.info.risk.utils.ThreadPool;
import com.info.risk.utils.ThreadPool2;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.User;
import com.info.web.service.IUserService;
import com.info.web.util.PageConfig;

@Slf4j
@Component("riskTaskJob")
public class RiskTaskService implements IRiskTaskService {

	@Autowired
	private IJxlService jxlService;
	@Autowired
	private IRiskCreditUserDao riskCreditUserDao;
	@Autowired
	private IRiskCreditUserService riskCreditUserService;
	@Autowired
	JedisCluster jedisCluster;
	@Autowired
	private IBackConfigParamsService backConfigParamsService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IBorrowOrderService borrowOrderService;
	int day = 2 * 60 * 24;

	/*
	 * @see com.info.risk.service.IRiskTaskService
	 *
	 */
	@Override
	public void findJxlReport() {
		log.info("findJxlReport is running");
		String key2 = ConstantRisk.JXL_WAIT_ANALYSIS;
		if (jedisCluster.get(key2) != null) {
			log.error("last findJxlReport is running");
		} else {
			try {
				jedisCluster.setex(key2, ConstantRisk.FLAG_SECOND * day, key2);
				HashMap<String, Object> params = new HashMap<>();
				Integer size = 5;
				params.put(Constant.PAGE_SIZE, size);
				//查找待认证用户数
				int totalPageNum = riskCreditUserDao.findJxlWaitReportCount(params);
				int total = 0;
				if (totalPageNum > 0) {
					if (totalPageNum % size > 0) {
						total = totalPageNum / size + 1;
					} else {
						total = totalPageNum / size;
					}
				}
				//每次5个用户
				for (int i = 0; i < total; i++) {
					params.put(Constant.CURRENT_PAGE, i);
					PageConfig<HashMap<String, Object>> pm = riskCreditUserService.findJxlPage(params);
					List<HashMap<String, Object>> list = pm.getItems();
					if (list != null && list.size() > 0) {
						for (final HashMap<String, Object> map : list) {
							String value = map.get("userId") + "";
							final String key = ConstantRisk.JXL_REPORT + value;
							try {
								if (jedisCluster.get(key) != null) {
									log.info("others is findJxlReport key=:{}" ,key);
								} else {
									jedisCluster.setex(key, ConstantRisk.FLAG_SECOND * day, value);
									ThreadPool.getInstance().run(new Runnable() {
										@Override
										public void run() {
											HashMap<String, Object> map1 = new HashMap<String, Object>();
											map1.putAll(map);
											// if
											// (riskCreditUserDao.findJxlHelp(map1)
											// <= 0) {
											// logger.info("report redis is null but db had deal");
											// } else {
											try {
												ServiceResult serviceResult = jxlService.findUserReport(map1);
												if (serviceResult.isSuccessed()) {
													map1.put("detail", serviceResult.getMsg());
													RiskCreditUser riskCreditUser3 = RiskCreditUserUtil.getInstance().createJxl(map1);
													if (riskCreditUser3 != null) {
														riskCreditUserService.updateJxl(riskCreditUser3);
													} else {
														// jedisCluster.del(key);
														log.info("updateJxl error riskCreditUser3=:{}",riskCreditUser3);
													}
												}
											} catch (Exception e) {
												jedisCluster.del(key);
												log.error("findJxlReport error map1=:{}" ,map1);
											} finally {
												jedisCluster.del(key);
											}

											// }
										}
									});
								}

							} catch (Exception e) {
								jedisCluster.del(key);
								log.error("findJxlReport error map=:{}error:{}",map, e);
							}
						}
						list = null;
					} else {
						break;
					}
				}
			} catch (Exception e) {
				jedisCluster.del(key2);
				log.error("findJxlReport error:{}", e);
			} finally {
				jedisCluster.del(key2);
			}
		}
	}

	@Override
	public void findMobileReport() {
		String suc = Constant.MX_CALL_BACK_SUC + "*";
		String fail = Constant.MX_CALL_BACK_FAIL + "*";
		TreeSet<String> keys1 = new TreeSet<>();
		TreeSet<String> keys2 = new TreeSet<>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys1.addAll(connection.keys(suc));
				keys2.addAll(connection.keys(fail));
			} catch (Exception e) {
				log.error("findMobileReport error:{}",e);
			} finally {
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		if (keys2.size() > 0) {
			for (final String key : keys2) {
				ThreadPool.getInstance().run(new Runnable() {
					@Override
					public void run() {
						String userId = key.substring(Constant.MX_CALL_BACK_FAIL.length());
						log.info("userId=:{},get report fail,reason=:{} ",userId,jedisCluster.get(key));
						// 更新为未认证
						userService.updateJxl(Integer.valueOf(userId));
						jedisCluster.del(key);
					}
				});
			}
		}
		if (keys1.size() > 0) {
			Map<String, String> keys = SysCacheUtils.getConfigParams(BackConfigParams.MX);
			final String url = keys.get("MX_URL");
			final String token = keys.get("MX_TOKEN");
			for (final String key : keys1) {
				final String userId = key.substring(Constant.MX_CALL_BACK_SUC.length());
				final String taskId = jedisCluster.get(key);
				final String key2 = ConstantRisk.JXL_REPORT + userId;
				if (jedisCluster.get(key2) != null) {
					log.info("others is findMobileReport key2=:{}",key2);
				} else {
					try {
						// 获取报告
						ThreadPool.getInstance().run(new Runnable() {
							@Override
							public void run() {
								jedisCluster.setex(key2, ConstantRisk.FLAG_SECOND * 2, userId);
								User user = userService.searchByUserid(Integer.valueOf(userId));
								String relation1 = User.CONTACTS_FAMILY.get(user.getFristContactRelation());
								String relation2 = User.CONTACTS_OTHER.get(user.getSecondContactRelation());
								String url2 = url + "/mobiles/" + user.getUserPhone() + "/jxreport?name=" + user.getRealname() + "&idcard="
										+ user.getIdNumber() + "&task_id=" + taskId + "&contact=" + user.getFirstContactPhone() + ":"
										+ user.getFirstContactName() + ":" + relation1 + "," + user.getSecondContactPhone() + ":"
										+ user.getSecondContactName() + ":" + relation2;
								String result = GzipUtil.gzipGet(url2, token);
//								logger.info("findMobileReport url2=" + url2 + ",return=" + result);
								if (StringUtils.isNotBlank(result)) {
									HashMap<String, Object> map1 = new HashMap<String, Object>();
									map1.put("userId", userId);
									map1.put("detail", result);
									map1.put("firstContactPhone", user.getFirstContactPhone());
									map1.put("secondContactPhone", user.getSecondContactPhone());
									RiskCreditUser riskCreditUser3 = RiskCreditUserUtil.getInstance().createMx(map1);
									if (riskCreditUser3 != null) {
										riskCreditUserService.updateJxl(riskCreditUser3);
										jedisCluster.del(key);
									} else {
										log.info("updateJxl error riskCreditUser3=:{}",riskCreditUser3);
									}
								}
							}
						});
					} catch (Exception e) {
						jedisCluster.del(key2);
						log.error("findMobileReport create report  error key=:{}error::{}",key, e);
					} finally {
						jedisCluster.del(key2);
					}
				}
			}
		}
	}

	@Override
	public void updateFromRisk() {
//		String key2 = "TO_NEW_RISK";
//		if (jedisCluster.get(key2) != null) {
//			logger.error("last updateAnalysis is running");
//			jedisCluster.del(key2);
//		} else {
//			try {
//				jedisCluster.setex(key2, ConstantRisk.FLAG_SECOND * day, key2);
//				riskCreditUserService.updateFromRisk();
//			} catch (Exception e) {
//				logger.error("task updateFromRisk error ", e);
//			} finally {
//				jedisCluster.del(key2);
//			}
//		}
	}

	@Override
	public void updateAnalysis() {
		if(true){
			return;
		}
		Map<String, String> map = SysCacheUtils.getConfigMap(BackConfigParams.SYSTEM);
		int limit = Integer.valueOf(map.get("SYSTEM_NEW_RISK"));//推送给新模型风控的记录数
		String newRiskKey = "newRiskKey";
		log.error("updateAnalysis is running");
		String key2 = ConstantRisk.RISK_ANALYSIS;
		if (jedisCluster.get(key2) != null) {
			log.error("last updateAnalysis is running");
		} else {
			try {

				//获取老用户开关 1机审 0人工审核
				HashMap<String, Object> paramsback = new HashMap<String, Object>();
				paramsback.put("sysType", "SYS_NOCACHE");
				paramsback.put("sysKey", "OLDU_SWITCH");
				//params.put("sysName","");
				List<BackConfigParams> listback = backConfigParamsService.findParams(paramsback);
				String offon = "1";
				if (listback.size() == 1) {
					offon = listback.get(0).getSysValue();
				}
				//是老用户 true 人工 false 机器
				boolean oldswitch = "0".equals(offon);

				log.info("updateAnalysis oldu switch =:{}",offon);
				int sub = ConstantRisk.FLAG_SECOND * day;
				String riskValue = jedisCluster.get(newRiskKey);
				if (riskValue == null) {
					riskValue = "0";
				}
				int riskCount = Integer.valueOf(riskValue);
				jedisCluster.setex(key2, ConstantRisk.FLAG_SECOND * day, key2);
				HashMap<String, Object> params2 = new HashMap<String, Object>();
				Integer size = 5;
				params2.put(Constant.PAGE_SIZE, size);
				int totalPageNum = riskCreditUserDao.findWaitAnalysisCount(params2);
				log.info("updateAnalysis 中的数据总数:{}",totalPageNum);
				int total = 0;
				if (totalPageNum > 0) {
					if (totalPageNum % size > 0) {
						total = totalPageNum / size + 1;
					} else {
						total = totalPageNum / size;
					}
				}
				for (int i = 0; i < total; i++) {
					params2.put(Constant.CURRENT_PAGE, i);
					PageConfig<HashMap<String, Object>> pm = riskCreditUserService.findAnalysisPage(params2);
					List<HashMap<String, Object>> list = pm.getItems();
					if (list != null && list.size() > 0) {
						for (final HashMap<String, Object> params : list) {
							String value = params.get("assetId") + "";
							final String key = ConstantRisk.REVIEW_BORROW + value;
							riskCount += 1;

							// 控制一天发送新模型风控的个数
							//查看是否是老用户
							boolean finallikd = false;
							try {
								BorrowOrder borrowOrder = borrowOrderService.findOneBorrow(Integer.parseInt(value));
								//logger.info("updateAnalysis 中的borrowOrder中的数据"+borrowOrder.toString());
								if (borrowOrder != null) {
									finallikd = 1 == borrowOrder.getCustomerType();
								}
							} catch (NumberFormatException e) {
								log.error("updateAnalysis oldu switch cast=NumberFormatException::{}" ,e);
							}
							// true 老用户人工审核  false 机器审核
							boolean tempboo = finallikd && oldswitch;

							final boolean flag = (riskCount <= limit)&&!tempboo;
							if (flag) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(new Date());
								cal.set(Calendar.HOUR_OF_DAY, 23);
								cal.set(Calendar.MINUTE, 59);
								cal.set(Calendar.SECOND, 59);
								int tmp = Integer.valueOf((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000 + "");
								if (tmp > 0) {
									sub = tmp;
								}
								jedisCluster.setex(newRiskKey, sub, riskCount + "");
							}
							try {
								if (jedisCluster.get(key) != null) {
									log.info("others is reviews key=:{}",key);
								} else {
									jedisCluster.setex(key, ConstantRisk.FLAG_SECOND * day, value);
									ThreadPool2.getInstance().run(new Runnable() {
										@Override
										public void run() {
											// if
											// (riskCreditUserDao.findAnalysisHelp(value)
											// <= 0) {
											// logger.info("analysis redis is null but db had deal");
											// } else {
											try {
												log.info("执行到updateAll");
												//riskCreditUserService.updateAll(params, flag);
											} catch (Exception e) {
												jedisCluster.del(key);
												log.error("updateAnalysis params=:{}" ,params);
											} finally {
												jedisCluster.del(key);
											}
											// }
										}
									});
								}
							} catch (Exception e) {
								jedisCluster.del(key);
								log.error("updateAnalysis error params=:{},error:{}",params, e);
							}
						}
						list = null;
					} else {
						break;
					}
				}
			} catch (Exception e) {
				jedisCluster.del(key2);
				log.error("updateAnalysis error:{} ", e);
			} finally {
				jedisCluster.del(key2);
			}
		}
	}

	//计算额度
	@Override
	public void updateEd() {
		log.info("updateEd is running");
		String key2 = ConstantRisk.CAL_MONEY;
		if (jedisCluster.get(key2) != null) {
			log.error("last updateEd is running");
		} else {
			jedisCluster.setex(key2, ConstantRisk.FLAG_SECOND * day, key2);
			try {
				HashMap<String, Object> params2 = new HashMap<>();
				Integer size = 5;
				params2.put(Constant.PAGE_SIZE, size);//每页获取5条数据
				//获取已认证用户但未计算贷款额度的总数，new_flag=3（可以计算额度）
				//查找user_info表中 new_flag = 3（可以计算额度）的数据的总条数
				int totalPageNum = riskCreditUserDao.findCalMoneyCount(params2);
				log.info("new_flag = 3的数据多少条:{}",totalPageNum);
				int total = 0;
				if (totalPageNum > 0) {
					if (totalPageNum % size > 0) {
						total = totalPageNum / size + 1;
					} else {
						total = totalPageNum / size;
					}
				}
				for (int i = 0; i < total; i++) {
					//获取待计算额度的用户信息
					params2.put(Constant.CURRENT_PAGE, i); //当前页
					PageConfig<RiskCreditUser> pm = riskCreditUserService.findCalPage(params2);
					List<RiskCreditUser> list = pm.getItems();
					if (list != null && list.size() > 0) {
						for (final RiskCreditUser riskCreditUser : list) {
							String value = riskCreditUser.getUserId() + "";//用户ID
							final String key = ConstantRisk.USER_MONEY + value;//USER_MONEY_{userId}
							if (jedisCluster.get(key) != null) {
								log.info("others is updateEd key=:{}",key);
							} else {
								try {
									//存储在redis里面，形式：USER_MONEY_{userId} = {userId}
									jedisCluster.setex(key, ConstantRisk.FLAG_SECOND * day, value);
									//开启线程
									ThreadPool.getInstance().run(new Runnable() {
										@Override
										public void run() {
											// if
											// (riskCreditUserDao.findEdHelp(riskCreditUser)
											// <= 0) {
											// logger.info("ed redis is null but db had deal");
											// } else {
											try {
												//计算并更新用户的贷款额度
												log.info("计算并更新用户的贷款额度:{}",riskCreditUser.toString());
												//riskCreditUserService.updateBorrowMoney(riskCreditUser);
											} catch (Exception e) {
												jedisCluster.del(key);
												log.error("updateEd error riskCreditUser=:{}",riskCreditUser);
											} finally {
												jedisCluster.del(key);
											}
											// }
										}
									});
								} catch (Exception e) {
									jedisCluster.del(key);
									log.error("updateEd error riskCreditUser=:{}error:{}",riskCreditUser, e);
								}
							}

						}
						list = null;
					} else {
						break;
					}
				}
			} catch (Exception e) {
				jedisCluster.del(key2);
				log.error("updateEd error:{}", e);
			} finally {
				jedisCluster.del(key2);
			}
		}
	}
}
