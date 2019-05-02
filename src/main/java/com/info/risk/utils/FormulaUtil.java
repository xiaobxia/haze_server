package com.info.risk.utils;


import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class FormulaUtil {

	/**
	 * 检查公式语法，把所有的唯一标识去掉，刊登否正常得出结果
	 * 
	 */
	public static Object checkFormula(String formula) {
		formula = formula.replaceAll(ConstantRisk.RULE_PREFIX, "");
		return calFormula(formula);
	}

	private static Object calFormula(String formula) {
		JexlContext ctxt = new MapContext();
		JexlEngine jexl = new JexlEngine();
		Expression expr = jexl.createExpression(formula);
		return expr.evaluate(ctxt);
	}
//
//	public static void main(String[] args) {
//		Calendar now = Calendar.getInstance();
//		now.add(Calendar.DAY_OF_YEAR, -1);
//		System.out.println(now.getTime());
//	}
}
