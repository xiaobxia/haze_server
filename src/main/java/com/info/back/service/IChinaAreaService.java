package com.info.back.service;


import com.info.back.pojo.Areas;
import com.info.back.pojo.ChinaArea;

import java.util.List;

/**
 * 中国地区信息服务接口
 *
 * @author tgy
 * @version [版本号, 2018年5月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IChinaAreaService {

    /**
     * 根据各种编号查询具体省或市或区
     *
     * @param codeId
     * @return
     * @throws Exception
     */
    public ChinaArea findAreaInfoByCodeId(String codeId) throws Exception;


    /**
     * 根据6位身份证查询地域信息
     *
     * @param code
     * @return
     * @throws Exception
     */
    public String findAreaByIdCardNo(String code) throws Exception;

    /**
     * 查询所有省份信息
     *
     * @return
     * @throws Exception
     */
    public List<Areas> selectAllProvince() throws Exception;

    /**
     * 根据省份查询城市
     *
     * @return
     * @throws Exception
     */
    public List<Areas> selectCityByProvince(Integer provinceId) throws Exception;

    /**
     * 根据城市查询县区
     *
     * @param cityId
     * @return
     * @throws Exception
     */
    public List<Areas> selectCountyByCity(Integer cityId) throws Exception;


}
