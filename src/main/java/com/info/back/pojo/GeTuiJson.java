package com.info.back.pojo;

import com.google.gson.Gson;

/**
 * 个推消息内部接口定义
 *
 * @author tgy
 * @version [版本号, 2018年3月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GeTuiJson {
	/**
	 * notice type 0:全部默认1：到期通知2：还款成功3：放款失败4：到账成功
	 */
	private int noticeType;
	/**
	 * CID
	 */
	private String clientId;
	/**
	 * 个推消息标题
	 */
	private String title;

	/**
	 * 个推消息类型
	 */
	private String channelType;

	/**
	 * 个推消息摘要
	 */
	private String summary;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 个推外部URL
	 */
	private String url;

	/**
	 * 1 显示通知 2 不现实通知
	 */
	private String isNotification;

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsNotification() {
		return isNotification;
	}

	public void setIsNotification(String isNotification) {
		this.isNotification = isNotification;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
