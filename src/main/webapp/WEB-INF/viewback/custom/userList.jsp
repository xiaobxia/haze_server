<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<style>
    fieldset legend {
        text-align: left;
    }
    .remarkTable,
    .remarkChildTable {
        padding: 0;
        border: solid 1px #928989;
        width: 100%;
        line-height: 21px;
    }
    .remarkTable {
        margin-bottom: 15px;
    }
    .remarkTable > tr:first-child td:first-child {
        font-size: 16px;
        font-weight: bold;
    }
    .remarkTable tr td {
        border-bottom: 1px solid #928989;
        border-right: 1px solid #928989;
        line-height: 31px;
        overflow: hidden;
        vertical-align: middle;
        font-size: 14px;
        text-indent: 15px;
    }
    .remarkChildTable {
        border: none;
    }
    .remarkChildTable tr td {
        padding: 5px;
    }
    .remarkChildTable tr td:last-child {
        border-right: none;
    }
    .remarkChildTable tr:last-child td {
        border-bottom: none;
    }
    .remarkTable > tr:first-child > td:first-child {
        border-bottom: none;
    }
    .remarkChildTable tr td{
        text-align: center;
    }
    .remarkChildTable tr td:nth-child(odd) {
        background: #efefef;
    }
    .remarkChildTable tr td input {
        display: block;
        width: 90%;
        height: 30px;
        line-height: 30px;
        padding: 0 10px;
    }
    .remarkTable tr th {
        line-height: 30px;
        font-size: 16px;
        background: #eaeaea;
        border-bottom: 1px solid #d4d4d4;
        text-indent: 15px;
    }
    .remarkTable tr th:not(:last-child) {
        border-right: 1px solid #928989;
    }
    .bg-f6 {
        background: #f6f6f6;
    }

    .new-popup .common-popContent table{
        width: 800px;
    }
    .new-popup .common-popContent table{
        border-left: 1px solid #c3c3c3;
        border-top: 1px solid #c3c3c3;
    }
    .new-popup table tr td {
        line-height: 28px;
        text-indent: 20px;
    }
    .new-popup .common-popContent table tr th {
        line-height: 30px;
        font-size: 16px;
        text-indent: 20px;
    }
    .popContent {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(0,-50%);
        margin-left: -300px;
        background: #f0eff0;
        border-radius: 8px;
        padding: 20px;
    }
    .new-popup {
        display: block;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,.6);
        z-index: 150;
    }

    .closeBtn {
        width: 800px;
        display: block;
        color: red !important;
        font-size: 25px;
        text-align: right;
        margin: 0 auto 15px;
    }
    .closeBtn span {
        padding: 10px 20px;
        border: 1px solid #c8c8c8;
        border-radius: 6px;
        font-size: 14px;
        letter-spacing: 1px;
        margin-top: 10px;
        color: #333;
    }
    .closeBtn:hover {
        text-decoration: none;
    }

    .common-popContent {
        width: 800px;
        max-height: 520px;
        overflow-y: scroll;
        color: #333;
        padding: 10px 20px;
        z-index: 100;
        padding-bottom: 15px;
        border-radius: 10px;
    }
