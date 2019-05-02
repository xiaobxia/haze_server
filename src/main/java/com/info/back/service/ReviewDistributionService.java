package com.info.back.service;

import com.info.back.dao.IBackUserDao;
import com.info.web.dao.IBackReviewDistributionDao;
import com.info.web.pojo.BackReviewDistribution;
import com.info.web.pojo.BackUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by tl on 2018/6/4.
 */
@Service
public class ReviewDistributionService implements IReviewDistributionService {
    @Resource
    private IBackReviewDistributionDao backReviewDistributionDao;
    @Resource
    private IBackUserDao backUserDao;
    public static final String MODULE_URL = "backBorrowOrder/getBorrowPage?bType=fengk_Review";

    @Override
    public List<BackUser> findAll(Map<String, Object> params) {
        return backUserDao.findReviewerByModuleUrl(MODULE_URL);
    }

    @Override
    public BackReviewDistribution findByUserId(Integer userId) {
        return backReviewDistributionDao.findByUserId(userId);
    }

    @Override
    public void insert(BackReviewDistribution backReviewDistribution) {
        backReviewDistributionDao.insert(backReviewDistribution);
    }

    @Override
    public void updateById(BackReviewDistribution backReviewDistribution) {
        backReviewDistributionDao.updateById(backReviewDistribution);
    }
}
