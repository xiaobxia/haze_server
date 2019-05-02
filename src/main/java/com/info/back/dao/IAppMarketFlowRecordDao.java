package com.info.back.dao;

import com.info.back.pojo.AppMarketFlowRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 应用市场流量DAO层
 *
 * @author tgy
 * @version [版本号, 2018年5月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface IAppMarketFlowRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppMarketFlowRecord record);

    int insertSelective(AppMarketFlowRecord record);

    AppMarketFlowRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppMarketFlowRecord record);

    int updateByPrimaryKey(AppMarketFlowRecord record);

    //插入每日数据
    int insertList(List<AppMarketFlowRecord> recordList);

    //插入或更新每日数据
    int insertAppmarket(List<AppMarketFlowRecord> recordList);

    //插入或更新每日数据
    int updateAppmarket(List<AppMarketFlowRecord> recordList);

    //查询当天多少用户做过实名认证
    List<Map<String,String>> selectTheDayNameAuthCunt(Map<String, Object> params);

    //查询当天多少用户做过绑卡
    List<Map<String,String>> selectTheDayBindCardCunt(Map<String, Object> params);

    //查询当天多少用户申请借款
    List<Map<String,String>> selectTheDayRelyLoanCunt(Map<String, Object> params);

    //查询当天多少用户放款成功
    List<Map<String,String>> selectTheDayyesLoanCunt(Map<String, Object> params);

    //查询当天多少用户放款失败
    List<Map<String,String>> selectTheDayFailLoanCunt(Map<String, Object> params);

    //查询当天的总订单数
    Integer selectTheDayAllOrdersCunt(Map<String, Object> params);

    //查询当天逾期的用户数
    List<Map<String,String>> selectTheDayDelayCunt(Map<String, Object> params);

    //分页查询app应用市场流量消息数量
    Integer selectPageCunt(Map<String, Object> params);

    //分页查询app应用市场流量消息
    List<AppMarketFlowRecord> selectPageList(Map<String, Object> params);

    //查询统计汇总
    List<AppMarketFlowRecord> selectAllList(Map<String, Object> params);

    //查询统计汇总数量计数
    Integer selectAllListCunt(Map<String, Object> params);

    //根据应用市场与日期查询应用市场信息
    AppMarketFlowRecord selectByMarketAndDate(Map<String, Object> params);

    //查询当日所有的注册量
    //int selectTheDayAllRegisterCunt();

    //获取当天用户的所属应用市场
    //String selectTheDayUsersAppMarket(String userId);
}