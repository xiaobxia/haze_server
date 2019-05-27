package com.info.web.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChannelInfo implements Serializable {
	private static final long serialVersionUID = 1950786603650458232L;
	private Integer id;
	private String channelName;
	private String channelCode;
	private String operatorName;
	private String channelTel;
	private Date createdAt;
	private Date updatedAt;
	private Integer apr;//一级佣金比例
	private String remark;
	private Integer status;
	private String userPhone;
	private User user;
	private String realname;
	private String userTel;
	private String userName;
	private String relPath;
	private Date createTime;
	
	
	private String channelProvince;
	private String channelCity;
	private String channelArea;
	private String channelPassword;
	
	/*渠道商上级*/
	private String channelSuperId;
	private String channelSuperName;
	private String channelSuperCode;
	
	private Integer rateId;
	private String channelRateName;

	/* 新增的 动态配置推广员  */
	private String apkUrl;
	private String channelTag;
	private String downloadPicUrl;
	private String registerPicUrl;
	private String picCodeNum;
	private String toutiaoConvertId;
	private Long userInfoId;

	private String channelUrl;
	private String promotionUrl;
}
