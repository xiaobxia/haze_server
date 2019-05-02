package com.info.web.pojo;

public class NoticeRelease {
	
	private Integer id;
	private Integer source;//渠道类型:1,APP端 2,微信 3,PC端 4,其他
	private Integer channel;//频道类型:1,首页 2,关于我们 3,其他
	private Integer column_type;//栏目类型:1,消息滚动设置 2,banner轮播页 3,消息中心
	private String title;//内容标题
	private Boolean dynamic_link;//是否生成动态链接:0,否  1,根据需要进行动态生成
	private Integer send_condition;//发送条件:1,注册成功 2,申请成功 3,放款成功 4,还款成功 5,逾期还款 6,其他
	private Integer sort;//序号
	private String send_content;//发送内容
	private Integer send_type;//推送方式:1,站内信 2,PUSH 3,第三方接口
	private String create_time;//创建时间
	private String remark;//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public Integer getColumn_type() {
		return column_type;
	}
	public void setColumn_type(Integer column_type) {
		this.column_type = column_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getDynamic_link() {
		return dynamic_link;
	}
	public void setDynamic_link(Boolean dynamic_link) {
		this.dynamic_link = dynamic_link;
	}
	public Integer getSend_condition() {
		return send_condition;
	}
	public void setSend_condition(Integer send_condition) {
		this.send_condition = send_condition;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getSend_content() {
		return send_content;
	}
	public void setSend_content(String send_content) {
		this.send_content = send_content;
	}
	public Integer getSend_type() {
		return send_type;
	}
	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
}
