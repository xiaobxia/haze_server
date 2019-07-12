package com.info.back.service;


import com.info.web.pojo.BackExtend;
import com.info.web.pojo.BackLimit;
import com.info.web.pojo.BorrowProductConfig;
import com.info.web.pojo.ProductDetail;
import com.info.web.util.PageConfig;

import java.util.HashMap;
import java.util.List;


public interface IProductService{
	//产品列表
	PageConfig<ProductDetail> getProductList(HashMap<String, Object> params);
	//产品金额列表
	List<ProductDetail> moneyList(HashMap<String, Object> params);
	//产品详情
	 ProductDetail getProductDetail(Integer id);
	//添加产品
	void addProduct(BorrowProductConfig borrowProductConfig);
	//修改产品
	void updateProduct(BorrowProductConfig borrowProductConfig);
	//续期列表
	PageConfig<BackExtend> getExtendList(HashMap<String,Object> params);
	//查询页面用 续期
	List<BackExtend> findExtendList(HashMap<String, Object> params);
	//提额列表
	PageConfig<BackLimit> getLimitList(HashMap<String,Object> params);
	//查询页面用 提额
	List<BackLimit> findLimitList(HashMap<String, Object> params);
	//添加提额
	void addLimit(BackLimit backLimit, Integer beforeLimitProductId);
	//修改提额
	void updateLimit(BackLimit backLimit, Integer beforeLimitProductId);
	//添加续期
	void addExtend(BackExtend backExtend);
	//修改续期
	void updateExtend(BackExtend backExtend);
	BackExtend findExtend(Integer id);
	BackLimit findLimit(Integer id);
	void openOrCloseProduct(Integer id) throws Exception;

	List<BorrowProductConfig> queryAllBorrowProductConfig();
}
