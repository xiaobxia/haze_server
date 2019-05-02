package com.info.web.pojo;

public class Banner {
	private int id;
	private String showType = "0";// 渠道类型 0表示pc端 1表示Android端 2表示ios端 3表示其他
	private String channelType = "0";// 频道类型0表示首页，1表示关于我们，3表示其他
	private String columnType = "0";// 栏目类型0表示banner轮播页，1表示消息滚动设置
	private String title;// banner标题
	private String url;// 图片存储路径
	private String reurl;// 跳转h5的url
	private String sort;// 序号
	private String startTime = "0";// 开始时间
	private String endTime = "0";// 开始时间
	private String status = "1";// 状态 1表示有效，0表示无效
	private String presentWay = "0";// 发布方式 0表示立即发布，1表示定时发布

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReurl() {
		return reurl;
	}

	public void setReurl(String reurl) {
		this.reurl = reurl;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPresentWay() {
		return presentWay;
	}

	public void setPresentWay(String presentWay) {
		this.presentWay = presentWay;
	}

}
