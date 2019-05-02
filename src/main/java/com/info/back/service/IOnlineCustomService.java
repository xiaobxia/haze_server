/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IOnlineCustomService
 * Author:   Liubing
 * Date:     2018/4/9 16:41
 * Description: 在线客服service层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.info.back.service;

import com.info.back.pojo.CustomerClassArrange;
import com.info.back.pojo.UserDetail;
import com.info.web.pojo.BackUser;
import com.info.web.util.PageConfig;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈在线客服service层〉
 *
 * @author Liubing
 * @create 2018/4/9
 * @since 1.0.0
 */
public interface IOnlineCustomService {
    UserDetail getUserInfoDetail(Map<String, Object> params);

    CustomerClassArrange getCustomerClassById(String id);

    CustomerClassArrange getCustomerClassByDate(String date);

    void saveCustomerClassArrange(CustomerClassArrange customerClassArrange);

    void updateCustomerClassArrange(CustomerClassArrange customerClassArrange);

    PageConfig<Map<String,Object>> getCustomerClassList(Map<String,Object> params);

    List<BackUser> getCustomerList();

    List<Map<String,Object>> getCustomerClassForExcel(String [] idArray);

    String getLastClassDate();
}
