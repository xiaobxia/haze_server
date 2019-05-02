package com.info.back.pojo;

/**
 * 中国地区信息
 *
 * @author tgy
 * @version [版本号, 2018年5月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ChinaArea {
    private Integer codeId;

    private Integer parentId;

    private String cityName;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }
}