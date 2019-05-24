<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="channel/goProductList?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        产品名称:
                        <select name="productName" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="productInfo" items="${productMoneyList}">
                                <option value="${productInfo.productName}"
                                        <c:if test="${productInfo.productName eq params.productName}">selected="selected"</c:if> >${productInfo.productName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        产品金额:
                        <select name="borrowAmount" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="productInfo" items="${productMoneyList}">
                                <option value="${productInfo.borrowAmount}"
                                        <c:if test="${productInfo.borrowAmount eq params.borrowAmount}">selected="selected"</c:if> >${productInfo.borrowAmount}</option>
                            </c:forEach>
                        </select>
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
        <table class="table" style="width: 100%;" layoutH="155" nowrapTD="false">
            <thead>
            <tr>
                <th>
                    产品ID
                </th>
                <th>
                    产品名称
                </th>
                <th>
                    产品金额
                </th>
                <th>
                    到账金额
                </th>
                <th>
                    服务费率
                </th>
                <th>
                    服务费
                </th>
                <th>
                    滞纳金
                </th>
                <th>
                    借款期限
                </th>
                <th>
                    是否默认
                </th>
                <th>
                    默认管理
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${pm.items }" varStatus="status">
                <tr target="id" rel="${product.productId }">
                    <td>
                            ${product.productId}
                    </td>
                    <td>
                            ${product.productName}
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${product.borrowAmount/100}" pattern="0.00"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${product.borrowAmount * (1 - product.totalFeeRate/100000)/100}" pattern="0.00"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${product.totalFeeRate/1000}" pattern="0.00"
                                          maxFractionDigits="2"/>%
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${product.totalFeeRate * product.borrowAmount / 10000000}" pattern="0.00"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${product.lateFee/100}" pattern="0.00"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>
                            ${product.borrowDay}
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${product.status == 0}">
                                <span>是</span>
                            </c:when>
                            <c:otherwise>
                                <span>否</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${product.status == 0}">
                                <span>置为否</span>
                            </c:when>
                            <c:otherwise>
                                <span>置为是</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>

<script type="text/javascript">
</script>