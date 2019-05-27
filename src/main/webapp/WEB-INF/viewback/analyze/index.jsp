<%--
  Created by IntelliJ IDEA.
  User: tql
  Date: 2018/4/12
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="dataAnalyze/index?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        用户属性：
                        <select name="customerTypeKey" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="customerType" items="${customerTypeMap}">
                                <option value="${customerType.key}" <c:if test="${customerType.key eq params.customerTypeKey}">selected="selected"</c:if> >${customerType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                       周期：
                        <select name="flag" class="textInput">
                            <c:forEach var="dateCycle" items="${dateCycleMap}">
                                <option value="${dateCycle.key}"
                                        <c:if test="${dateCycle.key eq params.flag}">selected="selected"</c:if>
                            >${dateCycle.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        从:
                        <input type="text" name="startWeekTime" id="startWeekTime"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${params.startWeekTime}" />至
                        <input type="text" name="endWeekTime" id="endWeekTime"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${params.endWeekTime}" />
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
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
            <thead>
            <tr >
                <th align="center"  >
                    序号
                </th>
                <th align="center"  >
                    日期
                </th>
                <th align="left"  >
                    申请人数
                </th>
                <th align="center"  >
                    贷款人数
                </th>
                <th align="center" >
                    贷款额
                </th>
                <th align="center"  >
                    注册用户数
                </th>
                <%--<th align="center" >
                    贷款余额
                </th>--%>
                <th align="center" >
                    核准率
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="data" items="${pm.items }" varStatus="status">
                <tr  target="sid_support" rel="${borrow.id }"  >
                    <td>${status.count}</td>
                    <td>${data.statisticsTime}</td>
                    <td>
                        <c:choose>
                            <c:when test="${params.get('customerTypeKey') == '' or params.get('customerTypeKey') == null}">${data.applyUserCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 1}">${data.applyUserOldCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 0}">${data.applyUserNewCount}</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${params.get('customerTypeKey') == '' or params.get('customerTypeKey') == null}">${data.loanUserCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 1}">${data.loanUserOldCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 0}">${data.loanUserNewCount}</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${params.get('customerTypeKey') == '' or params.get('customerTypeKey') == null}">${data.loanMoneyCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 1}">${data.loanMoneyOldCount}</c:when>
                            <c:when test="${params.get('customerTypeKey') == 0}">${data.loanMoneyNewCount}</c:when>
                        </c:choose>
                    </td>
                    <td>
                        ${data.registCount}
                    </td>
                    <%--<td>
                         ${data.loanBlance}
                    </td>--%>
                    <td>
                        <c:choose>
                            <c:when test="${params.get('customerTypeKey') == '' or params.get('customerTypeKey') == null}">
                                <fmt:formatNumber type="number" value="${data.customerCheckRate*100}" pattern="0.00" maxFractionDigits="2"/>
                            </c:when>
                            <c:when test="${params.get('customerTypeKey') == '1'}">
                                <fmt:formatNumber type="number" value="${data.oldCustomerCheckRate*100}" pattern="0.00" maxFractionDigits="2"/>
                            </c:when>
                            <c:when test="${params.get('customerTypeKey') == '0'}">
                                <fmt:formatNumber type="number" value="${data.newCustomerCheckRate*100}" pattern="0.00" maxFractionDigits="2"/>
                            </c:when>
                        </c:choose>
                        %
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
    </div>
</form>

<script type="text/javascript">
    if("${message}"){
        alertMsg.error(${message});
    }
</script>
