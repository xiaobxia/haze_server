package com.info.web.service;

import com.info.back.utils.ServiceResult;
import com.info.risk.pojo.RiskCreditUser;
import com.info.web.pojo.*;
import com.info.web.util.PageConfig;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjb
 * @version V1.0
 * @ClassName: IBorrowService.java
 * @Description: TODO
 * @Date 2016年12月12日 下午7:22:16
 */
public interface IBorrowOrderService {
    List<BorrowOrder> findOrderIdAndUserIdList(HashMap<String, Object> params);

    /**
     * 根据条件查找借款
     *
     * @param params
     * @return
     */
    public List<BorrowOrder> findAll(HashMap<String, Object> params);

    /**
     * 根据主键查找一条
     *
     * @return
     */
    public BorrowOrder findOneBorrow(Integer id);

    /**
     * 放款回调更新
     *
     * @param borrowOrder
     * @param code
     * @param desc
     */
    public void updateLoanNew(BorrowOrder borrowOrder, String code, String desc);

    /**
     * 通过条件查找一条
     *
     * @param param
     * @return
     */
    public BorrowOrder findByParam(HashMap<String, Object> param);

    /**
     * 根据主键删除对象
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 插入 对象
     *
     * @param backUser
     */
    public void insert(BorrowOrder borrowOrder);

    /**
     * 更新 象
     *
     * @param backUser
     */
    public void updateById(BorrowOrder borrowOrder);

    public Map<String, Object> updateLoan(BorrowOrder borrowOrder, Integer userId);

    /**
     * 更新 象
     *
     * @param backUser
     */
    public void authBorrowState(BorrowOrder borrowOrder);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageConfig<BorrowOrder> findPage(HashMap<String, Object> params);

    /**
     * 分页查询B类服务费
     *
     * @param params
     * @return
     */
    public PageConfig<BorrowOrderLoan> findBorrowFreeBPage(HashMap<String, Object> params);

    /**
     * 分页查询B类服务费-统计
     *
     * @param params
     * @return
     */
    public PageConfig<BorrowOrderLoan> findBorrowFreeBStatisPage(HashMap<String, Object> params);

    /**
     * 分页查询C类服务费-统计
     *
     * @param params
     * @return
     */
    public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCStatisPage(HashMap<String, Object> params);

    public Integer findBorrowFreeBCount(HashMap<String, Object> params);

    public Integer findBorrowFreeBStatisCount(HashMap<String, Object> params);

    /**
     * 分页查询C类服务费
     *
     * @param params
     * @return
     */
    public PageConfig<BorrowOrderLoanPerson> findBorrowFreeCPage(HashMap<String, Object> params);

    public Integer findBorrowFreeCCount(HashMap<String, Object> params);

    public Integer findBorrowFreeCStatisCount(HashMap<String, Object> params);

    public Map<String, Object> saveLoan(Map<String, String> params, User user);

    /**
     * 计算用户借款服务费
     *
     * @param configParams
     * @param money        单位：分
     * @return
     */
    public Map<String, Integer> calculateApr(Integer money, Integer period);

    // ************************用户额度管理

    /**
     * 提額
     *
     * @param backUser
     */
    public void addUserLimit(User user);

    /**
     * 修改额度
     *
     * @param mapParam userId，newAmountMax
     */
    public void changeUserLimit(HashMap<String, Object> mapParam);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageConfig<UserLimitRecord> finduserLimitPage(HashMap<String, Object> params);

    /**
     * 查询用户最近借款信息
     *
     * @param parseInt
     * @return
     */
    public BorrowOrder selectBorrowOrderUseId(Integer userId);

    /**
     * 检查当前用户是否存在未还款完成的订单
     *
     * @param userId
     * @return 1：是；0：否
     */
    public Integer checkBorrow(Integer userId);

    /**
     * 更新risk
     *
     * @param riskCreditUser
     */
    public void updateRiskCreditUserById(RiskCreditUser riskCreditUser);

    /**
     * 根据用户查询申请列表
     *
     * @param userId
     * @return
     */
    List<BorrowOrder> findByUserId(Integer userId);

    /**
     * 请求打款（批量 - 新增）
     *
     * @param borrowOrders
     */
    public void requestPaysForBatch(List<BorrowOrder> borrowOrders);

    /**
     * 请求打款（单条 - 新增）
     *
     * @param borrowOrder
     */
    public void requestPaysForSimgle(BorrowOrder borrowOrder);

    /**
     * 请求打款（非招行）
     *
     * @param borrowOrders
     */
    public void requestPaysNotCmbUser(List<BorrowOrder> borrowOrders);

    /**
     * 请求打款(招商用户)
     *
     * @param borrowOrders
     */
    public void requestPaysCmbUser(List<BorrowOrder> borrowOrders);

