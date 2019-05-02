package com.info.web.controller;

import com.info.web.dao.IBorrowOrderDao;
import com.info.web.pojo.BorrowOrderLocationStatistics;
import com.info.web.pojo.BorrowOrderLocationStatisticsDo;
import com.info.web.util.StringDateUtils;
import com.info.web.util.identitycardlocation.IdentityCardLocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phi on 2018/1/5.
 */
@Slf4j
@Controller
public class StatisticsController {
    @Autowired
    private IBorrowOrderDao borrowOrderDao;

    /**
     * 省失信率
     *
     */
    @RequestMapping(value = "getProvinceStatistics", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public void getProvinceStatistics(String beginTime, String endTime, HttpServletResponse response) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String verifyBeginTime = beginTime;
        String verifyEndTime = endTime;
        try {
            verifyBeginTime = timeStampFormat.format(simpleDateFormat.parse(beginTime));
            verifyBeginTime = StringDateUtils.daysAdd("yyyy-MM-dd HH:mm:ss",verifyBeginTime,3);
            verifyEndTime = timeStampFormat.format(simpleDateFormat.parse(endTime));
            verifyEndTime = StringDateUtils.daysAdd("yyyy-MM-dd HH:mm:ss",verifyEndTime,3);
        } catch (ParseException e) {
            log.error("getProvinceStatistics error:{}",e);
        }

        List<BorrowOrderLocationStatisticsDo> borrowOrderLocationStatisticsDos = borrowOrderDao
                .selectBorrowOrderLocationStatistics(Timestamp.valueOf(verifyBeginTime),Timestamp.valueOf(verifyEndTime));

        String res = format(statisticsListMaker(borrowOrderLocationStatisticsDos,"province"));

        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(res);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("getProvinceStatistics error:{}",e);
        }
    }

    /**
     * 市失信率
     *
     */
    @RequestMapping(value = "getCityStatistics", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public void getCityStatistics(String beginTime, String endTime, HttpServletResponse response) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String verifyBeginTime = beginTime;
        String verifyEndTime = endTime;
        try {
            verifyBeginTime = timeStampFormat.format(simpleDateFormat.parse(beginTime));
            verifyBeginTime = StringDateUtils.daysAdd("yyyy-MM-dd HH:mm:ss",verifyBeginTime,3);
            verifyEndTime = timeStampFormat.format(simpleDateFormat.parse(endTime));
            verifyEndTime = StringDateUtils.daysAdd("yyyy-MM-dd HH:mm:ss",verifyEndTime,3);
        } catch (ParseException e) {
            log.error("getCityStatistics error:{}",e);

        }

        List<BorrowOrderLocationStatisticsDo> borrowOrderLocationStatisticsDos = borrowOrderDao
                .selectBorrowOrderLocationStatistics(Timestamp.valueOf(verifyBeginTime),Timestamp.valueOf(verifyEndTime));

        String res = format(statisticsListMaker(borrowOrderLocationStatisticsDos,"city"));

        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(res);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("getCityStatistics error:{}",e);
        }
    }

    public String format(List<BorrowOrderLocationStatistics> locationStatistics) {
        String html = "";
        html += "<!DOCTYPE html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>";
        html += "<body>" + "<table border='1'><tr><td>失信率统计</td></tr></table>";
        html += "<table border='1'>";
        html += "<tr>" +
                "<td>地域</td><td>到期订单数</td><td>失信订单数</td><td>失信率（逾期三天以上）</td>" +
                "<td>99后失信订单数</td><td>99后失信率</td><td>99至92失信订单数</td><td>99至92失信率</td>" +
                "<td>91至85失信订单数</td><td>91至85失信率</td><td>84至76失信订单数</td><td>84至76失信率</td>" +
                "<td>75至68失信订单数</td><td>75至68失信率</td><td>68前失信订单数</td><td>68前失信率</td>";
        html += "</tr>";

        for (BorrowOrderLocationStatistics statistic : locationStatistics) {
            html += "<tr>";
            html += "<td>" + statistic.getLocation() + "</td>";
            html += "<td>" + statistic.getCount() + "</td>";
            html += "<td>" + statistic.getBadCreditCount() + "</td>";
            html += "<td>" + statistic.getBadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge99LaterCountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge99LaterBadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge99To92CountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge99To92BadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge91To85CountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge91To85BadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge84To76CountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge84To76BadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge75To68CountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge75To68BadCreditRate() + "</td>";
            html += "<td>" + statistic.getAge68BeforeCountBadCredit() + "</td>";
            html += "<td>" + statistic.getAge68BeforeBadCreditRate() + "</td>";
            html += "</tr>";
        }
        html += "</table>";
        html += "</body></html>";
        return html;
    }


    @RequestMapping(value = "getProvinceStatisticsAll", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public void getProvinceStatisticsAll(String beginTime, String endTime, HttpServletResponse response) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String verifyBeginTime = beginTime;
        String verifyEndTime = endTime;
        try {
            verifyBeginTime = timeStampFormat.format(simpleDateFormat.parse(beginTime));
            verifyEndTime = timeStampFormat.format(simpleDateFormat.parse(endTime));
        } catch (ParseException e) {
            log.error("getProvinceStatisticsAll error:{}",e);
        }

        List<BorrowOrderLocationStatisticsDo> borrowOrderLocationStatisticsDos =
                borrowOrderDao.selectBorrowOrderLocationStatisticsAll(Timestamp.valueOf(verifyBeginTime),Timestamp.valueOf(verifyEndTime));

        String res = formatAll(statisticsListMaker(borrowOrderLocationStatisticsDos,"province"));

        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(res);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("getProvinceStatisticsAll error:{}",e);
        }
    }

    @RequestMapping(value = "getCityStatisticsAll", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public void getCityStatisticsAll(String beginTime, String endTime, HttpServletResponse response) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String verifyBeginTime = beginTime;
        String verifyEndTime = endTime;
        try {
            verifyBeginTime = timeStampFormat.format(simpleDateFormat.parse(beginTime));
            verifyEndTime = timeStampFormat.format(simpleDateFormat.parse(endTime));
        } catch (ParseException e) {
            log.error("getCityStatisticsAll error:{}",e);
        }

        List<BorrowOrderLocationStatisticsDo> borrowOrderLocationStatisticsDos =
                borrowOrderDao.selectBorrowOrderLocationStatisticsAll(Timestamp.valueOf(verifyBeginTime),Timestamp.valueOf(verifyEndTime));

        String res = formatAll(statisticsListMaker(borrowOrderLocationStatisticsDos,"city"));

        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(res);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("getCityStatisticsAll error:{}",e);
        }
    }

    public String formatAll(List<BorrowOrderLocationStatistics> locationStatistics) {
        String html = "";
        html += "<!DOCTYPE html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>";
        html += "<body>" + "<table border='1'><tr><td>通过率统计</td></tr></table>";
        html += "<table border='1'>";
        html += "<tr>" +
                "<td>地域</td><td>申请总人数</td><td>放款总人数</td><td>放款数量占比</td>" +
                "<td>99后申请数量占比</td><td>99后放款数量占比</td><td>99至92申请数量占比</td>" +
                "<td>99至92放款数量占比</td><td>91至85申请数量占比</td><td>91至85放款数量占比</td>" +
                "<td>84至76申请数量占比</td><td>84至76放款数量占比</td><td>75至68申请数量占比</td>" +
                "<td>75至68放款数量占比</td><td>68前申请数量占比</td><td>68前放款数量占比</td>";
        html += "</tr>";

        for (BorrowOrderLocationStatistics statistic : locationStatistics) {
            html += "<tr>";
            html += "<td>" + statistic.getLocation() + "</td>";
            html += "<td>" + statistic.getCount() + "</td>";
            html += "<td>" + statistic.getPassCount() + "</td>";
            html += "<td>" + statistic.getPassRate() + "</td>";
            html += "<td>" + statistic.getAge99LaterRate() + "</td>";
            html += "<td>" + statistic.getAge99LaterPassRate() + "</td>";
            html += "<td>" + statistic.getAge99To92Rate() + "</td>";
            html += "<td>" + statistic.getAge99To92PassRate() + "</td>";
            html += "<td>" + statistic.getAge91To85Rate() + "</td>";
            html += "<td>" + statistic.getAge91To85PassRate() + "</td>";
            html += "<td>" + statistic.getAge84To76Rate() + "</td>";
            html += "<td>" + statistic.getAge84To76PassRate() + "</td>";
            html += "<td>" + statistic.getAge75To68Rate() + "</td>";
            html += "<td>" + statistic.getAge75To68PassRate() + "</td>";
            html += "<td>" + statistic.getAge68BeforeRate() + "</td>";
            html += "<td>" + statistic.getAge68BeforePassRate() + "</td>";
            html += "</tr>";
        }
        html += "</table>";
        html += "</body></html>";
        return html;
    }

    private List<BorrowOrderLocationStatistics> statisticsListMaker(List<BorrowOrderLocationStatisticsDo> borrowOrderLocationStatisticsDos,String type){
        Map<String, BorrowOrderLocationStatistics> map = new HashMap<>();
        for (BorrowOrderLocationStatisticsDo aDo : borrowOrderLocationStatisticsDos) {

            //没有身份证号的订单会丢失
            if (aDo.getIdNumber() != null) {
                aDo.setIdCardProvince(IdentityCardLocationUtil.getProvince(aDo.getIdNumber()));
                aDo.setIdCardCity(IdentityCardLocationUtil.getCity(aDo.getIdNumber()));
                aDo.setBirthYear(IdentityCardLocationUtil.getBirthYear(aDo.getIdNumber()));
            }

            if ("province".equals(type)){
                //按省份
                if (aDo.getIdCardProvince() != null) {
                    BorrowOrderLocationStatistics tmp;
                    if (map.containsKey(aDo.getIdCardProvince())) {
                        tmp = map.get(aDo.getIdCardProvince());
                    } else {
                        tmp = new BorrowOrderLocationStatistics();
                        tmp.setLocation(aDo.getIdCardProvince());
                        map.put(aDo.getIdCardProvince(), tmp);
                    }
                    tmp.setCount(tmp.getCount() + 1);//订单数
                    if (aDo.getPassFlag()) {
                        tmp.setPassCount(tmp.getPassCount() + 1);//通过数
                        tmp.addAgeCountPassCount(aDo.getBirthYear());//通过年龄分层
                    }
                    if (aDo.getBadCreditFlag()) {
                        tmp.setBadCreditCount(tmp.getBadCreditCount() + 1);//失信数
                        tmp.addAgeCountBadCredit(aDo.getBirthYear());//失信年龄分层
                    }
                    tmp.addAgeCount(aDo.getBirthYear());//申请年龄分层
                }
            }

            if ("city".equals(type)){
                //按城市
                if (aDo.getIdCardCity() != null) {
                    BorrowOrderLocationStatistics tmp;
                    if (map.containsKey(aDo.getIdCardCity())) {
                        tmp = map.get(aDo.getIdCardCity());
                    } else {
                        tmp = new BorrowOrderLocationStatistics();
                        tmp.setLocation(aDo.getIdCardCity());
                        map.put(aDo.getIdCardCity(), tmp);
                    }
                    tmp.setCount(tmp.getCount() + 1);//订单数
                    if (aDo.getPassFlag()) {
                        tmp.setPassCount(tmp.getPassCount() + 1);//通过数
                        tmp.addAgeCountPassCount(aDo.getBirthYear());//通过年龄分层
                    }
                    if (aDo.getBadCreditFlag()) {
                        tmp.setBadCreditCount(tmp.getBadCreditCount() + 1);//失信数
                        tmp.addAgeCountBadCredit(aDo.getBirthYear());//失信年龄分层
                    }
                    tmp.addAgeCount(aDo.getBirthYear());//申请年龄分层
                }
            }

        }
        List<BorrowOrderLocationStatistics> resList = new ArrayList<>(map.values());

        //按订单数降序
        resList.sort((BorrowOrderLocationStatistics b1, BorrowOrderLocationStatistics b2) ->
                -((Integer) b1.getCount()).compareTo(b2.getCount()));

        return resList;
    }

}
