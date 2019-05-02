package com.info.web.pojo.index;

public class IndexDto {
	
	private Integer id;
	private String content;
	private String status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "IndexDto [content=" + content + ", id=" + id + ", status="
				+ status + "]";
	}
	public IndexDto(String content, String status) {
		this.content = content;
		this.status = status;
	}
	public IndexDto(Integer id, String content, String status) {
		this.id = id;
		this.content = content;
		this.status = status;
	}
	public IndexDto() {
	}
	

}
