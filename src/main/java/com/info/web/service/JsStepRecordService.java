package com.info.web.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.back.utils.ServiceResult;
import com.info.web.dao.IJsStepRecordDao;
import com.info.web.pojo.JsAwardRecord;
import com.info.web.pojo.JsDrawrollsRecord;
import com.info.web.pojo.JsLoanPerson;
import com.info.web.pojo.JsStepConfig;
import com.info.web.pojo.JsStepRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * 统计行为记录
 * 
 * @author Administrator
 * 
 */
@Slf4j
@Service
public class JsStepRecordService implements IJsStepRecordService {

	@Autowired
	private IJsStepRecordDao jsStepRecordDao;
	@Autowired
	private IJsLoanPersonService jsLoanPersonService;
	@Autowired
	private IJsAwardRecordService jsAwardRecordService;
	@Autowired
	private IJsDrawrollsRecordService jsDrawrollsRecordService;
	@Autowired
	private IJsStepConfigService jsStepConfigService;
	 

	//
	// public JsStepRecord getOne(String id){
	// return super.get(id);
	// }
	//
	// public List<JsStepRecord> findList(JsStepRecord jsStepRecord) {
	// return super.findList(jsStepRecord);
	// }
	//
	// public Page<JsStepRecord> findPage(Page<JsStepRecord> page, JsStepRecord
	// jsStepRecord) {
	// return super.findPage(page, jsStepRecord);
	// }
	//
	// public Page<JsStepRecord> findPageList(int pageNo,int pageSize,
	// JsStepRecord jsStepRecord) {
	// Page<JsStepRecord> page =new Page<JsStepRecord>();
	// page.setPageNo(pageNo);
	// page.setPageSize(pageSize);
	// jsStepRecord.setPage(page);
	// page.setList(jsStepRecordDao.findPageList(jsStepRecord));
	// if(page.getLast()<pageNo){
	// page.setList(null);
	// }
	// return page;
	// }
	//
	//
	@Override
	public void save(JsStepRecord jsStepRecord) {
		if (jsStepRecord.getId() == null) {

			jsStepRecordDao.insert(jsStepRecord);
		} else {

			jsStepRecordDao.update(jsStepRecord);
		}
	}

