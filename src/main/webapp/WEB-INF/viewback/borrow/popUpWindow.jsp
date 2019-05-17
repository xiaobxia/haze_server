<%--
  Created by IntelliJ IDEA.
  User: tql
  Date: 2017/12/22
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--身份证正反面大图预览弹窗--%>
<div class="view-larger pre-view-l">
    <div class="overlay">
        <div class="o-header">
            <p class="fl count-status">0/0</p>
            <a href="javascript:void(0);" class="close fr">×</a>
            <div class="clear"></div>
        </div>
        <div class="o-middle">
            <img src="http://finance.tan66.com/static/default/img/jiaxi/tu_bg_l.png" class="left" alt="上一张" />
            <img src="http://finance.tan66.com/static/default/img/jiaxi/tu_bg_r.png" class="right" alt="下一张" />
        </div>
        <div class="img-group">
            <img src="http://finance.tan66.com/static/default/temp/crowd-product.jpg" alt="">
        </div>
    </div>
</div>

<%--new同盾反欺诈--%>
<section class="new-popup" id="new-tdpop" style="display: none;">
    <div class="popContent contact-content" style="transform: translate(0,-50%);">
        <a id="bb" href="javascript:;" class="closeBtn"> <span>同盾数据分析</span> × </a>
        <div id="aa" class="common-popContent">
            <table border="1">
                <c:choose>
                    <c:when test="${TDModel eq 'null' or empty TDModel}">
                        <tr><td style="width: 398px;">同盾反欺诈：</td><td>暂无数据</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${TDModel}" var="map">
                            <c:choose>
                                <c:when test="${map.key == 'checkResult'}">
                                    <tr><td>同盾建议：</td><td>${map.value}</td></tr>
                                </c:when>
                                <c:when test="${map.key == '同盾分'}">
                                    <tr><td>${map.key}</td><td>${map.value}</td></tr>
                                </c:when>
                                <c:when test="${map.key eq '7天内申请人在多个平台申请借款' or map.key eq '1个月内申请人在多个平台申请借款' or map.key eq '3个月内申请人在多个平台申请借款' or map.key eq '3个月内申请人在多个平台被放款_不包含本合作方'}">
                                    <c:if test="${fn:length(TDModel[map.key]) > 0}">
                                        <tr>
                                            <td rowspan="${fn:length(TDModel[map.key])+1}">${map.key}</td>
                                        </tr>
                                        <c:forEach items="${TDModel[map.key]}" var="in">
                                            <tr>
                                                <td>${in.key } 申请 （${in.value}）</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </c:when>
                                <c:when test="${map.key eq 'tdFqzList'}">
                                    <c:if test="${fn:length(TDModel[map.key]) > 0 and !empty TDModel[map.key][0]}">
                                        <tr>
                                            <td rowspan="${fn:length(TDModel[map.key])+1}">同盾反欺诈列表</td>
                                        </tr>
                                        <c:forEach items="${TDModel[map.key]}" var="in">
                                            <tr>
                                                <c:if test="${in ne 'auto' and in ne '同盾报文建议'}">
                                                    <td>
                                                            ${in}
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</section>
<%--new白骑士--%>
<section class="new-popup" id="new-bqspop" style="display: none;">
    <div class="popContent contact-content">
        <a id="dd" href="javascript:;" class="closeBtn"> <span>白骑士数据分析</span> × </a>
        <div id="ee" class="common-popContent">
            <table border="1">
                <c:choose>
                    <c:when test="${BQSModel eq 'null' or empty BQSModel or fn:length(BQSModel['detailMap']) < 0}">
                        <tr><td style="width: 398px;">白骑士反欺诈：</td><td>暂无数据</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${fn:length(BQSModel['detailMap']) > 0}" >
                            <c:forEach items="${BQSModel['detailMap']}" var="out">
                                <tr>
                                    <td style="width: 115px;" rowspan="${fn:length(BQSModel['detailMap'][out.key]) + 1}">${out.key}</td>
                                </tr>
                                <c:forEach items="${BQSModel['detailMap'][out.key]}" var="map">
                                    <tr>
                                        <td>${map.key}</td>
                                        <td>${map.value}</td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</section>
