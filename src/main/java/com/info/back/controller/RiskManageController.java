package com.info.back.controller;

import com.info.back.service.IBackModuleService;
import com.info.back.service.IBackUserService;
import com.info.back.service.ITaskJob;
import com.info.back.utils.*;
import com.info.risk.pojo.RiskManageRule;
import com.info.risk.pojo.RiskModelSwitch;
import com.info.risk.service.*;
import com.info.risk.utils.ConstantRisk;
import com.info.risk.utils.FormulaUtil;
import com.info.risk.utils.RuleChart;
import com.info.statistic.service.IOverdueService;
import com.info.statistic.service.IQuartzService;
import com.info.web.controller.BaseController;
import com.info.web.listener.IndexInit;
import com.info.web.pojo.BackConfigParams;
import com.info.web.pojo.BackLog;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.statistics.Overdue;
import com.info.web.service.IBackConfigParamsService;
import com.info.web.service.IBorrowOrderService;
import com.info.web.util.DateUtil;
import com.info.web.util.JSONUtil;
import com.info.web.util.encrypt.MD5coding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Controller
@RequestMapping("risk/")
public class RiskManageController extends BaseController {
	@Autowired
	private IRiskManageRuleService riskManageRuleService;
	@Autowired
	private IRiskModelOrderService riskModelOrderService;
	@Autowired
	private IOverdueService overdueService;
	@Autowired
	private IRiskModelSwitchService riskModelSwitchService;
	@Autowired
	private IQuartzService quartzService;
	@Autowired
	private IBackModuleService backModuleService;
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	JedisCluster jedisCluster;
	@Autowired
	private ITaskJob taskJob;
	@Autowired
	private IBorrowOrderService borrowOrderService;
	@Autowired
	private IBackConfigParamsService backConfigParamsService;

	// 查包
	@RequestMapping(value = "test")
	public void test(HttpServletRequest request) {
 		String nowDate=request.getParameter("nowDate");
 
 		borrowOrderService.assetpackqueryZCM(nowDate);
 
	}
//
//	@RequestMapping("test1")
//	// 推送
//	public void test1() {
//		// borrowOrderService.assetpackqueryZCM();
//		taskJob.assetsDivisionExt();
//	}

