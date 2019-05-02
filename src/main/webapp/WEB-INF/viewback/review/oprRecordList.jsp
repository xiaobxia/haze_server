<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="review/view_dis_opr_record?myId=${params.myId}" method="post">
    <div class="pageContent">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" id="status" name="status" value=""/>
        <table class="table" style="width: 100%;" layoutH="50" nowrapTD="false">
            <thead>
            <tr>
                <th align="center">
                    序号
                </th>
                <th align="center">
                    操作员
                </th>
                <th align="center">
                    被修改员
                </th>
                <th align="center">
                    操作时间
                </th>
                <th align="center">
                    比例修改值
                </th>
                <th align="center">
                    手动派单数
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="itemVal" items="${pm.items }" varStatus="status">
            <tr>
                <td>
                        ${status.count}
                </td>
                <td>
                        ${itemVal.oprUsrName}
                </td>
                <td>
                        ${itemVal.oprSetUsrName}
                </td>
                <td>
                    <fmt:formatDate value="${itemVal.oprSetDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                        ${itemVal.oprSetRate}
                </td>
                <td>
                        ${itemVal.oprDisCount}
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <%@ include file="../page.jsp" %>
    </div>
</form>