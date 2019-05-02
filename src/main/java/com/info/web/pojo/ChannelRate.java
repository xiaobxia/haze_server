package com.info.web.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ChannelRate{
	
	private Integer id;
	private String channelRateName;
	private BigDecimal channelRegisterRate;
	private BigDecimal channelNewloanRate;
	private Date createdAt;
	private Date updatedAt;
	private String remark;
	
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelRateName() {
		return channelRateName;
	}
	public void setChannelRateName(String channelRateName) {
		this.channelRateName = channelRateName;
	}
	public BigDecimal getChannelRegisterRate() {
		return channelRegisterRate;
	}
	public void setChannelRegisterRate(BigDecimal channelRegisterRate) {
		this.channelRegisterRate = channelRegisterRate;
	}
	public BigDecimal getChannelNewloanRate() {
		return channelNewloanRate;
	}
	public void setChannelNewloanRate(BigDecimal channelNewloanRate) {
		this.channelNewloanRate = channelNewloanRate;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
}
