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

    public ProductDetail getProductDetail(Integer id){
        ProductDetail productDetail = borrowProductConfigDao.getProductDetail(id);
        return productDetail;
    }

    @Override
    public void addProduct(BorrowProductConfig borrowProductConfig) {
          borrowProductConfigDao.insert(borrowProductConfig);
    }

    @Override
    public void updateProduct(BorrowProductConfig borrowProductConfig) {
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
    public List<BackLimit> findLimitList(HashMap<String, Object> params) {
        List<BackLimit> list = borrowProductConfigDao.getLimitList(params);
        return list;
    }
}
