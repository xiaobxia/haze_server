package com.info.csPackage;

import com.info.back.pojo.UserDetail;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取催收系统数据〉
 *
 * @author Liubing
 * @create 2018/4/25
 * @since 1.0.0
 */
public interface ICsGetDataService {
    String getLoanData(UserDetail user);
}
