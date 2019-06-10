<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="customService/getOrderRemindList?myId=${params.myId}"
      method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        用户名称:
                        <input type="text" name="userAccountLike" id="userAccountLike"
                               value="${params.userAccountLike }" />
                    </td>
                    <td>
                        手机:
                        <input type="text" name="userMobileLike" id="userMobileLike"
                               value="${params.userMobileLike }" />
                    </td>                    <td>
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
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId" />
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="138"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center"  >
                    序号
                </th>
                <th align="center"  >
                    姓名
                </th>
                <th align="center" >
                    手机号
                </th>
                <%--<th align="center"  >
                    是否是老用户
                </th>--%>
                <th align="center">
                    成功还款次数
                </th>
                <th align="center" >
                    借款到账金额
                </th>
                <th align="center" >
                    服务费
                </th>
                <th align="center" >
                    总需要还款金额
                </th>
                <th align="center" >
                    已还金额
                </th>

                <th align="center" >
                    放款时间
                </th>
                <th align="center" >
                    预期还款时间
                </th>
                <th align="center" class="loanStatusTitle">
                    状态
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repayment" items="${pm.items }" varStatus="status">
                <tr target="sid_support" rel="${repayment.assetOrderId}">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${repayment.realname}
                    </td>
                    <td>
                            ${repayment.userPhone }
                    </td>
                        <%--<td>
                            <c:if test="${repayment.customerType == '0'}">新用户</c:if>
                            <c:if test="${repayment.customerType == '1'}">老用户</c:if>
                        </td>--%>
                    <td class="loanSuccessCount">
                            ${repayment.loanCount}
                    </td>
                    <td>
                        <fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentPrincipal / 100.00}"/>
                    </td>
                    <td>
                        <fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentInterest / 100.00}"/>
                    </td>
                    <td>
                        <fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentAmount / 100.00}"/>
                    </td>
                    <td>
                        <fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentedAmount / 100.00}"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${repayment.creditRepaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${repayment.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td class="loanStatusName">
                            ${BORROW_STATUS_ALL[repayment.status]}
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

    if (renderLoanSuccessCount) {
        setTimeout(function () {
            renderLoanSuccessCount()
        }, 200)
    }
</script>