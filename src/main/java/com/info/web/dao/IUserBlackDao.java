package com.info.web.dao;

import com.info.web.pojo.UserBlack;

import java.util.List;


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

    /**
     * 黑名单用户
     * @return
     */
    List<UserBlack> userBlackList();

}