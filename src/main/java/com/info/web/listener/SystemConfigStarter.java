package com.info.web.listener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;

import com.info.web.pojo.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.info.constant.Constant;
import com.info.web.service.BackConfigParamsService;
import com.info.web.service.IBackConfigParamsService;
import com.info.web.util.ConfigConstant;

/**
 * 容器配置
 * @author zed
 */
@Slf4j
public class SystemConfigStarter implements Starter {

	private IBackConfigParamsService backConfigParamsService;

	@Override
	public void init(ServletContext ctx) {
		StopWatch stopWatch = new StopWatch("start servletContext");
		stopWatch.start();
		// 初始化spring bean
		initBeans(ctx);
		String key = null;
		LinkedHashMap<String, String> map = null;
		List<BackConfigParams> paramsList = null;
		List<BackConfigParams> list = backConfigParamsService.findParams(null);
		for (int i = 0; i < list.size(); i++) {
			BackConfigParams cfg = list.get(i);
			if (!cfg.getSysType().equals(key)) {
				if (key != null) {
					ctx.setAttribute(key, map);
					ctx.setAttribute(key + Constant.SYS_CONFIG_LIST, paramsList);
				}
				map = new LinkedHashMap<>();
				paramsList = new ArrayList<>();
				key = cfg.getSysType();
				ctx.setAttribute(key, key);
				ctx.setAttribute(key + Constant.SYS_CONFIG_LIST, paramsList);
			}
			map.put(cfg.getSysKey(), cfg.getSysValueAuto());
			paramsList.add(cfg);
			if (i == list.size() - 1) {
				ctx.setAttribute(key, map);
				ctx.setAttribute(key + Constant.SYS_CONFIG_LIST, paramsList);
			}
		}
		ctx.setAttribute(Constant.DEFAULT_PWD, ConfigConstant.getConstant(Constant.DEFAULT_PWD));
		ctx.setAttribute(Constant.BACK_URL, ConfigConstant.getConstant(Constant.BACK_URL));
		// 用户状态
		ctx.setAttribute("BACKUSER_STATUS_USE", BackUser.STATUS_USE);
		ctx.setAttribute("BACKUSER_STATUS_DELETE", BackUser.STATUS_DELETE);
		ctx.setAttribute("BACKUSER_ALL_STATUS", BackUser.ALL_STATUS);
		ctx.setAttribute("ALL_NOTICE_STATUS", BackNotice.ALL_NOTICE_STATUS);
		ctx.setAttribute("ALL_MESSAGE", BackNotice.ALL_MESSAGE);
		ctx.setAttribute("ALL_EMAIL", BackNotice.ALL_EMAIL);
		ctx.setAttribute("ALL_PHONE", BackNotice.ALL_PHONE);
		ctx.setAttribute("NOTICE_TYPE", BackMessageCenter.NOTICE_TYPE);
		ctx.setAttribute("SMS_REGISTER", Constant.SMS_REGISTER);
		ctx.setAttribute("SMS_DEL_BANK", Constant.SMS_DEL_BANK);
		ctx.setAttribute("SMS_SET_PAY", Constant.SMS_SET_PAY);
		ctx.setAttribute("SMS_SET_PHONE_OLD", Constant.SMS_SET_PHONE_OLD);
		ctx.setAttribute("SMS_SET_PHONE_NEW", Constant.SMS_SET_PHONE_NEW);
		// 所有借款状态
		ctx.setAttribute("BORROW_STATUS_ALL", BorrowOrder.borrowStatusMap);
		// 放款账户
		ctx.setAttribute("LOAN_ACCOUNTMap", BorrowOrder.LOAN_ACCOUNTMap);

		// 还款类型
		ctx.setAttribute("ALL_REPAY_TYPE", RepaymentDetail.REPAY_TYPE);

		//还款渠道
		ctx.setAttribute("ALL_REPAY_CHANNEL",RepaymentDetail.REPAY_CHANNEL);

		//续期类型
		ctx.setAttribute("RENEW_KIND",RenewalRecord.RENEW_KIND);

		//退款类型
		ctx.setAttribute("RETURN_TYPE", RepaymentReturn.RETURN_TYPE);
		
		//续期状态
		ctx.setAttribute("RENEWAL_STATUS", RenewalRecord.RENEWAL_STATUS);
		
		// 还款详情状态
		ctx.setAttribute("REPAYMENT_STATUS", RepaymentChecking.REPAYMENT_STATUS);
		//退款状态
		ctx.setAttribute("BACK_REPAY_STATUS", RepaymentChecking.BACK_REPAY_STATUS);
		
		// 借款方式
		ctx.setAttribute("LOAN_METHED", BorrowOrder.loanMethed);

		ctx.setAttribute("STATUS_CSTG", BorrowOrder.STATUS_CSTG);
		ctx.setAttribute("STATUS_DCS", BorrowOrder.STATUS_DCS);
		// ctx.setAttribute("STATUS_JSJJ", BorrowOrder.STATUS_JSJJ);
		ctx.setAttribute("STATUS_CSBH", BorrowOrder.STATUS_CSBH);
		ctx.setAttribute("STATUS_FSBH", BorrowOrder.STATUS_FSBH);
		ctx.setAttribute("STATUS_FSTG", BorrowOrder.STATUS_FSTG);
		ctx.setAttribute("STATUS_FKBH", BorrowOrder.STATUS_FKBH);
		ctx.setAttribute("STATUS_FKZ", BorrowOrder.STATUS_FKZ);
		ctx.setAttribute("STATUS_FKSB", BorrowOrder.STATUS_FKSB);
		ctx.setAttribute("STATUS_HKZ", BorrowOrder.STATUS_HKZ);
		ctx.setAttribute("STATUS_BFHK", BorrowOrder.STATUS_BFHK);
		ctx.setAttribute("STATUS_YHK", BorrowOrder.STATUS_YHK);
		ctx.setAttribute("STATUS_YYQ", BorrowOrder.STATUS_YYQ);
		ctx.setAttribute("STATUS_YHZ", BorrowOrder.STATUS_YHZ);
		ctx.setAttribute("STATUS_KKZ", BorrowOrder.STATUS_KKZ);
		ctx.setAttribute("STATUS_KKSB", BorrowOrder.STATUS_KKSB);
		stopWatch.stop();
		log.info(stopWatch.prettyPrint());
	}

