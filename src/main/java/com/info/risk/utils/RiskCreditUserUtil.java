package com.info.risk.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import com.info.web.util.OrderNoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.info.risk.pojo.RiskCreditUser;
import com.info.risk.pojo.RiskManageRule;
import com.info.risk.pojo.RiskRuleCal;
import com.info.web.util.DateUtil;

/**
 * 
 * 类描述：组装征信相关对象 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-12-17 上午10:49:08 <br>
 * 
 *
 */
@Slf4j
public class RiskCreditUserUtil {
	//测试 num
	static int kk = 0;
	public static RiskCreditUserUtil riskCreditUserUtil;

	public static RiskCreditUserUtil getInstance() {
		if (riskCreditUserUtil == null) {
			riskCreditUserUtil = new RiskCreditUserUtil();
		}
		return riskCreditUserUtil;
	}

	/**
	 * 构造芝麻关注度对象
	 * 
	 */
	public RiskCreditUser createZm(Integer userId, String creditWatch) {
		RiskCreditUser riskCreditUser = new RiskCreditUser();
		try {
			JSONObject re = JSONObject.fromObject(creditWatch);
			Integer zmIndustyBlack = 2;
			Integer zmOver = 0;
			Integer zmNoPayOver = 0;
			if (re.getBoolean("isMatched")) {
				JSONArray jsonArray = re.getJSONArray("details");
				for (int j = 0; j < jsonArray.size(); j++) {
					JSONObject temp = jsonArray.getJSONObject(j);
					String type = temp.getString("type");
					if (type != null && ConstantRisk.ZM_OVER.contains(type)) {
						zmOver++;
					}
					if (ConstantRisk.ZM_OVER_NO_PAY.contains(type)) {
						zmNoPayOver++;
					}
				}
				zmIndustyBlack = 1;
			}
			riskCreditUser = new RiskCreditUser(userId, zmIndustyBlack, zmOver, zmNoPayOver);
		} catch (Exception e) {
			log.error("createZm error:{} ", e);
		}
		return riskCreditUser;
	}

