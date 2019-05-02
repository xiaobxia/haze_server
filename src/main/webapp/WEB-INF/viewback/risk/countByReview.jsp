<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="risk/countByReview?myId=${params.myId}"
      method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                    <td>日期：
                        <input type="text" name="sDate" id="sDate" value="${params.sDate}"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"/>
                        至<input type="text" name="eDate" id="eDate" value="${params.eDate}"
                                class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"/>
                    </td>
                    <td>用户类别： <select name="customerType" id="customerType">
                        <option value="0">全部</option>
                        <option value="2" <c:if test="${2 eq params.customerType}">selected="selected"</c:if>>新用户
                        </option>
                        <option value="1" <c:if test="${1 eq params.customerType}">selected="selected"</c:if>>老用户
                        </option>
                    </select>
                    </td>
                    <td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="pageContent">


        <%--<jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>--%>
        <table class="table" style="width: 100%;" layoutH="150"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center">序号</th>
                <th align="center">姓名</th>
                <th align="center">审核量</th>
                <th align="center">过件量</th>
                <th align="center">过件率(%)</th>
                <th align="center">正常已还款数量</th>
                <th align="center">待还款数量</th>
                <th align="center">已逾期数量</th>
                <th align="center">逾期已还款数量</th>
                <th align="center">部分还款数量</th>
                <th align="center">已坏账数量</th>
                <th align="center">首逾逾期量</th>
                <th align="center">首期逾期率(%)</th>
                <th align="center">催回还款量</th>
                <th align="center">总续期次数</th>
                <th align="center">催回率(%)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="overdue" items="${list}" varStatus="status">

                <tr>
                    <td>${status.count}</td>
                    <td>${overdue.reviewUser}</td>
                    <td>${overdue.borrowNum}</td>
                    <td>${overdue.passNum}</td>
                    <td>${overdue.passRate}</td>
                    <td>${overdue.paidNum}</td>
                    <td>${overdue.toPayNum}</td>
                    <td>${overdue.overdueYet}</td>
                    <td>${overdue.overdueButRepay}</td>
                    <td>${overdue.partRepay}</td>
                    <td>${overdue.badOrd}</td>
                    <td>${overdue.firstOverdueNum}</td>
                    <td>${overdue.firstOverdueRate}</td>
                    <td>${overdue.collectNum}</td>
                    <td>${overdue.renewalNum}</td>
                    <td>${overdue.collectRate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>

<script type="text/javascript">
    if ("${message}") {
        alertMsg.error(${message});
    }

</script>