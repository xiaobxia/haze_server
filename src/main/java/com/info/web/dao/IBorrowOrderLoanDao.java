package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.BorrowOrderLoan;
import org.springframework.stereotype.Repository;

@Repository("borrowOrderLoanDao")
public interface IBorrowOrderLoanDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowOrderLoan record);

    int insertSelective(BorrowOrderLoan record);
    BorrowOrderLoan selectByParam(HashMap<String, Object> params);
    BorrowOrderLoan selectByPrimaryKey(Integer id);
    List<BorrowOrderLoan> findParams(HashMap<String, Object> params);
   
    int findParamsCount(HashMap<String, Object> params);
    int updateByPrimaryKeySelective(BorrowOrderLoan record);
    
    List<BorrowOrderLoan> findStatisParams(HashMap<String, Object> params);
    Integer findParamsStatisCount(HashMap<String, Object> params);
    int updateByPrimaryKey(BorrowOrderLoan record);
    /**
     * 根据订单号批量修改
     * @param record
     * @return
     */
    int updateByYurref(BorrowOrderLoan record);
    
    /**
     * 根据条件更新
     * @param params
     * @return
     */
    int updateByParams(HashMap<String, Object> params);
    
    int updateByOrderId(BorrowOrderLoan record);
    /**
     * 公司放款成功的服务费
     * @param params
     * @return
     */
  Long  findloanInterestsSucSumB(HashMap<String, Object> params);
}