	/**
	 * 组装聚信立详情
	 * 
	 */
	public RiskCreditUser createJxl(HashMap<String, Object> params) {
		RiskCreditUser riskCreditUser = null;
		Object userId = params.get("userId");
		try {
			Object detail = params.get("detail");
			Object firstContactPhone = params.get("firstContactPhone");
			Object secondContactPhone = params.get("secondContactPhone");
			if (userId != null && detail != null && firstContactPhone != null && secondContactPhone != null) {
				int jxlZjDkNum = 0;
				int jxlBjDkNum = 0;
				BigDecimal yjHf = BigDecimal.ZERO;
				int yjHfHelp = 0;
				int jxlAmthNum = 0;
				int jxlHtPhoneNum = 0;
				int jxlGjTs = 0;
				int link1Num = 0;
				int link2Num = 0;
				int jxlLink2Days = 0;
				int jxlLink1Days = 0;
				int jxlLink2Order = 0;
				int jxlLink1Order = 0;
				int jxlPhoneRegDays = -1;
				Date lastHf = null;
				int jxlTotal = 0;
				int jxlLxGjTs = 0;
				int jxlRealName = 2;// 运营商是否已实名
				int goAmNum = 0;// 去澳门次数
				int zjlxrBlack = 0;// 直接联系人黑名单个数
				int jjlxrBlack = 0;// 间接联系人黑名单个数
				int yqjjBlack = 0;// 引起间接黑名单个数
				int jxlSamePhoneCard = 1;// 聚信立手机号和身份证号是否匹配，默认是1匹配
				JSONObject jsonObject = JSONObject.fromObject(detail);
				if (jsonObject.containsKey("report_data")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date now = sdf.parse(sdf.format(new Date()));
					JSONObject jsonObject2 = jsonObject.getJSONObject("report_data");
					JSONObject user_info_check = jsonObject2.getJSONObject("user_info_check");
					if (user_info_check.containsKey("check_black_info")) {
						JSONObject jsonObject3 = user_info_check.getJSONObject("check_black_info");
						String obj1 = jsonObject3.getString("contacts_class1_blacklist_cnt");
						String obj2 = jsonObject3.getString("contacts_class2_blacklist_cnt");
						String obj3 = jsonObject3.getString("contacts_router_cnt");
						if (obj1 != null && !"null".equals(obj1)) {
							zjlxrBlack = Integer.valueOf(obj1 + "");
						}
						if (obj2 != null && !"null".equals(obj2)) {
							jjlxrBlack = Integer.valueOf(obj2 + "");
						}
						if (obj3 != null && !"null".equals(obj3)) {
							yqjjBlack = Integer.valueOf(obj3 + "");
						}
					}
					if (jsonObject2.containsKey("behavior_check")) {
						JSONArray jsonArray = jsonObject2.getJSONArray("behavior_check");
						if (jsonArray != null && jsonArray.size() > 0) {
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject jsonObject3 = jsonArray.getJSONObject(i);
								String checkPoint = jsonObject3.getString("check_point");
								String evidence = jsonObject3.getString("evidence");
								if ("contact_loan".equals(checkPoint)) {
									String flag = "次共";
									String flag2 = "主叫";
									String flag3 = "被叫";
									String flag4 = "联系列表";
									if (evidence.indexOf(flag4) >= 0) {
										String[] tmp = evidence.split(";");
										if (tmp != null && tmp.length > 0) {
											for (String detail2 : tmp) {
												while (detail2.indexOf(flag2) >= 0) {
													int index2 = detail2.indexOf(flag2);
													int index1 = detail2.indexOf(flag);
													jxlZjDkNum += Integer.valueOf(detail2.substring(index2 + flag2.length(), index1));
													detail2 = detail2.substring(index1 + 1);
													int index3 = detail2.indexOf(flag3);
													int index4 = detail2.indexOf(flag);
													jxlBjDkNum += Integer.valueOf(detail2.substring(index3 + flag3.length(), index4));
													detail2 = detail2.substring(index4 + 1);
												}
											}
										}
									}
								} else if ("conatct_macao".equals(checkPoint)) {
									String flag = "澳门地区号码通话";
									String flag2 = "次";
									int index = evidence.indexOf(flag);
									if (index >= 0) {
										int index2 = evidence.indexOf(flag2);
										jxlAmthNum += Integer.valueOf(evidence.substring(index + flag.length(), index2));
									}
								} else if ("contact_each_other".equals(checkPoint)) {
									String flag = "互通过电话的号码有";
									String flag2 = "个";
									int index = evidence.indexOf(flag);
									if (index >= 0) {
										int index2 = evidence.indexOf(flag2);
										jxlHtPhoneNum += Integer.valueOf(evidence.substring(index + flag.length(), index2));
									}

								} else if ("phone_silent".equals(checkPoint)) {

									String result = jsonObject3.getString("result");
									String flag0 = "天内有";
									if (result.indexOf(flag0) >= 0) {
										String[] array = result.split(flag0);
										if (array != null && array.length > 0) {
											String flag = "天";
											for (int j = 0; j < array.length; j++) {
												String tmp = array[j];
												if (j == 0) {
													jxlTotal += Integer.valueOf(tmp);
												} else {
													int index = tmp.indexOf(flag);
													if (index >= 0) {
														jxlGjTs += Integer.valueOf(tmp.substring(0, index));
													}
												}
											}
											String flag2 = "次: ";
											int index2 = evidence.indexOf(flag2);
											if (index2 >= 0) {
												evidence = evidence.substring(index2 + flag2.length());
												String[] array2 = evidence.split("/");
												for (int j = 0; j < array2.length; j++) {
													String tmp = array2[j];
													String flag3 = ", ";
													String flag4 = "天";
													int gj = 0;
													int index3 = tmp.indexOf(flag3);
													int index4 = tmp.indexOf(flag4);
													if (index3 >= 0 && index4 >= 0) {
														gj = Integer.valueOf(tmp.substring(flag3.length() + index3, index4));
														if (gj > jxlLxGjTs) {
															jxlLxGjTs = gj;
														}
													}
												}
											}
										}
									}

								}
							}
						}
					}
					if (jsonObject2.containsKey("cell_behavior")) {
						JSONArray jsonArray = jsonObject2.getJSONArray("cell_behavior");
						if (jsonArray != null && jsonArray.size() > 0) {
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject jsonObject3 = jsonArray.getJSONObject(i);
								JSONArray jsonArray2 = jsonObject3.getJSONArray("behavior");
								for (int j = 0; j < jsonArray2.size(); j++) {
									JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
									String amount = jsonObject4.getString("total_amount");
									if (!"-1".equals(amount)) {
										yjHfHelp += 1;
										yjHf = yjHf.add(new BigDecimal(amount));
									}
									Date tmp = null;
									if (jsonObject4.containsKey("cell_mth")) {
										tmp = sdf.parse(jsonObject4.getString("cell_mth") + "-01");
									}
									if (i == 0) {
										lastHf = tmp;
									} else {
										int num2 = DateUtil.daysBetween(lastHf, tmp);
										if (num2 < 0) {
											lastHf = tmp;
										}
									}
								}
							}
						}
					}
					if (jsonObject2.containsKey("collection_contact")) {
						JSONArray jsonArray = jsonObject2.getJSONArray("collection_contact");
						if (jsonArray != null && jsonArray.size() > 0) {
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject jsonObject3 = jsonArray.getJSONObject(i);
								JSONArray jsonArray2 = jsonObject3.getJSONArray("contact_details");
								if (jsonArray2 != null && jsonArray2.size() > 0) {
									for (int j = 0; j < jsonArray2.size(); j++) {
										JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
										int num = jsonObject4.getInt("call_cnt");
										String phone = jsonObject4.getString("phone_num");
										String transEnd = jsonObject4.getString("trans_end");
										int num2 = 0;
										if (!"".equals(transEnd) && !"null".equals(transEnd)) {
											Date end = sdf.parse(transEnd.substring(0, 10));
											num2 = DateUtil.daysBetween(end, now);
										}
										if (firstContactPhone.equals(phone)) {
											link1Num += num;
											jxlLink1Days += num2;
										} else if (secondContactPhone.equals(phone)) {
											link2Num += num;
											jxlLink2Days += num2;
										}
									}
								}
							}
						}
					}
					if (jsonObject2.containsKey("application_check")) {
						JSONArray jsonArray = jsonObject2.getJSONArray("application_check");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							JSONObject jsonObject4 = jsonObject3.getJSONObject("check_points");
							String appPoint = jsonObject3.getString("app_point");
							if ("contact".equals(appPoint)) {
								String phone = jsonObject4.getString("key_value");
								String value = jsonObject4.getString("check_mobile");
								String flag = "名第[";
								String flag2 = "]位";
								int index = value.indexOf(flag);
								int index2 = value.indexOf(flag2);
								if (index >= 0 && index2 >= 0) {
									int num = Integer.valueOf(value.substring(index + flag.length(), index2));
									if (firstContactPhone.equals(phone)) {
										jxlLink1Order += num;
									} else if (secondContactPhone.equals(phone)) {
										jxlLink2Order += num;
									}
								}
							} else if ("cell_phone".equals(appPoint)) {
								if (jsonObject4.containsKey("reg_time")) {
									String regTime = jsonObject4.getString("reg_time");
									if (StringUtils.isNotBlank(regTime)) {
										Date end = sdf.parse(regTime.substring(0, 10));
										jxlPhoneRegDays = DateUtil.daysBetween(end, now);
									}
								}
								if (jsonObject4.containsKey("reliability")) {
									if ("实名认证".equals(jsonObject4.getString("reliability"))) {
										jxlRealName = 1;
									}
								}
								// 姓名身份证号码任何一个匹配则是匹配，任何一个不匹配则是不匹配，其他情况是运营商未提供
								if (jsonObject4.containsKey("check_idcard") && jsonObject4.getString("check_idcard").indexOf("匹配成功") >= 0) {
									jxlSamePhoneCard = 1;
								} else if (jsonObject4.containsKey("check_name") && jsonObject4.getString("check_name").indexOf("匹配成功") >= 0) {
									jxlSamePhoneCard = 1;
								} else if ((jsonObject4.containsKey("check_name") && jsonObject4.getString("check_name").indexOf("匹配失败") >= 0)
										|| (jsonObject4.containsKey("check_idcard") && jsonObject4.getString("check_idcard").indexOf("匹配失败") >= 0)) {
									jxlSamePhoneCard = 2;
								} else {
									jxlSamePhoneCard = 3;
								}
							}
						}
					}
					if (jsonObject2.containsKey("trip_info")) {
						JSONArray jsonArray = jsonObject2.getJSONArray("trip_info");
						if (jsonArray != null && jsonArray.size() > 0) {
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject jsonObject3 = jsonArray.getJSONObject(i);
								if ("澳门".equals(jsonObject3.getString("trip_dest"))) {
									goAmNum += 1;
								}
							}
						}
					}
				}
				if (yjHfHelp != 0) {
					yjHf = yjHf.divide(new BigDecimal(yjHfHelp), 2, BigDecimal.ROUND_HALF_UP);
				}
				// 如果没有获取到入网时间
				if (jxlPhoneRegDays == -1) {
					// 如果有话费账单,以账单月1号作为入网时间
					if (lastHf != null) {
						jxlPhoneRegDays = DateUtil.daysBetween(lastHf, new Date());
					} else {
						// 没有账单，则入网时间为0
						jxlPhoneRegDays = 0;
					}
				}
				BigDecimal jxlGjBl = BigDecimal.ZERO;
				if (jxlTotal != 0) {
					jxlGjBl = new BigDecimal(jxlGjTs).divide(new BigDecimal(jxlTotal), 2, BigDecimal.ROUND_UP);
				}
				riskCreditUser = new RiskCreditUser(Integer.valueOf(userId + ""), jxlZjDkNum, jxlBjDkNum, yjHf, jxlLink2Days, jxlLink1Days, link2Num,
						link1Num, jxlLink2Order, jxlLink1Order, jxlGjTs, jxlHtPhoneNum, jxlAmthNum, jxlPhoneRegDays, detail + "", jxlTotal, jxlGjBl,
						jxlLxGjTs, jxlRealName, goAmNum, zjlxrBlack, jjlxrBlack, yqjjBlack, jxlSamePhoneCard);
			}
		} catch (Exception e) {
			log.error("createJxl error userId=:{}error:{}",userId, e);
		}
		return riskCreditUser;
	}

	/**
	 * 组装魔蝎详情
	 * 
	 */
	public RiskCreditUser createMx(HashMap<String, Object> params) {
		RiskCreditUser riskCreditUser = null;
		Object userId = params.get("userId");
		try {
			Object detail = params.get("detail");
			Object firstContactPhone = params.get("firstContactPhone");
			Object secondContactPhone = params.get("secondContactPhone");
			if (userId != null && detail != null && firstContactPhone != null && secondContactPhone != null) {
				int jxlZjDkNum = 0;
				int jxlBjDkNum = 0;
				BigDecimal yjHf = BigDecimal.ZERO;
				int yjHfHelp = 0;
				int jxlAmthNum = 0;
				int jxlHtPhoneNum = 0;
				int jxlGjTs = 0;
				int link1Num = 0;
				int link2Num = 0;
				int jxlLink2Days = 0;
				int jxlLink1Days = 0;
				int jxlLink2Order = 0;
				int jxlLink1Order = 0;
				int jxlPhoneRegDays = -1;
				Date lastHf = null;
				int jxlTotal = 0;
				int jxlLxGjTs = 0;
				int jxlRealName = 2;// 运营商是否已实名
				int goAmNum = 0;// 去澳门次数
				int zjlxrBlack = 0;// 直接联系人黑名单个数
				int jjlxrBlack = 0;// 间接联系人黑名单个数
				int yqjjBlack = 0;// 引起间接黑名单个数
				int jxlSamePhoneCard = 1;// 聚信立手机号和身份证号是否匹配，默认是1匹配
				JSONObject jsonObject = JSONObject.fromObject(detail);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date now = sdf.parse(sdf.format(new Date()));
				// JSONObject jsonObject2 = jsonObject.getJSONObject("report");
				JSONObject user_info_check = jsonObject.getJSONObject("user_info_check");
				if (user_info_check.containsKey("check_black_info")) {
					JSONObject jsonObject3 = user_info_check.getJSONObject("check_black_info");
					if (!jsonObject3.isNullObject()) {
						String obj1 = jsonObject3.getString("contacts_class1_blacklist_cnt");
						String obj2 = jsonObject3.getString("contacts_class2_blacklist_cnt");
						String obj3 = jsonObject3.getString("contacts_router_cnt");
						if (obj1 != null && !"null".equals(obj1)) {
							zjlxrBlack = Integer.valueOf(obj1 + "");
						}
						if (obj2 != null && !"null".equals(obj2)) {
							jjlxrBlack = Integer.valueOf(obj2 + "");
						}
						if (obj3 != null && !"null".equals(obj3)) {
							yqjjBlack = Integer.valueOf(obj3 + "");
						}
					}
				}
				if (jsonObject.containsKey("behavior_check")) {
					JSONArray jsonArray = jsonObject.getJSONArray("behavior_check");
					if (jsonArray != null && jsonArray.size() > 0) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							String checkPoint = jsonObject3.getString("check_point");
							String evidence = jsonObject3.getString("evidence");
							if ("contact_loan".equals(checkPoint)) {
								String flag = "次共";
								String flag2 = "主叫";
								String flag3 = "被叫";
								String flag4 = "联系列表";
								if (evidence.indexOf(flag4) >= 0) {
									String[] tmp = evidence.split(";");
									if (tmp != null && tmp.length > 0) {
										for (String detail2 : tmp) {
											while (detail2.indexOf(flag2) >= 0) {
												int index2 = detail2.indexOf(flag2);
												int index1 = detail2.indexOf(flag);
												jxlZjDkNum += Integer.valueOf(detail2.substring(index2 + flag2.length(), index1));
												detail2 = detail2.substring(index1 + 1);
												int index3 = detail2.indexOf(flag3);
												int index4 = detail2.indexOf(flag);
												jxlBjDkNum += Integer.valueOf(detail2.substring(index3 + flag3.length(), index4));
												detail2 = detail2.substring(index4 + 1);
											}
										}
									}
								}
							} else if ("conatct_macao".equals(checkPoint)) {
								String flag = "澳门地区号码通话";
								String flag2 = "次";
								int index = evidence.indexOf(flag);
								if (index >= 0) {
									int index2 = evidence.indexOf(flag2);
									jxlAmthNum += Integer.valueOf(evidence.substring(index + flag.length(), index2));
								}
							} else if ("contact_each_other".equals(checkPoint)) {
								String flag = "互通过电话的号码有";
								String flag2 = "个";
								int index = evidence.indexOf(flag);
								if (index >= 0) {
									int index2 = evidence.indexOf(flag2);
									jxlHtPhoneNum += Integer.valueOf(evidence.substring(index + flag.length(), index2));
								}

							} else if ("phone_silent".equals(checkPoint)) {

								String result = jsonObject3.getString("result");
								String flag0 = "天内有";
								if (result.indexOf(flag0) >= 0) {
									String[] array = result.split(flag0);
									if (array != null && array.length > 0) {
										String flag = "天";
										for (int j = 0; j < array.length; j++) {
											String tmp = array[j];
											if (j == 0) {
												jxlTotal += Integer.valueOf(tmp);
											} else {
												int index = tmp.indexOf(flag);
												if (index >= 0) {
													jxlGjTs += Integer.valueOf(tmp.substring(0, index));
												}
											}
										}
										String flag2 = "次: ";
										int index2 = evidence.indexOf(flag2);
										if (index2 >= 0) {
											evidence = evidence.substring(index2 + flag2.length());
											String[] array2 = evidence.split("/");
											for (int j = 0; j < array2.length; j++) {
												String tmp = array2[j];
												String flag3 = ", ";
												String flag4 = "天";
												int gj = 0;
												int index3 = tmp.indexOf(flag3);
												int index4 = tmp.indexOf(flag4);
												if (index3 >= 0 && index4 >= 0) {
													gj = Integer.valueOf(tmp.substring(flag3.length() + index3, index4));
													if (gj > jxlLxGjTs) {
														jxlLxGjTs = gj;
													}
												}
											}
										}
									}
								}

							}
						}
					}
				}
				if (jsonObject.containsKey("cell_behavior")) {
					JSONArray jsonArray = jsonObject.getJSONArray("cell_behavior");
					if (jsonArray != null && jsonArray.size() > 0) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							JSONArray jsonArray2 = jsonObject3.getJSONArray("behavior");
							for (int j = 0; j < jsonArray2.size(); j++) {
								JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
								String amount = jsonObject4.getString("total_amount");
								if (!"-1".equals(amount)) {
									yjHfHelp += 1;
									yjHf = yjHf.add(new BigDecimal(amount));
								}
								Date tmp = null;
								if (jsonObject4.containsKey("cell_mth")) {
									tmp = sdf.parse(jsonObject4.getString("cell_mth") + "-01");
								}
								if (i == 0) {
									lastHf = tmp;
								} else {
									int num2 = DateUtil.daysBetween(lastHf, tmp);
									if (num2 < 0) {
										lastHf = tmp;
									}
								}
							}
						}
					}
				}
				if (jsonObject.containsKey("collection_contact")) {
					JSONArray jsonArray = jsonObject.getJSONArray("collection_contact");
					if (jsonArray != null && jsonArray.size() > 0) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							JSONArray jsonArray2 = jsonObject3.getJSONArray("contact_details");
							if (jsonArray2 != null && jsonArray2.size() > 0) {
								for (int j = 0; j < jsonArray2.size(); j++) {
									JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
									int num = jsonObject4.getInt("call_cnt");
									String phone = jsonObject4.getString("phone_num");
									String transEnd = jsonObject4.getString("trans_end");
									int num2 = 0;
									if (!"".equals(transEnd) && !"null".equals(transEnd)) {
										Date end = sdf.parse(transEnd.substring(0, 10));
										num2 = DateUtil.daysBetween(end, now);
									}
									if (firstContactPhone.equals(phone)) {
										link1Num += num;
										jxlLink1Days += num2;
									} else if (secondContactPhone.equals(phone)) {
										link2Num += num;
										jxlLink2Days += num2;
									}
								}
							}
						}
					}
				}
				if (jsonObject.containsKey("application_check")) {
					JSONArray jsonArray = jsonObject.getJSONArray("application_check");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						JSONObject jsonObject4 = jsonObject3.getJSONObject("check_points");
						String appPoint = jsonObject3.getString("app_point");
						if ("contact".equals(appPoint)) {
							String phone = jsonObject4.getString("key_value");
							String value = jsonObject4.getString("check_mobile");
							String flag = "名第[";
							String flag2 = "]位";
							int index = value.indexOf(flag);
							int index2 = value.indexOf(flag2);
							if (index >= 0 && index2 >= 0) {
								int num = Integer.valueOf(value.substring(index + flag.length(), index2));
								if (firstContactPhone.equals(phone)) {
									jxlLink1Order += num;
								} else if (secondContactPhone.equals(phone)) {
									jxlLink2Order += num;
								}
							}
						} else if ("cell_phone".equals(appPoint)) {
							if (jsonObject4.containsKey("reg_time")) {
								String regTime = jsonObject4.getString("reg_time");
								if (StringUtils.isNotBlank(regTime) && !"".equals(regTime) && !"null".equals(regTime) && !"未提供该数据".equals(regTime)) {
									Date end = sdf.parse(regTime.substring(0, 10));
									jxlPhoneRegDays = DateUtil.daysBetween(end, now);
								}
							}
							if (jsonObject4.containsKey("reliability")) {
								if ("实名认证".equals(jsonObject4.getString("reliability"))) {
									jxlRealName = 1;
								}
							}
							// 姓名身份证号码任何一个匹配则是匹配，任何一个不匹配则是不匹配，其他情况是运营商未提供
							if (jsonObject4.containsKey("check_idcard") && jsonObject4.getString("check_idcard").indexOf("匹配成功") >= 0) {
								jxlSamePhoneCard = 1;
							} else if (jsonObject4.containsKey("check_name") && jsonObject4.getString("check_name").indexOf("匹配成功") >= 0) {
								jxlSamePhoneCard = 1;
							} else if ((jsonObject4.containsKey("check_name") && jsonObject4.getString("check_name").indexOf("匹配失败") >= 0)
									|| (jsonObject4.containsKey("check_idcard") && jsonObject4.getString("check_idcard").indexOf("匹配失败") >= 0)) {
								jxlSamePhoneCard = 2;
							} else {
								jxlSamePhoneCard = 3;
							}
						}
					}
				}
				if (jsonObject.containsKey("trip_info")) {
					JSONArray jsonArray = jsonObject.getJSONArray("trip_info");
					if (jsonArray != null && jsonArray.size() > 0) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							if ("澳门".equals(jsonObject3.getString("trip_dest"))) {
								goAmNum += 1;
							}
						}
					}
				}
				if (yjHfHelp != 0) {
					yjHf = yjHf.divide(new BigDecimal(yjHfHelp), 2, BigDecimal.ROUND_HALF_UP);
				}
				// 如果没有获取到入网时间
				if (jxlPhoneRegDays == -1) {
					// 如果有话费账单,以账单月1号作为入网时间
					if (lastHf != null) {
						jxlPhoneRegDays = DateUtil.daysBetween(lastHf, new Date());
					} else {
						// 没有账单，则入网时间为0
						jxlPhoneRegDays = 0;
					}
				}
				BigDecimal jxlGjBl = BigDecimal.ZERO;
				if (jxlTotal != 0) {
					jxlGjBl = new BigDecimal(jxlGjTs).divide(new BigDecimal(jxlTotal), 2, BigDecimal.ROUND_UP);
				}
				riskCreditUser = new RiskCreditUser(Integer.valueOf(userId + ""), jxlZjDkNum, jxlBjDkNum, yjHf, jxlLink2Days, jxlLink1Days, link2Num,
						link1Num, jxlLink2Order, jxlLink1Order, jxlGjTs, jxlHtPhoneNum, jxlAmthNum, jxlPhoneRegDays, detail + "", jxlTotal, jxlGjBl,
						jxlLxGjTs, jxlRealName, goAmNum, zjlxrBlack, jjlxrBlack, yqjjBlack, jxlSamePhoneCard);
			}
		} catch (Exception e) {
			log.error("createJxl error userId=:{}error:{}",userId, e);
		}
		return riskCreditUser;
	}

