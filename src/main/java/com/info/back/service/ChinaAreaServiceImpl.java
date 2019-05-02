package com.info.back.service;

import com.info.back.dao.IChinaAreaDao;
import com.info.back.pojo.Areas;
import com.info.back.pojo.ChinaArea;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 中国地区信息
 *
 * @author tgy
 * @version [版本号, 2018年5月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class ChinaAreaServiceImpl implements IChinaAreaService {

    @Autowired
    private IChinaAreaDao areaDao;

    /**
     * 根据各种编号查询具体省或市或区
     *
     */
    @Override
    public ChinaArea findAreaInfoByCodeId(String codeId) throws Exception {
        if (StringUtils.isEmpty(codeId)) {
            throw new Exception("codeId should not null!");
        }
        ChinaArea chinaArea = areaDao.selectByCodeId(Integer.valueOf(codeId));
        if (null == chinaArea) {
            throw new Exception("china area not exist this codeId:" + codeId);
        }

        return chinaArea;
    }

    /**
     * 根据6位身份证查询地域信息
     *
     */
    public String findAreaByIdCardNo(String code) throws Exception {

        if (StringUtils.isEmpty(code)) {
            throw new Exception("The six Idcard code should not null!");
        }
        if (code.length() < 6) {
            throw new Exception("The Idcard code not enough,please check!");
        }
        if (code.length() > 6) {
            code = code.substring(0, 6);
        }
        String area = areaDao.selectAreaInfo(code);
        if (StringUtils.isEmpty(area)) {
            throw new Exception("china area not exist this codeId:" + code);
        }
        return area;

    }

    /**
     * 查询所有省份信息
     *
     */
    @Override
    public List<Areas> selectAllProvince(){
        return areaDao.selectAllProvince();
    }

    /**
     * 根据省份查询城市
     *
     */
    @Override
    public List<Areas> selectCityByProvince(Integer provinceId) {
        return areaDao.selectCityByProvince(provinceId);
    }

    /**
     * 根据城市查询县区
     *
     */
    @Override
    public List<Areas> selectCountyByCity(Integer cityId) {
        return areaDao.selectCountyByCity(cityId);
    }

}
