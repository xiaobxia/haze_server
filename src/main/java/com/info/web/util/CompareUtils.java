package com.info.web.util;

import java.math.BigDecimal;
import java.util.Date;

public class CompareUtils {

	/**
	 * 大于等于
	 * 
	 * @param dec1 d
	 * @param dec2 d
	 * @return dec1 >= dec1
	 */
	public static boolean greaterEquals(BigDecimal dec1, BigDecimal dec2) {
		return dec1.compareTo(dec2) >= 0;
	}


	/**
	 * 等于
	 * 
	 * @param dec1 d
	 * @param dec2 d
	 * @return dec1 == dec1
	 */
	public static boolean equals(BigDecimal dec1, BigDecimal dec2) {
		return dec1.compareTo(dec2) == 0;
	}

	/**
	 * 大于
	 * 
	 * @param dec1 c
	 * @param dec2 c
	 * @return dec1 > dec2
	 */
	public static boolean greaterThan(BigDecimal dec1, BigDecimal dec2) {
		return dec1.compareTo(dec2) == 1;

	}
}
