<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = path + "/common/web";
%>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<c:set var="path" value="<%=path%>"></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>运营商数据样例</title>
    <meta charset=utf-8/>
    <!-- Bootstrap -->
    <link href="${basePath}/yunyingshang/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${basePath}/bootstrap-table-expandable.css" rel="stylesheet" media="screen">
    <link href="${basePath}/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <link href="${basePath}/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="${basePath}/styles.css" rel="stylesheet" media="screen">

    <script src="${basePath}/jquery-1.9.1.min.js"></script>
    <script src="${basePath}/bootstrap.min.js"></script>
    <script src="${basePath}/bootstrap-table-expandable.js"></script>
    <script src="${basePath}/bootstrap-datetimepicker.min.js"></script>
    <script src="${basePath}/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${basePath}/scripts.js"></script>
    <script src="${basePath}/mx.js"></script>

</head>
<body>
<style>
    div, h1, h2, h3, h4, h5, h6, table, tr, td {
        padding: 0;
        padding-left: 10px;
        margin: 0;
        box-sizing: border-box;
        font-size: 14px;
        white-space: nowrap;
        margin: 0 auto;
    }

    body {
        padding-top: 0px;
    }

    .box {
        width: 90%;
    }

    .table {
        margin: 0 auto 30px;
    }

    .tabbox {
        padding: 0;
        width: 100%;
        overflow-x: scroll;
    }

    table {
        width: 100%;
        border: 1px solid #ccc;
        border-collapse: collapse;
        margin: 0 auto;
    }

    .center {
        text-align: center;
    }

    .left {
        padding-left: 10px;
        text-align: left;
    }

    th {
        text-align: center;
        height: 30px;
        border-right: 1px solid #ccc;
    }

    .th {

        background: rgb(70, 140, 180);
    }

    td {
        height: 30px;
        border: 1px solid #ccc;
    }

    tr:nth-child(even) {
        background: rgb(235, 235, 235)
    }

    .sort {
        height: 30px;
        line-height: 30px;
        width: 100%;
        margin: 0 auto;
        font-size: 12px;
        color: rgb(119, 119, 119);
        text-align: left;
        font-weight: 100;
        padding: 0;
    }

    .h5 {
        width: 100%;
        height: 30px;
        margin: 0 auto;
        border-bottom: none;
        background: rgb(70, 140, 180);
        line-height: 30px;

    }

    h3 {
        font-size: 18px;
        font-weight: 700;
        width: 100%;
        margin: 30px auto;
    }

    .dropdown-menu {
        z-index: 10000;
    }
</style>

