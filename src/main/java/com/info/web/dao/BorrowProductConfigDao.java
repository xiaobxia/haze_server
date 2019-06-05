package com.info.web.dao;

import com.info.web.pojo.BackExtend;
import com.info.web.pojo.BackLimit;
import com.info.web.pojo.BorrowProductConfig;
import com.info.web.pojo.ProductDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BorrowProductConfigDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    int insert(BorrowProductConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    int insertSelective(BorrowProductConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    BorrowProductConfig selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    int updateByPrimaryKeySelective(BorrowProductConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table borrow_product_config
     *
     * @mbggenerated Tue Sep 11 15:53:00 CST 2018
     */
    int updateByPrimaryKey(BorrowProductConfig record);

    /**
     * 查詢所有可用借款产品
     *
     * @return
     */
    List<BorrowProductConfig> queryAllBorrowProductConfig();


    /**
     * 获取该产品借款借小于等于该产品的产品列表
     *
     * @param borrowProductId 产品id
     * @return
     */
    List<BorrowProductConfig> queryAllConfigByMaxBorrowProductId(Integer borrowProductId);


    /**
     * 根据期限和借款金额查询费率配置
     *
     * @param params
     * @return
     */
    BorrowProductConfig selectByBorrowMoneyAndPeriod(HashMap<String, String> params);

    /**
     * 得到产品线id
     * @param params
     * @return
     */
    int selectConfigId(HashMap<String, String> params);

    int selectCountByBorrowMoneyAndPeriod(HashMap<String, String> params);

    BorrowProductConfig queryUserConfigByUserIdAndDay(@Param("userId") int userId, @Param("day") int day);

    BorrowProductConfig queryMaxLimitProduct(Map params);

    BorrowProductConfig queryByBorrowDayAndAmount(@Param("amount") BigDecimal amount, @Param("borrowDay") int borrowDay);

    List<ProductDetail> getProductList( HashMap<String, Object> params);

    Integer getProductListCount( HashMap<String,Object> params);

    ProductDetail getProductDetail(@Param("id") Integer id);

    List<BackExtend> getExtendList( HashMap<String,Object> params);

    Integer getExtendCount( HashMap<String,Object> params);

    List<BackLimit> getLimitList(HashMap<String,Object> params);

    Integer getLimitListCount(HashMap<String,Object> params);

    void addLimit(BackLimit backLimit);

    void updateLimit(BackLimit backLimit);

    void addExtend(BackExtend backExtend);

    void updateExtend(BackExtend backExtend);

    BackExtend findExtend(Integer id);

    BackLimit findLimit(Integer id);

    BigDecimal queryByOrderId(Integer orderId);
}