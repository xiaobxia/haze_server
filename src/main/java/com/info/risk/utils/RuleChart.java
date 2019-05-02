package com.info.risk.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class RuleChart {
	private String name;
	private String value;
	private Integer[] symbolSize;
	private String symbol = "";
	private List<RuleChart> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSymbolSize(Integer[] symbolSize) {
		this.symbolSize = symbolSize;
	}

	public Integer[] getSymbolSize() {
		return symbolSize;
	}

	public String getSymbol() {
		return symbol;
	}

	public List<RuleChart> getChildren() {
		return children;
	}

	public void setChildren(List<RuleChart> children) {
		this.children = children;
	}

	public RuleChart() {
		super();
	}

	public RuleChart(String name, String value) {
		super();
		if (StringUtils.isNotBlank(name)) {
			int length = name.length();
//			if (length <= 10) {
//				setSymbolSize(new Integer[]{ 120, 70 });
//			}else if (length <= 20) {
				setSymbolSize(new Integer[]{ 200, 120 });
//			}
		}
		this.name = name;
		this.value = value;
	}
}
