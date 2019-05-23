package com.info.back.service;


import com.info.web.dao.BorrowProductConfigDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.BackExtend;
import com.info.web.pojo.BackLimit;
import com.info.web.pojo.BorrowProductConfig;
import com.info.web.pojo.ProductDetail;
import com.info.web.util.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService implements  IProductService{

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

    public ProductDetail getProductDetail(Integer id){
        ProductDetail productDetail = borrowProductConfigDao.getProductDetail(id);
        return productDetail;
    }

    @Override
    public void addProduct(BorrowProductConfig borrowProductConfig) {
        borrowProductConfig.setCreateTime(new Date());
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
}
