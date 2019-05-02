package com.info.web.service;

import com.google.common.collect.Maps;
import com.info.back.dao.IBackDictionaryDao;
import com.info.constant.Constant;
import com.info.web.dao.IBorrowRemarkDao;
import com.info.web.dao.ILabelCountBaseDao;
import com.info.web.dao.ILabelCountNumDao;
import com.info.web.dao.IPaginationDao;
import com.info.web.pojo.LabelCountBase;
import com.info.web.pojo.LabelCountNum;
import com.info.web.pojo.LabelCountPageResult;
import com.info.web.pojo.User;
import com.info.web.pojo.statistics.BackDictionary;
import com.info.web.util.DateUtil;
import com.info.web.util.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author cqry_20
 * @create 20--30 :
 **/
@Slf4j
@Service
public class LabelCountService implements ILabelCountService {

    @Resource
    private IBorrowRemarkDao   borrowRemarkDao;
    @Resource
    private ILabelCountNumDao  labelCountNumDao;
    @Resource
    private ILabelCountBaseDao labelCountBaseDao;
    @Autowired
    private IPaginationDao     paginationDao;
    @Resource
    private IBackDictionaryDao backDictionaryDao;

    @Override
    public void labelCount(String countDate) {
        log.info("labelCount start.....coutDate: {}", countDate);
        if (StringUtils.isEmpty(countDate)) {
            countDate = DateUtil.format_yyyy_MM_dd(new Date());
        }
        /*
          用户来源（0： 所有 1：Android  2：ios 3：pc ）

          0: 在线客服，1：电话客服

          用户类型（0：全部 1：新用户 2：老用户）
         */
        Map<String, Object> params          = Maps.newHashMapWithExpectedSize(4);
        Map<String, Object> existParams     = Maps.newHashMapWithExpectedSize(4);
        String              userFromALL     = "(0)";
        String              userFromAndroid = "(1)";
        String              userFromIos     = "(2)";
        String              userFromPc      = "(3,4)";
        Byte                customerOnline  = new Byte("0");
        Byte                customerTel     = new Byte("1");
        Byte                userALL         = new Byte("0");
        Byte                userNew         = new Byte("1");
        Byte                userOld         = new Byte("2");

        // 1.来源：全部， 新老用户：全部， 客服类型：在线
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        params.put("countDate", countDate);

        existParams.put("customerType", customerOnline);
        existParams.put("countDate", countDate);
        existParams.put("userFrom", userFromALL);
        existParams.put("userType", userALL);
        saveLabelCount(params, existParams);

        // 2.来源：全部， 新老用户：全部， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);


        // 3.来源：全部， 新老用户：新， 客服类型：在线
        existParams.put("customerType", customerOnline);
        existParams.put("userType", userNew);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        params.put("userType", User.CUSTOMER_NEW);
        saveLabelCount(params, existParams);

        // 4.来源：全部， 新老用户：老， 客服类型：在线
        existParams.put("userType", userOld);
        params.put("userType", User.CUSTOMER_OLD);
        saveLabelCount(params, existParams);

        // 5.来源：全部， 新老用户：新， 客服类型：电话
        existParams.put("userType", userNew);
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        params.put("userType", User.CUSTOMER_NEW);
        saveLabelCount(params, existParams);

        // 6.来源：全部， 新老用户：老， 客服类型：电话
        existParams.put("userType", userOld);
        params.put("userType", User.CUSTOMER_OLD);
        saveLabelCount(params, existParams);

        // 7.来源：安卓， 新老用户：全部， 客服类型：电话
        existParams.put("userFrom", userFromAndroid);
        existParams.put("userType", userALL);
        params.put("userFrom", userFromAndroid);
        params.remove("userType");
        saveLabelCount(params, existParams);

        // 8.来源：安卓， 新老用户：全部， 客服类型：在线
        existParams.put("customerType", customerOnline);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        saveLabelCount(params, existParams);


        // 9.来源：安卓， 新老用户：新， 客服类型：在线
        existParams.put("userType", userNew);
        params.put("userType", User.CUSTOMER_NEW);
        saveLabelCount(params, existParams);

        // 10.来源：安卓， 新老用户：新， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 11.来源：安卓， 新老用户：老， 客服类型：在线
        existParams.put("userType", userOld);
        existParams.put("customerType", customerOnline);
        params.put("userType", User.CUSTOMER_OLD);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        saveLabelCount(params, existParams);

        // 12.来源：安卓， 新老用户：老， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 13.来源：ios， 新老用户：全部， 客服类型：在线
        existParams.put("userFrom", userFromIos);
        existParams.put("userType", userALL);
        existParams.put("customerType", customerOnline);
        params.put("userFrom", userFromIos);
        params.remove("userType");
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        saveLabelCount(params, existParams);

        // 14.来源：ios， 新老用户：全部， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 15.来源：ios， 新老用户：新， 客服类型：在线
        existParams.put("userType", userNew);
        existParams.put("customerType", customerOnline);
        params.put("userType", User.CUSTOMER_NEW);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        saveLabelCount(params, existParams);

        // 16.来源：ios， 新老用户：新， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 17.来源：ios， 新老用户：老， 客服类型：在线
        existParams.put("customerType", customerOnline);
        existParams.put("userType", userOld);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        params.put("userType", User.CUSTOMER_OLD);
        saveLabelCount(params, existParams);

        // 18.来源：ios， 新老用户：老， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 19.来源：其他， 新老用户：全部， 客服类型：在线
        existParams.put("userFrom", userFromPc);
        existParams.put("userType", userALL);
        existParams.put("customerType", customerOnline);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        params.put("userFrom", userFromPc);
        params.remove("userType");
        saveLabelCount(params, existParams);

        // 20.来源：其他， 新老用户：全部， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 21.来源：其他， 新老用户：新， 客服类型：在线
        existParams.put("userType", userNew);
        existParams.put("customerType", customerOnline);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        params.put("userType", User.CUSTOMER_NEW);
        saveLabelCount(params, existParams);

        // 22.来源：其他， 新老用户：新， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);

        // 23.来源：其他， 新老用户：老， 客服类型：在线
        existParams.put("userType", userOld);
        existParams.put("customerType", customerOnline);
        params.put("userType", User.CUSTOMER_OLD);
        params.put("customerType", Constant.USER_REMARK_ONLINE);
        saveLabelCount(params, existParams);

        // 24.来源：其他， 新老用户：老， 客服类型：电话
        existParams.put("customerType", customerTel);
        params.put("customerType", Constant.USER_REMARK);
        saveLabelCount(params, existParams);
        log.info("labelCount end");
    }

