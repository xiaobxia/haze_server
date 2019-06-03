<%--
  Created by IntelliJ IDEA.
  User: xiefei
  Date: 2018/05/23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tomcat.apache.org/myfunction-taglib" prefix="myFunction" %>
<style type="text/css">
    .showRisk{
        align-items: flex-start;
        text-align: center;
        cursor: default;
        color: buttontext;
        background-color: buttonface;
        box-sizing: border-box;
        padding: 2px 6px 3px;
        border-width: 2px;
        border-style: outset;
        border-color: buttonface;
        border-image: initial;
    }
</style>

<table class="detail-table">
    <tr>
        <th>序号</th>
        <th>订单号</th>
        <th>借款金额(元)</th>
        <th>服务费利率(万分之一)</th>
        <th>服务费(元)</th>
        <th>申请时间</th>

        <th>放款时间</th>
        <th>子类型</th>
        <th>手机号</th>
        <th>状态</th>
        <th>审核人员</th>
    </tr>
    <c:forEach var="borrow" items="${userBorrows }" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${borrow.outTradeNo}</td>
            <td><fmt:formatNumber type="number"
                                  value="${borrow.moneyAmount/100}"
                                  pattern="0.00" maxFractionDigits="2"/></td>
            <td><fmt:formatNumber type="number" value="${borrow.apr}"
                                  pattern="0.00" maxFractionDigits="2"/></td>
            <td><fmt:formatNumber type="number"
                                  value="${borrow.loanInterests/100}"
                                  pattern="0.00" maxFractionDigits="2"/></td>
            <td><fmt:formatDate value="${borrow.orderTime }"
                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${borrow.loanTime }"
                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${borrow.projectNameVal }</td>
            <td>${borrow.userPhone }</td>
            <td>
                <c:choose>
                    <c:when test="${borrow.status == 34}">
                        <a class="icon" target="navtab" rel="ids"
                           posttype="string"
                           href="/back/backBorrowOrder/getOverDueHistoryList?myId=${params.myId}&projectName=${borrow.projectName}&id=${borrow.id}">${borrow.statusName } </a>
                    </c:when>
                    <c:otherwise>
                        ${borrow.statusName }
                    </c:otherwise>
                </c:choose>
            </td>
            <c:if test="${borrow.reviwRiskLabel == null}">
                <td>${borrow.reBackUserName}</td>
            </c:if>
            <c:if test="${borrow.reviwRiskLabel == 1}">
                <td>评分卡</td>
            </c:if>
            <c:if test="${borrow.reviwRiskLabel == 2}">
                <td>人工否决：${borrow.reBackUserName}</td>
            </c:if>
            <c:if test="${borrow.reviwRiskLabel == 3}">
                <td>${borrow.reBackUserName}</td>
            </c:if>
        </tr>
    </c:forEach>
</table>
