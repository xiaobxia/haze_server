package com.info.web.dao;

import com.info.web.pojo.UserBlack;


public interface IUserBlackDao {
    /**
     * 修改黑名单用户状态（删除）
     * @param id
     */
    void updateUserBlackStatus(Integer id);

    /**
     * 添加黑名单用户
     * @param userBlack
     */
    void addUserBlack(UserBlack userBlack);

}