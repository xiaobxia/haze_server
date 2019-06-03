<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

 <form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/gotoUserManage?myId=${searchParams.myId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>ID：<input type="text" name="id"
						value="${searchParams.id }" />
					</td>
					<!-- <td>用户类型: <select name="userType">
							<option value="">不限</option>
							<%--<c:forEach items="${USER_TYPE }" var="type">--%>
								<%--<c:if test="${type.key ne USER_TYPE_SYSTEM }">--%>
									<%--<option value="${type.key }"--%>
										<%--<c:if test="${searchParams.userSystem eq type.key }">selected="selected"</c:if>>${type.value--%>
										<%--}</option>--%>
								<%--</c:if>--%>
							<%--</c:forEach>--%>
					</select>
					</td> -->
					<td>真实姓名: <input type="text" name="realname"
						value="${searchParams.realname }" />
					</td>
					<td>证件号码: <input type="text" name="idNumber"
						value="${searchParams.idNumber }" />
					</td>
					<td>手机: <input type="text" name="userPhone"
								   value="${searchParams.userPhone }" />
					</td>
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
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${searchParams.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" width="100%" layoutH="114">
			<thead>
				<tr>
					<th align="center">用户ID</th>
					<th align="center">姓名</th>
					<%--<th align="center">公司名称</th>--%>
					<th align="center">联系方式</th>
					<%--<th align="center">生日</th>--%>
					<th align="center">性别</th>
					<!-- <th align="center">类型</th>
					<th align="center">状态</th>
					<th align="center">可再借时间</th> -->
					<th align="center">是否黑名单</th>
					<th align="center">创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${pm.items }" varStatus="status" >
					<tr target="userId" rel="${user.id }">
						<td align="center">${user.id }</td>
						<td align="center">${user.realname}</td>
						<%--<td align="center">${user.company_name }</td>--%>
						<td align="center">${user.user_phone }</td>
						<%--<td align="center">
							<c:choose>
								<c:when test="${user.id_number!=null && user.id_number!=''}">${fn:substring(user.id_number, 6, 10)}年${fn:substring(user.id_number, 10, 12)}月${fn:substring(user.id_number, 12, 14)}日</c:when>
								<c:otherwise></c:otherwise>
							</c:choose> 
						</td>--%>
						<td align="center">${user.user_sex }</td>
						<%-- <td align="center">${user.user_type }</td>
						<td align="center"></td> --%>
						
					<c:if test="${user.status==2}">
						<td align="center">是</td>
					</c:if>
					<c:if test="${user.status!=2}">
						<td align="center">否</td>
					</c:if>
						
						<td align="center">
						<fmt:formatDate value="${user.create_time }" pattern="yyyy-MM-dd HH:mm" />
							${ALL_ORIGIN_TYPE[user.originType] }
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
		
	</script>
</form>
