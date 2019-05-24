<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="configParams/getProductList?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
<%--                    <td>--%>
<%--                        产品金额:--%>
<%--                        <select name="borrowAmount" class="textInput">--%>
<%--                            <option value="">全部</option>--%>
<%--                            <c:forEach var="productInfo" items="${channelInfoList}">--%>
<%--                                <option value="${channelInfo.id}"--%>
<%--                                        <c:if test="${channelInfo.id eq params.channelInfoId}">selected="selected"</c:if> >${channelInfo.channelName}</option>--%>
<%--                            </c:forEach>--%>
<%--                        </select>--%>
<%--                    </td>--%>
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
                    是否默认
                </th>
                <th>
                    默认管理
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${pm.items }" varStatus="status">
                <tr target="productId" rel="${product.productId }">
                    <td>
                            ${product.productId}
                    </td>
                    <td>
                            ${product.borrowAmount}
                    </td>
                    <td>
                            ${product.borrowAmount * (1 - product.totalFeeRate)}
                    </td>
                    <td>
                            ${product.totalFeeRate}
                    </td>
                    <td>
                            ${product.totalFeeRate * product.borrowAmount}

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