<div class="box">
    <div>
        <h3 style="padding-left: 0px;font-size: 25px;text-align: center">
            运营商报告
        </h3>
        <div style="text-align: right;font-size: 10px">
            报告编号：${taskId}
        </div>
    </div>
    <div class="table">
        <h5 class="h5">1.1用户基本信息</h5>
        <div class="tabbox">
            <table>
                <tbody id="searchResultTable">
                <tr>
                    <td>姓名：${reportBasic.userBasicInfo.name}</td>
                    <td colspan="3">身份证号：${reportBasic.userBasicInfo.idcard}</td>
                </tr>
                <tr>
                    <td>姓名From客户：${reportBasic.userBasicInfo.nameFromCustom}</td>
                    <td colspan="3">身份证号From客户：${reportBasic.userBasicInfo.idcardFromCustom}</td>
                </tr>
                <tr>
                    <td>手机号码：${reportBasic.userBasicInfo.mobile}</td>
                    <td>入网时长：${reportBasic.friendCircle.riskAnalysis.inTime}</td>
                    <td>性别：${reportBasic.userBasicInfo.gender}</td>
                    <td>年龄：${reportBasic.userBasicInfo.age}</td>
                </tr>
                <tr>
                    <td>星座：${reportBasic.userBasicInfo.constellation}</td>
                    <td>邮箱：${reportBasic.userBasicInfo.email}</td>
                    <td colspan="2">通讯地址：${reportBasic.userBasicInfo.address}</td>
                </tr>
                <tr>
                    <td>籍贯：${reportBasic.userBasicInfo.nativePlace}</td>
                    <td>手机号码归属地：${reportBasic.userBasicInfo.liveAddress}</td>
                    <td>居住地址：${reportBasic.userBasicInfo.workAddress}</td>
                    <td>工作地址：${reportBasic.userBasicInfo.workAddress}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table2-->
    <div class="table">
        <h5 class="h5">1.2 数据来源</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>来源名称</th>
                    <th>数据类别</th>
                    <th>获取时间</th>
                </tr>
                </thead>
                <tbody>
                <tr class="center">
                    <td>${reportBasic.dataSource.sourceName}</td>
                    <td>${reportBasic.dataSource.dataType}</td>
                    <td>${reportBasic.dataSource.dataGainTime}</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
    <!--table3-->
    <div class="table">
        <h5 class="h5">1.3信息校验</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">检查项</th>
                    <th>结果</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>身份证号码有效性</td>
                    <td class="center">${basicInfoCheckItemMap.idcard_check}</td>
                </tr>
                <tr>
                    <td>邮箱有效性</td>
                    <td class="center">${basicInfoCheckItemMap.email_check}</td>
                </tr>
                <tr>
                    <td>通讯地址有效性</td>
                    <td class="center">${basicInfoCheckItemMap.address_check}</td>
                </tr>
                <tr>
                    <td>身份证号码是否与运营商数据匹配</td>
                    <td class="center">${basicInfoCheckItemMap.idcard_match}</td>
                </tr>
                <tr>
                    <td>姓名是否与运营商数据匹配</td>
                    <td class="center">${basicInfoCheckItemMap.name_match}</td>
                </tr>
                <tr>
                    <td>通话记录完整性</td>
                    <td class="center">${basicInfoCheckItemMap.call_data_check}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table4-->
    <div class="table">
        <h5 class="h5">1.4社交分析摘要</h5>
        <h6 class="sort">朋友圈</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>朋友圈大小</td>
                    <td class="center">${reportBasic.friendCircle.friendNum3m}</td>
                    <td class="center">${reportBasic.friendCircle.friendNum6m}</td>
                    <td class="center">近3/6月联系号码数</td>
                </tr>
                <tr>
                    <td>朋友圈亲密度</td>
                    <td class="center">${reportBasic.friendCircle.goodFriendNum3m}</td>
                    <td class="center">${reportBasic.friendCircle.goodFriendNum6m}</td>
                    <td class="center">近3/6月联系十次以上的号码数量</td>
                </tr>
                <tr>
                    <td>朋友圈中心地</td>
                    <td class="center">${reportBasic.friendCircle.friendCityCenter3m}</td>
                    <td class="center">${reportBasic.friendCircle.friendCityCenter6m}</td>
                    <td class="center">近3/6月联系次数最多的归属地</td>
                </tr>
                <tr>
                    <td>朋友圈是否在本地</td>
                    <td class="center">${reportBasic.friendCircle.isCityMatchFriendCityCenter3m}</td>
                    <td class="center">${reportBasic.friendCircle.isCityMatchFriendCityCenter6m}</td>
                    <td class="center">近3/6月朋友圈中心地是否与手机归属地一致</td>
                </tr>
                <tr>
                    <td>互通电话的号码数目</td>
                    <td class="center">${reportBasic.friendCircle.interPeerNum3m}</td>
                    <td class="center">${reportBasic.friendCircle.interPeerNum6m}</td>
                    <td class="center"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="table">
        <h6 class="sort">联系人Top3 (近3月通话次数降序)</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>号码</th>
                    <th>归属地</th>
                    <th>通话次数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callNumTop3m}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${item.city}</td>
                            <td>${item.callNum}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">联系人Top3 (近6月通话次数降序)</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>号码</th>
                    <th>归属地</th>
                    <th>通话次数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callNumTop6m}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${item.city}</td>
                            <td>${item.callNum}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">联系人号码归属地Top3 (近3月通话次数降序)</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>地址</th>
                    <th>通话次数</th>
                    <th>通话号码数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callLocationTop3m}" varStatus="status">
                        <tr class="center">
                            <td>${item.city}</td>
                            <td>${item.callNum}</td>
                            <td>${item.peerNumberCount}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">联系人号码归属地Top3 (近6月通话次数降序)</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>地址</th>
                    <th>通话次数</th>
                    <th>通话号码数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callLocationTop6m}" varStatus="status">
                        <tr class="center">
                            <td>${item.city}</td>
                            <td>${item.callNum}</td>
                            <td>${item.peerNumberCount}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">是否呼叫指定联系人号码</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <td>号码</td>
                    <td>姓名</td>
                    <td>近一周通话次数</td>
                    <td>近一月通话次数</td>
                    <td>近三月通话次数</td>
                    <td>近六月通话次数</td>
                    <td>近三月通话时长（秒）</td>
                    <td>近六月通话时长（秒）</td>
                    <td>近三月主叫次数</td>
                    <td>近六月主叫次数</td>
                    <td>近三月被叫次数</td>
                    <td>近六月被叫次数</td>
                    <td>近三月早上（5:30-11:30）通话次数</td>
                    <td>近六月早上（5:30-11:30）通话次数</td>
                    <td>近三月中午（11:30-13:30）通话次数</td>
                    <td>近六月中午（11:30-13:30）通话次数</td>
                    <td>近三月下午（13:30-17:30）通话次数</td>
                    <td>近六月下午（13:30-17:30）通话次数</td>
                    <td>近三月晚上（17:30-23:30）通话次数</td>
                    <td>近六月晚上（17:30-23:30）通话次数</td>
                    <td>近三月凌晨（23:30-5:30）通话次数</td>
                    <td>近六月凌晨（23:30-5:30）通话次数</td>
                    <td>近三月工作日通话次数</td>
                    <td>近六月工作日通话次数</td>
                    <td>近三月周末通话次数</td>
                    <td>近六月周末通话次数</td>
                    <td>近三月节假日通话次数</td>
                    <td>近六月节假日通话次数</td>
                    <td>近三月是否有全天联系</td>
                    <td>近六月是否有全天联系</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.designatedContact}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${contactMobileNameMap['${item.peerNumber}']}</td>
                            <td>${item.callNum1Week}</td>
                            <td>${item.callNum1Month}</td>
                            <td>${item.callNum3Month}</td>
                            <td>${item.callNum6Month}</td>
                            <td>${item.callTime3Month}</td>
                            <td>${item.callTime6Month}</td>
                            <td>${item.dialNum3Month}</td>
                            <td>${item.dialNum6Month}</td>
                            <td>${item.dialedNum3Month}</td>
                            <td>${item.dialedNum6Month}</td>
                            <td>${item.callNumMorning3Month}</td>
                            <td>${item.callNumMorning6Month}</td>
                            <td>${item.callNumNoon3Month}</td>
                            <td>${item.callNumNoon6Month}</td>
                            <td>${item.callNumAfternoon3Month}</td>
                            <td>${item.callNumAfternoon6Month}</td>
                            <td>${item.callNumEvening3Month}</td>
                            <td>${item.callNumEvening6Month}</td>
                            <td>${item.callNumNight3Month}</td>
                            <td>${item.callNumNight6Month}</td>
                            <td>${item.callNumWeekday3Month}</td>
                            <td>${item.callNumWeekday6Month}</td>
                            <td>${item.callNumWeekend3Month}</td>
                            <td>${item.callNumWeekend6Month}</td>
                            <td>${item.callNumHoliday3Month}</td>
                            <td>${item.callNumHoliday6Month}</td>
                            <td>${item.callIfWholeDay3Month}</td>
                            <td>${item.callIfWholeDay6Month}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table5-->
    <div class="table">
        <h5 class="h5">1.5 风险分析摘要</h5>
        <h6 class="sort">风险识别</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>号码沉默度</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.mobileSilence_3}</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.mobileSilence_6}</td>
                    <td class="center">满分10分</td>
                </tr>
                <tr>
                    <td>欠费风险度</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.arrearageRisk_3}</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.arrearageRisk_6}</td>
                    <td class="center">满分10分</td>
                </tr>
                <tr>
                    <td>亲情网风险度</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.bindingRisk}</td>
                    <td class="center">--</td>
                    <td class="center">满分10分</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>结果</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>申请人姓名+身份证号码是否出现在法院黑名单</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.isNameAndIdcardInCourtBlack}</td>
                </tr>
                <tr>
                    <td>申请人姓名+身份证号码是否出现在金融机构黑名单</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.isNameAndIdcardInFinanceBlack}</td>
                </tr>
                <tr>
                    <td>申请人姓名+手机号码是否出现在金融机构黑名单</td>
                    <td class="center">${reportBasic.friendCircle.riskAnalysis.isNameAndMobileInFinanceBlack}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">风险联系</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>号码类别</th>
                    <th>近3月通话次数</th>
                    <th>近6月通话次数</th>
                    <th>近3月通话时长（秒）</th>
                    <th>近6月通话时长（秒）</th>
                </tr>
                </thead>
                <tbody>
                <tr class="center">
                    <td>信用卡类</td>
                    <td>${riskCheckItemMap.call_credit_card.callNum3m}</td>
                    <td>${riskCheckItemMap.call_credit_car.callNum6m}</td>
                    <td>${riskCheckItemMap.call_credit_card.callTime3m}</td>
                    <td>${riskCheckItemMap.call_credit_card.callTime6m}</td>
                </tr>
                <tr class="center">
                    <td>小额贷款类</td>
                    <td>${riskCheckItemMap.call_loan.callNum3m}</td>
                    <td>${riskCheckItemMap.call_loan.callNum6m}</td>
                    <td>${riskCheckItemMap.call_loan.callTime3m}</td>
                    <td>${riskCheckItemMap.call_loan.callTime6m}</td>
                </tr>
                <tr class="center">
                    <td>催收公司类</td>
                    <td>${riskCheckItemMap.call_collection.callNum3m}</td>
                    <td>${riskCheckItemMap.call_collection.callNum6m}</td>
                    <td>${riskCheckItemMap.call_collection.callTime3m}</td>
                    <td>${riskCheckItemMap.call_collection.callTime6m}</td>
                </tr>
                <tr class="center">
                    <td>110</td>
                    <td>${riskCheckItemMap.call_110.callNum3m}</td>
                    <td>${riskCheckItemMap.call_110.callNum6m}</td>
                    <td>${riskCheckItemMap.call_110.callTime3m}</td>
                    <td>${riskCheckItemMap.call_110.callTime6m}</td>
                </tr>
                <tr class="center">
                    <td>120</td>
                    <td>${riskCheckItemMap.call_120.callNum3m}</td>
                    <td>${riskCheckItemMap.call_120.callNum6m}</td>
                    <td>${riskCheckItemMap.call_120.callTime3m}</td>
                    <td>${riskCheckItemMap.call_120.callTime6m}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table6-->
    <div class="table">
        <h5 class="h5">1.6 活跃分析摘要</h5>
        <h6 class="sort">活跃识别</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近3月</th>
                    <th>近6月</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>通话活跃天数</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.callDay_3}</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.callDay_6}</td>
                </tr>
                <tr>
                    <td>主叫次数</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialNum_6}</td>
                </tr>
                <tr>
                    <td>被叫次数</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialedNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialedNum_6}</td>
                </tr>
                <tr>
                    <td>主叫时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialDuration_3}</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialDuration_6}</td>
                </tr>
                <tr>
                    <td>被叫时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialedDuration_3}</td>
                    <td class="center">${reportBasic.friendCircle.activeDegree.dialedDuration_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table7-->
    <div class="table">
        <h5 class="h5">1.7 消费分析摘要</h5>
        <h6 class="sort">消费识别</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近3月</th>
                    <th>近6月</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>消费总额（分）</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.totalFee_3}</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.totalFee_6}</td>
                </tr>
                <tr>
                    <td>充值次数</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.rechargeTimes_3}</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.rechargeTimes_6}</td>
                </tr>
                <tr>
                    <td>单次充值最大金额（分）</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.rechargeMaxAmount_3}</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.rechargeMaxAmount_6}</td>
                </tr>
                <tr>
                    <td>账单最新认证时间</td>
                    <td class="center">${reportBasic.friendCircle.consumptionAnalysis.billLatestCertificationDay}</td>
                    <td class="center">--</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">消费细类统计</h6>
        <table>
            <thead>
            <tr class="center">
                <th>消费类别</th>
                <th>近1月消费金额（分）</th>
                <th>近3月消费金额（分）</th>
                <th>近6月消费金额（分）</th>
                <th>近3月月均消费金额（分）</th>
                <th>近6月月均消费金额（分）</th>
            </tr>
            </thead>
            <tbody>
            <tr class="center">
                <td>流量</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.webFee_1}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.webFee_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.webFee_6}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.webFee_Avg_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.webFee_Avg_6}</td>
            </tr>
            <tr class="center">
                <td>短信</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.smsFee_1}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.smsFee_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.smsFee_6}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.smsFee_Avg_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.smsFee_Avg_6}</td>
            </tr>
            <tr class="center">
                <td>通话</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.voiceFee_1}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.voiceFee_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.voiceFee_6}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.voiceFee_Avg_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.voiceFee_Avg_6}</td>
            </tr>
            <tr class="center">
                <td>增值业务</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.vasFee_1}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.vasFee_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.vasFee_6}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.vasFee_Avg_3}</td>
                <td class="center">${reportBasic.friendCircle.consumptionAnalysis.vasFee_Avg_6}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="table">
        <h5 class="h5">1.8 漫游分析摘要(近6月漫游天数降序)</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <td>漫游地</td>
                    <td>近3月<br/>漫游天数</td>
                    <td>近6月<br/>漫游天数</td>
                    <td>近3月最大连续<br/>漫游天数</td>
                    <td>近6月最大连续<br/>漫游天数</td>
                    <td>近3月连续漫游<br/>1天以上的次数</td>
                    <td>近6月连续漫游<br/>1天以上的次数</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.roamAnalysis}" varStatus="status">
                        <tr class="center">
                            <td>${item.roamLocation}</td>
                            <td>${item.roamDayNum_3}</td>
                            <td>${item.roamDayNum_6}</td>
                            <td>${item.maxRoamDayNum_3}</td>
                            <td>${item.maxRoamDayNum_6}</td>
                            <td>${item.continueRoamNum_3}</td>
                            <td>${item.continueRoamNum_6}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table8-->
    <h3>2.通话社交</h3>
    <div class="table">
        <h5 class="h5">2.1 总体统计</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>近3月月均</th>
                    <th>近6月月均</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>通话总时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalTime_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalTime_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalTime_Avg_6}</td>
                </tr>
                <tr>
                    <td>通话号码数目</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalPeerNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalPeerNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalPeerNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalPeerNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalPeerNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>通话号码归属地总数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalCityNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalCityNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalCityNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalCityNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalCityNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>主叫次数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>被叫次数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>主叫号码数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialPeerNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialPeerNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialPeerNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialPeerNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialPeerNum_Avg_6}</td>
                </tr>
                <tr>
                    <td>被叫号码数</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedPeerNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedPeerNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedPeerNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedPeerNum_Avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callAnalysis.totalDialedPeerNum_Avg_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table9-->
    <div class="table">
        <h5 class="h5">2.2 详细统计</h5>
        <h6 style="clear:both" class="sort">联系人</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <td>号码</td>
                    <td>号码标识</td>
                    <td>号码类型</td>
                    <td>归属地</td>
                    <td>近一周通话次数</td>
                    <td>近一月通话次数</td>
                    <td>近三月通话次数</td>
                    <td>近六月通话次数</td>
                    <td>近三月通话时长（秒）</td>
                    <td>近六月通话时长（秒）</td>
                    <td>近三月主叫次数</td>
                    <td>近六月主叫次数</td>
                    <td>近三月被叫次数</td>
                    <td>近六月被叫次数</td>
                    <td>近三月早上（5:30-11:30）通话次数</td>
                    <td>近六月早上（5:30-11:30）通话次数</td>
                    <td>近三月中午（11:30-13:30）通话次数</td>
                    <td>近六月中午（11:30-13:30）通话次数</td>
                    <td>近三月下午（13:30-17:30）通话次数</td>
                    <td>近六月下午（13:30-17:30）通话次数</td>
                    <td>近三月晚上（17:30-23:30）通话次数</td>
                    <td>近六月晚上（17:30-23:30）通话次数</td>
                    <td>近三月凌晨（23:30-5:30）通话次数</td>
                    <td>近六月凌晨（23:30-5:30）通话次数</td>
                    <td>近三月工作日通话次数</td>
                    <td>近六月工作日通话次数</td>
                    <td>近三月周末通话次数</td>
                    <td>近六月周末通话次数</td>
                    <td>近三月节假日通话次数</td>
                    <td>近六月节假日通话次数</td>
                    <td>近三月是否有全天联系</td>
                    <td>近六月是否有全天联系</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callContactDetail}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${item.tag}</td>
                            <td>${item.category}</td>
                            <td>${item.city}</td>
                            <td>${item.callNum1Week}</td>
                            <td>${item.callNum1Month}</td>
                            <td>${item.callNum3Month}</td>
                            <td>${item.callNum6Month}</td>
                            <td>${item.callTime3Month}</td>
                            <td>${item.callTime6Month}</td>
                            <td>${item.dialNum3Month}</td>
                            <td>${item.dialNum6Month}</td>
                            <td>${item.dialedNum3Month}</td>
                            <td>${item.dialedNum6Month}</td>
                            <td>${item.callNumMorning3Month}</td>
                            <td>${item.callNumMorning6Month}</td>
                            <td>${item.callNumNoon3Month}</td>
                            <td>${item.callNumNoon6Month}</td>
                            <td>${item.callNumAfternoon3Month}</td>
                            <td>${item.callNumAfternoon6Month}</td>
                            <td>${item.callNumEvening3Month}</td>
                            <td>${item.callNumEvening6Month}</td>
                            <td>${item.callNumNight3Month}</td>
                            <td>${item.callNumNight6Month}</td>
                            <td>${item.callNumWeekday3Month}</td>
                            <td>${item.callNumWeekday6Month}</td>
                            <td>${item.callNumWeekend3Month}</td>
                            <td>${item.callNumWeekend6Month}</td>
                            <td>${item.callNumHoliday3Month}</td>
                            <td>${item.callNumHoliday6Month}</td>
                            <td>${item.callIfWholeDay3Month}</td>
                            <td>${item.callIfWholeDay6Month}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">通话时段（近三月）</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>通话时段</th>
                    <th>通话次数</th>
                    <th>通话号码数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                    <th>最后一次通话时间</th>
                    <th>第一次通话时间</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callDurationDetail3m}" varStatus="status">
                        <tr class="center">
                            <td>${item.timeStep}</td>
                            <td>${item.totalNum}</td>
                            <td>${item.peerNum}</td>
                            <td>${item.totalTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                            <td>${item.latestCall}</td>
                            <td>${item.farthestCall}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">通话时段（近六月）</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>通话时段</th>
                    <th>通话次数</th>
                    <th>通话号码数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                    <th>最后一次通话时间</th>
                    <th>第一次通话时间</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.callDurationDetail6m}" varStatus="status">
                        <tr class="center">
                            <td>${item.timeStep}</td>
                            <td>${item.totalNum}</td>
                            <td>${item.peerNum}</td>
                            <td>${item.totalTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                            <td>${item.latestCall}</td>
                            <td>${item.farthestCall}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort"> 联系人手机号码归属地 (近3月联系次数降序)</h6>
        <div class="tabbox">
            <table>
                <tbody>
                <tr>
                    <td>地址</td>
                    <td>通话次数</td>
                    <td>通话号码数</td>
                    <td>通话时长（秒）</td>
                    <td>主叫次数</td>
                    <td>被叫次数</td>
                    <td>主叫时长（秒）</td>
                    <td>被叫时长（秒）</td>
                    <td>平均主叫时长（秒）</td>
                    <td>平均被叫时长（秒）</td>
                    <td>主叫次数比重</td>
                    <td>被叫次数比重</td>
                    <td>主叫时长比重</td>
                    <td>被叫时长比重</td>
                </tr>
                <c:forEach var="item" items="${reportBasic.friendCircle.callLocationDetail3m}" varStatus="status">
                <tr>
                    <td class="center">${item.city}</td>
                    <td class="center">${item.totalNum}</td>
                    <td class="center">${item.peerNum}</td>
                    <td class="center">${item.totalTime}</td>
                    <td class="center">${item.dialNum}</td>
                    <td class="center">${item.dialedNum}</td>
                    <td class="center">${item.dialTime}</td>
                    <td class="center">${item.dialedTime}</td>
                    <td class="center">${item.dialAvgTime}</td>
                    <td class="center">${item.dialedAvgTime}</td>
                    <td class="center">${item.dialNumPercent}</td>
                    <td class="center">${item.dialedNumPercent}</td>
                    <td class="center">${item.dialTimePercent}</td>
                    <td class="center">${item.dialedTimePercent}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort"> 联系人手机号码归属地 (近6月联系次数降序)</h6>
        <div class="tabbox">
            <table>
                <tbody>
                <tr>
                    <td>地址</td>
                    <td>通话次数</td>
                    <td>通话号码数</td>
                    <td>通话时长（秒）</td>
                    <td>主叫次数</td>
                    <td>被叫次数</td>
                    <td>主叫时长（秒）</td>
                    <td>被叫时长（秒）</td>
                    <td>平均主叫时长（秒）</td>
                    <td>平均被叫时长（秒）</td>
                    <td>主叫次数比重</td>
                    <td>被叫次数比重</td>
                    <td>主叫时长比重</td>
                    <td>被叫时长比重</td>
                </tr>
                <c:forEach var="item" items="${reportBasic.friendCircle.callLocationDetail6m}" varStatus="status">
                <tr>
                    <td class="center">${item.city}</td>
                    <td class="center">${item.totalNum}</td>
                    <td class="center">${item.peerNum}</td>
                    <td class="center">${item.totalTime}</td>
                    <td class="center">${item.dialNum}</td>
                    <td class="center">${item.dialedNum}</td>
                    <td class="center">${item.dialTime}</td>
                    <td class="center">${item.dialedTime}</td>
                    <td class="center">${item.dialAvgTime}</td>
                    <td class="center">${item.dialedAvgTime}</td>
                    <td class="center">${item.dialNumPercent}</td>
                    <td class="center">${item.dialedNumPercent}</td>
                    <td class="center">${item.dialTimePercent}</td>
                    <td class="center">${item.dialedTimePercent}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <!--table10-->
    <div class="table">
        <h5 class="h5">2.3 通话时间详细统计</h5>
        <div class="tabbox">
            <table>
                <tbody>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>近3月月均</th>
                    <th>近6月月均</th>
                </tr>
                <tr>
                    <td>单次通话最长时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.maxTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.maxTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.maxTime_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>单次通话最短时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.minTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.minTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.minTime_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>时长在1min内的通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time1_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time1_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time1_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time1_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time1_avg_6}</td>
                </tr>
                <tr>
                    <td>时长在1min－5min内的通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time2_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time2_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time2_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time2_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time2_avg_6}</td>
                </tr>
                <tr>
                    <td>时长在5min－10min内的通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time3_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time3_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time3_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time3_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time3_avg_6}</td>
                </tr>
                <tr>
                    <td>时长在10min以上的通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time4_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time4_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time4_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time4_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.time4_avg_6}</td>
                </tr>
                <tr>
                    <td>白天(7:00-18:00)通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeTime_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeTime_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeTime_avg_6}</td>
                </tr>
                <tr>
                    <td>夜晚(18:00-7:00)通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightTime_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightTime_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightTime_avg_6}</td>
                </tr>
                <tr>
                    <td>本机号码归属地通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localTime_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localTime_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localTime_avg_6}</td>
                </tr>
                <tr>
                    <td>本机号码归属地以外通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteTime_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteTime_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteTime_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteTime_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteTime_avg_6}</td>
                </tr>
                <tr>
                    <td>白天(7:00-18:00)通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeNum_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.daytimeNum_avg_6}</td>
                </tr>
                <tr>
                    <td>夜晚(18:00-7:00)通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightNum_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.nightNum_avg_6}</td>
                </tr>
                <tr>
                    <td>本机号码归属地通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localNum_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.localNum_avg_6}</td>
                </tr>
                <tr>
                    <td>本机号码归属地以外通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteNum_1}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteNum_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteNum_6}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteNum_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callTimeDetail.remoteNum_avg_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table11-->
    <h3>3.风险状况</h3>
    <div class="table">
        <h5 class="h5">3.1 风险统计</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>近3月月均</th>
                    <th>近6月月均</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>与110通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf110_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf110_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf110_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf110_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf110_avg_6}</td>
                </tr>
                <tr>
                    <td>与110通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf110_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf110_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf110_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf110_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf110_avg_6}</td>
                </tr>
                <tr>
                    <td>与120通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf120_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf120_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf120_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf120_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOf120_avg_6}</td>
                </tr>
                <tr>
                    <td>与120通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf120_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf120_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf120_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf120_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOf120_avg_6}</td>
                </tr>
                <tr>
                    <td>与贷款公司通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLoanFirm_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLoanFirm_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLoanFirm_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLoanFirm_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLoanFirm_avg_6}</td>
                </tr>
                <tr>
                    <td>与贷款公司通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLoanFirm_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLoanFirm_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLoanFirm_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLoanFirm_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLoanFirm_avg_6}</td>
                </tr>
                <tr>
                    <td>与信用卡机构通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCreditCardCenter_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCreditCardCenter_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCreditCardCenter_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCreditCardCenter_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCreditCardCenter_avg_6}</td>
                </tr>
                <tr>
                    <td>与信用卡机构通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCreditCardCenter_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCreditCardCenter_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCreditCardCenter_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCreditCardCenter_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCreditCardCenter_avg_6}</td>
                </tr>
                <tr>
                    <td>与澳门地区通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfMacon_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfMacon_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfMacon_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfMacon_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfMacon_avg_6}</td>
                </tr>
                <tr>
                    <td>与澳门地区通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfMacon_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfMacon_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfMacon_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfMacon_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfMacon_avg_6}</td>
                </tr>
                <tr>
                    <td>与催收公司通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCollectionFirm_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCollectionFirm_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCollectionFirm_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCollectionFirm_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfCollectionFirm_avg_6}</td>
                </tr>
                <tr>
                    <td>与催收公司通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCollectionFirm_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCollectionFirm_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCollectionFirm_6}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCollectionFirm_avg_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfCollectionFirm_avg_6}</td>
                </tr>
                <tr>
                    <td>与律师通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLawyer_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLawyer_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfLawyer_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与律师通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLawyer_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLawyer_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfLawyer_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与中介通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfAgency_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfAgency_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfAgency_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与中介通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfAgency_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfAgency_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfAgency_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与诈骗类号码通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfFraud_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfFraud_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfFraud_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与诈骗类号码通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfFraud_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfFraud_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfFraud_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与骚扰类号码通话次数</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfNuisance_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfNuisance_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.numbersOfNuisance_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>与骚扰类号码通话时长（秒）</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfNuisance_1}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfNuisance_3}</td>
                    <td class="center">${reportBasic.friendCircle.callThirdPartDetail.timesOfNuisance_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table12-->
    <div class="table">
        <h5 class="h5">3.2 稳定性</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>是否亲情网用户</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMember_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMember_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMember_6}</td>
                </tr>
                <tr>
                    <td>是否亲情网户主</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMaster_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMaster_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isFamilyNetMaster_6}</td>

                </tr>
                <tr>
                    <td>连续充值月数</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.rechargeMonths_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.rechargeMonths_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.rechargeMonths_6}</td>
                </tr>
                <tr>
                    <td>常联系地址是否为手机归属地</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isAddressMatchPhone_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isAddressMatchPhone_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.isAddressMatchPhone_6}</td>
                </tr>
                <tr>
                    <td>通话次数 小于 使用月数＊1次</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallLess_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallLess_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallLess_6}</td>
                </tr>
                <tr>
                    <td>通话次数 大于 使用月数＊300次</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallMore_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallMore_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.numbersOfVoiceCallMore_6}</td>
                </tr>
                <tr>
                    <td>连续欠费月数</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.unpaidMonths_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.unpaidMonths_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.unpaidMonths_6}</td>
                </tr>
                <tr>
                    <td>本机号码归属地使用月数</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.liveMonths_1}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.liveMonths_3}</td>
                    <td class="center">${reportBasic.friendCircle.callFamilyDetail.liveMonths_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">常用服务</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>服务类型</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                </tr>
                </thead>
                <tbody>
                <tr class="center">
                    <td>与银行通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfBank_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfBank_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfBank_6}</td>
                </tr>
                <tr class="center">
                    <td>与银行通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfBank_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfBank_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfBank_6}</td>
                </tr>
                <tr class="center">
                    <td>与航旅机构通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfRailwayAirway_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfRailwayAirway_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfRailwayAirway_6}</td>
                </tr>
                <tr class="center">
                    <td>与航旅机构通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfRailwayAirway_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfRailwayAirway_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfRailwayAirway_6}</td>
                </tr>
                <tr class="center">
                    <td>与特种服务通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfSpecialService_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfSpecialService_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfSpecialService_6}</td>
                </tr>
                <tr class="center">
                    <td>与特种服务通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfSpecialService_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfSpecialService_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfSpecialService_6}</td>
                </tr>
                <tr class="center">
                    <td>与快递公司通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfExpress_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfExpress_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfExpress_6}</td>
                </tr>
                <tr class="center">
                    <td>与快递公司通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfExpress_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfExpress_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfExpress_6}</td>
                </tr>
                <tr class="center">
                    <td>与保险公司通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfInsFin_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfInsFin_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfInsFin_6}</td>
                </tr>
                <tr class="center">
                    <td>与保险公司通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfInsFin_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfInsFin_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfInsFin_6}</td>
                </tr>
                <tr class="center">
                    <td>与汽车公司通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarFirm_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarFirm_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarFirm_6}</td>
                </tr>
                <tr class="center">
                    <td>与汽车公司通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarFirm_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarFirm_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarFirm_6}</td>
                </tr>
                <tr class="center">
                    <td>与通信服务机构通话次数</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarrier_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarrier_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.numbersOfCarrier_6}</td>
                </tr>
                <tr class="center">
                    <td>与通信服务机构通话时长（秒）</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarrier_1}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarrier_3}</td>
                    <td>${reportBasic.friendCircle.callThirdPartDetail.timesOfCarrier_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="table">
        <h6 class="sort">与服务号通话详情 (按月统计)</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <th>服务号码</th>
                    <th>月份</th>
                    <th>通话次数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                    <th>主叫时长（秒）</th>
                    <th>被叫时长（秒）</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.callServiceDetail}" varStatus="status">
                        <tr class="center">
                            <td>${item.servicePhone}</td>
                            <td>${item.months}</td>
                            <td>${item.totalNum}</td>
                            <td>${item.totalTime}</td>
                            <td>${item.dialNum}</td>
                            <td>${item.dialedNum}</td>
                            <td>${item.dialTime}</td>
                            <td>${item.dialedTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table13-->
    <h3>4.活跃程度</h3>
    <div class="table">
        <h5 class="h5">4.1 通话活跃</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>近3月月均</th>
                    <th>近6月月均</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>通话活跃天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.voiceCallDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.voiceCallDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.voiceCallDay_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.voiceCallDay_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.voiceCallDay_avg_6}</td>
                </tr>
                <tr>
                    <td>短信活跃天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageDay_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageDay_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageDay_avg_6}</td>
                </tr>
                <tr>
                    <td>充值次数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.rechargeTimes_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.rechargeTimes_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.rechargeTimes_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.rechargeTimes_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.rechargeTimes_avg_6}</td>
                </tr>
                <tr>
                    <td>通话次数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callTimes_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callTimes_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callTimes_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callTimes_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callTimes_avg_6}</td>
                </tr>
                <tr>
                    <td>短信次数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageTimes_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageTimes_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageTimes_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageTimes_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.shortMessageTimes_avg_6}</td>
                </tr>
                <tr>
                    <td>流量套餐总量（KB）</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageTotal_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageTotal_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageTotal_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageTotal_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageTotal_avg_6}</td>
                </tr>
                <tr>
                    <td>流量套餐使用量（KB）</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageUsage_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageUsage_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageUsage_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageUsage_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.packageUsage_avg_6}</td>
                </tr>
                <tr>
                    <td>均次通话时长（秒）</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callAvgDuration_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callAvgDuration_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.callAvgDuration_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>无呼出的天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDay_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDay_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDay_avg_6}</td>
                </tr>
                <tr>
                    <td>无呼出天数占比</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDayPercent_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDayPercent_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noDialDayPercent_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>无通话天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDay_6}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDay_avg_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDay_avg_6}</td>
                </tr>
                <tr>
                    <td>无通话天数占比</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDayPercent_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDayPercent_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.noCallDayPercent_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>最大连续开机天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOnMaxDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOnMaxDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOnMaxDay_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>关机天数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDay_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDay_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDay_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>关机天数占比</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDayPercent_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDayPercent_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.powerOffDayPercent_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                <tr>
                    <td>连续3天以上关机次数</td>
                    <td class="center">${reportBasic.activeDegreeDetail.continuePowerOffNum_1}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.continuePowerOffNum_3}</td>
                    <td class="center">${reportBasic.activeDegreeDetail.continuePowerOffNum_6}</td>
                    <td class="center">--</td>
                    <td class="center">--</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table13-->
    <h3>5.消费情况</h3>
    <div class="table">
        <h5 class="h5">5.1 消费统计</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr>
                    <th class="left">魔蝎变量</th>
                    <th>近1月</th>
                    <th>近3月</th>
                    <th>近6月</th>
                    <th>近3月月均</th>
                    <th>近6月月均</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>消费总金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.totalFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.totalFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.totalFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.totalFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.totalFee_avg_6}</td>
                </tr>
                <tr>
                    <td>网络流量消费金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.webFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.webFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.webFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.webFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.webFee_avg_6}</td>
                </tr>
                <tr>
                    <td>通话消费金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.voiceFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.voiceFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.voiceFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.voiceFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.voiceFee_avg_6}</td>
                </tr>
                <tr>
                    <td>短信消费金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.smsFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.smsFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.smsFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.smsFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.smsFee_avg_6}</td>
                </tr>
                <tr>
                    <td>增值业务消费金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.vasFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.vasFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.vasFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.vasFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.vasFee_avg_6}</td>
                </tr>
                <tr>
                    <td>其它消费金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.extraFee_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.extraFee_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.extraFee_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.extraFee_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.extraFee_avg_6}</td>
                </tr>
                <tr>
                    <td>充值金额（分）</td>
                    <td class="center">${reportBasic.consumptionDetail.rechargeAmount_1}</td>
                    <td class="center">${reportBasic.consumptionDetail.rechargeAmount_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.rechargeAmount_6}</td>
                    <td class="center">${reportBasic.consumptionDetail.rechargeAmount_avg_3}</td>
                    <td class="center">${reportBasic.consumptionDetail.rechargeAmount_avg_6}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--table14-->
    <h3>5.漫游详情统计</h3>
    <div class="table">
        <h5 class="h5">5.1 漫游详情统计(近6月漫游日期降序)</h5>
        <div class="tabbox">
            <table>
                <tbody>
                <tr>
                    <td>漫游日期</td>
                    <td>漫游城市</td>
                </tr>
                <c:forEach var="item" items="${reportBasic.roamDetail}" varStatus="status">
                <tr>
                    <td class="center">${item.roamDay}</td>
                    <td class="center">${item.roamLocation}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script>
    function openQuery() {
        $('#queryModal').modal('toggle');
    }

    function onclickQuery() {
        var name1 = $('#name1').val();
        var phone1 = $('#phone1').val();
        var name2 = $('#name2').val();
        var phone2 = $('#phone2').val();
        var name3 = $('#name3').val();
        var phone3 = $('#phone3').val();

        var retflag = true;
        var flag1 = false;
        var flag2 = false;
        var flag3 = false;
        var contact = '';

        if (name1.length > 0  && phone1.length > 0) {
            if (valverifyphone(phone1)) {
                contact = phone1 + ':' + name1 ;
                flag1 = true;
            } else {
                alert("请输入正确的手机号");
                retflag=false;
            }

        }
        if (name2.length > 0  && phone2.length > 0) {
            if (valverifyphone(phone2)) {
                if (flag1) {
                    contact = contact + ',' + phone2 + ':' + name2 ;
                } else {
                    contact = phone2 + ':' + name2 ;
                    flag2 = true;
                }
            } else {
                alert("请输入正确的手机号");
                retflag=false;
            }

        }

        if (name3.length > 0  && phone3.length>0) {
            if(valverifyphone(phone3)){
                if (flag1) {
                    contact = contact + ',' + phone3 + ':' + name3 ;
                } else if (flag2) {
                    contact = contact + ',' + phone3 + ':' + name3 ;
                } else {
                    contact = phone3 + ':' + name3 ;
                }
            }else {
                alert("请输入正确的手机号");
                retflag=false;
            }
        }

        if (retflag) {
            $('#contact').val(contact);
            return true;
        } else {
            return false;
        }

    }


    function valverifyphone(phoneNum) {
        var reg = /^1[3|4|5|7|8][0-9]{9}$/;
        var flag = reg.test(phoneNum); //true
        return flag;
    }
</script>
</body>
</html>