<%--new中智诚反欺诈--%>
<section class="new-popup" id="new-zzcfqz" style="display: none;">
    <div class="popContent contact-content">
        <a id="ss" href="javascript:;" class="closeBtn"> <span>中智诚反欺诈数据分析</span> × </a>
        <div id="ww" class="common-popContent">
            <table border="1">
                <c:choose>
                    <c:when test="${ZZCFQZModel eq 'null' or empty ZZCFQZModel}">
                        <tr><td style="width: 398px;">中智诚反欺诈：</td><td>暂无数据</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${fn:length(ZZCFQZModel['riskList']) > 0}">
                                <c:forEach var="in" items="${ZZCFQZModel['riskList']}">
                                    <tr><td>${in }</td></tr>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</section>
<%--new中智诚 黑名单--%>
<section class="new-popup" id="new-zzcblack" style="display: none;">
    <div class="popContent contact-content">
        <a id="xx" href="javascript:;" class="closeBtn"> <span>中智诚黑名单数据分析</span> × </a>
        <div id="ll" class="common-popContent">
            <table border="1">
                <c:choose>
                    <c:when test="${ZZCFQZModel eq 'null' or empty ZZCFQZModel}">
                        <tr><td style="width: 398px;">中智诚黑名单：</td><td>暂无数据</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${fn:length(ZZCHMDModel['blackList']) > 0 and !empty ZZCHMDModel['blackList'][0]}">
                                <tr>
                                    <c:forEach items="${ZZCHMDModel['blackList']}" var="in">
                                        <td>${in}</td>
                                    </c:forEach>
                                </tr>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</section>
<%--new 通讯录数据--%>
<section class="new-popup" id="new-contact" style="display: none;">
    <div class="popContent contact-content">
        <a  href="javascript:;" class="closeBtn"> <span>通讯录数据</span> × </a>
        <div class="common-popContent">
            <table border="1">
                <c:choose>
                    <c:when test="${contactList eq 'null' or empty contactList}">
                        <tr><td style="width: 398px;">通讯录数据：</td><td>暂无数据</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${contactList}" var="in">
                            <tr>
                                <td>${in.contactName}</td>
                                <td>${in.contactPhone}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</section>
