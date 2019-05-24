package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProductDetail {
	private Integer productId;//产品id
	private String productName;//产品名称
	private BigDecimal borrowAmount;//产品金额
	private BigDecimal totalFeeRate;//总费率
	private BigDecimal lateFee;//滞纳金
	private Integer extendId;//续期id
	private Integer limitId;//提额id
	private Integer status;//状态 0 默认 1 非默认
	private String extendName;//续期类型
	private String limitName;//提额类型
	private Integer limitCount;//还款几次可以提额
	private Integer limitProductId;//提额到那个产品
	private Integer limiitProductName;//提额产品的金额
	private Integer limitStatus;//提额状态 0 开启 1 关闭
	private String  limitRemark;//提额备注
	private Integer extendCount;//可续期次数
	private Integer extendMoney;//续期费用
	private Integer extendStatus;//续期状态 0 开启 1 关闭
	private String extendRemark;//续期备注
	private Integer extendDay;//续期天数
	private Integer borrowDay;//借款期限

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(BigDecimal borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public BigDecimal getTotalFeeRate() {
		return totalFeeRate;
	}

	public void setTotalFeeRate(BigDecimal totalFeeRate) {
		this.totalFeeRate = totalFeeRate;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public Integer getExtendId() {
		return extendId;
	}

	public void setExtendId(Integer extendId) {
		this.extendId = extendId;
	}

	public Integer getLimitId() {
		return limitId;
	}

	public void setLimitId(Integer limitId) {
		this.limitId = limitId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExtendName() {
		return extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public Integer getLimitProductId() {
		return limitProductId;
	}

	public void setLimitProductId(Integer limitProductId) {
		this.limitProductId = limitProductId;
	}

	public Integer getLimiitProductName() {
		return limiitProductName;
	}

	public void setLimiitProductName(Integer limiitProductName) {
		this.limiitProductName = limiitProductName;
	}

	public Integer getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(Integer limitStatus) {
		this.limitStatus = limitStatus;
	}

	public String getLimitRemark() {
		return limitRemark;
	}

	public void setLimitRemark(String limitRemark) {
		this.limitRemark = limitRemark;
	}

	public Integer getExtendCount() {
		return extendCount;
	}

	public void setExtendCount(Integer extendCount) {
		this.extendCount = extendCount;
	}

	public Integer getExtendMoney() {
		return extendMoney;
	}

	public void setExtendMoney(Integer extendMoney) {
		this.extendMoney = extendMoney;
	}

	public Integer getExtendStatus() {
		return extendStatus;
	}

	public void setExtendStatus(Integer extendStatus) {
		this.extendStatus = extendStatus;
	}

	public String getExtendRemark() {
		return extendRemark;
	}

	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
	}

	public Integer getExtendDay() {
		return extendDay;
	}

	public void setExtendDay(Integer extendDay) {
		this.extendDay = extendDay;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getBorrowDay() {
		return borrowDay;
	}

	public void setBorrowDay(Integer borrowDay) {
		this.borrowDay = borrowDay;
	}

	@Override
	public String toString() {
		return "ProductDetail{" +
				"productId=" + productId +
				", productName='" + productName + '\'' +
				", borrowAmount=" + borrowAmount +
				", totalFeeRate=" + totalFeeRate +
				", lateFee=" + lateFee +
				", extendId=" + extendId +
				", limitId=" + limitId +
				", status=" + status +
				", extendName='" + extendName + '\'' +
				", limitName='" + limitName + '\'' +
				", limitCount=" + limitCount +
				", limitProductId=" + limitProductId +
				", limiitProductName=" + limiitProductName +
				", limitStatus=" + limitStatus +
				", limitRemark='" + limitRemark + '\'' +
				", extendCount=" + extendCount +
				", extendMoney=" + extendMoney +
				", extendStatus=" + extendStatus +
				", extendRemark='" + extendRemark + '\'' +
				", extendDay=" + extendDay +
				", borrowDay=" + borrowDay +
				'}';
	}
}
