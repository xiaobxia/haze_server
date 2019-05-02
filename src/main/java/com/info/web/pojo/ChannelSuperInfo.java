package com.info.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class ChannelSuperInfo implements Serializable {
	
	private Integer id;
	private String channelSuperName;
	private String channelSuperCode;

	private Date createdAt;
	private Date updatedAt;
	private String remark;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelSuperName() {
		return channelSuperName;
	}
	public void setChannelSuperName(String channelSuperName) {
		this.channelSuperName = channelSuperName;
	}
	public String getChannelSuperCode() {
		return channelSuperCode;
	}
	public void setChannelSuperCode(String channelSuperCode) {
		this.channelSuperCode = channelSuperCode;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	

}
