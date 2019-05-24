package com.info.back.service;


import com.info.web.dao.BorrowProductConfigDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackExtend;
import com.info.web.pojo.BackLimit;
import com.info.web.pojo.BorrowProductConfig;
import com.info.web.pojo.ProductDetail;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Slf4j
@Service
public class ProductService implements  IProductService {

    @Autowired
    private BorrowProductConfigDao borrowProductConfigDao;

    @Autowired
    private IPaginationDao paginationDao;

    @Override
    public PageConfig<ProductDetail> getProductList(HashMap<String, Object> params) {
        PageConfig<ProductDetail> pageConfig;
        pageConfig = paginationDao.findPage("getProductList", "getProductListCount", params, "web");
        return pageConfig;
    }

    @Override
    public List<ProductDetail> moneyList(HashMap<String, Object> params) {
        return borrowProductConfigDao.getProductList(params);
    }

    public ProductDetail getProductDetail(Integer id) {
        ProductDetail productDetail = borrowProductConfigDao.getProductDetail(id);
        return productDetail;
    }

    @Override
    public void addProduct(BorrowProductConfig borrowProductConfig) {
        BigDecimal totalFeeRate = borrowProductConfig.getTotalFeeRate().subtract(BigDecimal.valueOf(15000));
        borrowProductConfig.setBorrowInterest(totalFeeRate);
        borrowProductConfig.setTurstTrial(BigDecimal.valueOf(4000));
        borrowProductConfig.setPlatformLicensing(BigDecimal.valueOf(5000));
        borrowProductConfig.setCollectChannelFee(BigDecimal.valueOf(3000));
        borrowProductConfig.setAccountManagerFee(BigDecimal.valueOf(3000));
        borrowProductConfig.setStatus(1);
        borrowProductConfig.setProjectName("haze");
        borrowProductConfig.setCreateTime(new Date());
        borrowProductConfig.setDealFlag("n");
        borrowProductConfigDao.insert(borrowProductConfig);
    }

    @Override
    public void updateProduct(BorrowProductConfig borrowProductConfig) {
        borrowProductConfig.setUpdateTime(new Date());
        borrowProductConfigDao.updateByPrimaryKeySelective(borrowProductConfig);
    }

    @Override
    public PageConfig<BackExtend> getExtendList(HashMap<String, Object> params) {
        PageConfig<BackExtend> pageConfig;
        pageConfig = paginationDao.findPage("getExtendList", "getExtendCount", params, "web");
        return pageConfig;
    }

    @Override
    public List<BackExtend> findExtendList(HashMap<String, Object> params) {
        List<BackExtend> list = borrowProductConfigDao.getExtendList(params);
        return list;
    }

    @Override
    public PageConfig<BackLimit> getLimitList(HashMap<String, Object> params) {
        PageConfig<BackLimit> pageConfig;
        pageConfig = paginationDao.findPage("getLimitList", "getLimitListCount", params, "web");
        return pageConfig;
    }

    @Override
    public List<BackLimit> findLimitList(HashMap<String, Object> params) {
        List<BackLimit> list = borrowProductConfigDao.getLimitList(params);
        return list;
    }

    @Override
    public void addLimit(BackLimit backLimit) {
        backLimit.setCreateDate(new Date());
        backLimit.setLimitStatus(0);
        borrowProductConfigDao.addLimit(backLimit);
    }

    @Override
    public void updateLimit(BackLimit backLimit) {
        backLimit.setUpdateDate(new Date());
        borrowProductConfigDao.updateLimit(backLimit);
    }

    @Override
    public void addExtend(BackExtend backExtend) {
        backExtend.setCreateDate(new Date());
        backExtend.setExtendStatus(0);
        borrowProductConfigDao.addExtend(backExtend);
    }

    @Override
    public void updateExtend(BackExtend backExtend) {
        backExtend.setUpdateDate(new Date());
        borrowProductConfigDao.updateExtend(backExtend);
    }

    @Override
    public BackExtend findExtend(Integer id) {
        BackExtend backExtend = borrowProductConfigDao.findExtend(id);
        return backExtend;
    }

    @Override
    public BackLimit findLimit(Integer id) {
        BackLimit backLimit = borrowProductConfigDao.findLimit(id);
        return backLimit;
    }

    @Override
    public Model openOrCloseProduct(Integer id, Model model) {
        try{
            //修改此id为默认产品
            BorrowProductConfig borrowProductConfig = new BorrowProductConfig();
            borrowProductConfig.setId(id);
            borrowProductConfig.setStatus(0);
            borrowProductConfig.setUpdateTime(new Date());
            borrowProductConfigDao.updateByPrimaryKeySelective(borrowProductConfig);
            //将其他所有产品设置为非默认产品
            List<ProductDetail> list = borrowProductConfigDao.getProductList(null);
            for (ProductDetail productDetail : list) {
                if(productDetail.getProductId() != id){
                    borrowProductConfig.setStatus(1);
                    borrowProductConfig.setId(productDetail.getProductId());
                    borrowProductConfig.setUpdateTime(new Date());
                    borrowProductConfigDao.updateByPrimaryKeySelective(borrowProductConfig);
                }
            }
            model.addAttribute("result","success");
        }catch (Exception e){
            log.error("修改为默认产品出错"+e.getMessage());
            model.addAttribute("result","error");
        }
        return model;
    }
}