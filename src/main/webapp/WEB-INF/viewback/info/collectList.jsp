<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"	action="xxx?myId=${params.myId}" method="post">
	
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="78"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center" width="50">
						序号
					</th>
					<th align="center" width="100">
						用户名
					</th>
					<th align="center" width="120">
						收藏时间
					</th>
					<th align="center" width="100">
						IP
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="collect" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${collect.id }">
						<td>
							${status.count}
						</td>
						<td>
							${collect.user.userAccount }
						</td>
						<td>
							<fmt:formatDate value="${collect.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							${collect.ip}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
	alertMsg.error("${message}");
</script>
	</c:if>
</form>