	//
	// @Transactional(readOnly = false)
	// public void delete(JsStepRecord jsStepRecord) {
	// super.delete(jsStepRecord);
	// }
	@Override
	public ServiceResult addUserStep(HashMap<String, String> mapParams) {

		ServiceResult serviceResult = new ServiceResult("500", "未知异常");

		try {

			log.info("request params===:{}",mapParams.toString());
			String userPhone = mapParams.get("userPhone"); // 用户手机号
			String userName = String.valueOf(mapParams.get("userName"));// 用户姓名
			// userName=new String(userName.getBytes("ISO8859-1"),"UTF-8");

			String userCardNo = mapParams.get("cardNum");// 身份证号
			String awardType = mapParams.get("awardType");// 行为类型
			String invitePhone = mapParams.get("invitePhone"); // 邀请人手机号

			if (userPhone == null || awardType == null) {
				serviceResult.setCode("500");
				serviceResult.setMsg("参数错误，请检查参数！");
				log.info("参数问题:{}",mapParams.toString());
				return serviceResult;
			}
			// 获取当期抽奖记录
			JsAwardRecord jsAwardRecord = getCurrentjsAwardRecordOrInsert();
			// 查询当前用户********************************************
			JsLoanPerson currentUser = getCurrentUser(userPhone, userName, userCardNo);

			// 获取行为
			JsStepConfig userStep = null;
			List<JsStepConfig> jsSteps = jsStepConfigService.findList(null);// 查询数据库

			for (JsStepConfig stepTemp : jsSteps) {
				if (awardType.equals(stepTemp.getStepCode()) && !stepTemp.getStepCode().equals(JsStepConfig.STEP_INVITE)) {
					userStep = stepTemp;
					break;
				}
			}
			if (userStep != null) {
				// 如果是认证成功，保存姓名、身份证号
				if (userStep.getStepCode().equals(JsStepConfig.STEP_APPROVE)) {
					if (StringUtils.isEmpty(currentUser.getName()) || StringUtils.isEmpty(currentUser.getIdNumber())) {
						if (userName == null || userCardNo == null) {
							serviceResult.setCode("500");
							serviceResult.setMsg("参数错误，请检查参数！认证通过userName、cardNum为必填项");
							log.info("认证通过参数问题：{}",mapParams.toString());

							return serviceResult;
						} else {
							JsLoanPerson userTwo = new JsLoanPerson();
							userTwo.setId(currentUser.getId());
							userTwo.setName(userName);
							userTwo.setIdNumber(userCardNo);
							userTwo.setUpdatedAt(new Date());
							jsLoanPersonService.save(userTwo);
						}

					}
				}
				// 添加行为**************************************
				JsStepRecord stepRecord = addStepRecord(currentUser, userStep);

				// 根据行为添加抽奖号码记录**********************
				if (userStep.getStatus() == 1 && userStep.getEffectiveCount() > 0) {
					// 添加抽奖记录
					addDrawrollsRecord(stepRecord, currentUser, jsAwardRecord);
					// 增加奖金池
					addAwardManey(stepRecord);
				}
				// 如果是认证成功，判断是否有邀请人
				if (userStep.getStepCode().equals(JsStepConfig.STEP_APPROVE)) {

					// 判断是否有邀请人
					if (invitePhone != null) {
						JsLoanPerson inviteuser = getCurrentUser(invitePhone, null, null);
						// if (inviteuser != null) {

						// 更新用户邀请信息
						JsLoanPerson currentUserUpdate = new JsLoanPerson();
						currentUserUpdate.setId(currentUser.getId());
						currentUserUpdate.setInviteCode(inviteuser.getPhone());
						currentUserUpdate.setUpdatedAt(new Date());
						jsLoanPersonService.save(currentUserUpdate);
						log.info("成功更新邀请用户信息！");
						JsStepConfig inviteUserStep = null;

						for (JsStepConfig stepTemp : jsSteps) {
							if (stepTemp.getStepCode().equals(JsStepConfig.STEP_INVITE)) {
								inviteUserStep = stepTemp;
								break;
							}
						}
						// 添加行为**************************************
						JsStepRecord stepRecordinvet = addStepRecord(inviteuser, inviteUserStep);
						// 根据行为添加抽奖号码记录
						if (inviteUserStep.getStatus().intValue() == 1 && stepRecordinvet.getEffectiveCount() > 0) {
							// 添加抽奖记录
							addDrawrollsRecord(stepRecordinvet, inviteuser, jsAwardRecord);
							// 增加奖金池
							addAwardManey(stepRecordinvet);
						}
					}

				}
				serviceResult = new ServiceResult(ServiceResult.SUCCESS, "成功");
			} else {
				serviceResult.setCode("500");
				serviceResult.setMsg("行为不存在：" + mapParams.toString());
			}
 

		} catch (Exception e) {
			serviceResult.setCode("500");
			serviceResult.setMsg("系统错误！");
			log.error("system error=={}:{}", mapParams.toString(),e);
 
		} finally {
			log.info("return===:{}",serviceResult.toString());
			return serviceResult;
		}

	}

	/**
	 * 获取当期抽奖记录
	 * 
	 */
	public JsAwardRecord getCurrentjsAwardRecord() {

		JsAwardRecord jsAwardRecord ;
		 
		HashMap<String,Object> paramsMap=new HashMap<>();
		paramsMap.put("status", 0);
		jsAwardRecord = jsAwardRecordService.findOne(paramsMap);

		return jsAwardRecord;
	}

	/**
	 * 增加奖金池
	 * 
	 */
	public synchronized void addAwardManey(JsStepRecord stepRecord) {

		BigDecimal maxAwardMoney = new BigDecimal(100000);// 最大奖金数
		// maxAwardMoney =new BigDecimal(DictUtils.getDictLabel("maxAwardMoney",
		// "awardMoney", ""));
		// maxAwardMoney =new BigDecimal(100000);
		// 获取当期抽奖记录
		JsAwardRecord jsAwardRecord = getCurrentjsAwardRecord();
		if (stepRecord.getEffectiveCount() > 0 && maxAwardMoney.subtract(jsAwardRecord.getAwardMoney()).compareTo(BigDecimal.ZERO) > 0) {
			JsAwardRecord jsAwardRecordNew = new JsAwardRecord();
			jsAwardRecordNew.setAwardMoney(jsAwardRecord.getAwardMoney().add(new BigDecimal(stepRecord.getEffectiveCount())));
			jsAwardRecordNew.setId(jsAwardRecord.getId());
			jsAwardRecordNew.setMoneyUpdateTime(new Date());
			// 更新到数据库jsAwardRecord
			jsAwardRecordService.save(jsAwardRecordNew);// 更新到数据库
			log.info(stepRecord.getRemark() + "增加奖金池，目前奖金：" + jsAwardRecordNew.getAwardMoney());
		}

	}

