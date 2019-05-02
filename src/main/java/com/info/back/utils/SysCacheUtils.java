package com.info.back.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.ContextLoader;

import com.info.constant.Constant;
import com.info.risk.pojo.RiskManageRule;
import com.info.risk.utils.ConstantRisk;
import com.info.web.pojo.BackConfigParams;

/**
 * 提供读取缓存的方法
 * 
 * @author Liutq
 * 
 */
@Slf4j
public class SysCacheUtils {

	private static ServletContext servletContext = null;

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getConfigParams(String type) {
		return (LinkedHashMap<String, String>) getServletContext()
				.getAttribute(type);
	}

	/**
	 * 获取系统参数
	 * 
	 * @return 2014-7-16 fx
	 */
	@SuppressWarnings("unchecked")
	public static List<BackConfigParams> getListConfigParams(String type) {
		type = type + Constant.SYS_CONFIG_LIST;
		return (List<BackConfigParams>) getServletContext().getAttribute(type);
	}

	/**
	 * 获得Map集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getConfigMap(String type) {
		return (Map<String, String>) getServletContext().getAttribute(type);
	}

	/**
	 * 获得基础规则Map集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBaseRule() {
		// 必须这么写，防止外部修改
		Map<String, String> map1 = (Map<String, String>) getServletContext()
				.getAttribute(ConstantRisk.BASE_RULE);
		return new HashMap<>(map1);
	}

	/**
	 * 获得所有规则集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, RiskManageRule> getAllRule() {
		Map<String, RiskManageRule> map2 = new HashMap<>();
		try {
			// 必须这么写，防止外部修改
			Map<String, RiskManageRule> map1 = (Map<String, RiskManageRule>) getServletContext()
					.getAttribute(ConstantRisk.ALL_RULE);
			for (String key : map1.keySet()) {
				RiskManageRule riskManageRule = new RiskManageRule();
				BeanUtils.copyProperties(riskManageRule, map1.get(key));
				map2.put(key, riskManageRule);
			}
		} catch (Exception e) {
			log.error("getAllRule error:{}",e);
		}
		return map2;
	}

	/**
	 * 弃用,存在引用传递问题9999999
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<RiskManageRule> getZrRule() {
		List<RiskManageRule> list2 = new ArrayList<>();
		try {
			List<RiskManageRule> list1 = (List<RiskManageRule>) getServletContext()
					.getAttribute(ConstantRisk.RULE_ZR);
			// 复制的时候当前节点基本类型没有问题，但是内部非基本类型，如child的拷贝仍然会被修改
			Map<String, RiskManageRule> map1 = (Map<String, RiskManageRule>) getServletContext()
					.getAttribute(ConstantRisk.ALL_RULE);
			for (RiskManageRule riskManageRule : list1) {
				RiskManageRule riskManageRule2 = new RiskManageRule();
				BeanUtils.copyProperties(riskManageRule2, riskManageRule);
				list2.add(riskManageRule2);
			}
		} catch (Exception e) {
			log.error("getZrRule error:{}",e);
		}
		return list2;
	}

	public static ServletContext getServletContext() {
		if (servletContext == null) {
			servletContext = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext();
		}
		return servletContext;
	}

}