<%--同盾运营商数据弹窗-new--%>
<section class="new-popup" id="new-tdYunYingShang" style="display: none;">
    <c:choose>
    <c:when test="${SJMHModel != 'null' and !empty SJMHModel}">
        <div class="tdyys-content popContent">
            <a  href="javascript:;" class="closeBtn" > <span>同盾运营商数据</span> × </a>
            <div class="table-box u-operator">
                <div class="table-title">运营商信息</div>
                <table>
                    <tbody>
                    <tr>
                        <td>用户实名认证姓名：</td>
                        <td>${SJMHModel.name}</td>
                    </tr>
                    <tr>
                        <td>运营商实名认证姓名：</td>
                        <td>${SJMHModel.realname}</td>
                    </tr>
                    <tr>
                        <td>手机号码：</td>
                        <td>${SJMHModel.phoneNumber }</td>
                    </tr>
                    <tr>
                        <td>联系地址：</td>
                        <td>${SJMHModel.local }</td>
                    </tr>
                    <tr>
                        <td>联系电话：</td>
                        <td>${SJMHModel.phoneNumber}</td>
                    </tr>
                    <tr>
                        <td>邮箱地址：</td>
                        <td>${SJMHModel.eMail}</td>
                    </tr>
                    <tr>
                        <td>入网时间：</td>
                        <td>${SJMHModel.netAge } 月</td>
                    </tr>
                    <tr>
                        <td>运营商：</td>
                        <td>${SJMHModel.channel }</td>
                    </tr>
                    <tr>
                        <td>账户状态：</td>
                        <td>${SJMHModel.accountType }</td>
                    </tr>
                    <tr>
                        <td>账户星级：</td>
                        <td>${SJMHModel.accountStart }</td>
                    </tr>
                    <tr>
                        <td>账户余额：</td>
                        <td>
                                ${SJMHModel.accountBalance } 元
                        </td>
                    </tr>
                    </tbody></table>
            </div>
            <div class="table-box">
                <div class="table-title">月使用情况</div>
                <table>
                    <thead>
                    <tr>
                        <th>类型</th>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <th>${time }</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>与第一紧急联系人最后一次通话时间</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.lastCallTimeFirst[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>与第二紧急联系人最后一次通话时间</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.lastCallTimeSecond[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>与第一紧急联系人通话次数</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.frequencyFirst[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>与第二紧急联系人通话次数</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.frequencySecond[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>与第一联系人通话次数排名</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.frequencyRankFirst[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>与第二联系人通话次数排名</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.frequencyRankSecond[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>手机关机静默天数</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.silenceDays[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>手机连续关机最大天数</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.maxSilenceDays[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>通话号码数量</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.monthNumberSize[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>手机通话晚间活跃度</td>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.monthNightFrequency[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>总费用(单位：元)</td>
                        <c:set value="0" var="billNumber" />
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.billInfoMap[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td>通话总时长(单位：分钟)</td>
                        <c:set value="0" var="callCount"/>
                        <c:forEach items="${SJMHModel.monthList }" var="time">
                            <td>${SJMHModel.monthDayDuration[time]}</td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td colspan="8" style="padding-bottom: 0;">
                            <table class="tdTopten">
                                <tr>
                                    <th colspan="7" style="text-align: center;font-weight: bold;font-size: 20px;line-height: 50px;">TOP &nbsp;10</th>
                                </tr>
                                <tr>
                                    <td colspan="1">碰撞数:${SJMHModel.tdTopTenSame }</td>
                                    <td colspan="3">呼入</td>
                                    <td colspan="3">呼出</td>
                                </tr>
                                <tr>
                                    <td>排名</td>
                                    <td>电话号码</td>
                                    <td>通话次数</td>
                                    <td>通讯备注</td>
                                    <td>电话号码</td>
                                    <td>通话次数</td>
                                    <td>通讯录备注</td>
                                </tr>
                                <c:forEach items="${SJMHModel.tdTopTenList}" var="in">
                                    <tr>
                                        <td>${in.rank}</td>
                                        <td>${in.callInNumber}</td>
                                        <td>${in.callInFrequency }</td>
                                        <td>${in.callInPerson}</td>
                                        <td>${in.callOutNumber}</td>
                                        <td>${in.callOutFrequency}</td>
                                        <td>${in.callOutPerson}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:when>
    <c:otherwise>
    <div class="tdyys-content popContent">
        <a id="tdCloseBtn" href="javascript:;" class="closeBtn" > <span>同盾运营商数据</span> × </a>
        <div class="table-box u-operator">
            <div class="table-title">运营商信息</div>
            <table>
                <tr>
                    <td>暂无数据</td>
                </tr>
            </table>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</section>

<%--同盾运营商老版本--%>
<div class="old-tdYunyingp popup new-popup" id="oldTunDun" style="display: none">
    <c:choose>
    <c:when test="${tdData != 'noData'}">
        <%--运营商信息--%>
        <div class="tdyys-content popContent">
            <a id="tdCloseBtn" href="javascript:;" class="closeBtn" > <span>同盾运营商数据</span> × </a>
            <div class="table-box u-operator">
                <div class="table-title">运营商信息</div>
                <table>
                    <c:set value="${personInfo.person_info }" var="account" />
                    <tbody><tr>
                        <td>姓名：</td>
                        <td>${user.realname}</td>
                    </tr>
                    <tr>
                        <td>手机号码：</td>
                        <td>${user.userPhone }</td>
                    </tr>
                    <tr>
                        <td>联系地址：</td>
                        <td>${user.presentAddress }</td>
                    </tr>
                    <tr>
                        <td>联系电话：</td>
                        <td>${account.user_contact_no}</td>
                    </tr>
                    <tr>
                        <td>邮箱地址：</td>
                        <td>${user.email}</td>
                    </tr>
                    <tr>
                        <td>入网时间：</td>
                        <td>${account.net_time }</td>
                    </tr>

                    <tr>
                        <td>账户状态：</td>
                        <td>${account.zhanghaoStatus }</td>
                    </tr>
                    <tr>
                        <td>账户星级：</td>
                        <td>${account.zhanghuXinji }</td>
                    </tr>
                    <tr>
                        <td>账户余额：</td>
                        <td>
                            <c:choose>
                                <c:when test="${account.accountMoney ne 'null'}">
                                    ${account.accountMoney/100 } 元
                                </c:when>
                                <c:otherwise>
                                    无数据
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                    </tbody></table>
            </div>
                <%--月使用量情况--%>
            <div class="table-box">
                <div class="table-title">月使用情况</div>
                <table>
                    <thead>
                    <tr>
                        <th>类型</th>
                        <c:forEach items="${timeCycle.time_cycle }" var="time">
                            <th>${time.time }</th>
                        </c:forEach>
                        <th>6个月内</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>与第一紧急联系人最后一次通话时间</td>
                        <c:forEach items="${firstStrTime.first_time }" var="time">
                            <td>${time.firstContactTime }</td>
                        </c:forEach>
                        <td>
                            无
                        </td>
                    </tr>
                    <tr>
                        <td>与第二紧急联系人最后一次通话时间</td>
                        <c:forEach items="${secondStrTime.second_time }" var="time">
                            <td>${time.secondContactTime }</td>
                        </c:forEach>
                        <td>无</td>
                    </tr>
                    <tr>
                        <c:set var="firstCount" value="0" />
                        <td>与第一紧急联系人通话次数</td>
                        <c:forEach items="${firstStrCount.first_count }" var="time">
                            <td>${time.first_count }</td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${firstStrCount.first_count }" var="time">
                                <c:set value="${firstCount + time.first_count }" var="firstCount" />
                            </c:forEach>
                                ${firstCount }
                        </td>
                    </tr>
                    <tr>
                        <td>与第二紧急联系人通话次数</td>
                        <c:set var="secondCount" value="0"/>
                        <c:forEach items="${secondStrCount.second_count }" var="time">
                            <td>${time.second_count }</td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${secondStrCount.second_count }" var="time">
                                <c:set value="${secondCount +time.second_count}" var="secondCount" />
                            </c:forEach>
                                ${secondCount}
                        </td>
                    </tr>
                    <tr>
                        <td>与第一联系人通话次数排名</td>
                        <c:forEach items="${firstRankPhone.first_rank_phone }" var="time">
                            <td>${time.first_rank }</td>
                        </c:forEach>
                        <td>无</td>
                    </tr>
                    <tr>
                        <td>与第二联系人通话次数排名</td>
                        <c:forEach items="${secondRankPhone.second_rank_phone }" var="time">
                            <td>${time.second_rank }</td>
                        </c:forEach>
                        <td>无</td>
                    </tr>
                    <tr>
                        <td>手机关机静默天数</td>
                        <c:set var="clientDay" value="0"/>
                        <c:forEach items="${phoneShut.phone_shut }" var="time">
                            <td>${time.phone_shut }</td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${phoneShut.phone_shut }" var="time">
                                <c:set value="${clientDay +time.phone_shut}" var="clientDay" />
                            </c:forEach>
                                ${clientDay}
                        </td>
                    </tr>
                    <tr>
                        <td>手机连续关机最大天数</td>
                        <c:forEach items="${shutPhoneMax.shut_max }" var="time">
                            <td>${time.count_max }</td>
                        </c:forEach>
                        <td>无</td>
                    </tr>
                    <tr>
                        <td>通话号码数量</td>
                        <c:set value="0" var="phoneNumber"/>
                        <c:forEach items="${phoneCount.phoneCount }" var="time">
                            <td>${time.phone_count }</td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${phoneCount.phoneCount }" var="time">
                                <c:set var="phoneNumber" value="${phoneNumber + time.phone_count}" />
                            </c:forEach>
                                ${phoneNumber}
                        </td>
                    </tr>
                    <tr>
                        <td>手机通话晚间活跃度</td>
                        <c:forEach items="${activeStr.active_str }" var="time">
                            <td>${time.active_count_night }/${time.phone_count }</td>
                        </c:forEach>
                        <td>无</td>
                    </tr>
                    <tr>
                        <td>总费用</td>
                        <c:set value="0" var="billNumber" />
                        <c:forEach items="${billCount.bill_count }" var="time">
                            <td>
                                <c:choose>
                                    <c:when test="${time.bill_total ne 'null'}">
                                        ${time.bill_total/100 }
                                    </c:when>
                                    <c:otherwise>
                                        无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${billCount.bill_count }" var="time">
                                <c:choose>
                                    <c:when test="${time.bill_total ne 'null' }">
                                        <c:set var="billNumber" value="${billNumber+time.bill_total}" />
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                                ${billNumber/100 }(元)
                        </td>
                    </tr>
                    <tr>
                        <td>通话总时长</td>
                        <c:set value="0" var="callCount"/>
                        <c:forEach items="${callTotal.total_call }" var="time">
                            <td>${time.total_call_count }</td>
                        </c:forEach>
                        <td>
                            <c:forEach items="${callTotal.total_call }" var="time">
                                <c:set var="callCount" value="${callCount +time.total_call_count }"/>
                            </c:forEach>
                                ${callCount }(分钟)
                        </td>

                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:when>
    <c:otherwise>
    <div class="tdyys-content popContent">
        <a id="tdCloseBtn" href="javascript:;" class="closeBtn" > <span>同盾运营商数据</span> × </a>
        <div class="table-box u-operator">
            <div class="table-title">运营商信息</div>
            <table>
                <tr>
                    <td>暂无数据</td>
                </tr>
            </table>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</div>


<%--高德地图弹窗内容区--%>
<div class="gaode-popup" style="display: none">
    <div class="box">
        <div class="title">高德地图地理位置<a href="javascript:;" class="u-close">×</a></div>
        <div id="gaode_container"></div>
        <div id="tip">
            <b>认证时地理位置：</b>
            <span id="result"></span>
        </div>
    </div>
</div>

<section id="cajlContactPop" class="popup new-popup" style="display: none">
    <div class="popContent" style="margin-left: -440px;">
        <a id="u-close" href="javascript:;" class="closeBtn" > <span>同盾数据分析</span> × </a>
        <div id="cajlContactTable" class="common-popContent"></div>
    </div>
</section>

<%--风控数据详情--%>
<section class="new-popup risk-popup" id="risk-model" style="display: none;">
    <div class="popContent contact-content">
        <a  href="javascript:;" class="closeBtn"> <span>风控建议</span> × </a>
        <div class="common-popContent">
            <table border="1" id="">
                <tr>
                    <td style="width: 398px;">硬指标建议</td>
                    <td id="requisite"></td>
                </tr>
                <tr>
                    <td style="width: 398px;">硬指标备注</td>
                    <td id="requisiteRemark"></td>
                </tr>
                <tbody id="riskTable">

                </tbody>
            </table>
        </div>
    </div>
</section>

<%--高德地图js--%>
<script type="text/javascript">
    var timesRun = 0;
    var interval = setInterval(function(){
        timesRun += 1;
        if(timesRun === 1){
            clearInterval(interval);
        }
        regeocoder();
    }, 100);

    var coordinate = [${user.presentLongitude},${user.presentLatitude}]
    var map = new AMap.Map("gaode_container", {
            resizeEnable: true,
            zoom: 18
        }),
        lnglatXY = coordinate; //已知点坐标
    function regeocoder() {  //逆地理编码
        var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "base"
        });
        geocoder.getAddress(lnglatXY, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
                geocoder_CallBack(result);
            }
        });
        var marker = new AMap.Marker({  //加点
            map: map,
            position: lnglatXY
        });
        map.setFitView();
    }
    function geocoder_CallBack(data) {
        var address = data.regeocode.formattedAddress; //返回地址描述
        document.getElementById("result").innerHTML = address;
        console.log(address);
        str = "<a target='_blank' href='http://m.amap.com/navi/?dest="+${user.presentLongitude}+"\,"+${ user.presentLatitude}+"&hideRouteIcon=1&key=4a133df767ca4e869e23c1afa240a3a6&destName="+address+"'>"+address+"</a>";
        document.getElementById("gaode_address").innerHTML = str;
    }
</script>

<script type="text/javascript">
    //同盾反欺诈按钮触发事件
    $('#tdfqzContactBtn').on('click', function () {
        $('#new-tdpop').fadeIn();
    });

    $('#oldTd-btn').on('click', function () {
        $('#oldTunDun').fadeIn();
    });

    $('#bqs-btn').on('click', function () {
        $('#new-bqspop').fadeIn();
    });


    $('#zzfqz-btn').on('click', function () {
        $('#new-zzcfqz').fadeIn();
    });
    //通讯录
    $('#contact-btn').on('click',function(){
        $('#new-contact').fadeIn();
    });


    //关闭按钮事件
    $('.closeBtn').click(function () {
        $('.new-popup').fadeOut();
    });
    $('#tdYunYingShang-btn').on('click',function(){
        $('#new-tdYunYingShang').fadeIn();
    });

    $("#tdfqzContactBtn2").on('click',function () {
        tdfqz();
        $('#cajlContactPop').fadeIn();
    });


    $(function(){
        $.fn.imgshow = function() {
            this.each(function() {
                var l = $(this).find("img");
                var j = 0;
                var m = 0;
                var n = "";
                var i = "";
                l.click(function() {
                    var k = "";
                    l.each(function(e, d) {
                        $(d).attr("eq", e);
                        var c = $(this).attr("data-original");
                        if (!c) {
                            k += "<img src='" + $(this).attr("src") + "' />"
                        } else {
                            k += "<img src='" + c + "' />"
                        }
                    });
                    $(".img-group").html("");
                    $(".img-group").html(k);
                    i = ".img-group";
                    $(".view-larger").show();
                    n = $(this);
                    m = parseInt(n.attr("eq"));
                    j = l.length;
                    $(i + " img")[m].onload = function() {
                        a(i, m, n.attr("title"))
                    }
                });
                function a(v, h, c) {
                    c = c || "";
                    h += 1;
                    var d = $(v + " img");
                    $(v).show();
                    //$(".title-status").html(c);
                    $(".count-status").html(h + "/" + j);
                    d.css({
                        display: "none",
                        width: "auto",
                        height: "auto"
                    });
                    d.eq(m).show();
                    var u = d.eq(m).height();
                    var f = d.eq(m).width();
                    var e = $(window).height() - 100;
                    var w = $(window).width() - 350;
                    if (u > e) {
                        d.eq(m).height(e)
                    } else {
                        if (f > w) {
                            d.eq(m).width(w)
                        }
                    }
                    var t = d.eq(m).height() / 2;
                    var g = d.eq(m).width() / 2;
                    $(v).stop(true, true).animate({
                        "margin-top": "-" + t + "px",
                        "margin-left": "-" + g + "px"
                    }, 0)
                }
                $(".o-middle .left").click(function(e) {
                    e.stopPropagation();
                    if (m <= 0) {
                        return
                    }
                    m -= 1;
                    a(i, m, n.attr("title"))
                });
                $(".o-middle .right").click(function(e) {
                    e.stopPropagation();
                    if (m >= j - 1) {
                        return
                    }
                    m += 1;
                    a(i, m, n.attr("title"))
                });
                $(".view-larger .lager-colse").click(function(e) {
                    e.stopPropagation();
                    $("body").css("overflow", "auto");
                    $(".view-larger").hide();
                    $(".img-group").hide()
                })

                $(".overlay").on("mouseover",function(){
                    $(this).css("cursor","url(../static/default/img/zoom_out.cur),auto");
                }).on("click",function(){
                    $("body").css("overflow", "auto");
                    $(".view-larger").hide();
                    $(".img-group").hide()
                });
            })
        }
        $(".identity").imgshow();
    });
    function riskModel(id,projectName) {
        $.ajax({
            type: 'GET',
            url: 'backBorrowOrder/getOldBorrowDetailById',
            // contentType: "text/html;charset=UTF-8",
            data: {id:id,projectName:projectName},
            success: function(data){
                var borrow,auto,riskModelOrder,oldCustomerAdviceStr='',oldCustomerAdviceReason='',modelAdviceStr='',
                    modelScore='',verifyReviewRemark='',cutoff='',reviewUp='',reviewDown='',modelName='',borrowStatus=''
                    ,reviewStatus='';
                borrow = data.borrow || {},auto = data.auto || {},riskModelOrder = data.riskModelOrder;
                borrowStatus = borrow.status;
                verifyReviewRemark = borrow.verifyReviewRemark?borrow.verifyReviewRemark:'';
                if(auto.oldCustomerAdvice != null && auto.oldCustomerAdvice != ''){
                    switch (auto.oldCustomerAdvice) {
                        case 'REVIEW': oldCustomerAdviceStr = '人工复审'; break;
                        case 'REJECT': oldCustomerAdviceStr = '拒绝'; break;
                        case 'PASS': oldCustomerAdviceStr = '通过'; break;
                    }
                    if(auto.oldCustomerAdviceReason != null && auto.oldCustomerAdviceReason != '' ){
                        oldCustomerAdviceReason = auto.oldCustomerAdviceReason;
                    }else{
                        oldCustomerAdviceReason= '';
                    }
                }

                if(riskModelOrder){
                    modelScore = riskModelOrder.modelScore?riskModelOrder.modelScore:'';
                    switch(riskModelOrder.modelAdvice){
                        case 0: modelAdviceStr = '通过'; break;
                        case 1: modelAdviceStr = '人工复审'; break;
                        case 2: modelAdviceStr = '拒绝'; break;
                    }
                    switch(riskModelOrder.personReviewAdvice){
                        case 0: reviewStatus = '通过'; break;
                        case 1: reviewStatus = '人工复审'; break;
                        case 2: reviewStatus = '拒绝'; break;
                    }
                    cutoff = riskModelOrder.cutoff?riskModelOrder.cutoff:'';
                    reviewUp = riskModelOrder.reviewUp?riskModelOrder.reviewUp:'';
                    reviewDown = riskModelOrder.reviewDown?riskModelOrder.reviewDown:'';
                    if(riskModelOrder.modelCode!=null&&riskModelOrder.variableVersion!=null){
                        if(riskModelOrder.modelCode==0&&riskModelOrder.variableVersion==0){
                            modelName = '人工';
                        }else{
                            modelName = '评分卡 '+riskModelOrder.modelCode+'_'+riskModelOrder.variableVersion;
                        }
                    }
                }
                var html =
                    '<tr><td style="width: 398px;">老用户建议</td><td>'+oldCustomerAdviceStr+'</td></tr>'+
                    '<tr><td style="width: 398px;">老用户备注</td><td>'+oldCustomerAdviceReason+'</td></tr>'+
                    '<tr><td style="width: 398px;">cutoff线</td><td>'+cutoff+'</td></tr>'+
                    '<tr><td style="width: 398px;">复审范围</td><td> 上限：'+reviewUp+'下限：'+reviewDown+'</td></tr>'+
                    '<tr><td style="width: 398px;">当前模型</td><td>'+modelName+'</td></tr>'+
                    '<tr><td style="width: 398px;">评分卡建议</td><td>'+modelAdviceStr+'</td></tr>'+
                    '<tr><td style="width: 398px;">评分卡备注</td><td>'+modelScore+'</td></tr>'+
                    '<tr><td style="width: 398px;">人工复审建议</td><td>'+reviewStatus+'</td></tr>'+
                    '<tr><td style="width: 398px;">人工复审备注</td><td>'+verifyReviewRemark+'</td></tr>';
                $('#riskTable').empty();
                $('#riskTable').append(html);
                //硬指标建议
                var requisite = '',requisiteRemark = '';

                if(auto.inflexibleAdvice != null && auto.inflexibleAdvice != ''){
                    switch (auto.inflexibleAdvice) {
                        case 'REVIEW': requisite = '人工复审'; break;
                        case 'REJECT': requisite = '拒绝'; break;
                        case 'PASS': requisite = '通过'; break;
                    }
                    if(auto.inflexibleAdviceReason != null && auto.inflexibleAdviceReason != '' ){
                        requisiteRemark = auto.inflexibleAdviceReason;
                    }else{
                        requisiteRemark= '';
                    }
                }
                //插入‘硬指标建议’ ‘硬指标备注’
                $('#requisite').text(requisite);
                $('#requisiteRemark').text(requisiteRemark);
                $('#risk-model').fadeIn();
            },
            dataType: "json",
        });
    }
</script>