	/**
	 * 添抽奖记录
	 * 
	 */
	public synchronized void addDrawrollsRecord(JsStepRecord stepRecord, JsLoanPerson user, JsAwardRecord jsAwardRecord) {
		for (int i = 0; i < stepRecord.getEffectiveCount(); i++) {

			JsDrawrollsRecord drawrollsRecord = new JsDrawrollsRecord();
			drawrollsRecord.setStepId(stepRecord.getId());
			drawrollsRecord.setUserId(user.getId());
			drawrollsRecord.setAddTime(new Date());
			drawrollsRecord.setUpdateTime(new Date());
			drawrollsRecord.setPeriods(jsAwardRecord.getPeriods());
			drawrollsRecord.setLuckyDraw(createLucyDraw(jsAwardRecord));
			drawrollsRecord.setStatus(0);
			drawrollsRecord.setIsValid(1);
			jsDrawrollsRecordService.save(drawrollsRecord);
			log.info(user.getPhone() + "添加一条抽奖记录");

		}

	}

	/**
	 * 根据期数创建抽奖号码
	 * 
	 */
	public Integer createLucyDraw(JsAwardRecord jsAwardRecord) {
		Integer lucyDraw = JsDrawrollsRecord.startNumber + 1;
		JsDrawrollsRecord drawrollsLast = new JsDrawrollsRecord();
		drawrollsLast.setPeriods(jsAwardRecord.getPeriods());
		drawrollsLast = jsDrawrollsRecordService.findMaxOne(drawrollsLast);
		if (drawrollsLast != null && drawrollsLast.getLuckyDraw() > 0) {
			lucyDraw = drawrollsLast.getLuckyDraw() + 1;
		}

		return lucyDraw;
	}

	/**
	 * 获取当前用户
	 * 
	 */
	public JsLoanPerson getCurrentUser(String userPhone, String userName, String userCardNo) {

		// 查询用户是否存在，不存在添加
		JsLoanPerson user = new JsLoanPerson();
		user.setPhone(userPhone);
		// user=查询数据库
		user = jsLoanPersonService.findOne(user);
		if (user == null) {
			user = new JsLoanPerson();
			user.setPhone(userPhone);
			// user.setName(userName);
			// user.setIdNumber(userCardNo);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			// 更新到数据库user
			jsLoanPersonService.save(user);
			log.info("新增用户：{}",user);

		}

		return user;
	}

	/**
	 * 获取当期抽奖记录
	 * 
	 */
	public JsAwardRecord getCurrentjsAwardRecordOrInsert() {

		JsAwardRecord jsAwardRecord = new JsAwardRecord();
		jsAwardRecord.setStatus(0);
		
		HashMap<String,Object> paramsMap=new HashMap<>();
			paramsMap.put("status", 0);
		jsAwardRecord = jsAwardRecordService.findOne(paramsMap);

		if (jsAwardRecord == null || jsAwardRecord.getStatus().intValue() != JsAwardRecord.ZT_WKJ.intValue()) {
			BigDecimal minAwardMoney = new BigDecimal(500);// 默认最小金额奖金，需要修改成数据库配置
			// minAwardMoney =new
			// BigDecimal(DictUtils.getDictLabel("minAwardMoney", "awardMoney",
			// ""));
			minAwardMoney = new BigDecimal(0);
			jsAwardRecord = new JsAwardRecord();
			// 查询出最大的期数，如果没有从1开始。
			JsAwardRecord jsAwardRecordlast = null;
			 
			paramsMap.clear();
			jsAwardRecordlast = jsAwardRecordService.findOne(paramsMap);
			jsAwardRecord.setPeriods(jsAwardRecordlast != null ? jsAwardRecordlast.getPeriods() + 1 : 1);
			jsAwardRecord.setAwardMoney(minAwardMoney);
			jsAwardRecord.setMoneyUpdateTime(new Date());
			jsAwardRecord.setStatus(JsAwardRecord.ZT_WKJ);
			jsAwardRecordService.save(jsAwardRecord);// 更新到数据库
			log.info("添加中奖期数记录==========:{}" ,jsAwardRecord.getPeriods());
		}

		return jsAwardRecord;
	}

	/**
	 * 添加行为
	 * 
	 */
	public JsStepRecord addStepRecord(JsLoanPerson user, JsStepConfig userStep) {
		// 添加行为
		JsStepRecord stepRecord = new JsStepRecord();
		stepRecord.setUserId(user.getId());
		stepRecord.setStepId(userStep.getId());
		stepRecord.setRemark(userStep.getStepName());
		stepRecord.setAddTime(new Date());
		stepRecord.setUpdateTime(new Date());
		if (userStep.getStatus()== 1) {
			stepRecord.setEffectiveCount(userStep.getEffectiveCount());
		}
		 save(stepRecord);
		log.info(user.getPhone() + "添加行为记录成功：");
		return stepRecord;
	}
}