	/**
	 * 获取spring注入的bean对象
	 * @param ctx ctx
	 */
	private void initBeans(ServletContext ctx) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		backConfigParamsService = (BackConfigParamsService) springContext.getBean("backConfigParamsService");
	}
//	@Override
//	public void risk(ServletContext ctx) {
//		initBeans(ctx);
//		ctx.setAttribute("ALL_RISK_TYPE", RiskManageRule.ALL_TYPE);
//		ctx.setAttribute("ALL_RISK_STATUS", RiskManageRule.ALL_STATUS);
//		ctx.setAttribute("ALL_ATTENTION_TYPE", RiskManageRule.ALL_ATTENTION_TYPE);
//		List<RiskManageRule> riskManageRules = riskManageRuleService.findAll();
//		Map<String, RiskManageRule> allRiskMap = new HashMap<>();
//		Map<String, String> baseRiskMap = new HashMap<>();
//		for (RiskManageRule riskManageRule : riskManageRules) {
//			String key2 = ConstantRisk.RULE_PREFIX + riskManageRule.getId();
//			if (riskManageRule.getRuleType().intValue() == RiskManageRule.RULE_BASE.intValue()) {
//				baseRiskMap.put(key2, riskManageRule.getFormula());
//			}
////			if (riskManageRule.getRootType().intValue() == RiskManageRule.ROOT_TYPE_ROOT_ZR.intValue()) {
////				// zrList.add(riskManageRule);
////			}
//			allRiskMap.put(key2, riskManageRule);
//		}
//		ctx.setAttribute(ConstantRisk.BASE_RULE, baseRiskMap);
//		ctx.setAttribute(ConstantRisk.ALL_RULE, allRiskMap);
//		List<RiskRuleProperty> list = riskCreditUserService.findRuleProperty(null);
//		// 规则和属性对应集合
//		Map<String, String> ALL_RULE_PROPERTY = new HashMap<>();
//		if (list != null && list.size() > 0) {
//			for (RiskRuleProperty riskRuleProperty : list) {
//				ALL_RULE_PROPERTY.put(riskRuleProperty.getRule(), riskRuleProperty.getProperty());
//			}
//		}
//		ctx.setAttribute(ConstantRisk.BASE_RULE_PROPERTY, ALL_RULE_PROPERTY);
//		ctx.setAttribute("ALL_ROOT", RiskManageRule.ALL_ROOT);
//		ctx.setAttribute("JC_RESULT", ConstantRisk.JC_RESULT);
//	}
}
