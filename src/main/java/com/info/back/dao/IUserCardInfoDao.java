/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IUserCardInfoDao
 * Author:   Liubing
 * Date:     2018/4/25 18:14
 * Description: 银行信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.info.back.dao;

import com.info.web.pojo.UserCardInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈银行信息〉
 *
 * @author Liubing
 * @create 2018/4/25
 * @since 1.0.0
 */
@Repository
public interface IUserCardInfoDao {
    List<Map<String,Object>> findUserBankList(Map<String, Object> map);

    int findUserBankTotal(Map<String, Object> map);

    UserCardInfo selectUserBankCard(Map<String, Object> map);

    int saveUserCardInfo(UserCardInfo userCardInfo);

    UserCardInfo findUserCardByUserId(Map<String, Object> map);

    int updateUserCardInfo(UserCardInfo userCardInfo);

    int deleteUserCardInfo(int id);
}