//	public static void main(String[] args) {
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("userId", "7");
//		params.put("detail", "");
//		params.put("firstContactPhone", "15139002006");
//		params.put("secondContactPhone", "13353779955");
//		RiskCreditUserUtil.getInstance().createMx(params);
//	}


	/**
	 * 组装白骑士相关信息
	 * 
	 * @param id
	 *            征信表主键ID
	 * @param msg
	 *            白骑士返回的审核意见
	 */
	public RiskCreditUser createBqs(Integer id, String msg) {
		RiskCreditUser riskCreditUser = null;
		try {
			Integer bqsBlack = 3;
			JSONObject jsonObject = JSONObject.fromObject(msg);
			String result = jsonObject.getString("finalDecision");
			if ("Accept".equals(result)) {
				bqsBlack = 1;
			} else if ("Reject".equals(result)) {
				bqsBlack = 2;
			} else {
				// 建议人工审核
				bqsBlack = 3;
			}
			riskCreditUser = new RiskCreditUser(id, bqsBlack);
		} catch (Exception e) {
			log.error("createBqs error id=:{} error:{}",id , e);
		}
		return riskCreditUser;
	}

	/**
	 * 组装中智诚相关信息
	 * 
	 * @param id
	 *            征信表主键ID
	 * @param msg
	 *            中智诚返回的接口信息
	 */
	public RiskCreditUser createZzc(Integer id, String msg) {
		RiskCreditUser riskCreditUser = null;
		try {
			riskCreditUser = new RiskCreditUser();
			JSONObject jsonObject = JSONObject.fromObject(msg);
			JSONObject rule_result = jsonObject.getJSONObject("rule_result");
			String risk_level = rule_result.getString("risk_level");
			Integer zzcFqz = 3;
			if ("high".equals(risk_level)) {
				zzcFqz = 1;
			} else if ("medium".equals(risk_level)) {
				zzcFqz = 2;
			}
			//JSONObject blacklist = jsonObject.getJSONObject("blacklist");
			//Integer zzcBlackNum = blacklist.getInt("count");
			Integer zzcBlackNum = jsonObject.getInt("count");
			Integer zzcBlack = 2;
			if (zzcBlackNum > 0) {
				zzcBlack = 1;
			}
			riskCreditUser.setId(id);
			riskCreditUser.setZzcBlackNum(zzcBlackNum);
			riskCreditUser.setZzcBlack(zzcBlack);
			riskCreditUser.setZzcFqz(zzcFqz);
			//todo:此时要设置 zzcId
			riskCreditUser.setZzcFqzId(OrderNoUtil.getInstance().getUUID());
		} catch (Exception e) {
			log.error("createZzc error id=:{}error:{}",id, e);
		}
		return riskCreditUser;
	}


	/**
	 * 组装91征信相关信息（跳过91征信这一块的信息）
	 *
	 * @param id
	 *            征信表主键
	 * @param msg
	 *            91返回的信息
	 */
	public RiskCreditUser createJyzx(Integer id, String msg) {
		RiskCreditUser riskCreditUser = null;
		try {
		//	Pkg2003 pkg2003 = (Pkg2003) JsonSerializer.deserializer(msg, new TypeReference<Pkg2003>() {});
			int jyLoanNum = 0;
			int jyJdNum = 0;
			int jyFkNum = 0;
			int jyHkNum = 0;
			BigDecimal jyJdBl = BigDecimal.ZERO;
			int jyOverNum = 0;
			BigDecimal jyOverBl = BigDecimal.ZERO;
			BigDecimal jyHkBl = new BigDecimal("1");
			/*List<LoanInfo> list = pkg2003.getLoanInfos();
			if (list != null && list.size() > 0) {
				jyLoanNum = list.size();
				for (LoanInfo loanInfo : list) {
					if (loanInfo.getBorrowState() == 1) {
						jyJdNum++;
					} else if (loanInfo.getBorrowState() == 2) {
						jyFkNum++;
					}
					short status = loanInfo.getRepayState();
					if ((status != 0 && status != 1 && status != 9) || loanInfo.getArrearsAmount() > 0) {
						jyOverNum++;
					}
					if (status == 1 || status == 9) {
						jyHkNum++;
					}
				}
				jyJdBl = new BigDecimal(jyJdNum).divide(new BigDecimal(jyLoanNum), 2, BigDecimal.ROUND_UP);
				jyOverBl = new BigDecimal(jyOverNum).divide(new BigDecimal(jyLoanNum), 2, BigDecimal.ROUND_UP);
				if (jyFkNum != 0) {
					jyHkBl = new BigDecimal(jyHkNum).divide(new BigDecimal(jyFkNum), 2, BigDecimal.ROUND_UP);
				}
			}*/
			riskCreditUser = new RiskCreditUser(id, jyLoanNum, jyJdNum, jyJdBl, jyOverNum, jyOverBl, jyFkNum, jyHkNum, jyHkBl);
		} catch (Exception e) {
			log.error("createJyzx error id=:{}error:{}" ,id, e);
		}
		return riskCreditUser;
	}


	/**
	 * 根据父节点找到所有引用的子节点
	 * 
	 * @param list
	 *            要查询子节点的树
	 * @param map
	 *            所有规则map集合
	 */
	public void findSon(List<RiskManageRule> list, Map<String, RiskManageRule> map, String parentId) {
		for (RiskManageRule rule : list) {
			String id = ConstantRisk.RULE_PREFIX + rule.getId();
			if (StringUtils.isBlank(parentId)) {
				parentId = id;
			}
			if (rule.getRuleType().intValue() == RiskManageRule.RULE_BASE.intValue()) {
				rule.setParent(parentId);
			} else if (rule.getRuleType().intValue() == RiskManageRule.RULE_EXTEND.intValue()) {
				String[] array = rule.getFormula().replaceAll(" ", "").split(ConstantRisk.REGEX);
				if (array != null && array.length > 0) {
					Set<String> set = new HashSet<String>(Arrays.asList(array));
					List<RiskManageRule> child = new ArrayList<RiskManageRule>();
					List<String> childIds = new ArrayList<String>();
					for (String key : set) {
						if (!"".equals(key)) {
							key = key.trim();
							RiskManageRule rule2 = map.get(key);
							if (rule2 != null) {
								String id2 = ConstantRisk.RULE_PREFIX + rule2.getId();
								//logger.info("findSon  key的值"+key+"  rule2"+rule2.toString());
								if (childIds.contains(id2)) {
									continue;
								}
								childIds.add(id2);
								child.add(rule2);
//								logger.info("childIds "+childIds.toString() +" child"+child.toString());
							}
						}

					}
					if (child != null && child.size() > 0) {
						rule.setParent(parentId);
						rule.setChild(child);
						findSon(child, map, id);
					}
				}
			}
		}
	}

	public void calSon2Parent(List<RiskManageRule> list, RiskManageRule parentRule, Map<String, String> map, final Integer creditId,
			final Integer assetId, final Integer userId, List<RiskRuleCal> calList) {
		log.info("calSon2Parent start");
//		logger.info("calSon2Parent 次数"+kk+"  "+list.toString());
//		logger.info("calSon2Parent 次数"+kk+"  "+"parentRule"+parentRule.toString());
//		logger.info("calSon2Parent 次数"+kk+"  "+"map"+map.toString());
//		logger.info("calSon2Parent 次数"+kk+"  "+"creditId"+creditId+" assetId"+assetId+" userId"+userId+" calList"+calList);
		for (int i = 0; i < list.size(); i++) {
			RiskManageRule riskManageRule = list.get(i);
			String id = ConstantRisk.RULE_PREFIX + riskManageRule.getId();

			String parentId = ConstantRisk.RULE_PREFIX + parentRule.getId();
			String parentFormula = parentRule.getFormula();
			String value = "";
//			logger.info("calSon2Parent 次数"+kk+"  "+"id"+id+" parentId"+parentId+" parentFormula"+parentFormula +"riskManageRule.getRuleType().intValue() "+riskManageRule.getRuleType().intValue());
			String regex = "\\b" + id + "\\b";
			if (riskManageRule.getRuleType().intValue() == RiskManageRule.RULE_BASE.intValue()) {
				value = map.get(id);
//				logger.info("calSon2Parent 次数"+kk+"  "+"键值对："+id+"="+value);
				parentRule.setFormula(parentFormula.replaceAll(regex, value));
//				logger.info("calSon2Parent 次数"+kk+"  "+"parentFormula.replaceAll(regex, value)"+parentFormula.replaceAll(regex, value));
				final String fValue = new String(value);
				final String fDetail = new String("");
				final Integer fId = riskManageRule.getId();
				final String fShow = riskManageRule.getFormulaShow();
				final String fName = riskManageRule.getRuleName();
				final Integer fType = riskManageRule.getAttentionType();
//				logger.info("calSon2Parent 次数"+kk+"  "+"fValue"+fValue+" fDetail"+fDetail+" fId"+fId+" fShow"+fShow+" fName"+fName+" fType"+fType);
				calList.add(new RiskRuleCal(userId, fId, creditId, assetId, fShow, fValue, fDetail, fName, fType));
			} else {
//				logger.info("calSon2Parent 次数"+kk+"  "+"else测试开始");
				List<RiskManageRule> child = riskManageRule.getChild();
				if (child != null && child.size() > 0) {
					kk++;
					calSon2Parent(child, riskManageRule, map, creditId, assetId, userId, calList);
				}
				value = riskManageRule.getFormula();
//				logger.info("calSon2Parent 次数"+kk+"  "+"value的值"+value);
				if (id.equals(parentId)) {
					parentFormula = value;
				} else {
					parentFormula = parentFormula.replaceAll(regex, value);
				}
//				logger.info("calSon2Parent 次数"+kk+"  "+"parentFormula 1 "+parentFormula);
				parentRule.setFormula(parentFormula);
//				logger.info("calSon2Parent 次数"+kk+"  "+"else测试结束");
			}
			parentFormula = parentRule.getFormula();
//			logger.info("calSon2Parent 次数"+kk+"  "+"parentFormula 2 "+parentFormula);
			if (i == (list.size() - 1) && !id.equals(parentId)) {
				JexlContext ctxt = new MapContext();
				JexlEngine jexl = new JexlEngine();
				if (parentFormula.indexOf(ConstantRisk.RULE_PREFIX) >= 0) {
					throw new RuntimeException("变量未计算完毕parentFormula=" + parentFormula);
				}
				Expression expr = jexl.createExpression(parentFormula);
				value = expr.evaluate(ctxt) + "";
//				logger.info("calSon2Parent 次数"+kk+"  "+"expr.evaluate(ctxt)"+expr.evaluate(ctxt));
				//中文校验
				Matcher matcher = ConstantRisk.PATTERN_CN.matcher(value);
				if (matcher.find()) {
					value = "'" + value + "'";
//					logger.info("calSon2Parent 次数"+kk+"  "+"matcher"+value);
				}
				// 计算用户额度的时候出现'81.5>0'这样的信息报错
				// parentRule.setFormula("'" + value + "'");
				parentRule.setFormula(value);

				final String fValue = new String(value);
				final String fDetail = new String("");
				final Integer fId = parentRule.getId();
				final String fShow = parentRule.getFormulaShow();
				final String fName = parentRule.getRuleName();
				final Integer fType = parentRule.getAttentionType();
//				logger.info("calSon2Parent最后 次数"+kk+"  "+"fValue"+fValue+" fDetail"+fDetail+" fId"+fId+" fShow"+fShow+" fName"+fName+" fType"+fType);
				calList.add(new RiskRuleCal(userId, fId, creditId, assetId, fShow, fValue, fDetail, fName, fType));
			}

		}
		log.info("calSon2Parent end");
	}

	/**
	 * 构造宜信对象
	 * 
	 */
	public RiskCreditUser createYx(Integer userId, String msg) {
		RiskCreditUser riskCreditUser = new RiskCreditUser();
		try {
			Integer yxFxNum = 0;
			Integer yxMonth3Over = 0;
			Integer yxLessMonth3Over = 0;
			Integer total = 0;
			JSONObject jsonObject = JSONObject.fromObject(msg);
			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			if (jsonObject2.containsKey("riskResults")) {
				JSONArray jsonArray = jsonObject2.getJSONArray("riskResults");
				if (jsonArray != null && jsonArray.size() > 0) {
					yxFxNum = jsonArray.size();
				}
			}
			if (jsonObject2.containsKey("loanRecords")) {
				JSONArray jsonArray = jsonObject2.getJSONArray("loanRecords");
				if (jsonArray != null && jsonArray.size() > 0) {
					total = jsonArray.size();
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						String tmpTotal = jsonObject3.getString("overdueTotal");
						if (StringUtils.isNotBlank(tmpTotal)) {
							total += Integer.valueOf(tmpTotal);
							String tmpM3 = jsonObject3.getString("overdueM3");
							if (StringUtils.isNotBlank(tmpM3)) {
								yxMonth3Over += Integer.valueOf(tmpM3);
							}

						}
					}
				}
			}
			yxLessMonth3Over = total - yxMonth3Over;
			riskCreditUser = new RiskCreditUser(userId, yxFxNum, yxMonth3Over, yxLessMonth3Over, 2);
		} catch (Exception e) {
			log.error("createZm error:{} ", e);
		}
		return riskCreditUser;
	}
}