    private void saveLabelCount(Map<String, Object> params, Map<String, Object> existParams) {
        String              typeAndroid = "1";
        String              typeIos     = "2";
        String              typeOther   = "3";
        String              all   = "0";
        Integer             userNum     = borrowRemarkDao.getUserNumByParams(params);
        List<LabelCountNum> countNums;
        if (params.get("customerType") != null && Constant.USER_REMARK_ONLINE.equals(params.get("customerType").toString())) {
            countNums = borrowRemarkDao.getLabelsOnlineCustomerByParams(params);
        } else {
            countNums = borrowRemarkDao.getLabelsTelCustomerByParams(params);
        }
        LabelCountBase countBase = new LabelCountBase();
        countBase.setCountDate(DateUtil.getDate(existParams.get("countDate").toString(), DateUtil.YMD));
        countBase.setCustomerType((Byte) existParams.get("customerType"));
        String fromStr = existParams.get("userFrom").toString();
        Byte   userFrom;
        switch (fromStr) {
            case "(0)":
                userFrom = new Byte(all);
                break;
            case "(1)":
                userFrom = new Byte(typeAndroid);
                break;
            case "(2)":
                userFrom = new Byte(typeIos);
                break;
            default:
                userFrom = new Byte(typeOther);
                break;
        }
        countBase.setUserFrom(userFrom);
        countBase.setUserType((Byte) existParams.get("userType"));
        countBase.setUserNum(userNum);
        //删除已有基础表数据
        existParams.put("userFromParam", userFrom);
        labelCountBaseDao.deleteExistByParams(existParams);
        //重新插入
        labelCountBaseDao.insertSelective(countBase);
        //保存标签个数详情
        if (CollectionUtils.isNotEmpty(countNums)) {
            insertBatch(countNums, countBase.getId());
        }
    }