    /**
     * 请求打款(非招商)
     *
     * @param borrowOrders
     */
    public ServiceResult subPayNotCmb(List<BorrowOrder> borrowOrders, String subType);

    /**
     * 请求打款(招商)
     *
     * @param borrowOrders
     */
    public ServiceResult subPayCmb(List<BorrowOrder> borrowOrders, String subType);

    /**
     * 请求打款（多条 - 新增）
     *
     * @param borrowOrders
     * @return
     */
    public ServiceResult subPaysForBatch(List<BorrowOrder> borrowOrders, String subType);

    /**
     * 请求打款（单条 - 新增）
     *
     * @param borrowOrder
     * @param subType
     * @return
     */
    public ServiceResult subPaysForSimgle(BorrowOrder borrowOrder, String subType);

    /**
     * 统计借款记录数
     *
     * @param params
     * @return
     */
    public int findParamsCount(HashMap<String, Object> params);

    /**
     * 口袋资产接口调用
     *
     * @param bo
     * @param user
     * @param bankAllInfo
     */
    public ServiceResult addDocking(BorrowOrderChecking bo);

    /**
     * 招财猫资产接口调用
     *
     * @param bo
     * @param user
     * @param bankAllInfo
     */
    public ServiceResult addDockingExtZCM(BorrowOrderCheckingExt bo);

    /**
     * 招财猫 查询指定日期资产包信息，同时将信息放入redis
     *
     * @param curDate
     * @return
     */
    public ServiceResult assetpackqueryZCM(String curDate);

    /**
     * @param bo
     */
    public void addBorrowChecking(BorrowOrder bo);

    /**
     * @param bo
     */
    public void addBorrowCheckingExt(BorrowOrder bo);

    /**
     * 重新放款（服务费）
     *
     * @param yurref
     */
    public Boolean loanAgainFee(String yurref, String Loantype);

    /**
     * 分页查询BorrowOrderCheck
     *
     * @param params
     * @return
     */
    PageConfig<BorrowOrderChecking> findBorrowOrderCheckPage(HashMap<String, Object> params);

    Integer findBorrowOrderCheckCount(HashMap<String, Object> params);

    /**
     * 查询放款成功对应的公司服务费金额
     *
     * @param params
     * @return
     */
    Long findloanInterestsSucSumB(HashMap<String, Object> params);

    /**
     * 查询放款成功对应的个人服务费金额
     *
     * @param params
     * @return
     */
    Long findloanInterestsSucSumC(HashMap<String, Object> params);

    /**
     * 查询放款成功对应的借款金额
     *
     * @param params
     * @return
     */
    Long findMoneyAmountSucSum(HashMap<String, Object> params);

    /**
     * 实际放款成功金额
     *
     * @param params
     * @return
     */
    Long findIntoMoneySucSum(HashMap<String, Object> params);

    /**
     * 批量插入合同资方数据
     *
     * @param boNewList
     * @return
     */
    int batchInsertBCI(List<BorrowOrder> boNewList);

    /**
     * 获取网银互联账户信息
     *
     * @param curLoanAccount
     * @return
     */
    HashMap<String, String> getHLAccountInfo(Integer curLoanAccount);

    /**
     * 获取代发账户信息
     *
     * @param curLoanAccount
     * @return
     */
    HashMap<String, String> getDFAccountInfo(Integer curLoanAccount);

    public List<BorrowOrder> findAssetBorrowOrderForZhimaFeedback(HashMap<String, Object> paraMap);


    /**
     * 分页查询招财猫资产
     *
     * @param params
     * @return
     */
    PageConfig<BorrowOrderCheckingExt> findBorrowOrderCheckExtPage(HashMap<String, Object> params);

    Integer findBorrowOrderCheckExtCount(HashMap<String, Object> params);

    public void autoFangkuan(BorrowOrder order);

    boolean distributeBorrowOrderForReview(BackUser backUser);

    void autoDistributeOrderForReview();

    int handDistributeOrderForReview(Map<Integer, List<BorrowOrder>> userDisOrder);

    Map<String, String> getStatisticMap(Integer id);

    /**
     * 查询后台复审用户
     *
     * @return
     */
    List<Map<String, String>> findBackReviewUserName();

    /**
     * 获取ai 语音认证失败用户集合
     * @return list
     */
    List<String> getUserIdList();

    List<String> getUserIdList2();

    List<String> getUserIdWaitList();

    Integer findOveChannle(Integer channelId,String loanTime,Integer status,String customerType);

    Integer findRepayCount(Integer channelId, List<Integer> statusList,String loanTime,String customerType);

    Integer findExtendChannel(Integer channelId,String loanTime);

    Integer findRenewalCount(Integer channelId,String loanTime);

}
