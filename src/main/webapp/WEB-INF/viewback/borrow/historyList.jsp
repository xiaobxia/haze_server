<%--
  Created by IntelliJ IDEA.
  User: xiefei
  Date: 2018/05/23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tomcat.apache.org/myfunction-taglib" prefix="myFunction" %>
<style type="text/css">
    .riskTable td:nth-child(odd){
        width: 10%;
    }
    .riskTable td:nth-child(even){
        width: 40%;
    }
    .risk-popup td:nth-child(odd){
        width: 20%;
    }
    .risk-popup td:nth-child(even){
        width: 80%;
    }
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

<tr>
    <td style="font-weight: bold">历史借款记录</td>
    <!-- 							<td>暂无信息</td> -->
    <td colspan="3">
        <table class="detailB" width="100%">
            <tr>
                <th align="center">序号</th>
                <th align="left">订单号</th>
                <th align="center">借款金额(元)</th>
                <th align="center">服务费利率(万分之一)</th>
                <th align="center">服务费(元)</th>
                <th align="center">申请时间</th>

                <th align="center">放款时间</th>
                <th align="center">子类型</th>
                <th align="center">手机号</th>
                <th align="center">状态</th>
                <th align="center">审核人员</th>
                <th align="center">备注</th>
            </tr>
            <c:forEach var="borrow" items="${userBorrows }" varStatus="status">
                <tr <c:choose>
                    <c:when test="${'jx' eq borrow.projectName}">
                        bgcolor="#DCFDFF";
                    </c:when>
                    <c:when test="${'ylh' eq borrow.projectName}">
                        bgcolor="#E5FDE4";
                    </c:when>
                    <c:when test="${'slj' eq borrow.projectName}">
                        bgcolor="#FCE3D3";
                    </c:when>
                    <c:when test="${'vxj' eq borrow.projectName}">
                        bgcolor="#FFE1FF";
                    </c:when>
                    <c:when test="${'wlqb' eq borrow.projectName}">
                        bgcolor="#497c74";
                    </c:when>
                    <c:when test="${'wyjj' eq borrow.projectName}">
                        bgcolor="#667c48";
                    </c:when>
                    <c:otherwise>
                        bgcolor="#D1D1D1";
                    </c:otherwise>
                </c:choose>>
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${borrow.outTradeNo}
                    </td>
                    <td>
                        <fmt:formatNumber type="number"
                                          value="${borrow.moneyAmount/100}"
                                          pattern="0.00" maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${borrow.apr}"
                                          pattern="0.00" maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number"
                                          value="${borrow.loanInterests/100}"
                                          pattern="0.00" maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${borrow.orderTime }"
                                        pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${borrow.loanTime }"
                                        pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                            ${borrow.projectNameVal }
                    </td>
                    <td>
                            ${borrow.userPhone }
                    </td>
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
                    <td>
                        <%--<c:if test="${appName eq borrow.orderTypeName }">--%>
                        <span class="showRisk" onclick="riskModel('${borrow.id}','${borrow.projectName}')">查看</span>
                        <%--</c:if>--%>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </td>
</tr>