    private void insertBatch(List<LabelCountNum> records, Integer baseId) {
        records.forEach(record -> {
            record.setBaseId(baseId);
            labelCountNumDao.insert(record);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageConfig<LabelCountBase> findPage(HashMap<String, Object> params) {
        params.put(Constant.NAME_SPACE, "LabelCountBase");
        PageConfig<LabelCountBase> page  = paginationDao.findPage("findByParams", "findByParamsCount", params, "web");
        List<LabelCountBase>       datas = page.getItems();
        if (CollectionUtils.isNotEmpty(datas)) {
            datas.forEach(data -> {
                params.put("baseId", data.getId());
                List<LabelCountPageResult> results = labelCountNumDao.findSecondLevelNum(params);
                if (CollectionUtils.isNotEmpty(results)) {
                    Integer    totalNum = results.stream().mapToInt(LabelCountPageResult::getNum).sum();
                    BigDecimal numDecimal;
                    if (totalNum > 0) {
                        BigDecimal totalDecimal = new BigDecimal(totalNum.toString());
                        for (LabelCountPageResult result : results) {
                            numDecimal = new BigDecimal(result.getNum() == null ? "0" : result.getNum().toString());
                            result.setRate(numDecimal.multiply(new BigDecimal("100")).divide(totalDecimal, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    data.setPageResults(results);
                }
            });
            page.setItems(datas);
        }
        return page;
    }

    private String getLabelName(String type, String key) {
        HashMap<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        Map<String, String>     labelMap;
        params.put("dictionary", type);
        List<BackDictionary> backDictionaries = backDictionaryDao.findDictionarys(params);
        if (CollectionUtils.isNotEmpty(backDictionaries)) {
            labelMap = backDictionaries.stream().collect(Collectors.toMap(BackDictionary::getDataValue, BackDictionary::getDictName));
            return labelMap.get(key);
        }
        return "";
    }

    @Override
    public List<LabelCountPageResult> getDetailResult(HashMap<String, Object> params) {
        String                                online       = "0";
        Integer                               morningJob   = 1;
        Integer                               nightJob     = 2;
        String                                customerType = params.get("customerType") == null ? "" : params.get("customerType").toString();
        List<LabelCountPageResult>            results      = labelCountNumDao.findThirdLevelNum(params);
        List<LabelCountPageResult> morningResult      = results.stream().filter(a -> morningJob.equals(a.getJobKind())).collect(Collectors.toList());
        List<LabelCountPageResult> nightResult      = results.stream().filter(a -> nightJob.equals(a.getJobKind())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(results)) {
            return new ArrayList<>();
        }
        Integer    morningNum = morningResult.stream().mapToInt(LabelCountPageResult::getNum).sum();
        Integer    nightNum = nightResult.stream().mapToInt(LabelCountPageResult::getNum).sum();
        Integer userNum = params.get("userNum") == null ? 0 : Integer.valueOf(params.get("userNum").toString());
        if (online.equals(customerType)) {
            BigDecimal numDecimal;
            if (userNum > 0) {
                BigDecimal totalDecimal = new BigDecimal(userNum.toString());
                for (LabelCountPageResult result : results) {
                    numDecimal = new BigDecimal(result.getNum().toString());
                    result.setRate(numDecimal.multiply(new BigDecimal("100")).divide(totalDecimal, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            return results;
        } else {
            BigDecimal numDecimal;
            BigDecimal moriningDecimal = new BigDecimal(morningNum);
            BigDecimal nightDecimal = new BigDecimal(nightNum);
            if (moriningDecimal.intValue() == 0 && nightDecimal.intValue() == 0) {
                return results;
            }
            for (LabelCountPageResult result : results) {
                numDecimal = new BigDecimal(result.getNum().toString());
                if(morningJob.equals(result.getJobKind()) && morningNum > 0) {
                    result.setRate(numDecimal.multiply(new BigDecimal("100")).divide(moriningDecimal, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                } else if(nightNum > 0) {
                    result.setRate(numDecimal.multiply(new BigDecimal("100")).divide(nightDecimal, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            Map<String, LabelCountPageResult> rateMap = results.stream().collect(Collectors.toMap(LabelCountPageResult::getMapK, Function.identity()));

            List<LabelCountPageResult> finalResults      = new ArrayList<>();
            for (LabelCountPageResult one : results) {
                if(isExist(finalResults, one.getName())) {
                    continue;
                }
                rateMap.remove(one.getMapK());
                if (morningJob.equals(one.getJobKind())) {
                    one.setMorningRate(one.getRate());
                    LabelCountPageResult night = rateMap.get(one.getMapK(nightJob));
                    if (night != null) {
                        one.setNightRate(night.getRate());
                        rateMap.remove(one.getMapK(nightJob));
                    }
                } else if (nightJob.equals(one.getJobKind())) {
                    one.setNightRate(one.getRate());
                    LabelCountPageResult morning = rateMap.get(one.getMapK(morningJob));
                    if (morning != null) {
                        one.setMorningRate(morning.getRate());
                        rateMap.remove(one.getMapK(morningJob));
                    }
                }
                finalResults.add(one);
                if (rateMap.size() == 0) {
                    break;
                }
            }
            return finalResults;
        }
    }

    private boolean isExist(List<LabelCountPageResult> finalResults, String mapK) {
        for(LabelCountPageResult result : finalResults) {
            if(mapK.equals(result.getName())) {
                return true;
            }
        }
        return false;
    }
}