	@RequestMapping("test2")
	// 模拟请求推送合同
	public void test2() {
		LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");

//		Date nowDate = new Date();
//		String nowDateS = DateUtil.getDateFormat(nowDate, "yyyy-MM-dd");

//		HashMap<String, Object> paramsM = new HashMap<String, Object>();
//		paramsM.put("packetTime", nowDateS);
//		paramsM.put("fillupFlag", 0);
		String oid_plat = mapFee.get("oid_plat_ZCM");
		String business_password = mapFee.get("business_password_ZCM");

		String business_code = mapFee.get("business_code_ZCM");
		String business_license = mapFee.get("business_license_ZCM");

//		HashMap<String, Integer> params = new HashMap<String, Integer>();
//		params.put("soketOut", 5000);
//		params.put("connOut", 5000);
		String url = "http://180.153.194.208:9999/xjx/sendContractInfo";
		String timestamp = DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");

		Map<String, Object> objj = new HashMap<>();
		String sign = MD5coding.MD5(oid_plat + MD5coding.MD5(business_password) + MD5coding.MD5(business_code) + MD5coding.MD5(business_license)
				+ timestamp);
		objj.put("Version", "1.0");
		objj.put("Timestamp", timestamp);
		objj.put("Sign", sign);
		objj.put("Oid_plat", oid_plat);

		objj.put("Period", 7);
		objj.put("Package_code", "4");

		Map[] borrow_info = new Map[4];

		Map<String, Object> borrow_info1 = new HashMap<String, Object>();
		borrow_info1.put("Order_number", 1224);
		Map<String, Object> borrow_info2 = new HashMap<String, Object>();
		borrow_info2.put("Order_number", 3344);
		Map<String, Object> borrow_info3 = new HashMap<String, Object>();
		borrow_info3.put("Order_number", 112);
		Map<String, Object> borrow_info4 = new HashMap<String, Object>();
		borrow_info4.put("Order_number", 334);
		borrow_info[0] = borrow_info1;
		borrow_info[1] = borrow_info2;
		borrow_info[2] = borrow_info3;
		borrow_info[3] = borrow_info4;
		objj.put("Borrow_info", borrow_info);

		Map[] investor_info = new Map[2];

		Map<String, Object> investor_info1 = new HashMap<String, Object>();
		investor_info1.put("Money_amount", 200);
		investor_info1.put("Real_name", "张三");
		investor_info1.put("Id_number", "42280355112222");
		investor_info1.put("Investor", "2014110111");
		Map<String, Object> investor_info2 = new HashMap<String, Object>();
		investor_info2.put("Money_amount", 200);
		investor_info2.put("Real_name", "张三");
		investor_info2.put("Id_number", "42280355112222");
		investor_info2.put("Investor", "2014110111");

		investor_info[0] = investor_info1;
		investor_info[1] = investor_info2;

		objj.put("Investor_info", investor_info);

		String paramsJosn = JSONUtil.beanToJson(objj);

		String result = new WebClient().postJsonData(url, paramsJosn, null);
//		if (result == null) {
//
//		}
 

	}
	@RequestMapping("test3")
	//  
	public void test3() {
		
		LinkedHashMap<String, String> mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");
		String ISOPEN_ZCM_14=mapFee.get("LOANISOPEN_ZCM_14");
		System.out.println("ISOPEN_ZCM_14==="+ISOPEN_ZCM_14);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sysKey", "LOANISOPEN_ZCM_14");
		List<BackConfigParams> LOANISOPEN_ZCM_14s = backConfigParamsService.findParams(params);
	 
		BackConfigParams LOANISOPEN_ZCM_14 = LOANISOPEN_ZCM_14s.get(0);// 14天放款开关
		 
			LOANISOPEN_ZCM_14.setSysValue("0");
	 
		List<BackConfigParams> list = new ArrayList<>();
		list.add(LOANISOPEN_ZCM_14);
		 
	 
		backConfigParamsService.updateValue(list, null);
		// 刷新系统缓存
		new IndexInit().updateCache();
		
		mapFee = SysCacheUtils.getConfigParams("ASSEST_ZCM");
		ISOPEN_ZCM_14=mapFee.get("LOANISOPEN_ZCM_14");
		log.info("ISOPEN_ZCM_14===:{}",ISOPEN_ZCM_14);

	}
	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("findList")
	public String findList(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String errorMsg = null;
		try {
			model.addAttribute("params", params);
			model.addAttribute("pm", riskManageRuleService.findPage(params));
		} catch (Exception e) {
			errorMsg = "服务器异常。请稍后重试！";
			log.error("findList error:{}", e);
		}
		model.addAttribute(MESSAGE, errorMsg);
		return "risk/ruleList";
	}

	@RequestMapping("countByModel")
	public String countByModel(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		params.put("source", Overdue.MODEL);
		HashMap<String,String> sourceMap = new HashMap<>();
		if ("model".equals(params.get("bType"))) {
			//模型
			params.put("reviewFlag",Overdue.MODEL);
			List<RiskModelSwitch> modelSwitchList = riskModelSwitchService.findParams(new HashMap<>());
			if(CollectionUtils.isNotEmpty(modelSwitchList)){
				for (RiskModelSwitch modelSwitch : modelSwitchList) {
					String key = modelSwitch.getModelCode() + "-" + modelSwitch.getVariableVersion();
					sourceMap.put(key,"模型"+key);
				}
			}
		} else if ("person".equals(params.get("bType"))) {
			//人工
			params.put("reviewFlag",Overdue.PERSON);
			List<BackUser> users = backUserService.findAll(new HashMap<>());
			if(CollectionUtils.isNotEmpty(users)){
				for (BackUser user : users) {
					sourceMap.put(user.getId()+"",user.getUserName());
				}
			}
		}
		//特殊情况件数
		HashMap<String, Object> param = new HashMap<>();
		param.put("modelAdvice",2);
		param.put("modelCode",'0');
		int extraNum = riskModelOrderService.findParamsCount(param);
		model.addAttribute("extraNum", extraNum);
		model.addAttribute("sourceMap", sourceMap);
		model.addAttribute("params", params);
		String errorMsg = null;
		try {
			model.addAttribute("pm", overdueService.findPage(params));
		} catch (Exception e) {
			errorMsg = "服务器异常。请稍后重试！";
			log.error("countByModel error_:{}", e);
		}
		model.addAttribute(MESSAGE, errorMsg);
		return "risk/countByModel";
	}

