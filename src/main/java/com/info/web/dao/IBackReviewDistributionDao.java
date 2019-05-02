package com.info.web.dao;

import com.info.web.pojo.BackReviewDistribution;

import java.util.List;
import java.util.Map;

/**
 * Created by tl on 2018/6/4.
 */
public interface IBackReviewDistributionDao {
    List<BackReviewDistribution> findParams(Map<String, Object> map);

    BackReviewDistribution findByUserId(Integer userId);

    void updateById(BackReviewDistribution backReviewDistribution);

//    void deleteById(Integer id);

    void insert(BackReviewDistribution backReviewDistribution);

    List<BackReviewDistribution> selectInUseBakRevDist(Map<String, Object> params);
}