</style>
<div class="pageContent">
    <form id="pagerForm" action="customService/userList?bType=${bType}&myId=${params.myId}"
          onsubmit="return navTabSearch(this)">
        <div class="pageHeader">
            <div class="searchBar">
                <table class="searchContent">
                    <tr>
                        <td>
                            手机号:
                            <input type="text" id="userPhone" name="userPhone" value="${params.userPhone}">
                            <input type="hidden" id="userName" name="userName" value="${user.realname}">
                            <input type="hidden" id="borrowOrderId" value="${user.assetOrderId}"/>
                        </td>
                        <td>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="submit">
                                        查询
                                    </button>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="panelBar">
            <jsp:include page="${BACK_URL}/rightSubList">
                <jsp:param value="${params.myId}" name="parentId"/>
            </jsp:include>
        </div>
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <!--添加备注-->
            <fieldset>
                <legend>用户资料</legend>
                <table class="remarkTable">
                    <tbody>
                    <tr>
                        <c:choose>
                            <c:when test="${fn:length(user.banks) == 0}">
                                <td class="bg-f6" align="center" rowspan="5">用户信息</td>
                            </c:when>
                            <c:otherwise>
                                <td class="bg-f6" align="center" rowspan="${4 + fn:length(user.banks)}">用户信息</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <td>姓名</td>
                        <td>${user.realname}</td>
                        <td>新老用户</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.customerType == 0}">
                                    新用户
                                </c:when>
                                <c:when test="${user.customerType == 1}">
                                    老用户
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>性别</td>
                        <td>${user.userSex}</td>
                        <td>用户来源</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.browerType == 1}">
                                    安卓
                                </c:when>
                                <c:when test="${user.browerType == 2}">
                                    苹果
                                </c:when>
                                <c:when test="${user.browerType == 3}">
                                    PC
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>手机号</td>
                        <td>${user.userPhone}</td>
                        <td>身份证号</td>
                        <td>${user.idNumber}</td>
                    </tr>
                    <c:choose>
                        <c:when test="${user.banks == null}">
                            <tr>
                                <td>银行卡号</td>
                                <td></td>
                                <td>银行名称</td>
                                <td></td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${user.banks}" var="bank" varStatus="bankIndex">
                                <tr>
                                    <td>银行卡号</td>
                                    <td>${bank.cardNo}</td>
                                    <td>银行名称</td>
                                    <td>${bank.bankName}</td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <tr>
                        <td class="bg-f6" align="center" rowspan="3">认证信息</td>
                    </tr>
                    <tr>
                        <td>个人信息认证</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.realnameStatus == 1}">
                                    已认证
                                </c:when>
                                <c:when test="${user.realnameStatus == 2}">
                                    未认证
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>紧急联系人认证</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.emergencyInfo == 1}">
                                    已认证
                                </c:when>
                                <c:when test="${user.emergencyInfo == 0}">
                                    未认证
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>淘宝授信</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.zmStatus == 2}">
                                    已认证
                                </c:when>
                                <c:when test="${user.zmStatus == 1}">
                                    未认证
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>同盾运营商认证</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.tdStatus == 2}">
                                    已认证
                                </c:when>
                                <c:when test="${user.tdStatus == 1}">
                                    未认证
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend>当前订单</legend>
                <table class="remarkTable">
                    <tbody>
                    <tr>
                        <td class="bg-f6" align="center" rowspan="5">订单信息</td>
                    </tr>
                    <tr>
                        <td>订单号</td>
                        <td>${user.yurref}</td>
                        <td>第三方流水号</td>
                        <c:choose>
                            <c:when test="${user.outTradeNo != null and user.outTradeNo != ''}">
                                <td>${user.outTradeNo}</td>
                            </c:when>
                            <c:otherwise>
                                <td>暂无</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>

                    <tr>
                        <td>放款金额</td>
                        <td>${user.moneyAmount/100}</td>
                        <td>放款时间</td>
                        <td>${user.loanTime}</td>
                    </tr>

                    <tr>
                        <td>订单状态</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.repaymentStatus == 0}">
                                    待初审
                                </c:when>
                                <c:when test="${user.repaymentStatus == -3}">
                                    初审驳回
                                </c:when>
                                <c:when test="${user.repaymentStatus == 1}">
                                    初审通过
                                </c:when>
                                <c:when test="${user.repaymentStatus == -4}">
                                    复审驳回
                                </c:when>
                                <c:when test="${user.repaymentStatus == 20}">
                                    复审通过,待放款
                                </c:when>
                                <c:when test="${user.repaymentStatus == -5}">
                                    放款驳回
                                </c:when>
                                <c:when test="${user.repaymentStatus == 22}">
                                    放款中
                                </c:when>
                                <c:when test="${user.repaymentStatus == -10}">
                                    放款失败
                                </c:when>
                                <c:when test="${user.repaymentStatus == 21}">
                                    已放款,还款中
                                </c:when>
                                <c:when test="${user.repaymentStatus == 23}">
                                    部分还款
                                </c:when>
                                <c:when test="${user.repaymentStatus == 30}">
                                    已还款
                                </c:when>
                                <c:when test="${user.repaymentStatus == -11}">
                                    已逾期
                                </c:when>
                                <c:when test="${user.repaymentStatus == -20}">
                                    已坏账
                                </c:when>
                                <c:when test="${user.repaymentStatus == 34}">
                                    逾期已还款
                                </c:when>
                                <c:when test="${user.repaymentStatus == 666}">
                                   待AI验证
                                </c:when>
                                <c:when test="${user.repaymentStatus == 667}">
                                    AI验证失败
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>预计还款时间</td>
                        <td>${user.repaymentTime}</td>
                    </tr>
                    <tr>
                        <td>逾期天数</td>
                        <td>${user.lateDay}</td>
                        <td>当前催收员</td>
                        <td>${user.currentJobName}</td>
                    </tr>
                    </tbody>
                </table>
            </fieldset>
            <!--查看备注-->
            <fieldset>
                <legend>备注信息</legend>
                <table class="remarkTable">
                    <tr>
                        <th>序号</th>
                        <th>添加时间</th>
                        <th>操作人</th>
                        <th>标签</th>
                        <th>备注</th>
                    </tr>
                    <c:forEach items="${user.remarks}" var="remark" varStatus="varStatus">
                        <tr>
                            <td>${varStatus.index + 1}</td>
                            <td><fmt:formatDate value="${remark.createTime}"   pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></td>
                            <td>${remark.jobName}</td>
                            <td>${remarkMap.get(remark.remarkFlag.toString())}</td>
                            <td>${remark.remarkContent}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${user.remarks.size() > 10}">
                        <tr id="show_all">
                            <td colspan="5" style="text-align: center"><a href="javascript:void(0);"
                                                                          onclick="show_all()"><span
                                    style="color: deepskyblue;font-size: 12px">查看全部>>></span></a></td>
                        </tr>
                    </c:if>
                </table>
            </fieldset>
            <fieldset>
                <legend>还款记录</legend>
                <table class="remarkTable">
                    <tr>
                        <th>序号</th>
                        <th>订单号</th>
                        <th>申请时间</th>
                        <th>放款时间</th>
                        <th>还款时间</th>
                        <th>实际放款金额</th>
                        <th>还款金额</th>
                        <th>还款方式</th>
                        <th>还款渠道</th>
                    </tr>
                    <c:forEach items="${user.repaymentHistory}" var="record" varStatus="varStatus">
                        <tr>
                            <td>${varStatus.index + 1}</td>
                            <td>${record.id}</td>
                            <td>${record.createTime}</td>
                            <td>${record.loanTime}</td>
                            <td>${record.repayTime}</td>
                            <td>${record.moneyAmount/100}</td>
                            <td>
                                <c:if test="${record.loanTime !=null }">
                                    ${record.money / 100.00}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${record.loanTime !=null }">
                                    ${record.repaymentType}
                                </c:if>
                            </td>
                            <td>${record.remark}</td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
            <!--续期记录-->
            <fieldset>
                <legend>续期记录</legend>
                <table class="remarkTable">
                    <tr>
                        <th>序号</th>
                        <th>订单号</th>
                        <th>续期操作时间</th>
                        <th>续期后还款时间</th>
                        <th>续期方式</th>
                        <th>实际放款金额</th>
                        <th>续期金额</th>
                    </tr>
                    <c:forEach items="${user.renewalHistory}" var="renewal" varStatus="varStatus">
                        <tr>
                            <td>${varStatus.index + 1}</td>
                            <td>${renewal.orderNo}</td>
                            <td>${renewal.renewalTime}</td>
                            <td>${renewal.repaymentTime}</td>
                            <td>${renewal.renewalType}</td>
                            <td>${renewal.moneyAmount/100}</td>
                            <td>${renewal.sumFee / 100.00}</td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>

        </div>
    </form>