	@RequestMapping("countByReview")
	public String countByReview(HttpServletRequest request, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		log.info("countByReview params:{}",params.toString());
		Object sDate = params.get("sDate");
        Object customerType = params.get("customerType");
        if (null != sDate && null != customerType) {
			model.addAttribute("params", params);
			String errorMsg = null;
			try {
				List<Overdue> overdueList = overdueService.analysisByReviewTime(params);
				//显示复审员姓名手机号
				log.info("countByReview 返回overdueList大小:{} ",overdueList.size());
				if (CollectionUtils.isNotEmpty(overdueList)) {
					HashMap<String, String> userMap = new HashMap<>();
					List<BackUser> users = backUserService.findAll(new HashMap<>());
					if (CollectionUtils.isNotEmpty(users)) {
						for (BackUser user : users) {
							userMap.put(user.getUserAccount(), user.getUserName() + " " + user.getUserMobile());
						}
						Iterator<Overdue> iter = overdueList.iterator();
						while (iter.hasNext()) {
							Overdue next = iter.next();
							String reviewUser = next.getReviewUser();
							if (reviewUser.startsWith("自动")||reviewUser.startsWith("机审")) {
								iter.remove();
							}
							if (userMap.containsKey(reviewUser)) {
								next.setReviewUser(userMap.get(reviewUser));
							}
						}
					}
				}
				model.addAttribute("list", overdueList);
			} catch (Exception e) {
				errorMsg = "服务器异常。请稍后重试！";
				log.error("countByReview error_:{}", e);
			}

			model.addAttribute(MESSAGE, errorMsg);
		}
		return "risk/countByReview";
	}
	@RequestMapping("perfect")
	public String perfect(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String sdate = (String) params.get("sdate");
		String edate = (String) params.get("edate");
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
					Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(edate);
					quartzService.perfectQuartz(sDate,eDate);
				} catch (Exception e) {
					log.error("perfect error_:{}", e);
				}
			}
		});
		model.addAttribute("msg", "perfect start");
		return "risk/riskPerfect";
	}
	@RequestMapping("perfectByChannel")
	public String perfectByChannel(HttpServletRequest request,Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String sdate = (String) params.get("sdate");
		String edate = (String) params.get("edate");
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
					Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(edate);
					quartzService.perfectQuartzByChannel(sDate,eDate);
				} catch (Exception e) {
					log.error("perfectQuartzByChannel error_:{}", e);
				}
			}
		});
		model.addAttribute("msg", "perfectQuartzByChannel start");
		return "risk/riskPerfect";
	}

	public ServiceResult checkCode(String formula, String self) {
		ServiceResult serviceResult = new ServiceResult("500", "变量校验出现异常");
		try {
			String[] array = formula.split(ConstantRisk.REGEX);
			Set<String> set = new HashSet<>(Arrays.asList(array));
			Map<String, RiskManageRule> allRule = SysCacheUtils.getAllRule();
			boolean flag = true;
			for (String code : set) {
				if (StringUtils.isNotBlank(code)) {
					code = code.replaceAll(" ", "");
					if (code.startsWith(ConstantRisk.RETURN_CHAR)) {
						if (!code.endsWith(ConstantRisk.RETURN_CHAR)) {
							serviceResult.setMsg(code + "未包含结束符号" + ConstantRisk.RETURN_CHAR);
							break;
						}
					} else if (code.endsWith(ConstantRisk.RETURN_CHAR)) {
						if (!code.startsWith(ConstantRisk.RETURN_CHAR)) {
							serviceResult.setMsg(code + "未包含开始符号" + ConstantRisk.RETURN_CHAR);
							break;
						}
					} else {
						if (!code.matches(ConstantRisk.REGEX_NUM)) {
							if (StringUtils.isNotBlank(self) && self.equals(code)) {
								flag = false;
								serviceResult.setMsg("表达式中不能包含当前所编辑的ID号" + code);
								break;
							} else {
								if (!allRule.containsKey(code)) {
									flag = false;
									serviceResult.setMsg("ID号" + code + "不存在");
									break;
								}
							}
						}
					}
				}
			}
			if (flag) {
				serviceResult = new ServiceResult(ServiceResult.SUCCESS, "校验通过");
			}
		} catch (Exception e) {
			log.error("checkCode:{}", e);
		}
		return serviceResult;
	}

	/**
	 * 添加规则
	 * 
	 * @param request req
	 * @param response res
	 * @param model model
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model, RiskManageRule riskManageRule) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = "";
		boolean flag = false;
		try {
			String type = params.get("type") + "";
			if ("toJsp".equals(type)) {
				url = "risk/saveUpdate";
			} else {
				boolean allow = true;
				ServiceResult serviceResult = null;
				riskManageRule.setFormulaShow(riskManageRule.getFormula());
				if (riskManageRule.getRuleType().intValue() == RiskManageRule.RULE_EXTEND) {
					FormulaUtil.checkFormula(riskManageRule.getFormula());
					serviceResult = checkCode(riskManageRule.getFormula(), null);
				}
				if (serviceResult != null && serviceResult.isFail()) {
					erroMsg = serviceResult.getMsg();
					allow = false;
				}
				if (allow) {
					riskManageRuleService.insert(riskManageRule);
					backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "insertRisk", riskManageRule.toString()));
					flag = true;
//					new IndexInit().updateRiskCache();
					String localhostBack = request.getServerName() + ":" + request.getServerPort();
					backModuleService.updateCacheOthers(localhostBack);// 刷新其它后台
				}
				SpringUtils.renderDwzResult(response, flag, erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId") + "");
			}
			params.put("url", "save");
			model.addAttribute("params", params);
		} catch (Exception e) {
			log.error("save error:{}", e);
			if (url == null) {
				String msg = e.getLocalizedMessage();
				String key = "FormulaUtil.calFormula@";
				int index = msg.indexOf(key);
				if (index >= 0) {
					erroMsg = "规则错误，请检查！";
				}
				SpringUtils.renderDwzResult(response, false, "操作失败" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId") + "");
			} else {
				erroMsg = "服务器异常，请稍后重试！";
			}
		} finally {
			model.addAttribute(MESSAGE, erroMsg);
		}
		return url;
	}

	/**
	 * 更新规则
	 * 
	 * @param request req
	 * @param response res
	 * @param model model
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Model model, RiskManageRule riskManageRule) {
		HashMap<String, Object> params = this.getParametersO(request);
		String url = null;
		String erroMsg = null;
		boolean flag = false;
		try {
			String type = params.get("type") + "";
			if ("toJsp".equals(type)) {
				Integer id = Integer.valueOf(params.get("id") + "");
				RiskManageRule riskManageRule2 = riskManageRuleService.findById(id);
				model.addAttribute("rule", riskManageRule2);
				url = "risk/saveUpdate";
			} else {
				boolean allow = true;
				ServiceResult serviceResult = null;
				riskManageRule.setFormulaShow(riskManageRule.getFormula());
				if (riskManageRule.getRuleType().intValue() == RiskManageRule.RULE_EXTEND) {
					FormulaUtil.checkFormula(riskManageRule.getFormula());
					serviceResult = checkCode(riskManageRule.getFormula(), ConstantRisk.RULE_PREFIX + riskManageRule.getId());
				}
				if (serviceResult != null && serviceResult.isFail()) {
					erroMsg = serviceResult.getMsg();
					allow = false;
				}
				if (allow) {
					riskManageRuleService.update(riskManageRule);
					backUserService.insertLog(new BackLog(loginAdminUser(request).getId(), "updateRisk", riskManageRule.toString()));
					flag = true;
//					new IndexInit().updateRiskCache();
					String localhostBack = request.getServerName() + ":" + request.getServerPort();
					backModuleService.updateCacheOthers(localhostBack);// 刷新其它后台
				}
				SpringUtils.renderDwzResult(response, flag, erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId") + "");
			}
			params.put("url", "update");
			model.addAttribute("params", params);
		} catch (Exception e) {
			if (url == null) {
				String msg = e.getLocalizedMessage();
				String key = "FormulaUtil.calFormula@";
				int index = msg.indexOf(key);
				if (index >= 0) {
					erroMsg = "规则错误，请检查！";
				}
				SpringUtils.renderDwzResult(response, false, "操作失败" + erroMsg, DwzResult.CALLBACK_CLOSECURRENT, params.get("parentId") + "");
			} else {
				erroMsg = "服务器异常，请稍后重试！";
			}
			log.error("update error:{}", e);
		} finally {
			model.addAttribute(MESSAGE, erroMsg);
		}
		return url;
	}

	/*** echart树开始 **/
	@RequestMapping(value = "/viewTree")
	public String viewTree(HttpServletRequest request, Model model) {
		String url = null;
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			String type = params.get("type") + "";
			if ("toJsp".equals(type)) {
				Integer id = Integer.valueOf(params.get("id") + "");
				RiskManageRule riskManageRule2 = riskManageRuleService.findById(id);
				model.addAttribute("rule", riskManageRule2);
				RuleChart ruleChart = new RuleChart(ConstantRisk.RULE_PREFIX + riskManageRule2.getId() + riskManageRule2.getRuleName(),
						riskManageRule2.getFormulaShow().replaceAll("\r\n", "<br>"));
				List<RiskManageRule> list = Collections.singletonList(riskManageRule2);
				List<RuleChart> list2 = Collections.singletonList(ruleChart);
				createTree(list, list2);
				String data = JSONUtil.beanListToJson(list2);
				// System.out.println(data);
				model.addAttribute("data", data);
				url = "risk/tree";
			}
		} catch (Exception e) {
			log.error("viewTree:{}", e);
		}
		return url;
	}

	public void createTree(List<RiskManageRule> list, List<RuleChart> list2) {
		Map<String, RiskManageRule> map = SysCacheUtils.getAllRule();
		for (int j = 0; j < list.size(); j++) {
			RiskManageRule rule = list.get(j);
			RuleChart ruleChart = list2.get(j);
			String id = ConstantRisk.RULE_PREFIX + rule.getId();
			if (rule.getRuleType().intValue() == RiskManageRule.RULE_BASE.intValue()) {

			} else if (rule.getRuleType().intValue() == RiskManageRule.RULE_EXTEND.intValue()) {
				String[] array = rule.getFormula().split(ConstantRisk.REGEX);
				List<RiskManageRule> child = new ArrayList<>();
				List<RuleChart> children = new ArrayList<>();
				List<String> hasCode = new ArrayList<>();
				for (int i = 0; i < array.length; i++) {
					if (StringUtils.isNotBlank(array[i])) {
						String unit = array[i].trim();
						if (hasCode.contains(unit)) {
							continue;
						}
						hasCode.add(unit);
						RiskManageRule rule2 = map.get(unit);
						if (rule2 != null) {
							RuleChart ruleChart2 = new RuleChart(ConstantRisk.RULE_PREFIX + rule2.getId() + rule2.getRuleName(), rule2
									.getFormulaShow().replaceAll("\r\n", "<br>"));
							child.add(rule2);
							children.add(ruleChart2);
						}
					}
				}
				if (child != null && child.size() > 0) {
					rule.setChild(child);
					ruleChart.setChildren(children);
					createTree(child, children);
				} else {
					log.info(id,"{}的表达式未解析到其他引用项");
				}
			}
		}
	}

	@RequestMapping(value = "/viewTree2")
	public String viewTree2(HttpServletRequest request, Model model) {
		String url = null;
		HashMap<String, Object> params = this.getParametersO(request);
		try {
			String type = params.get("type") + "";
			if ("toJsp".equals(type)) {
				Integer id = Integer.valueOf(params.get("id") + "");
				RiskManageRule riskManageRule2 = riskManageRuleService.findById(id);
				model.addAttribute("rule", riskManageRule2);
				String tree = "";
				List<RiskManageRule> list = Collections.singletonList(riskManageRule2);
				tree += createTree2(list);
				tree = tree.substring(4);
				tree = "<ul class='tree treeFolder expand'>" + tree;
				model.addAttribute("tree", tree);
				url = "risk/tree2";
			}
		} catch (Exception e) {
			log.error("viewTree:{}", e);
		}
		return url;
	}

	public String createTree2(List<RiskManageRule> list) {
		String tree = "<ul>";
		Map<String, RiskManageRule> map = SysCacheUtils.getAllRule();
		for (int j = 0; j < list.size(); j++) {
			RiskManageRule rule = list.get(j);
			String id = ConstantRisk.RULE_PREFIX + rule.getId();
			String title = rule.getFormulaShow().replaceAll("\r\n", "&#10;");
			if (rule.getRuleType().intValue() == RiskManageRule.RULE_BASE.intValue()) {
				tree += "<li><a title=\"默认值：" + title + "\">" + ConstantRisk.RULE_PREFIX + rule.getId() + "-" + rule.getRuleName() + "</a>";
			} else if (rule.getRuleType().intValue() == RiskManageRule.RULE_EXTEND.intValue()) {
				tree += "<li><a title=\"" + title + "\">" + ConstantRisk.RULE_PREFIX + rule.getId() + "-" + rule.getRuleName() + "</a>";
				String[] array = rule.getFormula().split(ConstantRisk.REGEX);
				List<RiskManageRule> child = new ArrayList<>();
				List<String> hasCode = new ArrayList<>();
				for (int i = 0; i < array.length; i++) {
					if (StringUtils.isNotBlank(array[i])) {
						String unit = array[i].trim();
						if (hasCode.contains(unit)) {
							continue;
						}
						hasCode.add(unit);
						RiskManageRule rule2 = map.get(unit);
						if (rule2 != null) {
							child.add(rule2);
						}
					}
				}
				if (child != null) {
					rule.setChild(child);
					tree += createTree2(child);
					tree += "</li>";
				} else {
					log.info(id, "{}的表达式未解析到其他引用项");
				}
			}
		}
		return tree + "</li></ul>";
	}

}
