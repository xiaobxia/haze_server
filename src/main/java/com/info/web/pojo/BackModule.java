package com.info.web.pojo;

import java.util.List;

/**
 * 
 * 类描述：系统菜单类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-27 下午06:01:20 <br>
 * 
 * @version
 * 
 */
public class BackModule {

	private Integer id;//

	private String moduleName;//菜单名称

	private String moduleUrl;//菜单路径
	private String moduleStyle;//菜单打开方式及样式

	private String moduleDescribe;//菜单描述
	private Integer moduleSequence;//排序
	private Integer moduleView;//是否显示，1为显示,0为隐藏

	private Integer moduleParentId;//父菜单ID

	private List<BackModule> moduleList;//菜单集合

	public List<BackModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<BackModule> moduleList) {
		this.moduleList = moduleList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModuleSequence() {
		return moduleSequence;
	}

	public void setModuleSequence(Integer moduleSequence) {
		this.moduleSequence = moduleSequence;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName == null ? null : moduleName.trim();
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl == null ? null : moduleUrl.trim();
	}

	public String getModuleDescribe() {
		return moduleDescribe;
	}

	public void setModuleDescribe(String moduleDescribe) {
		this.moduleDescribe = moduleDescribe == null ? null : moduleDescribe.trim();
	}

	public String getModuleStyle() {
		return moduleStyle;
	}

	public void setModuleStyle(String moduleStyle) {
		this.moduleStyle = moduleStyle;
	}

	public Integer getModuleView() {
		return moduleView;
	}

	public void setModuleView(Integer moduleView) {
		this.moduleView = moduleView;
	}

	public Integer getModuleParentId() {
		return moduleParentId;
	}

	public void setModuleParentId(Integer moduleParentId) {
		this.moduleParentId = moduleParentId;
	}

	@Override
	public String toString() {
		return "BackModule [id=" + id + ", moduleDescribe=" + moduleDescribe + ", moduleList=" + moduleList + ", moduleName=" + moduleName
				+ ", moduleParentId=" + moduleParentId + ", moduleSequence=" + moduleSequence + ", moduleStyle=" + moduleStyle + ", moduleUrl="
				+ moduleUrl + ", moduleView=" + moduleView + "]";
	}

}