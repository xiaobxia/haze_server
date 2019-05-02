<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="" method="post">
    <input type="hidden" id="status" value="${status}">
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="112"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center" width="50">
                    序号
                </th>
                <th align="center" width="100">
                    复审人员
                </th>
                <th align="center" width="50">
                    派单比例
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${list}" varStatus="status">
                <tr target="userId" rel="${user.id }">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${user.userName }
                    </td>
                    <td>
                            ${user.distributionRate }
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>