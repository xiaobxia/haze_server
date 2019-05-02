package com.info.web.dao;

import java.util.HashMap;
import java.util.List;

import com.info.web.pojo.BorrowOrderLoan;
import com.info.web.pojo.BorrowOrderLoanPerson;
import org.springframework.stereotype.Repository;

@Repository("borrowOrderLoanPersonDao")
public interface IBorrowOrderLoanPersonDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BorrowOrderLoanPerson record);

    int insertSelective(BorrowOrderLoanPerson record);
    BorrowOrderLoanPerson selectByParam(HashMap<String, Object> params);
    BorrowOrderLoanPerson selectByPrimaryKey(Integer id);
    
    List<BorrowOrderLoanPerson> findParams(HashMap<String, Object> params);
    


    int updateByPrimaryKeySelective(BorrowOrderLoanPerson record);

    int updateByPrimaryKey(BorrowOrderLoanPerson record);
    
    Integer findParamsCount(HashMap<String, Object> params);
    Integer findParamsStatisCount(HashMap<String, Object> params);
    /**
     * 根据订单号批量修改
     * @param record
     * @return
     */
    int updateByYurref(BorrowOrderLoanPerson record);
    
    /**
     * 根据条件更新
     * @param params
     * @return
     */
    int updateByParams(HashMap<String, Object> params);
    int updateByOrderId(BorrowOrderLoanPerson record);
    /**
     * 个人放款成功的服务费
     * @param params
     * @return
     */
    Long  findloanInterestsSucSumC(HashMap<String, Object> params);
}