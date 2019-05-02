package com.info.back.dao;


import com.info.back.pojo.Areas;
import com.info.back.pojo.ChinaArea;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 中国地区信息Dao层
 *
 * @author tgy
 * @version [版本号, 2018年5月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface IChinaAreaDao {
    int insert(ChinaArea record);

    int insertSelective(ChinaArea record);

    ChinaArea selectByCodeId(Integer codeId);

    /**
     * 查询省市信息 city带入市级信息
     */
    List<Areas> selectProAndCity();

    /**
     * 查询所有省份
     *
     * @return
     */
    List<Areas> selectAllProvince();

    /**
     * 根据省份查询地级市
     *
     * @param provinceId
     * @return
     */
    List<Areas> selectCityByProvince(Integer provinceId);

    /**
     * 根据市查询县级/区
     *
     * @param cityId
     * @return
     */
    List<Areas> selectCountyByCity(Integer cityId);

    /**
     * 根据前6位身份证好查询完整的地域信息
     *
     * @param code
     * @return
     */
    String selectAreaInfo(String code);
}