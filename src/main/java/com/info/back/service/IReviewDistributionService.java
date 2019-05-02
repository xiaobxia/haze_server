package com.info.back.service;

import com.info.web.pojo.BackReviewDistribution;
import com.info.web.pojo.BackUser;

import java.util.List;
import java.util.Map;

/**
 * 类描述：用户dao层 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午01:53:41 <br>
 */
public interface IReviewDistributionService {
    /**
     * 根据条件查询
     */
    List<BackUser> findAll(Map<String, Object> params);

    /**
     * 根据id查询<br>
     */
    BackReviewDistribution findByUserId(Integer userId);

    /**
     * 插入用户对象
     *
     * @param backReviewDistribution
     */
    void insert(BackReviewDistribution backReviewDistribution);

    /**
     * 更新用户对象
     *
     * @param backReviewDistribution
     */
    void updateById(BackReviewDistribution backReviewDistribution);

}
