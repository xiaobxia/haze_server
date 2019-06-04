package com.info.web.service;

import com.info.web.pojo.UserBlack;
import com.info.web.util.PageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface IUserBlackService {

    /**
     * 批量导入黑名单用户
     * @param name
     * @param file
     * @return
     */
    boolean batchImport(String name,MultipartFile file,String userType);
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
     * 后台查询 分页
     */
     PageConfig<UserBlack> getUserPage(HashMap<String, Object> params);

    /**
     * 通过id查询黑名单用户
     * @param id
     * @return
     */
     UserBlack findBlackUserByParams(Integer id , Integer  userType, String userPhone);
}