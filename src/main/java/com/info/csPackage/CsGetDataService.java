package com.info.csPackage;
import com.info.back.dao.ICsGetDataDao;
import com.info.back.pojo.UserDetail;
import com.info.back.utils.DynamicDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈获取催收数据〉
 *
 * @author Liubing
 * @create 2018/4/25
 * @since 1.0.0
 */
@Service
public class CsGetDataService implements ICsGetDataService {

    @Resource
    private ICsGetDataDao csGetDataDao;

    @Override
    public String getLoanData(UserDetail user){
        DynamicDataSource.setCustomerType(DynamicDataSource.QBM_CS);
        String jobName = csGetDataDao.queryCsDataByAssetOrderId(user.getAssetOrderId());
        user.setCurrentJobName(jobName);
        return csGetDataDao.queryCsDataByAssetOrderId(user.getAssetOrderId());
    }
}
