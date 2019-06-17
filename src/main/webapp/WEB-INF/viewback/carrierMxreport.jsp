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

    .userinfocheck{
        white-space: normal;
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

        background: rgb(70, 140, 180)!important;
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
        background: rgb(200, 200, 200);
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
            报告时间：${reportBasic.userBasicInfo.updateTime}&nbsp;&nbsp;&nbsp;&nbsp;报告编号：${reportBasic.userBasicInfo.taskId}
        </div>
    </div>
    <div class="table">
        <h5 class="h5">用户基本信息</h5>
        <div class="tabbox">
            <table>
                <tbody id="searchResultTable">
                <tr>
                    <td>姓名：${reportBasic.userBasicInfo.name}</td>
                    <td colspan="3">身份证号：${reportBasic.userBasicInfo.idCard}</td>
                </tr>
                <tr>
                    <td>姓名From客户：${reportBasic.userBasicInfo.carrierName}</td>
                    <td colspan="3">身份证号From客户：${reportBasic.userBasicInfo.carrierIdcard}</td>
                </tr>
                <tr>
                    <td>手机号码：${reportBasic.userBasicInfo.mobile}</td>
                    <td>入网时长：${reportBasic.userBasicInfo.inTime}月</td>
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
                    <td>手机号码归属地：${reportBasic.userBasicInfo.phoneAttribution}</td>
                    <td>居住地址：${reportBasic.userBasicInfo.liveAddress}</td>
                    <td>工作地址：${reportBasic.userBasicInfo.liveAddress}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="table">
        <div class="tabbox">
            <h6 class="sort">用户信息检测(联系人数据)</h6><table>
            <tbody>
            <tr class="center ">
                <td rowspan="9" style="text-align: center;vertical-align: middle;width: 15%">
                    用户查询信息
                </td>
                <td style="width: 25%">查询过该用户的相关企业数量</td>
                <td>${reportBasic.mxUserInfoCheck.checkSearchInfo.searchedOrgCnt}</td>
            </tr>
            <tr class="center">
                <td>查询过该用户的相关企业类型</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.searchedOrgType}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            <tr class="center">
                <td>身份证组合过的其他姓名</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.idcardWithOtherNames}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            <tr class="center">
                <td>身份证组合过其他电话</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.idcardWithOtherPhones}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            <tr class="center">
                <td>电话号码组合过其他姓名</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.phoneWithOtherNames}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            <tr class="center">
                <td>电话号码组合过其他身份证</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.phoneWithOtherIdcards}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            <tr class="center">
                <td>电话号码注册过的相关企业数量</td>
                <td>${reportBasic.mxUserInfoCheck.checkSearchInfo.registerOrgCnt}</td>
            </tr>
            <tr class="center">
                <td>电话号码注册过的相关企业类型</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.registerOrgType}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>

            </tr>
            <tr class="center">
                <td>电话号码出现过的公开信息网站</td>
                <td>
                    <c:forEach var="item" items="${reportBasic.mxUserInfoCheck.checkSearchInfo.arisedOpenWeb}" varStatus="status">
                        ${item}
                    </c:forEach>
                </td>
            </tr>
            </tbody>
        </table>
        </div>
    </div>

    <!-- 用户信息检测  关注名单(Ⅱ类)-->
    <div class="table">
        <div class="tabbox">
            <h6 class="sort">用户信息检测(关注名单(Ⅱ类))</h6><table>
            <tbody>
            <tr class="center ">
                <td rowspan="6" class="hideborder" style="text-align: center;vertical-align: middle;width: 15%">
                    关注名单(Ⅱ类)信息
                </td>
                <td style="width: 25%">关注名单综合评分</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.phoneGrayScore}
                    (分数范围0-100，40分以下为高危人群）
                </td>
            </tr>
            <tr class="center">
                <td>直接联系人中关注名单(Ⅱ类)人数</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.contactsClass1BlacklistCnt}
                    (直接联系人：和被查询号码有通话记录)
                </td>
            </tr>
            <tr class="center">
                <td>间接联系人中关注名单(Ⅱ类)人数</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.contactsClass2BlacklistCnt}
                    (和被查询人的直接联系人有通话记录)
                </td>
            </tr>
            <tr class="center">
                <td>直接联系人人数</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.contactsClass1Cnt} (直接联系人：和被查询号码有通话记录)</td>
            </tr>
            <tr class="center">
                <td>引起关注名单(Ⅱ类)的直接联系人数量</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.contactsRouterCnt}
                    (直接联系人有和关注名单(Ⅱ类)用户的通讯记录的号码数量)
                </td>
            </tr>
            <tr class="center">
                <td>直接联系人中引起间接关注名单(Ⅱ类)占比</td>
                <td>${reportBasic.mxUserInfoCheck.checkBlackInfo.contactsRouterRatio}
                    (直接联系人有和关注名单(Ⅱ类)用户的通讯记录的号码数量在直接联系人数量中的百分比)
                </td>
            </tr>
            </tbody>
        </table>
        </div>
    </div>

    <!--table2-->
    <div class="table">
        <h5 class="h5">数据来源</h5>
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
                    <td>${reportBasic.userBasicInfo.sourceNameZh}</td>
                    <td>${reportBasic.userBasicInfo.dataType}</td>
                    <td>${reportBasic.userBasicInfo.dataGainTime}</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
    <!--table3-->
    <div class="table">
        <h5 class="h5">信息校验</h5>
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
        <h5 class="h5">社交分析摘要</h5>
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
                    <td class="center">近3/6月互有主叫和被叫的联系人电话号码数目（去重）</td>
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
                    <th>在通讯录中姓名</th>
                    <th>归属地</th>
                    <th>通话次数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.peerNumTop33m}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${userContactsMap[item.peerNumber]}</td>
                            <td>${item.peerNumLoc}</td>
                            <td>${item.callCnt}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialCnt}</td>
                            <td>${item.dialedCnt}</td>
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
                    <th>在通讯录中姓名</th>
                    <th>归属地</th>
                    <th>通话次数</th>
                    <th>通话时长（秒）</th>
                    <th>主叫次数</th>
                    <th>被叫次数</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${reportBasic.friendCircle.peerNumTop36m}" varStatus="status">
                        <tr class="center">
                            <td>${item.peerNumber}</td>
                            <td>${userContactsMap[item.peerNumber]}</td>
                            <td>${item.peerNumLoc}</td>
                            <td>${item.callCnt}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialCnt}</td>
                            <td>${item.dialedCnt}</td>
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
                    <c:forEach var="item" items="${reportBasic.friendCircle.locationTop33m}" varStatus="status">
                        <tr class="center">
                            <td>${item.location}</td>
                            <td>${item.callCnt}</td>
                            <td>${item.peerNumberCnt}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialCnt}</td>
                            <td>${item.dialedCnt}</td>
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
                    <c:forEach var="item" items="${reportBasic.friendCircle.locationTop36m}" varStatus="status">
                        <tr class="center">
                            <td>${item.location}</td>
                            <td>${item.callCnt}</td>
                            <td>${item.peerNumberCnt}</td>
                            <td>${item.callTime}</td>
                            <td>${item.dialCnt}</td>
                            <td>${item.dialedCnt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%--<div class="table">
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
    </div>--%>
    <!--table5-->
    <div class="table">
        <h5 class="h5">风险分析摘要</h5>
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
                    <td class="center">${basicInfoCheckItemMap.mobile_silence_3m}</td>
                    <td class="center">${basicInfoCheckItemMap.mobile_silence_6m}</td>
                    <td class="center">满分10分</td>
                </tr>
                <tr>
                    <td>欠费风险度</td>
                    <td class="center">${basicInfoCheckItemMap.arrearage_risk_3m}</td>
                    <td class="center">${basicInfoCheckItemMap.arrearage_risk_6m}</td>
                    <td class="center">满分10分</td>
                </tr>
                <tr>
                    <td>亲情网风险度</td>
                    <td class="center">${basicInfoCheckItemMap.binding_risk}</td>
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
                    <td class="center">${basicInfoCheckItemMap.is_name_and_idcard_in_court_black}</td>
                </tr>
                <tr>
                    <td>申请人姓名+身份证号码是否出现在金融机构黑名单</td>
                    <td class="center">${basicInfoCheckItemMap.is_name_and_idcard_in_finance_black}</td>
                </tr>
                <tr>
                    <td>申请人姓名+手机号码是否出现在金融机构黑名单</td>
                    <td class="center">${basicInfoCheckItemMap.is_name_and_mobile_in_finance_black}</td>
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
                    <c:forEach var="item" items="${reportBasic.callRiskAnalysis}" varStatus="status">
                        <tr class="center">
                            <td>${item.analysisDesc}</td>
                            <td>${item.analysisPoint.callCnt3Month}</td>
                            <td>${item.analysisPoint.callCnt6Month}</td>
                            <td>${item.analysisPoint.callTime3Month}</td>
                            <td>${item.analysisPoint.callTime6Month}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table6-->
    <div class="table">
        <h5 class="h5">活跃分析摘要</h5>
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
                <c:forEach var="item" items="${reportBasic.friendCircle.activeDegree}" varStatus="status">
                    <tr>
                        <td>${item.appPointZh}</td>
                        <td class="center">${item.item.item3Month}</td>
                        <td class="center">${item.item.item6Month}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table7-->
    <div class="table">
        <h5 class="h5">消费分析摘要</h5>
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
                <c:forEach var="item" items="${reportBasic.friendCircle.consumptionDetail}" varStatus="status">
                    <tr>
                        <td>${item.appPointZh}</td>
                        <td class="center">${item.item.item3Month}</td>
                        <td class="center">${item.item.item6Month}</td>
                    </tr>
                </c:forEach>
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
            <c:forEach var="item" items="${reportBasic.friendCircle.consumptionDetail}" varStatus="status">
                <tr>
                    <td>${item.appPointZh}</td>
                    <td class="center">${item.item.item1Month}</td>
                    <td class="center">${item.item.item3Month}</td>
                    <td class="center">${item.item.item6Month}</td>
                    <td class="center">${item.item.avgItem3Month}</td>
                    <td class="center">${item.item.avgItem6Month}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="table">
        <h6 class="sort"> 运营商消费数据</h6>
        <table>
            <thead>
            <tr class="center">
                <td>运营商</td>
                <td>号码</td>
                <td>归属地</td>
                <td>月份</td>
                <td>呼叫次数</td>
                <td>主叫次数</td>
                <td>主叫时间(秒)</td>
                <td>被叫次数</td>
                <td>被叫时间(秒)</td>
                <td>短信数量</td>
                <td>话费消费(分)</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${reportBasic.cellBehavior}" varStatus="status">
                <c:forEach var="item1" items="${item.behavior}" varStatus="status1">
                    <tr>
                        <td class="userinfocheck">${item1.cellOperatorZh}</td>
                        <td class="userinfocheck">${item.phoneNum}</td>
                        <td class="userinfocheck">${item1.cellLoc}</td>
                        <td class="userinfocheck">${item1.cellMth}</td>
                        <td class="userinfocheck">${item1.callCnt}</td>
                        <td class="userinfocheck">${item1.dialCnt}</td>
                        <td class="userinfocheck">${item1.dialTime}</td>
                        <td class="userinfocheck">${item1.dialedCnt}</td>
                        <td class="userinfocheck">${item1.dialedTime}</td>
                        <td class="userinfocheck">${item1.smsCnt}</td>
                        <td class="userinfocheck">${item1.totalAmount}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="table">
        <h5 class="h5">漫游分析摘要(近6月漫游天数降序)</h5>
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
                            <td>${item.roamDayCnt_3}</td>
                            <td>${item.roamDayCnt_6}</td>
                            <td>${item.maxRoamDayCnt_3}</td>
                            <td>${item.maxRoamDayCnt_6}</td>
                            <td>${item.continueRoamCnt_3}</td>
                            <td>${item.continueRoamCnt_6}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table8-->
    <h3>通话社交</h3>
    <div class="table">
        <h5 class="h5">总体统计</h5>
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
                <c:forEach var="item" items="${reportBasic.friendCircle.activeDegree}" varStatus="status">
                    <tr>
                        <td>${item.appPointZh}</td>
                        <td class="center">${item.item.item1Month}</td>
                        <td class="center">${item.item.item3Month}</td>
                        <td class="center">${item.item.item6Month}</td>
                        <td class="center">${item.item.avgItem3Month}</td>
                        <td class="center">${item.item.avgItem6Month}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table9-->
    <div class="table">
        <h5 class="h5">详细统计</h5>
        <h6 style="clear:both" class="sort">联系人</h6>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center">
                    <td>号码</td>
                    <td>在通讯录中姓名</td>
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
                            <td>${userContactsMap[item.peerNumber]}
                                <%--<c:choose>
                                    <c:when test="${empty userContactsMap[item.peerNumber]}">未知</c:when>
                                    <c:otherwise>${userContactsMap[item.peerNumber]}</c:otherwise>
                                </c:choose>--%>
                            </td>
                            <td>${item.companyName}</td>
                            <td>${item.groupName}</td>
                            <td>${item.city}</td>
                            <td>${item.callCnt1Week}</td>
                            <td>${item.callCnt1Month}</td>
                            <td>${item.callCnt3Month}</td>
                            <td>${item.callCnt6Month}</td>
                            <td>${item.callTime3Month}</td>
                            <td>${item.callTime6Month}</td>
                            <td>${item.dialCnt3Month}</td>
                            <td>${item.dialCnt6Month}</td>
                            <td>${item.dialedCnt3Month}</td>
                            <td>${item.dialedCnt6Month}</td>
                            <td>${item.callCntMorning3Month}</td>
                            <td>${item.callCntMorning6Month}</td>
                            <td>${item.callCntNoon3Month}</td>
                            <td>${item.callCntNoon6Month}</td>
                            <td>${item.callCntAfternoon3Month}</td>
                            <td>${item.callCntAfternoon6Month}</td>
                            <td>${item.callCntEvening3Month}</td>
                            <td>${item.callCntEvening6Month}</td>
                            <td>${item.callCntNight3Month}</td>
                            <td>${item.callCntNight6Month}</td>
                            <td>${item.callCntWeekday3Month}</td>
                            <td>${item.callCntWeekday6Month}</td>
                            <td>${item.callCntWeekend3Month}</td>
                            <td>${item.callCntWeekend6Month}</td>
                            <td>${item.callCntHoliday3Month}</td>
                            <td>${item.callCntHoliday6Month}</td>
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
                <c:forEach var="item" items="${reportBasic.friendCircle['call_duration_detail_3m']}" varStatus="status">
                    <tr class="center">
                        <td>${item.timeStepZh}</td>
                        <td>${item.item.totalCnt}</td>
                        <td>${item.item.uniqNumCnt}</td>
                        <td>${item.item.totalTime}</td>
                        <td>${item.item.dialCnt}</td>
                        <td>${item.item.dialedCnt}</td>
                        <td>${item.item.latestCallTime}</td>
                        <td>${item.item.farthestCallTime}</td>
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
                <c:forEach var="item" items="${reportBasic.friendCircle['call_duration_detail_6m']}" varStatus="status">
                    <tr class="center">
                        <td>${item.timeStepZh}</td>
                        <td>${item.item.totalCnt}</td>
                        <td>${item.item.uniqNumCnt}</td>
                        <td>${item.item.totalTime}</td>
                        <td>${item.item.dialCnt}</td>
                        <td>${item.item.dialedCnt}</td>
                        <td>${item.item.latestCallTime}</td>
                        <td>${item.item.farthestCallTime}</td>
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
                <c:forEach var="item" items="${reportBasic.friendCircle['contact_region_3m']}" varStatus="status">
                <tr>
                    <td class="center">${item.regionLoc}</td>
                    <td class="center">${item.regionCallCnt}</td>
                    <td class="center">${item.regionUniqNumCnt}</td>
                    <td class="center">${item.regionCallTime}</td>
                    <td class="center">${item.regionDialCnt}</td>
                    <td class="center">${item.regionDialedCnt}</td>
                    <td class="center">${item.regionDialTime}</td>
                    <td class="center">${item.regionDialedTime}</td>
                    <td class="center">${item.regionAvgDialTime}</td>
                    <td class="center">${item.regionAvgDialedTime}</td>
                    <td class="center">${item.regionDialCntPct}</td>
                    <td class="center">${item.regionDialedCntPct}</td>
                    <td class="center">${item.regionDialTimePct}</td>
                    <td class="center">${item.regionDialedTimePct}</td>
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
                <c:forEach var="item" items="${reportBasic.friendCircle['contact_region_6m']}" varStatus="status">
                <tr>
                    <td class="center">${item.regionLoc}</td>
                    <td class="center">${item.regionCallCnt}</td>
                    <td class="center">${item.regionUniqNumCnt}</td>
                    <td class="center">${item.regionCallTime}</td>
                    <td class="center">${item.regionDialCnt}</td>
                    <td class="center">${item.regionDialedCnt}</td>
                    <td class="center">${item.regionDialTime}</td>
                    <td class="center">${item.regionDialedTime}</td>
                    <td class="center">${item.regionAvgDialTime}</td>
                    <td class="center">${item.regionAvgDialedTime}</td>
                    <td class="center">${item.regionDialCntPct}</td>
                    <td class="center">${item.regionDialedCntPct}</td>
                    <td class="center">${item.regionDialTimePct}</td>
                    <td class="center">${item.regionDialedTimePct}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <!--table10-->
    <div class="table">
        <h5 class="h5">通话时间详细统计</h5>
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
                <c:forEach var="item" items="${reportBasic.friendCircle.callTimeDetail}" varStatus="status">
                    <tr>
                        <td>${item.appPointZh}</td>
                        <td class="center">${item.item.item1Month}</td>
                        <td class="center">${item.item.item3Month}</td>
                        <td class="center">${item.item.item6Month}</td>
                        <td class="center">${item.item.avgItem3Month}</td>
                        <td class="center">${item.item.avgItem6Month}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table11-->
    <h3>风险状况</h3>
    <div class="table">
        <h5 class="h5">风险统计</h5>
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
                <c:forEach var="item" items="${reportBasic.callRiskAnalysis}" varStatus="status">
                    <tr class="center">
                        <td>与${item.analysisDesc}通话次数</td>
                        <td>${item.analysisPoint.callCnt1Month}</td>
                        <td>${item.analysisPoint.callCnt3Month}</td>
                        <td>${item.analysisPoint.callCnt6Month}</td>
                        <td>${item.analysisPoint.avgCallCnt3Month}</td>
                        <td>${item.analysisPoint.avgCallCnt6Month}</td>
                    </tr>
                    <tr class="center">
                        <td>与${item.analysisDesc}通话时长（秒）</td>
                        <td>${item.analysisPoint.callTime1Month}</td>
                        <td>${item.analysisPoint.callTime3Month}</td>
                        <td>${item.analysisPoint.callTime6Month}</td>
                        <td>${item.analysisPoint.avgCallTime3Month}</td>
                        <td>${item.analysisPoint.avgCallTime6Month}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table12-->
    <div class="table">
        <h5 class="h5">稳定性</h5>
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
                <c:forEach var="item" items="${reportBasic.callFamilyDetail}" varStatus="status">
                    <tr>
                        <td>${item.appPointZh}</td>
                        <td class="center">${item.item.item1Month}</td>
                        <td class="center">${item.item.item3Month}</td>
                        <td class="center">${item.item.item6Month}</td>
                    </tr>
                </c:forEach>
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
                <c:forEach var="item" items="${reportBasic.friendCircle.callServiceAnalysis}" varStatus="status">
                    <tr class="center">
                        <td>与${item.analysisDesc}通话次数</td>
                        <td>${item.analysisPoint.callCnt1Month}</td>
                        <td>${item.analysisPoint.callCnt3Month}</td>
                        <td>${item.analysisPoint.callCnt6Month}</td>
                    </tr>
                    <tr class="center">
                        <td>与${item.analysisDesc}通话时长（秒）</td>
                        <td>${item.analysisPoint.callTime1Month}</td>
                        <td>${item.analysisPoint.callTime3Month}</td>
                        <td>${item.analysisPoint.callTime6Month}</td>
                    </tr>
                </c:forEach>
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
                    <th>公司名称</th>
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
                    <c:forEach var="item" items="${reportBasic.friendCircle.mainService}" varStatus="status">
                        <c:forEach var="item1" items="${item.serviceDetails}" varStatus="status">
                            <tr class="center">
                                <td>${item.serviceNum}</td>
                                <td>${item.companyName}</td>
                                <td>${item1.interactMth}</td>
                                <td>${item1.interactCnt}</td>
                                <td>${item1.interactTime}</td>
                                <td>${item1.dialCnt}</td>
                                <td>${item1.dialedCnt}</td>
                                <td>${item1.dialTime}</td>
                                <td>${item1.dialedTime}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!--table13-->
    <%--<h3>4.活跃程度</h3>
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
    </div>--%>
    <!--table13-->
    <%--<h3>5.消费情况</h3>
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
    </div>--%>
    <!--table14-->
    <h3>漫游详情统计</h3>
    <div class="table">
        <h5 class="h5">漫游详情统计(近6月漫游日期降序)</h5>
        <div class="tabbox">
            <table>
                <tbody>
                <tr>
                    <td>漫游日期</td>
                    <td>漫游城市</td>
                </tr>
                <c:forEach var="item" items="${reportBasic.friendCircle.roamDetail}" varStatus="status">
                <tr>
                    <td class="center">${item.roamDay}</td>
                    <td class="center">${item.roamLocation}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <h3>运营商出行分析</h3>
    <div class="table">
        <h5 class="h5">出行数据分析</h5>
        <div class="tabbox">
            <table>
                <thead>
                <tr class="center hideborder" style="text-align: center">
                    <td>出发地</td>
                    <td>目的地</td>
                    <td>出发时间</td>
                    <td>结束时间</td>
                    <td>类型</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${reportBasic.friendCircle.tripInfo}" varStatus="status">
                    <tr class="center">
                        <td>${item.tripLeave}</td>
                        <td>${item.tripDest}</td>
                        <td>${item.tripStartTime}</td>
                        <td>${item.tripEndTime}</td>
                        <td>${item.tripType}</td>
                    </tr>
                </c:forEach>
                </tbody>
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