</div>

<section class="new-popup" id="all_remarks_pop" style="display: none;overflow: auto;">
    <div class="popContent remarks-content">
        <div id="ww" class="common-popContent">
            <table border="1">
                <tr>
                    <th>序号</th>
                    <th>添加时间</th>
                    <th>操作人</th>
                    <th>标签</th>
                    <th>备注</th>
                </tr>
                <c:forEach items="${user.remarks}" var="remark" varStatus="varStatus">
                    <tr>
                        <td>${varStatus.index + 1}</td>
                        <td><fmt:formatDate value="${remark.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date"
                                            dateStyle="long"/></td>
                        <td>${remark.jobName}</td>
                        <td>${remarkMap.get(remark.remarkFlag.toString())}</td>
                        <td>${remark.remarkContent}</td>
                    </tr>
                </c:forEach>
            </table>
            <%--<button value="确认" onclick="close_remark_pop()"/>--%>
        </div>
        <a id="bb" href="javascript:;" class="closeBtn" onclick="close_remark_pop()"> <span>确认</span></a>
    </div>
</section>
<script type="text/javascript">
    function sendUserId(obj) {
        var href = $(obj).attr("href");
        if ($('#borrowOrderId').val() == '') return;
        href = href + "&id=" + $('#borrowOrderId').val() + "&userPhone=" + $('#userPhone').val() + "&type=online"+"&userName="+$('#userName').val();
        $(obj).attr('href', href);
    }

    function show_all() {
        $('#all_remarks_pop').fadeIn();
    }


    function close_remark_pop() {
        $('#all_remarks_pop').fadeOut();
    }

</script>