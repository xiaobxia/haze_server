package com.info.back.pojo;

/**
 * 省市县三级联动数据
 *
 * @author tgy
 * @version [版本号, 2018年5月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Areas {

    /**
     * 省编号
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String province;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 城市
     */
    private String city;

    /**
     * 县ID
     */
    private String countyId;

    /**
     * 县
     */
    private String county;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
